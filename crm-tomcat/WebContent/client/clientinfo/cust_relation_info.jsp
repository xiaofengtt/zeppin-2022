<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<link href="/includes/default.css" type=text/css rel=stylesheet>
<base target="_self">
<title>添加客户关联关系</title>
</head>
<%
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO vo = new CustomerInfoVO();
String readerInfo = request.getParameter("readerInfo");
Integer family_id = Utility.parseInt(request.getParameter("family_id"), null);
String ok = Utility.trimNull(request.getParameter("ok"));
String cust_no = request.getParameter("cust_no");

String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = request.getParameter("card_id");
String family_name =request.getParameter("family_name");

String ret =request.getParameter("ret");
String ret1 ="";

boolean  bSuccess = false;
boolean ok2 = ok.equals("ok");
if(ok2)
{
	ret1= Utility.trimNull(request.getParameter("ret1"));
	String temp=null;
	String tcust_id=null;
	String tret1="";
	if(ret1.equals(""))
	{
		
		while(ret.indexOf("$") != -1)
		{
			temp = ret.substring(0, ret.indexOf("$"));	
			tcust_id = temp.substring(0, temp.indexOf(",")) ;
			vo.setCust_id(new Integer(tcust_id));
			vo.setFamily_id(family_id);
			vo.setFamily_name(family_name);
			vo.setInput_man(input_operatorCode);
			family_id = customer.appendRela(vo);

			ret = ret.substring(ret.indexOf("$") + 1);
		}
		bSuccess =true;
	}
	else{
		if(ret.equals(ret1)){bSuccess =true;}
		else{	tret1=ret1;
				vo.setFamily_id(family_id);
				vo.setFamily_name(family_name);
				vo.setInput_man(input_operatorCode);
				
				while(tret1.indexOf("$")!=-1)
				{
				temp = tret1.substring(0, tret1.indexOf("$"));	
				
				if(ret.indexOf(temp)==-1){
					tcust_id = temp.substring(0, temp.indexOf(",")) ;
			
					vo.setCust_id(new Integer(tcust_id));

					customer.deleteRela(vo);		}

					tret1 = tret1.substring(tret1.indexOf("$") + 1);
				}
				
				
				while(ret.indexOf("$") != -1)
				{
				temp = ret.substring(0, ret.indexOf("$"));	
				
				if(ret1.indexOf(temp)==-1){
				tcust_id = temp.substring(0, temp.indexOf(",")) ;		
				vo.setCust_id(new Integer(tcust_id));				
				customer.appendRela(vo);		}
				ret = ret.substring(ret.indexOf("$") + 1);
				}	
				
				
				bSuccess =true;
		}
	}
 
}
List list1 = null;
String[] readNames = null;
String[] readCodes  = null;
//寻找该家庭的成员
int index = -1;
if (family_id != null)
{
	vo.setFamily_id(family_id);
	vo.setInput_man(input_operatorCode);
	list1  = customer.listRelation1(vo);
	readNames = new String[list1.size()];
	readCodes = new String[list1.size()];
	Iterator it1 = list1.iterator();
	while (it1.hasNext())
	{
		Map map1 = (Map) it1.next();

		index++;
		readCodes[index] = String.valueOf(Utility.trimNull(map1.get("CUST_ID")));
		readNames[index] = String.valueOf(Utility.trimNull(map1.get("CUST_NAME")));
		family_name = Utility.trimNull(map1.get("FAMILY_NAME"));
		ret1 += readCodes[index]+","+ readNames[index]+"$";
		
	}

}


//寻找所有客户
CustomerInfoLocal cust_all = EJBFactory.getCustomerInfo();
CustomerInfoVO vo1 = new CustomerInfoVO();
vo1.setBook_code(new Integer(1));
vo1.setInput_man(input_operatorCode);
vo1.setCust_no(cust_no);
vo1.setCard_id(card_id);
vo1.setCust_name(cust_name);
vo1.setProduct_id(new Integer(0));

//if(request.getMethod().equals("POST")){ //2009-02-06 UPDATE YUZJ 
List list = cust_all.queryByCustNo(vo1);
Iterator it = list.iterator();


String[] allNames = new String[list.size()];
String[] allCodes = new String[list.size()];
index = -1;

while (it.hasNext())
{
	Map map = (Map) it.next();
	index++;
	allNames[index] = String.valueOf(Utility.trimNull(map.get("CUST_NAME")));
	allCodes[index] = String.valueOf(Utility.trimNull(map.get("CUST_ID")));
}

%>
<script src="/includes/default.vbs" language="vbscript"></script>
<script src="/includes/default.js" language="javascript"></script>

