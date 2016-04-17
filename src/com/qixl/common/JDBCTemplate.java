package com.qixl.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.qixl.goodsshare.exception.DBException;
import com.qixl.goodsshare.model.Book;
import com.qixl.goodsshare.model.Pagenation;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.util.DBUtils;

public class JDBCTemplate<T> {

    public ArrayList<T> query(String sql,JDBCCallBack<T> jdbcCallBack) {
        Connection conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<T> data = new ArrayList<T>();
        try {
            conn = DBUtils.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            rs = (ResultSet) pstmt.executeQuery();
            while(rs.next()){
                T object = jdbcCallBack.rsToObject(rs);
                data.add(object);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtils.close(rs, pstmt, conn);
        }
        return data;
    }
    
    
    public T queryOne(String sql,JDBCCallBack<T> jdbcCallBack) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        T data = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            jdbcCallBack.setParams(pstmt);
            rs = (ResultSet) pstmt.executeQuery();
            if(rs.next()){
                data = jdbcCallBack.rsToObject(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally{
                DBUtils.close(rs, pstmt, conn);
        }
        
        return data;
    }
    
    /*
    public T queryOne(String sql,JDBCCallBack<T> jdbcCallBack) {
        List<T> data = query(sql,jdbcCallBack);
        if(data != null && data.isEmpty()){
            return data.get(0);
        }
        
        return null;
    }
*/
}
