package myproject.myshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.order.Order;
import myproject.myshop.domain.order.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    @PersistenceContext
    private final EntityManager em;

    //주문 저장
    public Order saveOrder(Order order) {
        em.persist(order);
        return order;
    }


    public OrderItem saveOrderItem(OrderItem orderItem) {
        em.persist(orderItem);
        return orderItem;
    }

    public void update(OrderItem orderItem, Order order) {
        orderItem.setOrder(order);
    }
}
