<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
Integer level_id = Utility.parseInt(request.getParameter("level_id"),null);
String[] totalColumn = new String[0];
ProductLocal product = EJBFactory.getProduct();
CustomerLocal customer = EJBFactory.getCustomer();
ProductVO vo = new ProductVO();
CustLevelVO levelvo = new CustLevelVO();
vo.setProduct_id(product_id);
List productList = product.load(vo);
Map pMap = (Map)productList.get(0);
levelvo.setProduct_id(product_id);
levelvo.setLevel_id(level_id);
IPageList levelList = customer.listCustLevel(levelvo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
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
function refreshPage()
{
	location="contract_level_setting.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&product_id="+document.theform.product_id.value
					+"&level_id="+document.theform.level_id.value
}

function StartQuery()
{
	refreshPage();
}

function level_setting(product_id,level_value_id){
	location = 'level_setting_list.jsp?product_id='+product_id+'&level_value_id='+level_value_id;
}

function newLevel(product_id){
	if(showModalDialog('contract_level_add.jsp?product_id='+product_id, '','dialogWidth:550px;dialogHeight:180px;status:0;help:0')){
		location.reload();	
	}
}

function delLevel(serial_no,product_id){
	location=	'contract_level_del.jsp?serial_no='+serial_no+"&product_id="+product_id;
}
window.onload = function(){
initQueryCondition()};
</script>

</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="product_id" value="<%=product_id%>">
<div id="queryCondition" class="qcMain" style="display:none;width:220px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.levelID2",clientLocale)%> :</td><!--类别-->
			<td>
				<select name="level_id" style="width:150px">
					<%=Argument.getLevelID(level_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button></td>
			<!--确定-->
		</tr>
  </table>	
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=4  class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
					<tr>
					<td align=left></td>
					<td align=right >
					<div class="btn-wrapper">
					<font color="#215dc6" size=3><b><%=pMap.get("PRODUCT_NAME")%></b></font>
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--查询-->
						<button type="button"  class="xpbutton3" accessKey=n id="newButton" name="newButton" onclick="javascript:newLevel(<%=product_id%>);"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
						<!--新建-->
						</div>
					</td>				
				</tr>
				
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					
					<tr class="trh">
						<td align="center"><%=LocalUtilis.language("class.typeName",clientLocale)%> </td><!--类别名称-->
						<td align="center"><%=LocalUtilis.language("class.levelValueName2",clientLocale)%> </td><!--类别分类值名称-->
						<td align="center"><%=LocalUtilis.language("class.min",clientLocale)%> </td><!--最小值-->
						<td align="center"><%=LocalUtilis.language("class.max",clientLocale)%> </td><!--最大值-->
						<td align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
						<td align="center"><%=LocalUtilis.language("message.permissionView",clientLocale)%> </td><!--权限查看-->
					</tr>
<%
int iCurrent = 0;
int iCount=0;
List rsList = levelList.getRsList();
Map rowMap = null;
for(int i=0;i<rsList.size();i++)
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
				<td class="tdh"  align="center" ><%=Utility.trimNull(level_name)%></td>
				<td align="left"><%=Utility.trimNull(level_value_name)%></td>
				<td align="center"><%=Utility.trimNull(min_value)%></td>	
				<td align="center"><%=Utility.trimNull(max_value)%></td>
				<td align="center" width=60>
					 <a href="javascript:disableAllBtn(true);delLevel('<%=serial_no%>','<%=product_id%>')">
					 		<img border="0" src="<%=request.getContextPath()%>/images/delete.gif" width="16" height="16"/>	
					 </a>	
				</td>
				<td align="center" width=60>
					 <a href="javascript:disableAllBtn(true);level_setting('<%=product_id%>','<%=level_value_id%>')">
					 		<img border="0" src="<%=request.getContextPath()%>/images/system.gif" width="16" height="16"/>	
					 </a>	
				</td>
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
				<td align="center" ></td>		
			</tr>
<%
}
%>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=levelList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%>  <!--项--></b></td>
				<!-- <td align="center" >-</td>
				<td align="center" >-</td>
				<td align="center" >-</td>
				<td align="center" >-</td>
				<td align="center" >-</td> -->
			</tr>
		</table>

		

		<table border="0" width="100%">
			<tr valign="top">
				<td><%=levelList.getPageLink(sUrl,clientLocale)%></td>
					<td align="right"></td>
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
<%product.remove();
%>
