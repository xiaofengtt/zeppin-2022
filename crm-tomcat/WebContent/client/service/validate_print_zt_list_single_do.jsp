<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<% 
ValidateprintLocal validate = EJBFactory.getValidateprint();
//上页传的值
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String contract_sub_bh = request.getParameter("contract_sub_bh");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);
//设置打印标志为 1已打印
validate.updatePrintFlag1(new Integer(1),serial_no);
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">

<OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WB width=0></OBJECT>
<style media="print">
.noprint     { display: none }
</style>


<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>
<style>
td {
	font-size:16px;
}
</style>
</HEAD>
<BODY  class="body" leftmargin="" topmargin=0 rightmargin=0 onload="javascript:print();close();">

<% 
	String url="validate_print_zt_list_single_info.jsp";
	url = url + "?flag=2&serial_no="+serial_no+"&product_id=" +product_id+"&contract_sub_bh="+contract_sub_bh;

%>
	<jsp:include page="<%=url%>"></jsp:include>

</BODY>
</HMTL>