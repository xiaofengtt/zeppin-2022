<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String enfo_crm_wiki_q = Utility.trimNull(request.getParameter("enfo_crm_wiki_q"));
Integer selStatus = Utility.parseInt(request.getParameter("selStatus"),new Integer(0));
Integer openflag = new Integer(0);
Integer product_status = new Integer(0);
product_status = selStatus;

String class_type1 = Utility.trimNull(request.getParameter("selClass1"));
Integer pre_date1 = Utility.parseInt(request.getParameter("pre_date1"),new Integer(0));
Integer pre_date2 = Utility.parseInt(request.getParameter("pre_date2"),new Integer(0));
Integer prestart_date1 = Utility.parseInt(request.getParameter("prestart_date1"),new Integer(0));
Integer prestart_date2 = Utility.parseInt(request.getParameter("prestart_date2"),new Integer(0));
Integer include_product = Utility.parseInt(request.getParameter("chkProduct"),new Integer(0));
Integer include_wiki = Utility.parseInt(request.getParameter("chkWiki"),new Integer(0));
String sortfield = Utility.trimNull(request.getParameter("sortfield"));
String wiki_class = Utility.trimNull(request.getParameter("wiki_class"));

String[] totalColumn = new String[0];
WikiSearchVO vo = new WikiSearchVO();
ProductInfoReposLocal wikiRepos = EJBFactory.getProductInfoRepos();

vo.setSearchkey(enfo_crm_wiki_q);
vo.setInput_man(input_operatorCode);
vo.setInclude_product(include_product);
vo.setInclude_wiki(include_wiki);
vo.setProduct_status(product_status);
vo.setOpenflag(openflag);
vo.setClass_type1(class_type1);
vo.setPre_date1(pre_date1);
vo.setPre_date2(pre_date2);
vo.setPrestart_date1(prestart_date1);
vo.setPrestart_date2(prestart_date2);
vo.setWiki_class(wiki_class);
vo.setSortfield(sortfield);

IPageList pageList = wikiRepos.searchAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl + "&enfo_crm_wiki_q="+enfo_crm_wiki_q
			+ "&selStatus="+selStatus
			+ "&selClass1="+class_type1
			+ "&pre_date1="+pre_date1
			+ "&pre_date2="+pre_date2
			+ "&prestart_date1="+prestart_date1
			+ "&prestart_date2="+prestart_date2
			+ "&chkProduct="+include_product
			+ "&chkWiki="+include_wiki;
String sSortUrl = sUrl;
String sClassUrl = sUrl;
if (!"".equals(sortfield)) {
	sUrl = sUrl + "&sortfield="+sortfield;
	sClassUrl = sUrl;
	sSortUrl = sSortUrl + "&sortfield=";
} else {
	sSortUrl = sSortUrl + "&sortfield=INPUT_TIME DESC";
}
if (!"".equals(wiki_class)) {
	sUrl = sUrl + "&wiki_class="+wiki_class;
	sSortUrl = sUrl;
}
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<style type="text/css">
<!--
a:link {color: #0066CC;text-decoration: none;}
a:visited {color: #800080;text-decoration: none;}
a:active {text-decoration: none;}
a:hover {color: #8A2BE2;text-decoration: none;}
-->
</style>	
</head>
<script language="javascript">
	function refreshPage()
{
	
	var url = 'search_result.jsp?pagesize='+ document.getElementsByName('pagesize')[0].value
			+'&enfo_crm_wiki_q=<%=enfo_crm_wiki_q%>'
			+'&selStatus=<%=selStatus%>'
			+'&selClass1=<%=class_type1%>'
			+'&pre_date1=<%=pre_date1%>'
			+'&pre_date2=<%=pre_date2%>'
			+'&prestart_date1=<%=prestart_date1%>'
			+'&prestart_date2=<%=prestart_date2%>'
			+'&chkProduct=<%=include_product%>'
			+'&chkWiki=<%=include_wiki%>'
			+'&page=1';
	location = url;
}


function newInfo(serial_no){
	var ret = showModalDialog('wiki_info_new.jsp', '', 'dialogWidth:800px;dialogHeight:600px;status:0;help:0');
	if(ret != null){
		location = 'http://localhost:9080/wiki/wiki_list.jsp?menu_id='+ret+'&first_flag=1&parent_id=&log=0';
	}
}
</script>
<body class="BODY" topmargin="0" leftmargin="0" rightmargin="0" >
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="96%" align="center" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr height="30">
							<td colspan="2" align="left"></td>
							<td align="right"><%if(input_operator.hasFunc("W0501", 100)){%>	
								<button type="button"  class="xpbutton6" accessKey=n id="btnNew" style="" name="btnNew" title='新建' onclick="javascript:newInfo();">新建知识库文章</button>
								<%}%></td>				
						</tr>
					</table>
					<table cellspacing=0 cellpadding=0 border=0 width=100% align="center">
						<tr>
							<td colspan="3"><br></td>
						</tr>
						<tr  height=20>
							<td class="page-title" >显示列表</td>
							<td align=right style="position:relative; font-size:14px; color:#1466b8; border-bottom:2px solid #197fe6; padding:10px 15px;" >
								<a href="<%=sSortUrl%>">时间排序</a>&nbsp;
							</td>
						</tr>
					</table>
					<table border="0" width="100%" align="center">
						<tr valign="top">
							<td>
							</td>
							<td align="right">&nbsp;</td>
						</tr>
				<%
					String s_view_item = "";
					List rsList = pageList.getRsList();
					Map rowMap = null;
					if(rsList.size()>0){
						for(int i=0;i<rsList.size();i++)
						{ 
							rowMap = (Map)rsList.get(i);
							if (Utility.trimNull(rowMap.get("DATA_TYPE")).equals("3")) {
								s_view_item = "/wiki/wiki_info.jsp?serial_no="+Utility.trimNull(rowMap.get("FAQ_ID"));
							} else {
								s_view_item = "/wiki/product_info.jsp?preproduct_id="+ Utility.trimNull(rowMap.get("PREPRODUCT_ID")) +"&product_id="+ Utility.trimNull(rowMap.get("PRODUCT_ID"));
							}
					%>
							<tr valign="top">
								<td><a href="<%=s_view_item%>"><B><%=Utility.trimNull(rowMap.get("TITLE"))%></B></a>
					<%
							if (Utility.trimNull(rowMap.get("DATA_TYPE")).equals("3")) {
					%>
								&nbsp;&nbsp;<a href="<%=sClassUrl+"&wiki_class="+Utility.trimNull(rowMap.get("CLASS_NO"))%>"><FONT color="#666666"><%=Utility.trimNull(rowMap.get("CLASS_NAME"))%></FONT></a>
					<%
							}
					%>
								</td>
								<td align="right"><%=Utility.trimNull(rowMap.get("STATUS_NAME"))%></td>
							</tr>
							<tr valign="top">
								<td colspan="2"><BLOCKQUOTE dir="ltr"><FONT color="#888888"><%=Utility.trimNull(rowMap.get("BRIEF_SUMMARY"))%></FONT></BLOCKQUOTE></td>
							</tr>
					<%
						}
					}else{
				%>
					<tr height="30">
						<td colspan="2"><p align='left'><font size=-1> 抱歉，未找到任何符合您查询条件的信息.</font></p></td>
					</tr>
				<%}%>
					</table>
					<hr size="1" width="100%" color="#0066CC">
					<table border="0" width="100%" align="center">
						<tr valign="top">
							<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
								<td align="right"></td>
						</tr>
					</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</body>
</html>
<%wikiRepos.remove();
%>