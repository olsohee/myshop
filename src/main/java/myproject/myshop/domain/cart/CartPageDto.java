package myproject.myshop.domain.cart;

import lombok.Getter;
import lombok.Setter;
import myproject.myshop.domain.member.Member;

import java.util.List;

@Getter
@Setter
public class CartPageDto {

    //회원
    private Member member;

    //장바구니에 담긴 아이템 리스트
    private List<CartItem> itemList;

    //총 갯수
    private int totalCount;

    //총 금액
    private int totalPrice;

    public CartPageDto(Member member, List<CartItem> itemList, int totalCount, int totalPrice) {
        this.member = member;
        this.itemList = itemList;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
    }
}
