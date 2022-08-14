<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer offset_flag = Utility.parseInt(request.getParameter("offset_flag"), new Integer(0));
Integer isValid = Utility.parseInt(request.getParameter("isValid"), new Integer(0));

//声明辅助变量
boolean bSuccess = false;

//声明参数变量
Integer serviceType = new Integer(0);  //服务类型
String serviceTypeName ="";	
Integer offsetDays = new Integer(0); //天偏移量
String serviceWay ="";		//服务途径
String description ="";	 //描述信息
String noticeCaption ="";	 // 提示文字
Integer executor = new Integer(0);
Integer autoFlag = new Integer(0);
Integer tempID = new Integer(0);
String tempTitle = "";

//获得对象
ServiceManageLocal serviceManage = EJBFactory.getServiceManage();
ServiceManageVO vo = new ServiceManageVO();

if(request.getMethod().equals("POST")){
	serviceTypeName = Utility.trimNull(request.getParameter("serviceTypeName"));
	serviceType = Utility.parseInt(Utility.trimNull(request.getParameter("serviceType")), new Integer(0));
	offsetDays =  Utility.parseInt(Utility.trimNull(request.getParameter("offsetDays")), new Integer(0));
	serviceWay = Utility.trimNull(request.getParameter("serviceWay"));
	executor = Utility.parseInt(Utility.trimNull(request.getParameter("executor")), null);
	description = Utility.trimNull(request.getParameter("description"));
	noticeCaption = Utility.trimNull(request.getParameter("noticeCaption"));
	autoFlag = Utility.parseInt(Utility.trimNull(request.getParameter("autoFlag")), new Integer(0));
	tempID = Utility.parseInt(request.getParameter("tempID"), new Integer(0));
	tempTitle = Utility.trimNull(request.getParameter("tempTitle"));

	//若选择推迟
	if(offset_flag.intValue()>0){
		offsetDays = new Integer(offsetDays.intValue() * (-1));
	}
	
	vo.setSerial_no(serial_no);
	vo.setServiceType(serviceType);
	vo.setServiceTypeName(serviceTypeName);
	vo.setOffsetDays(offsetDays);
	vo.setServiceWay(serviceWay);
	vo.setExecutor(executor);
	vo.setDescription(description);
	vo.setNoticeCaption(noticeCaption);
	vo.setInputMan(input_operatorCode);
	vo.setIsValid(isValid);
	vo.setAutoFlag(autoFlag);
	vo.setTempID(tempID);
	vo.setTempTilte(tempTitle);
	
	serviceManage.edit_TServiceDefine(vo);	
	bSuccess = true;
}
else{
	List list = null;
	Map map = null;
	
	//根据serial_no取得activity的相关信息
	if(serial_no.intValue()!= 0){
		vo.setSerial_no(serial_no);
		list = serviceManage.query_TServiceDefine(vo);
		map = (Map)list.get(0);
	}
	
	//得到数据 赋值
	if(map!=null){
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		serviceType = Utility.parseInt(Utility.trimNull(map.get("ServiceType")), new Integer(0));
		offsetDays = Utility.parseInt(Utility.trimNull(map.get("OffsetDays")), new Integer(0));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		executor = Utility.parseInt(Utility.trimNull(map.get("Executor")),null);
		description = Utility.trimNull(map.get("Description"));
		noticeCaption = Utility.trimNull(map.get("NoticeCaption"));
		isValid = Utility.parseInt(Utility.trimNull(map.get("IsValid")), new Integer(0));
		//executor = (executor.intValue()==0?input_operatorCode:executor);	
		autoFlag = Utility.parseInt(Utility.trimNull(map.get("AutoFlag")), new Integer(0));
		tempID = Utility.parseInt(Utility.trimNull(map.get("TempID")), new Integer(0));
		tempTitle = Utility.trimNull(map.get("TempTitle"));

		if(offsetDays.intValue()<0){
			offset_flag = new Integer(1);
		}
	}
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
<title><%=LocalUtilis.language("menu.modifyTaskDefinition",clientLocale)%> </title><!-- 任务定义修改 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language=javascript>
	/*验证数据*/
	function validateForm(form){	
		if(!sl_checkNum(document.theform.offsetDays, "<%=LocalUtilis.language("class.OffsetDays",clientLocale)%> ",30,1)){return false;}//天偏移量
		if(!sl_check(document.theform.noticeCaption, "<%=LocalUtilis.language("class.noticeCaption",clientLocale)%> ",30,1)){return false;}//任务提示说明文字
		if(!sl_check(document.theform.description, " <%=LocalUtilis.language("class.taskDescription",clientLocale)%> ",200,0)){return false;}//详细描述
		if(!sl_checkChoice(document.getElementsByName('serviceWay')[0],"<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ")){ return false;}//联系方式
		var v_serviceWay = document.getElementsByName('serviceWay')[0].value;
		if(v_serviceWay=='110905'){
			if(!sl_check(document.getElementsByName('tempTitle')[0],"<%=LocalUtilis.language("class.tempTitle",clientLocale)%> ",50,1)){ return false;}//短信模板
		}
		return sl_check_update();	
	
	}

	/*保存*/
	function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.parent.document.getElementById("closeImg").click();	
		window.parent.location.reload();
	}
	function chooseAction(){
		var url = "<%=request.getContextPath()%>/affair/sms/smsTemplate_choose.jsp";
		var ret = showModalDialog(url,'','dialogWidth:610px;dialogHeight:450px;status:0;help:0');
		if(ret!=null&&ret.length>0){
			var rets = ret.split("_");
			document.getElementById("tempID").value=rets[0];
			document.getElementById("tempTitle").value=rets[1];
		}
		showWaitting(0);
	}
	function showTr(){
		var serviceWay = document.getElementsByName('serviceWay')[0].value;
		if(serviceWay=='110905'){
			document.getElementById("smsTr1").style.display = "";
			document.getElementById("smsTr2").style.display = "";
		}
		else{
			document.getElementById("smsTr1").style.display = "none";
			document.getElementById("smsTr2").style.display = "none";
		}
	}
	//初始化页面
	window.onload = function(){
		showTr();

		var v_bSuccess = document.getElementById("bSuccess").value;
		if(v_bSuccess=="true"){	
			sl_update_ok();	
			//window.parent.location.reload();
			//window.parent.document.getElementById("closeImg").click();		
			//window.close();
		}
	}
	function clearAction(){
		document.getElementById("tempID").value=0;
		document.getElementById("tempTitle").value="";
	}
