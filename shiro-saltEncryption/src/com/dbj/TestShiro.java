package com.dbj;

import com.dbj.bean.User;
import com.dbj.dao.DAO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.ArrayList;
import java.util.List;

public class TestShiro {
    public static void main(String[] args) {
        new DAO().createUser("tom", "123");
        User user = new User();
        user.setName("tom");
        user.setPassword("123");

        if(login(user)){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }
    }

    private static boolean hasRole(User user, String role){
        Subject subject= getSubject(user);
        return subject.hasRole(role);
    }

    private static boolean isPermitted(User user, String permit){
        Subject subject = getSubject(user);
        return subject.isPermitted(permit);
    }

    private static Subject getSubject(User user){
        //加载配置文件，并获取工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //获取安全管理者实例
        SecurityManager sm = factory.getInstance();
        //将安全管理者实例放入到全局对象中
        SecurityUtils.setSecurityManager(sm);
        //全局对象通过安全管理者生成Subject对象
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    private static boolean login(User user){
        Subject subject = getSubject(user);
        if(subject.isAuthenticated()){
            subject.logout();
        }
        //封装用户的数据
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(user.getName(), user.getPassword());
        try{
            //将用户的数据token最终传递到Realm中进行比对
            subject.login(usernamePasswordToken);
        }catch (AuthenticationException e){
            //验证出现错误
            return false;
        }
        return subject.isAuthenticated();
    }
}
