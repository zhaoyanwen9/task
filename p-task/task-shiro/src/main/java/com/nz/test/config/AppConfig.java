package com.nz.test.config;

import com.nz.test.bean.EventListenerBean;
import com.nz.test.bean.EventPublisherBean;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.chaoqi.springboot_shiro_redis.bean")
public class AppConfig {

    @Bean(initMethod = "initialize")
    @DependsOn("eventListener")
    public EventPublisherBean eventPublisherBean () {
        return new EventPublisherBean();
    }

    @Bean(name = "eventListener", initMethod = "initialize")
    // @Lazy
    public EventListenerBean eventListenerBean () {
        return new EventListenerBean();
    }

    public static void main (String... strings) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
