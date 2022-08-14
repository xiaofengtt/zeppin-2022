<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*"  %>
<%@ include file="/includes/operator.inc" %>
<%
BenifitorLocal local = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();
int iSerial_no;
String[] s = request.getParameterValues("s_id");
int iAction=Utility.parseInt(request.getParameter("s_action"),0);
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		iSerial_no = Utility.parseInt(s[i], -1);
       if(iSerial_no != -1)
		{
			vo.setBook_code(input_bookCode);
			vo.setSerial_no(new Integer(iSerial_no));
			vo.setInput_man(input_operatorCode);
			vo.setCheck_flag(new Integer(iAction));
			local.checkBenifitorProv(vo);
		}
	}
}
local.remove();
%>

<script src="/includes/default.vbs" language="vbscript"></script>
<script src="/includes/default.js" language="javascript"></script>

<script language="javascript">
<%
if(iAction==1) {	
%>
	sl_alert("收益级别审核成功！");
<%}
else
{
%>
	sl_alert("审核未通过！");
<%}%>
location="benifiter_level_check.jsp";
</script>








