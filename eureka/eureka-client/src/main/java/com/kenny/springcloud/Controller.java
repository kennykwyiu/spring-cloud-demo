package com.kenny.springcloud;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {

    @Value("${server.port}")
    private String port;

    @GetMapping("/sayHi")
    public String sayHi() {
        return "Hi, i am from port: " + port;
    }

}
