<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加公文</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script>
	var bSubmit=false
	var bLoaded=false;
	var bean_id = "<s:property value='bean.id'/>";
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
		var m2 = document.getElementsByName("sitemanager_id");
		for(var i=0;i<m2.length;i++){
			if(m2[i].checked){
				back = false;
				break;
			}
		}		
		if(back){
			alert("请指定接收人后再发送！");
			return;
		}
		if(bean_id==null||bean_id.length<=0){
			document.info_news_add.action="/entity/information/peDocumentOutbox_sendDocument.action";
		}else{
			document.info_news_add.action="/entity/information/peDocumentOutbox_editSend.action";
		}
		document.forms["info_news_add"].submit();		
	}
	function doCommit2() {
		if(!doCommit()) return false;
		if(bean_id==null||bean_id.length<=0){
			document.info_news_add.action="/entity/information/peDocumentOutbox_addDocument.action";
		}else{
			document.info_news_add.action="/entity/information/peDocumentOutbox_editDocument.action";
		}
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
		var managers = window.document.getElementsByName("manager_id");
		for(var i=0;i<managers.length;i++){
			if(msg.indexOf(managers[i].value)>=0)
				managers[i].checked = true;
		}
		var sitemanagers = window.document.getElementsByName("sitemanager_id");
		for(var i=0;i<sitemanagers.length;i++){
			if(msg.indexOf(sitemanagers[i].value)>=0)
				sitemanagers[i].checked = true;
		}
		if(bean_id==null||bean_id.length<=0){
		}else{
			window.document.getElementById('back').innerHTML="<a href='#' title='返回' class='button'><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=window.navigate('/entity/information/peDocumentOutbox.action') onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\"><span class='text'>返回</span></span></a>";
		}
		if(msg.indexOf("readonly")>=0){
			window.document.getElementById('managers_button').style.display = "none";
			window.document.getElementById('sitemanagers_button').style.display = "none";
			window.document.getElementById('pageBottomBorder_zlb').innerHTML="<a href='#' title='返回' class='button'><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=window.history.back() onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\"><span class='text'>返回</span></span></a>";
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

<body leftmargin="0" topmargin="0" class="scllbar" onload="onload_edit();setreceiver();">
  <form name='info_news_add' method='post' class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">公文</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
<input type="hidden" name="bean.id" value="<s:property value='bean.id'/>"/>
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">公文主题<s:if test='msg==null||msg.indexOf("readonly")<0'>*</s:if></span></td>
              <td > <input name="bean.title" <s:if test='msg!=null&&msg.indexOf("readonly")>=0'>readonly  style="border:0"</s:if><s:else>onMouseOver="window.status='添加公文的主题';" onMouseOut="window.status='信息提示...'"</s:else> value="<s:property value='bean.title'/>" type="text" class="selfScale" id="bean_title" > 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">接&nbsp;&nbsp;收&nbsp;&nbsp;人<s:if test='msg==null||msg.indexOf("readonly")<0'>*</s:if></span></td>
              <td ><input type="text" class="selfScale" width="400" readonly id="receiver" <s:if test='msg!=null&&msg.indexOf("readonly")>=0'>style="border:0"</s:if><s:else>onMouseOver="window.status='点击后面的按钮选择所要接收公文的管理员';" onMouseOut="window.status='信息提示...'"</s:else> >
			&nbsp;&nbsp;<input id="managers_button" type="button" name="managers" onClick="showDiv('cnt1')" value="管理员" onMouseOver="window.status='请选择所要接收公文的管理员名称';" onMouseOut="window.status='信息提示...'">
			<div id="cnt1" style="width:200px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <s:iterator value="peManagers" status="row">
              <!--  	<s:if test="(#row.index)%2==0"><tr></s:if>
                    <td height="22"><input id="manager_check_<s:property value='#row.index'/>" type="checkbox"  onclick="setreceiver();" name="manager_id" value="<s:property value="ssoUser.id"/>">
                    	<INPUT type="hidden" id="name_manager_check_<s:property value='#row.index'/>"  value="<s:property value='name'/>" /><s:property value="name"/></td>
                    <s:if test="(#row.index)%2==1"></tr></s:if>
                         -->
                 <tr>
						<td height="22"><input id="manager_check_<s:property value='#row.index'/>" type="checkbox"  onclick="setreceiver();" name="manager_id" value="<s:property value="ssoUser.id"/>">
                    	<INPUT type="hidden" id="name_manager_check_<s:property value='#row.index'/>"  value="<s:property value='name'/>" /><s:property value="name"/></td>                 
                 </tr>        
                </s:iterator>
             
                 
                <tr>
                	<td height="22">
                		<input id="manager_id_all" type="checkbox" name="manager_all" value="" onclick="listSelectM();setreceiver();">全选</td>
                 </tr>
                 <tr>   
                    <td>
                    	<input type='button' onclick="showDiv('cnt1')" value="确定">
                    </td>
                </tr>
                </table>
              </div>&nbsp;&nbsp;
            <input id="sitemanagers_button" type="button" name="sitemanagers" onClick="showDiv('cnt2')" value="分站管理员" onMouseOver="window.status='请选择所要接收公文的站点管理员名称';" onMouseOut="window.status='信息提示...'">
			<div id="cnt2" style="width:200px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">                
                <s:iterator value="siteManagers" status="row"> 
                <!-- <s:if test="(#row.index)%2==0"><tr></s:if>
                    <td height="22"><input id="sitemanager_check_<s:property value='#row.index'/>" type="checkbox" name="sitemanager_id" onclick="setreceiver();"  value="<s:property value="ssoUser.id"/>">
                    	<INPUT type="hidden" id="name_sitemanager_check_<s:property value='#row.index'/>" value="<s:property value='name'/>" /><s:property value="name"/></td>
                    <s:if test="(#row.index)%2==1"></tr></s:if>
                 -->	
                 <tr>
                    <td height="22"><input id="sitemanager_check_<s:property value='#row.index'/>" type="checkbox" name="sitemanager_id" onclick="setreceiver();"  value="<s:property value="ssoUser.id"/>">
                    	<INPUT type="hidden" id="name_sitemanager_check_<s:property value='#row.index'/>" value="<s:property value='name'/>" /><s:property value="name"/></td>
                  </tr>
                </s:iterator>
                <tr>
                	<td height="22">
                		<input id="manager_id_all" type="checkbox" name="sitemanager_all" value="" onclick="listSelectSiteM();setreceiver();">全选</td>
                 </tr>
                 <tr>   
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
              <td valign="top"><span class="name">公文内容<s:if test='msg==null||msg.indexOf("readonly")<0'>*</s:if></span></td>
              <td id="note"> 
              <s:if test='msg!=null&&msg.indexOf("readonly")>=0'>
              <s:property value="bean.note" escape="false"/>
			</s:if><s:else>
              <textarea class="smallarea"  name="bean.note" cols="70" rows="12" id="bean.note"><s:property value='bean.note'/></textarea>
			</s:else>
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
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit1()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">发送</span>
       		</span>
       	</a></td>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit2()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">保存</span>
       		</span>
       	</a>
       	</td>
       <td  align="center" id="back" valign="middle">
       	</td> 
       	</tr>
      </table></td>
  </tr>
</table>
</form>
<script type="text/javascript"> 
<s:if test='msg==null||msg.indexOf("readonly")<0'>
var oFCKeditor = new FCKeditor( 'bean.note' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; 
</s:if>
//--> 
</script> 
<script>bLoaded=true;</script>

</body>

</html>



