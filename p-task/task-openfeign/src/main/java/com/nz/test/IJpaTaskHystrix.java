package com.nz.test;

import com.nz.test.impl.JpaTaskHystrixImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "task-jpa", path = "task", fallback = JpaTaskHystrixImpl.class)
public interface IJpaTaskHystrix {

    @RequestMapping(value = "getByRp")
    String getByRp(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);
}
