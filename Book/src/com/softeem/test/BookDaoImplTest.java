package com.softeem.test;

import com.softeem.bean.Book;
import com.softeem.dao.BookDao;
import com.softeem.dao.impl.BookDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class BookDaoImplTest {

    @Test
    public void queryForPageTotalCount() throws SQLException {
        BookDao bookDao = new BookDaoImpl();
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems() throws SQLException {
        BookDao bookDao = new BookDaoImpl();
        List<Book> bookList = bookDao.queryForPageItems(0, 5,null,null,null,null);
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
}