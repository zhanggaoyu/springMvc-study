package cn.ann.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 21:05
 */
public class InterceptorController {
    @RequestMapping("/interceptor")
    public String testInterceptor() throws Exception {
        System.out.println("testInterceptor run...");
        return "redirect:/success.jsp";
    }
}
