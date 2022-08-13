<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.sms.*,com.whaty.platform.sms.basic.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../teacher/pub/priv.jsp"%>
<%
		String id = request.getParameter("id");
		try {
			try{
				SmsManagerPriv smsPriv = teacherOperationManage.getSmsManagerPriv();
				SmsManage smsManage = teacherOperationManage.getSmsManage(smsPriv);
				List list = new ArrayList();
				list.add(id);
				int count = smsManage.deleteSmsMessage(list);
				if(count > 0)
					out.print("<script>alert('短信删除成功！');window.navigate('sms_list.jsp');</script>");
				else
					out.print("<script>alert('短信删除失败!');window.history.back();</script>");
		%>
		<%
					}
					catch(Exception e)
					{
					%>
					<script>
					alert("<%=e.getMessage().trim()%>");
					window.history.back(-1);
					</script>
					<%
					return;
					}
%>
		
		
		<%			
			} catch (Exception e) {
				out.print(e.getMessage());
			}
%>
