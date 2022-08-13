<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="ctl00_Head1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:property value="paper.title" /></title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link type="text/css" rel="stylesheet" href="../css/que/NewDefault.css" />
<link href="../css/que/q_12px.css" rel="stylesheet" type="text/css" />
<link href="../css/que/newsolid_38.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>

<body>
	<div id="divNotRun"
		style="height:100px; text-align:center; display:none;"></div>
	<div id="jqContent" class="" style="text-align: left; ">
		<div class="que-header2">
			<div class="inn">

				<s:if test='%{#parameters.type[0]==3}'>
					<h1>培训情况调查问卷系统</h1>
				</s:if>
				<s:elseif test="%{#parameters.type[0]==2}">
					<h1>学员成绩及评价系统</h1>
				</s:elseif>
				<s:elseif test="%{#parameters.type[0]==1}">
					<h1>项目申报专家评审系统</h1>
				</s:elseif>
				<s:else>
					<s:property value="#parameters.type" />
				</s:else>
			</div>
		</div>
		<div id="headerCss"
			style="overflow-x: hidden; overflow-y: hidden; height:15px;">
			<div id="ctl00_header"></div>
		</div>
		<div id="mainCss">
			<div id="mainInner">
				<div id="box">
					<script type="text/javascript" src="assets/zhezhao.js"></script>
					<style>
.interface {
	text-align: left;
	border: solid 1px #ff9900;
	background: #FDEADA;
	color: #333333;
	vertical-align: middle;
	margin: 20px auto;
	width: 760px;
	height: 32px;
	line-height: 32px;
	padding: 0 4px;
}

html {
	overflow-x: hidden;
}
</style>
					<div class="survey" style="margin:0px auto;">

						<div id="ctl00_ContentPlaceHolder1_JQ1_divHead" class="surveyhead"
							style="border: 0px;">
							<h1 id="ctl00_ContentPlaceHolder1_JQ1_h1Name">
								<span id="ctl00_ContentPlaceHolder1_JQ1_lblQuestionnaireName"><s:property
										value="paper.title" escape="false" /></span>
							</h1>
							<div style="text-align: left; margin-left: 10px;"></div>
							<div style="clear: both;"></div>

							<div id="ctl00_ContentPlaceHolder1_JQ1_divDec"
								class="surveydescription">
								<span style="float: left;"></span> <span
									id="ctl00_ContentPlaceHolder1_JQ1_lblQuestionnaireDescription"
									style="vertical-align: middle;"><s:property
										value="paper.about" escape="false" /></span>
							</div>
							<div style="clear: both;"></div>
						</div>

						<s:if test="%{#parameters.type[0]==3}">
							<div style="border:1px dashed #6099c9;padding:20px;text-align:center;font-size:14px;color:#f00;margin:0 2%;">问卷采用匿名评价的方式，请您公正客观的评价此次培训的效果，感谢配合！</div>
						</s:if>



						<div id="ctl00_ContentPlaceHolder1_JQ1_question"
							class="surveycontent">
							<div id="ctl00_ContentPlaceHolder1_JQ1_divDisplayPageNum">
								<style type="text/css">
legend {
	display: none;
}

