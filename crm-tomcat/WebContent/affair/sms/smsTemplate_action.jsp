<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer tempID = Utility.parseInt(Utility.trimNull(request.getParameter("tempID")),new Integer(0));
//声明辅助变量
boolean bSuccess = false;
Map rsMap = new HashMap();
String temp_title = "";
String temp_type = "";
String temp_content = "";
//获得对象
SmsTemplateLocal smsTemplocal = EJBFactory.getSmsTemplate();
SmsTemplateVO vo = new SmsTemplateVO();
//保存页面对象
if(request.getMethod().equals("POST")){

	vo = new SmsTemplateVO();
	temp_title = Utility.trimNull(request.getParameter("temp_title"));
	temp_type = Utility.trimNull(request.getParameter("temp_type"));
	temp_content = Utility.trimNull(request.getParameter("temp_content"));

	vo.setTitle(temp_title);
	vo.setTempType(temp_type);
	vo.setInput_man(input_operatorCode);
	vo.setContent(temp_content);


	if(tempID.intValue()>0){
		vo.setTempId(tempID);
		smsTemplocal.modi(vo);	
	}
	else{
		smsTemplocal.append(vo);	
	}
	
	bSuccess = true;	
}

if(tempID.intValue()>0){
	vo = new SmsTemplateVO();
	vo.setTempId(tempID);
	rsMap = smsTemplocal.load(vo);

	temp_title = Utility.trimNull(rsMap.get("Title"));
	temp_type = Utility.trimNull(rsMap.get("TempType"));
	temp_content = Utility.trimNull(rsMap.get("Content"));
}
 %>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.smsTemplateSettings",clientLocale)%> </title><!--短信模板设置-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();		
		window.close();
	}
}
/*取消*/
function CancelAction(){
	window.returnValue=null;
	window.close();
}
/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "smsTemplate_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}
/*验证数据*/
function validateForm(form){	
	if(document.getElementsByName('temp_title')[0].value == ""){
		alert("标题不能为空");
		return false;
	}
	
	if(document.getElementsByName('temp_type')[0].value == ""){
		alert("请选择类型");
		return false;
	}
	if(document.getElementsByName('temp_content')[0].value == ""){
		alert("模板内容不能为空");
		return false;
	}
	if(!sl_check(document.getElementsByName('temp_title')[0],"<%=LocalUtilis.language("class.tempTitleName",clientLocale)%> ",20,0)){ return false;}//短信模板标题
	if(!sl_check(document.getElementsByName('temp_content')[0],"<%=LocalUtilis.language("class.smsTemplateContent",clientLocale)%> ",700,0)){ return false;}//短信模板内容
	if(!sl_checkChoice(document.getElementsByName('temp_type')[0], "<%=LocalUtilis.language("class.tempType",clientLocale)%> ")){return false;} //短信模板类型
    return sl_check_update();
}
</script>
</HEAD>
<BODY class="body body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="tempID" name="tempID" value="<%=tempID%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.smsTemplateSettings",clientLocale)%> </b></font><!--短信模板设置-->
	
</div>
<br/>
<div align="center">
	<table cellSpacing="1" cellPadding="2" width="100%"  bgcolor="#CCCCCC" class="product-list">
	
		<tr >
			<td><font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.tempTitleName",clientLocale)%> :</font></td><!--短信模板标题-->
			<td colspan="3">
				<input type="text" name="temp_title" id="temp_title" size="30"  value="<%= temp_title%>"/> 			
			</td>
		</tr>
		<tr >
			<td><font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.tempType",clientLocale)%> :</font></td><!--短信模板类型-->
			<td colspan="3">
				<select name="temp_type" style="width:120px">
					<%= Argument.getDictParamOptions(4038,temp_type) %>
				</select>				
			</td>
		</tr>
		<tr >
			<!--短信模板内容--><!--可用"%1"代替客户名称-->
            <td  valign=top>
            	<font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.smsTemplateContent",clientLocale)%>:</font><br>
            	<font size="1" face="微软雅黑">&nbsp;&nbsp;[<%=LocalUtilis.language("message.insteadOfCustomerName",clientLocale)%>]</font>
            </td>
			<td  colspan="3">
				<textarea rows="3" name="temp_content" id="temp_content"onkeydown="javascript:nextKeyPress(this)" cols="80"><%=temp_content%></textarea>			
			</td>
		</tr>
			
		<tr >	
			<td colspan="4" align="right">
                <!--保存-->
				<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
                <!--关闭-->
				<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
			</td>
		</tr>
	</table>
</div>
</form>
</BODY>

</HTML>