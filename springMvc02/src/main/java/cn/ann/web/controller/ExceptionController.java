package cn.ann.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 20:53
 */
@Controller
public class ExceptionController {
    @RequestMapping("/exception")
    public String testException() throws Exception {
        System.out.println("testException run...");
        int i = 1 / 0;
        return "redirect:/success.jsp";
    }

}
