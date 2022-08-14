<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<% 
//得到客户ID
String my_status = Utility.trimNull(request.getParameter("my_status"));
Integer target_custid = Utility.parseInt(Utility.trimNull(request.getParameter("target_custid")),new Integer(0));//目标客户
Integer input_flag =  Utility.parseInt(Utility.trimNull(request.getParameter("input_flag")),new Integer(0));//传入参数标识 0：对话框 1.iframe
Integer action_flag =  Utility.parseInt(Utility.trimNull(request.getParameter("action_flag")),new Integer(0));//拨号选项 0:客户选择;1:手工拨号
String closeStr =LocalUtilis.language("message.close",clientLocale);//关闭
if(input_flag.intValue()==1){
	closeStr = LocalUtilis.language("message.back",clientLocale);	//返回
}
//生成对象
CustomerLocal customerLocal = EJBFactory.getCustomer();
CustomerVO vo_cust = new CustomerVO();
//声明参数
List rsList_customer = new ArrayList();
Map map_customer = null;
String cust_name = "";
String o_tel = "";
String h_tel = "";
String mobile = "";
String bp = "";
String o_tel2 = "";
String h_tel2 = "";
String mobile2 = "";
String bp2 = "";
//查询信息
if(target_custid.intValue()>0){
	vo_cust.setCust_id(target_custid);
	vo_cust.setInput_man(input_operatorCode);
	vo_cust.setQuery_flag(new Integer(1));
	rsList_customer = customerLocal.listCustomerLoad(vo_cust);
	
	if(rsList_customer.size()>0){
		map_customer = (Map) rsList_customer.get(0);
		cust_name = Utility.trimNull(map_customer.get("CUST_NAME"));
		mobile = Utility.trimNull(map_customer.get("MOBILE"));
		o_tel = Utility.trimNull(map_customer.get("O_TEL"));
		h_tel = Utility.trimNull(map_customer.get("H_TEL"));
		bp = Utility.trimNull(map_customer.get("BP"));
		mobile2 = Utility.trimNull(map_customer.get("H_MOBILE"));
		o_tel2 = Utility.trimNull(map_customer.get("H_O_TEL"));
		h_tel2 = Utility.trimNull(map_customer.get("H_H_TEL"));
		bp2 = Utility.trimNull(map_customer.get("H_BP"));
	}		
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("index.callcenter.callTel",clientLocale)%> </TITLE><!-- 拨打电话 -->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/jQuery/keypad/keyboard.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script type="text/javascript" src='<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/includes/jQuery/keypad/keyboard.js'></script>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
window.onload = function(){
	//修正模式窗口和frame父窗口获取phone对象问题 modi by guifeng 
	var input_flag = document.getElementById("input_flag").value;
	var action_flag = document.getElementById("action_flag").value;
	var target_custid = document.getElementById("target_custid").value;
	//是否手工拨号
	if(action_flag==1||target_custid==0){
		document.getElementById("custTelSelectDiv").style.display="none";
		document.getElementById("calloutphone").readOnly = false;
		//$('#calloutphone').keypad(); 
		//$('#calloutphone_prefix').keypad();
		//$.keypad.setDefaults({prompt: '请使用软键盘'});
		$('#calloutphone').addClass('keyboardInput').bind("blur",function(){setCallTel(this.value,this.value);});
	}
}
function hiddenTel(tel){
	var tel_hidden = '';
	for(i=0;i<tel.length-4;i++){
		tel_hidden = tel_hidden +"*";
	}
	tel_hidden = tel_hidden + tel.substring(tel.length-4,tel.length);
}
//拨打电话
function callOutConfirm2(){
	var tel_perfix = document.getElementById("calloutphone_perfix").value;
	var tel_show = document.getElementById("calloutphone").value;
	var phoneNumber = document.getElementById("phoneNumber").value;
	var tel = phoneNumber;
	var telnum = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;

	if(tel_show.length==0){
		sl_alert('<%=LocalUtilis.language("message.teleIsNull",clientLocale)%> ！');//您拨打的电话不能为空
		return false;
	}
	else if(tel_perfix.length>0){
		tel_show = tel_perfix + '-' + tel_show;
		tel = tel_perfix + ""+ phoneNumber;
	}
	else if(!telnum.test(tel)){
		//您输入的电话号码  不符合规则，请检查后重新输入
        sl_alert('<%=LocalUtilis.language("index.callcenter.yourTel",clientLocale)%>'+tel_show+'<%=LocalUtilis.language("index.callcenter.confirmRuls",clientLocale)%>');
		return false;
	}
	if(document.getElementById("zeroFlag").checked){
		tel="0"+tel;
		tel_show = "0"+tel_show;
		phoneNumber = "0"+phoneNumber;
	}

	if(sl_confirm('<%=LocalUtilis.language("index.callcenter.yourCallTel",clientLocale)%> :'+tel_show)){//您要拨打的电话号码是
		document.getElementById("phoneNumber").value = tel;
		var url = '<%=request.getContextPath()%>/callcenter/callingRecord.jsp?my_status=<%=my_status%>';
		document.theform.action = url;
		document.theform.submit();
	}
}
//设置电话号码
function setCallTel(tel,showTel){
	var action_flag = document.getElementById("action_flag").value;
	var target_custid = document.getElementById("target_custid").value;
	if(tel.indexOf("-")==-1){	
		document.getElementById("calloutphone_perfix").value = '';
		document.getElementById("phoneNumber").value = tel;
	}
	else{
		var telSplite = tel.split("-");
		document.getElementById("calloutphone_perfix").value = telSplite[0];	
		document.getElementById("phoneNumber").value = telSplite[1];
	}
	if(action_flag!=1&&target_custid!=0){
		document.getElementById("calloutphone").value = showTel;
	}
	showWaitting(0);
}
function showInfo(){
	var custid = document.getElementById("target_custid").value;
	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?showflag=1&cust_id='+custid;
	showModalDialog(url,window,'dialogWidth:800px;dialogHeight:600px;status:0;help:0');
	showWaitting(0);
}
//撤销
function cancelAction(){
	var input_flag = document.getElementById("input_flag").value;
	if(input_flag==1){
		history.back(-1);	
	}
	else if(input_flag==0){
		window.close();
	}
	else if(input_flag==2){
		document.parentWindow.parent.dialogHidden();
	}
};
</script>
</HEAD>
<BODY class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" name="input_flag" id="input_flag" value="<%=input_flag%>" />
<input type="hidden" name="input_flag2" id="input_flag2" value="2" />
<input type="hidden" name="action_flag" id="action_flag" value="<%=action_flag%>" />
<input type="hidden" name="phoneNumber" id="phoneNumber" value="" /><!--真实号码 -->
<input type="hidden" name="target_custid" id="target_custid" value='<%=target_custid%>' />
<div>
	<div align="left" class="page-title">
        <!--拨打电话-->  
		<font color="#215dc6"><b><%=LocalUtilis.language("index.callcenter.callTel",clientLocale)%> </b></font>
	</div>
	<div align="right" style="margin-right:20px;" class="btn-wrapper">
		<!--拨打--> 
		<button type="button" class="xpbutton2" id="btnCall" name="btnCall" onclick="javascript:callOutConfirm2();"><%=LocalUtilis.language("message.call",clientLocale)%> </button>&nbsp;&nbsp; 
		<button type="button" class="xpbutton2" id="btnReturn" name="btnReturn" onclick="javascript:cancelAction();"><%=closeStr %></button>&nbsp;&nbsp;
	</div>
	<br/>
</div>

<div style="margin-left:18px" align="left">
	<input type="text" name="calloutphone_perfix" id="calloutphone_prefix" style="width:60px;">&nbsp;&nbsp;——&nbsp;&nbsp;
	<input type="text" name="calloutphone" id="calloutphone" style="width:120px;"> &nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" name="zeroFlag" id="zeroFlag"/><%=LocalUtilis.language("message.plus0",clientLocale)%> &nbsp;&nbsp;<!--加"0"拨号-->  
</div>

<div align="center" style="margin-top:5px;" id="custTelSelectDiv">
	<table border="0" width="95%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr style="background:F7F7F7;">
			<td width="30%"><font size="2" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("class.customerName",clientLocale)%>  </b></font></td><!--客户名称-->  
			<td align="center">
                <!--点击查看客户信息-->  
				<a title="<%=LocalUtilis.language("message.viewCustomerInfo",clientLocale)%> " href="javascript:showInfo();" class="a2"><%=cust_name%></a>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td width="30%"><font size="2" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("class.mobile",clientLocale)%> </b></font></td><!--手机号码-->  
			<td align="center">
			<%if(mobile2.length()>0){ %>
				<!--点击使用-->  
                <a title="<%=LocalUtilis.language("message.clickToUse",clientLocale)%> " href="javascript:setCallTel('<%=mobile2%>','<%=mobile%>');" class="a2"><%=mobile%></a>	
			<%}
			else{%>
				<font size="2" face="微软雅黑"><b><%=LocalUtilis.language("message.nothing",clientLocale)%> </b></font><!--无-->  
			<%}%>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td width="30%"><font size="2" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("class.mobile",clientLocale)%> 2</b></font></td><!--手机号码--> 
			<td align="center">
			<%if(bp2.length()>0){ %>
                <!--点击使用-->  
				<a title="<%=LocalUtilis.language("message.clickToUse",clientLocale)%> " href="javascript:setCallTel('<%=bp2%>','<%=bp%>');" class="a2"><%=bp%></a>
			<%}
			else{%>
				<font size="2" face="微软雅黑"><b><%=LocalUtilis.language("message.nothing",clientLocale)%> </b></font><!--无-->  
			<%}%>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td width="30%"><font size="2" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("class.companyPhone",clientLocale)%> </b></font></td><!--公司电话-->
			<td align="center">
				<%if(o_tel2.length()>0){%>
                <!--点击使用-->  
				<a title="<%=LocalUtilis.language("message.clickToUse",clientLocale)%> " href="javascript:setCallTel('<%=o_tel2%>','<%=o_tel%>');" class="a2"><%=o_tel%></a>				
				<%}
				else{%>
					<font size="2" face="微软雅黑"><b><%=LocalUtilis.language("message.nothing",clientLocale)%> </b></font><!--无-->
				<%}%>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td width="30%"><font size="2" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("class.telephone",clientLocale)%> </b></font></td><!--住宅电话-->  
			<td align="center">
				<%if(h_tel2.length()>0){ %>
                <!--点击使用-->  
				<a title="<%=LocalUtilis.language("message.clickToUse",clientLocale)%> " href="javascript:setCallTel('<%=h_tel2%>','<%=h_tel%>');" class="a2"><%=h_tel%></a>
				<%}
				else{%>
					<font size="2" face="微软雅黑"><b><%=LocalUtilis.language("message.nothing",clientLocale)%> </b></font><!--无-->
				<%}%>
			</td>
		</tr>
	</table>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>