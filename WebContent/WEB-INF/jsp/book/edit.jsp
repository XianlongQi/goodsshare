<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.qixl.goodsshare.model.Book"  %>
<%@ page import="com.qixl.goodsshare.util.StringUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的图书</title>
<link href="<%=request.getContextPath()%>/static/style/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/editbook.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
</head>
<body>

     <div class="warpper" id="editbookPage">
        <div id="headerWarpper">
            <div id="header">
                <div class="logo" onclick="toIndex('<%=request.getContextPath()%>');">图书分享</div>
                <div class="menu">
                   <ul>
                     <li><a href="#">我的图书</a></li>
                     <li><a href="#" style="background-color: #2B6C85;">全部图书</a></li>
                   </ul>
                </div>
                <div class="tool" >
                    <input name="searchKeyword" type="text">
                    <img class="search_button" src="<%=request.getContextPath()%>/static/images/BTN_Search_30x30.png"></img>
                    <img class="role_switch_button" src="<%=request.getContextPath()%>/static/images/BTN_SwitchRole_User_35x35.png"></img>
                </div>
            </div>
        </div>
        <%Book book = (Book)request.getAttribute("book"); %>
        <div id="breadcrumb">
            <ul>
                   <li>当前位置 &nbsp;&nbsp;:</li>
                   <li><a href="#">
                   <%if(book.getId() > 0){ %>
                                                         修改图书
                   <%}else{ %>
                                                          创建图书
                   <%} %>
                                                        
                   </a></li>
             </ul>
        </div>
      
      <div class="main" id="editBookMain">
        <form id="editBook" method="POST" action="<%=request.getContextPath()%>/savebook.action" >
         <div class="info_area">
             <div class="title">1、上传图书封面照片</div>
             <div class="show_image"></div>
          </div>
         <div class="info_area">
             <div class="title">2、填写书籍资料</div>
             
             <ul>
                   <li>
                     <input type="hidden" name="id" value="<%=book.getId() %>" />
                     <label>书名&nbsp;:&nbsp;&nbsp;&nbsp;</label>
                     <span><input type="text" id="name" name="name" style="width: 410px" value="<%=StringUtils.htmlEncode(book.getName()) %>"/></span>
                     <span class="error_message" id="name_error_message" >书名不能为空！</span>
                   </li>
                   <li>
                     <label>作者&nbsp;:&nbsp;&nbsp;&nbsp;</label>
                     <span><input type="text" id="author" name="author" value="<%=book.getAuthor() %>"/></span>
                     <span class="error_message" id="author_error_message">作者不能为空！</span>
                     </li>
                   <li><label>简介&nbsp;:&nbsp;&nbsp;&nbsp;</label>
                       <span><textarea rows="5" cols="5" name="desc"><%=book.getDesc() %></textarea>&nbsp;&nbsp;(选题)</span>
                   </li>
                  
             </ul>
             <div class="button" onclick="saveBook()">保存图书</div>
          </div>
         </form>
     </div>
     
  </div>
<script>
    function saveBook(){
        var editBookFormObj = document.getElementById("editBook");
        editBookFormObj.submit();
    }

</script>
</body>
</html>