<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer grade_id =Utility.parseInt(request.getParameter("grade_id"), new Integer(0));//评级体系编号

GradeStandardLocal gs_local = EJBFactory.getGradStandard();
GradeStandardVO gs_vo = new GradeStandardVO();

gs_vo.setGrade_id(grade_id);
gs_vo.setSerial_no(new Integer(0));//评级标准序号默认0

IPageList pageList = gs_local.queryGradeStandAll(gs_vo, Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();
String tempUrl = "";

tempUrl = "&grade_id=" + grade_id;
sUrl = sUrl + tempUrl;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
//编辑评级标准设置
function showInfo(grade_id,serial_no)
{   
	if(showModalDialog('client_level_update.jsp?newflag=0&grade_id=' + grade_id+'&serial_no='+serial_no, '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

//新增评级标准设置
function newInfo()
{
	if(showModalDialog('client_level_update.jsp?newflag=1', '', 'dialogWidth:420px;dialogHeight:400px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

//查询评级标准设置
function refreshPage()
{	
	disableAllBtn(true);
	waitting.style.visibility='visible';
	location = 'client_level_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&grade_id=' + document.theform.grade_id.value;
}

//查询
function StartQuery()
{
	refreshPage();
}

//窗体加载评级标准设置
window.onload = function(){
initQueryCondition()
};
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="client_level_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
<div id="queryCondition" class="qcMain" style="display:none;width:200px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
	<tr>
		<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!--评分体系-->
		<td><select size="1" name="grade_id"
					onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
							<%=Argument.getCrmGradeIDOptions(grade_id)%>
		</select></td>
	</tr>
	<tr>
        <!--确定-->
		<td align="center" colspan="4"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;					
			</td>
		</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" class="page-title">						
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>
					<td align="right">
					<div class="btn-wrapper">
					<%if (input_operator.hasFunc(menu_id, 108)) {%>
					<!--查询-->
                    <button type="button"  class="xpbutton3" accessKey=q  id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
					<%}if (input_operator.hasFunc(menu_id, 100)) {%>
					<!--新建记录--><!--新建-->
                    <button type="button"  class="xpbutton3" accessKey=n  id="btnNew" name="btnNew" title='<%=LocalUtilis.language("message.newRecord",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
					&nbsp;&nbsp;&nbsp; <%}%> <%if (input_operator.hasFunc(menu_id, 101)) {%>
					<!--删除所选记录--><!--删除-->
                    <button type="button"  class="xpbutton3" accessKey=d id="btnCancel" name="btnCancel" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%>' onclick="javascript:if(confirmRemove(document.theform.serial_no)) {  document.theform.submit();}"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
					<%}%>
					</div>
					</td>
				</tr>
			</table>
			<br/>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<!--序号-->
                    <td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td>
					<td><%=LocalUtilis.language("class.crmGrade",clientLocale)%> </td><!--评分体系-->
					<td><%=LocalUtilis.language("class.gradeLevel4",clientLocale)%> </td><!--等级分类-->
					<td><%=LocalUtilis.language("class.max",clientLocale)%> </td><!--最大值-->
					<td><%=LocalUtilis.language("class.min",clientLocale)%> </td><!--最小值-->
					<td><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
				</tr>
				<%
				int iCount = 0;
				int iCurrent = 0;
				while (it.hasNext())
				{  
					map = (Map)it.next();
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td width="10%"> 
					<table border="0" width="20%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" ><input type="checkbox" name="serial_no" value="<%=map.get("SERIAL_NO")+"#"+map.get("GRADE_ID")%>" class="flatcheckbox"></td>
							<td width="90%" align="left"><%=map.get("SERIAL_NO")%></td>							
						</tr>
					</table>
					</td>
					<td align="left"><%=Utility.trimNull(Argument.getTintegerparamValue(new Integer(2005),new Integer(Utility.trimNull(map.get("GRADE_BH")))))%></td>
<%-- 					<td align="left"><%=Utility.trimNull(map.get("GRADE_NAME"))%></td> --%>
					<td align="left"><%=Utility.trimNull(map.get("GRADE_LEVEL_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("MAX_VALUE"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("MIN_VALUE"))%></td>					
					<td align="center">
						<a href="#" onclick="javascript:showInfo(<%=map.get("GRADE_ID")%>,<%=map.get("SERIAL_NO")%>);">
							<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> '/><!--编辑-->
						</a>
					</td>					
				</tr>
				<%
				iCurrent++;
				iCount++;
				}
				for (; iCurrent < pageList.getPageSize(); iCurrent++)
				{
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>				
				</tr>
				<%}
				%>
				<tr class="trbottom">
					<!--合计--><!--项-->
                    <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>				
				</tr>
			</table>

			<br>

			<table border="0" width="100%" class="page-link">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>					
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
<%gs_local.remove();%>
