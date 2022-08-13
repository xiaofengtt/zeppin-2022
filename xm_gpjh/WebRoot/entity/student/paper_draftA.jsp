<%@ page language="java"  pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
response.setHeader("expires", "0");
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<title>论文初稿</title>
<script>

//删除字符串左边的空格
function ltrim(str) { 
	if(str.length==0)
		return(str);
	else {
		var idx=0;
		while(str.charAt(idx).search(/\s/)==0)
			idx++;
		return(str.substr(idx));
	}
}

//删除字符串右边的空格
function rtrim(str) { 
	if(str.length==0)
		return(str);
	else {
		var idx=str.length-1;
		while(str.charAt(idx).search(/\s/)==0)
			idx--;
		return(str.substring(0,idx+1));
	}
}

//删除字符串左右两边的空格
function trim(str) { 
	return(rtrim(ltrim(str)));
}

function validate(){

	var noteObj = document.getElementById('note');
	if(noteObj.value==null || trim(noteObj.value)=="") {
		alert("请留言。");
		return false;
	}

 var obj =document.getElementById('paper');
	  if(obj.value==null || obj.value=="") {
	  	return true;
	  }
	  var length = obj.value.length;
	  var charindex = obj.value.lastIndexOf(".");
	  var ExtentName = obj.value.substring(charindex,charindex+4);
	  if(!(ExtentName == ".doc" )){
	  	alert('文件格式不正确！');
	  	this.focus()
	  	return false;
	  }
	  return true;
}
</script>
</head>
<!-- JavaScript functions -->
<body>
<!--内容区-->

<div id="main_content">
    <div class="content_title">论文初稿</div>
    <div class="cntent_k">
   	  <div class="k_cc">
   	  <form action="/entity/workspaceStudent/studentDraftA_upload.action" enctype="multipart/form-data" method="post" onsubmit="return validate();">
	  	<input type="hidden" value="<s:property value="paper.getId()"/>" name="paperId"/>
	  	<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
		 	<tr>
			<td align="center" valign="middle" class="postFormBox"  colspan="2">
            <a href='/entity/workspaceStudent/studentDraftA_toDownloadDraftA.action?paperId=<s:property value="paper.getId()"/>' target="_blank"><font color="#0000ff">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看初稿详细内容（下载附件）</font></a>
			<a href='/entity/workspaceStudent/studentDraftA_toDownloadTeacherPiGai.action?paperId=<s:property value="paper.getId()"/>' target="_blank"><font color="#0000ff">查看教师修改（下载附件）</font></a>
			</td>
			</tr>
            <tr>
            <td valign="middle" class="postFormBox"> 学生姓名：</td>
			<td valign="middle" class="postFormBox"><s:property value="paper.getPeStudent().getTrueName()"/> </td>
            </tr>
            <tr>
             <td valign="middle" class="postFormBox"> 论文题目：</td>
			 <td valign="middle" class="postFormBox"><s:property value="paper.getPrTchPaperTitle().getTitle()" /> </td>
            </tr>
            
            <tr>
            <td valign="middle" class="postFormBox"> 指导教师：</td>
			<td valign="middle" class="postFormBox"><s:property value="paper.getPrTchPaperTitle().getPeTeacher().getTrueName()" /></td>
            </tr>
            <tr>
            <td valign="middle" class="postFormBox"> 初稿截止日期：</td>
			<td valign="middle" class="postFormBox"><s:date name="paper.getPrTchPaperTitle().getPeSemester().getPaperDraftEndDate()" format="yyyy年MM月dd日" /> </td>
            </tr>
          <tr>
            <td valign="middle" class="postFormBox">初稿批改信息：</td>
            <td valign="middle" >&nbsp;</td>
          </tr>
          
             <s:iterator value="paperContentList" status="st">
             	<tr>
            		<td valign="middle">&nbsp;</td>
            		<td valign="middle" class="postFormBox">第<s:property value="#st.count"/>次批改留言: <s:property value="getNote()"/> </td>
              	</tr>
             </s:iterator>
          
          <tr>
          	<td>&nbsp;</td><td>&nbsp;</td>
          </tr>
          <tr>
          	<td>&nbsp;</td><td>&nbsp;&nbsp;</td>
          </tr>
          <tr>
          	<td valign="middle" class="postFormBox">学生留言信息：</td>
          	<td valign="middle"></td>
          </tr>   
          
          <s:iterator value="stuMsgList" status="st">
          	<tr>
          		<td valign="middle"></td>
          		<td valign="middle" class="postFormBox">第<s:property value="#st.count"/>次学生留言：<s:property value="getNote()"/></td>
          	</tr>
          </s:iterator>
             
          <tr>
    <td class="tdLabel"><label for="paper" class="label">请上传批改后的初稿附件：</label></td>
    <td><input type="file" name="upload" value="" id="paper"/></td>
</tr>

          <tr>
                <td valign="middle" class="postFormBox">&nbsp;</td>
				<td valign="middle" class="postFormBox">&nbsp;</td>
          </tr>
          <tr>
    <td class="tdLabel"><label for="note" class="label">留言：</label></td>
    <td><textarea name="note" cols="30" rows="5" id="note"></textarea></td>
</tr>

          <tr>
                <td valign="middle" class="postFormBox"><p> </p></td>
                <td valign="middle" class="postFormBox">&nbsp;</td>
          </tr>
          <tr>
    <td colspan="2"><div align="center"><input type="submit" id="" value="提交"/>
</div></td>
</tr>

        </table>
		
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<!-- JavaScript onload -->

</body>
</html>
