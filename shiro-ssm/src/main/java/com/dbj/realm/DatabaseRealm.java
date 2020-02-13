package com.dbj.realm;

import com.dbj.service.PermissionService;
import com.dbj.service.RoleService;
import com.dbj.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class DatabaseRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        //通过service获取角色和权限
        Set<String> permissions = permissionService.listPermissions(username);
        Set<String> roles = roleService.listRoles(username);
        //授权对象
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        //把通过service获取到的角色和权限放进去
        sa.setRoles(roles);
        sa.setStringPermissions(permissions);
        return sa;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取账号和密码
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getPrincipal().toString();
        String password = new String(token.getPassword());
        //获取数据库中的密码
        String passwordInBD = userService.getPassword(username);
        if(null == passwordInBD || !passwordInBD.equals(password)){
            throw new AuthenticationException();
        }
        //认证信息里存放账号密码
        SimpleAuthenticationInfo sa = new SimpleAuthenticationInfo(username, password, getName());
        return sa;
    }
}
