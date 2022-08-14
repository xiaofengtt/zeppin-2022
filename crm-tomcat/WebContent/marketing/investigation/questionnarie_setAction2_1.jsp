<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer topic_id = Utility.parseInt(Utility.trimNull(request.getParameter("topic_id")),new Integer(0));
Integer ques_id = Utility.parseInt(Utility.trimNull(request.getParameter("ques_id")),new Integer(0));
Integer actionFlag3 = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag3")),new Integer(0));
String ques_title = Utility.trimNull(request.getParameter("ques_title"));
String topic_content = Utility.trimNull(request.getParameter("topic_content"));
String topic_value = Utility.trimNull(request.getParameter("topic_value"));

//声明辅助变量
boolean bSuccess = false;

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = null;
List list3 = null;
Map map3 = null;
//获得问卷信息
if(ques_id.intValue()>0){
	 vo = new QuestionnaireVO();	 
	 vo.setQues_id(ques_id);
	 
	 List list = local.queryQuestInfo(vo);
	 Map map = null;
	 
	 if(list.size()>0){
	 	 map = (Map)list.get(0);
	 	 ques_title = Utility.trimNull(map.get("QUES_TITLE"));
	 }
}
//获得题目信息
if(topic_id.intValue()>0){
	 vo = new QuestionnaireVO();	
	 vo.setTopic_id(topic_id);
	 
     List list2 = local.queryQuesTopic(vo);
	 Map map2 = null;
	 
	  if(list2.size()>0){
	  		map2 = (Map)list2.get(0);
	  		topic_content = Utility.trimNull(map2.get("TOPIC_CONTENT"));
	  }	
}
//保存参考值
if(request.getMethod().equals("POST")){
	if(actionFlag3.intValue()==1){
			 vo = new QuestionnaireVO();
			 
			 vo.setQues_id(ques_id);
			 vo.setTopic_id(topic_id);
			 vo.setQues_title(ques_title);
			 vo.setTopic_value(topic_value);
			 vo.setInputMan(input_operatorCode);
			 
			 local.appendTTopicScore(vo);
			 bSuccess = true;	
	}
}
//获得参考选项列表
if(topic_id.intValue()>0){
	 vo = new QuestionnaireVO();	
	 vo.setTopic_id(topic_id);
	 list3 = local.queryTTopicScroe(vo);
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.questionnarieSetAction2_1",clientLocale)%> </title>
<!--问卷一般选择题设置-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
window.onload=function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_actionFlag3= document.getElementById("actionFlag3").value;
	
	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
	}
	
	if(v_actionFlag3==0){
		document.getElementById("actionFlag3").value = 1;
	}
}

/*验证数据*/
function validateForm(form){
		if(!sl_check(document.getElementsByName('topic_value')[0], "<%=LocalUtilis.language("class.topicValue",clientLocale)%> ", 300, 1))return false;//参考选项	
		return true;
}

function saveAction(){
	var form = document.getElementById("theform");
	
	if(form.onsubmit()){
			if(sl_confirm("<%=LocalUtilis.language("message.saveGeneralMultipleChoiceInfo",clientLocale)%> ")){//保存一般选择题信息
					var form = document.getElementById("theform");
					form.action = "questionnarie_setAction2_1.jsp";
					form.submit();
			}		
	}
}

function delAction(serial_no){
	var v_topic_id= document.getElementById("topic_id").value;
	var v_ques_id= document.getElementById("ques_id").value;
	var url = "questionnarie_delTopicScore.jsp?check_serial_no="+serial_no;		
	
	showModalDialog(url,'','dialogWidth:550px;dialogHeight:400px;status:0;help:0');
    var url2 = "questionnarie_setAction2_1.jsp?topic_id="+v_topic_id+"&ques_id="+v_ques_id;
    changeUrl(url2);
}

//上一步
function previorsAction(){
	var v_ques_id= document.getElementById("ques_id").value;	
    var url = "questionnarie_setAction2.jsp?actionFlag2=1&ques_id="+v_ques_id;
    changeUrl(url);
}

//改变URL
function changeUrl(url){
	var a = document.createElement("a");
	a.href=url;
	document.body.appendChild(a);
    a.click();
}

