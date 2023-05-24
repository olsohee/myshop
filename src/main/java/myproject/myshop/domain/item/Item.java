package myproject.myshop.domain.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @NotBlank(message = "상품명은 필수입니다")
    @Column(name = "name")
    private String name;

    @NotNull(message = "가격은 필수입니다")
    @Column(name = "price")
    private Integer price;

    @NotNull(message = "수량은 필수입니다")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    public Item() {
    }

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
