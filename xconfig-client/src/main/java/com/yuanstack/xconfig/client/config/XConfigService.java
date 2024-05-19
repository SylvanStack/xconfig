package com.yuanstack.xconfig.client.config;

/**
 * @author Sylvan
 * @date 2024/05/19  16:15
 */
public interface XConfigService {
    String[] getPropertyNames();

    Object getProperty(String name);
}
