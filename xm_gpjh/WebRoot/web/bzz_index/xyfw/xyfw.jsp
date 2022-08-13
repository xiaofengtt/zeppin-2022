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
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
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
			<td width="30" height="30"><img src="/web/bzz_index/xyfw/images/msfc_11.jpg" border="0"></td>
			<td width="80" class="ms_title">学员服务</td>
			<td background="/web/bzz_index/xyfw/images/msfc_12.jpg">&nbsp;</td>
		  </tr>
		</table>
		<table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="5" background="/web/bzz_index/xyfw/images/box_top.jpg"></td>
		  </tr>
		  <tr>
			<td background="/web/bzz_index/xyfw/images/box_bg.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="240" valign="top"><br>
                  <table width="184" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td align="center"><img src="/web/bzz_index/xyfw/images/dx_2.jpg" width="171" height="122" vspace="10"></td>
                  </tr>
                  <tr>
                    <td class="msfc_left_t"> 　　走进清华门，就是清华人。每位走入网络课堂的学员，都将成为清华校友。希望各位学员朋友秉承清华大学“自强不息、厚德载物”的校训、“行胜于言”的校风，刻苦学习，学有所得，学有所成。清华大学拥有一流的人才，高端的科研成果，优质的教育资源。我们将会充分利用清华大学的教育优势，为学员提供全方位的服务支持平台。</td>
                  </tr>
                </table></td>
                <td align="center" valign="top" style="padding-top:20px"><table width="650" border="0" align="center" cellpadding="0" cellspacing="0" class="msfc_title">
                  <tr>
                    <td height="19"><table width="113" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                      <tr>
                        <td align="center" class="ms_title_t1">学员服务</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                
                  <%
                    String title="";
					String note="";
					dbpool db = new dbpool();
					MyResultSet rs = null;
					String sql="";
					sql="select * from pe_info_news a where a.fk_news_type_id='_xyfw' and a.flag_news_status='402880a91e4f1248011e4f1c0ab40004' and a.flag_isactive='402880a91e4f1248011e4f1a2b360002' order by a.title";
					rs=db.executeQuery(sql);
					if(rs!=null && rs.next())
					{
						note=fixnull(rs.getString("note"));
					}
					db.close(rs);
                   	%>
                  <table width="620" border="0" align="center" cellpadding="0" cellspacing="0" >
                    <tr>
                   <td class="con" >
                   <div class="mianer">
                   <%=note %>
</div></td>
                    </tr>
                    <tr>
                      <td colspan="3" align="center" height="5px"></td>
                    </tr>
                  </table>
                 </td>
              </tr>
            </table>
			  </td>
		  </tr>
		  <tr>
			<td height="20" background="/web/bzz_index/xyfw/images/msfc_18.jpg"></td>
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