<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
String  serial_no_list = Utility.trimNull(request.getParameter("serial_no_list"));
Integer productId  = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
TMoneyDataVO vo = new TMoneyDataVO();

boolean bSuccess = false;
if (request.getMethod().equals("POST")){
	String str[] = serial_no_list.split(",");
	System.err.println(str.length);
	if(str.length>0){
		for(int i=0;i<str.length;i++){
			moneyData.modiSubproductid(new Integer(str[i]),sub_product_id,productId);
		}
	}
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/jQuery/FormValidate/css/css.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT language="javascript">

<%if (bSuccess){%>  
	sl_alert("保存成功");
	window.returnValue = 1;
	window.close();	
<%}%>


function validateForm(form)
{
	return sl_check_update();
}

</SCRIPT>
<BODY class="BODY">

<form name="theform" method="post" action="modi_sub_product_id.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no_list"  value="<%=serial_no_list%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
		<tr>
			<td><img border="0" src="/images/ico_area.gif" width="32" height="32"><b>子产品修改</b></td>
		</tr>
		<tr>
			<td><hr size="1"></td>
		</tr>
	</TABLE>
	<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
		<tr>	
			<td align="right"><b>*产品选择</b></td>
			<td align=left >
				<input type="hidden" name="productId" value="<%=productId%>">
				<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductItem();" disabled  class="productname" style="width:330px;" >
					<%=Argument.getProductListOptions(input_bookCode, productId,"",input_operatorCode,17)%>
				</select>&nbsp;&nbsp;&nbsp;
				
			</td>
		</tr>
			
		<tr>
			<td align="right"><b>*子产品选择</b></td>
			<td align=left >
				 <select size="1"  name="sub_product_id" class="productname" style="width:330px;">
					<%=Argument.getSubProductOptions2(productId,new Integer(0),sub_product_id,3) %>
				</select> &nbsp;&nbsp;&nbsp;
				
			</td>
		</tr>
		<tr>
			<td colspan="4">
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.btnSave.disabled='true';document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--保存-->
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button><!--取消-->
					&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</TABLE>
	</TD>
</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
moneyData.remove();
}catch(Exception e){
	throw e;
}%>