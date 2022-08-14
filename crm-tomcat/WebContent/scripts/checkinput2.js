/*-------------------------------------------------------------
* 验证客户端输入数据
*Array.max()
*String.trim()
*isAlphaNumeric( strValue )
*isDate(sCheckStr)
*isEmail( strValue )
*isNumeric( strValue )
*isEmpty( strValue )
*isMoney( strValue )
*isFloat( strValue )
*isPhone( strValue )
*isPostalCode( strValue )
*isURL( strValue )
*checkLength( strValue, strParam )
*validate( strName, strDescription, strType)
*validate2( oField, strDescription, strType)
*validateFocus( strName, strDescription, strType)
*validateFocus2( oField, strDescription, strType)
*ActionConfirm(form,msg,field)
*findkey( strValue, strkey)检查输入数据中是否含有特殊字符
*------------------------------------------------------------*/


// 为 Array 类增加一个 max 方法
Array.prototype.max = function()
{
    var i, max = this[0];

    for( i = 1; i < this.length; i++ )
    {
        if( max < this[i] )
            max = this[i];
    }
    return max;
}

// 为 String 类增加一个 trim 方法
String.prototype.trim = function()
{
    // 用正则表达式将前后空格用空字符串替代。
    return this.replace( /(^\s*)|(\s*$)/g, "" );
}

// 使用正则表达式，检测 s 是否满足模式 re
function checkExp( re, s )
{
    return re.test( s );
}

// 验证是否 字母数字
function isAlphaNumeric( strValue )
{
    // 只能是 A-Z a-z 0-9 之间的字母数字 或者为空
    return checkExp( /^\w*$/gi, strValue );
}

// 验证是否 日期
// 日期格式必须是 2001-10-10/2001-01-10 或者为空
//////////////////////////////////////////////////////////////////////////////
//
//	Function:  isDate
//	Arguments:
//          String  sCheckStr
//	Returns:  Booleans
//	Description:  检测是否有效日期(yyyy-mm-dd)
//
//
//	Revision History
//
//	Version
//	1.0   Initial version
//  created by yangj 2003/01/02
//  modfi:	Rock 2003-5-22 18:33
//		use string function split and array function join
//
//////////////////////////////////////////////////////////////////////////////
function isDate(sCheckStr){

    var newDateObj;
    var newStr;
    var s;

    if ((sCheckStr == "") || (sCheckStr == null))
        return (true);

    if (sCheckStr.length != 10)
        return (false);

    s= sCheckStr.split("-")
    newDateObj = new Date(s[0], s[1] - 1, s[2]);

    s[0] = newDateObj.getFullYear()
    s[1] = newDateObj.getMonth() +1;
    s[2] = newDateObj.getDate() ;

    if (s[1] < 10)
        s[1] = "0" + s[1] ;
    if (s[2] < 10)
        s[2] = "0" + s[2] ;
    newStr = s.join('-')

    return (sCheckStr == newStr)
}

// 验证是否 Email
function isEmail( strValue )
{
    // Email 必须是 x@a.b.c.d 等格式 或者为空
    if( isEmpty( strValue ) ) return true;

    var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    return checkExp( pattern, strValue );

}

// 验证是否 为空
function isEmpty( strValue )
{
    if( strValue == "" )
        return true;
    else
        return false;
}

// 验证是否 数字
function isNumeric( strValue )
{
    // 数字必须是 0123456789 或者为空
    if( isEmpty( strValue ) ) return true;
    return checkExp( /^\d*$/g, strValue );
}

// 验证是否 货币
function isMoney( strValue )
{
    // 货币必须是 -12,345,678.9 等格式 或者为空
    if( isEmpty( strValue ) ) return true;

    return checkExp( /^[+-]?\d+(,\d{3})*(\.\d+)?$/g, strValue );
}

// 验证是否 小数
function isFloat( strValue )
{
    // 小数必须是 -12,345,678.9 等格式 或者为空
    if( isEmpty( strValue ) ) return true;

    return checkExp( /^[+-]?\d+(\.\d+)?$/g, strValue );
}

// 验证是否 电话
function isPhone( strValue )
{
    // 普通电话	(0755)4477377-3301/(86755)6645798-665
    // 不带区号电话 88989899
    // Call 机	95952-351
    // 手机		130/131/133/135/136/137/138/13912345678
    // 或者为空
    if( isEmpty( strValue ) ) return true;

    return checkExp( /(^\(\d{3,5}\)\d{6,8}(-\d{2,8})?$)|(^\d{7,8}$)|(^\d+-\d+$)|(^(130|131|133|135|136|137|138|139)\d{8}$)/g, strValue );
}

// 验证是否 邮政编码
function isPostalCode( strValue )
{
    // 邮政编码必须是6位数字
    return checkExp( /(^$)|(^\d{6}$)/gi, strValue )
}

