package com.billion.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder.routes()
                .route("quizz-category", route -> route.path("/category/**")
                                .filters(f -> f.rewritePath("/category/?(?<remaining>.*)", "/${remaining}")
                                        .circuitBreaker(c -> c.setName("categoryCB").setFallbackUri("forward:/categoryFallback")
                                        )
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver()))
                                )

                                .uri("lb://QUIZZ-CATEGORY")

                        //.uri("lb://QUIZZ-CATEGORY")
                )
                .route("QUIZZ-SERVICE", route -> route.path("/quizz/**")
                                .filters(f -> f.rewritePath("/quizz/?(?<remaining>.*)", "/${remaining}")
                                        .retry(retryConfig -> retryConfig.setRetries(3)
                                                .setMethods(HttpMethod.GET)
                                                .setBackoff(Duration.ofMillis(50), Duration.ofMillis(600), 2, true)

                                        )
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver()))
                                )
                                .uri("lb://QUIZZ-SERVICE")
                        //.uri("lb://QUIZZ-CATEGORY")
                )

                .build();

    }

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            System.out.println(exchange.getRequest().getHeaders().getHost().getHostName());
            return Mono.just(exchange.getRequest().getHeaders().getHost().getHostName());
        };
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5, 5);
    }
}
