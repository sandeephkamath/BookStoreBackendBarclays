package com.barclays.bookstore.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PaymentClient {

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;
    private final String apiToken;
    private static final String API_KEY="X-Api-Key";
    private static final String API_TOKEN="X-Auth-Token";

    public PaymentClient(RestTemplate restTemplate,
        @Value("${service.payment}") String url,
        @Value("${payment.credentials.key}") String apiKey,
        @Value("${payment.credentials.token}") String apiToken) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.apiKey=apiKey;
        this.apiToken=apiToken;
    }

    public PaymentResponse post(PaymentRequestDetails paymentRequestDetails ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY, apiKey);
        headers.set(API_TOKEN, apiToken);
        HttpEntity<PaymentRequestDetails> request =
            new HttpEntity<>(paymentRequestDetails, headers);
        try {
            PaymentResponse paymentResponse = restTemplate
                .postForObject(url, request, PaymentResponse.class);
            log.info(paymentResponse.toString());
            return paymentResponse;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
        return null;
    }


}
