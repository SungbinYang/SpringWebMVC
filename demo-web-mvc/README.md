## 스프링 MVC 핵심 기술 소개 
- 애노테이션 기반의 스프링 MVC 
    * 요청 맵핑하기
    * 핸들러 메소드 
    * 모델과 뷰
    * 데이터 바인더 
    * 예외 처리 
    * 글로벌 컨트롤러 
- 사용할 기술 
    * 스프링 부트 
    * 스프링 프레임워크 웹 MVC 
    * 타임리프
- 학습 할 애노테이션 
    * @RequestMapping 
        * @GetMapping, @PostMapping, @PutMapping, ... 
    * @ModelAttribute 
    * @RequestParam, @RequestHeader 
    * @PathVariable, @MatrixVariable 
    * @SessionAttribute, @RequestAttribute, @CookieValue 
    * @Valid 
    * @RequestBody, @ResponseBody 
    * @ExceptionHandler 
    * @ControllerAdvice
- 참고:
    * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-controller

## HTTP 요청 맵핑하기 1부: 요청 메소드 
- HTTP Method 
  * GET, POST, PUT, PATCH, DELETE, ... 
- GET 요청 
  * 클라이언트가 서버의 리소스를 요청할 때 사용한다. 
  * 캐싱 할 수 있다. (조건적인 GET으로 바뀔 수 있다.) 
  * 브라우저 기록에 남는다. 
  * 북마크 할 수 있다. 
  * 민감한 데이터를 보낼 때 사용하지 말 것. (URL에 다 보이니까)
  * idempotent
- POST 요청 
  * 클라이언트가 서버의 리소스를 수정하거나 새로 만들 때 사용한다. 
  * 서버에 보내는 데이터를 POST 요청 본문에 담는다. 
  * 캐시할 수 없다.
  * 브라우저 기록에 남지 않는다. 
  * 북마크 할 수 없다.
  * 데이터 길이 제한이 없다. 
- PUT 요청 
  * URI에 해당하는 데이터를 새로 만들거나 수정할 때 사용한다. 
  * POST와 다른 점은 “URI”에 대한 의미가 다르다. 
    * POST의 URI는 보내는 데이터를 처리할 리소스를 지칭하며 
    * PUT의 URI는 보내는 데이터에 해당하는 리소스를 지칭한다.
  * Idempotent 
- PATCH 요청 
  * PUT과 비슷하지만, 기존 엔티티와 새 데이터의 차이점만 보낸다는 차이가 있다.
  * Idempotent
- DELETE 요청 
  * URI에 해당하는 리소스를 삭제할 때 사용한다. 
  * Idempotent 
- 스프링 웹 MVC에서 HTTP method 맵핑하기
  * @RequestMapping(method=RequestMethod.GET)
  * @RequestMapping(method={RequestMethod.GET, RequestMethod.POST}) 
  * @GetMapping, @PostMapping, ...
- 참고 
  * https://www.w3schools.com/tags/ref_httpmethods.asp 
  * https://tools.ietf.org/html/rfc2616#section-9.3
  * https://tools.ietf.org/html/rfc2068

##  HTTP 요청 맵핑하기 2부: URI 패턴 맵핑 
- URI, URL, URN 햇갈린다 
  * https://stackoverflow.com/questions/176264/what-is-the-difference-between-a-uri-a-url-a nd-a-urn
