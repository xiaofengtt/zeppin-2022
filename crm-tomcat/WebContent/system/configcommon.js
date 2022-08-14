//���ת��
function moneyChange(proName){
	var dotbeg;
	var dotbeh;
	var dotIndex;
	var money = document.getElementsByName(proName)[0].value;
	if(checknumber(money)){//�ж�����
		document.getElementsByName(proName)[0].setAttribute("value",'');
		return false;
	}
	var dits =money.split('.');
	if(dits.length > 2){//�ж�С�������
		document.getElementsByName(proName)[0].setAttribute("value",'');
		return false;
	}
	
	//var numVerify = /^[0-9]$/;
	if(money.indexOf(',')!=-1){
		money = money.replaceAll(',','');
	}
	dotIndex = money.indexOf('.');
	if(dotIndex == -1){
		if(money != ''){
			dotIndex =money.length;
			dotbeh = '.00';
		}
	}else{
		dotbeh = money.substring(dotIndex,money.length);
		if(dotbeh == '.'){
			dotbeh='.00';
		}
	}
	
	dotbeg= money.substring(0,dotIndex);
	
	var result =addDot(dotbeg);
	var resultMoney =result + dotbeh;
	
	if(resultMoney =='undefined'){
		resultMoney='';
	}
	document.getElementsByName(proName)[0].setAttribute("value",resultMoney);
}
	
//���Ӷ���
function addDot(str){
	var integer =Math.floor(str.length/3);
	var decimal =str.length%3;
	var num=0;
	if(decimal>0){
		num=1;
	}
	var result =[];
	for(var i=0;i<integer+num;i++){
		if(num>0&&i==0){
			if(integer+num == 1){
				result[i]=str.substring(0,decimal);
			}else{
				result[i]=str.substring(0,decimal)+",";
			}
			str=str.substring(decimal);
		}else if(i==(integer+num-1)){
			result[i]=str.substring(0,3);
			str=str.substring(3);
		}else{
			result[i]=str.substring(0,3)+",";
			str=str.substring(3);
		}
	}
	var resultStr="";
	for(var j=0;j<result.length;j++){
		resultStr =resultStr + result[j];
	}
	return resultStr;
}
//���ҿ��У��
function checknumber(vardate) 
{ 
	var Letters = "1234567890.,"; 
	var c; 
	for(var i = 0; i<vardate.length; i++) 
	{ 
		c = vardate.charAt(i);
		if (Letters.indexOf(c) ==-1) 
		{ 
			return true; 
		} 
	}
		return false; 
} 

//���ֵ�У��
function checkAllNumber(name) 
{ 
	//alert("1");
	var nameValue=document.getElementsByName(name)[0].value;
	if(typeof(nameValue)!="undefined" && nameValue!=""){
		if(isNaN(nameValue)){
			document.getElementsByName(name)[0].value="";
			return false;
		}
	}
} 

//���͵�У��
function checkInteger(name)
{
	//alert("1");
	var nameValue=document.getElementsByName(name)[0].value;
	if(typeof(nameValue)!="undefined" && nameValue!=""){
		if(!(/^\d+$/.test(nameValue))){
			document.getElementsByName(name)[0].value="";
			return false;
		}
	}	
}


//��ѯ���в�ѯ����change�¼�
function conditionChange(conditionName,num){
	var conditionValue=document.getElementsByName(conditionName+'type')[0].value;//��ѯ����ֵ
	if(conditionValue =='7'){//ƥ������ʱ����������
		document.getElementsByName(conditionName)[0].style.setAttribute("display","none");
		if(document.getElementById(conditionName + '_dateButton')){
			document.getElementById(conditionName + '_dateButton').style.setAttribute("display","none");//���ڿ��ͷ��ť
		}
		document.getElementById('divbet'+num).style.display='';
		document.getElementsByName(conditionName+'one'+num)[0].setAttribute("value",'');
		document.getElementsByName(conditionName+'two'+num)[0].setAttribute("value",'');
		
	}else{//�ָ�����
		document.getElementsByName(conditionName)[0].style.setAttribute("display","");
		if(document.getElementById(conditionName + '_dateButton')){
			document.getElementById(conditionName + '_dateButton').style.setAttribute("display","");//���ڿ��ͷ��ť
		}
		document.getElementById('divbet'+num).style.display='none';
		document.getElementsByName(conditionName)[0].setAttribute("value",'');
		
	}
}
//---------ɾ��------------
function remove(delId){
	var ids = document.getElementsByName(delId);
	if(ids.length > 0){
		var num = 0;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				num ++;
			}
		}
		if(num <= 0){
			alert('��ѡ����Ҫɾ������Ϣ��');
			return false;
		}
	}else{
		alert('û�п�ɾ����Ϣ��');
		return false;
	}
	if(confirm("ȷ��Ҫɾ����")){
		disableAllBtn(true);
		document.theform.submit();
	}
}