// 验证是否 URL
function isURL( strValue )
{
    // http://www.yysoft.com/ssj/default.asp?Type=1&ArticleID=789
    if( isEmpty( strValue ) ) return true;

    var pattern = /^(http|https|ftp):\/\/(\w+\.)+[a-z]{2,3}(\/\w+)*(\/\w+\.\w+)*(\?\w+=\w*(&\w+=\w*)*)*/gi;
    // var pattern = /^(http|https|ftp):(\/\/|\\\\)(\w+\.)+(net|com|cn|org|cc|tv|[0-9]{1,3})((\/|\\)[~]?(\w+(\.|\,)?\w\/)*([?]\w+[=])*\w+(\&\w+[=]\w+)*)*$/gi;
    // var pattern = ((http|https|ftp):(\/\/|\\\\)((\w)+[.]){1,}(net|com|cn|org|cc|tv|[0-9]{1,3})(((\/[\~]*|\\[\~]*)(\w)+)|[.](\w)+)*(((([?](\w)+){1}[=]*))*((\w)+){1}([\&](\w)+[\=](\w)+)*)*)/gi;

    return checkExp( pattern, strValue );

}
// 检查中英文字段长度
// 中文为2个字节
function checklenstr(str) {
    num=str.length;
    var arr=str.match(/[^\\\\\\\\\\\\\\\\x00-\\\\\\\\\\\\\\\\x80]/ig);
    if(arr!=null)num+=arr.length;
    return num;
}

// 检查字段长度
//
//	strValue	字符串
//	strParam	检查参数，形如：L<10, L=5, L>117
//
function checkLength( strValue, strParam ){
    if( isEmpty( strValue ) )	return true;

    // 参数形如：L<10, L=5, L>117
    if( strParam.charAt( 0 ) != 'L' )	return false;

    var l = checklenstr(strValue);
    var ml = parseInt( strParam.substr( 2 ) );

    switch( strParam.charAt( 1 ) ){
        case '<' :
            if( l >= ml )
                return false;
            break;

        case '=' :
            if( l != ml )
                return false;
            break;

        case '>' :
            if( l <= ml )
                return false;
            break;

        default :
            return false
    }

    return true;
}

//检查是否在值域中
//
//	strValue	字符串
//	strCodeList	值域，形如：[1,2] 或['system','public']

function isWithin( strName,strValue, strCodeList ){
	var arr=strCodeList.split(",");
	if(!((strValue.length > arr[0]) && (strValue.length < arr[1]))) {
		 alert(strName+"的长度不在指定的范围内!");
		return false;
	}
    return true;
}

// 验证输入数据的合法性
//
//	输入参数
//		strName	要校验的域的id
//		strDescription	字段描述
//		strType	检查类型（可以使用多个，用空格分隔，如
//								strErrMsg = strErrMsg + Validate("penaltysum","罚没金额","NotEmpty Numeric");
//
//	输出参数
//		空串	通过验证
//		非空	未通过验证
//
function validate( strName, strDescription, strType)
{
    var oField =  strName ;
    if(!(oField)){
        return ( '!错误："' + strName + '" 不存在！\n');
    }
    return validate2( oField, strDescription, strType);
}
// 验证输入数据的合法性
//
//	输入参数
//		oField	要校验的域
//		strDescription	字段描述
//		strType	检查类型（可以使用多个，用空格分隔，如
//								strErrMsg = strErrMsg + Validate("penaltysum","罚没金额","NotEmpty Numeric");
//
//	输出参数
//		空串	通过验证
//		非空	未通过验证
//
function validate2(oField, strDescription, strType)
{
    var strMsg = "";

    var strValue = oField.value.trim();
    var arrType = strType.trim().split( " " );

    for( var i = 0; i < arrType.length; i++ )
        switch( arrType[i] )
                {
            case "AlphaNumeric" :	// 字母数字
                if( !isAlphaNumeric( strValue ) )
                    strMsg = '"' + strDescription + '" 必须是字母或数字！\n';
                break;

            case "Date" :	// 日期
                if( !isDate( strValue ) )
                    strMsg = '"' + strDescription + '" 必须具有正确的日期格式，如 2001-10-1\n';
                break;

            case "Email" :	// 电子邮件
                if( !isEmail( strValue ) )
                    strMsg = '"' + strDescription + '" 必须具有正确的邮件格式，如 webmaster@zrar.com\n';
                break;

            case "NotEmpty" :	// 不许空值
                if( isEmpty( strValue ) )
                    strMsg = '"' + strDescription + '" 不能为空！\n';
                break;

            case "Numeric" :	//数字
                if( !isNumeric( strValue )  )
                    strMsg = '"' + strDescription + '" 必须是整数！\n';
                break;

            case "Money" :	//货币
                if( !isMoney( strValue )  )
                    strMsg = '"' + strDescription + '" 必须具有正确的货币格式，如 123,456.789\n';
                break;

            case "Float" :	//小数
                if( !isFloat( strValue )  )
                    strMsg = '"' + strDescription + '" 必须具有正确的数字格式，如 123456.78\n';
                break;

            case "Phone" :	// 电话
                if( !isPhone( strValue ) )
                    strMsg = '"' + strDescription + '" 必须具有正确的电话格式，如 (0571)1234567-999\n';
                break;

            case "PostalCode" :	// 邮政编码
                if( !isPostalCode( strValue ) )
                    strMsg = '"' + strDescription + '" 必须是6位数字！\n';
                break;

            case "URL" :	// URL
                if( !isURL( strValue ) )
                    strMsg = '"' + strDescription + '" 必须是正确的URL格式！\n';
                break;
            case "ForbidChar" :	// URL
                if(forbiddenchar( strValue ) )
                    strMsg = '"' + strDescription + '" 包含了非法字符 "#","/","\\""," \' "！\n';
                break;

            default :	// 其他
                if( arrType[i].charAt( 0 ) == 'L' )
                {
                    if( !checkLength( strValue, arrType[i] ) )
                        strMsg = '"' + strDescription + '" 的长度必须 ' + arrType[i].substr(1) + '！\n';
                }
                else if(arrType[i].charAt(0) == '[' && arrType[i].substring(arrType[i].length - 1)==']'){
                    //值域[0,1] ['jj','dd']校验
                    if(!isWithin( strValue, arrType[i]))
                        strMsg =  '"' + strDescription + '" 必须为'+arrType[i] +'中的一个！\n';

                }
                else
                    strMsg = '错误："' + strDescription + '" 的类型 "' + strType + '" 不能识别！\n';
        }

    return strMsg;
}

