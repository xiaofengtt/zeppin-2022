<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<script language="javascript">
			function pageGuarding()
			{
				var xPage = document.jump.pageNo.value;
				var maxPage = document.jump.maxPage.value;

				var isError = false;
				
				for (var i=0;i<xPage.length;i++)
				{
					var k = xPage.charAt(i);
					if ((k<'0') || (k>'9'))
					{
						isError=true;
						break;
					}
				}
				if ((xPage=="0")||(xPage=="")||parseInt(xPage,10)>parseInt(maxPage,10)|| parseInt(xPage,10)<1)
				{
					
					isError=true;
				}

				if (isError)
				{
					alert("输入页数错误，该页无法跳转！");
					document.jump.pageNo.vaule="";
					document.jump.pageNo.focus();
					document.jump.pageNo.select();
					return false;
				}
				else
					return true;
			}
</script>

<table border="0" cellpadding="2" cellspacing="1" align="center" width=100%>
	<form name="jump" method="post" action="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action" onsubmit="return pageGuarding()">
	<tr>
	
		<td align=center width=40% style="font-family: Tahoma,Georgia;font-size: 9pt;"> 
		<s:if test="curPage == 1">
			<s:if test="totalPage > 1">
				<a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=<s:property value="curPage+1"/>"><img src="/entity/student/images/xyy.gif" width="57" height="20" border="0"></a> <a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=<s:property value="totalPage"/>"><img src="/entity/student/images/my.gif" width="57" height="20" border="0"></a>
			</s:if>
		</s:if>
		<s:elseif test="curPage > 1">
			<s:if test="totalPage > curPage">
				<a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=1"><img src="/entity/student/images/sy.gif" width="57" height="20" border="0"></a> <a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=<s:property value="curPage-1"/>"><img src="/entity/student/images/syy.gif" width="58" height="20" border="0"></a> 
				<a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=<s:property value="curPage+1"/>"><img src="/entity/student/images/xyy.gif" width="57" height="20" border="0"></a> <a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=<s:property value="totalPage"/>"><img src="/entity/student/images/my.gif" width="57" height="20" border="0"></a>
			</s:if>
			<s:elseif test="curPage == totalPage">
				<a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=1"><img src="/entity/student/images/sy.gif" width="57" height="20" border="0"></a> <a href="/entity/workspaceTeacher/tchPeBulletinView_toPageBulletin.action?curPage=<s:property value="curPage-1"/>"><img src="/entity/student/images/syy.gif" width="58" height="20" border="0"></a>
			</s:elseif>
		</s:elseif>
		</td>
		<td align="center" width=20% style="font-family: Tahoma,Georgia;font-size: 9pt;"> 
         共<s:property value="totalPage"/>页 &nbsp;&nbsp;第<s:property value="curPage"/>页&nbsp;&nbsp;&nbsp; 
		</td>
		<td align="center" width=20% style="font-family: Tahoma,Georgia;font-size: 9pt;">
			跳转到第<input  type="text" name="pageNo" type="text" size="3" maxlength="5">页
			<input type=hidden name=maxPage value="<s:property value="totalPage"/>" >
	    </td>
	    <td align="center">
			<input name=Submit type=image src="/entity/student/images/go.gif" width="40" height="20" border=0>
		</td>

		
		<td align=center width=25% style="font-family: Tahoma,Georgia;font-size: 9pt;">
    	每页 <s:property value="pageLimit"/> 条
		</td>
	</tr>
	</form>	
</table>
