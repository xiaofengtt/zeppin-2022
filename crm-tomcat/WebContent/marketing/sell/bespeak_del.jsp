<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ȡ����ID 
String[] s = request.getParameterValues("serial_no");
int serial_no;

//��ȡ����
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();

//��ѡ�в�Ϊ��
if (s != null){
	for(int i = 0;i < s.length; i++){
		serial_no = Utility.parseInt(s[i], 0);
		
		if(serial_no != 0){
			pre_vo.setSerial_no(new Integer(serial_no));
			pre_vo.setInput_man(input_operatorCode);	
			
			preContract.delete(pre_vo);
		}
	}
}
%>
<%preContract.remove();%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("bespeak_list.jsp");
</SCRIPT>

