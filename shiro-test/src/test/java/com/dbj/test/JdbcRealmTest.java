package com.dbj.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.beans.PropertyVetoException;

public class JdbcRealmTest {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    {
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/shiro-test?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test(){
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        //必须添加这句话，否则会出现org.apache.shiro.authz.UnauthorizedException: Subject does not have permission [user:delete]
        realm.setPermissionsLookupEnabled(true);
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("dbj", "123");
        subject.login(token);
        System.out.println("->" + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
    }
}
