<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��������
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
IPageList pageList = local.listCustDue(input_operatorCode,t_sPage,t_sPagesize);
//��ҳ��������
List list = pageList.getRsList();
Map map = null;
%>

<html>
<head>
	<title>�������ڿͻ�</title>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<base target="_self">
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<script>
		//ˢ��
		function refreshPage(){
			var url = "portal_custdue_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;		
			var a = document.createElement("a");
		    a.href = url;
		    document.body.appendChild(a);
		    a.click();			
		}

	</script>
</head>
<body class="BODY">
<form name="theform" method="get">
	<div>
		<div align="left">
			<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
			<font color="#215dc6"><b>�������ڿͻ�</b></font>
		</div>	
		<hr noshade color="#808080" size="1">
	</div>

	<table id="table3" border="0" cellspacing="1" cellpadding="3" class="tablelinecolor" width="100%" >
		<tr class="trh">
			<td align="center" width="*">�ͻ�����</td>
			<td align="center" width="*">�������</td>
			<td align="center" width="*">���ڲ�Ʒ</td>
			<td align="center" width="*">��������</td>
			<td align="center" width="*">�ۼ��Ϲ����</td>
		</tr>
		<%
			Iterator it = list.iterator();
			BigDecimal ben_amount = new BigDecimal(0);
			BigDecimal rg_money = new BigDecimal(0);
			int ben_end_date = 0;
			int iCount = 0;
	
			while(it.hasNext()){
				map = (HashMap)it.next();
				ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0));			
				ben_end_date = Utility.parseInt(Utility.trimNull(map.get("BEN_END_DATE")),0);
				rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0));
		%>
			<tr class="tr<%=iCount%2%>">
				<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
				<td align="center" width="*"><%=Format.formatMoney(ben_amount)%></td>
				<td align="center" width="*"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%></td>
				<td align="center" width="*"><%=Format.formatDateCn(ben_end_date)%></td>
				<td align="center" width="*"><%=Format.formatMoney(rg_money)%></td>
			</tr>
		<%}%>
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>
	         <tr class="tr0">
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	         </tr>
		<%}%>
	 </table>
	<div align="right">
		<%=pageList.getPageLink(sUrl,clientLocale)%>
	</div>
</form>
</body>
</html>