package com.problems.learning.system.lb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class LoadBalancerTest {

    private Server server1;
    private Server server2;
    private Server server3;

    @BeforeEach
    void setUp() {
        server1 = new Server("s1", "192.168.1.1", 8080);
        server2 = new Server("s2", "192.168.1.2", 8081);
        server3 = new Server("s3", "192.168.1.3", 8082);
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Round Robin Strategy")
    class RoundRobinTests {

        @Test
        @DisplayName("distributes requests sequentially across servers")
        void shouldDistributeSequentially() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);
            lb.addServer(server2);
            lb.addServer(server3);

            assertThat(lb.routeRequest(new Request("r1", ""))).isEqualTo(server1);
            assertThat(lb.routeRequest(new Request("r2", ""))).isEqualTo(server2);
            assertThat(lb.routeRequest(new Request("r3", ""))).isEqualTo(server3);
            // wraps around
            assertThat(lb.routeRequest(new Request("r4", ""))).isEqualTo(server1);
        }

        @Test
        @DisplayName("skips unhealthy servers")
        void shouldSkipUnhealthyServers() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);
            lb.addServer(server2);
            lb.markServerDown("s2");

            Server selected = lb.routeRequest(new Request("r1", ""));
            assertThat(selected).isEqualTo(server1);
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Weighted Round Robin Strategy")
    class WeightedRoundRobinTests {

        @Test
        @DisplayName("higher weight servers get more requests")
        void shouldFavorHigherWeightServers() {
            Server heavy = new Server("heavy", "10.0.0.1", 8080, 3);
            Server light = new Server("light", "10.0.0.2", 8081, 1);

            LoadBalancer lb = new LoadBalancer(new WeightedRoundRobinStrategy());
            lb.addServer(heavy);
            lb.addServer(light);

            int heavyCount = 0;
            int lightCount = 0;
            for (int i = 0; i < 8; i++) {
                Server s = lb.routeRequest(new Request("r" + i, ""));
                if (s.equals(heavy)) heavyCount++;
                else lightCount++;
                lb.releaseConnection(s);
            }

            // With weights 3:1, over 8 requests (2 full cycles of 4), heavy should get 6, light 2
            assertThat(heavyCount).isEqualTo(6);
            assertThat(lightCount).isEqualTo(2);
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Least Connections Strategy")
    class LeastConnectionsTests {

        @Test
        @DisplayName("routes to server with fewest active connections")
        void shouldRouteToLeastBusyServer() {
            LoadBalancer lb = new LoadBalancer(new LeastConnectionsStrategy());
            lb.addServer(server1);
            lb.addServer(server2);
            lb.addServer(server3);

            // Simulate server1 having 2 connections, server2 having 1
            lb.routeRequest(new Request("r1", "")); // server1 gets 1 (all at 0, picks first min)
            lb.routeRequest(new Request("r2", "")); // server2 gets 1 (server2 & server3 at 0)

            // Now server1=1, server2=1, server3=0 → should pick server3
            Server selected = lb.routeRequest(new Request("r3", ""));
            assertThat(selected).isEqualTo(server3);
        }

        @Test
        @DisplayName("rebalances after releasing connections")
        void shouldRebalanceAfterRelease() {
            LoadBalancer lb = new LoadBalancer(new LeastConnectionsStrategy());
            lb.addServer(server1);
            lb.addServer(server2);

            Server s1 = lb.routeRequest(new Request("r1", "")); // server1: 1
            lb.routeRequest(new Request("r2", ""));              // server2: 1
            lb.routeRequest(new Request("r3", ""));              // server1: 2 (both were at 1, picks first min → server1)

            lb.releaseConnection(s1); // server1: 1
            lb.releaseConnection(s1); // server1: 0

            // server1=0, server2=1 → should pick server1
            assertThat(lb.routeRequest(new Request("r4", ""))).isEqualTo(server1);
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Random Strategy")
    class RandomTests {

        @Test
        @DisplayName("selects from available servers")
        void shouldSelectFromPool() {
            LoadBalancer lb = new LoadBalancer(new RandomStrategy());
            lb.addServer(server1);
            lb.addServer(server2);
            lb.addServer(server3);

            Set<Server> selected = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                Server s = lb.routeRequest(new Request("r" + i, ""));
                selected.add(s);
                lb.releaseConnection(s);
            }

            // With 100 requests across 3 servers, all should be hit at least once
            assertThat(selected).containsExactlyInAnyOrder(server1, server2, server3);
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Server Pool Management")
    class ServerPoolTests {

        @Test
        @DisplayName("add and remove servers dynamically")
        void shouldAddAndRemoveServers() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);
            lb.addServer(server2);

            assertThat(lb.getAllServers()).hasSize(2);

            lb.removeServer("s1");
            assertThat(lb.getAllServers()).hasSize(1);
            assertThat(lb.getAllServers().get(0)).isEqualTo(server2);
        }

        @Test
        @DisplayName("does not add duplicate servers")
        void shouldNotAddDuplicates() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);
            lb.addServer(server1);

            assertThat(lb.getAllServers()).hasSize(1);
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Health Management")
    class HealthTests {

        @Test
        @DisplayName("throws when no healthy servers available")
        void shouldThrowWhenNoHealthyServers() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);
            lb.markServerDown("s1");

            assertThatThrownBy(() -> lb.routeRequest(new Request("r1", "")))
                    .isInstanceOf(NoHealthyServerException.class);
        }

        @Test
        @DisplayName("recovers when server is marked back up")
        void shouldRecoverWhenServerMarkedUp() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);

            lb.markServerDown("s1");
            assertThat(lb.getHealthyServers()).isEmpty();

            lb.markServerUp("s1");
            assertThat(lb.getHealthyServers()).hasSize(1);

            assertThatCode(() -> lb.routeRequest(new Request("r1", "")))
                    .doesNotThrowAnyException();
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Strategy Switching")
    class StrategySwitchingTests {

        @Test
        @DisplayName("can swap strategy at runtime")
        void shouldSwapStrategy() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            assertThat(lb.getStrategy()).isInstanceOf(RoundRobinStrategy.class);

            lb.setStrategy(new LeastConnectionsStrategy());
            assertThat(lb.getStrategy()).isInstanceOf(LeastConnectionsStrategy.class);
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("Connection Tracking")
    class ConnectionTrackingTests {

        @Test
        @DisplayName("increments and decrements active connections")
        void shouldTrackConnections() {
            LoadBalancer lb = new LoadBalancer(new RoundRobinStrategy());
            lb.addServer(server1);

            Server s = lb.routeRequest(new Request("r1", ""));
            assertThat(s.getActiveConnections()).isEqualTo(1);

            lb.routeRequest(new Request("r2", ""));
            assertThat(s.getActiveConnections()).isEqualTo(2);

            lb.releaseConnection(s);
            assertThat(s.getActiveConnections()).isEqualTo(1);

            lb.releaseConnection(s);
            assertThat(s.getActiveConnections()).isEqualTo(0);
        }

        @Test
        @DisplayName("does not go below zero connections")
        void shouldNotGoBelowZero() {
            server1.decrementConnections();
            assertThat(server1.getActiveConnections()).isEqualTo(0);
        }
    }
}

