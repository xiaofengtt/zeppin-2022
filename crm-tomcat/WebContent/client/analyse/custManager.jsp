<%@ page contentType="text/html; charset=GBK"  import="java.math.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.customer.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer team_id = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
String manager_name = Utility.trimNull(request.getParameter("manager_name"));
String leader_name = Utility.trimNull(request.getParameter("leader_name"));

String tempUrl = "";
String[] totalColumn = new String[0];
TcustmanagersVO vo = new TcustmanagersVO();
CustomerStatLocal tcustmanagers_Bean = EJBFactory.getCustomerStat();

vo.setTeam_id(team_id);
vo.setManagername(manager_name);
vo.setLeader_name(leader_name);

IPageList pageList =tcustmanagers_Bean.pagelist_TeamManager(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

//分页参数
int iCount = 0;
int iCurrent = 0;
Map map = null;
List list = pageList.getRsList();
sUrl = sUrl+ "&team_id=" + team_id
		+ "&manager_name="+manager_name
		+ "&leader_name=" + leader_name;

tcustmanagers_Bean.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.nonProductSet",clientLocale)%></TITLE><!--非信托产品设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function $$(_name){
	return document.getElementsByName(_name)[0];
}

function refreshPage() {
	disableAllBtn(true);
	location = 'custManager.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value
							+ '&team_id=' + document.theform.team_id.value
							+ '&manager_name=' + document.theform.manager_name.value
							+ '&leader_name=' + document.theform.leader_name.value;
}

function StartQuery() {
	refreshPage();
}

window.onload = function(){
		initQueryCondition();
	};
</script>
</HEAD>
<BODY class="BODY body-nox" >
<form name="theform" method="POST" action="noproduct_list.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table>
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
			<td  align="right"><%=LocalUtilis.language("class.teamName",clientLocale)%> : </td><!-- 团队名称 -->
			<td  align="left" colspan="3">
				<select size="1" name="team_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 300px">
				<%=Argument.getTeamName(team_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> : </td><!-- 客户经理 -->
			<td  align="left">
				<input type="text" name="manager_name" value="<%=manager_name %>"/>
			</td>
			<td align="right"><%=LocalUtilis.language("class.responsibleMan",clientLocale)%> : </td><!-- 团队负责人姓名 -->
			<td align="left" >
				<input type="text" name="leader_name" value="<%=leader_name %>"/>
			</td>
		</tr>
				<tr>
					<td align="center" colspan="4">
						<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
						<td  class="page-title">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
							<!--查询-->
						</div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class="trh">
		 <td width="*"  align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%></td><!-- 团队名称 -->
         <td width="*" align="center"><%=LocalUtilis.language("class.managerName",clientLocale)%> </td><!-- 客户经理名称 -->
         <td width="*"  align="center" sort="num"><%=LocalUtilis.language("message.customer",clientLocale)%><%=LocalUtilis.language("intrsut.home.quantity",clientLocale)%></td><!-- 客户数量 -->
         <td width="*"  align="center" sort="num"><%=LocalUtilis.language("intrsut.home.Institutions",clientLocale)%> </td><!-- 机构数 -->
         <td width="*"  align="center" sort="num"><%=LocalUtilis.language("intrsut.home.persionaldigital",clientLocale)%> </td><!-- 个人 -->
         <td width="*" align="center" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!--已购买金额 -->
         <td width="*"  align="center" sort="num"><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!-- 受益份额 -->
     </tr>
<%
Integer cust_type_p = new Integer(0);
Integer cust_type_e = new Integer(0);
BigDecimal rg_money = new BigDecimal(0);
BigDecimal ben_amount = new BigDecimal(0);
int cust_type_p_sum = 0;
int cust_type_e_sum = 0;
BigDecimal rg_money_sum = new BigDecimal(0);
BigDecimal ben_amount_sum = new BigDecimal(0);

for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);
	iCount++;
	cust_type_p = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE_P")),new Integer(0));
	cust_type_e = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE_E")),new Integer(0));
	rg_money = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY")),new BigDecimal(0),2,"1");
	ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0),2,"1");
	rg_money_sum = rg_money_sum.add(rg_money);
	ben_amount_sum = ben_amount_sum.add(ben_amount);
	cust_type_p_sum = cust_type_p_sum + cust_type_p.intValue();
	cust_type_e_sum = cust_type_e_sum + cust_type_e.intValue();
%>
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<%=Utility.trimNull(map.get("TEAM_NAME"))%>
            </td>
            <td align="center" style="padding-left: 7px;"><%=Utility.trimNull(map.get("MANAGERNAME"))%></td>
            <td align="center"><%=cust_type_p.intValue() + cust_type_e.intValue()%> 个</td>
            <td align="center"><%=cust_type_e %> 个</td>
            <td align="center"><%=cust_type_p%> 人</td>
            <td align="right"><%=Format.formatMoney(rg_money) %>&nbsp;&nbsp;</td>
	        <td align="right"><%=Format.formatMoney(ben_amount) %>&nbsp;&nbsp;</td>
         </tr>
<%}
for (int i=0;i < pageList.getBlankSize(); i++) { %>
					<tr class="tr<%=(i % 2)%>">
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
						<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
						<td></td>
						<td>&nbsp;&nbsp;<%=cust_type_p_sum + cust_type_e_sum %>个</td>
						<td>&nbsp;&nbsp;<%=cust_type_e_sum %> 个</td>
						<td>&nbsp;&nbsp;<%=cust_type_p_sum %> 人</td>
						<td align="right"><%=Format.formatMoney(rg_money_sum) %>&nbsp;&nbsp;</td>
						<td align="right"><%=Format.formatMoney(ben_amount_sum) %>&nbsp;&nbsp;</td>
					</tr>
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</td>
	</tr>
</table>
</form>
<%@ include file="/includes/foot.inc" %>
</BODY>
</HTML>