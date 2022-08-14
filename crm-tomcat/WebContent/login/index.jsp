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
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/callcenter/callcenter_2017.js'></script>
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogonSucc">
// <!CDATA[
    return csSoftPhone_evtLogonSucc()
// ]]>
</script>
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogoffSucc">
// <!CDATA[
    return csSoftPhone_evtLogoffSucc()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallArrive">
// <!CDATA[
    return csSoftPhone_evtCallArrive()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialSucc">
// <!CDATA[
    return csSoftPhone_evtDialSucc()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtDialFailed(ErrNum)
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtStateChange(State)">
// <!CDATA[
    //alert (State);
    return csSoftPhone_evtStateChange(State);
// ]]>
</script>

<script type="text/javascript" >


function csSoftPhone_evtCallArrive(){
    var ANI="";
    var SK="";
    var COID = "";
    ANI=csSoftPhone.getCOInfo ("ANI");
    SK= csSoftPhone.getCOInfo("SK");
    COID= csSoftPhone.getCOInfo("COID");    

    //var crmhtml  = "bill.do?method=CustomerInfo&customerphone="+ANI+"";
    var crmhtml="/login/callcenter_custinfo.jsp?op_code=<%=input_operatorCode %>&phone="+ANI+"&callid="+COID;
    window.open(crmhtml);
}

window.onload=function(){//自动连接呼叫中心

				<%if(Argument.getSyscontrolValue("DT_CALL")!=0 && no_callcenter.intValue() != 1){%>	
            	 //initMyPhone();
	          <%if(cc_status.equals("0")){%>
							//alert('<%=input_operatorCode%>');
							cmdSetParam_onclick("<%=input_operatorCode %>");	
	                //connect2Server('<%=Argument.getCTIServerIP()%>','<%=Argument.getCTIServerPort()%>','<%=Argument.getCallCenterDbIP()%>','<%=Argument.getCallCenterDbName()%>','<%=Argument.getCallCenterDbUser()%>','<%=Argument.getCallCenterDbPassword()%>');
	          <%}%>
	       <%}%>	   
	//cmdSetParam_onclick("<%=input_operatorCode %>");
}
var menuStr ="";
function clickInfo(){
	DWREngine.setAsync(false);
    	menuService.getMenuList(<%=input_operatorCode %>,2,"201",0,menuCallBack);
	
   	 menuinfo1 = eval(menuStr);
}
function menuCallBack(data){
	
    menuStr = data;  
	
}
//快速检索客户信息
function quickSearchCustomer()
{
	showModalDialog('<%=request.getContextPath()%>/client/clientinfo/quickSearchCustomer.jsp', '', 'dialogWidth:950px;dialogHeight:500px;status:0;help:0')
}
//外部链接
function web(){
	showModalDialog('<%=request.getContextPath()%>/system/systemparam/web_link.jsp', '1', 'dialogWidth:340px;dialogHeight:200px;edge: Raised; center: Yes; help: Yes; resizable: Yes; status: Yes; ')
}
//改变当前产品
function changeSelectProduct(){
	var ret_productname = showModalDialog('<%=request.getContextPath()%>/login/product_type_select.jsp', window, 'dialogWidth:650px;dialogHeight:560px;status:0;help:0');
	
	if(ret_productname!=null){	  	
		document.getElementById("now_product").innerText = ret_productname;
	}    	
}
function logOut(){
	//是否退出系统
	if(confirm("<%=LocalUtilis.language("index.msg.exitSystem",clientLocale)%> ？")){
		//quitCallCenter();
		var url = '<%=request.getContextPath()%>/login/logout_crm.jsp';
	    j$.ajax({
	        type:'get',
	        //async:false,
	        url:url,
	        success:function(data){
	        	data = data.replace(/^\s\s*/,'').replace(/\s\s*$/,'');
 				if(data == "0"){
					//注销失败,未能成功退出系统，请重试!
					alert("<%=LocalUtilis.language("index.msg.cancelFail",clientLocale)%> ");
					return;
				}else if(data == "1"){
					location = "<%=request.getContextPath()%>/login/logout.jsp";
				} 
	        },
	        error:function(data){
	            alert('error');
	        }
		});			
	}else{
		return;
	}
}
function quitCallCenter(){
	 <%if(Argument.getSyscontrolValue("DT_CALL")!=0 && no_callcenter.intValue() != 1 && cc_status.equals("0")){%>
		if(extension != 0){
			disconnect2server(op_code,extension);
		}
	<%}%>
}
function showPasswordDialog()//修改密码界面
{
	//修改成功
	if(showModalDialog('<%=request.getContextPath()%>/login/password.jsp', '', 'dialogWidth:320px;dialogHeight:270px;status:0;help:0')!= null)
		alert('<%=LocalUtilis.language("message.updateOk",clientLocale)%> !');
}
function showCallCenter(){
	//j$("#callcenterArea").show();
	showModalDialog('<%=request.getContextPath()%>/login/callcenter_custinfo.jsp', '', 'dialogWidth:860px;dialogHeight:390px;status:0;help:0');
}
function callout(phone){
	cmdDial_onclick(phone);
}

