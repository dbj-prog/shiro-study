package com.dbj.test;

import com.dbj.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {
    @Test
    public void test1(){
        //自定义realm
        CustomRealm customRealm = new CustomRealm();
        //创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置realm
        securityManager.setRealm(customRealm);
        //将安全管理器注入到安全工具中
        SecurityUtils.setSecurityManager(securityManager);
        //通过安全工具获取当前subject
        Subject subject = SecurityUtils.getSubject();
        //创建token,其中的username和password可以从前端页面表单获取
        UsernamePasswordToken token = new UsernamePasswordToken("dbj", "123");
        //用token进行登录验证
        subject.login(token);
        System.out.println("->" + subject.isAuthenticated());
        //判断当前用户是否具有某种角色或具有如下的权限
        subject.checkRoles("admin", "user");
        subject.checkPermissions("user:update","user:select");
    }
}
