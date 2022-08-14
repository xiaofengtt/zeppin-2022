<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" import="enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%
//本地化信息
Integer languageFlag = Utility.parseInt(Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
String languageType = "auto";
Locale clientLocale = null;

//是否webcall进入
Integer isWebcall = Utility.parseInt(Utility.trimNull(session.getAttribute("isWebcall")),Integer.valueOf("0"));
//no_callcenter=1不使用callcenter
Integer no_callcenter = Utility.parseInt(Utility.trimNull(session.getAttribute("no_callcenter")),Integer.valueOf("0"));

//设置语言环境
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//得到本地化信息
	languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
}
else if(languageFlag.intValue()==1){
	clientLocale = new Locale("zh","CN");
	languageType = "zh_CN";
}
else if(languageFlag.intValue()==2){
	clientLocale = new Locale("en","US");
	languageType = "en_US";
}
Integer user_id= (Integer)application.getAttribute("USER_ID");
//设置呼叫中心环境
String callcenter_mode = "";
if(user_id.intValue() == 15){
	callcenter_mode = "_jianxin";
}

OperatorLocal oplocal = EJBFactory.getOperator();  
InputMan input_operator = (InputMan)session.getAttribute("OPERATOR");
String DB00001="1";
Integer dbdriverflag = (Integer)session.getAttribute("DBDRIVER"); 
if(dbdriverflag!= null)
	DB00001 = dbdriverflag.toString();

int status=11;

//操作员尚未登录
if (input_operator == null) 
    throw new BusiException(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"！");
String input_operatorName = input_operator.getOp_name();
if (input_operatorName == null)
    throw new BusiException(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"！");
Integer input_operatorCode = input_operator.getOp_code();
if (input_operatorCode == null)
    throw new BusiException(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"！");
   
String parent_id = Utility.trimNull(request.getParameter("parent_id"),"11");

String user_logo_top="";
 
if(user_id!=null){
	 user_logo_top=request.getContextPath()+Argument.getUserLogoInfo(user_id.intValue())[1];
}

//设置产品id
Integer session_product_id=(Integer)session.getAttribute("overall_product_id");
Integer overall_product_id=enfo.crm.tools.Utility.parseInt(session_product_id,new Integer(0));
if(overall_product_id==null || overall_product_id.intValue()==0){
	Integer get_product_id=input_operator.getProductidByOpcode(input_operatorCode);
	overall_product_id=get_product_id;
	session.setAttribute("overall_product_id",get_product_id);
}

//获取操作员相关CC信息
String extension = "";
String recordExtension = "";
Integer depart = new Integer(0);
Integer role_id = new Integer(0);
String role_name = "";
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
vo.setManagerid(input_operatorCode);
String[] totalColumn = new String[0];
IPageList pageList =tcustmanagers_Bean.pagelist_query(vo,totalColumn,1,-1);

List depList = tcustmanagers_Bean.operator_query(vo);
Map depMap = null;
List rsList = pageList.getRsList();
Map operatorMap = null;

if(rsList!=null&&rsList.size()==1){
    operatorMap = (Map)rsList.get(0);
    extension = Utility.trimNull(operatorMap.get("Extension"));
    recordExtension = Utility.trimNull(operatorMap.get("RecordExtension"));
}

if(depList!=null&&depList.size()==1){
	depMap = (Map)depList.get(0);
	depart = Utility.parseInt(Utility.trimNull(depMap.get("DEPART_ID")), new Integer(0));
	role_id = Utility.parseInt(Utility.trimNull(depMap.get("ROLE_ID")), new Integer(0));
}

DepartmentLocal departmentLocal = EJBFactory.getDepartment();
DepartmentVO departVO = new DepartmentVO();

departVO.setDepart_id(depart);
List departList = departmentLocal.listProcAll(departVO);
Map departMap = null;
String depart_name = "";

if(departList!=null&&departList.size()==1){
	departMap = (Map)departList.get(0);
	depart_name = Utility.trimNull(departMap.get("DEPART_NAME"));
}

RoleLocal roleLocal = EJBFactory.getRole();
RoleVO role_vo = new RoleVO();
role_vo.setRole_id(role_id);
List role_list = roleLocal.listBySql(role_vo);
Map role_map = null;

