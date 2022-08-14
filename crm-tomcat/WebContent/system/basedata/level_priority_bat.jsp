<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer level_id = Utility.parseInt(request.getParameter("level_id"),new Integer(0));
String[] totalColumn = new String[0];
CustomerLocal customer = EJBFactory.getCustomer();
CustLevelVO levelvo = new CustLevelVO();
levelvo.setProduct_id(product_id);
levelvo.setLevel_id(level_id);
levelvo.setInput_man(input_operatorCode);
IPageList levelList = null;
levelList = customer.listCustLevel(levelvo,Utility.parseInt(sPage,-1),Utility.parseInt(sPagesize,-1));
sUrl = sUrl+"&product_id="+product_id+"&level_id="+level_id;
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function batchSet(){
	if(!sl_checkChoice(document.theform.op_code,"<%=LocalUtilis.language("class.operator",clientLocale)%> ")) return false;//操作员
	element = document.theform.product_id;
	if (element == null)
	{
		sl_alert("<%=LocalUtilis.language("message.permissionTypeNotSelect",clientLocale)%> ！");//未选定要授予的权限类别
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.permissionTypeNotSelect",clientLocale)%> ！");//未选定要授予的权限类别
		return false;
	}		
	if(confirm("<%=LocalUtilis.language("message.ifToGrantPermission",clientLocale)%> ？")){//确定要授予用户选中的权限吗
		document.theform.action = "level_priority_bat_action.jsp";
		document.theform.submit();
	}
}

function batchDel(){
	if(!sl_checkChoice(document.theform.op_code,"<%=LocalUtilis.language("class.operator",clientLocale)%> ")) return false;//操作员
	element = document.theform.product_id;
	if (element == null)
	{
		sl_alert("<%=LocalUtilis.language("message.permissionTypeToCancelNotSelect",clientLocale)%> ！");//未选定要取消授予的权限类别
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.permissionTypeToCancelNotSelect",clientLocale)%> ！");//未选定要取消授予的权限类别
		return false;
	}		
	if(confirm("<%=LocalUtilis.language("message.ifToCancelPermission",clientLocale)%> ？")){//确定要取消用户选中的权限吗
		document.theform.action = "level_priority_bat_del_action.jsp";
		document.theform.submit();
	}
}

function gohome(product_id){
	location = 'level_priority.jsp?product_id='+product_id;	
}
</script>

</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="rProductId" value="<%= product_id%>"/>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=4 class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
					<tr>
					<td align=right >
					<div class="btn-wrapper">
						<%=LocalUtilis.language("class.operator",clientLocale)%> ：&nbsp;&nbsp;<!--操作员-->
						<select name="op_code" style="width:150px">
							<%=Argument.getOperatorOptions(new Integer(0))%>
						</select>&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=b id="batButton" name="batButton" onclick="javascript:batchSet();"><%=LocalUtilis.language("message.grant",clientLocale)%> (<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--授权-->
						<button type="button"  class="xpbutton3" accessKey=b id="batDelButton" name="batDelButton" onclick="javascript:batchDel();"><%=LocalUtilis.language("message.cancel2",clientLocale)%> (<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--撤销-->
						<button type="button"  class="xpbutton3" accessKey=b id="backButton" name="queryButton" onclick="javascript:gohome(<%=product_id%>)"> <%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
						<!--返回-->
					</div>
					</td>				
				</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					
					<tr class="trh">
						<td align="left"><input type="checkbox" name="" class="flatcheckbox" onclick="javascript:selectAllBox(document.theform.product_id,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> </td>
						<!--产品名称-->
						<td align="center"><%=LocalUtilis.language("class.typeName",clientLocale)%> </td><!--类别名称-->
						<td align="center"><%=LocalUtilis.language("class.levelValueName2",clientLocale)%> </td><!--类别分类值名称-->
						<td align="center"><%=LocalUtilis.language("class.min",clientLocale)%> </td><!--最小值-->
						<td align="center"><%=LocalUtilis.language("class.max",clientLocale)%> </td><!--最大值-->
					</tr>
<%
int iCurrent = 0;
int iCount=0;
List rsList = levelList.getRsList();
Map rowMap = null;
for(int i=0;rsList!=null&&i<rsList.size();i++)
{ 
	rowMap = (Map)rsList.get(i);
	Integer serial_no = (Integer)rowMap.get("SERIAL_NO");
	product_id = (Integer)rowMap.get("PRODUCT_ID");
	String level_name = Utility.trimNull(rowMap.get("LEVEL_NAME"));
	Integer level_value_id = (Integer)rowMap.get("LEVEL_VALUE_ID");
	String level_value_name = Utility.trimNull(rowMap.get("LEVEL_VALUE_NAME"));
	BigDecimal min_value = (BigDecimal)rowMap.get("MIN_VALUE");
	BigDecimal max_value = (BigDecimal)rowMap.get("MAX_VALUE");

%>

			<tr class="tr<%=(iCurrent % 2)%>" >
				<td align="left"><input type="checkbox" name="product_id" class="flatcheckbox" value="<%=product_id%>$<%=level_value_id%>"><%=Argument.getProductName(product_id)%></td>
				<td class="tdh"  align="center" ><%=Utility.trimNull(level_name)%></td>
				<td align="left"><%=Utility.trimNull(level_value_name)%></td>
				<td align="center"><%=Utility.trimNull(min_value)%></td>	
				<td align="center"><%=Utility.trimNull(max_value)%></td>
			</tr>
<%
			iCurrent++;
			iCount++;
	
}

for (; iCurrent < levelList.getBlankSize(); iCurrent++){
%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>		
				<td align="center" ></td>
			</tr>
<%
}
%>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="5" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=levelList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td><!--合计--><!--项-->
				<!-- <td align="center" >-</td>
				<td align="center" >-</td>
				<td align="center" >-</td>
				<td align="center" >-</td> -->
			</tr>
		</table>

		

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
