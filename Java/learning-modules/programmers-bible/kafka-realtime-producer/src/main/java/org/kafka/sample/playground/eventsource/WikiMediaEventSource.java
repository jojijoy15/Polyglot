package org.kafka.sample.playground.eventsource;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource.Builder;
import com.launchdarkly.eventsource.HttpConnectStrategy;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import java.net.URI;
import okhttp3.Headers;

public class WikiMediaEventSource {

  private static final String eventStreamSourceUrl = "https://stream.wikimedia.org/v2/stream/recentchange";

  /*
  public EventSource createEventSource(EventHandler handler) {
    return new Builder(handler, URI.create(eventStreamSourceUrl))
        .headers(Headers.of("Accept", "text/event-stream",
                            "User-Agent", "my-test-client/1.0 (your@email.com)"))
        .build();
  }
  */

  // Works with eventsource library version 4
  public BackgroundEventSource createEventSource(BackgroundEventHandler handler) {
    ConnectStrategy strategy = HttpConnectStrategy.http(URI.create(eventStreamSourceUrl))
        .headers(Headers.of("Accept", "text/event-stream",
                            "User-Agent", "my-test-client/1.0 (your@email.com)"));

    Builder builder = new Builder(strategy);
    return new BackgroundEventSource.Builder(handler, builder)
        .build();
  }

}
