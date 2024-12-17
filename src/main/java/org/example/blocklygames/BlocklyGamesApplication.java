package org.example.blocklygames;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.blocklygames.mapper")
public class BlocklyGamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlocklyGamesApplication.class, args);
    }

}
