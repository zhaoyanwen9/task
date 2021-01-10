package com.nz.test.controller;

import com.nz.test.IMybatisTaskHystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mybatis")
public class MybatisTaskController {

    private static final Logger logger = LoggerFactory.getLogger(MybatisTaskController.class);

    @Autowired
    private IMybatisTaskHystrix iMybatisTaskHystrix;

    @GetMapping(value = "/task/getByRp")
    public String getByRp(@RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {
        logger.info("#### /mybatis/task/getByRp");
        return iMybatisTaskHystrix.getByRp(page, size);
    }
}
