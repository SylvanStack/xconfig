package com.yuanstack.xconfig.client.config;

import com.yuanstack.xconfig.client.repository.XRepository;
import com.yuanstack.xconfig.client.repository.XRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author Sylvan
 * @date 2024/05/19  16:15
 */
public interface XConfigService extends XRepositoryChangeListener {

    static XConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        XRepository repository = XRepository.getDefault(applicationContext, meta);
        Map<String, String> config = repository.getConfig();
        XConfigServiceImpl xConfigService = new XConfigServiceImpl(applicationContext, config);
        repository.addListener(xConfigService);
        return xConfigService;
    }

    String[] getPropertyNames();

    Object getProperty(String name);
}
