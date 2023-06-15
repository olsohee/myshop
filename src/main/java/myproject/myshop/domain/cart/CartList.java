package myproject.myshop.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CartList {

    @Id
    @GeneratedValue
    @Column(name = "cart_list_id")
    private Long id;

    @OneToMany(mappedBy = "cartList")
    List<CartItem> cartItems;

    @Column(name = "total_count")
    private int totalCount;

    public CartList(List<CartItem> cartItems, Integer totalCount) {
        this.cartItems = cartItems;
        this.totalCount = totalCount;
    }

    public CartList() {

    }
}
