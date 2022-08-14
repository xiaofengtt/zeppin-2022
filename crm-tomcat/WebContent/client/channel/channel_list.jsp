<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
String q_channel_code =Utility.trimNull(request.getParameter("q_channel_code"));
String q_channel_name =Utility.trimNull(request.getParameter("q_channel_name"));
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
Integer q_service_man = Utility.parseInt(Utility.trimNull(request.getParameter("q_service_man")),new Integer(0));
String channel_type_name = Argument.getChannelTypeName(channel_type);

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_channel_code="+q_channel_code;
tempUrl = tempUrl+"&q_channel_name="+q_channel_name;
tempUrl = tempUrl+"&channel_type="+channel_type;
tempUrl = tempUrl+"&q_service_man="+q_service_man;
sUrl = sUrl + tempUrl;
//辅助变量
input_bookCode = new Integer(1);
//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//获得对象
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();

vo.setBookCode(input_bookCode);
vo.setChannelCode(q_channel_code);
vo.setChannelName(q_channel_name);
vo.setChannel_type(channel_type);
vo.setService_man(q_service_man);
IPageList pageList = local.queryPage(vo,t_sPage,t_sPagesize);
//分页辅助参数
List list = pageList.getRsList();
int iCount = 0;
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
<title></title><!--代销渠道管理-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}
function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}

