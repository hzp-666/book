package com.softeem.dao.impl;

import com.softeem.bean.Admin;
import com.softeem.bean.User;
import com.softeem.dao.AdminDao;
import com.softeem.util.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl extends BaseDao implements AdminDao {

    public Admin queryAdminByUsernameAndPassword(String username, String password) throws SQLException{
        String sql = "select * from t_admin where username = ? and password =?";
        BeanHandler<Admin> handler = new BeanHandler<>(Admin.class);
        Admin admin = queryRunner.query(sql, handler, username,password);
        return admin;
    }
    @Override
    public User queryUserByUsername(String username) throws SQLException {
        return null;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> findALL() throws SQLException {
        return null;
    }

    @Override
    public void save(Admin admin) throws SQLException {

    }

    @Override
    public void updateById(Admin admin) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public Admin findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }
}
