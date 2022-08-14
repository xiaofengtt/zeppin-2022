<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("pre_product_id")),new Integer(0));
String pre_product_name = Utility.trimNull(request.getParameter("pre_product_name"));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));
String team_name = Utility.trimNull(request.getParameter("team_name"));

PreContractCrmLocal local = EJBFactory.getPreContractCrm();
PreContractCrmVO vo = new PreContractCrmVO();
vo.setPre_product_id(pre_product_id);
vo.setTeam_id(team_id);
vo.setPre_status("111301");
vo.setInput_man(input_operatorCode);
List list = local.listPreContractCrm(vo);
Map map = new HashMap();
%>
<HTML>
<HEAD>
<TITLE>团队预约明细信息</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
</script>
</HEAD>
<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="POST">
<div>
	<div align="left" class="page-title">
		<font color="#215dc6" size=3><b>团队预约明细信息</b></font>
	</div>
	<div align="left" class="btn-wrapper">
		<font color="red" style="font-weight: bold;">团队名称：<%=team_name %></font>
	</div>
</div>
<br/>
<div>
	<div style="overflow: scroll; width: 100%; height: 300px;overflow-x:hidden;">
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
	   		<td align="center">客户名称</td>
	    	<td align="center">客户类型</td>
	    	<td align="center">预约金额</td>
	    	<td align="center">到账金额</td>
	    	<td align="center">预计缴款时间</td>
	    	<td align="center">客户经理</td>
	    	<td align="center">联系电话</td>
	  	</tr>
		<%
		int iCount = 0;
		BigDecimal pre_money_sum = new BigDecimal(0);
		BigDecimal dz_money_sum = new BigDecimal(0);
		for(int i=0;i< list.size();i++){
			map = (Map)list.get(i);
			pre_money_sum = pre_money_sum.add(Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00)));
			dz_money_sum = dz_money_sum.add(Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")), new BigDecimal(0.00)));
 		%>
		<tr class="tr<%=(iCount%2)%>">
			 <td><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
			 <td><%=Utility.trimNull(map.get("CUST_TYPE_NAME")) %></td>
		     <td align="right"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00)))) %></td>
			 <td align="right"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")), new BigDecimal(0.00)))) %></td>
			 <td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")), new Integer(0))) %></td>
			 <td><%=Utility.trimNull(map.get("LINK_MAN_NAME")) %></td>
			 <td><%=Utility.trimNull(map.get("MOBILE")) %></td>
		</tr>	
		<%
		iCount++;
		}
		%>
		<tr class="trbottom">
			<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=list.size()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
			<td align="center"></td>
			<td align="right"><%=Utility.trimNull(Format.formatMoney(pre_money_sum)) %></td>
			<td align="right"><%=Utility.trimNull(Format.formatMoney(dz_money_sum)) %></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			</td>
		</tr>				
	</table>
	</div>
</div>
<div align="right" style="height: 30px; vertical-align: bottom;">
	<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:window.close();">关闭(<u>B</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>
<%
local.remove();
%>
