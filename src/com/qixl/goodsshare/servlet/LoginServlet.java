package com.qixl.goodsshare.servlet;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.qixl.goodsshare.Constants;
import com.qixl.goodsshare.dao.UserDao;
import com.qixl.goodsshare.exception.ParameterException;
import com.qixl.goodsshare.exception.ServiceException;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.service.UserService;
import com.qixl.goodsshare.util.DBUtils;
import com.qixl.goodsshare.util.StringUtils;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";
    private static final String WELCOME_PAGE = "/WEB-INF/jsp/welcome.jsp";
       
    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        System.out.println("get......");
        if(user != null){
            System.out.println("user:"+user);
            response.sendRedirect(request.getContextPath()+"/mybook.action");
        }else{
            System.out.println("nouser");
            String go = request.getParameter("go");
            
            System.out.println("go:..."+go);
            if(StringUtils.isEmpty(go)){
                go = "";
            }
            request.setAttribute("go",go);
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
        
        //request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        //doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        System.out.println("userName:"+userName+"password:"+password);
        System.out.println("post......");
        try{
            User user = null;
            UserService uesrService = new UserService();
            user = uesrService.login(userName, password);
           
            System.out.println("user---login");
            //System.out.println(user.toString());
            // user.setPassword("");
            HttpSession session =  request.getSession();
//            System.out.println("session");
            session.setAttribute(Constants.USER, user);
            
            String go = request.getParameter("go");
            String queryString = request.getParameter("queryString");
            if(!StringUtils.isEmpty(queryString)){
                if(queryString.startsWith("#")){
                    queryString = queryString.substring(1);
                }
                go = go + "?" + queryString; 
            }
            System.out.println("loginServlet:go:...."+go);
            if(go== ""){
                System.out.println("go:空;;;;;");
            }
            if(go== null){
                System.out.println("go:null;;;;;");
            }
            if(go == "null"){
                System.out.println("go:"+"null"+";;;;;");
            }
            if(StringUtils.isEmpty(go)){
                System.out.println("loginServlet:1:....");
                response.sendRedirect(request.getContextPath()+"/mybook.action");
            }else{
                System.out.println("loginServlet:2:....");
                response.sendRedirect(request.getContextPath()+"/"+go);
            } 
//           System.out.println(request.getContextPath().toString());
           
            //request.getRequestDispatcher(WELCOME_PAGE).forward(request, response);
        } catch (ParameterException parameterException) {
            Map<String,String> errorField = parameterException.getErrorField();
            request.setAttribute(Constants.ERROR_MESSAGE, errorField);
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        } catch (ServiceException serviceException) {
            int code = serviceException.getCode();
            request.setAttribute(Constants.TIP_MESSAGE, serviceException.getMessage()+"["+code+"]");
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }
}
