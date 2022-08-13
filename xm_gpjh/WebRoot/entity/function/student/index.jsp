<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<link rel="stylesheet" type="text/css" href="/entity/function/student/css/style.css">
<link href="/entity/function/student/css/admincss.css" rel="stylesheet" type="text/css">
<script>
var abcStatus=1;
function abc()
{
	if(document.getElementById("abc").width=="11")
	{
		document.getElementById("abc").width="12";
		abcStatus=1;
	}
	else
	{
		document.getElementById("abc").width="11";
		abcStatus=0;
	}
}
function aa(id)
{
	if(document.getElementById(id))
	document.getElementById(id).style.display = (document.getElementById(id).style.display=="none")?"block":"none";
}
</script>
<script type="text/javascript">
var times = 0;
function ForceWindow ()
{
  this.r = document.documentElement;
  this.f = document.createElement("FORM");
  this.f.target = "_blank";
  this.f.method = "post";
  this.r.insertBefore(this.f, this.r.childNodes[0]);
}
ForceWindow.prototype.open = function (sUrl)
{
  this.f.action = sUrl;
  this.f.submit();
}

/*
var oPopup = window.createPopup(); 
function showMenu() 
{
	var oPopBody = oPopup.document.body; 
	oPopBody.style.backgroundColor = "lightyellow"; 
	oPopBody.style.border = "solid black 1px"; 
	str="<iframe src='computetime.jsp?times=10'>"; 
	str+="</iframe>"; 
	oPopBody.innerHTML=str; 
	oPopup.show(50, 50, 180, 50, document.body); 
}
*/
//showMenu() ;
function setTime(time)
{
	times = parseInt(time);
}
function window.onunload(){
      var myWindow = new ForceWindow(); 
      window.navigate("/entity/studyZone/courseResources_computTime.action?course_id=<s:property value='peTchCourse.id'/>&learnedSeconds="+times);
}

</script>
<script language="JavaScript" src="js/cookie.js"></script>
<script>
var totalTime = 0;
var totalTimes = <s:property value="seconds"/>;
setInterval("timer()",1000);
function n2(n)
{
 if(n < 10)return "0" + n.toString();
 return n.toString();
}

function timer()
{
	totalTime +=1;
	var n = totalTime;
	document.getElementById("second").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("minute").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("hour").innerHTML = n2(n);
}
function timers()
{
	document.getElementById("timers").style.display = "block";
	var n = totalTimes;
	document.getElementById("seconds").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("minutes").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("hours").innerHTML = n2(n);
}

function window.onbeforeunload(){
    window.location.reload(); 
	setTime(totalTime);
}
function openware(){
	var xx= window.document.getElementById("combo-box-link").value;
	if(xx.length<=0){
		alert("请选择课件！");
	}else{
		window.open(xx);
	}
}
</script>
</head>

