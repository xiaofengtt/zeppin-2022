var bIsIE5 = navigator.userAgent.indexOf("IE 5")  > -1;
var bIsIE55= navigator.userAgent.indexOf("IE 5.5")  > -1;
var edit;
var RangeType;

function swapModes(flag)
{
alert("swapModes"+flag);
if(flag==1)
{
textEdit.document.designMode="On";
}
else if(flag==2)
{
textEdit.document.body.innerHTML="hello";
}
else textEdit.document.designMode="Off";
}


function setFocus() {
	textEdit.focus();
}
function selectRange(){
	edit = textEdit.document.selection.createRange();
	RangeType = textEdit.document.selection.type;
}
function execCommand(command) {
	
	if (bIsIE5) selectRange();
		
        if ((command=="Redo")||(command=="Undo"))
        {
        
	        if (arguments[1]==null)
			document.execCommand(command,true);
		else
			document.execCommand(command,true,arguments[1]);
        
        }
        else 
        {		
        
		if (arguments[1]==null)
			edit.execCommand(command,true);
		else
			edit.execCommand(command,true,arguments[1]);
	}
	
	textEdit.focus();
	
	if (RangeType != "Control") edit.select();	
}

function specialtype(Mark){
	var strHTML;
	if (bIsIE5) selectRange();	
	if (RangeType == "Text"){
		strHTML = "<" + Mark + ">" + edit.text + "</" + Mark + ">"; 
		edit.pasteHTML(strHTML);
		textEdit.focus();
		edit.select();
	}		
}


function foretype(colorvalue)
{
	var strHTML;
	if (bIsIE5) selectRange();	
	if (RangeType == "Text"){
		strHTML = "<span style='background-color:" + colorvalue + "'>" + edit.text + "</span>"; 
		edit.pasteHTML(strHTML);
		textEdit.focus();
		edit.select();
	}		
}


function insertText(Mark){
	var strHTML;
	if (bIsIE5) selectRange();	
	//if (RangeType == "Text")
	{
		strHTML = Mark; 
		//textEdit.document.select();
		//edit = textEdit.document.selection.createRange();
		if (textEdit.document.selection.type=="Control")
		{ 
			var myobj;
			alert("光标停留位置为一个对象，将被取代。");
			//+textEdit.document.selection.innerHTML);
			myobj = textEdit.document.selection.createRange();
			//edit(1).outerHTML
			myobj(0).outerHTML=strHTML;
			//edit.pasteHTML(strHTML);
			//edit.insertAdjacentHTML("afterEnd",strHTML);
			//eb._editor.frames.textEdit.document.selection.
		};
		else{
		edit.pasteHTML(strHTML);
		textEdit.focus();
		edit.select();
		}
		// edit: textEdit.document.selection.createRange()
	}		
}


function initEditor() {
	textEdit.document.designMode="On";
//	textEdit.document.open();
//	textEdit.document.write("");
//	textEdit.document.close();
}

window.onload = initEditor
