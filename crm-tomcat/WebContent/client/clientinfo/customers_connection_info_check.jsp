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
String check_reason = Utility.trimNull(request.getParameter("check_reason"));

boolean flag = false;

Integer statusDri = Utility.parseInt(request.getParameter("statusDri"),new Integer(-2));

boolean bSuccess = false;
Map map = new HashMap();
Map atta_map = new HashMap();

if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	cust_name = Utility.trimNull(file.getParameter("cust_name"));
	statusDri = Utility.parseInt(file.getParameter("statusDri"),new Integer(-2));
	check_reason = Utility.trimNull(file.getParameter("check_reason"));
	
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	vo.setStatus(statusDri);
	vo.setCheck_reason(check_reason);
	
// 	local.modiPreMoneyDetail(vo);
	try{
		local.checkCustomerConnection(vo);
		bSuccess = true;
	}catch(Exception e){
		out.println("<script language=\"javascript\">alert(\""+e.getMessage()+"��"+""+"\")</script>");
		out.println("<script language=\"javascript\">window.close();</script>");
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
<title>�ͻ���ϵ��ʽ�޸�ȷ��</title>
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
	//�ֻ�������� ����Ϊ11������
	if (! sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1)) return false;//�ֻ�����
	if(!(/^1\d{10}$/.test(form.mobile.value))){
		sl_alert("�ֻ������ʽ����ȷ");//�ֻ��������Ϊ11λ
		form.mobile.focus();
		return false;
	}

	if(form.check_reason.value != ''){
		if(form.check_reason.value.replace(/[\u0391-\uFFE5]/g,"aa").length > 100){
			sl_alert("ֻ��������50���֣�");//
			form.check_reason.focus();
			return false;
		}
	}
	
	return sl_check_update();
}
function op_check(s)
{
	if (s==3)
		str="��ȷ��ȷ��ͨ����";
	else 
		str="��ȷ��ȷ�ϲ�ͨ����"; 
			
	if(confirm(str)) {
		disableAllBtn(true);
		document.theform.statusDri.value=s;
		document.theform.submit();
	}
}
/*�鿴��������*/
function DownloadAttachment(attachmentId){
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="statusDri" value="0">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>�ͻ���ϵ��ʽ�޸�ȷ��</b></font></td>
	</tr>
	<br/>
</table>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">�ͻ�����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
		<tr>
		<td align="right">�ֻ�:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="mobile" class="edline" size="40" value="<%=Utility.trimNull(map.get("N_MOBILE")) %>">
		</td>
	</tr>
	<tr>
		<td align="right">��������:</td>
		<td align="left">
			<textarea style="width:220px" type="text" readonly="readonly" name="apply_reason" class="edline" size="80" value="<%=Utility.trimNull(map.get("APPLY_REASON")) %>"><%=Utility.trimNull(map.get("APPLY_REASON")) %></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">ȷ�����:</td>
		<td align="left">
			<textarea style="width:220px" type="text" name="check_reason" class="edline"></textarea>
		</td>
	</tr>
<%
AttachmentToCrmLocal attachmentLocal = EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO = new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(1101)); // 1101�ͻ���ϵ��ʽ�޸������TCUSTOMOERS_CONNECTION_MODI
attachmentVO.setInput_man(input_operatorCode);
List attachmentList =attachmentLocal.load(attachmentVO);
for (int i=0; i<attachmentList.size(); i++) {
	Map attachmentMap = (Map)attachmentList.get(i);
	String attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
	String origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
   	String save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME")); %>
	<tr>
		<td align="right">����<%=i+1%>��</td>
		<td align="left">
			<a href="javascript:DownloadAttachment(<%=attachmentId%>);" style="width:200px;text-align:right"><%=origin_name%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
<%} 
attachmentLocal.remove();%>	
	<% if(flag) {%>
	<tr>
		<td align="right">�ֻ�1:</td>
		<td align="left">
			<input type="text" name="bp" class="edline" size="40" value="">
		</td>
	</tr>
	<tr>
		<td align="right">��ͥ�绰:</td>
		<td align="left">
			<input type="text" name="h_tel" class="edline" size="40" value="">
		</td>
	</tr>
	<tr>
		<td align="right">��˾�绰:</td>
		<td align="left">
			<input type="text" name="o_tel" class="edline" size="40" value="">
		</td>
	</tr>
	<%} %>
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:op_check(3);">ȷ��ͨ�� (<u>S</u>)</button>
		&nbsp;&nbsp;<!--���ͨ��-->
		<button type="button"  class="xpbutton4" accessKey=o id="btnSave" name="btnSave" onclick="javascript:op_check(2)">ȷ�ϲ�ͨ�� (<u>O</u>)</button>
		&nbsp;&nbsp;<!--��˲�ͨ��-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>