package com.tevfikkoseli.wf.users.controller;


import com.tevfikkoseli.wf.users.data.UserEntity;
import com.tevfikkoseli.wf.users.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")

public class UserIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Test
    void sholudCreateUserInReactiveMongo()  {
        UserEntity user = new UserEntity(null, "Tevfik", "Koseli", "tkoseli@hotmail.com", "123456");

//        String json = """
//                {"firstName":"Tevfik","lastName":"Koseli", "email":"tkoseli@hotmail.com", "password":"123456"}
//                """;
        webTestClient.post()
                        .uri("/user")
                        .bodyValue(user)
                        .exchange()
                        .expectStatus().isCreated();

        userRepository.findAll()
                .doOnNext(p -> System.out.println("Find user :  " + p))
                .blockLast();
    }
}
