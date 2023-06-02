package myproject.myshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionManager;
import myproject.myshop.domain.member.LoginForm;
import myproject.myshop.domain.member.Member;
import myproject.myshop.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

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

        Member savedMember = loginService.save(member);

        //아이디 중복으로 회원가입 실패시 다시 회원가입 폼으로
        if(savedMember == null) {
            bindingResult.rejectValue("loginId", "duplicatedId", "이미 존재하는 아이디입니다");
            return "memberView/signupForm";
        }

        //회원가입 성공시 로그인 페이지로
        return "redirect:/login";
    }

    //로그인
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "memberView/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        //아이디, 패스워드가 비어있을 경우 오류(@Validated)
        if(bindingResult.hasErrors()) {
            return "memberView/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        //아이디 혹은 패스워트가 틀린 경우 오류
        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 틀립니다");
            return "memberView/loginForm";
        }

        //로그인 성공 처리: 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
        sessionManager.createSession(loginMember, response);

        return "redirect:/main";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/main";
    }

}
