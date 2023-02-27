package com.softeem.servlet;

import com.softeem.bean.Book;
import com.softeem.service.BookService;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.util.BaseServlet;
import com.softeem.util.Page;
import com.softeem.util.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void searchPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//娶不到默认显示第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//
        int min = WebUtils.parseInt(request.getParameter("min"), 0);
        int max = WebUtils.parseInt(request.getParameter("max"), 0);
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String author = request.getParameter("author") == null ? "" : request.getParameter("author");
        request.setAttribute("name", name);
        request.setAttribute("author", author);
        request.setAttribute("min", request.getParameter("min"));
        request.setAttribute("max", request.getParameter("max"));
        try {
            Page<Book> page = bookService.page(pageNo, pageSize, name, author, new BigDecimal(min), new BigDecimal(max));
            page.setUrl("BookServlet?action=searchPage&name=" + name + "&author=" + author + "&min=" + (min == 0 ? "" : min) + "&max=" + (max == 0 ? "" : max) + "&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> bookList = bookService.queryBooks();
            request.setAttribute("bookList", bookList);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//娶不到默认显示第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//

        try {
            Page<Book> page = bookService.page(pageNo, pageSize);
            page.setUrl("BookServlet?action=page&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            String pageNo = request.getParameter("pageNo");
            System.out.println(pageNo);
            Book book = bookService.queryBookById(Integer.valueOf(id));
            request.setAttribute("pageNo", pageNo);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
//        String id = request.getParameter("id");
        //文件上传不可以这样获取数据
        String pageNo = request.getParameter("pageNo");
        Book book = new Book();
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环这6段数据并处理它们
                for (FileItem fileItem : list) {
                    //判断那些是普通表单项,还是上传的文件类型
                    if (fileItem.isFormField()) {
                        //处理普通表单项
                        //System.out.println(fileItem.getFieldName() +" = " + MyUtils.parseString(fileItem.getString()));
                        if ("id".equals(fileItem.getFieldName())) {
                            book.setId(Integer.valueOf(fileItem.getString()));
                            book = bookService.queryBookById(book.getId());
                        } else if ("name".equals(fileItem.getFieldName())) {
                            book.setName(fileItem.getString("utf-8"));//图书名
                        } else if ("author".equals(fileItem.getFieldName())) {
                            book.setAuthor(fileItem.getString("utf-8"));//图书作者
                        } else if ("price".equals(fileItem.getFieldName())) {
                            book.setPrice(new BigDecimal(fileItem.getString()));//图书价格
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            book.setSales(Integer.valueOf(fileItem.getString()));//图书销量
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            book.setStock(Integer.parseInt(fileItem.getString()));//图书库存
                        } /*else if ("oldPath".equals(fileItem.getFieldName())) {
                            book.setImgPath(fileItem.getString());
                        }*/
                    } else {
                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        if (!filename.equals("")) {
                            //文件名 = 123.jpg       suffix = .jpg
                            String suffix = filename.substring(filename.lastIndexOf("."));
                            //通过时间毫秒 + 后缀 = 新文件名
                            String newfilename = System.currentTimeMillis() + suffix;


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String format = simpleDateFormat.format(new Date());
                            File file = new File("E:/bookimg/" + format + "/");
                            if (!file.exists()) {//判断要上传的文件目录是否存在
                                file.mkdirs();//创建目录
                            }
                            System.out.println(file.getAbsolutePath());
                            fileItem.write(new File(file, newfilename));//上传图片
                            String path = "e:/" + book.getImgPath();
                            File oldFile = new File(path);
                            oldFile.delete();
                            book.setImgPath("/bookimg/" + format + "/" + newfilename);//图书封面

                        }
                    }

                }

                bookService.updateBook(book);//将图片信息保存到数据库
//                request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
//                response.sendRedirect("BookServlet?action=page");
                response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println("不是多段数据..无法上传文件!");
        }
    }


    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String pageNo = request.getParameter("pageNo");
        System.out.println(pageNo);
        try {
            Book book = bookService.queryBookById(Integer.valueOf(id));
            String path = "e:/" + book.getImgPath();
            File file = new File(path);
            file.delete();
            bookService.deleteBook(Integer.valueOf(id));
//            response.sendRedirect("BookServlet?action=page");
            response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Book book = new Book();
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环这6段数据并处理它们
                for (FileItem fileItem : list) {
                    //判断那些是普通表单项,还是上传的文件类型
                    if (fileItem.isFormField()) {
                        //处理普通表单项
                        //System.out.println(fileItem.getFieldName() +" = " + MyUtils.parseString(fileItem.getString()));
                        if ("name".equals(fileItem.getFieldName())) {
                            book.setName(fileItem.getString("utf-8"));//图书名
                        } else if ("author".equals(fileItem.getFieldName())) {
                            book.setAuthor(fileItem.getString("utf-8"));//图书作者
                        } else if ("price".equals(fileItem.getFieldName())) {
                            book.setPrice(new BigDecimal(fileItem.getString()));//图书价格
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            book.setSales(Integer.valueOf(fileItem.getString()));//图书销量
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            book.setStock(Integer.parseInt(fileItem.getString()));//图书库存
                        }
                    } else {
                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        //文件名 = 123.jpg       suffix = .jpg
                        String suffix = filename.substring(filename.lastIndexOf("."));
                        //通过时间毫秒 + 后缀 = 新文件名
                        String newfilename = System.currentTimeMillis() + suffix;


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String format = simpleDateFormat.format(new Date());
                        File file = new File("E:/bookimg/" + format + "/");
                        if (!file.exists()) {//判断要上传的文件目录是否存在
                            file.mkdirs();//创建目录
                        }
                        System.out.println(file.getAbsolutePath());
                        fileItem.write(new File(file, newfilename));//上传图片
                        book.setImgPath("/bookimg/" + format + "/" + newfilename);//图书封面
                    }
                }
                bookService.addBook(book);//将图片信息保存到数据库
//                request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
                response.sendRedirect("BookServlet?action=page");
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println("不是多段数据..无法上传文件!");
        }
    }
}
