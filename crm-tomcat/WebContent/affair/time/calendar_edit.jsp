<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

ScheDulesVO vo = new ScheDulesVO();
ScheDulesLocal local = EJBFactory.getScheDules();

Integer serial_no = null;
Integer schedule_type = null;
String schedule_start_date = null;
String schedule_end_date = null;
String content = null;
Integer input_man = null;

//������Ϣ
if(request.getMethod().equals("POST")){
 	serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));
	schedule_type = Utility.parseInt(Utility.trimNull(request.getParameter("schedule_type")),null);
	schedule_start_date = Utility.trimNull(request.getParameter("schedule_start_date"));
	schedule_end_date = Utility.trimNull(request.getParameter("schedule_end_date"));
	content = Utility.trimNull(request.getParameter("content"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setSerial_no(serial_no);
	vo.setSchedule_type(schedule_type);
	vo.setSchedule_start_date(schedule_start_date);
	vo.setSchedule_end_date(schedule_end_date);
	vo.setContent(content);
	vo.setInput_man(input_man);
	
	try{
		local.modi(vo);
	}catch(Exception e){
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale);//����ʧ��
		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\");window.returnValue=null; window.close();</script>");
	}
	
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
<title><%=LocalUtilis.language("message.changeSchedule",clientLocale)%> </title><!--�޸��ճ�-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></SCRIPT>
<script language=javascript>
	var oState = {}
	
	oState = window.dialogArguments;

	<% if(bSuccess){ %>
		window.returnValue = true;
		window.close();
	<%}%>

	/*��֤����*/
	function validateForm(form){		
		return sl_check_update();	
	}
	
	/*����*/
	function SaveAction(){
		var startTime = Date.parse(new Date($("#schedule_start_date").val().replace(/-/g,'/')));
		var endTime = Date.parse(new Date($("#schedule_end_date").val().replace(/-/g,'/')));
		if(startTime > endTime){
			alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
			return false;
		}
		if(startTime == endTime){
			alert("��ʼʱ�䲻�ܵ��ڽ���ʱ��");
			return false;
		}
		if(document.getElementsByName("schedule_type")[0].getAttribute("value") == ""){
			alert("<%=LocalUtilis.language("message.selectScheduleType",clientLocale)%> ��"); return false;//��ѡ���ճ�����
		}
		
		if(!sl_check(document.getElementById('schedule_start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 60, 1))return false;//��ʼʱ��
		if(!sl_check(document.getElementById('schedule_end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 60, 1))return false��//����ʱ��
		if(!sl_check(document.getElementsByName("content")[0], "<%=LocalUtilis.language("class.scheduleDescription",clientLocale)%> ", 512, 1)) return false;//�ճ�����
		

		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	/*ɾ��*/
	function DelSelfAction(){
		theform.action = "calendar_remove.jsp?number="+oState.serial_no;
		theform.submit();
	}
	
	/*ȡ��*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}
	
	window.onload = function(){
		$$("serial_no").setAttribute("value",oState.serial_no);
		selectIndex($$("schedule_type"),oState.schedule_type);
		$$("content").setAttribute("value",oState.content);
		$$("schedule_start_date").setAttribute("value",new Date(oState.start).format("yyyy-MM-dd hh:mm:ss"));		
		$$("schedule_end_date").setAttribute("value",new Date(oState.end).format("yyyy-MM-dd hh:mm:ss"));		
		//$$("schedule_date_picker").setAttribute("value",new Date(oState.start).format("yyyy-MM-dd"));
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
	
	Date.prototype.format = function(format){ 
	  var o = { 
	    "M+" : this.getMonth()+1, //month 
	    "d+" : this.getDate(),    //day 
	    "h+" : this.getHours(),   //hour 
	    "m+" : this.getMinutes(), //minute 
	    "s+" : this.getSeconds(), //second 
	    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter 
	    "S" : this.getMilliseconds() //millisecond 
	  } 
	  if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
	    (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	  for(var k in o)if(new RegExp("("+ k +")").test(format)) 
	    format = format.replace(RegExp.$1, 
	      RegExp.$1.length==1 ? o[k] : 
	        ("00"+ o[k]).substr((""+ o[k]).length)); 
	  return format; 
	} 

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="calendar_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<input type="hidden" name="serial_no" />

<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td style="width:30%;" align="right">*<%=LocalUtilis.language("class.scheduleType",clientLocale)%> ��</td><!--�ճ�����-->
			<td >
				<!--3042�ճ�����-->
				<select name="schedule_type" style="width: 110px;">
					<%=Argument.getDictParamOptions((new Integer(3042)).intValue(),null)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.startTime",clientLocale)%> ��</td><!--��ʼʱ��-->
			<td >
				<input type="text" name="schedule_start_date" id="schedule_start_date" onclick="javascript:setday(this);" readOnly/> 		
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.endTime",clientLocale)%> ��</td><!--����ʱ��-->
			<td>
				<input type="text" name="schedule_end_date" id="schedule_end_date" onclick="javascript:setday(this);" readOnly/> 
				<!--input TYPE="button" value="��"  class=selectbtn tabindex="13" onclick="javascript:document.getElementById('schedule_time').onclick();"/-->	
			</td>
		</tr>	
		<tr>
			<td align="right"> <%=LocalUtilis.language("class.scheduleDescription",clientLocale)%> ��</td><!--�ճ�����-->
			<td colspan="3">
				<textarea rows="3" name="content" onkeydown="javascript:nextKeyPress(this)" cols="50"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<!--����-->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--ɾ��-->
    <button type="button" class="xpbutton3" accessKey=d id="btnDel" name="btnDel" onclick="javascript:DelSelfAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--����-->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>