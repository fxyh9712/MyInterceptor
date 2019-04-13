package com.fxyh.mybatis.mapper;

import com.fxyh.mybatis.domain.User;
import com.fxyh.mybatis.interceptor.PageParam;
import com.fxyh.mybatis.interceptor.UseMyPage;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserMapper {

    void save(User user);

    void delete(User user);

    void update(User user);

    User findById(Integer id);

    List<User> findAll();

//    @UseMyPage
    List<User> findAllByPage(PageParam pageParam, HashMap<String, Object> paramMap);
}
