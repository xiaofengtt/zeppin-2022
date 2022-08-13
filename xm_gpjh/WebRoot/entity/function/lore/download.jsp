<%@ page language="java" contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="com.whaty.platform.test.question.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>批量添加题目</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<%
 	String loreDirId = request.getParameter("loreDirId"); 
	String loreId = request.getParameter("id");   

%>
<script type="text/javascript">
	 
	
	function doSubmit() {
			document.forms['loreForm'].submit();
	}
	
function onDown(){
      var type = document.getElementById("type").value;
//      var id = document.getElementById("loreId").value;
	  var link="";
      if(type=="DANXUAN")
//      	link = "lore_list_excel1.jsp?id=" + id+"&type="+type;
    	link = "lore_list_excel1.jsp?id=" + <%=loreId%>+"&type="+type;      	
      if(type=="DUOXUAN")
      	link = "lore_list_excel2.jsp?id=" + <%=loreId%>+"&type="+type;
      if(type=="WENDA")
      	link = "lore_list_excel4.jsp?id=" + <%=loreId%>+"&type="+type;
       if(type=="PANDUAN")
      	link = "lore_list_excel3.jsp?id=" + <%=loreId%>+"&type="+type;
       if(type=="TIANKONG")
      	link = "lore_list_excel5.jsp?id=" + <%=loreId%>+"&type="+type;
       if(type!="YUEDU")		
	       window.open (link);
	   else
	   	   alert("不能导出");
   }
</script>
<SCRIPT>
  function showDiv(){
  		if(document.loreForm.type.value=='<%=QuestionType.DANXUAN %>'){
  			document.getElementById("link").href ="<%=request.getContextPath()%>/entity/function/lore/temp/DANXUAN.xls";
  		}
  		if(document.loreForm.type.value=='<%=QuestionType.DUOXUAN %>'){
  			document.getElementById("link").href ="<%=request.getContextPath()%>/entity/function/lore/temp/DUOXUAN.xls";
  		}
  		if(document.loreForm.type.value=='<%=QuestionType.PANDUAN %>'){
  			document.getElementById("link").href ="<%=request.getContextPath()%>/entity/function/lore/temp/PANDUAN.xls";
  		}
  		if(document.loreForm.type.value=='<%=QuestionType.TIANKONG %>'){
  			document.getElementById("link").href ="<%=request.getContextPath()%>/entity/function/lore/temp/TIANKONG.xls";
  		}
  		if(document.loreForm.type.value=='<%=QuestionType.WENDA %>'){
  			document.getElementById("link").href ="<%=request.getContextPath()%>/entity/function/lore/temp/WENDA.xls";
  		}
  }

</SCRIPT>
</head>

<%
//	String loreDirId = request.getParameter("loreDirId"); 
//	String loreId = request.getParameter("id");  
%>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="loreForm" action="excel_upload_info.jsp" method="post" ENCTYPE="multipart/form-data">
<INPUT type="hidden" name="loreDirId" value=<%=loreDirId%>>
<INPUT type="hidden" name="loreId" value=<%=loreId%>>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zsd.gif" width="217" height="86"></td>
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
                            <td width="100" valign="top" class="text3" style="padding-top:25px">批量导出题目</td>
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
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">题型:</td>
		                                  <td valign="top">
										  	<select name="type">
										  		<option value="<%=QuestionType.DANXUAN %>"><%=QuestionType.typeShow(QuestionType.DANXUAN) %></option>
										  		<option value="<%=QuestionType.DUOXUAN %>"><%=QuestionType.typeShow(QuestionType.DUOXUAN) %></option>
										  		<option value="<%=QuestionType.PANDUAN %>"><%=QuestionType.typeShow(QuestionType.PANDUAN) %></option>
										  		<option value="<%=QuestionType.TIANKONG %>"><%=QuestionType.typeShow(QuestionType.TIANKONG) %></option>
										  		<option value="<%=QuestionType.WENDA %>"><%=QuestionType.typeShow(QuestionType.WENDA) %></option>
										  	</select>
										  </td>		                                  
		                                </tr>
		                          <!-- <tr>
		                                <td>
		                                <a id="link">下载模板</a>
		                                </td>
		                                </tr>
		                           --> 
	                            	</table>
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
                      <td align="center"><a href="javascript:onDown();" class="tj">[导出]</a>&nbsp;
<%--                      <a href="lore_store_question.jsp?loreDirId=<%=loreDirId%>&id=<%=loreId %>" class="tj">[返回]</a> </td>--%>
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
</body>
</html>