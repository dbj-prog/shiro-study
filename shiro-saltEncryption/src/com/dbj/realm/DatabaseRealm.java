package com.dbj.realm;

import com.dbj.bean.User;
import com.dbj.dao.DAO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

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
        System.out.println(this.getCredentialsMatcher());
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getPrincipal().toString();
        User user = new DAO().getUser(username);
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        //认证信息里存放账号密码，getName()是当前Realm的继承方法，通常返回当前的类名databaseRealm
        //盐也放进去
        //这样通过shiro.ini里配置的HashedCredentialsMatcher进行自动校验
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
        return a;
//        //获取账号密码
//        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
//        String username = token.getPrincipal().toString();
//        String password = new String(token.getPassword());
//        //获取数据库中的密码
//        String passwordInDB = new DAO().getPassword(username);
//        //获取数据库中的盐值
//        String salt = new DAO().getUser(username).getSalt();
//        String encodedPassword = new SimpleHash("md5",password, salt, 2).toString();
//
//        if(null == passwordInDB || !passwordInDB.equals(encodedPassword)){
//            throw new AuthenticationException();
//        }
//        //认证信息里存放账号密码，getName()是当前Realm的继承放啊，通常返回当前类名：databaseRealm
//        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(username, password, getName());
//        return a;
    }
}
