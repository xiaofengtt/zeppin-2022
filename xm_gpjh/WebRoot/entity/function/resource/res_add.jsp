<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*,com.whaty.util.editor.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String dir_id = request.getParameter("dir_id");
	String type_id = request.getParameter("type_id");
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	List typeList = manage.getResourceTypeList();
	
	WhatyEditorConfig editorConfig=manage.getWhatyEditorConfig(session);
	session.setAttribute("whatyEditorConfig",editorConfig);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/admincss.css" rel="stylesheet" type="text/css">
	<script type='text/javascript' src='/upol/dwr/interface/ResourceFunctionForDWR.js'></script>
  	<script type='text/javascript' src='/upol/dwr/engine.js'></script>
  	<script type='text/javascript' src='/upol/dwr/util.js'></script>

<script>
	var intRowIndex = 0;
	
	var startIndex = 6;
	var endIndex = 6;
	
	function addRow(){
			//添加一行			
			var newTr = Tbl.insertRow();
			
			//添加两列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			
			//设置列内容和属性			
			newTd0.innerHTML = '<INPUT name="field_name" type="text" size="6">';	
			newTd1.innerHTML = '<input type="text" name="field_value" size="20">';
			
			newTr.attachEvent("onclick",getIndex);
			intRowIndex = newTr.rowIndex;
	}
	
	function deleteRow(tbIndex) {
		if(tbIndex < 8) {
			alert("无属性可删！");
			return;
		} else {
			Tbl.deleteRow(tbIndex);
			intRowIndex = Tbl.rows.length - 1;
		}		
	}
	
	function getIndex() {
		intRowIndex = event.srcElement.parentElement.parentElement.rowIndex;
	}
	
	function showResourceProp() {
		if(document.add_form.resource.checked == true){
			if(document.add_form.resourcedir.checked == true) {
				document.add_form.resourcedir.checked = false;
				dir_add.style.display = 'none';
			}
			res_add.style.display = '';
			document.add_form.action = "res_addexe.jsp";
		}else{
			res_add.style.display = 'none';
		}
	}

	function showResourceDirProp() {
		if(document.add_form.resourcedir.checked == true){
			if(document.add_form.resource.checked == true) {
				document.add_form.resource.checked = false;
				res_add.style.display = 'none';
			}
			dir_add.style.display = '';
			document.add_form.action = "resource_addexe.jsp";
		}else{
			dir_add.style.display = 'none';
		}
	}
</script>

<script>
	var bLoaded=false; 
	
	function pageGuarding()	{
		if(currentflag==1) {//处于普通编辑模式
			document.add_form.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
			//alert(document.add_form.body.value);
		} else {
			if (currentflag==3) {//处于预浏览先通编辑模式
				document.add_form.body.value=oDiv.innerHTML;
			}
		}
		document.add_form.body.value=Absolute2Relative(document.add_form.body.value);//替换绝对路径
		//alert(document.add_form.body.value);
		if (bLoaded==false) {
			alert("表单正在下载")
			return false
		}
		
		//alert(res_add.style.display == '');
        if(res_add.style.display == '') {
        	if(document.add_form.res_name.value == "") {
        		alert("请填写资源名称");
        		document.add_form.res_name.focus();
        		return false;
        	}
        	if(document.add_form.body.value == "") {
        		alert("请填写资源内容");
        		
        		return false;
        	}
        	if(document.add_form.type_id.value == "") {
        		alert("请选择资源类型");
        		return false;
        	}
        }
        
        if(dir_add.style.display == '') {
        	if(document.add_form.dir_name.value == "") {
        		alert("请填写资源目录名称");
        		document.add_form.dir_name.focus();
        		return false;
        	}
        }
		
		var elements=document.getElementsByTagName("input");        
        for(i=0;i<elements.length;i++) { 
            var element=elements[i]; 
            if(element.type=="checkbox") {
            	if(element.checked)
            		return true;             
            } 
        } 
        alert("请选择添加资源还是添加资源目录!");
        return false;
	}
