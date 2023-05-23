package myproject.myshop.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.myshop.domain.item.Item;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Repository
public class ItemH2Repository {

    private final DataSource dataSource;

    private static long sequence = 0L;

    public ItemH2Repository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Item save(Item item) throws SQLException {
        String sql = "insert into item(item_id, name, price, stock_quantity) values(?, ?, ?, ?)";

        item.setId(++sequence);

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, item.getId());
            pstmt.setString(2, item.getName());
            pstmt.setInt(3, item.getPrice());
            pstmt.setInt(4, item.getStockQuantity());

            pstmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            log.error("save: db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public Item findById(Long id) throws SQLException {
        String sql = "select * from item where item_id =  ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong("item_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getInt("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                return item;
            } else {
                //db에 해당 item_id가 없으면 예외 발생
                throw new NoSuchElementException("item not found: id=" + id);
            }
        } catch(SQLException e) {
            log.error("findById: db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public List<Item> findAll() throws SQLException {
        String sql = "select * from item";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Item> items = new ArrayList<>();

        try {
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong("item_id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getInt("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                items.add(item);
            }

            return items;
        } catch(SQLException e) {
            log.error("findAll: db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(Long itemId, Item updateParam) throws SQLException {
        String sql = "update item set name=?, price=?, stock_quantity=? where item_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection(dataSource);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, updateParam.getName());
            pstmt.setInt(2, updateParam.getPrice());
            pstmt.setInt(3, updateParam.getStockQuantity());
            pstmt.setLong(4, itemId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("update: db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    //커넥션 획득
    private Connection getConnection(DataSource dataSource) throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }

    //커넥션 종료
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
