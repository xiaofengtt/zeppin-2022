<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel()
{
	if(confirm("您确定要删除此资料吗？")) {
		return true;
	}
	return false;	
}
function DetailInfo(paperId)
{
	window.open('homeworkpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>

<s:if test="msg != '' and msg != null">
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
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                    <td width="35">&nbsp;</td>
                    <td   class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>资料下载管理</strong></td>   
                     </tr>
                     </table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="left" valign="top"> 
                        <table width="80%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center"><s:if test="roleCode == 3"><a href="/entity/function/kczl/homeworkpaper_add.jsp?type=KCZL&course_id=<s:property value="course_id"/>" class="tj">[添加参考资料]</a></s:if>&nbsp;</td>
                            <form method="post" action="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value='course_id'/>" name="searchForm" >
                            <td align="right">
                            	<table border="0" cellpadding="0" cellspacing="0"  width="100%">
                            		<tr>
                            			<td align="right" class="mc1">按标题搜索：</td>
                            			<td align="left"><input name="searchVal" type="text" size="20" maxlength="50" value="<s:property value='searchVal'/>"/></td>
                            			<td align="left"><input type="image" src="/entity/function/images/search.gif" width="99" height="19" /></td>
                            		</tr>                            
                            	</table>
                            </td>
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
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="/entity/function/images/tabletop.gif" style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='/entity/function/images/tabletop.gif');">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="45%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="13%" align="center" class="title">发布时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="30%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                 <s:iterator value="resList" id="res">
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor=""> 
                           	<td width="45%"  align="center"  class="td1"><a href="/entity/studyZone/courseResources_viewZL.action?r_id=<s:property value="#res.id"/>&course_id=<s:property value="#res.teachclassId"/>"><s:property value="#res.title"/></a></td>
                            <td width="15%" align="center"  class="td1"><s:date name="#res.publishDate" format="yyyy-MM-dd hh:mm:ss"/></td>
                            <s:if test="roleCode == 3">
	                            <td width="30%" align="center"  class="td1">
	                            <a href="/entity/studyZone/courseResources_editZL.action?r_id=<s:property value="#res.id"/>&course_id=<s:property value="#res.teachclassId"/>">编辑</a>
	                            <a href="/entity/studyZone/courseResources_deleteZL.action?r_id=<s:property value="#res.id"/>&course_id=<s:property value="#res.teachclassId"/>" onclick="return cfmdel()" class="button">删除</a>
					            </td>
					        </s:if>
					        <s:else>
					        	<td width="30%" align="center"  class="td1">
	                            <a href="/entity/studyZone/courseResources_viewZL.action?r_id=<s:property value="#res.id"/>&course_id=<s:property value="#res.teachclassId"/>">浏览</a>
					            </td>
					        </s:else>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </s:iterator>
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="99%" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td align="center" style="font-size:12px" background="/entity/function/kczl/images/bottom02.gif">
                      	共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
			            	<s:if test="curPage == 1">
			            		<font color="gray">首页 上一页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value="course_id"/>&curPage=1&searchVal=<s:property value="searchVal"/>">首页</a>
			            		<a href="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>&searchVal=<s:property value="searchVal"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value="course_id"/>&&curPage=<s:property value="%{curPage+1}"/>&searchVal=<s:property value="searchVal"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value="course_id"/>&&curPage=<s:property value="totalPage"/>&searchVal=<s:property value="searchVal"/>">尾页</a>
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
