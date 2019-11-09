package cn.ann.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 10:26
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello springMvc");
        return "success";
    }

    @RequestMapping(path = "/testRequestMapping", method = RequestMethod.POST, params = {"name", "age!=10"})
    public String testRequestMapping() {
        return "success";
    }

}
