<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*,enfo.crm.affair.*"%>
<%
request.setCharacterEncoding("UTF-8");

Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"),new Integer(0));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));  

String listSql = "SELECT A.*, B.GAIN_RATE FROM INTRUST..TGAINLEVEL A LEFT JOIN INTRUST..TGAINLEVELRATE B ON A.SERIAL_NO = B.DF_SERIAL_NO WHERE A.PRODUCT_ID = ? AND A.PROV_FLAG = ? AND A.PROV_LEVEL = ? ORDER BY B.START_DATE DESC";

BigDecimal gain_rate = new BigDecimal(0);
List result = CrmDBManager.listBySql(listSql, new Object[]{
		product_id,
		prov_flag,
		prov_level
});

if(result != null && result.size() > 0){
	Map map_gain_rate = (Map) result.get(0);
	gain_rate = Utility.parseDecimal(Utility.trimNull(map_gain_rate.get("GAIN_RATE")),new BigDecimal(0.00));
	gain_rate = gain_rate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
}

out.println(gain_rate);
%>
