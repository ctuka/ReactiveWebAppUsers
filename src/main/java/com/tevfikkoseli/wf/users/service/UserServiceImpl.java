package com.tevfikkoseli.wf.users.service;

import com.mongodb.DuplicateKeyException;
import com.tevfikkoseli.wf.users.data.UserEntity;
import com.tevfikkoseli.wf.users.data.UserRepository;
import com.tevfikkoseli.wf.users.dto.Mapper;
import com.tevfikkoseli.wf.users.dto.UserRequestEntityDto;
import com.tevfikkoseli.wf.users.dto.UserResponseEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<UserResponseEntityDto> createUser(Mono<UserRequestEntityDto> userRequestEntityDto) {



        return  userRequestEntityDto.map(mapper::userRequestDtoToUserEntity)
                .flatMap(userRepository::save)
                .map(mapper::userEntityToUseResponseDto)
                .onErrorMap(throwable -> {
                if(throwable instanceof DuplicateKeyException) {
                    return new ResponseStatusException(HttpStatus.CONFLICT, throwable.getMessage());
                }else
                {
                    return throwable;
                }

    });
    }

    @Override
    public Mono<UserResponseEntityDto> findUserById(String id) {

        return  userRepository.findUserEntityById(id)
                .map(userEntity -> mapper
                .userEntityToUseResponseDto(userEntity));
    }

    @Override
    public Flux<UserResponseEntityDto> findAll() {
        return userRepository.findAll().map(mapper::userEntityToUseResponseDto);
    }
}
