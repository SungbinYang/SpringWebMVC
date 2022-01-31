## 포매터 추가하기
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servl et/config/annotation/WebMvcConfigurer.html#addFormatters-org.springframework.format.Forma tterRegistry 
- Formatter 
    * Printer: 해당 객체를 (Locale 정보를 참고하여) 문자열로 어떻게 출력할 것인가
    * Parser: 어떤 문자열을 (Locale 정보를 참고하여) 객체로 어떻게 변환할 것인가 
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/format/Fo rmatter.html 
- 포매터 추가하는 방법 1 
    * WebMvcConfigurer의 addFormatters(FormatterRegistry) 메소드 정의 
- 포매터 추가하는 방법 2 (스프링 부트 사용시에만 가능 함) 
    * 해당 포매터를 빈으로 등록

## 도메인 클래스 컨버터 자동 등록
- 스프링 데이터 JPA는 스프링 MVC용 도메인 클래스 컨버터를 제공합니다.
- 도메인 클래스 컨버터 
  * 스프링 데이터 JPA가 제공하는 Repository를 사용해서 ID에 해당하는 엔티티를 읽어옵니다. 
- 의존성 설정 

  ```xml
  <dependency> 
  <groupId>org.springframework.boot</groupId> 
  <artifactId>spring-boot-starter-data-jpa</artifactId> 
  </dependency> 
  <dependency> 
  <groupId>com.h2database</groupId> 
  <artifactId>h2</artifactId> 
  </dependency>
  ```

- 엔티티 맵핑 

  ```java
  @Entity 
  public class Person { 
  @Id @GeneratedValue 
  private Integer id; 
  ...
  
  ```

- 리파지토리 추가 

  ```java
  public interface PersonRepository extends JpaRepository<Person, Integer> { }
  ```

- 테스트 코드 수정 
  * 테스트용 이벤트 객체 생성 
  * 이벤트 리파지토리에 저장 
  * 저장한 이벤트의 ID로 조회 시도

## 핸들러 인터셉터 1부: 개념
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servl et/config/annotation/WebMvcConfigurer.html#addInterceptors-org.springframework.web.servlet. config.annotation.InterceptorRegistry 
- HandlerInterceptor 
  * 핸들러 맵핑에 설정할 수 있는 인터셉터 
  * 핸들러를 실행하기 전, 후(아직 랜더링 전) 그리고 완료(랜더링까지 끝난 이후) 시점에 부가 작업을 하고 싶은 경우에 사용할 수 있다. 
  * 여러 핸들러에서 반복적으로 사용하는 코드를 줄이고 싶을 때 사용할 수 있다. 
    * 로깅, 인증 체크, Locale 변경 등... 
- boolean preHandle(request, response, handler) 
  * 핸들러 실행하기 전에 호출 됨 
  * “핸들러"에 대한 정보를 사용할 수 있기 때문에 서블릿 필터에 비해 보다 세밀한 로직을 구현할 수 있다.
  * 리턴값으로 계속 다음 인터셉터 또는 핸들러로 요청,응답을 전달할지(true) 응답 처리가 이곳에서 끝났는지(false) 알린다. 
- void postHandle(request, response, modelAndView) 
  * 핸들러 실행이 끝나고 아직 뷰를 랜더링 하기 이전에 호출 됨 
  * “뷰"에 전달할 추가적이거나 여러 핸들러에 공통적인 모델 정보를 담는데 사용할 수도 있다. 
  * 이 메소드는 인터셉터 역순으로 호출된다. 
  * 비동기적인 요청 처리 시에는 호출되지 않는다.
- void afterCompletion(request, response, handler, ex) 
  * 요청 처리가 완전히 끝난 뒤(뷰 랜더링 끝난 뒤)에 호출 됨 
  * preHandler에서 true를 리턴한 경우에만 호출 됨
  * 이 메소드는 인터셉터 역순으로 호출된다. 
  * 비동기적인 요청 처리 시에는 호출되지 않는다. 
- vs 서블릿 필터
  * 서블릿 보다 구체적인 처리가 가능하다. 
  * 서블릿은 보다 일반적인 용도의 기능을 구현하는데 사용하는게 좋다. 
