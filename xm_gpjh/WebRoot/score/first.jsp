<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<HTML>
<HEAD>
<TITLE>登分人帐户入口</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<link href="../admin/css_index.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY BGCOLOR=#FFFFFF background="../admin/images/bak.jpg" LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<form action="/score/scoreinputlogin_login.action" method="post">
<table width="100%" height="100%" border="0">
  <tr>
    <td><TABLE WIDTH=640 BORDER=0 align="center" CELLPADDING=0 CELLSPACING=0>
        <TR> 
          <TD COLSPAN=3 bgcolor="#F5F6F8">&nbsp; </TD>
          <TD COLSPAN=5 bgcolor="#F5F6F8">&nbsp; </TD>
          <TD COLSPAN=3 bgcolor="#F5F6F8">&nbsp; </TD>
        </TR>
        <TR bgcolor="#FFFFFF"> 
          <TD COLSPAN=11>&nbsp; </TD>
        </TR>
        <TR> 
          <TD COLSPAN=2 bgcolor="#FFFFFF">&nbsp; </TD>
          <TD COLSPAN=2 ROWSPAN=6> <IMG SRC="../admin/images/index_06.jpg" WIDTH=84 HEIGHT=334 ALT=""></TD>
          <TD height="53" COLSPAN=7 bgcolor="#FFFFFF">&nbsp; </TD>
        </TR>
        <TR> 
          <TD background="../admin/images/index_08.jpg"> <IMG SRC="../admin/images/index_08.jpg" WIDTH=20 HEIGHT=19 ALT=""></TD>
          <TD ROWSPAN=3> <IMG SRC="../admin/images/index_09.jpg" WIDTH=109 HEIGHT=172 ALT=""></TD>
          <TD ROWSPAN=2> <IMG SRC="../admin/images/index_10.jpg" WIDTH=37 HEIGHT=21 ALT=""></TD>
          <TD COLSPAN=5 ROWSPAN=2 background="../admin/images/index_11.jpg">请输入登分人帐户密码</TD>
          <TD ROWSPAN=2> <IMG SRC="../admin/images/index_12.jpg" WIDTH=13 HEIGHT=21 ALT=""></TD>
        </TR>
        <TR> 
          <TD> <IMG SRC="../admin/images/index_13.jpg" WIDTH=20 HEIGHT=2 ALT=""></TD>
        </TR>
        <TR> 
          <TD bgcolor="#FFFFFF">&nbsp; </TD>
          <TD ROWSPAN=3> <IMG SRC="../admin/images/index_15.jpg" WIDTH=37 HEIGHT=260 ALT=""></TD>
          <TD height="151" COLSPAN=6 align="left" valign="top" background="../admin/images/index_29.jpg"> 
            <blockquote> 
              <p>&nbsp;<font color="red"><s:property value="msg"/></font>&nbsp;</p>
              <p> 　帐　户： 
                <input class="input" type="text" name="bean.name"  size="14" value="<s:property value='bean.name'/>">
                <br> 　密　码： 
                <input class="input" type="password" name="bean.password" size="16" value="">
                <br>
                <br>
                　　　　　　　　　　　 
                <input type="submit" name="Submit" value="确定">
              </p>
            </blockquote>
            <div align="left"></div></TD>
        </TR>
        <TR> 
          <TD> <IMG SRC="../admin/images/index_18.jpg" WIDTH=20 HEIGHT=21 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/index_19.jpg" WIDTH=109 HEIGHT=21 ALT=""></TD>
          <TD COLSPAN=5> <IMG SRC="../admin/images/index_20.jpg" WIDTH=377 HEIGHT=21 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/index_21.jpg" WIDTH=13 HEIGHT=21 ALT=""></TD>
        </TR>
        <TR> 
          <TD bgcolor="#FFFFFF">&nbsp; </TD>
          <TD> <IMG SRC="../admin/images/index_23.jpg" WIDTH=109 HEIGHT=88 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/index_24.jpg" WIDTH=172 HEIGHT=88 ALT=""></TD>
          <TD COLSPAN=5 bgcolor="#FFFFFF">&nbsp; </TD>
        </TR>
        <TR> 
          <TD width="100%" bgcolor="#F5F6F8">&nbsp; </TD>
          <TD COLSPAN=8 bgcolor="#F5F6F8">&nbsp; </TD>
          <TD height="73" COLSPAN=2 bgcolor="#F5F6F8">&nbsp; </TD>
        </TR>
        <TR> 
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=20 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=109 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=79 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=5 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=37 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=172 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=43 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=37 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=114 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=11 HEIGHT=1 ALT=""></TD>
          <TD> <IMG SRC="../admin/images/spacer.gif" WIDTH=13 HEIGHT=1 ALT=""></TD>
        </TR>
      </TABLE></td>
  </tr>
</table>
</form>
<!-- End ImageReady Slices -->
</BODY>
</HTML>