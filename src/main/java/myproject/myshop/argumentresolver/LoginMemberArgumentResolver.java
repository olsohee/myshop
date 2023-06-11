package myproject.myshop.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionConst;
import myproject.myshop.domain.member.Member;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    //파라미터에 @Login 애노테이션이 있고, Member 타입이면 해당 ArgumentResolver가 사용된다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        log.info("supportsParameter 실행");

        //파라미터에 @Login 애노테이션이 있는지 확인
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        //파라미터가 Member 타입인지 확인
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    //컨트롤러 호출 직전에 호출되어서 필요한 파라미터 정보(member 객체)를 생성해준다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if(session == null) {
            return null;
        }
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
