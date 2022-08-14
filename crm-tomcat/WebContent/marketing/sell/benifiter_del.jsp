<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ò���
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
Integer contract_id = Utility.parseInt(request.getParameter("contract_id"), null);

//��ȡ����ID 
String[] s = request.getParameterValues("serial_no");
int serial_no;

//��ȡ����
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo_ben = new BenifitorVO();

//��ѡ�в�Ϊ��
if (s != null){
	for(int i = 0;i < s.length; i++){
		serial_no = Utility.parseInt(s[i], 0);
		
		if(serial_no != 0){
			vo_ben.setSerial_no(new Integer(serial_no));
			vo_ben.setInput_man(input_operatorCode);	
			
			benifitor.delete(vo_ben);
		}
	}
}
%>
<%benifitor.remove();%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("benifiter_list.jsp?contract_id=<%=contract_id%>&sQuery=<%=sQuery%>");
</SCRIPT>
