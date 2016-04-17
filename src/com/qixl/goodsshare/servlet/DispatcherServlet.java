package com.qixl.goodsshare.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qixl.common.ActionConfig;
import com.qixl.common.ModelAndView;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String,ActionConfig> actionConfigs = new HashMap<String,ActionConfig>(); 

	public DispatcherServlet() {
        super();
        actionConfigs.put("savebook.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","save"));
        actionConfigs.put("deletebook.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","delete"));
        actionConfigs.put("editbook.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","edit"));
        actionConfigs.put("mybook.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","mybook"));
        actionConfigs.put("allbook.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","allbook"));
        actionConfigs.put("login.action", new ActionConfig("com.qixl.goodsshare.Controller.UserController","login"));
        actionConfigs.put("saveLogin.action", new ActionConfig("com.qixl.goodsshare.Controller.UserController","saveLogin"));
        actionConfigs.put("logout.action", new ActionConfig("com.qixl.goodsshare.Controller.UserController","logout"));
        actionConfigs.put("goodsdetail.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","goodsDetail"));
        actionConfigs.put("borrowGoods.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","borrowGoods"));
        actionConfigs.put("borrowRequestDetail.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","borrowRequestDetail"));
        actionConfigs.put("borrowOut.action", new ActionConfig("com.qixl.goodsshare.Controller.BookController","borrowOut"));
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String requestedUri =  uri.substring(request.getContextPath().length() + 1);
		if(requestedUri == null ||requestedUri.equals("")) {
		    requestedUri = "login.action";
		}
		System.out.println("requestedUri："+requestedUri);
		ActionConfig actionConfig = actionConfigs.get(requestedUri);
		if(actionConfig != null){
		    String clsName = actionConfig.getClsName();
		    System.out.println(clsName);
		    
		    try {
                Class<?>[] param = new Class[2];
                param[0] = HttpServletRequest.class;
                param[1] = HttpServletResponse.class;
                
                Class<?> cls = Class.forName(clsName);
                Object controller = cls.newInstance();
                
                String methodName = actionConfig.getMethodName();
                Method method = cls.getMethod(methodName, param);
                
                Object [] objects = new Object[2];
                objects[0] = request;
                objects[1] = response;
                
                method.invoke(controller,objects);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500);
            }
		}else{
		    response.sendError(404);
		}		
//	    response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
