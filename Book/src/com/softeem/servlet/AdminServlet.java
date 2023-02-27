package com.softeem.servlet;

import com.softeem.bean.Admin;
import com.softeem.bean.User;
import com.softeem.service.AdminService;
import com.softeem.service.UserService;
import com.softeem.service.impl.AdminServiceImpl;
import com.softeem.service.impl.UserServiceImpl;
import com.softeem.util.BaseServlet;
import com.softeem.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends BaseServlet {

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Admin admin = new Admin();
        WebUtils.copyParamToBean(parameterMap, admin);
        AdminService adminService = new AdminServiceImpl();
        try {
            Admin myuser = adminService.login(admin);
            if (myuser != null) {
                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("admin", myuser);//登陆成功后的个人信息保存到session作用域中
                request.setAttribute("adminMsg", "欢迎回来!");
                request.getRequestDispatcher("/pages/manager/manager.jsp").forward(request, response);
            } else {
                request.setAttribute("adminMsg", "账户名或密码不正确");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }*/
}
