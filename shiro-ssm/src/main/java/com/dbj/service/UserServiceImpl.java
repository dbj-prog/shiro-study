package com.dbj.service;

import com.dbj.mapper.UserMapper;
import com.dbj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public String getPassword(String name) {
        User u  = userMapper.getByName(name);
        if(null==u)
            return null;
        return u.getPassword();
    }

}
