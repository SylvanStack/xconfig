package com.yuanstack.xconfig.client.value;

import com.yuanstack.xconfig.client.util.FieldUtils;
import com.yuanstack.xconfig.client.util.PlaceholderHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * process spring value.
 * 1. 扫描所有的spring Value，保存起来
 * 2. 在配置变更时，更新所有的spring value
 *
 * @author Sylvan
 * @date 2024/05/19  22:06
 */
@Slf4j
@Data
public class SpringValueProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationListener<EnvironmentChangeEvent> {
    private static final PlaceholderHelper helper = PlaceholderHelper.getInstance();
    private BeanFactory beanFactory;

    static final MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        FieldUtils.findAnnotatedField(bean.getClass(), Value.class).forEach(
                field -> {
                    log.info("[XCONFIG] >> find spring value: {}", field);
                    Value value = field.getAnnotation(Value.class);
                    helper.extractPlaceholderKeys(value.value()).forEach(key -> {
                        log.info("[XCONFIG] >> find spring value: {}", key);
                        SpringValue springValue = new SpringValue(bean, beanName, key, value.value(), field);
                        VALUE_HOLDER.add(key, springValue);
                    });
                });
        return bean;
    }

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent changeEvent) {
        log.info("[XCONFIG] >> update spring value for keys: {}", changeEvent.getKeys());
        changeEvent.getKeys().forEach(key -> {
            log.info("[XCONFIG] >> update spring value: {}", key);
            List<SpringValue> springValues = VALUE_HOLDER.get(key);
            if (springValues == null || springValues.isEmpty()) {
                return;
            }

            springValues.forEach(springValue -> {
                        log.info("[XCONFIG] >> update spring value: {} for key {}", springValue, key);
                        try {
                            Object value = helper.resolvePropertyValue((ConfigurableBeanFactory) beanFactory,
                                    springValue.getBeanName(), springValue.getPlaceholder());
                            log.info("[XCONFIG] >> update value: {} for holder {}", value, springValue.getPlaceholder());
                            springValue.getField().setAccessible(true);
                            springValue.getField().set(springValue.getBean(), value);
                        } catch (Exception ex) {
                            log.error("[XCONFIG] >> update spring value error", ex);
                        }
                    }
            );
        });
    }
}
