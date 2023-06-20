package myproject.myshop.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myproject.myshop.domain.item.Item;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    private int orderPrice;

    private int count;

    public OrderItem() {
    }

    public OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
