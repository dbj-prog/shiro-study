package com.dbj.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {
    @Test
    public void test(){
        IniRealm realm = new IniRealm("classpath:shiro.ini");
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("dbj", "123");
        subject.login(token);
        boolean b = subject.isPermitted("user:delete");
        System.out.println(b);
        boolean b1 = subject.isPermitted("user:update");
        System.out.println(b1);
    }
}
