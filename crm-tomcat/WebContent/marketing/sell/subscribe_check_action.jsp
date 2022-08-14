<%@ page contentType="text/html; charset=GBK" import="enfo.crm.service.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer if_mail = Utility.parseInt(Utility.trimNull(request.getParameter("if_mail")), new Integer(0));

boolean bSuccess = false;
ContractVO vo = new ContractVO();
CustomerVO cust_vo = new CustomerVO();
CustIntegralVO custIntegral_vo = new CustIntegralVO();

ContractLocal contract = EJBFactory.getContract();
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
IntegralService service = new IntegralService();//积分
CustomerService cust_service = new CustomerService();

Integer orderNo = new Integer(0);
Integer cust_id = new Integer(0);
Integer rule_id = new Integer(Argument.getSyscontrolValue("INTEGRAL"));
BigDecimal amount = new BigDecimal(0);

String serial = "";
Integer c = new Integer(0);
String[] s = request.getParameterValues("serial_no");
for (int i = 0; i<s.length; i++) {	
	IntegralCalVO integralVo = new IntegralCalVO();
	Integer serial_no = Utility.parseInt(s[i], new Integer(0));
	try{
		if(serial_no.intValue() > 0) {
			String[] ret = service.getContractInfo(serial_no, rule_id);//计算积分准备
			cust_id = Utility.parseInt(ret[2],new Integer(0));
	
			if(i>0) serial += "&serial_no="+serial_no;		
			
			//审核
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
	//		vo.setIf_mail(if_mail);
			contract.contractCheck(vo);
			
			// add sys message
			ContractVO c_vo = new ContractVO();
			c_vo.setSerial_no(serial_no);
			c_vo.setInput_man(input_operatorCode);
			List contractList = contract.load(vo);
	
			Integer r_cust_id = new Integer(0);
			Integer r_product_id = new Integer(0);
			if (contractList.size()>0) {
				Map m = (Map)contractList.get(0);
				r_cust_id = (Integer)m.get("CUST_ID");
				r_product_id = (Integer)m.get("PRODUCT_ID");
			}
			String cust_name = Argument.getCustomerName(r_cust_id, input_operatorCode);
			String product_name = Argument.getProductName(r_product_id);		
	
			Argument.addSysMessage(r_cust_id, "认购合同审核通过"
				, "认购合同审核通过，客户名称："+cust_name+"，产品名称："+product_name, input_operatorCode);
			
			//反向查询	
			cust_vo.setCust_id(cust_id);
			customer_inst.synchro_customers(cust_vo);
			//同步客户
			if(Argument.getSyscontrolValue("HUAAO1")==1)
				cust_service.sendCustToSAP(cust_id,input_operatorCode);
					
			if(rule_id.intValue()!=0){
				//计算
				amount = Utility.parseDecimal(ret[0],new BigDecimal(0));
				orderNo = Utility.parseInt(ret[1],new Integer(0));
				cust_id = Utility.parseInt(ret[2],new Integer(0));
				product_id = Utility.parseInt(ret[3],new Integer(0));
				orderNo = new Integer(orderNo.intValue()+1);
				
				integralVo.setAmount(amount);
				integralVo.setRuleID(rule_id);
				integralVo.setOrderNo(orderNo);
							
				custIntegral_vo.setCust_id(cust_id);
				custIntegral_vo.setBusi_type(new Integer(1));
				custIntegral_vo.setBusi_id(serial_no);
				custIntegral_vo.setAmount(amount);
				custIntegral_vo.setRule_id(rule_id);		
				custIntegral_vo.setProduct_id(product_id);
				custIntegral_vo.setInput_man(input_operatorCode);
				
				service.integralCal(integralVo,custIntegral_vo,1);//积分			
			}
		}
	}catch(Exception e){
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">window.location='subscribe_check_list.jsp';</script>");
		return;
	}
}

bSuccess = true;

contract.remove();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%if(bSuccess){%>
	if(<%=user_id%>==7){
		if(sl_confirm("打印认购及缴款确认书")){	
			var url = "<%=request.getContextPath()%>/marketing/sell/subscribe_check_print.jsp?serial_no=<%=s[0]+serial%>";
			var v = showModalDialog(url,'','dialogWidth:900px;dialogHeight:1000px;status:0;help:0;');
		}else{
			sl_check_ok("subscribe_check_list.jsp?contract_bh=<%=contract_bh%>&product_id=<%=product_id%>");
		}
		if(v==1){
		sl_check_ok("subscribe_check_list.jsp?contract_bh=<%=contract_bh%>&product_id=<%=product_id%>");
		}
	}else{
		sl_check_ok("subscribe_check_list.jsp?contract_bh=<%=contract_bh%>&product_id=<%=product_id%>");
	}
<%}%>
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post">
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
