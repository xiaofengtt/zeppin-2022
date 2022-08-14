<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
//获得对象
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal local = EJBFactory.getTcustmanagers();

Integer group_id = null;
String cno_number = null;
String summary = null;
Integer input_man = null;

//保存信息
if(request.getMethod().equals("POST")){
	
	group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
	cno_number = Utility.trimNull(request.getParameter("cno_number"));
	summary = Utility.trimNull(request.getParameter("summary"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setGroup_id(group_id);
	vo.setCno_number(cno_number);
	vo.setSummary(summary);
	vo.setInput_man(input_man);
	try{
		local.addManagerYchm(vo);
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
	
	/*验证数据*/
	function validateForm(form){
		if(!sl_checkChoice(document.getElementById("group_id"),"群组名称")){
			return false;
		}
		if(!sl_check(document.getElementById("cno_number"),"专属号码",60,1)){
			return false;
		}
		return sl_check_update();	
	}
	
	function ModiAction(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/service/service_define_template.jsp";
		oState.service_value = 0;
		oState.flag = "new";
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:370px;status:0;help:0')){
			document.getElementsByName("provideservices")[0].setAttribute("value",oState.service_value);
		}	
	}

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

</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/initmanagerychm_new.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>

<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td style="width:90px;" align="right">*群组名称：</td>
			<td>
				<select id="group_id" name="group_id" style="width:122px;">
					<%=Argument.getDictParamOptions(3062, null)%>
				</select>
			</td>
			<td style="width:120px;" align="right">*专属号码：</td>
			<td><input type="text" id="cno_number" name="cno_number"></td>
		</tr>	
		<tr>
			<td align="right">描述：</td>
			<td colspan="3">
				<textarea rows="3" cols="30" id="summary" name="summary"></textarea>		
			</td>
		</tr>
	</table>
</div>

<br>

<div align="right">
    <!-- 保存 -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 关闭 -->
	<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>&nbsp;&nbsp;
</div>
</form>

</BODY>
</HTML>