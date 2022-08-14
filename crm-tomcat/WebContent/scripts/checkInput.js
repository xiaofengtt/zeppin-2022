//字母的输入检查
function checkEdh(inputObject, checkFieldName) {
	if (!IsEdh(inputObject.value)) {
		if (checkFieldName == "") {
			alert("请输入字母。");
		} else {
			alert (checkFieldName);
		}
		inputObject.focus();
		inputObject.select();
	}
}


//字母及数字的输入检查
function checkEdhAndNum(inputObject, checkFieldName) {
	if (!IsEdhNum(inputObject.value)) {
		if (checkFieldName == "") {
			alert("请输入字母或数字。");
		} else {
			alert (checkFieldName);
		}
		inputObject.focus();
		inputObject.select();
	}
}

//数字的输入检查
function checkNum(inputObject, checkFieldName) {
	if (inputObject.value == "") return true;
	if (!IsNumber(inputObject.value)) {
		if (checkFieldName == "") {
			alert("请输入数字。");
		} else {
			alert (checkFieldName);
		}
		inputObject.focus();
		inputObject.select();
	}
}

//邮件地址检查
function checkMailAdr (inputObject) {
    if (!IsMailAdr (inputObject.value) && inputObject.value != "") {
            alert("邮件地址输入错误。");
        inputObject.focus();
        inputObject.select();
    }
}

//字母及数字的检查
function IsEdhNum(targetString){
	var strAlnum="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-."
	for ( var i = 0; i < targetString.length; i++ ){
		var c = targetString.charAt(i);
		if ( strAlnum.indexOf(c,0) == -1 ) {
			return false;
		}
	}
	return true;
}

//字母的检查
function IsEdh(targetString){
	var strAlnum="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-"
	for ( var i = 0; i < targetString.length; i++ ){
		var c = targetString.charAt(i);
		if ( strAlnum.indexOf(c,0) == -1 ) {
			return false;
		}
	}
	return true;
}

//数字检查
function IsNumber(targetString){
	var strNumber  ="0123456789.,";
	for (var i = 0; i < targetString.length; i++){
		var c = targetString.charAt(i);
		if (strNumber.indexOf(c,0) == -1) {
			return false;
		}
	}
	return true;
}

//数字检查
function IsNumber1(targetString){
	if (targetString == "") return true;
	var strNumber  ="0123456789.,";
	for (var i = 0; i < targetString.length; i++){
		var c = targetString.charAt(i);
		if (strNumber.indexOf(c,0) == -1) {
			return false;
		}
	}
	return true;
}

//邮件检查
function IsMailAdr(targetString){
    var ATkeyWord = targetString.indexOf("@",0);
    if (ATkeyWord == -1 || ATkeyWord == 0 || ATkeyWord == (targetString.length - 1)) return false;
    var DOTkeyWord = targetString.indexOf(".",0);
    if (DOTkeyWord == -1 || DOTkeyWord == 0 || DOTkeyWord == (targetString.length - 1)) return false;
    var atCount = 0;
    for ( var i = ATkeyWord + 1; i < targetString.length; i++) {
        var c = targetString.charAt(i);
        if (c.indexOf("@", 0) != -1) {
            atCount++;
        }
    }
    if (atCount >0) return false;
	return true;
}

/*
用途：判断是否是日期
输入：date：日期；fmt：日期格式
返回：如果通过验证返回true,否则返回false
*/
function isDate( date ) {
    var fmt="yyyy-MM-dd";
    var yIndex = fmt.indexOf("yyyy");
    if(yIndex==-1) return false;
   var year = date.substring(yIndex,yIndex+4);
   var mIndex = fmt.indexOf("MM");
    if(mIndex==-1) return false;
   var month = date.substring(mIndex,mIndex+2);
   var dIndex = fmt.indexOf("dd");
    if(dIndex==-1) return false;
   var day = date.substring(dIndex,dIndex+2);
    if(!IsNumber(year)||year>"2100" || year< "1900") return false;
    if(!IsNumber(month)||month>"12" || month< "01") return false;
    if(day>getMaxDay(year,month) || day< "01") return false;
    return true;
}
function getMaxDay(year,month) {
 if(month==4||month==6||month==9||month==11)
  return "30";
 if(month==2)
  if(year%4==0&&year%100!=0 || year%400==0)
   return "29";
  else
   return "28";
 return "31";
}


/*
用途：检查输入字符串是否符合金额格式
 格式定义为带小数的正数，小数点后最多2位
输入：
 s：字符串
返回：
 如果通过验证返回true,否则返回false 
*/
function isMoney( s ){   
 var regu = "^[0-9]+[\.][0-9]{0,2}$";
 var re = new RegExp(regu);
 if (re.test(s)) {
    return true;
 } else {
    return false;
 }
}
/*
用途：检查输入的起止日期是否正确，规则为两个日期的格式正确，
 且结束如期>=起始日期
输入：
 startDate：起始日期，字符串
 endDate：结束如期，字符串
返回：
 如果通过验证返回true,否则返回false 
*/
function checkTwoDate( startDate,endDate ) {
 if( !isDate(startDate) ) {
  alert("起始日期不正确!");
  return false;
 } else if( !isDate(endDate) ) {
  alert("终止日期不正确!");
  return false;
 } else if( startDate > endDate ) {
  alert("起始日期不能大于终止日期!");
  return false;
 }else{
 	 return true;
 }
}

/*
用途：检查输入字符串是否是带小数的数字格式,
输入：
 s：字符串
返回：
 如果通过验证返回true,否则返回false 
*/
function isDecimal( str ){   
         if(isInteger(str)) return true;
 var re = /^[-]{0,1}(\d+)[\.]+(\d+)$/;
  //var re = /^{0,1}(\d+)[\.]+(\d+)$/;//不可以是负数
 if (re.test(str)) {
    if(RegExp.$1==0&&RegExp.$2==0) return false;
    return true;
 } else {
    return false;
 }
}

/*
用途：检查输入对象的值是否符合整数格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false 
*/
function isInteger( str ){  
 var regu = /^[-]{0,1}[0-9]{1,}$/;
 //var regu = /^{0,1}[0-9]{1,}$/;
        return regu.test(str);
}
