<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("menu.documentReaderInfo",clientLocale)%> </title>
<!--添加阅读人-->
</head>
<%String readerInfo = request.getParameter("readerInfo");

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);

MessageLocal documentList = EJBFactory.getMessage();


//寻找该文档的阅读者
List readList=null;
Map readMap=null;
int readListSize=0;
if (serial_no != null)
{
   readList=documentList.listReaders(serial_no);
   readListSize=readList.size();
}

int index = -1;
String[] readNames = new String[readListSize];
String[] readCodes = new String[readListSize];
for(int i=0;readList!=null && i<readListSize;i++){
    readMap=(Map)(readList.get(i));
	index++;
	readCodes[index] = String.valueOf(Utility.trimNull(readMap.get("OP_CODE")));
	readNames[index] = Utility.trimNull(readMap.get("OP_NAME"));
}

//寻找所有阅读者
OperatorLocal operator = EJBFactory.getOperator();
OperatorVO operatorVo=new OperatorVO();
List operatorList=operator.listOpAll(operatorVo);
Map operatorMap=null;
int operatorListSize=0;
if(operatorList.size()>0)
  operatorListSize=operatorList.size();
String[] allNames = new String[operatorListSize];
String[] allCodes = new String[operatorListSize];
index = -1;

for(int j=0;operatorListSize!=0 && j<operatorListSize;j++){
    operatorMap=(Map)(operatorList.get(j));
	index++;
	allNames[index] = String.valueOf(Utility.trimNull(operatorMap.get("OP_NAME")));
	allCodes[index] = String.valueOf(Utility.trimNull(operatorMap.get("OP_CODE")));
}
%>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>

<script language="javascript">
function back(theform)
{
	var len = document.theform.currentMenu.length;
	var ret = "";

	for(var i = 0; i < len; i++)
		ret+= document.theform.currentMenu.options[i].value+','+
        	document.theform.currentMenu.options[i].text+'$';

	window.returnValue = ret;
	window.close();
}

function refreshButtons()
{
	document.theform.btnAdd.disabled = (document.theform.availableMenu.selectedIndex == -1);
	document.theform.btnAddAll.disabled = (document.theform.availableMenu.length == 0);

	document.theform.btnRemove.disabled = (document.theform.currentMenu.selectedIndex == -1);
	document.theform.btnRemoveAll.disabled = (document.theform.currentMenu.length == 0);
}

