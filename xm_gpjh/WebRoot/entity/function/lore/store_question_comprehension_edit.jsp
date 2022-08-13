<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>案例分析题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/entity/function/js/check.js"></script>
<script src="/FCKeditor/fckeditor.js"></script>
<script>
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
			alert("对不起，作答时间长度超过限制！");
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
			alert("对不起，题目分值长度超过限制！");
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
			alert("对不起，题目内容过长！");
			return;
		}
//判断案例分析题里面问题的fckediter的题目内容是否超过限制	
for(var singleNum = 1; singleNum <= <s:property value='numList[0]'/>; singleNum++) {
	
	   var oEditor1 = FCKeditorAPI.GetInstance("singleBody" + singleNum) ;
       var acontent1=oEditor1.GetXHTML();
       if(acontent1==""){
			alert("对不起，单选题题目内容不能为空！");
			return;
		}
	if(acontent1.length>1000){
			alert("对不起，单选题题目内容过长！");
			return;
		}
}
for(var multiNum = 1; multiNum <= <s:property value='numList[1]'/>; multiNum++) {
	
	   var oEditor1 = FCKeditorAPI.GetInstance("multiBody" + multiNum) ;
       var acontent1=oEditor1.GetXHTML();
       if(acontent1==""){
			alert("对不起，多选题题目内容不能为空！");
			return;
		}
	if(acontent1.length>1000){
			alert("对不起，多选题题目内容过长！");
			return;
		}
}
for(var judgeNum = 1; judgeNum <= <s:property value='numList[2]'/>; judgeNum++) {
	
	   var oEditor1 = FCKeditorAPI.GetInstance("judgeBody" + judgeNum) ;
       var acontent1=oEditor1.GetXHTML();
       if(acontent1==""){
			alert("对不起，判断题题目内容不能为空！");
			return;
		}
	if(acontent1.length>1000){
			alert("对不起，判断题题目内容过长！");
			return;
		}
}
for(var blankNum = 1; blankNum <= <s:property value='numList[3]'/>; blankNum++) {
	
	   var oEditor1 = FCKeditorAPI.GetInstance("blankBody" + blankNum) ;
       var acontent1=oEditor1.GetXHTML();
       if(acontent1==""){
			alert("对不起，填空题目内容不能为空！");
			return;
		}
	if(acontent1.length>1000){
			alert("对不起，填空题目内容过长！");
			return;
		}
}
for(var answerNum = 1; answerNum <= <s:property value='numList[4]'/>; answerNum++) {
	
	   var oEditor1 = FCKeditorAPI.GetInstance("answerBody" + answerNum) ;
       var acontent1=oEditor1.GetXHTML();
       if(acontent1==""){
			alert("对不起，问答题目内容不能为空！");
			return;
		}
	if(acontent1.length>1000){
			alert("对不起，问答题目内容过长！");
			return;
		}
}
		document.question.submit();
	}
	
</script>

<script language="JavaScript" type="text/javascript">     
   function checkLength(obj,len){
		if(obj.value.length >(len)){
			alert('您输入的字符数超过了指定长度,请检查重新输入！');
			obj.value = obj.value.substr(0,len);
			return false;
		  }
		}
</script>
</head>

<body leftmargin="0" topmargin="0">
<form name="question" action="/entity/studyZone/courseResources_doEditQuestion.action" method="post" onsubmit="return pageGuarding();">
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
            <!-- 
          <td width="3%"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td width="10%" class="text6">题目用途：</td>
          <td width="4%" class="text6"> <input type="checkbox" name="purposeVal" value="KAOSHI" <s:if test="questionInfo.purpose.contains('KAOSHI')">checked</s:if> > 
          </td>
          <td class="text6">在线自测</td>
           -->
           <td></td>
           <td></td>
           <td></td>
           <td></td>
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
          <!-- 
          <td><input type="checkbox" name="purposeVal" value="ZUOYE" <s:if test="questionInfo.purpose.contains('ZUOYE')">checked</s:if> ></td>
          <td class="text6">在线作业</td>
           -->
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td height="3" colspan="7" align="center" background="/entity/function/images/ct_08.gif"> 
          </td>
        </tr>
        <tr> 
          <td align="center"><img src="/entity/function/images/ct_09.gif" width="14" height="14"></td>
          <td class="text6">建议作答时间：</td>
          <td class="text6"><input name="referencetime" type="text" size="10" maxlength="15" value="<s:property value='questionInfo.referencetime'/>">
            秒</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <!-- 
          <td><input type="checkbox" name="purposeVal" value="EXPERIMENT" <s:if test="questionInfo.purpose.contains('EXPERIMENT')">checked</s:if> ></td>
          <td class="text6">实验</td>
           -->
          <td>&nbsp;</td>
          <td>&nbsp;</td>
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
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <!-- 
          <td><input type="checkbox" name="purposeVal" value="EXAM" <s:if test="questionInfo.purpose.contains('EXAM')">checked</s:if> ></td>
          <td class="text6">在线考试</td>
           -->
          <td>&nbsp;</td>
          <td>&nbsp;</td>
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
          	  <textarea name=body class=selfScale rows="22" cols="72"><s:property value='body' escape='false'/></textarea>
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
            <textarea name="studentnote" onkeyup="checkLength(this,500)" cols="81" rows="5"><s:property value='questionInfo.studentnote'/></textarea>
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目要求<br>
            （对教师）</td>
          <td height="90"> 
            <textarea name="teachernote" onkeyup="checkLength(this,500)" cols="81" rows="5"><s:property value='questionInfo.teachernote'/></textarea>
          </td>
        </tr>
    </table>
    </td>
  </tr>
