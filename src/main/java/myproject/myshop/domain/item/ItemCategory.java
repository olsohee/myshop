package myproject.myshop.domain.item;

public enum ItemCategory {
    VEGETABLE("채소"),
    FRUIT("과일"),
    SEAFOOD("해산"),
    MEAT("정육"),
    DRINK("음료"),
    BAKERY("제과");

    private final String label;

    ItemCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}