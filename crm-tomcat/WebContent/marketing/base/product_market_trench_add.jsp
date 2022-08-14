<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer sub_flag = Utility.parseInt(Utility.trimNull(request.getParameter("sub_flag")),new Integer(0));
String productName = Utility.trimNull(request.getParameter("productName"));

String channel_type = Utility.trimNull(request.getParameter("channel_type"));
String channel_memo = Utility.trimNull(request.getParameter("channel_memo"));
Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
Integer sub_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("sub_product_id")),new Integer(0));
String r_channel_name = Utility.trimNull(request.getParameter("r_channel_name"));
BigDecimal fareRate = Utility.parseDecimal(Utility.trimNull(request.getParameter("fareRate")), new BigDecimal(0),4,"0.01");
String summary = Utility.trimNull(request.getParameter("summary"));
boolean bSuccess = false;
List subList = null;
Map subMap = null;
ProductVO vo = new ProductVO();
ProductLocal local = EJBFactory.getProduct();

if(request.getMethod().equals("POST")){
	vo.setProduct_id(productId);
	vo.setChannel_type(channel_type);
	vo.setR_channel_id(r_channel_id);
	vo.setR_chanel_rate(fareRate);
	vo.setSummary(summary);
	vo.setInput_man(input_operatorCode);
	if(sub_flag.intValue() == 1)
		vo.setSub_product_id(sub_product_id);
	
	local.addMarketTrench(vo);;
	bSuccess = true;
}else{
	if(sub_flag.intValue() == 1){
		vo.setProduct_id(productId);
		subList = local.listSubProduct(vo);
	}
}
%>

<html>
<head>
<title>��Ʒ������������</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language=javascript>
<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>

var comboBoxTree;
var invest_type_name = '';
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
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/treeData.jsp'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){//ѡ���������ֵ֮ǰ���¼�   
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/treeData.jsp?value='+node.id;
       	 		},   
       	 		click:function(node)
       	 		{
           	 		channelTree(node.id);
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

Ext.onReady(function(){
	comboBoxParentTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxParentTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/parentTree.jsp?channel_type='+comboBoxTree.getValue()}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type='+comboBoxTree.getValue();
       	       	 }
       	 	}
    	},
    	
		//all:���н�㶼��ѡ��
		//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
		//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
		//leaf��ֻ��Ҷ�ӽ���ѡ
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
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/parentTree.jsp?channel_type='+channel_type}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//������Ϣ
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type='+comboBoxTree.getValue();
       	       	 }
       	 	}
    	},
    	
		//all:���н�㶼��ѡ��
		//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
		//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
		//leaf��ֻ��Ҷ�ӽ���ѡ
		selectNodeModel:'exceptRoot'
	});
} 

function validateForm(form)
{
	document.theform.channel_type.value = comboBoxTree.getValue();
	document.theform.channel_id.value = comboBoxParentTree.getValue();
	var sub_flag = form.sub_flag.value;
	
	if(document.theform.channel_type.value == 0){
		sl_alert("��ѡ���������ͣ�");
		return false;
	}
	//if(document.theform.channel_id.value == 0){
		//sl_alert("��ѡ���������ƣ�");
		//return false;
	//}
	if(document.theform.channel_type.value.substr(0,6)!='550001'){
		if(!sl_checkDecimal(form.fareRate, "�������۷���", 7, 4, 1))	return false;
	
		if(form.fareRate.value > 100){
			sl_alert("�������۷��ʲ��ܴ���100��");
			form.fareRate.focus();
			return false;
		}
	
		if(form.fareRate.value <= 0){
			sl_alert("�������۷��ʲ���С�ڵ����㣡");
			form.fareRate.focus();
			return false;
		}
	}
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ", 2000, 0))		return false;//��ע

	return sl_check_update();
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>
<input type="hidden" id="productId" name="productId" value="<%=productId%>"/>
<input type="hidden" name="sub_product_id" id="sub_product_id" value="<%=sub_product_id%>">
<input type="hidden" name="sub_flag" value="<%=sub_flag%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>��Ʒ������������</b></font></td>
	</tr>
</table>
<br/>
<div style="height: 20px;"></div>
<table border="0" width="100%" cellspacing="5" cellpadding="8" class="product-list">
	<tr>
		<td  width="100px" align="right">��Ʒ����:</td>
		<td><input readonly="readonly" class="edline" value="<%=productName %>" size="40"></td>
	</tr>
	<%if(sub_flag.intValue() ==  1){ %>
	<tr>
		<td  align="right">�Ӳ�Ʒ����:</td>
		<td align="left" >			
		<%
			if(subList != null && subList.size() != 0){
			for(int u=0; u<subList.size(); u++){
				subMap = (Map)subList.get(u);
				if(sub_product_id.intValue()==Utility.parseInt(Utility.trimNull(subMap.get("SUB_PRODUCT_ID")),new Integer(0)).intValue()){	
					
		%>			
			<input readonly="readonly" class="edline" value="<%=Utility.trimNull(subMap.get("LIST_NAME")) %>" size="40">
		<%
					break;
					}
				}
			}
		%>
		</td>
	</tr>
	<%} %>
	<tr>
		<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:</td> <!--��������-->
		<td><div id="comboBoxTree"></div></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>:</td><!--��������-->
		<td><div id="comboBoxParentTree"></div></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("message.channelsRate",clientLocale)%>:</td><!--�������۷���-->
		<td><input type="text" id="fareRate" name="fareRate" value="" onkeydown="javascript:nextKeyPress(this)" size="30"/>%</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%>:</td><!--��ע-->
		<td><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="50"></textarea></td>
	</tr>
</table>
<div style="height: 70px;"></div>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%local.remove(); %>
