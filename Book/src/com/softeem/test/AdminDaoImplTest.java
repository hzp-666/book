package com.softeem.test;

import com.softeem.bean.Admin;
import com.softeem.dao.AdminDao;
import com.softeem.dao.impl.AdminDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AdminDaoImplTest {

    @Test
    public void queryAdminByUsernameAndPassword() throws SQLException {
        AdminDao adminDao = new AdminDaoImpl();
        Admin admin = adminDao.queryAdminByUsernameAndPassword("admin", "123456");
        System.out.println(admin);
    }
}