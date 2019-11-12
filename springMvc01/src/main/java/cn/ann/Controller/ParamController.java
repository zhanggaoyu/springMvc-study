package cn.ann.Controller;

import cn.ann.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 16:43
 */
@Controller
@RequestMapping("/param")
public class ParamController {
    @RequestMapping("/param01")
    public String testParam01(String name, Integer age) {
        System.out.println(name + " : " + age);

        return "success";
    }

    @RequestMapping("/param02")
    public String testParam02(User user) {
        System.out.println(user);

        return "success";
    }

    @RequestMapping("/param03")
    public String testParam03(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request);
        System.out.println(response);

        return "success";
    }

    @RequestMapping("/testConverter")
    public String testConverter(User user) {
        System.out.println(user);

        return "success";
    }

}
