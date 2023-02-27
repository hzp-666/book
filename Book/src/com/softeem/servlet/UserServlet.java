package com.softeem.servlet;

import com.softeem.bean.User;
import com.softeem.service.UserService;
import com.softeem.service.impl.UserServiceImpl;
import com.softeem.util.BaseServlet;
import com.softeem.util.CookieUtils;
import com.softeem.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
//静态导入，import后面加了个static，在类中直接用 方法名/变量名 调用静态方法/变量
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap, user);
        UserService userService = new UserServiceImpl();
        try {
            User myuser = userService.login(user);
            System.out.println(myuser);
            if (myuser != null) {
                Cookie nameCookie = new Cookie("username", myuser.getUsername());
                Cookie passCookie = new Cookie("password", myuser.getPassword());
                nameCookie.setMaxAge(60 * 60 * 24 * 7);
                passCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(nameCookie);
                response.addCookie(passCookie);

                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("user", myuser);//登陆成功后的个人信息保存到session作用域中
                request.setAttribute("msg", "欢迎回来!");
                if (request.getParameter("url") != null && !request.getParameter("url").equals("")) {
                    request.getRequestDispatcher(request.getParameter("url")).forward(request, response);
                } else {
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "账户名或密码不正确");
                request.setAttribute("username", user.getUsername());
                request.setAttribute("password", user.getPassword());
                request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        // 获取Session 中的验证码
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        session.removeAttribute(KAPTCHA_SESSION_KEY);

        String code = request.getParameter("code");//验证码
        UserService userService = new UserServiceImpl();
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap, user);
        request.setAttribute("username", user.getUsername());//为了回显
        request.setAttribute("password", user.getPassword());
        request.setAttribute("email", user.getEmail());
//        request.setAttribute("code", code);
        try {
            if (token.equalsIgnoreCase(code)) {
                if (!userService.existsUsername(user.getUsername())) {
//                    User user = new User(null, username, password, email);
                    userService.registUser(user);
                    session.setAttribute("user", user);
                    request.setAttribute("msg", "注册成功!");
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "用户名已经存在请更换");
                    request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "验证码不正确");
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();//session立即失效
        Cookie nameCookie = CookieUtils.findCookie("username", request.getCookies());
        Cookie passCookie = CookieUtils.findCookie("password", request.getCookies());
        if (nameCookie != null) {
            nameCookie.setMaxAge(0);//立刻失效
            response.addCookie(nameCookie);
        }
        if (passCookie != null) {
            passCookie.setMaxAge(0);//立刻失效
            response.addCookie(passCookie);
        }
        response.sendRedirect("index.jsp");
    }
}
