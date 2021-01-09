package com.nz.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/task")
public class TaskController1 {

    @GetMapping(value = "/getByRp")
    public String getByRp(@RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {
        return UUID.randomUUID().toString();
    }
}