fieldset {
	border: 0px;
	margin: 0;
	padding: 0;
}
</style>
							</div>

							<div id="ctl00_ContentPlaceHolder1_JQ1_surveyContent">
								<fieldset class="fieldset" id="fieldset1">


									<s:set name="questions" value="paper.questions" />
									<s:iterator value="#questions" id="q">

										<s:if test="%{#q.type == 0}">
											<div class="div_title_page_question">
												<s:property value="#q.name" escape="false" />
											</div>
										</s:if>

										<s:if test="%{#q.type == 6}">
											<div class="div_question" id="div1">
												<div class="div_title_question_all">
													<div class="div_topic_question">
														<b><s:property value="#q.inx" />.</b>
													</div>
													<div id="divTitle1" class="div_title_question">
														<s:property value="#q.name" escape="false" />
														<span style="color:red;">&nbsp;*</span>
													</div>
													<div style="clear:both;"></div>
												</div>
												<div class="div_table_radio_question" id="divquestion1">
													<div class="div_table_clear_top"></div>
													<textarea style='overflow: auto;width:62%;height:90px;'
														class='inputtext' name="q<s:property value="#q.inx"/>"
														id="q<s:property value="#q.inx"/>"><s:if test='paperView=="edit"'><s:property value="resultHashMap.get(#q.id).get(0)" />
															</s:if></textarea>

													<div class="div_table_clear_bottom"></div>
												</div>
											</div>
										</s:if>

										<s:if test="%{#q.type == 2}">
											<div class="div_question" id="div1">
												<div class="div_title_question_all">
													<div class="div_topic_question">
														<b><s:property value="#q.inx" />.</b>
													</div>
													<div id="divTitle1" class="div_title_question">
														<s:property value="#q.name" escape="false" />
														<span style="color:red;">&nbsp;*</span>
													</div>
													<div style="clear:both;"></div>
												</div>
												<div class="div_table_radio_question" id="divquestion1">
													<div class="div_table_clear_top"></div>
													<ul>
														<s:set name="answers" value="#q.answers" />

														<s:if test="%{#q.scale == 0}">
															<s:set name="f" value="99/#q.arrange" />
															<s:iterator value="answers" id="a" status="st">
																<li style="width:<s:property value="#f"/>%;"><input
																	type="radio" name="q<s:property value="#q.inx"/>"
																	<s:if test='paperView=="edit"'>
																		<s:if test='resultHashMap.get(#q.id).containsKey(#a.id)'>
																		checked
																		</s:if>
																	</s:if>id="q<s:property value="#q.inx"/>_<s:property value="#q.inx"/>"
																	value="<s:property value="#a.inx"/>"><label
																		for="q<s:property value="#q.inx"/>_<s:property value="#a.inx"/>"><s:property
																				value="#a.name" /></label>
																				<s:if test="%{#q.isCount}">
																					<span style="color:#efa030">(分值:<s:property value="#a.score" />)</span>
																				</s:if>
																				</li>
															</s:iterator>
														</s:if>
														<s:if test="%{#q.scale != 0}">
															<s:iterator value="answers" id="a" status="st">

																<s:if test="%{#q.scale != 1}">
																	<s:if test="#st.first">
																		<li class="notchoice"
																			style="padding-right:15px;padding-top:5px;"><b><s:property
																					value="#a.name" escape="false" /></b></li>
																	</s:if>
																	<li style="padding-left:3px;"
																		name="q<s:property value="#q.inx"/>"
																		id="q<s:property value="#q.inx"/>_<s:property value="#a.inx"/>"
																		value="<s:property value="#a.score"/>"
																		title="<s:property value="#a.name"/>"
																		class="off<s:property value="#q.scale"/>"></li>
																	<s:if test="#st.last">
																		<li class="notchoice"
																			style="padding-left:15px;padding-top:5px;"><b><s:property
																					value="#a.name" escape="false" /></b></li>
																	</s:if>
																</s:if>
																<s:if test="%{#q.scale == 1}">
																	<li style="padding-left:3px;width:120px"><input
																		type="radio" name="q<s:property value="#q.inx"/>"
																		id="q<s:property value="#q.inx"/>_<s:property value="#a.inx"/>"
																		value="<s:property value="#a.inx"/>"
																		title="<s:property value="#a.name"/>" /><b><s:property
																				value="#a.name" escape="false" /></b></li>
																</s:if>

															</s:iterator>
														</s:if>
														<div style="clear:both;"></div>
													</ul>
													<div style="clear:both;"></div>
													<div class="div_table_clear_bottom"></div>
												</div>
											</div>
										</s:if>

										<s:if test="%{#q.type == 4}">
											<div class="div_question" id="div1">
												<div class="div_title_question_all">
													<div class="div_topic_question">
														<b><s:property value="#q.inx" />.</b>
													</div>
													<div id="divTitle1" class="div_title_question">
														<s:property value="#q.name" />
														<span style="color:red;">&nbsp;*</span>
													</div>
													<div style="clear:both;"></div>
												</div>
												<div class="div_table_radio_question" id="divquestion1">
													<div class="div_table_clear_top"></div>
													<ul>
														<s:set name="answers" value="#q.answers" />
														<s:iterator value="answers" id="a">
															<li style="width:99%;"><input type="checkbox"
															
																<s:if test='paperView=="edit"'>
																		<s:if test='resultHashMap.get(#q.id).containsKey(#a.id)'>
																		checked
																		</s:if>
																</s:if>
																name="q<s:property value="#q.inx"/>"
																id="q<s:property value="#q.inx"/>_<s:property value="#q.inx"/>"
																value="<s:property value="#a.inx"/>"><label
																	for="q<s:property value="#q.inx"/>_<s:property value="#a.inx"/>"><s:property
																			value="#a.name" /></label></li>
														</s:iterator>
														<div style="clear:both;"></div>
													</ul>
													<div style="clear:both;"></div>
													<div class="div_table_clear_bottom"></div>
												</div>
											</div>
										</s:if>
									</s:iterator>

									<div class="register_div">
										<div style="display:none;" id="divpoweredby">
											Powered by <a href="http://www./" title="问卷调查系统"
												class="link-06f" target="_blank"><strong>问卷调查系统</strong></a><span
												style="font-family:Tahoma">™</span>
										</div>
									</div>
								</fieldset>
							</div>

							<div style="margin-top: 6px;clear:both;" id="submit_div">
								<table id="submit_table" style="margin:0 auto;">

									<tbody>
										<tr>
											<td id="ctl00_ContentPlaceHolder1_JQ1_tdCode"
												style="display: none;"><input id="txtCode" size="14"
												maxlength="10" onkeydown="enter_clicksub(event);"
												style="height:24px; line-height:24px; border:1px solid #7F9DB9;">&nbsp;&nbsp;<img
													id="imgCode" style="display:none;"></td>

											<td id='submitVote'><s:if test="%{uid != 'success'}">
													<input type="button" class="submitbutton" value="提交答卷"
														onmouseout="this.className=&#39;submitbutton&#39;;"
														id="submit_button"> &nbsp;&nbsp; 
												</s:if></td>
											<td>

												<div id="divPreviewQ" style="display:none;">
													<div style="margin:30px auto; text-align:center;">
														<a id="hrefPQ" target="_blank"
															onclick="alert(&quot;您的答卷还没有提交，请预览答卷后返回此页面并点击“提交按钮”提交答卷！&quot;);PDF_close();return true;"
															class="btnbg"><span>成功生成预览，点击查看</span></a>
													</div>
												</div>
											</td>
											<td><a href="http://www./viewstat/2586349.aspx"
												id="ctl00_ContentPlaceHolder1_JQ1_hrefViewResult"
												class="link-U00a6e6" style="margin-left:10px; display:none;"
												target="_blank"> 查看结果</a></td>
											<td align="right"><span id="spanTest"
												style="visibility:hidden;"> &nbsp;&nbsp;<input
													type="button" style="font-weight: bold;" class="operation"
													value="试填问卷" id="submittest_button"
													title="只有发布者才能看到试填按钮，试填的答卷不会参与结果统计！"><a
														title="只有发布者才能看到试填按钮，试填的答卷不会参与结果统计！" style="color: green"
														href="javascript:void(0);"><b>(?)</b></a>&nbsp;&nbsp;<span
														style="color: #ff3300;"></span></span></td>



											<td align="right" valign="bottom"></td>

										</tr>
									</tbody>
								</table>
								<div style="clear:both;"></div>

							</div>
							<div id="divMinTime"
								style="position: absolute; width: 140px; font-size: 14px; color: rgb(102, 102, 102); left: 773px; top: 595px; display: none;">
								还剩<span style="color: Red; font-weight: bold;" id="spanMinTime">0</span>秒后才能继续
							</div>
							<div id="submit_tip"
								style="display: none; background-color: #f04810; color: White;
        padding: 10px">
							</div>
						</div>

						<div id="ctl00_ContentPlaceHolder1_JQ1_divLeftBar"
							style="text-align: center; position: absolute; width: 50px; padding: 8px 0px; left: 1104px; top: 431px; background-color: rgb(255, 255, 255); background-position: initial initial; background-repeat: initial initial;"
							class="leftbar">
							<div id="divProgressBar">
								<style>
