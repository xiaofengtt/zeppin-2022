<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractPayPlanLocal tcoContractPayPlanLocal = EJBFactory.getTcoContractPayPlan();
TcoContractPayPlanVO tcoContractPayPlanVO = new TcoContractPayPlanVO();

int payPlan_id;
DocumentFileToCrmDB file=null;
tcoContractPayPlanVO.setInput_man(input_operatorCode);
file = new DocumentFileToCrmDB(pageContext);
file.parseRequest();
Integer coContract_id=Utility.parseInt(file.getParameter("coContract_id"),new Integer(0));
String action=request.getParameter("action");
if(action.equals("del")){
	String[] s = file.getParameterValues("payPlan_id");
	if (s != null)
	{
		for(int i = 0;i < s.length; i++)
		{
			payPlan_id = Utility.parseInt(s[i], 0);
			if(payPlan_id != 0)
			{
				tcoContractPayPlanVO.setPayPlan_id(new Integer(payPlan_id));
				tcoContractPayPlanLocal.delete(tcoContractPayPlanVO);
			}
		}
	}
}
if(action.equals("add")){	
	Integer pay_num=Utility.parseInt(file.getParameter("pay_num"),new Integer(0));
	String pay_num_name=file.getParameter("pay_num_name");
	Integer exp_date=Utility.parseInt(file.getParameter("exp_date"),new Integer(0));
	BigDecimal pay_money=Utility.parseDecimal(Utility.trimNull(file.getParameter("pay_money")),new BigDecimal(0.00));
	BigDecimal pay_rate=Utility.parseDecimal(Utility.trimNull(file.getParameter("pay_rate")),new BigDecimal(0.00));
	String pay_summary=file.getParameter("pay_summary");
	tcoContractPayPlanVO.setCocontract_id(coContract_id);
		tcoContractPayPlanVO.setPay_num(pay_num);
		tcoContractPayPlanVO.setPay_num_name(pay_num_name);
		tcoContractPayPlanVO.setExp_date(exp_date);
		tcoContractPayPlanVO.setPay_rate(pay_rate);
		tcoContractPayPlanVO.setPay_money(pay_money);
		tcoContractPayPlanVO.setPay_summary(pay_summary);
	
	tcoContractPayPlanLocal.append(tcoContractPayPlanVO);
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("²Ù×÷³É¹¦");
	window.returnValue = 1;
	location =  "tcoContract_update.jsp?coContract_id="+<%=coContract_id%>;
</SCRIPT>
<%
tcoContractPayPlanLocal.remove();
 %>
