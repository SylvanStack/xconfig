package com.yuanstack.xconfig.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * config meta.
 *
 * @author Sylvan
 * @date 2024/05/19  17:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigMeta {
    String app;
    String env;
    String ns;
    String configServer;


    public String genKey() {
        return this.getApp() + "_" + this.getEnv() + "_" + this.getNs();
    }

    public String listPath() {
        return path("list");
    }

    public String versionPath() {
        return path("version");
    }

    private String path(String context) {
        return this.getConfigServer() + "/" + context + "?app=" + this.getApp()
                + "&env=" + this.getEnv() + "&ns=" + this.getNs();
    }
}
