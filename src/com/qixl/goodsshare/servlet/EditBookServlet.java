package com.qixl.goodsshare.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qixl.goodsshare.Constants;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.service.BookService;
import com.qixl.goodsshare.util.StringUtils;

/**
 * Servlet implementation class EditBookServlet
 */
public class EditBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	    //doPost(request, response);
	    
	    String idStr = request.getParameter("id");
	    Book book =  null;
	    
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute(Constants.USER);
	    
	    if(StringUtils.isEmpty(idStr)){
	        book = new Book();
	    }else{
	        int id = 0;
	        try{
	                id = Integer.parseInt(idStr);
	           }catch(Exception ex){
	               session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "书籍ID应该未整型数字！");
	                response.sendRedirect(request.getContextPath()+"/mybook.action");
	                return;
	           }
	        BookService bookService = new BookService();
	        book = bookService.getById(id);
	        if(book.getOwnerId() != user.getId()){
	            session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "当前没有权限修改此书籍");
	            response.sendRedirect(request.getContextPath()+"/mybook.action");
	            return;
	        }
	    }
	    request.setAttribute("book", book);
	    request.getRequestDispatcher("/WEB-INF/jsp/book/edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
