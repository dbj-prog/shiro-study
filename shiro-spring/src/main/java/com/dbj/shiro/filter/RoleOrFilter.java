package com.dbj.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 这个filter是只要具有角色集合中的一个角色就可以访问某资源，而不像roles对应的filter需要具有全部的角色才可以访问
 */
public class RoleOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        //获取到subject
        Subject subject = getSubject(servletRequest, servletResponse);
        //将参数强转为字符串数组
        String[] roles = (String[])o;
        //如果字符串为空或者长度为0表明不需要任何角色都可访问，即可匿名访问，则直接返回true
        if(roles == null || roles.length == 0){
            return true;
        }
        //遍历角色集合，只要当前用户具有一个角色即可
        for(String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
