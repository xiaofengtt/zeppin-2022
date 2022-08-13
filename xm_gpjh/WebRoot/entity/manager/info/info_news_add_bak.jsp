<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加公告</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<s:if test="#session.roleid==3&&#session.Logintype!=null" >
<script>
	var bSubmit=false
	var bLoaded=false;
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
			alert("请填写公告主题!!");
			document.getElementById("bean.title").focus();

			return false;
		}

		var plen =document.getElementsByName("person");
		var n=0;
		for(k=0;k<plen.length;k++){
			if(plen[k].checked){
			 n++;
			}
		}
		if(n==0){
			alert("请选择公告发送的人员范围");
			return false;
		}
		
		if(content.length <1){
			alert("公告内容为空!");
			return false;
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
	
	function listSelectSite() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'site_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.site_all.checked;
				}
			}
		}
		return true;
	}


	function listSelectGrade() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'grade_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.grade_all.checked;
				}
			}
		}
		return true;
	}
	
	
	function hideOther(par)
	{
		for(var i=1;i<5;i++)
		{
			if(document.getElementById("cnt"+String(i)) && document.getElementById("cnt"+String(i)).style.display == "block" && i!=par)
			{
				document.getElementById("cnt"+String(i)).style.display = "none"
			}
		}
	}
	
	function clickstu()
	{
		var stuObj;
		var siteObj;
		if(document.getElementById("studentS"))
		{
			stuObj = document.getElementById("studentS");
		}
		if(document.getElementById("subadmins"))
		{
				siteObj = document.getElementById("subadmins");
		}
		if(stuObj.checked){
			document.getElementById("site_button").disabled=false;
			document.getElementById("grade_button").disabled=false;
		}else if(siteObj.checked){
			document.getElementById("site_button").disabled=false;
			document.getElementById("grade_button").disabled=true;
		}else{
			document.getElementById("site_button").disabled=true;
			document.getElementById("grade_button").disabled=true;
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
		if("<s:property value='bean.enumConstByFlagIstop.name'/>"==window.document.getElementById("flagIstop_is").value)
			window.document.getElementById("flagIstop_is").checked = true;
		else
			window.document.getElementById("flagIstop_no").checked = true;
		if("<s:property value='bean.enumConstByFlagIsvalid.name'/>"==window.document.getElementById("flagIsvalid_no").value)
			window.document.getElementById("flagIsvalid_no").checked = true;
		else
			window.document.getElementById("flagIsvalid_is").checked = true;
		var str = "<s:property value='bean.scopeString'/>";
		var person =document.getElementsByName("person");
		for(var n=0;n<person.length;n++ ){
			if(str.indexOf(person[n].value)>=0)
			person[n].checked = true;
		}		
		clickstu();
		var sites = window.document.getElementsByName("site_id");
		for(var i=0;i<sites.length;i++){
			if(str.indexOf(sites[i].value)>=0)
				sites[i].checked = true;
		}
	
		var bean_id = "<s:property value='bean.id'/>";
		if(bean_id==null||bean_id.length<=0){
			window.document.info_news_add.action='/entity/information/peBulletin_addNotice.action';
		}else{
			window.document.info_news_add.action='/entity/information/peBulletin_editexe.action';
			window.document.getElementById('back').innerHTML="<a href='#' title='返回' class='button'><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=window.navigate('/entity/information/peBulletin.action') onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\"><span class='text'>返回</span></span></a>";
		}
	}
</script>
</s:if>
<s:elseif test="#session.roleid==2&&#session.Logintype!=null" >
<script>
var bSubmit=false
	var bLoaded=false;
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
			alert("请填写公告主题!!");
			document.getElementById("bean.title").focus();

			return false;
		}
		
		var dd =document.getElementsByName("person");
		var n=0;
		for(k=0;k<dd.length;k++){
			if(dd[k].checked){
			 n++;
			}
		}
		if(n==0){
			alert("请选择公告发送的人员范围");
			return false;
		}
		
		
		if(content.length <1){
			alert("公告内容为空!");
			return false;
		}
		window.document.info_news_add.action='/entity/information/peBulletin_addNotice.action';
		document.forms["info_news_add"].submit();		
	}
	
	
		function listSelectSite() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'site_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.site_all.checked;
				}
			}
		}
		return true;
		}
	
		function clickstu()
	{
		var stuObj;
		var siteObj;
		if(document.getElementById("studentS"))
		{
			stuObj = document.getElementById("studentS");
		}
		if(document.getElementById("subadmins"))
		{
				siteObj = document.getElementById("subadmins");
		}
		if(stuObj.checked){
			document.getElementById("site_button").disabled=false;
			document.getElementById("grade_button").disabled=false;
		}else if(siteObj.checked){
			document.getElementById("site_button").disabled=false;
			document.getElementById("grade_button").disabled=true;
		}else{
			document.getElementById("site_button").disabled=true;
			document.getElementById("grade_button").disabled=true;
		}
	}
	
	function showDiv(objID){
		var tempObj;
		if(document.getElementById(objID))	
		  tempObj = document.getElementById(objID);
		hideOther(objID.substring(3,objID.length));
		if(tempObj.style.display == "none")	tempObj.style.display = "block";
		else	tempObj.style.display = "none";
		
	}
	
	function hideOther(par)
	{
		for(var i=1;i<5;i++)
		{
			if(document.getElementById("cnt"+String(i)) && document.getElementById("cnt"+String(i)).style.display == "block" && i!=par)
			{
				document.getElementById("cnt"+String(i)).style.display = "none"
			}
		}
	}
	
	function listSelectGrade() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'grade_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.grade_all.checked;
				}
			}
		}
		return true;
	}
