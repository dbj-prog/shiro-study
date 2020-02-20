package com.dbj.shiro.dao.impl;

import com.dbj.shiro.dao.UserDAO;
import com.dbj.shiro.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate template;

    @Override
    public String getPasswordByUsername(String username) {
        String sql = "select username, password from users where username=?";
        List<User> list = template.query(sql, new String[]{username}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        return CollectionUtils.isEmpty(list) ? null : list.get(0).getPassword();
    }

    @Override
    public List<String> getPermissionByUsername(String username) {
        String sql = "select permission from roles_permissions where role_name=?";
        return template.query(sql, new String[]{username}, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });
    }

    @Override
    public List<String> getRolesByUsername(String username) {
        String sql = "select role_name from user_roles where username=?";
        return template.query(sql, new String[]{username}, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
    }
}
