<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<% response.setHeader("expires", "0"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="js/xtree.js"></script>
	<script type="text/javascript" src="js/xmlextras.js"></script>
	<script type="text/javascript" src="js/xloadtree.js"></script>
	<link type="text/css" rel="stylesheet" href="css/xtree.css" />
	<link href="../css/admincss.css" rel="stylesheet" type="text/css">
</head>
<%
	String keyId = request.getParameter("keyId");
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	ResourceDir resDir = resManage.getResourceDirByKeyId(keyId);
%>
<body leftmargin="0" topmargin="0" class="scllbar">
	<script type="text/javascript">
		/// XP Look
		webFXTreeConfig.rootIcon		= "images/xp/folder.png";
		webFXTreeConfig.openRootIcon	= "images/xp/openfolder.png";
		webFXTreeConfig.folderIcon		= "images/xp/folder.png";
		webFXTreeConfig.openFolderIcon	= "images/xp/openfolder.png";
		webFXTreeConfig.fileIcon		= "images/xp/file.png";
		webFXTreeConfig.lMinusIcon		= "images/xp/Lminus.png";
		webFXTreeConfig.lPlusIcon		= "images/xp/Lplus.png";
		webFXTreeConfig.tMinusIcon		= "images/xp/Tminus.png";
		webFXTreeConfig.tPlusIcon		= "images/xp/Tplus.png";
		webFXTreeConfig.iIcon			= "images/xp/I.png";
		webFXTreeConfig.lIcon			= "images/xp/L.png";
		webFXTreeConfig.tIcon			= "images/xp/T.png";
		
		//var tree = new WebFXLoadTree("WebFXLoadTree", "tree1.xml");
		//tree.setBehavior("classic");		
		
		var tree = new WebFXLoadTree("<%= resDir.getName() %>", "xml/root.jsp?parentid=<%= resDir.getId() %>&date=" + (new Date()).getTime());		
		//tree.add(new WebFXTreeItem("Tree Item 4"));
		
		document.write(tree);
	</script>
</body>
</html>
