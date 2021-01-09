package com.nz.test.impl;

import com.nz.test.ITaskHystrix;
import org.springframework.stereotype.Component;

@Component
public class TaskHystrixImpl implements ITaskHystrix {
    @Override
    public String getByRp(int page, int size) {
        return "调用第三方api错误";
    }
}
