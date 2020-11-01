package ru.oogis.controller.startController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestHelloReqest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test  // посылаем запрос с таким ответом v  return*
    public void helloReqest() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port +"/hello"
        , String.class)).contains("Hello world");
    }
}
