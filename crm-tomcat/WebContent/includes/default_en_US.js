//Title  : Useful Javascript functions
//Desp   :
//Author : 周斌
//Date   : 2003-4-3

/**
 * 全文字符串替换
 */
String.prototype.replaceAll = function(toReplace,replacement){
  return this.replace(new RegExp(toReplace,"gm"),replacement);
}

function dwrErrorHandler(errorString, exception)
{
	sl_alert(errorString);
}
function isEmpty(str)
{
	return ((str == null) || (str.length == 0));
}

function findCount(element,value)
{
	icount=0;
	for(i = 0; i <element.options.length; i++)
	{
		if(element.options[i].value==value)
		{
			icount=1;
			break;
		}
	}
	return icount;
}
function findValue(element,value)
{
	ivalue="";
	for(i = 0; i <element.options.length; i++)
	{
		if(element.options[i].text==value)
		{
			ivalue=element.options[i].value;
			break;
		}
	}
	return ivalue;
}
function isNull(str)
{
	var flag = false;
	for (i = 0; i < str.length; i++)
	{
		if (str.charAt(i) != ' ')
			flag = true;
	}
	return !flag;
}

function haveChinese(str)
{
	for (i = 0; i < str.length; i++)
		if (str.charCodeAt(i) > 127)
			return true;
	return false;
}

function isChinese(str)
{
	var flag = false;
	for (i = 0; i < str.length; i++)
	{
		flag = str.charCodeAt(i)>127
		if (!flag)	return flag;
	}
	return flag;
}

function ansiLength(str)
{
	var len = 0;
	for (i = 0; i < str.length; i++)
	{
		if (str.charCodeAt(i) > 127)
			len = len + 2;
		else
			len++;
	}
	return len;
	//return str.length;
}

function sl_alert(errinfo, url)
{
	alert("Note：　　　　\n\n" + errinfo + "\n\n");
	if (url != null)
		location.replace(url);
}

function sl_confirm(confirm_info, vbVersion)
{
	if (vbVersion == null)
		return confirm("Confirm：\n\n Are you sure you want to " + confirm_info + "？");
	else
		return sl_vb_comfirm("Confirm：\n\n Are you sure you want to " + confirm_info + "？");
}
//////////////
function sl_check(field, name, maxlength, minlength)
{
	var str;
	if(field == null) return true;
	str =field.value;
	var CheckData = /<|>|'|;|&|#|"|'/;
	str=str.replace(/^\s+|\s+$/g, "");
	if(minlength != 0 && str == "")
	{
		sl_alert(name + "Empty is wrong，please write " + name + "！");
		field.focus();
		field.select();
		return false;
	}
	if(!no_deviant_char(field,name)) return false;

	if(minlength != 0 && ansiLength(field.value) < minlength)
	{
		if(minlength == maxlength)
			sl_alert(name + "'s length is " + minlength + "size，please write again！");
		else
			sl_alert(name + "'s length at leaset is " + minlength + "size，please write again！");
		field.focus();
		return false;
	}
	if(maxlength != 0 && ansiLength(field.value) > maxlength)
	{
		sl_alert(name + "'s length is not more than " + maxlength + " size，please write again！");
		field.focus();
		return false;
	}
	if (CheckData.test(str)) {
		alert(name+"cann't contain special characters");
		field.focus();
		return false;
	}
	return true;
}

function sl_checkEmail(field, name, maxlength, minlength){
	var str;
	if(field == null) return true;
	str =field.value;
	var CheckData = /^(?!(\.|-|_))(?![a-zA-Z0-9\.\-_]*(\.|-|_)@)[a-zA-Z0-9\.\-_]+@(?!.{64,}\.)(?![\-_])(?![a-zA-Z0-9\-_]*[\-_]\.)[a-zA-Z0-9\-_]+(\.\w+)+$/;
	str=str.replace(/^\s+|\s+$/g, "");
	if(minlength != 0 && str == "")
	{
		sl_alert(name + "Empty is wrong，please write " + name + "！");
		field.focus();
		field.select();
		return false;
	}
	if(!no_deviant_char(field,name)) return false;

	if(minlength != 0 && ansiLength(field.value) < minlength)
	{
		if(minlength == maxlength)
			sl_alert(name + "'s length is" + minlength + "size，please write again！");
		else
			sl_alert(name + "'s length at leaset is" + minlength + "size，please write again！");
		field.focus();
		return false;
	}
	if(maxlength != 0 && ansiLength(field.value) > maxlength)
	{
		sl_alert(name + "'s length is not more than " + maxlength + "size，please write again！");
		field.focus();
		return false;
	}

	var tempArray = str.split(";");

	for(var i = 0; i<tempArray.length;i++){
			var temp = tempArray[i];
			if (temp.length>0&&!CheckData.test(temp)) {
				alert(name+"Email address is not correct！");
				field.focus();
				return false;
			}
	}
	return true;
}



function sl_checkNum(field, name, maxlength, minlength)
{
	var i, str
	if (!sl_check(field, name, maxlength, minlength))
		return false;
	str = field.value;
	for (i = 0; i < str.length; i++)
	{
		if (str.charAt(i) != ',')
		{
			if (str.charCodeAt(i) < 48 || str.charCodeAt(i) > 57)
			{
				sl_alert(name + "is an integer number，please write again！");
				field.focus();
				field.select();
				return false;
			}
		}
	}
	return true;
}

/**
aADD BY TSG 20080605
首位不为0
*/
function sl_checkNum1(field, name, maxlength, minlength)
{
	var i, str
	if (!sl_check(field, name, maxlength, minlength))
		return false;
	str = field.value;
	for (i = 0; i < str.length; i++)
	{
		if (str.charAt(i) != ',')
		{
		   if(i==0 && str.charCodeAt(i)==48){
		       sl_alert(name + "First cannot be 0，please write again！");
		       field.focus();
			   field.select();
			   return false;
		   }else{
				if (str.charCodeAt(i) < 48 || str.charCodeAt(i) > 57)
				{
					sl_alert(name + "is an integer number，please write again！");
					field.focus();
					field.select();
					return false;
				}
		 }
		}
	}
	return true;
}

function sl_checkDecimal(field, name, intlen, pointlen, minlength)
{
	if(field == null) return true;
	var pos1, pos2, ilen, plen;
	if(!sl_checkFloat(field, name, 1000, minlength))
		return false;
	if ((minlength == 0) && (field.value.length == 0))
		return true;
	field.value=sl_parseFloat(field.value);
	pos1 = field.value.indexOf(".");
	pos2 = field.value.lastIndexOf(".");
	if ((pos1 >= 0) && ((pos2 > pos1) || (field.value.length < 3)))
	{
		sl_alert("Digital format is invalid，Please enter a valid"+ name +"！");
		field.focus();
		field.select();
		return false;
	}
	if ((pos1 == 0) || (pos1 == field.value.length - 1))
	{
		sl_alert("Digital format is invalid，Please enter a valid"+ name +"！");
		field.focus();
		field.select();
		return false;
	}
	if (pos1 == -1)
	{
		ilen = field.value.length;
		plen = 0;
	}
	else
	{
		ilen = pos1;
		plen = field.value.length - pos1 - 1;
	}
	if (ilen > intlen)
	{
		sl_alert("Digital format is invalid，integer digit cannot exceed" + intlen + "size，Please enter a valid"+ name +"！");
		field.focus();
		field.select();
		return false;
	}
	if (plen > pointlen)
	{
		sl_alert("Digital format is invalid，decimal digit cannot exceed" + pointlen + "size，Please enter a valid"+ name +"！");
		field.focus();
		field.select();
		return false;
	}
	return true;
}

