<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
//退出页面不需要写退出逻辑，Filter根据配置的logoutUrl，遇到退出url请求时，会执行退出动作并处理相关逻辑
session.removeAttribute("card_id");
session.removeAttribute("sex" );
session.removeAttribute("birth");
session.removeAttribute("address");
session.removeAttribute("name");
session.removeAttribute("card_type");
session.removeAttribute("issueDate");
session.removeAttribute("period");
session.removeAttribute("issuePlace");
session.removeAttribute("nation");
session.removeAttribute("inputstream");
//业务系统的退出逻辑处理写在com.enfo.trust.utility.TrustLoginFilter.doLogout方法中，并重载父类该方法
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
//对于启用单点认证的情况，以下跳转其实不会起作用
response.sendRedirect(basePath); //跳转到当前web的根
%>
