package myproject.myshop.controller;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.member.LoginForm;
import myproject.myshop.domain.member.Member;
import myproject.myshop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @GetMapping("/signup")
    public String signupForm(@ModelAttribute Member member) {
        return "memberView/signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "memberView/signupForm";
        }

        memberService.save(member);
        return "redirect:/main";
    }

    //로그인
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "memberView/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult) {
        //아이디, 패스워드가 비어있을 경우 오류(@Validated)
        if(bindingResult.hasErrors()) {
            return "memberView/loginForm";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        //아이디 혹은 패스워트가 틀린 경우 오류
        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀립니다");
            return "memberView/loginForm";
        }

        return "redirect:/main";
    }
}
