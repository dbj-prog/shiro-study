package com.dbj.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class AuthorizingTest {
    @Test
    public void test(){
        SimpleAccountRealm accountRealm = new SimpleAccountRealm();
        //添加一个账户
        accountRealm.addAccount("dbj","123","admin","user");
        //安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //将realm注入到安全管理器中
        securityManager.setRealm(accountRealm);
        //将安全管理器注入到安全工具类中
        SecurityUtils.setSecurityManager(securityManager);
        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        //创建token
        UsernamePasswordToken token = new UsernamePasswordToken("dbj", "123");
        //进行登录操作
        subject.login(token);
        //判断当前用户是否具有如下角色
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.hasRole("admin1"));
        subject.checkRoles("admin","user");
    }

}
