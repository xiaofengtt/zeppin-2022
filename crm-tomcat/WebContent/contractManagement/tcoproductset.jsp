<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
TcoProductLocal local = EJBFactory.getTcoProduct();
TcoProductVO vo = new TcoProductVO();

String coProduct_name = Utility.trimNull(request.getParameter("coProduct_name"));

vo.setCoProduct_name(coProduct_name);
vo.setInput_man(input_operatorCode);
IPageList pageList = local.queryByPageList(vo,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&coProduct_name=" + coProduct_name;
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
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>
function showInfo(coProduct_id)
{
	if(showModalDialog('tcoProduct_edit.jsp?coProduct_id='+coProduct_id, '', 'dialogWidth:650px;dialogHeight:440px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
	//location = 'tcoProduct_edit.jsp?coProduct_id='+coProduct_id;
}

function newInfo()
{	
	if(showModalDialog('tcoProduct_new.jsp', '', 'dialogWidth:650px;dialogHeight:440px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
	//location = 'tcoProduct_new.jsp';
}



function refreshPage()
{
	disableAllBtn(true);
	location = 'tcoproductset.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
					+'&coProduct_name='+document.theform.coProduct_name.value;

}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

function op_check()
{
	if(checkedCount(document.theform.coProduct_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.action="tcoProduct_check.jsp";
		//location="tcoContract_check.jsp";
		document.theform.submit();
	}
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="tcoProduct_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:260px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right">产品名称:</td>
					<td><input type="text" name="coProduct_name" size="20" value="" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<button type="button" class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>


	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button" class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button" class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
							&nbsp;&nbsp;&nbsp;<!--新建-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button" class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.coProduct_id)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--删除所选记录--><!--删除-->
						<%}%>
						<%if (input_operator.hasFunc(menu_id, 101)){%>
                        <!-- 审核 --><!-- 审核通过 -->
						<button type="button" class="xpbutton4" name="btnCheck" title='<%=LocalUtilis.language("class.auditBy2",clientLocale)%>' onclick="javascript:op_check();"><%=LocalUtilis.language("class.auditBy2",clientLocale)%> </button>&nbsp;&nbsp;&nbsp; 
						<%}%>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td width="">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.coProduct_id);">
							产品编号
						</td>
						<td width="">产品名称</td>
						<td width="">首次发行日期</td>
						<td width="">产品类型</td>
						<td width="">研发情况</td>
						<td width="">功能简介</td>
						<td width="">建议销售价格</td>
						<td width="">审核标志</td>
						<td width="6%">编辑</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	int coProduct_id = Utility.parseInt(Utility.trimNull(map.get("COPRODUCT_ID")),0);
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="">
						  <input type="checkbox" name="coProduct_id" value="<%=coProduct_id %>" class="flatcheckbox">
						  <%=coProduct_id%>
						</td> 
						<td align="left"><%=Utility.trimNull(map.get("COPRODUCT_NAME")) %></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("PUBLISH_DATE")),new Integer(0)))%></td>
						<td align="center"><%=Utility.trimNull(map.get("COPRODUCT_TYPE_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("SELFMADE_TYPE_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("COPRODUCT_SUMMARY")) %></td>
						<td align="right"><%=Format.formatMoney0(Utility.parseDecimal(Utility.trimNull(map.get("COPRODUCT_PRICE")),new BigDecimal(0.00)))%></td>
						<td align="center"><%
											if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).intValue()==2){
											%>已审核<%	
											}else{
												%>未审核<%
											}			
						 %></td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=coProduct_id %>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="编辑" />
							</a>
						<%}%>	
						</td>
					</tr>
<%}
for (int i=0;i < pageList.getBlankSize(); i++){
%>
					<tr class="tr<%=(i % 2)%>">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<div>
					<%=pageList.getPageLink(sUrl,clientLocale)%>
				</div>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>
<div>
	
</div>				
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%local.remove();%>

