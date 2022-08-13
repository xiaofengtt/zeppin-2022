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
	<tr>
	
	<form name="setsize" method="post" action="<s:property value="uri"/>?isLimit=1<s:property value="link"/>">
    	<td width='30%' style='color:#666666;white-space: nowrap;' class="mc1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    	<font size=2>页次:<s:property value="curPage"/>/<s:property value="totalPage"/>页&nbsp&nbsp&nbsp&nbsp共:<s:property value="totalCount"/>条</font>
		每页<select name="limit" onChange="document.setsize.submit()">
				<option value="10" <s:if test="limit == 10">selected</s:if> >10</option>
				<option value="20" <s:if test="limit == 20">selected</s:if> >20</option>
				<option value="30" <s:if test="limit == 30">selected</s:if> >30</option>
				<option value="50" <s:if test="limit == 50">selected</s:if> >50</option>
				<option value="100" <s:if test="limit == 100">selected</s:if> >100</option>
			</select>
		</td>
	</form>
	
	<td width='46%' style='white-space: nowrap;'>
    	<table width="70%" height="22" border="0" cellpadding="0" cellspacing="0" class="zi_1">
	    	<tr align="center">
	    		<s:if test="curPage==1 && totalPage>1">
	    			<td><a href="<s:property value="uri"/>?curPage=<s:property value="curPage+1"/><s:property value="link"/>" title="下一页" class="button"><span unselectable="on"><font size=2>下一页</font></span></a></td>
  					<td><a href="<s:property value="uri"/>?curPage=<s:property value="totalPage"/><s:property value="link"/>" title="末页" class="button"><span unselectable="on"><font size=2>末页</font></span></a></td>
	    		</s:if>
	    		
	    		<s:elseif test="curPage>1 && curPage<totoalPage">
	    			<td><a href="<s:property value="uri"/>?curPage=1<s:property value="link"/>" title="首页" class="button"><span unselectable="on"><font size=2>首页</font></span></a></td>
	  				<td><a href="<s:property value="uri"/>?curPage=<s:property value="curPage-1"/><s:property value="link"/>" title="上一页" class="button"><span unselectable="on"><font size=2>上一页</font></span></a></td>
	  				<td><a href="<s:property value="uri"/>?curPage=<s:property value="curPage+1"/><s:property value="link"/>" title="下一页" class="button"><span unselectable="on"><font size=2>下一页</font></span></a></td>
	  				<td><a href="<s:property value="uri"/>?curPage=<s:property value="totalPage"/><s:property value="link"/>" title="末页" class="button"><span unselectable="on"><font size=2>末页</font></span></a></td>
	    		</s:elseif>
	    		
	    		<s:elseif test="curPage==totalPage && curPage>1">
	    			<td><a href="<s:property value="uri"/>?curPage=1<s:property value="link"/>" title="首页" class="button"><span unselectable="on"><font size=2>首页</font></span></a></td>
  					<td><a href="<s:property value="uri"/>?curPage=<s:property value="curPage-1"/><s:property value="link"/>" title="上一页" class="button"><span unselectable="on"><font size=2>上一页</font></span></a></td>
	    		</s:elseif>
	    	</tr>
		</table>
	</td>
	
	<form name="jump" method="post" action="<s:property value="uri"/>?<s:property value="link"/>" onsubmit="return pageGuarding()">
	 	<td width=25% style="font-family: Tahoma,Georgia;font-size: 9pt;">
	 	<input type=hidden name=maxPage value="<s:property value="totalPage"/>" >
	 	
	 	
			跳转到第<input type="text" name="pageNo" type="text" size="3" maxlength="5">页<input name=Submit type=image src="/entity/function/images/go.gif" width="40" height="20" border=0>
		</td>
	</form>
	
	</tr>
</table>
