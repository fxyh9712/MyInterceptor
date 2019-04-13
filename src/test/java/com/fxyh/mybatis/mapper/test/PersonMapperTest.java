package com.fxyh.mybatis.mapper.test;

import com.fxyh.mybatis.domain.Department;
import com.fxyh.mybatis.domain.Person;
import com.fxyh.mybatis.mapper.PersonMapper;
import com.fxyh.mybatis.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class PersonMapperTest {

    @Test
    public void testFindDepartmentByPersonName() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getSqlSession();
            // 得到UserMapper接口的代理对象
            PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

            Department department = personMapper.findDepartmentByPersonName("zhangsan");
            System.out.println(department);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession();
        }
    }

    @Test
    public void testFindPersonByName() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getSqlSession();
            // 得到UserMapper接口的代理对象
            PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

            Person person = personMapper.findPersonByName("zhangsan");
//            System.out.println(person.getDepartment());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession();
        }
    }
}