- 참고: 
  * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/w eb/servlet/HandlerInterceptor.html 
  * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/w eb/servlet/AsyncHandlerInterceptor.html
  * http://forum.spring.io/forum/spring-projects/web/20146-what-is-the-difference-between-u sing-a-filter-and-interceptor (스프링 개발자 Mark Fisher의 서블릿 필터와의 차이점에 대한 답변 참고)

## 핸들러 인터셉터 2부: 만들고 등록하기 
- 핸들러 인터셉터 구현하기 

  ```java
  public class GreetingInterceptor implements HandlerInterceptor { 
    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
      System.out.println("preHandle 1"); 
      return true; 
    } 
    @Override 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { 
      System.out.println("postHandle 1"); 
    } 
    @Override 
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { 
      System.out.println("afterCompletion 1"); 
    } 
  }
  
  ```

- 핸들러 인터셉터 등록하기 

  ```java
  @Configuration 
  public class WebConfig implements WebMvcConfigurer { 
    @Override 
    public void addInterceptors(InterceptorRegistry registry) { 
      registry.addInterceptor(new GreetingInterceptor()).order(0); 
      registry.addInterceptor(new AnotherInterceptor()) 
      .addPathPatterns("/keeun") 
      .order(-1); 
    } 
  }
  
  ```

- 특정 패턴에 해당하는 요청에만 적용할 수도 있다. 
- 순서를 지정할 수 있다.

## 리소스 핸들러
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servl et/config/annotation/WebMvcConfigurer.html#addResourceHandlers-org.springframework.web. servlet.config.annotation.ResourceHandlerRegistry 
- 이미지, 자바스크립트, CSS 그리고 HTML 파일과 같은 정적인 리소스를 처리하는 핸들러 등록하는 방법 
- 디폴트(Default) 서블릿 
  * 서블릿 컨테이너가 기본으로 제공하는 서블릿으로 정적인 리소스를 처리할 때 사용한다. 
  * https://tomcat.apache.org/tomcat-9.0-doc/default-servlet.html 
- 스프링 MVC 리소스 핸들러 맵핑 등록 
  * 가장 낮은 우선 순위로 등록. 
    * 다른 핸들러 맵핑이 “/” 이하 요청을 처리하도록 허용하고 
    * 최종적으로 리소스 핸들러가 처리하도록. 
  * DefaultServletHandlerConfigurer 
- 리소스 핸들러 설정 
  * 어떤 요청 패턴을 지원할 것인가 
  * 어디서 리소스를 찾을 것인가 
  * 캐싱
  * ResourceResolver: 요청에 해당하는 리소스를 찾는 전략 
    * 캐싱, 인코딩(gzip, brotli), WebJar, ... 
  * ResourceTransformer: 응답으로 보낼 리소스를 수정하는 전략 
    * 캐싱, CSS 링크, HTML5 AppCache, ... 
- 스프링 부트 
  * 기본 정적 리소스 핸들러와 캐싱 제공 
- 참고 
  * https://www.slideshare.net/rstoya05/resource-handling-spring-framework-41

## HTTP 메시지 컨버터 1부: 개요 
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servl et/config/annotation/WebMvcConfigurer.html#configureMessageConverters-java.util.List 
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servl et/config/annotation/WebMvcConfigurer.html#extendMessageConverters-java.util.List 
- HTTP 메시지 컨버터 
  * 요청 본문에서 메시지를 읽어들이거나(@RequestBody), 응답 본문에 메시지를 작성할 때(@ResponseBody) 사용한다. 
- 기본 HTTP 메시지 컨버터 
  * 바이트 배열 컨버터 
  * 문자열 컨버터 
  * Resource 컨버터 
  * Form 컨버터 (폼 데이터 to/from MultiValueMap<String, String>) 
  * (JAXB2 컨버터) 
  * (Jackson2 컨버터) 
  * (Jackson 컨버터) 
  * (Gson 컨버터) 
  * (Atom 컨버터) 
  * (RSS 컨버터) 
