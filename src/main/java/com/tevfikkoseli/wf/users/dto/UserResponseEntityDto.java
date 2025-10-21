package com.tevfikkoseli.wf.users.dto;

import java.util.UUID;

public class UserResponseEntityDto {

    private String id;

    private String firstName;


    private String lastName;


    public UserResponseEntityDto(String id, String fistName, String lastName) {
        this.id = id;
        this.firstName = fistName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFistName() {
        return firstName;
    }

    public void setFistName(String fistName) {
        this.firstName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
