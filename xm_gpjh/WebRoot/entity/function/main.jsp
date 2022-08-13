<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>

<%@ include file="./pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>�ޱĵ�</title>
<link href="css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
    <td valign="top" class="bg3">
    <table width="790" border="0" align="center" cellpadding="0" cellspacing="0">
        <!-- tr> 
          <td height="60">&nbsp;</td>
        </tr-->
        <tr> 
          <td><img src="images/index_01.gif" width="790" height="15"></td>
        </tr>
        <tr>
          <td><img src="images/index_02.gif" width="790" height="102"></td>
        </tr>
        <tr>
          <td valign="top" background="images/index_04.gif">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="10" rowspan="3">&nbsp;</td>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/index_06.gif" width="186" height="213"></td>
                      <td width="400" align="center" valign="top" background="images/index_07.gif"> 
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="6%">&nbsp;</td>
                            <td valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="29" align="center" background="images/index_10.gif" class="text3"><strong>最新公告</strong></td>
                                </tr>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	int totalItems=interactionManage.getAnnouncesNum(teachclass_id, null, null);
	//----------分页开始---------------
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize("4");
		pageover.setPageInt("1");
	//----------分页结束---------------
	List announceList = interactionManage.getAnnounces(pageover,teachclass_id, null, null);
%>
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<%
    List itemList=interactionManage.getTheachItem(courseId);
  String item="";
  String dayi="";
  String gonggao="";
  String taolun="";
  String kaoshi="";
  String zuoye="";
  String ziyuan="";
  List list = new ArrayList();
  Iterator it = list.iterator();
 if(itemList!=null)
 {
    for(int i=0;i<itemList.size();i++){
           CourseItem techItem=(CourseItem) itemList.get(i);
           gonggao=fixnull(techItem.getGonggao());
           dayi=fixnull(techItem.getDayi());  
           taolun=fixnull(techItem.getTaolun());
           kaoshi=fixnull(techItem.getKaoshi());
           zuoye=fixnull(techItem.getZuoye());
           ziyuan=fixnull(techItem.getZiyuan());
           if(gonggao.equals("1"))
           	list.add("<a href='./announce/announce_list.jsp'>公告栏</a>");
           if(dayi.equals("1"))
           	list.add("<a href='./answer/index.jsp'>答 疑</a>");
           if(taolun.equals("1"))
           	list.add("<a href='./forum/forumlist_list.jsp'>讨论区</a>");
           if(kaoshi.equals("1"))
           	list.add("<a href='./testpaper/onlinetestcourse_list.jsp'>在线考试</a>");
           if(zuoye.equals("1"))
           	list.add("<a href='./homeworkpaper/homeworkpaper_list.jsp'>在线作业</a>");
           if("teacher".equalsIgnoreCase(userType))
           	list.add("<a href='./lore/lore_dir_list.jsp'>知识点</a>");
           if(ziyuan.equals("1"))
           	list.add("<a href='./resource/course_resource.jsp'>资 源</a>");
   	}
   	it = list.iterator();
 }
%>              

                                <tr> 
                                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<%
                          	Iterator its=announceList.iterator();
                          	while(its.hasNext()){
                             	Announce announce = (Announce)its.next();
						%>
                                      <tr>
                                        <td width="5%" align="center" class="td1"><img src="images/ct_09.gif" width="14" height="14"></td>
                                        <td class="td1"><a href="./announce/announce_detail.jsp?id=<%=announce.getId()%>" target="_blank"><%=announce.getTitle()%></a></td>
                                        <td width="45%" align="center" class="td1"><%=announce.getDate()%></td>
                                      </tr>
						<%
							}
						%>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="8%">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td width="184" rowspan="3"><img src="images/index_05.gif" width="184" height="370"></td>
                <td width="10" rowspan="3">&nbsp;</td>
              </tr>
              <tr> 
                <td height="157" align="right" valign="top" background="images/index_08.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="12">&nbsp;</td>
                      <td width="80" valign="top" style="padding-top:10px">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center"><img src="images/indexxb1.gif" width="51" height="53"></td>
                          </tr>
                          <tr>
                            <td align="center">
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
                            </td>
                          </tr>
                        </table></td>
                      <td width="80" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="center" valign="top" style="padding-top:2px"><img src="images/indexxb2.gif" width="52" height="46"></td>
                          </tr>
                          <tr> 
                            <td align="center">                            
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
							</td>
                          </tr>
                        </table></td>
                      <td width="80" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="center" valign="top" style="padding-top:3px"><img src="images/indexxb3.gif" width="39" height="42"></td>
                          </tr>
                          <tr> 
                            <td align="center">                            
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
							</td>
                          </tr>
                        </table></td>
                      <td width="80" valign="top" style="padding-top:22px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="center" valign="top" ><img src="images/indexxb4.gif" width="39" height="42"></td>
                          </tr>
                          <tr> 
                            <td align="center">                            
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
							</td>
                          </tr>
                        </table></td>
                      <td width="80" valign="top" style="padding-top:55px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="center" valign="top" ><img src="images/indexxb5.gif" width="29" height="29"></td>
                          </tr>
                          <tr> 
                            <td align="center">
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
							</td>
                          </tr>
                        </table></td>
                      <td width="80" valign="top" style="padding-top:68px">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="center" valign="top" ><img src="images/indexxb6.gif" width="24" height="23"></td>
                          </tr>
                          <tr> 
                            <td align="center">
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
							</td>
                          </tr>
                        </table>
                      </td>
                      <td width="80" valign="top" style="padding-top:66px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="center" valign="top" ><img src="images/indexxb7.gif" width="20" height="21"></td>
                          </tr>
                          <tr> 
                            <td align="center">
                            <%
                            if(it.hasNext())
                            {
                            	out.print((String)it.next());
                            }
                            else
                            {
                            	out.print("&nbsp;");
                            }
                            %>
							</td>
                          </tr>
                        </table></td>
                      <td width="15">&nbsp;</td>
                    </tr>
                  </table>
				  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="right"><a href="#" onclick="javascript:parent.window.close()"><img src="images/exit.gif" width="35" height="35" border="0"></a></td>
  </tr>
</table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><img src="images/index_03.gif" width="790" height="13"></td>
        </tr>
      </table></td>
        </tr>
      </table>
</body>
</html>
