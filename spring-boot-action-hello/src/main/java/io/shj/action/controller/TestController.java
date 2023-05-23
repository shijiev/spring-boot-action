package io.shj.action.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/getOk")
    public String getOk(){
        return "ok";
    }
}
