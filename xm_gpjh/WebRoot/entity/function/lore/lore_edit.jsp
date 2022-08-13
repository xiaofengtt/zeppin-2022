<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑知识点</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="/js/calendar/calendar-win2000.css">
<script type="text/javascript" src="/js/calendar/calendar.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
<script type="text/javascript">
	function pageGuarding() {
		if(document.loreDirForm.lore_name.value == "") {
			alert("知识点名称不能为空！");
			document.loreDirForm.lore_name.focus();
			return false;
		}
		if(document.loreDirForm.creatDate.value == "") {
			alert("创建日期不能为空！");
			return false;
		}
		
		if(document.loreDirForm.lore_content.value == "") {
			alert("知识点内容不能为空！");
			document.loreDirForm.lore_content.focus();
			return false;
		}
		if(document.loreDirForm.lore_content.value.length>500){
		alert("您输入的知识点内容超过指定长度，请检查重新输入！");
			document.loreDirForm.lore_content.focus();
			return false;
		}
		
		return true;
	}
		
</script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<form name="loreDirForm" action="/entity/studyZone/courseResources_doLoreEdit.action" method="post" onsubmit="return pageGuarding();">
<INPUT type="hidden" name="loreDir_id" value=<s:property value="fatherDir_id"/> />
<INPUT type="hidden" name="course_id" value=<s:property value="course_id"/> />
<INPUT type="hidden" name="lore_id" value=<s:property value="lore_id"/> />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"><img src="/entity/function/images/zsd.gif" width="217" height="86"></td>
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
                            <td width="100" valign="top" class="text3" style="padding-top:25px">编辑知识点</td>
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
				                          <td width="80" class="text3" valign="top">名称:<font color=red>*</font></td>
		                                  <td valign="top"><input name="lore_name" type="text" size="30" maxlength="50" value="<s:property value='testLoreInfo.name'/>"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">创建日期:<font color=red>*</font></td>
		                                  <td>
		                                  <input type="text"
											class="input" name="creatDate" id="creatDate" value="<s:date name='testLoreInfo.creatdate' format='yyyy-MM-dd'/>" size="25" readonly> <img
											src="/entity/function/images/img.gif" width="20" height="14"
											id="f_trigger_c"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">内容:<font color=red>*</font></td>
		                                  <td valign="top"><textarea name="lore_content"  rows="10" cols="50"><s:property value='testLoreInfo.content'/></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<%---
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top">
		                                  	<INPUT type="radio" name="active" value="1" <%if(lore.isActive()){%>checked<%}%>>是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="active" value="0" <%if(!lore.isActive()){%>checked<%}%>>否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		--%>
                          		<br>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"></td>
				                          <td width="80" class="text3"></td>
		                                  <td><input type="submit" value="修改">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="window.location='/entity/studyZone/courseResources_loreList.action?course_id=<s:property value="course_id"/>&loreDir_id=<s:property value="fatherDir_id"/>'"/></td>	                                  
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
<script type="text/javascript">
   	Calendar.setup({
        inputField     :    "creatDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>
</body>
</html>
