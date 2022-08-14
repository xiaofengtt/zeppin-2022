<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String define_name = Utility.trimNull(request.getParameter("define_name"));
String detail_name = Utility.trimNull(request.getParameter("detail_name"));

CustomerClassLocal local = EJBFactory.getCustomerClass();
CustomerClassVO vo = new CustomerClassVO();

vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setClass_define_name(define_name);
vo.setClass_detail_name(detail_name);
vo.setInput_man(input_operatorCode);

IPageList pageList = local.queryNotCheckClass(vo, Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();
String tempUrl = "";

tempUrl = "&cust_no=" + cust_no + "&cust_name=" + cust_name + "&define_name=" + define_name + "&detail_name=" + detail_name;
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
//查询客户评级信息
function refreshPage()
{	
	disableAllBtn(true);
	waitting.style.visibility='visible';
	location = 'client_class_check_list.jsp?page=<%=sPage%>&pagesize='+ document.theform.pagesize.value 
				+'&cust_no=' + document.theform.cust_no.value
				+'&cust_name=' + document.theform.cust_name.value
				+'&define_name=' + document.theform.define_name.value
				+'&detail_name=' + document.theform.detail_name.value;
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

//客户评级审核
function check_grade(check_flag)
{
	//check_flag标志表示审核通过或不通过
	//判断有无选择项
	var selcount = checkedCount(document.theform.serial_no);
	if (selcount > 0)
	{
		if(check_flag=="2"){
			if(sl_confirm('<%=LocalUtilis.language("message.pass",clientLocale)%> ')){//审核通过
				disableAllBtn(true);
				document.getElementById("check_flag").value="2";
				document.theform.submit();
			}
		}else if(check_flag=="1"){
			if(sl_confirm('<%=LocalUtilis.language("message.notPass2",clientLocale)%> ')){//审核不通过
				disableAllBtn(true);
				document.getElementById("check_flag").value="1";
				document.theform.submit();
			}
		}
	}
	else
		if(check_flag=="2")
			sl_alert('<%=LocalUtilis.language("message.recordsPassTip",clientLocale)%> ！');//请选择要审核通过的记录
		else if(check_flag=="1")
			sl_alert('<%=LocalUtilis.language("message.recordsNotPass",clientLocale)%> ！');//请选择要审核不通过的记录
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="client_class_check.jsp">
<input type="hidden" name="check_flag" id="check_flag">
<%@ include file="/includes/waiting.inc"%>
<div id="queryCondition" class="qcMain" style="display:none;width:530px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
		<td valign="bottom" align="left">
			<input name="cust_no" value='<%=cust_no%>' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left">
			<input name="cust_name" value='<%=cust_name%>' onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="100">
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.ratingDefinition",clientLocale)%> :</td><!--评级定义-->
		<td valign="bottom" align="left">
			<input name="define_name" value='<%=define_name%>' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.ratingDetails",clientLocale)%> :</td><!--评级明细-->
		<td valign="bottom" align="left">
			<input name="detail_name" value='<%=detail_name%>' onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="100">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;<!--确定-->
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
                    <button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
                    <!-- 审核通过 -->
					<button type="button"  class="xpbutton3"  id="btnPass" name="btnPass" title='<%=LocalUtilis.language("message.pass",clientLocale)%>' onclick="javascript:check_grade('2');"><%=LocalUtilis.language("message.pass",clientLocale)%> </button>
					&nbsp;&nbsp;&nbsp;
                    <!-- 审核不通过 -->
					<button type="button"  class="xpbutton3"  id="btnUnPass" name="btnUnPass" title='<%=LocalUtilis.language("message.notPass2",clientLocale)%>' onclick="javascript:check_grade('1');"><%=LocalUtilis.language("message.notPass2",clientLocale)%> </button>
					</div>
					</td>
				</tr>
			</table>
			<br/>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<!--客户编号-->
                    <td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);"> <%=LocalUtilis.language("class.customerID",clientLocale)%> </td>
					<td><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
					<td><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
					<td><%=LocalUtilis.language("class.ratingDefinition",clientLocale)%> </td><!--评级定义-->
					<td><%=LocalUtilis.language("class.ratingDetails",clientLocale)%> </td><!--评级明细-->					
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
							<td width="10%" >
							<%if(map.get("CLASSDETAIL_ID") != null){%>
							<input type="checkbox" name="serial_no" value="<%=map.get("CUST_ID")+"#"+map.get("CLASSDEFINE_ID")+"#"+map.get("CLASSDETAIL_ID")%>" class="flatcheckbox">
							<%}else{%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%}%></td>
							<td width="90%" align="left"><%=map.get("CUST_NO")%></td>							
						</tr>
					</table>
					</td>
					<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("CLASSDEFINE_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("CLASSDETAIL_NAME"))%></td>	
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
				</tr>
				<%}
				%>
				<tr class="trbottom">
					<!--合计--><!--项-->
                    <td class="tdh" align="left" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>			
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
<%local.remove();%>
