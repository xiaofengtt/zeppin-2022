<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加试卷</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/add.js"></SCRIPT>
<script src="../js/check.js"></script>
<script>
	function doCommit() {
		if(document.select.questiontype_select.length<1)
		{
			alert("请选择题型!");
			document.forms["select"].questiontype_list.focus();
			return;
		}
		if(document.getElementsByName("lore_select").length<1)
		{
			alert("请选择知识点!");
			//document.forms["select"].lore_list.focus();
			return;
		}else{
			var lore =  document.getElementsByName("lore_select");
			var b = true;
			for(var i=0;i<lore.length;i++){
				if(lore[i].checked){
					b = false;
					break;
				}
			}
			if(b){
				alert("请选择知识点!");
				//document.forms["select"].lore_list.focus();
				return;
			}
		}
	    SelectTotal('lore_select');
	    SelectTotal('questiontype_select');
	    document.select.submit();
	}
	var store = new Array();
	function addRow(str) {
		var arr = str.split("::");
		for(i=0; i<arr.length; i++) {
			var cellArr = arr[i].split("|");
			var id = cellArr[0];
			var name = cellArr[1];
			var x=store.length;
			var st=0;
			for(f=0;f<x;f++)
			{
				if(id==store[f])
				{
					st=1;
					break;
				}
			}
			if(st==1)
				continue;
			store[x]=new Object();
			store[x] = id;
			//添加1行			
			var newTr = Tbl.insertRow(Tbl.rows.length-1);
			
			//添加2列			
			var newTd0 = newTr.insertCell();			
			var newTd1 = newTr.insertCell();
			var newTd2 = newTr.insertCell();
			
			//设置列内容和属性
			newTd0.align = 'right';
			newTd0.innerHTML = '<INPUT name="lore_select" type="checkbox" value="' + id + '~~~~'+name+'" checked>';
			newTd1.innerHTML = '&nbsp;';
			newTd2.innerHTML = name;
		}
	}
</script>
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
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
%>
<form name='select' action='paperpolicy_add2.jsp' method='post' class='nomargin' onsubmit="">
<input type="hidden" name="title" value="<%=title%>">
<input type="hidden" name="note" value="<%=note%>">
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
                            <td width="200" valign="top" class="text3" style="padding-top:25px">题型选择添加</td>
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
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0"  id="Tbl">
                                
						            <tr valign="bottom" class="postFormBox"> 
						              <td width="40%" align="right" valign="bottom" style="padding-bottom:10px"> 
						                <select name="questiontype_list" size="6"  class="selfScale" multiple>
						                  <option value="DANXUAN">单 选 题</option>
						                  <option value="DUOXUAN">多 选 题</option>
						                  <option value="TIANKONG">填 空 题</option>
						                  <option value="PANDUAN">判 断 题</option>
						                  <option value="WENDA">问 答 题</option>
						                  <option value="YUEDU">阅读理解题</option>
						                </select>
						              </td>
						              <td align="center" valign="middle"><p><a href="#" title="提交" class="tj" onclick="JavaScript:AddItem('questiontype_list','questiontype_select','')">&gt;&gt;</a></p>
						                <p><a href="#" title="提交" class="tj" onclick="JavaScript:DeleteItem('questiontype_select')">&lt;&lt;</a></p></td>
						              <td width="40%"  style="padding-bottom:10px"><select name="questiontype_select" size="6" class="selfScale" multiple>
						                </select></td>
						            </tr>
						            
						           	<tr align="center" class="postFormBox">
						                <td  align="center" colspan="3">
						                <input type="button" name="up" value="请选择知识点" onClick="window.open('lore/lore_dir_list.jsp','','height=500, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">
										</td>
						            </tr>
                          		
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
                      <td align="center"><a href="#" title="提交" onclick="doCommit()" class="tj">[提交]</a></td>
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
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>