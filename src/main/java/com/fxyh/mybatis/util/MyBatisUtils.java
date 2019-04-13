package com.fxyh.mybatis.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<>();

    static {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = MyBatisUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    }

    public static SqlSession getSqlSession() {
        SqlSession sqlSession = sqlSessionThreadLocal.get();
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
        }
        return sqlSession;
    }

    public static void closeSqlSession() {
        SqlSession sqlSession = sqlSessionThreadLocal.get();
        if (sqlSession != null) {
            sqlSession.close();
            sqlSessionThreadLocal.remove();
        }
    }
}
