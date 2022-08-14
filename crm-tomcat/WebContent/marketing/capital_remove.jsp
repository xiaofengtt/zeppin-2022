<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>

<%
IntrustCapitalInfoLocal capital = EJBFactory.getIntrustCapitalInfo();
String[] s = request.getParameterValues("serial_no");
Integer serial_no;

String capital_use = Utility.trimNull(request.getParameter("capital_use"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
String busi_id = Utility.trimNull(request.getParameter("busi_id"),"120388");
int readonly = Utility.parseInt(request.getParameter("readonly"),0);
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
Integer start_date=Utility.parseInt(request.getParameter("start_date"),new Integer(0));
Integer end_date=Utility.parseInt(request.getParameter("end_date"),new Integer(0));

if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		serial_no = Utility.parseInt(s[i], new Integer(0));
		if(serial_no.intValue() != 0)
		{
			capital.setSerial_no(serial_no);		
			capital.setInput_man(input_operatorCode);
			capital.delete();			
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	var url = "capital.jsp?busi_id=<%=busi_id%>&capital_use=<%=capital_use%>&readonly=<%=readonly%>&contract_bh=<%=contract_bh%>&product_id=<%=product_id%>&cust_id=<%=cust_id%>&start_date=<%=start_date%>&end_date=<%=end_date%>";
	sl_remove_ok(url);
</SCRIPT>
<%capital.remove();%>