package me.sungbin.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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
    public String createEvent(@Validated Event event, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/events/form";
        }
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        model.addAttribute(eventList);

        return "redirect:/event/list";
    }

    @GetMapping("/event/form")
    public String eventForm(Model model) {
        Event newEvent = new Event();
        newEvent.setLimit(50);
        model.addAttribute("event", newEvent);

        return "events/form";
    }

    @GetMapping("/event/list")
    public String getEvent(Model model) {
        Event event = new Event();
        event.setName("spring");
        event.setLimit(10);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        model.addAttribute(eventList);

        return "/events/list";
    }
}
