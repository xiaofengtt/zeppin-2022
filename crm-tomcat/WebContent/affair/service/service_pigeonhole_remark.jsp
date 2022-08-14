<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//声明辅助变量
boolean bSuccess = false;
List rsList = null;
Map rsMap = null;

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;

//声明显示字段
String serviceTitle ="";
Integer serviceType = Utility.parseInt(request.getParameter("serviceType"), new Integer(0));
String serviceTypeName = "";
String serviceWay = "";
String serviceWayName = "";
int serviceStatus = 0;//任务状态
String serviceStatusName = "";
String serviceResult =  Utility.trimNull(request.getParameter("serviceResult"));//处理备忘

if(request.getMethod().equals("POST")){
	vo = new ServiceTaskVO();	
	vo.setSerial_no(serial_no);
	vo.setResult(serviceResult);
	vo.setInputMan(input_operatorCode);
	
	serviceTask.treat(vo);
	bSuccess = true;
}

//编辑时显示 任务信息
if(serial_no.intValue()>0){
	//查询主信息
	vo = new ServiceTaskVO();	
	vo.setSerial_no(serial_no);
	vo.setServiceType(serviceType);
	vo.setInputMan(input_operatorCode);
	
	rsList = serviceTask.query(vo);	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
	}

	if(rsMap!=null){
		serviceTitle = Utility.trimNull(rsMap.get("ServiceTitle"));	
		serviceType = Utility.parseInt(rsMap.get("ServiceType").toString(), new Integer(0));
		serviceTypeName = Utility.trimNull(rsMap.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(rsMap.get("ServiceWay"));
		serviceWayName= Argument.getDictParamName(1109,serviceWay);
		serviceStatus = Utility.parseInt(Utility.trimNull(rsMap.get("Status")),0);
		serviceStatusName= Argument.getService_status_name(serviceStatus);
		serviceResult = Utility.trimNull(rsMap.get("Result"));
	}
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("messag.taskingMemo",clientLocale)%> </title><!-- 任务处理备忘 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;	
	if(v_bSuccess=="true"){	
		sl_update_ok();		
		window.close();
	}
}

function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="<%=request.getContextPath()%>/affair/service/service_pigeonhole_remark.jsp";
		document.getElementsByName('theform')[0].submit();
	}
}

/*验证数据*/
function validateForm(form){
	if(!sl_check(document.getElementById("serviceResult"),"<%=LocalUtilis.language("message.taskMemo",clientLocale)%> ",300,1)){ return false;}	//任务备忘		
	return sl_check_update();		
}
</script>

</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>

<div align="left" class="page-title">
    <!-- 任务管理 --><!-- 任务归档 --><!-- 任务备忘 -->
    <font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskArchiving",clientLocale)%>>><%=LocalUtilis.language("message.taskMemo",clientLocale)%> </b></font>
</div>
<br/>
<div  align="left"  style="margin-left:5px">
<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="table-popup">
		<tr style="">
			<td  width="120px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ：</font></td><!-- 任务标题 -->
			<td  width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceTitle%></font></td>	
			<td width="120px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ：</font></td><!-- 任务类别 -->
			<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceTypeName%></font></td>
		</tr>
		
		<tr style="">
			<td  width="120px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskFlagName",clientLocale)%> ：</font></td><!-- 任务状态 -->
			<td  width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceStatusName%></font></td>			
			<td width="120px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font> </td><!-- 联系方式 -->
			<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceWayName%></font></td>
		</tr>
		
		<tr style="">
			<td  width="120px"><font size="2" face="微软雅黑"><%=LocalUtilis.language("message.taskMemo",clientLocale)%> ：&nbsp;&nbsp;</font></td><!-- 任务备忘 -->
			<td  width="*" colspan="3">
				<textarea rows="8" name="serviceResult" id="serviceResult" onkeydown="javascript:nextKeyPress(this)" style="width:100%"><%=serviceResult%></textarea>			
			</td>
		</tr>
	
</table>
</div>

<div align="right" style="margin-right:5px; margin-top:10px;">
	<!-- 保存 -->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;	
	<!--关闭 -->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>

</form>
</BODY>
</HTML>