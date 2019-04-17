package com.fxyh.mybatis.mapper;

import com.fxyh.mybatis.domain.User;
import com.fxyh.mybatis.interceptor.PageParam;

import java.util.HashMap;
import java.util.List;

public interface UserMapper {

    void save(User user);

    void delete(User user);

    void update(User user);

    User findById(Integer id);

    List<User> findAll();

    List<User> findAllByPage(HashMap<String, Object> paramMap);
}
