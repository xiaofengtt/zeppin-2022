/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2008 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Scripts related to the Image dialog window (see fck_image.html).
 */

var dialog		= window.parent ;
var oEditor		= dialog.InnerDialogLoaded() ;
var FCK			= oEditor.FCK ;
var FCKLang		= oEditor.FCKLang ;
var FCKConfig	= oEditor.FCKConfig ;
var FCKDebug	= oEditor.FCKDebug ;
var FCKTools	= oEditor.FCKTools ;

var bEqButton = ( document.location.search.length > 0 && document.location.search.substr(1) == 'EquationButton' ) ;


var strTable;
var mathlabel = new  Array("代 数","微积分","统计学","矩 阵","集 合","三 角","几 何","自定义1","自定义2","自定义3","MathML");
var mathlabeltotalnum=new  Array(20,15,13,10,16,20,13,7,0,0,0);

function do_mml() {

mml_string ="getPreferredWidth:" + document.popup.getPreferredWidth() + "getPreferredHeight :"+document.popup.getPreferredHeight()+" Font Size: " + document.popup.getFontSize() + " MathML" + document.popup.getPackedMathML();
   // should set hidden input values in the form page (window.opener)
   //window.document.myForm.mml.value = mml_string;
  // window.document.myForm.posttype.value = "addeqn";

   // Then submit the window.opener form
  // alert(mml_string);
mml_string=" imgbase64: "+document.popup.getEquationImageBase64("png");
//mml_string=" imgbase64: "+document.popup.getEquationImageBase64Png();
   //alert(mml_string);


document.controler.MathML.value=document.popup.getPackedMathML();
document.controler.width.value=document.popup.getPreferredWidth();
document.controler.height.value=document.popup.getPreferredHeight();
document.controler.size.value=document.popup.getFontSize();
document.controler.image.value=document.popup.getEquationImageBase64(document.controler.imgtype.value);
   //window.opener.document.myForm.submit();
return true;
   // Then close itself
   //window.close();
}


function vier_input() {

if(viewcontrol.style.visibility!="hidden")
	{
	viewcontrol.style.visibility="hidden";
	inputcontrol.style.visibility="visible"
	document.controler.bview.value="预　览";
	
	}
else 
	{
	viewcontrol.style.visibility="visible";
	inputcontrol.style.visibility="hidden"
	document.controler.bview.value="编　辑";
	document.eq2.setMathML(document.popup.getPackedMathML());
	
	
	
//	alert(parseInt (viewcontrol.style.width));
	if (document.popup.getPreferredWidth()>(parseInt (viewcontrol.style.width)-25))
	document.eq2.width=document.popup.getPreferredWidth();
  else document.eq2.width=742;
//	alert(parseInt (document.popup.getPreferredHeight()));
	if (document.popup.getPreferredHeight()>(parseInt (viewcontrol.style.height)-20))
	document.eq2.height=document.popup.getPreferredHeight();
  else document.eq2.height=200;

	
	}	
	
}

function clear_mml() {
document.popup.setMathML("<math><mrow> </mrow></math>")
}

function getMathMLFromPop()
{
document.forms[0].MathML.value=document.popup.getPackedMathML();
}

function setMathMLFromTextarea()
{
insertMathML(document.forms[0].MathML.value);
}

function insertMathML(eq)
{
document.popup.insertMathMLAtCursor(eq);
}
function loadMathML(filename)
{
//alert(FCKConfig.PluginsPath+"mathEquation/"+filename);
document.popup.insertMathMLFileAtCursor(FCKConfig.PluginsPath+"mathEquation/"+filename);
document.eq2.setMathML(document.popup.getPackedMathML());

}

function gotopage(mtype,pagenum)
{
templatePanel.innerHTML=getstrTable(mtype,pagenum);
if(mtype==(mathlabel.length-1))
{
getMathMLFromPop();
}

}