/*查询功能*/
function QueryAction(){		
	var _pagesize = document.getElementsByName("pagesize")[0];		
	//url 组合
	var url = "channel_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&q_channel_code=" + document.getElementById("q_channel_code").getAttribute("value");
	var url = url + "&q_channel_name=" + document.getElementById("q_channel_name").getAttribute("value");
	var url = url + "&channel_type=" + document.getElementById("channel_type").getAttribute("value");
	var url = url + "&q_service_man=" + document.getElementById("q_service_man").getAttribute("value");

	window.location = url;
}
//修改渠道设置
function modiAction(channel_id){
	var url = "channel_action.jsp?channel_id="+channel_id;
	var ret = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:340px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
//新增渠道设置
function appendAction(channel_id){
	var url = "channel_action.jsp";
	var ret = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:340px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
//删除渠道
function delAction(){
	if(checkedCount(document.getElementsByName("check_channel_id")) == 0){
		sl_alert("<fmt:message key='message.selectRecordsToDelete'/>！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "channel_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;
}
//渠道维护计划
function servicePlansAction(){
	location = 'service_plans.jsp';
}

/**联系人**/
function setContact(cust_id, cust_type){
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contacts_list.jsp?cust_id="+cust_id+"&cust_type="+cust_type+"&contactType=3";
	window.location.href = url;
}

var comboBoxTree;
var invest_type_name = '<%=channel_type_name%>';
var intrustType_Flag;
var str;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

var comboBoxParentTree;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

Ext.onReady(function(){
	comboBoxTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>treeData.jsp'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){//选择树结点设值之前的事件   
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>treeData.jsp?value='+node.id;
       	 		},
       	 		click:function(node)
   	 			{
       	 		document.getElementById("channel_type").value = node.id;
       	 		}
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'leaf'
	});
}); 
</script>
</head>
<body class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_type_name" name="channel_type_name" value="<%=channel_type_name%>"/>
<div id="queryCondition" class="qcMain" style="display:none;width:680px;height:140px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	</br>
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td width="100px" align="right"><%=LocalUtilis.language("class.partnNo",clientLocale)%>: </td><!-- 渠道编号 -->
			<td  align="left">
				<input type="text" name="q_channel_code" id="q_channel_code" value="<%= q_channel_code%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
			<td width="100px" align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>: </td><!-- 渠道名称 -->
			<td  align="left">
				<input type="text" name="q_channel_name" id="q_channel_name" value="<%= q_channel_name%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
		</tr>
		<tr>
			<td width="100px" align="right" vAlign="top">
				<%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("class.manager",clientLocale)%>:</td><!-- 渠道经理 -->
			<td>
				<select size="1" id="q_service_man" name="q_service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(q_service_man)%>
				</select>
			</td>
			<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:</td>
			<td><div id="comboBoxTree"></div></td><!-- 渠道类别 -->
		</tr>
		<tr>
			<td align="center" colspan="6">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
				<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>
	</table>
</div>

<div>
	<div align="left" class="page-title ">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right" class="btn-wrapper">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
		<%//if (input_operator.hasFunc(menu_id, 102)) {%>
		<button type="button"  class="xpbutton3" accessKey=n id="appendButton" name="appendButton" title="<%=LocalUtilis.language("message.append",clientLocale)%>" onclick="javascript:appendAction();">
		   <%=LocalUtilis.language("message.append",clientLocale)%>(<u>N</u>)</button><!--新增-->
		&nbsp;&nbsp;&nbsp;<%//}%>
		<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%>" onclick="javascript:delAction();"><%=LocalUtilis.language("message.delete",clientLocale)%>(<u>D</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--删除-->
		<%if (input_operator.hasFunc(menu_id, 104)) {%>
		<button type="button"  class="xpbutton4" accessKey=u id="plansButton" name="plansButton" title="<%=LocalUtilis.language("message.maintenance",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%>" onclick="javascript:servicePlansAction();">
		   <%=LocalUtilis.language("message.maintenance",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%></button><!--维护计划-->
		<%}%>
	</div>
</div>
<br/>
<div valign="middle" align="center">
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="15px">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox"	
					   onclick="javascript:selectAllBox(document.theform.check_channel_id,this);" />
			</td>
			<td align="center" width="*"><%=LocalUtilis.language("class.partnNo",clientLocale)%></td><!-- 渠道编号 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnName",clientLocale)%></td><!-- 渠道名称 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.contact",clientLocale)%></td><!-- 联系人 -->
			<td align="center" width="*"><%=LocalUtilis.language("login.telephone",clientLocale)%></td><!-- 联系电话 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.postAddress",clientLocale)%></td><!-- 地址 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnType",clientLocale)%></td><!-- 渠道类别 -->
			<td align="center" width="*"><%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("class.manager",clientLocale)%></td><!-- 渠道经理 -->
			<td width="60px" align="center"><%=LocalUtilis.language("message.edit",clientLocale)%></td><!--编辑--> 
			<td align="center" width="8%"><%=LocalUtilis.language("class.contact",clientLocale)%> </td><!--联系人-->
		</tr>
		<%
			Iterator iterator = list.iterator();
			Integer channel_id = new Integer(0);
			String channel_name = "";
			Integer service_man = new Integer(0);
			
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
				channel_id = Utility.parseInt(Utility.trimNull(map.get("CHANNEL_ID")),new Integer(0));
				channel_type_name =Utility.trimNull(map.get("CHANNEL_TYPE_NAME"));
				channel_name = Utility.trimNull(map.get("CHANNEL_NAME"));
				service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="15px"><input type="checkbox" name="check_channel_id" value="<%=channel_id%>" class="flatcheckbox"/> </td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("CHANNEL_CODE"))%></td>
			<td align="center" width="*"><%=channel_name%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("LINK_MAN"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("PHONE"))%></td>
			<td align="left" width="150px"><input type="text" class="ednone" style="width:150px" value="<%=Utility.trimNull(map.get("ADDRESS"))%>" readonly></td>
			<td align="center" width="*"><%=Utility.trimNull(channel_type_name)%></td>
			<td align="center" width="*"><%=Argument.getManager_Name(service_man)%></td>
			<td align="center" width="60px">
				<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction(<%=channel_id%>);">
			</td>
			<td align="center">
				<a href="#" onclick="javascript: setContact(<%=channel_id%>,'<%=channel_name%>'); ">
					<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--联系人-->
				</a>
			</td>
		</tr>
		<%}%>
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>       	
	         <tr class="tr<%=(i % 2)%>">
	            <td align="center" width="15px">&nbsp;</td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>      
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>        
	            <td align="center"></td>  
	            <td align="center"></td> 
	            <td align="center" width="60px"></td>              
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="10" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</TABLE>
</div>
<br>
<div class="page-link">
	&nbsp;&nbsp;<%=pageList.getPageLink(sUrl)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<%
local.remove();
%>
