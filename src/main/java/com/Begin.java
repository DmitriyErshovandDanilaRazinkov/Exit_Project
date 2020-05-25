package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.singletonList;


@SpringBootApplication
@EnableSwagger2
public class Begin {

    public static void main(String[] args) {
        SpringApplication.run(Begin.class, args);
    }

}
