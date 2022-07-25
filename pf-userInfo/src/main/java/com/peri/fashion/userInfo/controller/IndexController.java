package com.peri.fashion.userInfo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页欢迎 Controller
 *
 * @author Elliot
 */
@RestController
public class IndexController {

    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 获取Id
     */
    @Value("${spring.application.name}")
    private String applicationId;

    /**
     * Index信息
     *
     * @return Index信息
     */
    @GetMapping("/")
    public Mono<ResponseEntity<List<String>>> index() {
        List<String> result = new ArrayList<>();
        result.add("Welcome To The " + applicationId + " ~");
        result.add("Priority of services : " + discoveryClient.getOrder());
        result.add("Service Id : " + applicationId);
        Mono<ResponseEntity<List<String>>> just = Mono.just((new ResponseEntity<>(result, HttpStatus.OK)));
        return just;
    }
}
