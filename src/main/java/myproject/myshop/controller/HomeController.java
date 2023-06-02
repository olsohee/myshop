package myproject.myshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionManager;
import myproject.myshop.domain.member.Member;
import myproject.myshop.domain.member.SessionConst;
import myproject.myshop.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String home() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member, Model model) {

        if(member == null) {
            return "main";
        }

        //세션이 유지되면 loginMain으로
        model.addAttribute("member", member);
        return "loginMain";
    }
}
