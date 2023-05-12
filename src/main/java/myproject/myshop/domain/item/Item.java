package myproject.myshop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private Long id;
    private String name;
    private int price;
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
