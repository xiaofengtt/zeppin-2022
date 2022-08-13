<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<script  language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除吗？"))
		window.navigate(link);
}
</script>


<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"><img src="/entity/function/images/dy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="680" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="60" valign="bottom" background="/entity/function/images/answer_01.gif"> 
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> 
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top"><img src="/entity/function/images/answer_xb.gif" width="15" height="16"></td>
                            <td class="text4" width="400" style="padding-left:8px"><div style="word-wrap: break-word; word-break: normal; ">问题: <s:property value="question.title"/></div></td>
  </tr>
</table>
                            </td>
                          </tr>
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><s:property value="question.peTrainee.trueName"/></font> 
                              发布时间：<s:date format="yyyy-MM-dd HH:mm:ss" name="question.createDate"/></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="/entity/function/images/answer_03.gif"><table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="/entity/function/images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" width="500" class="text2"><div style="word-wrap: break-word; word-break: normal; "><s:property value="question.detail" escape="false"/></div></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_07.gif" width="680" height="19"></td>
                    </tr>
				<s:if test="hasAns">
                    <tr>
                      <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> <table border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td valign="top"><img src="/entity/function/images/answer_xb.gif" width="15" height="16"></td>
                                  <td class="text4" style="padding-left:8px"><div style="word-wrap: break-word; word-break: normal; ">答 案</div></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><s:property value="ansList[0][2]"/></font> 
发布时间：<s:date format="yyyy-MM-dd HH:mm:ss" name="peAnswers.createDate"/></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="/entity/function/images/answer_03.gif"><table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="/entity/function/images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" width="500" class="text2"><div style="word-wrap: break-word; word-break: normal; "><s:property value="peAnswers.detail" escape="false"/></div></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_07.gif" width="680" height="19"></td>
                    </tr>
                 </s:if>
                    <tr> 
                      <td height="23" align="center" background="/entity/function/images/answer_03.gif">
				<s:if test="succ">
		        
	                      <a href="/entity/studyZone/courseResources_edit.action?ids=<s:property value="question.id"/>" class="tj">[修改问题]</a> &nbsp;
                       	  
	                      <a href="javascript:cfmdel('/entity/studyZone/courseResources_delexe.action?ids=<s:property value="question.id"/>')" class="tj">[删除问题]</a> &nbsp;	                      
                </s:if>
	                      <a href="/entity/studyZone/courseResources_quesList.action?ids=<s:property value="question.id"/>" class="tj">[返回答疑列表]</a> 
                      </td>
                    </tr>
                    <tr>
                      <td valign="top" class="bg4">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <!-- tr> 
                            <td><img src="/entity/function/images/answer_10.gif" width="11" height="11">&nbsp;<strong><span class="text2">思问哲学网改版以大体完成</span></strong></td>
                          </tr -->
                          <tr> 
                            <td height="9" background="/entity/function/images/answer_09.gif"> 
                            </td>
                          </tr>
                        </table>
                        <br>
                      </td>
                 
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>

