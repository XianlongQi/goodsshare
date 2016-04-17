<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "com.qixl.goodsshare.Constants" %>

<!DOCTYPE html >
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link href="<%=request.getContextPath()%>/static/style/reset.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/static/style/login.css" rel="stylesheet" type="text/css">
  </head>
  <style>
  .red{color:red;}
  </style>
<body onkeydown="keyLogin();">
    <%String tipMessage = (String)request.getAttribute(Constants.TIP_MESSAGE);
    String visibility = "hidden";
    if(tipMessage != null){
        visibility = "visible";
         
    } %>
    <% 
        Map<String,String> errorMap = (Map<String,String>)request.getAttribute(Constants.ERROR_MESSAGE);
        if(errorMap==null){
            errorMap = new HashMap<String,String>();
        }
    %>
    
    <div class="login_title">奇文共赏析</div>
    <div class="login_form">
       <div class="logo"></div>
       <div class="title">物品分享系统</div> 

       <form action="<%=request.getContextPath() %>/saveLogin.action" method="POST" id="loginForm">
            <div class="line">
                <label>用户名</label>
                <input type="text" name="userName" id="userName" value="qixl"/>
                <input type="hidden" name="go" value="<%=request.getParameter("go")%>" />
                <input type="hidden" name="queryString" id="queryString" value="" />
            </div>
            <div class="line" style="margin-top:20px;">
                <label>密码</label>
                <input type="text" name="password" id="password" value="123"/>
            </div>
            <div class="line" id="errorMsg" style="visibility:<%= visibility %>;margin-top:10px;margin-left: 110px;color:red;font-size:14px">
            <%= tipMessage %>
            </div>
            <div class="button" id="btn_login" onclick="login()">登录</div>
       </form>
    </div>
    <div class="login_footer">
        Copyright @ 2016 QiXianLong . All Rights Reserved
    </div>
     


      
    <script>
        function login(){
        	
            var loginFormObj = document.getElementById("loginForm");
            
            var userName = document.getElementById("userName").value;
            var password = document.getElementById("password").value;
            var errorMsg = "";
            var isSubmit = true;
            var userNameIsNull = false;
            var passwordIsNull = false;
            if (!userName) {
                //document.getElementById("tip_userName").innerHTML = "请输入用户名";
                errorMsg = "请输入用户名";
                document.getElementById("userName").style.border = "1px dashed #EB340A";
                userNameIsNull = true;
                isSubmit = false;
            }
            if (!password) {
                //document.getElementById("tip_password").innerHTML = "请输入密码";
                errorMsg = "请输入密码";
                document.getElementById("password").style.border = "1px dashed #EB340A";
                passwordIsNull = true;
                isSubmit = false;
            }
            if (userNameIsNull && passwordIsNull) {
            	errorMsg = "请输入用户名和密码";
            }
            
            if (!isSubmit) {
                document.getElementById("errorMsg").innerHTML = errorMsg;
                document.getElementById("errorMsg").style.visibility = "visible";
                return;
            }
            var userName = document.getElementById("userName").value;
            loginFormObj.submit();
        }
        
        function  keyLogin() {
	        if (event.keyCode==13) {                        
	        	//按Enter键的键值为13  
	        	     document.getElementById("btn_login").click(); 
	        }
        }
    </script>
</body>
</html>
<script>
var queryString = location.hash;
document.getElementById("queryString").value=queryString;
console.log(document.getElementById("queryString").value);
</script>