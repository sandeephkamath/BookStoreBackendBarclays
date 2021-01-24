package com.barclays.bookstore.etl;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.barclays.bookstore.book.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@RestClientTest(BookClient.class)
class BookClientTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllBooks_expectBooks_fromExternalService() throws JsonProcessingException {
        String url = "https://www.examplebookstore.com/";
        RestTemplate restTemplate = restTemplateBuilder.build();
        BookClient bookClient = new BookClient(restTemplate, url);
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer
            .createServer(restTemplate);
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess().body(objectMapper.writeValueAsString(getBooks()))
                .contentType(MediaType.APPLICATION_JSON));
        assertThat(bookClient.getAllBooks()).isEqualTo(getBooks());
    }

    private List<Book> getBooks() {
        return singletonList(Book.builder().bookId(1L).title("Harry Potter").build());
    }
}