package designpatterns.creational.singleton.attack.impl.simple;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.impl.SimpleSingleton;

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

            List<Callable<SimpleSingleton>> callables = simpleSingletonBreakTaskProvider(nThreads);
            List<Future<SimpleSingleton>> futures = ex.invokeAll(callables);
            Thread.sleep(5000);
            List<SimpleSingleton> list = futures.stream().map(f -> {
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

    private List<Callable<SimpleSingleton>> simpleSingletonBreakTaskProvider(int nThreads) {
        return IntStream.range(0, nThreads)
                .<Callable<SimpleSingleton>>mapToObj(e -> SimpleSingleton::getInstance)
                .toList();
    }
}
