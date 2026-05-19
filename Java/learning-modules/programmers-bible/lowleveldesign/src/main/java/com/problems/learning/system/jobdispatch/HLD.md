# Job Dispatch System — High-Level Design

## 1. Problem Statement

Dispatchers assign jobs (with varying durations) to field technicians. The system must:
- Let dispatchers create and assign jobs to **available** technicians
- Enforce a **10-hour daily limit** per technician
- Send **real-time notification alerts** to technicians on assignment
- Let both dispatchers and technicians view **job status**

---

## 2. Functional Requirements

| # | Requirement |
|---|-------------|
| FR1 | Dispatcher creates a job with estimated duration |
| FR2 | System shows available technicians (remaining capacity ≥ job duration) |
| FR3 | Dispatcher assigns job → system validates capacity & updates status |
| FR4 | Technician receives push/SMS notification on assignment |
| FR5 | Technician updates job status (In-Progress → Completed / Failed) |
| FR6 | Both dispatcher & technician can query job status in real time |

## 3. Non-Functional Requirements

- **Low latency** for assignment (< 500ms)
- **At-least-once** notification delivery
- **Consistency** — no double-booking beyond 10 hrs
- **Scalable** to thousands of dispatchers & technicians
- **Audit trail** for every state transition

---

## 4. High-Level Architecture

```
┌──────────────┐        ┌──────────────────┐        ┌──────────────────┐
│  Dispatcher   │──REST──▶  API Gateway /   │──────▶│  Job Service     │
│  Web/Mobile   │        │  Load Balancer   │        │  (Core Logic)    │
└──────────────┘        └──────────────────┘        └────────┬─────────┘
                                                             │
┌──────────────┐        ┌──────────────────┐                 │
│  Technician   │◀─Push──│  Notification    │◀──Events───────┤
│  Mobile App   │        │  Service         │                │
└──────────────┘        └──────────────────┘                 │
                                                             │
                        ┌──────────────────┐                 │
                        │  Technician      │◀────Query───────┤
                        │  Availability    │                 │
                        │  Service         │                 │
                        └──────────────────┘                 │
                                                             │
                        ┌──────────────────┐                 │
                        │  Status Query    │◀────────────────┘
                        │  Service         │
                        └────────┬─────────┘
                                 │
                        ┌────────▼─────────┐
                        │    Databases     │
                        │  (Job DB, Tech   │
                        │   Availability)  │
                        └──────────────────┘
```

---

## 5. Core Components

### 5.1 Job Service
- **Create Job** — validates fields, persists with status `CREATED`
- **Assign Job** — orchestrates the assignment:
  1. Calls Technician Availability Service to check remaining hours
  2. Atomically reserves capacity (optimistic lock / CAS)
  3. Updates job status → `ASSIGNED`
  4. Publishes `JobAssigned` event to message broker

### 5.2 Technician Availability Service
- Maintains per-technician **daily allocated hours**
- `getRemainingHours(techId, date)` → `10 - sum(assigned job durations)`
- `reserveCapacity(techId, hours)` — atomic deduction with concurrency protection
- Exposes `getAvailableTechnicians(requiredHours)` → list of eligible techs

### 5.3 Notification Service
- Subscribes to `JobAssigned` events from **message broker** (Kafka / SQS)
- Sends push notification (FCM/APNs) and/or SMS to the technician
- Retry & dead-letter queue for failed deliveries
- Stores notification log for audit

### 5.4 Status Query Service
- Read-optimized service (can use read replicas / cache)
- Returns job details + current status for dispatcher or technician
- Optionally uses **WebSocket / SSE** for real-time status updates on dashboards

---

## 6. Data Model

### Jobs Table
```
┌────────────────────────────────────────────────────────────┐
│ jobs                                                       │
├──────────────┬──────────┬──────────────────────────────────┤
│ job_id       │ UUID PK  │                                  │
│ title        │ VARCHAR  │ Job description                  │
│ duration_hrs │ DECIMAL  │ Estimated hours (e.g. 2.5)       │
│ status       │ ENUM     │ CREATED → ASSIGNED → IN_PROGRESS │
│              │          │ → COMPLETED / FAILED / CANCELLED │
│ dispatcher_id│ UUID FK  │ Who created it                   │
│ tech_id      │ UUID FK  │ Assigned technician (nullable)   │
│ assigned_at  │ TIMESTAMP│                                  │
│ completed_at │ TIMESTAMP│                                  │
│ created_at   │ TIMESTAMP│                                  │
│ version      │ INT      │ For optimistic locking           │
└──────────────┴──────────┴──────────────────────────────────┘
```

### Technician Availability Table
```
┌────────────────────────────────────────────────────┐
│ technician_daily_capacity                          │
├──────────────┬──────────┬──────────────────────────┤
│ tech_id      │ UUID PK  │                          │
│ work_date    │ DATE PK  │ Composite PK             │
│ allocated_hrs│ DECIMAL  │ Sum of assigned durations │
│ max_hrs      │ DECIMAL  │ Default 10               │
│ version      │ INT      │ Optimistic locking       │
└──────────────┴──────────┴──────────────────────────┘

Constraint: allocated_hrs <= max_hrs  (DB CHECK constraint)
```

