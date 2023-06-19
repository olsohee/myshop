package myproject.myshop.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionConst;
import myproject.myshop.domain.cart.CartList;
import myproject.myshop.domain.member.Member;
import myproject.myshop.domain.order.Order;
import myproject.myshop.service.CartService;
import myproject.myshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    //장바구니 상품 전체 주문
    @GetMapping("/order")
    public String createOrder(HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Order order = orderService.createOrder(member.getId());
        log.info("getId={}", order.getId());
        log.info("getMember={}", order.getMember());
        log.info("getOrderStatus={}", order.getOrderStatus());
        log.info("getOrderDate={}", order.getOrderDate());
        log.info("getOrderItems={}", order.getOrderItems());
        return "redirect:/";
    }

    //단일 상품 주문
    // ...
}
