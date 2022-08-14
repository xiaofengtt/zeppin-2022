<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer ques_id = Utility.parseInt(Utility.trimNull(request.getParameter("ques_id")),new Integer(0));
Integer show_flag = Utility.parseInt(Utility.trimNull(request.getParameter("show_flag")),new Integer(0));

//设置变量
String ques_no ="";
String ques_title="";
String ques_remark = "";
Integer ques_creator=new Integer(0);
String ques_creatorName = "";
String ques_creatTime ="";

//定义辅助变量
List list = null;
List list2 = null;
List list3 = null;
Map map = null;
Map map2 = null;
Map map3 = null;
int iCount = 0;
int iCount2 = 0;


//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = null;

if(ques_id.intValue()>0){
	 vo = new QuestionnaireVO();	 
	 vo.setQues_id(ques_id);
	 
	 list = local.queryQuestInfo(vo);
	 
	 if(list.size()>0){
	 	 map = (Map)list.get(0);
		
		ques_no = Utility.trimNull(map.get("QUES_NO"));
		ques_title = Utility.trimNull(map.get("QUES_TITLE"));
		ques_creator = Utility.parseInt(Utility.trimNull(map.get("CREATOR")),new Integer(0));
		ques_creatorName = Utility.trimNull(map.get("CREATOR_NAME"));
		ques_creatTime = Utility.trimNull(map.get("INPUT_TIME"));
		ques_remark = Utility.trimNull(map.get("REMARK"));
	 }
}

//问卷题目列表
vo = new QuestionnaireVO();

vo.setQues_id(ques_id);
list2 = local.queryQuesTopic(vo);

if(ques_creatTime.length()>0){
	ques_creatTime = ques_creatTime.substring(0,16);
}	

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title><%=LocalUtilis.language("menu.questionnarieSetAction3",clientLocale)%> </title>
<!--问卷信息查看-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<base target="_self">
<style>
.headdarkbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}

.headbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}
</style>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	show(1);
	
	var show_flag = document.getElementById("show_flag").value;
	
	if(show_flag==1){
		document.getElementById("btnPre").style.display="none";
		document.getElementById("btnSure").style.display="none";
	}
}

function show(parm){
   for(i=1;i<3;i++) {  
	     if(i!= parm){	     	
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");		   
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display = ''");
		 } 
	}
}

function previorsAction(){
	var a = document.createElement("a");
	var v_ques_id= document.getElementById("ques_id").value;
	
    a.href = "questionnarie_setAction2.jsp?actionFlag2=1&ques_id="+v_ques_id;
    document.body.appendChild(a);
    a.click();
}

/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "activeTr"+serial_no;
	var v_table = "activeTablePro"+serial_no;
	var v_flag = "activeFlag_display"+serial_no;
	var v_div = "activeDiv"+serial_no;
	var v_image = "activeImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<input type="hidden" id="ques_id" name="ques_id" value="<%=ques_id%>"/>
<input type="hidden" id="show_flag" name="show_flag" value="<%=show_flag%>"/>

<div align="left" class="page-title page-title-noborder">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.surveyManage",clientLocale)%>>><%=LocalUtilis.language("menu.questionnarieSetAction3",clientLocale)%>  </b></font>
	<!--调查管理>>问卷信息查看-->
</div>	

<div align="center">
		<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
					<TBODY>
						<TR>
							<TD vAlign=top>&nbsp;</TD>							
							<TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=90 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("menu.questionnarieInfo",clientLocale)%> </TD>
							<!--问卷信息-->
							<TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=90 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("menu.questionnarieTopicInfo",clientLocale)%> </TD>
							<!--问卷题目信息-->
						</TR>
				</TBODY>
		</TABLE>
</div>
<br/>
<div id="r1" align="center" style="display:none;height:300px;" >
		<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="table-popup">
				<tr style="background:F7F7F7;">
			 			<td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("menu.questionnarieInfo",clientLocale)%> </b></font></td>
						<!--问卷信息-->
			 	</tr>
			 	
 				<tr style="background:F7F7F7;">	
 					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.quesNO",clientLocale)%> ：</font></td><!--问卷编号-->
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_no%></font>  </td>
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!--问卷名称-->
					 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_title%></font>  </td>
				 </tr>
				 
	 			<tr style="background:F7F7F7;">	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.ques_creatorName",clientLocale)%> ：</font></td>
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_creatorName%></font>  </td><!--问卷创建人-->
					<td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.ques_creatTime",clientLocale)%> ：</font></td><!--问卷创建时间-->
					 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_creatTime%></font>  </td>
				 </tr>
			 	
			 	 <tr style="background:F7F7F7;">	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.quesRemark",clientLocale)%> ：</font></td><!--问卷说明-->
					 <td width="*" colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=ques_remark%></font></td>
				 </tr>
		</table>
</div>

