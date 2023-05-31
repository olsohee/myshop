package myproject.myshop.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/")
    public String home() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        //로그인 X 화면
        if(memberId == null) {
            return "main";
        }

        //로그인 O 화면
        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null) {
            return "main";
        }
        model.addAttribute("member", loginMember);
        return "loginMain";
    }
}
