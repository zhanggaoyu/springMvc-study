## spring01

---
1. 概述
   * springMvc是一种基于Java实现MVC设计模式的请求驱动类型的轻量级web框架
   * 它通过一套注解, 让一个简单的Java类成为处理请求的控制器, 而无需实现任何接口
   * 支持REST编程风格
   * 它在MVC三层架构中处于<span style="color:red;">表现层</span>
2. springMvc和struts2优劣分析
   1. 共同点: 
      * 它们都是表现层框架，都是基于 MVC 模型编写的。
      * 它们的底层都离不开原始 ServletAPI。
      * 它们处理请求的机制都是一个核心控制器。
   2. 区别：
      * Spring MVC 的入口是 Servlet, 而 Struts2 是 Filter
      * Spring MVC 是基于方法设计的，而 Struts2 是基于类，Struts2 每次执行都会创建一个动作类。所以 Spring MVC 会稍微比 Struts2 快些。
      * Spring MVC 使用更加简洁,同时还支持 JSR303, 处理 ajax 的请求更方便
        * JSR303 是一套 JavaBean 参数校验的标准，它定义了很多常用的校验注解，我们可以直接将这些注解加在我们 JavaBean 的属性上面，就可以在需要校验的时候进行校验了。
      * Struts2 的 OGNL 表达式使页面的开发效率相比 Spring MVC 更高些，但执行效率并没有比 JSTL 提升，尤其是 struts2 的表单标签，远没有 html 执行效率高。
3. springMvc基本执行流程
   * ![执行流程](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_springMvc%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.png)
4. hello
   1. 搭建环境
      1. 导依赖
         ```
         <dependencies>
           <dependency>
             <groupId>org.springframework</groupId>
             <artifactId>spring-context</artifactId>
             <version>5.0.2.RELEASE</version>
           </dependency>
           <dependency>
             <groupId>org.springframework</groupId>
             <artifactId>spring-web</artifactId>
             <version>5.0.2.RELEASE</version>
           </dependency>
           <dependency>
             <groupId>org.springframework</groupId>
             <artifactId>spring-webmvc</artifactId>
             <version>5.0.2.RELEASE</version>
           </dependency>
           <dependency>
             <groupId>javax.servlet</groupId>
             <artifactId>javax.servlet-api</artifactId>
             <version>3.1.0</version>
             <scope>provided</scope>
           </dependency>
           <dependency>
             <groupId>javax.servlet.jsp</groupId>
             <artifactId>javax.servlet.jsp-api</artifactId>
             <version>2.3.1</version>
             <scope>provided</scope>
           </dependency>
         </dependencies>
         ```
      2. 写springMvc配置文件
         ```
         <?xml version="1.0" encoding="UTF-8"?>
         <beans xmlns="http://www.springframework.org/schema/beans"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:context="http://www.springframework.org/schema/context"
                xmlns:mvc="http://www.springframework.org/schema/mvc"
                xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
             <!-- 开启注解扫描 -->
             <context:component-scan base-package="cn.ann"/>
         
             <!-- 配置视图解析器 -->
             <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
                 <property name="prefix" value=""/>
                 <property name="suffix" value=".html"/>
             </bean>
         
             <!-- 开启springMvc注解支持 -->
             <mvc:annotation-driven/>
         
             <!-- 静态资源放行 -->
             <mvc:default-servlet-handler/>
         </beans>
         ```
      3. web.xml
         ```
         <?xml version="1.0" encoding="UTF-8"?>
         <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
                  version="4.0">
             <servlet>
                 <!-- 配置springMvc -->
                 <servlet-name>dispatcherServlet</servlet-name>
                 <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
                 <init-param>
                     <param-name>contextConfigLocation</param-name>
                     <param-value>classpath:springMvc.xml</param-value>
                 </init-param>
                 <!-- 配置加载时机 -->
                 <load-on-startup>1</load-on-startup>
             </servlet>
             <servlet-mapping>
                 <servlet-name>dispatcherServlet</servlet-name>
                 <url-pattern>/</url-pattern>
             </servlet-mapping>
         </web-app>
         ```
   2. 编写程序  
      * 编写Controller类
        ```
        @Controller
        public class HelloController {
            @RequestMapping("/hello")
            public String hello() {
                System.out.println("hello springMvc");
                return "success";
            }
        }
        ```
      * 写页面, 加一个a标签即可, href为hello
   3. 启动服务器, 加载配置文件
      * DispatcherServlet对象创建
      * springMvc.xml加载
      * HelloController对象创建
   4. 发送请求, 后台处理请求
      * ![hello1](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_springMvc01hello1.png)
      * ![hello2](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_springMvc01hello2.png)
      * ![hello3](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_springMvc01hello3.png)