</SCRIPT>

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
		<!-- <li class="infoBtn buttonNC" title="关于"></li> -->
		<li class="pausBtn checkBtn" title="暂停动画"></li>
	</ul>
	<ul class="nav radioBtn">
	
		<li class="navIcon_Home" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=w',content:'lib/portlet-simple.jsp?display_flag=1'})">工作台</li>
		<%if(oplocal.checkMainMenu("2",input_operatorCode.toString())){%>
		<li class="navIcon_peoples" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=2',content:'lib/welcome.html'})">客户管理</li>
		<%}%>
		<%if(oplocal.checkMainMenu("3",input_operatorCode.toString())){%>
		<li class="navIcon_norm" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=3',content:'lib/welcome.html'})">营销管理</li>
		<%}%>
		<%if(oplocal.checkMainMenu("4",input_operatorCode.toString())){%>
		<li class="navIcon_person" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=4',content:'lib/welcome.html'})">经理管理</li>
		<%}%>
		<%if(oplocal.checkMainMenu("5",input_operatorCode.toString())){%>
		<li class="navIcon_notify" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=5',content:'lib/welcome.html'})">呼叫中心</li>
		<%}%>
		<%if(oplocal.checkMainMenu("w",input_operatorCode.toString())){%>
		<li class="navIcon_box" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=w',content:'lib/welcome.html'})">信息库</li>
		<%}%>
		<%if(oplocal.checkMainMenu("1",input_operatorCode.toString())){%>
		<li class="navIcon_gear" onclick="j$.writeURL({sidenav:'../lib/sidenav.jsp?menu_id=1',content:'lib/welcome.html'})">系统配置</li>
		<%}%>
		<!--<%if(oplocal.checkMainMenu("s",input_operatorCode.toString())){%>
		<li class="navIcon_earth" onclick="j$.writeURL({sidenav:'sidenav.jsp',content:'lib/welcome.html'})">开发配置</li>
		<%}%>-->
	</ul>
	<div class="region-selector">
		<span class="desc">当前产品</span><span class="content"><a href="#" onclick="javascript:changeSelectProduct();">
		<span  id="now_product">
			<%if(overall_product_id==null||overall_product_id.intValue()==0) {out.println("全部");}
				 else {out.print(Utility.trimNull(Argument.getProductFlag(overall_product_id,"product_code")) + "-" + Utility.trimNull(Argument.getProductFlag(overall_product_id,"product_name")));}%>
		</span></a></span>
	</div>
	<!-- 所在部门 -->
	<div class="szbm">
		<%=LocalUtilis.language("class.departID3",clientLocale)%> : <span style="color:#FFFFFF;"><%=depart_name %></span>
	</div>
	
	<div class="header-fun">
		
		<ul>
			<li class="quickSearch">
				<a href="#" onclick="javascript:quickSearchCustomer();" style="text-decoration:none;" ><span class="icon x2 y19"></span>&nbsp;快速检索</a>
			</li>
			
	 		<li style="display:none">
				<!-- 呼叫中心 -->
				<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
				<span class="icon x13 y6"></span><%=LocalUtilis.language("index.menu.callcenter",clientLocale)%>
				<a href="#" id="callcenterLink" target="_self" onclick="showCallCenter();"><span class="icon x13 y6"></span><%=LocalUtilis.language("index.menu.callcenter",clientLocale)%></a>
				<%}%>
			</li>
			<li class="link">
				<a href="#" onclick="web();" id="weblinkEl" style="text-decoration:none;" ><span class="icon x2 y24"></span>&nbsp;外部链接</a><!-- xxx<a href="#" onclick="javascript:callout('015867113001');">电话外拔</a> -->
			</li>
		</ul>
		<div class="hf-info">
			<!-- <span class="icon x13 y21"></span> -->
			
			<!-- 您好 空闲 -->
			<span class="hello"><%=LocalUtilis.language("index.msg.hello",clientLocale)%>!&nbsp;&nbsp; </span>
			<span style="color:#FFFFFF;"><%=input_operatorName%></span>
			<span class="icon top"></span>
			<div class="config-box">
				<div class="square"></div>
				<ul id="config-ul"> 
					<li id="userConfig">更换密码</li>
					<li id="logoff">退出</li>
				</ul>
			</div>
