<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
Integer level_value_id = Utility.parseInt(request.getParameter("level_value_id"),null);
Integer op_code = Utility.parseInt(request.getParameter("op_code"),null);
CustLevelAuthVO vo = new CustLevelAuthVO();
ProductVO pvo = new ProductVO();
OperatorVO ovo = new OperatorVO();
CustLevelVO levelvo = new CustLevelVO();
OperatorLocal operator = EJBFactory.getOperator();
ProductLocal product = EJBFactory.getProduct();
CustomerLocal customer = EJBFactory.getCustomer();

pvo.setProduct_id(product_id);
List productList = product.load(pvo);
Map pMap = null;
if(productList!=null&&productList.size()==1)
	pMap = (Map)productList.get(0);

levelvo.setProduct_id(product_id);
levelvo.setLevel_value_id(level_value_id);
IPageList levelList = null;
Map lMap = null;
if(product_id!=null&&product_id.intValue()!=0)
	levelList = customer.listCustLevel(levelvo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
if(levelList!=null&&levelList.getRsList().size()==1)
	lMap = (Map)levelList.getRsList().get(0);
	
vo.setProduct_id(product_id);
vo.setLevel_value_id(level_value_id);

IPageList pageList = operator.listCustLevelAuth(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl+"&product_id="+product_id+"&level_value_id="+level_value_id;

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
	location="level_setting_list.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&product_id="+document.theform.product_id.value
					+"&level_value_id="+document.theform.level_value_id.value
					+"&op_code="+document.theform.op_code.value;
}

function StartQuery()
{
	refreshPage();
}

function addCustLevelAuth(){
	if(!sl_checkChoice(document.theform.op_code,"<%=LocalUtilis.language("message.operator",clientLocale)%> "))  return false;//员工	
	disableAllBtn(true);
	document.theform.submit();
}

function delCustLevelAuth(product_id,level_value_id,op_code){
	if(confirm("<%=LocalUtilis.language("message.ifCancelCustomerAccess",clientLocale)%> ？")){//确认要取消该用户的客户访问权限
		disableAllBtn(true);
		location = 'levelAuth_del.jsp?product_id='+product_id
							+'&level_value_id='+level_value_id
						  +'&op_code='+op_code;
						
		}
}

function gohome(product_id){
	location = 'contract_level_setting.jsp?product_id='+product_id;	
	//history.back(-1);
}
window.onload = function(){
initQueryCondition()};
</script>

</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" action="levelAuth_add.jsp">
<input name="level_value_id" value="<%=level_value_id%>" type="hidden">
<input name="product_id" value="<%=product_id%>" type="hidden">
<div id="queryCondition" class="qcMain" style="display:none;width:280px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right">
				<%=LocalUtilis.language("message.opList",clientLocale)%> ：
			</td><!--员工列表-->
			<td valign="bottom">
				<select name="op_code" style="width:150px">
					<%=Argument.getOperatorOptions(op_code)%>
				</select>&nbsp;&nbsp;
				<a title="<%=LocalUtilis.language("message.clickToGrantUserAccess",clientLocale)%> " href="#" onclick="javascript:addCustLevelAuth();">
					 		<img border="0" src="/images/notify_new.gif" width="18" height="18"/>	
				</a>
			</td><!--单击授予用户客户访问权限-->
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button></td>
		</tr><!--确定-->
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
						<td colspan=4 class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
					<tr>
					<td align=left></td>
					<!--权限区间-->
					<td align=right >
					<div class="btn-wrapper">
					<font color="#215dc6" size=3><b><%=Utility.trimNull(pMap.get("PRODUCT_NAME"))%>_<%=Utility.trimNull(lMap.get("LEVEL_VALUE_NAME"))%>_<%=LocalUtilis.language("message.accessInterval",clientLocale)%> :<%=Utility.trimNull(lMap.get("MIN_VALUE"))%>~<%=Utility.trimNull(lMap.get("MAX_VALUE"))%></b></font>
					
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--查询-->
						<button type="button"  class="xpbutton3" accessKey=b id="backButton" name="queryButton" onclick="javascript:gohome(<%=product_id%>)"> <%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--返回-->
						</div>
					</td>				
				</tr>
				<tr><td>&nbsp;&nbsp;&nbsp; </td></tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					
					<tr class="trh">
						<td align="center"><%=LocalUtilis.language("class.opCode",clientLocale)%> </td><!--员工编号-->
						<td align="center"><%=LocalUtilis.language("class.opName",clientLocale)%> </td><!--员工姓名-->
						<td align="center"><%=LocalUtilis.language("class.departID2",clientLocale)%> </td><!--所属部门-->
						<td align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
					</tr>
<%
int iCurrent = 0;
int iCount=0;
List rsList = pageList.getRsList();
List oList = null;
Map rowMap = null;
Map oMap = null;

for(int i=0;rsList!=null&&i<rsList.size();i++)
{ 
	rowMap = (Map)rsList.get(i);
	
	op_code = (Integer)rowMap.get("OP_CODE");
	ovo.setOp_code(op_code);
	oList = operator.listOpAll(ovo);
	if(oList!=null&&oList.size()==1)
		oMap = (Map)oList.get(0);
%>

			<tr class="tr<%=(iCurrent % 2)%>" >
				<td class="tdh"  align="center" ><%=Utility.trimNull(op_code)%></td>
				<td align="left"><%=Utility.trimNull(oMap.get("OP_NAME"))%></td>	
				<td align="center"><%=Utility.trimNull(Argument.getDepartNameByDepartId((Integer)oMap.get("DEPART_ID")))%></td>
				<td align="center" width=60>
					 <a href="#" onclick="javascript:delCustLevelAuth('<%=product_id%>','<%=level_value_id%>','<%=op_code%>')">
					 		<img border="0" src="<%=request.getContextPath()%>/images/delete.gif" width="16" height="16"/>	
					 </a>	
				</td>
			</tr>
<%
			iCurrent++;
			iCount++;
	
}

for (int i=0; i < pageList.getBlankSize(); i++){
%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>		
				<td align="center" ></td>		
			</tr>
<%
}
%>
			<tr class="trbottom">
				<td class="tdh" align="center" ><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--><%=pageList.getTotalSize()%><%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
				<td align="center" >-</td>
				<td align="center" >-</td>
				<td align="center" >-</td>
			</tr>
		</table>

		<br>

		<table border="0" width="100%">
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl)%></td>
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
