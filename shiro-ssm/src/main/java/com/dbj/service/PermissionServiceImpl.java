package com.dbj.service;

import com.dbj.mapper.PermissionMapper;
import com.dbj.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Set<String> listPermissions(String userName) {
        Set<String> permissions = new HashSet<>();
        List<Permission> list = permissionMapper.listPermissionsByUserName(userName);
        for(Permission permission : list){
            permissions.add(permission.getName());
        }
        return permissions;
    }
}
