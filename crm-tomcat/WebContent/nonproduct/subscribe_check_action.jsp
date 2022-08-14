<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
SubscribeVO vo = new SubscribeVO();
SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();

String[] s = request.getParameterValues("q_subscribeId");
String[] c = request.getParameterValues("cust_id");

int subscribeId;
int cust_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		subscribeId = Utility.parseInt(s[i], 0);
		cust_id = Utility.parseInt(c[i], 0);
		if(subscribeId != 0 && cust_id != 0)
		{
			vo.setSubscribe_id(new Integer(subscribeId));
			vo.setCust_id(new Integer(cust_id));
			vo.setCheck_flag(new Integer(2));
			vo.setInput_man(input_operatorCode);
			try{
				subscribe.check(vo);
			}catch(Exception e){
				subscribe.remove();
				if(e.getMessage().lastIndexOf("11") > -1){
					out.println("<script type=\"text/javascript\">alert('非信托产品认购信息不存在')</script>");
				} else if(e.getMessage().lastIndexOf("12") > -1){
					out.println("<script type=\"text/javascript\">alert('非信托产品认购信息已审核')</script>");
				} else {
					out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
				}
				out.println("<script type=\"text/javascript\">window.location='subscribe_check.jsp';</script>");
				return;
			}
			
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.pass",clientLocale)%> ");//审核通过
	window.returnValue = 1;
	location =  "subscribe_check.jsp";
</SCRIPT>
<%subscribe.remove();%>

