<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.contractManage.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TcoTeamInfoVO tcoTeamInfoVO = new TcoTeamInfoVO();
TcoTeamInfoLocal tcoTeamInfoLocal = EJBFactory.getTcoTeamInfo();

String team_name = null;
String team_summary = null;
Integer team_admin = null;
Integer input_man = null;
Integer team_id = null;
/*--------------添加成员涉及到的变量begin-----------*/
String operatorCodes = Utility.trimNull(request.getParameter("operatorCodes"));

//员工信息表
OperatorLocal operator = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
vo.setStatus(new Integer(1));

List operator_list = operator.listOpAll(vo);
Map map = null;
/*--------------添加成员涉及到的变量end-----------*/
//保存信息
if(request.getMethod().equals("POST")){
	team_name = Utility.trimNull(request.getParameter("team_name"));
	team_summary = Utility.trimNull(request.getParameter("team_summary"));
	team_admin = Utility.parseInt(Utility.trimNull(request.getParameter("team_admin")),null);
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	tcoTeamInfoVO.setTeam_name(team_name);
	tcoTeamInfoVO.setTeam_summary(team_summary);
	tcoTeamInfoVO.setTeam_admin(team_admin);
	tcoTeamInfoVO.setInput_man(input_man);
	
	team_id= tcoTeamInfoLocal.append(tcoTeamInfoVO);	
	
	//添加成员
	operatorCodes=operatorCodes.split("&")[0];
	String[] codes=operatorCodes.split(",");
	TcoTeamMemberVO tcoTeamMemberVO = new TcoTeamMemberVO();
	TcoTeamMemberLocal tcoTeamMemberLocal = EJBFactory.getTcoTeamMember();
	for(int i=0;i<codes.length;i++){
		tcoTeamMemberVO.setTeam_id(team_id);
		tcoTeamMemberVO.setTeam_member(Utility.parseInt(Utility.trimNull(codes[i]),null));
		tcoTeamMemberVO.setInput_man(input_man);
		tcoTeamMemberLocal.append(tcoTeamMemberVO);
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
<title>新增项目组</title><!-- 新增项目组-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

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
		//保存选中的成员 begin
		var len = document.theform.currentMenu.length;
		var ret = "";
		var retValue = "";
		var retName = "";
		for(var i = 0; i < len; i++) {
			retValue+= document.theform.currentMenu.options[i].value+',';
			retName += document.theform.currentMenu.options[i].innerText+',';
		}
		retValue=retValue.substring(0,retValue.length-1);
		retName=retName.substring(0,retName.length-1);
		ret = retValue+"&"+retName;
		
	    document.theform.operatorCodes.value=ret;
	    //保存选中的成员 end
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}
	
	//以下是添加成员的方法
function refreshButtons(){
	document.theform.btnAdd.disabled = (document.theform.availableMenu.selectedIndex == -1);
	document.theform.btnAddAll.disabled = (document.theform.availableMenu.length == 0);

	document.theform.btnRemove.disabled = (document.theform.currentMenu.selectedIndex == -1);
	document.theform.btnRemoveAll.disabled = (document.theform.currentMenu.length == 0);
}

function back(theform){
    disableAllBtn(true);
	var len = document.theform.currentMenu.length;
	var ret = "";
	var retValue = "";
	var retName = "";
	for(var i = 0; i < len; i++) {
		retValue+= document.theform.currentMenu.options[i].value+'$';
		retName += document.theform.currentMenu.options[i].innerText+',';
	}
	retValue=retValue.substring(0,retValue.length-1);
	retName=retName.substring(0,retName.length-1);
	ret = retValue+"&"+retName;
	
    document.theform.operatorCodes.value=ret;
	
	window.returnValue=ret;
	window.close();
}

function addMenu(index, noSort){
	if(index != -1){
		var opt = document.theform.availableMenu.options[index];
		document.theform.currentMenu.add(document.createElement("OPTION"));
		document.theform.currentMenu.options[document.theform.currentMenu.length - 1].text = opt.text;
		document.theform.currentMenu.options[document.theform.currentMenu.length - 1].value = opt.value;
		document.theform.availableMenu.remove(index);
		
		if(index < document.theform.availableMenu.length)
			document.theform.availableMenu.selectedIndex = index;
	}
	
	if (!noSort){
		sortOptions(document.theform.currentMenu);
		refreshButtons();
	}
}

function addMultiMenu()
{
	var items = new Array();
	var index = 0;
	for(var i = 0; i < document.theform.availableMenu.length; i++)
		if(document.theform.availableMenu.options[i].selected)
			items[index++] = i;
	for(i = items.length - 1; i >= 0; i--)
		addMenu(items[i]);
}

function addAllMenu(){
	for(var i = document.theform.availableMenu.length - 1; i >= 0; i--)
		addMenu(i, true);
	sortOptions(document.theform.currentMenu);
	refreshButtons();
}

function removeMenu(index, noSort)
{
	if(index != -1)
	{
		var opt = document.theform.currentMenu.options[index];
		document.theform.availableMenu.add(document.createElement("OPTION"));
		document.theform.availableMenu.options[document.theform.availableMenu.length - 1].text = opt.text;
		document.theform.availableMenu.options[document.theform.availableMenu.length - 1].value = opt.value;
		document.theform.currentMenu.remove(index);
		if(index < document.theform.currentMenu.length)
			document.theform.currentMenu.selectedIndex = index;
	}
	if (!noSort)
	{
		sortOptions(document.theform.availableMenu);
		refreshButtons();
	}
}

function removeMultiMenu()
{
	var items = new Array();
	var index = 0;
	for(var i = 0; i < document.theform.currentMenu.length; i++)
		if(document.theform.currentMenu.options[i].selected)
			items[index++] = i;
	for(i = items.length - 1; i >= 0; i--)
		removeMenu(items[i]);
}

function removeAllMenu()
{
	for(var i = document.theform.currentMenu.length - 1; i >= 0; i--)
		removeMenu(i, true);
	sortOptions(document.theform.availableMenu);
	refreshButtons();
}

window.onload=function(){
	var operatorCodes = document.getElementById("operatorCodes").value;
	var operators = operatorCodes.split("$");
	var items = new Array();
	var index = 0;
	var v_operaorCode;

	if(operators.length>0){		
		for(var i = 0;i<operators.length;i++){	
			v_operaorCode = operators[i];
			
			for(var j = 0; j < document.theform.availableMenu.length; j++){
					if(document.theform.availableMenu.options[j].value == v_operaorCode){
						addMenu(j);
					}
			}
		}
	}
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="tcoTeamInfo_new.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>

<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td align="right">*项目组名称:</td><!-- 项目组名称 -->
			<td ><input type="text" style="width: 120px;" name="team_name"></td>
		</tr>	
		<tr>
			<td  align="right">*项目组长:</td><!-- 项目组长  -->
			<td>
				<select name="team_admin" style="width: 120px;">
					<%=Argument.getManager_Value(null)%>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right">项目组说明:</td><!-- 项目组说明 -->
			<td>
				<textarea rows="3" name="team_summary" onkeydown="javascript:nextKeyPress(this)" cols="30"></textarea>			
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="hidden" name="operatorCodes" id="operatorCodes"  value="<%=operatorCodes%>" />
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
					<font color="#215dc6"><b>选择项目组成员</b></font>
					<hr noshade color="#808080" size="1">
				</div><!--员工选择-->
				
				<div align="center">
				<TABLE>
					<TBODY>
						<TR align="right" valign="middle">
							<TD height="224" width="149">
								<SELECT size="16" name="availableMenu" multiple
									style="width: 125; height: 223"	onchange="javascript: refreshButtons();"
									ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
				<%
				//声明字段
				Iterator iterator = operator_list.iterator();
				int iCount = 0;
				String op_code;
				String op_name;
				
				while(iterator.hasNext()){
					iCount++;
					map = (Map)iterator.next();
					
					op_code = Utility.trimNull(map.get("OP_CODE"));
					op_name= Utility.trimNull(map.get("OP_NAME"));	
				%>
				 	<option  value=<%=op_code%>><%=op_name%></option>
				<%
				}
				%>
								 </SELECT>
							 </TD>
					
							<TD height="224" width="103">
								<CENTER>
										<TABLE>
											<TBODY>
												<TR>
													<TD width="98" align="center" valign="middle"><button type="button" class="xpbutton2" id="btnAdd" name="btnAdd" onclick="javascript:addMultiMenu();">&gt;</button></TD>
												</TR>
												<TR>
													<TD width="98" align="center" valign="middle"><button type="button" class="xpbutton2" id="btnMultiAdd" name="btnAddAll" onclick="javascript:addAllMenu();">&gt;&gt;</button></TD>
												</TR>
												<TR>
													<TD width="98" align="center" valign="middle"><button type="button" class="xpbutton2" id="btnRemove" name="btnRemove" onclick="javascript:removeMultiMenu();">&lt;</button></TD>
												</TR>
												<TR>
													<TD width="98" align="center" valign="middle"><button type="button" class="xpbutton2" id="btnMultiRemove" name="btnRemoveAll" onclick="javascript:removeAllMenu();">&lt;&lt;</button></TD>
												</TR>
											</TBODY>
										</TABLE>
									</CENTER>
								</TD>
								<TD height="224" width="149" align="left">
											<select size="16" name="currentMenu" multiple style="width:125; height: 223" onchange="javascript: refreshButtons();" ondblclick="javascript:removeMenu(document.theform.currentMenu.selectedIndex);">				
											</select>
								</TD>
								</TR>
						</TBODY>
					</TABLE>
				</div>
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