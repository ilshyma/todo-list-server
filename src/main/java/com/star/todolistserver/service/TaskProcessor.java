package com.star.todolistserver.service;


import com.star.todolistserver.exceptions.DtoValidateException;
import com.star.todolistserver.exceptions.UserNotFoundException;
import com.star.todolistserver.pojo.Task;
import com.star.todolistserver.pojo.TaskDto;
import com.star.todolistserver.pojo.User;
import com.star.todolistserver.pojo.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskProcessor {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    public List<TaskDto> getTasksByUserIds(List<UUID> userIds) {
        List<User> filteredUserIds = userService.getAllUser().stream().filter(u -> userIds.contains(u.getId())).collect(Collectors.toList());
        List<TaskDto> resultList = new ArrayList<>();
        filteredUserIds.forEach(u -> {
            List<Task> tasksByUserId = taskService.getTasksByUserId(u.getId());
            resultList.addAll(createTaskDtos(tasksByUserId, u));
            log.info("Found {} tasks for userId {}", tasksByUserId.size(), u.getId());
        });
        return resultList;
    }

    private List<TaskDto> createTaskDtos(List<Task> userTasks, User u) {
        List<TaskDto> resultList = new ArrayList<>();
        userTasks.forEach(task -> {
            resultList.add(
                    TaskDto.builder()
                            .taskName(task.getTaskName())
                            .taskPriority(task.getTaskPriority())
                            .user(u.createDto())
                            .taskId(task.getId())
                            .date(task.getDate())
                            .build());
        });
        return resultList;
    }

    public void deleteTask(UUID taskId) {
        taskService.deleteTask(taskId);
    }

    public void createTask(TaskDto taskDto) {
        basicValidation(taskDto);
        taskService.addTask(taskDto);
    }

    private void basicValidation(TaskDto taskDto) {
        UserDto user = taskDto.getUser();
        if (user == null) {
            throw new DtoValidateException("user");
        }
        if (user.getId() == null) {
            throw new DtoValidateException("user.id");
        }
        UUID userUuid;
        try {
            userUuid = UUID.fromString(user.getId());
        } catch (Exception e) {
            throw new UserNotFoundException(user.getId());
        }
        if (userService.getAllUser().stream().map(User::getId).noneMatch(uId -> Objects.equals(uId, userUuid))) {
            throw new UserNotFoundException(user.getId());
        }
        if (Strings.isBlank(taskDto.getTaskName())) {
            throw new DtoValidateException("taskName");
        }
        if (taskDto.getTaskPriority() == null) {
            throw new DtoValidateException("taskPriority");
        }
    }

    public void modify(TaskDto taskDto) {
        basicValidation(taskDto);
        if (taskDto.getTaskId() == null) {
            throw new DtoValidateException("taskId");
        }
        taskService.modify(taskDto);
    }

    public User createUser(UserDto dto) {
        if (dto.getUserGroup() == null) {
            throw new DtoValidateException("userGroup");
        }
        if (Strings.isBlank(dto.getName())) {
            throw new DtoValidateException("name");
        }

        User user = userService.addUser(dto);
        taskService.initNewTaskList(user.getId());
        return user;
    }
}
