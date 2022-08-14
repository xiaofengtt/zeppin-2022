<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
boolean bSuccess = false;

vo.setSerial_no(serial_no);
vo.setInput_man(input_operatorCode);
local.delPreMoneyDetail(vo);
bSuccess = true;
local.remove();

boolean from_signed_spot = Utility.trimNull(request.getParameter("from")).equals("signed_spot");
String qs = Utility.getQueryString(request
				, new String[]{"page","pagesize","q_pre_product_id","q_cust_name"
								,"start_date","end_date","pre_money1","pre_money2"
								,"pre_status","team_id","channel_type","channel_money1"
								,"channel_money2","q_pre_level","query_flag"});
%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){ %>
	sl_alert("到账处理记录删除成功！");
 <%if (from_signed_spot) {%>
	location = "../signed_spot.jsp?<%=qs%>";
 <%}else{%>
	location = "reserve_list.jsp";
 <%}
}%>
</script>