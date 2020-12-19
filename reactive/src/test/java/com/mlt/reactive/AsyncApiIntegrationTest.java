package com.mlt.reactive;


import org.junit.Test;

import org.junit.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncApiIntegrationTest {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Test
    public void async() {
        Flux<Integer> integers = Flux.create(emitter -> this.launch(emitter, 5));
        StepVerifier
                .create(integers.doFinally(signalType -> this.executorService.shutdown()))
                .expectNextCount(5)//
                .verifyComplete();
    }

    private void launch(FluxSink<Integer> integerFluxSink, int count) {
        this.executorService.submit(() -> {
            var integer = new AtomicInteger();
            Assert.assertNotNull(integerFluxSink);
            while (integer.get() < count) {
                double random = Math.random();
                integerFluxSink.next(integer.incrementAndGet());
                this.sleep((long) (random * 1_000));
            }
            integerFluxSink.complete();
        });
    }

    private void sleep(long s) {
        try {
            Thread.sleep(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}