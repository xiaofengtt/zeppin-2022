<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//check_flag:1表示审核通过；2表示审核不通过
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));

GradeTotalLocal local = EJBFactory.getGradeTotal();
GradeTotalVO vo = new GradeTotalVO();

String [] items = request.getParameterValues("serial_no");
Integer serial_no = new Integer(0);
if(items !=null)
{
	for(int i=0;i<items.length;i++)
	{
		serial_no = Utility.parseInt(items[i], new Integer(0));
		if(!serial_no.equals(new Integer(0)))
		{
			vo.setSerial_no(serial_no);
			vo.setCheck_flag(check_flag);
			vo.setInput_man(input_operatorCode);
			local.checkGradeTotal(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.checkOK",clientLocale)%> ！");//审核成功
	window.returnValue = 1;
	location =  "client_mark_grade_check_list.jsp";
</SCRIPT>
<%local.remove();%>