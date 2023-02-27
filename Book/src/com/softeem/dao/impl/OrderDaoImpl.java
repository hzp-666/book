package com.softeem.dao.impl;

import com.softeem.bean.Order;
import com.softeem.bean.User;
import com.softeem.dao.OrderDao;
import com.softeem.util.BaseDao;
import com.softeem.util.JdbcUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public User queryUserByUsername(String username) throws SQLException {
        return null;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Order> findALL() throws SQLException {
        return null;
    }

    @Override
    public void save(Order order) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        queryRunner.update(connection,sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public void updateById(Order order) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public Order findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Order> page(Integer pageNumber) throws SQLException {
        String sql = "select * from t_order order by create_time desc limit ?,?";
        List<Order> orderList = queryRunner.query(sql, new BeanListHandler<>(Order.class, getProcessor()), (pageNumber - 1) * pageSize, pageSize);
        return orderList;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_order";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }
}
