package me.sungbin.demobootweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName : me.sungbin.demobootweb
 * fileName : SimpleController
 * author : rovert
 * date : 2022/01/31
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/31       rovert         최초 생성
 */

@Controller
public class SimpleController {

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}
