package com.nz.test.impl;

import com.nz.test.IMybatisplusTaskHystrix;
import org.springframework.stereotype.Component;

@Component
public class MybatisplusTaskHystrixImpl implements IMybatisplusTaskHystrix {
    @Override
    public String getByRp(int page, int size) {
        return "调用第三方api错误-mybatisplus";
    }
}
