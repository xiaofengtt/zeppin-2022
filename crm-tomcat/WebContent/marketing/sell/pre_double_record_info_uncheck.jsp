<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
PreDoubleRecordInfoLocal local = EJBFactory.getPreDoubleRecordInfo();
PreDoubleRecordInfoVO vo = new PreDoubleRecordInfoVO();

AttachmentToCrmLocal attach = EJBFactory.getAttachmentToCrm();
AttachmentVO atta_vo = new AttachmentVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0));
String sl_date = Utility.trimNull(request.getParameter("sl_date"));
String sl_type = Utility.trimNull(request.getParameter("sl_type"));
String sl_type_name = Utility.trimNull(request.getParameter("sl_type_name"));

Integer statusDri = Utility.parseInt(request.getParameter("statusDri"),new Integer(-2));

boolean bSuccess = false;
Map map = new HashMap();
Map atta_map = new HashMap();

if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	cust_name = Utility.trimNull(file.getParameter("cust_name"));
	product_name = Utility.trimNull(file.getParameter("product_name"));
	pre_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("pre_money")),new BigDecimal(0));
	rg_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("rg_money")),new BigDecimal(0));
	sl_date = Utility.trimNull(file.getParameter("sl_date"));
	sl_type = Utility.trimNull(file.getParameter("sl_type"));
	sl_type_name = Argument.getDoubleRecordTypeOptionText(sl_type);
	
	statusDri = Utility.parseInt(file.getParameter("statusDri"),new Integer(-2));
	
	vo.setSerial_no(serial_no);
	vo.setSl_time(sl_date);
	vo.setSl_type(sl_type);
	vo.setSl_type_name(sl_type_name);
	vo.setInput_man(input_operatorCode);
	vo.setStatus(statusDri);
	
// 	local.modiPreMoneyDetail(vo);
	try{
		local.uncheckPreDoubleRecordInfo(vo);
		bSuccess = true;
	}catch(Exception e){
		out.println("<script language=\"javascript\">alert(\""+e.getMessage()+"！"+""+"\")</script>");
		out.println("<script language=\"javascript\">window.close();</script>");
		bSuccess = false;
	}

}else{
	if (serial_no.intValue() > 0){
		vo.setSerial_no(serial_no);
		List list = local.queryPreDoubleRecordInfo(vo);
		if(list != null && list.size()>0) map = (Map)list.get(0);
		sl_date = map.get("SL_TIME").toString();
		sl_type = map.get("RECORD_TYPE") == null ? "0":map.get("RECORD_TYPE").toString();
	}
}

local.remove();
attach.remove();
%>
<html>
<head>
<title>双录信息撤销审核</title>
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
	if (! sl_check(document.getElementById('sl_date'), "双录时间", 23, 1)) return false;
	if (! sl_checkChoice(document.theform.sl_type,"双录模式")) return false;//双录模式
	return sl_check_update();
}
function op_check(s)
{
	if (s==-1)
		str="你确定撤销审核吗？";
	
	if(confirm(str)) {
		disableAllBtn(true);
		document.theform.statusDri.value=s;
		document.theform.submit();
	}
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
<input type=hidden name="statusDri" value="-2">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>双录信息撤销审核</b></font></td>
	</tr>
	<br/>
</table>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">产品名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(product_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">预约金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(pre_money)) %>">
		</td>
	</tr>
	<tr>
		<td align="right">已到账金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(rg_money.subtract(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")))) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>双录时间:</td>
		<td align="left">
			<input type="text" name="sl_date" id="sl_date" size="30" readonly="readonly" value="<%=sl_date%>"/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>双录模式:</td>
		<td align="left">
			<select name="sl_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)" disabled>
				<%=Argument.getDoubleRecordTypeOptions(sl_type) %>   
			</select>
		</td>
	</tr>
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:op_check(-1);">撤销审核 (<u>S</u>)</button>
		&nbsp;&nbsp;<!--撤销审核-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>