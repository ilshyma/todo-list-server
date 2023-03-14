package com.star.todolistserver.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class User {

    private final UUID id = UUID.randomUUID();
    private String name;
    private UserGroup userGroup;

    public UserDto createDto(){
        return new UserDto(id.toString(), name, userGroup);
    }
}
