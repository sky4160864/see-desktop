package org.uly.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 服务端启动类
 *
 * @author C.H 2022/02/28 14:04
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServerApplication {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(ServerApplication.class);
    }
}
