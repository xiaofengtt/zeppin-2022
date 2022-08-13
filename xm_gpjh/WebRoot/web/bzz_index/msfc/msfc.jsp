<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/msfc.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center">
		<table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="30" height="30"><img src="/web/bzz_index/msfc/images/msfc_11.jpg" border="0"></td>
			<td width="80" class="ms_title">名师风采</td>
			<td background="/web/bzz_index/msfc/images/msfc_12.jpg">&nbsp;</td>
		  </tr>
		</table>
		<table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="5" background="/web/bzz_index/msfc/images/box_top.jpg"></td>
		  </tr>
		  <tr>
			<td background="/web/bzz_index/msfc/images/box_bg.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="240" valign="top"><br>
                  <table width="184" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td align="center"><img src="/web/bzz_index/msfc/images/dx.jpg" width="171" height="122" vspace="10"></td>
                  </tr>
                  <tr>
                    <td class="msfc_left_t"> 　　充分利用清华大学教育品牌优势，根椐班组长岗位要求和工作特点，按照“实战为主、兼顾理论”的原则，整合国内外一流的师资资源。主要有：专门从事企业基层管理者教育培训、有丰富经验的培训师；来自国内外著名高校、研究机构，具有深厚理论基础和丰富案例教学经验的教授、专家、学者；管理咨询机构有丰富实战管理经验的讲师。</td>
                  </tr>
                </table></td>
                <td align="center" valign="top" style="padding-top:20px"><table width="650" border="0" align="center" cellpadding="0" cellspacing="0" class="msfc_title">
                  <tr>
                    <td height="19"><table width="113" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                      <tr>
                        <td align="center" class="ms_title_t">(排名不分前后)</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                  <br>
                  <%
                  
					dbpool db = new dbpool();
					MyResultSet rs = null;
					String sql="select * from pe_info_news a where a.fk_news_type_id='_yxjs' order by a.title";
					int totalItems = 0;
					totalItems = db.countselect(sql);
					//----------分页开始---------------
						String spagesize = (String) session.getValue("pagesize");
						String spageInt = request.getParameter("pageInt");
						Page pageover = new Page();
						pageover.setTotalnum(totalItems);
						pageover.setPageSize(spagesize);
						pageover.setPageInt(spageInt);
						int pageNext = pageover.getPageNext();
						int pageLast = pageover.getPagePre();
						int maxPage = pageover.getMaxPage();
						int pageInt = pageover.getPageInt();
						int pagesize = pageover.getPageSize();
						String link = "";
					rs = db.execute_oracle_page(sql,pageInt,pagesize);
					while(rs!=null&&rs.next())
					{
						String title=fixnull(rs.getString("title"));
						String summary=fixnull(rs.getString("summary"));
						String id=fixnull(rs.getString("id"));
						String pic_link=fixnull(rs.getString("pic_link"));
                   	%>
                  <table width="620" border="0" align="center" cellpadding="0" cellspacing="0" class="list_box">
                    <tr>
                    <td width="70" align="left"><span class="msfc_name"><a href="/entity/first/firstInfoNews_toInfoDetail.action?bean.id=<%=id%>" target="_blank" style="text-decoration:none;">&nbsp;&nbsp;<%=title %></a></span></td>  
                      <td colspan=1 width="448"> <span class="msfc_name1"><%=summary %></span></td>
                    </tr>
                    <tr>
                      <td colspan="3" align="center" height="5px"></td>
                    </tr>
                  </table>
                   <%
	                 }
	                 db.close(rs);
	                  %>
                  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="90%" valign="middle" class="page"><%@ include file="/web/bzz_index/pub/dividepage.jsp" %>
                      </td>
                    </tr>
                  </table></td>
              </tr>
            </table>
			  </td>
		  </tr>
		  <tr>
			<td height="20" background="/web/bzz_index/msfc/images/msfc_18.jpg"></td>
		  </tr>
		</table>
	</td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


</body>
</html>