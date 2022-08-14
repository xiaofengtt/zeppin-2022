<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//声明辅助变量
boolean bSuccess = false;
List list = null;
Map map = null;

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO vo = new ActivityVO();

//声明字段
Integer manage_code = new Integer(0);
String active_type = null;
String active_type_name = null;
String active_theme = null;
String start_date = null;
String end_date = null;
String customer_type = null;
String active_plan = null;
Integer activity_id = Utility.parseInt(request.getParameter("activity_id"),new Integer(0));

//保存信息
if(request.getMethod().equals("POST")){
	manage_code = Utility.parseInt(Utility.trimNull(request.getParameter("manage_code")),new Integer(0));
	active_type = Utility.trimNull(request.getParameter("active_type"));
	active_type_name = Argument.getDictParamName(3012,Utility.trimNull(request.getParameter("active_type")));
	active_theme = 	Utility.trimNull(request.getParameter("active_theme"));
	start_date = Utility.trimNull(request.getParameter("start_date"));
	end_date =  Utility.trimNull(request.getParameter("end_date"));
	customer_type = Utility.trimNull(request.getParameter("customer_type"));	
	active_plan = Utility.trimNull(request.getParameter("active_plan"));	
	
	vo.setSerial_no(serial_no);
	vo.setManage_code(manage_code);
	vo.setActive_type(active_type);
	vo.setActive_theme(active_theme);
	vo.setActive_start_date(start_date);
	vo.setActive_end_date(end_date);
	vo.setCustomer_type(customer_type);
	vo.setActive_plan(active_plan);
	vo.setActive_flag(new Integer(1));//1为新建活动
	vo.setCreator(input_operatorCode);
	vo.setInput_man(input_operatorCode);
	
	activityLocal.modi(vo);
	
	String opValue =request.getParameter("operators");
    String[] ListValues= Utility.splitString(opValue,"$");
    
    if(ListValues!=null){
    	activityLocal.deleteCustomerInfo(activity_id);
    	 for(int i=0;i<ListValues.length;i++)		
         {	                
         	 vo.setCust_id(Utility.parseInt(ListValues[i],new Integer(0)));
        	 activityLocal.appendCustomerInfo(vo); 
         }
    }
	
	bSuccess = true;
}

//根据serial_no取得activity的相关信息
if(serial_no.intValue()!= 0){
	vo.setSerial_no(serial_no);
	list = activityLocal.query(vo);
	
	if(list.size()>0){
		map = (Map)list.get(0);
	}	
}

if(map!=null){
	active_type_name = Utility.trimNull(map.get("ACTIVE_TYPE_NAME"));
	active_type = Utility.trimNull(map.get("ACTIVE_TYPE"));
	active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));
	start_date = Utility.trimNull(map.get("START_DATE"));
	end_date = Utility.trimNull(map.get("END_DATE"));
	manage_code = Utility.parseInt(Utility.trimNull(map.get("MANAGE_CODE")),new Integer(0));
	customer_type  = Utility.trimNull(map.get("CUSTOMER_TYPE"));
	active_plan = Utility.trimNull(map.get("ACTIVE_PLAN"));
	activity_id = Utility.parseInt(Utility.trimNull(map.get("ACTIVITY_ID")),new Integer(0));
}	

if(start_date.length()>0){
	start_date = start_date.substring(0,16);
}

if(end_date.length()>0){
	end_date = end_date.substring(0,16);
}

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("message.activityEdit2",clientLocale)%> </title>
<!--修改活动-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
/*取消*/
function CancelAction(){
	window.returnValue=null;
	window.close();
}

