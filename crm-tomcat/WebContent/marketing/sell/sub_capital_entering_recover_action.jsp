<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
TMoneyDataLocal local = EJBFactory.getTMoneyData();
TMoneyDataVO vo = new TMoneyDataVO();

String[] serial_nos = request.getParameterValues("serial_no");
Integer ht_type = Utility.parseInt(Utility.trimNull(request.getParameter("ht_type")), new Integer(0));

if(serial_nos != null){
	for(int i = 0; i < Utility.parseInt(serial_nos.length,0); i++){
		vo.setContract_id(Utility.parseInt(serial_nos[i].split("\\$")[0],new Integer(0)));
		vo.setHt_type(Utility.parseInt(serial_nos[i].split("\\$")[1],new Integer(0)));
		vo.setInput_man(input_operatorCode);
		local.combineMoneyDataRestore(vo);
	}
}

local.remove();
%>
<script type="text/javascript">
    alert("认购合同生成恢复成功！");
    location = "sub_capital_entering_recover.jsp";
</script>