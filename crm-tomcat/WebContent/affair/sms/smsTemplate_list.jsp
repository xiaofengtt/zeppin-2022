<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
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
//获得对象
SmsTemplateLocal smsTemplocal = EJBFactory.getSmsTemplate();
SmsTemplateVO vo = new SmsTemplateVO();
vo.setTitle(q_temp_title);
vo.setTempType(q_temp_type);
IPageList pageList = smsTemplocal.listAll(vo,t_sPage,t_sPagesize);
//分页辅助参数
List list = pageList.getRsList();
Map map = null;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.smsTemplateMintenance",clientLocale)%> </title><!--短信模板维护-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />	

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*页面初始化*/	
window.onload = function(){
	initQueryCondition();
}
/*查询方法*/
function QueryAction(){
	var url = "smsTemplate_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + "&q_temp_title=" + document.getElementById("q_temp_title").value;
	var url = url + "&q_temp_type=" + document.getElementById("q_temp_type").value;

	window.location.href = url;
}
//刷新
function refreshPage(){
	 QueryAction();
}
function AddAction(){				
	var url = "smsTemplate_action.jsp";	
	showModalDialog(url,'', 'dialogWidth:600px;dialogHeight:450px;status:0;help:0');
	window.location.reload();
}
function ModiAction(tempID){				
	var url = "smsTemplate_action.jsp?tempID="+tempID;	
	showModalDialog(url,'', 'dialogWidth:600px;dialogHeight:450px;status:0;help:0');
	window.location.reload();
}
/*删除功能*/
function DelAction(){
	if(checkedCount(document.getElementsByName("check_smsTempID")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "smsTemplate_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}
</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:300px;height:130px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 

	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.templateTitle",clientLocale)%> ： </td><!--模板标题-->
			<td  align="left" colspan="3">
				<input TYPE="text" name="q_temp_title" id="q_temp_title" value="<%=q_temp_title%>"/>	
			</td>	
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.tempTypeName",clientLocale)%> ： </td><!--模板类别-->
			<td  align="left" colspan="3">
				<select name="q_temp_type" id="q_temp_type" onkeydown="javascript:nextKeyPress(this)" style="width:180px">	
					<%= Argument.getDictParamOptions(4038,q_temp_type) %>
				</select>
			</td>		
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>	
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>		
	<div align="right" class="btn-wrapper">
	
        <!-- 查询 -->
		<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<!-- 新建 -->
        <button type="button" class="xpbutton3" accessKey=a id="btnAdd" name="btnAdd" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AddAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>A</u>)</button>
		&nbsp;&nbsp;&nbsp;<%}%>
		<%if (input_operator.hasFunc(menu_id, 102)) {%>
		<!-- 删除 -->
        <button type="button" class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<%}%>
	</div>
<br/>
	<div>
		<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
			<tr class="trh">
				<td width="30px" align="center">
					 <input type="checkbox" name="checkBox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.check_smsTempID,this);" />
				 </td>
				 <td width="30px" align="center"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
				 <td width="20%" align="center"><%=LocalUtilis.language("class.templateTitle",clientLocale)%> </td><!--模板标题-->
		         <td width="20%" align="center"><%=LocalUtilis.language("class.tempTypeName",clientLocale)%> </td> <!--模板类别-->
		         <td width="*" align="center"><%=LocalUtilis.language("class.templateContent",clientLocale)%> </td> <!--模板内容-->
				 <td width="30px" align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		     </tr>
			<%
			//声明字段
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
			 %>
			<tr class="tr<%=iCount%2%>">
				<td align="center">
					<input type="checkbox" name="check_smsTempID" value="<%=Utility.parseInt(Utility.trimNull(map.get("TempID")),new Integer(0))%>" class="flatcheckbox"/>&nbsp;&nbsp;
				</td>
				<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("TempID")),new Integer(0))%></td> 
				<td align="center"><%= Utility.trimNull(map.get("Title"))%></td> 
				<td align="center"><%= Utility.trimNull(map.get("TempTypeName"))%></td> 
				<td align="center"><%= Utility.trimNull(map.get("Content"))%></td> 
 				<td align="center">              	
		          	<a href="javascript:ModiAction(<%=Utility.parseInt(Utility.trimNull(map.get("TempID")),new Integer(0))%>)">
		           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
		           	</a>
	         	</td>
			</tr>
			<%}%>
			<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
			 <tr class="tr0">
			        <td align="center"></td>
					<td align="center"></td>
			        <td align="center"></td>
			        <td align="center"></td>
			        <td align="center"></td>           
					<td align="center"></td>                        
			  </tr>           
			<%}%>
			<tr class="trbottom">
                <!--合计--><!--项-->
				<td align="left" class="tdh" colspan="6">
					&nbsp;
					<b>	<%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b>
				</td>
			</tr>
		</TABLE>
	</div>
	<br>
	<div class="page-link">
		<%=pageList.getPageLink(sUrl,clientLocale)%>
	</div>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<% smsTemplocal.remove();%>