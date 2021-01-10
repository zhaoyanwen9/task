package com.nz.test.impl;

import com.nz.test.IJpaTaskHystrix;
import org.springframework.stereotype.Component;

@Component
public class JpaTaskHystrixImpl implements IJpaTaskHystrix {
    @Override
    public String getByRp(int page, int size) {
        return "调用第三方api错误-jpa";
    }
}
