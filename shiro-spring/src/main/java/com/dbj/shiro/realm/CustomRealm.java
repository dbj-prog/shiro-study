package com.dbj.shiro.realm;

import com.dbj.shiro.dao.UserDAO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserDAO userDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取传过来的用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名获取角色和权限
        Set<String> roles = new HashSet<>(userDAO.getRolesByUsername(username));
        Set<String> permissions = new HashSet<>(userDAO.getPermissionByUsername(username));
        //创建授权信息对象，并将该对象对应的角色和权限放入到该对象中；
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        sa.setStringPermissions(permissions);
        sa.setRoles(roles);
        return sa;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //获取token中的用户名和密码
        String username = (String) authenticationToken.getPrincipal();
        String password = userDAO.getPasswordByUsername(username);
        if(password == null){
            return null;
        }
        //创建一个认证信息对象，将用户名和查到的密码返回
        SimpleAuthenticationInfo sa = new SimpleAuthenticationInfo(username, password, getName());
        //设置加密使用的盐值
        sa.setCredentialsSalt(ByteSource.Util.bytes("dbj"));
        return sa;
    }

}
