package designpatterns.creational.singleton.attack.impl.enumeration;

import designpatterns.creational.singleton.attack.FakeSingletonDetectorAttack;
import designpatterns.creational.singleton.impl.EnumSingleton;

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

            List<Callable<EnumSingleton>> callables = eagerSingletonBreakTaskProvider(nThreads);
            List<Future<EnumSingleton>> futures = ex.invokeAll(callables);
            List<EnumSingleton> list = futures.stream().map(f -> {
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
    
    private List<Callable<EnumSingleton>> eagerSingletonBreakTaskProvider(int nThreads) {
        return IntStream.range(0, nThreads)
                .<Callable<EnumSingleton>>mapToObj(e -> EnumSingleton::getInstance)
                .toList();
    }
}
