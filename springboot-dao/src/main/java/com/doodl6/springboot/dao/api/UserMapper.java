package com.doodl6.springboot.dao.api;

import com.doodl6.springboot.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User queryByName(String name);

    int deleteById(long userId);

}
