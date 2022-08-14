<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.customer.CustomerLocal"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递参数
Integer serial = Utility.parseInt(Utility.trimNull(request.getParameter("serial")),new Integer(0));
System.out.print("--------------------------------------------------------------------------"+serial);
Integer compType = Utility.parseInt(Utility.trimNull(request.getParameter("compType")),new Integer(0));
Integer inputDate = Utility.parseInt(Utility.trimNull(request.getParameter("inputDate")),new Integer(0));
String content = Utility.trimNull(request.getParameter("content"));
String checkContent = Utility.trimNull(request.getParameter("checkContent"));
String custName = Utility.trimNull(request.getParameter("custName"));;

//声明辅助变量
boolean bSuccess = false;
int iCount = 0;
List list = null;
Map map = new HashMap();

//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//保存对象
if(request.getMethod().equals("POST")){
	vo.setSerial_no(serial);
	vo.setComp_type(compType);
	vo.setInput_date(inputDate);
	vo.setContent(content);
	vo.setCheck_content(checkContent);
	vo.setInput_man(input_operatorCode);
	vo.setCust_name(custName);

	if(serial.intValue()>0){
		customer.updateCustomerComplaint(vo);		
		bSuccess = true;
	}
	else{
		customer.addCustomerComplaint(vo);
		bSuccess = true;
	}
}

if(serial.intValue()>0&&!request.getMethod().equals("POST")){
	Object[] params = new Object[7];
	params[0] = serial;
	params[1] = "";
	params[2] = new Integer(0);
	params[3] = new Integer(0);
	params[4] = new Integer(0);
	params[5] = "";
	params[6] = new Integer(0);

	IPageList pageList = customer.getCustomerComplaint(params,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
	map=(Map)pageList.getRsList().get(0);
	custName=Utility.trimNull(map.get("CUST_NAME"));
	compType=Utility.parseInt(Utility.trimNull(map.get("COMP_TYPE")),new Integer(0));
	inputDate = Utility.parseInt(Utility.trimNull(map.get("INPUT_DATE")),new Integer(0));
	content=Utility.trimNull(map.get("CONTENT"));
}

%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>客户投诉</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language="javascript">
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}
/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}

/*验证数据*/
function validateForm(form){	
	if(!sl_check(form.custName,"客户姓名",50,2)){return false;}
	if(!sl_check(form.content, "投诉内容",200,1)){return false;}
	if(!sl_checkDate(form.start_date_picker,"投诉时间")){return false;}	
	if(form.compType.value==0){
		sl_alert("请选择投诉方式!");
		return false;
	}

	<%if(serial.intValue()!=0){%>
		if(!sl_check(form.checkContent, "核查情况",1000,1)){return false;}
	<%}%>

	syncDatePicker(theform.start_date_picker, theform.inputDate);
	return sl_check_update();
}
/*取消*/
function CancelAction(){
	window.close();
}


</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" action="client_complaint_add.jsp" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial" name="serial" value="<%=serial%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b>客户投诉>>客户投诉设置</b></font>
</div>
<br/>
<div align="left" class="table-popup">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
	 	<tr>
	 		<td  width="100px" align="right">客户姓名:</td>
	 		<td  width="*">
	 			<input name="custName" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=custName%>" <%if(serial.intValue()!=0){ %>readonly <%}%>/>
	 		</td>
	 		<td  width="100px" align="right">投诉方式:</td>
			<td>
				<select size="1"  name="compType" style="width: 150px" onkeydown="javascript:nextKeyPress(this)" <%if(serial.intValue()!=0){ %>disabled="disabled"<%}%>>
					<%=Argument.getComplaintOptions(compType)%>
				</select>
			</td>
	 	</tr>
	 	<tr>
	 		<td width="100px" align="right" vAlign="top">投诉日期:</td>
			<td width="*">
				<input <%if(serial.intValue()!=0){ %> <%}%>type="text" name="start_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(inputDate)%>" readonly>
				<input type="<%if(serial.intValue()==0){%>button<%}else{%>hidden<%}%>" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);">
				<input type="hidden" name="inputDate" value="">
			</td>
	 	</tr>
		<%if(serial.intValue()!=0){%>
		<tr>
	 		<td width="100px" align="right" vAlign="top">*核查情况:</td>
			<td width="*">
				<input name="checkContent" onkeydown="javascript:nextKeyPress(this)" size="40" maxlength="60" value="<%=checkContent%>">
			</td>
	 	</tr>
		<%}%>
 		<tr>
			<td width="100px" align="right" vAlign="top">投诉内容:</td>
			<td  colspan=3><textarea rows="5" name="content" cols="100"><%=content%></textarea></td>
		</tr>
	</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<!-- 保存 -->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 关闭 -->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<%
customer.remove();
%>