//---------ɾ��------------
function selectMul(keyID,actionFlag){
	var ids = document.getElementsByName(keyID);
	if(ids.length > 0){
		var num = 0;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				num ++;
			}
		}
		if(num <= 0){
			alert('��ѡ���Ӧ����Ϣ��');
			return false;
		}
	}else{
		alert('û�п�ѡ��Ӧ����Ϣ��');
		return false;
	}
	if(confirm("ȷ��Ҫִ�в�����")){
		disableAllBtn(true);
		document.theform.actionFlag.value=actionFlag;
		document.theform.submit();
	}
}

//------------����id��nameֵ�Զ�����----------------
function getNameOnChange(returnValue){
	var fieldValue=returnValue.split(",");
	var fieldID=fieldValue[0];
	var fieldName=fieldValue[1];
	var selectName =document.getElementsByName(fieldID)[0].options[document.getElementsByName(fieldID)[0].selectedIndex].text;
	document.getElementsByName(fieldName)[0].value =selectName;
}

//------------�䶯�ж������Ϣ----------------
function clearOnchange(returnValue){
	var fieldValue=returnValue.split(",");
	for(var i=0;i<fieldValue.length;i++){
		document.getElementsByName(fieldValue[i])[0].value ="";
	}
}

//����У��
function checkNumber(start,end,value) 
{
	if(isNaN(value)){
		return false;
	}
	value=trim(value);
	if(parseFloat(value)<parseFloat(start) || parseFloat(value)>parseFloat(end)){
		return false;
	}
	return true;
}


function checkVDate(name) 
{
	var checkedDate = document.getElementsByName(name)[0].value;
	var year,month,day;		
	if (checkedDate.length == 8 && !isNaN(checkedDate)){
		checkedDate = trim(checkedDate);
		year = trim(checkedDate.substring(0, 4));
		month = trim(checkedDate.substring(4, 6));
		day = trim(checkedDate.substring(6, 8));
		document.getElementsByName(name)[0].value=year+"-"+month+"-"+day; 
		//document.getElementsByName(name)[0].focus();
		//return false;
	}else if (checkedDate.length != 10){
		document.getElementsByName(name)[0].value=""; 
		//document.getElementsByName(name)[0].focus();
		return false;
	}	
	checkedDate=checkedDate.replaceAll('-','');
	if (checkedDate.length != 8){
		document.getElementsByName(name)[0].value=""; 
		//document.getElementsByName(name)[0].focus();
		return false;
	}	
	//����Ϊ�� ���Ȳ�����8��14 ���ش���
	var maxDay = new Array(0,31,29,31,30,31,30,31,31,30,31,30,31);
	checkedDate = trim(checkedDate);
	year = trim(checkedDate.substring(0, 4));
	month = parseInt(trim(checkedDate.substring(4, 6)).replaceAll("0",""));
	day = trim(checkedDate.substring(6, 8));
	// ������1��4λ ��С��1900 ���ش���
	if (year < 1900) {
		document.getElementsByName(name)[0].value="";
		//document.getElementsByName(name)[0].focus();
		return false;
	}
	// ������5��6λ ����1��12����֮�� ���ش���
	if (month < 1 || month > 12) {
		alert("month:"+month);
		document.getElementsByName(name)[0].value="";
		//document.getElementsByName(name)[0].focus();
		return false;
	}
	// ������7��8λ ����1��maxDay[month]����֮�� ���ش���
	if (day > maxDay[month] || day == 0) {
		document.getElementsByName(name)[0].value="";
		//document.getElementsByName(name)[0].focus();
		return false;
	}
	// ������2�·����ڴ���29
	if (day == 29 && month == 2 && (year % 4 != 0 || year % 100 == 0) && (year % 4 != 0 || year % 400 != 0)) 
	{
		document.getElementsByName(name)[0].value="";
		//document.getElementsByName(name)[0].focus();
		return false;
	}
	return true;
}

