package com.softeem.service.impl;

import com.softeem.bean.Book;
import com.softeem.bean.CartItem;
import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.dao.BookDao;
import com.softeem.dao.OrderDao;
import com.softeem.dao.OrderItemDao;
import com.softeem.dao.impl.BookDaoImpl;
import com.softeem.dao.impl.OrderDaoImpl;
import com.softeem.dao.impl.OrderItemDaoImpl;
import com.softeem.service.Cart;
import com.softeem.service.OrderService;
import com.softeem.util.Page;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    //订单dao
    private OrderDao orderDao = new OrderDaoImpl();
    //订单项dao
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    //
    private BookDao bookDao = new BookDaoImpl();

    /**
     * 生成一个订单
     * 1.添加一个订单数据到数据库中的order表
     * 2.此订单中至少有一个订单项，至多有n个，所以要将订单项目都添加到orderItem表中
     * 3.清空掉购物车中的数据
     * @param cart 购物车对象
     * @param userId 用户id
     * @return 返回此订单的id
     */
    @Override
    public String createOrder(Cart cart, Integer userId) throws SQLException {
        //1.添加一个订单到order表中
        String orderId = ""+System.currentTimeMillis() + userId;
        Order order = new Order();
        order.setOrderId(orderId);//生成的订单编号
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));//当前的系统时间，订单创建的时间
        order.setPrice(cart.getTotalPrice());//订单的总价格
        order.setStatus(0);//订单状态 0 未发货，1 已发货，2 表示已签收
        order.setUserId(userId);//设置用户编号，因为这个订单要知道是谁下的单
        orderDao.save(order);


        //2.此订单中至少有一个订单项，至多有n个，所以要将订单项目都添加到orderItem表中
        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            OrderItem item = new OrderItem();
            item.setName(entry.getValue().getName());//设置订单项的名字
            item.setCount(entry.getValue().getCount());//设置订单项的数量
            item.setPrice(entry.getValue().getPrice());//设置订单项的单价
            item.setTotalPrice(entry.getValue().getTotalPrice());//设置订单项的总价
            item.setOrderId(orderId);//设置订单项的订单编号
            orderItemDao.save(item);

            //更新库存和销量
            Book book = bookDao.findById(entry.getValue().getId());//通过图书的主键id返回一个图书对象
            book.setSales(book.getSales()+item.getCount());//修改销量
            book.setStock(book.getStock()-item.getCount());//修改库存
            bookDao.updateById(book);//修改book库存和销量
        }

        //3.清空掉购物车中的数据
        cart.clear();
        return orderId;
    }

    @Override
    public Page<Order> page(Integer pageNo,Integer pageSize) throws SQLException {
        //调用public Integer queryForPageTotalCount()
        //public List<Book> queryForPageItems(int begin, int pageSize)
        Page<Order> page = new Page<>();
        Integer totalCount = orderDao.pageRecord();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize - 1) / pageSize);//设置当前页显示数量
        page.setPageNo(pageNo);
        page.setItems(orderDao.page(page.getPageNo()));//设置查询分页结果集
        return page;
    }

    @Override
    public Page<OrderItem> pageOrderItem(String orderId,Integer pageNo,Integer pageSize) throws SQLException {
        Page<OrderItem> page = new Page<>();
        Integer totalCount = orderItemDao.pageRecord(orderId);
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize - 1) / pageSize);//设置当前页显示数量
        page.setPageNo(pageNo);
        page.setItems(orderItemDao.page(orderId,page.getPageNo()));//设置查询分页结果集
        return page;
    }
}
