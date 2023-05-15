package myproject.myshop.domain.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private Long id;

    @NotNull(message = "상품명은 필수입니다")
    private String name;

    @NotNull(message = "가격은 필수입니다")
    private int price;

    @NotNull(message = "수량은 필수입니다")
    private int stockQuantity;

    private Category category;

    public Item() {
    }

    public Item(String name, int price, int stockQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
}