if(role_list!=null&&role_list.size()==1){
	role_map = (Map)role_list.get(0);
	role_name = Utility.trimNull(role_map.get("ROLE_NAME"));
}

//cc_status 坐席相关信息状态 0：全部OK，1：分机号未设置，2：录音号未设置，3：分机号和录音分机号都为设置 4：其他
if(!"".equals(extension)&&!"".equals(recordExtension)){
    session.setAttribute("cc_status","0");
    session.setAttribute("extension",extension);
    session.setAttribute("recordExtension",recordExtension);
}else if("".equals(extension)&&!"".equals(recordExtension)){
    session.setAttribute("cc_status","1");
}else if(!"".equals(extension)&&"".equals(recordExtension)){
    session.setAttribute("cc_status","2");
}else if("".equals(extension)&&"".equals(recordExtension)){
    session.setAttribute("cc_status","3");
}else{
    session.setAttribute("cc_status","4");
}
String cc_status = (String)session.getAttribute("cc_status");
extension = (String)session.getAttribute("extension");
recordExtension = (String)session.getAttribute("recordExtension");
String homepage_style="style='background: url("+request.getContextPath()+"/styles/images/sitearea-nav.jpg) repeat-x 0 -100px; COLOR:white;;'";
String selected_model_color="<font color='white'>";
Integer LOG0001=(Integer)session.getAttribute("LOG0001");
String mainPageName = "main.jsp";
if(Argument.getSyscontrolValue("USERNEWPORTAL")==1){
	mainPageName = "main_3.jsp";
}
%>


<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=7">	
<title><%=application.getAttribute("APPLICATION_NAME")%></title> 
<link href="../lib/styles/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">/*var portletPage="http://baidu.com";*/</script>
<script type="text/javascript" src="../lib/js/plugins/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../lib/js/plugins/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../lib/js/plugins/jquery.layout-1.3.js"></script>
<script type="text/javascript" src="../lib/js/plugins/utilities.js"></script>

	<script language=javascript>
		var j$ = jQuery.noConflict();
	</script>
<script type="text/javascript" src="../lib/js/page.all.js"></script>
<script type="text/javascript" src="../lib/js/plugins/initstyle.js"></script>
<script type="text/javascript" src="../lib/js/page.home.js"></script>

<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/prototype.lite.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/moo.fx.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/accordion.js'></script>
<!--	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/ext_index.js'></script>-->
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/register.js'></script>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/loginService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/menuService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>


<script type="text/javascript" >
var menuStr ="";
function clickInfo(){
	DWREngine.setAsync(false);
	alert(1111111);
    menuService.getMenuList(888,2,"201",0,menuCallBack);
	
	alert(menuStr);
    menuinfo1 = eval(menuStr);
}
function menuCallBack(data){
	
    menuStr = data;  
	
}
</script>

<!--[if IE 6]><script type="text/javascript" src="js/iepngfix_tilebg.js"></script><![endif]-->
<style type="text/css">
</style>
</head>
<body class="pageHome">

