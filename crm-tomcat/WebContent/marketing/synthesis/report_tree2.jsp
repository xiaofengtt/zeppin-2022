<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean successFlag = false;

int cell_id = Utility.parseInt(request.getParameter("cell_id"),0);
int flag = Utility.parseInt(request.getParameter("flag"),0);
String cell_code = Utility.trimNull(request.getParameter("q_cell_code"));
String cell_name = Utility.trimNull(request.getParameter("cell_name"));
session.setAttribute("QUERY_CELL_NAME", cell_name);
session.setAttribute("QUERY_CELL_CODE", cell_code);

if(request.getMethod().equals("POST") && flag==2)
{
	cell_id = Utility.parseInt(request.getParameter("cell_id"),0);
	session.setAttribute("SESSION_CELL_ID",new Integer(cell_id));	//将CELL_ID保存到SESSION中
	successFlag = true;
}	
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<BASE TARGET="_self">
<link href="/includes/default.css" type=text/css rel=stylesheet>
<link rel="stylesheet" href="/includes/jQuery/tree/style/demo.css" type="text/css">
<link rel="stylesheet" href="/includes/jQuery/tree/style/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/includes/jQuery/tree/script/jquery.ztree-2.6.min.js"></script>
<!-- 右键菜单引入 -->
<script src="/includes/jQuery/tree/script/jquery.contextMenu.js" type="text/javascript"></script>
<link href="/includes/jQuery/tree/style/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<style>
.top
{
	float: left;
	width: 100%;
	font-size: 93%;
	line-height: normal;
	border-bottom: 1px solid #BCD2E6;
	text-align:left;
	
}

.top ul
{
	margin: 0;
	padding: 2px 10px 0 0px;
	list-style: none;
}

.top ul li
{
	display: inline;
	margin: 0;
	padding: 0;
}

.top ul li a 
{
	text-decoration: none;
}

.span1
{
	cursor:hand;
    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
    WIDTH: 72px;
    PADDING-TOP: 0px;
    BACKGROUND-COLOR:white;		
    HEIGHT: 20px;
}

.span2
{
	cursor:hand;
    WIDTH: 72px;
    PADDING-TOP: 0px;
    BACKGROUND-COLOR:white;		
    HEIGHT: 20px;
}
}
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<%if(successFlag) {%>
	window.returnValue = <%=cell_id%>;
	window.close();
<%}%>    
    
    
var zTree1;
var zTree2;
var setting;
var setting2;
var zNodes =[];
setting = {
	expandSpeed: "",
	checkable : false,
	async: true,
	expandSpeed : "", //折叠时的动画速度 或 取消动画
	asyncUrl: "report_tree_json.jsp",
	asyncParam: ["id","code"],
	callback:{
	  click: zTreeClick
	}
};

setting2 = {
	expandSpeed: "",
	checkable : false,
	async: true,
	expandSpeed : "", //折叠时的动画速度 或 取消动画
	asyncUrl: "report_tree_json2.jsp",
	asyncParam: ["id","code"],
	callback:{
	  click: zTreeClick
	}
};

//加载初始化Tree
$(document).ready(function(){
	refreshTree();
	$("body").bind("dblclick", 
		function(event){
			var node1 = zTree1.getSelectedNode();
			var node2 = null;
			if(zTree2)
				node2 = zTree2.getSelectedNode();
			if(node1 != null){
					document.theform.cell_id.value = node1.id;
					pressok();
			}
			if(node2 != null){
					document.theform.cell_id.value = node2.id;
					pressok();
			}
		});
});


//初始化Tree
function refreshTree() {
	zTree1 = $("#showtree1").zTree(setting, clone(zNodes));
	zTree2 = $("#showtree2").zTree(setting2, clone(zNodes));
}

function showtree(value) {
	if(value == 1){
		document.getElementById("showcell").style.display = "none";
		document.getElementById("showsub").style.display = "";
		document.getElementById("d0").className = "span2";
		document.getElementById("d1").className = "span1";
		document.theform.flag.value = 1;
	}else if(value == 0){
		document.getElementById("showcell").style.display = "";
		document.getElementById("showsub").style.display = "none";
		document.getElementById("d0").className = "span1";
		document.getElementById("d1").className = "span2";
		document.theform.flag.value = 0;
	}
}

