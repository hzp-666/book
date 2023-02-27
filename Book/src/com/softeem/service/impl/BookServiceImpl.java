package com.softeem.service.impl;

import com.softeem.bean.Book;
import com.softeem.dao.BookDao;
import com.softeem.dao.impl.BookDaoImpl;
import com.softeem.service.BookService;
import com.softeem.util.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) throws SQLException {
        bookDao.save(book);
    }

    @Override
    public void deleteBook(Integer id) throws SQLException {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        bookDao.updateById(book);
    }

    @Override
    public Book queryBookById(Integer id) throws SQLException {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> queryBooks() throws SQLException {
        List<Book> bookList = bookDao.findALL();
        return bookList;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) throws SQLException {
        //调用public Integer queryForPageTotalCount()
        //public List<Book> queryForPageItems(int begin, int pageSize)
        Page<Book> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize - 1) / pageSize);//设置当前页显示数量
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize));//设置查询分页结果集
        return page;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        //调用public Integer queryForPageTotalCount()
        //public List<Book> queryForPageItems(int begin, int pageSize)
        Page<Book> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount(name, author, min, max);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize - 1) / pageSize);//设置当前页显示数量
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize,name, author, min, max));//设置查询分页结果集
        return page;
    }
}
