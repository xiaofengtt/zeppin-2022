<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改会议通知</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css">
<script type="text/javascript" src="/js/calendar/calendar.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
	<script>
		function pageGuarding(){
	
			
			if(document.meeting.name.value.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("会议名称不能为空!");
				document.meeting.name.focus();
				return false;
			}
			if(document.meeting.meeting_date.value == "") {
				alert("会议时间不能为空!");
				document.meeting.meeting_date.focus();
				return false;
			}
			
		    if(document.meeting.place.value.length == "")
			{
				alert("会议地点不能为空!");
				document.meeting.place.focus();
				return false;
			}
		var cb_len =document.getElementsByName("person");
		var n=0;
		for(k=0;k<cb_len.length;k++){
			if(cb_len[k].checked){
			 n++;
			}
		}
		if(n==0){
			alert("请指定回执填报单位！");
			return false;
		}	
		var cb2_len =document.getElementsByName("person2");
		var n2=0;
		for(j=0;j<cb2_len.length;j++){
			if(cb2_len[j].checked){
			 n2++;
			}
		}
		if(n2==0){
			alert("请指定资源上传单位！");
			return false;
		}	
		var s=document.getElementById('meeting');
		s.submit();
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
	function clickZXST()
	{
		var stObj;
		if(document.getElementById("ZXSTS"))
		{
			stObj = document.getElementById("ZXSTS");
		}
		if(stObj.checked){
			document.getElementById("zxst_button").style.display="block";
		}else{
			document.getElementById("zxst_button").style.display="none";
		}
	}
	function clickQTST()
	{
		var stObj;
		if(document.getElementById("QTSTS"))
		{
			stObj = document.getElementById("QTSTS");
		}
		if(stObj.checked){
			document.getElementById("qtst_button").style.display="block";
		}else{
			document.getElementById("qtst_button").style.display="none";
		}
	}
	function listSelectCB() {
		var form = document.forms['meeting'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.cb_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectZXST() {
		var form = document.forms['meeting'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'zxst_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.zxst_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectQTST() {
		var form = document.forms['meeting'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'qtst_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.qtst_all.checked;
				}
			}
		}
		return true;
	}
	function clickCB2()
	{
		var cbObj;
		if(document.getElementById("CBS2"))
		{
			cbObj = document.getElementById("CBS2");
		}
		if(cbObj.checked){
			document.getElementById("cb2_button").style.display="block";
		}else{
			document.getElementById("cb2_button").style.display="none";
		}
	}
	function clickZXST2()
	{
		var stObj;
		if(document.getElementById("ZXSTS2"))
		{
			stObj = document.getElementById("ZXSTS2");
		}
		if(stObj.checked){
			document.getElementById("zxst2_button").style.display="block";
		}else{
			document.getElementById("zxst2_button").style.display="none";
		}
	}
	function clickQTST2()
	{
		var stObj;
		if(document.getElementById("QTSTS2"))
		{
			stObj = document.getElementById("QTSTS2");
		}
		if(stObj.checked){
			document.getElementById("qtst2_button").style.display="block";
		}else{
			document.getElementById("qtst2_button").style.display="none";
		}
	}
	function listSelectCB2() {
		var form = document.forms['meeting'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.cb2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectZXST2() {
		var form = document.forms['meeting'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'zxst2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.zxst2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectQTST2() {
		var form = document.forms['meeting'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'qtst2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.qtst2_all.checked;
				}
			}
		}
		return true;
	}
	function showDiv(objID){
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		if(tempObj.style.display == 'none') {
			tempObj.style.display = 'block';
		}
		else {
			tempObj.style.display = 'none';
		}
	}
function onload_edit(){
		var str = "<s:property value='peMeeting.scopstring'/>";
		var person =document.getElementsByName("person");
		var person2 = document.getElementsByName("person2");
		for(var n=0;n<person.length;n++ ){
			if(str.indexOf(person[n].value)>=0)
			
			person[n].checked = true;
		}	
		for(var i=0;i<person2.length;i++){
		if(str.indexOf(person2[i].value)>0)
		person2[i].checked = true;
		}	
		clickCB();
		clickZXST();
		clickQTST();
		clickCB2();
		clickZXST2();
		clickQTST2();
		
		var cbs = window.document.getElementsByName("cb_id");
		for(var i=0;i<cbs.length;i++){
			if(str.indexOf(cbs[i].value)>=0){
				cbs[i].checked = true;
				}
		}
		var zxsts = window.document.getElementsByName("zxst_id");
		for(var i=0;i<zxsts.length;i++){
			if(str.indexOf(zxsts[i].value)>=0){
				zxsts[i].checked = true;
				}
		}
	var qtsts = window.document.getElementsByName("qtst_id");
		for(var i=0;i<qtsts.length;i++){
			if(str.indexOf(qtsts[i].value)>=0){
				qtsts[i].checked = true;
				}
		}
		var cbs2 = window.document.getElementsByName("cb2_id");
		for(var i=0;i<cbs2.length;i++){
			if(str.indexOf(cbs2[i].value)>=0){
				cbs2[i].checked = true;
				}
		}
		var zxsts2 = window.document.getElementsByName("zxst2_id");
		for(var i=0;i<zxsts2.length;i++){
			if(str.indexOf(zxsts2[i].value)>=0){
				zxsts2[i].checked = true;
				}
		}
	var qtsts2 = window.document.getElementsByName("qtst2_id");
		for(var i=0;i<qtsts2.length;i++){
			if(str.indexOf(qtsts2[i].value)>=0){
				qtsts2[i].checked = true;
				}
		}
		var bean_id = "<s:property value='peMeeting.id'/>";
	}
</script>		
</head>
<body leftmargin="0" topmargin="0" class="scllbar" onload="onload_edit();" >
  <form name='meeting'action="/entity/information/peMeeting_editexe.action" method='post' class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">修改会议通知</div></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
<input type="hidden" name="peMeeting.id" value="<s:property value='peMeeting.id'/>"/>
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="200" nowrap><span class="name">会议名称*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626"> <input name="name"   value="<s:property value='peMeeting.name'/>" type="text" class="selfScale" id="peMeeting.name" maxlength="30" size="50"> 
            </tr>
            <tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">会议时间*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
									<td width="626">
										<input type=text name="meeting_date" value="<s:property value='peMeeting.meetingDate'/>" id="peMeeting.meetingDate" class=selfScale size="50" readonly>
										<img src="/js/calendar/img.gif" width="20" height="14"
											id="f_ff"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="200" nowrap><span class="name">举办地点*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626"> <input name="place"   value="<s:property value='peMeeting.place'/>" type="text" class="selfScale" id="peMeeting.place" maxlength="30"  size="50"> 
            </tr>
          <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">指定回执填报单位:*&nbsp;</span></td> 
              <td>
			  评审专家<input type="checkbox" value="ps"  id="PSS" name="person"> &nbsp;&nbsp;&nbsp;&nbsp;
			  承办单位<input type="checkbox" value="cb" id="CBS" name="person" onClick="clickCB()">&nbsp;&nbsp;&nbsp;&nbsp;
			  中西部师范处<input type="checkbox" value="zxst" id="ZXSTS" name="person" onClick="clickZXST()">&nbsp;&nbsp;&nbsp;&nbsp;
			  其他师范处<input type="checkbox" value="qtst" id="QTSTS" name="person" onClick="clickQTST()">&nbsp;&nbsp;&nbsp;&nbsp;
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
               <input id="zxst_button" type="button" name="zxst" onClick="showDiv('cnt2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="zxst_id_all" type="checkbox" name="zxst_all" value="" onclick=listSelectZXST();>全选</td>
              </tr>
              	<s:iterator value="stScop"  id="zxst_">
                	<tr>
                    <td height="22"><input id="zxst_id_check" type="checkbox" name="zxst_id" value="<s:property value="#zxst_[0]"/>"><s:property value="#zxst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
		   <input id="qtst_button" type="button" name="qtst" onClick="showDiv('cnt3')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt3" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="qtst_id_all" type="checkbox" name="qtst_all" value="" onclick=listSelectQTST();>全选</td>
              </tr>
              	<s:iterator value="qtstScop"  id="qtst_">
                	<tr>
                    <td height="22"><input id="qtst_id_check" type="checkbox" name="qtst_id" value="<s:property value="#qtst_[0]"/>"><s:property value="#qtst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt3')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
			  </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">指定上传资源单位:*&nbsp;</span>&nbsp;</td> 
              <td>
			  评审专家<input type="checkbox" value="ps2"  id="PSS2" name="person2"> &nbsp;&nbsp;&nbsp;&nbsp;
			  承办单位<input type="checkbox" value="cb2" id="CBS2" name="person2" onClick="clickCB2()">&nbsp;&nbsp;&nbsp;&nbsp;
			  中西部师范处<input type="checkbox" value="zxst2" id="ZXSTS2" name="person2" onClick="clickZXST2()">&nbsp;&nbsp;&nbsp;&nbsp;
			  其他师范处<input type="checkbox" value="qtst2" id="QTSTS2" name="person2" onClick="clickQTST2()">&nbsp;&nbsp;&nbsp;&nbsp;
			  师范司项目办<input type="checkbox" value="sf2" id="SFS2" name="person2" >&nbsp;&nbsp;&nbsp;&nbsp;
			  项目执行办<input type="checkbox" value="xm2" id="XMS2" name="person2" >&nbsp;&nbsp;&nbsp;&nbsp;
		 <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='选择发布对象，可多选';" onMouseOut="window.status='信息提示...'">
			  
			  <input id="CB2_button" type="button" name="cb2" onClick="showDiv('cnt4')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt4" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="cb2_id_all" type="checkbox" name="cb2_all" value="" onclick=listSelectCB2();>全选</td>
              </tr>
              	<s:iterator value="cbScop"  id="cb2_">
                	<tr>
                    <td height="22"><input id="cb2_id_check" type="checkbox" name="cb2_id" value="<s:property value="#cb2_[0]"/>"><s:property value="#cb2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt4')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
               <input id="zxst2_button" type="button" name="zxst2" onClick="showDiv('cnt5')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt5" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="zxst2_id_all" type="checkbox" name="zxst2_all" value="" onclick=listSelectZXST2();>全选</td>
              </tr>
              	<s:iterator value="stScop"  id="zxst_">
                	<tr>
                    <td height="22"><input id="zxst2_id_check" type="checkbox" name="zxst2_id" value="<s:property value="#zxst_[0]"/>"><s:property value="#zxst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt5')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
		   <input id="qtst2_button" type="button" name="qtst2" onClick="showDiv('cnt6')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;
              <div id="cnt6" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="qtst2_id_all" type="checkbox" name="qtst2_all" value="" onclick=listSelectQTST2();>全选</td>
              </tr>
              	<s:iterator value="qtstScop"  id="qtst_">
                	<tr>
                    <td height="22"><input id="qtst2_id_check" type="checkbox" name="qtst2_id" value="<s:property value="#qtst_[0]"/>"><s:property value="#qtst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt6')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">会议内容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td> 
              <textarea class="smallarea"  name="note" cols="70" rows="12" id="peMeeting.note"><s:property value='peMeeting.note'/></textarea>
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
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" " onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'" onclick="javascript:pageGuarding();">
       			<span class="text">提交</span>       			
       		</span>
       	</a>
       	</td>
       <td  align="center"  valign="middle" >
       <a href='#' title='返回' class='button' ><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick="window.navigate('/entity/information/peMeeting.action')" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class='text'>返回</span></span></a>
       	</td>            
       	</tr>
      </table></td>
  </tr>
</table>
</form>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "peMeeting.meeting_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d--%H:%M:%S",       // format of the input field
        button         :    "f_ff",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    
</script>
<script type="text/javascript"> 


var oFCKeditor = new FCKeditor( 'note' ) ; 
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



