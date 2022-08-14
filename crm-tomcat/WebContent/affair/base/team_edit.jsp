<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TmanagerteamsVO vo = new TmanagerteamsVO();
TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();

Integer team_id = null;
String team_no = null;
String team_name = null;
Integer create_date = null;
Integer leader = null;
String description = null;
Integer input_man = null;
Integer mark_flag = Utility.parseInt(Utility.trimNull(request.getParameter("mark_flag")),new Integer(2));

//保存信息
if(request.getMethod().equals("POST")){
	team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),null);
	team_no = Utility.trimNull(request.getParameter("team_no"));
	team_name = Utility.trimNull(request.getParameter("team_name"));
	create_date = Utility.parseInt(Utility.trimNull(request.getParameter("create_date")),null);
	leader = Utility.parseInt(Utility.trimNull(request.getParameter("leader")),null);
	description = Utility.trimNull(request.getParameter("description"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);
	
	vo.setTeam_id(team_id);
	vo.setTeam_no(team_no);
	vo.setTeam_name(team_name);
	vo.setCreate_date(create_date);
	vo.setLeader(leader);
	vo.setDescription(description);
	vo.setInput_man(input_man);
	vo.setMark_flag(mark_flag);
	
	try{
		tmanagerteams_Bean.modi(vo);
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
        //团队编号
		if(!sl_checkNum(document.getElementsByName("team_no")[0], "<%=LocalUtilis.language("class.teamID",clientLocale)%> ", 7, 1)) return false;
		//团队名称
		if(!sl_check(document.getElementsByName("team_name")[0], "<%=LocalUtilis.language("class.teamName",clientLocale)%> ", 128, 1)) return false;
		//开始日期
		if((document.getElementById("create_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.create_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){
			return false;
		}else{
			syncDatePicker(document.theform.create_date_picker,document.theform.create_date);			
		}
		if(document.getElementsByName("leader")[0].getAttribute("value") == ""){
			alert("<%=LocalUtilis.language("message.selectLeader",clientLocale)%> ！"); return false;//请选择负责人名称
		}
		if(document.theform.mark_flag.checked)
			document.theform.mark_flag.value = 1;
		else
			document.theform.mark_flag.value = 2;
		if(!sl_check(document.getElementsByName("description")[0], "<%=LocalUtilis.language("class.description",clientLocale)%> ", 512, 0)) return false;//备注
		
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
			var _team_no = document.getElementsByName("team_no")[0];
			var _team_name = document.getElementsByName("team_name")[0];
			var _create_date = document.getElementsByName("create_date")[0];
			var _leader = document.getElementsByName("leader")[0];
			var _description = document.getElementsByName("description")[0];
			var _parent_name = document.getElementsByName("parent_name")[0];
			selectIndex(_leader,oState.data.leader);
			_team_id.setAttribute("value",oState.data.team_id);
			_team_no.setAttribute("value",oState.data.team_no);
			_team_name.setAttribute("value",oState.data.team_name);
			_create_date.setAttribute("value",oState.data.create_date);
			document.getElementsByName("create_date_picker")[0].setAttribute("value",exchangeDateStyle(oState.data.create_date));
			_description.setAttribute("value",oState.data.description);
			_parent_name.setAttribute("value",oState.data.parent_name);
			
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
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/team_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<input type="hidden" name="team_id" value="<%=team_id%>" />
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">
		<tr>
			<td style="width:120px;" align="right">*<%=LocalUtilis.language("class.teamID",clientLocale)%>:</td><!-- 团队编号 -->
			<td ><input type="text" style="width: 110px;" name="team_no" class="edline" readonly="readonly"></td>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.teamName",clientLocale)%>:</td><!-- 团队名称 -->
			<td ><input type="text" style="width: 120px;" name="team_name"></td>
		</tr>	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.leaderName",clientLocale)%>:</td><!-- 负责人名称 -->
			<td>
				<select name="leader" style="width: 110px;">
					<%=Argument.getManager_Value(null)%>
				</select>
			</td>
			
			
			
			<td align="right" style="width:90px;">*<%=LocalUtilis.language("class.createDate",clientLocale)%>:</td><!-- 成立日期 -->
			<td >
				<input type="text" name="create_date_picker" class=selecttext style="width: 99px;" <%if(create_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(create_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.create_date_picker,theform.create_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="create_date" id="create_date" />			
			</td>
		</tr>
		<tr>
			<td align="right">是否为营销团队:</td>
			<td>
				<input type="checkbox" name="mark_flag" value="2" class="flatcheckbox" <%=mark_flag.intValue() == 1 ? "checked" : "" %>>
			</td>
			<td align="right" >*父团队名称:</td>
			<td>
				<input type="text" name="parent_name" class="edline" readonly="readonly">
			</td>
			
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!-- 备注 -->
			<td colspan="3">
				<textarea rows="3" name="description" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
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