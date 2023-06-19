package myproject.myshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.SessionConst;
import myproject.myshop.domain.cart.CartPageDto;
import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.member.Member;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;
import myproject.myshop.repository.CartRepository;
import myproject.myshop.service.CartService;
import myproject.myshop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final CartRepository cartRepository;

    //장바구니 추가
    @GetMapping("/cart/{itemId}")
    public String addCart(@PathVariable Long itemId, HttpSession session) throws SQLException {

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER); //멤버
        CartList cartList = member.getCartList(); //멤버의 장바구니
        Item item = itemService.findById(itemId); //저장할 아이템

        //이미 등록된 아이템인 경우
        if (cartService.isExist(cartList, item)) {
            log.info("=====기존 등록된 아이템======");
            cartService.addExistCartItem(item, cartList);

            return "redirect:/cart";
        }

        //새로운 아이템인 경우
        else {
            log.info("=====새로운 아이템 등록======");
            CartItem cartItem = new CartItem(item, 1, cartList);
            cartService.addNewCartItem(cartItem, cartList);

            return "redirect:/cart";
        }
    }

    //장바구니 보여주기
    @GetMapping("/cart")
    public String cartForm(HttpServletRequest request, Model model) {

        //회원(Member)
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //장바구니(CartList)
        CartList cartList = cartRepository.findCartList(member.getCartList().getId());

        //장바구니에 담긴 아이템 리스트
        List<CartItem> cartItems = cartList.getCartItems();

        //장바구니에 담긴 아이템 수량
        int totalCount = cartList.getTotalCount();

        //장바구니에 담긴 아이템 총 가격
        int totalPrice = 0;
        for(int i = 0; i < cartItems.size(); i++) {
            Integer price = cartItems.get(i).getItem().getPrice();
            Integer quantity = cartItems.get(i).getCount();
            totalPrice += price * quantity;
        }

        CartPageDto cartPageDto = new CartPageDto(member, cartItems, totalCount, totalPrice);
        model.addAttribute("cartPageDto", cartPageDto);
        return "cartView/cart";
    }
}
