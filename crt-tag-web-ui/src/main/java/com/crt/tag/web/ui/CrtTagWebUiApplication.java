package com.crt.tag.web.ui;


import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
@Log4j2
public class CrtTagWebUiApplication implements HealthIndicator {


    public static void main(String[] args) {
        ApplicationStartup app =  new CrtTagWebUiApplication().new ApplicationStartup();
        long start = System.currentTimeMillis();
        SpringApplication springApplication = new SpringApplication(CrtTagWebUiApplication.class);
        springApplication.addListeners(app);
        springApplication.run(args);
        long cost = System.currentTimeMillis() - start;
        log.info("crt-tag-web-ui started status: {}, cost: {}", "SUCCESS!", cost);
    }

    @Override
    public Health health() {
        return Health.up().withDetail("标签管理平台", "启动成功!").build();
    }


     class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {



        @Override
        public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

            log.info("工程初始化完成!");
        }
    }
}
