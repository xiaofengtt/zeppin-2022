<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>结业条件设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">

<s:if test="msg!=null and msg !=''">
<script>
	alert("<s:property value='msg'/>");
	if(opener.location.href.indexOf('#')){
		opener.location.href=opener.location.href.substring(0,opener.location.href.length-1)+'?sdf='+new Date().getTime();
	}else{
		opener.location.href=opener.location.href+'?sdf='+new Date().getTime();
	}
</script>
</s:if>

  </head>
  
  <script type="text/javascript">
  
  function checkall(){
	
	if(document.getElementById('shamTestTimes').value==''){
			window.alert("模拟考试次数不能为空！");
  			document.getElementById('shamTestTimes').focus();
			return false;
	}else{
		var shamTestTimes_no=document.getElementById('shamTestTimes').value;
		var myreg=/^([1-9]?\d)$/;
		if(!myreg.test(shamTestTimes_no))
		{
			alert("模拟考试次数必须是数字！请重新输入");
			document.getElementById('shamTestTimes').focus();
			return false;
		}
	}
	
  }
  
</script>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title">培训计划结业条件设置</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/study/peCompletion_setup.action" method="post" onsubmit="return checkall();">
<input type="hidden" value="<s:property value='planId'/>" name="planId">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" >结业条件设置</td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
                          		<tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">培训计划名称</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="plan.name"/></td>
                                </tr>
                          		
						 		<tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">培训计划学分</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                    <s:property value="plan.creditRequire"/>
                                 </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">现场培训参加</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  	<input name="spotTraining" type="radio" value="1" <s:if test="plan.spotTraining == 1">checked</s:if>> 是
                                  	&nbsp;&nbsp;&nbsp;&nbsp;<input name="spotTraining" type="radio" value="0" <s:if test="plan.spotTraining == 0">checked</s:if>> 否
                                  </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">在线答疑参加</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  	<input  name="onlineAnswer" type="radio" value="1" <s:if test="plan.onlineAnswer == 1">checked</s:if>> 是
                                  	&nbsp;&nbsp;&nbsp;&nbsp;<input  name="onlineAnswer" type="radio" value="0" <s:if test="plan.onlineAnswer == 0">checked</s:if>> 否
                                  </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">模拟考试次数</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                    <input class=selfScale id='shamTestTimes' name='shamTestTimes' value='<s:property value="plan.shamtestTimes"/>' maxlength="2"/> </td>
                                </tr>
                           <tr>
                            <td height="50" align="center" colspan="2">
                              <input type="submit" value="提交" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
                              &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" onclick="javascript:window.close();" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"/>
                            </td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
