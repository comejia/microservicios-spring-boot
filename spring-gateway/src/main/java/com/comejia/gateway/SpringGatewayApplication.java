package com.comejia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGatewayApplication.class, args);
	}

	// Codigo en caso que no funcione "products" cuando el gateway es MVC
	// @Bean
	// RouterFunction<ServerResponse> routerFunction() {
	// 	return route("msvc-products")
	// 		.route(path("/api/products/**"), http())
	// 		.filter(lb("msvc-products"))
	// 		.filter(circuitBreaker(config -> config.setId("products")
	// 			.setStatusCodes("500")
	// 			.setFallbackPath("forward:/api/items/api/v1/items/5")))
	// 		.before(stripePrefix(2))
	// 		.build();
	// }
}
