package myproject.myshop.domain.item;

public enum ItemCategory {
    VEGETABLE("채소", 1),
    FRUIT("과일", 2),
    SEAFOOD("해산", 3),
    MEAT("정육", 4),
    DRINK("음료", 5),
    BAKERY("제과", 6);

    private final String label;
    private final int categoryId;
    private static final ItemCategory[] arr = ItemCategory.values();

    ItemCategory(String label, int categoryId) {
        this.label = label;
        this.categoryId = categoryId;
    }

    public String getLabel() {
        return label;
    }

    public static ItemCategory findLabelById(int id) {
        return arr[id];
    }
}