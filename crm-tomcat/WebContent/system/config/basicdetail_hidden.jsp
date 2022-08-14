<%@ page language="java" contentType="text/html;charset=GBK" import="enfo.crm.tools.*,java.util.*"%>
<%
	List fieldHideList =(List)request.getAttribute("fieldHideList");
	Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer(0));
	List modShowDataList = (List)request.getAttribute("modShowDataList");
	
	for(int i=0;i<fieldHideList.size();i++){//隐藏字段集
	Map  hideMap = (Map)fieldHideList.get(i);
	String interfaceField_code = Utility.trimNull(hideMap.get("INTERFACEFIELD_CODE"));
	String default_flag = Utility.trimNull(hideMap.get("DEFAULT_FLAG"));
	String default_value = Utility.trimNull(hideMap.get("DEFAULT_VALUE"));
	String show_type =Utility.trimNull(hideMap.get("SHOW_TYPE"));
	String edit_type =Utility.trimNull(hideMap.get("EDIT_TYPE"));
	String inputType = ConfigUtil.getInputType(show_type,edit_type);//文本框类型
	String fieldValue = ConfigUtil.getPageElementValue(identity_code,default_flag,default_value,inputType,interfaceField_code,modShowDataList);
	
%>
	<input type="hidden" name="<%=interfaceField_code%>" value="<%=fieldValue%>"/>
<%}%>