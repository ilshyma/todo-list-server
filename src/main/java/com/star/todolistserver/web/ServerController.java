package com.star.todolistserver.web;

import com.star.todolistserver.pojo.*;
import com.star.todolistserver.service.TaskProcessor;
import com.star.todolistserver.service.TaskService;
import com.star.todolistserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class ServerController {


    @Autowired
    private TaskProcessor taskProcessor;

    @Autowired
    private UserService userService;

    @GetMapping("vocabulary")
    @ResponseBody
    public Vocabulary getStaticParams() {
        return new Vocabulary();
    }

    @GetMapping("users")
    @ResponseBody
    public List<User> getAllUserList() {
        return userService.getAllUser();
    }

    @PostMapping("users")
    @ResponseBody
    public User createUser(@RequestBody UserDto dto) {
        return taskProcessor.createUser(dto);
    }

    @GetMapping("tasks")
    @ResponseBody
    public List<TaskDto> getAllUsersTasks(@RequestParam("userIds") List<UUID> userIds) {
        return taskProcessor.getTasksByUserIds(userIds);
    }

    @DeleteMapping("tasks")
    @ResponseBody
    public void deleteTask(@RequestParam("taskId") UUID taskId) {
        taskProcessor.deleteTask(taskId);
    }

    @PostMapping("tasks")
    @ResponseBody
    public void addTask(@RequestBody TaskDto taskDto) {
        taskProcessor.createTask(taskDto);
    }

    @PutMapping("tasks")
    @ResponseBody
    public void modifyTask(@RequestBody TaskDto taskDto) {
        taskProcessor.modify(taskDto);
    }


}
