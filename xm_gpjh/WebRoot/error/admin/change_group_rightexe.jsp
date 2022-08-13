<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>

<%
	PlatformFactory platformfactory = PlatformFactory.getInstance();
	BasicRightManage rightManage = platformfactory.creatBasicRightManage();
	String group_id=fixnull(request.getParameter("group_id"));
	int count=0;
	List officeModel=rightManage.getRightModels();
	if(officeModel!=null){
		for(int m=0;m<officeModel.size();m++){	
		RightModel rightMode=(RightModel)officeModel.get(m);
		String checkGroup="checkgroup" + rightMode.getId();
		String checkright[] = request.getParameterValues(checkGroup);
		//if(checkright!=null&&checkright.length>0){
			//for(int n=0; n<checkright.length; n++)
			 	count+=rightManage.changeGroupRights(group_id,rightMode.getId(),checkright);
		//}			 
	}
}
if (count < 1)
{
%>
<script language="javascript">
alert("请先设定部门权限,再提交,修改失败！");
window.history.back();
</script>
<%
}
else
{
%>
<script language="javascript">
alert("修改成功!");
window.navigate("change_group_right.jsp?group_id=<%=group_id%>");
</script>

<%}%>