function sl_checkChoice(field, name)
{
	if (field.value == null || field.value == "" || field.value == "0")
	{
		sl_alert("Please choose " + name + "！");
		field.focus();
		return false;
	}
	return true;
}

function sl_checkChoiceBit(field, name)
{
	if (field.value == null || field.value == "" || field.value == "-1")
	{
		sl_alert("Please choose" + name + "！");
		field.focus();
		return false;
	}
	return true;
}

function sl_checkFloat(field, name, maxlength, minlength)
{
	if(field == null) return true;
	DropSpace(field);
	var i, j, str;
	if (!sl_check(field, name, maxlength, minlength))
		return false;
	str = field.value;
	j = 0;
	if (str != "" && str.charAt(0) == '-')
		j = 1;
	for (i = j; i < str.length; i++)
	{
		if (str.charAt(i) != '.' && str.charAt(i) != ',')
		{
			if (str.charCodeAt(i) < 48 || str.charCodeAt(i) > 57)
			{
				sl_alert(name + "is an integer number，please write again！");
				field.focus();
				field.select();
				return false;
			}
		}
	}
	return true;
}

function sl_parseFloat(value){
	var s = "";
	for (var i = 0; i < value.length; i++){
		if (value.charAt(i) != ',')
			s = s + value.charAt(i);
	}
	return (s=="")?0:parseFloat(s);
}

function mOvr(src,clrOver) { if (!src.contains(event.fromElement)) { src.style.cursor = 'hand'; src.bgColor = clrOver; }}function mOut(src,clrIn) { if (!src.contains(event.toElement)) { src.style.cursor = 'default'; src.bgColor = clrIn; }}
function mClk(src) { if(event.srcElement.tagName=='TD'){src.children.tags('A')[0].click();} }

function popHelpWindow(HelpID, WinHeight)
{
	left = screen.width - 320
	if (WinHeight!=null)
		height = WinHeight
	else
		height = screen.height - 20
	return window.open("/includes/help.htm?help_id=" + HelpID,"","resizable=1,scrollbars=yes,status=no,toolbar=no,location=no,menu=no,width=316,height=" + height + ",left=" + left + ",top=0")
}

function popWindow(url)
{
	return window.open(url,"","resizable=0,scrollbars=yes,status=no,toolbar=no,location=no,menu=no,width=600,height=500,left=0,top=0")
}

function isValidDate(sDate)
{
	if(sDate.indexOf("0229")>0)
		return false;
	if(sDate.indexOf("0230")>0)
		return false;
	if(sDate.indexOf("0231")>0)
		return false;
	if(sDate.indexOf("0431")>0)
		return false;
	if(sDate.indexOf("0631")>0)
		return false;
	if(sDate.indexOf("0931")>0)
		return false;
	if(sDate.indexOf("1131")>0)
		return false;
	return true
}

function isValidDate(ssDate,prompt)
{
	var sDate;
	sDate=ssDate.value;

	if (sDate == null)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.length <= 0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("0229")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("0230")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("0231")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("0431")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("0631")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("0931")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	if (sDate.indexOf("1131")>0)
	{
		sl_alert(prompt);
		ssDate.focus();
		ssDate.select();
		return false;
	}
	return true;
}

function isCharsInBag(s, bag)
{
	var i;
	// Search through string's characters one by one.
	// If character is in bag, append to returnString.
	for (i = 0; i < s.length; i++)
	{
		// Check that current character isn't whitespace.
		var c = s.charAt(i);
		if (bag.indexOf(c) == -1) return false;
	}
	return true;
}

