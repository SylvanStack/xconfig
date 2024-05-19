package com.yuanstack.xconfig.server.controller;

import com.yuanstack.xconfig.server.dal.ConfigsMapper;
import com.yuanstack.xconfig.server.model.Configs;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * config server endpoint.
 *
 * @author Sylvan
 * @date 2024/05/19  14:35
 */
@RestController
public class XConfigController {
    private final ConfigsMapper mapper;

    Map<String, Long> VERSIONS = new HashMap<>();
    public XConfigController(ConfigsMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return mapper.list(app, env, ns);
    }

    @RequestMapping("/update")
    public List<Configs> update(@RequestParam("app") String app,
                                @RequestParam("env") String env,
                                @RequestParam("ns") String ns,
                                @RequestBody Map<String, String> params) {
        params.forEach((k, v) -> insertOrUpdate(new Configs(app, env, ns, k, v)));
        VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());
        return mapper.list(app, env, ns);
    }

    private void insertOrUpdate(Configs configs) {
        Configs conf = mapper.select(configs.getApp(), configs.getEnv(),
                configs.getNs(), configs.getPkey());
        if (conf == null) {
            mapper.insert(configs);
        } else {
            mapper.update(configs);
        }
    }

    @GetMapping("/version")
    public long version(String app, String env, String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }
}
