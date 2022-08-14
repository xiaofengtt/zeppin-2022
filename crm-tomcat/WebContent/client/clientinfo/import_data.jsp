<%@ page language="java" pageEncoding="GBK" import="enfo.crm.fileupload.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	FileUploader uploader = new FileUploader();
	uploader.uploadFile(pageContext);
%>
