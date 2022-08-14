<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer actionFlag2 = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag2")),new Integer(0));//1.新增；2编辑
Integer ques_id = Utility.parseInt(Utility.trimNull(request.getParameter("ques_id")),new Integer(0));
Integer topic_id = Utility.parseInt(Utility.trimNull(request.getParameter("topic_id")),new Integer(0));

//声明辅助变量
boolean bSuccess = false;

String ques_title = "";
String ques_no = "";
String topic_content ="";
String topic_remark = "";
String topic_type ="";
Integer topic_serial_no = new Integer(0);

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = null;

if(ques_id.intValue()>0){
	 vo = new QuestionnaireVO();	 
	 vo.setQues_id(ques_id);
	 
	 List list = local.queryQuestInfo(vo);
	 Map map = null;
	 
	 if(list.size()>0){
	 	 map = (Map)list.get(0);
	 	 
	 	 ques_title = Utility.trimNull(map.get("QUES_TITLE"));
	 	 ques_no 	 = Utility.trimNull(map.get("QUES_NO"));
	 }
}

if(request.getMethod().equals("POST")){
		String r_topic_content = Utility.trimNull(request.getParameter("topic_content"));
		String r_topic_remark = Utility.trimNull(request.getParameter("topic_remark"));
		String r_topic_type =Utility.trimNull(request.getParameter("topic_type"));

		if(actionFlag2.intValue()>0){
			vo = new QuestionnaireVO();
		
			vo.setQues_id(ques_id);
			vo.setQues_no(ques_no);
			vo.setQues_title(ques_title);
			vo.setTopic_content(r_topic_content);
			vo.setTopic_type(r_topic_type);
			vo.setRemark(r_topic_remark);
			vo.setInputMan(input_operatorCode);
			
			if(actionFlag2.intValue()==1){
					topic_id = local.appendQuesTopic(vo);
					bSuccess = true;
			}else if(actionFlag2.intValue()==2){
					vo.setTopic_id(topic_id);
					local.modiQuesTopic(vo);
					bSuccess = true;
			}
		}
}

if(actionFlag2.intValue()==2&&topic_id.intValue()>0){
	 vo = new QuestionnaireVO();
	
	 vo.setTopic_id(topic_id);
     List list2 = local.queryQuesTopic(vo);
	 Map map2 = null;
	 
	  if(list2.size()>0){
	  		map2 = (Map)list2.get(0);
	  		
	  		topic_type = Utility.trimNull(map2.get("TOPIC_TYPE"));
	  		topic_content = Utility.trimNull(map2.get("TOPIC_CONTENT"));
	  		topic_remark =  Utility.trimNull(map2.get("REMARK"));	 
	  		topic_serial_no = Utility.parseInt(Utility.trimNull(map2.get("TOPIC_SERIAL_NO")),new Integer(0));
	  }	
}

//题目列表
vo = new QuestionnaireVO();
vo.setQues_id(ques_id);
List list = local.queryQuesTopic(vo);
Map map = null;
int iCount = 0;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.questionnaireSet2",clientLocale)%> </title>
<!--问卷设置2-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
window.onload=function(){
	var v_bSuccess = document.getElementById("bSuccess").value;

	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
	}
}

/*验证数据*/
function validateForm(form){
		if(!sl_checkChoice(document.getElementsByName('topic_type')[0],"<%=LocalUtilis.language("class.topicType",clientLocale)%> ")){return false;}//题目类别
		if(!sl_check(document.getElementsByName('topic_content')[0], "<%=LocalUtilis.language("class.topicContent",clientLocale)%> ", 300, 1))return false;//题目内容	
		if(!sl_check(document.getElementsByName('remark')[0], "<%=LocalUtilis.language("class.topicRemark",clientLocale)%> ", 300, 0))return false;	//题目说明
		
		return true;
}

function saveAction(){
	var form = document.getElementById("theform");
	
	if(form.onsubmit()){
			var form = document.getElementById("theform");
			form.action = "questionnarie_setAction2.jsp";
			form.submit();
	}
}

function chooseItems(topic_id){
		var url = "questionnarie_setAction2_1.jsp?ques_id=<%=ques_id%>";		
		var url = url + "&topic_id=" +topic_id;
		changeUrl(url);
}

