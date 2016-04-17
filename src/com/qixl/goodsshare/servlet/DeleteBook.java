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
 * Servlet implementation class DeleteBook
 */
public class DeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBook() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String status = request.getParameter("status");
	    String idStr = request.getParameter("id");
	    if(StringUtils.isEmpty(status)){
	        status = "all";
	    }
	    String currentPage = request.getParameter("currentPage");
	    if(StringUtils.isEmpty(currentPage)){
	        currentPage = "1";
        }
	    int id = Integer.parseInt(idStr);
	    
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute(Constants.USER);
	    
	    BookService bookService = new BookService();
	    Book book = bookService.getById(id);
	    
	    if(book.getOwnerId() != user.getId()){
	        session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "当前没有权限删除此书籍");
	        response.sendRedirect(request.getContextPath()+"/mybook.action?sttus="+status+"&currentPage="+currentPage);
	        return;
	    }
	    
	    if(book.getOwnerId() != book.getCurrentId()){
            session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "当前书籍已经借出，不能删除");
            response.sendRedirect(request.getContextPath()+"/mybook.action?sttus="+status+"&currentPage="+currentPage);
            return;
        }
	    
	    bookService.deleteById(id);
	    session.setAttribute(Constants.SUCCESS_FLASH_MESSAGE, "删除此书籍成功");
	    response.sendRedirect(request.getContextPath()+"/mybook.action?sttus="+status+"&currentPage="+currentPage);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
