package me.sungbin.demowebmvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

@Controller
@RequestMapping("/api/events")
public class SampleApi {

    @ExceptionHandler
    public ResponseEntity<String> errorHandler() {
        return ResponseEntity.badRequest().body("can't create event as...");
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, BindingResult bindingResult) {
        // save event
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

}
