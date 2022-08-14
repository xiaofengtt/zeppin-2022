<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得对象
GradeIndexLocal gi_local = EJBFactory.getGradIndex();
GradeIndexVO gi_vo = new GradeIndexVO();

//gradeId=index_id+#+grade_id的组合，此处要拆分
String[] gradeId = request.getParameterValues("index_id");
Integer index_id = new Integer(0);
Integer grade_id = new Integer(0);

if (gradeId != null) {
	for (int i = 0; i < gradeId.length; i++) {
		String[] ss = Utility.splitString(gradeId[i], "#");
		if (ss != null) {
			index_id = Utility.parseInt(ss[0], new Integer(0));
			grade_id = Utility.parseInt(ss[1], new Integer(0));
		}
		if (!index_id.equals(new Integer(0)) && !grade_id.equals(new Integer(0))) {
			gi_vo.setGrade_id(grade_id);
			gi_vo.setIndex_id(index_id);
			gi_vo.setOp_code(input_operatorCode);
			gi_local.delGradeIndex(gi_vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("client_index_list.jsp");
</SCRIPT>
<%gi_local.remove();%>