</script>
</s:elseif><s:else>
<script>
	var bSubmit=false
	var bLoaded=false;
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
			alert("请填写公告主题!!");
			document.getElementById("bean.title").focus();

			return false;
		}
		
		if(content.length <1){
			alert("公告内容为空!");
			return false;
		}
		window.document.info_news_add.action='/entity/information/peBulletin_addNotice.action';
		document.forms["info_news_add"].submit();		
	}
	
	function showDiv(objID){
		var tempObj;
		if(document.getElementById(objID))	
		  tempObj = document.getElementById(objID);
		hideOther(objID.substring(3,objID.length));
		if(tempObj.style.display == "none")	tempObj.style.display = "block";
		else tempObj.style.display = "none";
		
	}
	
	function hideOther(par)
	{
		for(var i=1;i<5;i++)
		{
			if(document.getElementById("cnt"+String(i)) && document.getElementById("cnt"+String(i)).style.display == "block" && i!=par)
			{
				document.getElementById("cnt"+String(i)).style.display = "none"
			}
		}
	}
	
	function listSelectGrade() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'grade_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.grade_all.checked;
				}
			}
		}
		return true;
	}
</script>
</s:else>
</head>
<body leftmargin="0" topmargin="0" class="scllbar" <s:if test="#session.Logintype==3&&#session.Logintype!=null" > onload="onload_edit();"</s:if> >
  <form name='info_news_add' method='post' class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">添加公告</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
