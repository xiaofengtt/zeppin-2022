<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加作业</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<script src="../js/check.js"></script>
<SCRIPT src="../js/add.js"></SCRIPT>
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "不详";
			return str;	
	}
%>
<%
	String title = fixnull(request.getParameter("title"));
	String note = fixnull(request.getParameter("note"));
	String[] questiontypes = request.getParameterValues("questiontype_select");
	String[] lores = request.getParameterValues("lore_select");
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form name='select' action='paperpolicy_addexe.jsp' method='post' class='nomargin' onsubmit="">
<input type="hidden" name="teachclass_id" value=<%=teachclass_id%>>
<input type="hidden" name="title" value="<%=title%>">
<input type="hidden" name="note" value="<%=note%>">
<input type="hidden" name="type" value="test">
<%
	for(int i=0;i<questiontypes.length;i++)
	{
%>
<input type="hidden" name="questiontype" value=<%=questiontypes[i]%>>
<%
	}
	for(int i=0;i<lores.length;i++)
	{
%>
<input type="hidden" name="lore" value=<%=lores[i].substring(0,lores[i].indexOf("~~~~"))%>>
<%
	}
%>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="60%" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="200" valign="top" class="text3" style="padding-top:25px">题型选择添加</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="100%" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="100%" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="100%" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                            	
                            	
  <tr>
        <td align="center">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center">
                                <div class="border" name="DANXUAN" id="DANXUAN" style="display:none">
