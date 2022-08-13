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
			<td width="30" height="30"><img src="/web/bzz_index/yxbzz/images/msfc_11.jpg" border="0"></td>
			<td width="80" class="ms_title">优秀班组长</td>
			<td background="/web/bzz_index/yxbzz/images/msfc_12.jpg">&nbsp;</td>
		  </tr>
		</table>
		<table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="5" background="/web/bzz_index/yxbzz/images/box_top.jpg"></td>
		  </tr>
		  <tr>
			<td background="/web/bzz_index/yxbzz/images/box_bg.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="240" valign="top"><br>
                  <table width="184" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td align="center"><img src="/web/bzz_index/yxbzz/images/dx.jpg" width="171" height="122" vspace="10"></td>
                  </tr>
                  <tr>
                    <td class="msfc_left_t"> 　　班组是企业的基石。班组长作为企业生产经营一线的指挥者、组织者和管理者，担负着企业的产品生产、质量保证以及基层员工的管理重任。班组长在中央企业的发展中起着至关重要的作用，也涌现了一批批具有模范带头作用的优秀班组长。我们特开设优秀班组长专栏，给他们搭建一个自我展示的平台，介绍优秀班组长的先进事迹，推广他们在班组管理中的创新思路，弘扬他们在工作中兢兢业业、勇于拼搏的奋斗精神，充分体现当代工人阶级的良好精神风貌。希望优秀班组长栏目能成为学员们展示自我风采的舞台，也希望优秀班组长的事迹能在所有学员中起到先进示范作用。</td>
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
					String sql="select * from pe_info_news a where a.fk_news_type_id='_yxbz' and a.flag_news_status='402880a91e4f1248011e4f1c0ab40004' and a.flag_isactive='402880a91e4f1248011e4f1a2b360002'  order by a.title";
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
                     <!--  <td width="90" align="center"><img src=".<%=pic_link %>" width="64" height="64"></td>
                      <td width="448"> <span class="msfc_name"><%=title %></span> 
                        <%=summary %></td>
                      <td width="112"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<%=id %>" target="_blank"><img src="/web/bzz_index/yxbzz/images/msfc_28.jpg" width="60" height="13" border="0"></a></td>
                    -->
					 <tr>
                    <td width="80" align="left"><span class="msfc_name"><a href="/entity/first/firstInfoNews_toInfoDetail.action?bean.id=<%=id%>" target="_blank" style="text-decoration:none;">&nbsp;&nbsp;<%=title %></a></span></td>  
                      <td colspan=1 width="448"> <span class="msfc_name1"><%=summary %></span></td>
                    </tr>
                    </tr>
                    <tr>
                      <td colspan="3" align="center" height="5px"></td>
                    </tr>
                  </table>
                   <%
	                 }
	                 db.close(rs);
	                  %>
                  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="100%" valign="middle" class="page"><%@ include file="/web/bzz_index/pub/dividepage.jsp" %>
                      </td>
                    </tr>
                  </table></td>
              </tr>
            </table>
			  </td>
		  </tr>
		  <tr>
			<td height="20" background="/web/bzz_index/yxbzz/images/msfc_18.jpg"></td>
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