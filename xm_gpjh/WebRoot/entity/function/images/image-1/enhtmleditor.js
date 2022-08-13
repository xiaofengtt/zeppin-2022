var whaty_olddocmd;

function SetCookie (name, value)
{
	var argv = SetCookie.arguments;
	var argc = SetCookie.arguments.length;
	var expires = (2 < argc) ? argv[2] : null;
	var path = (3 < argc) ? argv[3] : null;
	var domain = (4 < argc) ? argv[4] : null;
	var secure = (5 < argc) ? argv[5] : false;
	
	document.cookie = name + "=" + escape (value) +
		((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
		((path == null) ? "" : ("; path=" + path)) +
		((domain == null) ? "" : ("; domain=" + domain)) +
		((secure == true) ? "; secure" : "");
}

function savefile()
{
	var expdate = new Date();
	var text = myEditor.frames.textEdit.document.body.innerHTML;
	expdate.setTime(expdate.getTime() +  (24 * 60 * 60 * 1000 * 365));
	SetCookie("content",text, expdate, "/", null, false);
}

// cancels the table selector when you click on DHTML Panel
function CheckLocalImage()
{
	var eb = document.all.editbar;
	var hasLocalfile=true,return_value;
 	var oControlRange = eb._editor.frames.textEdit.document.getElementsByTagName("img");
	for (i = 0; i < oControlRange.length; i++)
	{
		filename=oControlRange(i).getAttribute("src");
		if (filename.indexOf("file")==0) {
	
	input_box=confirm("文件:"+filename+"\n是本地文件，需要上载到服务器才能正常显示给他人浏览。\n点击确定上载图片，点击取消后将删除该图片");
	if (input_box==true)
	{ 
		
	return_value=showModalDialog("insertLocalImage.html",filename,"scroll:no;resizable:yes;dialogWidth:40;dialogHeight:20;dialogTop:50;dialogLeft:500;status:yes;");
	alert(return_value);
	if (return_value != "")
	{
		
	//      doInsert(return_value);
	for (j=i+1;j< oControlRange.length;j++)
	{
		
		if(oControlRange(i).getAttribute("src")==oControlRange(j).getAttribute("src")) oControlRange(j).setAttribute("src",return_value);
		
	}
	oControlRange(i).setAttribute("src",return_value)
	hasLocalfile=false;
	}
	else hasLocalfile=false;
	
	}
	else
	{
	// Output when Cancel is clicked
	oControlRange(i).outerHTML="";
	hasLocalfile=false;
	}

			//hasLocalfile=false;
	}
			
	}
return hasLocalfile;

}

function CheckLocalLink()
{
	var eb = document.all.editbar;
	var hasLocalfile=true,return_value;
 	var oControlRange = eb._editor.frames.textEdit.document.getElementsByTagName("a");
	for (i = 0; i < oControlRange.length; i++)
	{
		filename=oControlRange(i).getAttribute("href");
		if (filename.indexOf("file")==0) {
	
	input_box=confirm("文件:"+filename+"\n是本地文件，需要上载到服务器才能正常显示给他人浏览。\n点击确定上载文件，点击取消后将删除该文件");
	if (input_box==true)
	{ 
		
	return_value=showModalDialog("insertLocalLink.html",filename,"scroll:no;resizable:yes;dialogWidth:40;dialogHeight:20;dialogTop:50;dialogLeft:500;status:yes;");
	
	if (return_value != "")
	{
		
	//      doInsert(return_value);
	for (j=i+1;j< oControlRange.length;j++)
	{
		
		if(oControlRange(i).getAttribute("href")==oControlRange(j).getAttribute("href")) oControlRange(j).setAttribute("href",return_value);
		
	}
	oControlRange(i).setAttribute("href",return_value)
	hasLocalfile=false;
	}
	else hasLocalfile=false;
	
	}
	else
	{
	// Output when Cancel is clicked
	oControlRange(i).outerHTML="";
	hasLocalfile=false;
	}

			//hasLocalfile=false;
	}
			
	}
return hasLocalfile;

}

function getText()
{
	var abc;
	
	var eb = document.all.editbar;
	var oControlRange = eb._editor.frames.textEdit.document.selection.createRange();
	if(eb._editor.frames.textEdit.document.selection.type=="Text")
		abc=oControlRange.text;
	else
		abc="";
return abc;
	
}
function getEQ()
{
	var myEQ;

	myEQ="";

	var eb = document.all.editbar;
 	var oControlRange = eb._editor.frames.textEdit.document.selection.createRange();

    for (i = 0; i < oControlRange.length; i++)
{
   if(oControlRange(i).tagName.toLowerCase()=="img")
   {
   	if(oControlRange(i).getAttribute("src").indexOf("MathML")>=0)
   	{
   		return oControlRange(i).getAttribute("src");
   	}
   	
   	}
      	
  }
	return myEQ;
}

function getDRAW()
{
	
	var myDRAW;
	myDRAW="";

	var eb = document.all.editbar;
	var oControlRange = eb._editor.frames.textEdit.document.getElementsByTagName("APPLET");
	
    for (i = 0; i < oControlRange.length; i++)
{
      alert(oControlRange(i).outerHTML);
      if (oControlRange(i).outerHTML.indexOf("whatyCML")>=0) myDRAW=oControlRange(i).innerHTML;
      
      var oParam=oControlRange(i).getElementsByTagName("PARAM");
      
      for (j=0;j<oParam.length;j++)
      {
      	//if (oParam(j).getAttribute("NAME")=="molcontent") myDRAW=oParam(j).getAttribute("VALUE");
      	
	}
      
  }

	return myDRAW;

	
}

function getCML()
{
	
	var myCML;
	myCML="";
	var eb = document.all.editbar;
	var oControlRange = eb._editor.frames.textEdit.document.getElementsByTagName("APPLET");
	
    for (i = 0; i < oControlRange.length; i++)
{
      //alert(oControlRange(i).outerHTML);
      if (oControlRange(i).outerHTML.indexOf("whatyCML")>=0) 
      
      {myCML=oControlRange(i).innerHTML;
      
      var oParam=oControlRange(i).getElementsByTagName("PARAM");
      //var oParam=oControlRange(i).getElementsByName("molcontent");
      
      //alert(oParam.length);
      
      for (j=0;j<oParam.length;j++)
      {
//      	alert(oParam(j).getAttribute("VALUE"));
      //	if (oParam(j).getAttribute("NAME")=="molcontent") alert(oParam(j).getAttribute("VALUE"));
      	// myCML=oParam(j).getAttribute("VALUE");
      	
	}
	
	}

}      

	myCML=replaceSubstring(myCML,"yes","no");
	return myCML;
}
function doDown() 
{

	if(typeof(document.all.frame1) != "undefined") 
	{
		if(document.all.frame1.style.visibility == "visible") 
		{
			cancelTable(false);
			return;
		}
	}

	if (event.button == 2 | event.button == 3)
	{
		onlinehelp();
	
	} 
	else
	{
		document.frames.myEditor.selectRange();
		el = getReal(window.event.srcElement, "className", "coolButton");
		var cDisabled = el.cDisabled;
		cDisabled = (cDisabled != null);
		if ((el.className == "coolButton") && !cDisabled) 
		{
			makePressed(el)
		}
	
	}
}

function palette() 
{
	var return_value = showModalDialog("palette.html","","dialogWidth:18;dialogHeight:16;dialogTop:50;dialogLeft:500;status:no;");
	if (return_value != "")
		doFormat("ForeColor",return_value)
}

function forepalette() 
{
	var return_value = showModalDialog("palette.html","","dialogWidth:18;dialogHeight:16;dialogTop:50;dialogLeft:500;status:no;");
	if (return_value != "")
	{
		doforetype(return_value);
		document.all.colorimg.style.backgroundColor = return_value;
	}
}

function testselect() 
{
	doFormat("SelectAll");
	doFormat("Copy");
	//alert("Hello!");

}

function insertSpecialchars() 
{
	window.open("specialchars.html","","width=400,height=300");
}

//dropdown table selector
function onTable(num) 
{
	//open's table dialog window
	//if it's in a table

	var str = "<div id=\"tblsel\" style=\"background-color:blue;position:absolute;";
	str = str + "width:0;height:0;z-index:-1;\"></div>";
	str = str + makeTable(4, 5);
	str = str + "<div style=\"text-align:center\" id=\"tblstat\">1 × 1 的表格</div>";
	
	var ifrm = document.frames("frame1");
	var obj=document.all.quicktable;
	var x=0;
	var y=0;
        
        ifrm.document.body.innerHTML=str;

	while(obj.tagName!="BODY") 
	{
		x+=obj.offsetLeft;
		y+=obj.offsetTop;
		obj=obj.offsetParent;
	}	
	
	document.all.frame1.style.pixelTop = y + 24;
	document.all.frame1.style.pixelLeft = x;
	document.all.frame1.style.pixelWidth = 0;
	document.all.frame1.style.pixelHeight = 0;
	document.all.frame1.style.visibility = "visible";
	//bind events
	ae_hot=num;
	document.frames("frame1").document.body.onmouseover = paintTable;	
	document.frames("frame1").document.body.onclick = insertTable;
	if(typeof(document.onmousedown)=="function")
		whaty_olddocmd = document.onmousedown;
	else ae_olddoccmd=null;
	//alert(document.onmousedown);
	document.onmousedown = cancelTable;
	//DHTMLSafe.onmousedown = cancelTable;
	event.cancelBubble = true;

	ifrm.document.body.onselectstart=new Function("return false;");
	

	document.all.frame1.style.pixelWidth = ifrm.document.all.oTable.offsetWidth + 3;
	document.all.frame1.style.pixelHeight = ifrm.document.all.oTable.offsetHeight + 3 +
		ifrm.document.all.tblstat.offsetHeight;

}

function insertTable(rows, cols, attrs) 
{
	var trows,tcols,tattrs;
	var i,j;
	var TableHtml;

	if (typeof(rows) == "undefined") 
	{
		var se = document.frames('frame1').window.event.srcElement;

		if(se.tagName!='TD' && se.tagName!='TBODY' && se.tagName!='TABLE') 
		{
			alert(se.tagName);
			cancelTable();
				return;
		}
		trows = se.parentElement.rowIndex + 1;
		tcols = se.cellIndex + 1;
	  	tattrs = "border=1 cellPadding=0 cellSpacing=0 width=75%";
                
		cancelTable();
	}
       	else
       	{
		trows=rows;
		tcols=cols;
		tattrs=attrs;
	}

	TableHtml="<table "+tattrs+">";
	
	for(i=0;i<trows;i++)
	{
		TableHtml+="<tr>";
	  	for(j=0;j<tcols;j++)
		{
			TableHtml+="<td> </td>";
		}
		TableHtml+="</tr>";
	}
	TableHtml+="</table>";
	doInsert(TableHtml);
}
function insertLink() 
{
	var abc;
	abc= getText();
	var eb = document.all.editbar;
	eb._editor.frames.textEdit.document.focus();
	var return_value=showModalDialog("insertLink.htm",abc,"dialogWidth:30;dialogHeight:10;dialogTop:50;dialogLeft:500;status:no;");
	
	if (return_value != "")
	{
	      doInsert(return_value);
	       		
	}
}
function insertImage() 
{
	window.open("insertImage.jsp","","width=350,height=400");
}

function insertFile() 
{
	window.open("insertFile.jsp","","width=275,height=350");
}

function insertFlash() 
{
	window.open("insertFlash.jsp","","width=350,height=400");
}


function DrawImage() 
{
	window.open("WebDraw/draw.htm","","width=550,height=460");
}
function recorder()
{
	window.open("recorder.jsp","","width=300,height=350");
}

function MolecularFormula() 
{
	window.open("CML/draw.htm","","width=500,height=440");
}

/*function Equation() 
{
	var return_value=showModalDialog("MathML/draw.htm","","dialogWidth:45;dialogHeight:20;dialogTop:50;dialogLeft:500;status:no;");
	
	if (return_value != "")
	{
	      doInsert(return_value);
	       		
	}
         
}
*/

function Equation() 
{
	//var myEQ;
	//myEQ= getEQ();
	
	var eb = document.all.editbar;
	eb._editor.frames.textEdit.document.focus();
	//var return_value=showModalDialog("MathML/draw.htm",myEQ,"dialogWidth:45;dialogHeight:20;dialogTop:50;dialogLeft:500;status:no;");
	
	//var return_value=showModelessDialog("MathML2/popupindex.html","","dialogWidth:49.5;dialogHeight:34.6;dialogTop:50;dialogLeft:500;status:yes;scroll:no");
	window.open("MathML2/popupindex.jsp","MathML","width=785,height=498,status=yes");
	////if (return_value != "")
	//{
	  //    doInsert(return_value);
	       		
	//}
         
}
function onlinehelp() 
{
	showModalDialog("help.html","","dialogWidth:18;dialogHeight:16;dialogTop:50;dialogLeft:500;status:no;");
	
}

function sendfile()
{
	
		
		var error = false;
		var errorString = "警告!";
		var text = myEditor.frames.textEdit.document.body.innerHTML;
		alert(text);
		if (!error) 
		{
			var f = document.forms.postForm
			f.content.value = text;
			if (document.forms.postForm.title.value == "")
			{
				alert("标题不能为空");
			}
			else if (text == "")
			{
				alert("内容不能为空");
			}
			
			else
			{	
				f.submit();			
			}
		}
		else
			alert(errorString);
	
}

//	document.designMode="Off";
function showmore()
{


	if(document.all["editbar"].style.display=="none")
	{
		document.all["editbar"].style.display="";
		document.all["more"].style.display="";
		
		document.all["fore"].style.display="none";
		document.all["editbar1"].style.display="none";
	}
	else
	{
		document.all["editbar1"].style.display="";
		document.all["fore"].style.display="";
		
		document.all["editbar"].style.display="none";
		document.all["more"].style.display="none";
	
	}
	 
}


function  addTable()
{
	window.open("table.html","","width=450,height=300");
}


function  addTable_Old()
{
  	var return_value= showModalDialog("insertTable.html","","dialogWidth:20;dialogHeight:16;dialogTop:50;dialogLeft:500;status:no;");

  	if (return_value != "")
	{
     		doInsert(return_value);
	} 
}

function winhidden()
{
	window.close();
}

function fixSize() 
{
	document.all.edit1.style.height = Math.max(document.body.clientHeight - document.all.edit1.offsetTop, 0);
}

function init() 
{
	fixSize();
}

var activeCSS	= "border: 1 solid buttonface; color: windowtext; cursor: text;";
var inactiveCSS	= "border: 1 solid window; cursor: hand; color: red;";
var validTextColor = "windowtext";
var invalidTextColor = "buttonshadow";

// 调用格式命令
function doFormat(what) 
{
	var eb = document.all.editbar;
	eb._editor.setFocus();
	eb._editor.execCommand(what, arguments[1]);
	
}

function doSelectClick(str, el) 
{
	var Index = el.selectedIndex;
	if (Index != 0)
	{
		el.selectedIndex = 0;
		if (el.id == "specialtype")
			document.all.editbar._editor.specialtype(el.options[Index].value);
		else
			doFormat(str,el.options[Index].value);
	}
}

function doforetype(colorvalue)
{
	document.all.editbar._editor.foretype(colorvalue);
}

function doInsert(str) 
{
	document.all.editbar._editor.setFocus();
	document.all.editbar._editor.frames.textEdit.document.focus();
	document.all.editbar._editor.insertText(str);
}

document.onmouseover = doOver;
document.onmouseout  = doOut;
document.onmousedown = doDown;
document.onmouseup   = doUp;


function doOver() 
{
	var toEl = getReal(window.event.toElement, "className", "coolButton");
	var fromEl = getReal(window.event.fromElement, "className", "coolButton");
	if (toEl == fromEl) return;
	var el = toEl;
	var cDisabled = el.cDisabled;
	cDisabled = (cDisabled != null); 
	if (el.className == "coolButton")
		el.onselectstart = new Function("return false");
	if ((el.className == "coolButton") && !cDisabled) 
	{
		makeRaised(el);
		makeGray(el,false);
	}
}

function doOut() 
{
	var toEl = getReal(window.event.toElement, "className", "coolButton");
	var fromEl = getReal(window.event.fromElement, "className", "coolButton");
	if (toEl == fromEl) return;
	var el = fromEl;
	var cDisabled = el.cDisabled;
	cDisabled = (cDisabled != null);
	var cToggle = el.cToggle;
	toggle_disabled = (cToggle != null);
	if (cToggle && el.value) 
	{
		makePressed(el);
		makeGray(el,true);
	}
	else if ((el.className == "coolButton") && !cDisabled) 
	{
		makeFlat(el);
		makeGray(el,true);
	}
}

function doUp() 
{
	el = getReal(window.event.srcElement, "className", "coolButton");
	
	var cDisabled = el.cDisabled;
	cDisabled = (cDisabled != null);
	
	if ((el.className == "coolButton") && !cDisabled) 
	{
		makeRaised(el);
	}
}

function getReal(el, type, value) 
{
	temp = el;
	while ((temp != null) && (temp.tagName != "BODY")) 
	{
		if (eval("temp." + type) == value) 
		{
			el = temp;
			return el;
		}
		temp = temp.parentElement;
	}
	return el;
}

function findChildren(el, type, value) 
{
	var children = el.children;
	var tmp = new Array();
	var j=0;	
	for (var i=0; i<children.length; i++) 
	{
		if (eval("children[i]." + type + "==\"" + value + "\"")) 
		{
			tmp[tmp.length] = children[i];
		}
		tmp = tmp.concat(findChildren(children[i], type, value));
	}	
	return tmp;
}

function disable(el) 
{
	if (document.readyState != "complete") 
	{
		window.setTimeout("disable(" + el.id + ")", 100);
		return;
	}	
	var cDisabled = el.cDisabled;	
	cDisabled = (cDisabled != null);
	if (!cDisabled) {
		el.cDisabled = true;
		el.innerHTML = 	'<span style="background: buttonshadow; width: 100%; height: 100%; text-align: center;">' +
				'<span style="filter:Mask(Color=buttonface) DropShadow(Color=buttonhighlight, OffX=1, OffY=1, Positive=0); height: 100%; width: 100%%; text-align: center;">' +
				el.innerHTML + '</span>' + '</span>';
		if (el.onclick != null) 
		{
			el.cDisabled_onclick = el.onclick;
			el.onclick = null;
		}
	}
}

function enable(el) 
{
	var cDisabled = el.cDisabled;	
	cDisabled = (cDisabled != null);
		
	if (cDisabled) 
	{
		el.cDisabled = null;
		el.innerHTML = el.children[0].children[0].innerHTML;
		if (el.cDisabled_onclick != null) 
		{
			el.onclick = el.cDisabled_onclick;
			el.cDisabled_onclick = null;
		}
	}
}

function addToggle(el) 
{
	var cDisabled = el.cDisabled;	
	cDisabled = (cDisabled != null);	
	var cToggle = el.cToggle;	
	cToggle = (cToggle != null);
	
	if (!cToggle && !cDisabled) 
	{
		el.cToggle = true;		
		if (el.value == null)
			el.value = 0;		
		if (el.onclick != null)
			el.cToggle_onclick = el.onclick;
		else 
			el.cToggle_onclick = "";
		
		el.onclick = new Function("toggle(" + el.id +"); " + el.id + ".cToggle_onclick();");
	}
}

function removeToggle(el) 
{
	var cDisabled = el.cDisabled;	
	cDisabled = (cDisabled != null);	
	var cToggle = el.cToggle;	
	cToggle = (cToggle != null);	
	
	if (cToggle && !cDisabled) 
	{
		el.cToggle = null;
		if (el.value) 
		{
			toggle(el);
		}
		makeFlat(el);		
		if (el.cToggle_onclick != null) 
		{
			el.onclick = el.cToggle_onclick;
			el.cToggle_onclick = null;
		}
	}
}
function toggle(el) 
{
	el.value = !el.value;	
	if (el.value)
		el.style.background = "URL(/images/tileback.gif)";
	else
		el.style.backgroundImage = "";
}

function makeFlat(el) 
{
	with (el.style) 
	{
		background = "";
		border 	= "1px solid buttonface";
		padding = "1px";
	}
}

function makeRaised(el) 
{
	with (el.style) 
	{
		borderLeft   = "1px solid buttonhighlight";
		borderRight  = "1px solid buttonshadow";
		borderTop    = "1px solid buttonhighlight";
		borderBottom = "1px solid buttonshadow";
		padding      = "1px";
	}
}
function makePressed(el) 
{
	with (el.style) 
	{
		borderLeft   = "1px solid buttonshadow";
		borderRight  = "1px solid buttonhighlight";
		borderTop    = "1px solid buttonshadow";
		borderBottom = "1px solid buttonhighlight";
		paddingTop    = "2px";
		paddingLeft   = "2px";
		paddingBottom = "0px";
		paddingRight  = "0px";
	}
}


function makeGray(el,b) 
{
    	var filtval;
    
    	if(b)
        	filtval = "gray()";
   	else
        	filtval = "";

    	var imgs = findChildren(el, "tagName", "IMG");
        
    	for (var i=0; i<imgs.length; i++) 
    	{
       		imgs[i].style.filter = filtval;
    	}

}

function paintTable() 
{

	var se = document.frames('frame1').window.event.srcElement;
	var is_ie4=false;
	var sr, sc, tbl, fAll;
			
	fAll = document.frames('frame1').document.all;
	
	if(se.tagName!='TD') 
	{
		sr = 0;
		sc = 0;
		var str="&nbsp;Cancel";
		fAll.tblsel.style.width = 0;
		fAll.tblsel.style.height = 0;
		return;
	}
	
	tbl=fAll.oTable;
	sr=se.parentElement.rowIndex;
	sc=se.cellIndex;
	
	//Expand the table selector if its too small
	if(!is_ie4) 
	{
		if(tbl.rows.length == sr+1) 
		{
			var r = tbl.insertRow(-1);
			var td;
			
			for(var i=0;i<tbl.rows(1).cells.length;i++) 
			{
				td = r.insertCell(-1);
				td.innerHTML = "&nbsp;";
				td.style.pixelWidth = 20;
				td.style.pixelHeight = 20;
	
			}
			
			var bdy = document.frames("frame1").document.body;			
			var ifrm = document.frames("frame1");
				
			document.all.frame1.style.pixelWidth = ifrm.document.all.oTable.offsetWidth + 3;
			document.all.frame1.style.pixelHeight = ifrm.document.all.oTable.offsetHeight + 3 +
					ifrm.document.all.tblstat.offsetHeight;
		}
		if(tbl.rows(1).cells.length == sc+1) 
		{
			var td;
			for(var i=0;i<tbl.rows.length;i++)
			{
				td = tbl.rows(i).insertCell(-1);
				td.innerHTML = "&nbsp;";
				td.style.pixelWidth = 20;
				td.style.pixelHeight = 20;
			}			
			var bdy = document.frames("frame1").document.body;
			
			document.all.frame1.style.pixelWidth = bdy.createTextRange().boundingWidth + 5;
			document.all.frame1.style.pixelHeight = bdy.createTextRange().boundingHeight + 5;
		}
	}
	
	var str=(sr+1) + " × " + (sc+1) + " 的表格";
	
	fAll.tblsel.style.width = se.offsetWidth*(sc+1)+5;
	fAll.tblsel.style.height = se.offsetHeight*(sr+1)+5;
	
	fAll.tblstat.innerHTML = str;
}



function makeTable(rows, cols) 
{
	var a, b, str, n;
	
	str = "<table style=\"table-layout:fixed;border-style:solid; cursor:default;\" "; 
	str = str + "id=\"oTable\" cellpadding=\"0\" ";
	str = str + "cellspacing=\"0\" cols=" + cols;
	str = str + " border=6>\n";

	for (a=0;a<rows;a++) 
	{
		str = str + "<tr>\n";
		for(b=0;b<cols;b++) 
		{			
			str = str + "<td width=\"20\">" 
			str = str + "&nbsp;</td>\n";	
		}	
		str = str + "</tr>\n";
	}
	str = str + "</table>"
	return str;
}

//Closes table selector iframe and replaces document mousedown
function cancelTable(a) 
{
	document.onmousedown=null;
	document.all.frame1.style.visibility = "hidden";
	document.all.frame1.style.pixelWidth = 0;
	document.all.frame1.style.pixelHeight = 0;

	if(a==false) return;

	if(typeof(whaty_olddocmd)=="function") 
	{
		//whaty_olddocmd(false);
		document.onmousedown = whaty_olddocmd;
	}
	whaty_olddocmd = null;
	
	//alert("Cancel Table");
	//Set DropDownTable IFrame to small
	//document.onmousedown = whaty_olddocmd
	
	document.all.frame1.style.pixelWidth = 10;
	document.all.frame1.style.pixelHeight = 10;
	
}


document.write("<style>");
document.write(".coolBar {background: buttonface;border-top: 1px solid buttonhighlight;	border-left: 1px solid buttonhighlight;	border-bottom: 1px solid buttonshadow; border-right: 1px solid buttonshadow; padding: 2px; font: menu;}");
document.write(".coolButton {border: 1px solid buttonface; padding: 1px; text-align: center; cursor: default;}");
document.write(".coolButton IMG	{filter: gray();}");
document.write("</style>");