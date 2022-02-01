package me.sungbin.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/event")
    @ResponseBody
    public Event getEvent(String name, Integer limit) {
        Event event = new Event();
        event.setName(name);
        event.setLimit(limit);

        return event;
    }
}
