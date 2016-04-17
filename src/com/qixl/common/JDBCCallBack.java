package com.qixl.common;

import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public interface JDBCCallBack<T> {

    T rsToObject(ResultSet rs) throws SQLException;

    void setParams(PreparedStatement pstmt) throws SQLException;

}
