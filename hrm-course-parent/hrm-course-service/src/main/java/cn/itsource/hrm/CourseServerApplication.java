package cn.itsource.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.itsource.hrm.mapper")
@EnableTransactionManagement
@EnableSwagger2
public class CourseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseServerApplication.class,args);
    }
}