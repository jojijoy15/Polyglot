package designpatterns.behavioral.strategy.library.contract;

import java.util.Comparator;

public interface SortStrategy<T> {

    Comparator<T> sortComparator();
}
