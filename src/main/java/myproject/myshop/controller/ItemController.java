package myproject.myshop.controller;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.item.Item;
import myproject.myshop.repository.ItemH2Repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

import static myproject.myshop.repository.ConnectionConst.*;

@Slf4j
@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemH2Repository repository;

    //Hikari 커넥션 풀 사용
    //리포지토리에 dataSource 의존관계 주입
    public ItemController() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new ItemH2Repository(dataSource);
    }

    //상품 목록
    @GetMapping
    public String items(Model model) throws SQLException {
        List<Item> items = repository.findAll();
        model.addAttribute("items", items);
        return "itemView/items";
    }

    //상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) throws SQLException {
        Item item = repository.findById(itemId);
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
        Item savedItem = repository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    //상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) throws SQLException {
        Item item = repository.findById(itemId);
        model.addAttribute("item", item);
        return "itemView/editForm";
    }

    //상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult) throws SQLException {

        //검증 로직
        if(isDuplicate(item) && !item.getName().equals(repository.findById(itemId).getName())) {
            bindingResult.rejectValue("name", "duplicate","이미 존재하는 상품입니다");
        }

        if(bindingResult.hasErrors()) {
            return "itemView/addForm";
        }

        repository.update(itemId, item);
        return "redirect:/items/{itemId}";
    }

    //상품 등록, 수정시 상품 이름 중복 확인 메소드
    private boolean isDuplicate(Item item) throws SQLException {
        List<Item> items = repository.findAll();
        for(Item i : items) {
            if(i.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

//    @PostConstruct
//    public void init() throws SQLException {
//        repository.save(new Item("삼겹살", 20000, 12));
//        repository.save(new Item("사이다", 1500, 30));
//    }
}
