package com.yuanstack.xconfig.client.config;

import java.util.Map;

/**
 * @author Sylvan
 * @date 2024/05/19  16:16
 */
public class XConfigServiceImpl implements XConfigService {
    Map<String, String> config;

    public XConfigServiceImpl(Map<String, String> config) {
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
}