- 요청 식별자로 맵핑하기 
  * @RequestMapping은 다음의 패턴을 지원합니다. 
  * ?: 한 글자 (“/author/???” => “/author/123”) 
  * "*": 여러 글자 (“/author/*” => “/author/keesun”) 
  * "**": 여러 패스 (“/author/** => “/author/keesun/book”)
- 클래스에 선언한 @RequestMapping과 조합 
  * 클래스에 선언한 URI 패턴뒤에 이어 붙여서 맵핑합니다. 
- 정규 표현식으로 맵핑할 수도 있습니다. 
  * /{name:정규식} 
- 패턴이 중복되는 경우에는? 
  * 가장 구체적으로 맵핑되는 핸들러를 선택합니다. 
- URI 확장자 맵핑 지원
  * 이 기능은 권장하지 않습니다. (스프링 부트에서는 기본으로 이 기능을 사용하지 않도록 설정 해 줌) 
    * 보안 이슈 (RFD Attack) 
    * URI 변수, Path 매개변수, URI 인코딩을 사용할 때 할 때 불명확 함.
- RFD Attack 
  * https://www.trustwave.com/en-us/resources/blogs/spiderlabs-blog/reflected-file-downloa d-a-new-web-attack-vector/ 
  * https://www.owasp.org/index.php/Reflected_File_Download 
  * https://pivotal.io/security/cve-2015-5211

## HTTP 요청 맵핑하기 3부: 미디어 타입 맵핑 
- 특정한 타입의 데이터를 담고 있는 요청만 처리하는 핸들러
  * @RequestMapping(consumes=MediaType.APPLICATION_JSON_UTF8_VALUE) 
  * Content-Type 헤더로 필터링 
  * 매치 되는 않는 경우에 415 Unsupported Media Type 응답 
- 특정한 타입의 응답을 만드는 핸들러 
  * @RequestMapping(produces=”application/json”) 
  * Accept 헤더로 필터링 (하지만 살짝... 오묘함) 
  * 매치 되지 않는 경우에 406 Not Acceptable 응답 
- 문자열을 입력하는 대신 MediaType을 사용하면 상수를 (IDE에서) 자동 완성으로 사용할 수 있다.
- 클래스에 선언한 @RequestMapping에 사용한 것과 조합이 되지 않고 메소드에 사용한 @RequestMapping의 설정으로 덮어쓴다.
- Not (!)을 사용해서 특정 미디어 타입이 아닌 경우로 맵핑 할 수도 있다.

## HTTP 요청 맵핑하기 4부: 헤더와 매개변수
- 특정한 헤더가 있는 요청을 처리하고 싶은 경우 
  * @RequestMapping(headers = “key”) 
- 특정한 헤더가 없는 요청을 처리하고 싶은 경우
  * @RequestMapping(headers = “!key”) 
- 특정한 헤더 키/값이 있는 요청을 처리하고 싶은 경우 
  * @RequestMapping(headers = “key=value”) 
- 특정한 요청 매개변수 키를 가지고 있는 요청을 처리하고 싶은 경우 
  * @RequestMapping(params = “a”)
- 특정한 요청 매개변수가 없는 요청을 처리하고 싶은 경우 
  * @RequestMapping(params = “!a”) 
- 특정한 요청 매개변수 키/값을 가지고 있는 요청을 처리하고 싶은 경우 
  * @RequestMapping(params = “a=b”)

## HTTP 요청 맵핑하기 5부: HEAD와 OPTIONS 요청 처리 
- 우리가 구현하지 않아도 스프링 웹 MVC에서 자동으로 처리하는 HTTP Method
  * HEAD
  * OPTIONS 
- HEAD 
  * GET 요청과 동일하지만 응답 본문을 받아오지 않고 응답 헤더만 받아온다. 
- OPTIONS 
  * 사용할 수 있는 HTTP Method 제공 
  * 서버 또는 특정 리소스가 제공하는 기능을 확인할 수 있다. 
  * 서버는 Allow 응답 헤더에 사용할 수 있는 HTTP Method 목록을 제공해야 한다. 
- 참고
  * https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html 
  * https://github.com/spring-projects/spring-framework/blob/master/spring-test/src/test/java/ org/springframework/test/web/servlet/samples/standalone/resultmatchers/HeaderAssertionTests.java

## HTTP 요청 맵핑하기 6부: 커스텀 애노테이션 
- @RequestMapping 애노테이션을 메타 애노테이션으로 사용하기 
  * @GetMapping 같은 커스텀한 애노테이션을 만들 수 있다. 
- 메타(Meta) 애노테이션 
  * 애노테이션에 사용할 수 있는 애노테이션 
  * 스프링이 제공하는 대부분의 애노테이션은 메타 애노테이션으로 사용할 수 있다. 
- 조합(Composed) 애노테이션
  * 한개 혹은 여러 메타 애노테이션을 조합해서 만든 애노테이션 
  * 코드를 간결하게 줄일 수 있다. 
  * 보다 구체적인 의미를 부여할 수 있다.
- @Retention
  * 해당 애노테이션 정보를 언제까지 유지할 것인가.
  * Source: 소스 코드까지만 유지. 즉, 컴파일 하면 해당 애노테이션 정보는 사라진다는 이야기.
  * Class: 컴파인 한 .class 파일에도 유지. 즉 런타임 시, 클래스를 메모리로 읽어오면 해당 정보는 사라진다. 
  * Runtime: 클래스를 메모리에 읽어왔을 때까지 유지! 코드에서 이 정보를 바탕으로 특정 로직을 실행할 수 있다. 
- @Target 
  * 해당 애노테이션을 어디에 사용할 수 있는지 결정한다. 
- @Documented 
  * 해당 애노테이션을 사용한 코드의 문서에 그 애노테이션에 대한 정보를 표기할지 결정한다. 
- 메타 애노테이션 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans meta-annotations 
  * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/co re/annotation/AliasFor.html

## HTTP 요청 맵핑하기 7부: 맵핑 연습 문제
- 다음 요청을 처리할 수 있는 핸들러 메소드를 맵핑하는 @RequestMapping (또는 @GetMapping, @PostMapping 등)을 정의하세요.
  * GET /events
  * GET /events/1, GET /events/2, GET /events/3,
  * POST /events CONTENT-TYPE: application/json ACCEPT: application/json 
  * DELETE /events/1, DELETE /events/2, DELETE /events/3,
  * PUT /events/1 CONTENT-TYPE: application/json ACCEPT: application/json, PUT /events/2 CONTENT-TYPE: application/json ACCEPT: application/json, ...

## 핸들러 메소드 1부: 지원하는 메소드 아규먼트와 리턴 타입
- 핸들러 메소드 아규먼트: 주로 요청 그 자체 또는 요청에 들어있는 정보를 받아오는데 사용한다. 

| 핸들러 메소드 아규먼트                                                                         |설명|
|--------------------------------------------------------------------------------------|---|
| WebRequest, NativeWebRequest, ServletRequest(Response), HttpServletRequest(Response) |요청 또는 응답 자체에 접근 가능한 API|
| InputStream, Reader, OutputStream, Writer                                                  |요청 본문을 읽어오거나, 응답 본문을 쓸 때 사용할 수 있는 API|
| PushBuilder                                                                                  |스프링 5, HTTP/2 리소스 푸쉬에 사용|
| HttpMethod                                                                                   |GET, POST, ... 등에 대한 정보|
| Locale TimeZone ZoneId                                                                                  |LocaleResolver가 분석한 요청의 Locale 정보|
| @PathVariable                                                                                   |URI 템플릿 변수 읽을 때 사용|
| @MatrixVariable                                                                                  |URI 경로 중에 키/값 쌍을 읽어 올 때 사용|
| @RequestParam                                                                                   |서블릿 요청 매개변수 값을 선언한 메소드 아규먼트 타입으로 변환해준다. 단순 타입인 경우에 이 애노테이션을 생략할 수 있다.|
| @RequestHeader                                                                                   |요청 헤더 값을 선언한 메소드 아규먼트 타입으로 변환해준다.|

- 참고: 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-arguments

- 핸들러 메소드 리턴: 주로 응답 또는 모델을 랜더링할 뷰에 대한 정보를 제공하는데 사용한다.

| @ResponseBody            |리턴 값을 HttpMessageConverter를 사용해 응답 본문으로 사용한다.|
|--------------------------|---|
| HttpEntity ReponseEntity |응답 본문 뿐 아니라 헤더 정보까지, 전체 응답을 만들 때 사용한다.|
| String                   |ViewResolver를 사용해서 뷰를 찾을 때 사용할 뷰 이름.|
| View                     |암묵적인 모델 정보를 랜더링할 뷰 인스턴스|
| Map, Model               |(RequestToViewNameTranslator를 통해서) 암묵적으로 판단한 뷰 랜더링할 때 사용할 모델 정보|
| @ModelAttribute   |(RequestToViewNameTranslator를 통해서) 암묵적으로 판단한 뷰 랜더링할 때 사용할 모델 정보에 추가한다.이 애노테이션은 생략할 수 있다. |

- 참고: 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-returntypes

## 핸들러 메소드 2부: URI 패턴
- @PathVariable 
  * 요청 URI 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법. 
  * 타입 변환 지원. 
  * (기본)값이 반드시 있어야 한다.
  * Optional 지원. 
- @MatrixVariable 
  * 요청 URI 패턴에서 키/값 쌍의 데이터를 메소드 아규먼트로 받는 방법 
  * 타입 변환 지원.
  * (기본)값이 반드시 있어야 한다. 
  * Optional 지원. 
  * 이 기능은 기본적으로 비활성화 되어 있음. 활성화 하려면 다음과 같이 설정해야 함. 

  ```java
  @Configuration 
  public class WebConfig implements WebMvcConfigurer { 
    @Override 
    public void configurePathMatch(PathMatchConfigurer configurer) { 
      UrlPathHelper urlPathHelper = new UrlPathHelper(); 
      urlPathHelper.setRemoveSemicolonContent(false); 
      configurer.setUrlPathHelper(urlPathHelper); 
      } 
    }
  ```

- 참고: 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-typeconversion 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-matrix-variables

## 핸들러 메소드 3부: @RequestMapping
- @RequestParam 
  * 요청 매개변수에 들어있는 단순 타입 데이터를 메소드 아규먼트로 받아올 수 있다. 
  * 값이 반드시 있어야 한다. 
    * required=false 또는 Optional을 사용해서 부가적인 값으로 설정할 수도 있다. 
  * String이 아닌 값들은 타입 컨버전을 지원한다. 
  * Map<String, String> 또는 MultiValueMap<String, String>에 사용해서 모든 요청 매개변수를 받아 올 수도 있다. 
  * 이 애노테이션은 생략 할 수 잇다. 
- 요청 매개변수란? 
  * 쿼리 매개변수 
  * 폼 데이터 
- 참고: 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestparam