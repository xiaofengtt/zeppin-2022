<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

RemindersVO vo = new RemindersVO();
RemindersLocal local = EJBFactory.getReminders();

Integer serial_no = null;
Integer schedule_date = null;
String content = null;
Integer check_flag = null;
Integer input_man = null;

//保存信息
if(request.getMethod().equals("POST")){
 	serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),null);
	schedule_date = Utility.parseInt(Utility.trimNull(request.getParameter("schedule_date")),null);
	content = Utility.trimNull(request.getParameter("content"));
	check_flag = Utility.parseInt(Utility.trimNull(request.getParameter("check_flag")),null);
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setSerial_no(serial_no);
	vo.setSchedule_date(schedule_date);
	vo.setContent(content);
	vo.setCheck_flag(check_flag);
	vo.setInput_man(input_man);
	
	try{
		local.modi(vo);
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
<title><%=LocalUtilis.language("menu.modifyMeno",clientLocale)%> </title><!--修改备忘录-->
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

	/*验证数据*/
	function validateForm(form){		
		return sl_check_update();	
	}
	
	/*保存*/
	function SaveAction(){
		if(!sl_checkDate(document.theform.schedule_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> ")){//开始日期
			return false;
		}else{
			syncDatePicker(document.theform.schedule_date_picker,document.theform.schedule_date);			
		}
		
		if(!sl_check(document.getElementsByName("content")[0], "<%=LocalUtilis.language("class.menoDescribes",clientLocale)%> ", 200, 1)) return false;//备忘录描述
		
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}
	
	window.onload = function(){	
		$$("serial_no").setAttribute("value",oState.data.serial_no);
		$$("content").setAttribute("value",oState.data.content);
		$$("schedule_date").setAttribute("value",oState.data.schedule_date);
		$$("schedule_date_picker").setAttribute("value",exchangeDateStyle(oState.data.schedule_date));
		selectIndex($$("check_flag"),oState.data.check_flag);
	}
	
	function $$(_name){
		return document.getElementsByName(_name)[0] || (new Object());
	}
	
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="memo_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<input type="hidden" name="serial_no" />

<div align="center">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="product-list">
		<tr>
			<td align="right" style="width:90px;">*<%=LocalUtilis.language("class.startDate",clientLocale)%> ：</td><!--开始日期-->
			<td colspan="3">
				<input type="text" name="schedule_date_picker" class=selecttext style="width: 150px;" <%if(schedule_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(schedule_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.schedule_date_picker,theform.schedule_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="schedule_date" id="schedule_date" />			
			</td>
		</tr>	
		<tr>
			<td align="right" style="width:30%">*<%=LocalUtilis.language("class.memoStatus",clientLocale)%> ：</td><!--备忘录状态-->
			<td colspan="3">
				<select name="check_flag" style="width: 173px;">
					<option value="1"><%=LocalUtilis.language("message.new",clientLocale)%> </option><!--新建-->
					<option value="2"><%=LocalUtilis.language("message.read",clientLocale)%> </option><!--已读-->
					<option value="3"><%=LocalUtilis.language("message.finish",clientLocale)%> </option><!--完成-->
				</select>
			</td>
		</tr>	
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.menoDescribes",clientLocale)%> ：</td><!--备忘录描述-->
			<td colspan="3">
				<textarea rows="3" name="content" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--保存-->
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button><!--返回-->
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>