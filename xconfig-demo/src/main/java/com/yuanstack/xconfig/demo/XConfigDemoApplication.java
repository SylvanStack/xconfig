package com.yuanstack.xconfig.demo;

import com.yuanstack.xconfig.client.annotation.EnableXConfig;
import com.yuanstack.xconfig.demo.config.XDemoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author Sylvan
 * @date 2024/05/19  16:03
 */
@SpringBootApplication
@EnableConfigurationProperties({XDemoConfig.class})
@EnableXConfig
public class XConfigDemoApplication {

    @Value("${x.a}.${x.b}")
    private String a;

    final Environment environment;

    @Autowired
    private XDemoConfig xDemoConfig;

    public XConfigDemoApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(XConfigDemoApplication.class, args);
        System.out.println(" &&&& ====> " + applicationContext.getBean(ConfigurationPropertiesRebinder.class));
    }

    @Bean
    ApplicationRunner applicationRunner() {
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        return args -> {
            System.out.println(a);
            System.out.println(xDemoConfig.getA());
        };
    }
}
