\<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//获取主键ID 
String[] s = request.getParameterValues("serial_no");
int del_flag = Utility.parseInt(Utility.trimNull(request.getParameter("del_flag")),0);

PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
PreContractCrmVO pre_vo = new PreContractCrmVO();
boolean send_sms = Argument.getValueOfTSysControlByFlagType("SEND_SMS_FOR_DEL_PRECONTRACT", "0") == 1;

//若选中不为空
if (s != null){
	for(int i = 0;i < s.length; i++){
		int serial_no = Utility.parseInt(s[i], 0);
		
		if (serial_no > 0){
			pre_vo.setSerial_no(new Integer(serial_no));
			pre_vo.setInput_man(input_operatorCode);
			preContract.delete(pre_vo);

			List list = preContract.queryPreContractCrm(pre_vo, null, 1, -1).getRsList();
			if (list.size()>0) {
				Map map = (Map)list.get(0);
				Integer linkMan = Utility.parseInt((Integer)map.get("LINK_MAN"), new Integer(0));
				String servManName = Utility.trimNull(map.get("LINK_MAN_NAME"));
				String productName = Utility.trimNull(map.get("PREPRODUCT_NAME"));
				String mobile = Utility.trimNull(map.get("LINK_MAN_MOBILE"));
				//发送短信
				String smsContent = servManName+"，您好：您的预约已作废。购买产品："+productName;
				if (linkMan.intValue()>0 && linkMan.intValue()!=input_operatorCode.intValue() && send_sms && !"".equals(mobile))
					Argument.sendSMSSimple(input_operatorCode+"", mobile
						,smsContent, new Integer(1), "待发", new Integer(0), input_operatorCode);			
			}
		}
	}
}
%>
<%preContract.remove();%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(del_flag == 1){%>
	sl_alert("删除成功!");
<%}else{%>
	sl_alert("作废成功!");
<%}%>
	location = "reserve_list.jsp";
</SCRIPT>

