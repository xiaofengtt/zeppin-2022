<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此作业吗？")) {
		window.navigate(link);
		//response.sendRedrict("testpaper_list.jsp");
	}
		
}
function DetailInfo(paperId)
{
	window.open('testpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>
<s:if test="msg != null and msg !=''">
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
                            <td align="left"><a href="/entity/function/testpaper/testpaper_add.jsp?test_id=<s:property value='test_id'/>&course_id=<s:property value='course_id'/>" class="tj">&nbsp;&nbsp;&nbsp;&nbsp;[添加]</a>&nbsp;</td>
                            <!-- <td align="center"><a href="testpaperpolicy_list.jsp??test_id=<s:property value='test_id'/>&course_id=<s:property value='course_id'/>&type=list" class="tj">[查看组卷策略]</a>&nbsp;</td>-->
                          <!--   <td align="center"><a href="paperpolicy_add.jsp" class="tj">[添加新组卷策略]</a>&nbsp;</td>  -->
						</s:if>
                            <td></td>                            
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
              	<td height="17"></td>
              </tr>
              <tr>
                <td align="center">
				<table width="90%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="/entity/function/images/tabletop.gif" style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='/entity/function/images/tabletop.gif');">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="18%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="10%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="14%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <!-- td width="8%" align="center" class="title">试卷用途</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td-->
                      <td width="5%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="30%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                <s:set name="testHistory" value="testpaperHistoryList"/>
				<s:iterator value="testpaperList" id="testpaper" status="stas">
				<s:if test="roleCode == 3 or #testpaper.status == 1">
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<s:if test='#stas.odd'>#ffffff</s:if><s:else>#E8F9FF</s:else>"> 
                            <td width="5%" align="center" valign="middle" class="td1"><!-- a href="#" onclick="return false;"><img src="/entity/function/images/xb2.gif" width="8" height="8" border="0" onClick="DetailInfo('')"></a--></td>
                            <td width="17%"  class="td1"> 
                            	<a href="/entity/studyZone/courseResources_viewTestPaperInfo.action?paper_id=<s:property value='#testpaper.id'/>" target="_blank"><s:property value='#testpaper.title'/></a>
							</td>
                            <td width="10%" align="center"  class="td1"><s:property value='#testpaper.creatuser'/></td>
                            <td width="11%" align="center"  class="td1"><s:date name='#testpaper.creatdate' format='yyyy-MM-dd'/></td>
                            <!-- td width="11%" align="center"  class="td1"></td-->
                            <s:if test="roleCode == 3">	
	                            <td width="5%" align="center"  class="td1">
		                            <s:if test="#testpaper.status == 1">
		                            	<a href="/entity/studyZone/courseResources_changePaperStatus.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='#testpaper.id'/>&status=0">有效</a>
		                            </s:if>
		                            <s:else>
		                            	<a href="/entity/studyZone/courseResources_changePaperStatus.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='#testpaper.id'/>&status=1">无效</a>
		                            </s:else>
	                            </td>
                            </s:if>
                            <s:elseif test="#testpaper.status == 1">
	                            <td width="5%" align="center"  class="td1">有效</td>
                            </s:elseif>
                            <s:else>
                             <td width="5%" align="center"  class="td1">无效</td>
                            </s:else>
                            
                            <td width="30%" align="center"  class="td1">
                      	<s:if test="roleCode == 3">
							<a href="/entity/studyZone/courseResources_viewTestHistoryPaper.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='#testpaper.id'/>">查看已交</a>
                            <a href="/entity/studyZone/courseResources_editTestPaperInfo.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='#testpaper.id'/>">编辑试卷</a>
                            <a href="/entity/studyZone/courseResources_paperQuestionEdit.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='#testpaper.id'/>">编辑试题</a>
                            <a href="#" onclick="javascript:window.open('/entity/function/testpaper/preview_testpaper_frame.jsp?course_id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>','zc','resizable,width=800,height=600')" class="button">预览试题</a>
                            <a href="javascript:cfmdel('/entity/studyZone/courseResources_deletePaper.action?test_id=<s:property value='test_id'/>&course_id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>')" class="button">删除</a>
						</s:if>
						<s:elseif test="#testHistory[#stas.index][0] == 0">
							<font color=red>未做</font>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="javascript:window.open('/entity/function/testpaper/preview_testpaper_frame.jsp?course_id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>','zc','resizable,width=800,height=600')" class="button">开始自测</a>
							<!-- 
							<a href="#" onclick="javascript:window.open('/entity/function/testpaper/testpaper_pre.jsp?id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>','zc','resizable,width=800,height=600')">开始自测</a>
							 -->
						</s:elseif>
						<s:elseif test="#testHistory[#stas.index][0] == 1 and #testHistory[#stas.index][1] == 0">
							<font color=red>已做，未及格</font>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="javascript:window.open('/entity/function/testpaper/preview_testpaper_frame.jsp?course_id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>','zc','resizable,width=800,height=600')" class="button">开始自测</a>
							<a href="/entity/studyZone/courseResources_viewHistoryInfo.action?history_id=<s:property value='#testHistory[#stas.index][2]'/>" target=_blank>查看新交作业&nbsp;&nbsp;(历次最高成绩<s:property value='#testHistory[#stas.index][3]'/>)</a>
						</s:elseif>
						<s:elseif test="#testHistory[#stas.index][0] == 1 and #testHistory[#stas.index][1] == 1">
							已做&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="javascript:window.open('/entity/function/testpaper/preview_testpaper_frame.jsp?course_id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>','zc','resizable,width=800,height=600')" class="button">开始自测</a>
							<a href="/entity/studyZone/courseResources_viewHistoryInfo.action?history_id=<s:property value='#testHistory[#stas.index][2]'/>" target=_blank>查看新交作业&nbsp;&nbsp;(历次最高成绩<s:property value='#testHistory[#stas.index][3]'/>)</a>
						</s:elseif>
						<s:elseif test="#testHistory[#stas.index][0] == 1 and #testHistory[#stas.index][1] == 2">
							<font color=red>已做，分数未判</font>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="javascript:window.open('/entity/function/testpaper/preview_testpaper_frame.jsp?course_id=<s:property value='course_id'/>&paper_id=<s:property value='#testpaper.id'/>','zc','resizable,width=800,height=600')" class="button">开始自测</a>
						</s:elseif>
				            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    </s:if>
                  </s:iterator>
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="90%" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
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
			            		<a href="/entity/studyZone/courseResources_enterTestpaper.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=1">首页</a>
			            		<a href="/entity/studyZone/courseResources_enterTestpaper.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_enterTestpaper.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="%{curPage+1}"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_enterTestpaper.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="totalPage"/>">尾页</a>
			            	</s:else>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="100%" height="7"></td>
                    </tr>
                    <!-- <tr>
                      <td align=center>
                     <img src="/entity/function/images/fh.gif" width="80" height="24" style="cursor: pointer;" onclick="javascript:window.location='/entity/studyZone/courseResources_onlineTest.action?onlineType=<s:property value="onlineType"/>&course_id=<s:property value="course_id"/>'">
                    </td>
                    </tr> -->
                  </table>
                </td>
              </tr>
            </table></td>
        
        </tr>
      </table>
</body>
</html>
