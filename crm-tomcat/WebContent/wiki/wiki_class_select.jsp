<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,java.math.*,java.io.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
//读取json
FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();
String json = faqs.queryClassJosn(vo,clientLocale);
//将json写入txt文件
 String FileContent  =  json;
//获取当前文件所在路径
String path=request.getRealPath("")+"/wiki/";
//String path=request.getRealPath("");
String filename= path+"wiki_class.txt";
try  { 
    FileOutputStream fos  =   new  FileOutputStream(filename); 
    OutputStreamWriter osw  =   new  OutputStreamWriter(fos,  "utf8" ); 
    osw.write(FileContent); 
    osw.flush(); 
}  catch  (Exception e) { 
    e.printStackTrace(); 
} 
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link href="/includes/default.css" type=text/css rel=stylesheet>
<link rel="stylesheet" href="/includes/jQuery/tree/style/demo.css" type="text/css">
<link rel="stylesheet" href="/includes/jQuery/tree/style/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/includes/jQuery/tree/script/jquery-ztree-2.2.js"></script>
<!-- 右键菜单引入 -->
<script src="/includes/jQuery/tree/script/jquery.contextMenu.js" type="text/javascript"></script>
<link href="/includes/jQuery/tree/style/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var zTree1; 
var setting; 
var zNodes =[];
setting = { 
	async: true, //异步加载 
	editable:false, //设置可编辑状态 注意：如果该值为true时可以拖拽，反之不能
    edit_renameBtn :false, //出现编辑按钮 
    edit_removeBtn :false, //删除按钮
    expandSpeed : "show", //折叠时的动画速度或取消动画
	asyncUrl:"wiki_class.txt", //改成从txt里取就可以显示出来，原因还没找到，暂时通过txt中转下，找到原因后再修改,可能是编码问题，txt是utf8的才可以
	callback:{
	  click: zTreeClick,
	  rightClick: zTreeOnRightClick //右键
	} 
};

//加载初始化Tree
$(document).ready(function(){
	refreshTree();
	$("body").bind("mousedown", 
		function(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				$("#rMenu").hide();
			}
		}
	);
	$("body").bind("dblclick", 
		function(event){
			var node = zTree1.getSelectedNode();
			if(node){
				document.theform.faq_class_name.value = node.name;
				document.theform.faq_class_no.value = node.faq_class_no;
				pressok();
			}
		}
	);
});
function zTreeClick(event, treeId, treeNode){
	document.theform.faq_class_name.value = treeNode.faq_class_no + " - " + treeNode.name;
	document.theform.faq_class_no.value = treeNode.faq_class_no;
}