<div id="r2" style="display:none;height:300px;" align="center" class="table-popup">
		 <table  id="table1" cellSpacing="1" cellPadding="2" width="100%"  bgcolor="#CCCCCC">
		 			 	<tr style="background:6699FF;">
					 			<td align="center"  width="15%"><%=LocalUtilis.language("class.topicSerialNO",clientLocale)%> </td><!--题目序号-->
					 			<td align="center"  width="15%"><%=LocalUtilis.language("class.topicType",clientLocale)%> </td><!--题目类别-->
								<td align="center"  width="*"><%=LocalUtilis.language("class.topicContent",clientLocale)%> </td><!--题目内容-->
								<td align="center"  width="15%"><%=LocalUtilis.language("class.optionValue",clientLocale)%> </td><!--可选值-->
					 	</tr>	
					 	
<%
//声明字段
Iterator iterator = list2.iterator();
Integer s_topic_id = new Integer(0);
Integer s_topic_serial_no = new Integer(0);
String s_topic_type_name = "";
String s_topic_type = "";
String s_topic_content ="";

while(iterator.hasNext()){
	iCount++;
	map2 = (Map)iterator.next();
	
	s_topic_id = Utility.parseInt(Utility.trimNull(map2.get("TOPIC_ID")),new Integer(0));
	s_topic_serial_no = Utility.parseInt(Utility.trimNull(map2.get("TOPIC_SERIAL_NO")),new Integer(0));
	s_topic_type = Utility.trimNull(map2.get("TOPIC_TYPE"));
	s_topic_type_name = Argument.getDictParamName(0,s_topic_type);
	s_topic_content  = Utility.trimNull(map2.get("TOPIC_CONTENT"));
%>

		<tr class="tr<%=iCount%2%>">
			<td align="center"><%= s_topic_serial_no%></td> 
			<td align="center"><%= s_topic_type_name%></td> 
			<td align="left">&nbsp;&nbsp;<%= s_topic_content%></td> 	
			
	        <td align="center">
	        <%if(s_topic_type.equals("305102")){%>
					<div style="margin-right:12px" align="right">
							<select onkeydown="javascript:nextKeyPress(this)" style="width:170px">
								<%=Argument.getTopicValue(s_topic_id)%>
							</select>
					</div>
	         <%}
	         else if(s_topic_type.equals("305103")){%>
     				<div style="margin-right:12px" align="right">
						<select onkeydown="javascript:nextKeyPress(this)" style="width:170px">
							<%=Argument.getDictParamOptions(3052,"305202")%>
						</select>
					</div>
	         <%}%>
	         </td>   
		</tr>
		<!--下拉显示选项编号-->
		<tr id="activeTr<%=s_topic_id%>" style="display: none">
			<td align="center" bgcolor="#FFFFFF" colspan="10" >
				<div id="activeDiv<%=s_topic_id%>" style="overflow-y:auto;" align="center">
						<table id="activeTablePro<%=s_topic_id%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
							<tr  style="background:F7F7F7;">
					 			<td align="center"  width="15%"><%=LocalUtilis.language("class.topicValue2NO",clientLocale)%> </td><!--选项编号-->
					 			<td align="center"  width="*"><%=LocalUtilis.language("class.topicValue2",clientLocale)%> </td><!--选项值-->
					 		</tr>
<%
 vo = new QuestionnaireVO();

 vo.setTopic_id(s_topic_id);
 list3 = local.queryTTopicScroe(vo);
 
if(list3.size()>0){
	Iterator iterator2 = list3.iterator();
	Integer s_serial_no = new Integer(0);
	String s_topic_value_no = "";
	String s_topic_value = "";
	Integer s_score = new Integer(0);

	while(iterator2.hasNext()){
			iCount2++;
			map3 = (Map)iterator2.next();
			
			s_serial_no = Utility.parseInt(Utility.trimNull(map3.get("SERIAL_NO")),new Integer(0));
			s_topic_value_no = Utility.trimNull(map3.get("TOPIC_VALUE_NO"));
			s_topic_value = Utility.trimNull(map3.get("TOPIC_VALUE"));
%>
					<tr style="background:F7F7F7;">
							<td align="center"><%= s_topic_value_no%></td> 
							<td align="center"><%= s_topic_value%></td> 
					</tr>
<%
	}
}
%>		 	
					
					</table>
				</div>
			</td>
		</tr>
		
<%}%>
		</table>
</div>

<div align="right" style="margin-top:10px;">		
	<button type="button"  class="xpbutton3" accessKey=s id="btnPre" name="btnPre" title="<%=LocalUtilis.language("message.up",clientLocale)%> " onclick="javascript: previorsAction();"><%=LocalUtilis.language("message.up",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;<!--上一步-->		
	<button type="button"  class="xpbutton3" accessKey=n id="btnSure" name="btnSure" title="<%=LocalUtilis.language("message.confirm",clientLocale)%> " onclick="javascript: window.close();"><%=LocalUtilis.language("message.confirm",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;<!--确定-->	
</div>	

<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
