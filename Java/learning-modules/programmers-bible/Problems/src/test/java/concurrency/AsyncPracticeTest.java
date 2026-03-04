package concurrency;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import concurrency.async.AsyncPractice;
import org.junit.jupiter.api.Test;

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