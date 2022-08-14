<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer from_cust_id = Utility.parseInt(request.getParameter("from_cust_id"),new Integer(0));
Integer to_cust_id = Utility.parseInt(request.getParameter("to_cust_id"),new Integer(0));
boolean bSuccess=false;

CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setFrom_cust_id(from_cust_id);
vo.setTo_cust_id(to_cust_id);
vo.setInput_man(input_operatorCode);

local.unite(vo);//合并CRM中客户信息
bSuccess=true;

local.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	var bSuccess = <%=bSuccess%>;
	if (bSuccess) {
		sl_alert("<%=LocalUtilis.language("message.mergerSuccess",clientLocale)%> ！");//合并成功
		location.replace("client_unite_list.jsp");
	}
</SCRIPT>