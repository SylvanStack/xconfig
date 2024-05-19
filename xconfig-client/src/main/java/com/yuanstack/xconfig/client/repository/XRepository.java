package com.yuanstack.xconfig.client.repository;

import com.yuanstack.xconfig.client.config.ConfigMeta;

import java.util.Map;

/**
 * interface to get config from remote.
 *
 * @author Sylvan
 * @date 2024/05/19  17:03
 */
public interface XRepository {
    static XRepository getDefault(ConfigMeta meta) {
        return new XRepositoryImpl(meta);
    }

    Map<String, String> getConfig();
}
