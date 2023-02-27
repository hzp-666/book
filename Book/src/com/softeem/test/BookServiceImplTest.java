package com.softeem.test;

import com.softeem.bean.Book;
import com.softeem.service.BookService;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.util.Page;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImplTest {

    @Test
    public void addBook() {
    }

    @Test
    public void deleteBook() {
    }

    @Test
    public void updateBook() {
    }

    @Test
    public void queryBookById() {
        BookService bookService = new BookServiceImpl();
        try {
            System.out.println("bookService.queryBookById(1) = " + bookService.queryBookById(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void queryBooks() {
        BookService bookService = new BookServiceImpl();
        try {
            List<Book> bookList = bookService.queryBooks();
            for (Book book : bookList) {
                System.out.println(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void page() throws SQLException{
        BookService bookService = new BookServiceImpl();
        Page<Book> page = bookService.page(15, 4);
        for (Book item : page.getItems()) {
            System.out.println(item);
        }
    }

}