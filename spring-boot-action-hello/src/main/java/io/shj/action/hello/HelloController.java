package io.shj.action.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class HelloController {
    @RequestMapping("/getHello")
    public String getHello(){
        return "Hello, shj, action";
    }

    @GetMapping("/getAll")
    public List<Book> getAll(){
        Book b1 = new Book(1,"《技术实践1》","shijiepro");
        Book b2 = new Book(2,"《人不能糊涂过一生》","shijiepro");
        Book b3 = new Book(3,"《快速完成》","shijiepro");
        List<Book> list = new ArrayList<>();
        list.add(b1);list.add(b2);list.add(b3);
        return list;
    }
}
