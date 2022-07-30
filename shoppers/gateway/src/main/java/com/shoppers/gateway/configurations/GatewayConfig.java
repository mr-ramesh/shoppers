package com.shoppers.gateway.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

	@Autowired
	AuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("security", r -> r.path("/security/**").filters(f -> f.filter(filter)).uri("lb://SECURITY"))
				.route("product", r -> r.path("/product/**").filters(f -> f.filter(filter)).uri("lb://PRODUCT"))
				.route("order", r -> r.path("/order/**").filters(f -> f.filter(filter)).uri("lb://ORDER")).build();
	}

}