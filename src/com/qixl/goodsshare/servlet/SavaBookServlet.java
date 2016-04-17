package com.qixl.goodsshare.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qixl.goodsshare.Constants;
import com.qixl.goodsshare.exception.ParameterException;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.service.BookService;

public class SavaBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<String> mockPictures = new ArrayList<>();
	
       
    public SavaBookServlet() {
        super();
        mockPictures.add("/static/book/Book1_50x60.png");
        mockPictures.add("/static/book/Book2_50x60.png");
        mockPictures.add("/static/book/Book3_50x60.png");
        mockPictures.add("/static/book/Book4_50x60.png");
        mockPictures.add("/static/book/Book5_50x60.png");
    }
    
    public static int getRandom () {
        int max=5;
        Random random = new Random(max);
        int result = random.nextInt(max)+1;
        
        return result;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    String name = request.getParameter("name");
		String author = request.getParameter("author");
		String desc= request.getParameter("desc");
		
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.USER);
		Book book = new Book();
		book.setName(name);
		book.setAuthor(author);
		book.setDesc(desc);
		System.out.println("userId:"+user.getId());
		
		book.setOwnerId(user.getId());
		book.setCurrentId(user.getId());
		book.setOwnerName(user.getUserName());
		book.setCurrentOwnerName(user.getUserName());
		book.setPicture(mockPictures.get(getRandom()-1));
		
		BookService bookService = new BookService();
		try {
            bookService.sava(book);
        } catch (ParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	
		
		session.setAttribute("SUCCESS_FLASH_MESSAGE", "保存成功");
		//request.getRequestDispatcher("/mybook").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/mybook.action");
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
