package com.dbj.mapper;

import com.dbj.pojo.Role;

import java.util.List;

public interface RoleMapper {
    List<Role> listRolesByUserName(String userName);
}
