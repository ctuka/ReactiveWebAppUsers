package com.tevfikkoseli.wf.users.service;


import com.tevfikkoseli.wf.users.dto.UserRequestEntityDto;
import com.tevfikkoseli.wf.users.dto.UserResponseEntityDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService
{
      Mono<UserResponseEntityDto> createUser(Mono<UserRequestEntityDto> userRequestEntityDto) ;
      Mono<UserResponseEntityDto> findUserById(String id);
      Flux<UserResponseEntityDto> findAll();

}