function isEmail(s, name)
{
	var len;
	if(!sl_check(s,name,50,1))
		return false;

	pos1 = s.value.indexOf("@");
	pos2 = s.value.indexOf(".");
	pos3 = s.value.lastIndexOf("@");
	pos4 = s.value.lastIndexOf(".");
	//check '@' and '.' is not first or last character
	if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len))
	{
		sl_alert("Please enter a valid "+ name +"！");
		s.focus();
		return false;
	}
	else
	{
		//check @. or .@
		if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)
		  || ( pos1 != pos3 )  //find two @
		  || ( pos4 < pos3 )  //. should behind the '@'
		  || (pos4 == len - 1) ) //. should not in the last letter
		{
			sl_alert("Please enter a valid "+ name +"！");
			s.focus();
			return false;
		}
	}
	if (!isCharsInBag( s.value, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
	{
		sl_alert(name + "only contain characters ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@\n" + "please write again"+ name);
		s.focus();
		return false;
	}
	return true;
}

function no_deviant_char(s, alertwords)
{
	if (s.value.indexOf("'", 0) >= 0)
	{
		sl_alert(alertwords + " cannot contain single quotes, and other illegal characters！");
		s.focus();
		return false;
	}
	return true;
}

function openWin(url, name, w, h)
{
	var left=0;
	var top=0;
	left=(screen.width-w)/2;
	top=(screen.height-h)/2;
	return window.open(url,name,"width=" + w + ",height=" + h + ",left="+left+",top="+top+",toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no")
}

function chachEsc(nKeyCode)
{
	if (27 == nKeyCode)
	{
		//window.returnValue = null;
		window.close();
	}
}

function showFilter()
{
	if (showModalDialog('/public/filter.htm','','dialogWidth:310px;dialogHeight:136px;status:0;help:0;fullscreen=3') != null)
		location.reload();
}

function invertVisible(element)
{
	if(element.style.display == "")
		element.style.display = "none";
	else
		element.style.display = "";
}

function checkedCount(element)
{
	var iCount = 0;
	var i;

	if(element == null)
		return 0;

	if(element.length == null)
	{
		if(element.checked)
			return 1;
		else
			return 0;
	}

	for(i = 0; i < element.length; i++)
	{
		if(element[i].checked)
		{
			iCount++;
		}
	}
	return iCount;
}

function checkedValue(element)
{
	var i, ret = "";

	if(element.length == null)
	{
		if(element.checked)
			return element.value;
		else
			return null;
	}

	for(i = 0; i < element.length; i++)
		if(element[i].checked)
		{
			if (ret == "")
				ret = ret + element[i].value;
			else
				ret = ret + "," + element[i].value;

		}

	return ret;
}

// used at css tr0 and tr1, do not modify function name
function a()
//{this.style.background='#FFCC00'}
{this.style.background='#ADAED6'}

function b()
{this.style.background='#F7F7F7'}

function c()
{this.style.background='#FFFFFF'}
//新版高亮显示 start
function initHighlight()
{
	document.onmouseover = highlightOn;
	document.onmouseout  = highlightOff;
}

function highlightOn()
{
	var o=event.srcElement;
	if(o==null)
		return;
	if(o.tagName.toUpperCase()=="TD")
	{
		o = o.parentElement;
	}
	else
		return;
	if(o.className=="tr0"||o.className=="tr1")
		o.style.background = '#ADAED6';
	else
	{
		o = o.parentElement;
		if(o!=null&&o.tagName.toUpperCase()=="TBODY")	//表格套表格
		{
			o = o.parentElement.parentElement.parentElement;
			if(o.className=="tr0"||o.className=="tr1")
				o.style.background = '#ADAED6';
		}

	}


}

function highlightOff()
{
	var o=event.srcElement;
	if(o==null)
		return;
	if(o.tagName.toUpperCase()=="TD")
	{
		o = o.parentElement;
	}
	else
		return;
	if(o.className=="tr0")
	{
		o.style.background = "#F7F7F7";
	}
	else if(o.className=="tr1")
		o.style.background = "#FFFFFF";
	else
	{
		o = o.parentElement;
		if(o!=null&&o.tagName.toUpperCase()=="TBODY")	//表格套表格
		{
			o = o.parentElement.parentElement.parentElement;
			if(o.className=="tr0")
			{
				o.style.background = "#F7F7F7";
			}
			else if(o.className=="tr1")
				o.style.background = "#FFFFFF";
		}

	}

}
initHighlight();
//新版高亮显示 end



function swapHistoryRow(obj, i, j, len)
{
	var k, index;
	k = i + 1;
	while(k < len && obj.rows[k].className == "")
	{
		obj.moveRow(k, j);
	}
	k = j + 1;
	index = i + 1;
	while(k < len && obj.rows[k].className == "")
	{
		obj.moveRow(k, index);
		index++;
		k++;
	}
}

function table_sort_string(obj, n, mode, len)
{
	var i, j, multi;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_string_fast(obj, n, mode, len);
	for(i = 1; i < len - 1; i++)
	{
		if(tab.rows[i].cells[0].innerText == "")
			continue;
		if(tab.rows[i].className == ""||tab.rows[i].className =="trbottom")
			continue;
		for(j = i + 1; j < len; j++)
		{
			if(tab.rows[j].cells[0].innerText == "")
				continue;
			if(tab.rows[j].className == ""||tab.rows[i].className =="trbottom")
				continue;
			if (mode)
			{
				if(tab.rows[j].cells[n].innerText.localeCompare(tab.rows[i].cells[n].innerText))//if(tab.rows[i].cells[n].innerText > tab.rows[j].cells[n].innerText)
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
			else
			{
				if(tab.rows[j].cells[n].innerText.localeCompare(tab.rows[i].cells[n].innerText))//if(tab.rows[i].cells[n].innerText < tab.rows[j].cells[n].innerText)
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

// 这个效率更高，但不适用于multi的情况
function table_sort_string_fast(the_tab, col, mode, len){
	var tab_arr = new Array();
	var i;
	for(i=1;i<len;i++){
		if(the_tab.rows[i].cells[0].innerText == "")
			continue;
		if(the_tab.rows[i].className == ""||the_tab.rows[i].className =="trbottom")
			continue;
		tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i]));
	}
	function SortArr(mode) {
		return function (arr1, arr2){
			var flag;
			var a,b;
			a = arr1[0];
			b = arr2[0];
			//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			flag=mode?b.localeCompare(a):a.localeCompare(b);
			return flag;
		}
	}
	tab_arr.sort(SortArr(mode));

	for(i=0;i<tab_arr.length;i++)
		the_tab.firstChild.appendChild(tab_arr[i][1]);
	for(i = 1; i < the_tab.rows.length - tab_arr.length; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}

function table_sort_num(obj, n, mode, len)
{
	var i, j;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_num_fast(obj, n, mode, len);
	for(i = 1; i < len - 1; i++)
	{
		if(tab.rows[i].cells[0].innerText == "")
			continue;
		if(tab.rows[i].className == "")
			continue;
		for(j = i + 1;j < len; j++)
		{
			if(tab.rows[j].cells[0].innerText == "")
				continue;
			if(tab.rows[j].className == "")
				continue;
			if (mode)
			{
				if(sl_parseFloat(tab.rows[i].cells[n].innerText) < sl_parseFloat(tab.rows[j].cells[n].innerText))
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
			else
			{
				if(sl_parseFloat(tab.rows[i].cells[n].innerText) > sl_parseFloat(tab.rows[j].cells[n].innerText))
				{
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

function table_sort_num_fast(the_tab, col, mode, len)
{
	var tab_arr = new Array();
	var i;
	for(i=1;i<len;i++)
	{
		if(the_tab.rows[i].cells[0].innerText == "")
			continue;
		tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i]));
	}
	function SortArr(mode)
	{
		return function (arr1, arr2)
		{
			var flag;
			var a,b;
			a = sl_parseFloat(arr1[0]);
			b = sl_parseFloat(arr2[0]);
			//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			flag=mode?b.localeCompare(a):a.localeCompare(b);
			return flag;
		}
	}
	tab_arr.sort(SortArr(mode));

	for(i=0;i<tab_arr.length;i++)
		the_tab.firstChild.appendChild(tab_arr[i][1]);
	for(i = 1; i < the_tab.rows.length - tab_arr.length; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}

// 1. 表格会有多余的合计行
// 2. sort="num"标示的列使用数字排序
// 3. sort="multi"标示的表格注意隐藏列，这里使用className来判断
// 4. 要提高执行效率
function sortTable()
{

	var start = new Date;
	event.cancelBubble = true;
	var the_obj = event.srcElement;
	if (the_obj.tagName != "TD")
		return;

	var tab = this.parentElement.parentElement;
	if(tab.tagName != "TABLE") return;
	window.status = "Sorting data，please wait...";

	if(the_obj.mode == undefined)
		the_obj.mode = false;

	var len = tab.rows.length;

	if (len > 0)
	{
		if (tab.rows[len-1].cells[0].innerText.indexOf("total") >= 0)
			len--;
	}

	if(the_obj.sort == "num")
		table_sort_num(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string(tab, the_obj.cellIndex, the_obj.mode, len);
	the_obj.mode = !the_obj.mode;

	window.status = "";
}


function sortTable2(){
	var start = new Date;
	var the_obj = event.srcElement;
	var tab = this.parentNode.parentNode.parentNode;
	var tab2 = this.parentNode.parentNode;

	event.cancelBubble = true;

	if (the_obj.tagName.toLocaleUpperCase() != "TD") return;
	if(tab.tagName.toLocaleUpperCase() != "TABLE") return;

	window.status = "Sorting data , please wait...";

	if(the_obj.mode == undefined) the_obj.mode = false;

	var len = tab.rows.length;

	if (len > 0)
	{
		if (tab.rows[len-1].cells[0].innerText.indexOf("Total") >= 0)
			len--;
	}

	if(the_obj.sort == "num")
		table_sort_num(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string(tab, the_obj.cellIndex, the_obj.mode, len);
	the_obj.mode = !the_obj.mode;

	window.status = "";
}

function overTable()
{
	var the_obj = event.srcElement;
	the_obj.style.cursor = "hand";//"n-resize";
}

function sortOptions(selElt)
{
	for (var i = 0; i <selElt.options.length; i++)
	{
		for (var j = 0; j<selElt.options.length-1; j++)
		{
			if (selElt.options[j].value > selElt.options[j+1].value)
			{
				tmpTxt = selElt.options[j].text;
				tmpVal = selElt.options[j].value;
				selElt.options[j].text = selElt.options[j+1].text ;
				selElt.options[j].value = selElt.options[j+1].value;
				selElt.options[j+1].text = tmpTxt ;
				selElt.options[j+1].value = tmpVal;
			}
		}
	}
}

function nextKeyPress(ce, forceNext)
{
	if (event.keyCode == 13 || forceNext)
	{
		event.keyCode = 9;
		return true;
	}
}

function getMatch(srcStr,mark,vaction)
{
	var i;

	i=srcStr.indexOf(mark);
	if (i>0)
	{
		if (vaction==0)
			return (srcStr.substring(0,i));
		else
			return (srcStr.substring(i+1));
	} else
		return srcStr;
}

function select_clear(src)
{
	var len=src.options.length;
	for(i=len-1;i>-1;i--)
	{
		src.options.remove(i);
	}
}

function split(seed,src,selectDest,recDelim){
		var s;
		var s1;
		var s2;
		var s3;
		var s4;
		var i;
		var j;
			s=src;
			if (selectDest.options.length>0)
				select_clear(selectDest);
			for(i=0;i<s.length;i++)
			{
					 s1=s[i];
					 s2=getMatch(s1,"-",0);
					 s1=getMatch(s1,"-",1);


					 if (s2==seed)
					 {

					 	 s3=getMatch(s1,"-",0);
					     s4=getMatch(s1,"-",1);
								 new_opt=document.createElement("OPTION");
								 new_opt.value=s3;

					 			 selectDest.options.add(new_opt);
								 selectDest.options(selectDest.length-1).text=s1;

				}
		}

}

function isNum(s, prompt)
{
	var i;
	var str;
	str = s.value;
	if (str == null)
		return false;
	if (str.length <= 0)
		return false;
	for (i = 0; i < str.length; i++)
	{
		if (str.charCodeAt(i) < 48 || str.charCodeAt(i) > 57)
		{
			sl_alert(prompt);
			s.focus();
			s.select();
			return false;
		}
	}
	return true;
}


function isFloat(s, prompt)
{
	return sl_checkFloat(s,prompt,12,0);
}

function selectAll(checkname)
{
	if(checkname == null)
		return;
	var iCount = checkedCount(checkname);
	var i;

	if(checkname.length == null)
	{
		checkname.checked = iCount < 1;
		return;
	}

	for(i = 0; i < checkname.length; i++)
		checkname[i].checked = iCount < checkname.length;
}

function trimNullElement(element)
{
	if (element.value = "null")
		element.value = "";
}

function showAboutDialog()
{
	showModalDialog('/about.htm', '', 'dialogWidth:530px;dialogHeight:300px;status:0;help:0');
}

function showPasswordDialog()
{
	if(showModalDialog('/system/basedata/password.jsp', '', 'dialogWidth:320px;dialogHeight:270px;status:0;help:0')!= null)
		sl_update_ok();
}

function showCalculator(type, busi_flag, contract_id)
{
	var s = "";
	if (contract_id != null)
		s = "?busi_flag=" + busi_flag + "&contract_id=" + contract_id;
	return showModalDialog('/investment/calculator' + type + '.jsp' + s, '', 'dialogWidth:600px;dialogHeight:400px;status:0;help:0');
}


function chachExit()
{
	if(event.clientX >0 && event.clientY < 0 || event.altKey)
	{
		location='/exit.jsp';

	}

}

function confirmExit()
{
	var r = sl_confirm("exit system");
	if(r)
		top.location = "/exit.jsp";
	//return r;
}

function confirmReLoad()
{
	var r = sl_confirm("Login again");
	if(r)
		top.location = "/reload.jsp";
	//return r;
}


function sl_remove_ok(url)
{
	sl_alert("delete succeed！");  // in default.vbs 删除成功
	if (url != null)
		location.replace(url);
}

function sl_cancel_ok(url)
{
	sl_alert("Cancel succeed！");  // in default.vbs
	if (url != null)
		location.replace(url);
}

function sl_back_ok(url)
{
	sl_alert("Recovery succeed！");  // in default.vbs
	if (url != null)
		location.replace(url);
}

function sl_back_ok2()
{
	return sl_confirm("Recovery passed");
}

function sl_update_ok(url)
{
	sl_alert("Save succeed！");  // in default.vbs
	if (url != null)
		location.replace(url);
}
function sl_refer_ok(url)
{
	sl_alert("Submit succeed！");  // in default.vbs
	if (url != null)
		location.replace(url);
}
function sl_check_ok(url)
{
	sl_alert("check succeed！");  // in default.vbs
	if (url != null)
		location.replace(url);
}

function sl_check_ok2(url)
{
	sl_alert("check succeed！");  // in default.vbs
	if (url != null)
		location.replace(url);
}

function sl_check_remove()
{
	return sl_confirm("Delete");
}

function sl_check_update(doNew)
{
	if (doNew == "null")
		return sl_confirm("Save");
	else
		return sl_confirm("Save update");
}

function sl_check_pass()
{
	return sl_confirm("check passed");
}
function sl_check_unpass()
{
	return sl_confirm("check not passed");
}

function sl_check_fail()
{
	return sl_confirm("waitting to edit");
}

function confirmRemoveDemo(element)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}

	if(checkedCount(element) == 0)
	{
		sl_alert("Please choose the record to delete！");
		return false;
	}

	if(sl_confirm('delete the record you choosed'))
	{
		sl_alert("delete succeed！");
		location.reload();
	}
}


function confirmRemove(element)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("Please choose the record to delete！");
		return false;
	}
	return sl_confirm('delete the record you choosed');
}
function confirmRefer(element)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("Please choose the record to submit！");
		return false;
	}
	return sl_confirm('submit the record you choosed');

}

function confirmRefer1(element,mess)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert('Please choose the record to '+mess+'！');
		return false;
	}
	return sl_confirm('sibmit the record of the '+mess);

}

function confirmCancel(element)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("Please choose the record to cancel！");
		return false;
	}
	return sl_confirm('cancel the choosed record');
}


