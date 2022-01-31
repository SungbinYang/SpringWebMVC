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