package com.nz.test.controller;

import com.nz.test.IJpaTaskHystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jpa")
public class JpaTaskController {

    private static final Logger logger = LoggerFactory.getLogger(JpaTaskController.class);

    @Autowired
    private IJpaTaskHystrix iJpaTaskHystrix;

    @GetMapping(value = "/task/getByRp")
    public String getByRp(@RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {
        logger.info("#### /jpa/task/getByRp");
        return iJpaTaskHystrix.getByRp(page, size);
    }
}
