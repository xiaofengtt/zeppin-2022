// ************************************************************************
// NavBar.htm	Custom Navigation Bar(自制导航条)
// 2000/12/04	董国杰
// 导航栏1.0版
// 作者∶董国杰       E-mail: webmaster@biaoge.com.cn
// Copyleft@ 1999年，北京用友华表软件技术有限公司 http://www.biaoge.com.cn
// 您可以为任何目的使用，拷贝，修改，分发本代码,但必须在所有分发的软件及材料
// 中保留本声明。如果您将此导航条用在Internet上，请求您为我们做一个链接"华表，
// 中国人的电子表格"，并指向http://www.biaoge.com.cn。

// HtmlMenu version 1.0
// Author: Dongguojie        E-mail: webmaster@biaoge.com.cn
// Copyleft@ 1999,Cell Software Co., Ltd. http://www.cellactivex.com
// Permission to use, copy, modify, and distribute this software and its documentation for  
// any purpose is hereby granted without fee, provided that the above copyright notice, 
// author statement and this permission notice appear in all copies of this software and 
// related documentation.
// If you use this Navigation Bar on Internet, we ask for you making a hyperlink to http://www.cellactivex.com, 
// named "Cell Control, essential tool for applications".
// ************************************************************************


var g_arrayMenuBar = new Array();
var g_lTotalMenuBars = 0;
var g_winDocAll = null;
function flybar_keydown()
{
	if (event.ctrlKey)
	{
		return;
	}

	switch( event.keyCode )
	{
		case 13:	//Enter
		case 32:	//space
			event.cancelBubble=false;
			event.returnValue=false;
			ToggleTab( event.srcElement.id );
			break;
	}
}

function ToggleTab(srcElementID)
{
	event.cancelBubble = true;
	event.returnValue = false; 

	var tempEl;
	var srcEl = g_winDocAll[srcElementID];
	g_winDocAll['nbTableMain'].style.height = "100%";
	for( i = 0; i < g_lTotalMenuBars; i++)
	{
		if(srcEl.id == g_arrayMenuBar[i])
		{
			g_winDocAll['id' + g_arrayMenuBar[i]].style.height = "100%";
			g_winDocAll['id' + g_arrayMenuBar[i]].style.display="";
		}
		else
		{
			g_winDocAll['id' + g_arrayMenuBar[i]].style.height = 0;
			g_winDocAll['id' + g_arrayMenuBar[i]].style.display="none";
		}    
	}
}

function window.onload()
{
	g_winDocAll = window.document.all;
	g_winDocAll['idFile'].style.height = "100%";
	g_winDocAll['idFile'].style.display="";
	nbTableMenu.style.display="";
}

function window.onresize()
{
	try
	{
		if (!g_fPublicOnly)
		{
			if( "none" == g_winDocAll['idOutbarpane'].style.display )
			{
				var nHeight = document.body.clientHeight - g_winDocAll['nbTableMain'].offsetHeight;
				g_winDocAll['objTree'].style.height = nHeight;
			}
		}
		else
		{
			var nHeight = document.body.clientHeight - g_winDocAll['nbTableMain'].offsetHeight;
			g_winDocAll['objTree'].style.height = nHeight;
		}
	}
	catch(e)
	{
		
	}
}

function Navigate(url)
{
	if(parent != null && url != null)
	{
		if(parent.viewer != null)
		{
			parent.viewer.location = url;
		}   
	}
}

function slide_Tabs()
{
	ToggleTab(event.srcElement.parentElement.id);
}
