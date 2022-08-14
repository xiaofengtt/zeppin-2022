<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String product_code= Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),null);
Integer start_date2=Utility.parseInt(request.getParameter("start_date2"),null);
BigDecimal fact_money = Utility.parseDecimal(request.getParameter("fact_money"),null);
BigDecimal fact_money2 = Utility.parseDecimal(request.getParameter("fact_money2"),null);
String summary = Utility.trimNull(request.getParameter("summary"));

//状态参数
Integer product_status1 = Utility.parseInt(request.getParameter("status"),null);
Integer open_flag = Utility.parseInt(request.getParameter("open"),null);
String class_type1 = Utility.trimNull(request.getParameter("class1"));
String class1 = Utility.trimNull(request.getParameter("class1"));
String searchkey = Utility.trimNull(request.getParameter("searchkey"));
String sortfield = Utility.trimNull(request.getParameter("sortfield"));


String[] totalColumn = new String[0];
ProductInfoReposLocal product = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();

vo.setProduct_code(product_code);
vo.setProduct_name(product_name);
vo.setClass_type1(class_type1);
vo.setStart_date(start_date);
vo.setStart_date2(start_date2);
vo.setFact_money(fact_money);
vo.setFact_money2(fact_money2);
vo.setSummary(summary);
vo.setInput_man(input_operatorCode);
vo.setProduct_status1(product_status1);
vo.setOpen_flag(open_flag);
vo.setSearchkey(searchkey);
vo.setSortfield(sortfield);

IPageList pageList = product.productInforeposList(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+"&status="+product_status1
					+"&class1="+class_type1
					+"&class_type1="+class_type1
					+"&open="+open_flag;

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<style>
a:link {color: #0066CC;text-decoration: none;}
a:visited {color: #800080;text-decoration: none;}
a:active {text-decoration: none;}
a:hover {color: #8A2BE2;text-decoration: none;}
</style>
<script language=javascript>
function refreshPage()
{
	var url ="product_list.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&status=<%=product_status1%>"
					+"&class1=<%=class_type1%>"
					+"&open=<%=open_flag%>";
	location = url;
}

function StartQuery()
{
	refreshPage();
}

function newInfo()
{	
	location = 'product_new.jsp?class1=<%=class_type1%>'+'&class_type1=<%=class_type1%>'+'&status=<%=product_status1%>'+'&open=<%=open_flag%>';
}

function searchproduct(){
	if(document.theform.searchkey.value == ''){
		sl_alert("请输入内容进行查询");
	}else{
		location = 'product_list.jsp?class1=<%=class_type1%>'+'&class_type1=<%=class_type1%>'+'&status=<%=product_status1%>'+'&open=<%=open_flag%>&searchkey='+document.theform.searchkey.value;
	}
}

function sortMember(){
	var sortfield = '';
	<%if(sortfield.equals("")){%>
		sortfield = 'EXPRE_START_DATE,PRE_START_DATE';
	<%}else{%>
	<%}%>
	location = 'product_list.jsp?class1=<%=class_type1%>'+'&class_type1=<%=class_type1%>'+'&status=<%=product_status1%>'+'&open=<%=open_flag%>&sortfield='+sortfield+'&searchkey='+document.theform.searchkey.value;;
}
</script>

</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="sortfield" value="<%=sortfield%>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" align="center" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="3"><br></td>
					</tr>
					<tr height="30">
						<td colspan="2" align="left"><input type="text" style="width:300px;height:21px" class="top-search-input" name="searchkey" value="<%=searchkey%>"><a href="#" onclick="searchproduct()" id="Support" class="top-search"  title="搜索">
						<img src="<%=request.getContextPath()%>/images/find1.png" /></a></td>
						<td class="btn-wrapper"><%if(input_operator.hasFunc(menu_id, 100)){%>
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" onclick="javascript:newInfo();" >新建 (<u>N</u>)</button>
							<%}%></td>				
					</tr>
					<tr><td  colspan="3" >&nbsp;&nbsp;&nbsp; </td></tr>
				</table>
				<table class="table-list-title" cellspacing=0 cellpadding=0 border=0 width=100% align="center">
					<tr  height=20>
						<td width=1 bgcolor=#999999>
						<td align="center" nowrap  style="FONT-WEIGHT:bold;COLOR:#ffffff;BACKGROUND-COLOR:#0066CC" width=64>显示列表</td>
						<td align=right bgcolor=#eeeeee>
							排序：<a href="#" onclick="sortMember()" title="预约时间"><b>预约时间</b></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<br>
				<table id="table3" class="table-list" border="0" cellspacing="1" cellpadding="2" width="100%">
<%
List rsList = pageList.getRsList();
Map rowMap = null;
if(rsList.size() > 0){
	for(int i=0;i<rsList.size();i++)
	{ 
		rowMap = (Map)rsList.get(i);
		Integer product_id = Utility.parseInt(Utility.trimNull(rowMap.get("PRODUCT_ID")),null);
		product_code = Utility.trimNull(rowMap.get("PRODUCT_CODE"));
		product_name = Utility.trimNull(rowMap.get("PRODUCT_NAME"));
		fact_money= (BigDecimal)rowMap.get("FACT_MONEY");
		String intrust_type_name = Utility.trimNull(rowMap.get("INTRUST_TYPE_NAME"));
		BigDecimal pre_money = (BigDecimal)rowMap.get("PRE_MONEY");
		class_type1 = Utility.trimNull(rowMap.get("CLASS_TYPE1"));
		String brief_summary = Utility.trimNull(rowMap.get("BRIEF_SUMMARY"));
		Integer preproduct_id = Utility.parseInt(Utility.trimNull(rowMap.get("PREPRODUCT_ID")),null);
		
	%>
	           
		<tr>
			<td><a href="product_info.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&status=<%=product_status1%>&open=<%=open_flag%>&class1=<%=class1%>&page=<%=sPage%>&pagesize=<%=sPagesize%>" onclick="" id="Support"  title="<%=product_name%>"><b><%=product_name%></b></a></td>
			<td align="right"><%=Utility.trimNull(rowMap.get("CLASS_TYPE1_NAME"))%>&nbsp;&nbsp;<%=Utility.trimNull(rowMap.get("STATUS_NAME"))%></td>
		</tr>
		<tr>
			<td colspan="2"><FONT color="#666666"><%=brief_summary%></FONT></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
	
	<%
	}
}else{
%>	<tr height="30">
		<td><p align='left'><font size=-1> 抱歉，未找到任何符合您查询条件的信息.</font></p></td>
	</tr>
<%} %>
		</table>
		<hr size="1" width="100%" color="#0066CC">
		<table class="page-link" border="0" width="98%">
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
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%product.remove();
%>
