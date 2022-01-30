package me.sungbin.demobootweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName : me.sungbin.demobootweb
 * fileName : SampleController
 * author : rovert
 * date : 2022/01/30
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/30       rovert         최초 생성
 */

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String hello(@RequestParam("name") Person person) {
        return "hello " + person.getName();
    }
}
