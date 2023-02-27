package com.softeem.servlet;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.bean.User;
import com.softeem.service.Cart;
import com.softeem.service.OrderService;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.util.BaseServlet;
import com.softeem.util.Page;
import com.softeem.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    //生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if(user == null) {
            request.setAttribute("msg", "登陆时间超时，请重新登录");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
        }

        OrderService orderService = new OrderServiceImpl();
        try {
            //生成订单并且返回此订单号
            String orderId = orderService.createOrder(cart, user.getId());

            //为什么选择用重定向跳转而不用服务器请求转发，是为了防止重复提交
            session.setAttribute("orderId",orderId);
            response.sendRedirect(request.getContextPath()+"/pages/cart/checkout.jsp");//http:localhost:8080/Book/pages/cart/checkout.jsp
//            response.sendRedirect("/pages/cart/checkout.jsp"); 不能这样写，会变成http:localhost:8080/pages/cart/checkout.jsp 少了工程路径book

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    protected void listOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        try {
            Page<Order> page = orderService.page(pageNo, pageSize);
            page.setUrl("OrderServlet?action=listOrder&");
            request.setAttribute("page",page);
            request.getRequestDispatcher("pages/order/order.jsp").forward(request,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    protected void listOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        String orderItemId = request.getParameter("orderItemOrderId");
        try {
            Page<OrderItem> page = orderService.pageOrderItem(orderItemId,pageNo,pageSize);
            page.setUrl("OrderServlet?action=listOrderItem&orderItemOrderId="+orderItemId+"&");
            request.setAttribute("page",page);
            request.getRequestDispatcher("pages/order/orderitem.jsp").forward(request,response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
