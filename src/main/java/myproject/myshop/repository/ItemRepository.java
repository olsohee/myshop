package myproject.myshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.item.Item;
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
    private EntityManager em;

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

        em.createQuery("update Item i " +
                        "set i.name = :name, i.price = :price, i.stockQuantity = :stockQuantity " +
                        "where i.id = :itemId")
                .setParameter("name", updateParam.getName())
                .setParameter("price", updateParam.getPrice())
                .setParameter("stockQuantity", updateParam.getStockQuantity())
                .setParameter("itemId", itemId);
    }
}
