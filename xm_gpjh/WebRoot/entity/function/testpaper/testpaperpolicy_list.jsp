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
	if(confirm("您确定要删除此组卷策略吗？"))
		window.navigate(link);
}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
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
                      <td align="right" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center" nowrap="nowrap" width="30%"><a href="testpaper_list.jsp" class="tj">[查看试卷]</a>&nbsp;</td>
                            <td align="center"><img src="/entity/function/images/xb.gif" width="48" height="32"></td>
                            <td>
                            	<form method="post" action="/entity/studyZone/courseResources_enterTestpaperpolicy.action" name="paper_listSearchForm">
                            	<input type="hidden" name="test_id" value=<s:property value='test_id'/> />
                            	<input type="hidden" name="course_id" value=<s:property value='course_id'/> />
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" nowrap="nowrap" class="mc1">按标题搜索：</td>
                            			<td align="center"><input name="searchVal" type="text" size="20" maxlength="50" value=<s:property value='searchVal'/> ></td>
                            		</tr>                            
                            	</table>
                            </td>
                            	<td align="center"><input type="image" src="/entity/function/images/search.gif" width="99" height="19" /></td>
                            </form>
                            
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="812" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="/entity/function/images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="20%" align="center" class="title">策略用途</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <s:if test="roleCode == 3">
                      	<td width="15%" align="center" class="title">操作</td>
                      </s:if>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                 <s:iterator value="testpaperPolicyList" id="policy" status="stas">
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<s:if test='#stas.odd'>#ffffff</s:if><s:else>#E8F9FF</s:else>"> 
                            <td width="5%" align="center" valign="middle" class="td1" onClick="window.open('paperpolicy_info.jsp?id=<s:property value='#policy.id'/>','','dialogWidth=800px;dialogHeight=500px')"><a href="" onclick="return false;"><img src="/entity/function/images/xb2.gif" width="8" height="8" border="0"></a></td>
                            <td width="24%"  class="td1"><a href="#" onclick="window.open('paperpolicy_info.jsp?id=<s:property value='#policy.id'/>','','dialogWidth=800px;dialogHeight=500px,scrollbars=yes')"><s:property value='#policy.title'/></a></td>
                            <td width="18%" align="center"  class="td1"><s:property value='#policy.creatuser'/></td>
                            <td width="17%" align="left"  class="td1"><s:date name="#policy.creatdate" format="yyyy-MM-dd"/></td>
                            <td width="21%" align="center"  class="td1">在线自测</td>
							<s:if test="roleCode == 3">
                            	<td width="15%" align="center"  class="td1">
                     			<s:if test="type == 'list'">
                            		<a href="testpaper_add_bypolicy.jsp?id=<s:property value='#policy.id'/>&test_id=<s:property value='test_id'/>">组卷</a>
                      			</s:if>
                      			<s:else>
                            		<a href="javascript:cfmdel('paperpolicy_delexe.jsp?type=<s:property value='type'/>&test_id=<s:property value='test_id'/>&policyId=<s:property value='#policy.id'/>')" class="button">删除</a>
                            	</s:else>
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
			            		<a href="/entity/studyZone/courseResources_enterTestpaperpolicy.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=1">首页</a>
			            		<a href="/entity/studyZone/courseResources_enterTestpaperpolicy.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_enterTestpaperpolicy.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="%{curPage+1}"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_enterTestpaperpolicy.action?test_id=<s:property value="test_id"/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="totalPage"/>">尾页</a>
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
