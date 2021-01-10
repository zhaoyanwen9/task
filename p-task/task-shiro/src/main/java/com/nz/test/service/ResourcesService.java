package com.nz.test.service;

import com.nz.test.dao.domain.SysResources;

import java.util.List;

public interface ResourcesService {
    List<SysResources> selectAll();
}
