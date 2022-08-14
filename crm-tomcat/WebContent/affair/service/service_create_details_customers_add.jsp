<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer needFeedback = Utility.parseInt(request.getParameter("needFeedback"), new Integer(0));
Integer customer_cust_id = Utility.parseInt(request.getParameter("customer_cust_id"), new Integer(0));

//声明辅助变量
boolean bSuccess = false;

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//保存信息
if(request.getMethod().equals("POST")){
	vo.setTaskSerialNO(serial_no);
	vo.setNeedFeedBack(needFeedback);
	vo.setTargetCustID(customer_cust_id);
	vo.setInputMan(input_operatorCode);
	
	serviceTask.append_details(vo);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.addCustomerService",clientLocale)%> </title><!-- 新增客户服务 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>
	/*保存*/
	function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}
	
	/*验证数据*/
	function validateForm(form){
		return sl_check_update();		
	}
	
	/*客户信息*/
	function getMarketingCustomer(prefix,readonly){
		cust_id = getElement(prefix, "cust_id").value;
		
		var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
		
		v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
		if (v != null){
			showMarketingCustomer(prefix, v);
		}	
		
		return (v != null);
	}
	
	/*用于销售管理里*/
	function showMarketingCustomer(prefix, v){	
		getElement(prefix, "cust_name").value = v[0];
		getElement(prefix, "cust_type_name").value = v[1];
		getElement(prefix, "card_id").value = v[2];
		
		if(getElement(prefix, "h_tel")!=null)
			getElement(prefix, "h_tel").value = v[3];	
		if(getElement(prefix, "mobile")!=null)
			getElement(prefix, "mobile").value = v[4];		
		if(getElement(prefix, "post_address")!=null)
			getElement(prefix, "post_address").value = v[5];		
		if(getElement(prefix, "post_code")!=null)
			getElement(prefix, "post_code").value = v[6];	
		getElement(prefix, "cust_id").value = v[7];
			
		if(getElement(prefix, "service_man")!=null)
			getElement(prefix,"service_man").value=v[8];
		if(getElement(prefix, "service_man_value")!=null)
			getElement(prefix,"service_man_value").value=v[9];
		if(getElement(prefix, "o_tel")!=null)
			getElement(prefix, "o_tel").value = v[10];
		if(getElement(prefix, "bp")!=null)
			getElement(prefix, "bp").value = v[11];
			
		if(getElement(prefix, "is_link")!=null){
			getElement(prefix, "is_link").value = v[12];
			
			if(v[12]==1)
				getElement(prefix, "is_link").checked = true;
			else
				getElement(prefix, "is_link").checked = false;	
		}	
		
		getElement(prefix, "cust_type").value = v[13];		
		getElement(prefix, "cust_on").value = v[14];		
		getElement(prefix, "card_type").value = v[15];		
		getElement(prefix, "legal_man").value = v[16];	
		getElement(prefix, "contact_man").value = v[17];
		if(getElement(prefix, "e_mail")!=null)
			getElement(prefix, "e_mail").value = v[18];	
			
			
		if(getElement(prefix, "gain_acct")!=null)
			getElement(prefix, "gain_acct").value = v[0];  
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" value="<%=serial_no%>"/>

<!--客户信息变量-->
<input type="hidden" name="customer_cust_id" value=""/>
<input type="hidden" name="customer_cust_on" value=""/>
<input type='hidden' name="customer_service_man_value"  value=""/>
<input type='hidden' name="customer_cust_type"  value=""/>
<input type='hidden' name="customer_card_type" value=""/>
<input type='hidden' name="customer_legal_man" value=""/>
<input type='hidden' name="customer_contact_man" value=""/>
<input type='hidden' name="customer_e_mail" value=""/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.addCustomer",clientLocale)%> </b></font><!-- 客户添加 -->
	<hr noshade color="#808080" size="1">
</div>

<div align="center">
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
	<tr> 
		<td align="left"><b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b></td><!-- 客户信息 -->	
        <!-- 客户信息 --><!-- 请选择 -->
		<td align="left"><button type="button" class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.customerInfomation",clientLocale)%> " onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </button></td>
	<tr> 
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!-- 客户名称 -->
		<td colspan=3><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!-- 客户类别 -->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!-- 证件号码 -->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.telephone",clientLocale)%> :</td><!-- 住宅电话 -->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.mobile",clientLocale)%> :</td><!-- 手机 -->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!-- 联系地址 -->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!-- 邮政编码 -->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>	
	<tr>
		<td colspan="4">
			<hr size="1">
		</td>
	</tr> 
	<tr>
		<td align="left" colspan="4">
			<%=LocalUtilis.language("class.feedback",clientLocale)%> ：&nbsp;<!-- 反馈 -->
			<%=LocalUtilis.language("message.need",clientLocale)%><input type="radio" name="needFeedback" value="1" checked/><!-- 需要 -->
			<%=LocalUtilis.language("message.no",clientLocale)%><input type="radio" name="needFeedback" value="2"/><!-- 不需要 -->
		</td>
	</tr> 	
</table>
</div>

<div align="right">	
	<!-- 确认 -->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 关闭 -->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>

</form>
<%@ include file="/includes/foot.inc"%>
<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){		
		window.returnValue = 1;
		window.close();
	}
}
</script>
</BODY>
</HTML>