<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改任务</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css">
<script type="text/javascript" src="/js/calendar/calendar.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
<script>
	function doCommit() {		
		var content;
		if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("peJob.note"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
		
		if(isNull(document.getElementById("peJob.name").value)){ 
		}else{
			alert("请填任务名称!");
			document.getElementById("peJob.name").focus();

			return false;
		}
		
		if(document.getElementById("peJob.startDate").value=="") {
				alert("开始时间不能为空!");
				return false;
			}
			
		if(document.getElementById("peJob.finishDate").value=="") {
				alert("结束时间不能为空!");
				return false;
			}
			
		if(!checkRight(document.getElementById("peJob.startDate").value,document.getElementById("peJob.finishDate").value)) {
				alert("开始结束时间不合理！");
				return false;
			}
	
		var oEditor = FCKeditorAPI.GetInstance('peJob.note') ;
    	var acontent=oEditor.GetXHTML();
       	if(acontent.length > 2000)
			{
				alert("任务备注内容过多，请重新填写!");
				return false;
			}
	
		var plen =document.getElementsByName("cb_id");
		var n=0;
		for(k=0;k<plen.length;k++){
			if(plen[k].checked){
			 n++;
			}
		}
		if(n==0){
			alert("请选择任务承办单位！");
			return false;
		}
		var s=document.getElementById('job');
		s.submit();
	}
	function checkRight(startDateStr, finishDateStr) {
			var startArr = startDateStr.split("-");
			var finishArr = finishDateStr.split("-");
			var startDate = new Date(startArr[0],startArr[1],startArr[2]);
			var finishDate = new Date(finishArr[0],finishArr[1],finishArr[2]);
			if(startDate > finishDate)
				return false;
			return true;
		}
	function listSelectCB() {
		var form = document.forms['job'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.cb_all.checked;
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
	
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
  <form name='job' method='post' id='job' action="/entity/information/prJobUnit_editexeJob.action" class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">修改任务</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
		<input type="hidden" name="peJob.id" value="<s:property value='peJob.id'/>"/>
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">任务名称:*&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td width="626"> <input name="peJob.name"   value="<s:property value='peJob.name'/>" type="text" class="selfScale"  size="50" maxlength='20'> 
            </tr>
                        
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">任务重要性:*</span></td>
              <td> 
           <input type="radio" id="peJob.enumConstByFkJobPriority.name1" name="peJob.enumConstByFkJobPriority.code" value="1803"  <s:if test="peJob.enumConstByFkJobPriority.code == 1803">checked</s:if> >  不重要 
            <input type="radio" id="peJob.enumConstByFkJobPriority.name2" name="peJob.enumConstByFkJobPriority.code" value="1801" <s:if test="peJob.enumConstByFkJobPriority.code == 1801">checked</s:if> >  重要 
             <input type="radio" id="peJob.enumConstByFkJobPriority.name3" name="peJob.enumConstByFkJobPriority.code" value="1802" <s:if test="peJob.enumConstByFkJobPriority.code == 1802">checked</s:if> > 一般 
            </tr>
            <tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">开始时间:*&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td width="626">
										<input type=text name="peJob.startDate" value="<s:property value="peJob.startDate"/>" id="startDate" class=selfScale readonly>
										<img src="/js/calendar/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">结束时间:*&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td width="626">
										<input type="text" name="peJob.finishDate"  value="<s:property value="peJob.finishDate"/>" id=finishDate class=selfScale readonly>
										<img src="/js/calendar/img.gif" width="20" height="14"
											id="f_trigger_c"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
            
			<tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">承办单位:*&nbsp;&nbsp;&nbsp;&nbsp;</span><input id="CB_button" type="button" name="cb" onClick="showDiv('cnt2')" value="请选择" style="position:absolute;left:270px;font-size:12px;height:20;background-color:#ece9d8;border-width:1;">&nbsp;</td>
              <td>
			  
              <div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="cb_id_all" type="checkbox" name="cb_all" value="" onclick=listSelectCB();>全选</td>
              </tr>
              	<s:iterator value="cbScop"  id="cb_">
              	
                	<tr> 
                    <td height="22">
                    <s:set name="flag" value="false"/>
                    <s:iterator value="units" id="unit">
                    	<s:if test="#unit.peUnit.id==#cb_[0]">
                    		<s:set name="flag" value="true"/>
                    	</s:if>
                    </s:iterator>
                    <input id="cb_id_check" type="checkbox" name="cb_id" value="<s:property value="#cb_[0]"/>" <s:if test="#flag">checked</s:if> ><s:property value="#cb_[1]"/></td>
                    
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onclick="showDiv('cnt2')" value="确定" style="font-size:12px;width:46px;height:23px;padding-top:3px"></td></tr>
                </table>
              </div>
			  </td>
            </tr>
		   
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">任务备注:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td> 
              <textarea class="smallarea"  name="peJob.note" cols="70" rows="12" id="peJob.note"><s:property value='peJob.note'/></textarea>

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
    Calendar.setup({
        inputField     :    "peJob.startDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    
    Calendar.setup({
        inputField     :    "peJob.finishDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>

<script type="text/javascript"> 
var oFCKeditor = new FCKeditor( 'peJob.note' ) ; 
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



