<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
function setFormula(){
	result = showModalDialog('<%=request.getContextPath()%>menu_view_set.jsp','','dialogWidth:550px;dialogHeight:540px;status:0;help:0');	
	if(result!=null)
	location.reload();
}
function queryCustomer(){
	window.location='cust_worth.jsp?gs=1';
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="worth_analyse.jsp">
<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
	<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<!--客户分析--><!--客户综合查询-->
                    <td colspan=6><img src="<%=request.getContextPath()%>/images/member.gif" border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
				</tr>
				<tr>
					<td align=right>
						<!--添加公式-->
                        <button type="button"  class="xpbutton3" id="addButton" title='添加公式' name="addButton" onclick="javascript:setFormula();">添加公式</button>&nbsp;&nbsp;&nbsp;
						<!--册除公式-->
                        <button type="button"  class="xpbutton3" id="delButton" title='删除公式' name="delButton" onclick="">删除公式</button>&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=6>
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr class="trh">
					<td align="center" width="45px"><input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*">客户等级 </td><!--客户等级-->
					<td align="center" width="*">等级计算公式</td><!--等级计算公式-->
					<td align="center" width="100px">查看该等级客户  </td><!-- 该等级客户 -->			
				</tr>
				<tr class="tr0">
					<td align="center" width="45px"><input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*">钻石客户</td><!--客户等级-->
					<td align="left" width="*">&nbsp;【累计购买金额】> 10000000 OR 【推荐金额】> 50000000</td><!--等级计算公式-->
					<td align="center" width="100px">
						<button type="button"  class="xpbutton2" name="" onclick="javascript:queryCustomer();">
		         			<IMG id="detailsImage" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
			         	</button>
					</td><!-- 该等级客户 -->			
				</tr>
				<tr class="tr1">
					<td align="center" width="45px"><input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*">白金客户</td><!--客户等级-->
					<td align="left" width="*">&nbsp;(【累计购买金额】> 1000000 AND 【累计购买金额】< 10000000) OR (【推荐金额】> 30000000 AND 【推荐金额】< 50000000)</td><!--等级计算公式-->
					<td align="center" width="100px">
						<button type="button"  class="xpbutton2" name="" onclick="javascript:queryCustomer();">
		         			<IMG id="detailsImage" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
			         	</button>
					</td><!-- 该等级客户 -->		
				</tr>
				<tr class="tr0">
					<td align="center" width="45px"><input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*">一般客户</td><!--客户等级-->
					<td align="left" width="*">&nbsp;【累计购买金额】< 1000000 OR 【推荐金额】< 30000000</td><!--等级计算公式-->
					<td align="center" width="100px">
						<button type="button"  class="xpbutton2" name="" onclick="javascript:queryCustomer();">
		         			<IMG id="detailsImage" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
			         	</button>
					</td><!-- 该等级客户 -->	
				</tr>
				<tr class="tr1">
					<td align="center" width="45px"></td>
					<td align="center" width="*"></td><!--客户等级-->
					<td align="left" width="*"></td><!--等级计算公式-->
					<td align="center" width="100px"></td><!-- 该等级客户 -->	
				</tr>
				<tr class="tr0">
					<td align="center" width="45px"></td>
					<td align="center" width="*"></td><!--客户等级-->
					<td align="left" width="*"></td><!--等级计算公式-->
					<td align="center" width="100px"></td><!-- 该等级客户 -->	
				</tr>
				<tr class="tr1">
					<td align="center" width="45px"></td>
					<td align="center" width="*"></td><!--客户等级-->
					<td align="left" width="*"></td><!--等级计算公式-->
					<td align="center" width="100px"></td><!-- 该等级客户 -->	
				</tr>
			</table>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
