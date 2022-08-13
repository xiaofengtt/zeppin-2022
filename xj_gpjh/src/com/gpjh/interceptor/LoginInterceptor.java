package com.gpjh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected String doIntercept(ActionInvocation ai) throws Exception {
          
          //定义ActionContext 用来获取session ,request, response对象
          //以下是在普通类中获取HttpServletRequest和HttpServletResponse的方法
          ActionContext actionContext = ActionContext.getContext();
          HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST) ;
          HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
          String path = request.getContextPath();
          String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
          
          //获取session，检查用户是否已经登录，注意此处的return null;很主要，防止页面多次访问
          String user = (String)actionContext.getSession().get("ID");
          if(user == null){
              //return "login" 如果没有用户登录则转向此页面，basePath用来防止路径出错
              response.sendRedirect(basePath+"login.action");
              return null;
          }
          return ai.invoke();
    }
}