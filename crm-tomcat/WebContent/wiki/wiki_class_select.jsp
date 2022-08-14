<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,java.math.*,java.io.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
//��ȡjson
FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();
String json = faqs.queryClassJosn(vo,clientLocale);
//��jsonд��txt�ļ�
 String FileContent  =  json;
//��ȡ��ǰ�ļ�����·��
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
<!-- �Ҽ��˵����� -->
<script src="/includes/jQuery/tree/script/jquery.contextMenu.js" type="text/javascript"></script>
<link href="/includes/jQuery/tree/style/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var zTree1; 
var setting; 
var zNodes =[];
setting = { 
	async: true, //�첽���� 
	editable:false, //���ÿɱ༭״̬ ע�⣺�����ֵΪtrueʱ������ק����֮����
    edit_renameBtn :false, //���ֱ༭��ť 
    edit_removeBtn :false, //ɾ����ť
    expandSpeed : "show", //�۵�ʱ�Ķ����ٶȻ�ȡ������
	asyncUrl:"wiki_class.txt", //�ĳɴ�txt��ȡ�Ϳ�����ʾ������ԭ��û�ҵ�����ʱͨ��txt��ת�£��ҵ�ԭ������޸�,�����Ǳ������⣬txt��utf8�Ĳſ���
	callback:{
	  click: zTreeClick,
	  rightClick: zTreeOnRightClick //�Ҽ�
	} 
};

//���س�ʼ��Tree
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

//�Ҽ��¼�
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
//��ʼ��Tree
function refreshTree() {
	hideRMenu();
	zTree1 = $("#treeDemo").zTree(setting, clone(zNodes));
}

//����ڵ���Ҽ��¼�
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
//��ӽڵ�
function addTreeNode(){
	var node = zTree1.getSelectedNode();	
	var ret = newInfo();
	if(ret == null){
		return false;
	}else{
		var temp=[{'faq_class_no':ret.split("|")[0],'isParent':false,'open':true,'name':ret.split("|")[1],'bottom_flag':1}];
		zTree1.addNodes(zTree1.getSelectedNode().parentNode,temp); 
		var id ="treeDemo_"+ret.split("|")[0]+"_a";
		$("#"+id+"").click(); //ע���¼�
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

//�޸Ľڵ����� 
function renameTreeNode() { 
	var node = zTree1.getSelectedNode();
	var ret = modiInfo(node.faq_class_no,node.name);
	if(ret == null){
		hideRMenu(); return false;
	}
	//�����Ƹ�ԭ���Ʋ���ͬʱ�Ÿ��¸ýڵ�
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

//ɾ���ڵ�
function removeTreeNode() {
	var node = zTree1.getSelectedNode();
	if (node) {
		//��ɾ���ӽڵ���ɾ�����ڵ�
		if (node.isParent == true) {
			sl_alert("����ɾ��"+node.name+"���ӷ��࣡"); 
			hideRMenu();
			return  false;
		}
		if (sl_confirm("ɾ������"+node.name)==true){
			$.ajax({
				type:"POST",
				url:"wiki_class_remove.jsp",
				data:"faq_class_no="+node.faq_class_no,
				success:function(data){
					sl_alert("ɾ���ɹ�");
					zTree1.removeNode(node); //ɾ���ɹ���ɾ���ڵ�
					//reloadTree();
				},
				error:function(data){
					sl_alert("�Ѿ�����֪ʶ�����ݣ�����ɾ����");
				}
			});

		}
	}
	hideRMenu();
}
//ˢ��
function reloadTree() {
	refreshTree();
}
function pressok()
{
    var node = zTree1.getSelectedNode();
    if(node){ 
  		if(node.bottom_flag != 1)
		{
			alert("��ѡ����ϸ�ڵ�");
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
		alert("��ѡ��ڵ�!");
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
								<td class="page-title"><b><%=menu_info%>&gt;&gt;֪ʶ�����</b></td>
								<td align="right" valign="bottom"></td>
							</tr>
						</table>
						<br/>
						</TD>
					</TR>
					<tr class="product-list">
						<td><font color="#215dc6"><b></b></font></td><!--���ѡ��-->
					</tr>
					<tr class="product-list">
						<td><b><%=LocalUtilis.language("message.chooseType",clientLocale)%> <!--ѡ�����-->: <input type="text" name="faq_class_name" size="40" value=''></b></td>
					</tr>
					<tr class="product-list">
						<td>
						<!-- ֪ʶ�������-->
						<div style="text-align: left; border:0px;padding:3px; PADDING:0px; height:300px; width:430px; LINE-HEIGHT: 20px; OVERFLOW: hidden; overflow-y:auto">
							<ul id="treeDemo" class="tree"></ul>
						</div>	
						<!-- �Ҽ��˵� --> 
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
				<button type="button"  class="xpbutton3" onclick="javascript:pressok();" accessKey=o name="btnOk"><%=LocalUtilis.language("message.ok2",clientLocale)%> <!--ȷ��-->(<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp; 
				<button type="button"  class="xpbutton3" accessKey=c name="btnCancel" title='<%=LocalUtilis.language("message.cancel",clientLocale)%>' onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;&nbsp; <!--ȡ��-->
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

