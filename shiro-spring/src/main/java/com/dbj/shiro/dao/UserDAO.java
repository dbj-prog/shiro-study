package com.dbj.shiro.dao;

import java.util.List;

public interface UserDAO {
    String getPasswordByUsername(String username);

    List<String> getPermissionByUsername(String username);

    List<String> getRolesByUsername(String username);
}
