package com.dbj.dao;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DAO {
    public DAO(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/shiro?characterEncoding=utf-8&serverTimezone=UTC","root", "123456");
    }
    public String getPassword(String username){
        String sql = "select password from user where name=?";
        try(Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("password");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> listRoles(String username){
        Set<String> roles = new HashSet<>();
        String sql = "select r.name from user u " +
                "left join user_role ur on u.id=ur.uid " +
                "left join role r on r.id = ur.rid" +
                " where u.name=?";
        try(Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                roles.add(resultSet.getString(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return roles;
    }

    public Set<String> listPermissions(String username){
        Set<String> permissions = new HashSet<>();
        String sql = "select p.name from user u "+
                "left join user_role ur on u.id=ur.uid "+
                "left join role r on r.id=ur.rid "+
                "left join role_permission rp on rp.rid=r.id "+
                "left join permission p on p.id=rp.pid "+
                "where u.name=?";
        try(Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setString(1,username);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                permissions.add(resultSet.getString(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return permissions;
    }

    public static void main(String[] args){
        System.out.println(new DAO().listRoles("zhang3"));
        System.out.println(new DAO().listRoles("li4"));
        System.out.println(new DAO().listPermissions("zhang3"));
        System.out.println(new DAO().listPermissions("li4"));
    }
}