package myproject.myshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.member.LoginForm;
import myproject.myshop.domain.member.Member;
import myproject.myshop.domain.member.SessionConst;
import myproject.myshop.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static myproject.myshop.domain.member.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 회원가입
     */
    @GetMapping("/signup")
    public String signupForm(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                             @ModelAttribute Member signupMember, Model model) {

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }

        return "memberView/signupForm";
    }

    @PostMapping("/signup")
    public String signup(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                         @Validated @ModelAttribute Member signupMember, BindingResult bindingResult,
                         Model model) {

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
        if(bindingResult.hasErrors()) {
            return "memberView/signupForm";
        }

        Member savedMember = loginService.save(signupMember);

        //아이디 중복으로 회원가입 실패시 다시 회원가입 폼으로
        if(savedMember == null) {
            bindingResult.rejectValue("loginId", "duplicatedId", "이미 존재하는 아이디입니다");
            return "memberView/signupForm";
        }

        //회원가입 성공시 로그인 페이지로
        return "redirect:/login";
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginForm(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member
                            ,@ModelAttribute LoginForm loginForm,
                            Model model) {
        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
        return "memberView/loginForm";
    }

    @PostMapping("/login")
    public String login(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                        @Validated @ModelAttribute LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/main") String redirectURL, HttpServletRequest request,
                        Model model) {

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }

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

        //로그인 성공 처리
        HttpSession session = request.getSession(); //세션 생성
        session.setAttribute(LOGIN_MEMBER, loginMember); //세션에 로그인 회원 보관

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/main";
    }

}
