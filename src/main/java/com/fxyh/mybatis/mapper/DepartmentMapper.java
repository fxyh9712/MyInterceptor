package com.fxyh.mybatis.mapper;

import com.fxyh.mybatis.domain.Department;
import com.fxyh.mybatis.domain.Person;

import java.util.List;

public interface DepartmentMapper {

    Department findById(Integer id);

    /**
     * 根据部门名称查询该部门下的所有的员工
     * @param name
     * @return
     */
    List<Person> findPersonsByDepartmentName(String name);

    Department findByName(String name);
}