//单击选中
function zTreeClick(event, treeId, treeNode){
	//var node = zTree1.getSelectedNode();
	//if (!node.isParent){
	//	document.theform.cell_id.value =  node.id;
	//	document.theform.cell_code.value = node.name;
		document.theform.cell_id.value =  treeNode.id;
		document.theform.cell_code.value = treeNode.name;
}

//处理节点的右键事件
function clone(jsonObj) {
    var buf;
    if (jsonObj instanceof Array) {
        buf = [];
        var i = jsonObj.length;
        while (i--) {
            buf[i] = clone(jsonObj[i]);
        }
        return buf;
    }else if (typeof jsonObj == "function"){
        return jsonObj;
    }else if (jsonObj instanceof Object){
        buf = {};
        for (var k in jsonObj) {
            buf[k] = clone(jsonObj[k]);
        }
        return buf;
    }else{
        return jsonObj;
    }
}

function query()
{
	document.theform.submit(); 
}

function pressok()
{
    if(document.theform.cell_id.value=="")
    {
        alert("没有选择产品单元！");
        return;
    }
    if(document.theform.cell_id.value=="0")
    {
        alert("该级别的产品单元不能选择！");
        return;
    }		 
    //window.returnValue = document.theform.cell_id.value;
    //window.close();
  
    document.theform.flag.value = 2;
    document.theform.submit();	 
}
</SCRIPT>
</HEAD>

<BODY>
<form name="theform" method="POST">
<input type="hidden" name="cell_id" value="">
<input type="hidden" name="flag" value="<%=flag%>">
<TABLE cellSpacing="1" cellPadding="1" width="100%" border="0" align="left">
	<tr>
		<TD>
      		<TABLE  class=flyoutMenu ></table>
    	</TD>
    </tr>	
    <tr>
		<td colspan="3"><font color="#215dc6"><b><img border="0" src="/images/member.gif"  width="32">产品单元选择</b></font></td>
	</tr>
	<tr>
		<td align="right">产品单元编号:</td>
		<td><input type="text" name="q_cell_code" value="<%=Utility.trimNull(cell_code)%>"  size="10"></td>
	</tr><tr>
		<td align="right">产品单元名称:<td>
			<input type="text" name="cell_name" value="<%=Utility.trimNull(cell_name)%>"  size="40">
		</td>
		<td><button type="button"  class="xpbutton3" onclick="javascript:query();" accessKey=Q name="btnQuery">查询(<u>Q</u>)</button></td>
	</tr>
	<tr>
		<td align="right"><b>选择的产品单元:</b></td><td> <input type="text" name="cell_code" size="40" readonly >
		</td>
		<td>
			<button type="button"  class="xpbutton3" onclick="javascript:pressok();" accessKey=o name="btnOk">&nbsp;&nbsp;确定(<u>O</u>)</button>
			&nbsp;
			<button type="button"  class="xpbutton3" accessKey=c name="btnCancel" title="取消" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
		</td>
	</tr>
</TABLE>
</form>
<!-- 树 -->
<div style="margin-top:120px; height:450px;">
	<div class="top">
		<ul>
			<li><a href="#" onclick="javascript:showtree(0);return false;"><span id="d0" class="<%if(flag == 0){%>span1<%}else{%>span2<%}%>">&nbsp;&nbsp;&nbsp;&nbsp;产品单元</span></a></li>
			<li><a href="#" onclick="javascript:showtree(1);return false;"><span id="d1" class="<%if(flag == 0){%>span2<%}else{%>span1<%}%>">&nbsp;&nbsp;&nbsp;&nbsp;所有产品</span></a></li>
		</ul>
		<ul><li></li></ul>
	</div>
	<div id="showcell" <%if(flag == 1){%>style="display:none"<%}%>>
		<div style="text-align: left; PADDING:1px; width:530px; height:450px; OVERFLOW: auto;">
			<ul id="showtree1" class="tree"></ul>
		</div>
	</div>
	<div id="showsub" <%if(flag == 0){%>style="display:none"<%}%>>
		<div style="text-align: left; PADDING:1px; width:530px; height:450px; OVERFLOW: auto;">
			<ul id="showtree2" class="tree"></ul>
		</div>
	</div>
</div>
</BODY>
</HTML>
