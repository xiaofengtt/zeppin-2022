<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
GradeStandardLocal gs_local = EJBFactory.getGradStandard();
GradeStandardVO gs_vo = new GradeStandardVO();

String[] gradeId = request.getParameterValues("serial_no");
Integer serial_no = new Integer(0);
Integer grade_id = new Integer(0);
if (gradeId != null) {
	for (int i = 0; i < gradeId.length; i++) {
		String[] ss = Utility.splitString(gradeId[i], "#");
		if (ss != null) {
			serial_no = Utility.parseInt(ss[0], new Integer(0));
			grade_id = Utility.parseInt(ss[1], new Integer(0));
		}
		if (!serial_no.equals(new Integer(0))) {
			gs_vo.setGrade_id(grade_id);
			gs_vo.setSerial_no(serial_no);
			gs_vo.setInput_man(input_operatorCode);
			gs_local.delGradeState(gs_vo);
		}
	}
}
%>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("client_level_list.jsp");
</SCRIPT>
<%gs_local.remove();%>