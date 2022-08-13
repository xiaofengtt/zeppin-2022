<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<body>
<script language="javascript">
<s:if test="succ">
alert("<s:property value="msg"/>");
window.navigate("/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="course_id"/>");
</s:if>
<s:else>
alert("<s:property value="msg"/>");
window.history.back();
</s:else>
</script>
</body>