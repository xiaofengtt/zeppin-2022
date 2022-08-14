<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//声明辅助变量
boolean bSuccess = false;

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO vo = new ActivityVO();

//声明字段
String active_type;
String active_type_name;
String active_theme;
Integer manage_code;
String customer_type;
String active_plan;
String start_date;
String end_date ;

//保存信息
if(request.getMethod().equals("POST")){
	manage_code = Utility.parseInt(Utility.trimNull(request.getParameter("manage_code")),new Integer(0));
	active_type = Utility.trimNull(request.getParameter("active_type"));
	active_type_name = Argument.getDictParamName(3012,Utility.trimNull(request.getParameter("active_type")));
	active_theme = 	Utility.trimNull(request.getParameter("active_theme"));
	start_date = Utility.trimNull(request.getParameter("start_date"));
	end_date =  Utility.trimNull(request.getParameter("end_date"));
	active_plan = Utility.trimNull(request.getParameter("active_plan"));	
	customer_type = Utility.trimNull(request.getParameter("customer_type"));	
	
	vo.setActive_flag(new Integer(3));
	vo.setActive_type(active_type);
	vo.setActive_type_name(active_type_name);
	vo.setActive_theme(active_theme);
	vo.setActive_start_date(start_date);
	vo.setActive_end_date(end_date);
	vo.setCustomer_type(customer_type);
	vo.setActive_plan(active_plan);
	vo.setManage_code(manage_code);
	vo.setCreator(input_operatorCode);
	vo.setInput_man(input_operatorCode);
	
	activityLocal.append(vo);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Expires" CONTENT="0">
<!--新增活动管理-->
<title><%=LocalUtilis.language("menu.addActivity",clientLocale)%> </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<script language=javascript>
	/*验证数据*/
	function validateForm(form){
		if(!sl_check(document.theform.manage_name, "<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ",20,0)){return false;}//负责人			
		if(!sl_check(document.getElementById('start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//开始时间
		if(!sl_check(document.getElementById('end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//结束时间
		if(!sl_check(document.theform.customer_type, "<%=LocalUtilis.language("class.correspondingCustomers",clientLocale)%> ",200,0)){return false;}//对应客户群	
		if(!sl_check(document.theform.active_theme, "<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ",60,1)){return false;}//活动主题	
		if(!sl_check(document.theform.active_plan, "<%=LocalUtilis.language("class.activityPlan",clientLocale)%> ",60,0)){return false;}//活动计划
		if(!sl_checkChoice(document.theform.active_type,"<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ")){return false;}//活动类别
		if(!sl_checkChoice(document.theform.manage_code,"<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ")){return false;}//负责人
		
		if(document.getElementById('end_date').value<document.getElementById('start_date').value){
			sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//结束日期不能比开始日期早
			return false;
		}
		 
		return sl_check_update();		
	}

	/*保存*/
	function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}

</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="activity_add.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="3" class="product-list">
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.activityTheme",clientLocale)%> :&nbsp;&nbsp;&nbsp;</td><!--活动主题-->
			<td  align="left" colspan="3">
				<input type="text" name="active_theme" size="30" value="" onkeydown="javascript:nextKeyPress(this)"/>				
			</td>
		</tr>
	<tr>
			<td align="right">*<%=LocalUtilis.language("class.startTime",clientLocale)%> ：&nbsp;</td><!--开始时间-->
			<td  align="left">
				<input type="text" name="start_date" id="start_date" size="30" readOnly/> 				
			</td>	
	</tr>
	<tr>
			<td align="right">*<%=LocalUtilis.language("class.endTime",clientLocale)%> ：&nbsp;</td><!--结束时间-->
			<td  align="left">
				<input type="text" name="end_date" id="end_date" size="30" readOnly/> 		
			</td>
		</tr>	
		<script language="javascript">
			laydate({elem:'#start_date',format:'YYYY-MM-DD hh:mm:ss',istime:true});
			laydate({elem:'#end_date',format:'YYYY-MM-DD hh:mm:ss',istime:true});
		</script>
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.activityCategories",clientLocale)%> :&nbsp;&nbsp;&nbsp;</td><!--活动类别-->
			<td  align="left">
				<select name="active_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%=Argument.getDictParamOptions(3012,"")%>
				</select>				
			</td>			
			<td  align="right">*<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> :&nbsp;&nbsp;&nbsp;</td><!--负责人-->
			<td  align="left">	
				<select name="manage_code" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getManager_Value(input_operatorCode)%>
				</select>		
			</td>
		</tr>	
		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.correspondingCustomers",clientLocale)%> :&nbsp;&nbsp;&nbsp;</td><!--对应客户群-->
			<td align="left" colspan="3">
				<textarea rows="3" name="customer_type"  cols="83"></textarea>			
			</td>
		</tr>	
		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.plans&targets",clientLocale)%> :&nbsp;&nbsp;&nbsp;</td><!--计划与目标-->
			<td align="left" colspan="3">
				<textarea rows="3" name="active_plan"  cols="83"></textarea>			
			</td>
		</tr>			
	</table>
</div>

<div align="right" >
	<br>
	<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--关闭-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>

</form>
<% activityLocal.remove(); %>

<script language=javascript>
	window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;
		
		if(v_bSuccess=="true"){		
			window.returnValue = 1;
			window.close();
		}
	}
</script>
</BODY>
</HTML>
