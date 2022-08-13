<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改公告</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<script>
<!--平台管理员 -->
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

	
		var dd =document.getElementsByName("person")
		var n=0;
		for(k=0;k<dd.length;k++){
			if(dd[k].checked){
			 n++;
			}
		}
		if(n==0){
			alert("请选择公告发送的范围");
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
		if(tempObj.style.display == "none")	tempObj.style.display = "block";
		else	tempObj.style.display = "none";
		
	}
	
	function listSelectCB() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.cb_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectST() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'st_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.st_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectST2() {
		var form = document.forms['info_news_add'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'st2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.st2_all.checked;
				}
			}
		}
		return true;
	}
	function clickCB()
	{
		var cbObj;
		if(document.getElementById("CBS"))
		{
			cbObj = document.getElementById("CBS");
		}
		if(cbObj.checked){
			document.getElementById("cb_button").style.display="block";
		}else{
			document.getElementById("cb_button").style.display="none";
		}
	}
	function clickST()
	{
		var stObj;
		if(document.getElementById("ZXSTS"))
		{
			stObj = document.getElementById("ZXSTS");
		}
		if(stObj.checked){
			document.getElementById("st_button").style.display="block";
		}else{
			document.getElementById("st_button").style.display="none";
		}
	}
	function clickST2()
	{
		var qtstObj;
		if(document.getElementById("QTSTS"))
		{
			qtstObj = document.getElementById("QTSTS");
		}
		if(qtstObj.checked){
			document.getElementById("st2_button").style.display="block";
		}else{
			document.getElementById("st2_button").style.display="none";
		}
	}
	function onload_edit(){
		if(!<s:property value='cando'/>){
			alert("对不起,你无权修改!");
			window.history.back();
		}
	
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
		clickCB();
		clickST();
		clickST2();
		var cbs = window.document.getElementsByName("cb_id");
		for(var i=0;i<cbs.length;i++){
			if(str.indexOf(cbs[i].value)>=0){
				cbs[i].checked = true;
				}
		}
		var sts = window.document.getElementsByName("st_id");
		for(var i=0;i<sts.length;i++){
			if(str.indexOf(sts[i].value)>=0){
				sts[i].checked = true;
				}
		}
	var sts2 = window.document.getElementsByName("st2_id");
		for(var i=0;i<sts2.length;i++){
			if(str.indexOf(sts2[i].value)>=0){
				sts2[i].checked = true;
				}
		}
		var bean_id = "<s:property value='bean.id'/>";
	}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar"  onload="onload_edit();" >
  <form name='info_news_add' method='post' action="/entity/information/peBulletin_editexe.action" class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">修改通知公告</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
<input type="hidden" name="bean.id" value="<s:property value='bean.id'/>"/>
<input type="hidden" id="cando" name="cando" value="<s:property value='cando'/>"/>
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
              是<input type="radio" id="flagIstop_is" name="bean.enumConstByFlagIstop.name" value="置顶">
              否<input type="radio" id="flagIstop_no" name="bean.enumConstByFlagIstop.name" value="不置顶" checked>
              <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='设置新闻置顶与否';" onMouseOut="window.status='信息提示...'"></td>
            </tr>
            
             <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">活动状态*</span></td>
              <td> 
              是<input type="radio" id="flagIsvalid_is"  name="bean.enumConstByFlagIsvalid.name" value="是" checked>
              否<input type="radio" id="flagIsvalid_no"  name="bean.enumConstByFlagIsvalid.name" value="否">
              <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='设置新闻活动状态';" onMouseOut="window.status='信息提示...'"></td>
            </tr>
            
			<tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">人员范围*</span></td>
              <td>
			  评审专家<input type="checkbox" value="ps"  id="PSS" name="person"> &nbsp;&nbsp;&nbsp;&nbsp;
			  承办单位<input type="checkbox" value="cb" id="CBS" name="person" onClick="clickCB()">&nbsp;&nbsp;&nbsp;&nbsp;
			  中西部师范处<input type="checkbox" value="zxst" id="ZXSTS" name="person" onClick="clickST()">&nbsp;&nbsp;&nbsp;&nbsp;
			  其他师范处<input type="checkbox" value="qtst" id="QTSTS" name="person" onClick="clickST2()">&nbsp;&nbsp;&nbsp;&nbsp;
			  师范司项目办<input type="checkbox" value="sf" id="SFS" name="person" >&nbsp;&nbsp;&nbsp;&nbsp;
			  项目执行办<input type="checkbox" value="xm" id="XMS" name="person" >&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布对象，可多选';" onMouseOut="window.status='信息提示...'">
			  
			  <input id="CB_button" type="button" name="cb" onClick="showDiv('cnt1')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="cb_id_all" type="checkbox" name="cb_all" value="" onclick=listSelectCB();>全选</td>
              </tr>
              	<s:iterator value="cbScop"  id="cb_">
                	<tr>
                    <td height="22"><input id="cb_id_check" type="checkbox" name="cb_id" value="<s:property value="#cb_[0]"/>"><s:property value="#cb_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt1')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
               <input id="st_button" type="button" name="st" onClick="showDiv('cnt2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="st_id_all" type="checkbox" name="st_all" value="" onclick=listSelectST();>全选</td>
              </tr>
              	<s:iterator value="stScop"  id="st_">
                	<tr>
                    <td height="22"><input id="st_id_check" type="checkbox" name="st_id" value="<s:property value="#st_[0]"/>"><s:property value="#st_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
		   <input id="st2_button" type="button" name="st2" onClick="showDiv('cnt3')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt3" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="st2_id_all" type="checkbox" name="st2_all" value="" onclick=listSelectST2();>全选</td>
              </tr>
              	<s:iterator value="qtstScop"  id="qtst_">
                	<tr>
                    <td height="22"><input id="st2_id_check" type="checkbox" name="st2_id" value="<s:property value="#qtst_[0]"/>"><s:property value="#qtst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt3')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
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
      	<td width="120">&nbsp;</td>
       <td align="center" valign="middle" width="80">
       	<a href="#" title="提交" class='button'>
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
       		</span>
       	</a> 
       	</td>
       <td  align="center"  valign="middle" width="150">
       <a href='#' title='返回' class='button' ><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick="window.navigate('/entity/information/peBulletin.action')" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class='text'>返回</span></span></a>
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



