package myproject.myshop.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myproject.myshop.domain.member.Member;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    Member member;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;

    Date orderDate;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

}
