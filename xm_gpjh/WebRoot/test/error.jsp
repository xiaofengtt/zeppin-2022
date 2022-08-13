<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>
<s:property value="interceptError" escape="false"/>
<s:set value="%{@com.whaty.platform.sso.web.action.SsoConstant@SSO_USER_SESSION_KEY_BAK}" name="sso"/>
<s:if test="#session[#sso]!=null&&#session[#sso].getId()!=null&&#session[#sso].getId().length()>0">
请关闭或者注销模拟登录！
</s:if>