#loading {
	background:
		url(http://image.sojump.com/images/wjx/JoinQuestionnaire/bgProgressBg.gif)
		no-repeat 0px 0px;
	height: 120px;
	width: 15px;
	float: left;
	border: 1px #d6ebf7 solid;
}

#loadcss {
	display: block; /*很重要, 弄成块*/
	background:
		url(http://image.sojump.com/images/wjx/JoinQuestionnaire/ProgressBarbar.gif);
	background-repeat: repeat;
	background-attachment: fixed;
	text-align: center;
	width: 15px;
	line-height: 15px;
}
</style>
								<div style="text-align:left;">
									<span id="loadprogress"
										style="font-weight: bold; visibility: visible;">&nbsp;&nbsp;100%</span>
								</div>
								<div id="ctl00_ContentPlaceHolder1_JQ1_divProgressImg"
									style="float: left; padding-left: 15px; visibility: visible;">

									<div id="loading" title="已答题比率">
										<span id="loadcss"
											style="height: 100%; line-height: 0; font-size: 0px; overflow: hidden;"></span>
									</div>
								</div>
								<div style="float: left; width:14px; line-height:0;"
									id="divSaveText"></div>
								<div class="divclear"></div>
							</div>
							<div style="float: left;padding-left: 2px; visibility:hidden;">

							</div>
							<script type="text/javascript">
            var timerq; 
            var surveycontent=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_question");
            var container=document.getElementById("container");
            var progressBarType=1;
             var divLeftBar=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divLeftBar");
             var divProgressBar=document.getElementById("divProgressBar");  
             var loading=document.getElementById("loading"); 
             var divSave=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divSave");
              var issimple = '';
              var isSolid=1;
              var divSaveText=document.getElementById("divSaveText");
              var divProgressImg=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divProgressImg");
             var xTop=0;var solidmainCss=document.getElementById("mainCss");
             function addEventSimple(obj, evt, fn) {
                if (obj.addEventListener)
                    obj.addEventListener(evt, fn, false);
                else if (obj.attachEvent)
                    obj.attachEvent('on' + evt, fn);
            }
             function resizeLeftBar()
             {
                 if(!divLeftBar||!surveycontent)return;
                 var xy2=null;var clientWidth=0;
                  if(solidmainCss){
                    xy2=getTop(solidmainCss);
                    clientWidth=solidmainCss.offsetWidth||solidmainCss.clientWidth;
                   
                    }
                    else if(issimple && surveycontent){
                      xy2=getTop(surveycontent);
                      clientWidth=surveycontent.clientWidth;
                   }
                   else if(container){
                     xy2=getTop(container);
                      clientWidth=container.clientWidth;
                   }
                     if(!xy2)return;
                     var lWidth=0;
                      var leftQ=xy2.x+clientWidth-lWidth; 
                      divLeftBar.style.left=leftQ+"px";
                      xTop=getTop(surveycontent).y;
                      var docHeight=document.documentElement.clientHeight||document.body.clientHeight;
                      if(xTop>docHeight/2)
                        xTop=docHeight/2;
             }
              addEventSimple(window,"resize",resizeLeftBar);
              resizeLeftBar();
             addEventSimple(window,"scroll",mmq);
             mmq();
             var hasDisplayed=false;
            function mmq()   
            {   
              var posY=document.documentElement.scrollTop||document.body.scrollTop;
              if(divLeftBar){
                  divLeftBar.style.top=posY+xTop+"px"; 
              }
            }  
             function getTop(e)
            {
                if(!e)return;
                var x = e.offsetLeft;
                var y = e.offsetTop;
                while(e = e.offsetParent)
                {
                    x += e.offsetLeft;
                    y += e.offsetTop;
                }
                return {"x": x, "y": y};
            }
        </script>
							<div style="clear: both;"></div>
						</div>


						<div style="clear: both;"></div>
						<script type="text/javascript">
    var hasQJump = "";
