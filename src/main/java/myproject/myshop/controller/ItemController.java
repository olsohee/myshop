package myproject.myshop.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.item.ItemCategory;
import myproject.myshop.domain.member.Member;
import myproject.myshop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

import static myproject.myshop.domain.member.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @ModelAttribute("itemCategories")
    public ItemCategory[] itemCategories() {
        return ItemCategory.values();
    }

    //상품 목록
    @GetMapping
    public String items(Model model) throws SQLException {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        return "itemView/items";
    }

    @GetMapping("/category/{categoryId}")
    public String itemsCategory(@PathVariable int categoryId, Model model) {
        List<Item> items = itemService.findByCategoryId(categoryId);
        model.addAttribute("items", items);

        return "itemView/itemsCategory";
    }

    //상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) throws SQLException {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);

        return "itemView/item";
    }

    //상품 등록 폼
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());

        return "itemView/addForm";
    }

    //상품 등록
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws SQLException {

        //검증 로직
        if(isDuplicate(item)) {
            bindingResult.rejectValue("name", "duplicate","이미 존재하는 상품입니다");
        }

        //검증에 실패하면 다시 입력 폼으로 이동
        if(bindingResult.hasErrors()) {
            return "itemView/addForm";
        }

        //정상 로직(상품 등록)
        Item savedItem = itemService.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";
    }

    //상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) throws SQLException {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);

        return "itemView/editForm";
    }

    //상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute Item item,
                       BindingResult bindingResult, Model model) throws SQLException {

        //검증 로직
        if(isDuplicate(item) && !item.getName().equals(itemService.findById(itemId).getName())) {
            bindingResult.rejectValue("name", "duplicate","이미 존재하는 상품입니다");
        }

        if(bindingResult.hasErrors()) {

            return "itemView/addForm";
        }

        itemService.update(itemId, item);
        return "redirect:/items/{itemId}";
    }

    //상품 등록, 수정시 상품 이름 중복 확인 메소드
    private boolean isDuplicate(Item item) throws SQLException {
        List<Item> items = itemService.findAll();
        for(Item i : items) {
            if(i.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @PostConstruct
    private void init() throws SQLException {
        Item itemA = new Item("삼겹살", 14000, 10, ItemCategory.MEAT);
        Item itemB = new Item("부채살", 18000, 10, ItemCategory.MEAT);
        Item itemC = new Item("주스", 2200, 20, ItemCategory.DRINK);
        Item itemD = new Item("쿠키", 2500, 30, ItemCategory.BAKERY);

        itemService.save(itemA);
        itemService.save(itemB);
        itemService.save(itemC);
        itemService.save(itemD);
    }
}