<%-- 			&nbsp;&nbsp;<%=LocalUtilis.language("class.roleName2",clientLocale)%>:<%=role_name %>
			[<span id="cc_show_status"><%=LocalUtilis.language("index.callcenter.FreeStatus",clientLocale)%> </span>] --%>
		</div>
	</div>
<!-- 	<ul class="assist"> -->
<!-- 		<li class="userInfo">Administrator</li> -->
<!-- 		<li class="ctrlPanl" title="系统设置"></li> -->
<%-- 		<li id="userConfig" class="userConfig" title="<%=LocalUtilis.language("index.msg.setUserInfo",clientLocale)%> "></li> --%>
<%-- 		<%if(Argument.getSyscontrolValue("SCANCEL")==0 && isWebcall.intValue()==0){%> --%>
<%-- 		<li id="logoff" class="logoff" title="<%=LocalUtilis.language("index.msg.quitSys",clientLocale)%>"></li> --%>
<%-- 		<%}%> --%>
<!-- 	</ul> -->
</div>
<div class="ui-layout-west sideNavLayout" id="sideNavLayout" >
	<iframe src='lib/sidenav.jsp?menu_id=w' frameborder='0' id='sidenavFrame'></iframe>
</div>
<div class="ui-layout-center contentLayout" id="contentLayout">
	<iframe src='lib/portlet-simple.jsp?display_flag=1' frameborder='0' id='contentFrame'></iframe>
</div>
<div id='framePool'>
	
	
</div>
<div class="dialogCtrlPanl">
	<ul class="ctrlPanlGrid">
		
		<li>
			<span>皮肤切换</span>
			<ul class="themeSwitch radioBtn">
				<li class="themeRed"></li>
				<li class="themeDarkBlue"></li>
				<li class="themeLightBlue"></li>
				<li class="themeFlatBlue"></li>
			</ul>
		</li>
		<li><span>动画效果<font> (提高性能但降低用户体验)</font></span><span id="aniSwitch" class="switch checkBtn"></span></li>
		<li class="lastRow"><div class="tipSimple tipNormal"><span class="icon"></span><font>所选的结果自动保存！其中导航风格切换需刷新页面！</font></div></li>
	</ul>