function getstrTable(mtype,pagenum)
{
var MaxDataNum=mathlabeltotalnum[mtype];
var pagesize=10;
var TableHTML="";
var i,j,k;
pageup="上页";
base_mathpath="fck_mathml/";
strTable="<TABLE WIDTH=100% BORDER=0 align=\"center\" CELLPADDING=0 CELLSPACING=0>";
strTable+="<TR bgcolor=\"F5F5ED\"> ";
strTable+="    <TD height=\"17\" COLSPAN=20>&nbsp; </TD>";
strTable+="  </TR>";
strTable+="  <TR bgcolor=\"F5F5ED\"> ";
strTable+="    <TD width=\"42\" height=\"19\">&nbsp; </TD>";

if(mtype==0)
{
strTable+="    <TD width=\"57\" valign=\"bottom\" background=\"fck_mathml/images/index_12.jpg\" bgcolor=\"F5F5ED\"> ";
strTable+="      <div align=\"center\" class=\"t1\">代 数</div></TD>";
}
else 
{
strTable+="    <TD width=\"57\" valign=\"bottom\" background=\"fck_mathml/images/index_6.jpg\" bgcolor=\"F5F5ED\"> ";
strTable+="      <div align=\"center\"  style=\"cursor:hand;\" onclick=\"gotopage("+0+",0)\">"+mathlabel[0]+"</div></TD>";
     
}

for (i=1;i<mathlabel.length-1;i++)
{

if (i==mtype){
strTable+="    <TD width=\"57\" valign=\"bottom\" background=\"fck_mathml/images/index_12.jpg\"> ";
strTable+="      <div align=\"center\" class=\"t1\">"+mathlabel[i]+"</div></TD>";
 }
else
{
strTable+="    <TD width=\"57\" valign=\"bottom\" background=\"fck_mathml/images/index_7.jpg\"> ";
strTable+="      <div align=\"center\" style=\"cursor:hand;\" onclick=\"gotopage("+i+",0)\">"+mathlabel[i]+"</div></TD>";

} 
}


if(mtype==(mathlabel.length-1))
{
strTable+="    <TD COLSPAN=4 valign=\"bottom\" background=\"fck_mathml/images/index_12.jpg\" bgcolor=\"F5F5ED\"> ";
strTable+="      <div align=\"center\" class=\"t1\">MathML</div></TD>";
}
else 
{
strTable+="    <TD COLSPAN=4 valign=\"bottom\" background=\"fck_mathml/images/index_15.jpg\" bgcolor=\"F5F5ED\"> ";
strTable+="      <div align=\"center\" style=\"cursor:hand;\" onclick=\"gotopage("+(mathlabel.length-1)+",0)\">"+mathlabel[(mathlabel.length-1)]+"</div></TD>";
     
}


strTable+="    <TD width=\"81\" COLSPAN=2>&nbsp; </TD>";
strTable+="  </TR>";
strTable+="  <TR> ";
strTable+="    <TD COLSPAN=20 bgcolor=\"#FFFFFF\">";
strTable+="    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tb2\">";
strTable+="        <tr> ";
strTable+="          <td width=\"3%\" height=\"1\">&nbsp;</td>";
strTable+="          <td width=\"94%\">&nbsp;</td>";
strTable+="          <td width=\"3%\">&nbsp;</td>";
strTable+="        </tr>";
strTable+="        <tr> ";
strTable+="          <td height=\"124\">&nbsp;</td>";
strTable+="          <td valign=\"top\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tb\">";
strTable+="              <tr> ";
strTable+="                <td>";
strTable+="                  <table height=\"140\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
strTable+="                    <tr>";
strTable+="<td valign=\"top\">";
if(mtype==(mathlabel.length-1))
{
strTable+="<div align=center style=\"width:700;height:140;overflow:hidden;\">";
strTable+="<form><textarea name=\"MathML\" cols=\"113\" rows=\"8\" class=\"scr\"></textarea>";
strTable+="<BR><input type=button value=\"&or;插入公式区\" onclick=\"setMathMLFromTextarea()\" ><input type=button value=\"&and;放入文本区\" onclick=\"getMathMLFromPop()\" ></form>";
}
else {
	strTable+="<br>";
	if (pagenum==0) pageup="上页";
	else pageup="<a href=\"javascript:gotopage("+mtype+","+(pagenum-1)+");\">上页</a>";

	if ((pagenum+1)*pagesize>=MaxDataNum) pagedown="下页";
	else pagedown="<a href=\"javascript:gotopage("+mtype+","+(pagenum+1)+");\">下页</a>";
	strTable+="当前分类："+mathlabel[mtype]+" ";
	strTable+="共"+MaxDataNum+"条，"+pageup+"，"+pagedown+"<br>";
	strTable+="<div style=\"width:700;height:100;overflow:auto;\">";
		for(i=0;i<pagesize;i++)
		{
			j=pagenum*pagesize+i+1;
			if(j<=MaxDataNum) strTable+=j+".<img src=\"fck_mathml/data/"+mtype+"/"+j+".gif\" onclick=\"loadMathML('fck_mathml/data/"+mtype+"/"+j+".mml')\" />";
		}
strTable+="</div>";
     }
strTable+="</td>";
strTable+="                    </tr>";
strTable+="                  </table>";
//strTable+="                  <br> <br> <br> <br> <br> <br> <br> <br> ";
strTable+="</td>";
strTable+="              </tr>";
strTable+="            </table></td>";
strTable+="          <td><br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> </td>";
strTable+="        </tr>";
strTable+="      </table>";
strTable+="     </TD>";
strTable+=" </TR>";
strTable+="</TABLE>";

return strTable;
}
