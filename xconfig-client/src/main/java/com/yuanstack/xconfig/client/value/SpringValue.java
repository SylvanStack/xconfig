package com.yuanstack.xconfig.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * spring value.
 *
 * @author Sylvan
 * @date 2024/05/19  22:05
 */
@Data
@AllArgsConstructor
public class SpringValue {
    private Object bean;
    private String beanName;
    private String key;
    private String placeholder;
    private Field field;
}
