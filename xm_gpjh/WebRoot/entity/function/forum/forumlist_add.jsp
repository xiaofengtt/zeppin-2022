<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>建立新版</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<script language=javascript>

	function chkSubmit(form)
	{		
		if(form.name.value.length <2)
		{
				alert("标题好象忘记写了!");
				form.name.focus();
				form.name.select();
				return false;
		}
		
		if(form.content.value.length <2)
		{
				alert("内容部分为空，还是多写几句吧？");
				form.content.focus();
				form.content.select();
				return false;
		}
	}
	
</script>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/tlq.gif" width="217" height="86"></td>
              </tr>
              <tr>
          <td align="center" valign="top">
            <table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr> 
                <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="../images/tlq_10.gif" width="46" height="40"></td>
                            <td width="120" valign="top" class="text3" style="padding-top:25px;padding-left:15px">建立新版</td>
                            <td background="../images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="8"><img src="../images/tlq_01.gif" width="8" height="11"></td>
                            <td width="733" background="../images/tlq_02.gif"> </td>
                            <td width="9" align="right"><img src="../images/tlq_03.gif" width="9" height="11"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="8"> </td>
                    </tr>
                    <tr>
                   <form action="forumlist_addexe.jsp" method="POST" name="frmForum" onsubmit="return chkSubmit(this)">
                      <td align="center"><table width="80%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="10%" height="40" class="text6">版名：</td>
                            <td><input name="name" type="text" size="61" /></td>
                          </tr>
                          <tr> 
                            <td class="text6">内容：</td>
                            <td><textarea name="content" cols="60" rows="8"></textarea></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="50" align="center"><table border="0" cellspacing="0" cellpadding="0">
                          <tr align="center"> 
                            <td height="16"><input type="image" src="../images/OK.gif" width="80" height="24"></td>
                            <td width="50">&nbsp;</td>
                            <td><a href="javascript:history.back()"><img src="../images/tlfh.gif" width="80" height="24" border="0" /></a></td>
                          </tr>
                        </table></td>
                   </form>     
                    </tr>
                  </table>
                 
                  </td>
              </tr>
            </table><br> </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>