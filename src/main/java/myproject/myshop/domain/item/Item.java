package myproject.myshop.domain.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import myproject.myshop.domain.order.OrderItem;

import java.util.List;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item")
    List<OrderItem> orderItems;

    @NotBlank(message = "상품명은 필수입니다")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "가격은 필수입니다")
    @Column(nullable = false)
    private Integer price;

    @NotNull(message = "수량은 필수입니다")
    @Column(nullable = false)
    private Integer stockQuantity;

    @NotNull(message = "카테고리는 필수입니다")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    public Item() {
    }

    public Item(String name, int price, int stockQuantity, ItemCategory category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
}
