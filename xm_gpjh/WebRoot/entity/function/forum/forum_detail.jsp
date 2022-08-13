<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.forum.*"%>
<%@ page import="com.whaty.platform.interaction.forum.ForumList" %>

<%@ include file="../pub/priv.jsp"%>

<script  language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除吗？"))
		window.navigate(link);
}
</script>

<%
	String topic_id = request.getParameter("realted_id");
	String forumlist_id = request.getParameter("forumlist_id");
	
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	Forum forum=interactionManage.getForum(topic_id);
		
	//List searchProperty = new ArrayList();
	//searchProperty.add(new SearchProperty("topic_id", topic_id, "="));
%>

<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/tlq.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="680" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="60" valign="bottom" background="../images/answer_01.gif"> 
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> 
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top"><img src="../images/answer_xb.gif" width="15" height="16"></td>
                            <td class="text4" style="padding-left:8px">主题: <%=forum.getTitle()%></td>
  </tr>
</table>
                            </td>
                          </tr>
                          <tr align="center"> 
                            <td class="mc1"> 发布时间：<%=forum.getDate()%>&nbsp;&nbsp;&nbsp;作者: <font color="red"><%=forum.getUserName()%></font></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="../images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="../images/answer_03.gif"><table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="../images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" class="text2"><%=forum.getBody()%></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="../images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td height="23" align="center" background="../images/answer_03.gif">
                      	  <a href="retopic_add.jsp?topic_id=<%=forum.getId()%>&forumlist_id=<%=forum.getForumListId()%>" class="tj">[回复]</a> &nbsp;
<%                          
    		if((user != null && forum.getSubmitUserId().equals(user.getId())) || "teacher".equals(userType))
    		{ 
%>                        	  
	                      <a href="javascript:navigate('forum_edit.jsp?id=<%=forum.getId()%>&realted_id=<%=topic_id%>&forumlist_id=<%=forum.getForumListId()%>')" class="tj">[修改问题]</a> &nbsp;
	                      <a href="javascript:cfmdel('forum_delexe.jsp?id=<%=forum.getId()%>&forumlist_id=<%=forum.getForumListId()%>')" class="tj">[删除主题(包括文章)]</a> &nbsp;
<%
			}
%>            			  <%
								if(userType.equalsIgnoreCase("teacher")) {
						  %>
	                      <a href="#" class="tj">[收入常见问题库]</a>&nbsp; 
	                      <%
	                      		}
	                      %>
	                      <a href="javascript:window.close()" class="tj">[关闭]</a> 
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="../images/answer_07.gif" width="680" height="19"></td>
                    </tr>
                    <tr>
                      <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> <table border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td valign="top"><img src="../images/answer_xb.gif" width="15" height="16"></td>
                                  <td class="text4" style="padding-left:8px">回 
                                    帖  </td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                     
<%
       
        List forumList = interactionManage.getReplyForums(null, topic_id);	
        
        for(int i=0;i<forumList.size();i++)	        	  	
		{
    	Forum reforum = (Forum)forumList.get(i);    	
    	     
%>
                    <tr>
                      <td align="center"><table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><%=reforum.getUserName()%></font> 
                              发布时间：<%=reforum.getDate()%></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td valign="top" class="bg4">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <!-- tr> 
                            <td><img src="../images/answer_10.gif" width="11" height="11">&nbsp;<strong><span class="text2">思问哲学网改版以大体完成</span></strong></td>
                          </tr -->
                          <tr> 
                            <td class="text2"><%=reforum.getBody()%></td>
                          </tr>
<%                          
    		if((user != null && reforum.getSubmitUserId().equals(user.getId())) || "teacher".endsWith(userType))
    		{ 
%>    		                           
                          <tr> 
                            <td align="center"><a href="forum_edit.jsp?id=<%=reforum.getId()%>&realted_id=<%=topic_id%>&forumlist_id=<%=forum.getForumListId()%>" class="tj">[修改回帖]</a> 
                              <a href="javascript:cfmdel('forum_delexe.jsp?id=<%=reforum.getId()%>&realted_id=<%=topic_id%>&forumlist_id=<%=forum.getForumListId()%>')"  class="tj">[删除此回帖] </a> </td>
                          </tr>
<%			
			}
%>                          
                          <tr> 
                            <td height="9" background="../images/answer_09.gif"> 
                            </td>
                          </tr>
                        </table>
                        <br>
                      </td>
<%
		}
%>                      
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
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
	out.print(e);
	return;
}
%>
