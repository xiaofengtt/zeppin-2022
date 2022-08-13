<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>知识点目录</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<s:if test="msg != null and msg != ''">
	<script>
		alert("<s:property value='msg'/>");
	</script>
</s:if>
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
             <!-- <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"> <img src="/entity/function/images/zsd.gif" width="217" height="86"></td>
              </tr>-->
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="/entity/function/images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">知识点目录</td>
                            <td background="/entity/function/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><img src="/entity/function/images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  <tr>
                            
                      <td><img src="/entity/function/images/wt_04.gif" width="604" height="13"></td>
                          </tr>
                          <tr>
                            <td background="/entity/function/images/wt_05.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="38" valign="bottom"><img src="/entity/function/images/wt_07.gif" width="38" height="25"></td>
                                  <td valign="bottom" class="text1">当前目录：<s:property value="curTestLoreDir.name"/></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="/entity/function/images/wt_08.gif" width="572" height="11"></td>
                          </tr>
                          <tr>
                            <td background="/entity/function/images/wt_10.gif"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <s:iterator value="subTestLoreDirList" id="dir">
                                <tr>
                                  <td width="10%" align="center"><img src="/entity/function/images/wtxb1.gif" width="25" height="35"></td>
                                  <td>
                                  	<a href="/entity/studyZone/courseResources_loreList.action?loreDir_id=<s:property value="#dir.id"/>&course_id=<s:property value="course_id"/>"><s:property value="#dir.name"/></a>&nbsp;&nbsp;&nbsp;
                                  	<a href="/entity/studyZone/courseResources_loreDirEdit.action?loreDir_id=<s:property value="#dir.id"/>&fatherDir_id=<s:property value="curTestLoreDir.id"/>&course_id=<s:property value="course_id"/>" class="tj">[编辑]</a>
                                  	<a href="javascript:if(confirm('确定删除知识点目录吗？')) location.href='/entity/studyZone/courseResources_loreDirDelete.action?loreDir_id=<s:property value="#dir.id"/>&fatherDir_id=<s:property value="curTestLoreDir.id"/>&course_id=<s:property value="course_id"/>'" class="tj">[删除]</a>
                                  </td>
                                </tr>
                            </s:iterator>
                            <s:iterator value="testLoreInfoList" id="lore">
                                <tr>
                                  <td align="center"><img src="/entity/function/images/wtxb2.gif" width="25" height="35"></td>
                                  <td>
                                  	<a href="#" onclick="javascript:window.open('/entity/studyZone/courseResources_viewLoreInfo.action?lore_id=<s:property value="#lore.id"/>','newwindow', 'height=300, width=600, toolbar=no , menubar=no, scrollbars=yes, resizable=no, location=no, status=no')"><s:property value="#lore.name"/></a>&nbsp;&nbsp;&nbsp;
                                  	<a href="/entity/studyZone/courseResources_loreEdit.action?lore_id=<s:property value="#lore.id"/>&course_id=<s:property value="course_id"/>&fatherDir_id=<s:property value="curTestLoreDir.id"/>" class="tj">[编辑]</a>
                                  	<a href="javascript:if(confirm('确定删除知识点吗？')) location.href='/entity/studyZone/courseResources_loreDelete.action?lore_id=<s:property value="#lore.id"/>&course_id=<s:property value="course_id"/>&fatherDir_id=<s:property value="curTestLoreDir.id"/>'" class="tj">[删除]</a>
                                  	<a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value="#lore.id"/>&course_id=<s:property value="course_id"/>&fatherDir_id=<s:property value="curTestLoreDir.id"/>" class="tj">[进入题库]</a>
                                  </td>
                                </tr>
                            </s:iterator>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="/entity/function/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table></td>
                          </tr>
                          <tr>
                            
                      <td><img src="/entity/function/images/wt_06.gif" width="604" height="11"></td>
                          </tr>
                    <tr>
                      <td align="center">
                      <s:if test="curTestLoreDir.id != rootTestLoreDir.id">
                      	<a href="/entity/studyZone/courseResources_loreList.action?loreDir_id=<s:property value="curTestLoreDir.fatherdir"/>&course_id=<s:property value="course_id"/>" class="tj">[返回上层目录]</a>
                      </s:if>
                      <a href="/entity/function/lore/lore_add.jsp?loreDir_id=<s:property value="curTestLoreDir.id"/>&course_id=<s:property value="course_id"/>" class="tj">[添加新知识点]</a>
                      <a href="/entity/function/lore/lore_dir_add.jsp?fatherDir_id=<s:property value="curTestLoreDir.id"/>&course_id=<s:property value="course_id"/>" class="tj">[添加新目录]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</body>
</html>
