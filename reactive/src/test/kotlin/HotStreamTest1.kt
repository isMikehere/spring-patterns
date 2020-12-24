package com.mlt.reactive

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import reactor.core.publisher.Sinks
import java.util.function.Consumer


class HotStreamTest1 {

    @Test
    fun hot() {
        val first = mutableListOf<Int>()
        val second = mutableListOf<Int>()
        val emitter = Sinks.many().multicast().onBackpressureBuffer<Int>(10)
        emitter.asFlux().subscribe(collect(first))
        emitter.tryEmitNext(1)
        emitter.tryEmitNext(2)

        emitter.asFlux().subscribe(collect(second))
        emitter.tryEmitNext(3)
        emitter.tryEmitComplete()
        assertTrue(first.size > second.size)
    }

    private fun collect(collection: MutableList<Int>): Consumer<Int> {
        return Consumer { e: Int -> collection.add(e) }
    }
}