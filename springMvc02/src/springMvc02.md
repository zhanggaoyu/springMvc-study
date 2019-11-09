## 响应数据和结果视图

---
1. 返回值
   1. 返回String: 
      * 返回的字符串会被springMvc解析成视图, 然后返回, 如果没有这个资源会报错
        * 如: 代码中返回 "success", 在经过springMvc解析后(配置文件添加了后缀".html"), 会转发到success页面
        * 字符串可以这么写: 
          1. "forward:/success": 表示转发到success页面, 默认就是转发
          2. "redirect:/success.jsp": 表示重定向到success.jsp页面, 用这中写法的时候, 需要写去掉项目名的全路径
   2. 返回void:
      * 在方法中通过HttpServletRequest和HttpServletResponse处理业务并设置转发或者重定向后, 就没有必要有返回值了, 可以声明方法返回void
      * 如果方法中没有设置结果视图(转发或重定向)直接生命返回void会导致栈溢出
   3. 返回ModelAndView
      * ModelAndView是SpringMVC为我们提供的一个对象, 该对象也可以用作控制器方法的返回值. 而且, 当我们返回其他类型值的时候, SpringMvc也会将返回的对象封装为ModelAndView对象, 然后再进行处理
      * ModelAndView对象中的方法:
        * ModelAndView addAllObjects(@Nullable Map<String, ?> modelMap)  
            | ModelAndView addObject(Object attributeValue)  
            | ModelAndView addObject(String attributeName, Object attributeValue)
          * 这几个方法接收的参数 在经过 springMvc 处理后会将对象放在 request 域中, 而返回 ModelAndView 方便了链式编程
        * void setViewName(@Nullable String viewName) | void setView(@Nullable View view)
          * 设置结果视图, 通常会用 void setViewName(@Nullable String viewName)
2. ResponseBody响应Json数据
   1. 导入jackson依赖:
      ```
      <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.10.0</version>
      </dependency>
      ```
   2. 使用@ResponseBody注解实现将controller方法返回对象转换为json响应给客户端
      * Springmvc默认用MappingJacksonHttpMessageConverter对json数据进行转换, 需要加入jackson的包(依赖)
   3. 编码: 
      * controller(因为要看效果, 所以就直接写了)
        ```
        @ResponseBody
        @RequestMapping("testJson")
        public User testJson() {
            return new User("李四", 24);
        }
        ```
      * javascript
        ```
        <script src="js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript">
            $(() => {
                $("#jsonBtn").click(() => {
                    $.ajax({
                        url: "resp/testJson",
                        dataType: "JSON",
                        success: (data) => {
                            console.log(data);
                        }
                    });
                });
            })
        </script>
        ```
      * 注意: 需要设置静态资源放行才能访问静态资源(js, css, html) ====> ```<mvc:default-servlet-handler/>```
      * 运行结果  
        ![运行结果](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_191107082448Snipaste_2019-11-07_16-04-57.png)
3. 文件上传
   1. 必要条件: 
      * form表单的enctype取值必须是: multipart/form-data
        * enctype:是表单请求正文的类型, 默认值是: application/x-www-form-urlencoded
      * method属性取值必须是Post
      * 提供一个文件选择域```<input type=”file” />```
   2. 用springMvc实现文件上传
      1. springMvc文件上传需要用到fileupload组件:
         ```
         <dependency>
           <groupId>commons-fileupload</groupId>
           <artifactId>commons-fileupload</artifactId>
           <version>1.3.3</version>
         </dependency>
         ```
      2. 编写页面
         ```
         <form action="fileupload" method="post" enctype="multipart/form-data">
           file: <input type="file" name="file"/><br>
           <button type="submit">提交</button>
         </form>
         ```
      3. 编写Controller
         ```
         @RequestMapping("/testFileupload")
         public String testFileupload(HttpServletRequest request, @RequestParam("upload") MultipartFile file) throws IOException {
             System.out.println("testFileupload running...");
             String path = request.getSession().getServletContext().getRealPath("/upload/");
             File upload = new File(path);
             if (!upload.exists()) {
                 if (!upload.mkdirs()) {
                     throw new RuntimeException("文件夹创建失败");
                 }
             }
             // 获取post中的name的值
             System.out.println(file.getName());
             // 文件的MIME类型
             System.out.println(file.getContentType());
             // 文件名, 带后缀
             System.out.println(file.getOriginalFilename());
             file.transferTo(new File(path + UUID.randomUUID() + file.getOriginalFilename()));
     
             return "redirect:/success.jsp";
         }
         ```
      4. 配置解析器
         ```
         <!-- 配置文件解析器 -->
         <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
             <property name="maxUploadSize" value="10485760"/>
         </bean>
         ```
         * 注意: bean的id是固定值: multipartResolver, 不能更改
