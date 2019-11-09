package cn.ann.web.controller;

import cn.ann.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-6 20:28
 */
@Controller
@RequestMapping("/resp")
public class ResponseController {

    /**
     * 单纯返回void会引起栈溢出
     * @param response
     * @throws IOException
     */
    @RequestMapping("/testRetVoid")
    public void testRetVoid(HttpServletResponse response) throws IOException {
        System.out.println("testRetVoid run...");
        response.sendRedirect("/springMvc02/success.html");
    }

    @RequestMapping("/testRetString")
    public String testRetString() {
        System.out.println("testRetString run...");
        return "forward:success";
    }

    /*
     * 重定向可以通过HttpServletResponse对象实现
     */
    @RequestMapping("/testRetStringRedirect")
    public String testRetStringRedirect() {
        System.out.println("testRetStringRedirect run...");
        return "redirect:/success.jsp";
    }

    @RequestMapping("/testRetModelAndView")
    public ModelAndView testRetModelAndView() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(new User("张三", 23));
        mav.setViewName("success");

        return mav;
    }

    @ResponseBody
    @RequestMapping("/testJson")
    public User testJson() {
        return new User("李四", 24);
    }

}
