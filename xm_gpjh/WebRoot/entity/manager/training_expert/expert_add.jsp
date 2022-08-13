<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.entity.bean.PeTrainExpert"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加教学培训专家</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script type="text/javascript">
	function doCommit(){
	
	var content;
	var flag = true;
			if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("note"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
	
		var name = document.getElementById("name").value;
		if(name.length<1){
			alert("姓名不能为空!");
			flag = false;
			name.focus();
			return false;
		}
		var workplace = document.getElementById("workplace").value;
		if(workplace.length<1){
			alert("工作单位不能为空!");
			flag = false;
			workplace.focus();
			return false;
		}
		var types = document.getElementById("workplace").value;
		if(types==""){
			window.alert("请选择新闻类型!");
			flag = false;
			window.focus();
			return false;
		}
		
		//alert(content);
		//alert(content.length);
		//return false;
		if(flag==true){
			var action="/entity/information/peInfoNews_addInfoNews.action";
			document.addNews.action=action;
			document.addNews.submit();
		}
	}
	function checkLength(obj,len){
		if(obj.value.length > (len)){
			alert('此字段最多只能输入'+len+'个字符');
			obj.value = obj.value.substr(0,len);
			return false;
		}
	}
</script>
<body leftmargin="0" topmargin="0" class="scllbar" >
  <form name ="addNews" method="post" enctype="multipart/form-data">
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">添加教学培训专家</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">姓名*</span></td>
              <td width="626"> <input name="name" type="text" class="selfScale" id="name" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">工作单位*</span></td>
              <td width="626"> <input name="workplace" type="text" class="selfScale" id="workplace" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox">
            	<td valign="bottom" width="90" nowrap><span class="name">性别</span></td>
              <td width="626"><SELECT name="enumConstByFkGender.id" id="enumConstByFkGender.id">
              	<option value="">请选择性别...</option>
              	<s:if test="newList.size() > 0">
              	<s:iterator value="newList" status="types">
              		<option value="<s:property value='id' />"><s:property value="name"/></option>
              	</s:iterator>
              	</s:if>
              </SELECT>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox">
            	<td valign="bottom" width="90" nowrap><span class="name">学科</span></td>
              <td width="626"><SELECT name="peSubject.id">
              	<option value="">请选择学科...</option>
              	<s:if test="newList.size() > 0">
              	<s:iterator value="newList" status="types">
              		<option value="<s:property value='id' />"><s:property value="name"/></option>
              	</s:iterator>
              	</s:if>
              </SELECT>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox">
            	<td valign="bottom" width="90" nowrap><span class="name">省份</span></td>
              <td width="626"><SELECT name="peProvince.id">
              	<option value="">请选择省份...</option>
              	<s:if test="newList.size() > 0">
              	<s:iterator value="newList" status="types">
              		<option value="<s:property value='id' />"><s:property value="name"/></option>
              	</s:iterator>
              	</s:if>
              </SELECT>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox">
            	<td valign="bottom" width="90" nowrap><span class="name">状态*</span></td>
              <td width="626"><SELECT name="enumConstByFkStatus.id">
              	<option value="">请选择状态...</option>
              	<s:if test="newList.size() > 0">
              	<s:iterator value="newList" status="types">
              		<option value="<s:property value='id' />"><s:property value="name"/></option>
              	</s:iterator>
              	</s:if>
              </SELECT>
              </td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">手机</span></td>
              <td width="626"> <input name="telephone" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">民族</span></td>
              <td width="626"> <input name="folk" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">出生年月</span></td>
              <td width="626"> <input name="birthdate" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">所学专业</span></td>
              <td width="626"> <input name="major" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">email</span></td>
              <td width="626"> <input name="email" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">办公电话</span></td>
              <td width="626"> <input name="officePhone" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">年龄</span></td>
              <td width="626"> <input name="age" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">学历</span></td>
              <td width="626"> <input name="education" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">政治面貌</span></td>
              <td width="626"> <input name="politics" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">职务</span></td>
              <td width="626"> <input name="zhiwu" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">职称</span></td>
              <td width="626"> <input name="zhicheng" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">研究专长</span></td>
              <td width="626"> <input name="researchArea" type="textarea" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">教学专长</span></td>
              <td width="626"> <input name="trainingArea" type="textarea" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">身份证号</span></td>
              <td width="626"> <input name="idcard" type="textarea" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">通讯地址</span></td>
              <td width="626"> <input name="address" type="textarea" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">邮编</span></td>
              <td width="626"> <input name="zip" type="textarea" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">住宅电话</span></td>
              <td width="626"> <input name="homePhone" type="textarea" class="selfScale" id="title" size="50" maxlength="250"></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">传真</span></td>
              <td width="626"> <input name="fax" type="textarea" class="selfScale" id="title" size="50" maxlength="250" ></td> 
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">入库时间</span></td>
              <td width="626"> <input name="inputDate" type="textarea" class="selfScale" id="title" size="50" maxlength="250""></td> 
            </tr>
                        
       
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">个人简历</span></td>
              <td> 
              	<textarea class="smallarea" name="personalResume" cols="70" rows="12" id="personalResume"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">教育教学成果</span></td>
              <td> 
              	<textarea class="smallarea" name="trainingResult" cols="70" rows="12" id="trainingResult"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">教师培训经验</span></td>
              <td> 
              	<textarea class="smallarea" name="trainingExperience" cols="70" rows="12" id="trainingExperience"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">其他需要说明事项</span></td>
              <td> 
              	<textarea class="smallarea" name="otherItems" cols="70" rows="12" id="otherItems"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">所在单位意见</span></td>
              <td> 
              	<textarea class="smallarea" name="unitComment" cols="70" rows="12" id="unitComment"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">推荐部门意见</span></td>
              <td> 
              	<textarea class="smallarea" name="recommendComment" cols="70" rows="12" id="recommendComment"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">教育部师范司组织专家审核意见</span></td>
              <td> 
              	<textarea class="smallarea" name="finalComment" cols="70" rows="12" id="finalComment"></textarea>
			  </td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">备注</span></td>
              <td> 
              	<textarea class="smallarea" name="note" cols="70" rows="12" id="note"></textarea>
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


var oFCKeditor = new FCKeditor( 'personalResume' ) ; 
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


var oFCKeditor1 = new FCKeditor( 'trainingResult' ) ; 
oFCKeditor1.Height = 300 ; 
oFCKeditor1.Width  = 700 ; 

oFCKeditor1.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor1.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor1.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor1.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor1.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor1.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor1.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor1.ReplaceTextarea() ; 


var oFCKeditor2 = new FCKeditor( 'trainingExperience' ) ; 
oFCKeditor2.Height = 300 ; 
oFCKeditor2.Width  = 700 ; 

oFCKeditor2.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor2.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor2.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor2.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor2.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor2.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor2.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor2.ReplaceTextarea() ; 



var oFCKeditor3 = new FCKeditor( 'otherItems' ) ; 
oFCKeditor3.Height = 300 ; 
oFCKeditor3.Width  = 700 ; 

oFCKeditor3.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor3.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor3.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor3.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor3.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor3.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor3.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor3.ReplaceTextarea() ; 



var oFCKeditor4 = new FCKeditor( 'unitComment' ) ; 
oFCKeditor4.Height = 300 ; 
oFCKeditor4.Width  = 700 ; 

oFCKeditor4.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor4.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor4.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor4.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor4.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor4.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor4.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor4.ReplaceTextarea() ; 



var oFCKeditor5 = new FCKeditor( 'recommendComment' ) ; 
oFCKeditor5.Height = 300 ; 
oFCKeditor5.Width  = 700 ; 

oFCKeditor5.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor5.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor5.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor5.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor5.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor5.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor5.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor5.ReplaceTextarea() ; 



var oFCKeditor6 = new FCKeditor( 'finalComment' ) ; 
oFCKeditor6.Height = 300 ; 
oFCKeditor6.Width  = 700 ; 

oFCKeditor6.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor6.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor6.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor6.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor6.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor6.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor6.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor6.ReplaceTextarea() ; 




var oFCKeditor7 = new FCKeditor( 'note' ) ; 
oFCKeditor7.Height = 300 ; 
oFCKeditor7.Width  = 700 ; 

oFCKeditor7.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor7.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor7.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor7.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor7.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor7.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor7.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor7.ReplaceTextarea() ; 
//--> 
</script> 
<script>bLoaded=true;</script>

</body>

</html>



