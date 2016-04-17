<%@page import="javax.jws.soap.SOAPBinding.Use"%>
<%@page import="com.qixl.goodsshare.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.qixl.goodsshare.model.Book" %>
<%@ page import="com.qixl.goodsshare.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的物品</title>
<link href="<%=request.getContextPath()%>/static/style/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/allbook.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/goodsDetails.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.0.min.js"></script>
</head>
<body>

<div class="disabled_screen" id="deleteBookMark" style="display:none">
      
    </div>
     <div class="pop_win" id="deleteBookWin" style="display:none">
         <div class="title">确定借用吗？</div>
         <div class="action">
           <div class="action_item" onclick="confirmDeleteBook()">确定</div>
           <div class="action_item" style="margin-left: 40px;" onclick="cancelDeleteBook()">取消</div>
         </div>
      </div>
      
    
	<div class="warpper" id="mybookPage">
	    <div id="headerWarpper">
            <div id="header">
                <div class="logo" onclick="toIndex('<%=request.getContextPath()%>');">物品分享</div>
	            <div class="menu">
	               <ul>
	                 <li><a href="#" id="btn_mybook" onclick="toIndex('<%=request.getContextPath()%>');" >我的物品</a></li>
	                 <li><a href="<%=request.getContextPath()%>/allbook.action" style="background-color: #2B6C85;">全部物品</a></li>
	               </ul>
	            </div>
				<div class="tool" >
	                <input name="searchKeyword" type="text">
	                <img class="search_button" src="<%=request.getContextPath()%>/static/images/BTN_Search_30x30.png"></img>
	                <img class="role_switch_button" onClick="logout()" src="<%=request.getContextPath()%>/static/images/BTN_SwitchRole_User_35x35.png"></img>
                </div>
			</div>
		</div>
		
		<div id="breadcrumb">
            <ul>
                   <li>当前位置 &nbsp;&nbsp;:</li>
                   <li><a href="#">全部物品</a></li>
             </ul>
        </div>
      
		
        <div class="main" id="mybookMain">
	        <div id="left">
	           <div id="leftBinner" class="leftBinner">
		           	<div class="leftTitle">
		           		全部物品
		           	</div>
		           	<div>
		           		<ul>
		           			<li><a href="#">计算机类</a></li>
		           			<li><a href="#">设计</a></li>
		           			<li><a href="#">管理</a></li>
		           			<li><a href="#">人文</a></li>
		           			<li><a href="#">其他类</a></li>
		           		</ul>
		           	</div>
	           	</div>
	           </div>
	        
	
	        <div id="content">
	        <% ArrayList<Book> bookList = (ArrayList<Book>)request.getAttribute("books");
	        ArrayList<Integer> borrowRequestBookList = (ArrayList<Integer>)request.getAttribute("borrowRequestBookList");
	        User user = (User)request.getAttribute("user");
	        if(bookList != null){
	           for(Book book : bookList){
	        %>
	            <!--展示书信息开始 -->
	            <div class="bookShow">
	            	<div class="bookImage">
	            	   <img src="<%=request.getContextPath()%>/static/book/Book_Java_BCSX.jpg" />
	            	</div>
	            	<input type="hidden" id="<%=book.getId()%>" value="<%=book.getId()%>">
	            	<div class="bookName" ><%=book.getName() %></div>
	            	<div class="bookAuthor"><%=book.getAuthor() %></div>
	            	<div class="bookBottom">
	            	   <img style="float:left;" src="<%=request.getContextPath()%>/static/images/ICN_Holder_Blue_30x30.png">
	            	   <span class="bookBottom_text"><%=book.getOwnerName()%></span>
	            	</div>
	            	<%if (borrowRequestBookList.contains(book.getId()) ) {%>
	            	  <div style="">已借用，待处理</div>
	            	<%} else if (book.getCurrentId() == user.getId() && book.getOwnerId() == user.getId()) {%>
	            	  <div style="">我的书籍</div>
	            	<%}else if (book.getCurrentId() != user.getId() && book.getOwnerId() == user.getId()) { %>
	            	  <div style="">已借出</div>
	            	<%}else if (book.getCurrentId() == user.getId() && book.getOwnerId() != user.getId()){%>
	            	已借用
	            	<%} else { %>
	            	  <div style="background-color:#118200;color:#ffffff"  onclick="showDeleteBookWin('<%=book.getId()%>')" >请求借用</div>
	            	<%} %>
	            </div>
	            <!--展示书信息结束 -->
	            <%
	                } } 
	            %>
	             
	             <%
	               Book book = (Book)request.getAttribute("book");
		           if(book != null){
		               String bookDesc = book.getDesc();
                   %>
	            <div id="detail" style="display:none;">
                    <div class="detail_left">
                       <div class="detail_goodsImage">
                       
                       </div>
                    </div>
                    <div class="detail_right">
                       <div class="detail_title"><%=book.getName() %></div>
                       <div class="hor"></div>
                       <div class="hor">
                           <span>分类：</span><span id="category"></span>
                       </div>
                       <div class="hor">
                           <span>简介：</span><span id="introduction"><%=book.getDesc() %></span>
                       </div>
                       <div class="hor">
                           <span>共享者：</span><span id="introduction"></span>
                       </div>
                       <div class="hor">
                           <span>当前持有者：</span><span id="introduction"></span>
                       </div>
                       <div class="btn_borrow">
                           <div class="btn_borrow">请求借用</div>
                       </div>
                    </div>
                </div>
                <%}%>
	        </div>
	     </div>
	</div>
	
	<script>
		$(document).ready(function(){
		  $("#btn_mybook").click(function(){
			  $.ajax({
				  type: "GET",
				  url: "<%=request.getContextPath()%>"+"/mybook.action",
				  dataType: "script",
				  success:function (){
					  alert('sss');
					  }
				});
			    
		  });
		  
		  /*
		  $(".bookShow").click(function(){
			  //var detail = $("#detail");
			  //detail.display = none;
			  //detail.attr("display","block");
			  $("#detail").show();
             //alert(detail);
                
          });
		  */
		 
		});
		
		
			 
			 function showGoodDetail(id){
	             $.ajax({
	                   type: "GET",
	                   url: "<%=request.getContextPath()%>"+"/goodsdetail.action",
	                   data: "id="+id,
	                   success: function(msg){
	                       //alert(msg);
	                       alert("sss");
	                       $("#detail").show();
	                   }
	                });
			 }
	             var deleteBookId;
	             function showDeleteBookWin(id){
	                 var deleteBookMarkObj = document.getElementById("deleteBookMark");
	                 deleteBookMarkObj.style.display = "block";
	                 var deleteBookWinObj = document.getElementById("deleteBookWin");
	                 deleteBookWinObj.style.display = "block";
	                 deleteBookId = id;
	             }
	             function cancelDeleteBook() {
	                 var deleteBookMarkObj = document.getElementById("deleteBookMark");
	                 deleteBookMarkObj.style.display = "none";
	                 var deleteBookWinObj = document.getElementById("deleteBookWin");
	                 deleteBookWinObj.style.display = "none";
	             }
	             
	             function confirmDeleteBook() {
	               var queryString = location.search;
	               if (queryString.indexOf("?") != -1) {
	                   queryString = queryString.substr(1);
	                }
	                alert(queryString);
	                console.log(queryString);
	                location.href="<%=request.getContextPath()%>/borrowGoods.action?goodsId=" + deleteBookId + "&" + queryString;
	             } 
	             
	             function logout(){
	                 location.href="<%=request.getContextPath()%>/logout.action";
	             }
         
		 
	</script>

</body>
</html>