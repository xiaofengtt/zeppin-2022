<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ö���
ToMoneyAccountLocal local = EJBFactory.getToMoneyAccount();
ToMoneyAccountVO vo = new ToMoneyAccountVO();
//���ҳ�洫�ݲ���
String[] serial_no_array = request.getParameterValues("s_id");
Integer serial_no = new Integer(0);
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String sContract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
//url����
String tempUrl = "";
tempUrl = tempUrl+"&contract_sub_bh="+sContract_sub_bh;
tempUrl = tempUrl+"&product_id="+product_id;
//������
 if (serial_no_array != null) {
	for(int i = 0;i < serial_no_array.length; i++){
		serial_no = Utility.parseInt(serial_no_array[i], new Integer(-1));

       	if(serial_no.intValue() != -1){
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			local.restoreCheck(vo);
		}
	}
 }
local.remove();
 %>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">
	sl_alert("<%=LocalUtilis.language("message.successfulRecovery",clientLocale)%> ��");//�ʽ��˻ָ��ɹ�
	location="funds_recheck_list.jsp?1=1&<%=tempUrl%>";
</script>
