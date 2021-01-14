package com.mlt.reactive.orchestration

import com.mlt.reactive.common.Logging
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientAutoConfiguration : Logging {

    @Bean
    @ConditionalOnMissingBean
    fun loadBalancingWebClient(
        builder: WebClient.Builder,
        lbFunction: ReactorLoadBalancerExchangeFilterFunction
    ): WebClient {
        logger.info("registering a default load-balanced " + WebClient::class.java.name + '.');
        return builder.filter(lbFunction).build();
    }
}