<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//���ҳ�洫�ݱ���
String operand_no = Utility.trimNull(request.getParameter("operand_no"));
String operand_name = Utility.trimNull(request.getParameter("operand_name"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));

//ҳ�渨������
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;

//��ö��󼰽����
RatingVO vo = new RatingVO();
ManualScoringLocal manual_scoring = EJBFactory.getManualScoring();

vo.setManual_id(new Integer(0));
vo.setOperand_no(operand_no);
vo.setOperand_name(operand_name);
vo.setInput_man(input_man);
IPageList pageList = manual_scoring.pageList_tmanualscoring(vo, totalColumn, t_sPage, t_sPagesize);

sUrl = sUrl+ "&operand_no =" +operand_no + "&operand_name =" +operand_name;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<!--�˹��������-->
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
function showInfo(manual_id)
{
	var url = 'manual_scoring_action.jsp?manual_id=' + manual_id;
	var ret = showModalDialog(url,'', 'dialogWidth:340px;dialogHeight:240px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}

function newInfo()
{	
	var url = "manual_scoring_action.jsp";
	var ret = showModalDialog(url,'', 'dialogWidth:340px;dialogHeight:240px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);

	location = 'manual_scoring.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&operand_no=' + document.theform.operand_no.value
							+ '&operand_name=' + document.theform.operand_name.value;
}
/*ɾ������*/
function removeInfo()
{
	if(checkedCount(document.getElementsByName("manual_id")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ��");//��ѡ��Ҫɾ���ļ�¼
		return false;
	}
	if(sl_check_remove()){			
		var url = 'manual_scoring_remove.jsp';
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
<form name="theform" method="POST" action="manual_scoring.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
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
					<td align="right"><%=LocalUtilis.language("class.operandNumber",clientLocale)%>:</td><!--���������-->
					<td align="left">
						<input type="text" name="operand_no" value="<%=operand_no %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.operandName",clientLocale)%> : </td><!--����������-->
					<td align="left" >
						<input type="text" name="operand_name" value='<%=operand_name%>' onkeydown="javascript:nextKeyPress(this)" size="20">
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
						<td>
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.manual_id,this);">
						<%=LocalUtilis.language("class.operandNumber",clientLocale)%>
						</td><!--���������-->
						<td align="center"><%=LocalUtilis.language("class.operandName",clientLocale)%></td><!--����������-->
						<td align="center"><%=LocalUtilis.language("class.topicValue2",clientLocale)%></td><!--ѡ��ֵ-->
						<td align="center"><%=LocalUtilis.language("class.deValue",clientLocale)%></td><!--�÷�-->
						<td align="center" width="40px"><%=LocalUtilis.language("message.edit",clientLocale)%></td><!--�༭-->
						<td align="center" width="40px"><%=LocalUtilis.language("message.delete",clientLocale)%></td><!--ɾ��-->
					</tr>
<%
//��������
Integer manual_id = new Integer(0);

list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{
	//������
    map = (Map)list.get(i);
	if(map!=null){
	manual_id = Utility.parseInt(Utility.trimNull(map.get("MANUAL_ID")),new Integer(0));
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left">
						  <input type="checkbox" name="manual_id" value="<%=manual_id%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=Utility.trimNull(map.get("OPERAND_NO"))%>
						</td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("OPERAND_NAME")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("OPERATION_VALUE")) %></td>
						<td align="right"><%=Utility.trimNull(map.get("SCORE")) %>&nbsp;</td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=manual_id%>)">
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
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
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
manual_scoring.remove();
%>

