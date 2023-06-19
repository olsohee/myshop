package myproject.myshop.domain.order;

public enum OrderStatus {

    ORDER("대기", 1),
    COMPLETE("완료", 2),
    CANCEL("취소", 3);

    private final String label;

    private final int orderStatusId;

    OrderStatus(String label, int orderStatusId) {
        this.label = label;
        this.orderStatusId = orderStatusId;
    }
}
