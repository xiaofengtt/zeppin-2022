<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>


<%
//获得对象及结果集
ApplyreachLocal apply_reach = EJBFactory.getApplyreach();
ApplyreachVO vo = new ApplyreachVO();

//获得主键
String[] checked_serial_no = request.getParameterValues("serial_no");
int t_serial_no;

if (checked_serial_no!=null){
	for(int i=0;i<checked_serial_no.length;i++){
		t_serial_no = Utility.parseInt(checked_serial_no[i], 0);
		
		if(t_serial_no != 0){
			vo.setSerial_no(new Integer(t_serial_no));
			vo.setInput_man(input_operatorCode);
			apply_reach.delete(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	<%apply_reach.remove();%>
	sl_remove_ok("apply_purchase_list.jsp");
</SCRIPT>

