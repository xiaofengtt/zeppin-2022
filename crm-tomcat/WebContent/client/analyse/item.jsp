<?xml version="1.0" encoding="gbk" ?>
<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@page import="enfo.crm.intrust.ProductCellLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
ProductCellLocal productCell = EJBFactory.getProductCell(); 
String cell= Utility.trimNull(request.getParameter("cell"));//cell从bean里来
String cell_name = Utility.trimNull(session.getAttribute("QUERY_CELL_NAME"));
String[] str = Utility.splitString(cell+" ", "$");
Integer cell_id=Utility.parseInt(str[0],new Integer(0));
String cell_code = Utility.trimNull(str[1]);
out.print(productCell.getSubTree(input_bookCode,cell_id, cell_code,input_operatorCode,cell_name));
productCell.remove();
%> 