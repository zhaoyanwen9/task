package com.nz.test;

import com.nz.test.impl.TaskHystrixImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "task-mybatis", path = "task", fallback = TaskHystrixImpl.class)
public interface ITaskHystrix {

    @RequestMapping(value = "getByRp")
    String getByRp(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);
}
