package com.kenny.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class Controller {

    @Autowired
    private LoadBalancerClient client;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance serviceInstance = client.choose("eureka-client");
        if (serviceInstance == null) {
            return "No service instance found";
        }

        String target = String.format ("http://%s:%s/sayHi",
                serviceInstance.getHost(),
                serviceInstance.getPort());
        log.info ("url is {}", target);

        return restTemplate.getForObject( target, String.class);
    }

    @PostMapping("/hello")
    public Friend helloPost () {
        ServiceInstance serviceInstance = client.choose("eureka-client");
        if (serviceInstance == null) {
            return null ;
        }

        String target = String.format ("http://%s:%s/sayHi",
                serviceInstance.getHost(),
                serviceInstance.getPort());
        log.info ("url is {}", target);

        Friend friend = new Friend();
        friend.setName("Eureka Consumer");

        return restTemplate.postForObject( target, friend, Friend.class);
    }
}
