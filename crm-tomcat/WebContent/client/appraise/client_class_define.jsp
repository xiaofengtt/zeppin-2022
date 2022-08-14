<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.dao.*"  %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<!DOCTYPE html>
<html>
<head>
	<META http-equiv=Content-Type content="text/html; charset=gbk">
	<title></title>

	<link href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet">
	<script src="/lib/js/plugins/jquery-1.8.2.js"></script>
	<script src="//code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

	<script src="//cdn.jsdelivr.net/jquery.ui-contextmenu/1/jquery.ui-contextmenu.min.js"></script>
	<link href="/fancytree/skin-win8/ui.fancytree.css" rel="stylesheet">
	<script src="/fancytree/jquery.fancytree.js"></script>
	<script src="/fancytree/jquery.fancytree.dnd.js"></script>
	<script src="/fancytree/jquery.fancytree.edit.js"></script>
	<script src="/fancytree/jquery.fancytree.gridnav.js"></script>
	<script src="/fancytree/jquery.fancytree.table.js"></script>
	<link href="/fancytree/lib/prettify.css" rel="stylesheet">
	<script src="/fancytree/lib/prettify.js"></script>
	<link href="/fancytree/demo/sample.css" rel="stylesheet">
	<script src="/fancytree/demo/sample.js"></script>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/json2.js"></SCRIPT>
	

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
	var url_f = "./client_class_define_tree.jsp";
	var data = getData(url_f);
	var tree = "[";
	tree += "{\"title\": \"客户评级\",\"key\":\"0\", \"expanded\": true, \"folder\": true, \"children\": ";
	tree += data;
	tree += "}]";
	var treedata = JSON.parse(tree);
	$("#tree").fancytree({
		checkbox: false,
		titlesTabbable: false,     // Add all node titles to TAB chain
		quicksearch: true,        // Jump to nodes when pressing first character
		tooltip:false,
		source: treedata,

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
		case "addCustRating":
			newInfo(node.key,node.title);
			break;
		case "modifyCustRating":
			showInfo(node.key,node.title);
			break;
		case "deleteCustRating":
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
			{title: "添加客户分级", cmd: "addCustRating", uiIcon: "ui-icon-arrowreturn-1-e" },
			{title: "修改客户分级", cmd: "modifyCustRating", uiIcon: "ui-icon-pencil" },
			{title: "删除客户分级", cmd: "deleteCustRating", uiIcon: "ui-icon-trash" },
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

var define_id = "";
var define_name = "";
var paremt_id = "";
var level_id = "";//层级
var canmodi = "";//是否可修改1是2否
var candel = "";//是否可删除
var canadd = "";
var detail_id = "";
var level_ex = 0;


function getData(url){
	level_ex ++;
	var str = "";
	$.ajax({
        url:url,
        type: 'GET',
        dataType: 'xml',
        async: false,
        //timeout: 1000,
        error: function(xml){
          alert('加载XML文档出错');
        },
        success: function(xml){
			var len = $(xml).find("TreeNode").length-1;
			str += "[";
			$(xml).find("TreeNode").each(function(i){
				Href=$(this).attr("Href");
				Title=$(this).attr("Title");
				NodeXmlSrc=$(this).attr("NodeXmlSrc");
				if(Href.indexOf('#') != -1 && Href.indexOf('|') != -1){
					str += "{\"title\": \"";
					str += Title;
					if(NodeXmlSrc != undefined && NodeXmlSrc.indexOf("sQuery=") !=-1){
						if(level_ex == 1){
							str += "\",\"key\":\""+Href+"\", \"expanded\": true, \"folder\": true, \"children\":";
						}else{
							str += "\",\"key\":\""+Href+"\", \"expanded\": false, \"folder\": true, \"children\":";
						}
						str += getData("./"+NodeXmlSrc);
					}else{
						if(level_ex == 1){
							
						}
						str += "\",\"key\":\""+Href+"\", \"expanded\": true, \"folder\": false, \"children\":[]";
					}
					str += "}";
					if(len != i){
						str += ",";
					}
				}
			});
			str += "]";
        }
	});
	return str;
}

/*新增客户分级定义*/
function newInfo(key,title)
{
	//http://192.168.2.68:9080/client/appraise/client_class_define_new.jsp?define_id=0&parent_define_name=客户评级 &level_id=1&detail_id=0
 	if(key == '0' || key == ''){
		define_id = 0;
		level_id = 1;
		detail_id = 0;
		define_name = title;
		if(showModalDialog('client_class_define_new.jsp?define_id='+define_id+'&parent_define_name='+define_name+'&level_id='+level_id+'&detail_id='+detail_id,'', 'dialogWidth:420px;dialogHeight:310px;status:0;help:0')!=null){
			sl_update_ok();
			location.reload();
		}
	}else{
		var index = key.indexOf("#") + 1;
		var items = key.substring(index, key.length);
		define_id = items.split('|')[0];
		level_id = items.split('|')[1];
		canmodi = items.split('|')[2];
		detail_id = items.split('|')[3];
		candel = items.split('|')[4];
		canadd =  items.split('|')[5];
		define_name = title;
		//alert("define_id="+define_id+"^^^^^^^^level_id="+level_id+"^^^^^^^^canmodi="+canmodi+"^^^^^^^^detail_id="+detail_id+"^^^^^^^^candel="+candel+"^^^^^^^^canadd"+canadd);
		if(canadd == "" || canadd == "2" || canadd == "0"){
			sl_alert("该项为系统设定，无法修改！");
			return;
		}	
	 		
		if(showModalDialog('client_class_define_new.jsp?define_id='+define_id+'&parent_define_name='+define_name+'&level_id='+level_id+'&detail_id='+detail_id,'', 'dialogWidth:420px;dialogHeight:310px;status:0;help:0')!=null){
			sl_update_ok();
			location.reload();
		}
	} 
}
/*编辑群组*/
function showInfo()
{
 	if(key == '0' || key == ''){
 		return;
	}
	var index = key.indexOf("#") + 1;
	var items = key.substring(index, key.length);
	define_id = items.split('|')[0];
	level_id = items.split('|')[1];
	canmodi = items.split('|')[2];
	detail_id = items.split('|')[3];
	candel = items.split('|')[4];
	canadd =  items.split('|')[5];
	define_name = title;
    if(define_id == "" || define_id == "0")//depart_id=0或者为空，右键修改失效
    	return;
    if(canmodi == "" || canmodi == "2" || canmodi == "0"){
    	sl_alert("该项为系统设定，无法修改！");
    	return;
    }	
 		
    if(showModalDialog('client_class_define_edit.jsp?define_id='+define_id+'&flag=edit'+'&level_id='+(level_id-1)+'&detail_id='+detail_id+'&canmodi='+canmodi,'','dialogWidth:420px;dialogHeight:310px;status:0;help:0')!= null){
		sl_update_ok();
		location.reload();
	}
}

/*删除群组*/
function removeInfo(key,title){
 	if(key == '0' || key == ''){
 		sl_alert("该项为系统设定，无法删除！");
 		return;
	}
	var index = key.indexOf("#") + 1;
	var items = key.substring(index, key.length);
	define_id = items.split('|')[0];
	level_id = items.split('|')[1];
	canmodi = items.split('|')[2];
	detail_id = items.split('|')[3];
	candel = items.split('|')[4];
	canadd =  items.split('|')[5];
	define_name = title;
 	if(candel == "" || candel == "2" || candel == "0" || define_id == "0"){
 		sl_alert("该项为系统设定，无法删除！");
 		return;
 	}	
 		
	//该操作将删除   //群组   //要继续
    if(!sl_confirm("<%=LocalUtilis.language("message.deleteManager",clientLocale)%>(" + define_name + ")<%=LocalUtilis.language("message.group",clientLocale)%>，<%=LocalUtilis.language("message.toContinue",clientLocale)%>"))
	     return;
	location = 'client_class_define_remove.jsp?define_id='+define_id+'&detail_id='+detail_id+'&level_id='+(level_id-1);
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
