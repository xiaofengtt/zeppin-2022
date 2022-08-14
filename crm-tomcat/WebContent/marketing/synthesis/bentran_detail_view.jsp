<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc"%>
<%@ include file="/includes/operator.inc" %>

<%
BenChangeLocal benchg = EJBFactory.getBenChange();
BenChangeVO vo = new BenChangeVO();

Integer product_id=Utility.parseInt(request.getParameter("product_id"), new Integer(-1));
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));

String product_name = Argument.getProductName(product_id);
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));

vo.setProduct_id(product_id);
vo.setContract_bh(contract_bh);
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);

//获得客户信息
IPageList pageList = benchg.listAll(vo, 1, -1); // -1表示全部
//Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();

StringBuffer serialIdSAll = new StringBuffer();
//URL设置
sUrl += "&product_id=" + product_id + "&contract_bh=" + contract_bh;

//分页参数
int iCount = 0;
int iCurrent = 0;
%>

<html>
<head>
<base target="_self"> 
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>受益变更明细</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script type="text/javascript">
function refreshPage() {
	location.href = "bentran_detail_view.jsp?page=&pagesize="+ document.theform.pagesize.value 
		+ "&product_id=<%=product_id%>&contract_bh=<%=contract_bh%>";	
}
</script>
</head>

<body class="body body-nox">
<form name="theform">
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<h3 align="center"><%=product_name%>[<%=contract_sub_bh%>]受益权变更明细</h3>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
		<tr class="trh">
			<!-- 产品名称，合同编号，受益人名称、受益人银行账号、银行名称、受益金额 -->
	         <td width="15%" align="center">原受益人</td>
			 <td width="15%" align="center">新受益人</td>
			 <td width="40%" align="center">新受益人银行账号</td> 
		     <td width="10%" align="center">变更金额</td>
			 <td width="10%" align="center">变更方式</td>
			 <td width="10%" align="center">变更日期</td>
	    </tr>
<%
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	iCount ++;
%>   
         <tr class="tr<%=iCount%2%>">
			<td align="center"><%=Utility.trimNull(map.get("FROM_CUST_NAME"))%></td> 
			<td align="center"><%=Utility.trimNull(map.get("TO_CUST_NAME"))%></td> 
			<td align="center"><%=Utility.trimNull(map.get("BANK_NAME"))+"-"+Utility.trimNull(map.get("BANK_SUB_NAME"))+Utility.trimNull(map.get("BANK_ACCT"))%></td>
			<td align="right"><%=Format.formatMoney((BigDecimal)map.get("TO_AMOUNT"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("TRANS_FLAG_NAME"))%></td>
			<td align="center"><%=Format.formatDateLine((Integer)map.get("CHANGE_DATE"))%></td>
         </tr>   
<%}  
for(int i=0;i<8-iCount;i++){ %>      	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>  
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td> 
			<td align="center">&nbsp;</td> 
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		</tr>   
	</TABLE>
	</td>
	</tr>
	<%-- <tr>
    	<td>
    		<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
    	</td>
    </tr>--%>
</table>

</form>
</body>
</html>
<% benchg.remove(); %>
