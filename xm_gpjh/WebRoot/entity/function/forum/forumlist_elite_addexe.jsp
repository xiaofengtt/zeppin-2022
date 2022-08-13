<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.answer.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.util.string.*,com.whaty.platform.interaction.forum.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
	String forumId = request.getParameter("forumId");
	String threadId = request.getParameter("threadId");
	String courseId1 = openCourse.getCourse().getId();
	String keyId = "forum_" + courseId + "_" + forumId;
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage resManage = fac.creatBasicResourceManage();
	
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	Forum thread = interactionManage.getForum(threadId);
	ForumList forum = interactionManage.getForumList(forumId);
	
	String type_id = "";
	List typeList = resManage.getResourceTypeList();
	for(int i=0; i<typeList.size(); i++) {
		ResourceType resType = (ResourceType)typeList.get(i);
		if(resType.getName().equals("论坛精华区")) {
			type_id = resType.getId();
			break;
		}
	}
	
	ResourceDir eliteDir = resManage.getResourceDirByKeyId(keyId);
	if(eliteDir.getName() == null) {	//如果版块精华区资源目录还没有建立
		ResourceDir courseDir = resManage.getResourceDirByKeyId("forum_" + courseId);
		if(courseDir.getName() == null) {		//如果课程精华区目录还没有建立
			String dirName = openCourse.getCourse().getName();
			String parentDirId = resManage.getResourceDirByKeyId("resource_elite").getId();
			String note = "课程 " + openCourse.getCourse().getName() + "的精华区资源目录";
			String status = "1";
			String isInherit = "0";
			
			resManage.addResourceDir(dirName,parentDirId,note,status,isInherit,"forum_"+courseId);
			courseDir = resManage.getResourceDirByKeyId("forum_"+courseId);
			String dirId = courseDir.getId();
			List userList = new ArrayList();
			userList.add(user.getId());
			List dirList = new ArrayList();
			dirList.add(dirId);
			resManage.setResourceRight(userList, dirList);
		}
		
		String dirName = openCourse.getCourse().getName();
		String parentDirId = courseDir.getId();
		String note = "课程 " + openCourse.getCourse().getName() + "的版块 " + forum.getName() + " 精华区资源目录";
		String status = "1";
		String isInherit = "0";
		
		resManage.addResourceDir(dirName,parentDirId,note,status,isInherit,keyId);
		eliteDir = resManage.getResourceDirByKeyId(keyId);
		String dirId = eliteDir.getId();
		List userList = new ArrayList();
		userList.add(user.getId());
		List dirList = new ArrayList();
		dirList.add(dirId);
		resManage.setResourceRight(userList, dirList);
	}
	
	String title = thread.getTitle();
	String creatUser = thread.getUserName();
	String dir_id = eliteDir.getId();
	String language = request.getParameter("language");
	String description = request.getParameter("description");
	String keyword = request.getParameter("keyword");
	String xml = "<resource><item><name>内容</name><content>" + thread.getBody() + "</content></item></resource>";
	//out.print(xml);
	
	 int i = resManage.addResource(title, language,
			description, keyword, creatUser,
			type_id, dir_id, xml);
	if(i >0) {
%>
<script>
	alert("添加成功");
	window.location.href = "forum_list.jsp?forumlist_id=<%=forumId%>";
	window.close();
</script>
<%
	} else {
%>
<script>
	alert("添加失败");
	window.history.back();
</script>
<%
	}
%>
