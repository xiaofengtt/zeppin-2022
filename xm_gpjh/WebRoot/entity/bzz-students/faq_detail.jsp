<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.util.string.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.standard.interaction.answer.OracleQuestion"/>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.util.string.*"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%
	//gaoyuan 09-09-01 begin
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	if(us!=null)
	{
	}
	else
	{
	%>
	<script>
		window.alert("登录超时，为了您的帐户安全，请重新登录。");
		window.close();
		//window.location = "/";
	</script>
	<%
	return;
	}
	
	String id = request.getParameter("resId");
	Resource res = resManage.getResource(id);
	List contentList = res.getResourceContentList();
	ResourceContent detail = (ResourceContent)contentList.get(0);
		
	//String resId = request.getParameter("resId");
	//String xml = request.getParameter("xml");
	String xml=detail.getContent();
	
	
	xml = com.whaty.util.string.encode.HtmlEncoder.decode(xml);
	
//	System.out.println(xml);
	//xml = "<faq><author>张宏光</author><time>2007-01-15</time><body>gasdgfdsa</body><answer><reauthor>不要删除</reauthor><retime>2007-01-10</retime><rebody>asdfa</rebody></answer><answer><reauthor>不要删除</reauthor><retime>2007-01-10</retime><rebody>def</rebody></answer><answer><reauthor>不要删除</reauthor><retime>2007-01-15</retime><rebody>615111111111111</rebody></answer></faq>";
	List list = XMLParserUtil.parserFaqDetail(xml);
/*	String author = (String)list.get(0);
	String time = (String)list.get(1); 
	String body = (String)list.get(2); 
*/

	String author_temp = (String)list.get(0);// System.out.println("author_temp->"+author_temp);
//	int tei = author_temp.indexOf("--abXL8kdikvkLMbd9T--");//22

//	String author = author_temp.substring(0,tei);  //System.out.println("author->"+author);
//	String question_id = author_temp.substring(tei+21); //System.out.println("question_id->"+question_id);

	String author = (String)list.get(0);
	String question_id = (String)list.get(2);
	String body = new OracleQuestion(question_id).getBody();
	
	String time = (String)list.get(1); 
 
%>

<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3">
          	<table width="100%" border="0" cellspacing="0" cellpadding="0">              
              <tr>
                <td align="center" valign="top">
                <table width="680" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              	<tr>
              	<td bgcolor="#FFFFFF">
              	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="60" valign="bottom" background="/entity/function/images/answer_01.gif"> 
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> 
                            	<table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                	<td valign="top"><img src="/entity/function/images/answer_xb.gif" width="15" height="16"></td>
                            		<td class="text4" style="padding-left:8px">问题: </td>
  								</tr>
								</table>
                            </td>
                          </tr>
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><%=author%></font> 
                              发布时间：<%=time%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_02.gif" width="680" height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" background="/entity/function/images/answer_03.gif"><table width="94%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="22%"><img src="/entity/function/images/answer_04.gif" width="132" height="93"></td>
                            <td valign="top" class="text2"><%= body == null ? "原问题已被删除。" : body%></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_05.gif" width="680" height="12"></td>
                    </tr>
                    <tr> 
                      <td height="23" align="center" background="/entity/function/images/answer_03.gif"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/entity/function/images/answer_07.gif" width="680" height="19"></td>
                    </tr>
                    <tr>
                      <td align="center">
                      <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td align="center" valign="bottom"> 
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td valign="top"><img src="/entity/function/images/answer_xb.gif" width="15" height="16"></td>
                                  <td class="text4" style="padding-left:8px">答 案</td>
                                </tr>
                            </table>
                            </td>
                          </tr>
                      </table>
                      </td>
                    </tr>
                    
<%
if(body != null) {
        for(int i=3;i<list.size();i++) {
	    	List answer = (List)list.get(i);  
	    	String reauthor = (String)answer.get(0);
			String retime = (String)answer.get(1);
			String rebody = (String)answer.get(2);   
%>
                    <tr>
                      <td align="center">
                      <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center"> 
                            <td class="mc1">作者：<font color="#FF0000"><%=reauthor%></font> 
                              发布时间：<%=retime%></td>
                          </tr>
                      </table>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top" class="bg4">
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td class="text2"><%=rebody%></td>
                          </tr>           
                          <tr> 
                            <td height="9" background="/entity/function/images/answer_09.gif"> 
                            </td>
                          </tr>
                        </table>
                        <br>
                      </td>
<%
		}
		}
%>                      
                    </tr>
                    <tr align="center"><td> <input type="button" value="关闭" onclick="window.close()"> </td></tr>
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
