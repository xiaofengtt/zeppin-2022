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
	result = showModalDialog('<%=request.getContextPath()%>menu_view_set.jsp','','dialogWidth:550px;dialogHeight:500px;status:0;help:0');	
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
					<!--�ͻ�����--><!--�ͻ��ۺϲ�ѯ-->
                    <td colspan=6><img src="<%=request.getContextPath()%>/images/member.gif" border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
				</tr>
				<tr><td colspan=6>&nbsp;</td></tr>
				<tr>
					<td colspan=6>
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr class="trh">
					<td align="center" width="80px"><input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo">�ͻ����</td>
					<td align="center" width="*">�ͻ����� </td><!--�ͻ�����-->
					<td align="center" width="*">�ۼƹ�����</td><!--�ۼƹ�����-->
					<td align="center" width="*">�������</td><!--�������-->
					<td align="center" width="*">�Ƽ����</td><!--�Ƽ����-->
					<td align="center" width="*">�Ϲ�����</td><!--�Ϲ�����-->
					<td align="center" width="*">�״ι���ʱ��  </td><!-- �״ι���ʱ�� -->
					<td align="center" width="*">�������ʱ��  </td><!-- �������ʱ�� -->		
					<td align="center" width="*">�����ͬ������  </td><!-- �����ͬ������ -->		
					<td align="center" width="*">�ͻ���Ծ�� </td><!-- �ͻ���Ծ�� -->					
				</tr>
				<tr class="tr0">
					<td align="left" width="80px">&nbsp;<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*"></td><!--�ͻ�����-->
					<td align="center" width="*"></td><!--�ۼƹ�����-->
					<td align="center" width="*"></td><!--�������-->
					<td align="center" width="*"></td><!--�Ƽ����-->
					<td align="center" width="*"></td><!--�Ϲ�����-->
					<td align="center" width="*"></td><!-- �״ι���ʱ�� -->
					<td align="center" width="*"></td><!-- �������ʱ�� -->		
					<td align="center" width="*"></td><!-- �����ͬ������ -->		
					<td align="center" width="*"></td><!-- �ͻ���Ծ�� -->					
				</tr>
				<tr class="tr1">
					<td align="left" width="80px">&nbsp;<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*"></td><!--�ͻ�����-->
					<td align="center" width="*"></td><!--�ۼƹ�����-->
					<td align="center" width="*"></td><!--�������-->
					<td align="center" width="*"></td><!--�Ƽ����-->
					<td align="center" width="*"></td><!--�Ϲ�����-->
					<td align="center" width="*"></td><!-- �״ι���ʱ�� -->
					<td align="center" width="*"></td><!-- �������ʱ�� -->		
					<td align="center" width="*"></td><!-- �����ͬ������ -->		
					<td align="center" width="*"></td><!-- �ͻ���Ծ�� -->		
				</tr>
				<tr class="tr0">
					<td align="left" width="80px">&nbsp;<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*"></td><!--�ͻ�����-->
					<td align="center" width="*"></td><!--�ۼƹ�����-->
					<td align="center" width="*"></td><!--�������-->
					<td align="center" width="*"></td><!--�Ƽ����-->
					<td align="center" width="*"></td><!--�Ϲ�����-->
					<td align="center" width="*"></td><!-- �״ι���ʱ�� -->
					<td align="center" width="*"></td><!-- �������ʱ�� -->		
					<td align="center" width="*"></td><!-- �����ͬ������ -->		
					<td align="center" width="*"></td><!-- �ͻ���Ծ�� -->		
				</tr>
				<tr class="tr1">
					<td align="left" width="80px">&nbsp;<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*"></td><!--�ͻ�����-->
					<td align="center" width="*"></td><!--�ۼƹ�����-->
					<td align="center" width="*"></td><!--�������-->
					<td align="center" width="*"></td><!--�Ƽ����-->
					<td align="center" width="*"></td><!--�Ϲ�����-->
					<td align="center" width="*"></td><!-- �״ι���ʱ�� -->
					<td align="center" width="*"></td><!-- �������ʱ�� -->		
					<td align="center" width="*"></td><!-- �����ͬ������ -->		
					<td align="center" width="*"></td><!-- �ͻ���Ծ�� -->		
				</tr>
				<tr class="tr0">
					<td align="left" width="80px">&nbsp;<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*"></td><!--�ͻ�����-->
					<td align="center" width="*"></td><!--�ۼƹ�����-->
					<td align="center" width="*"></td><!--�������-->
					<td align="center" width="*"></td><!--�Ƽ����-->
					<td align="center" width="*"></td><!--�Ϲ�����-->
					<td align="center" width="*"></td><!-- �״ι���ʱ�� -->
					<td align="center" width="*"></td><!-- �������ʱ�� -->		
					<td align="center" width="*"></td><!-- �����ͬ������ -->		
					<td align="center" width="*"></td><!-- �ͻ���Ծ�� -->		
				</tr>
				<tr class="tr1">
					<td align="left" width="80px">&nbsp;<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo"></td>
					<td align="center" width="*"></td><!--�ͻ�����-->
					<td align="center" width="*"></td><!--�ۼƹ�����-->
					<td align="center" width="*"></td><!--�������-->
					<td align="center" width="*"></td><!--�Ƽ����-->
					<td align="center" width="*"></td><!--�Ϲ�����-->
					<td align="center" width="*"></td><!-- �״ι���ʱ�� -->
					<td align="center" width="*"></td><!-- �������ʱ�� -->		
					<td align="center" width="*"></td><!-- �����ͬ������ -->		
					<td align="center" width="*"></td><!-- �ͻ���Ծ�� -->		
				</tr>
				<tr class="trbottom">
					<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;0&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
				</tr>	
			</table>
		</TD>
	</TR>
</TABLE>

</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