4. 跨服务器文件上传: 分服务器处理的目的是让服务器各司其职, 从而提高我们项目的运行效率
   1. 跨服务器文件上传需要用到的组件的依赖:
      ```
      <dependency>
        <groupId>com.sun.jersey</groupId>
        <artifactId>jersey-client</artifactId>
        <version>1.19.1</version>
      </dependency>
      ```
   2. 编写页面
      ```
      <form action="testFileupload2Server" method="post" enctype="multipart/form-data">
        file: <input type="file" name="upload"/><br>
        <button type="submit">提交</button>
      </form>
      ```
   3. 编写Controller
      ```
      @RequestMapping("/testFileupload2Server")
      public String testFileupload2Server(HttpServletRequest request, @RequestParam("upload") MultipartFile file) throws IOException {
          System.out.println("testFileupload2Server running...");
          String path = "http://localhost:9090/springMvc02_file/upload/";
          // 创建客户端对象
          Client client = Client.create();
          // 与图片服务器连接
          WebResource resource = client.resource(path + UUID.randomUUID() + file.getOriginalFilename());
          // 上传文件
          resource.put(file.getBytes());
  
          return "redirect:/success.jsp";
      }
      ```
   4. 配置解析器
      * 与上面一样:
        ```
        <!-- 配置文件解析器 -->
        <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
            <property name="maxUploadSize" value="10485760"/>
        </bean>
        ```
        * 注意: bean的id是固定值: multipartResolver, 不能更改
   5. 创建文件服务器, 需要注意服务器的端口号要改一下
4. springMvc的异常处理
   * 我们可以定义一个异常处理器来处理异常
     2. 编写异常处理器类(实现HandlerExceptionResolver接口即可)
        ```
        public class ExceptionResolver implements HandlerExceptionResolver {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
                System.out.println("出异常了");
                ModelAndView mv = new ModelAndView();
                // 可以有日志记录的相关操作, 然后转发(重定向)到指定页面
                mv.setViewName("error");
        
                return mv;
            }
        }
        ```
     3. 配置异常处理器到spring容器中
        ```
        <bean id="exceptionResolver" class="cn.ann.web.resolver.ExceptionResolver"/>
        ```
5. springMvc的拦截器
   1. 拦截器是什么
      * 语义与过滤器差不多, 有点轻微的区别
      * 拦截器是springMvc的, springMvc框架可以使用, 过滤器任何Java web工程都可以使用
      * 过滤器在配置了 /* 以后会过滤所有的请求, 拦截器只会拦截访问的Controller方法, 如果是jsp, js, css等则不会拦截
      * 拦截器是AOP思想的具体应用. 要自定义拦截器, 需要实现 HandleInterceptor 接口
   2. 自定义拦截器(需要实现HandlerInterceptor)
      1. HandlerInterceptor
         1. boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            * 预处理, 返回true放行, 返回false不放行
         2. void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
            * 后处理
         3. void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            * 请求响应完成后, 最后才执行的方法
      2. 配置拦截器
         ```
         <mvc:interceptors>
             <mvc:interceptor>
                 <!-- 配置拦截什么 -->
                 <mvc:mapping path="/**"/>
                 <!-- 配置不拦截什么 -->
                 <!-- <mvc:exclude-mapping path=""/> -->
                 <!-- 指定自定义拦截器对象 -->
                 <bean class="cn.ann.web.interceptor.MyInterceptor"/>
             </mvc:interceptor>
             <mvc:interceptor>
                 <mvc:mapping path="/**"/>
                 <!-- <mvc:exclude-mapping path=""/> -->
                 <bean class="cn.ann.web.interceptor.MyInterceptor2"/>
             </mvc:interceptor>
         </mvc:interceptors>
         ```
      3. 执行顺序  
         ![执行顺序](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_191107133330Snipaste_2019-11-07_21-31-12.png)

---
本文代码: [此处](https://github.com/zhanggaoyu/springMvc-study)的 springMvc02
