<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.util.string.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
	String detail_xml = request.getParameter("xml");

	String courseId1 = openCourse.getBzzCourse().getId();
	StrManage strManage=StrManageFactory.creat(detail_xml); 
	detail_xml = strManage.htmlEncode(); 
	String keyId = "faq_" + courseId;
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	
	String type_id = "";
	List typeList = manage.getResourceTypeList();
	for(int i=0; i<typeList.size(); i++) {
		ResourceType resType = (ResourceType)typeList.get(i);
		if(resType.getName().equals("常见问题库")) {
			type_id = resType.getId();
			break;
		}
	}
	
	ResourceDir faqDir = manage.getResourceDirByKeyId(keyId);
	if(faqDir.getName() == null) {				//如果该门课程的常见问题库资源目录不存在
		String dirName = openCourse.getBzzCourse().getName();
		String parentDirId = manage.getResourceDirByKeyId("resource_faq").getId();
		String note = "课程 " + openCourse.getBzzCourse().getName() + "的常见问题库资源目录";
		String status = "1";
		String isInherit = "0";
		
		manage.addResourceDir(dirName,parentDirId,note,status,isInherit,keyId);
		faqDir = manage.getResourceDirByKeyId(keyId);
		String dirId = faqDir.getId();
		List userList = new ArrayList();
		userList.add(user.getId());
		List dirList = new ArrayList();
		dirList.add(dirId);
		manage.setResourceRight(userList, dirList);
	} 
	
	String title = request.getParameter("title");
	String creatUser = user.getName();
	String dir_id = faqDir.getId();
	String language = request.getParameter("language");
	String description = request.getParameter("description");
	String keyword = request.getParameter("keyword");
	String xml = "<resource><item><name>内容</name><content>" + detail_xml + "</content></item></resource>";
	
	 int i = manage.addResource(title, language,
			description, keyword, creatUser,
			type_id, dir_id, xml);
	if(i >0) {
%>
<script>
	alert("添加成功");
	window.location.href = "index.jsp";
	//window.close();
</script>
<%
	} 
	else if(i == -1)
	{
%>
<script>
	alert("(<%=title%>已收录)添加失败!");
	window.location.href = "index.jsp";
</script>
<%	
	}
	else
	{
%>
<script>
	alert("添加失败");
	window.history.back();
</script>
<%
	}
%>
