<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

//申明变量及获得页面参数
boolean bSuccess = false;
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);//产品编号
vo.setProduct_id(Utility.parseInt(request.getParameter("product_id"), null));//产品ID
Map map = new HashMap();
List list = null;
Iterator iterator = list.iterator();

//提交 
if (request.getMethod().equals("POST"))
{
	vo.setProduct_id(Utility.parseInt(request.getParameter("product_id"), null));
	vo.setBegin_price(Utility.parseDecimal(request.getParameter("begin_price"), null));
	vo.setEnd_price(Utility.parseDecimal(request.getParameter("end_price"), null));
	vo.setDiv_rate(Utility.parseDecimal(request.getParameter("div_rate"), null, 10, "0.01"));
	vo.setInput_man(input_operatorCode);
	if (serial_no == null)
		product.append_glf(vo);
	else
	{
		vo.setSerial_no(serial_no);
		product.save_glf(vo);
	}
	bSuccess = true;
}
else if (serial_no != null)
{
	vo.setSerial_no(serial_no);
	list = product.list_gfp(vo);
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.productStagItem2",clientLocale)%> </title>
<!--管理费提取分段配置信息-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

</HEAD>

<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	if(!sl_checkDecimal(form.begin_price, "<%=LocalUtilis.language("class.beginPrice",clientLocale)%> ", 6, 10,1))		return false;//起始净值
	if(!sl_checkDecimal(form.end_price, "<%=LocalUtilis.language("class.endPrice",clientLocale)%> ", 6, 10,1))		return false;//结束净值
	if(!sl_checkDecimal(form.div_rate, "<%=LocalUtilis.language("class.divRate",clientLocale)%> ", 8, 8,1))		return false;//提成比例

	return sl_check_update();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="product_stage_item_info.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
<input type="hidden" name="product_id" value="<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0)))%>">

<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="96%" cellspacing="0" cellpadding="0" height="74">
							<tr>
								<td class="page-title"><b><font color="#215dc6"><%=LocalUtilis.language("menu.productManageFeeSectionInfo",clientLocale)%> </font></b></td>
								<!--产品管理费提取分段信息-->
							</tr>
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.ID",clientLocale)%> :</td><!--编号-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" readonly class="edline" size="20" name="serial_no" value="<%=Utility.trimNull(serial_no)%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.beginPrice",clientLocale)%> :</td><!--起始净值-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="begin_price" value="<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("BEGIN_PRICE")),new BigDecimal(0)))%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.endPrice",clientLocale)%> :</td><!--结束净值-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="end_price" value="<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("END_PRICE")),new BigDecimal(0)))%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.divRate",clientLocale)%> :</td><!--提成比例-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="div_rate" value="<%=(Utility.parseDecimal(Utility.trimNull(map.get("DIV_RATE")),new BigDecimal(0)) == null)
	? ""
	: Utility.parseDecimal(Utility.trimNull(map.get("DIV_RATE")),new BigDecimal(0)).multiply(new BigDecimal(100)).setScale(8).toString()%>">%</td>
							</tr>
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;<!--保存-->
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;<!--取消-->
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>

</HTML>
<%product.remove();%>
