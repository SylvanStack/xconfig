package com.yuanstack.xconfig.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Test demo config.
 *
 * @author Sylvan
 * @date 2024/05/19  16:05
 */
@Data
@ConfigurationProperties(prefix = "x")
public class XDemoConfig {
    String a;
    String b;
    String c;
}
