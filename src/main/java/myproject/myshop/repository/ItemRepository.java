package myproject.myshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.item.Item;
import myproject.myshop.domain.item.ItemCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext //스프링부트가 해당 애노테이션을 보고 엔티티 매니저를 주입해준다.
    private final EntityManager em;

    public Item save(Item item) throws SQLException {
        em.persist(item);
        return item;
    }

    public Item findById(Long id) throws SQLException {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() throws SQLException {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public void update(Long itemId, Item updateParam) throws SQLException {
        Item savedItem = em.find(Item.class, itemId);
        savedItem.setName(updateParam.getName());
        savedItem.setCategory(updateParam.getCategory());
        savedItem.setPrice(updateParam.getPrice());
        savedItem.setStockQuantity(updateParam.getStockQuantity());
    }
}
