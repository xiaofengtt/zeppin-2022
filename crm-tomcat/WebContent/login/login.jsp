<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*,enfo.crm.affair.*"%>
<%
	String errorMsg = "";
	String input_opCode = "";
	String apply_name = "";
	String company_name = "";
	String loginUrl = request.getContextPath()+"/login/login_info.jsp";
	Integer languageFlag = new Integer(0);
	String languageType = "zh_CN";
	Locale clientLocale = null;
	boolean success = false;
	boolean oneSuccess = false;
	Integer user_id = new Integer(0);
	String user_logo="";

	//Integer[] opArray = null;
	//检查开关
	if(Argument.getSyscontrolValue("Global")!=0){
		languageFlag = new Integer(Argument.getSyscontrolValue("Global"));		
	}
	else{
		languageFlag = Utility.parseInt(request.getParameter("languageFlag"),Integer.valueOf("0"));
	}
	//不使用callcenter
	Integer no_callcenter = Utility.parseInt(request.getParameter("no_callcenter"),Integer.valueOf("0"));
	session.setAttribute("no_callcenter",no_callcenter);
	//设置语言环境
	session.setAttribute("languageFlag",languageFlag);
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
	//获得对象
	OperatorVO opVO = new OperatorVO();	
	OperatorLocal op = EJBFactory.getOperator();
	SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();
		
	Map map= null;
	try{
		map=(Map)systeminfo.listBySig1(new Integer(1)).get(0);	
	}
	catch(Exception e){
		throw new Exception(LocalUtilis.language("login.message.dataBaseError",clientLocale));//系统数据库未初始化!
	}
	//系统数据
	apply_name = Utility.trimNull(map.get("APPLICATION_NAME"));
	if(languageType.equals("en_US")){
		company_name = Utility.trimNull(map.get("USER_NAME_EN"));
	}
	else {
		company_name = Utility.trimNull(map.get("USER_NAME"));
	}
	
	if(apply_name!=null || apply_name!="") {
		application.setAttribute("APPLICATION_NAME",apply_name);
	} else {
		application.setAttribute("APPLICATION_NAME","盈丰金融财务管理系统");
	}
	
	application.setAttribute("COMPANY_NAME", map.get("USER_NAME"));
	application.setAttribute("USER_ID",map.get("USER_ID"));
	application.setAttribute("INTRUST_ADDRESS",map.get("INTRUST_ADDRESS"));
///////////////////////////////////////////////////////////////////////////////	2008-10-29
	DictparamVO dictVO = new DictparamVO();
	DictparamLocal dict = EJBFactory.getDictparam();
	
	List dictList = null;
	List opInfoList = null;
	
	Map dictMap = null;
	Map opMap = null;
	Map bookMap = null;

	user_id = (Integer)application.getAttribute("USER_ID");
	if(user_id!=null) {
		user_logo=Argument.getUserLogoInfo(user_id.intValue())[0];
	}
	//中投
	if(user_id.intValue() == 22){
		request.getRequestDispatcher(request.getContextPath()+"/login/login_zt.jsp").forward(request, response);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><%=application.getAttribute("APPLICATION_NAME")%></title>
<link href="/login/styles/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/login/js/plugins/jquery-1.8.2.js"></script>
<script type="text/javascript" src="/login/js/plugins/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="/login/js/plugins/initstyle.js"></script>
<!--[if IE 6]><script type="text/javascript" src="/login/js/iepngfix_tilebg.js"></script><![endif]-->
<style type="text/css">

</style>
<script language=javascript>
//jQuery.noConflict();
<%
	String errormsg = Utility.trimNull(request.getAttribute("errormsg"));
	if(!errormsg.equalsIgnoreCase("")){
		out.print("alert('"+errormsg+"')");
	}
%>
function login_submit(){
//  		jQuery.ajax({
//        //url:'http://192.168.2.162:8080/dologin?username='+jQuery("#username").val()+'&password='+jQuery("#password").val(),
//        url:'http://192.168.2.48:8080/dologin',
//        type:'POST', //GET
//			xhrFields: {
//            withCredentials: true
//        },//添加跨域名请求参数
//        crossDomain: true,//添加跨域名请求参数 
//        data:{
//            "username":jQuery("#username").val(),
//            "password":jQuery("#password").val()
//        }, 
//        dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
//        success:function(data,textStatus,jqXHR){
//            if (data.status==1){ //成功
//            	document.loginForm.submit();
//            }else{
//            	alert(data.msg);
//            }
//        },
//        error:function(xhr,textStatus){
//            alert("用户名或者密码错误!!!");
//        },
//        complete:function(){
//        }
//    })  
	document.loginForm.submit();
}

/*function nextKeyPress(ce, forceNext)
{	
	if (event.keyCode == 13 || forceNext)
	{
		event.keyCode = 9;
		return true;
	}
}*/
function check_login(){
	if (window.event.keyCode == 13){
		login_submit();	
	}
}

function changeLanguageType(){
	var languageFlag = document.getElementById("languageFlag").value;
	location = "<%=request.getContextPath()%>/login/login.jsp?languageFlag="+languageFlag;
}
</script>
</head>
<body>
	<div class="frame">
	<form id="loginForm" name="loginForm" method="post" onSubmit="return doSubmit();">
		<input type="hidden" name="serial" value="792487126859607321371232133245345346783">
		<input type="hidden" name="session" value="<%=session.getId()%>">
		<input class="user"  id="username" name="username" type="text" maxLength="20"  onkeydown = "javscript:check_login(this);" />
		<input class="pass" type="password" id="password" name="password" maxLength="20" onkeydown="check_login(this)"/>
		
		<%if(Argument.getSyscontrolValue("Global")==0){ %>
		<p style="margin-top: 10px;"><%=LocalUtilis.language("login.message.languageType",clientLocale)%> :<!--语言环境-->&nbsp;&nbsp;
		<select id="languageFlag" style="width:80px;" onchange="javascript:changeLanguageType();">
			<option value='0' <%if(languageFlag.intValue()==0)out.print("selected");%> ><%=LocalUtilis.language("login.message.auto",clientLocale)%> </option><!--默认-->
			<option value='1' <%if(languageFlag.intValue()==1)out.print("selected");%> ><%=LocalUtilis.language("login.message.chinese",clientLocale)%> </option><!--中文-->
			<option value='2' <%if(languageFlag.intValue()==2)out.print("selected");%> ><%=LocalUtilis.language("login.message.English",clientLocale)%> </option><!--英文-->
		</select></p>
		<%}%>
		<a class="buttonNC submit" onclick="javascript:login_submit();"></a>
	</form>	
	</div>
	<div id="dialog"></div>
</body>
</html>