// 检查输入数据的合法性（应用在离开字段时）
//
//	输入参数
//		obj		字段对象
//		strDescription	字段描述
//		strType	字段类型
//
function validateFocus( strName, strDescription, strType)
{
    var oField = strName ;
    if(!(oField)){
        window.alert('!错误："' + strName + '" 不存在！\n');
        return false;
    } else {
        return validateFocus2( oField, strDescription, strType);
    }
}

//控制不能输入半角单引号
function limitSingleQuotes(){
    if (window.event.keyCode ==39){
        alert("不能输入半角单引号！")
        window.event.returnValue=false;
    }
}

// 检查输入数据的合法性（应用在离开字段时）
//
//	输入参数
//		oField		字段对象
//		strDescription	字段描述
//		strType	字段类型
//
function validateFocus2( oField, strDescription, strType)
{
    var strMsg = validate2( oField, strDescription, strType);

    if( strMsg != "" )
    {
        window.alert( strMsg );
        oField.focus();
        return false;
    }
    return true
}
// 检查输入数据中是否含有特殊字符//
//	输入参数
//		strValue 输入字符串
//		strkey	 需检查的特殊字符
function findkey( strValue, strkey)
{
    var pos = strValue.indexOf(strkey)
    if(pos>=0) return true ;
    return false;
}

// 检查输入数据中是否含有非法字符//
//	输入参数
//		strValue 输入字符串
//		strkey	 需检查的特殊字符
function forbiddenchar(strValue)
{
    var strkey=new Array('#','/','\\','\'');
    var pos,i=0;
    do{      
        pos = strValue.indexOf(strkey[i]);
        if(pos>=0)return true;
        i++;
    }while(i<strkey.length);
   return false;
}
//选择记录提示
//form 提交的FORM名称	msg  提示信息	field CheckBox的名称
//
function ActionConfirm(form,msg,field){
    var flag=0;
    var truthBeTold;
    for(i = 0; i < field.length; i++)
    {
        if (field[i].disabled != true)
        {
            if(field[i].checked == true)
            {
                flag=1;
            }
        }
    }

    if(field.length == null)	//处理可能只有一条记录的Bug
    {
        if(field.checked == true)
        {
            flag=1;
        }
    }

    if (flag==0)
    {alert("请选择记录!");}
    else
    {
        truthBeTold =window.confirm("你确定要["+msg+"]吗?");
        if (truthBeTold) {
            form.DoType.value=msg;
            form.submit();
        }
    }
}
// 返回两个时间的天数差
function DateDiff(date1,date2 ){
    var n;
    var pos1,pos2;
    var year,month,eMonth,day;
    var TMonth = new Array(
            'January','February','March',
            'April','May','June','July',
            'August','September','October',
            'November','December'
            );
    pos1=date1.indexOf('-');
    if(pos1==-1){ alert("第一个日期格式不对！");return 0;}
    year=date1.substring(0,pos1);
    pos2=date1.indexOf('-',pos1+1);
    if(pos2==-1){ alert("第一个日期格式不对！");return 0;}
    month=date1.substring(pos1+1,pos2);
    eMonth = TMonth[month - 1]
    day=date1.substring(pos2+1);
    var begindate=new Date(""+eMonth+", "+day+" "+year+" 00:00");

    pos1=date2.indexOf('-');
    if(pos1==-1){ alert("第二个日期格式不对！");return 0;}
    year=date2.substring(0,pos1);
    pos2=date2.indexOf('-',pos1+1);
    if(pos2==-1){ alert("第二个日期格式不对！");return 0;}
    month=date2.substring(pos1+1,pos2);
    eMonth = TMonth[month - 1]
    day=date2.substring(pos2+1);
    var enddate=new Date(""+eMonth+", "+day+" "+year+" 00:00");
    n=parseInt((enddate - begindate)/24 / 60 / 60 / 1000);
    if(n>=0){
        n=n+1;
    }else{
        n=n - 1;
    }
    return n;
}
