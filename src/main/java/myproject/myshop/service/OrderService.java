package myproject.myshop.service;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;
import myproject.myshop.domain.member.Member;
import myproject.myshop.domain.order.Order;
import myproject.myshop.domain.order.OrderItem;
import myproject.myshop.domain.order.OrderStatus;
import myproject.myshop.repository.MemberRepository;
import myproject.myshop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartService cartService;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    public Order createOrder(Long memberId) {

        //회원
        Member member = memberRepository.findById(memberId);

        //orderItem(cartList를 통해 orderItem 생성)
        CartList cartList = cartService.findCartListByMemberId(memberId);
        List<CartItem> cartItems = cartList.getCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cartItems) {
            orderItems.add(new OrderItem(cartItem.getItem(), cartItem.getItem().getPrice() * cartItem.getCount(), cartItem.getCount()));
        }
        createOrderItem(orderItems);

        //주문 날짜
        Date orderDate = new Date();

        //주문 상태
        OrderStatus orderStatus = OrderStatus.COMPLETE;

        //주문 가격
        int totalPrice = 0;
        for(CartItem cartItem : cartItems) {
            totalPrice += cartItem.getCount() * cartItem.getItem().getPrice();
        }

        //주문 생성
        Order order = new Order(member, orderItems, orderDate, orderStatus, totalPrice);

        orderRepository.saveOrder(order);
        updateOrderItem(orderItems, order);

        return order;
    }

    public void createOrderItem(List<OrderItem> orderItems) {

        for(OrderItem orderItem : orderItems) {
            orderRepository.saveOrderItem(orderItem);
        }
    }

    public void updateOrderItem(List<OrderItem> orderItems, Order order) {

        for(OrderItem orderItem : orderItems) {
            orderRepository.update(orderItem, order);
        }
    }
}
