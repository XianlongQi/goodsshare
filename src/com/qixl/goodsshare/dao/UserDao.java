package com.qixl.goodsshare.dao;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.qixl.common.JDBCCallBack;
import com.qixl.common.JDBCTemplate;
import com.qixl.goodsshare.model.User;
import com.qixl.goodsshare.util.DBUtils;

public class UserDao {

    public User getUserByName(String userName){
        if(userName == null || userName.equals("")) {
            return null;
        }
        JDBCTemplate<User> jdbcTemplate = new JDBCTemplate<User>();
        return jdbcTemplate.queryOne("SELECT * FROM user WHERE user_name = ?", new JDBCCallBack<User>() {
            
            @Override
            public void setParams(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userName);                
            }
            
            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
        
    }
}