</script>



						<script type="text/javascript">
//总页数，问卷相关
var totalPage=1;
var hasDate ="";
var hasSlider ="";
var TouPiaoPay=0;
//var qstr = 'false§false¤page§1§§2§¤radio§1§1§true§false§0§true§ceping§0§0§§§false〒1〒0§false〒2〒0¤radio§2§1§true§false§0§true§§0§4§§§false〒1〒§false〒2〒§false〒3〒§false〒4〒§false〒5〒';//所有问题，与服务器端交互
var qstr = '<s:property value="qstr" escape="false"/>'
//其它交互
var starttime="2013/7/26 14:11:12";
var valuator = '<s:property value="#parameters.valuator"/>';
var tmpojectId = '<s:property value="#parameters.pid"/>';
var subjectId = '<s:property value="#parameters.sid"/>';
var trainingCollege = '<s:property value="#parameters.tc"/>';
var paperType = '<s:property value="#parameters.type"/>';
langVer=0;
var langsufix="";
if(langVer==1)
  langsufix="_en";
else if(langVer==2)
 langsufix="_tw";
else if(langVer==3)langsufix="_jp";
var sjUser='';
var tdCode="ctl00_ContentPlaceHolder1_JQ1_tdCode";
var guid = "";var mobileRnum="";var onlyMailSms="";
var sourceDetail = "未知";
if(document.referrer)
   sourceDetail=document.referrer;
