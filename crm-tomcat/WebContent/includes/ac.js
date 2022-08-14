
var AC_isIE;var AC_isGecko;var AC_isSafari;var AC_isOther;function AC_DetectBrowser()
{var ua=navigator.userAgent.toLowerCase();AC_isGecko=(navigator.product=='Gecko');AC_isIE=!AC_isGecko&&(ua.indexOf("msie")!=-1);AC_isSafari=!AC_isGecko&&!AC_isIE&&(ua.indexOf("safari")!=-1);AC_isOther=!AC_isGecko&&!AC_isIE&&!AC_isSafari;}
AC_DetectBrowser();function AC_AddElementClass(element,className){if(element&&element.className.indexOf(className)==-1)
element.className+=" "+className;}
function AC_RemoveElementClass(element,className)
{if(element==null)return;var loc=element.className.indexOf(className);if(loc==-1)return;element.className=element.className.substring(0,loc)+
element.className.substring(loc+className.length+1);}
function AC_ElementHasClass(element,className)
{return(element&&(element.className.indexOf(className)!=-1));}
function AC_ScrollToChild(parent,indexOrElement)
{if(parent==null||indexOrElement==null)return;var cType=typeof indexOrElement;if(cType=="number"||cType=="string")
{indexOrElement=parent.childNodes[indexOrElement];}
if(indexOrElement==null)return;if(indexOrElement.offsetTop+indexOrElement.offsetHeight>parent.scrollTop+parent.clientHeight){parent.scrollTop=indexOrElement.offsetTop+indexOrElement.offsetHeight-parent.clientHeight;return;}
if(indexOrElement.offsetTop<parent.scrollTop)
{parent.scrollTop=indexOrElement.offsetTop;}}
function AC_CancelEvent(event)
{event.returnValue=false;if(event.preventDefault)
{event.preventDefault();}}
function AC_MakeElementTreeUnselectable(element)
{if(element.nodeType==1)
{element.unselectable="on";}
for(var i=0,n=element.childNodes.length;i<n;++i)
{AC_MakeElementTreeUnselectable(element.childNodes[i]);}}
function AC_GetTarget(event)
{if(event==null)
{event=window.event;}
if(event==null)
{return null;}
if(event.srcElement!=null)
{return event.srcElement;}
var retVal=event.target;while(retVal&&retVal.nodeType!=1)
{retVal=retVal.parentNode;}
return retVal;}
var AC_targetElements;var AC_insertEmailsOnly;function AC_OnLoad()
{window.AC_selectedEmails=[];if(typeof AC_nickTokens=="undefined")
{return;}
AC_Debug("Debug output is on.\n");if(typeof window.AC_listStr=="undefined"||window.AC_listStr==null||window.AC_listStr.length==0)
{window.AC_listDisplayStr="";}
else
{window.AC_listDisplayStr=" ("+window.AC_listStr+")";}
if(typeof window.AC_nickNameStr=="undefined"||window.AC_nickNameStr==null||window.AC_nickNameStr.length==0)
{window.AC_nickNameDisplayStr="";}
else
{window.AC_nickNameDisplayStr=" ("+AC_nickNameStr+")";}
if(window.AC_nickTokens!=null&&AC_nickTokens.length>0&&AC_nickTokens[AC_nickTokens.length-1]==null)
{AC_nickTokens.length-=1;}
if(window.AC_allCanons!=null&&AC_allCanons.length>0&&AC_allCanons[AC_allCanons.length-1]==null)
{AC_allCanons.length-=1;}
if(typeof AC_targetElements=="undefined"&&AC_targetElements==null)
{AC_targetElements=["tofield","ccfield","bccfield"];}
for(var index=0,numEls=AC_targetElements.length;index<numEls;++index)
{var field=window.document.getElementById(AC_targetElements[index]);if(field!=null)
{field.autocomplete="off";field.onkeydown=AC_OnKeyDown;field.onkeypress=AC_OnKeyPress;field.onkeyup=AC_OnKeyUp;field.onblur=AC_OnBlur;field.onselect=AC_OnSelect;field.oncut=AC_OnCut;field.onpaste=AC_OnPaste;field.onclick=AC_OnClick;}}
onResize=AC_OnResize();if(typeof AC_nickTokens=="undefined")
{window.AC_loaded=true;}}
function AC_TokenIndex(tokenStr,tokenArray,startIndex,endIndex){if(tokenArray.length==0)return 0;var lo=((startIndex!=null)?startIndex:0),hi=((endIndex!=null)?endIndex:tokenArray.length);var mid=hi>>1;var t=null;while(hi>lo){t=AC_DeLatinRot13(tokenArray[mid][0].toLowerCase());if(tokenStr>t)
lo=mid+1;else
hi=mid;mid=(hi+lo)>>1;}
if(tokenStr>t)
return hi;return lo;}
function AC_CompTokensByToken(token1,token2)
{if(token1[2]==null)
{token1[2]=AC_Rot13(token1[0]);}
if(token2[2]==null)
{token2[2]=AC_Rot13(token2[0]);}
return token1[2].localeCompare(token2[2]);}
function AC_CompTokensByTokenIE5(token1,token2){if(token1[2]==null)
{token1[2]=AC_Rot13(token1[0].toLowerCase());}
if(token2[2]==null)
{token2[2]=AC_Rot13(token2[0].toLowerCase());}
var tok1=token1[2];var tok2=token2[2];if(tok1<tok2)
{return-1;}
if(tok1>tok2)
{return 1;}
return 0;}
var AC_TAB=9;var AC_DELETE=46;var AC_BACKSPACE=8;var AC_LEFT_ARROW=37;var AC_RIGHT_ARROW=39;var AC_HOME=36;var AC_END=35;var AC_PAGE_UP=33;var AC_PAGE_DOWN=34;var AC_UP_ARROW=38;var AC_DOWN_ARROW=40;var AC_ESC=27;var AC_ENTER=13;var AC_SPACE=32;var AC_COMMA_KEY=188;var AC_SEMI_COLON_KEY=186;var AC_NBSP=160;var AC_COMMA=44;var AC_SEMI_COLON=59;var AC_SHIFT_KEY=16;var AC_CTRL_KEY=17;var AC_ALT_KEY=18;var AC_LEFT_MS_WINDOWS_KEY=91;var AC_RIGHT_MS_WINDOWS_KEY=92;var AC_MS_MENU_KEY=93;var AC_VK_PROCESSKEY=229;var AC_handledEnter=false;function AC_OnKeyDown(event)
{if(event==null)event=window.event;var editCtrl=AC_GetTarget(event);if(editCtrl==null)return;var keyCode=event.keyCode;AC_handledEnter=false;if(event.ctrlKey||event.ctrlLeft||event.altKey||event.altLeft||event.metaKey)
{switch(keyCode)
{case AC_HOME:case AC_END:case AC_PAGE_UP:case AC_PAGE_DOWN:case AC_UP_ARROW:case AC_DOWN_ARROW:case AC_RIGHT_ARROW:AC_RemoveDropDown();break;}
return;}
var op=null;switch(keyCode)
{case AC_LEFT_ARROW:case AC_ESC:if(AC_IsActive())
{AC_RemoveDropDown();AC_CancelEvent(event);}
break;case AC_HOME:op="selectFirst";break;case AC_END:op="selectLast";break;case AC_PAGE_UP:op="selectPrevPage";break;case AC_PAGE_DOWN:op="selectNextPage";break;case AC_UP_ARROW:op="selectPrev";break;case AC_DOWN_ARROW:if(AC_IsActive())
{AC_UpdateDropDown(editCtrl,"selectNext");AC_CancelEvent(event);}
else
{AC_NewDropDown(editCtrl);if(AC_IsActive())
{AC_CancelEvent(event);}}
break;case AC_TAB:if(!AC_isIE)break;case AC_RIGHT_ARROW:if(AC_IsActive())
{AC_InsertSuggestion(editCtrl);AC_CancelEvent(event);}
break;default:break;}
if(AC_IsActive()&&op!=null)
{AC_UpdateDropDown(editCtrl,op);AC_CancelEvent(event);}}
function AC_OnKeyPress(event)
{if(event==null)event=window.event;var editCtrl=AC_GetTarget(event);if(editCtrl==null)return;var keyCode=event.keyCode;if(keyCode==0)
{keyCode=event.charCode;}
if(keyCode==0)
{keyCode=event.which;}
AC_handledEnter=false;if(event.charCode!=null&&event.charCode==0)
{switch(keyCode)
{case AC_ESC:case AC_HOME:case AC_END:case AC_PAGE_UP:case AC_PAGE_DOWN:case AC_UP_ARROW:case AC_DOWN_ARROW:case AC_RIGHT_ARROW:if(!event.ctrlKey&&!event.altKey&&!event.metaKey&&AC_IsActive())
{AC_CancelEvent(event);}
return;}}
switch(keyCode)
{case AC_SEMI_COLON:case AC_COMMA:if(AC_IsActive()&&event.shiftKey==false)
{AC_InsertSuggestion(editCtrl);AC_CancelEvent(event);}
break;case AC_ENTER:{if(editCtrl.tagName=="TEXTAREA")
{AC_CancelEvent(event);}
AC_handledEnter=true;if(typeof AC_HandleEnter=="function")
{if(AC_HandleEnter()==true)
{AC_CancelEvent(event);return;}}}
case AC_TAB:if(AC_IsActive())
{AC_InsertSuggestion(editCtrl);AC_CancelEvent(event);}
break;default:break;}}
function AC_OnKeyUp(event)
{if(event==null)event=window.event;var editCtrl=AC_GetTarget(event);if(editCtrl==null)return;var keyCode=event.keyCode;switch(keyCode)
{case AC_DELETE:case AC_BACKSPACE:if(AC_IsActive()&&editCtrl.value.length==0)
{AC_RemoveDropDown();}
else if(editCtrl.value.length>0)
{AC_KickNewDropDown(editCtrl);}
else if(editCtrl.value.length==0&&typeof AC_useSelect!="undefined"&&AC_useSelect==true)
{AC_ShowAll(AC_targetElements[0]);}
break;case AC_ESC:case AC_HOME:case AC_END:case AC_PAGE_UP:case AC_PAGE_DOWN:case AC_UP_ARROW:case AC_DOWN_ARROW:case AC_LEFT_ARROW:case AC_RIGHT_ARROW:case AC_TAB:case AC_SEMI_COLON:case AC_SEMI_COLON_KEY:case AC_COMMA:case AC_COMMA_KEY:case AC_ENTER:case AC_VK_PROCESSKEY:break;default:var isSpecial=false;if(typeof event.ctrlLeft!="undefined")
{isSpecial=(event.ctrlLeft==true);}
if(isSpecial==false&&typeof event.ctrlKey!="undefined")
{isSpecial=(event.ctrlKey==true);}
if(isSpecial==false&&typeof event.metaKey!="undefined")
{isSpecial=(event.metaKey==true);}
if(isSpecial==false)
{AC_KickNewDropDown(editCtrl);}
break;}
if(!AC_handledEnter&&keyCode==AC_ENTER)
{AC_KickNewDropDown(editCtrl);}}
function AC_OnBlur(event)
{if(event==null)
{event=window.event;}
var editCtrl=AC_GetTarget(event);if(editCtrl==null)
{return;}
var listGetsFocus=editCtrl.getAttribute("listGetsFocus");if(listGetsFocus!=null)
{editCtrl.removeAttribute("listGetsFocus");return;}
AC_RemoveDropDown();}
function AC_OnClick(event)
{if(event==null)
{event=window.event;}
var editCtrl=AC_GetTarget(event);if(editCtrl==null)
{return;}
AC_RemoveDropDown();}
function AC_OnSelect(event)
{if(event==null)
{event=window.event;}
var editCtrl=AC_GetTarget(event);if(editCtrl==null)
{return;}
AC_RemoveDropDown();}
function AC_OnCut(event)
{if(event==null)
{event=window.event;}
var editCtrl=AC_GetTarget(event);if(editCtrl==null)
{return;}
AC_RemoveDropDown();}
function AC_OnPaste(event)
{if(event==null)
{event=window.event;}
var editCtrl=AC_GetTarget(event);if(editCtrl==null)
{return;}
AC_RemoveDropDown();}
function AC_OnResize(event)
{AC_PlaceDropDown();}
function AC_GetCursorIndex(editCtrl)
{if(editCtrl==null||(editCtrl.type!="text"&&editCtrl.type!="textarea"))
{return-1;}
if(editCtrl.value==null||editCtrl.value.length==0)
{return-1;}
var cursorIndex=-1;if(editCtrl.createTextRange)
{var selection=window.document.selection.createRange();var textRange=editCtrl.createTextRange();if(textRange==null||selection==null||((selection.text!="")&&textRange.inRange(selection)==false))
{return-1;}
if(selection.text=="")
{if(textRange.boundingLeft==selection.boundingLeft)
{cursorIndex=0;}
else
{if(editCtrl.tagName=="INPUT")
{var contents=textRange.text;var index=1;while(index<contents.length)
{textRange.findText(contents.substring(index));if(textRange.boundingLeft==selection.boundingLeft)
{break;}
index++;}}
else if(editCtrl.tagName=="TEXTAREA")
{var index=editCtrl.value.length+1;var theCaret=document.selection.createRange().duplicate();while(theCaret.parentElement()==editCtrl&&theCaret.move("character",1)==1)
{--index;}
if(index==editCtrl.value.length+1)
{index=-1;}}
cursorIndex=index;}}
else
{cursorIndex=textRange.text.indexOf(selection.text);}}
else if(window.getSelection&&window.document.createRange)
{if(editCtrl.selectionStart<0||editCtrl.selectionStart>editCtrl.length)
{return cursorIndex;}
if(editCtrl.selectionEnd<0||editCtrl.selectionEnd>editCtrl.length||editCtrl.selectionEnd<editCtrl.selectionStart)
{return cursorIndex;}
cursorIndex=editCtrl.selectionStart;}
else
{}
return cursorIndex;}
function AC_Menu_DeselectItem(menuElement,index)
{var current=menuElement.childNodes[index];if(current!=null)
{AC_RemoveElementClass(current,"ac_menuitem_selected");}}
function AC_Menu_SelectItem(menuElement,index,scrollToIt){var current=menuElement.getAttribute("selectedIndex");if(current!=-1&&current!=null)
AC_Menu_DeselectItem(menuElement,current);if(index>=0&&index<menuElement.childNodes.length)
{AC_AddElementClass(menuElement.childNodes[index],"ac_menuitem_selected");}
if(scrollToIt)
{AC_ScrollToChild(menuElement,index);}}
function AC_Menu_onClick(event)
{listCtrl=document.getElementById("ac_select");if(listCtrl==null)return;var editCtrl=window.document.getElementById(listCtrl.getAttribute("editCtrlID"));if(editCtrl==null)
{return;}
AC_InsertSuggestion(editCtrl);}
function AC_Menu_onMouseDown(event)
{listCtrl=document.getElementById("ac_select");if(listCtrl==null)return;var editCtrl=window.document.getElementById(listCtrl.getAttribute("editCtrlID"));if(editCtrl==null)
{return;}
editCtrl.setAttribute("listGetsFocus","1");}
function AC_MenuItem_onMouseDown(event){if(event==null)event=window.event;var target=this;var current=target.parentNode.getAttribute("selectedIndex");if(current!=-1&&current!=null)
{AC_RemoveElementClass(target.parentNode.childNodes[current],"ac_menuitem_selected");}
AC_AddElementClass(target,"ac_menuitem_selected");var children=target.parentNode.childNodes;for(var i=0,n=children.length;i<n;++i)
{if(children[i]==target)
{target.parentNode.setAttribute("selectedIndex",i);break;}}}
function AC_MenuItem_onMouseOver(event)
{if(!AC_ElementHasClass(this,"ac_menuitem_selected"))
{AC_AddElementClass(this,"ac_menuitem_over");}}
function AC_MenuItem_onMouseOut(event)
{AC_RemoveElementClass(this,"ac_menuitem_over");}
var AC_MaxResultItems=30;var AC_MaxVisibleItems=15;var AC_dropDownDiv=null;var AC_dropDownList=null;var AC_dropDownIFrame=null;function AC_IsActive()
{if(AC_dropDownDiv!=null)
{return(AC_dropDownDiv.style.visibility=="visible");}
return false;}
function AC_ShowAll(controlName)
{AC_FindSuggestions("\u0007");AC_NewDropDown(controlName,"compile");}
function AC_NewDropDown(editCtrl,state)
{if(state==null)
{state="new";}
if(typeof editCtrl=="string")
{editCtrl=document.getElementById(editCtrl);}
if(typeof AC_useSelect=="undefined"||AC_useSelect==null||AC_useSelect==false)
{if(editCtrl==null||editCtrl.value.length==0)
{return;}}
if(state=="new")
{var cursorIndex=AC_GetCursorIndex(editCtrl);if(cursorIndex==-1)
{return;}
var endDelim=-1;var substr=editCtrl.value.substring(cursorIndex);var commaDelim=substr.indexOf(',');var semiColonDelim=substr.indexOf(';');if(semiColonDelim<commaDelim&&semiColonDelim!=-1)
{endDelim=semiColonDelim;}
else if(commaDelim!=-1)
{endDelim=commaDelim;}
else if(semiColonDelim!=-1)
{endDelim=semiColonDelim;}
if(endDelim==-1)
{endDelim=editCtrl.value.length;}
else
{endDelim+=cursorIndex;}
if(cursorIndex<endDelim)
{var nonWhiteSpace=editCtrl.value.substring(cursorIndex,endDelim).search(/\S/);if(nonWhiteSpace!=-1)
{return;}}
var startDelim=-1;substr=editCtrl.value.substring(0,cursorIndex);commaDelim=substr.lastIndexOf(',');semiColonDelim=substr.lastIndexOf(';');if(semiColonDelim>commaDelim)
{startDelim=semiColonDelim;}
else
{startDelim=commaDelim;}
var currentInput=editCtrl.value.substring(startDelim+1,cursorIndex);var firstChar=currentInput.search(/\S/);if(firstChar!=-1)
{currentInput=currentInput.substring(firstChar);}
state="find";}
var t1=(new Date()).getTime();if(state=="find")
{AC_FindSuggestions(currentInput);state="compile";}
var t2=(new Date()).getTime();if(state=="compile")
{AC_CompileSuggestions();state="render";}
var t3=(new Date()).getTime();if(t3-t1>400)
{AC_KickNewDropDown(editCtrl,"render");return;}
if(state=="render")
{if(AC_currentSuggestions!=null&&AC_currentSuggestions.length>0)
{if(typeof AC_useSelect!="undefined"&&AC_useSelect==true)
{AC_SetInSelect();}
else
{AC_CreateDropDown(editCtrl,AC_currentSuggestions);}}}
var t4=(new Date()).getTime();if(AC_currentSuggestions==null||AC_currentSuggestions.length==0)
{if(typeof AC_useSelect!="undefined"&&AC_useSelect==true)
{AC_RemoveFromSelect();}
else
{AC_RemoveDropDown(editCtrl);}
return;}
if(typeof AC_useSelect=="undefined"||AC_useSelect==null||AC_useSelect==false)
{AC_PlaceDropDown(editCtrl,true);}}
function AC_CreateDropDown(editCtrl,suggestions)
{if(suggestions==null||suggestions.length==0)
{AC_RemoveDropDown(editCtrl);return;}
var selectedIndex=0;if(AC_dropDownDiv==null)
{AC_dropDownDiv=window.document.createElement("div");AC_dropDownDiv.style.position="absolute";AC_dropDownDiv.style.zIndex=1;AC_dropDownDiv.style.visibility="hidden";AC_dropDownDiv.style.fontSize="0.9em";}
editCtrl.parentNode.insertBefore(AC_dropDownDiv,editCtrl.nextSibling);if((typeof editor!="undefined"&&editor.useAX)&&AC_dropDownIFrame==null){AC_dropDownIFrame=window.document.createElement("iframe");AC_dropDownIFrame.style.position="absolute";AC_dropDownIFrame.style.visibility="hidden";AC_dropDownIFrame.frameborder="0";AC_dropDownIFrame.scrolling="no";AC_dropDownIFrame.src="javascript:false;"}
if(AC_dropDownIFrame!=null){editCtrl.parentNode.insertBefore(AC_dropDownIFrame,editCtrl.nextSibling);}
if(AC_dropDownList==null)
{AC_dropDownList=window.document.createElement("div");AC_dropDownList.className="ac_menu";AC_dropDownList.id="ac_select";AC_dropDownList.onclick=AC_Menu_onClick;AC_dropDownList.onmousedown=AC_Menu_onMouseDown;AC_dropDownList.unselectable="on";AC_dropDownList.style.width=editCtrl.offsetWidth;AC_dropDownDiv.appendChild(AC_dropDownList);}
AC_dropDownList.style.visibility="hidden";for(var i=suggestions.length,n=AC_dropDownList.childNodes.length;i<n;++i)
{AC_dropDownList.removeChild(AC_dropDownList.childNodes[suggestions.length]);}
for(var index=0;index<suggestions.length;index++)
{var option=AC_dropDownList.childNodes[index];if(option==null)
{option=window.document.createElement("div");option.className="ac_menuitem";option.onmousedown=AC_MenuItem_onMouseDown;option.onmouseover=AC_MenuItem_onMouseOver;option.onmouseout=AC_MenuItem_onMouseOut;AC_dropDownList.appendChild(option);}
else
{AC_RemoveElementClass(option,"ac_menuitem_selected");}
option.value=suggestions[index][0];option.text=suggestions[index][1];option.innerHTML="<nobr>"+suggestions[index][2]+"</nobr>";}
if(suggestions.length<=AC_MaxVisibleItems)
{AC_dropDownList.style.height="auto";if(AC_isGecko)
{AC_dropDownList.style.overflow="hidden";}
AC_dropDownList.style.overflowY="hidden";}
else
{var option=AC_dropDownList.childNodes[AC_MaxVisibleItems-1];AC_dropDownList.style.height=(option.offsetTop+option.offsetHeight+2)+"px";if(AC_isGecko)
{AC_dropDownList.style.overflow="-moz-scrollbars-vertical";}
AC_dropDownList.style.overflowY="scroll";}
AC_MakeElementTreeUnselectable(AC_dropDownList);AC_dropDownList.setAttribute("selectedIndex",selectedIndex);AC_Menu_SelectItem(AC_dropDownList,0);AC_dropDownList.style.visibility="visible";AC_dropDownList.setAttribute("selectedIndex",selectedIndex);AC_PlaceDropDown(editCtrl,true);}
function AC_UpdateDropDown(editCtrl,op)
{if(op==null||op==""||editCtrl==null||editCtrl.value.length==0||AC_dropDownList==null||AC_dropDownList.childNodes.length==0)
{AC_RemoveDropDown(editCtrl);return;}
var selectedIndex=AC_dropDownList.getAttribute("selectedIndex");if(selectedIndex==null)
{selectedIndex=0;}
var newSelection=selectedIndex;switch(op)
{case"selectPrev":newSelection--;break;case"selectNext":newSelection++;break;case"selectNextPage":newSelection+=AC_MaxVisibleItems;break;case"selectPrevPage":newSelection-=AC_MaxVisibleItems;break;case"selectFirst":newSelection=0;break;case"selectLast":newSelection=AC_dropDownList.childNodes.length-1;break;default:AC_RemoveDropDown(editCtrl);return;}
if(newSelection<0)
{newSelection=0;}
if(newSelection>(AC_dropDownList.childNodes.length-1))
{newSelection=(AC_dropDownList.childNodes.length-1);}
if(newSelection!=selectedIndex)
{AC_Menu_SelectItem(AC_dropDownList,newSelection,true);AC_dropDownList.setAttribute("selectedIndex",newSelection);}
AC_PlaceDropDown(editCtrl,true);}
function AC_PlaceDropDown(editCtrl,forceIt)
{if(editCtrl==null&&AC_dropDownList!=null)
{editCtrl=window.document.getElementById(AC_dropDownList.getAttribute("editCtrlID"));}
if(editCtrl==null||AC_dropDownList==null)
{return;}
if(AC_dropDownDiv.style.visibility!="visible"&&!forceIt)
{return;}
var left=window.document.body.clientLeft-AC_dropDownList.offsetLeft;var top=editCtrl.offsetHeight+window.document.body.clientTop-AC_dropDownList.offsetTop;var parent=editCtrl;do
{left+=parent.offsetLeft;top+=parent.offsetTop;parent=parent.offsetParent;}
while(parent!=null);AC_dropDownDiv.style.left=left;AC_dropDownDiv.style.top=top;AC_dropDownDiv.style.visibility="visible";if(AC_dropDownIFrame!=null){AC_dropDownIFrame.style.left=AC_dropDownDiv.style.left;AC_dropDownIFrame.style.top=AC_dropDownDiv.style.top;AC_dropDownIFrame.width=AC_dropDownDiv.offsetWidth;AC_dropDownIFrame.height=AC_dropDownDiv.offsetHeight;AC_dropDownIFrame.style.zIndex=editor.ComposeControl.style.zIndex+1;AC_dropDownDiv.style.zIndex=AC_dropDownIFrame.style.zIndex+1;AC_dropDownIFrame.style.visibility="visible";}
if(AC_dropDownList.offsetWidth<editCtrl.offsetWidth)
{AC_dropDownList.style.posWidth=editCtrl.offsetWidth;}
AC_dropDownList.setAttribute("editCtrlID",editCtrl.id);}
function AC_RemoveDropDown()
{if(AC_dropDownDiv!=null)
{AC_dropDownDiv.parentNode.removeChild(AC_dropDownDiv);AC_dropDownDiv.style.visibility="hidden";AC_dropDownDiv=null;}
if(AC_dropDownIFrame!=null)
{AC_dropDownIFrame.parentNode.removeChild(AC_dropDownIFrame);AC_dropDownIFrame.style.visibility="hidden";AC_dropDownIFrame=null;}
AC_dropDownList=null;}
function AC_InsertSuggestion(editCtrl)
{if(AC_IsActive()==false)
{return;}
var cursorIndex=AC_GetCursorIndex(editCtrl);if(cursorIndex==-1)
{return;}
var startDelim=-1;substr=editCtrl.value.substring(0,cursorIndex);commaDelim=substr.lastIndexOf(',');semiColonDelim=substr.lastIndexOf(';');if(semiColonDelim>commaDelim)
{startDelim=semiColonDelim;}
else
{startDelim=commaDelim;}
var stuffBeforeInsert="";if(startDelim!=-1)
{stuffBeforeInsert=editCtrl.value.substring(0,startDelim+1)+" ";}
var stuffAfterInsert=editCtrl.value.substring(cursorIndex);editCtrl.value=stuffBeforeInsert;var item=AC_dropDownList.childNodes[AC_dropDownList.getAttribute("selectedIndex")];editCtrl.value+=item.text.replace(/\u00A0/g,"");AC_selectedEmails[AC_selectedEmails.length]=AC_Rot13(AC_allCanons[item.value+1]);if(stuffAfterInsert.length==0)
{editCtrl.value+=', ';}
else
{editCtrl.value+=stuffAfterInsert;}
AC_RemoveDropDown();editCtrl.focus();}
var AC_updateTimer=null;var AC_updateEditCtrl;var AC_updateState;function AC_KickNewDropDown(editCtrl,state)
{if(AC_updateTimer!=null)
{clearTimeout(AC_updateTimer);}
AC_updateEditCtrl=editCtrl;AC_updateState=state;AC_updateTimer=setTimeout("AC_HandleNewDropDown()",10);}
function AC_HandleNewDropDown()
{var editCtrl=AC_updateEditCtrl,state=AC_updateState;AC_ClearUpdate();if(editCtrl!=null)
{AC_NewDropDown(editCtrl,state);}}
function AC_ClearUpdate()
{if(AC_updateTimer!=null)
{clearTimeout(AC_updateTimer);AC_updateTimer=null;}
AC_updateEditCtrl=null;AC_updateState=null;}
function AC_EmailExists(stringOfEmails)
{var retVal="";var listOfEmails=stringOfEmails.split(',');var emailExpr1=new RegExp(/([^@ ;<>]+@[^@ ;<>]+\.[^@ ;<>]+)/);var whiteStrip=new RegExp(/(\s*)/);for(var index=0;index<listOfEmails.length;index++)
{var email=listOfEmails[index];var ematch=email.match(emailExpr1);if(ematch==null||ematch[1]==null||ematch[1]=="")
{var ematch=email.match(whiteStrip);if(index<listOfEmails.length-1||ematch==null||ematch[1]!=email)
{retVal+="0,";}
continue;}
email=ematch[1];var found=false;for(var j=0;j<AC_selectedEmails.length;j++)
{if(email==AC_selectedEmails[j])
{found=true;break;}}
if(!found)
{retVal+="0,";}
else
{if(typeof window.document.Compose!="undefined"&&typeof window.document.Compose.AC_Done!="undefined"&&window.document.Compose.AC_Done!=null&&typeof window.document.Compose.AC_Done.value!="undefined"&&window.document.Compose.AC_Done.value!=1)
{window.document.Compose.AC_Done.value=1;}
retVal+="1,";}}
if(retVal=="")
{retVal="0,";}
return retVal;}
function AC_PostProcess()
{if(typeof window.document.Compose=="undefined"||typeof window.document.Compose.AC_Done=="undefined"||typeof window.document.Compose.AC_ToList=="undefined"||typeof window.document.Compose.AC_CcList=="undefined"||typeof window.document.Compose.AC_BccList=="undefined"||typeof AC_selectedEmails=="undefined")
{return;}
if(typeof AC_nickTokens=="undefined")
{return;}
window.document.Compose.AC_Done.value="";window.document.Compose.AC_ToList.value="";window.document.Compose.AC_CcList.value="";window.document.Compose.AC_BccList.value="";var toField=window.document.getElementById("tofield");if(toField!=null&&window.document.Compose.AC_ToList!=null&&typeof window.document.Compose.AC_ToList!="undefined"&&toField.value.length>0)
{var binaryList=AC_EmailExists(toField.value);if(binaryList!=null)
{window.document.Compose.AC_ToList.value=binaryList;}}
var ccField=window.document.getElementById("ccfield");if(ccField!=null&&window.document.Compose.AC_CcList!=null&&typeof window.document.Compose.AC_CcList!="undefined"&&ccField.value.length>0)
{var binaryList=AC_EmailExists(ccField.value);if(binaryList!=null)
{window.document.Compose.AC_CcList.value=binaryList;}}
var bccField=window.document.getElementById("bccfield");if(bccField!=null&&window.document.Compose.AC_BccList!=null&&typeof window.document.Compose.AC_BccList!="undefined"&&bccField.value.length>0)
{var binaryList=AC_EmailExists(bccField.value);if(binaryList!=null)
{window.document.Compose.AC_BccList.value=binaryList;}}}
AC_RotTable={};AC_LatinTable=[];function AC_BuildTables()
{var s="ABCDEFGHIJKLMNOPQRSTUVWXYZ",t="abcdefghijklmnopqrstuvwxyz";for(var i=0;i<26;++i)
{AC_RotTable[s.charAt(i)]=s.charAt((i+13)%26);AC_RotTable[t.charAt(i)]=t.charAt((i+13)%26);}
AC_LatinTable[192]=AC_LatinTable[193]=AC_LatinTable[194]=AC_LatinTable[195]=AC_LatinTable[196]=AC_LatinTable[197]="a";AC_LatinTable[199]="c";AC_LatinTable[200]=AC_LatinTable[201]=AC_LatinTable[202]=AC_LatinTable[203]="e";AC_LatinTable[204]=AC_LatinTable[205]=AC_LatinTable[206]=AC_LatinTable[207]="i";AC_LatinTable[209]="n";AC_LatinTable[210]=AC_LatinTable[211]=AC_LatinTable[212]=AC_LatinTable[213]=AC_LatinTable[214]=AC_LatinTable[216]="o";AC_LatinTable[217]=AC_LatinTable[218]=AC_LatinTable[219]=AC_LatinTable[220]="u";AC_LatinTable[221]="y";AC_LatinTable[224]=AC_LatinTable[225]=AC_LatinTable[226]=AC_LatinTable[227]=AC_LatinTable[228]=AC_LatinTable[229]="a";AC_LatinTable[231]="c";AC_LatinTable[232]=AC_LatinTable[233]=AC_LatinTable[234]=AC_LatinTable[235]="e";AC_LatinTable[236]=AC_LatinTable[237]=AC_LatinTable[238]=AC_LatinTable[239]="i";AC_LatinTable[241]="n";AC_LatinTable[242]=AC_LatinTable[243]=AC_LatinTable[244]=AC_LatinTable[245]=AC_LatinTable[246]=AC_LatinTable[248]="o";AC_LatinTable[249]=AC_LatinTable[250]=AC_LatinTable[251]=AC_LatinTable[252]="u";AC_LatinTable[253]=AC_LatinTable[255]="y";}
AC_BuildTables();function AC_Rot13(inputText){if(inputText==null)return"";var resultText=[];var c,s;for(var i=0,n=inputText.length;i<n;++i)
{resultText[i]=(s=AC_RotTable[(c=inputText.charAt(i))])?s:c;}
return resultText.join("");}
function AC_DeLatin(inputText){if(inputText==null)return"";var resultText=[];for(var i=0,n=inputText.length;i<n;++i)
{var c=inputText.charCodeAt(i);var sub=AC_LatinTable[c];if(sub==null)sub=inputText.charAt(i);resultText[resultText.length]=sub;}
return resultText.join("");}
function AC_DeLatinRot13(inputText){if(inputText==null)return"";var resultText=[];for(var i=0,n=inputText.length;i<n;++i)
{var c=inputText.charCodeAt(i);var sub=AC_LatinTable[c];if(sub==null){c=inputText.charAt(i);sub=AC_RotTable[c];if(sub==null)sub=c;}
resultText[resultText.length]=sub;}
return resultText.join("");}
function AC_NextToken(token){while(token.length>0){var c=token.charCodeAt(token.length-1);if(c<65535)
return token.substring(0,token.length-1)+String.fromCharCode(c+1);token=token.substring(0,token.length-1);}
return null;}
function AC_DeLatinString(input){input=input.replace(/[\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5]/gi,"a");input=input.replace(/[\u00E8\u00E9\u00EA\u00EB]/gi,"e");input=input.replace(/[\u00E7]/gi,"c");input=input.replace(/[\u00F1]/gi,"n");input=input.replace(/[\u00F2\u00F3\u00F4\u00F5\u00F6\u00F8]/gi,"o");input=input.replace(/[\u00F9\u00FA\u00FB\u00FC]/gi,"u");input=input.replace(/[\u00FD\u00FF]/gi,"y");input=input.replace(/[\u00C6]/gi,"\u00E6");return input;}
function AC_InnerMatchString(input)
{input=input.replace(/([\\|\[|\]|\(|\)|\{|\}|\+|\.|\^|\$|\?])/g,"\\\$1");input=input.replace(/[a\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5]/gi,"[a\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5]");input=input.replace(/[e\u00E8\u00E9\u00EA\u00EB]/gi,"[e\u00E8\u00E9\u00EA\u00EB]");input=input.replace(/[c\u00E7]/gi,"[c\u00E7]");input=input.replace(/[n\u00F1]/gi,"[n\u00F1]");input=input.replace(/[o\u00F2\u00F3\u00F4\u00F5\u00F6\u00F8]/gi,"[o\u00F2\u00F3\u00F4\u00F5\u00F6\u00F8]");input=input.replace(/[u\u00F9\u00FA\u00FB\u00FC]/gi,"[u\u00F9\u00FA\u00FB\u00FC]");input=input.replace(/[y\u00FD\u00FF]/gi,"[y\u00FD\u00FF]");return input;}
function AC_OuterSearchExpr(input)
{var isDoubleByte=false;for(var index=0;index<input.length;++index)
{if(input.charCodeAt(index)>127)
{isDoubleByte=true;break;}}
if(isDoubleByte==true)
{var newInput="";for(var index=0;index<input.length;++index)
{if(input.charAt(index)=="\\")
{newInput+="\\";index++;if(index>=input.length)
{break;}}
newInput+=input.charAt(index)+"\u00A0{0,1}";}
input=newInput;}
input=input.replace(/ /," (\\S+ +<{0,1})*");return new RegExp("(^|[ <\"]|\u00A0)"+input,"gi");}
function AC_OuterMenuExpr(input)
{var isDoubleByte=false;for(var index=0;index<input.length;++index)
{if(input.charCodeAt(index)>127)
{isDoubleByte=true;break;}}
if(isDoubleByte==true)
{var newInput="";for(var index=0;index<input.length;++index)
{newInput+=input.charAt(index)+"\u00A0{0,1}";}
input=newInput;}
input=input.replace(/</,"&lt;");if(input.search(/ /)==-1)
{input+=")(.*)($";}
else
{input=input.replace(/ /," +)((?:\\S+ +(?:<|&lt;){0,1})*?)(");}
return new RegExp("(^|[ <\"]|&lt;|\u00A0)("+input+")","gi");}
var AC_currentInput=null;var AC_currentResults=null;var AC_currentSuggestions=null;var AC_substringMatches={};var AC_substringMatchesLinear={};function AC_CompileSuggestions()
{var inputText=AC_currentInput;var searchResults=AC_currentResults;if(searchResults==null)
{searchResults=AC_substringMatchesLinear[inputText];}
if(searchResults==null)
{AC_currentSuggestions=null;return null;}
var suggestions=new Array();try
{var matchExpr=AC_OuterMenuExpr(AC_InnerMatchString(inputText));}
catch(e)
{var matchExpr=null;}
var total=0;var seenAddresses=[];for(var j=0;j<2;++j)
{if(typeof AC_ignoreMaxResultItems=="undefined"||AC_ignoreMaxResultItems==null||AC_ignoreMaxResultItems==false)
{if(total>=AC_MaxResultItems)
{break;}}
var isNick=(j==0);var matches=searchResults[j];if(matches==null)continue;if(matches.start==null)
{var fromList=matches;indexes={start:0,end:fromList.length}}
if(fromList==null||fromList.length==0)
{continue;}
if(searchResults[j+2]!=null)
{fromList=searchResults[j+2];}
else
{var sortEnd=indexes.start;if(typeof AC_ignoreMaxResultItems=="undefined"||AC_ignoreMaxResultItems==null||AC_ignoreMaxResultItems==false)
{sortEnd+=AC_MaxResultItems;}
else
{sortEnd+=indexes.end;}
if(indexes.end<sortEnd)
{sortEnd=indexes.end;}
fromList=fromList.slice(indexes.start,sortEnd);fromList.sort((typeof inputText.localeCompare=="function")?AC_CompTokensByToken:AC_CompTokensByTokenIE5);searchResults[j+2]=fromList;}
indexes={start:0,end:fromList.length};for(var index=indexes.start;index<indexes.end;index++)
{if(typeof AC_ignoreMaxResultItems=="undefined"||AC_ignoreMaxResultItems==null||AC_ignoreMaxResultItems==false)
{if(total>=AC_MaxResultItems)
{break;}}
var cIndex=fromList[index][1];if(cIndex==null)continue;if(typeof seenAddresses[cIndex]!="undefined")
{continue;}
seenAddresses[cIndex]=null;if(fromList[index][3]==null)
{fromList[index][3]=AC_Rot13(AC_allCanons[cIndex]).replace(/"/g,"\\\"");}
var name=fromList[index][3];if(name.search(/[,";@]/)!=-1)
{name='"'+name+'"';}
name=name.replace(/</g,"&lt;");name=name.replace(/>/g,"&gt;");if(fromList[index][4]==null)
{fromList[index][4]=AC_Rot13(AC_allCanons[cIndex+1]);}
var addr=fromList[index][4];var isList=(addr.length==0);var suggestionText;var menuText;if(typeof AC_insertEmailsOnly!="undefined"&&AC_insertEmailsOnly==true)
{if(isList)
{suggestionText=name;}
else
{suggestionText=addr;}
addr=addr.replace(/</g,"&lt;");addr=addr.replace(/>/g,"&gt;");menuText=name+(isList?"":(" &lt;"+addr+"&gt;"));}
else
{suggestionText=name+(isList?"":(" <"+addr+">"));addr=addr.replace(/</g,"&lt;");addr=addr.replace(/>/g,"&gt;");menuText=name+(isList?"":(" &lt;"+addr+"&gt;"));}
if(isNick)
{var nick=AC_Rot13(fromList[index][0]);if(matchExpr)
{nick=nick.replace(matchExpr,"$1<b>$2</b>$3$4");}
menuText=["<i>",nick,AC_nickNameDisplayStr,"</i> - ",menuText].join("");}
else if(isList)
{if(matchExpr)
{name=name.replace(matchExpr,"$1<b>$2</b>$3$4");}
menuText=["<i>",name,AC_listDisplayStr,"</i>"].join("");}
else
{if(matchExpr)
{menuText=menuText.replace(matchExpr,"$1<b>$2</b>$3<b>$4</b>");menuText=menuText.replace(/\u00A0/g,"");}}
suggestions[suggestions.length]=[cIndex,suggestionText,menuText];total++;}}
AC_currentSuggestions=suggestions;if(suggestions.length==0)
{return null;}
return suggestions;}
function AC_FindSuggestions(inputText)
{if(inputText!=null)
{inputText=AC_DeLatinString(inputText.toLowerCase());}
if(AC_currentInput!=inputText)
{AC_currentInput=inputText;AC_currentResults=null;AC_currentSuggestions=null;}
if(typeof AC_nickTokens=="undefined")
{return(AC_currentSuggestions=null);}
if(inputText==null||inputText.length<=0||(AC_nickTokens==null))
{return(AC_currentSuggestions=null);}
{AC_currentSuggestions=AC_FindLinear(inputText);}}
function AC_CleanRecipientValue(fieldID){var field=document.getElementById(fieldID);if(field==null||field.value==null||field.value.length==0)
return;field.value=field.value.replace(/(\n\r|\r\n|\s)/g," ").replace(/;/g,",").replace(/,(,|\s)*,/g,",");}
function AC_FindLinear(inputText)
{if(AC_substringMatchesLinear[inputText]==null)
{AC_substringMatchesLinear[inputText]=[null,null];var matches=AC_substringMatchesLinear[inputText];if(inputText.length>1)
{var prefix=inputText.substring(0,inputText.length-1);var prefixMatches=AC_substringMatchesLinear[prefix];if(prefixMatches==null)
{AC_FindLinear(prefix);}
prefixMatches=AC_substringMatchesLinear[prefix];if(prefixMatches[0]!=null)
{if(prefixMatches[0].length==0)
{matches[0]=prefixMatches[0];}
else
{matches[0]=AC_FindLinearTokens(inputText,prefixMatches[0]);}}
if(matches[0]!=null&&matches[0].length>=AC_MaxResultItems)
{matches[1]=-1;}
else if(prefixMatches[1]!=null&&prefixMatches[1]!=-1)
{if(prefixMatches[1].length==0)
{matches[1]=prefixMatches[1];}
else
{matches[1]=AC_FindLinearTokens(inputText,prefixMatches[1]);}}}
if(matches[0]==null)
{matches[0]=AC_FindLinearTokens(inputText,AC_nickTokens);}
if(matches[1]==null)
{if(matches[0]!=null&&matches[0].length>=AC_MaxResultItems)
{matches[1]=-1;}
else
{matches[1]=AC_FindLinearCanons(inputText,AC_allCanons);}}}
AC_currentResults=AC_substringMatchesLinear[inputText];}
function AC_FindLinearTokens(inputText,fromList)
{var results=[],token;var showAll=false;if(inputText=="\u0007")
{showAll=true;}
var inputExpr=AC_OuterSearchExpr(AC_Rot13(AC_InnerMatchString(inputText)));if(typeof AC_ignoreNickNames!="undefined"&&AC_ignoreNickNames==true)
{return;}
for(var i=0,n=fromList.length;i<n;i++)
{token=fromList[i];if(showAll==true||token[0].search(inputExpr)!=-1)
{results[results.length]=token;}}
return results;}
function AC_FindLinearCanons(inputText,fromList)
{var results=[];var name,email;var showAll=false;if(inputText=="\u0007")
{showAll=true;}
var inputExpr=AC_OuterSearchExpr(AC_Rot13(AC_InnerMatchString(inputText)));for(var i=0,n=fromList.length;i<n;i+=2)
{var name=fromList[i];var email=fromList[i+1];if(showAll==true||name.search(inputExpr)!=-1||email.search(inputExpr)!=-1)
{if(email.length==0&&typeof AC_ignoreLists!="undefined"&&AC_ignoreLists==true)
{continue;}
if(email.length>0)
{name=[name," <",email,">"].join("");}
results[results.length]=[name,i];}}
return results;}
function AC_Debug(){var text=AC_Debug.arguments[0];for(var i=1,n=AC_Debug.arguments.length;i<n;++i){text+=" "+AC_Debug.arguments[i];}}
function AC_SetInSelect()
{var targetElement=document.getElementById(AC_targetSelect);if(AC_currentSuggestions==null||AC_currentSuggestions.length==0)
{return;}
var selectedIndex=0;targetElement.innerHTML="";for(var index=0;index<AC_currentSuggestions.length;index++)
{optionText=AC_currentSuggestions[index][1].replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");optionElement=document.createElement("option");optionElement.setAttribute("value",optionText);optionElement.setAttribute("title",AC_currentSuggestions[index][1]);optionElement.innerHTML=optionText;targetElement.appendChild(optionElement);}}
function AC_RemoveFromSelect()
{var targetElement=document.getElementById(AC_targetSelect);var children=targetElement.childNodes;for(var index=0,numChildren=children.length;index<numChildren;++index)
{targetElement.removeChild(children[0]);}}