package com.yuanstack.xconfig.client.config;

import com.yuanstack.xconfig.client.repository.XRepository;

import java.util.Map;

/**
 * @author Sylvan
 * @date 2024/05/19  16:15
 */
public interface XConfigService {

    static XConfigService getDefault(ConfigMeta meta) {
        XRepository repository = XRepository.getDefault(meta);
        Map<String, String> config = repository.getConfig();
        return new XConfigServiceImpl(config);
    }

    String[] getPropertyNames();

    Object getProperty(String name);
}
