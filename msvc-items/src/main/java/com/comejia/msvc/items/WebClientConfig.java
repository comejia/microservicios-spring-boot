package com.comejia.msvc.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
// import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${config.baseurl.msvc-products}")
    private String url;

    // @Bean
    // @LoadBalanced
    // WebClient.Builder webClient() {
    //     return WebClient.builder().baseUrl(this.url);
    // }

    @Bean
    WebClient webClient(
        WebClient.Builder builder,
        @Value("${config.baseurl.msvc-products}") String url,
        ReactorLoadBalancerExchangeFilterFunction lbFunction
    ) {
        return builder.baseUrl(url)
            .filter(lbFunction)
            .build();
    }

}
