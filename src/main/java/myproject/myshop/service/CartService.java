package myproject.myshop.service;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;
import myproject.myshop.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    //장바구니에 추가
    public CartList addCart(CartItem cartItem, CartList cartList) {
        //cartItem 저장
        cartRepository.saveCartItem(cartItem);

        //장바구니 가져와서 cartItem 추가
        CartList savedCartList = cartRepository.findCartList(cartList);
        CartItem savedCartItem = cartRepository.findCartItem(cartItem);

        savedCartList.getCartItems().add(savedCartItem);
        savedCartList.setTotalCount(savedCartList.getTotalCount() + 1);

        return cartList;
    }

    //이미 등록된 아이템인지 확임
    public boolean isExist(CartItem cartItem, CartList cartList) {
        List<CartItem> cartItems = cartList.getCartItems();
        for(CartItem c : cartItems) {
            if(c.getItem().getName().equals(cartItem.getItem().getName())) {
                return true;
            }
        }
        return false;
    }

    //기존 장바구니에 새로운 아이템 추가
    public CartItem addNewCartItem(CartItem cartItem) {

        //cartItem 저장
        cartRepository.saveCartItem(cartItem);

        return cartItem;
    }

}
