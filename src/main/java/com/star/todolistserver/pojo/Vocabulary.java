package com.star.todolistserver.pojo;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Vocabulary {
    private final List<UserGroup> userGroups = Arrays.stream(UserGroup.values()).collect(Collectors.toList());
    private final List<TaskPriority> taskPriorities = Arrays.stream(TaskPriority.values()).collect(Collectors.toList());
}
