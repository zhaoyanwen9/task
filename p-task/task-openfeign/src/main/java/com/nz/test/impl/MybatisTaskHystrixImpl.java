package com.nz.test.impl;

import com.nz.test.IMybatisTaskHystrix;
import org.springframework.stereotype.Component;

@Component
public class MybatisTaskHystrixImpl implements IMybatisTaskHystrix {
    @Override
    public String getByRp(int page, int size) {
        return "调用第三方api错误-mybatis";
    }
}