- 설정 방법
  * 기본으로 등록해주는 컨버터에 새로운 컨버터 추가하기: extendMessageConverters
  * 기본으로 등록해주는 컨버터는 다 무시하고 새로 컨버터 설정하기: configureMessageConverters 
  * 의존성 추가로 컨버터 등록하기 (추천) 
    * 메이븐 또는 그래들 설정에 의존성을 추가하면 그에 따른 컨버터가 자동으로 등록 된다. 
    * WebMvcConfigurationSupport 
    * (이 기능 자체는 스프링 프레임워크의 기능임, 스프링 부트 아님.) 
- 참고 
  * https://www.baeldung.com/spring-httpmessageconverter-rest

## HTTP 메시지 컨버터 2부: JSON
- 스프링 부트를 사용하지 않는 경우 
  * 사용하고 싶은 JSON 라이브러리를 의존성으로 추가
  * GSON 
  * JacksonJSON 
  * JacksonJSON2
- 스프링 부트를 사용하는 경우 
  * 기본적으로 JacksonJSON 2가 의존성에 들어있다.
  * 즉, JSON용 HTTP 메시지 컨버터가 기본으로 등록되어 있다. 
- 참고 
  * JSON path 문법 
  * https://github.com/json-path/JsonPath
  * http://jsonpath.com/

## HTTP 메시지 컨버터 3부: XML 
- OXM(Object-XML Mapper) 라이브러리 중에 스프링이 지원하는 의존성 추가 
  * JacksonXML 
  * JAXB
- 스프링 부트를 사용하는 경우 
  * 기본으로 XML 의존성 추가해주지 않음.
- JAXB 의존성 추가 

  ```xml
    <dependency> 
      <groupId>javax.xml.bind</groupId> 
      <artifactId>jaxb-api</artifactId> 
    </dependency> 
    <dependency> 
      <groupId>org.glassfish.jaxb</groupId> 
      <artifactId>jaxb-runtime</artifactId> 
    </dependency> 
    <dependency> 
      <groupId>org.springframework</groupId> 
      <artifactId>spring-oxm</artifactId> 
      <version>${spring-framework.version}</version> 
    </dependency>
  ```

- Marshaller 등록 

  ```java
  @Bean 
  public Jaxb2Marshaller marshaller() { 
    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller(); 
    jaxb2Marshaller.setPackagesToScan(Event.class.getPackageName()); 
    return jaxb2Marshaller; 
  }
  
  ```

- 도메인 클래스에 @XmlRootElement 애노테이션 추가 
- 참고
  * Xpath 문법 
  * https://www.w3schools.com/xml/xpath_syntax.asp 
  * https://www.freeformatter.com/xpath-tester.html

## 그밖에 WebMvcConfigurer 설정
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servl et/config/annotation/WebMvcConfigurer.html 
- CORS 설정 
  * Cross Origin 요청 처리 설정 
  * 같은 도메인에서 온 요청이 아니더라도 처리를 허용하고 싶다면 설정한다. 
- 리턴 값 핸들러 설정 
  * 스프링 MVC가 제공하는 기본 리턴 값 핸들러 이외에 리턴 핸들러를 추가하고 싶을 때 설정한다. 
- 아큐먼트 리졸버 설정 
  * 스프링 MVC가 제공하는 기본 아규먼트 리졸버 이외에 커스텀한 아규먼트 리졸버를 추가하고 싶을 때 설정한다. 
- 뷰 컨트롤러 
  * 단순하게 요청 URL을 특정 뷰로 연결하고 싶을 때 사용할 수 있다. 
- 비동기 설정 
  * 비동기 요청 처리에 사용할 타임아웃이나 TaskExecutor를 설정할 수 있다. 
- 뷰 리졸버 설정 
  * 핸들러에서 리턴하는 뷰 이름에 해당하는 문자열을 View 인스턴스로 바꿔줄 뷰 리졸버를 설정한다.
- Content Negotiation 설정 
  * 요청 본문 또는 응답 본문을 어떤 (MIME) 타입으로 보내야 하는지 결정하는 전략을 설정한다.