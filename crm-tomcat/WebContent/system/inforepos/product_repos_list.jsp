<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String product_code= Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
String class_type1 = Utility.trimNull(request.getParameter("class_type1"));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),null);
Integer start_date2=Utility.parseInt(request.getParameter("start_date2"),null);
BigDecimal fact_money = Utility.parseDecimal(request.getParameter("fact_money"),null);
BigDecimal fact_money2 = Utility.parseDecimal(request.getParameter("fact_money2"),null);
String summary = Utility.trimNull(request.getParameter("summary"));


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


IPageList pageList = product.productInforeposList(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+"&product_code="+product_code
					+"&product_name="+product_name
					+"&class_type1="+class_type1
					+"&start_date="+start_date
					+"&setStart_date2="+start_date2
					+"&fact_money="+fact_money
					+"&fact_money2="+fact_money2
					+"&summary="+summary;
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
<script language=javascript>
function showInfo(product_id)
{	
	location = 'product_query_info.jsp?product_id=' + product_id;
}


function editInfo(product_id)
{	
	location = 'product_task_list.jsp?product_id=' + product_id;
}

function showManager(product_id)
{
	if(showModalDialog('product_manager_update.jsp?product_id=' + product_id,'','dialogWidth:360px;dialogHeight:300px;status:0;help:0')!=null)
	{	
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ");//保存成功
		location.reload();
	}
}
function refreshPage()
{
	
	var url ="product_repos_list.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&product_code=<%=product_code%>"
					+"&class_type1=<%=class_type1%>"
					+"&product_name=<%=product_name%>"
					+"&start_date=<%=start_date%>"
					+"&start_date2=<%=start_date2%>"
					+"&fact_money=<%=fact_money%>"
					+"&fact_money2=<%=fact_money2%>"
					+'&summary=<%=summary%>';
					
	if(document.theform.intrust_flag1){
		url = url + "&intrust_flag1="+document.theform.intrust_flag1.value;
	}
	location = url

}

function StartQuery()
{
	refreshPage();
}

function openQuery(product_id,item_id)
{	
	showModalDialog('product_list_detail.jsp?product_id='+product_id+'&item_id='+item_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}

function setTextEndInfo(product_id,class_type1)
{
	var rets = showModalDialog('product_repos_info.jsp?product_id='+product_id+'&class_type1='+class_type1,'','dialogWidth:520px;dialogHeight:420px;status:0;help:0');
	if(rets != null){
		location = 'product_repos_list.jsp?page=<%=sPage%>&pagesize='+document.theform.pagesize.value;
	}
	//if(showModalDialog('product_repos_info.jsp?product_id='+product_id+'&class_type1='+class_type1,'','dialogWidth:520px;dialogHeight:420px;status:0;help:0') != null)
	//{
	//	sl_update_ok();
	//}
}

function productSearch()
{	
	var ret = showModalDialog('product_search.jsp?product_code=<%=product_code%>'+'&class_type1=<%=class_type1%>' + '&product_name=<%=product_name%>'+'&start_date=<%=start_date%>'+'&start_date2=<%=start_date2%>'+'&fact_money=<%=fact_money%>'+'&fact_money2=<%=fact_money2%>'+'&summary=<%=summary%>', '', 'dialogWidth:500px;dialogHeight:280px;status:0;help:0');
	if(ret != null)
	{
		disableAllBtn(true);
	    location = 'product_repos_list.jsp?page=<%=sPage%>&pagesize='+document.theform.pagesize.value
	     +'&product_code='+ret[0]
	   	 +'&class_type1='+ret[1]
	   	 +'&product_name='+ret[2]
	   	 +'&start_date='+ret[3]
	     +'&start_date2='+ret[4]
		 +'&fact_money='+ret[5]
		 +'&fact_money2='+ret[6]
	     +'&summary='+ret[7];
	}
}

</script>

</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=4>
							<IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28">
							<font color="#215dc6"><b>信息维护 >> 产品要素维护</b></font>
						</td>					
					</tr>
					<tr>
						<td align=right >
							<button type="button"  class="xpbutton3" accessKey=f id="search" name="search" onclick="javascript:productSearch();" <%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						</td>				
					</tr>
					<tr><td>&nbsp;&nbsp;&nbsp; </td></tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					
					<tr class="trh">
						<td align="center" >编号</td><!--编号-->
						<td align="center" >名称</td><!--名称-->
						<td align="center" >预期发行金额</td>
						<td align="center" >实际发行金额</td>
						<td align="center" >运用方式</td><!--财产类型-->
						<td align="center" >编辑</td><!--风险设置-->
					</tr>
					
<%
int iCurrent = 0;
int iCount=0;
List rsList = pageList.getRsList();
Map rowMap = null;
for(int i=0;i<rsList.size();i++)
{ 
	rowMap = (Map)rsList.get(i);
	Integer product_id = (Integer)rowMap.get("PRODUCT_ID");
	product_code = Utility.trimNull(rowMap.get("PRODUCT_CODE"));
	product_name = Utility.trimNull(rowMap.get("PRODUCT_NAME"));
	fact_money= (BigDecimal)rowMap.get("FACT_MONEY");
	String intrust_type_name = Utility.trimNull(rowMap.get("INTRUST_TYPE_NAME"));
	BigDecimal pre_money = (BigDecimal)rowMap.get("PRE_MONEY");
	class_type1 = Utility.trimNull(rowMap.get("CLASS_TYPE1"));
%>
            <tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh"  align="center" ><%=Utility.trimNull(product_code)%>-<%=Utility.trimNull(product_id)%></td>
				<td align="left"><%=Utility.trimNull(product_name)%></td>
				<td align="right"><%=Format.formatMoney(fact_money)%></td>
				<td align="right"><%=Format.formatMoney(pre_money)%></td>
				<td align="center"><%=Utility.trimNull(intrust_type_name)%></td>
				<td align="center">
					<a href="#" onclick="javascript:setTextEndInfo('<%=Utility.trimNull(product_id)%>','<%=class_type1%>')">
						<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="编辑 " /><!--风险设置-->
					</a>
				</td>
			</tr>
<%
			iCurrent++;
			iCount++;
	
}

for (; iCurrent < pageList.getBlankSize(); iCurrent++){
%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
			</tr>
<%
}
%>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="14">
					<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;
					<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
				</td>
			</tr>
		</table>

		<br>

		<table border="0" width="100%">
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
