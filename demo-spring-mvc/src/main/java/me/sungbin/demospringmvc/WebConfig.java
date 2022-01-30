package me.sungbin.demospringmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName : me.sungbin.demospringmvc
 * fileName : WebConfig
 * author : rovert
 * date : 2022/01/30
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/30       rovert         최초 생성
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }
}