//右键事件
function zTreeOnRightClick(event, treeId, treeNode) {
	if(treeNode == null){
   	    return false;
    }
	if(treeNode.faq_class_no == "" || treeNode.faq_class_no == "0"){
		return false;
	}
	var select_no = "";
	if(treeNode != null)
		select_no = treeNode.faq_class_no;
	if (!treeNode) {
		zTree1.cancelSelectedNode();
		showRMenu("root", event.clientX, event.clientY, select_no);
	} else if (treeNode && !treeNode.noR) {
		if (treeNode.newrole && event.target.tagName != "a" && $(event.target).parents("a").length == 0) {
			zTree1.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY, select_no);
		} else {
			zTree1.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY, select_no);
		}
	}
}
//初始化Tree
function refreshTree() {
	hideRMenu();
	zTree1 = $("#treeDemo").zTree(setting, clone(zNodes));
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

function hideRMenu() {
	$("#rMenu").hide();
}
function showRMenu(type, x, y, faq_class_no) {
	$("#rMenu li").show();
	$("#rMenu").css({"top":y+"px", "left":x+"px", "display":"block"});
}
//添加节点
function addTreeNode(){
	var node = zTree1.getSelectedNode();	
	var ret = newInfo();
	if(ret == null){
		return false;
	}else{
		var temp=[{'faq_class_no':ret.split("|")[0],'isParent':false,'open':true,'name':ret.split("|")[1],'bottom_flag':1}];
		zTree1.addNodes(zTree1.getSelectedNode().parentNode,temp); 
		var id ="treeDemo_"+ret.split("|")[0]+"_a";
		$("#"+id+"").click(); //注册事件
	}
}
function newInfo()
{
	hideRMenu();
	var ret = showModalDialog('wiki_class_update.jsp?is_new=1', '', 'dialogWidth:400px;dialogHeight:300px;status:0;help:0')
	if(ret != null){
		sl_update_ok();
		return ret;
	}
}

//修改节点名称 
function renameTreeNode() { 
	var node = zTree1.getSelectedNode();
	var ret = modiInfo(node.faq_class_no,node.name);
	if(ret == null){
		hideRMenu(); return false;
	}
	//新名称跟原名称不相同时才更新该节点
	if(ret != node.name)
	{
		node.name = ret;
		zTree1.updateNode(node,true);
	}
}
function modiInfo(faq_class_no,faq_class_name)
{
	hideRMenu();
	var ret = showModalDialog('wiki_class_update.jsp?faq_class_no=' + faq_class_no+'&faq_class_name='+faq_class_name, '', 'dialogWidth:400px;dialogHeight:300px;status:0;help:0')
	if(ret != null){
		sl_update_ok();
		return ret;
	}
}

//删除节点
function removeTreeNode() {
	var node = zTree1.getSelectedNode();
	if (node) {
		//先删除子节点在删除根节点
		if (node.isParent == true) {
			sl_alert("请先删除"+node.name+"的子分类！"); 
			hideRMenu();
			return  false;
		}
		if (sl_confirm("删除分类"+node.name)==true){
			$.ajax({
				type:"POST",
				url:"wiki_class_remove.jsp",
				data:"faq_class_no="+node.faq_class_no,
				success:function(data){
					sl_alert("删除成功");
					zTree1.removeNode(node); //删除成功后删除节点
					//reloadTree();
				},
				error:function(data){
					sl_alert("已经存在知识库内容，不能删除！");
				}
			});

		}
	}
	hideRMenu();
}
//刷新
function reloadTree() {
	refreshTree();
}
function pressok()
{
    var node = zTree1.getSelectedNode();
    if(node){ 
  		if(node.bottom_flag != 1)
		{
			alert("请选择明细节点");
			return false;
		}else{
			document.theform.faq_class_name.value = node.name;
			document.theform.faq_class_no.value = node.faq_class_no;
			var v = new Array();
			v[0]=document.theform.faq_class_no.value;
	  		v[1]=document.theform.faq_class_name.value;
	  		window.returnValue =v;
	  		window.close();
		}
  	}else{
		alert("请选择节点!");
		return false;
  	}
}
</SCRIPT>
</HEAD>

<BODY class="BODY body-nox">
<form name="theform">
<input type="hidden" name="faq_class_no"   value='<%=faq_class_no%>'>
<TABLE  height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
	 <TD>
            <TABLE  class=flyoutMenu >
      </table>
    </TD>
			<TD vAlign=top align=left width="100%">
			<TABLE  cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%">
							<tr>
								<td class="page-title"><b><%=menu_info%>&gt;&gt;知识库类别</b></td>
								<td align="right" valign="bottom"></td>
							</tr>
						</table>
						<br/>
						</TD>
					</TR>
					<tr class="product-list">
						<td><font color="#215dc6"><b></b></font></td><!--类别选择-->
					</tr>
					<tr class="product-list">
						<td><b><%=LocalUtilis.language("message.chooseType",clientLocale)%> <!--选择类别-->: <input type="text" name="faq_class_name" size="40" value=''></b></td>
					</tr>
					<tr class="product-list">
						<td>
						<!-- 知识库分类树-->
						<div style="text-align: left; border:0px;padding:3px; PADDING:0px; height:300px; width:430px; LINE-HEIGHT: 20px; OVERFLOW: hidden; overflow-y:auto">
							<ul id="treeDemo" class="tree"></ul>
						</div>	
						<!-- 右键菜单 --> 
						<ul id="rMenu" class="contextMenu">
							<li class="copy" id="m_add"><a href="#" onclick="addTreeNode();"><%=LocalUtilis.language("message.add",clientLocale)%></a></li>
							<li class="edit" id="m_edit"><a href="#" onclick="renameTreeNode();"><%=LocalUtilis.language("message.update",clientLocale)%></a></li>
							<li class="delete" id="m_del"><a href="#" onclick="removeTreeNode();"><%=LocalUtilis.language("message.delete",clientLocale)%></a></li>
							<li class="quit separator" id="m_reset"><a href="#" onclick="reloadTree();"><%=LocalUtilis.language("message.refresh",clientLocale)%></a></li>
						</ul>
						</td>
					</tr>
					<tr>
				<td align="right">
				<button type="button"  class="xpbutton3" onclick="javascript:pressok();" accessKey=o name="btnOk"><%=LocalUtilis.language("message.ok2",clientLocale)%> <!--确定-->(<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp; 
				<button type="button"  class="xpbutton3" accessKey=c name="btnCancel" title='<%=LocalUtilis.language("message.cancel",clientLocale)%>' onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;&nbsp; <!--取消-->
				</td>
			</tr>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
 </BODY>
</HTML>

