<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();

Integer managerid = null;
String extension = null;
String recordextension = null;
String dutyname = null;
int provideservices = 0;
Integer input_man = null;
Integer t_serviceType = new Integer(0);
//保存信息
if(request.getMethod().equals("POST")){
	String[] temp_checks = request.getParameterValues("serviceValueBox");
	//逐个删除
	if(temp_checks!=null){
		for(int i=0;i<temp_checks.length;i++){
			t_serviceType = Utility.parseInt(temp_checks[i], new Integer(0));			
			provideservices += t_serviceType.intValue();
		}
	}
	managerid = Utility.parseInt(Utility.trimNull(request.getParameter("managerid")),new Integer(0));
	extension = Utility.trimNull(request.getParameter("extension"));
	recordextension = Utility.trimNull(request.getParameter("recordextension"));
	dutyname = Utility.trimNull(request.getParameter("dutyname"));
	//ovideservices = Utility.parseInt(Utility.trimNull(request.getParameter("provideservices")),new Integer(0));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setManagerid(managerid);
	vo.setExtension(extension);
	vo.setRecordextension(recordextension);
	vo.setDutyname(dutyname);
	vo.setProvideservices(new Integer(provideservices));
	vo.setInput_man(input_man);
	try{
		tcustmanagers_Bean.modi(vo);
	}catch(Exception e){
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
	}
	bSuccess = true;
}
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("index.menu.addManager",clientLocale)%> </title><!-- 新增经理 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
	var oState = {}
	
	oState = window.dialogArguments;
	
	<% if(bSuccess){ %>
		window.returnValue = true;
		window.close();
	<%}%>	
	
	function ModiAction(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/service/service_define_template.jsp";
		oState.service_value = document.getElementsByName("provideservices")[0].getAttribute("value");
		oState.flag = "edit";
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:370px;status:0;help:0')){
			document.getElementsByName("provideservices")[0].setAttribute("value",oState.service_value);
		}	
	}
	
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}
	
	function validateForm(form){
		if(document.getElementsByName("managerid")[0].getAttribute("value") == ""){
			alert("<%=LocalUtilis.language("message.selectOperator",clientLocale)%> ！");<!-- 请选择操作员 -->
			return false;
		}
		if(!sl_checkNum(document.getElementsByName("extension")[0], "<%=LocalUtilis.language("class.extension",clientLocale)%> ", 9, 0)) return false;//分机号码
		if(!sl_checkNum(document.getElementsByName("recordextension")[0], "<%=LocalUtilis.language("class.recordExtension",clientLocale)%> ", 9, 0)) return false;//录音机号码
		if(!sl_check(document.getElementsByName("dutyname")[0], "<%=LocalUtilis.language("class.dutyName",clientLocale)%> ", 120, 0)) return false;//岗位名称
		
		return sl_check_update();	
	}

	function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/initmanager_update.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="5" class="product-list">
		<tr>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.operator",clientLocale)%>：</td><!-- 操作员 -->
			<td>
				<select name="managerid" style="width:122px;">
					<%=Argument.getOperator_Value(null)%>
				</select>
			</td>
			<td style="width:90px;" align="right"><%=LocalUtilis.language("class.extension",clientLocale)%>：</td><!-- 分机号码 -->
			<td><input type="text" name="extension"></td>
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.dutyName",clientLocale)%>：</td><!-- 岗位名称 -->
			<td >
				<input type="text" name="dutyname" >		
			</td>
			<td style="width:90px;" align="right"><%=LocalUtilis.language("class.recordExtension",clientLocale)%>：</td><!-- 录音机号码 -->
			<td><input type="text" name="recordextension"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.serviceType",clientLocale)%>：</td><!-- 服务类别 -->
			<td colspan="3">
				<!--
					<button type="button" onclick="ModiAction()">
						<img border="0" src="/images/edit.gif" align="center" title="选择" width="20" height="15">
					</button>
				-->
				<input type="hidden" name="provideservices">
				<table width="100%">
					<tr>
						
		<%
		//获得对象
		ServiceManageLocal serviceManage = EJBFactory.getServiceManage();
		ServiceManageVO vo_service = new ServiceManageVO();
		
		//页面辅助变量
		int iCount = 0;
		int iCurrent = 0;
		List list_service = serviceManage.query_TServiceDefine(vo_service);
		Map map_service = null;
		
		//声明参数变量
		Integer serviceType = new Integer(0);
		String serviceTypeName = "";
		Iterator iterator = list_service.iterator();	
		
		while(iterator.hasNext()){
			iCount++;
			map_service = (Map)iterator.next();
			serviceType = Utility.parseInt(Utility.trimNull(map_service.get("ServiceType")),new Integer(0));
			serviceTypeName = Utility.trimNull(map_service.get("ServiceTypeName"));	
		%>
				<td bgcolor="#FFFFFF" align="left">
					<input type="checkbox" name="serviceValueBox" class="selectAllBox" value="<%=serviceType%>" onclick="">
					&nbsp;&nbsp;&nbsp;<%=serviceTypeName%>
				</td>
				<%if(iCount%2==0){%>
					</tr>
					<tr>
				<%}%>
		<%}%>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
    <!-- 保存 -->
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 关闭-->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
<%
tcustmanagers_Bean.remove();
serviceManage.remove();
%>
<script language="javascript">
window.onload = function (){
		try{
			var _managerid = document.getElementsByName("managerid")[0];
			var _extension = document.getElementsByName("extension")[0];
			var _dutyname = document.getElementsByName("dutyname")[0];
			var _provideservices = document.getElementsByName("provideservices")[0];
			var _recordextension = document.getElementsByName("recordextension")[0];
	
			selectIndex(_managerid,oState.data.managerid);
			
			if(oState.data.extension!="null"){
				_extension.setAttribute("value",oState.data.extension);
			}
			if(oState.data.recordextension!="null"){
				_recordextension.setAttribute("value",oState.data.recordextension);
			}			
			if(oState.data.dutyname!="null"){
				_dutyname.setAttribute("value",oState.data.dutyname);
			}			
			if(oState.data.provideservices!="null"){
				_provideservices.setAttribute("value",oState.data.provideservices);
				
				var _serviceValueBox = document.getElementsByName("serviceValueBox");
				for(var i=0;i != _serviceValueBox.length; i++){
					if( (parseInt(_serviceValueBox[i].getAttribute("value")) & parseInt(oState.data.provideservices)) != 0){
							_serviceValueBox[i].checked = true;	
					}
				}
			}
		}catch(e){

		}
		
		
}
</script>
</BODY>
</HTML>