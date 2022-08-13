<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>判断题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
	function onCommit(b)
	{
		var dire = b;
		var select = false;
		var answer = document.getElementsByName("answer");
		for(i=0;i<answer.length;i++)
		{
			if(answer[i].checked)
				select = true;
		}
		if(!select)
		{
			alert("请选择答案！");
			return;
		}
		document.judge.action="/entity/studyZone/courseResources_saveResultAndDirect.action?direction="+dire;
		document.judge.submit();
	}
	
</script>
</head>

<body>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="42" style="padding-left:53px;padding-top:8px" class="text3">判断题</td>
  </tr>
<form id="judge" name="judge" action="" method="post">
<input type="hidden" name="qNum" value="<s:property value='qNum'/>">
<input type="hidden" name="type" value="<s:property value='type'/>">
  <tr> 
    <td valign="top"  align=center> <table height="390" border="0" cellpadding="0" cellspacing="0" align=center>
        <tr> 
          <td valign="top" class="bg2" align=center> <table width="100%" border="0" cellspacing="0" cellpadding="6" align=center>
              <tr> 
                <td class="content1"><s:property value='title' escape='false'/></td>
              </tr>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
                      <td width="10%" class="mc1"><input type="radio" name="answer" value="正确" <s:if test="userAnswer == '正确'">checked</s:if> ></td>
                      <td class="mc1">正确</td>
                    </tr>
                    <tr> 
                      <td width="10%" class="mc1"><input type="radio" name="answer" value="错误" <s:if test="userAnswer== '错误'">checked</s:if> ></td>
                      <td class="mc1">错误</td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td ><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr align="center"> 
                      <td>
                      <s:if test="hasPre">
                      	<input type=button value="上一题" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0 onclick="javascript:onCommit('last')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      </s:if>
                     <s:if test="hasNext">
                       <input type=button value="下一题" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0 onclick="javascript:onCommit('next')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     </s:if>
                     <s:else>
                        <input type=button value="完成自测" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0 onclick="javascript:if(confirm('确定提交答案吗？')) onCommit('final');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </s:else>
                        <input type=reset value="重填" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" border=0>&nbsp;&nbsp;
                      <!-- img src="/entity/function/images/OK.gif" width="80" height="24" onclick="onCommit()"></td>
                      <td><img src="/entity/function/images/reset.gif" width="80" height="24" onclick="reset()"--></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="/entity/function/images/st_07.gif" width="489" height="15"></td>
  </tr>
</form>
</table>
</body>
</html>
