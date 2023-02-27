package com.softeem.dao.impl;

import com.softeem.bean.User;
import com.softeem.dao.UserDao;
import com.softeem.util.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public User queryUserByUsername(String username) throws SQLException{
        String sql = "select * from t_user where username = ?";
        BeanHandler<User> handler = new BeanHandler<>(User.class);
        User user = queryRunner.query(sql, handler, username);
        return user;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException{
        String sql = "select * from t_user where username = ? and password =?";
        BeanHandler<User> handler = new BeanHandler<>(User.class);
        User user = queryRunner.query(sql, handler, username,password);
        return user;
    }

    @Override
    public List<User> findALL() throws SQLException {
        String sql = "select * from t_user";
        BeanListHandler<User> handler = new BeanListHandler<>(User.class);
        List<User> userList = queryRunner.query(sql, handler);
        return userList;
    }

    @Override
    public void save(User user) throws SQLException {
        String sql = "insert into t_user values (null,?,?,?)";
        Long id = queryRunner.insert(sql, new ScalarHandler<Long>(), user.getUsername(), user.getPassword(), user.getEmail());
        user.setId(id.intValue());
    }

    @Override
    public void updateById(User user) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public User findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<User> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }
}
