package com.yuanstack.xconfig.client.repository;

import com.yuanstack.xconfig.client.config.ConfigMeta;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * interface to get config from remote.
 *
 * @author Sylvan
 * @date 2024/05/19  17:03
 */
public interface XRepository {
    static XRepository getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        return new XRepositoryImpl(applicationContext, meta);
    }

    Map<String, String> getConfig();

    void addListener(XRepositoryChangeListener listener);
}
