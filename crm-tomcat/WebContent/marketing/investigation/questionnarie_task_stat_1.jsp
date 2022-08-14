<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递参数
Integer ques_taskId = Utility.parseInt(request.getParameter("ques_taskId"), new Integer(0));
Integer topic_id =Utility.parseInt(request.getParameter("topic_id"), new Integer(0));
String topic_content =Utility.trimNull(request.getParameter("topic_content"));
String q_topicType = Utility.trimNull(request.getParameter("q_topicType"));

//获得对象
QuestionnaireLocal quesLocal = EJBFactory.getQuestionnaire();
QuestionnaireVO vo_ques = new QuestionnaireVO();

vo_ques.setQues_taskId(ques_taskId);
vo_ques.setTopic_id(topic_id);

List rsList = quesLocal.statQuestRecord(vo_ques);
Map rsMap = null;
int iCount = 0;
String[] ItemName = null;
String[] ItemValue = null;
String[] PercentageValue = null;

try{
	if(rsList.size()>0){
		int size = rsList.size();
		ItemName = new String[size];
		ItemValue = new String[size];
		PercentageValue = new String[size];
	}
}
catch(Exception e){
// 	throw new Exception(LocalUtilis.language("message.dataError",clientLocale));
	out.println("<script type=\"text/javascript\">alert('"+LocalUtilis.language("message.dataError",clientLocale)+"')</script>");
	out.println("<script type=\"text/javascript\">window.close();</script>");
	return;

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
<title><%=LocalUtilis.language("menu.questionnarieTaskStat_1",clientLocale)%> </title>
<!--问卷记录信息统计-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
/*初始化方法*/
window.onload=function(){
	show(1); 
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
</script>

</head>
<body>
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.surveyManage",clientLocale)%>>><%=LocalUtilis.language("menu.questionnarieTaskStat",clientLocale)%>  </b></font>
	<!--调查管理>>调查结果统计-->
</div>		

<div align="left"  style="margin-left:10px">
	<TABLE cellSpacing=0 cellPadding=0 width="97%" border="0" class="edline">
				<TBODY>
					<TR>							
						<TD vAlign=top><%=LocalUtilis.language("class.topicContent3",clientLocale)%> ：<%=topic_content%></TD><!--题目-->					
						<TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.icon",clientLocale)%> </TD>
						<!--图表-->
						<TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.list",clientLocale)%> </TD>
						<!--列表-->
					</TR>
			</TBODY>
	</TABLE>
</div>

<div  id="r2" align="left"  style="display:none; height:340px; margin-left:10px; margin-top:10px;">
	<table  cellSpacing="1" cellPadding="2" width="510px"  bgcolor="#CCCCCC">
			<tr style="background:6699FF;">
				<td  align="center" width="250px"><%=LocalUtilis.language("class.topicValue",clientLocale)%> </td><!--参考选项-->
				<td  align="center" width="250px"><%=LocalUtilis.language("message.statisticalResults",clientLocale)%> </td><!--统计结果-->
			</tr>
<%
Iterator iterator = rsList.iterator();
String topic_value = "";
Integer stat_num = new Integer(0); 
try{
	while(iterator.hasNext()){
		rsMap = (Map)iterator.next();
		
		topic_value = Utility.trimNull(rsMap.get("TOPIC_VALE"));
		stat_num = Utility.parseInt(Utility.trimNull(rsMap.get("STAT_NUM")),new Integer(0));
		
		if(q_topicType.equals("305103")){
			topic_value = Argument.getDictParamName(3052,topic_value);
		}
		
		ItemName[iCount] = topic_value;
		ItemValue[iCount] = stat_num.toString();
		PercentageValue[iCount] = stat_num.toString();
		iCount++;
%>
			<tr style="background:F7F7F7;">
				<td  align="center" width="250px"><%=topic_value%></td>
				<td  align="center" width="250px"><%=stat_num%></td>
			</tr>
<%}
}catch(Exception e){
	throw new Exception(LocalUtilis.language("message.dataError",clientLocale));
}%>
	</table>
</div>

<div  id="r1" align="left"  style="display:none; height:340px; margin-left:10px;margin-top:10px;">
<%
String chartHTMLCode = "";
try{
	FusionCharts Chart = new FusionCharts();
	FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
	String XMLStr = ChartCreator.ganeratePie3D(ItemName,ItemValue,PercentageValue,"",LocalUtilis.language("message.statResultDistribute",clientLocale),LocalUtilis.language("message.size",clientLocale));//统计结果分布 个
	int height = 337; 
	chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr,"ENFO",520,height,false);
}
catch(Exception e){
// 	throw new BusiException(LocalUtilis.language("message.dataError",clientLocale));
	out.println("<script type=\"text/javascript\">alert('"+LocalUtilis.language("message.dataError",clientLocale)+"')</script>");
	out.println("<script type=\"text/javascript\">window.close();</script>");
	return;
}%>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr bgcolor="white">
			<td align="left">
				<%=chartHTMLCode%>
			</td>
		</tr>
	</table>
</div>

<div align="right" style="margin-top:10px; margin-right:5px;">			
	<button type="button"  class="xpbutton3" accessKey=n id="btnSure" name="btnSure" title="<%=LocalUtilis.language("message.confirm",clientLocale)%> " onclick="javascript: window.close();"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>
	<!--确定-->
</div>	
<%quesLocal.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>