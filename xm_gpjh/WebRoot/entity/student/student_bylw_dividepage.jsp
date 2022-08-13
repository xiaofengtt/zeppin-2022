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
<table border="0" cellpadding="2" cellspacing="1" align="center" width=100% class="tab_dc_Mid">
	<tr>
	<form name="jump" method="post" action="/info/firstPage_getInfoList.action?type=880&property=bylw" onsubmit="return pageGuarding()">
	  <td align=center width=40% style="font-family: Tahoma,Georgia;font-size: 9pt;"> 
		<s:if test="curPage == 1">
			<s:if test="totalPage > 1">
				<a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=<s:property value="curPage+1"/>"><img src="/entity/images/xyy1.gif" width="47" height="20" border="0"></a> <a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=<s:property value="totalPage"/>"><img src="/entity/images/my1.gif" width="47" height="20" border="0"></a>			</s:if>
		</s:if>
		<s:elseif test="curPage > 1">
			<s:if test="totalPage > curPage">
				<a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=1"><img src="/entity/images/sy1.gif" width="47" height="20" border="0"></a> <a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=<s:property value="prePage"/>"><img src="/entity/images/syy1.gif" width="47" height="20" border="0" /></a> 
				<a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=<s:property value="nextPage"/>"><img src="/entity/images/xyy1.gif" width="47" height="20" border="0"></a> <a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=<s:property value="totalPage"/>"><img src="/entity/images/my1.gif" width="47" height="20" border="0"></a>			</s:if>
			<s:elseif test="curPage == totalPage">
				<a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=1"><img src="/entity/images/sy1.gif" width="47" height="20" border="0"></a> <a href="/info/firstPage_getInfoList.action?type=880&property=bylw&curPage=<s:property value="prePage"/>"><img src="/entity/images/syy1.gif" width="47" height="20" border="0"></a>			</s:elseif>
		</s:elseif>		</td>
		<td align="center" width=20% style="font-family: Tahoma,Georgia;font-size: 9pt;"> 
         共<s:property value="totalPage"/>页 &nbsp;&nbsp;第<s:property value="curPage"/>页&nbsp;&nbsp;&nbsp;		</td>
		<td align="center" width=20% style="font-family: Tahoma,Georgia;font-size: 9pt;">
			跳转到第<input type="text" name="pageNo" type="text" size="3" maxlength="5">页
			<input type=hidden name=maxPage value="<s:property value="totalPage"/>">	    </td>
	    <td align="center">
			<input name=Submit type=image src="/entity/images/go1.gif" width="40" height="20" border=0>		</td>
	</form>
		
	<form name="setsize" method="post" action="/info/firstPage_getInfoList.action?type=880&property=bylw&isLimit=1">
		<td align=center width=25% style="font-family: Tahoma,Georgia;font-size: 9pt;">
    	每页
			<select name="limit" onChange="document.setsize.submit()">
				<option value="10" <s:if test="limit == 10">selected</s:if> >10</option>
				<option value="20" <s:if test="limit == 20">selected</s:if> >20</option>
				<option value="30" <s:if test="limit == 30">selected</s:if> >30</option>
				<option value="50" <s:if test="limit == 50">selected</s:if> >50</option>
				<option value="100" <s:if test="limit == 100">selected</s:if> >100</option>
			</select>
    	条		</td>
		<input type="hidden" name="path" value="<s:property value="totalPage"/>">
	</form>
	</tr>
</table>
</body>