<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
QuotientAffirmVO vo = new QuotientAffirmVO();
SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();

String[] s = request.getParameterValues("q_subscribeId");
Integer syDate = Utility.parseInt(Utility.trimNull(request.getParameter("q_syDate")),new Integer(0));
System.out.println(syDate);
int subscribeId;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		subscribeId = Utility.parseInt(s[i], 0);

		if(subscribeId != 0 )
		{
			vo.setSubscribe_id(new Integer(subscribeId));
			vo.setEnd_date(syDate);
			vo.setInput_man(input_operatorCode);
			try{
				subscribe.settle(vo);
			}catch(Exception e){
				subscribe.remove();
				if(e.getMessage().lastIndexOf("11") > -1){
					out.println("<script type=\"text/javascript\">alert('非信托产品收益信息不存在')</script>");
				} else if(e.getMessage().lastIndexOf("12") > -1){
					out.println("<script type=\"text/javascript\">alert('非信托产品认购信息不存在')</script>");
				} else if(e.getMessage().lastIndexOf("13") > -1){
					out.println("<script type=\"text/javascript\">alert('非信托产品份额信息不存在')</script>");
				} else {
					out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
				}
				out.println("<script type=\"text/javascript\">window.location='subscribe_settle.jsp';</script>");
				return;
			}
			
			
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.settleOK",clientLocale)%> ");//合同结清成功
	window.returnValue = 1;
	location =  "subscribe_settle.jsp";
</SCRIPT>
<%subscribe.remove();%>

