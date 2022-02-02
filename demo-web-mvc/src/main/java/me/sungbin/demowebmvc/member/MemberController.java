package me.sungbin.demowebmvc.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.PushBuilder;

/**
 * packageName : me.sungbin.demowebmvc.member
 * fileName : MemberController
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/push")
    public String index(Model model, PushBuilder pushBuilder) {
        model.addAttribute("name", "sungbin");
        model.addAttribute("message", "반갑습니다.");

        pushBuilder.path("/webjars/jquery/3.6.0/dist/jquery.js").push();
        pushBuilder.path("/webjars/bootstrap/5.1.3/js/bootstrap.js").push();
        pushBuilder.path("/webjars/bootstrap/5.1.3/css/bootstrap.css").push();

        return "/member/index";
    }

    @GetMapping("/pull")
    public String index(Model model) {
        model.addAttribute("name", "sungbin");
        model.addAttribute("message", "반갑습니다.");

        return "/member/index";
    }
}
