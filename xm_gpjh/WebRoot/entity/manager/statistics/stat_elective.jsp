<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>选课统计</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<form name='search' action='/entity/manager/statistics/stat_elective_exe.jsp' method='post' class='nomargin'>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="/entity/manager/statistics/images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="/entity/manager/statistics/images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="/entity/manager/statistics/images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">选择学期查看选课统计</td>
              </tr>
            </table></td>
          <td width="8"><img src="/entity/manager/statistics/images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder"> 
        <div class="border">
          <table width="40%" border="0" cellpadding="0" cellspacing="0">
          	<tr valign="bottom" class="postFormBox"> 
              <td>
              <span class="name">选择学期&nbsp;</span>
              </td>
              <td>
				<select name=batch_id>
				<%
					dbpool db = new dbpool();
					MyResultSet rs = null;
					String sql = "select id,name from pe_bzz_batch order by name desc";
					rs = db.executeQuery(sql);
					while(rs!=null&&rs.next())
					{
						String id = rs.getString("id");
						String name = rs.getString("name");
				%>
						<option value="<%=id%>" ><%=name %></option>
				<%
					}
					db.close(rs);
				%>
				</select>
				</td>
			</tr>
          </table>
      </div>
    </td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="/entity/manager/statistics/images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
        <tr>
          <td align="center" valign="middle"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'" onclick="javascript:document.search.submit();"><span class="text" >提交</span></span></td>
        </tr>
      </table></td>
  </tr>
  
</table>
 </form>
</body>
</html>
