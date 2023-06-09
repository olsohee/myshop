package myproject.myshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static myproject.myshop.domain.member.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member, Model model) {

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
        return "main";
    }
}
