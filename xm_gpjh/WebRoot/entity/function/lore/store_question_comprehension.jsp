<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>案例分析题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script src="/FCKeditor/fckeditor.js"></script>
<script language="javascript" src="/entity/function/js/check.js"></script>
<s:if test="msg != null and msg != ''">
	<script>
		alert("<s:property value='msg'/>");
	</script>
</s:if>
<script>
function resetAll() {
	document.forms['question'].reset();
	var oEditor = FCKeditorAPI.GetInstance('body') ;
	oEditor.SetData('');	
}
	var bLoaded=false; 
	function pageGuarding()	{ 
		
		if (bLoaded==false) {
			alert("表单正在下载")
			return false
		}
		/*
		var purcheck = false;
		var obj = document.getElementsByName("purposeVal");
		for(i=0; i<obj.length; i++) {
			if(obj[i].checked)
				purcheck = true;
		}
		if(purcheck == false)
		{
			alert("题目用途请至少选择一项");
			return false;
		}
		*/
		
		if(document.question.referencetime.value==""){
			alert("对不起，作答时间不能为空！");
			 document.question.referencetime.focus();
			return;
		}
		if(document.question.referencetime.value.length>10){
			alert("对不起，作答时间超过限制长度！");
			 document.question.referencetime.focus();
			return;
		}
		if(document.question.referencetime.value!=""){
			if(isNum(document.question.referencetime.value))
			{
			}
		else
			{
				alert("输入时间必须为数字!");
				document.question.referencetime.focus();
				//document.form_xml.zip_code.select();
				return ;
			}
		}
		
		if(document.question.referencescore.value==""){
			alert("对不起，题目分值不能为空！");
			document.question.referencescore.focus();
			return;
		}
		if(document.question.referencescore.value.length>10){
			alert("对不起，题目分值超过限制长度！");
			document.question.referencescore.focus();
			return;
		}
		if(document.question.referencescore.value!=""){
		if(isNum(document.question.referencescore.value))
			{
			}
		else
			{
				alert("输入分值必须为数字!");
				document.question.referencescore.focus();
				//document.form_xml.zip_code.select();
				return ;
			}
		}
		
		if(document.question.title.value==""){
			alert("对不起，题目名称不能为空！");
			document.question.title.focus();
			return;
		}
		//判断fckeditor 的内容是否为空
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			return;
		}
	if(acontent.length>1000){
			alert("对不起，题目内容超过指定长度！");
			return;
		}
		
		document.question.submit();
	}
	function pageGuarding1()	{ 
		
		if (bLoaded==false) {
			alert("表单正在下载")
			return false
		}
		/*
		var purcheck = false;
		var obj = document.getElementsByName("purposeVal");
		for(i=0; i<obj.length; i++) {
			if(obj[i].checked)
				purcheck = true;
		}
		if(purcheck == false)
		{
			alert("题目用途请至少选择一项");
			return false;
		}
		*/
		
		if(document.question.referencetime.value==""){
			alert("对不起，作答时间不能为空！");
			 document.question.referencetime.focus();
			return;
		}
		if(document.question.referencetime.value.length>10){
			alert("对不起，作答时间超过限制长度！");
			 document.question.referencetime.focus();
			return;
		}
		if(document.question.referencetime.value!=""){
			if(isNum(document.question.referencetime.value))
			{
			}
		else
			{
				alert("输入时间必须为数字!");
				document.question.referencetime.focus();
				//document.form_xml.zip_code.select();
				return ;
			}
		}
		
		if(document.question.referencescore.value==""){
			alert("对不起，题目分值不能为空！");
			document.question.referencescore.focus();
			return;
		}
		if(document.question.referencescore.value.length>10){
			alert("对不起，题目分值超过限制长度！");
			document.question.referencescore.focus();
			return;
		}
		if(document.question.referencescore.value!=""){
		if(isNum(document.question.referencescore.value))
			{
			}
		else
			{
				alert("输入分值必须为数字!");
				document.question.referencescore.focus();
				//document.form_xml.zip_code.select();
				return ;
			}
		}
		
		if(document.question.title.value==""){
			alert("对不起，题目名称不能为空！");
			document.question.title.focus();
			return;
		}
		//判断fckeditor 的内容是否为空
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			return;
		}
		if(acontent.length>1000){
			alert("对不起，题目内容超过限制长度！");
			return;
		}
	/*	if(document.question.body.value==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		*/
		
		document.question.types.value="1";
		document.question.submit();
	}
	
	function checkLength(obj,len){
		if(obj.value.length > (len)){
			alert('此字段最多只能输入'+len+'个字符');
			obj.value = obj.value.substr(0,len);
			return false;
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0">
<form name="question" action="/entity/studyZone/courseResources_toCompQuestionAdd.action" method="post">
<input type="hidden" name="type" value="<s:property value='#parameters.type[0]'/>" />
<input type="hidden" name="types" value="0">
<input type="hidden" name="course_id" value="<s:property value='#parameters.course_id[0]'/>">
<input type="hidden" name="lore_id" value="<s:property value='#parameters.lore_id[0]'/>">
<input type="hidden" name="fatherDir_id" value="<s:property value='#parameters.fatherDir_id[0]'/>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题属性选择区</td>
  </tr>
  <tr> 
    <td><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="3%" align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td width="15%" class="text6">难&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
          <td width="45%"> <select style="width:80px" name="diff">
              <option value="0.0"> 0.0</option>
              <option value="0.1"> 0.1</option>
              <option value="0.2"> 0.2</option>
              <option value="0.3"> 0.3</option>
              <option value="0.4"> 0.4</option>
              <option value="0.5"> 0.5</option>
              <option value="0.6"> 0.6</option>
              <option value="0.7"> 0.7</option>
              <option value="0.8"> 0.8</option>
              <option value="0.9"> 0.9</option>
              <option value="1.0"> 1.0</option>
            </select> </td>
            <!--
          <td width="3%"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td width="10%" class="text6">题目用途：</td>
          <td width="4%" class="text6"> <input type="checkbox" name="purposeVal" value="KAOSHI" checked> 
          </td>
          <td class="text6">在线自测</td>
           -->
           <td width="3%"></td>
          <td width="10%" class="text6"></td>
          <td width="4%" class="text6">
          </td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">认知分类：</td>
          <td><select style="width:80px" name="cognizetype">
              <option value="LIAOJIE"> 了解</option>
              <option value="LIJIE"> 理解</option>
              <option value="YINGYONG"> 应用</option>
              <option value="FENXI"> 分析</option>
              <option value="ZONGHE"> 综合</option>
              <option value="PINGJIAN"> 评鉴</option>
            </select></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <!-- 
          <td><input type="checkbox" name="purposeVal" value="ZUOYE"></td>
          <td class="text6">在线作业</td>
           -->
           <td class="text6"></td>
           <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议作答时间：</td>
          <td class="text6"><input name="referencetime" type="text" size="20" maxlength="25">
            秒</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <!-- 
          <td><input type="checkbox" name="purposeVal" value="EXPERIMENT"></td>
          <td class="text6">实验</td>
           -->
           <td class="text6"></td>
           <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议题目分值：</td>
          <td class="text6"><input name="referencescore" type="text" size="20" maxlength="25">
            分</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <!-- 
          <td><input type="checkbox" name="purposeVal" value="EXAM"></td>
          <td class="text6">在线考试</td>
           -->
          <td class="text6"></td>
           <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" background="/entity/function/images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目名称：</td>
          <td class="text6"><input name="title" type="text" size="20" maxlength="25"></td>
          <!-- <td><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目类型：</td>
          <td class="text6"> <input type="hidden" name="type" value=""></td>
          <td class="text6"></td> -->
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
      </table>
      <br></td>
  </tr>
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区（录入文章内容）</td>
  </tr>
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
          	  <textarea name=body class=selfScale rows="22" cols="72"></textarea>
          </td>
        </tr>
      </table><br>
     </td>
  </tr>
  
  <tr> 
    <td>
    <table id="Tbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td align="center"  class="text6">题目提示<br>
            （对学生）</td>
          <td height="90" valign="middle"> 
            <textarea name="studentnote" cols="81" rows="5" onkeyup="checkLength(this,1000);"></textarea>
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目要求<br>
            （对教师）</td>
          <td height="90"> 
            <textarea name="teachernote" cols="81" rows="5" onkeyup="checkLength(this,1000);"></textarea>
          </td>
        </tr>
    </table>
  	</td>
  </tr>
  
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><a href="javascript:pageGuarding();"><img src="/entity/function/images/bcfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&course_id=<s:property value='#parameters.course_id[0]'/>" target=mainer><img src="/entity/function/images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="#" onclick="resetAll()"><img src="/entity/function/lore/images/cxlr.gif" width="105" height="30" border="0"></a></td>
          <!-- <td><a href="#" onclick="pageGuarding1();"><img src="/entity/function/images/xyt.gif" width="105" height="30" border="0"></a></td> -->
        </tr>
      </table>
    </td>
  </tr>
</table>
<script>bLoaded=true;</script>
</form>
</body>
<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'body' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>
