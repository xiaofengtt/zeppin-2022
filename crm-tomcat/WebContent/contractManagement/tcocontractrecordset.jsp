<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
TcoContractRecordLocal local = EJBFactory.getTcoContractRecord();
TcoContractRecordVO vo = new TcoContractRecordVO();
 
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String main_pro_name = Utility.trimNull(request.getParameter("main_pro_name"));
String record_date_begin = Utility.trimNull(request.getParameter("record_date_begin"));
String record_date_end = Utility.trimNull(request.getParameter("record_date_end"));

vo.setCust_name(cust_name);
vo.setMain_pro_name(main_pro_name);
vo.setRecord_date_begin(Utility.parseInt(record_date_begin,new Integer(0)));
vo.setRecord_date_end(Utility.parseInt(record_date_end,new Integer(0)));
vo.setInput_man(input_operatorCode);
IPageList pageList = local.queryByPageList(vo,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&cust_name=" + cust_name+"&main_pro_name="+main_pro_name+"&record_date_begin="+record_date_begin
+ "&record_date_end="+ record_date_end;
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
function showInfo(mainRecord_id)
{
	/**
	if(showModalDialog('contract_update.jsp?contract_id='+contract_id, '', 'dialogWidth:650px;dialogHeight:440px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
	*/
	location = 'tcoContractRecord_edit.jsp?mainRecord_id='+mainRecord_id;
}

function newInfo()
{	
	/**
	if(showModalDialog('contract_new.jsp', '', 'dialogWidth:650px;dialogHeight:440px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
*/
	location = 'tcoContractRecord_new.jsp';
}



function refreshPage()
{
	disableAllBtn(true);

	syncDatePicker(document.theform.record_date_begin_picker, document.theform.record_date_begin);	
	syncDatePicker(document.theform.record_date_end_picker, document.theform.record_date_end);
	location = 'tcocontractrecordset.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
					+'&cust_name='+document.theform.cust_name.value
					+'&main_pro_name='+document.theform.main_pro_name.value
					+ '&record_date_begin=' + document.theform.record_date_begin.value
					+'&record_date_end='+document.theform.record_date_end.value;
}

function removeInfo(mainRecord_id)
{
	if(!sl_confirm("该操作将删除选中的记录，要继续"))
	     return;
	location = 'tcoContract_remove.jsp?mainRecord_id=' + mainRecord_id;
}


window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

function op_check()
{
	if(checkedCount(document.theform.mainRecord_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.action="tcoContract_check.jsp";
		//location="tcoContract_check.jsp";
		document.theform.submit();
	}
}
function checkInfo(serialno)
{
	location = 'tcocontract_new.jsp';
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="tcoContractRecord_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:560px;">
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
					<td align="right">客户名称:</td><!--客户名称-->
					<td><input type="text" name="cust_name" size="20" value="" onkeydown="javascript:nextKeyPress(this)"></td>
					<td align="right">维护项目名称:</td><!--维护项目名称-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="main_pro_name" size="20" value="">&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right">受理日期从:</td>
					<td>
						<INPUT TYPE="text" NAME="record_date_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.record_date_begin_picker,theform.record_date_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="record_date_begin" id="record_date_begin"   value="">
					</td>
					<td align="right">受理日期到:</td>
					<td>
						<INPUT TYPE="text" NAME="record_date_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.record_date_end_picker,theform.record_date_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="record_date_end" id="record_date_end"   value="">
					</td>
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
							<button type="button" class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.mainRecord_id)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--删除所选记录--><!--删除-->
						<%}%>
						<%if (input_operator.hasFunc(menu_id, 103)){%>
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
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.mainRecord_id);">
							记录编号
						</td>
						<td width="">客户名称</td><!--备注-->
						<td width="">维护项目名称</td>
						<td width="">受理日期</td>
						<td width="">受理状态</td>
						<td width="6%">编辑</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	int mainRecord_id = Utility.parseInt(Utility.trimNull(map.get("MAINRECORD_ID")),0);
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="">
						  <input type="checkbox" name="mainRecord_id" value="<%=mainRecord_id %>" class="flatcheckbox">
						  <%=Utility.trimNull(map.get("MAINRECORD_ID")) %>
						</td> 
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
						<td align="center"><%=Utility.trimNull(map.get("MAIN_PRO_NAME")) %></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("RECORD_DATE")),new Integer(0)))%></td>
						<td align="center"><%=Utility.trimNull(map.get("MAIN_STATUS_NAME"))%></td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=mainRecord_id %>)">
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
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
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

