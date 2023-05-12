package myproject.myshop.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.item.Category;
import myproject.myshop.domain.item.Item;
import myproject.myshop.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    //상품 목록
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    //상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    //상품 등록 폼
    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    //상품 등록
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    //상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "editForm";
    }

    //상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("삼겹살", 20000, 12, Category.MEAT));
        itemRepository.save(new Item("사이다", 1500, 30, Category.DRINK));
    }
}
