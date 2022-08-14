<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象
SaleParameterVO vo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

//页面变量
Integer serial_no = new Integer(0);
Integer preProductId = Utility.parseInt(request.getParameter("preProductId"),new Integer(0));
Integer team_id = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
String team_name = "";
BigDecimal quota_money = new BigDecimal(0);//销售配额
Integer quota_qualified_num = new Integer(0);//合格投资人数量配额

boolean bSuccess = false;
List listAll = null;
Map map = null;

//获得产品所在的团队配额信息
if(preProductId.intValue()!= 0){
	vo.setTeamID(team_id);
	vo.setPre_product_id(preProductId);
	listAll = sale_parameter.queryQuota(vo,input_operatorCode);
	map = (Map)listAll.get(0); 
}
if(map!= null){
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	team_name = Utility.trimNull(map.get("TEAM_NAME"));
	//quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
	//quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
}

//保存配额信息
if(request.getMethod().equals("POST")){
	vo.setFunction_id(new Integer(101));
	vo.setPreproduct_id(preProductId);
	vo.setTeamID(team_id);
	vo.setQuotaMoney(Utility.parseDecimal(Utility.trimNull(request.getParameter("quota_money")), new BigDecimal(0),2,"1"));
	vo.setQuota_qualified_num(Utility.parseInt(Utility.trimNull(request.getParameter("quota_qualified_num")),new Integer(0)));
	try{
		sale_parameter.adjustQuota(vo,input_operatorCode);
	}catch(Exception e){
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">window.close();</script>");
		return;
	}
	
	bSuccess = true;
}
%>

<html>
<head>
<TITLE><%=LocalUtilis.language("message.saleQuotaInfo",clientLocale)%></TITLE>
<!--调整销售配额-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
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
		if(!sl_checkDecimal(form.quota_money, '<%=LocalUtilis.language("class.quotaMoney",clientLocale)%> ', 13, 3, 1))return false;//销售配额 
		return sl_check_update();
	}
	function checkHTML(easy_tag,find_tag){
		var tags = new Array();
		for(var i=0;i != easy_tag.length;i++){
			tags.push(easy_tag.charAt(i));
		}
		while( tags.length !=0 ){
			if(find_tag.indexOf(tags.pop()) != -1 ){
				if(arguments[2] != null) arguments[2].focus();
				return false;
			}
		}
		return true
	}
	
	function showCnMoney(value)
	{
		temp = value;
		if (trim(value) == ""){
			//quota_money_cn.innerText = "<font color='red'>(元)</font>";
			quota_money_cn.innerText = "";
		}else{
			quota_money_cn.innerText = "(" + numToChinese(temp) + ")";
		}
	}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="quota_adjust.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no %>">
<input type=hidden name="preProductId" value="<%=preProductId %>">
<input type=hidden name="team_id" value="<%=team_id %>">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"> 
				<font color="#215dc6"><b><%=LocalUtilis.language("class.saleQuota",clientLocale)%>  </b></font></td>
		</tr><!--修改销售配额信息-->
	</table>
	<br/>
	<table border="0" width="100%" class="product-list">
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("class.teamName",clientLocale)%> :&nbsp;&nbsp;</td><!--团队名称-->
			<td align="left">
				<input type="text" name="team_name" readonly="readonly" class="edline" value="<%=team_name%>" onkeydown="javascript:setProduct(this.value);" size="25">
			</td>
		</tr>
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("message.add2",clientLocale)%><%=LocalUtilis.language("class.saleQuota",clientLocale)%> :&nbsp;&nbsp;</td><!--销售配额-->
			<td align="left"><input type="text" name="quota_money" size="25" value="<%=quota_money %>" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value);"></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<span id="quota_money_cn"></span>
			</td>
		</tr>	
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("message.add2",clientLocale)%><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人数量配额-->
			<td align="left"><input type="text" name="quota_qualified_num" size="25" value="<%=quota_qualified_num %>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>				
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

