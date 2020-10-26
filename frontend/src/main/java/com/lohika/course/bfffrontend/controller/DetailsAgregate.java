package com.lohika.course.bfffrontend.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/details")
@Slf4j
public class DetailsAgregate {

    private WebClient authorClient;
    private WebClient bookClient;

    public DetailsAgregate(@Value("${books.url}") String booksUrl,
            @Value("${authors.url}") String authorsUrl,
            WebClient.Builder webClientBuilder) {
        this.authorClient = webClientBuilder.clone().baseUrl(authorsUrl).build();
        this.bookClient = webClientBuilder.clone().baseUrl(booksUrl).build();
    }

    @NewSpan("getBooksAndAuthors span name")
    @GetMapping
    public Mono<Map> getBooksAndAuthors() {
        Mono<Object> authors = authorClient.get().retrieve().bodyToMono(Object.class);
        Mono<Object> books = bookClient.get().retrieve().bodyToMono(Object.class);
        return authors.zipWith(books).map(t -> {
            Map<String, Object> result = new HashMap<>();
            result.put("authors", t.getT1());
            result.put("books", t.getT2());
            return result;
        });
    }
}
