<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
System.out.println("--------------sale_parameter_person_QuotaUpdate_new.jsp----------------");
//获得对象
SaleParameterVO vo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
//页面变量
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//产品ID
Integer teamId = Utility.parseInt(request.getParameter("teamId"),new Integer(0));//团队ID
Integer opCode = Utility.parseInt(request.getParameter("opCode"),new Integer(0));//个人ID
String opName = Utility.trimNull(request.getParameter("opName"));//个人名
Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//子产品ID
BigDecimal freeMoney = Utility.parseDecimal(request.getParameter("freeMoney"),new BigDecimal(0));//可设置销售配额
BigDecimal quotaMoney = Utility.parseDecimal(Utility.trimNull(request.getParameter("quotaMoney")), new BigDecimal(0),0,"1");//销售配额
Integer quotaQualifiedNum = Utility.parseInt(Utility.trimNull(request.getParameter("quotaQualifiedNum")),new Integer(0));
Integer freeNum = Utility.parseInt(Utility.trimNull(request.getParameter("freeNum")),new Integer(0));//合格投资人数量配额
Integer tzQualifiedNum = Utility.parseInt(request.getParameter("tzQualifiedNum"),new Integer(0));//可调整参数
boolean bSuccess = false;
freeMoney = freeMoney.add(quotaMoney);
freeNum = new Integer( freeNum.intValue() + quotaQualifiedNum.intValue());
//保存配额信息
if(request.getMethod().equals("POST")){
	vo.setProductID(productId);
	vo.setTeamID(teamId);
	vo.setQuota_qualified_num(quotaQualifiedNum);
	vo.setQuotaMoney(quotaMoney);
	vo.setSub_product_id(subProductId);
	vo.setService_man(opCode);
	vo.setTz_qualified_num(tzQualifiedNum);
	sale_parameter.modiPersonValue(vo);
	bSuccess = true;
}
%>

<html>
	<head>
		<title><%=LocalUtilis.language("message.saleQuotaInfo",clientLocale)%></title><!--修改销售配额-->
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<base target="_self">
		
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script language=javascript>
			<%if (bSuccess){%>
				window.returnValue = "1";
				window.close();
			<%}%>
		
			var reg = new RegExp("^([1-9]\\d*|0)$");
			function validateForm(form){
				<%if(user_id.intValue()==5){%>
					if(checkQuotaMoney()&&checktzQualifiedNum()){
						return sl_check_update();
					}
				<%}else{%>
					if(checkQuotaMoney()&&checkQuotaQualifiedNum()){
						return sl_check_update();
					}
				<%}%>	
			}
			function showCnMoney(value,name){
				temp = value;
				if (trim(value) == ""){
					name.innerText = "";
				}else{
					name.innerText = "(" + numToChinese(temp) + ")";
				}
			}
			function checkQuotaMoney(){
				var freeMoney = document.getElementById("freeMoney").value;
				var quotaMoney = document.getElementById("quotaMoney");
				if(!reg.test(quotaMoney.value)){
					alert("输入的销售配额不合规，请输入正整数！");
					quotaMoney.focus();
					return false;
				}else{
					if(parseInt(freeMoney) < parseInt(quotaMoney.value)){
						alert("您输入的配额过大,请输入小于可分配配额的整数！");
						quotaMoney.focus();
						return false;
					}else{
						return true;
					}
				}
			}
			function checkQuotaQualifiedNum(){
				var freeNum = document.getElementById("freeNum").value;
				var quotaQualifiedNum = document.getElementById("quotaQualifiedNum");
				if(!reg.test(quotaQualifiedNum.value)){
					alert("输入的投资人数不合规，请输入正整数！");
					quotaQualifiedNum.focus();
					return false;
				}else{
					if(parseInt(freeNum) < parseInt(quotaQualifiedNum.value)){
						alert("您输入的配额过大,请输入小于可分配个人的整数！");	
						quotaQualifiedNum.focus();
						return false;
					}else{
						return true;
					}
				}
			}
			function checktzQualifiedNum(){
				var tzQualifiedNum = document.getElementById("tzQualifiedNum");
				if(!reg.test(tzQualifiedNum.value)){
					alert("输入的可调整参数不合规，请输入正整数！");
					tzQualifiedNum.focus();
					return false;
				}else{
					return true;
				}
			}
		</script>
	</head>
	
	<body class="body" onkeydown="javascript:chachEsc(window.event.keyCode)">
		<form name="theform" method="post" action="sale_parameter_person_QuotaUpdate_new.jsp" onsubmit="javascript:return validateForm(this);">
			<input type=hidden name="productId" value="<%=productId %>">
			<input type=hidden name="subProductId" value="<%=subProductId %>">
			<input type=hidden name="teamId" value="<%=teamId %>">
			<input type=hidden name="opCode" value="<%=opCode %>">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--修改销售配额信息-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.modiSalesQuota",clientLocale)%> </b></font></td>
				</tr>
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
						<input type="text" name="opName" readonly="readonly" class="edline" value="<%=opName%>"  size="25">
					</td>
				</tr>
				<tr>
					<td align="right" width="180px">可分配销售配额 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" id="freeMoney" name="freeMoney" size="25" value="<%=freeMoney%>" readonly="readonly" class="edline"/>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<span id="freeMoneyCN" class="span">(<%=Format.formatMoneyCN(freeMoney)%>)</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="180px"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> :&nbsp;&nbsp;</td><!--销售配额-->
					<td align="left">
						<input type="text" id="quotaMoney" name="quotaMoney" size="25" value="<%=quotaMoney%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,quotaMoneyCN);" onblur="javascript:checkQuotaMoney();">
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<span id="quotaMoneyCN"  class="span"></span>
					</td>
				</tr>
				<%if(user_id.intValue()==5){ %>
				<tr>
					<td align="right" width="180px">可调整参数:&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" name="tzQualifiedNum" size="25" <%if(tzQualifiedNum.intValue()==0){ %> value="3" <%}else{ %> value="<%=tzQualifiedNum %>" <%} %> onblur="javascript:checktzQualifiedNum();"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="180px"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人数量配额-->
					<td align="left">
						<input type="text" readonly="readonly" class="edline" name="quotaQualifiedNum" size="25" value="<%=quotaQualifiedNum%>" onkeydown="javascript:nextKeyPress(this)">
					</td>
				</tr>
				<%}else{ %>
				<tr>
					<td align="right" width="180px">可分配数量配额 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" id="freeNum" name="freeNum" size="25" value="<%=freeNum%>" readonly="readonly" class="edline"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="180px"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人数量配额-->
					<td align="left">
						<input type="text" id="quotaQualifiedNum" name="quotaQualifiedNum" size="25" value="<%=quotaQualifiedNum%>" onkeydown="javascript:nextKeyPress(this)" onblur="javascript:checkQuotaQualifiedNum();">
					</td>
				</tr>
				<%} %>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="center">
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.btnSave.disabled='true'; document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;<!--保存-->
						<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						&nbsp;&nbsp;<!--取消-->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>