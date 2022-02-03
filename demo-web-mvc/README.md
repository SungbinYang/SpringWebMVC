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

## 핸들러 메소드 4부: 폼 서브밋 (타임리프)
- 폼을 보여줄 요청 처리
  * GET /events/form 
  * 뷰: events/form.html
  * 모델: “event”, new Event() 
- 타임리프 
  * @{}: URL 표현식
  * ${}: variable 표현식
  * *{}: selection 표현식
- 참고
  * https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html
  * https://www.getpostman.com/downloads/

## 핸들러 메소드 5부: @ModelAttribute
- @ModelAttribute 
  * 여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객체를 새로 만들 때 사용할 수 있다.
  * 여러 곳? URI 패스, 요청 매개변수, 세션 등
  * 생략 가능
- 값을 바인딩 할 수 없는 경우에는? 
  * BindException 발생 400 에러 
- 바인딩 에러를 직접 다루고 싶은 경우
  * BindingResult 타입의 아규먼트를 바로 오른쪽에 추가한다. 
- 바인딩 이후에 검증 작업을 추가로 하고 싶은 경우
  * @Valid 또는 @Validated 애노테이션을 사용한다. 
- 참고 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args

## 핸들러 메소드 6부: @Validated
- 스프링 MVC 핸들러 메소드 아규먼트에 사용할 수 있으며 validation group이라는 힌트를 사용할 수 있다. 
- @Valid 애노테이션에는 그룹을 지정할 방법이 없다. 
- @Validated는 스프링이 제공하는 애노테이션으로 그룹 클래스를 설정할 수 있다.

## 핸들러 메소드 7부: 폼 서브밋 (에러 처리) 
- 바인딩 에러 발생 시 Model에 담기는 정보 
  * Event
  * BindingResult.event 
- 타임리프 사용시 바인딩 에러 보여주기
  * https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#field-errors

  ```html
  <p th:if="${#fields.hasErrors('limit')}" th:errors="*{limit}">Incorrect date</p>
  ```

- Post / Redirect / Get 패턴
  * https://en.wikipedia.org/wiki/Post/Redirect/Get 
  * Post 이후에 브라우저를 리프래시 하더라도 폼 서브밋이 발생하지 않도록 하는 패턴
- 타임리프 목록 보여주기 
  * https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#listing-seed-starter-data

  ```html
  <a th:href="@{/events/form}">Create New Event</a> 
  <div th:unless="${#lists.isEmpty(eventList)}"> 
    <ul th:each="event: ${eventList}"> 
        <p th:text="${event.Name}">Event Name</p> 
    </ul> 
  </div>
  ```

## 핸들러 메소드 8부: @SessionAttributes 
- 모델 정보를 HTTP 세션에 저장해주는 애노테이션
  * HttpSession을 직접 사용할 수도 있지만 
  * 이 애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어준다. 
  * @ModelAttribute는 세션에 있는 데이터도 바인딩한다.
  * 여러 화면(또는 요청)에서 사용해야 하는 객체를 공유할 때 사용한다. 
- SessionStatus를 사용해서 세션 처리 완료를 알려줄 수 있다.
  * 폼 처리 끝나고 세션을 비울 때 사용한다.

## 핸들러 메소드 9부: 멀티 폼 서브밋
- 세션을 사용해서 여러 폼에 걸쳐 데이터를 나눠 입력 받고 저장하기 
  * 이벤트 이름 입력받고 
  * 이벤트 제한 인원 입력받고
  * 서브밋 -> 이벤트 목록으로! 
- 완료된 경우에 세션에서 모델 객체 제거하기 
  * SessionStatus

## 핸들러 메소드 10부: @SessionAttribute
- HTTP 세션에 들어있는 값 참조할 때 사용
  * HttpSession을 사용할 때 비해 타입 컨버전을 자동으로 지원하기 때문에 조금 편리함. 
  * HTTP 세션에 데이터를 넣고 빼고 싶은 경우에는 HttpSession을 사용할 것. 
