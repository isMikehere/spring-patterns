package com.mlt.reactive.com.mlt.reactive.streams

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.concurrent.atomic.AtomicInteger


class OnErrorResumeTest {
    private val resultsInError: Flux<Int> = Flux
        .just(1, 2, 3)
        .flatMap {
            when (it == 2) {
                true -> Flux.error { IllegalArgumentException("ops!!") }
                else -> Flux.just(it)
            }
        }

    @Test
    fun onErrorResume() {
        val onErrorResume = resultsInError.onErrorResume(IllegalArgumentException::class.java) {
            Flux.just(3, 2, 1)
        }
        StepVerifier.create(onErrorResume).expectNext(1, 3, 2, 1).verifyComplete();
    }

    @Test
    fun onErrorReturn() {
        val onErrorReturn = resultsInError.onErrorReturn(0)
        StepVerifier.create(onErrorReturn).expectNext(1, 0).verifyComplete()
    }


    @Test
    fun onErrorMap() {
        class GenericException : RuntimeException()

        val counter = AtomicInteger()
        val resultsInError = Flux.error<Int>(IllegalArgumentException("oops!"))
        val errorHandlingStream = resultsInError
            .onErrorMap(IllegalArgumentException::class.java) {
                GenericException()
            }
            .doOnError(GenericException::class.java) {
                counter.incrementAndGet()
            }
        StepVerifier.create(errorHandlingStream).expectError().verify()
        assertEquals(counter.get(), 1)
    }
}