function checkDate(sDate) 
{
	var checkedDate = sDate;
	var year,month,day;
	if (checkedDate.length != 8){
		return false;
	}
	checkedDate=checkedDate.replaceAll('-','');
	if (checkedDate.length != 8){
		return false;
	}
	//����Ϊ�� ���Ȳ�����8��14 ���ش���
	var maxDay = new Array(0,31,29,31,30,31,30,31,31,30,31,30,31);
	checkedDate = trim(checkedDate);
	year = trim(checkedDate.substring(0, 4));
	month = trim(checkedDate.substring(4, 6));
	day = trim(checkedDate.substring(6, 8));
	
	// ������1��4λ ��С��1900 ���ش���
	if (year < 1900) {
		return false;
	}
	// ������5��6λ ����1��12����֮�� ���ش���
	if (month < 1 || month > 12) {
		return false;
	}
	// ������7��8λ ����1��maxDay[month]����֮�� ���ش���
	if (day > maxDay[month] || day == 0) {
		return false;
	}
	// ������2�·����ڴ���29
	if (day == 29 && month == 2 && (year % 4 != 0 || year % 100 == 0) && (year % 4 != 0 || year % 400 != 0)) 
	{
		return false;
	}
	return true;
}


//У������
function checkEMail(EMail)
{
	var Count = 0;
	var str = EMail;
	str = trim(str);
	if (str.length > 0)
	{
		var Letters = "@";
		//����ַ������Ƿ����"@"�ַ�
		if (str.indexOf(Letters) == -1)
		{			        
			return false;
		}
		//����ַ������Ƿ���ڶ��"@"�ַ�
		for (i = 0;i < str.length;i++)
		{
			var CheckChar = str.charAt(i);
			if (Letters.indexOf(CheckChar) >= 0)
			{			        
				Count = Count + 1;
			}
		}
		if(Count > 1)
		{
			return false;
		}
	}
	return true;
}


//------------------------���֤����Ϸ���У��------
function checkIndCerd(cardID){

		cardID = cTrim(cardID,0);
		var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
		var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
		if(cardID.length == 15 )
		{
			if(!r15.test(cardID)) return sl_alert("���֤��ʽ����ȷ!!");
		}else if(cardID.length == 18){
			if(!r18.test(cardID)) return sl_alert("���֤��ʽ����ȷ!!");					
		}else{
			sl_alert("���֤����ֻ��["+cardID.length+"]λ�����Ϲ淶!!");
			return false;		
		}
		
		var Ai;
		if(cardID.length==18)
		{
			Ai = cardID.substring(0,17);
			
		}
		else if(cardID.length==15) //�����15λ ��Ҫ���ϡ�19����
		{
			Ai =cardID.substring(0,6)+"19"+cardID.substring(6,15);
			
		}
		isYear = Ai.substr(6,4);
		isMonth = Ai.substr(10,2);
		isDay = Ai.substr(12,2);
		if(isYear==0||isYear<1900||isYear>new Date().getYear()){  //�жϾ������֤����ݵĺϷ���
			sl_alert("���֤����ݲ��Ϸ�!");
			return false;
		}
		if(isDay<1||isDay>31||isMonth<1||isMonth>12){  //�ж����ں��·ݵĺϷ���
			sl_alert("���֤���ڲ��Ϸ�!");
			return false;	
		} 
		if (cardID.length == 18){		    
			var strJiaoYan = new Array("1","0","X","9","8","7","6","5","4","3","2");
			var intQuan = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
			var intTemp = 0;
			for (i = 0; i < cardID.length - 1; i++)
		    {
					intTemp += cardID.substring(i, i + 1) * intQuan[i];
		    }
			intTemp %= 11;
			
			if(!(cardID.substring(cardID.length - 1)==strJiaoYan[intTemp])){
				sl_alert("���֤���벻�Ϸ�!");
				return false;
			}
		}
		return true;
}


