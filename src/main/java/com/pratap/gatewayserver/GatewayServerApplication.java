package com.pratap.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		String bankName = "narayanBank";
		return builder.routes()
				.route(p -> p
						.path("/" + bankName + "/accounts/**")
						.filters(f -> f.rewritePath("/" + bankName + "/accounts/(?<segment>.*)", "/accounts/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://ACCOUNTS")).
				route(p -> p
						.path("/" + bankName + "/loans/**")
						.filters(f -> f.rewritePath("/" + bankName + "/loans/(?<segment>.*)","/loans/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://LOANS")).
				route(p -> p
						.path("/" + bankName + "/cards/**")
						.filters(f -> f.rewritePath("/" + bankName + "/cards/(?<segment>.*)","/cards/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://CARDS")).build();
	}
}
