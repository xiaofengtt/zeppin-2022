<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
IntegralCalLocal integeralCal = EJBFactory.getIntegralCal();
IntegralCalVO vo = new IntegralCalVO();

List listRule = integeralCal.queryRule(vo);
List listRuleDtl = integeralCal.queryRuleDtl(vo);
List listRuleAmount = integeralCal.queryRuleAmount(vo);

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--积分规则-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	//initQueryCondition();
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
</div>

<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right">
		<%//if (input_operator.hasFunc(menu_id, 108)) {%><!--查询-->
		<!-- 
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;
		 --><%//}%>
	</div>
	<hr noshade color="#808080" size="1" width="98%">
</div>
<div align="center">
	<TABLE cellSpacing=0 cellPadding=3 width="98%" border=0>
		<tr >
			<td>&nbsp;&nbsp;<b>积分规则</b></td>  
		</tr>
	</TABLE>
	<table border="0"  width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">

		<tr class="trh">
			<td align="center" >规则名称</td>
			<td align="center" >积分值</td>
			<td align="center" >生效起始日</td>
			<td align="center" >生效终止日</td>
			<td align="center" >额度基数</td>
			<td align="center" >增减标志</td>
		</tr>
		
		<%
			Map map_Rule = null;
			Iterator listRule_iterator = listRule.iterator();
			String next_value = "增";
			Integer next_value_flag = new Integer(1);


			while(listRule_iterator.hasNext()){
				map_Rule = (Map)listRule_iterator.next();
				next_value_flag = Utility.parseInt(Utility.trimNull(map_Rule.get("Next_Value")),new Integer(1));

				if(next_value_flag.intValue()!=1){next_value="减";}
		 %>
			  <tr class="tr0">
	            <td align="center" ><%=Utility.trimNull(map_Rule.get("RULE_NAME"))%></td>
	            <td align="center" ><%=Utility.trimNull(map_Rule.get("INTEGRAL_VALUE"))%></td>
	            <td align="center" ><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map_Rule.get("BEG_DATE")),new Integer(0))))%></td>
	            <td align="center" ><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map_Rule.get("END_DATE")),new Integer(0))))%></td>       
	            <td align="center" ><%=Utility.trimNull(map_Rule.get("AMOUNT_RADIX"))%></td>   
	            <td align="center" ><%=next_value%></td>       
	         </tr> 
		<%}%>	
		</table>
		<BR>
		<TABLE cellSpacing=0 cellPadding=3 width="98%" border=0>
			<tr >
				<td>&nbsp;&nbsp;<b>明细规则（定义购买次数）</b></td>  
			</tr>
		</TABLE>
		<table border="0"  width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trh">
				<td align="center" >所属规则</td>
				<td align="center" >明细规则说明</td>
				<td align="center" >级次</td>
				<td align="center" >下限</td>
				<td align="center" >上限</td>
				<td align="center" >额度规则</td>
				
			</tr>

			<%
				Map map_RuleDtl = null;
				Iterator listRuleDtl_iterator = listRuleDtl.iterator();	
				Integer no_amount = new Integer(0);
	
				while(listRuleDtl_iterator.hasNext()){
					map_RuleDtl = (Map)listRuleDtl_iterator.next();
					no_amount = Utility.parseInt(Utility.trimNull(map_Rule.get("NO_MOUNT")),new Integer(0));
										
		 	%>
			  <tr class="tr0">
	            <td align="center" ><%=Utility.trimNull(map_RuleDtl.get("RULE_NAME"))%></td>
				<td align="center" ><%=Utility.trimNull(map_RuleDtl.get("REMARK"))%></td>       
	            <td align="center" ><%=Utility.trimNull(map_RuleDtl.get("LEVEL"))%></td>
	            <td align="center" ><%=Utility.trimNull(map_RuleDtl.get("LOWER_VALUE"))%></td>
	            <td align="center" ><%=Utility.trimNull(map_RuleDtl.get("UPPER_VALUE"))%></td>       
	            <td align="center" ><%if(no_amount.intValue()==0){out.print("存在");}else{ out.print("不存在");}%></td>  	            
	         </tr> 
			<%}%>	
		</table>
		<BR>
		<TABLE cellSpacing=0 cellPadding=3 width="98%" border=0>
			<tr >
				<td>&nbsp;&nbsp;<b>额度规则表</b></td>  
			</tr>
		</TABLE>
		<table border="0"  width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trh">
				<td align="center" >所属规则</td>
				<td align="center" >所属明细</td>
				<td align="center" >额度规则说明</td>
				<td align="center" >级次</td>
				<td align="center" >下限</td>
				<td align="center" >上限</td>
				<td align="center" >倍值</td>
			</tr>
			<%
				Map map_RuleAmount = null;
				Iterator listRuleAmount_iterator = listRuleAmount.iterator();	
	
				while(listRuleAmount_iterator.hasNext()){
					map_RuleAmount = (Map)listRuleAmount_iterator.next();										
		 	%>
			  <tr class="tr0">
	            <td align="center" ><%=Utility.trimNull(map_RuleAmount.get("RULE_NAME"))%></td>
				<td align="center" ><%=Utility.trimNull(map_RuleAmount.get("DTL_REMARK"))%></td>       
	            <td align="center" ><%=Utility.trimNull(map_RuleAmount.get("REMARK"))%></td>
				<td align="center" ><%=Utility.trimNull(map_RuleAmount.get("LEVEL"))%></td>
	            <td align="center" ><%=Utility.trimNull(map_RuleAmount.get("LOWER_VALUE"))%></td>
	            <td align="center" ><%=Utility.trimNull(map_RuleAmount.get("UPPER_VALUE"))%></td>       
	            <td align="center" ><%=Utility.trimNull(map_RuleAmount.get("MULTIPLE"))%></td>  	            
	         </tr> 
			<%}%>	
		</table>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>