package myproject.myshop;

import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.item.ItemCategory;
import org.junit.jupiter.api.Test;

public class ItemCategoryTest {

    @Test
    public void itemSave() {
        Item item = new Item("삼겹살", 1000, 20, null);
        System.out.println(item.getCategory());
        System.out.println(item.getCategory().getLabel());
        System.out.println(item.getCategory().name());
    }
}
