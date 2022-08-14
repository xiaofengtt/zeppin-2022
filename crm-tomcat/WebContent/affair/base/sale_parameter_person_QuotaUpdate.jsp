<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象
SaleParameterVO vo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
ProductLocal prdocutLocal = EJBFactory.getProduct();
ProductVO productVO = new ProductVO();

//页面变量
Integer serial_no = new Integer(0);
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer team_id = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
Integer op_code = Utility.parseInt(request.getParameter("op_code"),new Integer(0));
String op_name = Utility.trimNull(request.getParameter("op_name"));
Integer sub_productId = Utility.parseInt(request.getParameter("sub_productId"),new Integer(0));
BigDecimal quota_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("quota_money")), new BigDecimal(0),2,"1");//销售配额
Integer quota_qualified_num = Utility.parseInt(Utility.trimNull(request.getParameter("quota_qualified_num")),new Integer(0));//合格投资人数量配额

boolean bSuccess = false;

//保存配额信息
if(request.getMethod().equals("POST")){
	vo.setProductID(productId);
	vo.setTeamID(team_id);
	vo.setSerial_NO(Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0)));
	vo.setQuota_qualified_num(quota_qualified_num);
	vo.setQuotaMoney(quota_money);
	vo.setSub_product_id(Utility.parseInt(request.getParameter("sub_productId"),new Integer(0)));
	vo.setService_man(Utility.parseInt(request.getParameter("op_code"),new Integer(0)));
	sale_parameter.modiPersonValue(vo);

	bSuccess = true;
}
%>


<HTML>
<head>
<TITLE><%=LocalUtilis.language("message.saleQuotaInfo",clientLocale)%></TITLE>
<!--修改销售配额-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>
	function validateForm(form){	
		if(!sl_checkDecimal(form.quota_money, '<%=LocalUtilis.language("class.quotaMoney",clientLocale)%> ', 13, 3, 1))
			return false;//销售配额 
		return sl_check_update();
	}

	
	function showCnMoneyZ(value,name)
	{
		temp = value;
		if (trim(value) == ""){
			name.innerText = "<font color='red'>(元)</font>";
		}else{
			name.innerText = "(" + numToChinese(temp) + ")";
		}
	}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="sale_parameter_person_QuotaUpdate.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="productId" value="<%=productId %>">
<input type=hidden name="sub_productId" value="<%=sub_productId %>">
<input type=hidden name="team_id" value="<%=team_id %>">
<input type=hidden name="op_code" value="<%=op_code %>">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.modiSalesQuota",clientLocale)%> </b></font></td>
		</tr><!--修改销售配额信息-->
		<tr>
			<td>
				<hr noshade color="#808080" size="1">
			</td>
		</tr>
	</table>
	<table border="0" width="100%" >
		<tr>
			<td align="right" width="180px">个人名称 :&nbsp;&nbsp;</td>
			<td align="left">
				<input type="text" name="team_name" readonly="readonly" class="edline" value="<%=op_name%>" onkeydown="javascript:setProduct(this.value);" size="25">
			</td>
		</tr>
		<tr>
			<td align="right" width="180px">
				<input type=hidden name="serial_no" value="<%=serial_no %>">
				<%=LocalUtilis.language("class.saleQuota",clientLocale)%> :&nbsp;&nbsp;</td><!--销售配额-->
			<td align="left"><input type="text" name="quota_money" size="25" value="<%=quota_money%>" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,quota_money_cn);"></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<span id="quota_money_cn"  class="span"></span>
			</td>
		</tr>	
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人数量配额-->
			<td align="left"><input type="text" name="quota_qualified_num" size="25" value="<%=quota_qualified_num%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>	
			
	</table>

	<table border="0" width="100%">
		<tr>
			<td align="center">
				<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
				</td>
	</tr>
</table>
</form>
</body>
</html>

