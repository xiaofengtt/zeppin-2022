<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递参数
Integer channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
String channel_code = Utility.trimNull(request.getParameter("channel_code"));
String channel_name = Utility.trimNull(request.getParameter("channel_name"));
String link_man = Utility.trimNull(request.getParameter("link_man"));
String phone = Utility.trimNull(request.getParameter("phone"));
String zip = Utility.trimNull(request.getParameter("zip"));
String fax = Utility.trimNull(request.getParameter("fax"));
String address = Utility.trimNull(request.getParameter("address"));
String summary = Utility.trimNull(request.getParameter("summary"));
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_type_name = Utility.trimNull(request.getParameter("channel_type_name"));
Integer parent_channel = Utility.parseInt(Utility.trimNull(request.getParameter("parent_channel")),null);
String channel_type_name1 = Utility.trimNull(request.getParameter("channel_type_name1"));
Integer service_man = Utility.parseInt(Utility.trimNull(request.getParameter("service_man")),null);

//声明辅助变量
boolean bSuccess = false;
input_bookCode = new Integer(1);
int iCount = 0;
List list = null;
Map map = new HashMap();

//获得对象
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();
//保存对象
if(request.getMethod().equals("POST")){
	vo.setBookCode(input_bookCode);
	vo.setChannelCode(channel_code);
	vo.setChannelName(channel_name);
	vo.setAddress(address);
	vo.setLink_man(link_man);
	vo.setZip(zip);
	vo.setPhone(phone);
	vo.setFax(fax);
	vo.setSellFlag(new Integer(2));
	vo.setSummary(summary);
	vo.setInputMan(input_operatorCode);
	vo.setChannel_type(channel_type);
	vo.setParent_channel(parent_channel);
	vo.setService_man(service_man);
	
	if(channel_id.intValue()>0){
		vo.setChannelID(channel_id);		
		local.modi(vo);
		bSuccess = true;
	}
	else{
		local.append(vo);
		bSuccess = true;
	}
}

if(channel_id.intValue()>0){
	vo = new ChannelVO();
	vo.setChannelID(channel_id);
	vo.setBookCode(input_bookCode);

	list = local.query(vo);

	if(list!=null&&list.size()>0){
		map = (HashMap)list.get(0);
		
		if(map!=null){
			channel_id = Utility.parseInt(Utility.trimNull(map.get("CHANNEL_ID")),new Integer(0));
			channel_code = Utility.trimNull(map.get("CHANNEL_CODE"));
			channel_name = Utility.trimNull(map.get("CHANNEL_NAME"));
			link_man = Utility.trimNull(map.get("LINK_MAN"));
			phone = Utility.trimNull(map.get("PHONE"));
			zip = Utility.trimNull(map.get("ZIP"));
			fax = Utility.trimNull(map.get("FAX"));
			address = Utility.trimNull(map.get("ADDRESS"));
			summary = Utility.trimNull(map.get("SUMMARY"));
			channel_type = Utility.trimNull(map.get("CHANNEL_TYPE"));
			channel_type_name = Utility.trimNull(map.get("CHANNEL_TYPE_NAME"));
			parent_channel = Utility.parseInt(Utility.trimNull(map.get("PARENT_CHANNEL")),null);
			service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
		}
	}
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
<title></title><!--代理销售渠道设置-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>

<script language="javascript">
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}
/*保存*/
function SaveAction(){
	if(!sl_check(document.getElementsByName('channel_code')[0],"<%=LocalUtilis.language("class.partnNo",clientLocale)%>",6,1)){return false;} //渠道编号
	var channel_code = document.getElementsByName('channel_code')[0].value;
	var h_channel_code = document.getElementsByName('h_channel_code')[0].value;
	
	if(channel_code==h_channel_code){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	else{
		utilityService.checkChannelCode(channel_code,{callback:function(data){
			if(data==0){				
				if(document.getElementsByName('theform')[0].onsubmit()){
					document.getElementsByName('theform')[0].submit();
				}
			}
			else{			
				sl_alert("<%=LocalUtilis.language("class.partnNo",clientLocale)+LocalUtilis.language("message.NotAvailable",clientLocale)%>！"); //渠道编号不可用
			}
		}});
	}
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
           	 		channelTree(node.id);
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

Ext.onReady(function(){
	
	if(<%=parent_channel%> == null){
		invest_type_name = '';
	}
	else{
		invest_type_name = '<%=Argument.getChannel(parent_channel)%>';
	}
	
	comboBoxParentTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxParentTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>parentTree.jsp?channel_type=<%=channel_type%>'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>parentTree.jsp?value='+node.id+'&channel_type=<%=channel_type%>';
       	       	 }
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'exceptRoot'
	});
}); 

function channelTree(channel_type){
	document.getElementById('comboBoxParentTree').innerHTML = '';
	comboBoxParentTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxParentTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>parentTree.jsp?channel_type='+channel_type}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>parentTree.jsp?value='+node.id+'&channel_type='+comboBoxTree.getValue();
       	       	 }
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'exceptRoot'
	});
}

