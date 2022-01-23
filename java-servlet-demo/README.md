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

## 서블릿 리스너와 필터
- 서블릿 리스너
    * 웹 애플리케이션에서 발생하는 주요 이벤트를 감지하고 각 이벤트에 특별한 작업이 필요한 경우에 사용할 수 있다. 
        * 서블릿 컨텍스트 수준의 이벤트 
            * 컨텍스트 라이프사이클 이벤트
            * 컨텍스트 애트리뷰트 변경 이벤트
        * 세션 수준의 이벤트 
            * 세션 라이프사이클 이벤트 
            * 세션 애트리뷰트 변경 이벤트 
- 서블릿 필터
    * 들어온 요청을 서블릿으로 보내고, 또 서블릿이 작성한 응답을 클라이언트로 보내기 전에 특별한 처리가 필요한 경우에 사용할 수 있다.
    * 체인 형태의 구조 

    ![](./img01.png)

- 참고 
    * https://docs.oracle.com/cd/B14099_19/web.1012/b14017/filters.htm#i1000654

## 스프링 IoC 컨테이너 연동 

![](./img02.png)

> (출처: https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc)

- 서블릿 애플리케이션에 스프링 연동하기 
    * 서블릿에서 스프링이 제공하는 IoC 컨테이너 활용하는 방법 
    * 스프링이 제공하는 서블릿 구현체 DispatcherServlet 사용하기 
- ContextLoaderListener 
    * 서블릿 리스너 구현체
    * ApplicationContext를 만들어 준다. 
    * ApplicationContext를 서블릿 컨텍스트 라이프사이클에 따라 등록하고 소멸시켜준다.
    * 서블릿에서 IoC 컨테이너를 ServletContext를 통해 꺼내 사용할 수 있다.