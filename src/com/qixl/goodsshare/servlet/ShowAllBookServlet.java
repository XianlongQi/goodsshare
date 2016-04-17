package com.qixl.goodsshare.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.service.BookService;

/**
 * Servlet implementation class ShowAllBookServlet
 */
public class ShowAllBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ALLBOOKSPAGE = "/WEB-INF/jsp/book/allbook.jsp";
    public ShowAllBookServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookService bookService = new BookService();
		ArrayList<Book> bookList = new ArrayList<Book>();
		bookList = bookService.queryAll();
		request.setAttribute("books", bookList);
		request.getRequestDispatcher(ALLBOOKSPAGE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
