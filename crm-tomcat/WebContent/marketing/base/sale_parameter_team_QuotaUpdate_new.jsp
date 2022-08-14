<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得对象
SaleParameterVO vo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
//页面变量
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//产品ID
Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//子产品ID
Integer teamId = Utility.parseInt(request.getParameter("teamId"),new Integer(0));//团队ID
String teamName = Utility.trimNull(request.getParameter("teamName"));//团队名
BigDecimal freeMoney = Utility.parseDecimal(request.getParameter("freeMoney"),new BigDecimal(0));//可设置销售配额
BigDecimal quotaMoney = Utility.parseDecimal(Utility.trimNull(request.getParameter("quotaMoney")), new BigDecimal(0),0,"1");//销售配额
Integer quotaQualifiedNum = Utility.parseInt(Utility.trimNull(request.getParameter("quotaQualifiedNum")),new Integer(0));
Integer freeNum = Utility.parseInt(Utility.trimNull(request.getParameter("freeNum")),new Integer(0));//合格投资人数量配额
Integer tzQualifiedNum = Utility.parseInt(request.getParameter("tzQualifiedNum"),new Integer(0));//可调整参数
freeMoney = freeMoney.add(quotaMoney);
freeNum = new Integer( freeNum.intValue() + quotaQualifiedNum.intValue());
boolean bSuccess = false;
List list = null;
Map map = null;
vo.setProductID(productId);
vo.setInput_man(input_operatorCode);
vo.setTeamID(teamId);
vo.setSub_product_id(subProductId);
if(input_operator.hasFunc(menu_id, 131)){
	vo.setQueryAll(new Integer(1));
}else{
	vo.setQueryAll(new Integer(0));
}
Integer nextAllQuotaQualifiedNum = new Integer(0);
BigDecimal  nextAllQuotaMoney = new BigDecimal(0);
list = sale_parameter.queryTTeamValue2(vo);
if(null == list || list.size() ==0){
	list = sale_parameter.queryPersonValueNew(vo);
}
for(int i=0; i<list.size(); i++){
	map = (Map)list.get(i);
	nextAllQuotaMoney = nextAllQuotaMoney.add(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),0,"1"));
	nextAllQuotaQualifiedNum =  new Integer(nextAllQuotaQualifiedNum.intValue() + Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0)).intValue());
}
if(request.getMethod().equals("POST")){
	vo.setProductID(productId);
	vo.setTeamID(teamId);
	vo.setSerial_NO(Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0)));
	vo.setQuota_qualified_num(quotaQualifiedNum);
	vo.setQuotaMoney(quotaMoney);
	vo.setSub_product_id(subProductId);
	vo.setTz_qualified_num(tzQualifiedNum);
	sale_parameter.modi(vo,input_operatorCode);

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
				var nextAllQuotaMoney = document.getElementById("nextAllQuotaMoney").value;
				if(!reg.test(quotaMoney.value)){
					alert("输入的销售配额不合规，请输入正整数！");
					quotaMoney.focus();
					return false;
				}else{
					if(parseInt(freeMoney) < parseInt(quotaMoney.value)){
						alert("销售配额不能大于可分配销售配额！");
						quotaMoney.focus();
						return false;
					}
					if(parseInt(quotaMoney.value) < parseInt(nextAllQuotaMoney)){
						alert("销售配额不能小于子团队配额之和（"+nextAllQuotaMoney+"）");
						quotaMoney.focus();
						return false;
					}
					return true;
				}
			}
			function checkQuotaQualifiedNum(){
				var freeNum = document.getElementById("freeNum").value;
				var quotaQualifiedNum = document.getElementById("quotaQualifiedNum");
				var nextAllQuotaQualifiedNum = document.getElementById("nextAllQuotaQualifiedNum").value;
				if(!reg.test(quotaQualifiedNum.value)){
					alert("输入的投资人数不合规，请输入正整数！");
					quotaQualifiedNum.focus();
					return false;
				}else{
					if(parseInt(freeNum) < parseInt(quotaQualifiedNum.value)){
						alert("小额合格投资人数不能大于可分配数量配额！");	
						quotaQualifiedNum.focus();
						return false;
					}
					if(parseInt(quotaQualifiedNum.value) < parseInt(nextAllQuotaQualifiedNum)){
						alert("小额合格投资人数不能小于子团队配额之和（"+nextAllQuotaQualifiedNum+"）");
						quotaQualifiedNum.focus();
						return false;
					}
					return true;
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
			function saveData(){
				if (checkQuotaMoney()==false){
					return false;
				}
				document.theform.btnSave.disabled='true';
				document.theform.submit();
			}
		</script>
	</head>

	<body class="body" onkeydown="javascript:chachEsc(window.event.keyCode)">
		<form name="theform" method="post" action="sale_parameter_team_QuotaUpdate_new.jsp" onsubmit="javascript:return validateForm(this);">
			<input type=hidden name="productId" value="<%=productId %>">
			<input type=hidden name="subProductId" value="<%=subProductId %>">
			<input type=hidden name="teamId" value="<%=teamId %>">
			<input type=hidden id="nextAllQuotaMoney" name="nextAllQuotaMoney" value="<%=nextAllQuotaMoney %>">
			<input type=hidden id="nextAllQuotaQualifiedNum" name="nextAllQuotaQualifiedNum" value="<%=nextAllQuotaQualifiedNum %>">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--修改销售配额信息-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.modiSalesQuota",clientLocale)%> </b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%" >
				<tr>
					<td align="right" width="180px"><%=LocalUtilis.language("class.teamName",clientLocale)%> :&nbsp;&nbsp;</td><!--团队名称-->
					<td align="left">
						<input type="text" name="teamName" readonly="readonly" class="edline" value="<%=teamName%>" size="25">
					</td>
				</tr>
				<tr>
					<td align="right" width="180px">可分配销售配额 :&nbsp;&nbsp;</td>
					<td align="left"><input type="text" name="freeMoney" size="25" value="<%=freeMoney%>" readonly="readonly" class="edline" id="freeMoney"/></td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<span id="freeMoneyCN" class="span">(<%=Format.formatMoneyCN(freeMoney)%>)</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="180px"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> :&nbsp;&nbsp;</td><!--销售配额-->
					<td align="left">
						<input type="text" id="quotaMoney" name="quotaMoney" size="25" value="<%=quotaMoney%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,quotaMoneyCN);">
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<span id="quotaMoneyCN" class="span"></span>
					</td>
				</tr>
				<%if(user_id.intValue()==5){ %>
				<tr>
					<td align="right" width="180px">可调整参数:&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" name="tzQualifiedNum" size="25"  <%if(tzQualifiedNum.intValue()==0){ %> value="3" <%}else{ %> value="<%=tzQualifiedNum %>" <%} %> onblur="javascript:checktzQualifiedNum();">
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
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveData();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;<!--保存-->
						<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						&nbsp;&nbsp;<!--取消-->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>