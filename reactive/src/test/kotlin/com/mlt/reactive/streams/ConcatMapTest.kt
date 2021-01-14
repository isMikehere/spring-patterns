package com.mlt.reactive.com.mlt.reactive.streams

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class ConcatMapTest {

    @Test
    fun concatMap() {
        val data = Flux
            .just(Pair(1, 300L), Pair(2, 200L), Pair(3, 100L))
            .concatMap { pair -> delayReplyFor(pair.first, pair.second) }

        StepVerifier
            .create(data)
            .expectNext(1, 2, 3)
            .verifyComplete()
    }

    private fun delayReplyFor(i: Int, delay: Long): Flux<Int?> {
        return Flux.just(i).delayElements(Duration.ofMillis(delay))
    }

}