<body scroll=no onload="timers()">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="93" valign="top"><table width="100%" height="93" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#003FA9">
      <tr>
      <td width="342" align="center" style="font-family: 隶书;font-size:24px;color:white;">生殖健康咨询师网络培训课堂</td>
        <td style="padding-bottom:12px; background-repeat:no-repeat; padding-right:12px" align="right" valign="bottom" background="/entity/function/student/images/topbg.jpg"><a href="#"><img src="/entity/function/student/images/tuichu.jpg" width="62" height="21" border="0" onclick="if(confirm('您确定要退出工作室吗？')){parent.window.close();}"/></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="211" height="100%" valign="top" background="/entity/function/student/images/leftbg2.jpg" bgcolor="#004DA9" style="padding-left:4px; background-repeat:repeat-y" id="bj"><table width="208" height="42" border="0" cellpadding="0" cellspacing="0" bgcolor="#E2EEF3" >
          <tr>
            <td ><table style="margin-top:5px;" width="208" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="80" valign="top" bgcolor="#003fa9" class="bai"><p>·当前课程：<br>
                </p></td>
                <td width="128" bgcolor="#003fa9" class="bai"><s:property value="peTchCourse.name"/></td>
              </tr>
            </table>            
           </td>
          </tr>
          <tr>
            <td ><table style="margin-top:15px; margin-left:2px" width="205" border="0" cellpadding="0" cellspacing="0" bgcolor="#e2eef3">
              <!-- 
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/coursenote_list.jsp?course_id=<s:property value='peTchCourse.id'/>" target="mainer" class="menuzi">课程简介</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu2bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/teachernote_list.jsp?course_id=<s:property value='peTchCourse.id'/>" target="mainer" class="menuzi">教师简介</a></td>
              </tr>
               -->
               <tr>
              	<td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_showIframe.action?course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">课程简介</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu5bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=homework&course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">在线作业</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu3bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">资料下载</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu4bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">课程答疑</a></td>
              </tr>
              <tr>
              	              	<!-- <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu8bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="#" onclick="javascript:window.open('/training/student/course/jumpCourseware.jsp?course_id=<s:property value='peTchCourse.id'/>','courseWin');return false" class="menuzi">课件学习</a></td> -->
              	<td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu8bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_showCourseWareList.action?course_id=<s:property value='peTchCourse.id'/>" target="mainer" class="menuzi">课件学习</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu6bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=test&course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">在线自测</a></td>
              </tr>

              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu7bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_showVoteList.action?course_id=<s:property value='peTchCourse.id'/>" target="mainer" class="menuzi">问卷调查</a></td>
                <td width="107" height="45" ></td>
              </tr>
             
            </table></td>
          </tr>
        </table>
          <table width="209" height="8" border="0" cellpadding="0" cellspacing="0" background="/entity/function/student/images/leftcenterbg.jpg">
              <tr>
                <td></td>
              </tr>
            </table>
          <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td valign="top" background="/entity/function/student/images/leftbtbg.jpg" style="background-position:bottom; background-repeat:no-repeat;"><table style="margin-top:15px" width="206" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="30" class="bai" style="padding-left:10px"> <img src="/entity/function/student/images/tubiao2.jpg" width="12" height="12"> 您本次学习时长为</td>
                  </tr>
                  <tr>
                    <td class="yellowzi" height="25" align="center" bgcolor="#3363b4"><div id="timer">
                    	<span id = "hour" class="time">00</span>
                    	:<span id = "minute" class="time">00</span>
                    	:<span id = "second" class="time">00</span>
                    </div></td>
                  </tr>
                  <tr>
                    <td height="30" class="bai" style="padding-left:10px"><img src="/entity/function/student/images/tubiao2.jpg" width="12" height="12"> 您学习总时长为</td>
                  </tr>
                  <tr>
                    <td height="25" align="center" bgcolor="#3363b4" class="yellowzi"><div id="timers" >
                    	<span id = "hours" class="time">00</span>
                    	:<span id = "minutes" class="time">00</span>
                    	:<span id = "seconds" class="time">00</span>
                    </div></td>
                  </tr>
                </table></td>
              </tr>
          </table></td>
        <td width="12" align="left" background="/entity/function/student/images/leftbg2.jpg" style="background-repeat:repeat-y; background-position:right" id="abc"><img id="button" alt="单击收缩/展开右侧功能区"  src="/entity/function/student/images/jiaotou.jpg" width="9" height="79" style="margin:0px 3px 0px 0px; cursor:pointer" onClick="abc();if(abcStatus==0) this.src='/entity/function/student/images/jiaotou1.jpg';else this.src='/entity/function/student/images/jiaotou.jpg';aa('bj');"></td>
        <td height="100%" align="center" valign="top"><iframe src='<s:if test="type=='dayi'">/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="peTchCourse.id"/></s:if>
          <s:elseif test="type=='zice'">/entity/studyZone/courseResources_onlineTest.action?onlineType=test&course_id=<s:property value="peTchCourse.id"/></s:elseif>
          <s:elseif test="type=='zuoye'">/entity/studyZone/courseResources_onlineTest.action?onlineType=homework&course_id=<s:property value="peTchCourse.id"/></s:elseif>
          <s:else>/entity/studyZone/courseResources_showIframe.action?course_id=<s:property value="peTchCourse.id"/></s:else>' width="100%" height="100%" frameborder="0" id="mainer" name="mainer" align="top" scrolling="yes" style="margin:0px 8px 0px 0px;"></iframe></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="47"><table width="100%" height="47" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="5" bgcolor="#003284"></td>
      </tr>
      <tr>
        <td height="42" align="center" background="/entity/function/student/images/btbg.jpg" class="baizi">版权所有：生殖健康咨询师培训网</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

