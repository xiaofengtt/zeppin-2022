<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
//check_flag:1表示审核通过；2表示审核不通过
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));

CustomerClassLocal local = EJBFactory.getCustomerClass();
CustomerClassVO vo = new CustomerClassVO();

String [] items = request.getParameterValues("serial_no");
String serial_no = "";
if(items !=null)
{
	for(int i=0;i<items.length;i++)
	{
		serial_no = Utility.trimNull(items[i]);
		if(serial_no != "")
		{
			String [] params = Utility.splitString(serial_no, "#");
			vo.setCust_id(Utility.parseInt(Utility.trimNull(params[0]),new Integer(0)));
			vo.setClass_difine_id(Utility.parseInt(Utility.trimNull(params[1]),new Integer(0)));
			vo.setClass_detail_id(Utility.parseInt(Utility.trimNull(params[2]),new Integer(0)));
			vo.setCheck_flag(check_flag);
			vo.setInput_man(input_operatorCode);
			local.checkCustomerClass(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.successful",clientLocale)%> ！");//操作成功
	window.returnValue = 1;
	location =  "client_class_check_list.jsp";
</SCRIPT>
<%local.remove();%>