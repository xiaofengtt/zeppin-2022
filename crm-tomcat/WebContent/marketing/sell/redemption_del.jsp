<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取主键ID 
String[] s = request.getParameterValues("serial_no");
int serial_no;

//获得对象及结果集
RedeemLocal redeem = EJBFactory.getRedeem();
RedeemVO vo = new RedeemVO();

//若选中不为空
if (s != null){
	for(int i = 0;i < s.length; i++){
		serial_no = Utility.parseInt(s[i], 0);
		
		if(serial_no != 0){
			vo.setSerial_no(new Integer(serial_no));
			vo.setInput_man(input_operatorCode);	
			
			redeem.delete(vo);
		}
	}
}
%>

<%redeem.remove();%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("redemption_list.jsp");
</SCRIPT>
