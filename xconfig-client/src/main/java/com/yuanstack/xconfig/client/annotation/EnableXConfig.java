package com.yuanstack.xconfig.client.annotation;

import com.yuanstack.xconfig.client.config.XConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * xconfig client entrypoint.
 *
 * @author Sylvan
 * @date 2024/05/19  15:26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({XConfigRegistrar.class})
public @interface EnableXConfig {
}
