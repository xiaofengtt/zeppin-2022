<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));

boolean bSuccess = false;
String product_name = "";
Integer service_man = new Integer(0);
if (request.getMethod().equals("POST"))
{
	vo.setProduct_id(product_id);
	vo.setService_man(Utility.parseInt(request.getParameter("service_man"),new Integer(0)));
	vo.setInput_man(input_operatorCode);
	product.modiManager(vo);
	bSuccess = true;
}else{
	vo.setProduct_id(product_id);
	List list = product.load(vo);
	if(list.size() > 0){
		Map map = (Map) list.get(0);
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));	
	}
}
%>
<html>

<head>
<title><%=LocalUtilis.language("menu.managerUpdate",clientLocale)%> </title>
<!--编辑窗口-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>

<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close(); 
<%}%>

function validateForm(form)
{
	//if(!sl_checkChoice(form.admin_manager, "<%=LocalUtilis.language("intrsut.home.trustmanager",clientLocale)%> "))	return false;//信托经理
	return sl_check_update();
}
</script>
<body class="body body-nox" topmargin="0" leftmargin="0" rightmargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="product_manager_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="product_id" value="<%=product_id%>">
<table border="0" width="100%" cellspacing="0" cellpadding="4">
	<tr>
		<TD vAlign=top align=left height="146">
		<table border="0" width="100%" cellspacing="0" cellpadding="4">
			<TR>
				<td colspan="2" background="<%=request.getContextPath()%>/images/headerbg.gif"><b><font color="#FFFFFF"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </font></b></td>
			</tr>
			<tr>
				<td align="right" height="34"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
				<td height="34"><%=Utility.trimNull(product_name)%></td>
			</tr>
			<tr>
				<td align="right" height="29"><%=LocalUtilis.language("class.product",clientLocale)%><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--产品客户经理-->
				<td height="29"><select size="1" style="width: 120px" name="service_man" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getOperatorOptions(service_man) %>
				</select></td>
			</tr>
			<tr>
				<td colspan="2" height="33">
				<TABLE border="0" width="100%">
					<TBODY>
						<TR>
							<TD align="right" height="7">
							<button type="button"  class="xpbutton3" accesskey="s" id="btnSave"
								name="btnSave"
								onclick="javascript:if(document.theform.onsubmit()) { 	disableAllBtn(true); document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<U>S</U>)</BUTTON>
							&nbsp;&nbsp;<!--保存-->
							<button type="button"  class="xpbutton3" accesskey="c" id="btnCancel"
								name="btnCancel"
								onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<U>C</U>)</BUTTON><!--取消-->
							&nbsp;&nbsp;</TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
			</TR>
		</TABLE>
		</TD>
	</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%> 
</body>
</html>
<%product.remove();%>