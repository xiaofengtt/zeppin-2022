<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取主键ID 
String[] s = request.getParameterValues("serial_no");
int serial_no;

//获取对象
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();

//若选中不为空
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

