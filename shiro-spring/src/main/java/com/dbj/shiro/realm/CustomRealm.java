package com.dbj.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    //用Map模拟数据库来存储数据
    Map<String, String> map = new HashMap<>();
    {
        map.put("dbj", "a889385ec627ef8d61e01a81ebc2daa0");
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取传过来的用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名获取角色和权限
        Set<String> roles = getRolesByUsername(username);
        Set<String> permissions = getPermissionByUsername(username);
        //创建授权信息对象，并将该对象对应的角色和权限放入到该对象中；
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        sa.setStringPermissions(permissions);
        sa.setRoles(roles);
        return sa;
    }

    //根据用户名查询权限
    private Set<String> getPermissionByUsername(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:update");
        permissions.add("user:select");
        return permissions;
    }
    //根据用户名查询角色
    private Set<String> getRolesByUsername(String username) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //获取token中的用户名和密码
        String username = (String) authenticationToken.getPrincipal();
        String password = getPasswordByUsername(username);
        if(password == null){
            return null;
        }
        //创建一个认证信息对象，将用户名和查到的密码返回
        SimpleAuthenticationInfo sa = new SimpleAuthenticationInfo(username, password, getName());
        //设置加密使用的盐值
        sa.setCredentialsSalt(ByteSource.Util.bytes("dbj"));
        return sa;
    }

    private String getPasswordByUsername(String username) {
        return map.get(username);
    }

}
