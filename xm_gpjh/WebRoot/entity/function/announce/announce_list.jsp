<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>

<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%!
	String fixnull(String str){
		if(str==null || str.equalsIgnoreCase("null"))
			return "";
		else
			return str.trim();
	}
%>
<%
	String title = fixnull(request.getParameter("title"));
	String publisher_name = fixnull(request.getParameter("publisher_name"));
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	int totalItems;
	totalItems=interactionManage.getAnnouncesNum(teachclass_id, title, publisher_name);
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
		String link = "&title=" + title + "&publisher_name=" + publisher_name;
		//----------分页结束---------------
	List announceList = interactionManage.getAnnounces(pageover,teachclass_id, title, publisher_name);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/ggl.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="95%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
<%
							if("teacher".equals(userType))
							{
%>
                            <td align="center">
                            	<a href="announce_add.jsp" class="tj">[添加新公告]</a>
                            </td>
<%
							}
%>
                            <td align="center"><img src="../images/xb.gif" width="48" height="32"></td>
                            <td>
                            	<form method="post" action="announce_list.jsp" name="announce_listSearchForm">
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" class="mc1">按名称搜索：</td>
                            			<td align="center"><input name="title" type="text" size="10" maxlength="50" value="<%=title%>"/></td>
                            			<td align="center" class="mc1">按发布人搜索：</td>
                            			<td align="center"><input name="publisher_name" type="text" size="10" maxlength="50" value="<%=publisher_name%>"/></td>
                            		</tr>                            
                            	</table>
                            </td>
                            	<td align="center"><input type="image" src="../images/search.gif" width="99" height="19" /></td>
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
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                      
                <td height="26" background="../images/tabletop.gif" style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='../images/tabletop.gif');">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    	<tr>
                      	<td width="5%" height="17">&nbsp;</td>
                      	<td width="58%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
                      	<td width="18%" align="center" class="title">发布人</td>
                      	<td align="center" class="title">发布时间</td>
                      	<td width="4%">&nbsp;</td>
                    	</tr>
                  	</table>
                  </td>
                 </tr>
                    
                    <tr>
                      <td align="center" background="../images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
						<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=announceList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	Announce announce = (Announce)it.next();
						
						%>
                       
                         <tr> 
                            <td bgcolor="<%=trBgcolor%>" width="5%" align="center" valign="middle"  class="td1"><img src="images/xb2.gif" width="8" height="8"></td>
<!--                        <td bgcolor="<%=trBgcolor%>" width="58%" align="center" class="td1"><a href="announce_detail.jsp?id=<%=announce.getId()%>&pageInt=<%=pageInt%>"><font color="blue"><u> <%=announce.getTitle()%> </u></font></a></td>   -->
                            <td bgcolor="<%=trBgcolor%>" width="58%" align="center" class="td1"><a href="announce_detail.jsp?id=<%=announce.getId()%>&pageInt=<%=pageInt%>&title=<%=title%>&publisher_name=<%=publisher_name%>"><%=announce.getTitle()%></a></td>
                            <td bgcolor="<%=trBgcolor%>" width="18%" align="center"  class="td1"><%=announce.getPublisherName()%></td>
                            <td bgcolor="<%=trBgcolor%>" align="center"  class="td1"><%=announce.getDate()%></td>
                            <td bgcolor="<%=trBgcolor%>" width="40" align="center"  class="td1">&nbsp;</td>
                          </tr>
                        <%  
                       		trNo++;	
                          	}
                        %>     
                          
                        </table>
                      </td>
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
