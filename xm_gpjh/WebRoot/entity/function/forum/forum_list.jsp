<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.forum.*"%>

<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主题列表</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%!
	String fixNull(String s)
	{
		if(s == null || s.equalsIgnoreCase("null"))
			return "";
		return s;
	}
	
%>
<%
	String forumlist_id = fixNull(request.getParameter("forumlist_id"));
	String title = fixNull(request.getParameter("title"));
	String submit_user_name = fixNull(request.getParameter("submit_user_name"));
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
		
		int totalItems = interactionManage.getTopicForumsNum(forumlist_id, title, submit_user_name);

		//----------分页开始---------------
		String spagesize = (String) session.getValue("pagesize");
		
		String spageInt = request.getParameter("pageInt");
		
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "&forumlist_id=" + forumlist_id + "&title=" + title + "&submit_user_name";
		//----------分页结束---------------
		List forum_list = interactionManage.getTopicForums(pageover, forumlist_id, title, submit_user_name);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/tlq.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="95%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center"><a href="forumlist_elite.jsp?forumId=<%=forumlist_id %>" class="tj">[精华区]</a>&nbsp;<a href="forum_add.jsp?forumlist_id=<%=forumlist_id%>" class="tj">[新主题]</a></td>
                            <td align="center"><img src="../images/xb.gif" width="48" height="32"></td>
                            <td>
                            	<form method="post" action="forum_list.jsp?forumlist_id=<%=forumlist_id%>" name="searchForm">
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" class="mc1">按主题搜索：</td>
                            			<td align="center"><input name="title" type="text" size="20" maxlength="50" /></td>
                            		</tr>
                            		<tr>
                            			<td align="center" class="mc1">按作者搜索：</td>
                            			<td align="center"><input name="submit_user_name" type="text" size="20" maxlength="50" /></td>
                            		</tr>                            
                            	</table>
                            	</form>
                            </td>
                           	<td align="center">
                           		<a href="javascript:document.searchForm.submit()"><img src="../images/search.gif" width="99" height="19" border="0"/></a>
                           	</td>
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
                      
                <td height="26" background="../images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    	<tr>
                      	<td width="15%" height="17">&nbsp;</td>
                      	<td width="48%" align="center" class="title">主&nbsp;&nbsp;&nbsp;&nbsp;题</td>
                      	<td width="18%" align="center" class="title">作者</td>
                      	<td align="center" class="title">时间</td>
                      	<td width="4%">&nbsp;</td>
                    	</tr>
                  	</table>
                  </td>
                 </tr>
                    
                    <tr>
                      <td align="center" background="../images/tablebian.gif">
                      	<FORM name="threads" action="" method="post">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
						<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=forum_list.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	Forum forum = (Forum)it.next();
						
						%>
                       
                         <tr> 
                            <td bgcolor="<%=trBgcolor%>" width="15%" align="center" valign="middle"  class="td1"><img src="images/xb2.gif" width="8" height="8">
                            	<%
                            		if(userType.equals("teacher")) {
                            	%>
                            	<a href="forumlist_elite_add.jsp?forumId=<%=forumlist_id%>&threadId=<%=forum.getId()%>" class="tj">[收入精华区]</a>
                            	<%
                            		}
                            	%>
                            </td>
                            <td bgcolor="<%=trBgcolor%>" width="48%" align="center" class="td1"><a href="forum_detail.jsp?realted_id=<%=forum.getId()%>&pageInt=<%=pageInt%>&forumlist_id=<%=forumlist_id%>" target="_blank"><%=forum.getTitle()%></a></td>
                            <td bgcolor="<%=trBgcolor%>" width="18%" align="center"  class="td1"><%=forum.getUserName() %></td>
                            <td bgcolor="<%=trBgcolor%>" align="center"  class="td1"><%=forum.getDate()%></td>
                            <td bgcolor="<%=trBgcolor%>" width="40" align="center"  class="td1">&nbsp;</td>
                          </tr>
                        <%  
                       		trNo++;	
                          	}
                        %>     
                        
                        </table>
                        </FORM>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
					<%@ include file="../pub/dividepage.jsp"%>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
