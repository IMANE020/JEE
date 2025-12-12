package com.cigma.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController


public class HelloController {

    @RequestMapping(value={"hello", "/"})
    public String hello() {
        System.out.println("ce package sera scané par \n" +
                "@SpringBootApplication");
        return "hello";
    }

}