5. springMvc框架结构  
   ![springMvc组件图](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_springMvcFrame.png)
   * 执行流程
     1. 用户发送请求到前端控制器 DispatcherServlet
     2. DispatcherServlet收到请求调用HandlerMapping处理器映射器
     3. 处理器映射器根据请求url找到具体的处理器, 生成处理器对象及处理器拦截器(如果有则生成)一并返回给DispatcherServlet
     4. DispatcherServlet通过HandlerAdapter处理器适配器调用处理器
     5. 执行<span style="color: green;">处理器/后端控制器</span>Controller
     6. Controller执行完成返回ModelAndView
     7. HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet
     8. DispatcherServlet将ModelAndView传给ViewResolver视图解析器
     9. ViewResolver解析后返回具体View
     10. DispatcherServlet对View进行渲染视图(即将模型数据填充至视图中)
     11. DispatcherServlet响应用户
   * 组件说明
     1. DispatcherServlet：前端控制器
        * 用户请求到达前端控制器, 它就相当于mvc模式中的c, dispatcherServlet是整个流程控制的中心, 由它调用其它组件处理用户的请求, dispatcherServlet的存在降低了组件之间的耦合性
     2. HandlerMapping：处理器映射器
        * HandlerMapping负责根据用户请求url找到Handler即处理器, springMvc提供了不同的映射器实现不同的映射方式, 例如: 配置文件方式, 实现接口方式, 注解方式等
     3. Handler：处理器
        * Handler是继DispatcherServlet前端控制器的后端控制器, 在DispatcherServlet的控制下Handler对具体的用户请求进行处理
        * 由于Handler涉及到具体的用户业务请求, 所以一般情况需要程序员根据业务需求开发Handler
     4. HandleAdapter：处理器适配器
        * 通过HandlerAdapter对处理器进行执行, 这是适配器模式的应用, 通过扩展适配器可以对更多类型的处理器进行执行
        * Java的io流中就运用了适配器模式(转换流)
     5. ViewResolver：视图解析器
        * ViewResolver负责将处理结果生成View视图, ViewResolver首先根据逻辑视图名解析成物理视图名即具体的页面地址, 再生成View视图对象, 最后对View进行渲染, 将处理结果通过页面展示给用户
     6. View：视图
        * springMvc框架提供了很多的View视图类型的支持, 包括: jsPlView, freemarkerView, pdfView等. 我们最常用的视图就是jsp
        * 一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户, 需要由程序员根据业务需求开发具体的页面
     * springMvc.xml中```<mvc:annotation-driven/>```的作用: 配置这个标签就相当于配置了处理器映射器(HandleMapping), 处理器适配器(HandleAdapter) 和 异常解析器(ExceptionResolver)
     * 在springMvc的各个组件中, 处理器映射器 & 处理器适配器 & 视图解析器称为springMvc的三大组件. 需要用户开发的组件有handler & view
6. @RequestMapping解析:
   1. 位置: 可以作用在类上或方法上(便于分模块开发)
   2. 属性: 
      * value(path): 两个属性作用一样, 用于指定映射路径, 需要以 '/' 开头
      * method: 用于指定允许接受的请求方式
      * params: 用于指定限制请求参数的条件. 支持简单的表达式, 但是要求请求参数的key和value必须和配置的一模一样
        * {"name"}: 参数名必须是name
        * {"age>10"}: age不能是10
      * headers: 指定必须有的请求头
   3. 扩展(了解): path属性支持Ant风格的url
      * ant风格:
        - ?: 匹配文件名的一个字符. 
          * /springMvc01/anno/??/testAntPath ====> /springMvc01/anno/12/testAntPath
          * /springMvc01/anno/??testAntPath ====> /springMvc01/anno/12testAntPath
        - \*: 匹配文件名的任意(多个)字符. 
          * /springMvc01/anno/\*/testAntPath ====> /springMvc01/anno/12asdfasd啊啊啊/testAntPath
          * /springMvc01/anno/\*testAntPath ====> /springMvc01/anno/12asdfasd啊啊啊testAntPath
        - \*\*: 匹配多层路径. 
          * /springMvc01/anno/\*\*/testRequestHeader ====> /springMvc01/anno/12/a/sd/fa/sd/啊/啊啊/testAntPath
          * /springMvc01/anno/\*\*testRequestHeader ====> 这种写法是错误的
        - ant风格的一个坑: 
          * 在向 requestMapping 中添加ant风格的url时, '?', '\*'需要放在'/'后面, '\*\*' 必须包裹在两个'/'中间
