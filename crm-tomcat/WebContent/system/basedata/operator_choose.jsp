<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String operatorCodes = Utility.trimNull(request.getParameter("operatorCodes"));

//员工信息表
OperatorLocal operator = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
vo.setStatus(new Integer(1));

List operator_list = operator.listOpAll(vo);
Map map = null;

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.operatorEmailChoose",clientLocale)%> </title>
<!--选择员工-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
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
<form name="theform" method ="POST" >
<input type="hidden" name="operatorCodes" id="operatorCodes"  value="<%=operatorCodes%>" />
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.operatorChoose",clientLocale)%> </b></font>
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
									<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnAdd" name="btnAdd" onclick="javascript:addMultiMenu();">&gt;</button></TD>
								</TR>
								<TR>
									<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnMultiAdd" name="btnAddAll" onclick="javascript:addAllMenu();">&gt;&gt;</button></TD>
								</TR>
								<TR>
									<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnRemove" name="btnRemove" onclick="javascript:removeMultiMenu();">&lt;</button></TD>
								</TR>
								<TR>
									<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnMultiRemove" name="btnRemoveAll" onclick="javascript:removeAllMenu();">&lt;&lt;</button></TD>
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
<div style="margin-left:44%" align="center">
         <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"  onclick="javascript:back(this);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;
		 <!--保存-->
		 <button type="button"  class="xpbutton3" accessKey=c name="btnReturn" title="<%=LocalUtilis.language("message.close&Back",clientLocale)%> " onclick="javascript:disableAllBtn(true);window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button> &nbsp;
		 <!--关闭返回--><!--关闭-->
</div>
<% operator.remove(); %>
</form>
</BODY>
</HTML>