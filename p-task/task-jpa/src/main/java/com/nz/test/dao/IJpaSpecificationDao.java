package com.nz.test.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IJpaSpecificationDao<T> extends JpaSpecificationExecutor<T> {
}