function confirmAudit(element)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("Please choose the record to check！");
		return false;
	}
	return sl_confirm('to check the choosed record');
}

function confirmCheckDemo(isDialog)
{
	if(sl_confirm("pass checking"))
	{
		if(isDialog)
		{
			window.returnValue = null;
			window.close();
		}
		else
		{
			history.back();
		}
	}
}

function confirmWaitDemo(isDialog)
{
	if(sl_confirm("Wating to edit"))
	{
		if(isDialog)
		{
			window.returnValue = null;
			window.close();
		}
		else
		{
			history.back();
		}
	}
}

function syncDatePicker(picker, element)
{
	//element.value = picker.Year * 10000 + picker.Month * 100 + picker.Day;
	var s;
	var s1;
	var year;
	var month;
	var day;

	var i=0;
	var j=0;
	if(picker.value.length==8)
		picker.value=exchangeDateStyle(picker.value);

	s=picker.value;
	year=s.substring(0,4);
	i=s.indexOf('-');
	s1=s.substring(i+1,s.length);
	j=s1.indexOf('-');
	month=s1.substring(0,j);
	day=s1.substring(j+1,s1.length);
	if(month.length==1)
	month='0'+month;

	element.value=year+month+day;
}

function isBetween(element,low,high,point,caption)
{
	var s;
	if (element == null)
		return false;
	var str=element.value;
	s=element.value;
	if (str==null)
		return false;
	if (str.length<1)
		return false;
	var str=element.value;
	if (s<low || s>=high)
	{
		alert(caption);
		element.focus();
		element.select();
		return false;
	}
	var i=0;

	i=str.indexOf(".");
	if (i>0)
	{
		var len=str.length;
		if (len-i-1>point)
		{
			alert("At most "+point+" size after decimal!");
			element.focus();
			element.select();
			return false;
		}
	}
	return true;
}


function findElement(name)
{
	return document.all(name);
}

