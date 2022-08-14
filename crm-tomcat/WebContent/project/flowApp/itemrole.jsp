<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,enfo.crm.project.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer item_id =Utility.parseInt(request.getParameter("item_id"),new Integer(0));
Integer inputman = Utility.parseInt(request.getParameter("inputman"),input_operatorCode); //操作员

boolean bSuccess=false;
boolean bHadRight=false;

//查出所有客户经理信息
TcustmanagersLocal manager = EJBFactory.getTcustmanagers();
TcustmanagersVO vo5 = new TcustmanagersVO();
List list = manager.list_query(vo5);
String[] allNames = new String[list.size()];
String[] allCodes = new String[list.size()];

int index = -1;
for(int i=0;i<list.size();i++){
	index++;
	Map map = (Map)list.get(i);
	allNames[index] = String.valueOf(Utility.trimNull(map.get("ManagerName")));
	allCodes[index] = String.valueOf(Utility.trimNull(map.get("ManagerID")));
}


//寻找该项目中有哪些操作员有权限
BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
List listOperat = businessLogicLocal.listDepartItemQuery(request.getParameter("item_id"));
index = -1;
String[] operNames = new String[listOperat.size()];
String[] operCodes = new String[listOperat.size()];
for(int j =0;j<listOperat.size();j++)
{    
    index++;
    Map map = (Map)listOperat.get(j);
    operNames[index]=Utility.trimNull(map.get("ManagerName"));
    operCodes[index]=Utility.trimNull(String.valueOf(map.get("ManagerID")));
}

//保存操作员权限
TcustmanagersLocal manager2 = EJBFactory.getTcustmanagers();
TcustmanagersVO vo2 = new TcustmanagersVO();
if(request.getMethod().equals("POST")){
	String opValue =request.getParameter("operators");
    String[] ListValues= Utility.splitString(opValue,"$");
    if(ListValues!= null)
    {	
		vo2.setItem_id(item_id);
        vo2.setFlag(new Integer(2)); 
		vo2.setInput_man(input_operatorCode);
		manager2.addItemManagerId(vo2);//删除权限
        
		for(int i=0;i<ListValues.length;i++)		
		{
			vo2.setItem_id(item_id);
			vo2.setManagerid(Utility.parseInt(ListValues[i],new Integer(0)));
			vo2.setFlag(new Integer(1));
	    	manager2.addItemManagerId(vo2);//增加权限
		}
		bSuccess=true;
     }
 }

%>


<HTML>
<title>
</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">

<%if(bSuccess){
%>
  success()

<%
  } 
%>

function refreshButtons()
{
	document.theform.btnAdd.disabled = (document.theform.availableMenu.selectedIndex == -1);
	document.theform.btnAddAll.disabled = (document.theform.availableMenu.length == 0);

	document.theform.btnRemove.disabled = (document.theform.currentMenu.selectedIndex == -1);
	document.theform.btnRemoveAll.disabled = (document.theform.currentMenu.length == 0);
}

function success(){
	sl_alert('保存成功');
   location="/project/flowApp/depart_item_010.jsp";
}


function save(theform)
{
    disableAllBtn(true);
	var len = document.theform.currentMenu.length;
	var ret = "";
	for(var i = 0; i < len; i++)  
	ret+= document.theform.currentMenu.options[i].value+'$';
	ret=ret.substring(0,ret.length-1);
    document.theform.operators.value=ret;
	document.theform.submit();
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

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method ="POST" action="itemrole.jsp" >
<input type="hidden" name="item_id" value="<%=item_id %>">
<input type="hidden" name="operators" value="">
<TABLE style="width: 100%" >
			<tr>
					    	  <td class="page-title"><font color="#215dc6"><b><%=menu_info%></b></td>
					        </tr>
					        <tr><td><br/></td></tr>
    <TBODY align="center" class="product-list">
		<TR>
			<TD >
		<DIV>
		<br/>
		<TABLE >
			<TBODY>
			
				<TR>
					<TD >
					
					<TABLE class="product-list">
						<TBODY>
							<TR  >
								<TD style="width: 100%">
								
								<SELECT size="20" style="width: 200" name="availableMenu" multiple
										onchange="javascript: refreshButtons();"
									ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
								<%
boolean flag;
boolean bOp;
boolean bRole;
if (item_id!=null)
{
	if (allCodes != null && allCodes.length != 0)
	{
		for (int i = 0; i < allCodes.length; i++)
		{
			
			flag=true;
			
			for (int j = 0; j < operNames.length; j++)
			{
				if (allNames[i].equalsIgnoreCase(operNames[j]))
				{   
					flag = false;
					break;
				}
		
			}
			
			if(flag)
			{			   
%>               <option  value=<%=allCodes[i]%>><%=allNames[i]%></option>
<%				  			
			}				
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
%>						  
                          </SELECT></td>
								<TD >
								<TABLE>
									<TBODY>
										<TR>
											<TD  align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnAdd" name="btnAdd" onclick="javascript:addMultiMenu();">&gt;</button></TD>
										</TR>
										<TR>
											<TD align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnMultiAdd" name="btnAddAll" onclick="javascript:addAllMenu();">&gt;&gt;</button></TD>
										</TR>
										<TR>
											<TD  align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnRemove" name="btnRemove" onclick="javascript:removeMultiMenu();">&lt;</button></TD>
										</TR>
										<TR>
											<TD  align="center" valign="middle"><button  type="button" class="xpbutton2" id="btnMultiRemove" name="btnRemoveAll" onclick="javascript:removeAllMenu();">&lt;&lt;</button></TD>
										</TR>
									</TBODY>
								</TABLE>
								</TD>
								<TD  align="left">
								<select size="20"   name="currentMenu" multiple style="width:200" onchange="javascript: refreshButtons();" ondblclick="javascript:removeMenu(document.theform.currentMenu.selectedIndex);">
							<%
if (operNames != null && operNames.length != 0)
{
		for (int i = 0; i < operNames.length; i++)
		{
%>
							<option value=<%=operCodes[i]%>><%=operNames[i]%></option>
							<%}
}
%>
						</select>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
					
					</TD>				
				</TR>
			</TBODY>
		</TABLE>
		<br/>

		</DIV>
		</TD>
		</TR>
	</TBODY>
			<TABLE align="right">
						<TBODY>
							<TR >
                         <td align="right">
                         <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave"  onclick="javascript:save(this);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;
						 <!--保存-->
						 <button  type="button" class="xpbutton3" accessKey=c name="btnReturn" title="<%=LocalUtilis.language("message.close&Back",clientLocale)%> " onclick="javascript:disableAllBtn(true);history.back();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						 <!--关闭返回--><!--取消-->
						 &nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
  manager.remove();
  manager2.remove();
  
%>



