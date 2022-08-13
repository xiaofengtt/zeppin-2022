<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>生殖健康咨询师培训网</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
   	<script> 
	function search(){
  	 document.forms[0].submit(); 
   
   }
   </script>
  </head>
  
 <body >
<table width="1002" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td  width="1002" height="210" valign="top"><iframe id="top" name="top" frameborder="0" scrolling="no" src="/web/top.jsp" width="1002" height="210"></iframe></td>
  </tr>
  <tr>
    <td width="1002"  height="100%" valign="top" align="center">
	<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0px 3px 0px;">
      <tr>
        <td><table width="980" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/04.jpg">
          <tr>
            <td width="104"><img src="/web/news/images/dcwj.jpg" width="104" height="29" /></td>
            <td width="798" align="right">
			  
			  <table width="280" border="0" cellspacing="0" cellpadding="0" style=" margin-right:5px; margin-bottom:3px;">
              <tr>
              <td>&nbsp;
              </td>
                <!--  td width="80" class="orangefont" align="right">文章搜索</td>
                <td width="140"><input name="searchTitle" id="searchTitle" type="text" class="text" value="<s:property value="searchTitle"/>"/></td>
                <td width="60" align="left" style="padding-left:2px;"><img src="/web/news/images/search.jpg" width="39" height="17" border="0" style="cursor: hand"  onclick="search()"/></td>-->
              </tr>
            </table>
            </td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:18px 0 3px 0px;border-bottom:2px solid #F2F2F2;" >
          <tr >
            <td width="80"  class="newstitle rightborder"></td>
            <td width="680" class="newstitle rightborder">标题</td>
            <td width="120" class="newstitle rightborder">日期</td>
            <td width="100" class="newstitle"></td>
          </tr>
        </table>
		<s:iterator value="peVotePaperList">
		<table width="980" border="0" cellspacing="0" cellpadding="0" height="25"  class="newslist ">		
          <tr >
            <td width="80"  class="newsfontcenter1 "></td>
            <td width="680" class="newsfontleft newstyle1"><a href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title" />   </a> </td>
            <td width="120" class="newsfontcenter "><s:date name="foundDate" format="yyyy-MM-dd" /></td>
            <td width="100" class="newsfontcenter"></td>
          </tr>
         </table>  			
		</s:iterator>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="1002"  height="68" valign="top">
	<iframe id="footer" name="footer" frameborder="0" scrolling="no" src="/web/footer.jsp" width="1002"  height="68" ></iframe>	</td>
  </tr>
</table>

<map name="Map" id="Map">
  <area shape="rect" coords="272,94,391,127" href="#" />
  <area shape="rect" coords="267,16,386,49" href="#" />
<area shape="rect" coords="73,94,194,128" href="#" />
<area shape="rect" coords="72,15,191,48" href="#" /><area shape="rect" coords="176,55,272,91" href="#" /></map>
</body>
</html>