<input type="hidden" name="bean.id" value="<s:property value='bean.id'/>"/>
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">公告主题*</span></td>
              <td width="626"> <input name="bean.title"   value="<s:property value='bean.title'/>" type="text" class="selfScale" id="bean.title" size="50"> 
                
            <!--  &nbsp;<img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='添加公告的主题';" onMouseOut="window.status='信息提示...'"> --> </td>
            </tr>
                        
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">是否置顶*</span></td>
              <td> 
              是<input type="radio" id="flagIstop_is" name="bean.enumConstByFlagIstop.name" value="是">
              否<input type="radio" id="flagIstop_no" name="bean.enumConstByFlagIstop.name" value="否" checked>
              <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='设置新闻置顶与否';" onMouseOut="window.status='信息提示...'"></td>
            </tr>
            
             <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">活动状态*</span></td>
              <td> 
              是<input type="radio" id="flagIsvalid_is"  name="bean.enumConstByFlagIsvalid.name" value="是" checked>
              否<input type="radio" id="flagIsvalid_no"  name="bean.enumConstByFlagIsvalid.name" value="否">
              <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='设置新闻活动状态';" onMouseOut="window.status='信息提示...'"></td>
            </tr>
            
           	<s:if test="#session.roleid==3&&#session.Logintype!=null">
			<tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">人员范围&nbsp;&nbsp;</span></td>
              <td>
                首页发布<input type="checkbox" value="index"  id="index" name="person"> &nbsp;&nbsp;&nbsp;&nbsp;
			  管理员<input type="checkbox" value="managers"  id="adminS" name="person"> &nbsp;&nbsp;&nbsp;&nbsp;
			  企业管理员<input type="checkbox" value="subadmins"  id="subadminS" name="person" onClick="clickstu()"> &nbsp;&nbsp;&nbsp;&nbsp;
			  学员<input type="checkbox" value="students" id="studentS" name="person" onClick="clickstu()">&nbsp;&nbsp;&nbsp;&nbsp;
			  <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布对象，可多选';" onMouseOut="window.status='信息提示...'">
			  </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">企业范围&nbsp;&nbsp;</span></td>
              <td>
			<input id="site_button" type="button" name="site" onClick="showDiv('cnt1')" value="设定范围" disabled>&nbsp;<img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布站点，可多选,如果不选则表示不对站点加以限制';" onMouseOut="window.status='信息提示...'">
			<div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <tr>
                    <td height="22"><input id="site_id_all" type="checkbox" name="site_all" value="" onclick=listSelectSite();>全选</td>
                </tr>       
                <s:iterator value="peSites" id="peSites" status="it">
                	<tr>
                    <td height="22">
                      <input id="site_id_check" type="checkbox" name="site_id" value="<s:property value="code"/>"><s:property value="name"/>
                    </td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt1')" value="确定"></td></tr>
                </table>
              </div>
              </td>
            </tr>
		</s:if><s:elseif test="#session.roleid==2&&#session.Logintype!=null">
				<tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">人员范围&nbsp;&nbsp;</span></td>
              <td>
			  二级企业管理员<input type="checkbox" value="subadmins"  id="subadminS" name="person" onClick="clickstu()"> &nbsp;&nbsp;&nbsp;&nbsp;
			  学员<input type="checkbox" value="students" id="studentS" name="person" onClick="clickstu()">&nbsp;&nbsp;&nbsp;&nbsp;
			  <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布对象，可多选';" onMouseOut="window.status='信息提示...'">
			  </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">企业范围&nbsp;&nbsp;</span></td>
              <td>
			<input id="site_button" type="button" name="site" onClick="showDiv('cnt1')" value="设定范围" disabled>&nbsp;<img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布站点，可多选,如果不选则表示不对站点加以限制';" onMouseOut="window.status='信息提示...'">
			<div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <tr>
                    <td height="22"><input id="site_id_all" type="checkbox" name="site_all" value="" onclick=listSelectSite();>全选</td>
                </tr>       
                <s:iterator value="peSites" id="peSites" status="it">
                	<tr>
                    <td height="22">
                      <input id="site_id_check" type="checkbox" name="site_id" value="<s:property value="code"/>"><s:property value="name"/>
                    </td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt1')" value="确定"></td></tr>
                </table>
              </div>
              </td>
            </tr>
		</s:elseif>
		<s:else>
			<input type="hidden"  name="site_id" value="<s:property value ="#session.peEnterprisemanager"/>"/>
			<input type="hidden"  name="person" value="students"/>
		</s:else>  
		 <tr valign="bottom" class="postFormBox"> 
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期&nbsp;&nbsp;</span></td>
              <td>
              <input id="grade_button" type="button" name="grade" onClick="showDiv('cnt2')" value="设定学期">&nbsp;<img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布批次，可多选,如果不选则表示不对批次加以限制';" onMouseOut="window.status='信息提示...'">
              <div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="grade_id_all" type="checkbox" name="grade_all" value="" onclick=listSelectGrade();>全选</td>
              </tr>
              
              	<s:iterator value="peGrades">
                	<tr>
                    <td height="22"><input id="grade_id_check" type="checkbox" name="grade_id" value="<s:property value="batchCode"/>"><s:property value="name"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt2')" value="确定"></td></tr>
                </table>
              </div>
              </td>
            </tr>

             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">公告内容*</span></td>
              <td> 
              <textarea class="smallarea"  name="bean.note" cols="70" rows="12" id="bean.note"><s:property value='bean.note'/></textarea>

			  </td>
            </tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
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
//--> 
</script> 
<script>bLoaded=true;</script>

</body>

</html>



