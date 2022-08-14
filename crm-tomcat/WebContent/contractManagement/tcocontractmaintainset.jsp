<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
TcoContractMaintainLocal local = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO vo = new TcoContractMaintainVO();

String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String main_pro_name = Utility.trimNull(request.getParameter("main_pro_name"));
String contractMaintain_sub_bh = Utility.trimNull(request.getParameter("contractMaintain_sub_bh"));
String start_date_begin = Utility.trimNull(request.getParameter("start_date_begin"));
String start_date_end = Utility.trimNull(request.getParameter("start_date_end"));
String end_date_begin = Utility.trimNull(request.getParameter("end_date_begin"));
String end_date_end = Utility.trimNull(request.getParameter("end_date_end"));
String collect_time_begin = Utility.trimNull(request.getParameter("collect_time_begin"));
String collect_time_end = Utility.trimNull(request.getParameter("collect_time_end"));
vo.setMain_pro_name(main_pro_name);
vo.setCoContractMaintain_sub_bh(contractMaintain_sub_bh);
vo.setStart_date_begin(Utility.parseInt(start_date_begin,new Integer(0)));
vo.setStart_date_end(Utility.parseInt(start_date_end,new Integer(0)));
vo.setEnd_date_begin(Utility.parseInt(end_date_begin,new Integer(0)));
vo.setEnd_date_end(Utility.parseInt(end_date_end,new Integer(0)));
vo.setCollect_time_begin(Utility.parseInt(collect_time_begin,new Integer(0)));
vo.setCollect_time_end(Utility.parseInt(collect_time_end,new Integer(0)));
vo.setInput_man(input_operatorCode);
vo.setCust_name(q_cust_name);

