<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript">
			alert("<s:property value="interactionMsg"/>");
			<s:if test="interactionTogo!=null">
				location.href = "<s:property value="interactionTogo"/>";
			</s:if> <s:else>
				window.history.back();
			</s:else>
		</script>
	</head>
	<body>
	</body>
</html>
