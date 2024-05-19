package com.yuanstack.xconfig.client.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author Sylvan
 * @date 2024/05/19  16:16
 */
@Slf4j
public class XConfigServiceImpl implements XConfigService {
    Map<String, String> config;
    ApplicationContext applicationContext;

    public XConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return this.config.get(name);
    }

    @Override
    public void onChange(ChangeEvent event) {
        //Set<String> keys = calcChangedKeys(this.config, event.config());
        //if (keys.isEmpty()) {
        //    log.info("[XCONFIG] calcChangedKeys return empty, ignore update.");
        //    return;
        //}
        this.config = event.config();
        if (!config.isEmpty()) {
            log.info("[XCONFIG] fire an EnvironmentChangeEvent with keys: {}", config.keySet());
            applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
        }
    }
}
