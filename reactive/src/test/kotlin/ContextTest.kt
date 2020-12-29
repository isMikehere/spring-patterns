package com.mlt.reactive

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.SignalType
import reactor.util.context.Context
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger


class ContextTest {

    @Test
    fun context() {
        val observedContextValues = ConcurrentHashMap<String, AtomicInteger>()
        val max = 3
        val key = "key1"
        val cdl = CountDownLatch(max)
        val context = Context.of(key, "value1")
        val just = Flux//
            .range(0, max)//
            .delayElements(Duration.ofMillis(1))//
            .doOnEach {
                when (it.type) {
                    SignalType.ON_NEXT -> {
                        val key1: String = it.contextView.get(key)
                        assertNotNull(key1)
                        assertEquals(key1, "value1")
                        observedContextValues
                            .computeIfAbsent("key1") { AtomicInteger(0) }
                            .incrementAndGet()
                    }
                    else -> {

                    }
                }
            }
            .contextWrite(context)

        just.subscribe {
            println("integer: $it")
            cdl.countDown()
        }
        cdl.await();
        observedContextValues[key]?.let { assertEquals(it.get(), max) };
    }
}