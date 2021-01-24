package com.barclays.bookstore.image;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ImageClient {

    private final RestTemplate restTemplate;
    private final String url;

    public ImageClient(RestTemplate restTemplate,
        @Value("${service.images}") String url) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public List<Image> getAllImages() {
        try {
            ResponseEntity<List<Image>> rateResponse =
                restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Image>>() {
                    });
            return rateResponse.getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return new ArrayList<>();
        }
    }

}
