<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ȡ��ҳ���ѯ����
String q_channel_code =Utility.trimNull(request.getParameter("q_channel_code"));
String q_channel_name =Utility.trimNull(request.getParameter("q_channel_name"));
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
Integer q_service_man = Utility.parseInt(Utility.trimNull(request.getParameter("q_service_man")),new Integer(0));
String channel_type_name = Argument.getChannelTypeName(channel_type);

//url����
String tempUrl = "";
tempUrl = tempUrl+"&q_channel_code="+q_channel_code;
tempUrl = tempUrl+"&q_channel_name="+q_channel_name;
tempUrl = tempUrl+"&channel_type="+channel_type;
tempUrl = tempUrl+"&q_service_man="+q_service_man;
sUrl = sUrl + tempUrl;
//��������
input_bookCode = new Integer(1);
//ҳ���ø�������
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//��ö���
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();

vo.setBookCode(input_bookCode);
vo.setChannelCode(q_channel_code);
vo.setChannelName(q_channel_name);
vo.setChannel_type(channel_type);
vo.setService_man(q_service_man);
IPageList pageList = local.queryPage(vo,t_sPage,t_sPagesize);
//��ҳ��������
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
<title></title><!--������������-->
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
/*��������*/	
window.onload = function(){
	initQueryCondition();
}
function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}

/*��ѯ����*/
function QueryAction(){		
	var _pagesize = document.getElementsByName("pagesize")[0];		
	//url ���
	var url = "channel_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&q_channel_code=" + document.getElementById("q_channel_code").getAttribute("value");
	var url = url + "&q_channel_name=" + document.getElementById("q_channel_name").getAttribute("value");
	var url = url + "&channel_type=" + document.getElementById("channel_type").getAttribute("value");
	var url = url + "&q_service_man=" + document.getElementById("q_service_man").getAttribute("value");

	window.location = url;
}
//�޸���������
function modiAction(channel_id){
	var url = "channel_action.jsp?channel_id="+channel_id;
	var ret = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:340px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
//������������
function appendAction(channel_id){
	var url = "channel_action.jsp";
	var ret = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:340px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
//ɾ������
function delAction(){
	if(checkedCount(document.getElementsByName("check_channel_id")) == 0){
		sl_alert("<fmt:message key='message.selectRecordsToDelete'/>��");//��ѡ��Ҫɾ���ļ�¼
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
//����ά���ƻ�
function servicePlansAction(){
	location = 'service_plans.jsp';
}

/**��ϵ��**/
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
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){//ѡ���������ֵ֮ǰ���¼�   
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>treeData.jsp?value='+node.id;
       	 		},
       	 		click:function(node)
   	 			{
       	 		document.getElementById("channel_type").value = node.id;
       	 		}
       	 	}
    	},
    	
		//all:���н�㶼��ѡ��
		//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
		//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
		//leaf��ֻ��Ҷ�ӽ���ѡ
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
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>��</td><!--��ѯ����-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	</br>
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td width="100px" align="right"><%=LocalUtilis.language("class.partnNo",clientLocale)%>: </td><!-- ������� -->
			<td  align="left">
				<input type="text" name="q_channel_code" id="q_channel_code" value="<%= q_channel_code%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
			<td width="100px" align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>: </td><!-- �������� -->
			<td  align="left">
				<input type="text" name="q_channel_name" id="q_channel_name" value="<%= q_channel_name%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
		</tr>
		<tr>
			<td width="100px" align="right" vAlign="top">
				<%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("class.manager",clientLocale)%>:</td><!-- �������� -->
			<td>
				<select size="1" id="q_service_man" name="q_service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(q_service_man)%>
				</select>
			</td>
			<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:</td>
			<td><div id="comboBoxTree"></div></td><!-- ������� -->
		</tr>
		<tr>
			<td align="center" colspan="6">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
				<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)</button><!--ȷ��-->
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
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--��ѯ-->
		&nbsp;&nbsp;&nbsp;<%}%>
		<%//if (input_operator.hasFunc(menu_id, 102)) {%>
		<button type="button"  class="xpbutton3" accessKey=n id="appendButton" name="appendButton" title="<%=LocalUtilis.language("message.append",clientLocale)%>" onclick="javascript:appendAction();">
		   <%=LocalUtilis.language("message.append",clientLocale)%>(<u>N</u>)</button><!--����-->
		&nbsp;&nbsp;&nbsp;<%//}%>
		<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%>" onclick="javascript:delAction();"><%=LocalUtilis.language("message.delete",clientLocale)%>(<u>D</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--ɾ��-->
		<%if (input_operator.hasFunc(menu_id, 104)) {%>
		<button type="button"  class="xpbutton4" accessKey=u id="plansButton" name="plansButton" title="<%=LocalUtilis.language("message.maintenance",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%>" onclick="javascript:servicePlansAction();">
		   <%=LocalUtilis.language("message.maintenance",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%></button><!--ά���ƻ�-->
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
			<td align="center" width="*"><%=LocalUtilis.language("class.partnNo",clientLocale)%></td><!-- ������� -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnName",clientLocale)%></td><!-- �������� -->
			<td align="center" width="*"><%=LocalUtilis.language("class.contact",clientLocale)%></td><!-- ��ϵ�� -->
			<td align="center" width="*"><%=LocalUtilis.language("login.telephone",clientLocale)%></td><!-- ��ϵ�绰 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.postAddress",clientLocale)%></td><!-- ��ַ -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnType",clientLocale)%></td><!-- ������� -->
			<td align="center" width="*"><%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("class.manager",clientLocale)%></td><!-- �������� -->
			<td width="60px" align="center"><%=LocalUtilis.language("message.edit",clientLocale)%></td><!--�༭--> 
			<td align="center" width="8%"><%=LocalUtilis.language("class.contact",clientLocale)%> </td><!--��ϵ��-->
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
					<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--��ϵ��-->
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
			<td class="tdh" colspan="10" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--��--></b></td>
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
