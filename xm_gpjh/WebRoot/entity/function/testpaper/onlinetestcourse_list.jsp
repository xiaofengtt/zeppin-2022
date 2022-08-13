<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训课堂</title>
<style type="text/css">
<!--
#wrap{word-break:break-all; width:150px;} 
-->
</style>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除这条记录吗？"))
		window.navigate(link);
}
function DetailInfo(testCourseId)
{
	window.open('onlinetestcourse_info.jsp?testCourseId='+testCourseId,'','width=630,height=550,scrollbars=yes');
}
</script>
<s:if test="msg != null and msg != ''">
	<script>
		alert("<s:property value='msg'/>");
	</script>
</s:if>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<br>
<table width="90%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="95%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                          	<td class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong><s:if test="onlineType == 'test'">在线自测</s:if><s:elseif test="onlineType == 'homework'">在线作业</s:elseif></strong></td>
                          </tr>
                          <tr>             
                            <s:if test="roleCode == 3">
                            	<td align="center" valign="middle" height="15"><a href="/entity/function/testpaper/onlinetestcourse_add.jsp?onlineType=<s:property value='onlineType'/>&course_id=<s:property value='course_id'/>" class="tj">[添加]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </s:if>
                            
                            <td>
                            	<form method="post" action="/entity/studyZone/courseResources_onlineTest.action" name="paper_listSearchForm">
                            		<input type="hidden" name="course_id" value='<s:property value="course_id"/>'/>
                            		<input type="hidden" name="onlineType" value='<s:property value="onlineType"/>'/>
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="right" class="mc1">按标题搜索：</td>
                            			<td align="left"><input name="searchVal" type="text" size="20" maxlength="50" value="<s:property value='searchVal'/>"/></td>
                            		    <td width="50">&nbsp;</td>
                            		    <td align="center"><input type="image" src="/entity/function/images/search.gif" width="99" height="19" /></td>
                            		</tr>                            
                            	</table>
                            	</form>
                            </td>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                 <td align="center">
				<table width="88%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td align="center" height="26" background="/entity/function/images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="16%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="30%" align="center" class="title">时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="12%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="13%" align="center" class="title">发布时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="6%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="18%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                <s:set name="createUser" value="createUserList"/>
				<s:iterator value="testInfoList" id="testInfo" status="stas">
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<s:if test='#stas.odd'>#ffffff</s:if><s:else>#E8F9FF</s:else>"> 
                            <td width="17%" align="center" class="td1">
                            	<a href="/entity/studyZone/courseResources_testView.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>" target=_blank>&nbsp;&nbsp;<s:property value='#testInfo.title'/></a>
							</td>
                            <td width="31%" align="center"  class="td1"><s:property value="#testInfo.startDate"/>&nbsp;到&nbsp;<s:property value="#testInfo.endDate"/></td>
                            <td width="13%" align="center"  class="td1"><s:property value='#createUser[#stas.index]'/></td>
                            <td width="14%" align="center"  class="td1"><s:date name="#testInfo.creatdate" format="yyyy-MM-dd"/></td>
                            <s:if test="roleCode == 3">	
	                            <td width="7%" align="center"  class="td1">
		                            <s:if test="#testInfo.status == 1">
		                            	<a href="/entity/studyZone/courseResources_changestatus.action?onlineType=<s:property value='onlineType'/>&course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>&status=0">有效</a>
		                            </s:if>
		                            <s:else>
		                            	<a href="/entity/studyZone/courseResources_changestatus.action?onlineType=<s:property value='onlineType'/>&course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>&status=1">无效</a>
		                            </s:else>
	                            </td>
                            </s:if>
                            <s:elseif test="#testInfo.status == 1">
	                            <td width="7%" align="center"  class="td1">有效</td>
                            </s:elseif>
                            <s:else>
                             <td width="7%" align="center"  class="td1">无效</td>
                            </s:else>   
                           <td width="18%" align="center"  class="td1">
                            <a href="/entity/studyZone/courseResources_enterTestpaper.action?onlineType=<s:property value='onlineType'/>&course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>&isAutoCheck=<s:property value='#testInfo.isautocheck'/>&isHiddenAnswer=<s:property value='#testInfo.ishiddenanswer'/>">进入</a>|
							
							<s:if test="roleCode == 3">	
                            	<a href="/entity/studyZone/courseResources_editTest.action?onlineType=<s:property value='onlineType'/>&course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>">编辑</a>|
                            	<a href="javascript:cfmdel('/entity/studyZone/courseResources_deleteTest.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>&onlineType=<s:property value="onlineType"/>')" class="button">删除</a>
							</s:if>
                            <s:else>  
                              <a href="/entity/studyZone/courseResources_testView.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='#testInfo.id'/>" target="_blank">查看</a>
                      		</s:else>
				            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
				</s:iterator>                    
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table><br/>
                </td>
              </tr>
              <tr>
                <td align="center">
				<table width="88%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td align="center" style="font-size:12px" background="/entity/function/images/bottom02.gif">
                       	共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
			            	<s:if test="curPage == 1">
			            		<font color="gray">首页 上一页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=<s:property value="onlineType"/>&course_id=<s:property value="course_id"/>&curPage=1&searchVal=<s:property value="searchVal"/>">首页</a>
			            		<a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=<s:property value="onlineType"/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>&searchVal=<s:property value="searchVal"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=<s:property value="onlineType"/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="%{curPage+1}"/>&searchVal=<s:property value="searchVal"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=<s:property value="onlineType"/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="totalPage"/>&searchVal=<s:property value="searchVal"/>">尾页</a>
			            	</s:else>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="100%" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        
        </tr>
      </table>
</body>
</html>