<script language="javascript">
<%if(bSuccess){%>
window.returnValue = 1;
window.close();
<%}%>
function back(theform)
{	
	
	var len = document.theform.currentMenu.length;

	var ret = "";
 
	for(var i = 0; i < len; i++)  
		ret+= document.theform.currentMenu.options[i].value+','+
        	document.theform.currentMenu.options[i].text+'$';
//document.theform.submit();
	//window.returnValue = ret;
	//window.close();
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
function validateForm(form)
{
	
	if(!sl_check(form.family_name, "关联名称", 100, 1))	return false;
	var len = document.theform.currentMenu.length;

	var ret = "";
 
	for(var i = 0; i < len; i++)  
		ret+= document.theform.currentMenu.options[i].value+','+
		document.theform.currentMenu.options[i].text+'$';

    document.theform.ret.value =ret;
    document.theform.ok.value="ok";
        	return true;
}
function selectNo(value)
{
	if (document.theform.cust_no.readOnly)	return;
	if (event.keyCode == 13 )
	{
		document.theform.submit();
		//location = 'family_info.jsp?cust_no=' + value+'&cust_name='+document.theform.cust_name+'&card_id'+document.theform.card_id ;
	}
}
function selectName(value)
{
	if (document.theform.cust_name.readOnly)	return;
	
	
	if (event.keyCode == 13)
	{document.theform.submit();
		//location = 'family_info.jsp?cust_name=' + value +'&cust_no='+document.theform.cust_no+'&card_id'+document.theform.card_id;
	}
}
function selectCard(value)
{
	if (document.theform.card_id.readOnly)	return;
	if (event.keyCode == 13)
	{document.theform.submit();
		//location = 'family_info.jsp?card_id=' + value +'&cust_no='+document.theform.cust_no+'&cust_name'+document.theform.cust_name;
	}
}
</script>

<body onkeydown="javascript:chachEsc(window.event.keyCode);">
<form name="theform" onsubmit="javascript: return validateForm(this);"  method="post">
<input type=hidden name="ret" value="">
<input type=hidden name="ret1" value="<%=ret1%>">
<input type=hidden name="ok" value="<%=ok%>">
<table border="0" width="100%%" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td width="16" valign="top"></td>
		<td valign="top" width="100%" align="center">

		<table cellSpacing=0 cellPadding=0 width=100% border=0>
			<tr>
				<td width=100% class="page-title"><font color="#215dc6"><b></b></font><font color="#215DC6"><b>添加客户关联关系</b></font><b><font color="#215dc6"> </font></b></td>
			</tr>
			<tr>
				<td colspan="2" width="100%">

				<table border="0" width="300" cellspacing="1" cellpadding="4">
				<tr>
				<td colspan="2" width="30%" align="right">客户编号:</td>
				<td colspan="2" width="70%"><INPUT type="text" name="cust_no" size="20" value="<%=Utility.trimNull(cust_no)%>" onkeydown="javascript:selectNo(this.value);nextKeyPress(this);"></td>
				
			</tr>
			<tr>
				<td colspan="2" width="30%" align="right">客户名称:</td>
				<td colspan="2" width="70%"><INPUT type="text" onkeydown="javascript:selectName(this.value);nextKeyPress(this);" name="cust_name" size="20" value="<%=Utility.trimNull(cust_name)%>"></td>
				
			</tr>
			<tr>
			<td colspan="2" width="30%" align="right">证件号码:</td>
				<td colspan="2" width="70%"><INPUT onkeydown="javascript:selectCard(this.value);nextKeyPress(this);" type="text" name="card_id" size="20" value="<%=Utility.trimNull(card_id)%>"></td>
			</tr>
		</table>
		(输入完按回车搜索)
		<hr>
		<table cellSpacing=0 cellPadding=0   border=0 align="center">
			<tr>
				<td width="5" align="right" valign="top"></td>
				<td width="87">客户名单<br>
				<select size="16" name="availableMenu" multiple style="width: 160; height: 223" onchange="javascript: refreshButtons();" ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
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
			if(readNames  != null && readNames.length > 0){
				for (int j = 0; j < readNames.length; j++)
				{
					if (allNames[i].equalsIgnoreCase(readNames[j]))
					{
						flag = false;
						break;
					}
	
				}
			}
			if (flag)
			{%>
				<option value=<%=allCodes[i]%>><%=allNames[i]%></option>
			<%}
		}
	}
}else
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
%>				<option value=<%=allCodes[i]%>><%=allNames[i]%></option>
				<%}
		}
	}
}
else
{
	for (int i = 0; i < allNames.length; i++)
	{
%>
				<option value=<%=allCodes[i]%>><%=allNames[i]%></option>
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
						<td width="108" valign="top">关联成员名单<br>
						<select size="16" name="currentMenu" multiple style="width: 160; height: 223" onchange="javascript: refreshButtons();" ondblclick="javascript:removeMenu(document.theform.currentMenu.selectedIndex);">
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
							<option value=<%=readCodes[i]%>><%=readNames[i]%></option>
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
							<option value=<%=op_code%>><%=op_name%></option>
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
					<tr>
				<td colspan="2" width="30%" align="right">关联名称:</td>
						<td colspan="2" width="70%"><INPUT type="text" name="family_name" size="20" value="<%=Utility.trimNull(family_name)%>"></td>
						
					</tr>
				</table>

				<table border="0" width="100%">
					<tr>
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit())document.theform.submit();">保存(<u>S</u>)</button>
						&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=c name="btnReturn" title="关闭返回" onclick="javascript:top.window.returnValue=null;window.close()">取消(<u>C</u>)</button>
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
<%
customer.remove();
cust_all.remove();
%>
