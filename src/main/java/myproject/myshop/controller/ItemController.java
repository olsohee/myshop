package myproject.myshop.controller;

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
    public String items(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                        Model model) throws SQLException {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
        return "itemView/items";
    }

    @GetMapping("/category/{categoryId}")
    public String itemsCategory(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                                @PathVariable int categoryId, Model model) {
        List<Item> items = itemService.findByCategoryId(categoryId);
        model.addAttribute("items", items);

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
        return "itemView/itemsCategory";
    }

    //상품 상세
    @GetMapping("/{itemId}")
    public String item(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                       @PathVariable Long itemId, Model model) throws SQLException {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
        return "itemView/item";
    }

    //상품 등록 폼
    @GetMapping("/add")
    public String addForm(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                          Model model) {
        model.addAttribute("item", new Item());

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }
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
    public String editForm(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                           @PathVariable Long itemId, Model model) throws SQLException {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);

        if(member == null) {
            model.addAttribute("isNull", true); //세션 X
        } else {
            model.addAttribute("isNull", false); //세션 O
        }

        return "itemView/editForm";
    }

    //상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                       @PathVariable Long itemId, @Validated @ModelAttribute Item item,
                       BindingResult bindingResult,
                       Model model) throws SQLException {

        //검증 로직
        if(isDuplicate(item) && !item.getName().equals(itemService.findById(itemId).getName())) {
            bindingResult.rejectValue("name", "duplicate","이미 존재하는 상품입니다");
        }

        if(bindingResult.hasErrors()) {

            if(member == null) {
                model.addAttribute("isNull", true); //세션 X
            } else {
                model.addAttribute("isNull", false); //세션 O
            }

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
}
