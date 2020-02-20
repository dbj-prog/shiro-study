package com.dbj.shiro.controller;

import com.dbj.shiro.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping(value = "/subLogin", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try{
            subject.login(token);
        }catch(AuthenticationException e){
            return e.getMessage();
        }
        return subject.hasRole("admin") ? "有admin角色":"没有admin权限";
    }

//    执行该方法需要具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/role")
    @ResponseBody
    public String testRole(){
        return "role";
    }

    //    执行该方法需要具有admin1角色
    @RequiresRoles("admin1")
    @RequestMapping("/role1")
    @ResponseBody
    public String testRole1(){
        return "role1";
    }

    @RequestMapping("/role2")
    @ResponseBody
    public String testRole2(){
        return "role2";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping("/perms")
    @ResponseBody
    public String testPerms(){
        return "perms";
    }

    @RequestMapping("/perms1")
    @ResponseBody
    public String testPerms1(){
        return "perms1";
    }
}