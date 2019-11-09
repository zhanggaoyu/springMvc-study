## SSM(SpringMvc-Spring-MyBatis)整合

---
1. 导入依赖
   ```
   <properties>
     <spring.version>5.0.2.RELEASE</spring.version>
     <log4j.version>2.11.2</log4j.version>
     <mysql.version>5.1.28</mysql.version>
     <mybatis.version>3.5.3</mybatis.version>
     <slf4j.version>1.7.28</slf4j.version>
   </properties>
   <dependencies>
     <!-- spring+springMvc -->
     <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-webmvc</artifactId>
       <version>${spring.version}</version>
     </dependency>
     <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-tx</artifactId>
       <version>${spring.version}</version>
     </dependency>
     <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-jdbc</artifactId>
       <version>${spring.version}</version>
     </dependency>
     <!-- 切面 -->
     <dependency>
       <groupId>org.aspectj</groupId>
       <artifactId>aspectjweaver</artifactId>
       <version>1.9.4</version>
     </dependency>
     <!-- mybatis -->
     <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis</artifactId>
       <version>${mybatis.version}</version>
     </dependency>
     <!-- mybatis和spring整合 -->
     <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis-spring</artifactId>
       <version>2.0.3</version>
     </dependency>
     <!-- mysql -->
     <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>${mysql.version}</version>
     </dependency>
     <!-- C3P0 -->
     <dependency>
       <groupId>com.mchange</groupId>
       <artifactId>c3p0</artifactId>
       <version>0.9.5.4</version>
     </dependency>
     <!-- 文件上传需要的组件 -->
     <dependency>
       <groupId>commons-fileupload</groupId>
       <artifactId>commons-fileupload</artifactId>
       <version>1.3.3</version>
     </dependency>
     <!-- json -->
     <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.10.0</version>
     </dependency>
     <!-- 跨服务器文件传输 -->
     <!--<dependency>
       <groupId>com.sun.jersey</groupId>
       <artifactId>jersey-client</artifactId>
       <version>1.19.1</version>
     </dependency>-->
     <!-- github分页 -->
     <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper</artifactId>
       <version>5.1.2</version>
     </dependency>
     <!-- servlet, jsp和jstl -->
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
     <dependency>
       <groupId>javax.servlet</groupId>
       <artifactId>jstl</artifactId>
       <version>1.2</version>
     </dependency>
     <!-- 测试 -->
     <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-test</artifactId>
       <version>${spring.version}</version>
     </dependency>
     <dependency>
       <groupId>junit</groupId>
       <artifactId>junit</artifactId>
       <version>4.12</version>
       <scope>test</scope>
     </dependency>
     <!-- 日志: log4j2 -->
     <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-core</artifactId>
       <version>${log4j.version}</version>
     </dependency>
     <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>slf4j-api</artifactId>
       <version>${slf4j.version}</version>
     </dependency>
     <dependency>
       <groupId>org.slf4j</groupId>
       <artifactId>slf4j-log4j12</artifactId>
       <version>${slf4j.version}</version>
     </dependency>
   </dependencies>
   ```
   * properties中的参数用于版本锁定, 根据需要修改即可, 另外, 其他的工具类根据需要添加即可
2. 设置SpringMvc配置文件
   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
   
       <!-- 配置视图解析器 -->
       <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/"/>
           <property name="suffix" value=".jsp"/>
       </bean>
   
       <!-- 配置文件解析器 -->
       <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
           <property name="maxUploadSize" value="10485760"/>
       </bean>
   
       <!-- 静态资源放行 -->
       <mvc:default-servlet-handler/>
       <!-- 开启注解 -->
       <mvc:annotation-driven/>
   
   </beans>
   ```
   * 异常解析器和拦截器根据需求自己配
3. Spring配置文件applicationContext.xml配置(我在Spring的配置文件中设置了MybBatis的所有信息, 所以不再配置MyBatis的配置文件)
   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
       <!-- 开启注解扫描并排除 @Controller 注解 -->
       <!-- TODO -->
       <context:component-scan base-package="cn.ann">
           <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
       </context:component-scan>
       <!-- 加载外部 properties 文件(该文件用于存储数据库连接信息) -->
       <context:property-placeholder location="classpath:database.properties"/>
   
       <!-- 配置数据源|连接池 -->
       <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
           <property name="driverClass" value="${mysql.driver}"/>
           <property name="jdbcUrl" value="${mysql.url}"/>
           <property name="user" value="${mysql.username}"/>
           <property name="password" value="${mysql.password}"/>
       </bean>
   
       <!-- 配置SqlSessionFactory -->
       <bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"/>
           <!-- MyBatis中的别名 -->
           <!-- TODO -->
           <property name="typeAliasesPackage" value="cn.ann.beans"/>
           <!-- MyBatis中的setting标签配置, 按需要配置即可 -->
           <!-- TODO -->
           <property name="configuration">
               <bean class="org.apache.ibatis.session.Configuration">
                   <!-- 配置数据库字段名转驼峰 -->
                   <property name="mapUnderscoreToCamelCase" value="true"/>
                   <!-- 配置mybatis怎么处理null值 -->
                   <property name="jdbcTypeForNull" value="NULL"/>
                   <!-- 配置懒加载 -->
                   <property name="lazyLoadingEnabled" value="true"/>
                   <property name="aggressiveLazyLoading" value="false"/>
                   <property name="lazyLoadTriggerMethods" value=""/>
                   <!-- 配置日志 -->
                   <property name="logImpl" value="org.apache.ibatis.logging.log4j2.Log4j2Impl"/>
               </bean>
           </property>
       </bean>
       <!-- 配置mapper文件的路径, 配置了该bean就不用写Mapper的实现了 -->
       <!-- TODO -->
       <bean id="mapperScan" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
           <property name="basePackage" value="cn.ann.mapper"/>
       </bean>
   
       <!-- 配置事务 -->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"/>
       </bean>
       <tx:advice id="txAdvice">
           <tx:attributes>
               <!-- TODO -->
               <tx:method name="*"/>
               <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
               <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
           </tx:attributes>
       </tx:advice>
       <!-- 配置事务到切面 -->
       <aop:config>
           <!-- TODO -->
           <aop:pointcut id="txPc" expression="execution(* cn.ann.service..*.*(..))"/>
           <aop:advisor advice-ref="txAdvice" pointcut-ref="txPc"/>
       </aop:config>
   </beans>
   ```
   * 配置文件中需要修改的地方, 我都用 TODO 标注了, 按需修改即可
