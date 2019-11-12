package cn.ann.web.resolver;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 20:35
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    /**
     *
     * @param request  请求
     * @param response 响应
     * @param o        public java.lang.String cn.ann.web.controller.ExceptionController.testException() throws java.lang.Exception
     *                 出异常的对象 org.springframework.web.method.HandlerMethod
     * @param e        异常
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        System.out.println("出异常了");
        System.out.println(o.getClass());
        e.printStackTrace();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/error.jsp");

        return mv;
    }
}
