<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得对象
ToMoneyAccountLocal local = EJBFactory.getToMoneyAccount();
ToMoneyAccountVO vo = new ToMoneyAccountVO();
//获得页面传递参数
String[] serial_no_array = request.getParameterValues("s_id");
Integer serial_no = new Integer(0);
Integer to_bank_date=Utility.parseInt(request.getParameter("to_bank_date"),new Integer(0));
Integer q_product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer qs_date = Utility.parseInt(request.getParameter("qs_date"),new Integer(0));
String sContract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer sbf_serial_no = Utility.parseInt(request.getParameter("sbf_serial_no"),new Integer(0));
//url设置
String tempUrl = "";
tempUrl = tempUrl+"&qs_date="+qs_date;
tempUrl = tempUrl+"&contract_sub_bh="+sContract_sub_bh;
tempUrl = tempUrl+"&product_id="+q_product_id;
//逐个审核
 if (serial_no_array != null) {
	for(int i = 0;i < serial_no_array.length; i++){
		serial_no = Utility.parseInt(serial_no_array[i], new Integer(-1));

       	if(serial_no.intValue() != -1){
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			vo.setTo_bank_date(to_bank_date);
			vo.setSbf_serial_no(sbf_serial_no);
			local.check(vo);
		}
	}
  }
local.remove();
 %>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">
	sl_alert("<%=LocalUtilis.language("message.moneyIntoAccountAudit",clientLocale)%> ！");
	location="funds_check_list.jsp?1=1&<%=tempUrl%>";
</script>