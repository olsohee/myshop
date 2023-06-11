package myproject.myshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.argumentResolver.Login;
import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.member.Member;
import myproject.myshop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(@Login Member member, Model model) throws SQLException {

        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        if(member == null) {
            return "redirect:/login";
        }

        return "main";
    }
}
