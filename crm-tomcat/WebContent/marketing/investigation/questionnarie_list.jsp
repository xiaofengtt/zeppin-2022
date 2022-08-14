<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
String q_quest_title =  Utility.trimNull(request.getParameter("q_quest_title"));
Integer q_creator = Utility.parseInt(Utility.trimNull(request.getParameter("q_creator")),new Integer(0));

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = new QuestionnaireVO();
//设置值
vo.setQues_title(q_quest_title);
vo.setCreator(q_creator);
vo.setStatus(new Integer(1));
vo.setInputMan(input_operatorCode);

//不分页 查询全部
List list = local.queryQuestInfo(vo);
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
<title><%=LocalUtilis.language("menu.questionnarieList",clientLocale)%> </title>
<!--问卷信息列表-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}

function AppendAction(){
	showModalDialog('questionnarie_setAction1.jsp','','dialogWidth:950px;dialogHeight:450px;status:0;help:0');
	window.location.reload();
}

function ModiAction(ques_id){
	var url = "questionnarie_setAction1.jsp?actionFlag=2&ques_id="+ques_id;
	showModalDialog(url,'','dialogWidth:950px;dialogHeight:450px;status:0;help:0');
	window.location.reload();	
}

function QueryAction(){
	var q_quest_title = document.getElementById("q_quest_title").value;
	var q_creator = document.getElementById("q_creator").value;
	
	var url = "questionnarie_list.jsp?q_quest_title="+q_quest_title;
	var url = url +"&q_creator=" +q_creator;
	
	window.location.href=url;
}

/*删除功能*/
function DelAction(){
	if(checkedCount(document.getElementsByName("ques_id")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "questionnarie_delQuesInfo.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

function showAction(ques_id){
	var url = "questionnarie_setAction3.jsp?show_flag=1&&ques_id="+ques_id;
	showModalDialog(url,'','dialogWidth:950px;dialogHeight:450px;status:0;help:0');
	showWaitting(0);
}

</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get" >
<div id="queryCondition" class="qcMain" style="display:none;width:480px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.questTitle",clientLocale)%> ： </td><!--问卷标题-->
			<td  align="left">
					<input type="text" name="q_quest_title" id="q_quest_title" value="<%= q_quest_title%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
			<td  align="right"><%=LocalUtilis.language("class.created",clientLocale)%> : </td><!--创建人-->
			<td  align="left">
				<select name="q_creator" id="q_creator" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%=Argument.getOperatorOptions(q_creator)%>
				</select>
			</td>		
		</tr>
		
			<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--确认-->	 				
			</td>
		</tr>	
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	

	<div align="right" class="btn-wrapper">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--查询--><!--查询--><%}%>
		
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
		<button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("menu.questionnaireSet",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("index.msg.set",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--问卷设置--><!--设置--><%}%>
		
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<!--删除--><!--删除--><%}%>
	</div>	
	<br/>
</div>

<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" >
		<tr class="trh">
			<td align="center" width="15%">
				<input type="checkbox" 
							name="btnCheckbox" 
							class="selectAllBox"	
							onclick="javascript:selectAllBox(document.theform.ques_id,this);" />
				<%=LocalUtilis.language("class.quesNO",clientLocale)%> 
			</td><!--问卷编号-->			
			<td align="center" width="*"><%=LocalUtilis.language("class.questionaireName",clientLocale)%> </td><!--问卷名称-->
			<td align="center" width="10%"><%=LocalUtilis.language("class.created",clientLocale)%> </td><!--创建人-->
			<td align="center" width="15%"><%=LocalUtilis.language("class.Insert_date",clientLocale)%> </td><!--创建时间-->
			<td align="center" width="8%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		</tr>
<%
//声明字段
Iterator iterator = list.iterator();
Integer ques_id = new Integer(0);
String ques_no = "";
String ques_title = "";
String creator_name = "";
String creator_time = "";

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	ques_id = Utility.parseInt(Utility.trimNull(map.get("QUES_ID")),new Integer(0));
	ques_no = Utility.trimNull(map.get("QUES_NO"));
	ques_title = Utility.trimNull(map.get("QUES_TITLE"));
	creator_name = Utility.trimNull(map.get("CREATOR_NAME"));
	creator_time = Utility.trimNull(map.get("INPUT_TIME"));
	
	if(creator_time.length()>0){
		creator_time = creator_time.substring(0,16);
	}	
%>
			<tr class="tr<%=iCount%2%>">
				  <td align="center">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%">							   
										<input type="checkbox" name="ques_id" value="<%=ques_id%>" class="flatcheckbox">			
								</td>
								<td width="90%" align="left">&nbsp;&nbsp;<%=ques_no%></td>
							</tr>
						</table>
					</td>	
					 <td align="left">&nbsp;&nbsp;<a href="javascript:showAction(<%=ques_id%>)" class="a2"><%= ques_title%></a></td>        
					 <td align="center"><%= creator_name%></td>   
					 <td align="center"><%= creator_time%></td>   
					 <td align="center">         
							<%if (input_operator.hasFunc(menu_id, 108)) {%>
				              	<a href="javascript:ModiAction(<%= ques_id%>);">
				               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
				               	</a>
							<%} %>
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
	         </tr>           
	<%}%> 
	</table>


</div>

<%local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
