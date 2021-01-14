package com.mlt.reactive.com.mlt.reactive.streams

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

class SchedulersHookTest {
    @Test
    fun onScheduleHook() {
        val counter = AtomicInteger()
        Schedulers.onScheduleHook("my hook") { runnable ->
            Runnable {
                val threadName = Thread.currentThread().name
                counter.incrementAndGet()
                print("before execution: $threadName")
                runnable.run()
                print("afterEmitterProcessor execution: $threadName")
            }
        }
        val integerFlux = Flux.just(1, 2, 3)
            .delayElements(Duration.ofMillis(1))
            .subscribeOn(Schedulers.immediate())
        StepVerifier.create(integerFlux).expectNext(1, 2, 3).verifyComplete()
        assertEquals(3, counter.get()) {
            "count should be 3"
        }
    }
}