7. 参数绑定: 方法的参数列表里面的参数
   1. 基本类型和String等
      * post表单提交的name和参数名一致即可
   2. 乱码问题: 添加过滤器即可
      ```
      <filter>
          <filter-name>characterEncodingFilter</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <init-param>
              <param-name>encoding</param-name>
              <param-value>UTF-8</param-value>
          </init-param>
      </filter>
      <filter-mapping>
          <filter-name>characterEncodingFilter</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
      ```
   3. 对象属性: 要求提供getter和setter
      * 基本属性和String: post表单提交的name和属性名一致即可
      * 对象: 如User中有Address类型的属性addr, Address中有属性name. 写addr.name即可
   4. 对象中的list和map类型
      * list: list的属性名\[索引值\].属性 ---> 属性指的是list泛型的属性
      * map: map的属性名\['key'\].属性 ---> 属性指的是map第二个泛型的属性
   5. 自定义类型转换器
      1. 编写自定义的转换器
         ```
         public class StringToDateConverter implements Converter<String, Date> {
             @Override
             public Date convert(String s) {
                 try {
                     if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$", s)) {
                         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                         return format.parse(s);
                     } else if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$", s)) {
                         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         return format.parse(s);
                     } else if (Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{1,2}:\\d{1,2}$", s)) {
                         s = s.replace('T', ' ');
                         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         return format.parse(s);
                     } else {
                         throw new RuntimeException("输入格式不正确");
                     }
                 } catch (ParseException e) {
                     e.printStackTrace();
                     throw new RuntimeException(e);
                 }
             }
         }
         ```
      2. 注册到spring中
         ```
         <!-- 注册自定义转换器 -->
         <bean id="conversionServiceFactory" class="org.springframework.context.support.ConversionServiceFactoryBean">
             <property name="converters">
                 <set>
                     <bean class="cn.ann.utils.StringToDateConverter"/>
                 </set>
             </property>
         </bean>
     
         <!-- 开启springMvc注解支持 -->
         <mvc:annotation-driven conversion-service="conversionServiceFactory"/>
         ```
   6. 获取原生的request和response(原生的ServletAPI对象): 在参数列表添加参数即可
