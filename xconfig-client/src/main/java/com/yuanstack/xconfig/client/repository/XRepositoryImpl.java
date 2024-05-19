package com.yuanstack.xconfig.client.repository;

import com.alibaba.fastjson.TypeReference;
import com.yuanstack.xconfig.client.config.ConfigMeta;
import com.yuanstack.xconfig.client.util.HttpUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Sylvan
 * @date 2024/05/19  17:03
 */
@Data
public class XRepositoryImpl implements XRepository {

    ConfigMeta meta;

    // 心跳检测 版本差异
    Map<String, Long> versionMap = new HashMap<>();
    Map<String, Map<String, String>> configMap = new HashMap<>();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    // 事件通知变更数据
    ApplicationContext applicationContext;
    List<XRepositoryChangeListener> listeners = new ArrayList<>();


    public XRepositoryImpl(ApplicationContext applicationContext, ConfigMeta meta) {
        this.meta = meta;
        this.applicationContext = applicationContext;
        executor.scheduleWithFixedDelay(this::heartbeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return findAll();
    }

    @Override
    public void addListener(XRepositoryChangeListener listener) {
        listeners.add(listener);
    }

    private void heartbeat() {
        String versionPath = meta.versionPath();
        String key = meta.genKey();
        Long version = HttpUtils.httpGet(versionPath, new TypeReference<Long>() {
        });
        Long oldVersion = versionMap.getOrDefault(key, -1L);
        if (version > oldVersion) { // 发生了变化了
            System.out.println("[XCONFIG] current=" + version + ", old=" + oldVersion);
            System.out.println("[XCONFIG] need update new configs.");
            versionMap.put(key, version);
            Map<String, String> newConfigs = findAll();
            configMap.put(key, newConfigs);
            System.out.println("[XCONFIG] fire an EnvironmentChangeEvent with keys：" + newConfigs.keySet());
            listeners.forEach(l -> l.onChange(new XRepositoryChangeListener.ChangeEvent(meta, newConfigs)));
        }
    }

    private @NotNull Map<String, String> findAll() {
        String listPath = meta.listPath();
        System.out.println("[XCONFIG] list all configs from kk config server.");
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }
}
