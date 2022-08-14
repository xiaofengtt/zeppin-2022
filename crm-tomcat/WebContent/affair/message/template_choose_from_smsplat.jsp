<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//取得页面查询参数
String q_temp_title = Utility.trimNull(request.getParameter("q_temp_title"));
String q_temp_type = Utility.trimNull(request.getParameter("q_temp_type"));
//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_temp_title="+q_temp_title;
tempUrl = tempUrl+"&q_temp_type="+q_temp_type;
sUrl = sUrl + tempUrl;
//页面用辅助变量
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String temlpateUrl=Argument.getDictParamName(800213,"800213");
String json=SmsClient.doGet(temlpateUrl,"UTF-8");

//获得对象
SmsTemplateVO vo = new SmsTemplateVO();
vo.setTitle(q_temp_title);
vo.setTempType(q_temp_type);
//IPageList pageList = smsTemplocal.listAll(vo,t_sPage,t_sPagesize);
//分页辅助参数
//List list = pageList.getRsList();
List list=JsonUtil.parseJSON2ArrayList(json);
Map map = null;
%>

<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.smdTemplateSelection",clientLocale)%> </title><!--短信模板选择-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*查询方法*/
function QueryAction(){
	disableAllBtn(true);
	var form = document.getElementsByName("theform")[0];
	form.action = "template_choose.jsp";
	form.submit();	
}
//刷新
function refreshPage(){
	 QueryAction();
}
//function chooseAction(content){
	////document.getElementById("content").value = content;
//}
function chooseAction(tempID,title,content){
	document.getElementById("tempID").value = tempID;
	document.getElementById("title").value = title;
	document.getElementById("content").value = content;
}
function saveInfo(){
	var tempID = document.getElementById("tempID").value;
	var title = document.getElementById("title").value;
	var content = document.getElementById("content").value;
	if (tempID=="0001" || content=="<content>"){ //当自由编辑模板时，宏名去掉
		content="";
	}
	window.returnValue = tempID+"_"+title+"_"+content;
	window.close();
}
</script>
</head>
<body class="body">
<form id="theform" name="theform" method="post">
<input type="hidden" name ="tempID">
<input type="hidden" name ="title">
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b><%=LocalUtilis.language("menu.smdTemplateSelection",clientLocale)%> </b></font><!--短信模板选择-->
	</div>	
	<div align="right">
		<select name="q_temp_type" id="q_temp_type" onchange="javascript:QueryAction();" onkeydown="javascript:nextKeyPress(this)" style="width:180px">	
			<%= Argument.getDictParamOptions(4038,q_temp_type) %>
		</select>
	</div>
	<hr noshade color="#808080" size="1" width="98%">

	<div align="center">
		<table border="0" width="575px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trtagsort">
				 <td width="30px" align="center"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
				 <td width="20%" align="center"><%=LocalUtilis.language("class.templateTitle",clientLocale)%> </td><!--模板标题-->
		         <td width="20%" align="center"><%=LocalUtilis.language("class.tempTypeName",clientLocale)%> </td><!--模板类别-->	         
		         <td width="*" align="center"><%=LocalUtilis.language("class.templateContent",clientLocale)%> </td><!--模板内容-->
		     </tr>
		</table>
		<span id="tableList" style="overflow-y:auto;height:200px;">
			<table border="0" width="575px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
				<%
				//声明字段
				Iterator iterator = list.iterator();
				String tempID = "";
				String title = "";
				String content ="";
				while(iterator.hasNext()){
					iCount++;
					map = (Map)iterator.next();
					tempID = Utility.trimNull(map.get("templateId"));
					title = Utility.trimNull(map.get("templateName"));
					content = Utility.trimNull(map.get("templateContent"));
				 %>
                <!--双击选中短信模板-->
                <tr class="tr<%=iCount%2%>" style="cursor:hand;" title="<%=LocalUtilis.language("message.doubleClickSelectTemp",clientLocale)%> " onDBlclick="javascript:chooseAction('<%=tempID%>','<%=title%>','<%=content%>');">
					<td width="30px" align="center"><%=tempID%></td> 
					<td width="20%" align="center"><%= title%></td> 
					<td width="20%" align="center"><%= title%></td> 
					<td width="*" align="center"><%= content%></td> 
				</tr>
				<%}%>
			</table>
		</span>	
		<br>
		<table border="0">
			<tr valign="top">
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.templateContent",clientLocale)%>:</font></td>
				<td  width="500px" colspan="3">
					<textarea rows="5" name="content"  style="width:95%"></textarea>		
				</td>
			</tr>
			<tr>
				<td  colspan="3">&nbsp;</td>
				<td align="right">
					<button class="xpbutton3" id="btnSave" name="btnSave" onclick="javascript:saveInfo();">
					<%=LocalUtilis.language("message.save",clientLocale)%></button>
				</td>
			</tr>
		</table>

	</div>
</form>
</body>
</html>