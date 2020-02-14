package com.dbj.realm;

import com.dbj.pojo.User;
import com.dbj.service.PermissionService;
import com.dbj.service.RoleService;
import com.dbj.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
        String user = (String)principalCollection.getPrimaryPrincipal();
        Set<String> permissions= permissionService.listPermissions(user);
        Set<String> roles = roleService.listRoleNames(user);
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        sa.setStringPermissions(permissions);
        sa.setRoles(roles);
        return sa;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getPrincipal().toString();
        User user = userService.getByName(username);
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo sa = new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
        return sa;
    }
}
