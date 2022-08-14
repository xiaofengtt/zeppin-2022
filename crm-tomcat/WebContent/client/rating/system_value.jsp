<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//���ҳ�洫�ݱ���
Integer operand_id = Utility.parseInt(Utility.trimNull(request.getParameter("operand_id")),new Integer(0));
String operand_xh = Utility.trimNull(request.getParameter("operand_xh"));
String source_table = Utility.trimNull(request.getParameter("source_table"));
String source_field = Utility.trimNull(request.getParameter("source_field"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));

//ҳ�渨������
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;

//��ö��󼰽����
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
<!--��Ŀ��ֵ����-->
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

//ϵͳ�����������
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
/*ɾ������*/
function removeInfo()
{
	if(checkedCount(document.getElementsByName("operand_v_id")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ��");//��ѡ��Ҫɾ���ļ�¼
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
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   					<td align="right">
   						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- Ҫ����Ĳ�ѯ���� -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right"><%=LocalUtilis.language("class.operandName",clientLocale)%>:</td><!--������-->
					<td align="left">
						<select name="operand_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getOperandOptions(operand_id,new Integer(2),new Integer(0))%>
						</select>
					</td>
					<td align="right"><%=LocalUtilis.language("class.serialNumber",clientLocale)%>:</td><!--���-->
					<td align="left">
						<input type="text" name="operand_xh" value="<%=operand_xh %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%>:</td><!--������Դ��-->
					<td align="left">
						<input type="text" name="source_table" value="<%=source_table %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.dataSourceField",clientLocale)%> : </td><!--������Դ�ֶ�-->
					<td align="left" >
						<input type="text" name="source_field" value='<%=source_field%>' onkeydown="javascript:nextKeyPress(this)" size="20">
					</td>
				</tr>
				<tr>
					<td align="right">�ͻ�����:</td><!--������-->
					<td align="left">
						<select name="cust_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getCustTypeOptions(cust_type)%>
						</select>
					</td>
				</tr>	
				<tr>
					<td align="center" colspan="4">
						<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--ȷ��-->
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
							&nbsp;&nbsp;&nbsp;<!--��ѯ-->
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--�½���¼-->
							&nbsp;&nbsp;&nbsp;<!--�½�-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:removeInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--ɾ����ѡ��¼--><!--ɾ��-->
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
						<%=LocalUtilis.language("class.serialNumber",clientLocale)%></td><!--���-->
						<td align="center">����������</td><!--������Դ��-->
						<td align="center"><%=LocalUtilis.language("class.dataSourceTable",clientLocale)%></td><!--������Դ��-->
						<td align="center"><%=LocalUtilis.language("class.dataSourceField",clientLocale)%></td><!--������Դ�ֶ�-->
						<td align="center"><%=LocalUtilis.language("class.topCriticalValue",clientLocale)%></td><!--ͷ�ٽ�ֵ-->
						<td align="center"><%=LocalUtilis.language("class.endCriticalValue",clientLocale)%></td><!--β�ٽ�ֵ-->
						<td align="center">����ֵ</td><!--Ϊ��ȡֵ-->
						<!--<td align="center"><%=LocalUtilis.language("class.conditionSet",clientLocale)%></td>��������-->
						<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
						<td align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--ɾ��-->
					</tr>
<%
//��������
Integer operand_v_id = new Integer(0);

list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{

	//������
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
								<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--��ϵ��--
							</a>
						</td>
						-->
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=operand_v_id%>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--�༭-->
							</a>
						<%}%>	
						</td>
						<td align="center">
							<%if(input_operator.hasFunc(menu_id, 101)){%>
							<a href="#" onclick="javascript:removeInfo()">
								<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--ɾ��-->
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
						<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
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
