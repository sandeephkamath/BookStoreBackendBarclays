package com.barclays.bookstore.etl;

import com.barclays.bookstore.book.Book;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class BookClient {

    private final RestTemplate restTemplate;
    private final String url;

    public BookClient(RestTemplate restTemplate,
        @Value("${service.books}") String url) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public List<Book> getAllBooks() {
        try {
            ResponseEntity<List<Book>> rateResponse =
                restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {
                    });
            return rateResponse.getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return new ArrayList<>();
        }
    }

}
