package com.softeem.servlet;

import com.softeem.bean.Book;
import com.softeem.bean.CartItem;
import com.softeem.service.BookService;
import com.softeem.service.Cart;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.util.BaseServlet;
import com.softeem.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {

    /**
     * 加入购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        HttpSession session = request.getSession();

        //获取请求参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        try {
            //提供主键id获取图书对象
            Book book = bookService.queryBookById(id);
            //把图书信息，转换成CartItem商品类
            CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
            //从session会话作用域中取出cart，如果为null，购物城中无商品，否则有
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();//创建Cart对象
                session.setAttribute("cart", cart);
            }
            //添加商品项
            cart.addItem(cartItem);
            session.setAttribute("lastName", cartItem.getName());
            System.out.println("请求头 Referer 的值：" + request.getHeader("Referer"));
            // 重定向回原来商品所在的地址页面
            response.sendRedirect(request.getHeader("Referer"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        System.out.println(id);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        String count = request.getParameter("count");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.updateCount(Integer.valueOf(id),Integer.valueOf(count));
        }
        response.sendRedirect(request.getHeader("Referer"));

    }
}
