<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer int_flag=Utility.parseInt(request.getParameter("int_flag"),new Integer(1));

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int iCount = 0;
List list = null;
Map map = null;

//获得对象及结果集
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO vo = new PreContractVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setCust_name(q_cust_name);
vo.setInt_flag(int_flag);

IPageList pageList = preContract.query_reginfo(vo,totalColumn,t_sPage,-1);
list = pageList.getRsList();
%>

<HTML>
<HEAD>
<TITLE>预登记信息打印</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
</HEAD>


<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="q_cust_name" value="<%=q_cust_name%>">
<input type="hidden" name="int_flag" value="<%=int_flag%>">

<div align="center" id="tablebtn">
	<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title="打印" onclick="javascript:tablebtn.style.display='none'; window.print();tablebtn.style.display='';">打印(<u>D</u>)</button>
	&nbsp;&nbsp;&nbsp; 
	<button type="button"  class="xpbutton3" accessKey=c name="btnClose" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>   
	&nbsp;&nbsp;&nbsp;
</div>

<br>
<div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
	    <td align="center">客户编号</td>
		<td align="center">客户姓名</td>
		<td align="center">家庭电话</td>						
		<td align="center">办公电话</td>
		<td align="center">手机</td>
		<td align="center">手机2</td>
		<td align="center">登记金额(万元)</td>
		<td align="center">登记日期</td>						
	</tr>
	
<%
//声明变量
Integer cust_id;
String cust_no = null;
String cust_name = null;
String cust_tel = null;
String o_tel = null;
String mobile = null;
String bp = null;
BigDecimal reg_money = new BigDecimal(0);
Integer reg_data = null;

Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	cust_no = Utility.trimNull(map.get("CUST_NO"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	cust_tel = Utility.trimNull(map.get("CUST_TEL"));
	o_tel = Utility.trimNull(map.get("O_TEL"));
	mobile = Utility.trimNull(map.get("MOBILE"));
	bp =  Utility.trimNull(map.get("BP"));
	reg_money = Utility.parseDecimal(Utility.trimNull(map.get("REG_MONEY")),new BigDecimal(0),2,"0.0001");	
	reg_data = Utility.parseInt(Utility.trimNull(map.get("REG_DATE")),new Integer(0));
%>
	<tr class="tr<%= iCount%2%>">
		 <td align="center"><%=cust_no%></td>
		 <td align="center"><%= cust_name%></td>
		 <td align="center"><%= cust_tel%></td>
		 <td align="center"><%= o_tel%></td>
		 <td align="center"><%= mobile%></td>
		 <td align="center"><%= bp%></td>
		 <td align="center"><%= Format.formatMoney(reg_money)%></td>
		 <td align="center"><%= Format.formatDateLine(reg_data)%></td>	
	</tr>
<%}%>				
</table>
</div>

</form>
<%preContract.remove();%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>

