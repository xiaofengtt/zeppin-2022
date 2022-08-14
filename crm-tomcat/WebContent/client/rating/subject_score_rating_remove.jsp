<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象及结果集
RatingVO vo = new RatingVO();
SubjectScoreRatingLocal subject_score_rating = EJBFactory.getSubjectScoreRating();
String[] s = request.getParameterValues("rating_id");
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
int rating_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		rating_id = Utility.parseInt(s[i], 0);
		if(rating_id != 0)
		{
			vo.setRating_id(new Integer(rating_id));
			vo.setInput_man(input_man);
			subject_score_rating.delete_subjectScoreRating(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//删除成功
	window.returnValue = 1;
	location =  "subject_score_rating.jsp";
</SCRIPT>
<%subject_score_rating.remove();%>
