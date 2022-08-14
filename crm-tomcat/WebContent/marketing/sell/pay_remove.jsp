<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得对象
MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO vo = new MoneyDetailVO();
//获得页面传递参数
String[] dataArray = request.getParameterValues("serial_no");
String[] tempArray = null;
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String data = "";
Integer check_flag ;
Integer serial_no;
//逐个删除
for(int i=0;i < dataArray.length; i++){
	data = Utility.trimNull(dataArray[i]);
	tempArray = data.split("_");
	serial_no = Utility.parseInt(tempArray[0], new Integer(0));
	check_flag = Utility.parseInt(tempArray[1], new Integer(0));
	//删除
	if(serial_no.intValue() != 0){
		vo = new MoneyDetailVO();
		vo.setSerial_no(serial_no);
		vo.setInput_man(input_operatorCode);

		if(check_flag.intValue() == 2){
			continue;
		}
		else{
			moneyDetailLocal.delete(vo);
		}
	}
}
moneyDetailLocal.remove();
 %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("pay_list.jsp?q_check_flag=1&product_id=<%=product_id%>");
</SCRIPT>