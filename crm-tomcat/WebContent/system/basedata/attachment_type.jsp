<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer attachment_type_id = Utility.parseInt(request.getParameter("attachment_type_id"),new Integer(0));
String attachment_type_name = Utility.trimNull(request.getParameter("attachment_type_name"));
Integer df_table_id = Utility.parseInt(request.getParameter("df_table_id"),new Integer(0));

AttachmentTypeVO vo = new AttachmentTypeVO();
AttachmentTypeLocal attachment_type = EJBFactory.getAttachmentType();
vo.setAttachmentType_name(attachment_type_name);
vo.setAttachmentType_id(Utility.parseInt(attachment_type_id,new Integer(0)));
vo.setDF_Table_id(df_table_id);
String[] totalColumn = new String[0];
IPageList pageList = attachment_type.listByMul(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&attachment_type_id=" +attachment_type_id+"&attachment_type_name="+attachment_type_name + "&df_table_id=" + df_table_id;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.attachment_typeSet",clientLocale)%> </TITLE>
<!--�����������-->
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
function showInfo(attachment_type_id)
{
	if(showModalDialog('attachment_type_update.jsp?attachment_type_id=' + attachment_type_id, '', 'dialogWidth:320px;dialogHeight:280px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function newInfo()
{	
	if(showModalDialog('attachment_type_new.jsp', '', 'dialogWidth:290px;dialogHeight:240px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);
	
	location = 'attachment_type.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
	+ "&attachment_type_name=" + $$("attachment_type_name").getAttribute("value") 
	+ "&df_table_id=" + $$("df_table_id").getAttribute("value"); 
}

function removeInfo(attachment_type_id)
{
	if(!sl_confirm("<%=LocalUtilis.language("message.delAttachmentTypeConfirm",clientLocale)%> "))//�ò�����ɾ���������Ҫ����
	     return;
	location = 'attachment_type_remove.jsp?attachment_type_id=' + attachment_type_id;
}


window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY body-nox" >
<form name="theform" method="POST" action="attachment_type_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:280px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   					<td align="right">
   						<button type="button"  class="qcClose"  accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- Ҫ����Ĳ�ѯ���� -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right"><%=LocalUtilis.language("class.attachmentTypeName",clientLocale)%> :</td><!--�����������-->
					<td><input type="text" name="attachment_type_name" size="25" value="<%=attachment_type_name%>" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
<%

	String value;
	if(Utility.trimNull(df_table_id).length() == 1) {
		value="0"+Utility.trimNull(df_table_id);
	}	
	else {
		value=Utility.trimNull(df_table_id);
	}
 %>
				<tr>
					<td align="right" noWrap><%=LocalUtilis.language("class.dfTable",clientLocale)%> :</td><!--��Ӧ��--> 
					<td><select size="1" name="df_table_id" onkeydown="javascript:nextKeyPress(this)" style="width: 150px">
						<%=Argument.getAttachmentTypeValue(value)%>
					</select></td>				
				</tr>
				<tr>
					<td align="center" colspan="2">
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
						<td class="page-title">			
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--��ѯ-->
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button"  class="xpbutton3" accessKey=n  id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--�½���¼-->
							&nbsp;&nbsp;&nbsp;<!--�½�-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button"  class="xpbutton3" accessKey=d id="btnDelete"  name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.attachment_type_id)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--ɾ����ѡ��¼--><!--ɾ��-->
						<%}%>
						</div>
						<br/>
						</td>
						
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td width="50px">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.attachment_type_id);">
							<%=LocalUtilis.language("class.ID",clientLocale)%> 
						</td><!--���-->
						<td><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--����-->
						<td><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--��ע-->
						<td><%=LocalUtilis.language("class.dfTable",clientLocale)%> </td><!--��Ӧ����-->
						<td width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
						<td width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--ɾ��-->
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	attachment_type_id = Utility.parseInt(Utility.trimNull(map.get("ATTACHMENT_TYPE_ID")),null);
	attachment_type_name = Utility.trimNull(map.get("ATTACHMENT_TYPE_NAME"));
	String attachment_type_summary = Utility.trimNull(map.get("ATTACHMENT_TYPE_SUMMARY"));
	String table_name = Utility.trimNull(map.get("DF_TABLE_NAME"));
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="50px">
						  <input type="checkbox" name="attachment_type_id" value="<%=attachment_type_id%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=attachment_type_id %>
						</td>
						<td align="left">&nbsp;&nbsp;<%=attachment_type_name%></td>
						<td align="left">&nbsp;&nbsp;<%=attachment_type_summary%></td>
						<td align="left">&nbsp;&nbsp;<%=table_name%></td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=attachment_type_id%>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--�༭-->
							</a>
						<%}%>	
						</td>
						<td align="center">
							<%if(input_operator.hasFunc(menu_id, 101)){%>
							<a href="#" onclick="javascript:removeInfo(<%=attachment_type_id%>)">
								<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--ɾ��-->
							</a>
							<%}%>
						</td>
					</tr>
<%
iCurrent++;
iCount++;
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
						<td class="tdh" align="left" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
						<td></td>
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
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%attachment_type.remove();%>

