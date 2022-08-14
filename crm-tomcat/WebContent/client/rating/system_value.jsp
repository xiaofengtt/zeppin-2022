<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得页面传递变量
Integer operand_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_id")),new Integer(0));
String operand_xh = Utility.trimNull(request.getParameter("operand_xh"));
String source_table = Utility.trimNull(request.getParameter("source_table"));
String source_field = Utility.trimNull(request.getParameter("source_field"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));

//页面辅助参数
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;

//获得对象及结果集
RatingVO vo = new RatingVO();
SystemValueLocal system_value = EJBFactory.getSystemValue();

vo.setOperand_v_id(new Integer(0));
vo.setOperand_id(operand_id);
vo.setOperand_xh(operand_xh);
vo.setSource_table(source_table);
vo.setSource_field(source_field);
vo.setInput_man(input_man);
vo.setCust_type(cust_type);
IPageList pageList = system_value.pageList_tsystemvalue(vo, totalColumn, t_sPage, t_sPagesize);

sUrl = sUrl+ "&operand_id =" +operand_id+ "&operand_xh =" +operand_xh
		+ "&source_table =" +source_table+ "&source_field =" +source_field+"&cust_type="+cust_type;
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<!--科目分值设置-->
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

<script language=javascript>
function showInfo(operand_v_id)
{
	var url = 'system_value_action.jsp?operand_v_id=' + operand_v_id;
	window.location = url;
}

function newInfo()
{	
	var url = "system_value_action.jsp";
	window.location = url;
}

//系统打分条件定义
function conditionSet(operand_v_id)
{
	var url = 'system_condition_action.jsp?operand_v_id=' + operand_v_id;
	window.location = url;
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);

	location = 'system_value.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&operand_id=' + document.theform.operand_id.value
							+ '&operand_xh=' + document.theform.operand_xh.value
							+ '&source_table=' + document.theform.source_table.value
							+ '&source_field=' + document.theform.source_field.value
							+ '&cust_type='+document.theform.cust_type.value;
}
/*删除功能*/
function removeInfo()
{
	if(checkedCount(document.getElementsByName("operand_v_id")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	if(sl_check_remove()){			
		var url = 'system_value_remove.jsp';
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	return false;
}


window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="system_value.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:460px;">
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
					<td align="right"><%=LocalUtilis.language("class.operandName",clientLocale)%>:</td><!--操作数-->
					<td align="left">
						<select name="operand_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getOperandOptions(operand_id,new Integer(2),new Integer(0))%>
						</select>
					</td>
					<td align="right"><%=LocalUtilis.language("class.serialNumber",clientLocale)%>:</td><!--序号-->
					<td align="left">
						<input type="text" name="operand_xh" value="<%=operand_xh %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%>:</td><!--数据来源表-->
					<td align="left">
						<input type="text" name="source_table" value="<%=source_table %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.dataSourceField",clientLocale)%> : </td><!--数据来源字段-->
					<td align="left" >
						<input type="text" name="source_field" value='<%=source_field%>' onkeydown="javascript:nextKeyPress(this)" size="20">
					</td>
				</tr>
				<tr>
					<td align="right">客户类型:</td><!--操作数-->
					<td align="left">
						<select name="cust_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getCustTypeOptions(cust_type)%>
						</select>
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
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
							&nbsp;&nbsp;&nbsp;<!--新建-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:removeInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--删除所选记录--><!--删除-->
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
						<td align="center">
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.operand_v_id,this);">
						<%=LocalUtilis.language("class.serialNumber",clientLocale)%></td><!--编号-->
						<td align="center">操作数名称</td><!--数据来源表-->
						<td align="center"><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%></td><!--数据来源表-->
						<td align="center"><%=LocalUtilis.language("class.dataSourceField",clientLocale)%></td><!--数据来源字段-->
						<td align="center"><%=LocalUtilis.language("class.topCriticalValue",clientLocale)%></td><!--头临界值-->
						<td align="center"><%=LocalUtilis.language("class.endCriticalValue",clientLocale)%></td><!--尾临界值-->
						<td align="center">数据值</td><!--为真取值-->
						<!--<td align="center"><%=LocalUtilis.language("class.conditionSet",clientLocale)%></td>条件设置-->
						<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
						<td align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
					</tr>
<%
//声明参数
Integer operand_v_id = new Integer(0);

list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{

	//读数据
    map = (Map)list.get(i);
	if(map!=null){
	operand_v_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_V_ID")),new Integer(0));
	String end_threshold = Utility.trimNull(map.get("END_THRESHOLD"));
	if("999999999999.000".equals(end_threshold))
		end_threshold = "";
%>
					<tr class="tr<%=(iCount % 2)%>">
						<td align="left">
						  <input type="checkbox" name="operand_v_id" value="<%=operand_v_id%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=Utility.trimNull(map.get("OPERAND_XH"))%>
						</td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("OPERAND_NAME")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("SOURCE_TABLE_CN")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("SOURCE_FIELD_CN")) %></td>
						<td align="right"><%=Utility.parseDecimal(Utility.trimNull(map.get("TOP_THRESHOLD")),new BigDecimal(0))%>&nbsp;</td>
						<td align="right"><%=Utility.trimNull(end_threshold)%>&nbsp;</td>
						<td align="center"><%=Utility.trimNull(map.get("TRUE_VALUE")) %></td>
						<!--
						<td align="center">
							<a href="javascript:#" onclick="javascript:conditionSet(<%=operand_v_id%>);return false; ">
								<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--联系人--
							</a>
						</td>
						-->
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=operand_v_id%>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
							</a>
						<%}%>	
						</td>
						<td align="center">
							<%if(input_operator.hasFunc(menu_id, 101)){%>
							<a href="#" onclick="javascript:removeInfo()">
								<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
							</a>
							<%}%>
						</td>
					</tr>
<%
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
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
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
system_value.remove();
%>

