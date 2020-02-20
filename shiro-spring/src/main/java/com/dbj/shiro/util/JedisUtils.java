package com.dbj.shiro.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JedisUtils {
    @Resource
    private JedisPool jedisPool;
}
