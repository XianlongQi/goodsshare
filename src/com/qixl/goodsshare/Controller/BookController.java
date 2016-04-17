package com.qixl.goodsshare.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qixl.common.ModelAndView;
import com.qixl.goodsshare.Constants;
import com.qixl.goodsshare.exception.ParameterException;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.BookStatus;
import com.qixl.goodsshare.model.BorrowRequest;
import com.qixl.goodsshare.model.Pagenation;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.service.BookService;
import com.qixl.goodsshare.util.StringUtils;

public class BookController {

    List<String> mockPictures = new ArrayList<>();

    public BookController() {
        mockPictures.add("/static/book/Book1_50x60.png");
        mockPictures.add("/static/book/Book2_50x60.png");
        mockPictures.add("/static/book/Book3_50x60.png");
        mockPictures.add("/static/book/Book4_50x60.png");
        mockPictures.add("/static/book/Book3_50x60.png");
        mockPictures.add("/static/book/Book2_50x60.png");
        mockPictures.add("/static/book/Book5_50x60.png");
    }

    public BookController(List<String> mockPictures) {
        super();
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String desc = request.getParameter("desc");

        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setDesc(desc);
        System.out.println("userId:" + user.getId());

        book.setOwnerId(user.getId());
        book.setCurrentId(user.getId());
        book.setOwnerName(user.getUserName());
        book.setCurrentOwnerName(user.getUserName());
        book.setPicture(mockPictures.get(getRandom() - 1));

        BookService bookService = new BookService();
        try {
            bookService.sava(book);
        } catch (ParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        session.setAttribute("SUCCESS_FLASH_MESSAGE", "保存成功");
        // request.getRequestDispatcher("/mybook").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/mybook.action");

        System.out.println("save");
    }

    public static int getRandom() {
        int max = 5;
        Random random = new Random(max);
        int result = random.nextInt(max) + 1;

        return result;
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String status = request.getParameter("status");
        String idStr = request.getParameter("id");
        if (StringUtils.isEmpty(status)) {
            status = "all";
        }
        String currentPage = request.getParameter("currentPage");
        if (StringUtils.isEmpty(currentPage)) {
            currentPage = "1";
        }
        int id = Integer.parseInt(idStr);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        BookService bookService = new BookService();
        Book book = bookService.getById(id);

        if (book.getOwnerId() != user.getId()) {
            session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "当前没有权限删除此书籍");
            response.sendRedirect(
                    request.getContextPath() + "/mybook.action?sttus=" + status + "&currentPage=" + currentPage);
            return;
        }

        if (book.getOwnerId() != book.getCurrentId()) {
            session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "当前书籍已经借出，不能删除");
            response.sendRedirect(
                    request.getContextPath() + "/mybook.action?sttus=" + status + "&currentPage=" + currentPage);
            return;
        }

