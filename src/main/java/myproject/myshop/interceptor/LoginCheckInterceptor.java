package myproject.myshop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override //preHandle은 컨트롤러 호출 전에 호출된다.
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            //미인증 사용자는 로그인으로 리다이렉트
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false; //다음 인터셉터 또는 컨트롤러를 호출하지 않고 종료
        }

        return true;
    }
}