</script>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="28">
    <table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="../images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="../images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="../images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="../images/page_titleRightM.gif" class="pageTitleText">添加资源</td>
              </tr>
            </table></td>
          <td width="8"><img src="../images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
    </table>
    </td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder"> 
	<div class="border">
		<form name='add_form' action='' method='post' class='nomargin' onsubmit="return pageGuarding();">
		<INPUT type="hidden" name="dir_id" value="<%=dir_id%>">
		<table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td>
              	<input name="resource" type="checkbox" value="1" onclick="showResourceProp()">添加资源<BR>
              	<input name="resourcedir" type="checkbox" value="1" onclick="showResourceDirProp()">添加资源目录
              </td>
            </tr>
            
            <tr>
	            <td>
	            <div id=res_add style='display: none'>
		            <table id="Tbl">
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">名称</span> 
			              </td>
			              <td>
			              	<INPUT name="res_name" type="text" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">语言</span> 
			              </td>
			              <td>
			              	<INPUT name="res_language" type="text" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">描述</span> 
			              </td>
			              <td>
			              	<INPUT name="res_description" type="textarea" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">关键字</span> 
			              </td>
			              <td>
			              	<INPUT name="res_keywords" type="text" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">创建者</span> 
			              </td>
			              <td>
			              	<INPUT name="res_creatuser" type="text" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">内容</span> 
			              </td>
			              <td>
			              	<textarea class="smallarea"  name="body" cols="70" rows="12" id="body"></textarea>
							  <script language=JavaScript src="<%=editorConfig.getEditorURI() %>config.jsp"></script><br>
							  <script language=JavaScript src="<%=editorConfig.getEditorURI() %>edit.js"></script>
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">类型</span> 
			              </td>
			              <td>
			              	<SELECT name="type_id" onchange="ResourceFunctionForDWR.getResourceFieldList(this.options[this.options.selectedIndex].value, reply)">
			              		<OPTION value="">请选择资源类型</OPTION>
			              		<%
			              			for(int i=0; i<typeList.size(); i++) {
			              				ResourceType type = (ResourceType)typeList.get(i);
			              		%>
			              		<OPTION value="<%=type.getId()%>"><%=type.getName()%></OPTION>
			              		<%
			              			}
			              		%>
			              	</SELECT>
			              	<script type='text/javascript'>
							    var reply = function(data) {
							      if (data != null && typeof data == 'object') {
							      	while(Tbl.rows.length > 8)
							      		Tbl.deleteRow(Tbl.rows.length-1);
							      	for(var m=0; m<data.length; m++) {
								      	//添加一行			
										var newTr = Tbl.insertRow();
										
										//添加两列			
										var newTd0 = newTr.insertCell();			
										var newTd1 = newTr.insertCell();
										
										//设置列内容和属性			
										newTd0.innerHTML = '<INPUT name="field_name" type="text" size="6" value="' + data[m] + '">';	
										newTd1.innerHTML = '<input type="text" name="field_value" size="20">';
										
										intRowIndex = newTr.rowIndex;
									}
							      } else {
							      	//DWRUtil.setValue('d0', DWRUtil.toDescriptiveString(data, 1));
							      	return;
							      }
							    }
							</script>
			              </td>
			            </tr>
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">&nbsp;</td>
			              <td><span class="name" onclick="addRow()">添加新属性</span>&nbsp;&nbsp;<span class="name" onclick="deleteRow(intRowIndex)">删除属性</span></td>
			            </tr>
		            </table>
	            </div>
	            </td>
            </tr>
            
            <tr>
	            <td>
	            <div id=dir_add style='display: none'>
		            <table>    
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">名称：</span> 
			              </td>
			              <td>
			              	<INPUT name="dir_name" type="text" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">简介：</span> 
			              </td>
			              <td>
			              	<INPUT name="dir_note" type="textarea" >
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">状态：</span> 
			              </td>
			              <td>
			              	<INPUT name="dir_status" type="radio" value="0">锁定&nbsp;
			              	<INPUT name="dir_status" type="radio" value="1" checked>激活
			              </td>
			            </tr>
			            
			            <tr valign="bottom" class="postFormBox"> 
			              <td width="50" valign="bottom">
			              	<span class="name">权限：</span> 
			              </td>
			              <td>
			              	<INPUT name="dir_isinherit" type="radio" value="0" checked>单独&nbsp;
			              	<INPUT name="dir_isinherit" type="radio" value="1">继承父目录权限
			              </td>
			            </tr>
		            </table>
	            </div>
	            </td>
            </tr> 
            
            <tr valign="bottom" class="postFormBox"> 
              <td align="center">
              	<INPUT type="submit" value="添加">
              </td>
            </tr>           
        </table>
        </form>
	</div>
    </td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
        <tr>
          <td align="center" valign="middle"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script>bLoaded=true;</script>
</body>
</html>
