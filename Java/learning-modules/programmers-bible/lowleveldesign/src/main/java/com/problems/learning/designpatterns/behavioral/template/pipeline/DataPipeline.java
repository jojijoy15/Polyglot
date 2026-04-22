package com.problems.learning.designpatterns.behavioral.template.pipeline;

import java.util.List;

/**
 * PROBLEM SOLVED:
 * Without Template Method, each pipeline (CSV, JSON, DB) would
 * duplicate the same orchestration logic:
 * <p>
 * "read data, then validate, then transform, then save, then report"
 * <p>
 * This duplication means:
 * - A bug fix in the pipeline order must be applied in 3 places
 * - A new step (e.g. "encrypt before save") requires changes in all 3 classes
 * - Nothing enforces that all pipelines follow the same sequence
 * <p>
 * Template Method solves this by defining the SKELETON of the algorithm
 * once in the abstract class. Subclasses fill in the STEPS — but they
 * can never change the order or structure of the pipeline.
 */
public abstract class DataPipeline {

    // ----------------------------------------------------------------
    // THE TEMPLATE METHOD — final so subclasses cannot reorder steps
    // ----------------------------------------------------------------

    /**
     * PROBLEM SOLVED:
     * Marked final so no subclass can accidentally override the pipeline
     * sequence. The order read→validate→transform→save→report is
     * guaranteed to be the same for every data source.
     */
    public final void execute(String source) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.printf("║  Pipeline: %-30s ║%n", getClass().getSimpleName());
        System.out.println("╚══════════════════════════════════════════╝");

        long start = System.currentTimeMillis();

        // Step 1 — always runs for every pipeline
        List<String> rawData = readData(source);

        // Step 2 — always runs, but each pipeline validates differently
        if (!validate(rawData)) {
            System.out.println("❌ Validation failed — pipeline aborted.");
            return;
        }

        // Step 3 — transformation logic differs per source
        List<String> processed = transform(rawData);

        // Step 4 — HOOK: subclass can optionally enrich data before saving
        //          Default is a no-op — subclasses override only if needed
        processed = enrich(processed);

        // Step 5 — save logic differs per destination
        save(processed);

        // Step 6 — always runs regardless of pipeline type
        long duration = System.currentTimeMillis() - start;
        report(rawData.size(), processed.size(), duration);
    }

    // ----------------------------------------------------------------
    // ABSTRACT STEPS — subclasses MUST implement these
    // ----------------------------------------------------------------

    /**
     * PROBLEM SOLVED:
     * Reading from a CSV file, a REST API, or a database are completely
     * different operations. Forcing them into abstract methods means
     * each subclass handles its own reading — while the pipeline
     * sequence stays locked in the template.
     */
    protected abstract List<String> readData(String source);

    /**
     * PROBLEM SOLVED:
     * CSV validation checks column counts, JSON validation checks
     * schema, DB validation checks row integrity. Each is different —
     * but ALL pipelines must validate. Making it abstract enforces
     * this contract: you cannot build a pipeline that skips validation.
     */
    protected abstract boolean validate(List<String> rawData);

    /**
     * PROBLEM SOLVED:
     * Transformation is highly source-specific — CSV needs parsing,
     * JSON needs field extraction, DB results need formatting.
     * Abstract method ensures each pipeline owns its transformation
     * without the base class needing to know the data format.
     */
    protected abstract List<String> transform(List<String> rawData);

    /**
     * PROBLEM SOLVED:
     * Saving to a file, an API endpoint, or back to a DB are all
     * different. Abstract method ensures the pipeline always saves —
     * but delegates HOW to the subclass.
     */
    protected abstract void save(List<String> processedData);

    // ----------------------------------------------------------------
    // HOOK METHOD — subclasses MAY override but don't have to
    // ----------------------------------------------------------------

    /**
     * PROBLEM SOLVED:
     * Some pipelines need an extra enrichment step (e.g. geo-lookup,
     * currency conversion) but others don't. A hook provides an
     * optional extension point — without forcing every subclass to
     * implement a no-op method just to satisfy a contract.
     * <p>
     * Default implementation: returns data unchanged (no-op).
     * Subclasses override only when they need it.
     */
    protected List<String> enrich(List<String> data) {
        return data; // default: pass through untouched
    }

    // ----------------------------------------------------------------
    // CONCRETE STEP — same for ALL pipelines, no override needed
    // ----------------------------------------------------------------

    /**
     * PROBLEM SOLVED:
     * Report generation is identical for all pipelines — input count,
     * output count, duration. Defining it as a concrete method means
     * it is written once and inherited by all subclasses.
     * No duplication, no inconsistency.
     */
    private void report(int inputCount, int outputCount, long durationMs) {
        System.out.println("\n📊 Pipeline Report ─────────────────────");
        System.out.println("   Input records  : " + inputCount);
        System.out.println("   Output records : " + outputCount);
        System.out.println("   Dropped        : " + (inputCount - outputCount));
        System.out.println("   Duration       : " + durationMs + "ms");
        System.out.println("────────────────────────────────────────");
    }
}