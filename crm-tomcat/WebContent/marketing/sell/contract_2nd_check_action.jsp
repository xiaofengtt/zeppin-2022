<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer contract_type = Utility.parseInt(request.getParameter("contract_type"), new Integer(-1));  
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(-1));  
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));  
String prov_level = Utility.trimNull(request.getParameter("prov_level")).trim();  
String channel_coopertype = Utility.trimNull(request.getParameter("channel_coopertype")).trim();  
Integer valid_period = Utility.parseInt(request.getParameter("valid_period"), null); 
Integer period_unit = Utility.parseInt(request.getParameter("period_unit"), null); 
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0)); 
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0)); 
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0)); 
String ht_bank_id = Utility.trimNull(request.getParameter("ht_bank_id")).trim();  
String ht_bank_sub_name = Utility.trimNull(request.getParameter("ht_bank_sub_name")).trim();  
String jg_wtrlx2 = Utility.trimNull(request.getParameter("jg_wtrlx2")).trim();  
Integer city_serial_no = Utility.parseInt(request.getParameter("city_serial_no"), null);
int check_flag = Utility.parseInt(request.getParameter("check_flag"), 0);
// 返回用到的原查询条件	

String sRetQuery = Utility.trimNull(request.getParameter("sRetQuery"));

ContractVO vo = new ContractVO();
ContractLocal contract = EJBFactory.getContract();
ApplyreachLocal apply = EJBFactory.getApplyreach();
ApplyreachVO appvo = new ApplyreachVO();

boolean bSuccess = false;
if ("POST".equals(request.getMethod())) {

	vo.setContract_type(contract_type);
	vo.setSerial_no(serial_no);
	vo.setProv_flag(prov_flag);
	vo.setProv_level(prov_level);
	vo.setChannel_cooperation(channel_coopertype);
	vo.setWith_bank_flag(with_bank_flag);
	vo.setHt_bank_id(ht_bank_id);
	vo.setHt_bank_sub_name(ht_bank_sub_name);
	vo.setWith_secuity_flag(with_security_flag);
	vo.setWith_private_flag(with_private_flag);
	vo.setCity_serialno(city_serial_no);
	vo.setJg_wtrlx2(jg_wtrlx2);
	vo.setValid_period(valid_period);
	vo.setPeriod_unit(period_unit);
	vo.setInput_man(input_operatorCode);
	contract.modiContractFor2ndEdit(vo);
	if(check_flag == 1){
		//认购审核
		if(contract_type.intValue() == 1)
			contract.contractCheck(vo);
		//申购审核
		if(contract_type.intValue() == 2){
			appvo.setSerial_no(serial_no);
			appvo.setInput_man(input_operatorCode);
			apply.checkApplyreachContract(appvo);
		}
	}
	bSuccess = true;
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.pass",clientLocale)%> ");//审核通过
	location.href = "contract_2nd_edit_list.jsp?<%=sRetQuery%>";
</SCRIPT>
<%apply.remove();%>