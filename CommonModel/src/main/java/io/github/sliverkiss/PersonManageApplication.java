package io.github.sliverkiss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 美术馆人事管理应用启动程序
 *
 * @author sliverkiss
 * @date 2023/06/25
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// @SpringBootApplication
public class PersonManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonManageApplication.class, args);
    }
}


