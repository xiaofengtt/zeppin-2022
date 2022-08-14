<%@ page contentType="text/html; charset=GBK" import="java.math.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
//获得对象及结果集
TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
String         sQuery   = request.getParameter("sQuery");
Integer checkflag       = Utility.parseInt(request.getParameter("checkflag"),new Integer(0));

String[]       s        = request.getParameterValues("serial_no");
if (s != null){
	for(int i = 0;i < s.length; i++){
		TMoneyDataVO vo = new TMoneyDataVO();
		int serial_no = Utility.parseInt(s[i], 0);
		if(serial_no != 0){
			vo.setSerial_no(new Integer(serial_no));
			vo.setInput_man(input_operatorCode);
			vo.setCheck_flag(checkflag);
			moneyData.check(vo);
		}
	}
}

moneyData.remove();

boolean from_signed_spot = Utility.trimNull(request.getParameter("from")).equals("signed_spot");
String qs = Utility.getQueryString(request
				, new String[]{"page","pagesize","q_pre_product_id","q_cust_name"
								,"start_date","end_date","pre_money1","pre_money2"
								,"pre_status","team_id","channel_type","channel_money1"
								,"channel_money2","q_pre_level","query_flag"});
%>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if (from_signed_spot) {%>
	sl_check_ok("<%=request.getContextPath()%>/marketing/signed_spot.jsp?<%=qs%>");
<%} else {%>
	sl_check_ok("<%=request.getContextPath()%>/marketing/sell/sub_capital_entering_check.jsp?back_flag=1&sQuery=<%=sQuery%>");
<%}%>
</SCRIPT>
<%
}catch(Exception e){
	throw e;
}%>