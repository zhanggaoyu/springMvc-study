package cn.ann.Controller;

import cn.ann.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;
import java.util.Map;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-4 19:38
 */
@Controller
@RequestMapping("/anno")
@SessionAttributes(value = "user", types = User.class)
public class AnnoController {

    @RequestMapping("/**/testAntPath")
//    @RequestMapping("/**testAntPath")
    public String testAntPath() {
        System.out.println("testAntPath run...");

        return "success";
    }

    @RequestMapping("/testRequestParam")
    public String testRequestParam(@RequestParam("username") String name, Integer age) {
        System.out.println(name + " : " + age);

        return "success";
    }

    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String body) {
        System.out.println(body);

        return "success";
    }

    @RequestMapping("/testPathVariable/{sid}")
    public String testPathVariable(@PathVariable("sid") String id) {
        System.out.println(id);

        return "success";
    }

    @RequestMapping("/testRequestHeader")
    public String testRequestHeader(@RequestHeader("accept") String accept) {
        System.out.println(accept);

        return "success";
    }

    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue(name = "JSESSIONID", required = false) String cv) {
        System.out.println(cv);

        return "success";
    }

    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("user01") User user) {
        System.out.println("testModelAttribute run...");
        System.out.println(user);

        return "success";
    }

    /**
     * 多用于填充对象的空数据, 可以在使用对象之前将对象的所有信息从数据库中查出来
     */
    @ModelAttribute
    public void model(User user, Map<String, User> map) {
        System.out.println("model run..."+user);
        // 可以在这里面写从数据库查询的操作
        user.setBirthday(new Date());

        map.put("user01", user);
    }

    @RequestMapping("/testSessionAttributesPut")
    public String testSessionAttributesPut(Model model) {
        User user = new User();
        user.setName("sessionZS");
        user.setAge(23);
        user.setBirthday(new Date());
        model.addAttribute(user);

        return "success";
    }

    @RequestMapping("/testSessionAttributesGet")
    public String testSessionAttributesGet(ModelMap model) {
        System.out.println(model.get("user"));

        return "success";
    }

    @RequestMapping("/testSessionAttributesClean")
    public String testSessionAttributesClean(SessionStatus status) {
        status.setComplete();

        return "success";
    }

}
