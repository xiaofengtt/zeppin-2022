<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.contractManage.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TcoTeamInfoVO vo = new TcoTeamInfoVO();
TcoTeamInfoLocal tcoTeamMemberLocal = EJBFactory.getTcoTeamInfo();

Integer team_id  = null;
String team_name = null;
String team_summary = null;
Integer team_admin = null;
String team_admin_name = null;
Integer input_man = null;

//保存信息
if(request.getMethod().equals("POST")){
	team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),null);
	team_name = Utility.trimNull(request.getParameter("team_name"));
	team_summary = Utility.trimNull(request.getParameter("team_summary"));
	team_admin = Utility.parseInt(Utility.trimNull(request.getParameter("team_admin")),null);
	team_admin_name=Utility.trimNull(request.getParameter("team_admin_name"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);
	
	vo.setTeam_id(team_id);
	vo.setTeam_name(team_name);
	vo.setTeam_summary(team_summary);
	vo.setTeam_admin(team_admin);
	vo.setTeam_admin_name(team_admin_name);
	vo.setInput_man(input_man);
	
	try{
		tcoTeamMemberLocal.modi(vo);
	}catch(Exception e){
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
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
<title><%=LocalUtilis.language("index.menu.addTeam",clientLocale)%> </title><!-- 新增团队-->
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
		window.returnValue = true;
		window.close();
	<%}%>

	/*验证数据*/
	function validateForm(form){		
		return sl_check_update();	
	}

	/*保存*/
	function SaveAction(){
        if(!sl_check(document.getElementsByName("team_name")[0], "项目组名称", 128, 1)) return false;// 项目组名称
		
		if(document.getElementsByName("team_admin")[0].getAttribute("value") == ""){
            //请选择项目组长
			alert("请选择项目组长 ！"); return false;
		}
        //项目组说明
		if(!sl_check(document.getElementsByName("team_summary")[0], "项目组说明", 512, 0)) return false;
		
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
			var _team_id = document.getElementsByName("team_id")[0];
			var _team_name = document.getElementsByName("team_name")[0];
			var _team_summary = document.getElementsByName("team_summary")[0];
			var _team_admin = document.getElementsByName("team_admin")[0];
			var _team_admin_name = document.getElementsByName("team_admin_name")[0];
			selectIndex(_team_admin,oState.data.team_admin);
			_team_id.setAttribute("value",oState.data.team_id);
			_team_name.setAttribute("value",oState.data.team_name);
			_team_summary.setAttribute("value",oState.data.team_summary);
			_team_admin.setAttribute("value",oState.data.team_admin);
			_team_admin_name.setAttribute("value",oState.data.team_admin_name);
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
<form name="theform" method="post" action="tcoTeamInfo_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<input type="hidden" name="team_id" value="<%=team_id%>" />
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td style="width:90px;" align="right">*项目组名称:</td><!-- 项目组名称 -->
			<td ><input type="text" style="width: 120px;" name="team_name"></td>
		</tr>	
		<tr>
			<td  align="right">*项目组长:</td><!-- 项目组长  -->
			<td>
				<select name="team_admin" style="width: 120px;">
					<%=Argument.getManager_Value(team_admin)%>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right">项目组说明:</td><!-- 项目组说明 -->
			<td>
				<textarea rows="3" name="team_summary" onkeydown="javascript:nextKeyPress(this)" cols="30"><%=team_summary%></textarea>			
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
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>