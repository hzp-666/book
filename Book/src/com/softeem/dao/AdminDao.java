package com.softeem.dao;

import com.softeem.bean.Admin;
import com.softeem.util.BaseInterface;

import java.sql.SQLException;

public interface AdminDao extends BaseInterface<Admin> {
    public Admin queryAdminByUsernameAndPassword(String username, String password) throws SQLException;
}
