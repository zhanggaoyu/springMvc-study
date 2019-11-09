package cn.ann.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by 88475 with IntelliJ IDEA on 2019-11-7 20:36
 */
public class MyInterceptor2 implements HandlerInterceptor {
    /**
     * 预处理
     * @return true放行, 到下一个拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor2的预处理执行了.....");
        return true;
    }

    /**
     *
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor2的后处理执行了.....");
    }

    /**
     * 最后处理(响应完成之后)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyInterceptor2的最终处理执行了.....");
    }
}
