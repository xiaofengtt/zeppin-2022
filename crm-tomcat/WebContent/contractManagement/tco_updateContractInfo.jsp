<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,java.math.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractLocal tcoContractLocal = EJBFactory.getTcoContract();
TcoContractVO tcoContractVO = new TcoContractVO();
TcoContractPayPlanLocal tcoContractPayPlanLocal = EJBFactory.getTcoContractPayPlan();
TcoContractPayPlanVO tcoContractPayPlanVO = new TcoContractPayPlanVO();
TcoContractPointsLocal tcoContractPointsLocal = EJBFactory.getTcoContractPoints();
TcoContractPointsVO tcoContractPointsVO = new TcoContractPointsVO();
boolean bSuccess = false;
Integer coContract_id=new Integer(0);
/*********因为增加了一个附件上传，所以原先request方法取值的改为 file*******************/
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
    file.parseRequest();
	coContract_id=Utility.parseInt(file.getParameter("coContract_id"),new Integer(0));
	tcoContractVO.setCocontract_id(coContract_id);	
	tcoContractVO.setCust_id(Utility.parseInt(file.getParameter("cust_id"),new Integer(0)));
	tcoContractVO.setCocontract_sub_bh(Utility.trimNull(file.getParameter("cocontract_sub_bh")));
	tcoContractVO.setSign_date(Utility.parseInt(file.getParameter("sign_date"),new Integer(0)));
	tcoContractVO.setStart_date(Utility.parseInt(file.getParameter("start_date"),new Integer(0)));
	tcoContractVO.setEnd_date(Utility.parseInt(file.getParameter("end_date"),new Integer(0)));
	tcoContractVO.setMain_end_date(Utility.parseInt(file.getParameter("main_end_date"),new Integer(0)));
	tcoContractVO.setHt_money(Utility.parseDecimal(file.getParameter("ht_money"),new BigDecimal(0.00)));
	tcoContractVO.setPayment_type(Utility.trimNull(file.getParameter("payment_type")));
	tcoContractVO.setCocontract_type(Utility.trimNull(file.getParameter("cocontract_type")));
	tcoContractVO.setInput_man(input_operatorCode);
	tcoContractLocal.modi(tcoContractVO);

	bSuccess = true;
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("合同信息修改成功 ");//合同信息修改成功
	window.returnValue = 1;
	location =  "tcoContract_update.jsp?coContract_id="+<%=coContract_id%>;
</SCRIPT>
<%
tcoContractLocal.remove();
 %>