//�����֯��������
function checkEntCert(CorpID)
{
    var financecode = new Array();
    if(CorpID=="00000000-0")	return false;
    if(CorpID.length!=10){
    	sl_alert("��֯�������볤��ֻ��["+CorpID.length+"]λ�����Ϲ淶!!");
		return false;
    }
	for (i=0;i<CorpID.length;i++)
	{
		financecode[i]= CorpID.charCodeAt(i);
	}
    var w_i = new Array(8);
    var c_i = new Array(8);
    s = 0,c = 0;
    w_i[0] = 3;
    w_i[1] = 7;
    w_i[2] = 9;
    w_i[3] = 10;
    w_i[4] = 5;
    w_i[5] = 8;
    w_i[6] = 4;
    w_i[7] = 2;
    if(financecode[8] != 45){
    	sl_alert("��֯����������벻�Ϸ�!");
    	return false;
    }
    for(i = 0; i < 10; i++)
    {
        c = financecode[i];
        if(c <= 122 && c >= 97)
            return false;
    }
    fir_value = financecode[0];
    sec_value = financecode[1];
    if(fir_value >= 65 && fir_value <= 90)
        c_i[0] = (fir_value + 32) - 87;
    else if(fir_value >= 48 && fir_value <= 57)
        c_i[0] = fir_value - 48;
    else
        return false;
    s += w_i[0] * c_i[0];
    if(sec_value >= 65 && sec_value <= 90)
        c_i[1] = (sec_value - 65) + 10;
    else if(sec_value >= 48 && sec_value <= 57)
        c_i[1] = sec_value - 48;
    else
        return false;
    s += w_i[1] * c_i[1];
    for(j = 2; j < 8; j++)
    {
        if(financecode[j] < 48 || financecode[j] > 57)
            return false;
        c_i[j] = financecode[j] - 48;
        s += w_i[j] * c_i[j];
    }

    c = 11 - s % 11;
    if(!(financecode[9] == 88 && c == 10 || c == 11 && financecode[9] == 48 || c == financecode[9] - 48)){
		sl_alert("��֯����������벻�Ϸ�!");
		return false;
	}
	return true;
}

//���Ӫҵִ�պ���
function checkBusinessID(CorpID)
{
	//�������Ϊ��5λע��ŵ����ɹ���Ϊ���������루6λ��+��ҵ�����루1λ��+˳��ţ�7λ��+����λ��1λ����У��λ�ɼ��鹫ʽ�Զ����ɣ�Ϊ����1��9�е�һλ
    var financecode = new Array();
    if(CorpID.length!=15){
    	sl_alert("Ӫҵִ�պ��볤��ֻ��["+CorpID.length+"]λ�����Ϲ淶!!");
		return false;
    }
    return true;
}
//�������У��
function checkLoanCardID(loanCardCode) 
{
	var financecode = new Array();
    if(loanCardCode.length!=16 && loanCardCode.length!=18){
    	sl_alert("������ų���ֻ��["+loanCardCode.length+"]λ�����Ϲ淶!!");
		return false;
    }	
	for (i=0;i<loanCardCode.length;i++)
	{
	 	financecode[i]= loanCardCode.charCodeAt(i);
	}
	var weightValue = new Array(14);
	var checkValue = new Array(14);
	totalValue = 0;
	c = 0;
	weightValue[0] = 1;
	weightValue[1] = 3;
	weightValue[2] = 5;
	weightValue[3] = 7;
	weightValue[4] = 11;
	weightValue[5] = 2;
	weightValue[6] = 13;
	weightValue[7] = 1;
	weightValue[8] = 1;
	weightValue[9] = 17;
	weightValue[10] = 19;
	weightValue[11] = 97;
	weightValue[12] = 23;
	weightValue[13] = 29;
	for ( j = 0; j < 14; j++) 
	{
		if (financecode[j] >= 65 && financecode[j] <= 90) 
		{
			checkValue[j] = (financecode[j] - 65) + 10;
		} else if (financecode[j] >= 48 && financecode[j] <= 57) 
		{
			checkValue[j] = financecode[j] - 48;
		} else 
		{
			return false;
		}
		totalValue += weightValue[j] * checkValue[j];
	}
	c = 1 + totalValue % 97;
	val = (financecode[14] - 48) * 10 + (financecode[15] - 48);
    if(!(val == c)){
		sl_alert("������Ų��Ϸ�!");
		return false;
	}	
	return true;
}


