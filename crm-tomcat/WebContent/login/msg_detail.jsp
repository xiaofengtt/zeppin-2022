<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ include file="/includes/operator.inc"%>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

OperatorLocal op = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
vo.setSerial_no(serial_no);
vo.setTo_op_code(input_operatorCode);
vo.setOp_code(input_operatorCode);
List list = op.listSysMessage(vo, 1, 0).getRsList();
		
String title = "";
String msg = "";
String input_time = "";
Integer is_read = new Integer(1);
String from = "";
if (list.size()>0) {
	Map map = (Map)list.get(0);
	title =  Utility.trimNull(map.get("TITLE"));
	msg =  Utility.trimNull(map.get("MSG"));	
	input_time = map.get("INPUT_TIME").toString().substring(0,16);
	is_read = (Integer)map.get("IS_READ");
	from = Utility.trimNull(map.get("FROM_OPNAME"));
}

op.remove();
%>
<html>
<head>
<title>消息明细</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link href="/includes/default.css" type=text/css rel=stylesheet>
<script src="/includes/default.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
<script language=javascript>
var retval = <%=is_read%>;

function markAsRead(serial_no) {
	$.ajax({
	  type: 'POST',
	  url: "mark_msg_as_read.jsp",
	  data: {
			serial_no: <%=serial_no%>
	  },
	  success: function() {
			document.getElementById("readMarker").style.display = "none";
			retval = 2; // read
	  }
	});
}

window.onunload = function() {
		window.returnValue = retval;
	};
</script>
</head>
<body class="body">
	<table class="tablelinecolor" border="0" width="95%" height="60%" cellspacing="1" cellpadding="1" align="center" style="margin:5px">
		<tr class="tr1">
			<td align="right" width="20%" style="font-weight:bold">标题:&nbsp;</td>
			<td align="left" width="*">&nbsp;<%=title%></td>	
		</tr>
		<tr class="tr0">
			<td align="right" width="20%" style="font-weight:bold">正文:&nbsp;</td>
			<td align="left" width="*">&nbsp;<%=msg%></td>	
		</tr>
		<tr class="tr1">
			<td align="right" width="20%" style="font-weight:bold">发送人:&nbsp;</td>
			<td align="left" width="*">&nbsp;<%=from%></td>	
		</tr>
		<tr class="tr0">
			<td align="right" width="20%" style="font-weight:bold">发送时间:&nbsp;</td>
			<td align="left" width="*">&nbsp;<%=input_time%></td>	
		</tr>				
	</table>

	<TABLE width="100%" height="30%" style="table-layout:fixed">
		<tr>
			<td align="right" align="bottom" width="100%" height="100%">
			<%if (is_read.intValue()==1) { %>
				<button type="button"  id="readMarker" class="xpbutton4" onclick="javascript:markAsRead(<%=serial_no%>);">标为已读</button>
			<% } %>
				&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:window.close();">关闭</button>				
			</td>
		</tr>	
		</tr>
	</TABLE>
</body>
</html>