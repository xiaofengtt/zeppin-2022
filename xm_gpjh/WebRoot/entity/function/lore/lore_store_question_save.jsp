<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<script>
	alert('<s:property value="msg" />');
    parent.location='/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value='lore_id'/>&course_id=<s:property value='course_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>';
</script>

