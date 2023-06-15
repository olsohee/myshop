package myproject.myshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;
import myproject.myshop.domain.item.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    @PersistenceContext
    private final EntityManager em;

    public CartList saveCartList(CartList cartList) {
        em.persist(cartList);
        return cartList;
    }

    public CartItem saveCartItem(CartItem cartItem) {
        em.persist(cartItem);
        return cartItem;
    }

    public CartList findCartList(Long cartListId) {
        return em.find(CartList.class, cartListId);
    }

    public CartItem findCartItem(Long cartItemId) {
        return em.find(CartItem.class, cartItemId);
    }

    public List<CartItem> findAllCartItem() {
        return em.createQuery("select c from CartItem c", CartItem.class).getResultList();
    }
}