- @SessionAttributes와는 다르다. 
  * @SessionAttributes는 해당 컨트롤러 내에서만 동작.
    * 즉, 해당 컨트롤러 안에서 다루는 특정 모델 객체를 세션에 넣고 공유할 때 사용.
  * @SessionAttribute는 컨트롤러 밖(인터셉터 또는 필터 등)에서 만들어 준 세션 데이터에 접근할 때 사용한다.

## 핸들러 메소드 11부: RedirectAttributes
- 리다이렉트 할 때 기본적으로 Model에 들어있는 primitive type 데이터는 URI 쿼리 매개변수에 추가된다. 
  * 스프링 부트에서는 이 기능이 기본적으로 비활성화 되어 있다. 
  * Ignore-default-model-on-redirect 프로퍼티를 사용해서 활성화 할 수 있다. 
- 원하는 값만 리다이렉트 할 때 전달하고 싶다면 RedirectAttributes에 명시적으로 추가할 수 있다.
- 리다이렉트 요청을 처리하는 곳에서 쿼리 매개변수를 @RequestParam 또는 @ModelAttribute로 받을 수 있다.

## 핸들러 메소드 12부: Flash Attributes
- 주로 리다이렉트시에 데이터를 전달할 때 사용한다.
  * 데이터가 URI에 노출되지 않는다. 
  * 임의의 객체를 저장할 수 있다. 
  * 보통 HTTP 세션을 사용한다.
- 리다이렉트 하기 전에 데이터를 HTTP 세션에 저장하고 리다이렉트 요청을 처리 한 다음 그 즉시 제거한다. RedirectAttributes를 통해 사용할 수 있다.
- XPath 
  * https://www.w3schools.com/xml/xpath_syntax.asp
  * https://www.freeformatter.com/xpath-tester.html#ad-output

## 핸들러 메소드 13부: MultipartFile
- MultipartFile 
  * 파일 업로드시 사용하는 메소드 아규먼트 
  * MultipartResolver 빈이 설정 되어 있어야 사용할 수 있다. (스프링 부트 자동 설정이 해 줌)
  * POST multipart/form-data 요청에 들어있는 파일을 참조할 수 있다. 
  * List<MultipartFile> 아큐먼트로 여러 파일을 참조할 수도 있다.
- 파일 업로드 폼 

  ```html
  <form method="POST" enctype="multipart/form-data" action="#" th:action="@{/file}"> File: <input type="file" name="file"/> 
    <input type="submit" value="Upload"/> 
  </form>
  ```

- 파일 업로드 처리 핸들러 

  ```java
  @PostMapping("/file") 
  public String uploadFile(@RequestParam MultipartFile file, 
  RedirectAttributes attributes) { 
    String message = file.getOriginalFilename() + " is uploaded."; 
    System.out.println(message); 
    attributes.addFlashAttribute("message", message); 
    return "redirect:/events/list"; 
  }
  ```
  
- 메시지 출력 

  ```html
  <div th:if="${message}"> 
      <h2 th:text="${message}"/> 
  </div>
  ```

- 파일 업로드 관련 스프링 부트 설정 
  * MultipartAutoConfiguration 
  * MultipartProperties 
- 참고 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-multipart-forms
  * https://spring.io/guides/gs/uploading-files/

## 핸들러 메소드 14부: ResponseEntity
- 파일 리소스를 읽어오는 방법
  * 스프링 ResourceLoader 사용하기
- 파일 다운로드 응답 헤더에 설정할 내용 
  * Content-Disposition: 사용자가 해당 파일을 받을 때 사용할 파일 이름
  * Content-Type: 어떤 파일인가 
  * Content-Length: 얼마나 큰 파일인가
- 파일의 종류(미디어 타입) 알아내는 방법
  * http://tika.apache.org/ 
