package com.gongnaixiao.threadpool.spring.controller;

import com.gongnaixiao.threadpool.spring.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/task")

    public void run() {
        taskService.asyncExecute();
    }
}
