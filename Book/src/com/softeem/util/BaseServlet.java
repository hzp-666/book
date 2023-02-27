package com.softeem.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String methodName = request.getParameter("action");
        System.out.println("methodName = " + methodName);

        Class aClass = this.getClass();//得到类描述对象
        try {
            //得到一个方法对象
            Method declaredMethod = aClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            declaredMethod.setAccessible(true);//取消访问检查
            declaredMethod.invoke(this, request, response);
            //把异常抛给过滤器
        } catch (Exception e) {
            throw new RuntimeException(e);//把异常抛给过滤器
//            e.printStackTrace(); 不能打印，必须往上抛
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
