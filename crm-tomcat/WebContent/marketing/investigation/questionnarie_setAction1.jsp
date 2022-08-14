<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
String ques_title = Utility.trimNull(request.getParameter("ques_title"));
String remark =  Utility.trimNull(request.getParameter("remark"));
Integer ques_id = Utility.parseInt(Utility.trimNull(request.getParameter("ques_id")),new Integer(0));
String ques_no =  Utility.trimNull(request.getParameter("ques_no"));
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(1));//1 新建，2 编辑

//声明辅助变量
boolean bSuccess = false;

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = null;

if(request.getMethod().equals("POST")){
	 vo = new QuestionnaireVO();
	 
	 vo.setQues_title(ques_title);
	 vo.setRemark(remark);
	 vo.setInputMan(input_operatorCode);
	 
	 if(actionFlag.intValue()==1){
	 		 ques_id = local.appendQuestInfo(vo);
	 		 bSuccess = true;
	 }
	 else if(actionFlag.intValue() ==2){
		 	vo.setQues_id(ques_id);
		 	local.modiQuestInfo(vo);
		 	bSuccess = true;
	 }
}

if(ques_id.intValue()>0){
	 vo = new QuestionnaireVO();
	 
	 vo.setQues_id(ques_id);
	 
	 List list = local.queryQuestInfo(vo);
	 Map map = null;
	 
	 if(list.size()>0){
	 	 map = (Map)list.get(0);
	 	 
	 	 ques_title = Utility.trimNull(map.get("QUES_TITLE"));
	 	 remark =  Utility.trimNull(map.get("REMARK"));	 
	 	 ques_no 	 = Utility.trimNull(map.get("QUES_NO"));
	 }
}

%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.questionnaireSet1",clientLocale)%> </title>
<!--问卷设置1-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
window.onload=function(){
	var ques_id = document.getElementById("ques_id").value;
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(ques_id>0){
		 document.getElementById("btnNext").style.display = "";
	}
	
	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
	}
}

function appendAction(){
	var form = document.getElementById("theform");
	
	if(form.onsubmit()&&sl_confirm("<%=LocalUtilis.language("message.saveQuestionnarieInfo",clientLocale)%> ")){//保存问卷信息
			form.action = "questionnarie_setAction1.jsp";
			form.submit();
	}
}

/*验证数据*/
function validateForm(form){
		if(!sl_check(document.getElementsByName('ques_title')[0], "<%=LocalUtilis.language("class.quesTitle",clientLocale)%> ", 30, 1))return false;//问卷题目	
		if(!sl_check(document.getElementsByName('remark')[0], "<%=LocalUtilis.language("class.quesRemark",clientLocale)%> ", 300, 0))return false;//问卷说明	
		
		return true;
}

function nextAction(){	
	var a = document.createElement("a");
	var v_ques_id= document.getElementById("ques_id").value;
	
    a.href = "questionnarie_setAction2.jsp?actionFlag2=1&ques_id="+v_ques_id;
    document.body.appendChild(a);
    a.click();
}

</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="actionFlag" name="actionFlag" value="<%=actionFlag%>"/>
<input type="hidden" id="ques_id" name="ques_id" value="<%=ques_id%>"/>
<input type="hidden" id="ques_no" name="ques_no" value="<%=ques_no%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>" />

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=LocalUtilis.language("menu.surveyManage",clientLocale)%>>><%=LocalUtilis.language("menu.questionnaireSet",clientLocale)%>  </b></font><!--调查管理>>问卷设置--> 
	</div>	
</div>
<br/>
<p class="title-table"><b>&nbsp;&nbsp;<%=LocalUtilis.language("menu.questionnaireInfoSet",clientLocale)%> </b></p>
<div style="height:300px;margin-left:10px;">
	<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
				<tr style="background:F7F7F7;">
			 			<td colspan="2" align="left"></td>
						<!--问卷信息设置-->
			 	</tr>
			 	
			 	 <tr >	
					 <td width="180px" align="right"><font size="3" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!--问卷名称-->
					 <td>
					 		<input type="text" name="ques_title" size="30" value="<%= ques_title%>" onkeydown="javascript:nextKeyPress(this)"/>		
					  </td>
				 </tr>
				 
				 <tr >	
					 <td width="180px" valign="top" align="right"><font size="3" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.quesRemark",clientLocale)%> ：</font></td><!--问卷说明-->
					 <td>
					 		<textarea rows="10" name="remark"  style="width:100%;"><%= remark%></textarea>
					  </td>
				 </tr>
	</table>
</div>

<div align="right" style="margin-top:10px;margin-right:10px">		
	<button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.save",clientLocale)%> " onclick="javascript: appendAction();"><%=LocalUtilis.language("message.save",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;<!--保存-->	
	<button type="button"  class="xpbutton3" style="display:none;" accessKey=n id="btnNext" name="btnNext" title="<%=LocalUtilis.language("message.next",clientLocale)%> " onclick="javascript: nextAction();"><%=LocalUtilis.language("message.next",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;<!--下一步-->	
	<button type="button"  class="xpbutton3" accessKey=c id="btnColse" name="btnColse" title="<%=LocalUtilis.language("message.close",clientLocale)%> " onclick="javascript: window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> 
    </button><!--关闭-->
</div>	
<%local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
