package com.softeem.service.impl;

import com.softeem.bean.Admin;
import com.softeem.dao.AdminDao;
import com.softeem.dao.impl.AdminDaoImpl;
import com.softeem.service.AdminService;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {
    @Override
    public Admin login(Admin admin) throws SQLException {
        AdminDao adminDao = new AdminDaoImpl();
        return adminDao.queryAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
    }
}
