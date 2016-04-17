<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.qixl.goodsshare.model.Book" %>
<%@ page import="com.qixl.goodsshare.util.StringUtils" %>
<%@ page import="com.qixl.goodsshare.model.Pagenation" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的物品</title>
<link href="<%=request.getContextPath()%>/static/style/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/static/style/mybook.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.0.min.js"></script>
</head>
<body>

<div class="disabled_screen" id="deleteBookMark" style="display:none">
      
    </div>
     <div class="pop_win" id="deleteBookWin" style="display:none">
         <div class="title">确定删除？</div>
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
	                 <li><a href="#">我的物品</a></li>
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
                   <li><a href="#">我的物品</a></li>
             </ul>
        </div>
        <% 
            String successFlashMessage = (String)session.getAttribute("SUCCESS_FLASH_MESSAGE");
            successFlashMessage = successFlashMessage == null ? "" : successFlashMessage;
            String isDisplayFlashMessage = "";
            if (StringUtils.isEmpty(successFlashMessage)) {
                isDisplayFlashMessage = "style='display:none'";
            }
            
        
        %>
      <div id="sucessFlashMessage" <%=isDisplayFlashMessage%>>
        <%
            
            out.write(successFlashMessage);
            session.removeAttribute("SUCCESS_FLASH_MESSAGE");
            if (!successFlashMessage.equals("")) {
                
                %>
                <script>
                    setTimeout(function () {
                    	document.getElementById("successFlashMessage").style.display = "none";
                    }, 3000);
                </script>
                <%
            }
        %>
      </div>
		
      <div class="main" id="mybookMain">
        <div id="left">
          
           <div class="user_information">
           
               <div class="user_header">
                   <img src="<%=request.getContextPath()%>/static/images/MyBooks_IMG_DefaultAvatar_80x80.png"/>
                   <div class="user_name">齐显龙</div>
               </div>
               
               <div class="user_basis">
                   <div class="tip">
                    <label>基本资料</label><img src="<%=request.getContextPath()%>/static/images/BTN_Edit_20x20.png"/>
                   </div>
                   <ul>
                       <li><label>部门&nbsp;:&nbsp;</label><span>电子政务</span></li>
                       <li><label>电话&nbsp;:&nbsp;</label><span>18201659916</span></li>
                       <li><label>邮箱&nbsp;:&nbsp;</label><span>qxlstruggle@163.com</span></li>
                       <li><label>地址&nbsp;:&nbsp;</label><span>海淀区学清路</span></li>
                   </ul>
               </div>
	           <div class="user_message">
	              <div class="my_message">
		              <label>我的消息</label>
		              <label class="message_tip"></label>
		              <label class="tip_count">5</label>
	              </div>
	              <div class="my_request" onClick="showBorrowRequuest()">
	                  <label style="cursor: pointer;">借用请求</label>
                      <label class="message_tip"></label>
                      <label class="tip_count"><%=request.getAttribute("borrowCount") %></label>
	              </div>
	           </div>
           </div>
        </div>

        <div id="content">
            <div id="myBookTab">
               <ul>
                  <%String status = (String)request.getAttribute("status"); %>
                   <li><a href="<%=request.getContextPath()%>/mybook.action?status=all">全部</a><span><%=request.getAttribute("allCount") %></span></li>
                   <li><a href="<%=request.getContextPath()%>/mybook.action?status=out" >已借出</a><span><%=request.getAttribute("outCount") %></span></li>
                   <li><a href="<%=request.getContextPath()%>/mybook.action?status=borrow" style="border-right: 1px solid #8EBACA;">未借出</a><span><%=request.getAttribute("borrowCount") %></span></li>
                   <li><a href="<%=request.getContextPath()%>/mybook.action?status=in" >借入</a><span><%=request.getAttribute("inCount") %></span></li>
                   
               </ul>
                <div> <a href="<%=request.getContextPath()%>/editbook.action">新增物品</a></div>
             </div>
            <!-- Table 内容开始-->
            <div class="table">
                 <!-- Table Header开始-->
                <div class="header">
                  <ul>
                    <li class="index">编号</li>
                    <li class="book_info" >书籍</li>
                    <%if(status.equals("in")){ %>
                    <li class="book_sharer">共享者</li>
                    <%}else { %>
                    <li class="book_status">状态</li>
                    <li class="book_operator">操作</li>
                    <%} %>
                    
                    <li class="book_history">借用历史</li>
                  </ul>
                </div>
                <!-- Table Header结束-->
                
                <div class="content">
                
                  <!-- Table 行内容开始-->
                 <%
	                 ArrayList<Book> books = null;
	                int i = 0;
	                books = (ArrayList<Book>)request.getAttribute("books");
	                if(!books.isEmpty()){
	                for(Book book : books){
	                    i++;
                %>
                  <div>
                    <ul>
                      <li class="index"><%=i %></li>
                      <li class="book_info">
                         <img class="book_img" src="<%=request.getContextPath()%><%=book.getPicture()%>">
                         <label class="book_title" title=""><%=book.getName()%></label>
                         <label class="book_author" title=""><%=book.getAuthor()%></label>
                         
                      </li>
                    <%if(status.equals("in")){ %>
                     <li class="book_sharer">
                          <img src="<%=request.getContextPath()%>/static/images/ICN_Holder_Blue_30x30.png">
                          <label class="holder_name"><%=book.getCurrentOwnerName()%></label>
                      </li>
                    <%}else { %>
                    <li class="book_status">
                          <img src="<%=request.getContextPath()%>/static/images/ICN_Holder_Blue_30x30.png">
                          <label class="holder_name"><%=book.getOwnerName() %></label>
                          <%if (book.getCurrentId() == book.getOwnerId()) { %>
                          <label class="status_dec">[未借出] </label>
                          <%} else{%>
                          <label class="status_dec">[已借出] </label>
                         <%} %>
                      </li>
                      <li class="book_operator">
                        <div class="cell">
                        <%if(book.getCurrentId() == book.getOwnerId()) { %>
                           <a class="update_button" href="<%=request.getContextPath()%>/editbook.action?id=<%=book.getId()%>">更新</a>
                           <div class="update_button" onclick="showDeleteBookWin(<%=book.getId() %>,'<%=status%>')">删除</a>
                           <%}else{ %>
                           <a class="update_button" href="<%=request.getContextPath()%>/editbook.action?id=<%=book.getId()%>">更新</a>
                           <%} %>
                        </div>

                      </li>
                      <%} %>
                      <li class="book_history">
                         <div class="short_desc">
                          2011年11月4 &nbsp;:&nbsp;借给zack.liu <br />
                          2011年12月4 &nbsp;:&nbsp;借给billy.he <br />
                          2011年12月4 &nbsp;:&nbsp;录入系统 <br />
                         </div>
                         <img class="read_more" src="<%=request.getContextPath()%>/static/images/ICN_ReadMore_30x10.png"/>
                      </li>
                    </ul>
                  </div>
                  <!-- Table 行内容结束-->
                  <%}
                }%>
                 </div>
                 
                
            </div>
             <!-- Table 内容结束-->
             
           <!-- 分页开始  -->
           <%Pagenation pagenation = (Pagenation)request.getAttribute("pagenation"); %>
          <script>
               function pagenationGo(status) {
            	   var pageCount = document.getElementById("currentPage").value;
            	   /*
            	   if(!isInteger(pageCount)){
            		   var errorFlashMessgeObj =document.getElementById("errorFlashMessage");
            		   errorFlashMessgeObj.innerHTML = "请输入正确的页码";
            		   document.getElementById("errorFlashMessge").style.display = "block";
            		   setTimeout(function (){document.getElementById("errorFlashMessge").style.display = "none";}, 3000);
            		   return;
            	   }
            	   */
            	   location.href="<%=request.getContextPath()%>/mybook.action?currentPage=" + pageCount+"&status="+status;
               }
          
          </script>
           <div class="pagination">
            <span class="first_page">
            
            <%if(pagenation.isFirstPage()){ %>
              <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_FirstPage_Disable_20x20.png"/>
            <%} else{%>
            <a href="<%=request.getContextPath()%>/mybook.action?currentPage=1&status=<%=status %>">
               <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_FirstPage_Normal_20x20.png"/>
            </a>
            <%} %>
            </span>
            <span class="pre_page">
               <%if(!pagenation.isFirstPage() && pagenation.getCurretnPage() > 1) {%>
                 <a href="<%=request.getContextPath()%>/mybook.action?currentPage=<%=pagenation.getCurretnPage() - 1 %>">
                    <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_PrePage_Normal_20x20.png"/>
                 </a>
                 <%} else{ %>
                    <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_PrePage_Disable_20x20.png"/>
                 <%} %>
               
            </span>
            <span class="current_page"> 第<%=pagenation.getCurretnPage()%>页</span>
            <span class="next_page">
             <%if(!pagenation.isLastPage() && pagenation.getCurretnPage() >= 1) {%>
               <a href="<%=request.getContextPath()%>/mybook.action?currentPage=<%=pagenation.getCurretnPage() + 1 %>&status=<%=status %>">
                    <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_NextPage_Normal_20x20.png"/>
               </a>
             <%}else{ %>
                <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_NextPage_Disable_20x20.png"/>
             <%} %>
            </span>
            <span class="last_page">
               <%if(pagenation.isLastPage()) {%>
               <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_LastPage_Disable_20x20.png"/>
               <%}else { %>
               <a href="<%=request.getContextPath()%>/mybook.action?currentPage=<%=pagenation.getPageCount()%>&status=<%=status %>">
               <img src="<%=request.getContextPath()%>/static/images/MyBooks_BTN_LastPage_Normal_20x20.png"/>
               </a>
              <%} %>
            </span>
            <span class="total_page"> 共<%= pagenation.getPageCount()%>页</span>
            <span class="go_desc">到第</span>
              <input type="text" class="go_input" id="currentPage" name="currentPage" value="<%=pagenation.getCurretnPage() %>"/>
            <span class="go_page"> 页</span>
            <div class="go_button" onclick="pagenationGo('<%=status %>')">GO</div>
          </div>
          
           <!-- 分页结束  -->
        
     </div>
     </div>
	</div>

	<script>
	var deleteBookId;
	function showDeleteBookWin(id,status){
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
       location.href="<%=request.getContextPath()%>/deletebook.action?id=" + deleteBookId + "&" + queryString;
    }
    
    
    function showBorrowRequuest() {
    	 $.ajax({
             type: "GET",
             url: "<%=request.getContextPath()%>"+"/borrowRequestDetail.action",
             data: "borrowid=2",
             success: function(date){
                 //alert(date);
                 $("#content").html(date);
             }
          });
    }
    
    function logout(){
    	location.href="<%=request.getContextPath()%>/logout.action";
    }
    
    function borrowOut(borrowMessageId,borrowUserId,borrowUserName,goodsId){
    	//var borrowMessageId = $("")
    	//alert(borrowMessageId+","+borrowUserId+","+borrowUserName+","+goodsId)
    	
    	 
    	$.ajax({
             type: "GET",
             url: "<%=request.getContextPath()%>"+"/borrowOut.action",
             data: "borrowMessageId=" + borrowMessageId+"&borrowUserId="+borrowUserId+"&borrowUserName="+borrowUserName+"&goodsId="+goodsId,
             success: function(date){
                 //alert("借出成功");
                 
                 window.status='Hello' ;
                 setTimeout('clearWord()', 3000) ;
                 
                 showBorrowRequuest();
                 //$("#content").html(date);
             }
          });
    }
    
    function clearWord(){
    	window.status="";
    }
    
	</script>	

</body>
</html>