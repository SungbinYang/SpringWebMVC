package me.sungbin.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : FileController
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

@Controller
public class FileController {

    @GetMapping("/file")
    public String fileUpload(Model model) {
        return "files/index";
    }

    @PostMapping("/file")
    public String fileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        // save
        System.out.println("file name: " + file.getName());
        System.out.println("file original name: " + file.getOriginalFilename());
        String message = file.getOriginalFilename() + " is uploaded";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/file";
    }
}