</div>
<div class="passChange">test</div>
<style>
#callcenterArea{position:fixed; bottom:0; left:0; right:0;  width:100%; z-index:10; }
#callcenterArea .ctrl-btn{position:absolute; left:10px; top:-30px; width:100px; height:30px; padding:8px 0 0 15px; background-color:#308fe1; border-radius:5px 5px 0 0;}
#callcenterArea .ctrl-btn .desc{float:left; color:#fff;}
#callcenterArea .ctrl-btn .icon{margin-right:5px;}
#callcenterArea .callcenter-inner{height:100px; display:none; overflow:hidden;}
#callcenterArea #csSoftPhone{width:100%;}

</style>
<div id="callcenterArea" >
	<div class="ctrl-btn"><span class="icon x9 y34"></span><span class="desc">CallCenter</span></div>
	<div class="callcenter-inner">
		<object id="csSoftPhone"
        classid="CLSID:A972798F-50FC-4818-BCE2-2472BC68766C"
        codebase="CrystalSoftPhone32.CAB#version=3,2,0,3"
        style="width: 100%!important; height: 100px"><param name="ServerIP" value="127.0.0.1"/>
        <param name="LocalIP" value="127.0.0.1"/><param name="AGENTINS" value="6001"/>
        <param name="SK" value="1001|1002"/><param name="AgentID" value="1001"/>
        <param name="AgentName" value="Demo"/></object>
	</div>
</div>
<input type="hidden" name="input_operatorCode" id="input_operatorCode" value="<%=input_operatorCode%>">
	<input type="hidden" id="parent_id" value="<%=parent_id%>" />
	<input type="hidden" id="agent_status" value=""/>
	<input type="hidden" id="callRecordID" value=""/>
	<input type="hidden" id="callTalkingStartTime" value="" /><!-- 通话开始时间 -->
	<input type="hidden" id="callTalkType" value="0" /> <!-- 通话类型：0-默认从index.jsp呼叫中心进入；1-从其他页面进入，自带cust_id；2.来点振铃 -->
	<input type="hidden" id="target_custid" value="" /> <!-- 目标cust_id -->
	<input type="hidden" id="communication_info" value=""/>
	<input type="hidden" id="queueInfo" value=""/>
	<input type="hidden" id="isWebcall" value="<%=isWebcall%>"/>

	<input type="hidden" id="my_status" value="st_free"/>
	<input type="hidden" id="languageFlag" value="<%=languageFlag%>"/>
	<input type="hidden" id="languageType" value="<%=languageType%>"/>
	<input type="hidden" id="dt_call" value="<%=Argument.getSyscontrolValue("DT_CALL")%>"/>
</body>
</html>
<script type="text/javascript">
(function($,document){
j$.timer = {
    data:   {}
,   set:    function(s, fn, ms, e){j$.timer.clear(s);j$.timer.data[s]=setTimeout(fn, ms ,e);}
,   clear:  function(s){var t=j$.timer.data; if(t[s]){clearTimeout(t[s]);delete t[s];}}
}
})(jQuery,document);

j$(window).load(function(){
	j$.timer.set("callcenter-width",function(){
		
		//alert(123)
	},1000)
	j$("#callcenterArea .ctrl-btn").click(function(){
		if(j$("#callcenterArea .callcenter-inner").is(':visible')){
			j$("#callcenterArea .callcenter-inner").slideUp(300);
			j$("#callcenterArea .icon").removeClass("x8 y34").addClass("x9 y34");
			
		}else{
			j$("#callcenterArea .callcenter-inner").slideDown(300);
			j$("#callcenterArea .icon").removeClass("x9 y34").addClass("x8 y34");
			j$("#callcenterArea,#csSoftPhone").width(j$(window).width());
		}
	})
})

j$("#userConfig").click(function() {
	showPasswordDialog();
});
j$("#logoff").click(function() {
	logOut();
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