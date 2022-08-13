<%@ page contentType="text/html;charset=UTF-8" %>
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

				if ((xPage=="0")||(xPage=="")||parseInt(xPage)>parseInt(maxPage)|| parseInt(xPage)<1)
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

					document.jump.submit();
			}
</script>
<%
	String servletPath = request.getRequestURI();// getServletPath should work
	int j = servletPath.indexOf('?');		// here but doesn't, so we
	if (j != -1)					 // remove query by hand...
		servletPath = servletPath.substring(0, j);
%>

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
                            <td height="53" background="../images/middle_04.gif">
                            <!-- start:内容区－分页 -->
                            <table  border="0" align="center" cellpadding="0" cellspacing="0">
                              	<%
					              	if (maxPage == 0)
					              		maxPage=1;
				              	%>
                                <tr>
		                        <%
			            	    	if(pageInt==1 && maxPage>1) {
								%>
									<td width="55"><a href="<%=servletPath%>?pageInt=<%=pageNext+link%>" title="下一页" class="button"><img src="../images/xyy.gif" width="55" height="19" border="0"></a></td>
		              				<td width="55"><a href="<%=servletPath%>?pageInt=<%=maxPage+link%>" title="末页" class="button"><img src="../images/my.gif" width="55" height="19" border="0"</a></td>
		              			<%
		              				} else if(pageInt>1 && pageInt<maxPage) {
		              			%>
		                        	<td width="55"><a href="<%=servletPath%>?pageInt=<%=1+link%>"  title="首页"><img src="../images/sy.gif" width="55" height="19" border="0"></a></td>
		              				<td width="55"><a href="<%=servletPath%>?pageInt=<%=pageLast+link%>" title="上一页"><img src="../images/syy.gif" width="55" height="19" border="0"></a></td>
		              				<td width="55"><a href="<%=servletPath%>?pageInt=<%=pageNext+link%>" title="下一页"><img src="../images/xyy.gif" width="55" height="19" border="0"></a></td>
		              				<td width="55"><a href="<%=servletPath%>?pageInt=<%=maxPage+link%>" title="末页"><img src="../images/my.gif" width="55" height="19" border="0"></a></td>
		              			<%
		              				} else if(pageInt==maxPage && pageInt>1) {
		              			%>
		              				<td width="55"><a href="<%=servletPath%>?pageInt=<%=1+link%>" title="首页"><img src="../images/sy.gif" width="55" height="19" border="0"></a></td>
		              				<td width="55"><a href="<%=servletPath%>?pageInt=<%=pageLast+link%>" title="上一页"><img src="../images/syy.gif" width="55" height="19" border="0"></a></td>
		              			<%
		              				}
		              			%>        
                                  
					                <form name="setsize" method="post" action="pub/set_pagesize.jsp" onsubmit="return checkPagesize()">
					                <%
										String path = servletPath + "?" + link;
										String tmp_pagesize = (String)session.getValue("pagesize");
										
										if (tmp_pagesize == null || tmp_pagesize.length() == 0 || tmp_pagesize.equals("null"))
										{
											tmp_pagesize = "10";
											session.putValue("pagesize", tmp_pagesize);
										}
									%> 
									<td width="36" align="left" valign="bottom" class="12content">共<%=maxPage%>页</td>
                                    <td width="36" align="left" valign="bottom" class="12content">第<%=pageInt %>页</td>               
					                <td width="44">
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
					                   <td width="14" align="right" class="12content">条</td>  
									<input type="hidden" name="path" value="/">
									</form>        
									                  
					      		  <form name="jump" method="post" action="<%=servletPath+"?"+link%>" onsubmit="">	
                                  <td width="22" align="left" class="12content" ></td>									
                                  <td  width="52"class="12content">跳到<input name="pageInt" type="text" class="tz"><input name="maxPage" type="hidden" value=<%=maxPage%>></td>
								  <td class="12content" width="14">页</td>
                                  <td width="38" class="12content"><a href="#" onclick="return pageGuarding()" class="button"><img src="../images/go.gif" width="30" height="15" border="0"></a></td>
								  
								  </form>	
                                </tr>
                                <!-- end:内容区－分页 -->
                              </table></td>
        </tr>    
</table>