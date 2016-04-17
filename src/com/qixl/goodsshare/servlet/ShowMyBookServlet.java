package com.qixl.goodsshare.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qixl.goodsshare.Constants;
import com.qixl.goodsshare.dao.BookDao;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.BookStatus;
import com.qixl.goodsshare.model.Pagenation;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.service.BookService;
import com.qixl.goodsshare.util.StringUtils;


public class ShowMyBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShowMyBookServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    request.setCharacterEncoding("UTF-8");
	    
	    String currentPageStr = request.getParameter("currentPage");
	    if(StringUtils.isEmpty(currentPageStr)){
	        currentPageStr="1";
	    }
	   
	    int currentPage = Integer.parseInt(currentPageStr);
	    if(currentPage < 1){
	        currentPage = 1;
	    }
	    
	    
	    Pagenation pagenation = new Pagenation();
	    pagenation.setCurretnPage(currentPage);
	    
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute(Constants.USER);
	    
	    String status = request.getParameter("status");
        if(StringUtils.isEmpty(status)){
            status="all";
        }
        
        
	    BookService bookService = new BookService();
	    
	    BookStatus statusEnum = null;
	    try{
	        statusEnum = BookStatus.valueOf(status);
	    }catch(Exception ex){
	        statusEnum = BookStatus.valueOf("alls");
	    }
	    
	    
	   
	    ArrayList<Book> books = bookService.query(user.getId(), pagenation, statusEnum);
//	    ArrayList<Book> books = bookService.query(user.getId(),pagenation);
	    /*
	    if(status.equals("all")){
	        books = bookService.query(user.getId(),pagenation,status);
	    }else if(status.equals("out")){
	        books = bookService.queryMyOutBook(user.getId(),pagenation);
	    }else if(status.equals("in")){
            books = bookService.queryMyInBook(user.getId(),pagenation);
        }else if(status.equals("borrow")){
            books = bookService.queryMyBorrowBook(user.getId(),pagenation);
        }else{
            status = "all";
            books = bookService.query(user.getId(), pagenation,status);
        }
	    */
	    request.setAttribute("status", status);
	    
	    request.setAttribute("books", books);
	    request.setAttribute("pagenation", pagenation);
	    /*
	    List<String> statusList = new ArrayList<String>();
	    statusList.add("all");
	    statusList.add("out");
	    statusList.add("in");
	    statusList.add("borrow");
	    
	    for(String statusItem : statusList){
	        int mybookCount = bookService.getMyBookCount(user.getId(), statusItem);
	        request.setAttribute(statusItem+"Count", mybookCount);
	    }
	    */
	    for(BookStatus enumStatus : BookStatus.values()){
    	    int mybookCount = bookService.getMyBookCount(user.getId(), enumStatus);
            request.setAttribute(enumStatus+"Count", mybookCount);
	    }
	    /*
	    int mybookCount = bookService.getMyBookCount(user.getId(),status);
	   
	    int mybookOutCount = bookService.getMyBookOutCount(user.getId());
	    
	    int mybookInCount = bookService.getMyBookInCount(user.getId());
	    
	    int mybookBorrowCount = bookService.getMyBookBorrowCount(user.getId());
	    
	    request.setAttribute("mybookCount", mybookCount);
	    request.setAttribute("mybookOutCount", mybookOutCount);
	    request.setAttribute("mybookInCount", mybookInCount);
	    request.setAttribute("mybookBorrowCount", mybookBorrowCount);
	    */
	    request.getRequestDispatcher("/WEB-INF/jsp/book/mybook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
