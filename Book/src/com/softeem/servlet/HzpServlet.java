package com.softeem.servlet;

import com.softeem.util.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HzpServlet", value = "/HzpServlet")
public class HzpServlet extends BaseServlet {

    protected void myTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("我是HzpServlet，myTest方法执行成功");
        int i = 10 / 0;
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }*/
}
