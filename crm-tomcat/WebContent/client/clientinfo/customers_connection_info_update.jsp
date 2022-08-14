<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
CustomerConnectionLocal local = EJBFactory.getCustomerConnection();
CustomerConnectionVO vo = new CustomerConnectionVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
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
Map map = new HashMap();

if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
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
	vo.setSerial_no(serial_no);
	vo.setCust_id(cust_id);
	vo.setCust_tel(cust_tel);
	vo.setO_tel(o_tel);
	vo.setH_tel(h_tel);
	vo.setMobile(mobile);
	vo.setBp(bp);
	vo.setInput_man(input_operatorCode);
	vo.setApply_reason(apply_reason);
	try{
		local.modiCustomerConnection(vo);
		file.uploadAttchment_crm(new Integer(1101),"TCUSTOMOERS_CONNECTION_MODI",serial_no,"",input_operatorCode); // 1101客户联系方式修改申请表TCUSTOMOERS_CONNECTION_MODI
		bSuccess = true;
	}catch(Exception e){
		out.println("<script language=\"javascript\">alert(\""+e.getMessage()+"！"+""+"\")</script>");
		bSuccess = false;
	}

}else{
	if (serial_no.intValue() > 0){
		vo.setSerial_no(serial_no);
		List list = local.queryCustomerConnection(vo);
		if(list != null && list.size()>0) map = (Map)list.get(0);
	}
}

local.remove();
%>
<html>
<head>
<title>客户联系方式修改编辑</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<base target="_self"/>
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

//删除数据库中已有附件的方法
function deleteDbAttachment(attachmentId,save_name){
    if (confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //确认删除附件吗
		var url = "customers_connection_info_attachment.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name
			+"&serial_no=<%=serial_no%>&cust_name=<%=cust_name%>";
		document.getElementById("go-link").href = url;
		document.getElementById("go-link").click();	
    }
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<a id="go-link"></a>
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>客户联系方式修改编辑</b></font></td>
	</tr>
	<br/>
</table>
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
			<input type="text" name="mobile" class="edline" size="40" value="<%=Utility.trimNull(map.get("N_MOBILE")) %>">
		</td>
	</tr>
	<tr>
		<td align="right">申请事由:</td>
		<td align="left">
			<textarea style="width:220px" type="text" name="apply_reason" class="edline" size="80" value="<%=Utility.trimNull(map.get("APPLY_REASON")) %>"><%=Utility.trimNull(map.get("APPLY_REASON")) %></textarea>
		</td>
	</tr>
<%
AttachmentToCrmLocal attachmentLocal = EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO = new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(1101)); // 1101客户联系方式修改申请表TCUSTOMOERS_CONNECTION_MODI
attachmentVO.setInput_man(input_operatorCode);
List attachmentList =attachmentLocal.load(attachmentVO);
for (int i=0; i<attachmentList.size(); i++) {
	Map attachmentMap = (Map)attachmentList.get(i);
	String attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
	String origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
   	String save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME")); %>
	<tr>
		<td align="right">附件<%=i+1%>：</td>
		<td align="left">
			<a href="javascript:DownloadAttachment(<%=attachmentId%>);" style="width:200px;text-align:right"><%=origin_name%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" id="btn_DelDbAttachment" name="btn_DelDbAttachment" onclick="javascript:deleteDbAttachment('<%=attachmentId%>','<%=save_name%>');">删除附件</button>
		</td>
	</tr>
<%} 
attachmentLocal.remove();%>	
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
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>