var nv='';
var source = '';
var udsid='';
var eproguid = '';
var eprotype='';
var parterparm='';
var parterparmname='';
 var operation = 'i';
 var userGuid='';
 var activityId = '<s:property value="paper.id"/>';
 var code = '';
 var simple = '';
  var qinvited = '';
  var qwidth='';
   var parterid = '';
   if(!window.isRunning)
      window.isRunning="true";
   var displayPrevPage="none";
   <s:if test="%{uid == 'success'}">
   var isPub="1";
   </s:if>
   <s:else>
   var isPub="";
   </s:else>
   var isSuper="";
   if(!parterid)
      parterid='';
var execute = "true";
  var needRegister="false";
  var hasJoin='';
  var nfjoinid='';
 var promoteSource="";
 var lastSavePage=0;
 var lastSaveQ=0;
  var jiFen="0";
 var hrefPreview = document.getElementById("ctl00_ContentPlaceHolder1_JQ1_hrefPreview");
 var afterDigitPublish = 1;
 var inviteid='';
 var survey = document.getElementById("ctl00_ContentPlaceHolder1_JQ1_surveyContent");
var refu='';
var isTest=''&&isPub;
var isPreview='';
var Password = "";

var isProduction="true";
var wbid='';
var needJQJiang=0;
var IsSampleService=0;
var divDec="ctl00_ContentPlaceHolder1_JQ1_divDec";
 try{
        HTMLElement.prototype.click = function() {
    var evt = this.ownerDocument.createEvent('MouseEvents');
    evt.initMouseEvent('click', true, true, this.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
    this.dispatchEvent(evt);
    } 
    }catch(ex){};
 

if(execute=="true")
{  
    document.write(
            '<script type="text/javascript" language="javascript"' + ' src="../css/que/hintinfo'+langsufix+'.js"><' + '/script>'+
            '<script type="text/javascript" language="javascript"' + ' src="../css/que/jqnew2.js"><' + '/script>');
 }
 var refer=document.referrer;
 if(!refer)refer="";
 else refer=refer.toLowerCase();
 var isFromSojiang=0;
 var isLogin=true;
 var CurrentDomain=1;
 var jiFenBao=0;var HasJiFenBao=0;
 var sojumpParm="";
 function gotoReg(){
    PDF_launch(url,740,420, function () {
		            if(!isLogin)gotoReg();
                    else window.location.href=window.location.href;
	    });
 }
   if(needRegister=="true" && isRunning=="true" && refer.indexOf("/jq/")==-1) {
         isLogin=false;
         alert('此问卷赠送'+jiFen+'个积分（积分可以兑换支付宝现金或礼品），您必须注册或登录后填写才能获取积分。');
           var url='/register/registers.aspx?eproguid='+"&type=1";
           window.onload=function(){
             gotoReg();
           };
     }
     else if(jiFenBao>0){
       window.onload=function(){
          PDF_launch("/wjx/design/setalipay.aspx?activity=2586349",340,220, function () {
		   var spanaliAccount=document.getElementById("spanaliAccount");
           if(spanaliAccount && window.alipayAccount)
           spanaliAccount.innerHTML="集分宝会赠送到<b style='font-size:13px;color:red;'>"+window.alipayAccount+"</b>，";
	    });
       }
     }
     else if(!isTest && window.location.href.toLowerCase().indexOf("/jq/")>-1 && window.PDF_launch){//&& isRunning!="true" 
        var tMsg=document.getElementById("spanNotSubmit");var val='';
        if(tMsg) val=tMsg.getAttribute("value");
        var divNotRun=document.getElementById("divNotRun");
        if(tMsg&&tMsg.innerHTML && (!getCookie("noJQPromote")||val=="1") && CurrentDomain &&divNotRun){
          divNotRun.innerHTML="<div style=' margin-top:30px;'>"+tMsg.parentNode.innerHTML+"<div style='margin-top:10px;'><input type='button' value='确定' class='operation' onclick='window.parent.PDF_close();' /></div></div>";
          window.onload=function(){
           PDF_launch("divNotRun",520,120);
             setCookie("noJQPromote","1",null,"/","",null);
           };
        }
     }


 
function getCookieVal(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1) {
        endstr = document.cookie.length;
    }
    return unescape(document.cookie.substring(offset, endstr));
}
function getCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg) {
            return getCookieVal(j);
        }
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
    }
    return "";
}
function setCookie(name, value, expires, path, domain, secure) {
    document.cookie = name + "=" + escape(value) +
            ((expires) ? "; expires=" + expires : "") +
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            ((secure) ? "; secure" : "");
}
  function closeInfo()//关闭
   {
        var divInfo= document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divPreview");
        divInfo.style.display="none";
   }
  var cProvince="";
  var cCity="";
  var cIp="";
  var NeedSearchKeyword=1;
  var allowCopy=0 ;
  var allowViewStat=0;
  var Search_Keyword='';
  var allowSaveJoin='';
  if(isPub && onlyMailSms){
    if(!guid&&!mobileRnum){
      alert("提示：此问卷只允许从问卷星系统发送的邮件和短信中包含的问卷链接访问。\r\n您是问卷发布者，可以从普通链接访问！");
    }
  }
  var cepingCandidate="";
</script>

						<script type="text/javascript">
  var needAvoidCrack=0;
  var AvoidCrackSetOpen=0;
</script>

						<script type="text/javascript">
try{document.execCommand("BackgroundImageCache", false, true);}
catch(ex){}
</script>

						<script type="text/javascript">
    try {
        sourceDetail = '';
    }
    catch (ex) { }
    
</script>

						<div style="clear: both;"></div>
					</div>


				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="footercss">
			<div id="footerLeft"></div>
			<div id="footerCenter"></div>
			<div id="footerRight"></div>
		</div>
		<div style="clear: both; height: 10px;"></div>
		<div style="height: 20px;">&nbsp;</div>
	</div>

	<div style="clear:both;"></div>

	</div>

</body>
</html>