4. log4j2配置文件(无脑复制)
   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration status="WARN" monitorInterval="30">
       <appenders>
           <console name="Console" target="SYSTEM_OUT">
               <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
           </console>
           <File name="log" fileName="log/test.log" append="false">
               <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
           </File>
           <RollingFile name="RollingFileInfo" fileName="${sys:user.home}/logs/info.log"
                        filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
               <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
               <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
               <Policies>
                   <TimeBasedTriggeringPolicy/>
                   <SizeBasedTriggeringPolicy size="100 MB"/>
               </Policies>
           </RollingFile>
           <RollingFile name="RollingFileWarn" fileName="${sys:user.home}/logs/warn.log"
                        filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
               <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY"/>
               <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
               <Policies>
                   <TimeBasedTriggeringPolicy/>
                   <SizeBasedTriggeringPolicy size="100 MB"/>
               </Policies>
               <DefaultRolloverStrategy max="20"/>
           </RollingFile>
           <RollingFile name="RollingFileError" fileName="${sys:user.home}/logs/error.log"
                        filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
               <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
               <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
               <Policies>
                   <TimeBasedTriggeringPolicy/>
                   <SizeBasedTriggeringPolicy size="100 MB"/>
               </Policies>
           </RollingFile>
       </appenders>
       <loggers>
           <logger name="org.springframework" level="INFO"/>
           <logger name="org.mybatis" level="INFO"/>
           <root level="all">
               <appender-ref ref="Console"/>
               <appender-ref ref="RollingFileInfo"/>
               <appender-ref ref="RollingFileWarn"/>
               <appender-ref ref="RollingFileError"/>
           </root>
       </loggers>
   </configuration>
   ```
5. 在Tomcat的web.xml中配置spring和springMvc
   * 配置spring随服务器启动而启动并加载配置文件
     ```
     <!-- 配置服务器启动加载spring配置文件 -->
     <listener>
         <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     </listener>
     <context-param>
         <param-name>contextConfigLocation</param-name>
         <param-value>classpath:applicationContext.xml</param-value>
     </context-param>
     ```
   * 配置springMvc
     ```
     <!-- 配置spring-mvc -->
     <servlet>
         <servlet-name>dispatcherServlet</servlet-name>
         <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
         <init-param>
             <param-name>contextConfigLocation</param-name>
             <param-value>classpath:spring-mvc.xml</param-value>
         </init-param>
     </servlet>
     <servlet-mapping>
         <servlet-name>dispatcherServlet</servlet-name>
         <url-pattern>/</url-pattern>
     </servlet-mapping>
     <!-- 字符编码过滤器 -->
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

---
本文代码: [此处](https://github.com/zhanggaoyu/springMvc-study)的 ssm-demo
SSM整合依赖包括:
1. spring依赖(基础包, aop, 切面, 事务, jdbc)
2. springMvc依赖(webMvc)
3. mybatis依赖(mybatis包, mybatis-spring整合包)
4. 数据库驱动包(mysql)
5. servlet, jsp, jstl支持
6. 工具包(酌情删减)
   1. 文件上传(fileupload)
   2. 连接池(c3p0)
   3. json转化(jackson)\
   4. 跨服务器文件传输依赖(jersey-client: 已注释)
   5. github分页(pagehelper)
7. 测试和日志打印
