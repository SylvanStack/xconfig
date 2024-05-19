package com.yuanstack.xconfig.client.repository;

import com.alibaba.fastjson.TypeReference;
import com.yuanstack.xconfig.client.config.ConfigMeta;
import com.yuanstack.xconfig.client.util.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sylvan
 * @date 2024/05/19  17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XRepositoryImpl implements XRepository {

    ConfigMeta meta;

    @Override
    public Map<String, String> getConfig() {
        String listPath = meta.getConfigServer() + "/list" + "?app=" + meta.getApp()
                + "&env=" + meta.getEnv() + "&ns=" + meta.getNs();
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }
}
