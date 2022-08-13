<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>

<%@ include file="pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "不详";
			return str;
	
	}
%>

<script language="javascript">
function checkForm(){
	if(document.form_xml.name.value=="")
	{
		alert("输入名称为空!");
		document.form_xml.name.focus();
		document.form_xml.name.select();
		return false;
	}
}
</script>

<%

String techear_id = request.getParameter("techear_id");

try
{ 
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content-type:text/html; charset=utf-8">
<title>欢迎光临生殖健康咨询师培训网</title></title></title>
<link href="css/css.css" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#CCCCCC" width="1"> </td>
           
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="554" border="0" align="center" cellpadding="0" cellspacing="0">		   
        <tr> 
          <td valign="top" style="padding-left:45px">
          <form name="form_xml" action="courseware_addexe.jsp" method="post" onsubmit="return checkForm();">
            <table width="80%" border="1" cellpadding="2" cellspacing="0" bordercolorlight="#BDDFF8" bordercolordark="#FFFFFF">
              <tr align="center"> 
                <td width="25%" colspan="2" class="text3" background="images/tablebg2.gif" style="padding-top:8px" ><strong>添加课件</strong></td>
              </tr>
              <!--tr> 
                <td width="20%" align="center" bgcolor="#DFF5FF" class="td2">课件编号*</td>
                <td class="text3"><input type="text" align="left" size="40" name="id" maxlength=50></td>
              </tr-->              
              <tr> 
                <td width="20%" align="center" class="td2"> 课件名称* </td>
                <td class="content2"><input type="text" align="left" size="40" name="name" maxlength=50></td>
              </tr>
              <tr> 
                <td width="20%" align="center" class="td2"> 作者 </td>
                <td class="content2"><input type="text" align="left" size="40" name="author"></td>
              </tr>
              <tr> 
                <td width="20%" align="center" class="td2"> 发行单位 </td>
                <td class="content2"><input type="text" align="left" size="40" name="publisher"></td>
              </tr>
              <tr> 
                <td width="20%" align="center" class="td2"> 所属类型* </td>
	            <td class="content2">
	            	<select name="active">
		                <option value="0" selected>有效</option>
		                <option value="1" >停用</option>
	        		 </select>
				 </td>
              </tr>
			 	<input type="hidden" name="techear_id" value="<%=techear_id%>">
  				<input type="hidden" name="teachclass_id" value="<%=teachclass_id%>">       
              <tr>
              	<td colspan="2" class="listname" align="center" style="padding-left:20px">
              		<input type="submit" name="submit" value="提交" />&nbsp;
             		<input type="button" name="goback" value="返回" onclick="javascript:window.navigate('course.jsp')" />
              	</td>
              </tr>
            </table>
           </form>
         </td>
        </tr>
                        </table></td>
                    </tr>
                  </table>
		  <br>
		  </td>
  </tr>
</table>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
</body>
</html>
