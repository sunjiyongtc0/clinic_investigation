package com.clin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "com.clin.filter")
@SpringBootApplication(scanBasePackages = "com.clin")
public class WebApplication {


    public static void main(String[] args) {

        SpringApplication.run(WebApplication.class);


    }

}




