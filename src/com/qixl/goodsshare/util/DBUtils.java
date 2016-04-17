package com.qixl.goodsshare.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class DBUtils {

    public static Connection getConnection () {
        Connection conn = null;
      //1:加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //2、得到 Connection对象  
        String jdbcUrl = PropertiesUtils.getProperty("jdbcUrl");
        try { 
            conn = (Connection) DriverManager.getConnection(jdbcUrl,"root","");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return conn;
    }
    
    public static void close(ResultSet rs,PreparedStatement pstmt,Connection conn){
        try {
            if(rs != null){
                rs.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