function enableElements(element, enable)
{
	var i;
	for (i = 0; i < element.all.length; i++)
	{
		if(enable)
		element.all.item(i).style.display = "";//!;
		else
		element.all.item(i).style.display = "none";//!;
	}
}

function cTrim(sInputString,iType)
{
    var sTmpStr = ' ';
    var i = -1;

    if(iType == 0 || iType == 1)
    {
        while(sTmpStr == ' ')
        {
            ++i;
            sTmpStr = sInputString.substr(i,1);
        }
        sInputString = sInputString.substring(i);
    }
    if(iType == 0 || iType == 2)
    {
        sTmpStr = ' ';
        i = sInputString.length;
        while(sTmpStr == ' ')
        {
            --i;
            sTmpStr = sInputString.substr(i,1);
        }
        sInputString = sInputString.substring(0,i+1);
    }
    return sInputString;
}

function confirmDelete(element, field, form)
{
	var selcount = 0;
	if (element == null)
		return;

	if (element != null && element.checked)
		selcount = 1;
	for (var i = 0; i < element.length; i++)
	{
		if(element[i].checked)
			selcount++;
	}

	if (selcount >= 1)
	{
		if(sl_confirm("delete the choosedrecord"))
		{
			field.value = 1;
			form.submit();
		}
	}
	else
		sl_alert("Please choose the record to delete！");
}

function trim(s)
{
	while (s.substring(0,1) == ' ')
	{
		s = s.substring(1,s.length);
	}
	while (s.substring(s.length-1,s.length) == ' ')
	{
		s = s.substring(0,s.length-1);
	}
	return s;
}

function trimZero(s)
{
	var result = s, s1;
	var n1 = 0, n2 = 0;
	s1 = s;

	n1 = s1.indexOf("零仟零佰零拾零万");
	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 8);
		s1 = result;
		n1 = s1.indexOf("零仟零佰零拾零万");
	}
	n1 = s1.indexOf("零拾");

	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零拾");
	}
	n1 = s1.indexOf("零佰");
	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零佰");
	}
	n1 = s1.indexOf("零仟");
	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零仟");
	}
	n1 = s1.indexOf("零元");
	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 1);
		s1 = result;
		n1 = s1.indexOf("零元");
	}
	n1 = s1.indexOf("零角");
	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零角");
	}
	n1 = s1.indexOf("零分");
	while (n1 > 0)
	{
		result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零分");
	}
	n1 = s1.indexOf("零万");
	while (n1 > 0)
	{
		if (s1.substring(0, n1).length > 0)
		    result = s1.substring(0, n1) + s1.substring(n1 + 1);
		else
			result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零万");
	}
	n1 = s1.indexOf("零亿");
	while (n1 > 0)
	{
		if (s1.substring(0, n1).length > 0)
			result = s1.substring(0, n1) + s1.substring(n1 + 1);
		else
			result = s1.substring(0, n1) + s1.substring(n1 + 2);
		s1 = result;
		n1 = s1.indexOf("零亿");
	}
	return result;
}


function numToChinese1(input)
{
	var s1 = "零壹贰叁肆伍陆柒捌玖";
	var s4 = "分角整元拾佰仟万拾佰仟亿拾佰仟";
	var temp = "";
	var result = "";
	var bZero = false;

	if (input == null)
		return "输入字串不是数字串只能包括以下字符（'0'～'9'，'.')，输入字串最大只能精确到仟亿，小数点只能两位！";


	var temp = trim(input);

	var f = parseFloat("0" + temp);
	var len = 0;
	if (temp.indexOf(".") == -1)
		len = temp.length;
	else
		len = temp.indexOf(".");
	if (len > s4.length - 3)
		return "输入字串最大只能精确到仟亿，小数点只能两位！";

	var n1, n2 = 0;
	var num = "";
	var unit = "";

	for (var i = 0; i < temp.length; i++)
	{
		if (i > len + 2)
		{
			break;
		}
		if (i == len)
		{
			continue;
		}
		n1 = parseInt(temp.charAt(i));
		num = s1.substring(n1, n1 + 1);
		n2 = len - i + 2;
		unit = s4.substring(n2, n2 + 1);
		result = result + num + unit;
	}
	if ((len == temp.length) || (len == temp.length - 1))
		result = result + "整";
	if (len == temp.length - 2)
		result = result + "零分";

	result = trimZero(result);
	return result;
}

function switchFrame()
{
	if (table1.style.display == "none")
	{
		table1.style.display = "";
		top.frame.rows = "77,*,25";
 		image1.src = "/includes/msmenu/images/up_enabled.gif";
 	}
	else
	{
		table1.style.display = "none";
		top.frame.rows = "23,*,25";
 		image1.src = "/includes/msmenu/images/down_enabled.gif";
	}
}

function initFrame()
{
	if (top.frame.rows == "23,*,25")
	{
		table1.style.display = "none";
		image1.src = "/includes/msmenu/images/down_enabled.gif";
	}
}

function getCurrency(sub_code, book_code)
{
	if (book_code == 1)
		return sub_code.substring(3, 5);
	else
		return sub_code.substring(5, 7);
}

 // 数字金额转换汉字金额
 function numToChinese(numberValue){

  var numberValue=new String(Math.round(numberValue*100)); // 数字金额
  var chineseValue=""; // 转换后的汉字金额
  var firstNum=numberValue.substr(0,1);
  if(firstNum=="-"){
     chineseValue="负";
     numberValue=numberValue.substr(1,numberValue.length-1);
   }

  var String1 = "零壹贰叁肆伍陆柒捌玖";       // 汉字数字
  var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分";     // 对应单位
  var len=numberValue.length;         // numberValue 的字符串长度
  var Ch1;             // 数字的汉语读法
  var Ch2;             // 数字位的汉字读法
  var nZero=0;            // 用来计算连续的零值的个数
  var String3;            // 指定位置的数值

  if (numberValue==0){

   chineseValue = "零元整";
   return chineseValue;

  }

  String2 = String2.substr(String2.length-len, len);   // 取出对应位数的STRING2的值

  for(var i=0; i<len; i++){

   String3 = parseInt(numberValue.substr(i, 1),10);   // 取出需转换的某一位的值
   //alert(String3);
   if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){

    if ( String3 == 0 ){

     Ch1 = "";
     Ch2 = "";
     nZero = nZero + 1;

    }else if ( String3 != 0 && nZero != 0 ){

     Ch1 = "零" + String1.substr(String3, 1);
     Ch2 = String2.substr(i, 1);
     nZero = 0;

    }else{

     Ch1 = String1.substr(String3, 1);
     Ch2 = String2.substr(i, 1);
     nZero = 0;
    }
   }else{              // 该位是万亿，亿，万，元位等关键位
    if( String3 != 0 && nZero != 0 ){

     Ch1 = "零" + String1.substr(String3, 1);
     Ch2 = String2.substr(i, 1);
     nZero = 0;

    }else if ( String3 != 0 && nZero == 0 ){

     Ch1 = String1.substr(String3, 1);
     Ch2 = String2.substr(i, 1);
     nZero = 0;

    }else if( String3 == 0 && nZero >= 3 ){

     Ch1 = "";
     Ch2 = "";
     nZero = nZero + 1;

    }else{

     Ch1 = "";
     Ch2 = String2.substr(i, 1);
     nZero = nZero + 1;

    }

    if( i == (len - 11) || i == (len - 3)) {    // 如果该位是亿位或元位，则必须写上
     Ch2 = String2.substr(i, 1);
    }

   }
   chineseValue = chineseValue + Ch1 + Ch2;

  }

  if ( String3 == 0 ){           // 最后一位（分）为0时，加上“整”
   chineseValue = chineseValue + "整";
  }

  return chineseValue;
 }
