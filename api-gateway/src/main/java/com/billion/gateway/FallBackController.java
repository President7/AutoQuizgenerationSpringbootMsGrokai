package com.billion.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class FallBackController {

    @GetMapping("/categoryFallback")
    public Mono<String> categoryFallBack() {

        return Mono.just("Category service is down, Please try again latter... CB");
    }

}