</script>

</head>

<body class="body body-nox">
<form name="theform" method="post" action="service_define_edit.jsp" onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="serviceType" name="serviceType" value="<%=serviceType%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("menu.taskDefinition",clientLocale)%> </b></font><!-- 任务管理--><!-- 任务定义 -->
</div>
<br/>
<div align="left" >
	<table cellSpacing="1" cellPadding="2" width="100%"  class="product-list">
	 	<tr style="">
	 		<td colspan="2" align="left"></td><!-- 任务定义 -->
	 	</tr>
		<tr style="">	
			<td  width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> :&nbsp;&nbsp;</font></td><!-- 任务类别 -->
			<td  width="*">
				<input readonly class="edline" name="serviceTypeName" size="30" value="<%=serviceTypeName%>" onkeydown="javascript:nextKeyPress(this)"/>				
				&nbsp;&nbsp;&nbsp;			
			</td>
		</tr>
		<tr style="">
			<td  width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.isValid",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 是否开启 -->
			<td  width="*">	
				<input type="checkbox" name="isValid" <%if(isValid.intValue()==1) out.print("checked");%> value="1" />		
				&nbsp;&nbsp;&nbsp;			
			</td>
		</tr>
		<tr style="">
			<td  width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 客户经理 -->
			<td  width="*">
				<select name="executor" style="width:120px">
					<%=Argument.getManager_Value(executor)%>
				</select>				
			</td>
		</tr>
		<tr style="">
			<td  width="*" align="right"><font size="2" face="微软雅黑">*<%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 联系方式 -->
			<td  width="*">
				<select name="serviceWay" style="width:120px" onchange="javascript:showTr();">
					<%= Argument.getDictParamOptions(1109, serviceWay)%>
				</select>				
			</td>
		</tr>
		<tr id="smsTr1" style=" display:none">
			<td width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.sendSms",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 短信发送 -->
			<!-- 自动 -->
            <td width="*"><input type="checkbox" name="autoFlag" <%if(autoFlag.intValue()==1) out.print("checked");%> value="1" /><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.auto",clientLocale)%> </font></td>
		</tr>
		<tr id="smsTr2" style=" display:none">
			<td width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.tempTitle",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 短信模板 -->
			<td width="*">				
				<input type="text" name="tempTitle" id="tempTitle" size="30"  value="<%=tempTitle%>" onkeydown="javascript:nextKeyPress(this)" readonly />	
				<!-- 选择 -->
                &nbsp;&nbsp;&nbsp;<button type="button" class="xpbutton2" id="btnChoTemp" name="btnChoTemp" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
				<!-- 清空 -->
                &nbsp;<button type="button" class="xpbutton2" id="btnClear" name="btnClear" onclick="javascript:clearAction();"><%=LocalUtilis.language("message.clear",clientLocale)%> </button>
				<input type="hidden" name="tempID" id="tempID"  value="<%=tempID%>" />
			</td>
		</tr>
		<tr style="">
			<!-- 天偏移量 -->
            <td  width="*" align="right"><font size="2" face="微软雅黑">*<%=LocalUtilis.language("class.OffsetDays",clientLocale)%> :</font>&nbsp;&nbsp;</td>
			<td  width="*">
				<select name="offset_flag">
					<option value="1" <%if(offset_flag.intValue()==1){out.print("selected");}%>><%=LocalUtilis.language("message.inAdvance",clientLocale)%> </option><!-- 提前 -->
					<option value="0" <%if(offset_flag.intValue()==0){out.print("selected");}%>><%=LocalUtilis.language("message.putOff",clientLocale)%> </option><!-- 推迟 -->
				</select>	
				<input name="offsetDays" type="text" value="<%=Math.abs(offsetDays.intValue())%>" size="8"/>&nbsp;&nbsp;<%=LocalUtilis.language("message.days",clientLocale)%> <!-- 天 -->
			</td>
		</tr>
		<tr style="">
			<td  width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.noticeCaption",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 提示说明文字 -->
			<td  width="*">
				<textarea rows="3" name="noticeCaption" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=noticeCaption%></textarea>			
			</td>
		</tr>	
		
		<tr style="">
			<td  width="*" align="right"><font size="2" face="微软雅黑"><%=LocalUtilis.language("class.taskDescription",clientLocale)%> :</font>&nbsp;&nbsp;</td><!-- 定义 --> <!--详细描述 -->
			<td  width="*">
				<textarea rows="3" name="description" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=description%></textarea>			
			</td>
		</tr>	
	</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<!-- 保存 -->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 关闭 -->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>
</form>
</body>
</html>
<%serviceManage.remove();%>