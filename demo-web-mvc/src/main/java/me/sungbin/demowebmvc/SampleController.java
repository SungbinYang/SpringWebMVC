package me.sungbin.demowebmvc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : SampleController
 * author : rovert
 * date : 2022/01/31
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/31       rovert         최초 생성
 */

@Controller
public class SampleController {

    @GetMapping(value = "/hello", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
