package com.nz.test;

import com.nz.test.impl.MybatisplusTaskHystrixImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "task-mybatisplus", path = "task", fallback = MybatisplusTaskHystrixImpl.class)
public interface IMybatisplusTaskHystrix {
    @RequestMapping(value = "getByRp")
    String getByRp(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);
}