### Technicians Table
```
┌───────────────────────────────────────┐
│ technicians                           │
├──────────────┬──────────┬─────────────┤
│ tech_id      │ UUID PK  │             │
│ name         │ VARCHAR  │             │
│ phone        │ VARCHAR  │ For SMS     │
│ device_token │ VARCHAR  │ For push    │
│ is_active    │ BOOLEAN  │             │
└──────────────┴──────────┴─────────────┘
```

---

## 7. Job Assignment Flow (Sequence)

```
Dispatcher           API Gateway        Job Service       Tech Availability      Message Broker      Notification Svc      Technician
   │                     │                  │                    │                     │                    │                  │
   │──Create Job────────▶│─────────────────▶│                    │                     │                    │                  │
   │                     │                  │──Persist (CREATED)─│                     │                    │                  │
   │◀─────Job Created────│◀─────────────────│                    │                     │                    │                  │
   │                     │                  │                    │                     │                    │                  │
   │──Get Available──────▶│─────────────────▶│──getAvailable(hrs)▶│                     │                    │                  │
   │  Technicians        │                  │◀──techList─────────│                     │                    │                  │
   │◀─────Tech List──────│◀─────────────────│                    │                     │                    │                  │
   │                     │                  │                    │                     │                    │                  │
   │──Assign Job─────────▶│─────────────────▶│──reserveCapacity──▶│                     │                    │                  │
   │  (jobId, techId)    │                  │◀──OK───────────────│                     │                    │                  │
   │                     │                  │──Update ASSIGNED───│                     │                    │                  │
   │                     │                  │──Publish Event────────────────────────────▶│                    │                  │
   │◀─────Assigned───────│◀─────────────────│                    │                     │──Push/SMS─────────▶│                  │
   │                     │                  │                    │                     │                    │──Notification────▶│
```

---

## 8. API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/jobs` | Create a new job |
| GET    | `/api/jobs/{jobId}` | Get job status (dispatcher & tech) |
| GET    | `/api/jobs?dispatcherId={id}` | List jobs for a dispatcher |
| GET    | `/api/jobs?techId={id}` | List jobs for a technician |
| PUT    | `/api/jobs/{jobId}/assign` | Assign job to a technician |
| PUT    | `/api/jobs/{jobId}/status` | Update status (tech: IN_PROGRESS, COMPLETED, FAILED) |
| GET    | `/api/technicians/available?hours={h}&date={d}` | Get available technicians |
| GET    | `/api/technicians/{id}/capacity?date={d}` | Get remaining capacity |

---

## 9. Key Design Decisions

### 9.1 Concurrency — Preventing Over-Allocation
Two dispatchers assigning to the same technician simultaneously:

```sql
-- Optimistic locking on technician_daily_capacity
UPDATE technician_daily_capacity
SET allocated_hrs = allocated_hrs + :jobHrs, version = version + 1
WHERE tech_id = :techId AND work_date = :date
  AND version = :expectedVersion
  AND allocated_hrs + :jobHrs <= max_hrs;
-- If rows affected = 0 → conflict → retry or reject
```

### 9.2 Notification Reliability
- **Event-driven** via message broker (Kafka/SQS) → decoupled from assignment
- **Outbox pattern**: write event to `outbox` table in the same DB transaction as job update → relay to broker → guarantees no lost notifications
- Dead-letter queue for persistently failing notifications

### 9.3 Real-Time Status Updates
- **Polling**: simple GET endpoints (good enough for low frequency)
- **WebSocket/SSE**: for dispatcher dashboards needing live updates
- **Cache**: Redis cache for hot job status reads (invalidate on status change events)

---

## 10. Tech Stack Recommendation

| Layer | Technology |
|-------|-----------|
| API Gateway | AWS API Gateway / Kong / Spring Cloud Gateway |
| Job Service | Spring Boot (Java) |
| Availability Service | Spring Boot (Java) |
| Notification Service | Spring Boot / AWS Lambda |
| Database | PostgreSQL (ACID for capacity checks) |
| Message Broker | Apache Kafka / AWS SQS |
| Push Notifications | Firebase Cloud Messaging (FCM) / APNs |
| Cache | Redis |
| Real-time | WebSocket (Spring WebSocket) / SSE |

---

## 11. Capacity Constraint Enforcement Summary

```
Technician Daily Limit = 10 hours

On Assignment:
  remaining = 10 - SUM(duration_hrs) of ASSIGNED/IN_PROGRESS jobs for today
  IF job.duration_hrs > remaining → REJECT
  ELSE → atomically allocate (optimistic lock)

On Job Completion/Cancellation:
  Reclaim hours → allocated_hrs -= job.duration_hrs
  (Technician becomes available for more work)
```

---

## 12. Failure Scenarios & Handling

| Scenario | Handling |
|----------|---------|
| Notification fails | Retry with exponential backoff → DLQ → manual review |
| Double assignment race | Optimistic lock rejects second write → return "Tech unavailable" |
| Job service crashes mid-assign | Outbox pattern ensures event is published on recovery |
| Technician app offline | Push notification queued by FCM; status visible when app reopens |
| DB failover | Read-replica promotion; in-flight transactions retry |

