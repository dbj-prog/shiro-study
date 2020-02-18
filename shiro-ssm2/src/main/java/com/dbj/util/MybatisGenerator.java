package com.dbj.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MybatisGenerator {
    public static void main(String[] args) throws Exception{
        String today = "2020-5-16";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse(today);
        Date d = new Date();
        if(d.getTime() > now.getTime() + 1000*60*60*24){
            System.err.println("----未成功运行-----");
            System.err.println("本程序具有破坏作用，应该运行一次，如果必须再运行，需要修改today变量为今天，如" +
                    sdf.format(new Date()));
            return;
        }
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream is = MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration = cp.parseConfiguration(is);
        is.close();
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator generator = new MyBatisGenerator(configuration, callback, warnings);
        generator.generate(null);

        System.out.println("生成代码成功，只能执行一次，以后执行会覆盖掉mapper，pojo,xml等文件上做的修改");
    }
}