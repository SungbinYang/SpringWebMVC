package me.sungbin.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/hello")
public class SampleController {

    @RequestMapping("/sungbin")
    @ResponseBody
    public String helloSungbin() {
        return "hello sungbin";
    }
}
