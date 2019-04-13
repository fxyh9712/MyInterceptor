package com.fxyh.mybatis.mapper.test;

import com.fxyh.mybatis.domain.Department;
import com.fxyh.mybatis.domain.Person;
import com.fxyh.mybatis.mapper.DepartmentMapper;
import com.fxyh.mybatis.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class DepartmentMapperTest {

    @Test
    public void testFindPersonsByDepartmentName() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getSqlSession();
            // 得到UserMapper接口的代理对象
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);

            List<Person> personList = departmentMapper.findPersonsByDepartmentName("IBM");
            personList.stream().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession();
        }
    }

    @Test
    public void testFindByName() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getSqlSession();
            // 得到UserMapper接口的代理对象
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);

            Department department = departmentMapper.findByName("IBM");
            department.getPersons().stream().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession();
        }
    }
}
