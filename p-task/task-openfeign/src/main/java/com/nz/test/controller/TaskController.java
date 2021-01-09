package com.nz.test.controller;

import com.nz.test.ITaskHystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private ITaskHystrix iTaskHystrix;

    @GetMapping(value = "/getByRp")
    public String getByRp(@RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {
        return iTaskHystrix.getByRp(page, size);
    }
}
