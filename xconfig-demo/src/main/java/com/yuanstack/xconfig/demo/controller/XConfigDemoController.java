package com.yuanstack.xconfig.demo.controller;

import com.yuanstack.xconfig.demo.config.XDemoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sylvan
 * @date 2024/05/19  16:07
 */

@RestController
public class XConfigDemoController {

    @Value("${x.a}")
    private String a;

    @Value("${x.b}")
    private String b;
    @Autowired
    private XDemoConfig xDemoConfig;

    @GetMapping("/demo")
    public String demo() {
        return "x.a = " + a + "\n"
                + "x.b = " + b + "\n"
                + "demo.a = " + xDemoConfig.getA() + "\n"
                + "demo.b = " + xDemoConfig.getB() + "\n";
    }
}
