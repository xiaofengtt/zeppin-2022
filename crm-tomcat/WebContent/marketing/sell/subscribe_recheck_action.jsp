<%@ page contentType="text/html; charset=GBK" import="enfo.crm.service.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ContractLocal contract = EJBFactory.getContract();
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//ͬ���ÿͻ�����

IntegralCalVO integralVo = new IntegralCalVO();
CustomerVO cust_vo = new CustomerVO();
CustIntegralVO custIntegral_vo = new CustIntegralVO();
ContractVO vo = new ContractVO();
String[] s = request.getParameterValues("selectbox");
IntegralService service = new IntegralService();//����
Integer orderNo = new Integer(0);
Integer cust_id = new Integer(0);
Integer product_id = new Integer(0);
BigDecimal amount = new BigDecimal(0);
Integer rule_id = new Integer(Argument.getSyscontrolValue("INTEGRAL"));

Integer serial_no=null;
boolean bsuccess=false;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		integralVo = new IntegralCalVO();
		serial_no = Utility.parseInt(s[i], new Integer(0));
		if(serial_no.intValue() != 0)
		{  
			String[] ret = service.getContractInfo(serial_no,rule_id);//�������׼��
			cust_id = Utility.parseInt(ret[2],new Integer(0));
			try{
				//��˻ָ�
				vo.setSerial_no(serial_no);
				vo.setInput_man(input_operatorCode);
				contract.recheckContract(vo);
				//�����ѯ	
				cust_vo.setCust_id(cust_id);
				customer_inst.synchro_customers(cust_vo);
				
				if(rule_id.intValue()==1){
					//����
					amount = Utility.parseDecimal(ret[0],new BigDecimal(0));
					orderNo = Utility.parseInt(ret[1],new Integer(0));				
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
					
					service.integralCal(integralVo,custIntegral_vo,-1);//����				
				}
			}catch(Exception e){
				out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
				out.println("<script type=\"text/javascript\">window.location='subscribe_recheck_list.jsp';</script>");
				return;
			}
			
			bsuccess=true;
		}
	}
}
%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(bsuccess){%>	
sl_alert('<%=LocalUtilis.language("message.recoverySuccess",clientLocale)%> !');//�ָ��ɹ�
location="subscribe_recheck_list.jsp";
<%}%>
</SCRIPT>
<%contract.remove();%>