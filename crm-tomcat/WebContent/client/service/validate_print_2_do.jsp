<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<% 
String printInfo = Utility.trimNull(request.getParameter("printInfo"));
String[] printInfoArray = Utility.splitString(printInfo,",");

Integer product_id = new Integer(0);
Integer serial_no = new Integer(0);
String contract_bh = "";
String printDetail = "";
String[] printInfoDetail = null;

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
String url = "";					

for(int i = 0; i<printInfoArray.length;i++){
		printDetail = printInfoArray[i];
		printInfoDetail = Utility.splitString(printDetail,"|");	
	if(user_id.intValue()==22){
		url="validate_print_zt_info.jsp";
	}else{
		url = "validate_print_2_1_do.jsp";					
	}
		serial_no = Utility.parseInt(printInfoDetail[0],new Integer(0));
		product_id = Utility.parseInt(printInfoDetail[1],new Integer(0));
		contract_bh = printInfoDetail[2];

		url = url + "?flag=2&serial_no="+serial_no+"&product_id=" +product_id+"&contract_bh="+contract_bh;

%>
	<jsp:include page="<%=url%>"></jsp:include>
<%}%>

</BODY>
</HMTL>