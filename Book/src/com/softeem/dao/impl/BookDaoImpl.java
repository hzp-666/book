package com.softeem.dao.impl;

import com.softeem.bean.Book;
import com.softeem.bean.User;
import com.softeem.dao.BookDao;
import com.softeem.util.BaseDao;
import com.softeem.util.JdbcUtils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {
    @Override
    public User queryUserByUsername(String username) throws SQLException {
        return null;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Book> findALL() throws SQLException {
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class,getProcessor());
        List<Book> list = queryRunner.query("select * from t_book order by id desc", handler);
        return list;
    }

    @Override
    public void save(Book book) throws SQLException {
        String sql = "insert into t_book values (null,?,?,?,?,?,?)";
        queryRunner.update(sql,book.getName(),book.getPrice(),book.getAuthor(),book.getSales(),book.getStock(),book.getImgPath());

    }

    @Override
    public void updateById(Book book) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "update t_book set name=?, price=?, author=?, sales=?, stock=?, img_path=? where id = ?";
        queryRunner.update(connection,sql,book.getName(),book.getPrice(),book.getAuthor(),book.getSales(),book.getStock(),book.getImgPath(),book.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "delete from t_book where id = ?";
        queryRunner.update(sql,id);
    }

    @Override
    public Book findById(Integer id) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "select * from t_book where id = ?";
        BeanHandler<Book> handler = new BeanHandler<>(Book.class,getProcessor());
        Book book = queryRunner.query(connection,sql, handler, id);
        return book;
    }

    @Override
    public List<Book> page(Integer pageNumber) throws SQLException {
        String sql = "select * from t_book limit ?,?";
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class);
        List<Book> list = queryRunner.query(sql, handler, (pageNumber - 1) * pageSize, pageSize);
        return list;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }

    @Override
    public Integer queryForPageTotalCount() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();

    }

    /**
     *
     * @param begin 起始值
     * @param pageSize 每次查询几条数据
     * @return
     * @throws SQLException
     */
    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) throws SQLException {
        String sql = "select * from t_book order by id desc limit ?,?";
        List<Book> bookList = queryRunner.query(sql, new BeanListHandler<>(Book.class, getProcessor()), begin, pageSize);
        return bookList;
    }

    @Override
    public Integer queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        StringBuilder sql =new StringBuilder("select count(*) from t_book where 1 = 1 ");
        ArrayList<Object> list = new ArrayList<>();

        if(name!=null&&!"".equals(name)) {
            sql.append(" and name like ? ");
            list.add("%"+name+"%");
        }
        if(author!=null&&!"".equals(author)) {
            sql.append(" and author like ? ");
            list.add("%"+author+"%");
        }
        if((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min值大于max值
            if(min.compareTo(max)==1) { //进行两值交换
                BigDecimal temp = min ;
                min = max;
                max = temp;
            }
            sql.append(" and price between ? and ? ");
            list.add(min);
            list.add(max);
        } else if (min != null && min.signum() == 1) {
            sql.append(" and price > ? ");
            list.add(min);
        } else if (max != null && max.signum() == 1) {
            sql.append(" and price < ? ");
            list.add(max);
        }
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql.toString(), handler, list.toArray());
        return i.intValue();

    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        StringBuilder sql = new StringBuilder( "select * from t_book  where 1 = 1 ");
        ArrayList<Object> list = new ArrayList<>();

        if(name!=null&&!"".equals(name)) {
            sql.append(" and name like ? ");
            list.add("%"+name+"%");
        }
        if(author!=null&&!"".equals(author)) {
            sql.append(" and author like ? ");
            list.add("%"+author+"%");
        }
        if((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min值大于max值
            if(min.compareTo(max)==1) { //进行两值交换
                BigDecimal temp = min ;
                min = max;
                max = temp;
            }
            sql.append(" and price between ? and ? ");
            list.add(min);
            list.add(max);
        } else if (min != null && min.signum() == 1) {
            sql.append(" and price > ? ");
            list.add(min);
        } else if (max != null && max.signum() == 1) {
            sql.append(" and price < ? ");
            list.add(max);
        }

        StringBuilder end = new StringBuilder(" order by price desc limit ?,?");
        sql.append(end);
        list.add(begin);
        list.add(pageSize);
        List<Book> bookList = queryRunner.query(sql.toString(), new BeanListHandler<>(Book.class, getProcessor()),list.toArray());
        return bookList;
    }
}