<s:iterator value='comprehensionObjectList' id='comp' status='stus'>
<s:if test="#comp[0] == 'DANXUAN'">
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区</td>
  </tr>
  <tr> 
    <td>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr> 
          <td align="center"  class="text6">题目类型</td>
          <td valign="middle"> 
            <INPUT type="hidden" name="singleType<s:property value='#comp[6]'/>" value="<s:property value='#comp[0]'/>">单选题
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目名称</td>
          <td> 
            <INPUT type="text" name="singleTitle<s:property value='#comp[6]'/>" value="<s:property value='#comp[1]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">建议作答时间</td>
          <td valign="middle"> 
            <INPUT type="text" name="singleTime<s:property value='#comp[6]'/>" value="<s:property value='#comp[2]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">建议题目分值</td>
          <td> 
            <INPUT type="text" name="singleScore<s:property value='#comp[6]'/>" value="<s:property value='#comp[3]'/>">
          </td>
        </tr>
		<tr> 
          <td align="center"  class="text6">题目内容</td>
          <td height="90" valign="middle"> 
            <textarea name="singleBody<s:property value='#comp[6]'/>" cols="81" rows="5"><s:property value='#comp[4]' escape='false'/></textarea>
            <INPUT type="hidden" name="singleSerial<s:property value='#comp[6]'/>" value="<s:property value='%{#stus.index + 1}'/>">
          </td>
        </tr>
       <s:iterator value='#comp[7]' id='index'>
        <tr> 
          <td align="center"  class="text6">选项<s:property value='#index[0]'/><input type="radio" name="singleAnswer<s:property value='#comp[6]'/>" value="<s:property value='#index[0]'/>" <s:if test="#comp[5].equals(#index[0])">checked</s:if> ></td>
          <td> 
            <input name="singleOptions<s:property value='#comp[6]'/>" type="text" size="80" maxlength="80" value="<s:property value='#index[1]'/>">
          </td>
        </tr>
      </s:iterator>
    </table>
    </td>
  </tr>