        bookService.deleteById(id);
        session.setAttribute(Constants.SUCCESS_FLASH_MESSAGE, "删除此书籍成功");
        response.sendRedirect(
                request.getContextPath() + "/mybook.action?sttus=" + status + "&currentPage=" + currentPage);
        System.out.println("delete");
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        Book book = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        if (StringUtils.isEmpty(idStr)) {
            book = new Book();
        } else {
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ex) {
                session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "书籍ID应该未整型数字！");
                response.sendRedirect(request.getContextPath() + "/mybook.action");
                return;
            }
            BookService bookService = new BookService();
            book = bookService.getById(id);
            if (book.getOwnerId() != user.getId()) {
                session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "当前没有权限修改此书籍");
                response.sendRedirect(request.getContextPath() + "/mybook.action");
                return;
            }
        }
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/jsp/book/edit.jsp").forward(request, response);
        System.out.println("edit");
    }

    public void mybook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("mybook...");
        String currentPageStr = request.getParameter("currentPage");
        if (StringUtils.isEmpty(currentPageStr)) {
            currentPageStr = "1";
        }

        int currentPage = Integer.parseInt(currentPageStr);
        if (currentPage < 1) {
            currentPage = 1;
        }

        Pagenation pagenation = new Pagenation();
        pagenation.setCurretnPage(currentPage);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        String status = request.getParameter("status");
        if (StringUtils.isEmpty(status)) {
            status = "all";
        }

        BookService bookService = new BookService();

        BookStatus statusEnum = null;
        try {
            statusEnum = BookStatus.valueOf(status);
        } catch (Exception ex) {
            statusEnum = BookStatus.valueOf("alls");
        }

        ArrayList<Book> books = bookService.query(user.getId(), pagenation, statusEnum);
        int borrowCount = bookService.getMyBookBorrowCountByUserId(user.getId());
        // ArrayList<Book> books = bookService.query(user.getId(),pagenation);
        /*
         * if(status.equals("all")){ books =
         * bookService.query(user.getId(),pagenation,status); }else
         * if(status.equals("out")){ books =
         * bookService.queryMyOutBook(user.getId(),pagenation); }else
         * if(status.equals("in")){ books =
         * bookService.queryMyInBook(user.getId(),pagenation); }else
         * if(status.equals("borrow")){ books =
         * bookService.queryMyBorrowBook(user.getId(),pagenation); }else{ status
         * = "all"; books = bookService.query(user.getId(), pagenation,status);
         * }
         */

        request.setAttribute("status", status);
        request.setAttribute("borrowCount", borrowCount);
        request.setAttribute("books", books);
        request.setAttribute("pagenation", pagenation);
        /*
         * List<String> statusList = new ArrayList<String>();
         * statusList.add("all"); statusList.add("out"); statusList.add("in");
         * statusList.add("borrow");
         * 
         * for(String statusItem : statusList){ int mybookCount =
         * bookService.getMyBookCount(user.getId(), statusItem);
         * request.setAttribute(statusItem+"Count", mybookCount); }
         */
        for (BookStatus enumStatus : BookStatus.values()) {
            int mybookCount = bookService.getMyBookCount(user.getId(), enumStatus);
            request.setAttribute(enumStatus + "Count", mybookCount);
        }
        /*
         * int mybookCount = bookService.getMyBookCount(user.getId(),status);
         * 
         * int mybookOutCount = bookService.getMyBookOutCount(user.getId());
         * 
         * int mybookInCount = bookService.getMyBookInCount(user.getId());
         * 
         * int mybookBorrowCount =
         * bookService.getMyBookBorrowCount(user.getId());
         * 
         * request.setAttribute("mybookCount", mybookCount);
         * request.setAttribute("mybookOutCount", mybookOutCount);
         * request.setAttribute("mybookInCount", mybookInCount);
         * request.setAttribute("mybookBorrowCount", mybookBorrowCount);
         */
        request.getRequestDispatcher("/WEB-INF/jsp/book/mybook.jsp").forward(request, response);
        System.out.println("mybook");
    }

    private static final String ALLBOOKSPAGE = "/WEB-INF/jsp/book/allbook.jsp";

    public void allbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookService();
        ArrayList<Book> bookList = new ArrayList<Book>();
        ArrayList<Integer> borrowRequestBookList = new ArrayList<Integer>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        
        bookList = bookService.queryAll();
        borrowRequestBookList = bookService.queryBorrowRequestingBooks(user.getId());
        request.setAttribute("books", bookList);
        request.setAttribute("borrowRequestBookList", borrowRequestBookList);
        
        request.setAttribute("user", user);
        request.getRequestDispatcher(ALLBOOKSPAGE).forward(request, response);
    }

    public void goodsDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // String idStr = request.getParameter("id");
        String idStr = request.getParameter("id");
        Book book = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        if (StringUtils.isEmpty(idStr)) {
            book = new Book();
        } else {
            int id = 0;
            try {
                id = Integer.parseInt(idStr);
            } catch (Exception ex) {
                session.setAttribute(Constants.ERROR_FLASH_MESSAGE, "书籍ID应该未整型数字！");
                response.sendRedirect(request.getContextPath() + "/mybook.action");
                return;
            }
            BookService bookService = new BookService();
            book = bookService.getById(id);
        }

        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/jsp/book/edit.jsp").forward(request, response);
        System.out.println("allbook");
    }

    public void borrowGoods(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String goodsIdStr = request.getParameter("goodsId"); // 物品ID
        // String goodsOwnerIdStr =
        // request.getParameter("goodsOwnerId");//物品拥有人的ID
        // String currentOwnerIdStr =
        // request.getParameter("currentOwnerId");//物品持有人的ID

        // int goodsId = Integer.parseInt(goodsIdStr);
        int goodsId = Integer.parseInt(goodsIdStr);
        // int goodsOwnerId = Integer.parseInt(goodsOwnerIdStr);
        // int currentOwnerId = Integer.parseInt(currentOwnerIdStr);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        int borrowId = user.getId(); // 发出借用请求人的ID

        BookService bookService = new BookService();
        Book book = bookService.getById(goodsId);

        bookService.borrowGoods(book, borrowId);
        session.setAttribute(Constants.SUCCESS_FLASH_MESSAGE, "借用请求已发出，待物品持有者同意！");
        response.sendRedirect(request.getContextPath() + "/allbook.action");
        System.out.println("borrowGoods");
    }

    public void borrowRequestDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("borrowRequestDetail...");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        // String borrowId = request.getParameter("borrowid");

        BookService bookService = new BookService();

        ArrayList<BorrowRequest> borrowRequests = bookService.queryByBorrowId((user.getId()));
        // int borrowCount =
        // bookService.getMyBookBorrowCountByUserId(user.getId());

        // request.setAttribute("borrowCount", borrowCount);
        request.setAttribute("borrowRequests", borrowRequests);

        request.getRequestDispatcher("/WEB-INF/jsp/book/borrowrequest.jsp").forward(request, response);
        System.out.println("borrowRequests");
    }

    public void borrowOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // borrowMessageId+","+borrowUserId+","+borrowUserName+","+goodsId
        String borrowMessageIdStr = request.getParameter("borrowMessageId");
        String borrowUserIdStr = request.getParameter("borrowUserId");
        String borrowUserNameStr = request.getParameter("borrowUserName");
        String goodsIdStr = request.getParameter("goodsId");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        int borrowMessageId = Integer.parseInt(borrowMessageIdStr);
        int borrowUserId = Integer.parseInt(borrowMessageIdStr);
        int goodsId = Integer.parseInt(borrowMessageIdStr);

        BookService bookService = new BookService();
        bookService.updateBorrowMessageById(borrowMessageId);
        bookService.updateBGoodsById(goodsId,borrowUserId,borrowUserNameStr);
        bookService.saveGoodsHistory(goodsId,borrowUserId);
        
        session.setAttribute(Constants.SUCCESS_FLASH_MESSAGE, "借出成功");
//        request.getRequestDispatcher("/WEB-INF/jsp/book/mybook.jsp").forward(request, response);
        System.out.println("borrowOut");
    }

}
