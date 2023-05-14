package io.shj.action.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/getHello")
    public String getHello(){
        return "Hello, shj, action";
    }
}
