<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
PreDoubleRecordInfoLocal local = EJBFactory.getPreDoubleRecordInfo();
PreDoubleRecordInfoVO vo = new PreDoubleRecordInfoVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0));
String sl_date = Utility.trimNull(request.getParameter("sl_date"));
String sl_type = Utility.trimNull(request.getParameter("sl_type"));
String sl_type_name = Utility.trimNull(request.getParameter("sl_type_name"));
boolean bSuccess = false;

DocumentFileToCrmDB file = null;
	
if (request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	cust_name = Utility.trimNull(file.getParameter("cust_name"));
	product_name = Utility.trimNull(file.getParameter("product_name"));
	pre_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("pre_money")),new BigDecimal(0));
	rg_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("rg_money")),new BigDecimal(0));
	sl_date = Utility.trimNull(file.getParameter("sl_date"));
	sl_type = Utility.trimNull(file.getParameter("sl_type"));
	sl_type_name = Argument.getDoubleRecordTypeOptionText(sl_type);
	vo.setPre_serial_no(serial_no);
	vo.setSl_time(sl_date);
	vo.setSl_type(sl_type);
	vo.setSl_type_name(sl_type_name);
	vo.setInput_man(input_operatorCode);
	try{
		Integer preserial_no = local.addPreDoubleRecordInfo(vo);
		bSuccess = true;
	}catch(Exception e){
		out.println("<script language=\"javascript\">alert(\""+e.getMessage()+"��"+""+"\")</script>");
		bSuccess = false;
	}



}

local.remove();
%>
<html>
<head>
<title>˫¼��Ϣ¼��</title>
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
	if (! sl_check(document.getElementById('sl_date'), "˫¼ʱ��", 23, 1)) return false;
	if (! sl_checkChoice(document.theform.sl_type,"˫¼ģʽ")) return false;//˫¼ģʽ

	return sl_check_update();
}

</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="pre_money" value="<%=pre_money%>">
<input type=hidden name="rg_money" value="<%=rg_money%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>˫¼��Ϣ¼��</b></font></td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">��Ʒ����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(product_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">�ͻ�����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">ԤԼ���:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(pre_money)) %>">
		</td>
	</tr>
	<tr>
		<td align="right">�ѵ��˽��:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(rg_money)) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>˫¼ʱ��:</td>
		<td align="left">
			<input type="text" name="sl_date" id="sl_date" size="30" onclick="javascript:setday(this);" readOnly/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>˫¼ģʽ:</td>
		<td align="left">
			<select name="sl_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDoubleRecordTypeOptions("") %>   
			</select>
		</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>