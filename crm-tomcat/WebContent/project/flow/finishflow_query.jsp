<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.project.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
String currentMonth=ConfigUtil.getToday("day","yyyyMM");
String queryMonth=ConfigUtil.getToday("day","yyyy-MM");

//显示所有的统计信息
List list = businessLogicLocal.listFlowTask(String.valueOf(input_operatorCode),queryMonth,"2");
String[][] showUser=new String[list.size()][11];
for(int i=0;i<list.size();i++){
    Map map = (Map)list.get(i);
    showUser[i][0] = Utility.trimNull(map.get("FLOW_NO"));
    showUser[i][1] = Utility.trimNull(map.get("FLOW_NAME"));
    showUser[i][2] = Utility.trimNull(map.get("NODE_NO"));
    showUser[i][3] = Utility.trimNull(map.get("NODE_NAME"));
    showUser[i][4] = Utility.trimNull(map.get("USER_NAME"));
    showUser[i][5] = Utility.trimNull(map.get("COL1"));
    showUser[i][6] = Utility.trimNull(map.get("COL2"));
    showUser[i][7] = Utility.trimNull(map.get("COL3"));
    showUser[i][8] = Utility.trimNull(map.get("COL4"));
    showUser[i][9] = Utility.trimNull(map.get("COL5"));
    showUser[i][10] = Utility.trimNull(map.get("COL6"));        
}

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform" method="POST" action="">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>	
	<TABLE cellSpacing=0 cellPadding=4 width="80%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td  valign="bottom" align="center">
						<font size="4%" style="normal"><b>已结束流程任务一览情况 </b>（单位:个）</font>
					</td>
				</tr>
			</table>
			<table id="table4" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
				<tr class="trh">
					<td height="20px" rowspan=1 align="center" style="width:15%" >流程名称</td>
					<td height="20px" rowspan=1 align="center" style="width:15%" >阶段名称</td>
					<td height="20px" rowspan=1 align="center" style="width:10%" >当前处理人</td>
					<td height="20px" colspan=1 align="center" style="width:10%" >【<%=currentMonth%>】</td>
					<td height="20px" colspan=1 align="center" style="width:10%" >【<%=ConfigUtil.getRelativeMonth(currentMonth,-1)%>】</td>
					<td height="20px" colspan=1 align="center" style="width:10%" >【<%=ConfigUtil.getRelativeMonth(currentMonth,-2)%>】</td>
					<td height="20px" colspan=1 align="center" style="width:10%" >【<%=ConfigUtil.getRelativeMonth(currentMonth,-3)%>】</td>
					<td height="20px" colspan=1 align="center" style="width:10%" >【<%=ConfigUtil.getRelativeMonth(currentMonth,-4)%>】</td>
					<td height="20px" colspan=1 align="center" style="width:10%" >【<%=ConfigUtil.getRelativeMonth(currentMonth,-5)%>】</td>					
				</tr>				
			  <%for(int i=0;i<showUser.length;i++){ %>
				<tr class="tr0">
					<%if(!"Total".equals(showUser[i][0])){%>
						<td height="20px" align="left"  bgColor="#FFD2D2" style="width:15%"><%=showUser[i][1]%></td>
						<td height="20px" align="left"  bgColor="#FFD2D2" style="width:15%"><%=showUser[i][3]%></td>
						<td height="20px" align="left"  bgColor="#FFD2D2" style="width:10%"><%=showUser[i][4]%></td>
						<%for(int j=5;j<=10;j++){ %>
							<td height="20px" align="right" bgColor="#FFD2D2" style="width:10%"><%=showUser[i][j]%></td>
						<%}%>
					<%}else{%>	
						<td height="20px" colspan=3 align="left"  bgColor="#EDEDED" style="width:40%"><%=showUser[i][1]%></td>
						<%for(int j=5;j<=10;j++){ %>
							<td height="20px" align="right" bgColor="#EDEDED" style="width:10%"><%=showUser[i][j]%></td>
						<%}%>						
					 <%}%>				
				</tr>
			  <%}%>
			</table>
	    </TD>
	    </TR>			
	</TABLE>
	
	</TD>
	</TR>
</TABLE>
<script language=javascript>
function getReportData(){
	if(confirm("确定要执行操作吗？")){
		document.theform.submit();
	}
}

</script>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>

<%businessLogicLocal.remove();%>



