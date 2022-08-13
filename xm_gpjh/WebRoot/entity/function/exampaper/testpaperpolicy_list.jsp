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
	if(confirm("您确定要删除此组卷策略吗？"))
		window.navigate(link);
}
</script>
</head>
<%!
	String fixnull(String str) {
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
		return str;
	}
%>	
<%
	String title = fixnull(request.getParameter("title"));
	String type = fixnull(request.getParameter("type"));
	String paperId = fixnull(request.getParameter("paperId"));
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		int totalItems;
		totalItems = testManage.getPaperPolicysNum(null,title,null,null,null,null,"exam",teachclass_id);
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
		String link = "&title="+title+"&type="+type+"&paperId="+paperId;
		//----------分页结束---------------
		List testPolicyList = testManage.getPaperPolicys(pageover,null,title,null,null,null,null,"exam",teachclass_id);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/zxzc.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="90%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center" width="30%"><a href="testpaper_list.jsp" class="tj">[查看试卷]</a>&nbsp;</td>
                            <td align="center" ><img src="../images/xb.gif" width="48" height="32"></td>
                            <td width="70%">
                            	<form method="post" action="testpaperpolicy_list.jsp" name="paper_listSearchForm">
                            	<input type="hidden" name="paperId" value=<%=paperId%>/>
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" class="mc1">按标题搜索：</td>
                            			<td align="center"><input name="title" type="text" size="20" maxlength="50" value=<%=title%>></td>
                            		</tr>                            
                            	</table>
                            </td>
                            	<td align="center"><input type="image" src="../images/search.gif" width="99" height="19" /></td>
                            </form>
                            
                            <td>&nbsp;</td>
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
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="20%" align="center" class="title">策略用途</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=testPolicyList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	PaperPolicy testPaperPolicy = (PaperPolicy)it.next();
                             	String policyId = testPaperPolicy.getId();
								String policyTitle = testPaperPolicy.getTitle();
								String creatUser = testPaperPolicy.getCreatUser();
								String creatDate = testPaperPolicy.getCreatDate();
								String policyType = testPaperPolicy.getType();
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            <td width="5%" align="center" valign="middle" class="td1" onClick="window.open('paperpolicy_info.jsp?id=<%=testPaperPolicy.getId()%>','','dialogWidth=800px;dialogHeight=500px')"><a href="" onclick="return false;"><img src="images/xb2.gif" width="8" height="8" border="0"></a></td>
                            <td width="24%"  class="td1"><a href="#" onclick="window.open('paperpolicy_info.jsp?id=<%=testPaperPolicy.getId()%>','','dialogWidth=800px;dialogHeight=500px,scrollbars=yes')"><%=policyTitle%></a></td>
                            <td width="18%" align="center"  class="td1"><%int index = creatUser.indexOf(")"); String str=creatUser.substring(index+1); %><%=str%></td>
                            <td width="17%" align="left"  class="td1"><%=creatDate%></td>
                            <td width="21%" align="center"  class="td1">在线考试</td>
                            <td width="15%" align="center"  class="td1">
                     		<%if(!type.equals("list")){%>
                            <a href="testpaper_add_bypolicy.jsp?id=<%=policyId%>&paperId=<%=paperId%>">组卷</a>
                      		<%}%>
                            <a href="javascript:cfmdel('paperpolicy_delexe.jsp?title=<%=title%>&type=<%=type%>&pageInt=<%=pageInt%>&paperId=<%=paperId%>&policyId=<%=policyId%>')" class="button">删除</a>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    	<%  
                       		trNo++;	
                          	}
                        %> 
                    
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                       <%@ include file="../pub/dividepage.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="806" height="7"></td>
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
