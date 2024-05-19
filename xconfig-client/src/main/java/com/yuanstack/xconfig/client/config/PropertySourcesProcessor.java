package com.yuanstack.xconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * property sources processor.
 *
 * @author Sylvan
 * @date 2024/05/19  16:22
 */
@Data
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, EnvironmentAware, PriorityOrdered {

    private final static String X_PROPERTY_SOURCES = "XPropertySources";
    private final static String X_PROPERTY_SOURCE = "XPropertySource";

    Environment environment;
    ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment ENV = (ConfigurableEnvironment) environment;
        if (ENV.getPropertySources().contains(X_PROPERTY_SOURCES)) {
            return;
        }

        String app = ENV.getProperty("xconfig.app", "app1");
        String env = ENV.getProperty("xconfig.env", "dev");
        String ns = ENV.getProperty("xconfig.ns", "public");
        String configServer = ENV.getProperty("xconfig.configServer", "http://localhost:9129");
        ConfigMeta configMeta = new ConfigMeta(app, env, ns, configServer);
        XConfigService configService = XConfigService.getDefault(applicationContext, configMeta);

        XPropertySource propertySource = new XPropertySource(X_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(X_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        ENV.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
