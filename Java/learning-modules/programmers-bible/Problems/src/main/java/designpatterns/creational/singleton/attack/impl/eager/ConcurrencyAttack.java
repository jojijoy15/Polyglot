package designpatterns.creational.singleton.attack.impl.eager;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.impl.EagerSingleton;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ConcurrencyAttack implements FakeSingletonDetectorAttack {

    private static final Logger logger = Logger.getLogger(ConcurrencyAttack.class.getName());

    @Override
    public void breakSingleton() {

        int nThreads = Runtime.getRuntime().availableProcessors();
        logger.info("Concurrency attack on singleton instance begins with thread count : " + nThreads);
        try (ExecutorService ex = Executors.newFixedThreadPool(nThreads)) {

            List<Callable<EagerSingleton>> callables = eagerSingletonBreakTaskProvider(nThreads);
            List<Future<EagerSingleton>> futures = ex.invokeAll(callables);
            List<EagerSingleton> list = futures.stream().map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            long count = list.stream().distinct().count();
            logger.info("Total '" + count + "' Distinct Singletons created");

        } catch (InterruptedException e) {
           throw new RuntimeException(e);
       } finally {
           logger.info("Executors closed");
       }
    }
    
    private List<Callable<EagerSingleton>> eagerSingletonBreakTaskProvider(int nThreads) {
        return IntStream.range(0, nThreads)
                .<Callable<EagerSingleton>>mapToObj(e -> EagerSingleton::getInstance)
                .toList();
    }
}
