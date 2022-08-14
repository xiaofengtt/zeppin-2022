<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer inputman = Utility.parseInt(request.getParameter("inputman"),input_operatorCode); //操作员

boolean bSuccess=false;
boolean bHadRight=false;

//查出所有角色信息

OperatorLocal olocal = EJBFactory.getOperator();

TOperatorVO vo5 = new TOperatorVO();
List list = olocal.listOpAll(vo5);
String[] allNames = new String[list.size()];
String[] allCodes = new String[list.size()];

int index = -1;
for(int i=0;i<list.size();i++){
	index++;
	Map map = (Map)list.get(i);
	allNames[index] = String.valueOf(Utility.trimNull(map.get("OP_NAME")));
	allCodes[index] = String.valueOf(Utility.trimNull(map.get("OP_CODE")));
}


//寻找该用户有哪些角色
TcustmanagersLocal local_ychmOper = EJBFactory.getTcustmanagers();
TcustmanagersVO vo1 = new TcustmanagersVO();
vo1.setSerial_no(serial_no);

List listYchmOperat = local_ychmOper.queryTmanagerZshmOper(vo1);
index = -1;
String[] operNames = new String[listYchmOperat.size()];
String[] operCodes = new String[listYchmOperat.size()];
for(int j =0;j<listYchmOperat.size();j++)
{    
    index++;
    Map map = (Map)listYchmOperat.get(j);
    operNames[index]=Utility.trimNull(map.get("OP_NAME"));
    operCodes[index]=Utility.trimNull(String.valueOf(map.get("OP_CODE")));
}

TcustmanagersVO vo = new TcustmanagersVO();
Integer input_man = new Integer(0);
if(request.getMethod().equals("POST")){
    String opValue =request.getParameter("operators");
    String[] ListValues= Utility.splitString(opValue,"$");
    if(ListValues!= null){	
    	vo.setSerial_no(serial_no);
    	local_ychmOper.deleteychmext(vo);
		for(int i=0;i<ListValues.length;i++){	 
			vo.setManagerid(Utility.parseInt(Utility.trimNull(ListValues[i]), new Integer(0)));
			local_ychmOper.addTmanagerZshmext(vo);
		}
		bSuccess=true;
     }
}

%>


<HTML>
<title>
</title>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">

<%if(bSuccess){
%>
  window.returnValue = true;
  window.close();
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

function back(theform)
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

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method ="POST" action="initmanagerychm_role.jsp" >
<input type="hidden" name="operators" value="">
<input type="hidden" name="serial_no" value=<%=serial_no%>>
<TABLE>
    <TBODY>
		<TR>
			<TD width="417" height="312">
		<DIV align="right">
		<TABLE>
			<TBODY>
				<TR>
					<TD height="37" width="511"><TABLE>
						<TBODY>
						    <tr>
					    	  <td colspan="3"><IMG src="<%=request.getContextPath()%>/images/member.gif" border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></td>
					        </tr>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD width="511" height="272">
					<CENTER>
					<TABLE>
						<TBODY>
							<TR align="right" valign="middle">
								<TD height="224" width="149">
								
								<SELECT size="16" name="availableMenu" multiple
									style="width: 125; height: 223"	onchange="javascript: refreshButtons();"
									ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
								<%
boolean flag;
boolean bOp;
boolean bRole;
if (serial_no!=null)
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
								<TD height="224" width="103">
								<CENTER>
								<TABLE>
									<TBODY>
										<TR>
											<TD width="98" align="center" valign="middle"><button class="xpbutton2" id="btnAdd" name="btnAdd" onclick="javascript:addMultiMenu();">&gt;</button></TD>
										</TR>
										<TR>
											<TD width="98" align="center" valign="middle"><button class="xpbutton2" id="btnMultiAdd" name="btnAddAll" onclick="javascript:addAllMenu();">&gt;&gt;</button></TD>
										</TR>
										<TR>
											<TD width="98" align="center" valign="middle"><button class="xpbutton2" id="btnRemove" name="btnRemove" onclick="javascript:removeMultiMenu();">&lt;</button></TD>
										</TR>
										<TR>
											<TD width="98" align="center" valign="middle"><button class="xpbutton2" id="btnMultiRemove" name="btnRemoveAll" onclick="javascript:removeAllMenu();">&lt;&lt;</button></TD>
										</TR>
									</TBODY>
								</TABLE>
								</CENTER>
								</TD>
								<TD height="224" width="149" align="left">
								<select size="16" name="currentMenu" multiple style="width:125; height: 223" onchange="javascript: refreshButtons();" ondblclick="javascript:removeMenu(document.theform.currentMenu.selectedIndex);">
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
					</CENTER>
					<TABLE align="right">
						<TBODY>
							<TR >
                         <td align="right">
                         <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave"  onclick="javascript:back(this);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;
						 <!--保存-->
						 <button class="xpbutton3" accessKey=c name="btnReturn" title="<%=LocalUtilis.language("message.close&Back",clientLocale)%> " onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						 <!--关闭返回--><!--取消-->
						 &nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>				
				</TR>
			</TBODY>
		</TABLE>
		</DIV>
		</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>