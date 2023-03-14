package com.star.todolistserver.service;


import com.star.todolistserver.exceptions.TaskNotFoundException;
import com.star.todolistserver.exceptions.UserNotFoundException;
import com.star.todolistserver.pojo.Task;
import com.star.todolistserver.pojo.TaskDto;
import com.star.todolistserver.pojo.TaskPriority;
import com.star.todolistserver.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private UserService userService;
    private Map<UUID, List<Task>> userTaskStorage;

    @PostConstruct
    public void init() {
        userTaskStorage = new HashMap<>();
        userService.getAllUser().forEach(u -> {
            userTaskStorage.put(u.getId(),
                    List.of(
                            Task.builder()
                                    .taskName("tro-lo-lo 1")
                                    .taskPriority(TaskPriority.MEDIUM)
                                    .userId(u.getId())
                                    .date(LocalDate.now())
                                    .build(),
                            Task.builder()
                                    .taskName("tru-lu-lu 2")
                                    .taskPriority(TaskPriority.LOW)
                                    .userId(u.getId())
                                    .date(LocalDate.now().plusDays(1))
                                    .build(),
                            Task.builder()
                                    .taskName("tra-lya-lya 3")
                                    .taskPriority(TaskPriority.LOW)
                                    .userId(u.getId())
                                    .build()
                    ));

        });
    }

    public void deleteTask(UUID userId, UUID taskId) {
        List<Task> userTasks = userTaskStorage.get(userId);
        List<Task> filteredTasks = userTasks.stream().filter(t -> !Objects.equals(t.getId(), taskId)).collect(Collectors.toList());
        userTaskStorage.put(userId, filteredTasks);
    }

    public void deleteTask(UUID taskId) {
        Set<Map.Entry<UUID, List<Task>>> entries = userTaskStorage.entrySet();
        entries.forEach(e -> {
            if (e.getValue().stream().anyMatch(t -> Objects.equals(t.getId(), taskId))) {
                log.info("Trying delete task {} for user {}", taskId, e.getKey());
                List<Task> filteredTasks = e.getValue().stream().filter(t -> !Objects.equals(t.getId(), taskId)).collect(Collectors.toList());
                userTaskStorage.put(e.getKey(), filteredTasks);
                log.info("Task {} was deleted for user {}", taskId, e.getKey());
            }
        });
    }

    public void addTask(TaskDto taskDto) {
        UUID userId = UUID.fromString(taskDto.getUser().getId());
        Task newTask = Task.builder().userId(userId).taskPriority(taskDto.getTaskPriority()).taskName(taskDto.getTaskName()).date(taskDto.getDate()).build();
        List<Task> tasksByUserId = getTasksByUserId(userId);
        ArrayList<Task> prevTasks = new ArrayList<>(tasksByUserId);
        prevTasks.add(newTask);
        userTaskStorage.put(userId, prevTasks);
        log.info("Task {} added for userId {}", newTask, userId);
    }

    public List<Task> getTasksByUserId(UUID userId) {
        return userTaskStorage.get(userId);
    }

    public void modify(TaskDto taskDto) {
        UUID userId = UUID.fromString(taskDto.getUser().getId());
        UUID taskId = taskDto.getTaskId();
        List<Task> tasksByUserId = getTasksByUserId(userId);
        Task taskToUpdate = tasksByUserId.stream().filter(t -> t.getId().equals(taskId)).findFirst().orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
        taskToUpdate.setTaskPriority(taskDto.getTaskPriority());
        taskToUpdate.setDate(taskDto.getDate());
        taskToUpdate.setTaskName(taskDto.getTaskName());
        log.info("Finished modifying task {}", taskDto);
    }

    public void initNewTaskList(UUID id) {
        userTaskStorage.put(id, new ArrayList<>());
    }
}
