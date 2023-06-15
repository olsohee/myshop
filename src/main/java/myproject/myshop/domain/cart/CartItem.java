package myproject.myshop.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.member.Member;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "cart_list_id")
    private CartList cartList;

    @Column(name = "count")
    private Integer count;

    public CartItem(Item item, Integer count, CartList cartList) {
        this.item = item;
        this.count = count;
        this.cartList = cartList;
    }

    public CartItem() {

    }
}
