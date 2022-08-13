<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<html>
<head>
<title></title>
<link href="/entity/function/testpaper/menu/js/tree/menu.css" rel="stylesheet" type="text/css">
<script src="/entity/function/testpaper/menu/js/tree/menu.js" type="text/javascript"></script>
<script language="JavaScript">
function window_onload()
{
	initialize();
	setTimeout(expandall,500);
}
//
var judge=1;
function expandall()
{
	var o = document.getElementById('topB');
	if (judge==0)
	{
	
		closeAll();
		judge=1;
		o.src='/entity/function/testpaper/menu/js/tree/icon-closeall.gif';
		o.alt='全部展开';
		document.getElementById("topText").innerText = "( 展开全部 )";
	}
	else
	{
		openAll();
		judge=0;
		o.src='/entity/function/testpaper/menu/js/tree/icon-expandall.gif';
		o.alt='全部折叠';
		document.getElementById("topText").innerText = "( 折叠全部 )";
	}
}
</SCRIPT>
</head>
<body class="scllbar" style="background-color:transparent" bgcolor="#FFFFFF" text="#000000" onselectstart="return false;"  oncontextmenu="" onLoad="window_onload()">
<table id=control width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="16" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand"  onclick="expandall(document.getElementById('topB'))"><img src="/entity/function/testpaper/menu/js/tree/icon-expandall.gif" id="topB" width="16" height="15" class="button" vspace="2" alt="全部展开"></td>
    <td id="topText" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand;padding-top:4px"  onclick="expandall(document.getElementById('topB'))">( 折叠全部 )</td>
  </tr>
</table>
<table border=0>
  <tr>
    <td>
	<script language="javascript" type="text/javascript">
	// treemenu 的参数意义依次为：资源目录，如"treemenu/image/";树名称；树图标

	//objTree	= new treemenu("","课程超市－课程列表","../js/tree/usericon.gif");
	// add_item 的参数意义依次为：该项编号，不能重复；父id，所属的上一层的编号；该项显示的内容；折叠时的图标；展开时的图标；网址或命令；指向的窗口
	
	<s:set name='titleStr' value='titleStrList'/>
	<s:iterator value='leftQuestionList' id='list' status='stus'>
		<s:if test="#list[0] != null && #list[0].size()>0">
			
			add_item(<s:property value='%{#stus.index + 1}'/>,0,"<s:property value='#titleStr[#stus.index]'/>","/entity/function/testpaper/menu/js/tree/close.gif","/entity/function/testpaper/menu/js/tree/open.gif","#","");
			
			<s:set name='serial' value='#list[1]'/>
			<s:set name='title' value='#list[2]'/>
			<s:set name='num' value='#list[3]'/>
			<s:iterator value='#list[0]' id='question' status='stas'>
				add_item(<s:property value='#serial[#stas.index]'/>,<s:property value='%{#stus.index + 1}'/>,"<s:property value='#title[#stas.index]'/>","/entity/function/testpaper/menu/js/tree/mono.gif","/entity/function/testpaper/menu/js/tree/usericon.gif","/entity/studyZone/courseResources_previewInfo.action?question_id=<s:property value='#question.id'/>&qNum=<s:property value='#num[#stas.index]'/>","submain");
			</s:iterator>
		</s:if>
	</s:iterator>
		
	// menu 的参数意义为：所要显示的树的父id；

	// 该函数返回树的html代码，需要由 write 函数输出。

	document.write(menu(0));
	//parent.parent.submain.location= "/entity/studyZone/courseResources_previewQuestion.action";
	parent.parent.submain.location= "/entity/studyZone/courseResources_previewQuestion.action?question_id=<s:property value='leftQuestionList[0][0][0].id'/>&qNum=1";
	</script>
    </td>
  </tr>
</table>
</body>
</html>