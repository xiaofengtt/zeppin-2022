<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多选题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/entity/function/js/check.js"></script>
<script src="/FCKeditor/fckeditor.js"></script>
<script>
function resetAll() {
	document.question.reset();
	document.question.diff.value="";
	document.question.cognizetype.value="";
	document.question.referencescore.value="";
	document.question.title.value="";
	var options=document.getElementsByName("options");
	for(i=0;i<options.length;i++)
	{
		options[i].value="";
	}
	
	var oEditor = FCKeditorAPI.GetInstance('body') ;
	oEditor.SetData('');	
}

	var bLoaded=false; 
	
	function pageGuarding()	{
/*		if(currentflag==1) {//处于普通编辑模式
			document.question.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		} else {
			if (currentflag==3) {//处于预浏览先通编辑模式
				document.question.body.value=oDiv.innerHTML;
			}
		}
		document.question.body.value=Absolute2Relative(document.question.body.value);//替换绝对路径
		if (bLoaded==false) {
			alert("表单正在下载")
			return false;
		}
*/				
		if(document.question.referencetime.value==""){
			alert("对不起，作答时间不能为空！");
			 document.question.referencetime.focus();
			return;
		}
		if(document.question.referencetime.value.length>10){
			alert("对不起，作答时间超过限制！");
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
			alert("对不起，题目分值超过限制！");
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
		//alert("11");
		//alert(document.question.body.value);
		
		
		//判断fckeditor 的内容是否为空
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       if(acontent==""){
			alert("对不起，题目内容不能为空！");
			return;
		}
		if(acontent.length>1000){
			alert("对不起，题目内容过长！");
			return;
		}
		if(document.question.title.value.indexOf("'")!="-1")
		{
			alert("对不起，题目名称不能包含英文单引号！");
			document.question.title.focus();
			return;
		}
	/*	if(document.question.body.value==""){
			alert("对不起，题目内容不能为空！");
			document.question.body.focus();
			return;
		}
		*/
		
		
		var count=0;
		for(var j=0;j <document.question.answers.length;j++)
			{  
				if(document.question.answers[j].checked)
				{
					count ++;
					//alert( "对不起，您还没选择答案！");
					//document.question.multi_answers.focus();
					//return false;
				} 
			}
			if(count==0){
			alert( "对不起，您还没选择答案！");
			return;
			}
		
		
		if(document.getElementById("optionsA").value==""||document.getElementById("optionsB").value=="")		
	{
		alert("选项A和B答案内容不能为空。"); return;
	}
	
		document.question.submit();
		
	}
	
	//var intRowIndex = 3;
	//var charCode = 67;
	var intRowIndex = 1 + <s:property value='charCode'/>;
	var charCode = 65 + <s:property value='charCode'/>;
	function insertRow(){
			//添加一行			
			var newTr = Tbl.insertRow(intRowIndex);
			
			//添加两列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			
			//设置列内容和属性
			newTd0.align = 'center';
			newTd0.className = 'text6';			
			newTd0.innerHTML = '选项' + String.fromCharCode(charCode) 
			   + '<input type="checkbox" name="answers" value="' + String.fromCharCode(charCode) + '">';	
			newTd1.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
               + '<td width="82%"><input name="options" type="text" value="" size="80" maxlength="80"></td>'
               + '<td style="padding-left:5px"></td>'
			   + '<td valign="bottom" style="padding-left:5px"></td></tr></table>';
			
			//newTr.attachEvent("onclick",getIndex);
			//intRowIndex = newTr.rowIndex;
			intRowIndex = intRowIndex + 1;
			charCode = charCode + 1;
	}
	
	function deleteRow() {
		if(intRowIndex < 4) {
			alert("选项不能少于两个！");
			return;
		} else {
			Tbl.deleteRow(intRowIndex - 1);
			intRowIndex -= 1;
			charCode -= 1;
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0">
<form name="question" method="post" action="/entity/studyZone/courseResources_doEditQuestion.action" onsubmit="return pageGuarding();">
<input type="hidden" name="question_id" value="<s:property value='question_id'/>">
<input type="hidden" name="course_id" value="<s:property value='course_id'/>">
<input type="hidden" name="lore_id" value="<s:property value='lore_id'/>">
<input type="hidden" name="fatherDir_id" value="<s:property value='fatherDir_id'/>">
<input type="hidden" name="curPage" value="<s:property value='curPage'/>">
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
              <option value="0.0" <s:if test="questionInfo.diff == '0.0'">selected</s:if> > 0.0</option>
              <option value="0.1" <s:if test="questionInfo.diff == '0.1'">selected</s:if> > 0.1</option>
              <option value="0.2" <s:if test="questionInfo.diff == '0.2'">selected</s:if> > 0.2</option>
              <option value="0.3" <s:if test="questionInfo.diff == '0.3'">selected</s:if> > 0.3</option>
              <option value="0.4" <s:if test="questionInfo.diff == '0.4'">selected</s:if> > 0.4</option>
              <option value="0.5" <s:if test="questionInfo.diff == '0.5'">selected</s:if> > 0.5</option>
              <option value="0.6" <s:if test="questionInfo.diff == '0.6'">selected</s:if> > 0.6</option>
              <option value="0.7" <s:if test="questionInfo.diff == '0.7'">selected</s:if> > 0.7</option>
              <option value="0.8" <s:if test="questionInfo.diff == '0.8'">selected</s:if> > 0.8</option>
              <option value="0.9" <s:if test="questionInfo.diff == '0.9'">selected</s:if> > 0.9</option>
              <option value="1.0" <s:if test="questionInfo.diff == '1.0'">selected</s:if> > 1.0</option>
            </select> </td>
          <td width="3%"><br></td>
          <td width="10%" class="text6"><br></td>
          <td width="4%" class="text6"> <br> 
          </td>
          <td class="text6"><br></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">认知分类：</td>
          <td><select style="width:80px" name="cognizetype">
              <option value="LIAOJIE" <s:if test="questionInfo.cognizetype == 'LIAOJIE'">selected</s:if> > 了解</option>
              <option value="LIJIE" <s:if test="questionInfo.cognizetype == 'LIJIE'">selected</s:if> > 理解</option>
              <option value="YINGYONG" <s:if test="questionInfo.cognizetype == 'YINGYONG'">selected</s:if> > 应用</option>
              <option value="FENXI" <s:if test="questionInfo.cognizetype == 'FENXI'">selected</s:if> > 分析</option>
              <option value="ZONGHE" <s:if test="questionInfo.cognizetype == 'ZONGHE'">selected</s:if> > 综合</option>
              <option value="PINGJIAN" <s:if test="questionInfo.cognizetype == 'PINGJIAN'">selected</s:if> > 评鉴</option>
            </select></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td><br></td>
          <td class="text6"></td>
        </tr>
        
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议题目分值：</td>
          <td class="text6"><input name="referencescore" type="text" size="10" maxlength="15" value="<s:property value='questionInfo.referencescore'/>">
            分</td>
          <td><input name="referencetime" type="hidden" value="10"></td>
          <td>&nbsp;</td>
          <td><br></td>
          <td class="text6"></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" background="/entity/function/images/ct_08.gif"> </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目名称：</td>
          <td class="text6"><input name="title" type="text" size="10" maxlength="15" value="<s:property value='questionInfo.title'/>">(不能包含英文单引号)</td>
          <td><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">题目类型：</td>
          <td class="text6"> <input type="hidden" name="type" value="<s:property value='questionInfo.type'/>"></td>
          <td class="text6"><s:property value='typeStr'/></td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
      </table>
      <br></td>
  </tr>
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区（"Shift+Enter"=换行,"Enter"=分段）</td>
  </tr>
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
<!--          	  <img src="/entity/function/images/1.gif" width="730" height="417">-->
          	  <textarea name=body class=selfScale rows="22" cols="72"><s:property value='body'/></textarea>
          </td>
        </tr>
      </table><br>
     </td>
  </tr>
  <tr> 
    <td>
    <table id="Tbl" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td height="25" colspan="2">
			<input type="button" name="add" value="添加选项" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" onclick="insertRow()">&nbsp;&nbsp;&nbsp;
			<input type="button" name="del" value="删除选项" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" onclick="deleteRow()">
		</td>
		</tr>
		<s:iterator value="answerList" id="al">
        <tr> 
          <td align="center" class="text6">选项<s:property value='#al[0]'/><input type="checkbox" name="answers" value="<s:property value='#al[0]'/>" <s:if test="#al[1] == 1">checked</s:if> ></td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%"><input name="options" id="options<s:property value='#al[0]'/>" type="text" size="80" maxlength="80" value="<s:property value='#al[2]'/>"></td>
                <td style="padding-left:5px"><br></td>
				<td valign="bottom" style="padding-left:5px"></td>

              </tr>
            </table>
          </td>
        </tr>
        </s:iterator>
        
      </table></td>
  </tr>
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><a href="#" onclick="javascript:pageGuarding()"><img src="/entity/function/images/bcfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value='course_id'/>&curPage=<s:property value='curPage'/>" target=mainer><img src="/entity/function/images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="/entity/studyZone/courseResources_editQuestion.action?course_id=<s:property value='course_id'/>&question_id=<s:property value='question_id'/>&lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&type=<s:property value='type'/>&curPage=<s:property value='curPage'/>" ><img src="/entity/function/images/cxlr.gif" width="105" height="30" border="0"></a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script>bLoaded=true;</script>
</form>
<form action="" target=_blank name="edit" method=post>
 	<input type=hidden name="content">
 </form>
<script>bLoaded=true;</script>
<script>
	function editOptions(b)
	{
		var opt = b;
		document.edit.content.value=document.getElementById(opt).value;
		document.edit.action="editor.jsp?comp="+opt;
		document.edit.submit();
	}
</script>
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
</body>
</html>
