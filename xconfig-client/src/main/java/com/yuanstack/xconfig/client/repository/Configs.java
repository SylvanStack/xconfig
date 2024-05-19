package com.yuanstack.xconfig.client.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sylvan
 * @date 2024/05/19  17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configs {
    private String app;
    private String env;
    private String ns;
    private String pkey;
    private String pval;
}
