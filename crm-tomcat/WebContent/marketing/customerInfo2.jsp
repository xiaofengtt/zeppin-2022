<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
Integer flag = Utility.parseInt(Utility.trimNull(request.getParameter("flag")), new Integer(0));
String cust_type_name = Utility.trimNull(request.getParameter("cust_type_name"));
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </TITLE>
<!--客户信息-->
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
window.onload = function(){
	if(<%=flag%>==1){
		document.theform.update.style.display = "none";
		document.theform.btnUpdate.style.display = "none";
	}
};

//修改
function updateInfo(){
	var v_cust_id = document.theform.v_cust_id.value;
	document.getElementsByName("sonIframe")[0].src="customerInfo_details_action.jsp?cust_id="+v_cust_id;
}
//更新证件图片
function uploadCard(){
	var v_cust_id = document.theform.v_cust_id.value;
	var url = '/marketing/customer_card_update.jsp?upload_flag=1&cust_id='+ v_cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:500px;status:0;help:0;');
	if(v == 1)
		document.getElementsByName("sonIframe")[0].src="customerInfo_details_view.jsp?cust_id="+v_cust_id;
}
/*保存信息*/
function saveAction(){
	var v = document.theform.resultValue.value;
	var v_array = v.split(",");
	
	window.returnValue = v_array;		
	window.close();
}

/*改变显示参数*/
function changeAction(){
	var v_cust_id = document.theform.v_cust_id.value;
	document.getElementsByName("sonIframe")[0].src="customerInfo_details_view.jsp?cust_id="+v_cust_id;
}

//合格投资人信息维护
function editInfo(cust_id){
	showModalDialog('/client/appraise/amcust_info.jsp?signed_spot_flag=1&cust_id='+cust_id,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');
}
</SCRIPT>
</HEAD>

<BODY class="BODY body-nox">
<form name="theform" method="post">
<div style="margin-left:10px" >
	<div class="page-title"><b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b></div>
</div><!--客户信息-->
<div align="right"  class="btn-wrapper">
	<%if("机构".equalsIgnoreCase(cust_type_name)){ %>
	<button type="button"  class="xpbutton5" id="editInf" onclick="javascript:editInfo(<%=cust_id %>);">机构附加信息</button>&nbsp;&nbsp;&nbsp;
	<%}%>
	<button type="button"  class="xpbutton5" id="update" onclick="javascript:uploadCard();" <%if(user_id.intValue() != 2 ){ %> style="display:none"<%} %>>更新证件附件</button>&nbsp;&nbsp;&nbsp;
	<%if (input_operator.hasFunc(menu_id, 121)){%>
	<button type="button"  class="xpbutton3"  accessKey=u id="btnUpdate" name="btnUpdate" onclick="javascript:updateInfo();"><%=LocalUtilis.language("message.update",clientLocale)%> (<u>U</u>)</button>&nbsp;
	<%} %>
</div><!--修改-->
<br/>
<!--子窗口-->
<iframe src ="customerInfo_details_view.jsp?cust_id=<%=cust_id%>"  frameborder="no" border="0" name="sonIframe" height="500" width="700"></iframe>

<!--子窗口 和 父窗口 的 联系节点-->

<input type="button" style="visibility:hidden;" name="saveButton" onclick="javascript:saveAction()"/>
<input type="button" style="visibility:hidden;" name="changeButton" onclick="javascript:changeAction()"/>
<input type="hidden" name="resultValue" value=""/>
<input type="hidden" name="v_cust_id" value="<%=cust_id%>"/>
</form>
</BODY>
</HTML>

