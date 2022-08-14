<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.dao.*"  %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<!DOCTYPE html>
<html>
<head>
	<META http-equiv=Content-Type content="text/html; charset=gbk">
	<title></title>

	<link href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet">
	<!-- <script src="//code.jquery.com/jquery-3.2.1.min.js"></script> -->
	<script src="/lib/js/plugins/jquery-1.8.2.js"></script>
	<script src="//code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

	<!-- jquery-contextmenu (https://github.com/mar10/jquery-ui-contextmenu/) -->
	<script src="//cdn.jsdelivr.net/jquery.ui-contextmenu/1/jquery.ui-contextmenu.min.js"></script>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<link href="/fancytree/skin-win8/ui.fancytree.css" rel="stylesheet">
	<script src="/fancytree/jquery.fancytree.js"></script>
	<script src="/fancytree/jquery.fancytree.dnd.js"></script>
	<script src="/fancytree/jquery.fancytree.edit.js"></script>
	<script src="/fancytree/jquery.fancytree.gridnav.js"></script>
	<script src="/fancytree/jquery.fancytree.table.js"></script>
<!--
	<script src="../../build/jquery.fancytree-all.min.js"></script>
-->

	<!-- Start_Exclude: This block is not part of the sample code -->
	<link href="/fancytree/lib/prettify.css" rel="stylesheet">
	<script src="/fancytree/lib/prettify.js"></script>
	<link href="/fancytree/demo/sample.css" rel="stylesheet">
	<script src="/fancytree/demo/sample.js"></script>
	<!-- End_Exclude -->

<style type="text/css">
	.ui-menu {
		width: 180px;
		font-size: 63%;
	}
	.ui-menu kbd { /* Keyboard shortcuts for ui-contextmenu titles */
		float: right;
	}
	/* custom alignment (set by 'renderColumns'' event) */
	td.alignRight {
	   text-align: right;
	}
	td.alignCenter {
	   text-align: center;
	}
	td input[type=input] {
		width: 40px;
	}
</style>

<script type="text/javascript">
var CLIPBOARD = null;
$(function(){

	$("#tree").fancytree({
		checkbox: false,
		titlesTabbable: false,     // Add all node titles to TAB chain
		quicksearch: true,        // Jump to nodes when pressing first character
		tooltip:false,
		// source: SOURCE,
		source: { url: "/system/basedata/deptTreeJson.jsp"},

		extensions: ["edit", "dnd", "table", "gridnav"],

		dnd: {
			preventVoidMoves: false,
			preventRecursiveMoves: false,
			autoExpandMS: 400,
			dragStart: function(node, data) {
				return true;
			},
			dragEnter: function(node, data) {
				// return ["before", "after"];
				return true;
			},
			dragDrop: function(node, data) {
				//data.otherNode.moveTo(node, data.hitMode);
			}
		},
		edit: {
			triggerStart: ["f2", "shift+click", "mac+enter"],
			close: function(event, data) {
				if( data.save && data.isNew ){
					// Quick-enter: add new nodes until we hit [enter] on an empty title
					$("#tree").trigger("nodeCommand", {cmd: "addSibling"});
				}
			}
		},
		table: {
			indentation: 20,
			nodeColumnIdx: 2,
			checkboxColumnIdx: 0
		},
		gridnav: {
			autofocusInput: false,
			handleCursorKeys: true
		},

		lazyLoad: function(event, data) {
			data.result = {url: "../demo/ajax-sub2.json"};
		},
		createNode: function(event, data) {
			var node = data.node,
				$tdList = $(node.tr).find(">td");

			if( node.isFolder() ) {
				$tdList.eq(2)
					.prop("colspan", 6)
					.nextAll().remove();
			}
		},
		renderColumns: function(event, data) {
			var node = data.node,
				$tdList = $(node.tr).find(">td");
			$tdList.eq(4).find("input").val(node.data.foo);
		}
	}).on("nodeCommand", function(event, data){
		// Custom event handler that is triggered by keydown-handler and
		// context menu:
		var refNode, moveMode,
			tree = $(this).fancytree("getTree"),
			node = tree.getActiveNode();

		switch( data.cmd ) {
		case "moveUp":
			refNode = node.getPrevSibling();
			if( refNode ) {
				node.moveTo(refNode, "before");
				node.setActive();
			}
			break;
		case "moveDown":
			refNode = node.getNextSibling();
			if( refNode ) {
				node.moveTo(refNode, "after");
				node.setActive();
			}
			break;
		case "indent":
			refNode = node.getPrevSibling();
			if( refNode ) {
				node.moveTo(refNode, "child");
				refNode.setExpanded();
				node.setActive();
			}
			break;
		case "outdent":
			if( !node.isTopLevel() ) {
				node.moveTo(node.getParent(), "after");
				node.setActive();
			}
			break;
		case "rename":
			node.editStart();
			break;
		case "remove":
			refNode = node.getNextSibling() || node.getPrevSibling() || node.getParent();
			node.remove();
			if( refNode ) {
				refNode.setActive();
			}
			break;
		case "addChild":
			node.editCreateNode("child", "");
			break;
		case "addSibling":
			node.editCreateNode("after", "");
			break;
		case "cut":
			CLIPBOARD = {mode: data.cmd, data: node};
			break;
		case "copy":
			CLIPBOARD = {
				mode: data.cmd,
				data: node.toDict(function(n){
					delete n.key;
				})
			};
			break;
		case "clear":
			CLIPBOARD = null;
			break;
		case "paste":
			if( CLIPBOARD.mode === "cut" ) {
				// refNode = node.getPrevSibling();
				CLIPBOARD.data.moveTo(node, "child");
				CLIPBOARD.data.setActive();
			} else if( CLIPBOARD.mode === "copy" ) {
				node.addChildren(CLIPBOARD.data).setActive();
			}
			break;
		case "addLevel":
			newInfo(node.key,node.title);
			break;
		case "update":
			showInfo(node.key,node.title);
			break;
		case "delete":
			removeInfo(node.key,node.title);
			break;
		case "refresh":
			location.reload();
			break;
		default:
			alert("Unhandled command: " + data.cmd);
			return;
		}

	// }).on("click dblclick", function(e){
	// 	console.log( e, $.ui.fancytree.eventToString(e) );

	})/* .on("keydown", function(e){
		var cmd = null;

		// console.log(e.type, $.ui.fancytree.eventToString(e));
		switch( $.ui.fancytree.eventToString(e) ) {
		case "ctrl+shift+n":
		case "meta+shift+n": // mac: cmd+shift+n
			cmd = "addChild";
			break;
		case "ctrl+c":
		case "meta+c": // mac
			cmd = "copy";
			break;
		case "ctrl+v":
		case "meta+v": // mac
			cmd = "paste";
			break;
		case "ctrl+x":
		case "meta+x": // mac
			cmd = "cut";
			break;
		case "ctrl+n":
		case "meta+n": // mac
			cmd = "addSibling";
			break;
		case "del":
		case "meta+backspace": // mac
			cmd = "remove";
			break;
		// case "f2":  // already triggered by ext-edit pluging
		// 	cmd = "rename";
		// 	break;
		case "ctrl+up":
			cmd = "moveUp";
			break;
		case "ctrl+down":
			cmd = "moveDown";
			break;
		case "ctrl+right":
		case "ctrl+shift+right": // mac
			cmd = "indent";
			break;
		case "ctrl+left":
		case "ctrl+shift+left": // mac
			cmd = "outdent";
		}
		if( cmd ){
			$(this).trigger("nodeCommand", {cmd: cmd});
			// e.preventDefault();
			// e.stopPropagation();
			return false;
		}
	}) */;

	/*
	 * Tooltips
	 */
	// $("#tree").tooltip({
	// 	content: function () {
	// 		return $(this).attr("title");
	// 	}
	// });

	/*
	 * Context menu (https://github.com/mar10/jquery-ui-contextmenu)
	 */
	$("#tree").contextmenu({
		delegate: "span.fancytree-node",
		menu: [
			{title: "添加下级部门", cmd: "addLevel", uiIcon: "ui-icon-arrowreturn-1-e" },
			{title: "修改", cmd: "update", uiIcon: "ui-icon-pencil" },
			{title: "删除", cmd: "delete", uiIcon: "ui-icon-trash" },
			{title: "刷新", cmd: "refresh", uiIcon: "ui-icon-refresh" }
		],
		beforeOpen: function(event, ui) {
			var node = $.ui.fancytree.getNode(ui.target);
			$("#tree").contextmenu("enableEntry", "paste", !!CLIPBOARD);
			node.setActive();
		},
		select: function(event, ui) {
			var that = this;
			// delay the event, so the menu can close and the click event does
			// not interfere with the edit control
			setTimeout(function(){
				$(that).trigger("nodeCommand", {cmd: ui.cmd});
			}, 100);
		}
	});
});

var depart_id = "";
var depart_name = "";
var paremt_id = "";

function showInfo(key,title)
{	
    if(key==''||key=='0'){//depart_id=0或者为空，右键修改实效
    	return;
    }else{
    	depart_id=key.split("_")[0];
    }
    if(showModalDialog('department_update.jsp?depart_id=' + depart_id,'','dialogWidth:420px;dialogHeight:310px;status:0;help:0')!= null){
		sl_update_ok();
		location.reload();
	}
}

function newInfo(key,title)
{
	if(key == "0" || key == ""){
		depart_id="";
	}else{
		depart_id=key.split("_")[0];
	}
	if(showModalDialog('department_new.jsp?parent_id=' + depart_id,'', 'dialogWidth:420px;dialogHeight:310px;status:0;help:0')!=null){
		sl_update_ok();
		location.reload();
	}
}

function removeInfo(key,title)
{
	if(key == "0" || key == ""){
		return;
	}else{
		depart_id=key.split("_")[0];
		depart_name=title.split("_")[1].split("(")[0];
	}
	if(!sl_confirm("<%=LocalUtilis.language("message.deleteManager",clientLocale)%>(" + depart_name + ")<%=LocalUtilis.language("class.department",clientLocale)%>，<%=LocalUtilis.language("message.toContinue",clientLocale)%> "))
	//"该操作将删除(" + depart_name + ")部门，要继续"
	     return;
	location = 'department_delete.jsp?depart_id=' + depart_id;
}

</script>
<style type="text/css">
.page-title{position:relative; font-size:14px; font-family: Tahoma, Verdana, Arial;font-weight:bold; color:#1466b8; border-bottom:2px solid #197fe6; padding:10px 15px; }
.page-title:before{position:absolute; left:0; top:8px; height:20px; width:8px; border-radius:5px; content:" ";  background-color:#197fe6; margin-right:10px;}
.page-title-noborder{border:none; }
.page-title-select{position:absolute; right:10px; top:15px;}
.tree-wrapper{width:500px; background-color:#fff; border-radius:5px; margin:20px auto; border:1px solid #ccc; padding:10px;}
.tree-wrapper #tree{width:100%;}
.tree-wrapper #tree td{border:none;}
.tree-wrapper #tree .hidden{display:none;}
</style>
</head>

<body class="example">
	<div class="page-title"><%=menu_info%></div>
	<div class="tree-wrapper">
	<table id="tree">
		<colgroup>
		<col width="0px">
		</colgroup>
		<tbody>
			<tr>
				<td class="hidden"></td>
				<td class="hidden"></td>
				<td></td>
			</tr>
		</tbody>
	</table>
	</div>
</body>
</html>
