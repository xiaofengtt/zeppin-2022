<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script>
var totalTime = 600;
setInterval("timer()",1000);
function n2(n)
{
 if(n < 10)return "0" + n.toString();
 return n.toString();
}

function initTime(second)
{
	totalTime = second;
	document.getElementById("timer").style.display = "";
}
function timer()
{
	if(totalTime>0)	
	{
		totalTime -=1;
		var n = totalTime;
		document.getElementById("second").innerHTML = n2(n % 60);
 		n = (n - n % 60) / 60;
 		document.getElementById("minute").innerHTML = n2(n % 60);
 		n = (n - n % 60) / 60;
 		document.getElementById("hour").innerHTML = n2(n);
	}
	else 
	{
		//commitPaper();
	}
}
function saveTime()
{
	document.cookie="TestTime="+totalTime;
}

</script>
 </head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif" style='overflow:scroll;overflow-x:hidden' onload="initTime('<s:property value='seconds'/>')" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"><img src="/entity/function/images/zxzy.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="98%" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="15%" background="/entity/function/images/st_01.gif" class="text3" style="padding-left:50px">自&nbsp;测&nbsp;题&nbsp;预&nbsp;览</td>
                            <td width="75%" height="53" background="/entity/function/images/wt_03.gif" align="right" valign="middle">
                            <!-- span><a href="testpaper_result.jsp?id=" onclick="javascript:parent.location.replace(this.href);event.returnValue=false;" border=0 target=submain><img src="/entity/function/images/tjsj.gif" width="97" height="31" border=0></a></SPAN-->
                            <div id="timer" style="display:none" border="1px #000000 solid">
                            <span id = "hour" style="font-size:24">00</span><span style="font-size:24;font-weight:bold;">:</span><span id = "minute" style="font-size:24">00</span><span style="font-size:24;font-weight:bold;">:</span><span id = "second" style="font-size:24">00</span><%--
                            &nbsp;&nbsp;&nbsp;&nbsp;<SPAN valign="bottom"><a href="testpaper_result.jsp?id=<%=id%>" onclick="javascript:location.replace(this.href);event.returnValue=false;" border=0><img src="/entity/function/images/tjsj.gif" width="97" height="31" border=0></a></SPAN>
                            --%></div>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
