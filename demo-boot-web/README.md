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