function delAction(topic_id){
	var v_topic_id= document.getElementById("topic_id").value;
	//var v_ques_id= document.getElementById("ques_id").value;
	var url = "questionnarie_delQuesTopic.jsp?ques_id=<%=ques_id%>";		
	var url = url + "&topic_id=" +topic_id;
	
	showModalDialog(url,'','dialogWidth:550px;dialogHeight:400px;status:0;help:0');
     var url2 = "questionnarie_setAction2.jsp?ques_id=<%=ques_id%>&v_topic_id="+v_topic_id;
     changeUrl(url2);
}
//上一步
function previorsAction(){
	var v_ques_id= document.getElementById("ques_id").value;	
    var url = "questionnarie_setAction1.jsp?actionFlag=2&ques_id="+v_ques_id;
    changeUrl(url);
}
//下一步
function nextAction(){
	var v_ques_id= document.getElementById("ques_id").value;	
    var url = "questionnarie_setAction3.jsp?ques_id="+v_ques_id;
    changeUrl(url);
}
//添加
function addAction(){
	var v_ques_id= document.getElementById("ques_id").value;	
    var url = "questionnarie_setAction2.jsp?actionFlag2=1&ques_id="+v_ques_id;
	changeUrl(url);
}
//编辑
function modiAction(topic_id){
	var v_ques_id= document.getElementById("ques_id").value;
	var url = "questionnarie_setAction2.jsp?actionFlag2=2&ques_id="+v_ques_id+"&topic_id="+topic_id;	
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
function locationAction(flag,topic_id){
	var url = "quesTopic_location.jsp?flag="+flag+"&topic_id="+topic_id;
	showModalDialog(url,'','dialogWidth:450px;dialogHeight:450px;status:0;help:0');
	 var url2 = "questionnarie_setAction2.jsp?ques_id=<%=ques_id%>&v_topic_id="+topic_id;
     changeUrl(url2);
}

function changeTopicType(){
	var s_topic_type = document.getElementById("s_topic_type").value;
	document.getElementById("topic_type").value = s_topic_type;
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="topic_id" name="topic_id" value="<%=topic_id%>"/>
<input type="hidden" id="ques_id" name="ques_id" value="<%=ques_id%>"/>
<input type="hidden" id="actionFlag2" name="actionFlag2" value="<%=actionFlag2%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>" />

<div align="left" class="page-title">

	<font color="#215dc6"><b><%=LocalUtilis.language("menu.surveyManage",clientLocale)%>>><%=LocalUtilis.language("menu.questionnaireTopicSet",clientLocale)%>  </b></font>
	<!--调查管理>>问卷题目设置-->
</div>	
<br/>
<div align="right" style="margin-right:8px;" class="btn-wrapper">
	 	<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="<%=LocalUtilis.language("message.append",clientLocale)%> " onclick="javascript: addAction();"><%=LocalUtilis.language("message.append",clientLocale)%> </button>
		<!--新增-->
</div>

<div style="width:100%; ">
		<div style=" float:left; width:43%;  height:300px; margin-left:5px; ">
			 <table cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
			 			 	<tr style="background:F7F7F7;">
						 			<td colspan="2" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("menu.questionnaireTopicSet",clientLocale)%> </b></font></td>
									<!--问卷题目设置-->
						 	</tr>
						 	
							 <tr style="background:F7F7F7;">	
								 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.quesNO",clientLocale)%> ：</font></td><!--问卷编号-->
								 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_no%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!--问卷名称-->
								 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_title%></font>  </td>
							 </tr>
							 <%if(actionFlag2.intValue()==2&&topic_id.intValue()>0){%>
								<tr style="background:F7F7F7;">	
									 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.topicSerialNO",clientLocale)%> ：</font></td><!--题目序号-->
									 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=topic_serial_no%></font></td>
								 </tr>
							 <%}%>
							  <tr style="background:F7F7F7;">	
								 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.topicType",clientLocale)%> ：</font></td><!--题目类别-->
								 <td width="*">
						 				<select name="s_topic_type" id="s_topic_type" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:changeTopicType();" style="width:175px"  <%if(actionFlag2.intValue()==2&&topic_id.intValue()>0){out.print("disabled");}%>>	
											<%=Argument.getDictParamOptions(3051,topic_type)%>
										</select>								
										<input type="hidden" name="topic_type" id="topic_type" value="<%=topic_type%>" />	 
								 </td>
							 </tr>
							 
							  <tr style="background:F7F7F7;">	
								 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.topicContent",clientLocale)%> ：</font></td><!--题目内容-->
								 <td width="*">
								 	<textarea rows="5" name="topic_content"  style="width:100%;"><%=topic_content%></textarea>	
								 </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.topicRemark",clientLocale)%> ：</font></td><!--题目说明-->
								 <td width="*">
								 	<textarea rows="5" name="topic_remark"  style="width:100%;"><%=topic_remark%></textarea>	
								 </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
							 	<td colspan="2" align="right">
							 			 <button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.save",clientLocale)%> " onclick="javascript: saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> </button>
										 <!--保存-->
										&nbsp;&nbsp;&nbsp;		
										<button type="button"  class="xpbutton3" accessKey=c id="btnColse" name="btnColse" title="<%=LocalUtilis.language("message.close",clientLocale)%> " onclick="javascript: window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> </button>
										<!--关闭-->										
							 	</td>
							 </tr>
							 
			 </table>
		</div>
		
		<div style="float:right; width:55%; height:300px; overflow-y:auto;">
			 <table cellSpacing="1" cellPadding="2" width="100%"  bgcolor="#CCCCCC">
			 			 	<tr style="background:6699FF;">
						 			<td align="center"  width="15%"><%=LocalUtilis.language("class.topicSerialNO",clientLocale)%> </td><!--题目序号-->
						 			<td align="center"  width="15%"><%=LocalUtilis.language("class.topicType",clientLocale)%> </td><!--题目类别-->
									<td align="center"  width="*"><%=LocalUtilis.language("class.topicContent",clientLocale)%> </td><!--题目内容-->
									<td align="center"  width="10%"><%=LocalUtilis.language("message.up2",clientLocale)%> </td><!--向上-->
									<td align="center"  width="10%"><%=LocalUtilis.language("message.down",clientLocale)%> </td><!--向下-->
									<td align="center"  width="10%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
									<td align="center"  width="10%"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
						 	</tr>
<%
//声明字段
Iterator iterator = list.iterator();
Integer s_topic_id = new Integer(0);
Integer s_topic_serial_no = new Integer(0);
String s_topic_type_name = "";
String s_topic_type = "";
String s_topic_content ="";

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	s_topic_id = Utility.parseInt(Utility.trimNull(map.get("TOPIC_ID")),new Integer(0));
	s_topic_serial_no = Utility.parseInt(Utility.trimNull(map.get("TOPIC_SERIAL_NO")),new Integer(0));
	s_topic_type = Utility.trimNull(map.get("TOPIC_TYPE"));
	s_topic_type_name = Argument.getDictParamName(0,s_topic_type);
	s_topic_content  = Utility.trimNull(map.get("TOPIC_CONTENT"));
%>
		<tr class="tr<%=iCount%2%>">
			<td align="center"><%= s_topic_serial_no%></td> 
			<td align="center">
			<%if(s_topic_type.equals("305102")){%>
				<a href="javascript:chooseItems(<%=s_topic_id%>);" class="a2">
					 <U><%= s_topic_type_name%></U>
				</a>
			<%}
			else{%>
					<%= s_topic_type_name%>
			<%}%>
			</td> 
			<td align="left">&nbsp;&nbsp;<%= s_topic_content%></td> 	
			<td align="center" ><A title="<%=LocalUtilis.language("message.up2",clientLocale)%> " href="#" onclick="javascript:locationAction(1,'<%=s_topic_id%>');">↑</a></td><!--向上-->
			<td align="center" ><A title="<%=LocalUtilis.language("message.down",clientLocale)%> " href="#" onclick="javascript:locationAction(2,'<%=s_topic_id%>');">↓</a></td><!--向下-->				
			<td align="center">         
	              	<a href="javascript:modiAction('<%=s_topic_id%>');">
	               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
	               	</a>
             </td>    
 			 <td align="center" >
					<a href="#" onclick="javascript:delAction(<%=s_topic_id%>);">
						<img src="<%=request.getContextPath()%>/images/recycle.gif" height="16" width="16" alt="<%=LocalUtilis.language("message.delete",clientLocale)%> "/><!--删除-->
					</a>								
			</td>	
		</tr>
<%}%>

	<%for(int i=0;i<(8-iCount);i++){%>     	
	         <tr class="tr0">
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>      
	            <td align="center">&nbsp;</td>      
	            <td align="center">&nbsp;</td>      
	            <td align="center">&nbsp;</td>      
	            <td align="center">&nbsp;</td>   
	         </tr>           
	<%}%> 
			 </table>	
		</div>

		<div align="right" style="float:right; width:49%; margin-top:10px;">		
				<button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.up",clientLocale)%> " onclick="javascript: previorsAction();"><%=LocalUtilis.language("message.up",clientLocale)%> </button>
				<!--上一步-->
				&nbsp;&nbsp;&nbsp;		
				<button type="button"  class="xpbutton3" accessKey=c id="btnColse" name="btnColse" title="<%=LocalUtilis.language("message.next",clientLocale)%> " onclick="javascript: nextAction();"><%=LocalUtilis.language("message.next",clientLocale)%> </button>
				<!--下一步-->
		</div>	
</div>

<%local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
