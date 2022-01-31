package me.sungbin.demowebmvc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : PracticeController
 * author : rovert
 * date : 2022/02/01
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/01       rovert         최초 생성
 */

@Controller
public class EventController {

    @GetMapping("/events")
    @ResponseBody
    public String event1() {
        return "event";
    }

    @GetMapping("/events/{id:[0-9]+}")
    @ResponseBody
    public String event2(@PathVariable Long id) {
        return "event";
    }

    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String event3() {
        return "event";
    }

    @DeleteMapping("/events/{id:[0-9]+}")
    @ResponseBody
    public String event4(@PathVariable Long id) {
        return "event";
    }

    @PutMapping(value = "/events/{id:[0-9]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String event5(@PathVariable Long id) {
        return "event";
    }
}
