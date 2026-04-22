package com.problems.learning.designpatterns.behavioral.template.client;

import com.problems.learning.designpatterns.behavioral.template.pipeline.CsvDataPipeline;
import com.problems.learning.designpatterns.behavioral.template.pipeline.DataPipeline;
import com.problems.learning.designpatterns.behavioral.template.pipeline.DbQueryPipeline;
import com.problems.learning.designpatterns.behavioral.template.pipeline.JsonApiPipeline;

public class DataPipelineETLApp {

    public static void main(String[] args) {

        // All three pipelines share the same execution contract
        // but each behaves differently inside each step

        DataPipeline csv = new CsvDataPipeline();
        DataPipeline json = new JsonApiPipeline();
        DataPipeline db = new DbQueryPipeline();

        csv.execute("data/employees.csv");
        json.execute("https://api.inventory.com/products");
        db.execute("jdbc:postgresql://localhost/orders");
    }
}