<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加试卷</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">

<script>

	function KeyPress(objTR) {
		//只允许录入数据字符 0-9 和小数点 
			//var objTR = element.document.activeElement;		
		var txtval=objTR.value;	
		var key = event.keyCode;
	
		if(key < 48||key > 57)	{		
			event.keyCode = 0;
			alert("请输入数字");
		}
	}
</script>
<script type="text/javascript" src="/entity/function/testpaper/checkform.js"></script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/testpaper/images/bg2.gif">
<form name="paperForm" action="/entity/studyZone/courseResources_testpaperAdd.action" method="post" onsubmit="return checkAll('title,time,note')">
<input type="hidden" name="test_id" value="<s:property value='#parameters.test_id[0]'/>" >
<input type="hidden" name="course_id" value="<s:property value='#parameters.course_id[0]'/>" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/testpaper/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="/entity/function/testpaper/images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">添加试卷</td>
                            <td background="/entity/function/testpaper/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="/entity/function/testpaper/images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="/entity/function/testpaper/images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="/entity/function/testpaper/images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="/entity/function/testpaper/images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="/entity/function/testpaper/images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/testpaper/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">标题:<font color=red>*</font></td>
		                                  <td valign="top"><input name="title" id="title" min="3" max="50" msg="标题长度应在3-50字符" type="text" size="30" maxlength="50"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="/entity/function/testpaper/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">考试时限:<font color=red>*</font></td>
		                                  <td style="font-size:12px"><input name="time" id="time" min="1" max="17" msg="时限不合法" type="text" size="30" maxlength="50" onkeypress="javascript:KeyPress(this);">分钟（请填写数字）</td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/testpaper/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">说明:<font color=red>*</font></td>
		                                  <td valign="top"><textarea name="note" id="note" rows="10" cols="40" min="3" max="50" msg="说明长度应在3-50字符"></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/testpaper/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">是否激活:</td>
		                                  <td valign="middle" style="font-size:12px">
		                                  	<INPUT type="radio" name="status" value="1" checked>是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0">否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/testpaper/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">组卷方法:</td>
		                                  <td valign="middle" style="font-size:12px">
		                                  	<input type="radio" name="types" value="1" checked>手工组卷&nbsp;&nbsp;&nbsp;
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"></td>
				                          <td width="80" class="text3"></td>
		                                  <td  align="center"><input type="submit" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="添加">&nbsp;&nbsp;&nbsp;&nbsp;
		                                  <input type="button" value="返回" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" onclick="javascript:window.location.href='/entity/studyZone/courseResources_enterTestpaper.action?test_id=<s:property value='#parameters.test_id[0]'/>&course_id=<s:property value='#parameters.course_id[0]'/>'"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="/entity/function/testpaper/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="/entity/function/testpaper/images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</form>
</body>
</html>
