<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

		<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:15px 0px;">
		  <tr>
			<td align="center" class="moreblack" > <a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=1">首页</a>
		<s:if test="curPage == 1">
			<s:if test="totalPage > 1">
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="nextPage"/>">下一页</a>  
			</s:if>
		</s:if>
		<s:elseif test="curPage > 1">
			<s:if test="totalPage > curPage">
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="prePage"/>">上一页</a>   
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="nextPage"/>">下一页</a>
			</s:if>
			<s:elseif test="curPage == totalPage">
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="prePage"/>">上一页</a>  
			</s:elseif>
		</s:elseif>		
			
			  <a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="totalPage" />">末页</a>   
			共<span class="orange"><s:property value="totalCount" /></span>篇文章 <span class="orange"><s:property value="number" /></span>篇文章/页  
			页次：<span class="orange"><s:property value="curPage" /></span>/<s:property value="totalPage" />页</td>
		  </tr>
		</table>