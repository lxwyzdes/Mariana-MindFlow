package com.hy.qql.demo.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

// 代码生成器
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/qql?serverTimezone=GMT%2b8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("qql") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\厚朴杯\\SpringBoot\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                             builder.parent("com.hy.qql.demo") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\厚朴杯\\SpringBoot\\src\\main\\resources\\mapper\\"));// 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    简化get set方法
                    builder.entityBuilder().enableLombok();
//                    开启驼峰字符
                    builder.controllerBuilder().enableHyphenStyle();
//                    开启生成@RestController
                    builder.controllerBuilder().enableRestStyle();
                    builder.addInclude("sys_yuan") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
