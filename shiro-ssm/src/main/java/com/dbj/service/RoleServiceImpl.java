package com.dbj.service;

import com.dbj.mapper.RoleMapper;
import com.dbj.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Set<String> listRoles(String userName) {
        List<Role> list = roleMapper.listRolesByUserName(userName);
        Set<String> roles = new HashSet<>();
        for(Role role : list){
            roles.add(role.getName());
        }
        return roles;
    }
}
