package com.shoppers.gateway.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.shoppers.gateway.model.TokenApiResponse;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

	@Value("${security.url}")
	private String securityUrl;

	@Autowired
	private RouterValidator routerValidator;

	@Autowired
	public WebClient.Builder webClientBuilder;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if (routerValidator.isSecured.test(request)) {
			if (this.isAuthMissing(request))
				return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			final String token = this.getAuthHeader(request).substring(7);
			try {
				log.info("Security URL : {}", securityUrl);
				return webClientBuilder.baseUrl(securityUrl).build().get()
						.uri(securityUrl + "/security/token/validateToken?token=" + token).retrieve()
						.bodyToMono(TokenApiResponse.class).flatMap(resp -> {
							if (!Boolean.parseBoolean(resp.getValue().toString()))
								return this.onError(exchange,
										resp.getErrorMessage().isEmpty() ? "Authorization header is invalid"
												: resp.getErrorMessage(),
										HttpStatus.UNAUTHORIZED);
							return chain.filter(exchange);
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chain.filter(exchange);
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}

	private boolean isAuthMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

}
