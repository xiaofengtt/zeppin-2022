<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//取得页面查询参数
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));//客户ID
String imgURl = "http://"+request.getRemoteAddr()+":"+request.getServerPort();
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.modifyCustomerRating",clientLocale)%> </TITLE><!--修改客户评级-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<style>
.TreePanel {
	font-size: 12px; color: #666; font-family: verdana, geneva, arial, helvetica, sans-serif; white-space: nowrap
}
.TreePanel img {
	border-right: 0px; border-top: 0px; vertical-align: middle; border-left: 0px; border-bottom: 0px
}
.TreePanel a {
	color: #333; text-decoration: none
}
.TreePanel a.node {
	padding-right: 2px; padding-left: 2px; padding-bottom: 1px; padding-top: 1px; white-space: nowrap
}
.TreePanel a.nodesel {
	padding-right: 2px; padding-left: 2px; padding-bottom: 1px; padding-top: 1px; white-space: nowrap
}
.TreePanel a.node:hover {
	font-weight: bold; color: #333; text-decoration: none
}
.TreePanel a.nodesel:hover {
	font-weight: bold; color: #333; text-decoration: none
}
.TreePanel a.nodesel {
	background-color: #c0d2ec
}
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerService.js'></script>
<script language=javascript>
//图片处理对象
var IMG = {
	ICONS :	{
		root			:	"<%=request.getContextPath()%>/images/tree_img/emp.gif",
		folderopen		:	"<%=request.getContextPath()%>/images/tree_img/folderopen.gif",	
		minus			: 	"<%=request.getContextPath()%>/images/tree_img/minus.gif",//减号
		minusbottom		: 	"<%=request.getContextPath()%>/images/tree_img/minusbottom.gif",//减号最后
		join			:	"<%=request.getContextPath()%>/images/tree_img/join.gif",
		joinbottom		:	"<%=request.getContextPath()%>/images/tree_img/joinbottom.gif",
		plus			:	"<%=request.getContextPath()%>/images/tree_img/plus.gif",
		plusbottom		:	"<%=request.getContextPath()%>/images/tree_img/plusbottom.gif",
		empty			: 	"<%=request.getContextPath()%>/images/tree_img/empty.gif",
		line			: 	"<%=request.getContextPath()%>/images/tree_img/line.gif",
		emp			 	: 	"<%=request.getContextPath()%>/images/tree_img/emp.gif",
		checkbox0		:	"<%=request.getContextPath()%>/images/tree_img/checkbox_0.gif",
		checkbox1		:	"<%=request.getContextPath()%>/images/tree_img/checkbox_1.gif",
		checkbox2 		:   "<%=request.getContextPath()%>/images/tree_img/checkbox_2.gif"
	},
	CHANGE_IMG : function(imgObj){
		var imgName = imgObj.title;
		var imgName2 = null;
		switch(imgName){
			case "minus" : imgName2 = "plus";imgObj.title="plus";break;
			case "plus" : imgName2 = "minus";imgObj.title="minus";break;
			case "minusbottom" : imgName2 = "plusbottom";imgObj.title="plusbottom";break;
			case "plusbottom" : imgName2 = "minusbottom";imgObj.title="minusbottom";break;		
		}
		if(imgName2!=null){
			imgObj.setAttribute("src", IMG.ICONS[imgName2]);
		}	
	},
	TO_AMEND : function(dataObj){
		var imgJoinId = "imgJoin_"+ dataObj.getClassId()+"_"+dataObj.getParentId();
		var imgObj = $(imgJoinId);
		var hasNext = dataObj.getBottomFlag();
		if(hasNext==2){
			imgObj.setAttribute("src", IMG.ICONS["minusbottom"]);
			imgObj.setAttribute("title","minusbottom");
		}
		else{
			imgObj.setAttribute("src", IMG.ICONS["joinbottom"]);
			imgObj.setAttribute("title","joinbottom");
		}
	}
};
</script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/client_class_edit.js'></script>
<script language=javascript>
oState.cd_no = 1;
function SaveAction(){
	var v_cust_id = $("cust_id").value;
	for(var i=0 ; i<oState.CUST_DENFINE_ID_ARRAY.length; i++){
		var parentId = oState.CUST_DENFINE_ID_ARRAY[i];
		var v_classId = oState.CUST_LEVEL_MAP[parentId];
		var paramsObj = {cust_id:v_cust_id,define_id:parentId,classId:v_classId};
		DataProxy.paramsObjs2.push(paramsObj);
	}
	if(oState.CUST_DENFINE_ID_ARRAY.length>0){
		DataProxy.updateCustLevel();
	}
	else{
		sl_alert('<%=LocalUtilis.language("message.noChangeCustLevel",clientLocale)%> ');//未更改客户级别
	}
}
function show(parm){
   for(i=0;i<8;i++){  
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
window.onload = function(){	
	var cust_id = $("cust_id").value;
	DataProxy.init(cust_id);
	//window.frames["sonIframe"].document.getElementById("btnSave").style.display = 'none'; 
	//window.frames["sonIframe"].document.getElementById("titleNameTable").style.display = 'none';
	//window.frames["sonIframe"].document.getElementById("showflag").value = '1';
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<input type="hidden" name="cust_id" id="cust_id" value="<%=cust_id%>"/>
<input type="hidden" id="inputMan" value="<%=input_operatorCode%>"/>
<input type="hidden" id="bsucess" value="0"/>

<div align="left" class="page-title page-title-noborder">
	<b><%=LocalUtilis.language("message.modifyCustomerRating",clientLocale)%> </b><!--修改客户评级-->
</div>	

<div align="left">
	<table border="0" cellspacing="1" cellpadding="2" width="99%">
		<tr>
			<td width="10">&nbsp;&nbsp;</td>
			<td width="200" vAlign=top align=left>
				<div>&nbsp;&nbsp;</div>
				<div id="TreePanel" class="TreePanel" style="overflow-y:auto;width:200;"></div>
				<br>
				<div align="left" style="margin-left:5px;">
					<!--保存-->
                    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;
					<!--返回-->
                    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:location.href='client_class.jsp';"><%=LocalUtilis.language("message.back",clientLocale)%> 
                    (<u>C</u>)</button>
				</div>
			</td>
			<td>
				<iframe src ="<%=request.getContextPath()%>/client/clientinfo/client_query_info2.jsp?cust_id=<%=cust_id%>" 
						name="sonIframe" frameborder="no" border="0" height="650" width="900" />
			</td>
		</tr>
	</table>
</div>
</BODY>
</HTML>