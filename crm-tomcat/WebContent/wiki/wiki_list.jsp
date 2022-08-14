<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
String q_faq_keywords = Utility.trimNull(request.getParameter("q_faq_keywords"));
String 	sort_field = Utility.trimNull(request.getParameter("sort_field"),"INPUT_TIME")+ " DESC";
String menu_ids = Utility.trimNull(request.getParameter("menu_id"));
FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();
vo.setWiki_class(menu_ids);
vo.setSearch_key(q_faq_keywords);
vo.setInput_man(input_operatorCode);
vo.setSortfield(sort_field);
String[] totalColumn = new String[0];
IPageList pageList = null;

pageList = faqs.listByMul(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,10));

sUrl += "&menu_id="+menu_ids;
%>
<HTML>
<HEAD>
<TITLE>知识库</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<STYLE type=text/css>
<!--
body,td{font-family:arial}
TD{FONT-SIZE:9pt;LINE-HEIGHT:18px}
.cred{color:#FF0000}
//-->
</STYLE>
<style>
a:link {color: #0066CC;text-decoration: none;}
a:visited {color: #800080;text-decoration: none;}
a:active {text-decoration: none;}
a:hover {color: #8A2BE2;text-decoration: none;}
</style>
<script language=javascript>
window.onload = function(){
	initQueryCondition()
};
function newInfo(serial_no){
	//var ret = showModalDialog('wiki_info_new.jsp?faq_class_no=<%=menu_id%>', '', 'dialogWidth:800px;dialogHeight:600px;status:0;help:0');
	//if(ret != null){
	//	location = 'http://localhost:9080/wiki/wiki_list.jsp?menu_id='+ret+'&first_flag=1&parent_id=&log=0';
	//}
	location = 'wiki_info_new.jsp?faq_class_no=<%=menu_id%>';
}
function showInfo(serial_no,class_no){
	location = 'wiki_info.jsp?serial_no='+ serial_no +'&q_faq_keywords=<%=q_faq_keywords%>&faq_class_no='+class_no;
}
function showByOrder(sort_field)
{	
	location = 'wiki_list.jsp?menu_id=<%=menu_id%>&page=<%=sPage%>&sort_field='+ sort_field +'&pagesize=' + document.theform.pagesize.value ;
}
function refreshPage()
{	
	var q_faq_keywords = document.theform.q_faq_keywords.value;
	location = 'wiki_list.jsp?menu_id=<%=menu_id%>&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&q_faq_keywords='+ q_faq_keywords;
}

function StartQuery()
{
	refreshPage();
}
</script>
</HEAD>
<BODY class="BODY body-nox" leftmargin="0" topmargin="3" marginwidth="0" marginheight="0">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td>搜索关键字：</td>
	   		<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table>						
		<tr>
			</td><td>关键字:</td>
			<td><input type="text" name="q_faq_keywords" onkeydown="javascript:nextKeyPress(this)" size="70" value="<%=q_faq_keywords%>"></td>
		</tr>
		<tr>
			<td align="center" colspan=4>
				<!-- 确定 -->
		    	<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok2",clientLocale)%> (<u>O</u>)</button>
			</td>
		</tr>
	</table>
</div>
<table align="center" width="100%">
	<tr>	
		<td>
		<table align="center" width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td class="page-title" style="border:none;"><b><%=menu_info%></b></td>
			</tr>
			<tr valign="top">
				<td align="right" >
					<div class="btn-wrapper">
					<%if(input_operator.hasFunc(menu_id, 100)){%>
                        <button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title='新建' onclick="javascript:newInfo();">新建 (<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%}%>
                        <button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton" title='搜索' onclick="javascript:disableAllBtn(true);newInfo();">搜索 (<u>Q</u>)</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</td>
			</tr>
		</table>
		<table class="table-list-title"  cellspacing=0 cellpadding=0 border=0 width=100% align="center">
			<tr  height=20>
				<td width=1 bgcolor=#999999>
				<td align="center" nowrap  style="FONT-WEIGHT:bold;COLOR:#ffffff;BACKGROUND-COLOR:#0066CC" width=64>显示列表</td>
				<td align=right bgcolor=#eeeeee>
					<a href="#" onclick="javascript:showByOrder('INPUT_TIME');">时间</a>&nbsp;&nbsp;&nbsp;
					<a href="#" onclick="javascript:showByOrder('POPULARITY_RATING ');">支持率</a>
				</td>
			</tr>
		</table>
		<%
		int iCount = 0;
		int iCurrent = 0;
		List list = pageList.getRsList();
		Map map = null;
		if(list.size()>0){
			for(int i=0;i<list.size();i++)
			{
			    map = (Map)list.get(i);
				Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
				String faq_title = Utility.trimNull(map.get("FAQ_TITLE"));
				String faq_content = Utility.trimNull(map.get("FAQ_CONTENT"));
				String faq_keywords = Utility.trimNull(map.get("FAQ_KEYWORDS"));
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String class_no =  Utility.trimNull(map.get("CLASS_NO"));

				String input_time = Utility.trimNull(map.get("INPUT_TIME"));
				if(input_time.length() > 19){
					input_time = input_time.substring(0,19);
				}
				java.math.BigDecimal popularity_rating  =  Utility.parseDecimal(Utility.trimNull(map.get("POPULARITY_RATING")),new java.math.BigDecimal(0));
				double zero = 0;
		%>
		<br>
		<table class="table-list" width="98%" align="center" cellspacing="0" cellpadding="2" border="0">
			<tr>
			    <td width="50%"><a href="#" onclick="javascript:showInfo('<%=serial_no%>','<%=class_no%>');"><font size=3><%=faq_title%></font></a></td>
				<td width="25%" align="right"><font size=2>
					<%if(popularity_rating.doubleValue() != zero){ %>支持率:<%=popularity_rating %>%<%} %>&nbsp;
					&nbsp;&nbsp;<%=input_time%>
				</font></td>
				<td width="25%">&nbsp;</td>
			</tr>
			<tr>
			    <td width="75%" colspan="2"><font size=2>
					<% 	int max_lenth = 200;
						if(faq_content.length() > max_lenth){
							out.print(faq_content.substring(0,max_lenth)+"....");
						}else{
							out.print(faq_content);	
						}
					%>	
					</font>
			    </td>
				<td width="25%">&nbsp;</td>	
			</tr>
			<tr>
			    <td width="75%" colspan="2">
					<a href="#" onclick="javascript:showInfo('<%=serial_no%>','<%=class_no%>');">阅读全文</a>&nbsp;&nbsp;|&nbsp;&nbsp;
					关键字:&nbsp;<font color="red"><%=faq_keywords %></font></td>
				<td width="25%">&nbsp;</td>	
			</tr>
		</table>
		<%	}
		}else{ %>
		<p align='left'><font size=-1> 抱歉，未找到任何符合您查询条件的信息.</font></p>	
		<%} %>
		<hr size="1" width="100%" color="#0066CC">
		<table border="0" width="100%" class="page-link">
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>