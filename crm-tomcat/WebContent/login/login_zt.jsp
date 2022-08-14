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
	int loginSuccess = 0;
	if("post".equalsIgnoreCase(request.getMethod())){
		String username = Utility.trimNull(request.getParameter("username"));
		String password = Utility.trimNull(request.getParameter("password"));
		username = username != null ? username.replaceAll("'","''") : "";
		password = password != null ? password.replaceAll("'","''") : "";	
		LoginService loginService = new LoginService();
		loginSuccess = loginService.checkUserPass(username, password); 
		if(loginSuccess==100){
			loginService.getpageSize(username,request);
			loginService.doLogin(request,username, password);
		}else{
			errorMsg = "用户名或密码错误！";
		}
	}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
	<title><%=application.getAttribute("APPLICATION_NAME")%></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/login.css"></link>
	<style>
	.cssLoginBtn_L1, .cssLoginBtn_L2 {
		border: 0;
		background-image: url(../images/login-btn-zt.jpg);
		height: 31px;
		line-height: 23px;
		width: 117px;
	}
	</style>
	<script language=javascript>

	<%if(!"".equalsIgnoreCase(errorMsg)){%>
		alert("用户名或密码错误！");
	<%}else if(loginSuccess==100){%>
		location="/login/index.jsp";

	<%}%>
	function login_submit()
	{
		if(document.loginForm.onsubmit())
		{ 
			document.loginForm.submit();
		}
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
	<table cellpadding="0" cellspacing="0" height="100%" width="100%">
		<tbody>
			<tr>
				<td>
					<div id="GradientDiv" class="cssWLGradientIMG"></div>
				</td>
			</tr>
			<tr valign="middle" style="height:100%;">
				<td id="mainTD" align="center">
					<table cellspacing="0" cellpadding="0">
						<tr valign="middle">
							<td id="leftTD">
								<table>
									<tr>
										<td>
											<div style="float: left;">
												<%if(user_id.intValue() == 22){%> 
												<img  src="<%=request.getContextPath()%><%=user_logo%>" height="182" width="406"/>
												<%}else{%>
												<img src="<%=request.getContextPath()%><%=user_logo%>" height="77" width="275"/>
												<%}%>
											</div>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<div style="float: left; text-align: center; padding-left: 150px;">

											</div>
										</td>
									</tr>
								</table>
							</td>
							<td id="rightTD">
								<form id="loginForm" name="loginForm" method="post" onSubmit="return doSubmit();" action="/login/login_zt.jsp">
									<input type="hidden" name="serial" value="792487126859607321371232133245345346783">
									<input type="hidden" name="session" value="<%=session.getId()%>">
									<h1 style="margin: 20px 0pt 20px; font-size: 24px; font-weight: bold; height: 30px;"><%=company_name%></h1>
									<p style="font-size: 20px;"><%=LocalUtilis.language("login.userName",clientLocale)%> <!-- 用户名 -->
									<input type="text" id="username" name="username" class="cssTextInput" tabindex="1" maxlength="16" onblur="this.style.backgroundColor='#FFFFFF'" onfocus="this.style.backgroundColor='#e2f8ff'" autocomplete="off"  onkeydown = "javscript:check_login(this);" /></p>
									<p style="margin-top: 20px; font-size: 20px;">&nbsp;&nbsp;密　码<%//=LocalUtilis.language("login.password",clientLocale)%> <!-- 密码 -->
									<input type="password" id="password" name="password" class="cssTextInput" tabindex="2" maxlength="32" onblur="this.style.backgroundColor='#FFFFFF'" onfocus="this.style.backgroundColor='#e2f8ff'" onkeydown="check_login(this)" autocomplete="off"/></p>
									<%if(Argument.getSyscontrolValue("Global")==0){ %>
									<p style="margin-top: 10px;"><%=LocalUtilis.language("login.message.languageType",clientLocale)%> :<!--语言环境-->&nbsp;&nbsp;
									<select id="languageFlag" style="width:80px;" onchange="javascript:changeLanguageType();">
										<option value='0' <%if(languageFlag.intValue()==0)out.print("selected");%> ><%=LocalUtilis.language("login.message.auto",clientLocale)%> </option><!--默认-->
										<option value='1' <%if(languageFlag.intValue()==1)out.print("selected");%> ><%=LocalUtilis.language("login.message.chinese",clientLocale)%> </option><!--中文-->
										<option value='2' <%if(languageFlag.intValue()==2)out.print("selected");%> ><%=LocalUtilis.language("login.message.English",clientLocale)%> </option><!--英文-->
									</select></p>
									<%}%>
									<p style="margin: 20px 0 0 42px;"><input type="hidden" id="remMe" name="remMe" class="cssCheckbox"/></p>
									<p style="margin-left:42px;"><input type="hidden" id="remPwd" name="remPwd" class="cssCheckbox"/></p>
									<p style="margin: 20px 0 16px 95px;"><button type="button"   class="cssLoginBtn_L1" onclick="javascript:login_submit();"><font style="font-size: 20px;">登&nbsp;陆</font></button></p>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="cssFooterPadding" id="footerTD">
					<!--版权所有-->
					<div class="cssFooterLeft">CopyRight&nbsp;?&nbsp;2008-2018&nbsp;<%=LocalUtilis.language("login.allRights",clientLocale)%> &nbsp;<a href="http://www.enfo.com.cn/" target="_blank">杭州盈丰软件技术有限公司</a></div>
					<div class="cssFooterRight">
						<!-- 联系电话 -->
						<a class="cssFooterRightA" href="#"><%=LocalUtilis.language("login.telephone",clientLocale)%> ：0571-87703600</a>
						<!-- 关于 -->
						<a class="cssLastFooterRightA" href="http://www.enfo.com.cn/" target="_blank"><%=LocalUtilis.language("login.about",clientLocale)%> </a>
					</div>
				</td>
			</tr>
		</tbody>
	</table>

	<!-- javascript -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/md5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/cookiemanager.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/login_<%=languageType%>.js"></script>
</body>
</html>


<script language="javascript">
	function imgref(e){
		e.src="<%=request.getContextPath()%>/service/ValidateImg"+"Servlet";
	}
</script>
