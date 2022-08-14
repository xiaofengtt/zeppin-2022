<%@ page contentType="text/html; charset=GBK" import="java.math.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
try{
//获得对象及结果集
TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
TMoneyDataVO vo = new TMoneyDataVO();
String         sQuery   = request.getParameter("sQuery");
String[]       s        = request.getParameterValues("serial_no");
Integer checkflag       = Utility.parseInt(request.getParameter("checkflag"),new Integer(2));

if (s != null){
	for(int i = 0;i < s.length; i++){		
		int serial_no = Utility.parseInt(s[i], 0);
		if(serial_no != 0){
			vo.setSerial_no(new Integer(serial_no));
			vo.setInput_man(input_operatorCode);
			vo.setCheck_flag(checkflag);
			moneyData.input_check(vo);
		}
	}
}

moneyData.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_check_ok("<%=request.getContextPath()%>/marketing/sell/sub_capital_entering_preliminary_check.jsp?back_flag=1&sQuery=<%=sQuery%>");
</SCRIPT>
<%
}catch(Exception e){
	throw e;
}%>