function addMenu(index, noSort)
{
	if(index != -1)
	{
		var opt = document.theform.availableMenu.options[index];
		document.theform.currentMenu.add(document.createElement("OPTION"));
		document.theform.currentMenu.options[document.theform.currentMenu.length - 1].text = opt.text;
		document.theform.currentMenu.options[document.theform.currentMenu.length - 1].value = opt.value;
		document.theform.availableMenu.remove(index);
		if(index < document.theform.availableMenu.length)
			document.theform.availableMenu.selectedIndex = index;
	}
	if (!noSort)
	{
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

function addAllMenu()
{
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
</script>
<body onkeydown="javascript:chachEsc(window.event.keyCode);">

<form name="theform">
<table border="0" width="342" cellspacing="0" cellpadding="0">
	<tr>
		<td width="16" valign="top"></td>
		<td valign="top" width="322">

		<table cellSpacing=0 cellPadding=0 width=309 border=0>
			<tr>
				<td width=308><font color="#215dc6"><b><img border="0" src="<%=request.getContextPath()%>/images/25.gif" width="32" height="32"></b></font><font color="#215DC6"><b>
				    <%=LocalUtilis.language("menu.documentReaderInfo",clientLocale)%> </b></font><b><font color="#215dc6"> </font></b>
				</td><!--添加阅读人-->
				<td width=35 valign="bottom"></td>
			</tr>
			<tr>
				<td colspan="2" width="345">

				<table border="0" width="300" cellspacing="1" cellpadding="4">
					<tr>
						<td width="5" align="right" valign="top"></td>
						<td width="87"><%=LocalUtilis.language("class.nameNoReadRight",clientLocale)%> <br><!--无阅读权名单-->
						<select size="16" name="availableMenu" multiple style="width: 85; height: 223; overflow-x:hidden;" onchange="javascript: refreshButtons();" ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
							<%String temp = "";
String op_code = "";
String op_name = "";
boolean flag;
String saveInfo = readerInfo;
if (readerInfo == null || readerInfo.length() == 0)
{
	if (allNames != null && allNames.length != 0)
	{
		for (int i = 0; i < allNames.length; i++)
		{
			flag = true;
			for (int j = 0; j < readNames.length; j++)
			{
				if (allNames[i].equalsIgnoreCase(readNames[j]))
				{
					flag = false;
					break;
				}

			}
			if (flag)
			{
%>
							<option value=<%=allCodes[i]%> title=<%=allNames[i]%>><%=allNames[i]%></option>
							<%}
}
}
}
else
{
	if (!readerInfo.equals(""))
	{
		if (allNames != null && allNames.length != 0)
		{
			for (int i = 0; i < allNames.length; i++)
			{
				flag = true;
				readerInfo = saveInfo;
				while (readerInfo.indexOf("$") != -1)
				{
					temp = readerInfo.substring(0, readerInfo.indexOf("$"));
					op_code = temp.substring(0, temp.indexOf(","));
					op_name = temp.substring(temp.indexOf(",") + 1, temp.length());

					if (op_code.trim().equalsIgnoreCase(allCodes[i].trim()))
						flag = false;

					if (readerInfo.indexOf("$") != readerInfo.length() - 1)
						readerInfo = readerInfo.substring(readerInfo.indexOf("$") + 1, readerInfo.length());
					else
						break;
				}
				if (flag)
				{
%>
							<option value=<%=allCodes[i]%> title=<%=allCodes[i]%>><%=allNames[i]%></option>
							<%}
}
}
}
else
{
	for (int i = 0; i < allNames.length; i++)
	{
%>
							<option value=<%=allCodes[i]%> title=<%=allCodes[i]%>><%=allNames[i]%></option>
							<%}
}
}

readerInfo = saveInfo;
%>
						</select></td>
						<td width="50" align="center" valign="middle">
						<p align="center">
						<button type="button"  class="xpbutton2" id="btnAdd" name="btnAdd" onclick="javascript:addMultiMenu();">&gt;</button>
						</p>
						<p align="center">
						<button type="button"  class="xpbutton2" id="btnMultiAdd" name="btnAddAll" onclick="javascript:addAllMenu();">&gt;&gt;</button>
						</p>
						<p align="center">
						<button type="button"  class="xpbutton2" id="btnRemove" name="btnRemove" onclick="javascript:removeMultiMenu();">&lt;</button>
						</p>
						<p align="center">
						<button type="button"  class="xpbutton2" id="btnMultiRemove" name="btnRemoveAll" onclick="javascript:removeAllMenu();">&lt;&lt;</button>
						</p>
						</td>
						<td width="108" valign="top"><%=LocalUtilis.language("class.nameWithReadRight",clientLocale)%> <br><!--有阅读权名单-->
						<select size="16" name="currentMenu" multiple style="width: 85; height: 223" onchange="javascript: refreshButtons();" ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
							<%temp = "";
op_code = "";
op_name = "";
if (readerInfo == null || readerInfo.length() == 0)
{
	if (readNames != null && readNames.length != 0)
	{
		for (int i = 0; i < readNames.length; i++)
		{
%>
							<option value=<%=readCodes[i]%> title=<%=readNames[i]%>><%=readNames[i]%></option>
							<%}
}
}
else
{
	if (!readerInfo.equals(""))
	{
		while (readerInfo.indexOf("$") != -1)
		{
			temp = readerInfo.substring(0, readerInfo.indexOf("$"));
			op_code = temp.substring(0, temp.indexOf(","));
			op_name = temp.substring(temp.indexOf(",") + 1, temp.length());
%>
							<option value=<%=op_code%> title=<%=op_name%>><%=op_name%></option>
							<%if (readerInfo.indexOf("$") != readerInfo.length() - 1)
	readerInfo = readerInfo.substring(readerInfo.indexOf("$") + 1, readerInfo.length());
else
	break;
}
}
}
%>
						</select></td>
					</tr>
				</table>

				<table border="0" width="100%">
					<tr>
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:back(this);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;<!--保存-->
						<button type="button"  class="xpbutton3" accessKey=c name="btnReturn" title="<%=LocalUtilis.language("message.close&Back",clientLocale)%> " onclick="javascript:top.window.returnValue=null;window.close()">
						   <%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)
						</button><!--关闭返回--><!--取消-->
						&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

</form>
</body>
</html>
<%documentList.remove();
operator.remove();
%>
