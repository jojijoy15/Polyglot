package streams.collectors;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CustomLinkedHashSetCollector<T> implements Collector<T, Set<T>, Set<T>> {

  @Override
  public Supplier<Set<T>> supplier() {
    return LinkedHashSet::new;
  }

  @Override
  public BiConsumer<Set<T>, T> accumulator() {
    return Set::add;
  }

  @Override
  public BinaryOperator<Set<T>> combiner() {
    return (a, b) -> {
      a.addAll(b);
      return a;
    };
  }

  @Override
  public Function<Set<T>, Set<T>> finisher() {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of(Characteristics.IDENTITY_FINISH);
  }
}
