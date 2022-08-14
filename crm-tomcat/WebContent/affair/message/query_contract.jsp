<%@ page language="java" contentType="application/json; charset=GBK" pageEncoding="GBK" %>
<%@ page import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %> %>
<%@ include file="/includes/operator.inc" %>
<% 
	Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(-1));
	String s = Argument.getApplyContract2(input_bookCode, product_id, input_operatorCode, "");

	out.clear();	
	out.print(s); 
	out.flush();
	//out.close();
	return;
%>