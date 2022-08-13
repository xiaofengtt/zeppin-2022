<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此作业吗？"))
		window.navigate(link);
}
function DetailInfo(paperId)
{
	window.open('homeworkpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>
</head>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>	
<%
	String type = "ZFX";
	String title = fixnull(request.getParameter("title"));
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	
	
	int totalItems = 0;
	if("teacher".equalsIgnoreCase(userType))
		totalItems = interactionManage.getTeachClassListNum(teachclass_id,type,title);
	else
	//	totalItems = interactionManage.getActiveTeachClassListNum(teachclass_id,type);
		totalItems = interactionManage.getActiveTeachClassListNum(teachclass_id,type,title);
	//----------分页开始---------------
		String spagesize = (String) session.getAttribute("pagesize");
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

		String link = "&type="+type+"&title="+title;
		//----------分页结束---------------
	List testPaperList = null;
	if("teacher".equalsIgnoreCase(userType))
//		testPaperList = interactionManage.getTeachClassList(teachclass_id,type);
		testPaperList = interactionManage.getTeachClassList(pageover, teachclass_id,type,title);
	else
//		testPaperList = interactionManage.getActiveTeachClassList(teachclass_id,type);
		testPaperList = interactionManage.getActiveTeachClassList(pageover, teachclass_id,type,title);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/zxzy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="60%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <td align="center"><a href="homeworkpaper_add.jsp?type=ZFX" class="tj">[添加复习资料]</a>&nbsp;</td>
<%
	}
%>	                            	
                            <!-- td align="center"><a href="homeworkhistory_list.jsp" class="tj">[查看已交作业]</a></td-->
                            <td>
                            	<form method="post" action="homeworkpaper_list.jsp" name="paper_listSearchForm">
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" class="mc1">按标题搜索：</td>
                            			<td align="center"><input name="title" type="text" size="20" maxlength="50" value="<%=title%>"/></td>
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
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="../images/tabletop.gif" style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='../images/tabletop.gif');">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="45%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="../images/topxb.gif"></td>
                      <td width="13%" align="center" class="title">发布时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="../images/topxb.gif"></td>
                    <%
					if("teacher".equalsIgnoreCase(userType))
					{
					%>	
                      <td width="5%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="../images/topxb.gif"></td>
                    <%
					}
					%>	
                      <td width="30%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    ;
                          	Iterator it=testPaperList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	InteractionTeachClass homeworkPaper = (InteractionTeachClass)it.next();
                             	String paperId = homeworkPaper.getId();
								String paperTitle = homeworkPaper.getTitle();
								String paperType = homeworkPaper.getType();
								String status = homeworkPaper.getStatus();
								String creatDate = homeworkPaper.getPublish_date();
								String status_link = "";
								if(status.equals("0"))
								{
									status_link = "<a href='homeworkpaper_changestatus.jsp?paperId="+paperId+"&title="+ title + "&pageInt="+pageInt+"&status=0'>无效</a>";
								}
								else
								{
									status_link = "<a href='homeworkpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=1'>有效</a>";
								}
						%>
                    <tr>
                      <td align="center" background="../images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                           	<td width="45%"  class="td1"><a href="homeworkpaper_info.jsp?id=<%=paperId%>&paperId=<%=paperId %>&type=<%=paperType%>&title=<%=title%>&pageInt=<%=pageInt%>"><%=paperTitle%></a></td>
                            
                            <td width="15%" align="center"  class="td1"><%=creatDate%></td>
                            
                            <%
							if("teacher".equalsIgnoreCase(userType))
							{
							%>	
                            <td width="6%" align="center"  class="td1">
							<%=status_link%>
                            </td>
                            <%
                            }
                          
                             %>
                              <td width="30%" align="center"  class="td1">
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>							
                            <a href="homeworkpaper_edit.jsp?id=<%=paperId%>&type=<%=paperType%>&status=<%=status%>&title=<%=title%>&pageInt=<%=pageInt%>">编辑</a>
                            <a href="javascript:cfmdel('homeworkpaper_delete.jsp?pageInt=<%=pageInt%>&paperId=<%=paperId%>&title=<%=title %>')" class="button">删除</a>
<%
	}
	else
	{
%>	 
				<a href="homeworkpaper_info.jsp?id=<%=paperId%>&type=<%=paperType%>&title=<%=title%>&pageInt=<%=pageInt%>">查看</a>
                
				
<%
	}
 %>                           	
				            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    	<%  
                    			}
                        %> 
                    
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="99%" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="../images/bottomtop.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                       <%@ include file="../pub/dividepage1.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="../images/bottom03.gif" width="100%" height="7"></td>
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
	out.print(e.getMessage());
	return;
}
%>
