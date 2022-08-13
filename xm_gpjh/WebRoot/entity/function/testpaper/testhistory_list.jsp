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
	if(confirm("您确定要删除此试卷吗？"))
		window.navigate(link);
}
function DetailInfo(tsId)
{
	window.open('testhistory_info.jsp?tsId='+tsId);
}

</script>
	<s:if test="msg != null and msg != ''">
		<script>
			alert("<s:property value='msg'/>");
		</script>
	</s:if>
	
</head>
<s:if test="msg != null and msg != ''"></s:if>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<form name="searchForm" action="/entity/studyZone/courseResources_viewTestHistoryPaper.action" method="post">
<input type="hidden" name="paper_id" value="<s:property value="paper_id"/>" >
<input type="hidden" name="test_id" value="<s:property value="test_id"/>" >
<input type="hidden" name="course_id" value="<s:property value="course_id"/>" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top" colspan=2> 
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">                         
                          <tr>
	                    <s:if test="roleCode == 3">
                          	<td  class="mc1">
							<!-- 学号：<input type='text' name='userid' value='<s:property value='userid'/>' size='10' maxlength='245' style='vertical-align: bottom;'>
							&nbsp;&nbsp;&nbsp;
							姓名：<input type='text' name='userName' value='<s:property value='userName'/>' size='20' maxlength='245' style='vertical-align: bottom;'>
          					 -->
          					 已判卷：<input type='radio' name='searchVal' value='1' <s:if test="searchVal == 1">checked</s:if> >&nbsp;&nbsp;&nbsp;&nbsp;
          					 未判卷：<input type='radio' name='searchVal' value='0' <s:if test="searchVal == 0">checked</s:if> >
          					</td>
          					<td align="center"><input type="image" src="/entity/function/images/search.gif" width="99" height="19" /></td>
	          			</s:if>
                            <td align="center"><a href="/entity/studyZone/courseResources_enterTestpaper.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='paper_id'/>" class="tj">[返回试卷列表]</a></td>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
		</form>

              <tr>
                <td align="center">
				<table width="812" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="/entity/function/images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">试卷名称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">答题人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">答题时间</td>
					<s:if test="roleCode == 3">
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">操作</td>
					</s:if>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                 <s:iterator value='testpaperHistoryList' id='history' status='stus'>
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<s:if test="#stus.odd">#E8F9FF</s:if><s:else>#ffffff</s:else>"> 
                            <td width="5%" align="center" valign="middle" class="td1"><img src="/entity/function/images/xb2.gif" width="8" height="8" border="0"></td>
                            <td width="24%"  class="td1"><a href="/entity/studyZone/courseResources_viewHistoryInfo.action?history_id=<s:property value='#history[0].id'/>" target=_blank><s:property value='#history[1]'/></a></td>
                            <td width="18%" align="center"  class="td1"><s:property value='#history[2]'/></td>
                            <td width="17%" align="center"  class="td1"><s:date name='#history[0].testDate' format='yyyy-MM-dd hh:mm:ss'/></td>
						<s:if test="roleCode == 3">	
                            <td width="15%" align="center"  class="td1">
                            <a href="/entity/studyZone/courseResources_testhistoryCheck.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&history_id=<s:property value='#history[0].id'/>&curPage=<s:property value="curPage"/>" class="button">批改</a>
                            <a href="javascript:cfmdel('/entity/studyZone/courseResources_deleteHistory.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&paper_id=<s:property value='paper_id'/>&history_id=<s:property value='#history[0].id'/>&curPage=<s:property value="curPage"/>')" class="button">删除</a>
				            </td>
						</s:if>
                          </tr>
                        </table>
                      </td>
                    </tr>
                </s:iterator>    
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
			<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td align="center" style="font-size:12px" background="/entity/function/images/bottom02.gif">
                       	共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
			            	<s:if test="curPage == 1">
			            		<font color="gray">首页 上一页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_viewTestHistoryPaper.action?paper_id=<s:property value="paper_id"/>&test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=1">首页</a>
			            		<a href="/entity/studyZone/courseResources_viewTestHistoryPaper.action?paper_id=<s:property value="paper_id"/>&test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_viewTestHistoryPaper.action?paper_id=<s:property value="paper_id"/>&test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage+1}"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_viewTestHistoryPaper.action?paper_id=<s:property value="paper_id"/>&test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="totalPage"/>">尾页</a>
			            	</s:else>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        
        </tr>
      </table>
</body>
</html>
