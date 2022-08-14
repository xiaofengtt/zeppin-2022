<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得页面传递变量
Integer subject_id = Utility.parseInt(Utility.trimNull(request.getParameter("subject_id")),new Integer(0));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer currentdate = Utility.parseInt(request.getParameter("currentdate"),new Integer(0));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));

//页面辅助参数
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;

//获得对象及结果集
RatingVO vo = new RatingVO();
RatingLocal rating = EJBFactory.getRating();

vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setSubject_id(subject_id);
vo.setCurrnet_date(currentdate);
vo.setInput_man(input_man);
IPageList pageList = rating.pageList_tcustscore(vo, totalColumn, t_sPage, t_sPagesize);

sUrl = sUrl+ "&subject_id =" +subject_id+ "&cust_no =" +cust_no
		+ "&cust_name =" +cust_name+ "&currentdate =" +currentdate;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<!--计分操作数设置-->
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
<script language=javascript>
function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);
	syncDatePicker(document.theform.currentdate_picker, document.theform.currentdate);
	location = 'cust_score.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&subject_id=' + document.theform.subject_id.value
							+ '&cust_no=' + document.theform.cust_no.value
							+ '&cust_name=' + document.theform.cust_name.value
							+ '&currentdate=' + document.theform.currentdate.value;
}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="cust_score.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
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
					<td align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%>:</td><!--客户编号-->
					<td align="left">
						<input type="text" name="cust_no" value="<%=cust_no %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--客户名称-->
					<td align="left" >
						<input type="text" name="cust_name" value='<%=cust_name%>' onkeydown="javascript:nextKeyPress(this)" size="20">
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.subjectsName",clientLocale)%>:</td><!--科目名称-->
					<td align="left">
						<select name="subject_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getScoreSubjectOptions(subject_id)%>
						</select>
					</td>
					<td align="right"><%=LocalUtilis.language("class.scoringDate",clientLocale)%> :</td><!--打分日期-->
					<td>
						<input TYPE="text" style="width:120" NAME="currentdate_picker" class=selecttext value="<%=Format.formatDateLine(currentdate)%>" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.currentdate_picker,theform.currentdate_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="hidden" NAME="currentdate" value="">
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
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
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
						<td align="center"><%=LocalUtilis.language("class.customerID",clientLocale)%></td><!--客户编号-->
						<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!--客户名称-->
						<td align="center"><%=LocalUtilis.language("class.ratingSubjectName",clientLocale)%></td><!--评分科目名称-->
						<td align="center"><%=LocalUtilis.language("class.currentDate",clientLocale)%></td><!--当前日期-->
						<td align="center"><%=LocalUtilis.language("class.currentScore",clientLocale)%></td><!--当前分值-->
						<td align="center"><%=LocalUtilis.language("class.increaseDecrease",clientLocale)%></td><!--比上期增减-->
						<td align="center"><%=LocalUtilis.language("class.endDate3",clientLocale)%></td><!--截止日期-->
						<td align="center"><%=LocalUtilis.language("class.memberDescription",clientLocale)%></td><!--描述-->
					</tr>
<%
list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{
	//读数据
	map = (Map)list.get(i);
	if(map!=null){
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center">&nbsp;<%=Utility.trimNull(map.get("CUST_NO")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("CUST_NAME")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("SUBJECT_NAME")) %></td>
						<td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("CURRENTDATE")),new Integer(0))) %></td>
						<td align="right"><%=Utility.trimNull(map.get("CURRENT_SOURCE")) %>&nbsp;</td>
						<td align="right"><%=Utility.trimNull(map.get("REGULATION")) %>&nbsp;</td>
						<td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0)))%></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("SUMMARY")) %></td>
					</tr>
<%
	iCurrent++;
	iCount++;
	}
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
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
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<table border="0" width="100%">
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
<%
rating.remove();
%>

