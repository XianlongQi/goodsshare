package com.qixl.goodsshare.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.qixl.common.JDBCCallBack;
import com.qixl.common.JDBCTemplate;
import com.qixl.goodsshare.exception.DBException;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.BookStatus;
import com.qixl.goodsshare.model.BorrowRequest;
import com.qixl.goodsshare.model.Pagenation;
import com.qixl.goodsshare.util.DBUtils;

public class BookDao {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public void save(Book book) {
        String sql = "insert into goods(name,picture,owner_id,current_owner_id,author,description,created_time,updated_time,owner_name,current_owner_name) values (?,?,?,?,?,?,NOW(),NOW(),?,?)";
        conn = DBUtils.getConnection();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getPicture());
            pstmt.setInt(3, book.getOwnerId());
            pstmt.setInt(4, book.getCurrentId());
            pstmt.setString(5, book.getAuthor());
            pstmt.setString(6, book.getDesc());
            pstmt.setString(7, book.getOwnerName());
            pstmt.setString(8, book.getCurrentOwnerName());

            pstmt.executeUpdate();

            rs = (ResultSet) pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println(id);
                book.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
    }

    public void update(Book book) {
        String sql = "UPDATE goods SET name = ?,author = ?,description = ?,updated_time = NOW() WHERE id = "
                + book.getId();

        try {
            conn = DBUtils.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getDesc());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
    }

    // 查询书籍
    public ArrayList<Book> query(int userId, Pagenation pagenation,BookStatus status) {
        
        pagenation.setTotalCount(this.getMyBookCount(userId,status));     
        if (pagenation.getCurretnPage() > pagenation.getPageCount()) {    
            pagenation.setCurretnPage(pagenation.getCurretnPage());       
        }                       
        String sql ="SELECT * FROM goods WHERE "+this.getWhereSQL(userId, status.getValue())+" ORDER BY updated_time DESC LIMIT "+ pagenation.getOffset() + "," + pagenation.getPageSize() + ";";
        JDBCTemplate<Book> jdbcTemplate = new JDBCTemplate<Book>();
        ArrayList<Book> bookList  = jdbcTemplate.query(sql,new JDBCCallBack<Book>() {
            
            @Override
            public Book rsToObject(ResultSet rs) throws SQLException {
                Book book = new Book();                                       
                book.setId(rs.getInt("id"));                                  
                book.setName(rs.getString("name"));                           
                book.setPicture(rs.getString("picture"));                     
                book.setOwnerId(rs.getInt("owner_id"));                       
                book.setCurrentId(rs.getInt("current_owner_id"));             
                book.setAuthor(rs.getString("author"));                       
                book.setDesc(rs.getString("description"));                    
                book.setOwnerName(rs.getString("owner_name"));                
                book.setCurrentOwnerName(rs.getString("current_owner_name")); 
                return book;
            }

            @Override
            public void setParams(PreparedStatement pstmt) throws SQLException {
                
            }
        });
        return bookList;
//        ArrayList<Book> bookList = new ArrayList<Book>();
//        try {
//            conn = DBUtils.getConnection();
//            pagenation.setTotalCount(this.getMyBookCount(userId,status));
//            if (pagenation.getCurretnPage() > pagenation.getPageCount()) {
//                pagenation.setCurretnPage(pagenation.getCurretnPage());
//            }
////            pstmt = (PreparedStatement) conn.prepareStatement(
////                    "select b.*,u.user_name as current_owner_name from book as b left join user as u on b.current_owner_id = u.id where owner_id = "
////                            + userId +  " AND deleted = 0 limit " + pagenation.getOffset() + "," + pagenation.getPageSize() + ";");
//            String sql ="SELECT * FROM book WHERE "+this.getWhereSQL(userId, status)+" ORDER BY updated_time DESC LIMIT "+ pagenation.getOffset() + "," + pagenation.getPageSize() + ";";
////            System.out.println("sql:"+sql);
//            pstmt = (PreparedStatement) conn.prepareStatement(sql);
//            // pstmt.setInt(1, userId);
//            rs = (ResultSet) pstmt.executeQuery();
//            while (rs.next()) {
//                Book book = new Book();
//                book.setId(rs.getInt("id"));
//                book.setName(rs.getString("name"));
//                book.setPicture(rs.getString("picture"));
//                book.setOwnerId(rs.getInt("owner_id"));
//                book.setCurrentId(rs.getInt("current_owner_id"));
//                book.setAuthor(rs.getString("author"));
//                book.setDesc(rs.getString("description"));
//                book.setOwnerName(rs.getString("owner_name"));
//                book.setCurrentOwnerName(rs.getString("current_owner_name"));
//                bookList.add(book);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DBException();
//        } finally {
//            DBUtils.close(rs, pstmt, conn);
//        }
//        return bookList;
    }

    // 根据userId查询书籍
    public ArrayList<Book> queryByUserId(int userId) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        String sql = "select * from goods where owner_id=?";
        conn = DBUtils.getConnection();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPicture(rs.getString("picture"));
                book.setOwnerId(rs.getInt("owner_id"));
                book.setCurrentId(rs.getInt("current_owner_id"));
                book.setAuthor(rs.getString("author"));
                book.setDesc(rs.getString("description"));
                book.setOwnerName(rs.getString("owner_name"));
                book.setCurrentOwnerName(rs.getString("current_owner_name"));
                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }

        return bookList;

    }

    // 查询所有书籍
    public ArrayList<Book> queryAll() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        String sql = "SELECT * from goods  WHERE deleted = 0 ORDER BY created_time DESC";
        conn = DBUtils.getConnection();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPicture(rs.getString("picture"));
                book.setOwnerId(rs.getInt("owner_id"));
                book.setCurrentId(rs.getInt("current_owner_id"));
                book.setAuthor(rs.getString("author"));
                book.setDesc(rs.getString("description"));
                book.setOwnerName(rs.getString("owner_name"));
                book.setCurrentOwnerName(rs.getString("current_owner_name"));
                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }

        return bookList;

    }

    public ArrayList<Book> queryMyOutBook(int userId, Pagenation pagenation) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            conn = DBUtils.getConnection();
            pagenation.setTotalCount(this.getMyBookOutCount(userId));
            if (pagenation.getCurretnPage() > pagenation.getPageCount()) {
                pagenation.setCurretnPage(pagenation.getCurretnPage());
            }
            pstmt = (PreparedStatement) conn.prepareStatement(
                    "select b.*,u.user_name as current_owner_name from goods as b left join user as u on b.current_owner_id = u.id where owner_id = "
                            + userId + " and current_owner_id != " + userId + " AND deleted = 0  limit " + pagenation.getOffset() + ","
                            + pagenation.getPageSize() + ";");
            rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPicture(rs.getString("picture"));
                book.setOwnerId(rs.getInt("owner_id"));
                book.setCurrentId(rs.getInt("current_owner_id"));
                book.setAuthor(rs.getString("author"));
                book.setDesc(rs.getString("description"));
                book.setOwnerName(rs.getString("owner_name"));
                book.setCurrentOwnerName(rs.getString("current_owner_name"));
                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }

        return bookList;

    }

    public ArrayList<Book> queryMyInBook(int userId, Pagenation pagenation) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            conn = DBUtils.getConnection();
            pagenation.setTotalCount(this.getMyBookInCount(userId));
            if (pagenation.getCurretnPage() > pagenation.getPageCount()) {
                pagenation.setCurretnPage(pagenation.getCurretnPage());
            }
            pstmt = (PreparedStatement) conn.prepareStatement(
                    "select b.*,u.user_name as current_owner_name from goods as b left join user as u on b.current_owner_id = u.id where  current_owner_id = "
                            + userId + " and owner_id !=" + userId + " AND deleted = 0  limit " + pagenation.getOffset() + ","
                            + pagenation.getPageSize() + ";");
            rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPicture(rs.getString("picture"));
                book.setOwnerId(rs.getInt("owner_id"));
                book.setCurrentId(rs.getInt("current_owner_id"));
                book.setAuthor(rs.getString("author"));
                book.setDesc(rs.getString("description"));
                book.setOwnerName(rs.getString("owner_name"));
                book.setCurrentOwnerName(rs.getString("current_owner_name"));
                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }

        return bookList;

    }

    public ArrayList<Book> queryMyBorrowBook(int userId, Pagenation pagenation) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            conn = DBUtils.getConnection();
            pagenation.setTotalCount(this.getMyBookBorrowCount(userId));
            if (pagenation.getCurretnPage() > pagenation.getPageCount()) {
                pagenation.setCurretnPage(pagenation.getCurretnPage());
            }
            pstmt = (PreparedStatement) conn.prepareStatement(
                    "select b.*,u.user_name as current_owner_name from goods as b left join user as u on b.current_owner_id = u.id where owner_id = "
                            + userId + " and current_owner_id =" + userId + " AND deleted = 0  limit " + pagenation.getOffset() + ","
                            + pagenation.getPageSize() + ";");
            rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPicture(rs.getString("picture"));
                book.setOwnerId(rs.getInt("owner_id"));
                book.setCurrentId(rs.getInt("current_owner_id"));
                book.setAuthor(rs.getString("author"));
                book.setDesc(rs.getString("description"));
                book.setOwnerName(rs.getString("owner_name"));
                book.setCurrentOwnerName(rs.getString("current_owner_name"));
                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }

        return bookList;

    }

    public int getMyBookCount(int userId,BookStatus status) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int mybookCount = 0;
        try {
            conn = DBUtils.getConnection();
            String countSQL = "SELECT count(*) AS mybookCount FROM goods WHERE "+this.getWhereSQL(userId, status.getValue());
            stmt = (PreparedStatement) conn.prepareStatement(countSQL);
            rs = (ResultSet) stmt.executeQuery();
            if (rs.next()) {
                mybookCount = rs.getInt("mybookCount");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, stmt, conn);
        }
        return mybookCount;
    }

    public int getMyBookOutCount(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int mybookCount = 0;
        try {
            conn = DBUtils.getConnection();
            String countSQL = "SELECT count(*) AS mybookCount FROM goods WHERE  owner_id = " + userId
                    + " and current_owner_id != " + userId+ " AND deleted = 0 ;";
            stmt = (PreparedStatement) conn.prepareStatement(countSQL);
            rs = (ResultSet) stmt.executeQuery();
            if (rs.next()) {
                mybookCount = rs.getInt("mybookCount");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, stmt, conn);
        }
        return mybookCount;
    }

    public int getMyBookBorrowCount(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int mybookCount = 0;
        try {
            conn = DBUtils.getConnection();
            String countSQL = "SELECT count(*) AS mybookCount FROM goods WHERE   owner_id = " + userId
                    + " and current_owner_id = " + userId+ " AND deleted = 0 ;";
            stmt = (PreparedStatement) conn.prepareStatement(countSQL);
            rs = (ResultSet) stmt.executeQuery();
            if (rs.next()) {
                mybookCount = rs.getInt("mybookCount");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, stmt, conn);
        }
        return mybookCount;
    }

    public int getMyBookInCount(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int mybookCount = 0;
        try {
            conn = DBUtils.getConnection();
            String countSQL = "SELECT count(*) AS mybookCount FROM goods WHERE   owner_id != " + userId
                    + " and current_owner_id = " + userId+ " AND deleted = 0 ;";
            stmt = (PreparedStatement) conn.prepareStatement(countSQL);
            rs = (ResultSet) stmt.executeQuery();
            if (rs.next()) {
                mybookCount = rs.getInt("mybookCount");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, stmt, conn);
        }
        return mybookCount;
    }

    /*
    // 查询书籍
    private ArrayList<Book> query(int userId, Pagenation pagenation, String status) {
        String sql = "";
        if (status.equals("all")) {
            sql = "";
        } else if (status.equals("out")) {
            sql = "";
        } else if (status.equals("in")) {
            sql = "";
        } else if (status.equals("borrow")) {
            sql = "";
        }

        return null;
    }
*/
    public Book getById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Book book = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "select * from goods as b where id = " + id;
            stmt = (PreparedStatement) conn.prepareStatement(sql);
            rs = (ResultSet) stmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPicture(rs.getString("picture"));
                book.setOwnerId(rs.getInt("owner_id"));
                book.setCurrentId(rs.getInt("current_owner_id"));
                book.setAuthor(rs.getString("author"));
                book.setDesc(rs.getString("description"));
                book.setOwnerName(rs.getString("owner_name"));
                book.setCurrentOwnerName(rs.getString("current_owner_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, stmt, conn);
        }
        return book;
    }

    public void updateDeleted(int id, int deleted) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String sql = "";
            conn = DBUtils.getConnection();
            stmt = (PreparedStatement) conn.prepareStatement("UPDATE goods SET deleted = "+deleted+",updated_time = NOW() WHERE id = "+id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        }finally{
            DBUtils.close(null, stmt, conn);
        }

    }
    
    private String getWhereSQL (int userId,String status){
        String sql ="";
        if("all".equals(status)){
            sql="owner_id = "+ userId + " AND deleted = 0 ";
        }else if("out".equals(status)){
            sql="owner_id = "+ userId + " and current_owner_id != " + userId + " AND deleted = 0 ";
        }else if("in".equals(status)){
            sql="current_owner_id = "+ userId + " and owner_id !=" + userId + " AND deleted = 0 ";
        }else if("borrow".equals(status)){
            sql="owner_id = "+ userId + " and current_owner_id =" + userId + " AND deleted = 0 ";
        }
        return sql;
    }

    public void borrowGoods(Book book, int borrowId) {
        
        String sql = "INSERT INTO borrow_message (goods_id,borrow_id,goods_owner_id,current_owner_id,managed,borrow_time) VALUE(?,?,?,?,?,NOW())";
        conn = DBUtils.getConnection();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, book.getId());
            pstmt.setInt(2, borrowId);
            pstmt.setInt(3, book.getOwnerId());
            pstmt.setInt(4, book.getCurrentId());
            pstmt.setInt(5, 0);
            pstmt.executeUpdate();

            rs = (ResultSet) pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println(id);
                book.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
        
    }
    //借书请求 数
    public int getBorrowCount(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int borrowCount = 0;
        try {
            conn = DBUtils.getConnection();
            String countSQL = "select count(*) as borrowCount from borrow_message bm INNER JOIN user u on u.id = bm.borrow_id inner JOIN goods g on g.id = bm.goods_id where bm.current_owner_id = "+userId+" and bm.managed = '0'";
            stmt = (PreparedStatement) conn.prepareStatement(countSQL);
            rs = (ResultSet) stmt.executeQuery();
            if (rs.next()) {
                borrowCount = rs.getInt("borrowCount");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, stmt, conn);
        }
        return borrowCount;
    }

    public ArrayList<BorrowRequest> queryByBorrowId(int userId) {
        ArrayList<BorrowRequest> borrowRequestList = new ArrayList<BorrowRequest>();
        try {
            conn = DBUtils.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(
                    "select * from borrow_message bm INNER JOIN user u on u.id = bm.borrow_id inner JOIN goods g on g.id = bm.goods_id where bm.current_owner_id ="+userId+" and bm.managed = 0");
            rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                BorrowRequest borrowRequest = new BorrowRequest();
                borrowRequest.setBorrowRequestId(rs.getInt("borrow_message_id"));
                borrowRequest.setName(rs.getString("name"));
                borrowRequest.setPicture(rs.getString("picture"));
                borrowRequest.setOwnerId(rs.getInt("owner_id"));
                borrowRequest.setCurrentId(rs.getInt("current_owner_id"));
                borrowRequest.setAuthor(rs.getString("author"));
                borrowRequest.setDesc(rs.getString("description"));
                borrowRequest.setOwnerName(rs.getString("owner_name"));
                borrowRequest.setCurrentOwnerName(rs.getString("current_owner_name"));
                borrowRequest.setBorrowUserId(rs.getInt("borrow_id"));
                borrowRequest.setBorrowUserName(rs.getString("user_name"));
                borrowRequest.setBorrowUserPhone(rs.getString("mobile_number"));
                borrowRequest.setBorrowTime(rs.getDate("borrow_time"));
                borrowRequestList.add(borrowRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
        return borrowRequestList;
    }

    public void updateBorrowMessageById(int borrowMessageId) {
        String sql = "update borrow_message set managed = 1 where borrow_message_id = "+borrowMessageId;

        try {
            conn = DBUtils.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
    }

    public void updateBGoodsById(int goodsId, int borrowUserId, String borrowUserNameStr) {
        String sql = " update goods set current_owner_id = ? , current_owner_name = ? where id = "+goodsId;

        try {
            conn = DBUtils.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, borrowUserId);
            pstmt.setString(2, borrowUserNameStr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
    }

    public void insertGoodsHistory(int goodsId, int borrowUserId) {
        String sql = "insert into goods_history(goods_id,borrow_time,borrow_user_id) VALUES(?,NOW(),?)";
        conn = DBUtils.getConnection();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, goodsId);
            pstmt.setInt(2, borrowUserId);
            pstmt.executeUpdate();
            rs = (ResultSet) pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
    }

    public ArrayList<Integer> queryBorrowRequestingBooks(int userId) {
        ArrayList<Integer> borrowRequestBookList = new ArrayList<Integer>();
        String sql = "select g.id from goods g inner JOIN borrow_message bm on bm.goods_id = g.id WHERE bm.managed = 0  and bm.borrow_id = "+userId;
        conn = DBUtils.getConnection();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            rs = (ResultSet) pstmt.executeQuery();
            while (rs.next()) {
                Integer id =rs.getInt("id");
                borrowRequestBookList.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }

        return borrowRequestBookList;
    }
}