/*验证数据*/
function validateForm(form){
	if(!sl_check(form.channel_name,"<%=LocalUtilis.language("class.partnName",clientLocale)%>",80,1)){return false;} //渠道名称
	//if(!sl_check(form.link_man,"联系人",50,1)){return false;} //联系人
	if(!sl_check(form.address, "<%=LocalUtilis.language("class.postAddress2",clientLocale)%>", 60, 0))	return false;//邮寄地址
	if(!sl_check(form.zip, "<%=LocalUtilis.language("class.postcode",clientLocale)%>", 6, 0))	return false;//邮政编码
	if(!sl_check(form.fax, "<%=LocalUtilis.language("class.fax",clientLocale)%> ", 60, 0))	return false;//传真
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.description",clientLocale)%>",200,0)){return false;}//备注
	if(intrustType_Flag == '1'){
			document.theform.channel_type.value = comboBoxTree.getValue();
			document.theform.parent_channel.value = comboBoxParentTree.getValue();
	}
	return sl_check_update();
}
/*取消*/
function CancelAction(){
	window.close();
}


</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" action="channel_action.jsp" method="post" onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=channel_id%>"/>
<input type="hidden" id="h_channel_code" name="h_channel_code" value="<%=channel_code%>"/>
<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_type_name" name="channel_type_name" value="<%=channel_type_name%>"/>
<input type="hidden" id="parent_channel" name="parent_channel" value="<%=parent_channel%>"/>
<input type="hidden" id="comboBoxTree_id" name="comboBoxTree_id" value="0"/>
<div align="left"  class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.salesAgent",clientLocale)+LocalUtilis.language("message.channels",clientLocale)+">>"+LocalUtilis.language("menu.salesAgent",clientLocale)+LocalUtilis.language("message.channels",clientLocale)+LocalUtilis.language("message.set",clientLocale)%></b></font> <!-- 代理销售渠道>>代理销售渠道设置 -->
</div>
<br/>
<div align="left">
	<table border="0" width="100%" cellspacing="2" cellpadding="2" class="table-popup">
	 	<tr>
	 		<td  width="100px" align="right">*<%=LocalUtilis.language("class.partnNo",clientLocale)%>:</td>
	 		<td  width="*"><!-- 渠道编号 -->
	 			<input name="channel_code" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=channel_code%>" />
	 		</td>
	 		<td  width="100px" align="right">*<%=LocalUtilis.language("class.partnName",clientLocale)%>:</td>
			<td><input name="channel_name" onkeydown="javascript:nextKeyPress(this)" size="30" value="<%=channel_name%>" /></td><!-- 渠道名称 -->
	 	</tr>
		<tr >
	 		<td  width="100px" align="right">*<%=LocalUtilis.language("class.partnType",clientLocale)%>:</td>
			<td><div id="comboBoxTree"></div></td><!-- 渠道类别 -->
			<td  width="100px" align="right">*<%=LocalUtilis.language("class.ParentChannel",clientLocale)%>:</td> <!-- 父渠道 -->
			<td><div id="comboBoxParentTree"></div></td><!-- 父渠道 -->
	 	</tr>
	 	<tr>
	 		<td  width="100px" align="right"><%=LocalUtilis.language("class.contact",clientLocale)%>:</td>
	 		<td  width="*"><!-- 联系人 -->
	 			<input name="link_man" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="20"	value="<%=link_man%>" />
	 		</td>
	 		<td  width="100px" align="right"><%=LocalUtilis.language("login.telephone",clientLocale)%>:</td>
	 		<td  width="*"><!-- 联系电话 -->
	 			<input name="phone" onkeydown="javascript:nextKeyPress(this)" maxlength="200" size="20"	value="<%=phone%>" />
	 		</td>
	 	</tr>
	 	<tr>
	 		<td width="100px" align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%>:</td><!--邮政编码-->
	 		<td width="*">
	 			<input name="zip" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=zip%>">
	 		</td>
	 		<td width="100px" align="right"><%=LocalUtilis.language("class.fax",clientLocale)%>:</td><!--传真-->
			<td width="*">
				<input name="fax" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=fax%>">
			</td>
	 	</tr>
	 	<tr>
	 		<td width="100px" align="right" vAlign="top"><%=LocalUtilis.language("class.postAddress2",clientLocale)%>:</td><!--邮寄地址-->
			<td width="*">
				<input name="address" onkeydown="javascript:nextKeyPress(this)" size="40" maxlength="60" value="<%=address%>">
			</td>
			<td width="100px" align="right" vAlign="top">
			<%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("class.manager",clientLocale)%>:</td><!-- 渠道经理 -->
			<td width="*" >
				<select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(service_man)%>
				</select>
			</td>
	 	</tr>
 		<tr>
			<td width="100px" align="right" vAlign="top"><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!--备注-->
			<td  colspan=3><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="100"><%=summary%></textarea></td>
		</tr>
	</table>
</div>
<br/>
<div align="right" style="margin-right:5px">
	<!-- 保存 -->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 关闭 -->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<%
local.remove();
%>
