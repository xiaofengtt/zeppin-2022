<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ȡ����ID 
String[] s = request.getParameterValues("serial_no");
int serial_no;

//��ȡ����
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();

//��ѡ�в�Ϊ��
if (s != null){
	for(int i = 0;i < s.length; i++){
		serial_no = Utility.parseInt(s[i], 0);
		
		if(serial_no != 0){
			vo.setRefund_date(new Integer(Utility.getCurrentDate()));
			vo.setPre_serial_no(new Integer(serial_no));
			vo.setInput_man(input_operatorCode);
			local.refundPreMoneyDetail(vo);
		}
	}
}
%>
<%local.remove();%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
sl_alert("�˿�ɹ�!");
location = "reserve_list.jsp";
</SCRIPT>

