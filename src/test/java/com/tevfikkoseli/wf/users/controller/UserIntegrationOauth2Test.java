package com.tevfikkoseli.wf.users.controller;


import com.tevfikkoseli.wf.users.configuration.EmbeddedMongoNoAuthConfig;
import com.tevfikkoseli.wf.users.configuration.TestSecurityConfig;
import com.tevfikkoseli.wf.users.data.UserEntity;
import com.tevfikkoseli.wf.users.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Import({EmbeddedMongoNoAuthConfig.class, TestSecurityConfig.class})

public class UserIntegrationOauth2Test {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

//    @BeforeEach
//    void cleanDb() {
//        userRepository.deleteAll().block();
//    }

    @Test
    void sholudCreateUserInReactiveMongo()  {
        UserEntity user = new UserEntity(null, "Tevfik", "Koseli", "tkoseli@hotmail.com", "123456");

//        String json = """
//                {"firstName":"Tevfik","lastName":"Koseli", "email":"tkoseli@hotmail.com", "password":"123456"}
//                """;
        webTestClient.mutateWith(mockJwt()
                        .jwt(jwt -> jwt.claim("scope", "user.write")
                                 .claim("sub", "tevfik"))
                        .authorities(new SimpleGrantedAuthority("SCOPE_user.write")))
                        .post()
                        .uri("/user")
                        .bodyValue(user)
                        .exchange()
                        .expectStatus().isCreated()
                .expectBody(UserEntity.class)
                .value(saved -> {
                    assertThat (saved.getId()).isNotNull();
                    assertThat(saved.getLastName()).isEqualTo("Koseli");
                });

        var saved = userRepository.findAll().collectList().block();
                assertThat(saved).hasSize(1);
                assertThat(saved.get(0).getFirstName()).isEqualTo("Tevfik");
    }



    @Test
    void shouldCreateUserAndSaveEmailInMongo() {
        UserEntity user = new UserEntity(null, "Tevfik", "Koseli", "tkoseli@hotmail.com", "123456");

        // 1️⃣ Make API call
        webTestClient.mutateWith(mockJwt()
                        .jwt(jwt -> jwt.claim("scope", "user.write").claim("sub", "tevfik"))
                        .authorities(new SimpleGrantedAuthority("SCOPE_user.write")))
                .post()
                .uri("/user")
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated();

        // 2️⃣ Verify MongoDB contains the user with that email
        var savedUsers = userRepository.findAll().collectList().block();

        assertThat(savedUsers)
                .isNotEmpty()
                .anyMatch(u -> "tkoseli@hotmail.com".equals(u.getEmail()));

        // 3️⃣ (Optional) Verify firstName too
        assertThat(savedUsers.get(0).getFirstName()).isEqualTo("Tevfik");
    }

}
