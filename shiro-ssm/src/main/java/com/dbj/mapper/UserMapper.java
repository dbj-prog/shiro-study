package com.dbj.mapper;

import com.dbj.pojo.User;

public interface UserMapper {
    User getByName(String name);
}
