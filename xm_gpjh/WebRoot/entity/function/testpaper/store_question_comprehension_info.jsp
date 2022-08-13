<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>案例分析题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
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
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="/entity/function/images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">题目信息</td>
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
                                  <td width="38" valign="bottom">&nbsp;</td>
                                  <td valign="bottom" class="text1"></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="/entity/function/images/wt_08.gif" width="572" height="11"></td>
                          </tr>
                          <tr>
                            <td background="/entity/function/images/wt_10.gif">
                            <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">                            
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><s:property value='body'/></td>
                                </tr>                        
                            </table>
                            </td>
                          </tr>
                        <s:iterator value='questionList' id='question' status='stus'>
                          <tr>
                            <td background="/entity/function/images/wt_10.gif">
                            <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">                            
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><s:property value='%{#stus.index + 1 + "、" + #question[0]}'/></td>
                                </tr>
                              <s:if test="#question[2] == 'XUAN'">
                              <s:iterator value='#question[3]' id='xuan'>
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><s:property value='#xuan'/></td>
                                </tr>
                              </s:iterator>
                              </s:if>
                                <tr>
                                  <td width="10%" align="center"></td>
                                  <td class="text6"><s:property value='#question[1]'/></td>
                                </tr>                       
                            </table>
                            </td>
                          </tr>
                        </s:iterator>
                          <tr>
                            <td><img src="/entity/function/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table></td>
                          </tr>
                          <tr>
                            
                      <td><img src="/entity/function/images/wt_06.gif" width="604" height="11"></td>
                          </tr>
                    <tr>
                      <td align="center"></td>
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
