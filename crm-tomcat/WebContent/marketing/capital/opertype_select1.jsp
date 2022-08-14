<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*,java.io.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc"%>
<%
OpertypeLocal local = EJBFactory.getOpertype(); 
String json = local.queryCapitaltypeJosn(null,clientLocale);
//将json写入txt文件
 String FileContent  =  json;
//获取当前文件所在路径
String path=request.getRealPath("")+"/marketing/capital/";
//String path=request.getRealPath("");
String filename= path+"opertype.txt";
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
  	<meta http-equiv="content-type" content="text/html; charset=GBK">
  	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/includes/jQuery/tree/style/demo.css" type="text/css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/includes/jQuery/tree/style/zTreeStyle.css" type="text/css">
  	<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-ztree-2.2.js"></script>
	<!-- 右键菜单引入 -->
	<script src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery.contextMenu.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/includes/jQuery/tree/style/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
	<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
  	<SCRIPT LANGUAGE="JavaScript">
	var zTree1; 
	var setting; 
	var zNodes =[];
	setting = { 
		async: true, //异步加载 
		editable:false, //设置可编辑状态 注意：如果该值为true时可以拖拽，反之不能
	    edit_renameBtn :false, //出现编辑按钮 
	    edit_removeBtn :false, //删除按钮
	    expandSpeed : "show", //折叠时的动画速度 或 取消动画
		//asyncUrl:"opertype_json.jsp",  //获取节点数据的URL地址 使用这个地址数据显示不出来，hesl 2011.7.8
		asyncUrl:"opertype.txt", //改成从txt里取就可以显示出来，原因还没找到，暂时通过txt中转下，找到原因后再修改,可能是编码问题，txt是utf8的才可以
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
					document.theform.selhref.value = node.caption;
					document.theform.capitaloper_type.value = node.id;
					pressok();
				}
			}
		);
	});
	function zTreeClick(event, treeId, treeNode){
		document.theform.selhref.value = treeNode.caption;
		document.theform.capitaloper_type.value = treeNode.id;
	}
	
	//右键事件
	function zTreeOnRightClick(event, treeId, treeNode) {
		if(treeNode == null){
	   	    return false;
        }
		var select_id = -1;
		if(treeNode != null)
			select_id = treeNode.id;
		if (!treeNode) {
			zTree1.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY, select_id);
		} else if (treeNode && !treeNode.noR) {
			if (treeNode.newrole && event.target.tagName != "a" && $(event.target).parents("a").length == 0) {
				zTree1.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY, select_id);
			} else {
				zTree1.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY, select_id);
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
	function showRMenu(type, x, y, id) {
		$("#rMenu li").show();
		if (type=="root") {
			$("#m_del").hide();
			$("#m_unCheck").hide();
			$("#m_add").hide();
		}
		if(id == 0)
		{
			$("#m_del").hide();
			$("#m_unCheck").hide();
		}
		$("#rMenu").css({"top":y+"px", "left":x+"px", "display":"block"});
	}
	
	//修改节点名称 
	function renameTreeNode() { 
		var node = zTree1.getSelectedNode();
		var ret = showInfo(node.id);
		if(ret == null){
			hideRMenu(); return false;
		}
		//新名称跟原名称不相同时才更新该节点
		if(ret != node.name)
		{
			node.name = ret;
			document.theform.selhref.value = ret;
			zTree1.updateNode(node,true);
		}
	} 

	function showInfo(serial_no)
	{
		hideRMenu();
		var ret = showModalDialog('opertype_update.jsp?depart_id=' + serial_no, '', 'dialogWidth:400px;dialogHeight:300px;status:0;help:0')
		if(ret != null){
			sl_update_ok();
			return ret;
		}
	}

	
	//添加节点
	function addTreeNode(){
		if(zTree1.getSelectedNode()){  //是否选中节点 
			var ret = newInfo(zTree1.getSelectedNode().id,zTree1.getSelectedNode().zc_flag);
			if(ret == null){
				return false;
			}else{
				var temp=[{'id':ret.split("|")[0],'open':false,'name':ret.split("|")[1],'caption':ret.split("|")[1],'bottom_flag':1}];
				zTree1.addNodes(zTree1.getSelectedNode(),temp); 
				zTree1.getSelectedNode().bottom_flag = 2;
				var id="treeDemo_"+ret.split("|")[0]+"_a";
				$("#"+id+"").click(); //注册事件
			}
			hideRMenu();
		} 
	}

	function newInfo(serial_no,zc_flag)
	{
		hideRMenu();
		var ret = showModalDialog('opertype_update.jsp?parent_id=' + serial_no+'&zc_flag='+zc_flag, '', 'dialogWidth:400px;dialogHeight:300px;status:0;help:0')
		if(ret != null){
			sl_update_ok();
			return ret;
		}
	}


	//刷新
	function reloadTree() {
		refreshTree();
	}
	
	function pressok()
	{
		var v = new Array();
		v[0]=document.theform.capitaloper_type.value;
  		v[1]=document.theform.selhref.value;
  		window.returnValue =v;
  		window.close();
	}
  </SCRIPT>
</HEAD>

<BODY class="BODY">
<form name="theform">
<input type="hidden" id="capitaloper_type" value="">
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
								<td class="page-title"><b><%=menu_info%></b></td>
								<td align="right" valign="bottom"></td>
							</tr>
							<tr>
								<td colspan="2">
								<br/>
								</td>
							</tr>
						</table>
						</TD>
					</TR>
			<tr class="product-list">
				<td><b><%=LocalUtilis.language("message.chooseType",clientLocale)%> <!--选择类别-->: <input type="text" name="selhref" size="40" readonly value=''></b></td>
			</tr>
					<tr class="product-list">
						<td>
						<!-- 资产类别树 -->
						<div style="text-align: left; border:0px;padding:3px; PADDING:0px; height:300px; width:430px; LINE-HEIGHT: 20px; OVERFLOW: hidden; overflow-y:auto">
							<ul id="treeDemo" class="tree"></ul>
						</div>	
						<!-- 右键菜单 --> 
						<ul id="rMenu" class="contextMenu">
							<li class="copy" id="m_add"><a href="#" onclick="addTreeNode();"><%=LocalUtilis.language("menu.addSubType",clientLocale)%></a></li>
							<li class="edit" id="m_del"><a href="#" onclick="renameTreeNode();"><%=LocalUtilis.language("message.update",clientLocale)%></a></li>
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
