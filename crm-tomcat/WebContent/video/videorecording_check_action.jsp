<%@ page contentType="text/html; charset=GBK" import="java.math.*,java.util.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.contractManage.*,enfo.crm.vo.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String contractBH = Utility.trimNull(request.getParameter("contractBH"));
String productname = Utility.trimNull(request.getParameter("productname"));
String custname = Utility.trimNull(request.getParameter("custname"));
Integer checkflag = Utility.parseInt(request.getParameter("checkflag"),new Integer(0));

Integer contract_id = Utility.parseInt(request.getParameter("contract_id"),new Integer(0));
Integer vid = Utility.parseInt(request.getParameter("vid"),new Integer(0));
Integer checkflag_action = Utility.parseInt(request.getParameter("checkflag_action"),new Integer(0));
String checkcomment=Utility.trimNull(request.getParameter("checkcomment"));
String from=Utility.trimNull(request.getParameter("from"));

VideoRecordingBean vr = new VideoRecordingBean();
String errMsg=vr.checkVideoRecording(vid, checkflag_action, checkcomment, input_operatorCode);
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script type="text/javascript">
window.onload = function(){
<%if (errMsg==null || "".equals(errMsg)){
	if (checkflag_action.intValue()==1){%>
		alert("撤消成功");
		toback();
<%	}else{
%>
	alert("审核成功");
	toback();
<%	}
}else{%>
	alert("<%=errMsg%>");
	toback();
<%}%>
}
function toback(){
	<%if ("contract".equals(from)){ %>
	location = '<%=request.getContextPath()%>/marketing/sell/subscribe_check_info.jsp?checkflag=1&serial_no=<%=contract_id%>';
	<%}else { %>
	location = 'videorecording_list.jsp?page=1&pagesize='
		+'&contractBH=' + document.theform.contractBH.value
		+'&productname=' + document.theform.productname.value
		+'&custname=' + document.theform.custname.value
		+'&checkflag=' + document.theform.checkflag.value;
	<%}%>
}
</script>
</HEAD>
<BODY class="BODY">
<%//@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="videorecording_check_info.jsp">
<input name="vid" type="hidden" value="<%=vid %>">
<input name="contractBH" type="hidden" value="<%=contractBH %>">
<input name="productname" type="hidden" value="<%=productname %>">
<input name="custname" type="hidden" value="<%=custname %>">
<input name="checkflag" type="hidden" value="<%=checkflag %>">

</form>
</BODY>
</HTML>