//�绰����У��
function checkTelphoneNo(telphone){
	var telphone=telphone.replaceAll('-','');
	if(telphone.length==7 || telphone.length==8 || telphone.length==11 || telphone.length==12)
	{
		if(telphone.length==7 && !checkNumber(1000000,9999999,telphone)){
			return false;	
		}else if(telphone.length==8 && !checkNumber(10000000,99999999,telphone)){
			return false;	
		}else if(telphone.length==11){
			if(telphone.substring(0,1) ==0 && !checkNumber(1000000000,9999999999,telphone)){
				return false;
			}else if(telphone.substring(0,1) ==1 && !checkNumber(10000000000,99999999999,telphone)){
				return false;
			}
		}else if(telphone.length==12 && !checkNumber(10000000000,99999999999,telphone)){
			return false;	
		}
	}else{
		return false;
	}
	return true;
}

//�����Զ���С��λ����������,����objectΪ�������ֵ,����decimalΪ����С��λ��
function roundOff(number,digit)
{
    var sNumstr = 1;
    for (i=0;i<digit;i++)
    {
       sNumstr=sNumstr*10;
    }
    sReturnValue = Math.round(parseFloat(number)*sNumstr)/sNumstr;
    return sReturnValue;
}


//-----------��ѯ--------------
function StartQuery()
{
	refreshPage();
}

//-----------ҳ���ӡ--------------
function printPage(url)
{
	var scrWidth=screen.availWidth;
	var scrHeight=screen.availHeight;
	var self=window.open(url,"PowerBOS","resizable=1");
	self.moveTo(0,0);
	self.resizeTo(scrWidth,scrHeight);	
}

//----------ҵ���ظ���������----------
function setDoubleValue(valueType)
{
	if(valueType=="href"){
		var href999=document.getElementsByName('href999');
		if(href999 && href999.length){
			for(var i=0;i<href999.length;i=i+1){
				href999[i].style.display='none';
			}
		}
	}else if(valueType=="button"){
		disableAllBtn(true);
	}
}

//-----------ҳ���ģʽ--------------
function pageOpen(url,param,mode,style,type)
{
	if(typeof(param)=='undefined' || param=='') param="1=1";
	
	if(mode=="inline"){
		location=url+'?'+param;
	}else if(mode=="popup"){
		var showValue=style.split(",");
		if(showModalDialog(url+'?'+param, '', 'dialogWidth:'+showValue[0]+'px;dialogHeight:'+showValue[1]+'px;status:0;help:0') != null)
		{
			sl_update_ok();
			location.reload();
		}
	}else if(mode=="inopen"){
		sl_update_ok();
		location=url+'?'+param;
	}else if(mode=="popopen"){
		var showValue=style.split(",");
		showModalDialog(url+'?'+param, '', 'dialogWidth:'+showValue[0]+'px;dialogHeight:'+showValue[1]+'px;status:0;help:0');
	}else if(mode=="closeforparam"){
		window.returnValue = 1;
		window.close();
	}else if(mode=="selfclose"){
		window.close();
	}
}

//------------���ö�Ӧֵ-------------
function setFormValue(fieldname,fieldvalue)
{
	if(document.getElementsByName(fieldname)[0] ==undefined){
		fieldvalue="";
	}else if(typeof(document.getElementsByName(fieldname)[0].value)=='undefined' || document.getElementsByName(fieldname)[0].value==''){
		document.getElementsByName(fieldname)[0].value=fieldvalue;
	}else{
		document.getElementsByName(fieldname)[0].value=fieldvalue;
	}
}

//-----------��ö�Ӧֵ--------------
function getFormValue(fieldname)
{
	var fieldvalue="";
	if(document.getElementsByName(fieldname)[0] ==undefined){
		fieldvalue="NoExist";
	}else if(typeof(document.getElementsByName(fieldname)[0].value)=='undefined' || document.getElementsByName(fieldname)[0].value==''){
		fieldvalue=document.getElementsByName(fieldname)[0].value;
	}else{
		fieldvalue=document.getElementsByName(fieldname)[0].value;
	}
	return fieldvalue;
}

//--------------�����߼�У��-------------
function conValidate(className,methodName,paramName){
	return showModalDialog('/system/config/content_validate_logic.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');

}

