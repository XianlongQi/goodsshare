<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.qixl.goodsshare.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%User user = (User)session.getAttribute("USER"); %>
    Welcome:<%=user.getUserName() %>
</body>
</html>