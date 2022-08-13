<script language="javascript">
			function pageGuarding()
			{
				var xPage = document.jump.pageInt.value;
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

				if (!isError && ((xPage=="0")||(xPage=="")||parseInt(xPage)>parseInt(maxPage)|| parseInt(xPage)<1))
				{
					
					isError=true;
				}

				if (isError)
				{
					alert("输入页数错误，该页无法跳转！");
					document.jump.pageInt.focus();
					document.jump.pageInt.select();
					return false;
				}
				else
					return true;
			}
</script>
<%
	String servletPath = request.getRequestURI();// getServletPath should work
	int j = servletPath.indexOf('?');		// here but doesn't, so we
	if (j != -1)					 // remove query by hand...
		servletPath = servletPath.substring(0, j);
%>




<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <%--<tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
        --%><tr>
          <td valign="middle"> 
            <!-- start:内容区－分页 -->
            <table width='100%' align="center" cellpadding='1' cellspacing='0'>
              <tr> 
              	<%
	              	if (maxPage == 0)
	              		maxPage=1;
              	%>
                <th width="46" align="center" style='white-space: nowrap;padding-top:8px;'>
                
                </th>
                
                <form name="setsize" method="post" action="/entity/manager/statistics/pub/set_pagesize.jsp" onsubmit="return checkPagesize()">
                <th width='30%' style='color:#666666;white-space: nowrap;'>
                <%
					String path = servletPath + "?" + link;
					String tmp_pagesize = (String)session.getValue("pagesize");
					
					if (tmp_pagesize == null || tmp_pagesize.length() == 0 || tmp_pagesize.equals("null"))
					{
						tmp_pagesize = "10";
						session.putValue("pagesize", tmp_pagesize);
					}
				%> <%=pageInt %>/<%=maxPage%>页 &nbsp;&nbsp;共:<%=totalItems%>条&nbsp;                 
                  每页 
                  <select name="pagesize" onChange="document.setsize.submit()">
                    <%
				String pagesizenum[] = new String[13];
				pagesizenum[0] = "10";
				pagesizenum[1] = "15";
				pagesizenum[2] = "20";
				pagesizenum[3] = "25";
				pagesizenum[4] = "30";
				pagesizenum[5] = "35";
				pagesizenum[6] = "40";
				pagesizenum[7] = "45";
				pagesizenum[8] = "50";
				pagesizenum[9] = "100";
				pagesizenum[10] = "200";
				pagesizenum[11] = "300";
				pagesizenum[12] = "500";
						
						for (int i = 0; i < pagesizenum.length; i ++) {
							if (tmp_pagesize.equals(pagesizenum[i])) {	
					%>
							<option value="<%=pagesizenum[i]%>" selected><%=pagesizenum[i]%></option>
					<%
							} else {
					%>
							<option value="<%=pagesizenum[i]%>"><%=pagesizenum[i]%></option>
					<%
							}
						}
					%>
                  </select>
				</th>
				<input type="hidden" name="path" value="<%=path%>">
				</form>
				
                <th width='46%' style='white-space: nowrap;'> 
                	<table width="70%" height="22" border="0" cellpadding="0" cellspacing="0">
            	    	<tr align="center"> 
            	    	<%
	            	    	if(pageInt==1 && maxPage>1) {
						%>
							<td><a href="<%=servletPath%>?pageInt=<%=pageNext+link%>" title="下一页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">下一页</span></span></a></td>
              				<td><a href="<%=servletPath%>?pageInt=<%=maxPage+link%>" title="末页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">末页</span></span></a></td>
              			<%
              				} else if(pageInt>1 && pageInt<maxPage) {
              			%>
                        	<td><a href="<%=servletPath%>?pageInt=<%=1+link%>" title="首页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">首页</span></span></a></td>
              				<td><a href="<%=servletPath%>?pageInt=<%=pageLast+link%>" title="上一页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">上一页</span></span></a></td>
              				<td><a href="<%=servletPath%>?pageInt=<%=pageNext+link%>" title="下一页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">下一页</span></span></a></td>
              				<td><a href="<%=servletPath%>?pageInt=<%=maxPage+link%>" title="末页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">末页</span></span></a></td>
              			<%
              				} else if(pageInt==maxPage && pageInt>1) {
              			%>
              				<td><a href="<%=servletPath%>?pageInt=<%=1+link%>" title="首页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">首页</span></span></a></td>
              				<td><a href="<%=servletPath%>?pageInt=<%=pageLast+link%>" title="上一页" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">上一页</span></span></a></td>
              			<%
              				}
              			%>
            			</tr>
          			</table>
          		</th>
          		<form name="jump" method="post" action="<%=servletPath+"?"+link%>" onsubmit="return pageGuarding()">
        		<th width='24%' style='color:#666666;white-space: nowrap;'>跳转到第<input name="pageInt" type="text" size="4" style="height:16px">页<input name="maxPage" type="hidden" value=<%=maxPage%>></th>
        		<th width='0' style='white-space: nowrap;'><span unselectable="on" class="norm"  style="width:20px;padding-top:2px;padding-bottom:2px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><input type="image" src="/entity/manager/statistics/images/buttons/pageJump.png" alt="跳转" width="20" height="18" class="button"></span></th>
      		  	</form>
      		  </tr>
    		</table>
	        <!-- end:内容区－分页 -->
          </td>
        </tr>
</table>