//*****************判断日期*********************************
//20041118 Created by CaiYuan
//判断日期格式的步骤:  1.判断是否有非法字符，日期中只能包含数字和分隔符
//					  2.判断日期中是否有两个分隔符把字符串分成3份，即年、月、日
//					  3.判断日期年、月、日是否合法
//					  4.在月的基础上判断日的合法性(是否闰年)
function sl_checkDate(DateString,DateName)
{
	var info = "Date format is not valid";

	var separator = '-';  	//分隔符必须是‘-’
	var tempy = ''; 		//年
	var tempm = ''; 		//月
	var tempd = ''; 		//日
	var tempArray;
	if(DateString.value.length==8)
		DateString.value=exchangeDateStyle(DateString.value);

	//1。。。。
	if (!isCharsInBag( DateString.value, "0123456789-"))
	{
		sl_alert(DateName + "Only contains correctly 0123456789 and separator '-'\n" + "Please re-enter"+ DateName);
		DateString.focus();
		DateString.select();
		return false;
	}
	if (!sl_check(DateString, DateName, 10, 8))
	{
		DateString.focus();
		DateString.select();
		return false;
	}
//2。。。。
	tempArray = DateString.value.split(separator);

	if (tempArray.length!=3) 	//判断是否被分隔成3份（年、月、日）
	{
		sl_alert(info +",Please write again!");
		DateString.focus();
		DateString.select();
		return false;
	}
//3。。。。
	//判断年的合法性
	if (tempArray[0].length!=4)
	{
		sl_alert(info + ",Please enter a valid year!");
		DateString.focus();
		DateString.select();
		return false;
	}
	else
	{
		tempy = tempArray[0];
	}
	//判断月的合法性
	if (tempArray[1].length!=2)
	{
		sl_alert(info + ",Please enter a valid month!");
		DateString.focus();
		DateString.select();
		return false;
	}
	else
	{
		tempm = tempArray[1];
		if(tempm<1||tempm>12)
		{
   			sl_alert("month must be between 01 and 12!");
    		DateString.focus();
			DateString.select();
    		return false;
    	}
	}
	//判断日的合法性
	if (tempArray[2].length!=2)
	{
		sl_alert(info + ",Please enter a valid date!");
		DateString.focus();
		DateString.select();
		return false;
	}
	else
	{
		tempd = tempArray[2];
		if(tempd<1||tempd>31)
		{
   			 sl_alert("date must be between 01 and 31!");
   			 DateString.focus();
			 DateString.select();
   		 	 return false;
 		}
 		else
 		{
 			if(tempm == 2)
 			{
     			if(isLeapYear(tempy)&&tempd>29)
     			{
       				alert("this year is leap-year，February's date must be between 01 and 29!");
      				DateString.focus();
					DateString.select();
       				return false;
     			}
 				if(!isLeapYear(tempy)&&tempd>28)
 				{
      			     alert("this year is not leap-year，February's date must be between 01 and 2!");
       			     DateString.focus();
					 DateString.select();
      				 return false;
     			}
 			}
			if((tempm == 4||tempm == 6||tempm == 9||tempm == 11)&&tempd>30)
			{
				alert("this month does not contains 31" + ",Please enter a valid date!");
      				DateString.focus();
					DateString.select();
       				return false;
			}
 		}
	}
	return true;
}

