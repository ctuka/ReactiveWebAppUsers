package com.tevfikkoseli.wf.users.dto;

import com.tevfikkoseli.wf.users.data.UserEntity;

import java.util.UUID;

public class Mapper {



    public UserEntity userRequestDtoToUserEntity ( UserRequestEntityDto userRequestDto) {
        UserEntity userEntity = new UserEntity(
                UUID.randomUUID().toString(),
                userRequestDto.getFirstName(),
                userRequestDto.getLastName(),
                userRequestDto.getEmail(),
                userRequestDto.getPassword()
        );
        return userEntity;
    }

    public UserResponseEntityDto userEntityToUseResponseDto (UserEntity userEntity)
    {
        return new UserResponseEntityDto(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName()
                );
    }

}