</s:if>
<s:elseif test="#comp[0] == 'DUOXUAN'">
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区</td>
  </tr>
  <tr> 
    <td>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr> 
          <td align="center"  class="text6">题目类型</td>
          <td valign="middle"> 
            <INPUT type="hidden" name="multiType<s:property value='#comp[6]'/>" value="<s:property value='#comp[0]'/>">多选题
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目名称</td>
          <td> 
            <INPUT type="text" name="multiTitle<s:property value='#comp[6]'/>" value="<s:property value='#comp[1]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">建议作答时间</td>
          <td valign="middle"> 
            <INPUT type="text" name="multiTime<s:property value='#comp[6]'/>" value="<s:property value='#comp[2]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">建议题目分值</td>
          <td> 
            <INPUT type="text" name="multiScore<s:property value='#comp[6]'/>" value="<s:property value='#comp[3]' />">
          </td>
        </tr>
		<tr> 
          <td align="center"  class="text6">题目内容</td>
          <td height="90" valign="middle"> 
            <textarea  name="multiBody<s:property value='#comp[6]'/>" cols="81" rows="5"><s:property value='#comp[4]' escape='false'/></textarea>
            <INPUT type="hidden" name="multiSerial<s:property value='#comp[6]'/>" value="<s:property value='%{#stus.index + 1}'/>">
          </td>
        </tr>
       <s:iterator value='#comp[7]' id='index'>
        <tr> 
          <td align="center"  class="text6">选项<s:property value='#index[0]'/><input type="checkbox" name="multiAnswer<s:property value='#comp[6]'/>" value="<s:property value='#index[0]'/>" <s:if test="#comp[5].contains(#index[0])">checked</s:if> ></td>
          <td> 
            <input name="multiOptions<s:property value='#comp[6]'/>" type="text" size="80" maxlength="80" value="<s:property value='#index[1]'/>">
          </td>
        </tr>
       </s:iterator>
    </table>
    </td>
  </tr>
  </s:elseif>
  <s:elseif test="#comp[0] == 'PANDUAN'">
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区</td>
  </tr>
  
  <tr> 
    <td>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr> 
          <td align="center"  class="text6">题目类型</td>
          <td valign="middle"> 
            <INPUT type="hidden" name="judgeType<s:property value='#comp[6]'/>" value="<s:property value='#comp[0]'/>">判断题
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目名称</td>
          <td> 
            <INPUT type="text" name="judgeTitle<s:property value='#comp[6]'/>" value="<s:property value='#comp[1]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">建议作答时间</td>
          <td valign="middle"> 
            <INPUT type="text" name="judgeTime<s:property value='#comp[6]'/>" value="<s:property value='#comp[2]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">建议题目分值</td>
          <td> 
            <INPUT type="text" name="judgeScore<s:property value='#comp[6]'/>" value="<s:property value='#comp[3]'/>">
          </td>
        </tr>
		<tr> 
          <td align="center"  class="text6">题目内容</td>
          <td height="90" valign="middle"> 
            <textarea  name="judgeBody<s:property value='#comp[6]'/>" cols="81" rows="5"><s:property value='#comp[4]' escape='false'/></textarea>
            <INPUT type="hidden" name="judgeSerial<s:property value='#comp[6]'/>" value="<s:property value='%{#stus.index + 1}'/>">
          </td>
        </tr>
        
        <tr> 
          <td align="center" class="text6">答案</td>
          <td height="35"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="82%" class="text6"><input type="radio" name="judgeAnswer<s:property value='#comp[6]'/>" value="1" <s:if test="#comp[5]==1">checked</s:if>>正确&nbsp;<input type="radio" name="judgeAnswer<s:property value='#comp[6]'/>" value="0" <s:if test="#comp[5]==0">checked</s:if> >错误</td>
                <td style="padding-left:5px">&nbsp;</td>
				<td valign="bottom" style="padding-left:5px">&nbsp;</td>

              </tr>
            </table>
          </td>
        </tr>
 
    </table>
    </td>
  </tr>
  </s:elseif>
  <s:elseif test="#comp[0] == 'TIANKONG'">
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区</td>
  </tr>
  
  <tr> 
    <td>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr> 
          <td align="center"  class="text6">题目类型</td>
          <td valign="middle"> 
            <INPUT type="hidden" name="blankType<s:property value='#comp[6]'/>" value="<s:property value='#comp[0]'/>">填空题
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目名称</td>
          <td> 
            <INPUT type="text" name="blankTitle<s:property value='#comp[6]'/>" value="<s:property value='#comp[1]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">建议作答时间</td>
          <td valign="middle"> 
            <INPUT type="text" name="blankTime<s:property value='#comp[6]'/>" value="<s:property value='#comp[2]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">建议题目分值</td>
          <td> 
            <INPUT type="text" name="blankScore<s:property value='#comp[6]'/>" value="<s:property value='#comp[3]'/>">
          </td>
        </tr>
		<tr> 
          <td align="center"  class="text6">题目内容</td>
          <td height="90" valign="middle"> 
            <textarea name="blankBody<s:property value='#comp[6]'/>" cols="81" rows="5"><s:property value='#comp[4]' escape='false'/></textarea>
            <INPUT type="hidden" name="blankSerial<s:property value='#comp[6]'/>" value="<s:property value='%{#stus.index + 1}'/>">
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">答案</td>
          <td height="90" valign="middle"> 
            <textarea name="blankAnswer<s:property value='#comp[6]'/>" onkeyup="checkLength(this,500)" cols="81" rows="5"><s:property value='#comp[5]'/></textarea>
          </td>
        </tr>
 
    </table>
    </td>
  </tr>
  </s:elseif>
 <s:elseif test="#comp[0] == 'WENDA'">
  <tr> 
    <td height="41" background="/entity/function/images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区</td>
  </tr>
  
  <tr> 
    <td>
    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr> 
          <td align="center"  class="text6">题目类型</td>
          <td valign="middle"> 
            <INPUT type="hidden" name="answerType<s:property value='#comp[6]'/>" value="<s:property value='#comp[0]'/>">问答题
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">题目名称</td>
          <td> 
            <INPUT type="text" name="answerTitle<s:property value='#comp[6]'/>" value="<s:property value='#comp[1]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center"  class="text6">建议作答时间</td>
          <td valign="middle"> 
            <INPUT type="text" name="answerTime<s:property value='#comp[6]'/>" value="<s:property value='#comp[2]'/>">
          </td>
        </tr>
        <tr> 
          <td align="center" class="text6">建议题目分值</td>
          <td> 
            <INPUT type="text" name="answerScore<s:property value='#comp[6]'/>" value="<s:property value='#comp[3]'/>">
          </td>
        </tr>
		<tr> 
          <td align="center"  class="text6">题目内容</td>
          <td height="90" valign="middle"> 
            <textarea name="answerBody<s:property value='#comp[6]'/>" cols="81" rows="5"><s:property value='#comp[4]' escape='false'/></textarea>
            <INPUT type="hidden" name="answerSerial<s:property value='#comp[6]'/>" value="<s:property value='%{#stus.index + 1}'/>">
          </td>
        </tr>
        
        <tr> 
          <td align="center"  class="text6">答案</td>
          <td height="90" valign="middle"> 
            <textarea name="answerAnswer<s:property value='#comp[6]'/>" onkeyup="checkLength(this,500)" cols="81" rows="5" ><s:property value='#comp[5]'/></textarea>
          </td>
        </tr>
 
    </table>
    </td>
  </tr>
  </s:elseif>