<table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td colspan="2" valign="bottom" class="pageTitleText" >单选题：</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：<font color=red>*</font></span></td>
              <td> <input name="DANXUAN_num" type="text" class="selfScale"> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <input name="DANXUAN_totalscore" type="text" class="selfScale"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 
	            <select name="DANXUAN_diff_low">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0"> 1.0</option>
	            </select>
                到 	            
                <select name="DANXUAN_diff_high">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0" selected> 1.0</option>
	            </select>
				</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="DANXUAN_time_low" size="6" class="selfScale2">秒&nbsp;
                到 
                <input type="text" name="DANXUAN_time_high" size="6"  class="selfScale2">秒&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="DANXUAN_score_low" size="6" class="selfScale2">分&nbsp;
                到 
                <input type="text" name="DANXUAN_score_high" size="6"  class="selfScale2">分&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="DANXUAN_cognizetype_list" size="6" class="selfScale" multiple>
			              <option value="LIAOJIE"> 了解</option>
			              <option value="LIJIE"> 理解</option>
			              <option value="YINGYONG"> 应用</option>
			              <option value="FENXI"> 分析</option>
			              <option value="ZONGHE"> 综合</option>
			              <option value="PINGJIAN"> 评鉴</option>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" onclick="JavaScript:AddItem('DANXUAN_cognizetype_list','DANXUAN_cognizetype_select','');return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" onclick="JavaScript:DeleteItem('DANXUAN_cognizetype_select');return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="DANXUAN_cognizetype_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="DANXUAN_lore_list" size="6" class="selfScale" multiple>
                      <%
                      	String lore_id = "";
                      	String lore_name = "";
                      	for(int i=0;i<lores.length;i++)
                      	{
                      		lore_id = lores[i].substring(0,lores[i].indexOf("~~~~"));
                      		lore_name = lores[i].substring(lores[i].indexOf("~~~~")+4);
                      %>
                        <option value=<%=lore_id%>><%=lore_name%></option>
                      <%
                      	}
                      %>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('DANXUAN_lore_list','DANXUAN_lore_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('DANXUAN_lore_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="DANXUAN_lore_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
          </table>
          </div>
          </td>
  </tr>
  <tr>
        <td align="center">
        <div class="border" name="DUOXUAN" id="DUOXUAN" style="display:none">
              <table width="80%" border="0" cellpadding="0" cellspacing="0">
                  <tr valign="bottom" class="postFormBox"> 
                    <td colspan="2" valign="bottom" class="pageTitleText" >多选题：</td>
                  </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：<font color=red>*</font></span></td>
              <td> <input name="DUOXUAN_num" type="text" class="selfScale"> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <input name="DUOXUAN_totalscore" type="text" class="selfScale"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 
	            <select name="DUOXUAN_diff_low">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0"> 1.0</option>
	            </select>
                到 	            
                <select name="DUOXUAN_diff_high">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0" selected> 1.0</option>
	            </select>
				</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="DUOXUAN_time_low" size="6" class="selfScale2">秒&nbsp;
                到 
                <input type="text" name="DUOXUAN_time_high" size="6"  class="selfScale2">秒&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="DUOXUAN_score_low" size="6" class="selfScale2">分&nbsp;
                到 
                <input type="text" name="DUOXUAN_score_high" size="6"  class="selfScale2">分&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="DUOXUAN_cognizetype_list" size="6" class="selfScale" multiple>
			              <option value="LIAOJIE"> 了解</option>
			              <option value="LIJIE"> 理解</option>
			              <option value="YINGYONG"> 应用</option>
			              <option value="FENXI"> 分析</option>
			              <option value="ZONGHE"> 综合</option>
			              <option value="PINGJIAN"> 评鉴</option>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('DUOXUAN_cognizetype_list','DUOXUAN_cognizetype_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('DUOXUAN_cognizetype_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="DUOXUAN_cognizetype_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="DUOXUAN_lore_list" size="6" class="selfScale" multiple>
                      <%
                      	for(int i=0;i<lores.length;i++)
                      	{
                      		lore_id = lores[i].substring(0,lores[i].indexOf("~~~~"));
                      		lore_name = lores[i].substring(lores[i].indexOf("~~~~")+4);
                      %>
                        <option value=<%=lore_id%>><%=lore_name%></option>
                      <%
                      	}
                      %>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('DUOXUAN_lore_list','DUOXUAN_lore_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('DUOXUAN_lore_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="DUOXUAN_lore_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
			  </table>
        </div>
        </td>
  </tr>
  <tr>
        <td align="center">
        <div class="border" name="TIANKONG" id="TIANKONG" style="display:none">
              <table width="80%" border="0" cellpadding="0" cellspacing="0">
                  <tr valign="bottom" class="postFormBox"> 
                    <td colspan="2" valign="bottom" class="pageTitleText" >填空题：</td>
                  </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" align="right" nowrap="nowrap" valign="bottom" style="padding-right:15px"><span class="name">题型数量：<font color=red>*</font></span></td>
              <td> <input name="TIANKONG_num" type="text" class="selfScale"> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <input name="TIANKONG_totalscore" type="text" class="selfScale"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 
	            <select name="TIANKONG_diff_low">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0"> 1.0</option>
	            </select>
                到 	            
                <select name="TIANKONG_diff_high">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0" selected> 1.0</option>
	            </select>
				</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="TIANKONG_time_low" size="6" class="selfScale2">秒&nbsp;
                到 
                <input type="text" name="TIANKONG_time_high" size="6"  class="selfScale2">秒&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="TIANKONG_score_low" size="6" class="selfScale2">分&nbsp;
                到 
                <input type="text" name="TIANKONG_score_high" size="6"  class="selfScale2">分&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="TIANKONG_cognizetype_list" size="6" class="selfScale" multiple>
			              <option value="LIAOJIE"> 了解</option>
			              <option value="LIJIE"> 理解</option>
			              <option value="YINGYONG"> 应用</option>
			              <option value="FENXI"> 分析</option>
			              <option value="ZONGHE"> 综合</option>
			              <option value="PINGJIAN"> 评鉴</option>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('TIANKONG_cognizetype_list','TIANKONG_cognizetype_select','');return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('TIANKONG_cognizetype_select');return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="TIANKONG_cognizetype_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知识点： <font color=red>*</font></span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="TIANKONG_lore_list" size="6" class="selfScale" multiple>
                      <%
                      	for(int i=0;i<lores.length;i++)
                      	{
                      		lore_id = lores[i].substring(0,lores[i].indexOf("~~~~"));
                      		lore_name = lores[i].substring(lores[i].indexOf("~~~~")+4);
                      %>
                        <option value=<%=lore_id%>><%=lore_name%></option>
                      <%
                      	}
                      %>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('TIANKONG_lore_list','TIANKONG_lore_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('TIANKONG_lore_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="TIANKONG_lore_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
			  </table>
        </div>
        </td>
  </tr>
  <tr>
        <td align="center">
        <div class="border" name="PANDUAN" id="PANDUAN" style="display:none">
              <table width="80%" border="0" cellpadding="0" cellspacing="0">
                  <tr valign="bottom" class="postFormBox"> 
                    <td colspan="2" valign="bottom" class="pageTitleText" >判断题：</td>
                  </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：<font color=red>*</font></span></td>
              <td> <input name="PANDUAN_num" type="text" class="selfScale"> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <input name="PANDUAN_totalscore" type="text" class="selfScale"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 
	            <select name="PANDUAN_diff_low">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0"> 1.0</option>
	            </select>
                到 	            
                <select name="PANDUAN_diff_high">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0" selected> 1.0</option>
	            </select>
				</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="PANDUAN_time_low" size="6" class="selfScale2">秒&nbsp;
                到 
                <input type="text" name="PANDUAN_time_high" size="6"  class="selfScale2">秒&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="PANDUAN_score_low" size="6" class="selfScale2">分&nbsp;
                到 
                <input type="text" name="PANDUAN_score_high" size="6"  class="selfScale2">分&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="PANDUAN_cognizetype_list" size="6" class="selfScale" multiple>
			              <option value="LIAOJIE"> 了解</option>
			              <option value="LIJIE"> 理解</option>
			              <option value="YINGYONG"> 应用</option>
			              <option value="FENXI"> 分析</option>
			              <option value="ZONGHE"> 综合</option>
			              <option value="PINGJIAN"> 评鉴</option>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('PANDUAN_cognizetype_list','PANDUAN_cognizetype_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('PANDUAN_cognizetype_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="PANDUAN_cognizetype_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="PANDUAN_lore_list" size="6" class="selfScale" multiple>
                      <%
                      	for(int i=0;i<lores.length;i++)
                      	{
                      		lore_id = lores[i].substring(0,lores[i].indexOf("~~~~"));
                      		lore_name = lores[i].substring(lores[i].indexOf("~~~~")+4);
                      %>
                        <option value=<%=lore_id%>><%=lore_name%></option>
                      <%
                      	}
                      %>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('PANDUAN_lore_list','PANDUAN_lore_select','') ;return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('PANDUAN_lore_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="PANDUAN_lore_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
			  </table>
        </div>
        </td>
  </tr>
  <tr>
        <td align="center">
        <div class="border" name="WENDA" id="WENDA" style="display:none">
              <table width="80%" border="0" cellpadding="0" cellspacing="0">
                  <tr valign="bottom" class="postFormBox"> 
                    <td colspan="2" valign="bottom" class="pageTitleText" >问答题：</td>
                  </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" align="right" nowrap="nowrap" valign="bottom" style="padding-right:15px"><span class="name">题型数量：<font color=red>*</font></span></td>
              <td> <input name="WENDA_num" type="text" class="selfScale"> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <input name="WENDA_totalscore" type="text" class="selfScale"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 
	            <select name="WENDA_diff_low">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0"> 1.0</option>
	            </select>
                到 	            
                <select name="WENDA_diff_high">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0" selected> 1.0</option>
	            </select>
				</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="WENDA_time_low" size="6" class="selfScale2">秒&nbsp;
                到 
                <input type="text" name="WENDA_time_high" size="6"  class="selfScale2">秒&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="WENDA_score_low" size="6" class="selfScale2">分&nbsp;
                到 
                <input type="text" name="WENDA_score_high" size="6"  class="selfScale2">分&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="WENDA_cognizetype_list" size="6" class="selfScale" multiple>
			              <option value="LIAOJIE"> 了解</option>
			              <option value="LIJIE"> 理解</option>
			              <option value="YINGYONG"> 应用</option>
			              <option value="FENXI"> 分析</option>
			              <option value="ZONGHE"> 综合</option>
			              <option value="PINGJIAN"> 评鉴</option>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('WENDA_cognizetype_list','WENDA_cognizetype_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('WENDA_cognizetype_select') ;return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="WENDA_cognizetype_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="WENDA_lore_list" size="6" class="selfScale" multiple>
                      <%
                      	for(int i=0;i<lores.length;i++)
                      	{
                      		lore_id = lores[i].substring(0,lores[i].indexOf("~~~~"));
                      		lore_name = lores[i].substring(lores[i].indexOf("~~~~")+4);
                      %>
                        <option value=<%=lore_id%>><%=lore_name%></option>
                      <%
                      	}
                      %>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('WENDA_lore_list','WENDA_lore_select','') ;return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('WENDA_lore_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="WENDA_lore_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
			  </table>
        </div>
        </td>
  </tr>
  
  <tr>
              <td align="center">
        	<div class="border" name="YUEDU" id="YUEDU" style="display:none">
              <table width="80%" border="0" cellpadding="0" cellspacing="0">
                  <tr valign="bottom" class="postFormBox"> 
                    <td colspan="2" valign="bottom" class="pageTitleText" >阅读理解题：</td>
                  </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="20%" nowrap="nowrap" align="right" valign="bottom" style="padding-right:15px"><span class="name">题型数量：<font color=red>*</font></span></td>
              <td> <input name="YUEDU_num" type="text" class="selfScale"> &nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom" style="padding-right:15px"><span class="name">题型总分：</span> 
              </td>
              <td valign="middle"> <input name="YUEDU_totalscore" type="text" class="selfScale"> 
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">题型难度：</span> 
              </td>
              <td valign="middle">从 
	            <select name="YUEDU_diff_low">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0"> 1.0</option>
	            </select>
                到 	            
                <select name="YUEDU_diff_high">
	              <option value="0.0"> 0.0</option>
	              <option value="0.1"> 0.1</option>
	              <option value="0.2"> 0.2</option>
	              <option value="0.3"> 0.3</option>
	              <option value="0.4"> 0.4</option>
	              <option value="0.5"> 0.5</option>
	              <option value="0.6"> 0.6</option>
	              <option value="0.7"> 0.7</option>
	              <option value="0.8"> 0.8</option>
	              <option value="0.9"> 0.9</option>
	              <option value="1.0" selected> 1.0</option>
	            </select>
				</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">答题时间：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="YUEDU_time_low" size="6" class="selfScale2">秒&nbsp;
                到 
                <input type="text" name="YUEDU_time_high" size="6"  class="selfScale2">秒&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td align="right" valign="bottom"  style="padding-right:15px"><span class="name">参考分值：</span> 
              </td>
              <td valign="middle">从 
                <input type="text" name="YUEDU_score_low" size="6" class="selfScale2">分&nbsp;
                到 
                <input type="text" name="YUEDU_score_high" size="6"  class="selfScale2">分&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">认知分类：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="YUEDU_cognizetype_list" size="6" class="selfScale" multiple>
			              <option value="LIAOJIE"> 了解</option>
			              <option value="LIJIE"> 理解</option>
			              <option value="YINGYONG"> 应用</option>
			              <option value="FENXI"> 分析</option>
			              <option value="ZONGHE"> 综合</option>
			              <option value="PINGJIAN"> 评鉴</option>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('YUEDU_cognizetype_list','YUEDU_cognizetype_select',''); return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('YUEDU_cognizetype_select'); return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="YUEDU_cognizetype_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
            <tr valign="bottom"> 
              <td align="right" valign="top"  style="padding-right:15px"><span class="name">知 识 点：<font color=red>*</font> </span> 
              </td>
                    <td colspan="1" valign="bottom" > 
                      <table width="80%" border="0" cellspacing="0" cellpadding="0">
                        <tr valign="bottom"> 
                    <td width="40%" align="right" valign="bottom" style="padding-bottom:10px;padding-top:10px"> 
                      <select name="YUEDU_lore_list" size="6" class="selfScale" multiple>
                      <%
                      	for(int i=0;i<lores.length;i++)
                      	{
                      		lore_id = lores[i].substring(0,lores[i].indexOf("~~~~"));
                      		lore_name = lores[i].substring(lores[i].indexOf("~~~~")+4);
                      %>
                        <option value=<%=lore_id%>><%=lore_name%></option>
                      <%
                      	}
                      %>
                      </select> </td>
                    <td align="center" valign="middle"><p><a href="" title="提交" class="button" onclick="JavaScript:AddItem('YUEDU_lore_list','YUEDU_lore_select','') ;return false;"><span unselectable="on">&gt;&gt;</span></a></p>
                      <p><a href="" title="提交" class="button" onclick="JavaScript:DeleteItem('YUEDU_lore_select') ;return false;"><span unselectable="on">&lt;&lt;</span></a></p></td>
                    <td width="40%"  style="padding-bottom:10px"><select name="YUEDU_lore_select" size="6" class="selfScale" multiple>
                      </select></td>
                  </tr>
                </table></td>
            </tr>
                </table>      </div>
                </td>
  </tr>

</table>

          </td>
  </tr>
                          		
                          		
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="100%" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="100%" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="" class="tj" onclick="doCommit();return false;">[提交]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</form>


<script>
	function changediv(div)
	{
		document.getElementById(div).style.display=''
	}
	function checkDiv(div)
	{
		var divs = 'document.select.'+ div;
		if(!isNull(eval(divs+'_num.value')))
		{
			alert('请填写题型数量!');
			eval(divs+'_num.focus()');
			return false;
		}
		if(!isInt(eval(divs+'_num.value')))
		{
			alert('题型数量请填写整数!');
			eval(divs+'_num.focus()');
			return false;
		}
		if((eval(divs+'_num.value')*1)<1)
		{
			alert('题型数量请填写大于0的整数!');
			eval(divs+'_num.focus()');
			return false;
		}
		/*if(!isNull(eval(divs+'_totalscore.value')))
		{
			alert('请填写题型总分!');
			eval(divs+'_totalscore.focus()');
			return false;
		}*/
		if(eval(divs+'_diff_low.value')*1>eval(divs+'_diff_high.value')*1)
		{
			alert('题型难度上限应大于或等于下限!');
			eval(divs+'_diff_low.focus()');
			return false;
		}
		if(eval(divs+'_cognizetype_select.length')<1)
		{
			alert("请选择认知分类!");
			eval(divs+'_cognizetype_list.focus()');
			return;
		}
		if(eval(divs+'_lore_select.length')<1)
		{
			alert("请选择知识点!");
			eval(divs+'_lore_list.focus()');
			return;
		}
	    SelectTotal(div+'_lore_select');
	    SelectTotal(div+'_cognizetype_select');
		return true;
	}
	function doCommit() {
		if(document.getElementById('DANXUAN').style.display=='')
		{
			if(!checkDiv('DANXUAN'))
				return;
		}
		if(document.getElementById('DUOXUAN').style.display=='')
		{
			if(!checkDiv('DUOXUAN'))
				return;
		}
		if(document.getElementById('TIANKONG').style.display=='')
		{
			if(!checkDiv('TIANKONG'))
				return;
		}
		if(document.getElementById('PANDUAN').style.display=='')
		{
			if(!checkDiv('PANDUAN'))
				return;
		}
		if(document.getElementById('WENDA').style.display=='')
		{
			if(!checkDiv('WENDA'))
				return;
		}
		if(document.getElementById('YUEDU').style.display=='')
		{
			if(!checkDiv('YUEDU'))
				return;
		}
	    document.select.submit();
	}
</script>
<%
	if(questiontypes!=null && questiontypes.length>0)
	{
		for(int i=0;i<questiontypes.length;i++)
		{
			%>
			<script>
			changediv('<%=questiontypes[i]%>');
			</script>
			<%
		}
	}

	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>