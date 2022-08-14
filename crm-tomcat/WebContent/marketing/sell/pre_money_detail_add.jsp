<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();

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

	vo.setPre_serial_no(serial_no);
	vo.setDz_time(dz_date);
	vo.setDz_money(dz_money);
	vo.setJk_type(jk_type);
	vo.setInput_man(input_operatorCode);
	vo.setIs_onway(is_onway);

	Integer preserial_no = local.addPreMoneyDetail(vo);
	file.uploadAttchment(new Integer(3), "TPREMONEYDETAIL", preserial_no, "", input_operatorCode);
	bSuccess = true;

	//取到账记录信息
	int flag_sendsmscustomer = Utility.parseInt(file.getParameter("flag_sendsmscustomer"),new Integer(0)).intValue();
	int flag_sendsmsmanager = Utility.parseInt(file.getParameter("flag_sendsmsmanager"),new Integer(0)).intValue();
	vo.setSerial_no(preserial_no);
	vo.setInput_man(input_operatorCode);
	if (1==flag_sendsmscustomer) 
		vo.setSms1_customer(Utility.trimNull(file.getParameter("sms1_customer")) + dz_money + "元");
	else 
		vo.setSms1_customer("");
	
	if (1==flag_sendsmsmanager) 
		vo.setSms2_serviceman(Utility.trimNull(file.getParameter("sms2_serviceman")) + dz_money + "元");
	else
		vo.setSms2_serviceman("");
	
	local.updateSMS(vo); //记录短信内容
	List listAll = local.queryPreMoneyDetail(vo);
	if (listAll.size()>0) {
		Map map = (Map)listAll.get(0);
		//发送短信
		String smsContent = Utility.trimNull(map.get("SMS1_CUSTOMER"));
		if (1==flag_sendsmscustomer) 
			Argument.sendSMSSimple(input_operatorCode+"",Utility.trimNull(map.get("CUST_MOBILE")),smsContent,new Integer(1),"待发",new Integer(0),input_operatorCode);
		
		smsContent = Utility.trimNull(map.get("SMS2_SERVICEMAN"));
		if (1==flag_sendsmsmanager) 
			Argument.sendSMSSimple(input_operatorCode+"",Utility.trimNull(map.get("SERVICE_MAN_MOBILE")),smsContent,new Integer(1),"待发",new Integer(0),input_operatorCode);
	}
}
//本次最多能换金额
String yh_money_cn = Utility.trimNull(Format.formatMoney0(pre_money.subtract(rg_money)));
BigDecimal yh_money = pre_money.subtract(rg_money);

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
	if (! sl_check(document.getElementById('dz_date'), "到账时间", 23, 1)) return false;
	if (!sl_checkDecimal(document.theform.dz_money, "到账金额", 13, 3, 1)) return false;
	if (document.theform.dz_money.value <= 0){
		sl_alert("到账金额不能小于等于零！");
		return false;
	}

	if(parseFloat(form.dz_money.value) > parseFloat(form.yh_money.value)){
		sl_alert("累计到账金额不能大于预约金额"+form.yh_money_cn.value+"！");
		return false;
	}
	if (!sl_checkChoice(form.jk_type, "交款方式")) return false;
	return sl_check_update();
}

function _checked(obj) {
	document.getElementById("onway_attach").style.display = obj.checked?"":"none";
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
<input type=hidden name="yh_money_cn" value="<%=yh_money_cn%>">
<input type=hidden name="yh_money" value="<%=yh_money%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>到账处理</b></font></td>
	</tr>
</table>
<br/>
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
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(rg_money)) %>">
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
		<td align="right"><font color="red">*</font>交款方式:</td>
		<td align="left">
			<select name="jk_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1114, "") %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">在途资金:</td>
		<td align="left">
			<input type="checkbox" name="is_onway" value="1" onclick="javascript:_checked(this);"/>是
		</td>
	</tr>
	<tr id="onway_attach" style="display:none">
		<td align="right">相关附件:</td>
		<td align="left">
			<input type="file" name="onway_attach"/>
		</td>
	</tr>
	<tr>
		<td align="right"><input type="checkbox" name="flag_sendsmscustomer" value="1" checked/>发送短信 To:客户</td>
		<td align="left">
			<textarea rows="2" name="sms1_customer"  onkeydown="javascript:nextKeyPress(this)" cols="50"><%=sms1_customer%></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><input type="checkbox" name="flag_sendsmsmanager" value="1" checked/>发送短信 To:客户经理</td>
		<td align="left">
			<textarea rows="2" name="sms2_serviceman"  onkeydown="javascript:nextKeyPress(this)" cols="50"><%=sms2_serviceman%></textarea>
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