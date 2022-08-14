<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
SalesResultForStatisticVO vo = new SalesResultForStatisticVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0));
String dz_date = Utility.trimNull(request.getParameter("dz_date"));
BigDecimal dz_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("dz_money")),new BigDecimal(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
String sms1_customer = cust_name+"，您好：您的缴款已到账。购买产品："+product_name+"，缴款金额：";
String sms2_serviceman = cust_name+" 缴款已到账。购买产品："+product_name+"，缴款金额：";
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
	dz_date = Utility.trimNull(file.getParameter("dz_date"));
	dz_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("dz_money")),new BigDecimal(0));
	jk_type = Utility.trimNull(file.getParameter("jk_type"));
	Integer is_onway = Utility.parseInt(file.getParameter("is_onway"), new Integer(0));
	
	String rg_cust_name = Utility.trimNull(file.getParameter("rg_cust_name"));
	String rg_product_name = Utility.trimNull(file.getParameter("rg_product_name"));
	Integer rg_cust_type = Utility.parseInt(file.getParameter("rg_cust_type"), new Integer(0));
	Integer rg_product_type = Utility.parseInt(file.getParameter("rg_product_type"), new Integer(0));
	String rg_product_type_name = Argument.getSalecChangesResultProductTypeName(rg_product_type+"");
	
	Integer rg_service_man = Utility.parseInt(file.getParameter("rg_service_man"),new Integer(0));

	vo.setPre_serial_no(serial_no);
	vo.setDz_time(dz_date);
	vo.setDz_money(dz_money);
	vo.setJk_type(jk_type);
	vo.setInput_man(input_operatorCode);
	vo.setIs_onway(is_onway);
	vo.setCust_name(rg_cust_name);
	vo.setRg_product_name(rg_product_name);
	vo.setRg_cust_type(rg_cust_type);
	vo.setPre_product_type(rg_product_type);
	vo.setPre_product_type_name(rg_product_type_name);
	vo.setRg_service_man(rg_service_man);
	
	Integer preserial_no = local.addSalesResultForStatistic(vo);
// 	file.uploadAttchment(new Integer(3), "TPREMONEYDETAIL", preserial_no, "", input_operatorCode);
	file.uploadAttchment_crm(new Integer(1102),"TSALESRESULTFORSTATISTIC",preserial_no,"",input_operatorCode); // 1102-TSALESRESULTFORSTATISTIC
	bSuccess = true;

}
//本次最多能换金额
// String yh_money_cn = Utility.trimNull(Format.formatMoney0(pre_money.subtract(rg_money)));
// BigDecimal yh_money = pre_money.subtract(rg_money);

local.remove();
%>
<html>
<head>
<title>到账处理</title>
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
	if(!sl_check(form.rg_product_name, "产品名称 ", 100, 1))	return false;//产品名称
	if(!sl_checkChoice(document.theform.rg_product_type,"产品类型 ")) return false;//产品类型
	if(!sl_check(form.rg_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))	return false;//客户名称
	if(!sl_checkChoice(document.theform.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")) return false;//客户类型
	if (!sl_checkChoice(form.rg_service_man, "客户经理")) return false;
	if (! sl_check(document.getElementById('dz_date'), "到账时间", 23, 1)) return false;
	if (!sl_checkDecimal(document.theform.dz_money, "到账金额", 13, 3, 1)) return false;
	if (document.theform.dz_money.value <= 0){
		sl_alert("到账金额不能小于等于零！");
		return false;
	}

	if (!sl_checkChoice(form.jk_type, "缴款方式")) return false;
	return sl_check_update();
}

function _checked(obj) {
	document.getElementById("onway_attach").style.display = obj.checked?"":"none";
}
var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' class='xpbutton3' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='移除'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}
/*查看附件方法*/
function DownloadAttachment(attachmentId){
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
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
		<td class="page-title"><font color="#215dc6"><b>到账处理</b></font></td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right"><font color="red">*</font>产品名称:</td>
		<td align="left">
			<input type="text" name="rg_product_name" class="edline" size="30" value="<%=Utility.trimNull(product_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>产品分类:</td>
		<td align="left">
			<select name="rg_product_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getSalecChangesResultProductTypeOptions(null) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>客户名称:</td>
		<td align="left">
			<input type="text" name="rg_cust_name" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%"><font color="red">*</font>客户类型:</td>
		<td width="25%">
			<select name="rg_cust_type" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px">	
				<option value="0">请选择</option>
				<option value="1" >个人</option>
				<option value="2" >机构</option>
			</select>
			<input readonly="readonly" class="edline" name="input_value2" size="27" style="display: none;" id="customerType">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>客户经理:</td>
		<td align="left">
			<select name="rg_service_man" style="width: 147px">
				<%=Argument.getManager_Value(null) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>到账时间:</td>
		<td align="left">
			<input type="text" name="dz_date" id="dz_date" size="30" onclick="javascript:setday(this);" readOnly/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>到账金额:</td>
		<td align="left">
			<input type="text" name="dz_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,dz_money_cn)">
			<br><span id="dz_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>缴款方式:</td>
		<td align="left">
			<select name="jk_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1114, "") %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top"><input type="button" class="xpbutton6" onclick="addline()" value="添加更多附件" style="line-height:15px;margin-top:5px;"/></td>	
		<td>
			<div>
            	<div style="float:left">
	            	<table id="test" style="width:190px;" ><tr ><td><input type="file" name="file_info" size="45">&nbsp;</td></tr></table>
            	</div>
            <div>
		</td>
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