<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="java.util.ArrayList" %>
<%@ page import="com.qixl.goodsshare.model.BorrowRequest" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<!-- Table 内容开始-->
            <div class="table">
                 <!-- Table Header开始-->
                <div class="header">
                  <ul>
                    <li class="index">编号</li>
                    <li class="book_info" >书籍</li>
                    <li class="book_status">借书人</li>
                    <li class="borrow_operator">借用时间</li>
                    <li class="borrow_operator">操作</li>
                    
                  </ul>
                </div>
                <!-- Table Header结束-->
                
                <div class="content">
                
                  <!-- Table 行内容开始-->
                 <%
                     ArrayList<BorrowRequest> borrowRequests = null;
                    int i = 0;
                    borrowRequests = (ArrayList<BorrowRequest>)request.getAttribute("borrowRequests");
                    if(!borrowRequests.isEmpty()){
                    for(BorrowRequest borrowRequest : borrowRequests){
                        i++;
                %>
                  <div>
                    <ul>
                      <li class="index"><%=i %></li>
                      <li class="book_info">
                         <img class="book_img" src="<%=request.getContextPath()%><%=borrowRequest.getPicture()%>">
                         <label class="book_title" title=""><%=borrowRequest.getName()%></label>
                         <label class="book_author" title=""><%=borrowRequest.getAuthor()%></label>
                         
                      </li>
                      <input type="hidden" value="<%=borrowRequest.getBorrowRequestId()%>">
                     <li class="book_status">
                          <img src="<%=request.getContextPath()%>/static/images/ICN_Holder_Blue_30x30.png">
                          <label class="holder_name"><%=borrowRequest.getBorrowUserName()%></label>
                          <label class="holder_phone"><%=borrowRequest.getBorrowUserPhone()%></label>
                      </li>
                    <li class="borrow_operator">
                          <label style="line-height:80px;"><%=borrowRequest.getBorrowTime().toString()%></label>
                      </li>
                      <li class="borrow_operator">
                         <div class="short_desc">
                          <span onClick="borrowOut('<%=borrowRequest.getBorrowRequestId()%>','<%=borrowRequest.getBorrowUserId()%>','<%=borrowRequest.getBorrowUserName()%>','<%=borrowRequest.getGoodsId()%>')" style="text-align: center;cursor: pointer;background-color:#026dd5;width:50px;display:block;float:left;color:#ffffff;margin-top:30px;line-height:20px;">借出</span>
                          <span style="text-align: center;margin-top: 30px;cursor: pointer;display: block;float: left;margin-left: 20px;">忽略</span>
                         </div>
                      </li>
                    </ul>
                  </div>
                  <!-- Table 行内容结束-->
                  <%}
                }%>
                 </div>
                 
                
            </div>
             <!-- Table 内容结束-->
</body>
</html>