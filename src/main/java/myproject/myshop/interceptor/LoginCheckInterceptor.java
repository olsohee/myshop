package myproject.myshop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    //preHandle은 컨트롤러 호출 전에 호출된다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?requestURL=" + requestURI);
            return false; //반환값이 false이면 다음 인터셉터 혹은 컨트롤러를 실행하지 않는다.
        }

        return true;
    }
}
