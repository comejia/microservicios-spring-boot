package com.comejia.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("PRE-REQUEST: Global filter executed");

        //exchange.getRequest().mutate().headers(h -> h.add("token", "abcdef"));
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header("token", "abcdef").build();

        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange).then(Mono.fromRunnable(() -> {
            logger.info("POST-REQUEST: Global filter executed");

            Optional<String> token = Optional.ofNullable(newExchange.getRequest().getHeaders().getFirst("token"));

            token.ifPresent(value -> {
                logger.info("POST-REQUEST: Token: {}", value);
                newExchange.getResponse().getHeaders().add("token", value);
            });

            newExchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
            //newExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
