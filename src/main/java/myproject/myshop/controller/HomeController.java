package myproject.myshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myproject.myshop.SessionManager;
import myproject.myshop.domain.member.Member;
import myproject.myshop.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String main(HttpServletRequest request, Model model) {

        Member member = (Member)sessionManager.getSession(request);

        //로그인 X 화면
        if(member == null) {
            return "main";
        }

        //로그인 O 화면
        model.addAttribute("member", member);
        return "loginMain";
    }
}
