package me.sungbin.demowebmvc;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : SampleApi
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

@RestController
@RequestMapping("/api/events")
public class SampleApi {

    @PostMapping
    public Event createEvent(@Valid @RequestBody Event event, BindingResult bindingResult) {
        // save event
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
        }

        return event;
    }

}
