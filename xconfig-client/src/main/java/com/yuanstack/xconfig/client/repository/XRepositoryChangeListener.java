package com.yuanstack.xconfig.client.repository;

import com.yuanstack.xconfig.client.config.ConfigMeta;

import java.util.Map;

/**
 * @author Sylvan
 * @date 2024/05/19  18:21
 */
public interface XRepositoryChangeListener {
    void onChange(ChangeEvent changeEvent);

    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {}
}
