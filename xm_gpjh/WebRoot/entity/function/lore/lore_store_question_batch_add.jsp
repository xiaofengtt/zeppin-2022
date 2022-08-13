<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>批量添加题目</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function pageGuarding() {
		if(document.loreForm.src.value == "") {
			alert("文件不能为空，请选择文件");
			document.loreForm.src.focus();
			return false;
		}
		
		return true;
	}
	
	function doSubmit() {
		if(pageGuarding()) {
			document.forms['loreForm'].submit();
		}
	}
	

</script>
<SCRIPT>
  function showDiv(){
  		if(document.loreForm.type.value=='DANXUAN'){
  			document.getElementById("link").href ="/entity/function/lore/temp/DANXUAN.xls";
  		}
  		if(document.loreForm.type.value=='DUOXUAN'){
  			document.getElementById("link").href ="/entity/function/lore/temp/DUOXUAN.xls";
  		}
  		if(document.loreForm.type.value=='PANDUAN'){
  			document.getElementById("link").href ="/entity/function/lore/temp/PANDUAN.xls";
  		}
  		if(document.loreForm.type.value=='TIANKONG'){
  			document.getElementById("link").href ="/entity/function/lore/temp/TIANKONG.xls";
  		}
  		if(document.loreForm.type.value=='WENDA'){
  			document.getElementById("link").href ="/entity/function/lore/temp/WENDA.xls";
  		}
  }

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="loreForm" action="/entity/studyZone/courseResources_excelUpload.action" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="lore_id" value="<s:property value='#parameters.lore_id[0]'/>" >
<input type="hidden" name="fatherDir_id" value="<s:property value='#parameters.fatherDir_id[0]'/>" >
<input type="hidden" name="course_id" value="<s:property value='#parameters.course_id[0]'/>" >
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
                            <td width="100" valign="top" class="text3" style="padding-top:25px">批量添加题目</td>
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
										  	<select name="type" onchange="showDiv()">
										  		<option value="DANXUAN">单选题</option>
										  		<option value="DUOXUAN">多选题</option>
										  		<option value="PANDUAN">判断题</option>
										  		<!-- 
										  		<option value="TIANKONG">填空题</option>
										  		 -->
										  		<option value="WENDA">问答题</option>
										  	</select>
										  	 <a href="./temp/DANXUAN.xls" id="link" name="link">下载标准模板</a>
										  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">选择文件:</td>
		                                  <td>
		                                  	<input type="file" name="src">
		                                  </td>		                                  
		                                </tr>
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
                      <td align="center"><a href="javascript:doSubmit();" class="tj">[导入]</a>&nbsp;<a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&course_id=<s:property value='#parameters.course_id[0]'/>" class="tj">[返回]</a> </td>
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