<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,java.math.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),null);
String product_name = Utility.trimNull(request.getParameter("product_name"),null);
String channel_type = Utility.trimNull(request.getParameter("channel_type"),null);
String product_status = Utility.trimNull(request.getParameter("product_status"),"110203");
int flag = Utility.parseInt(request.getParameter("flag"),4);
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();
ChannelLocal local2 = EJBFactory.getChannel();
ChannelVO vo2 = new ChannelVO();

vo.setProduct_id(product_id);
vo.setStart_date(start_date);
vo.setSellFlag(new Integer(2));//渠道
vo.setInput_man(input_operatorCode);
vo.setProduct_status(product_status);
vo.setChannel_type(channel_type);
List list  = local.queryByAllAnalysis(vo,1);

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.roleSet",clientLocale)%> </TITLE>
<!--角色设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<style>
		.headdarkbutton
		{
			cursor:hand;
			BORDER-RIGHT: 0px solid;
		    BORDER-TOP: 0px solid;
		    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
		    PADDING-BOTTOM: 0px;
		    BORDER-LEFT: 0px solid;
		    WIDTH: 80px;
		    PADDING-TOP: 0px;
		    BACKGROUND-COLOR:white;		
		    BORDER-BOTTOM: 0px solid;
		    HEIGHT: 20px;
		}
		
		.headbutton
		{
			cursor:hand;
			BORDER-RIGHT: 0px solid;
		    BORDER-TOP: 0px solid;
		    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
		    PADDING-BOTTOM: 0px;
		    BORDER-LEFT: 0px solid;
		    WIDTH: 80px;
		    PADDING-TOP: 0px;
		    BACKGROUND-COLOR:white;		
		    BORDER-BOTTOM: 0px solid;
		    HEIGHT: 20px;
		}
</style>
<script language=javascript>

	
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="role_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=product_name%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<!--<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;查询-->
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table  border="0"  width="100%" cellspacing="1" cellpadding="2">
	    			<tr>
						<td>&nbsp;</td>
						<td align="right" id=d0 style="background-repeat: no-repeat;cursor: hand;" vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/head_00_01.gif'>&nbsp;&nbsp;渠道统计</td>
	    			</tr>
    			</table>

				<table id="view_1" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td width="">渠道名称</td>
						<td width="">认购份额</td>
						<td width="">申购份额</td>
						<td width="">赎回份额</td>
						<td width="">新增客户数</td>
						<td width="">客户总数</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
for(int i=0;i<list.size();i++){	

	Map map = (Map)list.get(i);
	channel_type    = Utility.trimNull(map.get("CHANNEL_TYPE"));
	String channel_type_name	   = Utility.trimNull(map.get("CHANNEL_TYPE_NAME"));
	BigDecimal rg_amount   = Utility.parseDecimal(Utility.trimNull(map.get("RG_AMOUNT")),new BigDecimal(0));
	BigDecimal sg_amount   = Utility.parseDecimal(Utility.trimNull(map.get("SG_AMOUNT")),new BigDecimal(0));
	BigDecimal sh_amount   = Utility.parseDecimal(Utility.trimNull(map.get("SH_AMOUNT")),new BigDecimal(0));
	BigDecimal pre_money_2 = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY_2")),new BigDecimal(0));
	Integer new_cust_num   = Utility.parseInt(Utility.trimNull(map.get("NEW_CUST_NUM")),null);
	Integer total_cust_num = Utility.parseInt(Utility.trimNull(map.get("TOTAL_CUST_NUM")),null);
	String product_status_name =Utility.trimNull(map.get("PRODUCT_STATUS_NAME"));
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td class=""><%=Utility.trimNull(channel_type_name) %></td>
						<td align="right"><%=Format.formatMoney(rg_amount) %></td>
						<td align="right"><%=Format.formatMoney(sg_amount) %></td>
						<td align="right"><%=Format.formatMoney(sh_amount) %></td>
						<td align="right"><%=Utility.trimNull(new_cust_num) %></td>
						<td align="right"><%=Utility.trimNull(total_cust_num) %></td>
					</tr>
<%
iCurrent++;
iCount++;
}
%>

				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%local.remove();%>

