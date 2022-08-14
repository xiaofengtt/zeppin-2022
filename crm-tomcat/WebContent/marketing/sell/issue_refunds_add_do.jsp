<%@ page contentType="text/html; charset=GBK" import="java.math.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String flag = Utility.trimNull(request.getParameter("flag"));//标记：new新增 edit编辑
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));//退款申请序号
Integer ht_serial_no = Utility.parseInt(request.getParameter("ht_serial_no"), new Integer(0));//合同序号
BigDecimal fee = Utility.parseDecimal(Utility.trimNull(request.getParameter("fee")), null);//退款手续费
Integer sq_date = Utility.parseInt(request.getParameter("sq_date"),null);//退款日期
Integer t_rg_fee =Utility.parseInt(request.getParameter("t_rg_fee"),new Integer(0));//是否退认购费
String summary = Utility.trimNull(request.getParameter("summary"));//退款原因
BigDecimal ht_fee = Utility.parseDecimal(Utility.trimNull(request.getParameter("ht_fee")), null);//退款认购/申购费
Integer rebate_flag =Utility.parseInt(request.getParameter("rebate_flag"),new Integer(0));//退款类别
BigDecimal sq_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("sq_money")), null);//退款金额

SqstopContractLocal local = EJBFactory.getSqstopContract();//发行期退款申请Bean
SqstopContractVO vo = new SqstopContractVO();

vo.setFee(fee);
vo.setSq_date(sq_date);
vo.setT_rg_fee(t_rg_fee);
vo.setReason(summary);
vo.setInput_man(input_operatorCode);
vo.setRebate_flag(rebate_flag);
vo.setHt_fee(ht_fee);
vo.setSq_money(sq_money);

if(serial_no != null && !(serial_no.equals(new Integer(0))) && flag.equals("edit")){
	vo.setSerial_no(serial_no);
	local.modi(vo);//修改
}
else{
	vo.setHt_serial_no(ht_serial_no);
	local.append(vo);//新增	
}
%>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">
	sl_update_ok("issue_refunds_list.jsp");
</script>
<%local.remove();%>