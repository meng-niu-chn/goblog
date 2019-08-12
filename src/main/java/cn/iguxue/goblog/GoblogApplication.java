package cn.iguxue.goblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.iguxue.goblog.mapper")
public class GoblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoblogApplication.class, args);
    }

}
