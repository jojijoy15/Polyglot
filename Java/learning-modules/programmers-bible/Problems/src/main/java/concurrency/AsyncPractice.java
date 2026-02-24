package concurrency;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class AsyncPractice {

  public CompletableFuture<HttpResponse<String>> fetchTodo() {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
        .build();
    return client.sendAsync(request, BodyHandlers.ofString());
  }

}
