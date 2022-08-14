<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,java.math.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractMaintainLocal tcoContractMaintainLocal = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO tcoContractMaintainVO = new TcoContractMaintainVO();
boolean bSuccess = false;
Integer maintain_id=new Integer(0);
/*********因为增加了一个附件上传，所以原先request方法取值的改为 file*******************/
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
    file.parseRequest();
	maintain_id=Utility.parseInt(file.getParameter("maintain_id"),new Integer(0));
	tcoContractMaintainVO.setMaintain_id(maintain_id);
	tcoContractMaintainVO.setCust_id(Utility.parseInt(file.getParameter("cust_id"),new Integer(0)));
	tcoContractMaintainVO.setCoContractMaintain_sub_bh(Utility.trimNull(file.getParameter("coContractMaintain_sub_bh")));
	tcoContractMaintainVO.setMain_period(Utility.parseInt(file.getParameter("main_period"),new Integer(0)));
	tcoContractMaintainVO.setMain_period_unit(Utility.parseInt(file.getParameter("main_period_unit"),new Integer(0)));
	tcoContractMaintainVO.setMain_pro_name(Utility.trimNull(file.getParameter("main_pro_name")));
	tcoContractMaintainVO.setCollect_time(Utility.parseInt(file.getParameter("collect_time"),new Integer(0)));
	tcoContractMaintainVO.setStart_date(Utility.parseInt(file.getParameter("start_date"),new Integer(0)));
	tcoContractMaintainVO.setEnd_date(Utility.parseInt(file.getParameter("end_date"),new Integer(0)));
	tcoContractMaintainVO.setHt_money(Utility.parseDecimal(file.getParameter("ht_money"),new BigDecimal(0.00)));
	tcoContractMaintainVO.setWh_money(Utility.parseDecimal(file.getParameter("wh_money"),new BigDecimal(0.00)));
	tcoContractMaintainVO.setMain_summary(Utility.trimNull(file.getParameter("main_summary")));
	tcoContractMaintainVO.setInput_man(input_operatorCode);
	tcoContractMaintainLocal.modi(tcoContractMaintainVO);
	bSuccess = true;
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("维护合同修改成功 ");//维护合同修改成功
	window.returnValue = 1;
	location =  "tcoContractMaintain_update.jsp?maintain_id="+<%=maintain_id%>;
</SCRIPT>
<%
tcoContractMaintainLocal.remove();
 %>
