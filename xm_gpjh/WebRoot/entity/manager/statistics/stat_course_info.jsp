<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page  import="java.util.*"%>
<%@ page import="com.whaty.platform.test.question.*"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Element"%>
<%@page import="com.whaty.platform.database.oracle.dbpool"%>
<%@page import="com.whaty.platform.database.oracle.MyResultSet"%>
<%@ include file="./pub/priv.jsp"%>
<html>
<head>
</head>
<body background="images/bg2.gif">
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">作&nbsp;&nbsp;业</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<%
						String paperId = request.getParameter("paperid");
						String user_id = request.getParameter("userid");
						
						dbpool db = new dbpool();
						MyResultSet rs = null;
						MyResultSet rs1 = null;
						String sql = "";
						sql = "select hy.id as id,hy.TEST_RESULT as testresult from test_homeworkpaper_history hy where hy.user_id like '%"+user_id+"%' and hy.TESTPAPER_ID ='"+paperId+"'";
						rs = db.executeQuery(sql);
						String totalscore = ""; //总分
						while(rs!=null&&rs.next()){
							
							String btltle =""; //第几题
							String sanswer=""; //标准答案
							String uanswer=""; //用户答案
							String sscore =""; //题目分数
							String uscore =""; //用户得分
							
							String testresult = rs.getString("testresult");
							Document doc = DocumentHelper.parseText(testresult);
							List itemlist = doc.selectNodes("/answers/item");
							for(Iterator it =itemlist.iterator();it.hasNext();){
								Element element = (Element)it.next();
								totalscore = element.selectSingleNode("/answers/totalscore").getText();
								btltle = element.element("title").getTextTrim();
								String typeEle = element.element("type").getTextTrim();
								sanswer = element.element("sanswer").getTextTrim();
								uanswer = element.element("uanswer").getTextTrim();
								if(typeEle.equalsIgnoreCase(TestQuestionType.PANDUAN)){
									if(uanswer.equals("0"))
										uanswer = "错误";
									if(uanswer.equals("1"))
										uanswer = "正确";
									if(sanswer.equals("0"))
										sanswer = "错误";
									if(sanswer.equals("1"))
										sanswer = "正确";
								}
								if(typeEle.equalsIgnoreCase(TestQuestionType.DUOXUAN)){
								if(sanswer!=null&&!sanswer.equals("")&&!sanswer.equals("null")&&uanswer!=null)
								{
									sanswer = sanswer.replace('|',',');
									uanswer = uanswer.replace('|',',');
								}
								else
								{
									sanswer = "未答此题！";
								}
								}
								sscore = element.element("sscore").getTextTrim();
								uscore = element.element("uscore").getTextTrim();
								
								String id = element.element("id").getTextTrim();
								sql="select po.id as id ,po.QUESTIONCORE as QUESTION from test_paperquestion_info po where po.id='"
								+id+"'  and  po.TESTPAPER_ID ='"+paperId+"'";
								rs1 = db.executeQuery(sql);
								while(rs1!=null&&rs1.next()){
									String question = rs1.getString("QUESTION");
									Document dm = DocumentHelper.parseText(question);
									String body = dm.selectSingleNode("/question/body").getText();
									List list = dm.selectNodes("/question/select/item");
									%>
									<tr> 
			                            <td valign="middle" align="left" style="font-size:14px;color:#7f7f7f;line-height:24px"><%=btltle %> &nbsp; <%=body %></td>
			                        </tr>
									<%
									for(Iterator itt = list.iterator();itt.hasNext();){
										Element ele = (Element)itt.next();
										String index = ele.element("index").getTextTrim();
										String content = ele.element("content").getTextTrim();
										String san  = index+". "+content;
									%>
			                          <tr> 
			                            <td valign="middle" align="left"> &nbsp; &nbsp; &nbsp; &nbsp;<span style="font-size:13px;line-height:24px">
											<%=san %></span>
			                            </td>
			                          </tr>
			                          <%
									}
									%>
									<tr> 
                           			 <td valign="middle" align="left" style="font-size:13px;color:red;line-height:24px"> &nbsp; &nbsp; &nbsp;标准答案: &nbsp; &nbsp; <%=sanswer%></td>
                          			</tr>
									<tr> 
                           			 <td valign="middle" align="left" style="font-size:13px;color:blue;line-height:24px"> &nbsp; &nbsp; &nbsp;您的答案: &nbsp; &nbsp; <%=uanswer%></td>
                          			</tr>
                          			<tr> 
                           			 <td valign="middle" align="left" style="font-size:13px;line-height:24px"> &nbsp; &nbsp; &nbsp;题目分数: &nbsp; &nbsp; <%=sscore%></td>
                          			</tr>
                          			<tr>
                          				<td>&nbsp;</td>
                          			</tr>
									<%
									
							}
								db.close(rs1);
							}
						}		
						db.close(rs);
						%>
                         
                       <!--    <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;" line-height:24px">作业总得分：<span style="font-size:13px;color:red;"><%=totalscore%></span></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">作业总批注：</td>
                          </tr>  -->
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/gb.gif" width="80" height="24" onclick="window.close()"></td><!--
			                      <td class="text"><img src="images/zjfh.gif" width="100" height="30" onclick="window.location.href='homeworkpaper_list.jsp'"></td>
			                    --></tr>
			                  </table></td>
			              </tr>
                        </table><br>
                      </td>
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

