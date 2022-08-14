<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
TmanagerteamsVO vo = new TmanagerteamsVO();
TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();

if("self".equals(Utility.trimNull(request.getParameter("action"))) ){
	vo.setTeam_id(Utility.parseInt(request.getParameter("number"),new Integer(0)));
	vo.setInput_man(input_operatorCode);
	tmanagerteams_Bean.delete(vo);	

}else{
	String[] s = request.getParameterValues("btnCheckbox");
	int _id;
	if (s != null){
		for(int i = 0;i < s.length; i++){
			_id = Utility.parseInt(s[i], 0);
			if(_id != 0){	
				vo.setTeam_id(new Integer(_id));
				vo.setInput_man(input_operatorCode);	
				tmanagerteams_Bean.delete(vo);	
			}
		}
	}
}

tmanagerteams_Bean.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	sl_remove_ok("<%=request.getContextPath()%>/affair/base/team.jsp");
</SCRIPT>
