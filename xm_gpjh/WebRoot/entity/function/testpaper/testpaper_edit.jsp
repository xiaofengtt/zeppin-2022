<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<script type="text/javascript">
function checkLength(obj,len){
		if(obj.value.length >(len)){
			alert('您输入的字符数超过了指定长度,请检查重新输入！');
			obj.value = obj.value.substr(0,len);
			return false;
		  }
		}
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改试卷</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript" src="/entity/function/testpaper/checkform.js"></script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<form name="paperForm" action="/entity/studyZone/courseResources_doEditTestPaperInfo.action" method="post" onsubmit="return checkAll('title,time,note')">
<input type="hidden" name="course_id" value="<s:property value='course_id'/>">
<input type="hidden" name="test_id" value="<s:property value='test_id'/>">
<input type="hidden" name="paper_id" value="<s:property value='paper_id'/>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="/entity/function/images/wt_02.gif" width="78" height="52"></td>
                            <td width="120" valign="top" class="text3" style="padding-top:25px">修改试卷基本信息</td>
                            <td background="/entity/function/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="/entity/function/images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="/entity/function/images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="/entity/function/images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="/entity/function/images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="/entity/function/images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">标题:</td>
		                                  <td valign="top">
		                                  <input name="title" id="title" min="3" max="50" msg="标题长度应在3-50字符" type="text" size="30" maxlength="50" value="<s:property value='paperInfo.title'/>">
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">考试时限:</td>
		                                  <td class="text1"><input name="time" id="time" min="1" max="17" msg="时限不合法,只能为数字" type="text" size="30" maxlength="50" value="<s:property value='paperInfo.time'/>">分钟（请填写数字）</td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">说明:</td>
		                                  <td valign="top"><textarea name="note"  id="note" onkeyup="checkLength(this,50)" rows="10" cols="50" min="3" max="50" msg="说明长度应在3-50字符"><s:property value='paperInfo.note'/></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top" class="text1">
		                                  	<INPUT type="radio" name="status" value="1" <s:if test="paperInfo.status == 1">checked</s:if> >是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" <s:if test="paperInfo.status == 0">checked</s:if> >否
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
		                                  <td align="center" class="text1"><input type="submit" value="修改" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;">&nbsp;&nbsp;&nbsp;&nbsp;
		                                  <input type="button" value="返回" onclick="window.location.href='/entity/studyZone/courseResources_enterTestpaper.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='paper_id'/>'" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" >
		                                  </td>	
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="/entity/function/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="/entity/function/images/wt_06.gif" width="604" height="11"></td>
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