/*验证数据*/
function validateForm(form){
	if(!sl_check(document.theform.active_theme, "<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ",60,1)){return false;}//活动主题	
	if(!sl_checkChoice(document.theform.active_type,"<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ")){return false;}//活动类别
	if(!sl_check(document.theform.manage_name, "<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ",20,0)){return false;}//负责人		
	if(!sl_check(document.getElementById('start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//开始时间
	if(!sl_check(document.getElementById('end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//结束时间
	
	if(document.getElementById('end_date').value<document.getElementById('start_date').value){
		sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//结束日期不能比开始日期早
		return false;
	}
	
	if(!sl_check(document.theform.customer_type, "<%=LocalUtilis.language("class.correspondingCustomers",clientLocale)%> ",200,1)){return false;}//对应客户群			
	if(!sl_check(document.theform.active_plan, "<%=LocalUtilis.language("class.activityPlan",clientLocale)%> ",500,1)){return false;}//活动计划
	 
	return sl_check_update();		
}

function addCustomerinfo()
{
	var url = "activity_custominfo_query.jsp?serial_no="+document.theform.serial_no.value;		
	var ret = showModalDialog(url,'', 'dialogWidth:580px; dialogHeight:470px; status:0;help:0');
	document.theform.operators.value = ret;
}

/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="activity_edit.jsp" onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="activity_id" name="activity_id" value="<%=activity_id%>"/>
<input type="hidden" name="operators" value=""/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.activityEdit2",clientLocale)%> </b></font><!--修改活动-->
</div>

<br/>
<div align="center">
	<table border="0" width="100%"  cellspacing="0" cellpadding="4" class="product-list">
		<tr>
			<td  align="right" width="40%">*<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ：&nbsp;</td><!--活动主题-->
			<td  align="left" colspan="3">
				<input type="text" name="active_theme" size="30" value="<%= active_theme%>" onkeydown="javascript:nextKeyPress(this)"/>				
			</td>
		</tr>
		
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ：&nbsp;</td><!--活动类别-->
			<td  align="left" colspan="3">
				<select name="active_type" onkeydown="javascript:nextKeyPress(this)" style="width:175px">	
					<%=Argument.getDictParamOptions(3012,active_type)%>
				</select>				
			</td>			
		</tr>
		
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ：&nbsp;</td><!--负责人-->
			<td  align="left" colspan="3">	
				<select name="manage_code" onkeydown="javascript:nextKeyPress(this)" style="width:175px">
					<%=Argument.getManager_Value(manage_code)%>
				</select>		
			</td>	
		</tr>
		
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.startDate",clientLocale)%> ：&nbsp;</td><!--开始日期-->
			<td  align="left">
				<input type="text" name="start_date" id="start_date" size="30" onclick="javascript:setday(this);"  value="<%= start_date%>" readOnly/> 				
			</td>	
		</tr>
		
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.endDate",clientLocale)%> ：&nbsp;</td><!--结束日期-->
			<td  align="left">
				<input type="text" name="end_date" id="end_date" size="30" onclick="javascript:setday(this);" value="<%= end_date%>"  readOnly/> 		
			</td>
		</tr>	
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.customerchange",clientLocale)%> ：&nbsp;</td><!--客户选择-->
			<td  align="left">
				<input type="button" name="buttonname"  onclick="javascript:addCustomerinfo();" size="10" value="<%=LocalUtilis.language("message.pleaseSelect",clientLocale)%>"/> 		
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.correspondingCustomers",clientLocale)%> ：&nbsp;</td><!--对应客户群-->
			<td align="left" colspan="3">
				<textarea rows="3" name="customer_type"  cols="83"><%= customer_type%></textarea>			
			</td>
		</tr>	
				
		<tr> 
			<td align="right" >*<%=LocalUtilis.language("class.activePlan2",clientLocale)%> ：&nbsp;</td><!--计划和目标-->
			<td align="left" colspan="3">
				<textarea rows="3" name="active_plan" cols="83"><%= active_plan%></textarea>			
			</td>
		</tr>			
	</table>
</div>

<div align="right">
	<br>
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> <!--保存-->(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> <!--关闭-->(<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<% activityLocal.remove(); %>
</form>

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