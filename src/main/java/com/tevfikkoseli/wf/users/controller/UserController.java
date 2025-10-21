package com.tevfikkoseli.wf.users.controller;


import com.mongodb.DuplicateKeyException;
import com.tevfikkoseli.wf.users.dto.UserRequestEntityDto;
import com.tevfikkoseli.wf.users.dto.UserResponseEntityDto;
import com.tevfikkoseli.wf.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    String getUser () {
        return "Users Microservice working ...";
    }

    @PostMapping
    Mono<ResponseEntity<UserResponseEntityDto>> createUser (@RequestBody UserRequestEntityDto userRequestDto) {


        return userService.createUser(Mono.just(userRequestDto)).map(
                response -> ResponseEntity.status(HttpStatus.CREATED)
                        .location(URI.create("/user/" + response.getId()))
                        .body(response));

    }

    @GetMapping("/userget")
    Mono<ResponseEntity<UserResponseEntityDto>> getUserById(@RequestParam String id) {
        return userService.findUserById(id).map(
                response -> ResponseEntity.status(HttpStatus.OK)
                        .body(response)).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .onErrorResume(DuplicateKeyException.class, ex ->
                        Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists"))
                );
    }

    @GetMapping("/all")
    Flux<UserResponseEntityDto> findAll() {
        return userService.findAll();
    }
}