String[] totalColumn = {"HT_MONEY", "WH_MONEY"};
IPageList pageList = local.queryByPageList(vo, totalColumn,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&q_cust_name=" + q_cust_name + "&main_pro_name=" + main_pro_name+"&contractMaintain_sub_bh="+contractMaintain_sub_bh+"&start_date_begin="+start_date_begin+ "&start_date_end="
		 + start_date_end+"&end_date_begin="+end_date_begin+"&end_date_end="+end_date_end
			+"&collect_time_begin="+collect_time_begin
					+"&collect_time_end="+collect_time_end;
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
function showInfo(maintain_id)
{
	/**
	if(showModalDialog('contract_update.jsp?contract_id='+contract_id, '', 'dialogWidth:650px;dialogHeight:440px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
	*/
	location = 'tcoContractMaintain_update.jsp?maintain_id='+maintain_id;
}
function viewInfo(maintain_id)
{
	location = 'tcoContractMaintain_view.jsp?maintain_id='+maintain_id;
}
function viewCoContract(coContract_id)
{
	location = 'tcoContract_view.jsp?coContract_id='+coContract_id;
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
	location = 'tcoContractMaintain_new.jsp';
}



function refreshPage()
{
	disableAllBtn(true);

	syncDatePicker(document.theform.start_date_begin_picker, document.theform.start_date_begin);	
	syncDatePicker(document.theform.start_date_end_picker, document.theform.start_date_end);
	syncDatePicker(document.theform.end_date_begin_picker, document.theform.end_date_begin);
	syncDatePicker(document.theform.end_date_end_picker, document.theform.end_date_end);
	syncDatePicker(document.theform.collect_time_begin_picker, document.theform.collect_time_begin);
	syncDatePicker(document.theform.collect_time_end_picker, document.theform.collect_time_end);
	
	location = 'tcocontractmaintainset.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
					+'&q_cust_name=' + document.theform.q_cust_name.value
					+'&main_pro_name='+document.theform.main_pro_name.value
					+'&contractMaintain_sub_bh='+document.theform.contractMaintain_sub_bh.value
					+ '&start_date_begin=' + document.theform.start_date_begin.value
					+'&start_date_end='+document.theform.start_date_end.value
					+'&end_date_begin='+document.theform.end_date_begin.value
					+ '&end_date_end=' + document.theform.end_date_end.value
					+'&collect_time_begin='+document.theform.collect_time_begin.value
					+'&collect_time_end='+document.theform.collect_time_end.value;

}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

function op_check()
{
	if(checkedCount(document.theform.maintain_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.action="tcoContractMaintain_check.jsp";
		document.theform.submit();
	}
}
function checkInfo(serialno)
{
	location = 'tcoContractMaintain_new.jsp';
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="tcoContractMaintain_remove.jsp">
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
					<td align="right">客户名称:</td>
					<td><input type="text" name="q_cust_name" size="20" value="<%=q_cust_name %>" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="right">维护项目名称:</td><!--维护项目名称-->
					<td><input type="text" name="main_pro_name" size="20" value="<%=main_pro_name %>" onkeydown="javascript:nextKeyPress(this)"></td>
					<td align="right">维护合同编号:</td><!--维护合同编号-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="contractMaintain_sub_bh" size="20" value="<%=contractMaintain_sub_bh %>">&nbsp;&nbsp;
					</td>
				</tr>
					<tr>
					<td align="right">预计收款日期从:</td>
					<td>
						<INPUT TYPE="text" NAME="collect_time_begin_picker" class=selecttext value="<%=collect_time_begin %>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.collect_time_begin_picker,theform.collect_time_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="collect_time_begin" id="collect_time_begin"   value="">
					</td>
					<td align="right">预计收款日期到:</td>
					<td>
						<INPUT TYPE="text" NAME="collect_time_end_picker" class=selecttext value="<%=collect_time_end %>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.collect_time_end_picker,theform.collect_time_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="collect_time_end" id="collect_time_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">开始日期从:</td>
					<td>
						<INPUT TYPE="text" NAME="start_date_begin_picker" class=selecttext value="<%=start_date_begin %>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_begin_picker,theform.start_date_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="start_date_begin" id="start_date_begin"   value="">
					</td>
					<td align="right">开始日期到:</td>
					<td>
						<INPUT TYPE="text" NAME="start_date_end_picker" class=selecttext value="<%=start_date_end %>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_end_picker,theform.start_date_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="start_date_end" id="start_date_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">到期日期从:</td>
					<td>
						<INPUT TYPE="text" NAME="end_date_begin_picker" class=selecttext value="<%=end_date_begin %>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_begin_picker,theform.end_date_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="end_date_begin" id="end_date_begin"   value="">
					</td>
					<td align="right">到期日期到:</td>
					<td>
						<INPUT TYPE="text" NAME="end_date_end_picker" class=selecttext value="<%=end_date_end %>" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_end_picker,theform.end_date_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="end_date_end" id="end_date_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<button type="button" class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>


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
							<button type="button" class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.maintain_id)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
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
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.maintain_id);">
							维护合同编号
						</td>
						<td>原合同号</td>
						<td>客户名称</td>
						<td width="">维护项目名称</td><!--备注-->
						<td width="">预计收款时间</td>
						<td width="">起始日期</td>
						<td width="">到期日期</td>
						<td width="">合同总金额</td>
						<td width="">维护费金额</td>
						<td width="">审核标志</td>
						<td width="6%">编辑</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
int maintain_id=0;
Map map=new HashMap();
List list = pageList.getRsList();
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);
	maintain_id = Utility.parseInt(Utility.trimNull(map.get("MAINTAIN_ID")),0);
	int coContract_id = Utility.parseInt(Utility.trimNull(map.get("COCONTRACT_ID")),0);
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="">
							<%
							if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).intValue()!=2){
							%>
						  <input type="checkbox" name="maintain_id" value="<%=maintain_id %>" class="flatcheckbox">
						  <%}else{ %>
						  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  <%} %>
						  <a href="#" onclick="javascript:viewInfo(<%=maintain_id %>)">
						  <%=Utility.trimNull(map.get("COCONTRACTMAINTAIN_SUB_BH")) %>
						  </a>
						</td>
						<td align="left">
							<%if(coContract_id!=0) { %>
							<a href="#" onclick="javascript:viewCoContract(<%=coContract_id%>)">
							<% } %>
							<%=Utility.trimNull(map.get("COCONTRACT_SUB_BH")) %>
							<%if(coContract_id!=0) { %>
							</a>
							<% } %>
						</td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME")) %></td> 
						<td align="left"><%=Utility.trimNull(map.get("MAIN_PRO_NAME")) %></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("COLLECT_TIME")),new Integer(0)))%></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0)))%></td>
						<td align="right"><%=Format.formatMoney0(Utility.parseDecimal(Utility.trimNull(map.get("HT_MONEY")),new BigDecimal(0.00)))%></td>
						<td align="right"><%=Format.formatMoney0(Utility.parseDecimal(Utility.trimNull(map.get("WH_MONEY")),new BigDecimal(0.00)))%></td>
						<td align="center"><%
											if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).intValue()==2){
											%>已审核<%	
											}else{
												%>未审核<%
											}			
						 %></td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)&&Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).intValue()!=2){%>
							<a href="#" onclick="javascript:showInfo(<%=maintain_id %>)">
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
						<td></td>
						<td></td>
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" >
							<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td align="right"><%=Format.formatMoney0(Utility.parseDecimal(Utility.trimNull(pageList.getTotalValue("HT_MONEY")),new BigDecimal(0.00))) %></td>
						<td align="right"><%=Format.formatMoney0(Utility.parseDecimal(Utility.trimNull(pageList.getTotalValue("WH_MONEY")),new BigDecimal(0.00))) %></td>
						<td></td>
						<td></td>
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

