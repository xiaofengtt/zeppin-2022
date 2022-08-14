<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer type_id = Utility.parseInt(request.getParameter("type_id"),new Integer(0));
String type_name = Utility.trimNull(request.getParameter("type_name"));
Integer flag_del = Utility.parseInt(request.getParameter("flag_del"),new Integer(0));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

vo.setType_id(type_id);
vo.setType_name(type_name);

String[] totalColumn = new String[0];
IPageList pageList = dictparam.listDictparamAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl + "&type_id=" + type_id + "&type_name=" + type_name; 
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.parameter",clientLocale)%> </TITLE>
<!--�����ֵ�-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function removeAction(serial_no)
{
	if(!sl_confirm("<%=LocalUtilis.language("message.recordDel",clientLocale)%> ")) return false;//ɾ��������¼
    location = 'parameter_remove.jsp?serial_no='+serial_no;
}

function showInfo(serial_no)
{	
	if(showModalDialog('parameter_info.jsp?first=1&serial_no='+ serial_no, '', 'dialogWidth:600px;dialogHeight:450px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function refreshPage()
{
	disableAllBtn(true);
	waitting.style.visibility='visible';
    location = 'parameter.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&type_id=' + document.theform.type_id.value +'&type_name=' + document.theform.type_name.value;
}

function StartQuery()
{
	refreshPage();
}
window.onload = function(){
initQueryCondition()};
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="parameter_remove.jsp">
<input type="hidden" name="del_flag" value="0">
<div id="queryCondition" class="qcMain" style="display:none;width:300px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.busiName3",clientLocale)%> :</td><!--ҵ�����-->
		<td align="left"><select size="1" name="type_id" style="WIDTH: 200px" onkeydown="javascript:nextKeyPress(this)">
							
							<%=Argument.getAllDictParamOptions(type_id)%>
		
							</select></td>
	</tr>
	<tr>						
         <td align="right"><%=LocalUtilis.language("class.typeName4",clientLocale)%> :</td><!--ҵ������-->
		 <td align="left"><input type="text" name="type_name" onkeydown="javascript:nextKeyPress(this)" size="35" value="<%=type_name%>"></td>      
	</tr>
	
	<tr>
		<td align="center" colspan="2"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--ȷ��-->(<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
			</td>
		</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<%//@ include file="menu.inc" %>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" class="page-title">
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>
					<td valign="bottom" align="right">	
					<div class="btn-wrapper">	
					<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"
						<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>Q</u>)
					</button>&nbsp;&nbsp;&nbsp;&nbsp;
					<%if(input_operator.hasFunc(menu_id,100)){%>	
					<button type="button"  class="xpbutton3" accessKey=n id="btnNew"  name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:showInfo(0);"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
					</button><!--�½���¼--><!--�½�-->
					&nbsp;&nbsp;&nbsp;
					<%}if(input_operator.hasFunc(menu_id,101)){%>
					<button type="button"  class="xpbutton3" accessKey=d id="btnDelete"  name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " 
					        onclick="javascript:if(confirmRemove(document.theform.type_value)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
					</button><!--ɾ����ѡ��¼--><!--ɾ��-->
					<%}%>
					</div>	
					<br/>
					</td>
				</tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trtagsort">
					<td width="14%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.type_value);">
						<%=LocalUtilis.language("class.busiName",clientLocale)%>  &nbsp;</td><!--ҵ�����-->
					<td width="30%"><%=LocalUtilis.language("class.typeName2",clientLocale)%> </td><!--ҵ������-->
					<td width="10%"><%=LocalUtilis.language("message.paraValue",clientLocale)%> </td><!--����ֵ-->
					<td width="*"><%=LocalUtilis.language("class.typeContent",clientLocale)%> </td><!--����˵��-->
					<td width="*">����ֵ1</td><!--����ֵ1-->
					<td width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
					<td width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--ɾ��-->
				</tr>
				<!--�ӽ��������ʾ��¼-->
<%
int iCount = 0;
int iCurrent = 0;
List rsList = pageList.getRsList();
Map rowMap = null;
String type_value = "";
for(int i=0;i<rsList.size();i++)
{
	rowMap = (Map)rsList.get(i);
	String serial_no2 = Utility.trimNull(rowMap.get("SERIAL_NO"));
	String type_id2 = Utility.trimNull(rowMap.get("TYPE_ID"));
	String type_name2 = Utility.trimNull(rowMap.get("TYPE_NAME"));
	type_value = Utility.trimNull(rowMap.get("TYPE_VALUE"));
	String type_content = Utility.trimNull(rowMap.get("TYPE_CONTENT"));
	String additive_value = Utility.trimNull(rowMap.get("ADDITIVE_VALUE"));
%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input type="checkbox" name ="type_value" value="<%=type_value%>" class="flatcheckbox"></td>
							<td width="90%"  align="center"><%=type_id2%></td>
						</tr>
					</table>
					</td>
					<td align="center"><%=type_name2%></td>
					<td align="center"  ><%=type_value%></td>
					<td align="center" ><%=type_content%></td>
					<td align="center" ><%=additive_value%></td>
					<td align="center">
					<%if (input_operator.hasFunc(menu_id, 102)){%>
					<a href="javascript:#" onclick="javascript:showInfo(<%=serial_no2%>);return false; ">
						<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--�༭-->
					</a>
					<%}%>
					</td>
					<td align="center">
					<%if (input_operator.hasFunc(menu_id, 101)){%>
					<a href="javascript:#" onclick="javascript:removeAction('<%=serial_no2%>');return false;">
						<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--ɾ��-->
					</a>
					<%}%>
					</td>
				</tr>
				<!--��ȫ����-->
<%
iCurrent++;
iCount++;
}
for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
				<tr class="tr<%=(i % 2)%>">
					<td class="tdh" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
				</tr>
				<%}
%>
				<tr class="trbottom">	
					<td class="tdh" align="left" colspan="7" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
					<!-- <td  align="center" >-</td>
					<td  align="center" >-</td>
					<td  align="center" >-</td>
					<td  align="center" >-</td>
					<td  align="center" >-</td> -->
				</tr>
			</table>

			

			<table border="0" width="100%" class="page-link">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					<td align="right">
			
					    <input type="hidden" class="xpbutton3" accessKey=r id="btnRefresh" name="btnRefresh" title="<%=LocalUtilis.language("message.currentPageRefrash",clientLocale)%> "
						     onclick="javascript:refreshPage();"/><!--ˢ�µ�ǰҳ��-->
					        <!--ˢ��(<u>R</u>)</button>
					        &nbsp;&nbsp;&nbsp;-->
					    <input type="hidden"class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="<%=LocalUtilis.language("message.backLastPage",clientLocale)%> "
						     onclick="javascript:history.back();"/><!--������һҳ-->
					        <!--����(<u>B</u>)</button>
					        &nbsp;&nbsp;&nbsp;-->
					</td>
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

<%dictparam.remove();%>