package com.fxyh.mybatis.mapper;

import com.fxyh.mybatis.domain.User;

import java.util.List;

public interface UserMapper {

    void save(User user);

    void delete(User user);

    void update(User user);

    User findById(Integer id);

    List<User> findAll();
}