//闰年判断
function isLeapYear(year)
{
 	if((year%4==0&&year%100!=0)||(year%400==0))
 	{
 		return true;
 	}
 		return false;
}
//*********************************************************
//计算输入字符串的长度
function showLength(input)
{
	var temp = trim(input);
	var tempArray = temp.split(' ');
	var result = "";
	for(var i=0;i<tempArray.length;i++)
	{
		result = result + tempArray[i];
	}
	return result.length;
}
//根据输入金额显示相应的中文名
function showCnMoney(value,name)
{
	if (trim(value) == "")
		name.innerText = "";
	else
		name.innerText = "(" + numToChinese(value) + ")";
}
//*****************判断日期---无分隔符*********************************
//20041230 Created by CaiYuan
//判断日期格式的步骤:  1.判断是否有非法字符，日期中只能包含数字，且只能是8位
//					  2.substring把日期分割成年、月、日
//					  3.判断日期年、月、日是否合法
//					  4.在月的基础上判断日的合法性(是否闰年)
function sl_checkNumDate(DateString,DateName)
{
	var info = "日期格式不合法 ";

	var tempy = ''; 		//年
	var tempm = ''; 		//月
	var tempd = ''; 		//日

//1。。。。
	if (!isCharsInBag( DateString.value, "0123456789"))
	{
		sl_alert(DateName + "Only contains 0123456789\n" + "Please reEnter"+ DateName);
		DateString.focus();
		DateString.select();
		return false;
	}
	if (!sl_check(DateString, DateName, 8, 8))
	{
		DateString.focus();
		DateString.select();
		return false;
	}
//2。。。。
	tempy = DateString.substring(0,3);
	tempm = DateString.substring(4,5);
	tempd = DateString.substring(6,7);
//3。。。。
	//判断年的合法性
	if (tempy<1800||tempy>3000)
	{
		sl_alert(info + ",Please enter a valid month!");
		DateString.focus();
		DateString.select();
		return false;
	}
	//判断月的合法性
	if(tempm<1||tempm>12)
	{
   		sl_alert("month must be between 01 and 12!"); //月份必须在01和12之间
    		DateString.focus();
		DateString.select();
    		return false;
    	}
	//判断日的合法性
	if(tempd<1||tempd>31)
	{
		 sl_alert("date must be between 01 and 31!");//日期必须在01和31之间
 		 DateString.focus();
		 DateString.select();
 	 	 return false;
 	}
 	else
 	{
 		if(tempm == 2)
 		{
  			if(isLeapYear(tempy)&&tempd>29)
    			{
       				alert("this year is leap-year，February's date must be between 01 and 29!");//该年为闰年，二月份日期必须在01到29之间
      				DateString.focus();
					DateString.select();
       				return false;
     			}
 				if(!isLeapYear(tempy)&&tempd>28)
 				{
      			     alert("this year is not leap-year，February's date must be between 01 and 28!");
       			     DateString.focus();
					 DateString.select();
      				 return false;
     			}
 		}
		if((tempm == 4||tempm == 6||tempm == 9||tempm == 11)&&tempd>30)
		{
				alert("this month does not contains 31" + ",Please enter a valid date!"); //该月没有31号
      				DateString.focus();
				DateString.select();
       				return false;
		}
 	}
return true;
}
//*********************************************************
//20050114 By CaiYuan  记录点击按钮的操作日志
function btnClick_log(id,btn_name)
{
	showModalDialog('/btnClick_log.jsp?id='+id+'&btn_name='+btn_name,'','dialogWidth:300px;dialogHeight:200px;status:0;help:0');
}
//*********************************************************
//20050127 程序合并更新
function findCount(element,value)
{
	icount=0;
	for(i = 0; i <element.options.length; i++)
	{
		if(element.options[i].value==value)
		{
			icount=1;
			break;
		}
	}
	return icount;
}
function findValueIndex(element,value)
{
	icount=0;
	for(i = 0; i <element.options.length; i++)
	{
		if(element.options[i].value==value)
		{
			icount=i;
			break;
		}
	}
	return icount;
}
function confirmPrint(element)
{
	if (element == null)
	{
		sl_alert("Without any record selected！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("Please choose the record to print！");
		return false;
	}
	return sl_confirm('print the choosed record');
}
////////yingxj 计算两个日期相差天数
function getDaysDiff(dateFrom, dateTo)
{
	var objdateFrom = dateFrom;
	var objdateTo = dateTo;
	var sdate = new Date(objdateFrom.value.replace(/-/g,"\/"));
	var edate = new Date(dateTo.value.replace(/-/g,"\/"));
	var n = (edate.getTime()-sdate.getTime())/(24*60*60*1000);
	return n;

}

/*
	function Datetime(date,val,flag,sflag,returnflag)
	date  日期YYYY-MM-DD格式;
	val   数值;
	flag  年月日标志 1.年 2.月 3.日 ;
	sflag 加减标志   1.加 2.减;
	returnflag 返回标志 1.以YYYYMMDD返回 2.以YYYY-MM-DD返回;
	by wangc
 */
function Datetime(date,val,flag,sflag,returnflag){
		var strd;
		var sdate=new Date(date.replace(/-/g,"\/"));
		if(flag==1){
			if(sflag==1)
				sdate.setYear(sdate.getYear()+parseInt(val));
			else
				sdate.setYear(sdate.getyear()-parseInt(val));
		}else if(flag==2){
			if(sflag==1)
				sdate.setMonth(sdate.getMonth()+parseInt(val));
			else
				sdate.setMonth(sdate.getMonth()-parseInt(val));
		}else if(flag==3){
			if(sflag==1)
				sdate.setDate(sdate.getDate()+parseInt(val));
			else
				sdate.setDate(sdate.getDate()-parseInt(val));
		}if(returnflag==1)
			return sdate.getYear()*10000+(sdate.getMonth()+1)*100+sdate.getDate();
		else if(returnflag==2){
			if(sdate.getMonth()<10)
				return strd=sdate.getYear()+'-0'+(sdate.getMonth()+1)+'-'+sdate.getDate();
			else
				return strd=sdate.getYear()+'-'+(sdate.getMonth()+1)+'-'+sdate.getDate();
		}
	}
//调用资产类别树
function openTree()
{
	var v = showModalDialog('/investment/capital/department.jsp', '', 'dialogWidth:450px;dialogHeight:500px;status:0;help:0');
	if (v != null)
	{

		var value = v.split("$");

		document.theform.capitalid.value = 	value[0];
		document.theform.capitaltype.value = value[1];
	}
}

function exchangeDateStyle(value)
{
	//element.value = picker.Year * 10000 + picker.Month * 100 + picker.Day;
	var s1;
	var s;
	var year;
	var month;
	var day;
	s=value;

	if(s.length==8)
	{
		year=s.substring(0,4);
		month=s.substring(4,6);
		day=s.substring(6,8);

		s1=year+"-"+month+"-"+day;
	}

	return s1;
}
function selectAllBox(checkname,obj)
{
	if(checkname == null)
		return;
	var flag=obj.checked;
	var iCount = checkedCount(checkname);
	var i;
	if(checkname.length==null)
	{
		if(flag)
			checkname.checked = true;
		else
			checkname.checked = false;
		return;
	}
	for(i = 0; i < checkname.length; i++)
	{
		if(flag)
			checkname[i].checked = true;
		else
			checkname[i].checked = false;
	}
}
function showWaitting(flag)
{
	var waitDiv = document.getElementById("waitting");
	if(waitDiv!=null)
	{
		if(flag!=null&&flag==1)
		{
			waitDiv.style.visibility="visible";
		}
		else
			waitDiv.style.visibility="hidden";
	}
}

function setWaitingTitle(title)
{
	var waittingTitle = document.getElementById("waittingTitle");
	if(waittingTitle!=null&&title!=null&&title!="")
	{
		waittingTitle.innerText = title+"，Please waiting ...";//
	}
}

function setWaittingFlag(flag) //是否在页面提交或跳转的时候显示等待框
{
	if(flag!=null&&flag==true)
	{
		document.body.onbeforeunload = function (){
			showWaitting(1);
		}
	}
	else
	{
		document.body.onbeforeunload = function (){

		}
	}

}

//将所有按钮置灰或恢复
function disableAllBtn(flag)
{
	var objs = document.getElementsByTagName("button");
	if(objs==null)
		return;
	for(var i=0;i<objs.length;i++)
	{
		if(flag!=null&&flag==true)
			objs[i].disabled = true;
		else
			objs[i].disabled = false;
	}
}

/**
 * 获得选中复选框的值数组
 * @return 数组
 */
function getCheckedBoxValue(element)
{
	var i,j=0;
	var ret = new Array();

	if(element.length == null)
	{
		if(element.checked)
		{
			ret[0] = element.value;
		}
		return ret;
	}

	for(i = 0; i < element.length; i++)
	{
		if(element[i].checked)
		{
			ret[j] = element[i].value;
			j++;
		}
	}

	return ret;
}

function Selecthide()
{
	if(parent.leftshow)
	{
		parent.centerFrame.cols="200,8,*";
		parent.leftshow = false;
		//document.all.menuSwitch.src='../images/dot_6.gif';
		document.all.menuSwitch.title='hidden';//隐藏
	}
	else
	{
		parent.centerFrame.cols="0,8,*";
		parent.leftshow = true;
		//document.all.menuSwitch.src='../images/dot_5.gif';
		document.all.menuSwitch.title='show';//显示
	}
}

function sethide(){
	parent.centerFrame.cols="0,8,*";
	parent.leftshow = true;
}

//2007-08-08 TSG
function addValue(value1,value2){
 if(value1 == "" || value1 == "null" || value1 == null) value1 = "0";
 if(value2 == "" || value2 == "null" || value2 == null) value2 = "0";
 var temp1=0;
 var temp2=0;
 if(value1.indexOf(".")!=-1)
   temp1=value1.length - value1.indexOf(".")-1;
 if(value2.indexOf(".")!=-1)
   temp2=value2.length - value2.indexOf(".")-1;

 var temp=0;

 if(temp1>temp2)
 	temp = (parseFloat(value1) + parseFloat(value2)).toFixed(temp1);
 else
  temp = (parseFloat(value1) + parseFloat(value2)).toFixed(temp2);

 return temp;

}

function subValue(value1,value2){
 if(value1 == "" || value1 == "null" || value1 == null) value1 = "0";
 if(value2 == "" || value2 == "null" || value2 == null) value2 = "0";
 var temp1 = 0;
 var temp2 = 0;
 if(value1.indexOf(".") != -1)
   temp1 = value1.length - value1.indexOf(".")-1;
 if(value2.indexOf(".") != -1)
   temp2 = value2.length - value2.indexOf(".")-1;

 var temp = 0;

 if(temp1 > temp2)
 	temp = (parseFloat(value1) - parseFloat(value2)).toFixed(temp1);
 else
    temp = (parseFloat(value1)- parseFloat(value2)).toFixed(temp2);

 return temp;

}
function DropSpace(field)
{
	if((typeof field != "object") || (field == null)){return {};}
	if((field.value != "") && (field.value != null))
	{
		var temp_string = field.value;
		var pos1 = 0;
		var pos2 = 0;

		if(temp_string.charAt(0) != " ")
		{
			pos1 = 0;
		}
		else
		{
			var temp_pos=0;
			while(temp_pos<temp_string.length)
			{
				var head_char = temp_string.charAt(temp_pos);
				if(head_char != " ")
				{
					pos1 = temp_pos;
					temp_pos = temp_string.length;
				}
				temp_pos++;
			}
			if(temp_pos == temp_string.length)
			{
				pos1 = temp_string.length - 1;
			}
		}

		if(temp_string.charAt((temp_string.length - 1)) != " ")
		{
			pos2 = temp_string.length - 1;
		}
		else
		{
			var temp_pos=temp_string.length - 1;
			while(temp_pos>=0)
			{
				var last_char = temp_string.charAt(temp_pos);
				if(last_char != " ")
				{
					pos2 = temp_pos;
					temp_pos = -1;
				}
				temp_pos--;
			}
			if(temp_pos == -1)
			{
				pos2 = 0;
			}
		}
		if(pos2 >= pos1)
		{
			field.value = temp_string.substring(pos1,pos2+1);
			return field.value;
		}
		else
		{
			field.value =  "";
			return field.value;
		}
	}
}
/**
 * 显示、隐藏进度条
 */
function showWaiting(flag)
{
	var waitDiv = document.getElementById("waiting");
	if(waitDiv!=null)
	{
		if(flag!=null&&flag==1)
		{
			waitDiv.style.visibility="visible";
		}
		else
			waitDiv.style.visibility="hidden";
	}
}
function setLefthide()
{
	//隐藏左边的菜单
	parent.centerFrame.cols="5,*";
	parent.leftshow = true;
}

/**
 * 查询按钮权限问题
 */

function disableQueryButton(objId)
{
	var queryButton = document.getElementById(objId);
	queryButton.disabled = true;
}

//回车后光标的走向
function cursorMakefor(obj1,obj2)
{
	if(event.keyCode == 13)
		document.getElementById(obj2).focus();
}


//获取下拉列表选中项的文本值
function getSelectedOptionText(name){
   var return_value = '';
   var obj=document.getElementById(name);
   if(obj != null){
	   for(i=0;i<obj.length;i++){
	     if(obj[i].selected==true){
	      // if(obj[i].value != 0)//排除返回请选择
	         return_value =  obj[i].innerText; //关键是通过option对象的innerText属性获取到选项文本
	        //alert('--' + obj[i].innerText)
	     }
	   }
   }
   return return_value;
}

//ADD BY TSG 2009-04-05
//将带‘ ，’的float转化为String
function sl_parseFloatToString(value)
{
	var s = "";
	value = trimNull(value);

	for (var i = 0; i < value.length; i++)
	{
		if (value.charAt(i) != ',')
			s = s + value.charAt(i)
	}
		return s;
}

//@autor by 鲁大深 解决VBscript trim_money()方法不支持问题
function parseStringToFloat(obj){
	if ( typeof obj == "object"){
		var _obj = obj;
		var s="";
		var _obj_value = trimNull(obj.getAttribute("value"));
		if(_obj_value.length > 0){
			for (var i = 0; i < _obj_value.length; i++){
				if (_obj_value.charAt(i) != ',')
					s = s + _obj_value.charAt(i);
			}
		}
		_obj.setAttribute("value",s);
	}
}

//ADD BY TSG 2009-04-07
//判断是否为浮点数
function checkFloat(theFloat){
    theFloat = trimNull(theFloat);
    len = theFloat.length;
    //alert('theFloat:' + theFloat + 'len:' + len);
    if(len > 1 && theFloat.substring(0,1) == '-'){//负数
       theFloat = theFloat.substring(1,len)
       len = len - 1;
      // alert('theFloat:' + theFloat + 'len:' + len);
    }
    dotNum = 0;
    for(var i = 0;i < len;i++){
        oneNum = theFloat.substring(i,i+1);
        if (oneNum == ".")
            dotNum++;
        //当前位置字符为非'.'且非数字字符或者要判断的theFloat含有多个'.',返回false，即theFloat为非浮点数
        if ( ((oneNum < "0" || oneNum > "9") && oneNum != ".") || dotNum > 1)
          return false;
    }
    if (len > 1 && theFloat.substring(0,1) == "0"){//首位为0，第二位非'.'，返回false，即theFloat为非浮点数
        if (theFloat.substring(1,2) != ".")
            return false;
    }
    return true;
}

//ADD BY TSG 2009-04-13  将str中findChar替换为repChar
function sl_replace(str,findChar,repChar)
{
	var s = '';
	str = trimNull(str);
	for (var i = 0; i < str.length; i++)
	{
		if (str.charAt(i) != findChar)
			s = s + str.charAt(i)
	     else
	        s = s + repChar;
	}
   return s;
}

//ADD BY TSG 2009-04-13 剔除null值和空格
function trimNull(str){
  var s = '';
  if(str == null || str == 'null') return s;
  s = trim(str);
  return s;
}

//ADD BY TAOCHEN 2009-11-06
/*非负检查 Decimal*/
function sl_checkDecimalNegative(field,name){
	var data;

	if(sl_checkDecimal(field,name)){
		data = field.value;

		if(data<=0){
			sl_alert(name+"Must be greater than zero！");
			return false;
		}
	}

	return true;
}
//add by hesl 2011-05-24
function sl_modify_ok(url)
{
	sl_alert("modify success");  // in default.vbs
	if (url != null)
		location.replace(url);
}

function selectConfirm(element,info)
{
	if (element == null)
	{
		sl_alert("未选定任何记录！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("请选定要"+info+"的记录！");
		return false;
	}
	return sl_confirm(info+"选定的记录");
}


function exprotExcel(tableid) //读取表格中每个单元到EXCEL中
{
    var curTbl = document.getElementById(tableid);
    var oXL = new ActiveXObject("Excel.Application");
    //创建AX对象excel
    var oWB = oXL.Workbooks.Add();
    //获取workbook对象
    var oSheet = oWB.ActiveSheet;
    //激活当前sheet
    var Lenr = curTbl.rows.length;
    //取得表格行数
    for (i = 0; i < Lenr; i++)
    {
        var Lenc = curTbl.rows(i).cells.length;
        //取得每行的列数
        for (j = 0; j < Lenc; j++)
        {
            oSheet.Cells(i + 1, j + 1).value = curTbl.rows(i).cells(j).innerText;
            //赋值
        }
    }
    oXL.Visible = true;
    //设置excel可见属性
}


//四舍五入方法
function FormatNumber(srcStr,nAfterDot){
  var srcStr,nAfterDot;
  var resultStr,nTen;
  srcStr = ""+srcStr+"";
  strLen = srcStr.length;
  dotPos = srcStr.indexOf(".",0);
  if (dotPos == -1){
    resultStr = srcStr+".";
    for (i=0;i<nAfterDot;i++){
      resultStr = resultStr+"0";
    }
  }
  else{
    if ((strLen - dotPos - 1) >= nAfterDot){
      nAfter = dotPos + nAfterDot + 1;
      nTen =1;
      for(j=0;j<nAfterDot;j++){
        nTen = nTen*10;
      }
      resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
    }
    else{
      resultStr = srcStr;
      for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
        resultStr = resultStr+"0";
      }

    }
  }

 return resultStr;

}

//去除文本当中的所有空格
function trimString(str){
	return str.replace(/\s/ig,'');
}
