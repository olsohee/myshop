package myproject.myshop.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import myproject.myshop.domain.cart.CartItem;
import myproject.myshop.domain.cart.CartList;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "loginId")
    private String loginId;

    @NotBlank
    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "cart_list_id")
    CartList cartList;

    public Member() {
    }

    public Member(String name, String loginId, String password, CartList cartList) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.cartList = cartList;
    }
}
