<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//页面传递参数
Integer grade_id =Utility.parseInt(request.getParameter("grade_id"), new Integer(0));//评级体系编号
//获得对象
GradeInfoLocal grade_local = EJBFactory.getGradInfo();
GradeInfoVO grade_vo = new GradeInfoVO();
//输入查询参数 
grade_vo.setGrade_id(grade_id);
 //查询信息 
IPageList pageList = grade_local.queryGradAll(grade_vo,1,-1);
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();
String tempUrl = "";
//URL设置  
tempUrl = "&grade_id=" + grade_id;
sUrl = sUrl + tempUrl;
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.launderingriskAssessment",clientLocale)%>>><%=LocalUtilis.language("menu.ratingSystemSet",clientLocale)%>  </TITLE><!--洗钱风险评定--><!--评分体系设置-->
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
function refreshPage(){	
	disableAllBtn(true);
	waitting.style.visibility='visible';
	location = 'client_mark_type_list.jsp?grade_id=' + document.theform.grade_id.value;
}

function StartQuery(){
	refreshPage();
}
/*页面初始化*/
window.onload = function(){
	initQueryCondition();
};
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post">
<%@ include file="/includes/waiting.inc"%>
<div id="queryCondition" class="qcMain" style="display:none;width:200px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   	<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   	<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
	<tr>
		<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!--评分体系-->
		<td><select size="1" name="grade_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
							<%=Argument.getCrmGradeIDOptions(grade_id)%></select></td>
	</tr>
	<tr>
		<td align="center" colspan="4"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;<!--确定-->
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
					<!--查询-->
                    <button type="button"  style="display:none" class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
					<!--button class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="新建记录" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
					&nbsp;&nbsp;&nbsp; 
					<button type="button"  class="xpbutton3" accessKey=d id="btnCancel" name="btnCancel" title="删除所选记录" onclick="javascript:if(confirmRemove(document.theform.projectCatalogId)) {  document.theform.submit();}">删除(<u>D</u>)</button-->
					</div>
					</td>
				</tr>
			</table>
			<br/>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<!--td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.projectCatalogId);"> 序号</td-->
					<td><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
					<td><%=LocalUtilis.language("class.gradeType",clientLocale)%> </td><!--评级类别-->
					<td><%=LocalUtilis.language("class.ratingName",clientLocale)%> </td><!--评级名称-->
				</tr>
				<%int iCount = 0;
				int iCurrent = 0;
				while(it.hasNext())
				{ 
					map = (Map)it.next();
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td align="left" width="20%"><%=Utility.trimNull(map.get("GRADE_BH"))%></td>
					<td align="left" width="40%"><%=Argument.getTintegerparamValue(new Integer(2005),new Integer(String.valueOf(map.get("GRADE_TYPE"))))%></td>
					<td align="left" width="40%"><%=Utility.trimNull(map.get("GRADE_NAME"))%></td>
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
				</tr>
				<%}
				%>				
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
<%grade_local.remove();%>
