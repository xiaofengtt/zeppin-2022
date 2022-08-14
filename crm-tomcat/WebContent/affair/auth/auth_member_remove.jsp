<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
AuthorizationCustsVO authorizationCustsVO = new AuthorizationCustsVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();

Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
//if("self".equals(Utility.trimNull(request.getParameter("action"))) ){
//	vo.setTeam_id(Utility.parseInt(request.getParameter("number"),new Integer(0)));
//	vo.setInput_man(input_operatorCode);
	
//	try{
//		tmanagerteams_Bean.delete(vo);	
//	}catch(Exception e){
//		String message = enfo.crm.tools.LocalUtilis.language("message.deleteFail", clientLocale);//É¾³ýÊ§°Ü
//		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\");</script>");
//	}
//}else{
	String[] s = request.getParameterValues("selectbox"); 
	String str = "";
	String [] items = new String[2];
	if (s != null)
	{	
		for(int i = 0;i < s.length; i++)
		{	
			str = Utility.trimNull(s[i]);
			if(str != null)
			{
				items = Utility.splitString(str,"$");
				authorizationCustsVO.setSerial_no(Utility.parseInt(items[0], new Integer(0)));
				authorizationCustsVO.setInput_man(input_operatorCode);
				authorizationLocal.delete(authorizationCustsVO);
			}
		}
	}
//}

authorizationLocal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	sl_remove_ok("auth_member_list.jsp?ca_id="+<%=ca_id%> + "&ca_name="+'<%=ca_name%>');
</SCRIPT>
