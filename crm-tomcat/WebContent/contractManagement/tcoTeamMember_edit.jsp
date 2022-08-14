<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.contractManage.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TcoTeamMemberVO vo = new TcoTeamMemberVO();
TcoTeamMemberLocal tcoTeamMemberLocal = EJBFactory.getTcoTeamMember();

Integer serial_no = null;
Integer team_id = null;
Integer team_member = null;
String team_position = null;
Integer input_man = null;

//保存信息
if(request.getMethod().equals("POST")){
	serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),null);
	team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),null);
	team_member = Utility.parseInt(Utility.trimNull(request.getParameter("team_member")),null);
	team_position = Utility.trimNull(request.getParameter("team_position"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setSerial_no(serial_no);
	vo.setTeam_id(team_id);
	vo.setTeam_member(team_member);
	vo.setTeam_position(team_position);
	vo.setInput_man(input_man);
	try{
		tcoTeamMemberLocal.modi(vo);
	}catch(Exception e){       
       	String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale);//保存失败
		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\");window.returnValue=null;window.close();</script>");
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
<title><%=LocalUtilis.language("menu.modifyTeamMember",clientLocale)%> </title><!-- 修改团队成员 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
	var oState = {};
	
	oState = window.dialogArguments||{};

	<% if(bSuccess){ %>
		oState.newlist_flag = 1;
		window.returnValue = true;
		window.close();
	<%}%>

	/*验证数据*/
	function validateForm(form){
        //请选择项目组成员
		if(theform.team_member.value == ""){alert("请选择项目组成员");return false;}				
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
		window.returnValue=null;
		window.close();
	}
	
	window.onload = function (){
		try{
			var _team_position = document.getElementsByName("team_position")[0];
			_team_position.setAttribute("value",oState.data.team_position);
		}catch(e){

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
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="tcoTeamMember_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<input type="hidden" name="serial_no" value="<%=Utility.trimNull(request.getParameter("serial_no"))%>" />
<input type="hidden" name="team_id" value="<%=Utility.trimNull(request.getParameter("team_id"))%>" />
<input type="hidden" name="team_member" value="<%=Utility.trimNull(request.getParameter("team_member"))%>" />

<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">

		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.memberName",clientLocale)%> ：</td><!-- 成员名称 -->
			<td>
				<select style="width: 120px;" disabled="disabled">
					<%=Argument.getManager_Value(Utility.parseInt(Utility.trimNull(request.getParameter("team_member")),null))%>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right">职务：</td><!-- 职务-->
			<td colspan="3">
				<textarea rows="3" name="team_position" onkeydown="javascript:nextKeyPress(this)" cols="30"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
    <!-- 保存 -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 返回 -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>