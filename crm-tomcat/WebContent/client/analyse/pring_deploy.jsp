<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
StringBuffer list = new StringBuffer(200);  //每页打印个数
%>
<html>
<head>
<TITLE>打印客户收益分配明细</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<!--media=print 这个属性可以在打印时有效-->
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<script language="javascript">

</script>
</head>
<body topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform"  method="post">
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
			<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('确认要打印吗？'))	{	document.all.WebBrowser.ExecWB(6,6);}">直接打印(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:window.returnValue=null;window.close();">返回(<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;
		
			</td>
		</tr>
</table>
<table border="1" width="100%" cellspacing="0" cellpadding="4" height="20%">
	<tr>
		<td align="center" height="8%" colspan="6"><font size="3"><b>收益分配明细表</b></font></td>		
	</tr>	
<%
DeployLocal deploy_local = EJBFactory.getDeploy();
CustomerLocal cust_local = EJBFactory.getCustomer();
DeployVO deploy_vo = new DeployVO();
CustomerVO cust_vo = new CustomerVO();

Integer strID = null;
Map deploy_map = new HashMap();
Map cust_map = new HashMap();
String items = request.getParameter("cust_id");				

if (items != null && items.length() > 0)
{ 	
	String [] paras = Utility.splitString(items, ",");
	for(int n = 0;n < paras.length;n++)
	{
		strID = Utility.parseInt(Utility.trimNull(paras[n]), new Integer(0));
		if(!"".equals(new Integer(0)))
		{	
			deploy_vo.setCust_id(strID);
			deploy_vo.setStartdate(new Integer(0));
			deploy_vo.setEnddate(new Integer(0));
			
			cust_vo.setCust_id(strID);
			cust_vo.setInput_man(input_operatorCode);
			//获得客户受益信息
			List deploy_list = deploy_local.querycust(deploy_vo);
			if(deploy_list != null && deploy_list.size() > 0)
				deploy_map = (Map)deploy_list.get(0);
			
			//获得客户信息
			List cust_list = cust_local.listProcAll(cust_vo);
			if(cust_list != null && cust_list.size() > 0)
				cust_map = (Map)cust_list.get(0);		
%>
			<tr>		
				<td align="left" height="8%" width="8%">姓名:</td>
				<td align="left" height="3%" width="22%" colspan="5"><font size="3" face="宋体"><b><%=Utility.trimNull(cust_map.get("CUST_NAME"))%>&nbsp;</b></font>
			</tr>
			<tr>
				<td  align="center" height="25">项目名称</td>
				<td  align="center" height="25" width="124">受益金额</td>
				<td  align="center" height="25" width="106">年收益率(%)</td>
				<td  align="center" height="25" width="105">返还本金</td>
				<td  align="center" height="25" width="156">分配收益额</td>
				<td  align="center" height="25" width="128">分配日期</td>
			</tr>
			<!--存在收益明细时才实现-->
			<%if(deploy_list != null && deploy_list.size() > 0){%>
			<tr>
				<td align="left" height="25"><%=Utility.trimNull(deploy_map.get("PRODUCT_NAME"))%>&nbsp;</td>
				<td align="right" height="25" width="124"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(deploy_map.get("SY_AMOUNT"))))%>&nbsp;</td>
       		 	<td align="left" height="25" width="106"><%=Utility.trimNull(Utility.stringToDouble(Utility.trimNull(deploy_map.get("RATE"))).multiply(new BigDecimal(100)).setScale(3,BigDecimal.ROUND_HALF_UP))%>&nbsp;</td>
       			<td align="left" height="25" width="105"><%=(Utility.trimNull(deploy_map.get("SY_TYPE")).equals("111605")?Format.formatMoney(Utility.stringToDouble(Utility.trimNull(deploy_map.get("SY_MONEY")))):"0")%>&nbsp;</td>
        		<td align="left" height="25" width="156"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(deploy_map.get("SY_MONEY"))))%>&nbsp;</td>
        		<td align="left" height="25" width="128"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(deploy_map.get("SY_DATE")), new Integer(0)))%>&nbsp;</td>
			</tr>
			<%}%>
			<%if(n < (paras.length - 1)){%>
			<tr>
				<td colspan="6">&nbsp;</td>
			</tr>
			<%}%>
		<%}
	}
}	
%>			
</table>
<%	
deploy_local.remove();
cust_local.remove();
%>
</form>
</body>
</html>

