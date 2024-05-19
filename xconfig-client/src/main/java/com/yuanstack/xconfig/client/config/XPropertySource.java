package com.yuanstack.xconfig.client.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * xproperty source.
 *
 * @author Sylvan
 * @date 2024/05/19  16:09
 */
public class XPropertySource extends EnumerablePropertySource<XConfigService> {
    public XPropertySource(String name, XConfigService source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
