<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库添加</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
	function onSelect(idVal) {
		var obj = document.getElementById(idVal);
		if(obj.value == "DANXUAN") {
			parent.ctcontent.location = "store_question_single.jsp?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&type="
					+ obj.value;
		}
		if(obj.value == "DUOXUAN") {
			parent.ctcontent.location = "store_question_multi.jsp?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&type="
					+ obj.value;
		}
		if(obj.value == "PANDUAN") {
			parent.ctcontent.location = "store_question_judge.jsp?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&type="
					+ obj.value;
		}
		if(obj.value == "TIANKONG") {
			parent.ctcontent.location = "store_question_blank.jsp?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&type="
					+ obj.value;
		}
		if(obj.value == "WENDA") {
			parent.ctcontent.location = "store_question_answer.jsp?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&type="
					+ obj.value;
		}
		if(obj.value == "YUEDU") {
			parent.ctcontent.location = "store_question_comprehension.jsp?lore_id=<s:property value='#parameters.lore_id[0]'/>&fatherDir_id=<s:property value='#parameters.fatherDir_id[0]'/>&type="
					+ obj.value;
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
              <tr>
                
          <td align="center" valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" align="center" >
              <tr> 
                <td align="center" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" align="center" >
                    <tr> 
                      
                      <td style="padding-left:25px"><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                      	<td colspan=15 height=20></td>
                      </tr>
                          <tr> 
                            <td class="text4">客观题:</td>
                            <td><input type="radio" id="single" name="radiobutton" value="DANXUAN" checked onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">单项选择题</td>
                            <td><input type="radio" id="multi" name="radiobutton" value="DUOXUAN" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">多项选择题</td>
                            <td><input type="radio" id="judge" name="radiobutton" value="PANDUAN" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">判断题</td>
                            <td width=100>&nbsp;</td>
                            <td class="text4">主观题:</td>
                            <!--
                            <td><input type="radio" id="blank" name="radiobutton" value="TIANKONG" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">填空题</td>
                             -->
                            <td><input type="radio" id="answer" name="radiobutton" value="WENDA" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">问答题(或论述题)</td>
                            <td><input type="radio" id="comprehesion" name="radiobutton" value="YUEDU" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">案例分析题</td>
                            
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>

            </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
