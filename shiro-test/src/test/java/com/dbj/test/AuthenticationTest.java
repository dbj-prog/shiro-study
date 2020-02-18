package com.dbj.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class AuthenticationTest {
    @Test
    public void test(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("dbj", "123");
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("dbj", "123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
    }
}
