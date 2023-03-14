package com.star.todolistserver.service;

import com.star.todolistserver.pojo.Task;
import com.star.todolistserver.pojo.User;
import com.star.todolistserver.pojo.UserDto;
import com.star.todolistserver.pojo.UserGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private Map<UUID, User> userStorage;
    @PostConstruct
    public void init(){
        userStorage = new HashMap<>();
        User user1 = User.builder()
                .name("Vlad")
                .userGroup(UserGroup.USER)
                .build();
        userStorage.put(user1.getId(), user1);

        User user2 = User.builder()
                .name("Oksana")
                .userGroup(UserGroup.ADMIN)
                .build();
        userStorage.put(user2.getId(), user2);
    }

    public List<User> getAllUser(){
        return new ArrayList<>(userStorage.values());
    }

    public User addUser(UserDto dto) {
        User newUser = User.builder().name(dto.getName()).userGroup(dto.getUserGroup()).build();
        userStorage.put(newUser.getId(), newUser);
        log.info("User {} was added", newUser);
        return newUser;
    }
}