8. 常用注解: <span style="color:red;">若没有特殊说明, 以下注释全是用于参数列表中的参数的</span>
   1. @RequestParam: 解决参数列表中参数名和post表单中name值不一致的问题
      * 属性: 
        * name(value): 请求参数中的名称
        * required: 请求参数中是否必须提供此参数, 默认为true
        * defaultValue: 设置默认值
   2. @RequestBody(常用于异步(ajax)请求中): 用于获取请求体的内容, 直接使用获取到的是key=value&key=value的数据
      * 属性: 
        * required: 是否必须有请求体. 默认值是true. 
          * 值为true时, get请求方式会报错
          * 值为false时, get请求会返回null
      * 不适用于get方式, 因为get方式没有请求体
   3. @PathVariable: 用于绑定url中的占位符. 例如: 请求url中 /delete/{id}, 这个{id}就是url占位符. url支持占位符是spring3.0之后加入的. 是springMvc支持rest风格URL的一个重要标志
      * 属性:
        1. name(value): 用于指定url中占位符名称
        2. required: 是否必须提供占位符
      * rest风格:  
        ```
        什么是rest：
           REST（英文：Representational State Transfer，简称REST）描述了一个架构样式的网络系统，比如 web 应用程序。它首次出现在 2000 年 Roy Fielding 的博士论文中，他是 HTTP 规范的主要编写者之一。在目前主流的三种Web服务交互方案中，REST相比于SOAP（Simple Object Access protocol，简单对象访问协议）以及XML-RPC更加简单明了，无论是对URL的处理还是对Payload的编码，REST都倾向于用更加简单轻量的方法设计和实现。值得注意的是REST并没有一个明确的标准，而更像是一种设计的风格。 
           它本身并没有什么实用性，其核心价值在于如何设计出符合REST风格的网络接口。 
        restful的优点 
           它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用。 
        restful的特性： 
           资源（Resources）：网络上的一个实体，或者说是网络上的一个具体信息。 它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的存在。可以用一个URI（统一资源定位符）指向它，每种资源对应一个特定的 URI 。要 获取这个资源，访问它的URI就可以，因此 URI 即为每一个资源的独一无二的识别符。 
           表现层（Representation）：把资源具体呈现出来的形式，叫做它的表现层 （Representation）。 比如，文本可以用 txt 格式表现，也可以用 HTML 格式、XML 格式、JSON 格式表现，甚至可以采用二进制格式。 
           状态转化（State Transfer）：每 发出一个请求，就代表了客户端和服务器的一次交互过程。 HTTP协议，是一个无状态协议，即所有的状态都保存在服务器端。因此，如果客户端想要操作服务器，必须通过某种手段，让服务器端发生“状态转化”（State Transfer）。而这种转化是建立在表现层之上的，所以就是 “表现层状态转化”。具体说，就是 HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源。 
        ```
        * restful的示例: 
          * /account/1 HTTP GET ： 得到 id = 1 的 account 
          * /account/1 HTTP DELETE： 删除 id = 1的 account 
          * /account/1 HTTP PUT： 更新id = 1的 account
          * /account HTTP POST： 新增 account
      * 浏览器form表单仅支持get和post, 所以spring添加了一个过滤器, 可以将这些请求转化为标准的http方法, 从而支持GET, POST, PUT 和 DELETE
        * HiddenHttpMethodFilter
        * 需要在form表单中添加hidden表单项: 
          * ```<input type="hidden" name="_method" value="GET|POST|PUT|DELETE"/>```
   4. @RequestHeader: 用于获取请求消息头
      * 属性:
        1. name(value): 提供消息头名称
        2. required: 是否必须有此消息头
      * 在实际开发中并不常用
   5. @CookieValue: 用于把指定cookie名称的值传入控制器方法参数
      * 属性:
        1. name(value): 指定cookie的名称
        2. required: 是否必须有此cookie
   6. @ModelAttribute: 该注解是SpringMVC4.3版本以后新加入的。它可以用于修饰方法和参数
      * 位置: 
        1. 出现在方法上, 表示当前方法会在控制器的方法执行之前, 先执行. 它可以修饰没有返回值的方法, 也可以修饰有具体返回值的方法
        2. 出现在参数上，获取指定的数据给参数赋值
      * 属性:
        1. name(value): 用于获取数据的key. key可以是POJO的属性名称, 也可以是map结构的key
        2. binding: 表示是否必须绑定数据. 默认是true
      * 应用场景:
        * 当表单提交数据不是完整的实体类数据时, 保证没有提交数据的字段使用数据库对象原来的数据
      * 示例代码
        ```
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
        ```
   7. @SessionAttributes: 用于多次执行控制器方法间的参数共享
      * 位置: 类上
        * ```@SessionAttributes(value = "user", types = User.class)```
      * 属性:
        1. names(value): 用于指定存入的属性名称, 数组类型
        2. type: 用于指定存入的数据类型, 数组类型  
        
      ```
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
      ```
   8. @InitBinder: 由该注解标识的方法, 可以完成对WebDataBinder对象的初始化. WebDataBinder是DataBinder的子类, 用于完成表单字段到JavaBean属性的绑定. 简单地说, 就是可以设置哪些表单属性不用映射到JavaBean
      * @InitBinder标识的方法不能有返回值, 必须声明为void
      * @InitBinder方法的参数一般是WebDataBinder

---
本文代码: [此处](https://github.com/zhanggaoyu/springMvc-study)的 springMvc01
