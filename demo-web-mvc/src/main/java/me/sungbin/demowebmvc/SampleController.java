package me.sungbin.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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
@SessionAttributes("event")
public class SampleController {

    @GetMapping("/event/form/name")
    public String eventFormName(Model model) {
        model.addAttribute("event", new Event());

        return "/events/form-name";
    }

    @PostMapping("/event/form/name")
    public String eventFormNameSubmit(@Validated Event event, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/events/form-name";
        }
        return "redirect:/event/form/limit";
    }

    @GetMapping("/event/form/limit")
    public String eventFormLimit(Event event, Model model) {
        model.addAttribute("event", event);

        return "/events/form-limit";
    }

    @PostMapping("/event/form/limit")
    public String eventFormLimitSubmit(@Validated Event event, BindingResult bindingResult, Model model, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            return "/events/form-limit";
        }

        status.setComplete();

        return "redirect:/event/list";
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
