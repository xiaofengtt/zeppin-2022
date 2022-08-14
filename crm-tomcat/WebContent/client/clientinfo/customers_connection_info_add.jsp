<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
CustomerConnectionLocal local = EJBFactory.getCustomerConnection();
CustomerConnectionVO vo = new CustomerConnectionVO();

Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String o_tel = Utility.trimNull(request.getParameter("o_tel"));
String h_tel = Utility.trimNull(request.getParameter("h_tel"));
String bp = Utility.trimNull(request.getParameter("bp"));
String apply_reason = Utility.trimNull(request.getParameter("apply_reason"));

boolean bSuccess = false;
boolean flag = false;

DocumentFileToCrmDB file = null;
	
if (request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	cust_id = Utility.parseInt(file.getParameter("cust_id"),new Integer(0));
	mobile = Utility.trimNull(file.getParameter("mobile"));
	o_tel = Utility.trimNull(file.getParameter("o_tel"));
	h_tel = Utility.trimNull(file.getParameter("h_tel"));
	bp = Utility.trimNull(file.getParameter("bp"));
	
	apply_reason = Utility.trimNull(file.getParameter("apply_reason"));
	
	if (file.getParameter("h_tel") != null
			&& !file.getParameter("h_tel").equals(""))
		cust_tel = Utility.trimNull(file.getParameter("h_tel"));
	else if (file.getParameter("o_tel") != null
			&& !file.getParameter("o_tel").equals(""))
		cust_tel = Utility.trimNull(file.getParameter("o_tel"));
	
	vo.setCust_id(cust_id);
	vo.setCust_tel(cust_tel);
	vo.setO_tel(o_tel);
	vo.setH_tel(h_tel);
	vo.setMobile(mobile);
	vo.setBp(bp);
	vo.setInput_man(input_operatorCode);
	vo.setApply_reason(apply_reason);
	try{
		Integer preserial_no = local.addCustomerConnection(vo);
		file.uploadAttchment_crm(new Integer(1101),"TCUSTOMOERS_CONNECTION_MODI",preserial_no,"",input_operatorCode); // 1101客户联系方式修改申请表TCUSTOMOERS_CONNECTION_MODI
		bSuccess = true;
	}catch(Exception e){
		out.println("<script language=\"javascript\">alert(\""+e.getMessage()+"！"+""+"\")</script>");
		bSuccess = false;
	}



}

local.remove();
%>
<html>
<head>
<title>客户联系方式修改录入</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form){
	//手机号码必填 长度为11的数字
	if (! sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1)) return false;//手机号码
	if(!(/^1\d{10}$/.test(form.mobile.value))){
		sl_alert("手机号码格式不正确");//手机号码必须为11位
		form.mobile.focus();
		return false;
	}
	if(form.apply_reason.value != ''){
		if(form.apply_reason.value.replace(/[\u0391-\uFFE5]/g,"aa").length > 100){
			sl_alert("只允许输入50个字！");//
			form.apply_reason.focus();
			return false;
		}
	}
	return sl_check_update();
}
var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' class='xpbutton3' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='移除'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}
/*查看附件方法*/
function DownloadAttachment(attachmentId){
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}

</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="cust_id" value="<%=cust_id%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>客户联系方式修改</b></font></td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">手机:</td>
		<td align="left">
			<input type="text" name="mobile" class="edline" size="40" value="">
		</td>
	</tr>
	<tr>
		<td align="right">申请事由:</td>
		<td align="left">
			<textarea style="width:220px" type="text" name="apply_reason" class="edline" size="80" value=""></textarea>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top"><input type="button" class="xpbutton6" onclick="addline()" value="添加更多附件" style="line-height:15px;margin-top:5px;"/></td>	
		<td>
			<div>
            	<div style="float:left">
	            	<table id="test" style="width:190px;" ><tr ><td><input type="file" name="file_info" size="45">&nbsp;</td></tr></table>
            	</div>
            <div>
		</td>
	</tr>	
	<% if(flag) {%>
	<tr>
		<td align="right">手机1:</td>
		<td align="left">
			<input type="text" name="bp" class="edline" size="40" value="">
		</td>
	</tr>
	<tr>
		<td align="right">家庭电话:</td>
		<td align="left">
			<input type="text" name="h_tel" class="edline" size="40" value="">
		</td>
	</tr>
	<tr>
		<td align="right">公司电话:</td>
		<td align="left">
			<input type="text" name="o_tel" class="edline" size="40" value="">
		</td>
	</tr>
	<%} %>
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();">提交审核 (<u>S</u>)</button>
		&nbsp;&nbsp;<!--提交审核-->
		<button type="button"  class="xpbutton4" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>