<div class="ui-layout-north header"  id="home-banner">
	<div class="shine"></div>
	<ul class="logoWrap">
		<li class="companyName"></li>
		<li class="systemName"></li>
	</ul>
	<ul class="logoBtn">
		<li class="infoBtn buttonNC" title="关于"></li>
		<li class="pausBtn checkBtn" title="暂停动画"></li>
	</ul>
	<ul class="nav radioBtn">
		<li class="navIcon_Home" onclick="j$.writeURL({sidenav:'../lib/sidenav.html?id=1',content:'login/main.jsp'})">工作台</li>
		<li class="navIcon_norm" onclick="">菜单测试</li>
		<li class="navIcon_paper" onclick="clickInfo();">客户管理</li> 
		<!-- <li class="navIcon_paper" onclick="j$.writeURL({sidenav:'../lib/sidenav.html?id=1',content:'../lib/welcome.html'})">客户管理</li> -->
		<li class="navIcon_doc" onclick="j$.writeURL({content:'login/main.jsp'})">业务办理</li>
		<li class="navIcon_search" onclick="$.writeURL({sidenav:'sidenav.html',content:'welcome.html'})">报表查询</li>
		<li class="navIcon_email" onclick="$.writeURL({sidenav:'sidenav.html',content:'welcome.html'})">电子邮件</li>
		<li class="navIcon_peoples" onclick="$.writeURL({sidenav:'sidenav.html',content:'welcome.html'})">用户中心</li>
		<li class="navIcon_folder" onclick="$.writeURL({sidenav:'sidenav.html',content:'welcome.html'})">文档中心</li>
		<li class="navIcon_earth" onclick="$.writeURL({sidenav:'sidenav.html',content:'welcome.html'})">信息发布</li>
		<li class="navIcon_notify">通知公告</li>
		<li class="navIcon_eyes">办事公开</li>
		<li class="navIcon_gear">系统管理</li>
		<li class="navIcon_person">个人自助</li>
		<li class="navIcon_book" onclick="$.writeURL()">通讯录</li>
		<li class="navIcon_receptor">信息发布</li>
		<li class="navIcon_notepad">值班管理</li>
		<li class="navIcon_officdoc">公文办理</li>
		<li class="navIcon_stupid">两项工作</li>
		<li class="navIcon_box">专卖内管</li>
		<li class="navIcon_norm">标准化</li>
	</ul>
	<ul class="navIcon-norm-menu">
		<li><a href="#">新建任务一</a></li>
		<li><a href="#">新建任务二</a></li>
		<li><a href="#">新建任务三</a></li>
	</ul>
	<ul class="assist">
		<li class="userInfo">Administrator</li>
		<li class="ctrlPanl" title="系统设置"></li>
		<li class="userConfig" title="用户配置"><font>用户配置</font></li>
		<li class="logoff" title="注销"></li>
	</ul>
</div>
<div class="ui-layout-west sideNavLayout" id="sideNavLayout" >
	<iframe src='../lib/sidenav.html' frameborder='0' id='sidenavFrame'></iframe>
</div>
<div class="ui-layout-center contentLayout" id="contentLayout">
	<iframe src='../lib/portlet.html' frameborder='0' id='contentFrame'></iframe>
</div>
<div id='framePool'>
	
	
</div>
<div class="dialogCtrlPanl">
	<ul class="ctrlPanlGrid">
		<li class="firstRow">
			<span>导航风格<font> (需刷新页面)</font></span>
			<select id="navStyleSwitch">
				<option value="navStyleIcons">图标风格</option>
				<option value="navStyleTabs">标签风格</option>
			</select>
		</li>
		<li>
			<span>皮肤切换</span>
			<ul class="themeSwitch radioBtn">
				<li class="themeRed"></li>
				<li class="themeDarkBlue"></li>
				<li class="themeLightBlue"></li>
				<li class="themeBlack noInt"></li>
				<li class="themeGray noInt"></li>
			</ul>
		</li>
		<li><span>动画效果<font> (提高性能但降低用户体验)</font></span><span id="aniSwitch" class="switch checkBtn"></span></li>
		<li class="lastRow"><div class="tipSimple tipNormal"><span class="icon"></span><font>所选的结果自动保存！其中导航风格切换需刷新页面！</font></div></li>
	</ul>
</div>
<div class="passChange">test</div>

</body>
</html>
<script type="text/javascript">
j$(".logoff").click(function(){
	window.open("login.html","_self")
});

j$(".userConfig").click(function() {
	j$(".passChange").dialog({
		height: 200,
        width: 400,
        modal: true
	});
	j$.writeURL({sidenav:'sidenav.html',content:'welcome.html'})
});
j$(".navIcon_norm").click(function() {
	var menu = j$(".navIcon-norm-menu").show().position({
		my: "left top",
		at: "left bottom",
		of: this
	});
	j$( document ).one( "click", function() {
		menu.hide();
	});
	return false;
})
j$(".navIcon-norm-menu").hide().css("width","100").menu();
j$(".header").mouseleave(function(){j$(".navIcon-norm-menu").hide()});
/*
		function close(){
		   window.open("close.html",'newwindow','height=100, width=400, top=0, left=0, toolbar=no, 

menubar=no, scrollbars=no, resizable=no,location=no, status=no',false);
		}
		j$(function(){
			j$(window).bind("beforeunload",function(){close();});
		});
*/		
</script>

<%
tcustmanagers_Bean.remove();
roleLocal.remove();
departmentLocal.remove();
%>