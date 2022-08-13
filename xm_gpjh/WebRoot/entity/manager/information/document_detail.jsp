<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script>
	var msg = "<s:property value='msg'/>";
	function doCommit() {
		var content;
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("bean.note"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		if(isNull(document.getElementById("bean.title").value)){ 
		}else{
			alert("请填写公文主题!!");
			document.getElementById("bean.title").focus();

			return false;
		}
		
		if(content.length <1){
			alert("公文内容为空!");
			return false;
		}
		return true;
	}
	function doCommit1() {
		if(!doCommit()) return false;
		var back = true;
		var m1 = document.getElementsByName("manager_id");
		for(var i=0;i<m1.length;i++){
			if(m1[i].checked){
				back = false;
				break;
			}
		}
		if(back){
			var m2 = document.getElementsByName("sitemanager_id");
			for(var i=0;i<m2.length;i++){
				if(m2[i].checked){
					back = false;
					break;
				}
			}
		}
		if(back){
			alert("请指定收件人后再发送！");
			return;
		}
		document.info_news_add.action="/entity/information/peDocumentOutbox_returnDocument.action";
		document.forms["info_news_add"].submit();		
	}
	function doCommit2() {
		if(!doCommit()) return false;
		document.info_news_add.action="/entity/information/peDocumentOutbox_saveDocument.action";
		document.forms["info_news_add"].submit();		
	}
	function showDiv(objID){
		var tempObj;
		if(document.getElementById(objID))	tempObj = document.getElementById(objID);
		hideOther(objID.substring(3,objID.length));
		if(tempObj.style.display == "none")	tempObj.style.display = "block";
		else	tempObj.style.display = "none";
		
	}
	
	function listSelectM() {
		var form = document.getElementsByName("manager_id");
		for (var i = 0 ; i < form.length; i++) {
				form[i].checked = document.info_news_add.manager_all.checked;
		}
		return true;
	}
	function listSelectSiteM() {
		var form = document.getElementsByName("sitemanager_id");
		for (var i = 0 ; i < form.length; i++) {
				form[i].checked = document.info_news_add.sitemanager_all.checked;
		}
		return true;
	}
	
	
	function hideOther(par)
	{
		for(var i=1;i<5;i++)
		{
			if(document.getElementById("cnt"+String(i)) && document.getElementById("cnt"+String(i)).style.display == "block" && i!=par)
			{
				document.getElementById("cnt"+String(i)).style.display = "none";
			}
		}
	}
	
	
	function listSelect(listId) {
		var form = document.getElementsByName(listId);
		for (var i = 0 ; i < form.length; i++) 
		{
			form[i].checked=false;
		}
	}
	function onload_edit(){
		var sender='<s:property value="bean.peDocument.ssoUser.id"/>';
		var old_receivers = '';
		var managers = window.document.getElementsByName("manager_id");
		for(var i=0;i<managers.length;i++){
			var id = 'name_'+managers[i].id;
			var vl = window.document.getElementById(id).value+',';
			if(msg.indexOf(managers[i].value)>=0)
					old_receivers += vl;
			if(sender==managers[i].value){
				managers[i].checked = true;
				window.document.getElementById('oldsender').innerHTML="发件人："+vl;
				window.document.getElementById('receiver').value=vl;			
			}
		}
		var sitemanagers = window.document.getElementsByName("sitemanager_id");
		for(var i=0;i<sitemanagers.length;i++){
			var id = 'name_'+sitemanagers[i].id;
			var vl = window.document.getElementById(id).value+',';
			if(msg.indexOf(sitemanagers[i].value)>=0)
				old_receivers += vl;
			if(sender==sitemanagers[i].value){
				sitemanagers[i].checked = true;
				window.document.getElementById('oldsender').innerHTML="发件人："+vl;
				window.document.getElementById('receiver').value=vl;				
			}
		}		
		window.document.getElementById("old").innerHTML="所有接收人："+old_receivers;
		if(msg.indexOf("reply")<0){
			window.document.getElementById("reply").style.display = "none";
		}else{
			editorLoad();
		}
	}
	function setreceiver(){
		var receivers = window.document.getElementById('receiver').value;
		var managers = window.document.getElementsByName('manager_id');
		for(var i=0;i<managers.length;i++){
			var id = 'name_'+managers[i].id;
			var vl = window.document.getElementById(id).value+',';
			if(managers[i].checked){
				if(receivers.indexOf(vl)<0)
					receivers += vl;
			}else{
				if(receivers.indexOf(vl)>=0)
					receivers = receivers.replace(vl,'');
			}
		}
		var sitemanagers = window.document.getElementsByName('sitemanager_id');
		for(var i=0;i<sitemanagers.length;i++){
			var id = 'name_'+sitemanagers[i].id;
			var vl = window.document.getElementById(id).value+',';
			if(sitemanagers[i].checked){
				if(receivers.indexOf(vl)<0)
					receivers += vl;
			}else{
				if(receivers.indexOf(vl)>=0)
					receivers = receivers.replace(vl,'');
			}
		}
		window.document.getElementById('receiver').value=receivers;
	}
</script>
</head>
<body onload="onload_edit();">
<div id="main_content">
    <div class="content_title">公文内容</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="86%">
				<tr ><td height="25" align="center" class="topTitleText"><s:property value="bean.peDocument.title" /></td></tr>
				<tr><td class="content_title"  align="left">
					<table width="100%" ><tr><td align="left"><div id="oldsender"></div></td>
						<td align="left">发送时间：<s:date name="bean.peDocument.sendDate" format="yyyy-MM-dd" /></td></tr></table>
					</td></tr>
				<tr><td class="content_title"  align="left">
				<table width="100%"><tr><td align="left"><div id="old"></div></td></tr></table></td></tr>
				<tr><td>
				<table width="100%" ><tr><td align="left" class="content_title" width="32" valign="top">内容:</td>
				<td align="left" class="content_title" ><s:property value='bean.peDocument.note' escape='false'/></td></tr></table>
				</td></tr>
			</table>
	  </div>
	<div align="center">
	<table  border="0" width="86%"><tr><td align="center">
		<a href="#" title="返回" class="button">
       		<span class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="javascript:window.history.back();" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">返回</span>
       		</span>
       	</a></td><td align="center" >
       	<a href="#" title="回复" class="button">
       		<span class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="showDiv('reply');editorLoad();" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">回复</span>
       		</span>
       	</a></td></tr></table>
	</div>
<div class="k_cc" style="display:block" id="reply" >
 <form name='info_news_add' method='post' class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">回复公文</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">回复主题*</span></td>
              <td > <input name="bean.title" value="回复:<s:property value='bean.peDocument.title'/>" type="text" class="selfScale" id="bean_title" onMouseOver="window.status='添加公文的主题';" onMouseOut="window.status='信息提示...'"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">接&nbsp;&nbsp;收&nbsp;&nbsp;人*</span></td>
              <td ><input type="text" class="selfScale" width="400" readonly id="receiver" onMouseOver="window.status='点击后面的按钮选择所要接收公文的管理员';" onMouseOut="window.status='信息提示...'">
			&nbsp;&nbsp;<input id="managers_button" type="button" name="managers" onClick="showDiv('cnt1')" value="管理员" onMouseOver="window.status='请选择所要接收公文的管理员名称';" onMouseOut="window.status='信息提示...'">
			<div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <s:iterator value="peManagers" status="row">
                	<s:if test="(#row.index)%2==0"><tr></s:if>
                    <td height="22"><input id="manager_check_<s:property value='#row.index'/>" type="checkbox"  onclick="setreceiver();" name="manager_id" value="<s:property value="ssoUser.id"/>">
                    	<INPUT type="hidden" id="name_manager_check_<s:property value='#row.index'/>"  value="<s:property value='name'/>" /><s:property value="name"/></td>
                    <s:if test="(#row.index)%2==1"></tr></s:if>
                </s:iterator>
                <tr>
                	<td height="22">
                		<input id="manager_id_all" type="checkbox" name="manager_all" value="" onclick="listSelectM();setreceiver();">全选</td>
                    <td>
                    	<input type='button' onclick="showDiv('cnt1')" value="确定">
                    </td>
                </tr>
                </table>
              </div>&nbsp;&nbsp;
            <input id="sitemanagers_button" type="button" name="sitemanagers" onClick="showDiv('cnt2')" value="分站管理员" onMouseOver="window.status='请选择所要接收公文的站点管理员名称';" onMouseOut="window.status='信息提示...'">
			<div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">                
                <s:iterator value="siteManagers" status="row"> 
                	<s:if test="(#row.index)%2==0"><tr></s:if>
                    <td height="22"><input id="sitemanager_check_<s:property value='#row.index'/>" type="checkbox" name="sitemanager_id" onclick="setreceiver();"  value="<s:property value="ssoUser.id"/>">
                    	<INPUT type="hidden" id="name_sitemanager_check_<s:property value='#row.index'/>" value="<s:property value='name'/>" /><s:property value="name"/></td>
                    <s:if test="(#row.index)%2==1"></tr></s:if>
                </s:iterator>
                <tr>
                	<td height="22">
                		<input id="manager_id_all" type="checkbox" name="sitemanager_all" value="" onclick="listSelectSiteM();setreceiver();">全选</td>
                    <td>
                    	<input type='button' onclick="showDiv('cnt2')" value="确定">
                    </td>
                </tr>
                </table>
              </div>
              </td>
            </tr>
			<tr valign="bottom" class="postFormBox">
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">公文内容*</span></td>
              <td id="note"> 
              <textarea class="smallarea"  name="bean.note" cols="70" rows="12" id="bean.note"></textarea>

			  </td>
            </tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" ><div class="content_bottom" id="pageBottomBorder_zlb">
	<table width="60%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span  class="norm"  style="width:66px;height:15px;padding-top:3px" onClick="doCommit1()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交回复</span>
       		</span>
       	</a></td>
       	<td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span  class="norm"  style="width:66px;height:15px;padding-top:3px" onClick="doCommit2()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">保存回复</span>
       		</span>
       	</a>
       	</td>
       	</tr>
      </table></div>
      </td>
  </tr>
</table>
</form></div>
<script type="text/javascript"> 
var has_load = false;
function editorLoad() {
if(has_load==false){
	var oFCKeditor = new FCKeditor( 'bean.note' ) ; 
	oFCKeditor.Height = 300 ; 
	oFCKeditor.Width  = 630 ; 
	
	oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	
	oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor.ReplaceTextarea(); 
	has_load = true;
	}
}
//--> 
</script> 
</div></div>
</body>
</html>
