var value1 = "", value2 = "", value3 = "0"
var value4 = "done", num = 0, mvalue = 0; 
function nkey(i) {
  if (num == 1) { 
     value1 = value2; value2 = value3;
     value3 = "" + i; value4 = "eidt"; num = 2; 
  } else { 
     if (value4 == "done" || value3 + "" == "0")
     { 
     	value3 = "" + i; value4 = "eidt";
	 }else {
	 	value3 += "" + i;
	 }
  }
  more(); 
}
function show(){
	alert(num);
}
function back() {
  if (num == 1) {
     value3 = value3.length > 1 ?value3.substring(0,value3.length-1):0; value4 = "eidt"; 
  } else { 
     if (value4 == "done")
     { 
     	value3 = value3.length > 1?value3.substring(0,value3.length-1):0; value4 = "eidt";
	 }else {
	 	value3 = value3.length > 1?value3.substring(0,value3.length-1):0;
	 }
  }
  more(); 
}
function ptkey() {
 if (value4 != "point")  { 
   if (value4 == "done") {
     if (num != 1) {
     	 ckey(0); 
      } 
      return;
   } else {
   	 value3 += "."; more();
   }
   value4 = "point"; 
 }
} 
function more() {
  switch(value3){
  	case '+':
  		return;
  	case '*':
  		return;
  	case '/':
  		return;
  	case '-':
  		return;
  	default:
  		$('inputvalue').value = value3 == "0"?"0.":value3;
  		break;
  }
}
function fkey(j) {
	 if (num != 1) {
	   value2 = eval(value1 + value2 + value3);
	   value1 = ""; 
	   value2 = round(value2);
	   num = 1;
       value4 = "done";
	 }
	 value3 = j;
	 rkey('=');
	 more();
}     
function rkey(j) {
 if (num != 1) { if (j == "%") {
     value3 = value3 * 0.01; 
   } 
   if (j == "=") {
     value3 = eval(value1 + value2 + value3); 
     value2 = ""; value1 = ""; num = 0;
   }
   value3 = round(value3); more(); value4 = "done"; 
 }
}
function ckey(k) {
 	if (k == 0) { if (value2 != "") 
   	{
		value3 = value2;
		value3 = "0"
	} else {
		value3 = "0";
	}
   value2 = value1; value1 = "";
   if (num == 2) { num = 1; } else { num = 0; }
 } 
 if (k == 1) {
   value3 = "0"; value2 = ""; value1 = ""; num = 0;
 }
 more(); value4 = "done"; 
}
function mvkey(k) {
 if (num == 0) {
   if (k == "+") { mvalue = mvalue + eval(value3); }
   if (k == "-") { mvalue = mvalue - eval(value3); }
   mvalue = round(mvalue); setmemo(); value4 = "done"; 
 }
}
function mckey(k) {
 if(k == "=") {
   if (num != 1) { ckey(0); }
   nkey(mvalue); value4 = "done"; 
 }
 if (k == "c") { mvalue = 0; setmemo(); } 
}
function setmemo() {
  document.cform.V2.value = "";
  if (mvalue != 0) {
    document.cform.V2.value = "<M> " + mvalue + "; ";
  }
}
function sbkey() {
 value2 = altsb(value2 + ""); 
 value3 = altsb(value3 + "");
 
} 
function altsb(sb) {
 if (sb == "") { value3 = - eval(value3) + ""; }
 if (sb == "+") { return "-"; }
 if (sb == "-") { return "+"; }
 if (sb == "*") { return "/"; }
 if (sb == "/") { return "*"; }
 return sb; 
}

function round(rndval) {
 var full = rndval + "", endstr = ""; 
 if (full.indexOf('.') < 0) { return rndval; } 
 if (eval(full.substring(0, full.indexOf('.') + 9)) == 0) 
 { return rndval; }
 var cutsize = full.length; 
 if (full.indexOf('e') > 0 ) {
   cutsize = full.indexOf('e') - 1; 
   endstr = full.substring(cutsize + 1, full.length); 
 }
 var cutstr = full.substring(full.indexOf('.'), cutsize); 
 cutsize = cutstr.length;
 if (cutsize > 9) {
   var addup = 1;
   full = full.substring(0, full.indexOf('.')); 
   for (var x=0; x<cutsize - 1; x++) {addup *= 0.1;}
   cutstr = (addup*4 + eval(cutstr)) + "";
   var cutlen = cutstr.length; 
   if (cutstr.indexOf('e') != -1) { return rndval; } 
   if (cutsize - cutlen > 4) {
     return eval(full + cutstr.substring(1, cutlen)) + endstr; 
   }
   cutstr = cutstr.substring(0, cutstr.length - 2); 
   cutstr = eval(cutstr) + "";
   if (cutstr.indexOf('e') != -1) { return rndval; } 
   if (cutlen - cutstr.length > 4) {
     return eval(full + cutstr.substring(1, cutstr.length)) + endstr; 
   }
 }
 return rndval; 
}