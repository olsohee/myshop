package myproject.myshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    @PersistenceContext
    private final EntityManager em;

    public void saveCartList(CartList cartList) {
        em.persist(cartList);
    }

    public void saveCartItem(CartItem cartItem) {
        em.persist(cartItem);
    }

    public CartList findCartList(CartList cartList) {
        return em.find(CartList.class, cartList.getId());
    }

    public CartItem findCartItem(CartItem cartItem) {
        return em.find(CartItem.class, cartItem.getId());
    }
}
