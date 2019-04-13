package com.fxyh.mybatis.mapper;

import com.fxyh.mybatis.domain.Department;
import com.fxyh.mybatis.domain.Person;

public interface PersonMapper {

    /**
     * 根据人的名称查询该人所在部门
     * @param name
     * @return
     */
    Department findDepartmentByPersonName(String name);

    Person findPersonByName(String name);
}
