<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<br>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                          <td width = "8">&nbsp;</td>
                          <td   class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>课程答疑</strong></td> 
                          </tr>
                          </table>
                          <br>
<table width="85%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="/entity/function/images/dy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
                    <tr> 
                      <td align="right" valign="top"> 
                      	<form name = "search" method="post" action="/entity/studyZone/courseResources_questionList.action">
                        <input type="hidden" name="course_id" value="<s:property value="course_id"/>" />
                       
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="left">
                            	<s:if test="roleCode==0"><a href="/entity/studyZone/courseResources_addQuestion.action?course_id=<s:property value="course_id"/>" class="tj">[我要提问]</a></s:if>
                            </td>
                            <td align="center"><img src="/entity/function/images/xb.gif" width="48" height="32"></td>
                            <td align="center" class="mc1">按问题名称搜索：</td>
                            <td align="center">
								<input name="searchTitle" type="text" size="10" maxlength="50" value=""> </td>
                            <td align="center" class="mc1">按发布人搜索：</td>
                            <td align="center">
								<input name="searchCreater" type="text" size="10" maxlength="50" value=""></td>
                            <td align="center"><a href="javascript:document.search.submit()"><img src="/entity/function/images/search.gif" width="99" height="19" border="0"></a></td>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                        </form>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              
              
              
              <tr>
                <td align="center">
<table width="85%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="/entity/function/images/tabletop.gif">
                <table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="40%" align="left" class="title">问&nbsp;&nbsp;&nbsp;&nbsp;题</td>
                      <td width="10%" align="center" class="title">是否回答</td>                      
                      <td width="15%" align="center" class="title">发布人</td>
                      <td width="15%" align="center" class="title">发布时间</td>                      
                    </tr>
                  </table></td>
                    </tr>
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
<table width="85%" border="0" cellspacing="0" cellpadding="0" class="mc1">
           	<s:iterator value="questionList" id="q" status="stas">                                                                                           
              <tr <s:if test="#stas.odd">bgcolor="E8F9FF"</s:if>> 
                <td width="5%" align="center" valign="middle"  class="td1"><img src="/entity/function/images/xb2.gif" width="8" height="8"></td>
                <td width="40%" align="left" class="td1"><a href="/entity/studyZone/courseResources_quesDetail.action?ids=<s:property value="#q.id"/>&course_id=<s:property value="course_id"/>"><font color="blue"><u><s:property value="#q.title"/></u></font> <a>&nbsp;<!-- a href="answer_list.jsp?id=">[答案列表]</a--></td>
                <td width="10%" align="center"  class="td1"><s:if test="#q.enumConstByFlagSolve.code== 1">是</s:if><s:else>否</s:else></td>                
                <td width="15%" align="center"  class="td1"><s:property value="#q.peTrainee.trueName"/></td>
                <td width="15%" align="center"  class="td1"><s:date name="#q.createDate" format="yyyy-MM-dd"/></td>
              </tr>
			</s:iterator>           
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
			                           
              <tr>
                <td align="center">
					<table width="85%" border="0" cellspacing="0" cellpadding="0">
						<tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td align="center" style="font-size:12px" background="/entity/function/images/bottom02.gif">
                      	第 <s:property value="curPage"/> 页
                       	共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
			            	<s:if test="curPage == 1">
			            		<font color="gray">首页 上一页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="course_id"/>&curPage=1&searchTitle=<s:property value="searchTitle"/>&searchCreater=<s:property value="searchCreater"/>">首页</a>
			            		<a href="/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>&searchTitle=<s:property value="searchTitle"/>&searchCreater=<s:property value="searchCreater"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage+1}"/>&searchTitle=<s:property value="searchTitle"/>&searchCreater=<s:property value="searchCreater"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="course_id"/>&curPage=<s:property value="totalPage"/>&searchTitle=<s:property value="searchTitle"/>&searchCreater=<s:property value="searchCreater"/>">尾页</a>
			            	</s:else>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="100%" height="7"></td>
                    </tr>
            		</table>
	            </td>
       		 </tr>
        
      </table>
</body>
</html>
 