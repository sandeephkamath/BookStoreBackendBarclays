package com.barclays.bookstore.image;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

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
@RestClientTest(ImageClient.class)
class ImageClientTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllImages_expectImages_fromExternalService() throws JsonProcessingException {
        String url = "https://www.exampleimagestore.com/";
        RestTemplate restTemplate = restTemplateBuilder.build();
        ImageClient imageClient = new ImageClient(restTemplate, url);

        MockRestServiceServer mockRestServiceServer = MockRestServiceServer
            .createServer(restTemplate);
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess().body(objectMapper.writeValueAsString(getImages()))
                .contentType(MediaType.APPLICATION_JSON));

        assertThat(imageClient.getAllImages()).isEqualTo(getImages());
    }

    private List<Image> getImages() {
        return singletonList(Image.builder().image("http://www.pic1.com.").build());
    }
}