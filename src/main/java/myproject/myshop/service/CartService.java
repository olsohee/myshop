package myproject.myshop.service;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;
import myproject.myshop.domain.item.Item;
import myproject.myshop.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    //장바구니에 추가
    public CartList addNewCartItem(CartItem cartItem, CartList cartList) {
        //cartItem 저장
        cartRepository.saveCartItem(cartItem);

        //장바구니 가져와서 cartItem 추가
        CartList savedCartList = cartRepository.findCartList(cartList.getId());
        CartItem savedCartItem = cartRepository.findCartItem(cartItem.getId());

        savedCartList.getCartItems().add(savedCartItem);
        savedCartList.setTotalCount(savedCartList.getTotalCount() + 1);

        return cartList;
    }

    public CartList addExistCartItem(Item item, CartList cartList) {

        //cartItem 값 증가
        CartItem savedCartItem = findCartItemByItem(cartList, item);
        savedCartItem.setCount(savedCartItem.getCount() + 1);

        //cartList 값 증
        CartList savedCartList = cartRepository.findCartList(cartList.getId());
        savedCartList.setTotalCount(savedCartList.getTotalCount() + 1);

        return savedCartList;
    }
    public boolean isExist(CartList cartList, Item item) {
        CartList savedCartList = cartRepository.findCartList(cartList.getId());
        for (CartItem cartItem : savedCartList.getCartItems()) {
            if (cartItem.getItem().getName()
                    .equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    //장바구니 아이템들 중 특정 아이템 찾기
    public CartItem findCartItemByItem(CartList cartList, Item item) {
        CartList savedCartList = cartRepository.findCartList(cartList.getId());
        List<CartItem> cartItems = savedCartList.getCartItems();
        for(CartItem c : cartItems) {
            if(c.getItem().getName().equals(item.getName())) {
                return c;
            }
        }
        return null;
    }
}
