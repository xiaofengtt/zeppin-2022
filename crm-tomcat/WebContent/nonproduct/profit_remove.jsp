<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
QuotientAffirmVO vo = new QuotientAffirmVO();
ProfitLocal profit = EJBFactory.getProfitLocal();

String[] s = request.getParameterValues("q_serialNo");
int serialNo;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		serialNo = Utility.parseInt(s[i], 0);
		if(serialNo != 0)
		{
			vo.setSerial_no(new Integer(serialNo));
			profit.delete(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "profit_list.jsp";
</SCRIPT>
<%profit.remove();%>
