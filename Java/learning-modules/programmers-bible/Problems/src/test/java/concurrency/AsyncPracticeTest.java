package concurrency;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

class AsyncPracticeTest {

  private AsyncPractice asyncPractice = new AsyncPractice();

  @Test
  void fetchTodo() {
    asyncPractice.fetchTodo()
        .thenApply(stringHttpResponse -> {
          try {
            return new ObjectMapper().readTree(stringHttpResponse.body());
          } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
            }

        })
        .handle((response , ex ) -> {
          if( null == ex) {
            throw new RuntimeException();
          } else {
            return response;
          }
        });
//        .exceptionally(ex -> new ObjectMapper().createObjectNode());
  }
}