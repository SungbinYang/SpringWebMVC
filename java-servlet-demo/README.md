## 서블릿 애플리케이션 개발 
- 준비물: 메이븐, 톰캣
- 서블릿 구현

```java
public class HelloServlet extends HttpServlet { 
@Override 
public void init() throws ServletException { 
System.out.println("init"); 
} 
@Override 
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
System.out.println("doGet"); 
resp.getWriter().write("Hello Servlet"); 
} 
@Override 
public void destroy() { 
System.out.println("destroy"); 
} 
}
```

- 서블릿 등록

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  
  <servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>me.sungbin.HelloServlet</servlet-class>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello</url-pattern>
  </servlet-mapping>
</web-app>
```