- ResponseEntity 
  * 응답 상태 코드
  * 응답 헤더
  * 응답 본문

  ```java
      @GetMapping("/file/{filename}")
      public ResponseEntity<Resource> fileDownload(@PathVariable String filename) throws IOException {
          Resource resource = resourceLoader.getResource("classpath:" + filename);
          File file = resource.getFile();
          Tika tika = new Tika();
          String mediaType = tika.detect(file);
  
          return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + resource.getFilename() + "\"")
                  .header(HttpHeaders.CONTENT_TYPE, mediaType)
                  .header(HttpHeaders.CONTENT_LENGTH, file.length() + "")
                  .body(resource);
      }
  ```
  
- 참고 
  * https://spring.io/guides/gs/uploading-files/ 
  * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition
  * https://www.baeldung.com/java-file-mime-type

## 핸들러 메소드 15부: @RequestBody & HttpEntity
- @RequestBody
  * 요청 본문(body)에 들어있는 데이터를 HttpMessageConveter를 통해 변환한 객체로 받아올 수 있다. 
  * @Valid 또는 @Validated를 사용해서 값을 검증 할 수 있다.
  * BindingResult 아규먼트를 사용해 코드로 바인딩 또는 검증 에러를 확인할 수 있다. 
- HttpMessageConverter 
  * 스프링 MVC 설정 (WebMvcConfigurer)에서 설정할 수 있다.
  * configureMessageConverters: 기본 메시지 컨버터 대체 (비추!!)
  * extendMessageConverters: 메시지 컨버터에 추가 
  * 기본 컨버터 
    * WebMvcConfigurationSupport.addDefaultHttpMessageConverters 
- HttpEntity 
  * @RequestBody와 비슷하지만 추가적으로 요청 헤더 정보를 사용할 수 있다. 
- 참고 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestbody
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-httpentity

## 핸들러 메소드 16부: @ResponseBody & ResponseEntity
- @ResponseBody 
  * 데이터를 HttpMessageConverter를 사용해 응답 본문 메시지로 보낼 때 사용한다. 
  * @RestController 사용시 자동으로 모든 핸들러 메소드에 적용 된다. 
- ResponseEntity 
  * 응답 헤더 상태 코드 본문을 직접 다루고 싶은 경우에 사용한다.
- 참고 
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-responsebody
  * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-responseentity

## 핸들러 메소드 17부: @JsonView
- 도메인 또는 DTO 클래스를 이용해서 다양한 JSON 뷰를 만들 수 있는 기능
- 뷰 정의

  ```java
  public class BookJsonView {
  
      interface Simple {}
  
      interface Complex extends Simple {}
  }
  ```

- @JsonView 사용

  ```java
  @Getter @Setter
  public class Book {
  
      @JsonView(BookJsonView.Simple.class)
      private Long id;
  
      @JsonView(BookJsonView.Simple.class)
      private String isbn;
  
      @JsonView(BookJsonView.Complex.class)
      private Date published;
  
      @JsonView(BookJsonView.Complex.class)
      private Set<Author> authors = new HashSet<>();
  
      @JsonView(BookJsonView.Simple.class)
      private String title;
  
      public void addAuthor(Author author) {
          this.authors.add(author);
      }
  }
  ```

- 전제 조건
> Mapper.DEFAULT_VIEW_INCLUSION 이 기능 Disabled (스프링부트 기본설정)
> 그래야 @JsonView 애노테이션 안 붙인 프로퍼티는 제외시켜준다.

## 핸들러 메소드 18부: 정리

## 모델: @ModelAttribute 또 다른 사용법
- @ModelAttribute의 다른 용법 
  * @RequestMapping을 사용한 핸들러 메소드의 아규먼트에 사용하기
  * @Controller 또는 @ControllerAdvice 를 사용한 클래스에서 모델 정보를 초기화 할 때 사용한다.
  * @RequestMapping과 같이 사용하면 해당 메소드에서 리턴하는 객체를 모델에 넣어 준다. 
    * RequestToViewNameTranslator
  
```java
@ModelAttribute 
public void subjects(Model model) { 
  model.addAttribute("subjects", List.of("study", "seminar", "hobby", "social")); 
}

```