</s:iterator>
  <tr> 
    <td height="65" align="right" style="padding-right:50px"> 
      <table width="50%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><a href="#" onclick="pageGuarding();"><img src="/entity/function/images/bcfh.gif" width="100" height="30" border="0"></a></td>  
          <td><a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value='course_id'/>&curPage=<s:property value='curPage'/>" target=mainer ><img src="/entity/function/images/zjfh.gif" width="100" height="30" border="0"></a></td>
          <td><a href="#" onclick="window.location.reload()"><img src="/entity/function/lore/images/cxlr.gif" width="105" height="30" border="0"></a></td>
          <s:if test='anliNextId!=null&&anliNextId!=""'>
          	<td><a href="/entity/studyZone/courseResources_editQuestion.action?course_id=<s:property value='course_id'/>&question_id=<s:property value='anliNextId'/>&lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&type=<s:property value='type'/>"><img src="/entity/function/images/xyt.gif" width="105" height="30" border="0"></a></td>
          </s:if>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script>bLoaded=true;</script>
<INPUT type="hidden" name="singleNum" value="<s:property value='numList[0]'/>">
<INPUT type="hidden" name="multiNum" value="<s:property value='numList[1]'/>">
<INPUT type="hidden" name="judgeNum" value="<s:property value='numList[2]'/>">
<INPUT type="hidden" name="blankNum" value="<s:property value='numList[3]'/>">
<INPUT type="hidden" name="answerNum" value="<s:property value='numList[4]'/>">
<INPUT type="hidden" name="totalNum" value="<s:property value='%{comprehensionObjectList.size() -1}'/>">
</form>
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

for(var singleNum = 1; singleNum <= <s:property value='numList[0]'/>; singleNum++) {
	var oFCKeditor = new FCKeditor( "singleBody" + singleNum ) ; 
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

}
for(var multiNum = 1; multiNum <= <s:property value='numList[1]'/>; multiNum++) {
	var oFCKeditor = new FCKeditor( "multiBody" + multiNum ) ; 
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

}
for(var judgeNum = 1; judgeNum <= <s:property value='numList[2]'/>; judgeNum++) {
	var oFCKeditor = new FCKeditor( "judgeBody" + judgeNum ) ; 
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

}
for(var blankNum = 1; blankNum <= <s:property value='numList[3]'/>; blankNum++) {
	var oFCKeditor = new FCKeditor( "blankBody" + blankNum ) ; 
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

}
for(var answerNum = 1; answerNum <= <s:property value='numList[4]'/>; answerNum++) {
	var oFCKeditor = new FCKeditor( "answerBody" + answerNum ) ; 
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

}
//--> 
</script> 
</body>
</html>
