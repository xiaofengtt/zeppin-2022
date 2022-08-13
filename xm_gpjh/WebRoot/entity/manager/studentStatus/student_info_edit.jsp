<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学生信息修改</title>
		<% String path = request.getContextPath();%>
		<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=path%>/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="<%=path%>/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=path%>/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="<%=path%>/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script>
		Ext.onReady(function(){
		
		    var birthdayDate = new Ext.form.DateField({
			        fieldLabel: '出生日期',           
			        name: 'bean.prStudentInfo.birthday',
			        id:'birthday',
			        format:'Y-m-d',
			        allowBlank:false,
			        anchor: '29%'   
			    }); 
			    
			birthdayDate.on('render',function showvalue(p,_record,_options){
					birthdayDate.setValue('<s:date name="bean.prStudentInfo.birthday" format="yyyy-MM-dd" />');
					birthdayDate.setRawValue('<s:date name="bean.prStudentInfo.birthday" format="yyyy-MM-dd" />');
			},birthdayDate);
			
			birthdayDate.render('showbirthday');
			
			var graduateDate = new Ext.form.DateField({
			        fieldLabel: '原毕业年份',           
			        name: 'bean.prStudentInfo.graduateYear',
			        id:'graduate_date',
			        format:'Y年m月',
			        maxLength:8,
			        width:200,
			        allowBlank:true,	
			        anchor: '29%'   
			    });
		    graduateDate.on('render',function showvalue(p,_record,_options){
				graduateDate.setValue('<s:property value="bean.prStudentInfo.graduateYear"/>');
				graduateDate.setRawValue('<s:property value="bean.prStudentInfo.graduateYear"/>');
			},graduateDate);
			graduateDate.render('graduateYear');
			
			var forkDataStore = new Ext.data.SimpleStore({
				fields : ['value', 'text'], 
				data : [['汉族','汉族'],['蒙古族','蒙古族'],['回族','回族'],['藏族','藏族'],['维吾尔族','维吾尔族'],['苗族','苗族'],['彝族','彝族'],['壮族','壮族'],['布依族','布依族'],['朝鲜族','朝鲜族'],['满族','满族'],['侗族','侗族'],['瑶族','瑶族'],['白族','白族'],['土家族','土家族'],['哈尼族','哈尼族'],['哈萨克族','哈萨克族'],['傣族','傣族'],['黎族','黎族'],['傈僳族','傈僳族'],['佤族','佤族'],['畲族','畲族'],['高山族','高山族'],['拉祜族','拉祜族'],['水族','水族'],['东乡族','东乡族'],['纳西族','纳西族'],['景颇族','景颇族'],['柯尔克孜族','柯尔克孜族'],['土族','土族'],['达斡尔族','达斡尔族'],['仫佬族','仫佬族'],['羌族','羌族'],['布朗族','布朗族'],['撒拉族','撒拉族'],['毛难族','毛难族'],['仡佬族','仡佬族'],['锡伯族','锡伯族'],['阿昌族','阿昌族'],['普米族','普米族'],['塔吉克族','塔吉克族'],['怒族','怒族'],['乌孜别克族','乌孜别克族'],['俄罗斯族','俄罗斯族'],['鄂温克族','鄂温克族'],['崩龙族','崩龙族'],['保安族','保安族'],['裕固族','裕固族'],['京族','京族'],['塔塔尔族','塔塔尔族'],['独龙族','独龙族'],['鄂伦春族','鄂伦春族'],['赫哲族','赫哲族'],['门巴族','门巴族'],['珞巴族','珞巴族'],['基诺族','基诺族'],['其他','其他'],['外国血统','外国血统']] 
			});
			var forkDataCombo =new Ext.form.ComboBox({
				store: forkDataStore,
				displayField:'text',
				valueField: 'value' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择民族...',
				applyTo: 'combo-box-forkData',
				editable: true,
				forceSelection: true
			});
			
			var zzmmDataStore = new Ext.data.SimpleStore({
				fields : ['value', 'text'], 
				data : [['中共党员','中共党员'],['中共预备党员','中共预备党员'],['共青团员','共青团员'],['民革会员','民革会员'],['民盟盟员','民盟盟员'],['民建会员','民建会员'],['民进会员','民进会员'],['农工党党员','农工党党员'],['致公党党员','致公党党员'],['九三学社社员','九三学社社员'],['台盟盟员','台盟盟员'],['无党派民主人士','无党派民主人士'],['群众','群众']] 
			});
			var zzmmDataCombo =new Ext.form.ComboBox({
				store: zzmmDataStore,
				displayField:'text',
				valueField: 'value' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'政治面貌...',
				applyTo: 'combo-box-zzmmData',
				editable: true,
				forceSelection: true
			});
			
			var provinceDataStore = new Ext.data.SimpleStore({
				fields : ['value', 'text'], 
				data : [['北京市','北京'],['天津市','天津'],['河北省','河北'],['山西省','山西'],['内蒙古自治区','内蒙古'],['辽宁省','辽宁'],['吉林省','吉林'],['黑龙江省','黑龙江'],['上海市','上海'],['江苏省','江苏'],['浙江省','浙江'],['安徽省','安徽'],['福建省','福建'],['江西省','江西'],['山东省','山东'],['河南省','河南'],['湖北省','湖北'],['湖南省','湖南'],['广东省','广东'],['广西壮族自治区','广西'],['海南省','海南'],['重庆市','重庆'],['四川省','四川'],['贵州省','贵州'],['云南省','云南'],['西藏自治区','西藏'],['陕西省','陕西'],['甘肃省','甘肃'],['青海省','青海'],['宁夏回族自治区','宁夏'],['新疆维吾尔自治区','新疆'],['台湾省','台湾省'],['香港特别行政区','香港'],['澳门特别行政区','澳门'],['其它','其它']] 
			});
			var provinceDataCombo =new Ext.form.ComboBox({
				store: provinceDataStore,
				displayField:'text',
				valueField: 'value' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择...',
				applyTo: 'combo-box-provinceData',
				editable: true,
				forceSelection: true
			});
			
		});
		
		function check(){
		
			var flag=true;
			var operateresult='';
			
			if(document.getElementById('name').value=='' || document.getElementById('name').value.trim() == ''){
				flag=false;
				operateresult +='姓名不能为空！\n';
			}
			if(document.getElementById('mobilephone').value =='' || document.getElementById('mobilephone').value.trim() == '' ){
				flag=false;
				operateresult +='手机号不能为空！\n';
			}
			
			if(document.getElementById('cardno').value =='' || document.getElementById('cardno').value.trim() == '' ){
				flag=false;
				operateresult +='证件号码不能为空！\n请填写完整后在提交！';
			}
			if(!flag){
				window.alert(operateresult);
			}			
			if(document.getElementById('graduate_date').value.length>0){
				var  reg = /^(\d){4}年(\d){2}月$/;
				var gdate = document.getElementById('graduate_date').value;
				if(!reg.test(gdate)){
					alert("原毕业时间填写错误!");
					flag=false;
				}
			}
			if(!checkTextDataForPHONE()){
				flag=false;
			}
			
			if(!checkIdCard()){
				flag=false;
			}
			
			if(!checkBirthday()){
				flag=false;
			}
			
			if(!checkEmail()){
				flag=false;
			}
			
			return flag;
		}
		
		function   checkIdCard(){ 
		    with(document.print.cardType)
			{
				var card_select = options[selectedIndex].text
			}
			if(card_select=="身份证"){
			
			  idcard=document.getElementById('cardno').value;       
			  var   area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}       
			  var   idcard,Y,JYM;     
			  var   S,M;     
			  var   idcard_array   =   new   Array();     
			  idcard_array   =   idcard.split("");     
			  //地区检验  
			
			if(idcard.length == 0){
				return false;
			}
			
			  if(area[parseInt(idcard.substr(0,2))]==null)
			  {
			     alert("身份证号码输入错误！\n身份证地区非法！请重新输入");
			     return false;
			   }     
			  //身份号码位数及格式检验     
			  switch(idcard.length){     
			  case   15:     
			  if   (   (parseInt(idcard.substr(6,2))+1900)   %   4   ==   0   ||   ((parseInt(idcard.substr(6,2))+1900)   %   100   ==   0   &&   (parseInt(idcard.substr(6,2))+1900)   %   4   ==   0   )){     
			  ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性     
			  }   else   {     
			  ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性     
			  }     
			  if(ereg.test(idcard)){
			  	var birDay = '';
					birDay = idcard.substring(6,12);
					birDay = "19" + birDay;
					birDay = birDay.substring(0,4) + "-" + birDay.substring(4,6) + "-" + birDay.substr(6);
				document.getElementById('birthday').value = birDay;  
			     if(idcard!=document.getElementById('cardno1').value)
			 	return true;   //cardNoisexist(idcard);
			 else
			 	return true;
			  }    
			  else
			  {
			  	alert("身份证号码输入错误！\n身份证号码出生日期超出范围或含有非法字符!请重新输入");
			  	return false;
			  }     
			  break;     
			  case   18:     
			  //18位身份号码检测     
			  //出生日期的合法性检查       
			  //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))     
			  //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))     
			  if   (   parseInt(idcard.substr(6,4))   %   4   ==   0   ||   (parseInt(idcard.substr(6,4))   %   100   ==   0   &&   parseInt(idcard.substr(6,4))%4   ==   0   )){     
			  ereg=/^[1-9][0-9]{5}[0-9]{4}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式     
			  }   else   {     
			  ereg=/^[1-9][0-9]{5}[0-9]{4}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式     
			  }     
			  if(ereg.test(idcard)){//测试出生日期的合法性     
			  //计算校验位     
			  S   =   (parseInt(idcard_array[0])   +   parseInt(idcard_array[10]))   *   7     
			  +   (parseInt(idcard_array[1])   +   parseInt(idcard_array[11]))   *   9     
			  +   (parseInt(idcard_array[2])   +   parseInt(idcard_array[12]))   *   10     
			  +   (parseInt(idcard_array[3])   +   parseInt(idcard_array[13]))   *   5     
			  +   (parseInt(idcard_array[4])   +   parseInt(idcard_array[14]))   *   8     
			  +   (parseInt(idcard_array[5])   +   parseInt(idcard_array[15]))   *   4     
			  +   (parseInt(idcard_array[6])   +   parseInt(idcard_array[16]))   *   2     
			  +   parseInt(idcard_array[7])   *   1       
			  +   parseInt(idcard_array[8])   *   6     
			  +   parseInt(idcard_array[9])   *   3   ;     
			  Y   =   S   %   11;     
			  M   =   "F";     
			  JYM   =   "10X98765432";     
			  M   =   JYM.substr(Y,1);//判断校验位     
			  if(M   ==   idcard_array[17]){
			  	var birDay = '';
					birDay = idcard.substring(6,14);
					birDay = birDay.substring(0,4) + "-" + birDay.substring(4,6) + "-" + birDay.substr(6);	
				document.getElementById('birthday').value = birDay;
			     if(idcard!=document.getElementById('cardno1').value)
			 	return true;   //cardNoisexist(idcard);
			 	else
			 	return true;   //检测ID的校验位     
			  }else
			  {
			  	alert("身份证号码校验错误!请重新输入！");
			  	return false;
			  }     
			  }     
			  else
			  {
			  	alert("身份证号码输入错误！\n身份证号码出生日期超出范围或含有非法字符!请重新输入");
			  	return false;
			  }     
			  break;     
			  default:     
			  alert("身份证号码输入错误！\n身份证号位数不对！请重新输入")
			  return false;    
			  break;     
			  } 
			  } 
			 else
			  {
			  	if(document.getElementById('cardno').value==''){
			  		alert("证件号码不能为空!请输入证件号码");
			  		return false;
			  	}
			  	
			  }
			  if(idcard!=document.getElementById('cardno1').value)
			 	return true;// cardNoisexist(idcard);
			 else
			 	return true;
		}
		//验证证件号码是否已存在
		function cardNoisexist(cardno){
	 		var url="/info/register_isexistCardNo.action?cardno=" + cardno;
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest( );
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = checkCardNo;
		    req.send(null);
			return true;
		}
		function checkCardNo(){
			if(req.readyState == 4){
					if(req.status == 200){
						var textResult = req.responseText;
						if (textResult == '1'){
							alert('所输入的证件号码已经存在，请重新输入！');
						}
					} 
				}
		}
		function checkBirthday()
		{
			if(document.getElementById('birthday').value.length==0){
				return true;
			}
			with(document.print.cardType)
			{
				var card_select=options[selectedIndex].text
			}
			if(card_select=="身份证"){
				var idCard=document.getElementById('cardno').value;
				var myBirthday=document.getElementById('birthday').value;
				var temStr="";
				if(document.getElementById('cardno').value.length==0)
				{
					alert("请先输入身份证号码！");
					return false;
				}
			
				if(idCard.length==15)//输入的身份证号是15位
				{
					temStr = idCard.substring(6,12);
					temStr = "19" + temStr;
					temStr = temStr.substring(0,4) + "-" + temStr.substring(4,6) + "-" + temStr.substr(6);
				}
				else//输入的身份证号是18位
				{
					temStr = idCard.substring(6,14);
					temStr = temStr.substring(0,4) + "-" + temStr.substring(4,6) + "-" + temStr.substr(6);	
				}
				if(myBirthday!=temStr)
				{
					alert("选择的生日和身份证上的不同！！\n请重新选择");
					return false;
				}
				return true;
			}
			else
			return true;
			}
			
			function checkEmail()
			{
				if(document.getElementById('email').value==''){
					return true;
				}
				var email=document.getElementById('email').value;
				var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
				if(!pattern.test(email))
				{
					alert("邮箱格式不正确！\n请重新输入");
					return false;
				}
				return true;
			}
			
			function checkTextDataForPHONE()
			{
				if(document.getElementById('mobilephone').value.length==0){
					return false;
				}
				var myMobile_no=document.getElementById('mobilephone').value;
				var myreg=/^(((13[0-9]{1})|159|(15[0-9]{1}))+\d{8})$/;
				if(!myreg.test(myMobile_no))
				{
					alert("手机号码不合法！\n请重新输入");
					return false;
				}
				
				return true;
			}
			
			function isAllNum(obj,flag_last){
				var myReg = /\D+/;
				var temp = obj.value;
				if(temp != null && temp != ''){
					if(myReg.test(temp)){
						if(flag_last != null){
							return false;
						}else{
							alert('只能输入数字');
							return false;
						}
					}
				}
				return true;
			}
		</script>
	</head>
	<body>
		<form name="print" method="post" action="peStudentInfo_editexe.action" enctype="multipart/form-data" onsubmit="return check();">
			<div id="main_content">
			   <div class="content_title">学生信息修改</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <s:property value="msg"/>
			   <table width="80%">
			   		<tr>
			   			<td colspan="3">
			   				<div align="center" class="postFormBox"><s:property value="bean.enumConstByFlagStudentStatus.name"/><font color="red"><s:if test="bean.enumConstByFlagAdvanced.code==1">[<s:property value="bean.enumConstByFlagAdvanced.name"/>]</s:if></font></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   			<input type="hidden" name="bean.id" value=<s:property value="bean.id"/> />
			   			<input type="hidden" name="bean.prStudentInfo.id" value=<s:property value="bean.prStudentInfo.id"/> />
			   			<input type="hidden" name="bean.ssoUser.id" value=<s:property value="bean.ssoUser.id"/> />
			   				<div align="left" class="postFormBox"><span class="name"><label>所属学习中心<font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="bean.peSite.name" size="30" readonly value="<s:property value="bean.peSite.name"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>所属层次<font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" ><input type="text" readonly name="bean.peEdutype.name" size="30"  value="<s:property value="bean.peEdutype.name"/>"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>所属专业<font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" readonly name="bean.peMajor.name" size="30"  value="<s:property value="bean.peMajor.name"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>所属年级<font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" ><input type="text" readonly name="bean.peGrade.name" size="30"  value="<s:property value="bean.peGrade.name"/>"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>学号 <font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" readonly name="bean.regNo" value="<s:property value="bean.regNo"/>" />
			   				<%--<input type="hidden" size="30" name="maxRegNo" value='<s:property value="maxRegNo"/>' id="regNo"/>
			   				<font color=red>学号若修改为6位请填入：<b><s:property value="maxRegNo"/></b></font>--%></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>姓名 <font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.trueName" id="name" value="<s:property value="bean.trueName"/>"  /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>证件类型 <font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" name="cardType">
				   				<s:select list="#{'身份证':'身份证','军官证':'军官证','护照':'护照','港、澳、台居民证件':'港、澳、台居民证件'}" value="%{bean.prStudentInfo.cardType}" theme="simple" name="bean.prStudentInfo.cardType" id="cardType"></s:select>
							</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>证件号码 <font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<input type="text" size="30" name="bean.prStudentInfo.cardNo" id="cardno" onblur="checkIdCard()" value='<s:property value="bean.prStudentInfo.cardNo"/>' />
			   				<input type="hidden"  id="cardno1"  value='<s:property value="bean.prStudentInfo.cardNo"/>' />
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>性别</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   					<s:select list="#{'男':'男','女':'女'}" value="%{bean.prStudentInfo.gender}" theme="simple" name="bean.prStudentInfo.gender"></s:select>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>出生日期</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id = "showbirthday">
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>政治面貌</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-zzmmData" size="30" maxlength="20" name="bean.prStudentInfo.zzmm" value="<s:property value="bean.prStudentInfo.zzmm"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>民族</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-forkData" size="30" name="bean.prStudentInfo.fork" value="<s:property value="bean.prStudentInfo.fork"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>婚姻状态</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<s:select list="#{'已婚':'已婚','未婚':'未婚','离异':'离异'}" value="%{bean.prStudentInfo.marriage}" theme="simple" name="bean.prStudentInfo.marriage" id="marriage"></s:select>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>生源地</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<table><tr><td>
			   					<input type="text" size="10" id="combo-box-provinceData" name="bean.prStudentInfo.province" value="<s:property value="bean.prStudentInfo.province"/>" />
			   				</td><td>
			   					省<input type="text" size="10" name="bean.prStudentInfo.city" value="<s:property value="bean.prStudentInfo.city"/>" />市
			   				</td></tr></table>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>通信地址</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.address" value="<s:property value="bean.prStudentInfo.address"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>邮政编码</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.zip" id="zip" onblur="isAllNum(this)" value="<s:property value="bean.prStudentInfo.zip"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>联系电话</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.phone" onblur="isAllNum(this)" value="<s:property value="bean.getPrStudentInfo().getPhone()"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>移动电话 <font color="#FF0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.mobilephone" id="mobilephone" value="<s:property value="bean.getPrStudentInfo().getMobilephone()"/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>电子邮件</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.email" id="email" onfocus="checkBirthday()" onblur="checkEmail()" value="<s:property value='bean.prStudentInfo.email'/>" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>职业</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.occupation" value="<s:property value="bean.prStudentInfo.occupation" />" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>工作单位</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.workplace" value="<s:property value="bean.prStudentInfo.workplace" />" /></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>文化程度</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   					<s:select list="#{'小学':'小学','初中':'初中','高中':'高中','中专':'中专','职高':'职高','高职':'高职','大专':'大专','本科':'本科','硕士':'硕士','博士':'博士'}" value="%{bean.prStudentInfo.xueli}" theme="simple" name="bean.prStudentInfo.xueli" id="edu"></s:select>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业学校</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.graduateSchool" value="<s:property value="bean.prStudentInfo.graduateSchool"/>" /></div>
			   			</td>
			   		</tr>
			   	<!-- 	<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业学校代码</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.graduateSchoolCode" value="<s:property value="bean.prStudentInfo.graduateSchoolCode"/>" /></div>
			   			</td>
			   		</tr>  -->
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业时间</label></span></div>
			   			</td>
			   			<td>
			   			<!-- <div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.graduateYear" value="<s:property value="bean.prStudentInfo.graduateYear"/>" /></div>
			   			 --><div align="left" class="postFormBox" id="graduateYear" ></div>
			   			
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>原毕业证书号</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" size="30" name="bean.prStudentInfo.graduateCode" value="<s:property value="bean.prStudentInfo.graduateCode"/>" /></div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">
			   				<s:if test='readonly==null||readonly.equals("false")'><input type="submit" name="Submit" value="提交"/></s:if>			
			   				<input type="button" name="close" value="关闭" onclick="javascript:window.close();"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>