<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_2</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="../js/check.js"></script>
<SCRIPT src="../js/add.js"></SCRIPT>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;	
	}
%>
<%
	String id = fixnull(request.getParameter("id"));
	String title = "";
	String note = "";
	String[] questiontypes = request.getParameterValues("questiontype_select");
	String[] lores = request.getParameterValues("lore_select");
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		PaperPolicy paperPolicy = testManage.getPaperPolicy(id);
		title = paperPolicy.getTitle();
		note = paperPolicy.getNote();
		PaperPolicyCore paperPolicyCore = testManage.getPaperPolicyCore(paperPolicy.getPolicyCore());
		String lore = "";
		List loreList = testManage.getLores(null,paperPolicyCore.getLore());
		for(Iterator it=loreList.iterator();it.hasNext();)
		{
			lore+=","+((Lore)it.next()).getName();
		}
		lore = lore.substring(1);
		String questiontype = "";
		List questiontypeList = paperPolicyCore.getTestQuestionType();
		for(Iterator it=questiontypeList.iterator();it.hasNext();)
		{
			questiontype+=","+TestQuestionType.typeShow((String)it.next());
		}
		questiontype = questiontype.substring(1);
    	List list = new ArrayList();
    	HashMap con = new HashMap();
%>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="160" valign="top" class="text3" style="padding-top:25px">详细信息</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td valign="top" class="pageBodyBorder"> 
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" align="left" valign="bottom" style="padding-right:15px"><span class="name">标题：</span></td>
              <td> <%=title%> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" align="left" valign="bottom" style="padding-right:15px"><span class="name">备注：</span></td>
              <td> <%=fixnull(note)%> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" align="left" valign="bottom" style="padding-right:15px"><span class="name">知识点：</span></td>
              <td> <%=lore%> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" align="left" valign="bottom" style="padding-right:15px"><span class="name">题型：</span></td>
              <td> <%=questiontype%> &nbsp;</td>
            </tr>
          </table>
    </td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder"> 
    <%
    	if(questiontypeList.contains("DANXUAN"))
    	{
			list = paperPolicyCore.getTestQuestionConfig("DANXUAN");
			if(list!=null && list.size()>0)
				con = (HashMap)list.get(0);
			else
				con = null;
		}
	%>
        <div class="border" name="DANXUAN" id="DANXUAN" style="display:none">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >单选题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：</span></td>
              <td> 
            <%
            	String[] num = null;
            	String[] totalscore = null;
            	String[] diff = null;
            	String[] time = null;
            	String[] score = null;
		        String[] cognizetype = null;
            	List subList = new ArrayList();
            	if(con!=null)
            	{
            		num = (String[])con.get("A");
            		totalscore = (String[])con.get("B");
            		diff = (String[])con.get("C");
            		time = (String[])con.get("D");
            		score = (String[])con.get("E");
            		cognizetype = (String[])con.get("F");
                    subList = (List)con.get("lore");
            	}
            	else
            	{
            		num = new String[]{""};
            		totalscore = new String[]{""};
            		diff = new String[]{"0.0","1.0"};
            		time = new String[]{"",""};
            		score = new String[]{"",""};
            		cognizetype = new String[]{};
                    subList = new ArrayList();
                    subList.add("");
            	}
            %>
              <%=num[0]%> 
			&nbsp;</td>
            </tr>
            <%
            	if(totalscore!=null&&totalscore.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <%=totalscore[0]%>  
              </td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 <%=diff[0]%> 到 <%=diff[1]%></td>
            </tr>
            <%
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <%=time[0]%>秒&nbsp;
                到 
                <%=time[1]%>秒&nbsp;</td>
            </tr>
            <%
            	}
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <%=score[0]%>分&nbsp;
                到 
                <%=score[1]%>分&nbsp;</td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	String cognizetypestr = "";
                      	for(int i=0;i<cognizetype.length;i++)
                      		cognizetypestr+=","+CognizeType.typeShow(cognizetype[i]);
                      	if(cognizetypestr.length()>0)
							cognizetypestr = cognizetypestr.substring(1);
                      	%>
                      	<%=cognizetypestr%>
                  </td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	String lorestr = "";
						loreList = testManage.getLores(null,subList);
						for(Iterator it=loreList.iterator();it.hasNext();)
						{
							lorestr+=","+((Lore)it.next()).getName();
						}
                      	if(lorestr.length()>0)
							lorestr = lorestr.substring(1);
                      	%>
                      	<%=lorestr%>
				</td>
            </tr>
          </table>
          </td>
        </tr>
      </table>
      </div>
    </td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder" > 
    <%
    	if(questiontypeList.contains("DUOXUAN"))
    	{
			list = paperPolicyCore.getTestQuestionConfig("DUOXUAN");
			if(list!=null && list.size()>0)
				con = (HashMap)list.get(0);
			else
				con = null;
		}
	%>
        <div class="border" name="DUOXUAN" id="DUOXUAN" style="display:none">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >多选题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：</span></td>
              <td> 
            <%
            	if(con!=null)
            	{
            		num = (String[])con.get("A");
            		totalscore = (String[])con.get("B");
            		diff = (String[])con.get("C");
            		time = (String[])con.get("D");
            		score = (String[])con.get("E");
            		cognizetype = (String[])con.get("F");
                    subList = (List)con.get("lore");
            	}
            	else
            	{
            		num = new String[]{""};
            		totalscore = new String[]{""};
            		diff = new String[]{"0.0","1.0"};
            		time = new String[]{"",""};
            		score = new String[]{"",""};
            		cognizetype = new String[]{};
                    subList = new ArrayList();
                    subList.add("");
            	}
            %>
              <%=num[0]%> 
			&nbsp;</td>
            </tr>
            <%
            	if(totalscore!=null&&totalscore.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <%=totalscore[0]%>  
              </td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 <%=diff[0]%> 到 <%=diff[1]%></td>
            </tr>
            <%
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <%=time[0]%>秒&nbsp;
                到 
                <%=time[1]%>秒&nbsp;</td>
            </tr>
            <%
            	}
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <%=score[0]%>分&nbsp;
                到 
                <%=score[1]%>分&nbsp;</td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	cognizetypestr = "";
                      	for(int i=0;i<cognizetype.length;i++)
                      		cognizetypestr+=","+CognizeType.typeShow(cognizetype[i]);
                      	if(cognizetypestr.length()>0)
							cognizetypestr = cognizetypestr.substring(1);
                      	%>
                      	<%=cognizetypestr%>
				</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	lorestr = "";
						loreList = testManage.getLores(null,subList);
						for(Iterator it=loreList.iterator();it.hasNext();)
						{
							lorestr+=","+((Lore)it.next()).getName();
						}
                      	if(lorestr.length()>0)
							lorestr = lorestr.substring(1);
                      	%>
                      	<%=lorestr%>
				</td>
            </tr>
          </table>
          </td>
	    </tr>
	  </table>
	  </div>
	</td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder" > 
    <%
    	if(questiontypeList.contains("TIANKONG"))
    	{
			list = paperPolicyCore.getTestQuestionConfig("TIANKONG");
			if(list!=null && list.size()>0)
				con = (HashMap)list.get(0);
			else
				con = null;
		}
	%>
        <div class="border" name="TIANKONG" id="TIANKONG" style="display:none">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >填空题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：</span></td>
              <td> 
            <%
            	if(con!=null)
            	{
            		num = (String[])con.get("A");
            		totalscore = (String[])con.get("B");
            		diff = (String[])con.get("C");
            		time = (String[])con.get("D");
            		score = (String[])con.get("E");
            		cognizetype = (String[])con.get("F");
                    subList = (List)con.get("lore");
            	}
            	else
            	{
            		num = new String[]{""};
            		totalscore = new String[]{""};
            		diff = new String[]{"0.0","1.0"};
            		time = new String[]{"",""};
            		score = new String[]{"",""};
            		cognizetype = new String[]{};
                    subList = new ArrayList();
                    subList.add("");
            	}
            %>
              <%=num[0]%> 
			&nbsp;</td>
            </tr>
            <%
            	if(totalscore!=null&&totalscore.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <%=totalscore[0]%>  
              </td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 <%=diff[0]%> 到 <%=diff[1]%></td>
            </tr>
            <%
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <%=time[0]%>秒&nbsp;
                到 
                <%=time[1]%>秒&nbsp;</td>
            </tr>
            <%
            	}
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <%=score[0]%>分&nbsp;
                到 
                <%=score[1]%>分&nbsp;</td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	cognizetypestr = "";
                      	for(int i=0;i<cognizetype.length;i++)
                      		cognizetypestr+=","+CognizeType.typeShow(cognizetype[i]);
                      	if(cognizetypestr.length()>0)
							cognizetypestr = cognizetypestr.substring(1);
                      	%>
                      	<%=cognizetypestr%>
				</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	lorestr = "";
						loreList = testManage.getLores(null,subList);
						for(Iterator it=loreList.iterator();it.hasNext();)
						{
							lorestr+=","+((Lore)it.next()).getName();
						}
                      	if(lorestr.length()>0)
							lorestr = lorestr.substring(1);
                      	%>
                      	<%=lorestr%>
				</td>
            </tr>
          </table>
          </td>
	    </tr>
	  </table>
	  </div>
	</td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder" > 
    <%
    	if(questiontypeList.contains("PANDUAN"))
    	{
			list = paperPolicyCore.getTestQuestionConfig("PANDUAN");
			if(list!=null && list.size()>0)
				con = (HashMap)list.get(0);
			else
				con = null;
		}
	%>
        <div class="border" name="PANDUAN" id="PANDUAN" style="display:none">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >判断题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：</span></td>
              <td> 
            <%
            	if(con!=null)
            	{
            		num = (String[])con.get("A");
            		totalscore = (String[])con.get("B");
            		diff = (String[])con.get("C");
            		time = (String[])con.get("D");
            		score = (String[])con.get("E");
            		cognizetype = (String[])con.get("F");
                    subList = (List)con.get("lore");
            	}
            	else
            	{
            		num = new String[]{""};
            		totalscore = new String[]{""};
            		diff = new String[]{"0.0","1.0"};
            		time = new String[]{"",""};
            		score = new String[]{"",""};
            		cognizetype = new String[]{};
                    subList = new ArrayList();
                    subList.add("");
            	}
            %>
              <%=num[0]%> 
			&nbsp;</td>
            </tr>
            <%
            	if(totalscore!=null&&totalscore.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <%=totalscore[0]%>  
              </td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 <%=diff[0]%> 到 <%=diff[1]%></td>
            </tr>
            <%
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <%=time[0]%>秒&nbsp;
                到 
                <%=time[1]%>秒&nbsp;</td>
            </tr>
            <%
            	}
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <%=score[0]%>分&nbsp;
                到 
                <%=score[1]%>分&nbsp;</td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	cognizetypestr = "";
                      	for(int i=0;i<cognizetype.length;i++)
                      		cognizetypestr+=","+CognizeType.typeShow(cognizetype[i]);
                      	if(cognizetypestr.length()>0)
							cognizetypestr = cognizetypestr.substring(1);
                      	%>
                      	<%=cognizetypestr%>
				</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	lorestr = "";
						loreList = testManage.getLores(null,subList);
						for(Iterator it=loreList.iterator();it.hasNext();)
						{
							lorestr+=","+((Lore)it.next()).getName();
						}
                      	if(lorestr.length()>0)
							lorestr = lorestr.substring(1);
                      	%>
                      	<%=lorestr%>
				</td>
            </tr>
          </table>
          </td>
	    </tr>
	  </table>
	  </div>
	</td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder" > 
    <%
    	if(questiontypeList.contains("WENDA"))
    	{
			list = paperPolicyCore.getTestQuestionConfig("WENDA");
			if(list!=null && list.size()>0)
				con = (HashMap)list.get(0);
			else
				con = null;
		}
	%>
        <div class="border" name="WENDA" id="WENDA" style="display:none">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >问答题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：</span></td>
              <td> 
            <%
            	if(con!=null)
            	{
            		num = (String[])con.get("A");
            		totalscore = (String[])con.get("B");
            		diff = (String[])con.get("C");
            		time = (String[])con.get("D");
            		score = (String[])con.get("E");
            		cognizetype = (String[])con.get("F");
                    subList = (List)con.get("lore");
            	}
            	else
            	{
            		num = new String[]{""};
            		totalscore = new String[]{""};
            		diff = new String[]{"0.0","1.0"};
            		time = new String[]{"",""};
            		score = new String[]{"",""};
            		cognizetype = new String[]{};
                    subList = new ArrayList();
                    subList.add("");
            	}
            %>
              <%=num[0]%> 
			&nbsp;</td>
            </tr>
            <%
            	if(totalscore!=null&&totalscore.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <%=totalscore[0]%>  
              </td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 <%=diff[0]%> 到 <%=diff[1]%></td>
            </tr>
            <%
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <%=time[0]%>秒&nbsp;
                到 
                <%=time[1]%>秒&nbsp;</td>
            </tr>
            <%
            	}
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <%=score[0]%>分&nbsp;
                到 
                <%=score[1]%>分&nbsp;</td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	cognizetypestr = "";
                      	for(int i=0;i<cognizetype.length;i++)
                      		cognizetypestr+=","+CognizeType.typeShow(cognizetype[i]);
                      	if(cognizetypestr.length()>0)
							cognizetypestr = cognizetypestr.substring(1);
                      	%>
                      	<%=cognizetypestr%>
				</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	lorestr = "";
						loreList = testManage.getLores(null,subList);
						for(Iterator it=loreList.iterator();it.hasNext();)
						{
							lorestr+=","+((Lore)it.next()).getName();
						}
                      	if(lorestr.length()>0)
							lorestr = lorestr.substring(1);
                      	%>
                      	<%=lorestr%>
				</td>
            </tr>
          </table>
          </td>
	    </tr>
	  </table>
	  </div>
	</td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder" > 
    <%
    	if(questiontypeList.contains("YUEDU"))
    	{
			list = paperPolicyCore.getTestQuestionConfig("YUEDU");
			if(list!=null && list.size()>0)
				con = (HashMap)list.get(0);
			else
				con = null;
		}
	%>
        <div class="border" name="YUEDU" id="YUEDU" style="display:none">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >阅读理解题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：</span></td>
              <td> 
            <%
            	if(con!=null)
            	{
            		num = (String[])con.get("A");
            		totalscore = (String[])con.get("B");
            		diff = (String[])con.get("C");
            		time = (String[])con.get("D");
            		score = (String[])con.get("E");
            		cognizetype = (String[])con.get("F");
                    subList = (List)con.get("lore");
            	}
            	else
            	{
            		num = new String[]{""};
            		totalscore = new String[]{""};
            		diff = new String[]{"0.0","1.0"};
            		time = new String[]{"",""};
            		score = new String[]{"",""};
            		cognizetype = new String[]{};
                    subList = new ArrayList();
                    subList.add("");
            	}
            %>
              <%=num[0]%> 
			&nbsp;</td>
            </tr>
            <%
            	if(totalscore!=null&&totalscore.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <%=totalscore[0]%>  
              </td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 <%=diff[0]%> 到 <%=diff[1]%></td>
            </tr>
            <%
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <%=time[0]%>秒&nbsp;
                到 
                <%=time[1]%>秒&nbsp;</td>
            </tr>
            <%
            	}
            	if(time!=null && time.length>0)
            	{
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <%=score[0]%>分&nbsp;
                到 
                <%=score[1]%>分&nbsp;</td>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	cognizetypestr = "";
                      	for(int i=0;i<cognizetype.length;i++)
                      		cognizetypestr+=","+CognizeType.typeShow(cognizetype[i]);
                      	if(cognizetypestr.length()>0)
							cognizetypestr = cognizetypestr.substring(1);
                      	%>
                      	<%=cognizetypestr%>
				</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点： </span> 
              </td>
                    <td valign="bottom" > 
                      <%
                      	lorestr = "";
						loreList = testManage.getLores(null,subList);
						for(Iterator it=loreList.iterator();it.hasNext();)
						{
							lorestr+=","+((Lore)it.next()).getName();
						}
                      	if(lorestr.length()>0)
							lorestr = lorestr.substring(1);
                      	%>
                      	<%=lorestr%>
				</td>
            </tr>
          </table>
          </td>
	    </tr>
	  </table>
	  </div>
	</td>
  </tr>
<script>
	function changediv(div)
	{
		document.getElementById(div).style.display=''
	}
</script>
<%
	if(questiontypeList!=null)
	{
		for(Iterator it=questiontypeList.iterator();it.hasNext();)
		{
			%>
			<script>
			changediv('<%=(String)it.next()%>');
			</script>
			<%
		}
	}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
  	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="#" class="tj" onclick="javascript:window.close()">[关闭]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</body>
</html>
