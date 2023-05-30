package myproject.myshop.service;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.item.ItemCategory;
import myproject.myshop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) throws SQLException {
        return itemRepository.save(item);
    }

    public Item findById(Long id) throws SQLException {
        return itemRepository.findById(id);
    }

    public List<Item> findAll() throws SQLException {
        return itemRepository.findAll();
    }

    public void update(Long itemId, Item updateParam) throws SQLException {
        itemRepository.update(itemId, updateParam);
    }
}
