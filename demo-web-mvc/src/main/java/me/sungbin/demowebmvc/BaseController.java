package me.sungbin.demowebmvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : BaseController
 * author : rovert
 * date : 2022/02/03
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/03       rovert         최초 생성
 */

@ControllerAdvice(assignableTypes = {EventController.class, SampleApi.class})
public class BaseController {

    @ExceptionHandler({EventException.class, RuntimeException.class})
    public String eventErrorHandler(RuntimeException exception, Model model) {
        model.addAttribute("message", "runtime Error");

        return "error";
    }

    @InitBinder("event")
    public void initEventBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id"); // 받고싶지 않는 필드값을 걸러낼수 있다.
        webDataBinder.addValidators(new EventValidator()); // 커스텀한 validation을 할수 있다.
    }

    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
    }
}