//--------------��ҵ���漰����¼�룬���ã��߼�У��-------------
function checkRule(className,methodName,paramName){

	if(className=="BusinessCheck")
		return showModalDialog('/project/apply/efvbankerror_check.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFANSBusinessCheck")
		return showModalDialog('/chart/item/efvanserror_check.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFGMSBusinessCheck")
		return showModalDialog('/projects/apply/efgmserror_check.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFIFMSBusinessCheck")
		return showModalDialog('/fund/item/efifmserror_check.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFSPSBusinessCheck")
		return showModalDialog('/course/item/efspserror_check.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="ConfigUtil")
		return showModalDialog('/system/config/efctserror_check.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
}

//--------------����ҵ���漰��js����java�У��߼�У��-------------
function checkLogic(className,methodName,paramName){
	if(className=="BusinessCheck")
		return showModalDialog('/project/apply/businesslogic_control.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFANSBusinessCheck")
		return showModalDialog('/chart/item/efanslogic_control.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFGMSBusinessCheck")
		return showModalDialog('/projects/apply/efgmslogic_control.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="CRMBusinessCheck")
		return showModalDialog('/project/flowApp/crmlogic_control.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFSPSBusinessCheck")
		return showModalDialog('/course/item/efspslogic_control.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="ConfigUtil")
		return showModalDialog('/system/config/content_validate_logic.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
}

//--------------ҵ���߼�����-------------
function actionLogic(className,methodName,paramName){
	if(className=="BusinessCheck")
		return showModalDialog('/project/apply/efvbanklogic_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFANSBusinessCheck")
		return showModalDialog('/chart/item/efanslogic_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFGMSBusinessCheck")
		return showModalDialog('/projects/apply/efgmslogic_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFIFMSBusinessCheck")
		return showModalDialog('/fund/item/efifmslogic_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="EFSPSBusinessCheck")
		return showModalDialog('/course/item/efspslogic_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
	else if(className=="ConfigUtil")
		return showModalDialog('/system/config/efctslogic_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');
}

//--------------��ʼ������-------------
function actionFlow(className,methodName,paramName){
	return showModalDialog('/project/flow/flow_action.jsp?className='+className+'&methodName='+methodName+'&paramName='+paramName, '', 'dialogWidth:0px;dialogHeight:0px;status:0;help:0');

}

//--------�����б�ҳ��ı�����ݵ�excel-----------
function exportExcelFromPageGrid(fileTitle)
{
	if(sl_confirm("��������"))
	{
		//disableAllBtn(true);//��������Ȼ���ҳ���button������
	    setWaittingFlag(false);
		location = "/system/config/downloadGrid_excel.jsp?excel_title=" + fileTitle;
	}
}

//--------����excel����ģ��--------
function exportExcelTemplate(imp_type_code, imp_type_name){
	if(sl_confirm("����ģ��"))
	{
		//disableAllBtn(true);//��������Ȼ���ҳ���button������
	    setWaittingFlag(false);
	    var url = "/system/config/download_excelImp_template.jsp";
	    url = url + "?imp_type_code=" + imp_type_code + "&imp_type_name=" + imp_type_name;
		location = url;
	}
}
//--------����excel���ݵ����ݿ����ʱ��EXCEL_DATATEMP--------
function importExcel(){
	var filePathName = document.getElementsByName("file_name")[0].getAttribute("value");
	if( (filePathName == null) || (filePathName == "") || (filePathName == "undefined") ){
		alert("��ѡ��XLS�ļ���");
		return;
	}else{
		var bool_last_xls = filePathName.lastIndexOf('.') > 0;
		var bool_last_str = filePathName.substr(filePathName.length-4).toLocaleLowerCase() == '.xls';
		if( !(bool_last_xls && bool_last_str) ){
			alert("����Ĳ���.xls�ļ�");
		}else{
			var theform = document.getElementsByName("theform")[0];
			if(theform.encoding){
				theform.setAttribute('encoding','multipart/form-data');
			}else{   
				theform.setAttribute('enctype','multipart/form-data');   
			}
			theform.setAttribute("action","/system/config/doupload_excel.jsp");
			theform.submit();
		}
	}
}

//--------����ʱ����ʽ�������ݵ�ʵ��ҵ���--------
function busiImportInfo(impTypeCode,paramName){
	if(sl_confirm("��ʽ��������"))
	{
		var modUrl = '/system/config/excel_imp_acct.jsp?impTypeCode='+impTypeCode+'&paramName='+paramName;
		if(showModalDialog(modUrl, '', 'dialogWidth:800px;dialogHeight:600px;status:0;help:0') != null)
		{
			window.returnValue = 1;
			self.close();
		}
	}
}

