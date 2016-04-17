package com.qixl.goodsshare.service;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PagesPerMinute;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.qixl.goodsshare.dao.BookDao;
import com.qixl.goodsshare.exception.DBException;
import com.qixl.goodsshare.exception.ParameterException;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.BookStatus;
import com.qixl.goodsshare.model.BorrowRequest;
import com.qixl.goodsshare.model.Pagenation;
import com.qixl.goodsshare.util.DBUtils;
import com.qixl.goodsshare.util.StringUtils;

public class BookService {
        
    public int sava (Book book) throws ParameterException{
        ParameterException parameterException = new ParameterException();
        if(StringUtils.isEmpty(book.getName())){
            parameterException.addErrorField("name", "请填写书名");
        }
        
        if(StringUtils.isEmpty(book.getAuthor())){
            parameterException.addErrorField("name", "请填写作者");
        }
        
        if(parameterException.isErrorField()){
            throw parameterException;
        }
        
        BookDao bookDao  = new BookDao();
        if(book.getId() == 0){
            bookDao.save(book);
        }else {
            bookDao.update(book);
        }
        
        return book.getId();
    }
    
    public ArrayList<Book> query(int userId,Pagenation pagenation,BookStatus status) {
        /*
        if(status == null){
            status = "all";
        }
        
        List<String> statusList = new ArrayList<String>();
        statusList.add("all");
        statusList.add("out");
        statusList.add("in");
        statusList.add("borrow");
        boolean b = false;
        for(String statusItem : statusList){
            if(statusItem.equals(status)){
                b = true;
                break;
            }
        }
        if(!b){
            status = "all";
        }
        */
        ArrayList<Book> bookList = new ArrayList<Book>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.query(userId,pagenation,status);
        
        return bookList;
    }
    
    public ArrayList<Book> queryByUserId (int userId) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.queryByUserId(userId);
        
        return bookList;
    }
    
    public ArrayList<Book> queryAll () {
        ArrayList<Book> bookList = new ArrayList<Book>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.queryAll();
        
        return bookList;
    }
    
   

    public ArrayList<Book> queryMyOutBook(int userId,Pagenation pagenation) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.queryMyOutBook(userId,pagenation);
        
        return bookList;
    }

    public ArrayList<Book> queryMyInBook(int userId,Pagenation pagenation) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.queryMyInBook(userId,pagenation);
        
        return bookList;
    }

    public ArrayList<Book> queryMyBorrowBook(int userId,Pagenation pagenation) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.queryMyBorrowBook(userId,pagenation);
        
        return bookList;
    }

    public int getMyBookCount (int userId,BookStatus status){
        /*
        if(status == null){
            status = "all";
        }
        
        List<String> statusList = new ArrayList<String>();
        statusList.add("all");
        statusList.add("out");
        statusList.add("in");
        statusList.add("borrow");
        boolean b = false;
        for(String statusItem : statusList){
            if(statusItem.equals(status)){
                b = true;
                break;
            }
        }
        if(!b){
            status = "all";
        }
        */
        BookDao bookDao = new BookDao();
        return bookDao.getMyBookCount(userId,status);
    }
    
    public int getMyBookOutCount(int userId) {
        BookDao bookDao = new BookDao();
        return bookDao.getMyBookOutCount(userId);
    }
    
    public int getMyBookInCount(int userId) {
        BookDao bookDao = new BookDao();
        return bookDao.getMyBookInCount(userId);
    }

    public int getMyBookBorrowCount(int userId) {
        BookDao bookDao = new BookDao();
        return bookDao.getMyBookBorrowCount(userId);
    }

    public Book getById(int id) {
        BookDao bookDao = new BookDao();
        
        return bookDao.getById(id);
    }

    public void deleteById(int id) {
        BookDao bookDao = new BookDao();
        bookDao.updateDeleted(id,1);
        
    }

    public void borrowGoods(Book book, int borrowId) {
        BookDao bookDao = new BookDao();
        bookDao.borrowGoods(book,borrowId);
    }

    public int getMyBookBorrowCountByUserId(int id) {
        BookDao bookDao = new BookDao();
        int borrowCount = bookDao.getBorrowCount(id);
        return borrowCount;
    }
    //查找 我要处理的借书请求
    public ArrayList<BorrowRequest> queryByBorrowId(int userId) {
        ArrayList<BorrowRequest> bookList = new ArrayList<BorrowRequest>();
        BookDao bookDao  = new BookDao();
        bookList = bookDao.queryByBorrowId(userId);
        return bookList;
    }

    public void updateBorrowMessageById(int borrowMessageId) {
        BookDao bookDao = new BookDao();
        bookDao.updateBorrowMessageById(borrowMessageId);
        
    }

    public void updateBGoodsById(int goodsId, int borrowUserId, String borrowUserNameStr) {
        BookDao bookDao = new BookDao();
        bookDao.updateBGoodsById(goodsId,borrowUserId,borrowUserNameStr);
    }

    public void saveGoodsHistory(int goodsId, int borrowUserId) {
        BookDao bookDao = new BookDao();
        bookDao.insertGoodsHistory(goodsId,borrowUserId);
    }

    public ArrayList<Integer> queryBorrowRequestingBooks(int userId) {
        ArrayList<Integer> borrowRequestBookList = new ArrayList<Integer>();
        BookDao bookDao  = new BookDao();
        borrowRequestBookList = bookDao.queryBorrowRequestingBooks(userId);
        
        return borrowRequestBookList;
    }
    

}
