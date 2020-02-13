package com.dbj.mapper;

import com.dbj.pojo.Permission;

import java.util.List;

public interface PermissionMapper {
    List<Permission> listPermissionsByUserName(String userName);
}