//改变序号
function locationAction(flag,serialNo){
	var v_topic_id= document.getElementById("topic_id").value;
	var v_ques_id= document.getElementById("ques_id").value;
	var url = "topicScore_location.jsp?flag="+flag+"&serialNo="+serialNo;
	var url2 = "questionnarie_setAction2_1.jsp?topic_id="+v_topic_id+"&ques_id="+v_ques_id;
	
	showModalDialog(url,'','dialogWidth:450px;dialogHeight:450px;status:0;help:0');
     changeUrl(url2);
}
</script>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="topic_id" name="topic_id" value="<%=topic_id%>"/>
<input type="hidden" id="ques_id" name="ques_id" value="<%=ques_id%>"/>
<input type="hidden" id="actionFlag3" name="actionFlag3" value="<%=actionFlag3%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>" />

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=LocalUtilis.language("menu.surveyManage",clientLocale)%>>><%=LocalUtilis.language("menu.questionnaireTopicSet",clientLocale)%> >><%=LocalUtilis.language("menu.generalChoiceSet",clientLocale)%> </b></font>
		<!--调查管理>>问卷题目设置 >>一般选择题设置-->
	</div>	
	
</div>
<br/>
<div style="margin-left:5px">
	<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="table-popup">
				<tr style="background:F7F7F7;">
			 			<td colspan="4" align="left"><font size="3" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("menu.generalChoiceSet",clientLocale)%> </b></font></td>
						<!--一般选择题设置-->
			 	</tr>
			 	
				 <tr style="background:F7F7F7;">	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!--问卷名称-->
					 <td width="*" colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_title%></font>  </td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.quesTitle",clientLocale)%> ：</font></td><!--问卷题目-->
					 <td width="*" colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=topic_content%></font>  </td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.topicValue",clientLocale)%> ：</font></td><!--参考选项-->
					 <td width="*" colspan="3"><input type="text" name="topic_value" size="30" value="" onkeydown="javascript:nextKeyPress(this)"/></td>
				 </tr>
				 
	 			 <tr style="background:F7F7F7;">	
				 	<td colspan="4" align="right">
				 			<button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.up",clientLocale)%> " onclick="javascript: previorsAction();"><%=LocalUtilis.language("message.up",clientLocale)%> </button>							
				 			&nbsp;&nbsp;&nbsp;<!--上一步-->		
				 			<button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.add",clientLocale)%> " onclick="javascript: saveAction();"><%=LocalUtilis.language("message.add",clientLocale)%> (<u>S</u>)</button>
                            <!--添加-->				 			
				 	</td>							 
				 </tr>
	</table>
</div>

<div style="margin-left:5px;margin-top:10px;">
		<table  id="table1" cellSpacing="1" cellPadding="2" width="98%"  bgcolor="#CCCCCC">
				 <tr style="background:6699FF;">
			 			<td align="center"  width="20%"><%=LocalUtilis.language("class.topicValue2NO",clientLocale)%> </td><!--选项编号-->
			 			<td align="center"  width="*"><%=LocalUtilis.language("class.topicValue2",clientLocale)%> </td><!--选项值-->
			 			<td align="center"  width="10%"><%=LocalUtilis.language("message.up2",clientLocale)%> </td><!--向上-->
						<td align="center"  width="10%"><%=LocalUtilis.language("message.down",clientLocale)%> </td><!--向下-->
						<td align="center"  width="10%"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
			 	</tr>
<%
if(list3.size()>0){
	Iterator iterator = list3.iterator();
	Integer s_serial_no = new Integer(0);
	String s_topic_value_no = "";
	String s_topic_value = "";
	int iCount=0;
	
	while(iterator.hasNext()){
			iCount++;
			map3 = (Map)iterator.next();
			
			s_serial_no = Utility.parseInt(Utility.trimNull(map3.get("SERIAL_NO")),new Integer(0));
			s_topic_value_no = Utility.trimNull(map3.get("TOPIC_VALUE_NO"));
			s_topic_value = Utility.trimNull(map3.get("TOPIC_VALUE"));
	
%>
	<tr class="tr<%=iCount%2%>">
			<td align="center"><%= s_topic_value_no%></td> 
			<td align="center"><%= s_topic_value%></td> 
			<td align="center" ><A title="<%=LocalUtilis.language("message.up2",clientLocale)%> " href="#" onclick="javascript:locationAction(1,'<%=s_serial_no%>');">↑</a></td><!--向上-->
			<td align="center" ><A title="<%=LocalUtilis.language("message.down",clientLocale)%> " href="#" onclick="javascript:locationAction(2,'<%=s_serial_no%>');">↓</a></td><!--向下-->	
		 	 <td align="center" >
				<a href="#" onclick="javascript:delAction(<%=s_serial_no%>);">
					<img src="<%=request.getContextPath()%>/images/recycle.gif" height="16" width="16" alt="<%=LocalUtilis.language("message.delete",clientLocale)%> "/><!--删除-->
				</a>								
			</td>	
	</tr>
<%
	}
}
%>		 			 	
		</table>
</div>
<%local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
