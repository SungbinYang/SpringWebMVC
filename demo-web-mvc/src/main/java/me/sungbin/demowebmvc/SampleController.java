package me.sungbin.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/event/name/{name}")
    @ResponseBody
    public Event getEvent(@Validated(Event.ValidateLimit.class) Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("===============================");
            bindingResult.getAllErrors().forEach(System.out::println);
        }
        return event;
    }

    @GetMapping("/event/form")
    public String eventForm(Model model) {
        Event newEvent = new Event();
        newEvent.setLimit(50);
        model.addAttribute("event", newEvent);

        return "events/form";
    }
}
