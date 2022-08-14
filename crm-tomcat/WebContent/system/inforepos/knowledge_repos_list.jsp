<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String q_faq_keywords = Utility.trimNull(request.getParameter("q_faq_keywords"));

FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();


//vo.setSerial_no(serial_no);
String[] totalColumn = new String[0];
IPageList pageList = null;

if(q_faq_keywords == null || q_faq_keywords.equals("")){
	pageList = faqs.listByMul(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}else{
	vo.setFaq_keywords(q_faq_keywords);
	vo.setInput_man(input_operatorCode);
	pageList = faqs.searchFaqs(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}

sUrl = sUrl ;

%>

<HTML>
<HEAD>
<TITLE>新增知识库</TITLE>
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
function showInfo(serial_no)
{
	if(showModalDialog('knowledge_update.jsp?serial_no=' + serial_no, '', 'dialogWidth:420px;dialogHeight:300px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function newInfo()
{	
	if(showModalDialog('knowledge_new.jsp', '', 'dialogWidth:420px;dialogHeight:300px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}

function faqSearch()
{	
	var ret = showModalDialog('knowledge_search.jsp', '', 'dialogWidth:340px;dialogHeight:150px;status:0;help:0');
	if(ret != null)
	{
		location = 'knowledge_repos_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&q_faq_keywords=' + ret;
	}
//	if(showModalDialog('knowledge_search.jsp', '', 'dialogWidth:350px;dialogHeight:120px;status:0;help:0') != null)
}

function showInfo1(serial_no){
	
	location = 'knowledge_info.jsp?serial_no='+ serial_no +'&q_faq_keywords=<%=q_faq_keywords%>';
}

function refreshPage()
{
	//disableAllBtn(true);
	
	location = 'knowledge_repos_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value ;
}

function removeInfo(serial_no)
{
	if(!sl_confirm("删除该条记录"))
	     return;
	location = 'knowledge_remove.jsp?serial_no=' + serial_no;
}


//window.onload = function(){
//initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="knowledge_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
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
							<font color="#215dc6"><b>知识库>>知识库维护</b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=f id="search" name="search" onclick="javascript:faqSearch();" <%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
							&nbsp;&nbsp;&nbsp;<!--新建-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
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
						<td width="50px">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);">
							<%=LocalUtilis.language("class.ID",clientLocale)%> 
						</td>
						<!--编号-->
						<td><%=LocalUtilis.language("class.Title",clientLocale)%> </td><!--标题-->
						<td>关键字</td><!--关键字-->
						<td>检索次数</td><!--录入时间-->
						<td>支持次数</td><!--更新时间-->
						<td>反对次数</td><!--录入人-->
						<td width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
						<td width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
	String faq_title = Utility.trimNull(map.get("FAQ_TITLE"));
	String faq_keywords = Utility.trimNull(map.get("FAQ_KEYWORDS"));
%>

					<tr class="tr<%=(iCurrent % 2)%>" style="cursor:hand;">
						<td width="50px" ondblclick="javascript:showInfo1('<%=serial_no%>')">
						  <input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=serial_no%>
						</td>
						<td align="left" ondblclick="javascript:showInfo1('<%=serial_no%>')">&nbsp;&nbsp;<%=faq_title%></td>
						<td align="left" ondblclick="javascript:showInfo1('<%=serial_no%>')">&nbsp;&nbsp;<%=faq_keywords%></td>
						<td align="right" ondblclick="javascript:showInfo1('<%=serial_no%>')">&nbsp;&nbsp;<%=Utility.trimNull(map.get("MATCH_TIMES"))%></td>
						<td align="right" ondblclick="javascript:showInfo1('<%=serial_no%>')">&nbsp;&nbsp;<%=Utility.trimNull(map.get("SUPPORT_TIMES"))%></td>
						<td align="right" ondblclick="javascript:showInfo1('<%=serial_no%>')">&nbsp;&nbsp;<%=Utility.trimNull(map.get("OPPOSE_TIMES"))%></td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=serial_no%>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
							</a>
						<%}%>	
						</td>
						<td align="center">
							<%if(input_operator.hasFunc(menu_id, 101)){%>
							<a href="#" onclick="javascript:removeInfo(<%=serial_no%>)">
								<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
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
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
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
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%faqs.remove();%>

