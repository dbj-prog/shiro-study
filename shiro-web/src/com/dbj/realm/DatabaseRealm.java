package com.dbj.realm;

import com.dbj.dao.DAO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

public class DatabaseRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //能进入到这里表示账号已经通过验证了
        String username = (String)principalCollection.getPrimaryPrincipal();
        //通过DAO获取角色和权限
        Set<String> permissions = new DAO().listPermissions(username);
        Set<String> roles = new DAO().listRoles(username);
        //授权对象
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        s.setStringPermissions(permissions);
        s.setRoles(roles);
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取账号密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName= token.getPrincipal().toString();
        String password= new String( token.getPassword());
        //获取数据库中的密码
        String passwordInDB = new DAO().getPassword(userName);

        //如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
        if(null==passwordInDB || !passwordInDB.equals(password))
            throw new AuthenticationException();

        //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName,password,getName());
        return a;
    }
}
