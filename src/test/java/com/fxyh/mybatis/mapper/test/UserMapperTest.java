package com.fxyh.mybatis.mapper.test;

import com.fxyh.mybatis.mapper.UserMapper;
import com.fxyh.mybatis.domain.User;
import com.fxyh.mybatis.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;

public class UserMapperTest {

    @Test
    public void testAddUser() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.getSqlSession();
            // 得到UserMapper接口的代理对象
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            User user = new User();
            user.setName("lisi");
            user.setPassword("admin");
            user.setSalary(12.34F);
            user.setBirthday(new Date());
            userMapper.save(user);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MyBatisUtils.closeSqlSession();
        }
    }
}
