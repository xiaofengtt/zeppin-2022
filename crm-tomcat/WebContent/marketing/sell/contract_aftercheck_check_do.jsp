<%@ page contentType="text/html; charset=GBK" import="enfo.crm.service.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String[] s = request.getParameterValues("serial_no");
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(2));
String check_summary = Utility.trimNull(request.getParameter("check_summary"));

ContractLocal contract = EJBFactory.getContract();
ContractVO vo = new ContractVO();
vo.setCheck_flag(check_flag);	
vo.setSummary(check_summary);
vo.setInput_man(input_operatorCode);

boolean bSuccess = false;
for(int i = 0; i < s.length; i++){
	int serial_no = Utility.parseInt(s[i], 0);
	if(serial_no != 0){
		vo.setSerial_no(new Integer(serial_no));				
		contract.checkContractModi(vo);		
	}
}
bSuccess = true;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
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
<script language=javascript>
<%if(bSuccess){%>
	sl_check_ok("contract_aftercheck_check.jsp");
<%}%>
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post">
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>

