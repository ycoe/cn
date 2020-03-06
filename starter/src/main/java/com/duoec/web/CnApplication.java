package com.duoec.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author xuwenzhen
 * @date 2020/3/1
 */
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.duoec")
public class CnApplication {
    public static void main(String[] args) {
        String rootPath = System.getProperty("user.dir");
        System.setProperty("log.output.path", rootPath + "/logs");
        new SpringApplication(CnApplication.class).run(args);
    }
}
