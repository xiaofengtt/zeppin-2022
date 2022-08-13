<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*;" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>报名信息录入</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
	<link href="/web/registration/registration.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script type="text/javascript" src="/js/check.js"></script> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script> 
	function sub(){  
	if(userAddGuarding()){
   document.forms[0].submit();   
   }
   }
   
  	function res(){
	document.forms[0].reset();
	
	}
   </script> 
<script>
		Ext.onReady(function(){
		
//生成学习中心下拉列表
			var siteDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getRecsites.action'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'sites'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    siteDataStore.setDefaultSort('id', 'asc');
		    siteDataStore.load();
			
			var siteCombo = new Ext.form.ComboBox({
				store: siteDataStore,
				displayField:'name',
				valueField: 'id' ,
				id:'sitename',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择学习中心...',
				applyTo: 'combo-box-sites',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
			//生成层次下拉列表（与学习中心级联显示）
			 var edutypeDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getRecEdutypes.action'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'edutypes'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    edutypeDataStore.setDefaultSort('id', 'asc');
		    
		    var edutypeCombo = new Ext.form.ComboBox({
				//el:'combo-box-grades',
				store: edutypeDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择层次...',
				applyTo: 'combo-box-edutypes',
				editable: false,
				forceSelection: true,	//True to restrict the selected value to one of the values in the list, false to allow the user to set arbitrary text input
				selectOnFocus:true
			});
			
			//学习中心和层次进行级联
			siteCombo.on('select', function() {
				edutypeCombo.reset();
				Ext.apply(edutypeDataStore.baseParams, {
			   		requestsiteid:siteCombo.getValue()
			  	});
				edutypeDataStore.load();
			}); 
				//学习中心和层次进行级联
			objexpertName.on('blur', function() {
					unitCombo.reset();
				 Ext.apply(unitDataStore.baseParams, {
			   		requestExpertName:objexpertName.getValue()
			  	});
				unitDataStore.load();
				provinceCombo.reset();
				Ext.apply(provinceDataStore.baseParams, {
			   		requestExpertName:objexpertName.getValue()
			  	});
				provinceDataStore.load();
			}); 
			//生成专业下拉列表（与学习中心、层次级联显示）
			 var majorDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getRecMajors.action'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'majors'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    majorDataStore.setDefaultSort('id', 'asc');
		    
		    var majorCombo = new Ext.form.ComboBox({
				//el:'combo-box-grades',
				store: majorDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择专业...',
				applyTo: 'combo-box-majors',
				editable: false,
				forceSelection: true,	//True to restrict the selected value to one of the values in the list, false to allow the user to set arbitrary text input
				selectOnFocus:true
			});
		    
		    //学习中心、层次和专业进行级联
			edutypeCombo.on('select', function() {
				majorCombo.reset();
				Ext.apply(majorDataStore.baseParams, {
			   		requestsiteid:siteCombo.getValue(),
			   		requestedutypeid:edutypeCombo.getValue()
			  	});
				majorDataStore.load();
			}); 
					var birthdayDate = new Ext.form.DateField({
			        fieldLabel: '出生日期',           
			        name: 'student.birthday',
			        id:'birthday',
			        format:'Y-m-d',
			        allowBlank:false,
			        editable: false,
			        anchor: '29%'   
			    }); 
			    
			birthdayDate.render('showbirthday'); 	
			     birthdayDate.addListener('focus',function(){
			   		document.getElementById("baseInfor1").style.background="#D2D2FF";
					document.getElementById("birthdayInfo").innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的出生日期</div>';
			   }); 
			   birthdayDate.addListener('blur',function(){
			   		document.getElementById("baseInfor1").style.background="#FFFFFF";
					document.getElementById("birthdayInfo").innerHTML='';
			   });    					
			});
</script>
  </head>
  
  <body>
  <form name="print" method="POST" action="/entity/recruit/addPeRecStudentSingle_addStudent.action" onsubmit='return userAddGuarding();' enctype="multipart/form-data">
      <table width="1002" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td width="1002"  height="100%" valign="top" align="center">
	<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0px 3px 0px;">
      <tr>
        <td><table width="980" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/04.jpg">
          <tr>
            <td width="104"><img src="/web/registration/images/baomin1_03.jpg" width="104" height="29" /></td>
            <td width="798" align="center">
			<table width="240" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:5px;">
              <tr>
                <td bgcolor="#FFFBE5"  class="redtitle">学  生  基  本  信  息</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" align="center" style="padding-bottom:5px;">
		<table width="96%" height="100%" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px; border:1px solid #E1E1E1;">
          <tr>
            <td valign="top">
			<table id="baseInfor1" width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;">
              
              <tr>
                <td height="198" align="center"> 
				<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">基本信息</td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 姓名：</td>
                        <td width="43%" align="left"><input name="student.name" id="name" type="text" class="textreg" onFocus="foName()" onBlur="blName()"/></td>
                        <td width="34%" id="nameInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 民族：</td>
                        <td width="43%" align="left"><select name="student.folk" id="folk" class="textlong" onFocus="foFolk()" onBlur="blFolk()">
                <OPTION value = "">请选择...</OPTION>
                
                <OPTION VALUE="汉族">汉族</OPTION>
                
                <OPTION VALUE="蒙古族">蒙古族</OPTION>
                
                <OPTION VALUE="回族">回族</OPTION>
                
                <OPTION VALUE="藏族">藏族</OPTION>
                
                <OPTION VALUE="维吾尔族">维吾尔族</OPTION>
                
                <OPTION VALUE="苗族">苗族</OPTION>
                
                <OPTION VALUE="彝族">彝族</OPTION>
                
                <OPTION VALUE="壮族">壮族</OPTION>
                
                <OPTION VALUE="布依族">布依族</OPTION>
                
                <OPTION VALUE="朝鲜族">朝鲜族</OPTION>
                
                <OPTION VALUE="满族">满族</OPTION>
                
                <OPTION VALUE="侗族">侗族</OPTION>
                
                <OPTION VALUE="瑶族">瑶族</OPTION>
                
                <OPTION VALUE="白族">白族</OPTION>
                
                <OPTION VALUE="土家族">土家族</OPTION>
                
                <OPTION VALUE="哈尼族">哈尼族</OPTION>
                
                <OPTION VALUE="哈萨克族">哈萨克族</OPTION>
                
                <OPTION VALUE="傣族">傣族</OPTION>
                
                <OPTION VALUE="黎族">黎族</OPTION>
                
                <OPTION VALUE="傈僳族">傈僳族</OPTION>
                
                <OPTION VALUE="佤族">佤族</OPTION>
                
                <OPTION VALUE="畲族">畲族</OPTION>
                
                <OPTION VALUE="高山族">高山族</OPTION>
                
                <OPTION VALUE="拉祜族">拉祜族</OPTION>
                
                <OPTION VALUE="水族">水族</OPTION>
                
                <OPTION VALUE="东乡族">东乡族</OPTION>
                
                <OPTION VALUE="纳西族">纳西族</OPTION>
                
                <OPTION VALUE="景颇族">景颇族</OPTION>
                
                <OPTION VALUE="柯尔克孜族">柯尔克孜族</OPTION>
                
                <OPTION VALUE="土族">土族</OPTION>
                
                <OPTION VALUE="达斡尔族">达斡尔族</OPTION>
                
                <OPTION VALUE="仫佬族">仫佬族</OPTION>
                
                <OPTION VALUE="羌族">羌族</OPTION>
                
                <OPTION VALUE="布朗族">布朗族</OPTION>
                
                <OPTION VALUE="撒拉族">撒拉族</OPTION>
                
                <OPTION VALUE="毛难族">毛难族</OPTION>
                
                <OPTION VALUE="仡佬族">仡佬族</OPTION>
                
                <OPTION VALUE="锡伯族">锡伯族</OPTION>
                
                <OPTION VALUE="阿昌族">阿昌族</OPTION>
                
                <OPTION VALUE="普米族">普米族</OPTION>
                
                <OPTION VALUE="塔吉克族">塔吉克族</OPTION>
                
                <OPTION VALUE="怒族">怒族</OPTION>
                
                <OPTION VALUE="乌孜别克族">乌孜别克族</OPTION>
                
                <OPTION VALUE="俄罗斯族">俄罗斯族</OPTION>
                
                <OPTION VALUE="鄂温克族">鄂温克族</OPTION>
                
                <OPTION VALUE="崩龙族">崩龙族</OPTION>
                
                <OPTION VALUE="保安族">保安族</OPTION>
                
                <OPTION VALUE="裕固族">裕固族</OPTION>
                
                <OPTION VALUE="京族">京族</OPTION>
                
                <OPTION VALUE="塔塔尔族">塔塔尔族</OPTION>
                
                <OPTION VALUE="独龙族">独龙族</OPTION>
                
                <OPTION VALUE="鄂伦春族">鄂伦春族</OPTION>
                
                <OPTION VALUE="赫哲族">赫哲族</OPTION>
                
                <OPTION VALUE="门巴族">门巴族</OPTION>
                
                <OPTION VALUE="珞巴族">珞巴族</OPTION>
                
                <OPTION VALUE="基诺族">基诺族</OPTION>
                
                <OPTION VALUE="其他">其他</OPTION>
                
                <OPTION VALUE="外国血统">外国血统</OPTION>                        
                        </select></td>
                        <td width="34%"  id="folkInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 出生地：</td>
                        <td width="43%" align="left">
            <select name="student.province"  id="province"  onFocus="foProvince()" onBlur="blProvince()">
             <OPTION  value = "">请选择</OPTION>
                
                
                <OPTION VALUE="北京">北京</OPTION>
                
                <OPTION VALUE="天津">天津</OPTION>
                
                <OPTION VALUE="河北">河北</OPTION>
                
                <OPTION VALUE="山西">山西</OPTION>
                
                <OPTION VALUE="内蒙古">内蒙古</OPTION>
                
                <OPTION VALUE="辽宁">辽宁</OPTION>
                
                <OPTION VALUE="吉林">吉林</OPTION>
                
                <OPTION VALUE="黑龙江">黑龙江</OPTION>
                
                <OPTION VALUE="上海">上海</OPTION>
                
                <OPTION VALUE="江苏">江苏</OPTION>
                
                <OPTION VALUE="浙江">浙江</OPTION>
                
                <OPTION VALUE="安徽">安徽</OPTION>
                
                <OPTION VALUE="福建省">福建</OPTION>
                
                <OPTION VALUE="江西">江西</OPTION>
                
                <OPTION VALUE="山东">山东</OPTION>
                
                <OPTION VALUE="河南">河南</OPTION>
                
                <OPTION VALUE="湖北">湖北</OPTION>
                
                <OPTION VALUE="湖南">湖南</OPTION>
                
                <OPTION VALUE="广东">广东</OPTION>
                
                <OPTION VALUE="广西">广西</OPTION>
                
                <OPTION VALUE="海南">海南</OPTION>
                
                <OPTION VALUE="重庆">重庆</OPTION>
                
                <OPTION VALUE="四川">四川</OPTION>
                
                <OPTION VALUE="贵州">贵州</OPTION>
                
                <OPTION VALUE="云南">云南</OPTION>
                
                <OPTION VALUE="西藏">西藏</OPTION>
                
                <OPTION VALUE="陕西">陕西</OPTION>
                
                <OPTION VALUE="甘肃">甘肃</OPTION>
                
                <OPTION VALUE="青海">青海</OPTION>
                
                <OPTION VALUE="宁夏">宁夏</OPTION>
                
                <OPTION VALUE="新疆">新疆</OPTION>
                
                <OPTION VALUE="台湾">台湾</OPTION>
                
                <OPTION VALUE="香港">香港</OPTION>
                
				  <OPTION VALUE="澳门">澳门</OPTION> 
				  
                <OPTION VALUE="其它">其它</OPTION>
        </select> <span class="grayfont1">省</span>
		<input type="text" name="student.city" size="5"   onFocus="foProvince()" onBlur="blProvince()"/> <span class="grayfont1">市</span></td>
                        <td width="34%"  id="provinceInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 政治面貌：</td>
                        <td width="43%" align="left"><select name="student.zzmm" class="textlong" id="zzmm"  onFocus="fozzmm()" onBlur="blzzmm()">
                 <OPTION value = "">请选择...</OPTION>
                
                <OPTION VALUE="中共党员">中共党员</OPTION>
                
                <OPTION VALUE="中共预备党员">中共预备党员</OPTION>
                
                <OPTION VALUE="共青团员">共青团员</OPTION>
                
                <OPTION VALUE="民革会员">民革会员</OPTION>
                
                <OPTION VALUE="民盟盟员">民盟盟员</OPTION>
                
                <OPTION VALUE="民建会员">民建会员</OPTION>
                
                <OPTION VALUE="民进会员">民进会员</OPTION>
                
                <OPTION VALUE="农工党党员">农工党党员</OPTION>
                
                <OPTION VALUE="致公党党员">致公党党员</OPTION>
                
                <OPTION VALUE="九三学社社员">九三学社社员</OPTION>
                
                <OPTION VALUE="台盟盟员">台盟盟员</OPTION>
                
                <OPTION VALUE="无党派民主人士">无党派民主人士</OPTION>
                
                <OPTION VALUE="群众">群众</OPTION>                       
                        </select></td>
                        <td width="34%" id="zzmmInfor">&nbsp;</td>
                      </tr>
  <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 证件类型：</td>
                        <td width="43%" align="left"><select name="student.cardType" class="textlong" id="cardType"   

onFocus="foCardType()" onBlur="blCardType()">
                <OPTION value = "">请选择...</OPTION>
                
                <OPTION VALUE="身份证">身份证</OPTION>
                
                <OPTION VALUE="军官证">军官证</OPTION>
                
                <OPTION VALUE="护照">护照</OPTION>
                
                <OPTION VALUE="港、澳、台居民证件">港、澳、台居民证件</OPTION>                        
                        </select> </td>
                        <td width="34%" id="cardTypeInfor"  >&nbsp;</td>
                      </tr>
					  <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 证件号码：</td>
                        <td width="43%" align="left"><input name="student.cardNo" id="cardno" type="text" class="textreg" onFocus="foCardNo()" onBlur="blCardNo()" /></td>
                        <td width="34%" id="cardNoInfor">&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 性别：</td>
                        <td width="43%" align="left"><label>
                          <input type="radio" name="student.gender" checked="checked" value="男"  onFocus="foGender()" onBlur="blGender()" />
                          <span class="grayfont1">男</span>
                          <input type="radio" name="student.gender" value="女"   onFocus="foGender()" onBlur="blGender()" />
                        </label>
                          <span class="grayfont1">女 <span  >&nbsp;</span></span></td>
                        <td width="34%"  id="genderInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 户口：</td>
                        <td width="43%" align="left"><input name="student.register" type="text" class="textreg" id="register" onFocus="foRegister()" onBlur="blRegister()"  /></td>
                        <td width="34%" id="registerInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 出生日期：</td>
                        <td width="43%" align="left">
	                     <div align="left"  id = "showbirthday" >			   						</div>						</td>
                        <td width="34%" id="birthdayInfo">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 文化程度：</td>
                        <td width="43%" align="left"><select name="student.xueli" class="textlong" id="oldGraduatedEduType"  onFocus="foxueli()" onBlur="blxueli()">
                <OPTION value = "">请选择...</OPTION>
                
                <OPTION VALUE="小学">小学</OPTION>
                
                <OPTION VALUE="初中">初中</OPTION>
                
                <OPTION VALUE="高中">高中</OPTION>
                
                <OPTION VALUE="中专">中专</OPTION>
                
                <OPTION VALUE="职高">职高</OPTION>
                
                <OPTION VALUE="大专">大专</OPTION>
                
                <OPTION VALUE="高职">高职</OPTION>  
                
                <OPTION VALUE="本科">本科</OPTION>
                
                <OPTION VALUE="硕士">硕士</OPTION>
                
                <OPTION VALUE="博士">博士</OPTION>
                                   </select> <span  >&nbsp;</span></td>
                        <td width="34%"  id="xueliInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 婚姻状况：</td>
                        <td width="43%" align="left">
						<label>
                          <input type="radio" name="student.marriage" value="已婚"  onFocus="foMarriage()" 

onBlur="blMarriage()"/>
                          <span class="grayfont1">已婚</span>
                          <input type="radio" name="student.marriage" value="未婚" checked="checked" onFocus="foMarriage()" onBlur="blMarriage()"/>
                        </label><span class="grayfont1">未婚</span>						</td>
                        <td width="34%"  id="marriageInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24">照片：</td>
                        <td width="43%" align="left">
	                     <input type="file" name="upload" id="upload" size="20" onFocus="foUpload()" onBlur="blUpload()">						</td>
                        <td width="34%" id="uploadInfor">&nbsp;</td>
                      </tr>                      
                    </table></td>
                  </tr>
                </table>
				<!-------------- ----------------->
				
				<!--------- ------------------>				</td>
              </tr>
            </table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;" id="graduateInfor">
  <tr>
    <td align="center" >
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">毕业信息</td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 毕业院校：</td>
                        <td width="43%" align="left"><input name="student.graduateSchool" type="text" class="textreg" id="xueliSch"  onFocus="foGraduateSchool()" onBlur="blGraduateSchool()"/></td>
                        <td width="34%" id="graduateSchoolInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 就读专业：</td>
                        <td width="43%" align="left"><input name="student.graduateMajor" type="text" class="textreg" id="graduateMajor" onFocus="foGraduateMajor()" onBlur="blGraduateMajor()" /></td>
                        <td width="34%" id="graduateMajorInfor" >&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 毕业时间：</td>
                        <td width="43%" align="left"><select name="graduateYear" class="textshort" id="graduateYear" onFocus="foGraduateDate()" onBlur="blGraduateDate()" >
						 	<OPTION value = "">请选择...</OPTION>
    <option VALUE="1970">1970</option>

    <option VALUE="1971">1971</option>

    <option VALUE="1972">1972</option>

    <option VALUE="1973">1973</option>

    <option VALUE="1974">1974</option>

    <option VALUE="1975">1975</option>

    <option VALUE="1976">1976</option>

    <option VALUE="1977">1977</option>

    <option VALUE="1978">1978</option>

    <option VALUE="1979">1979</option>

    <option VALUE="1980">1980</option>

    <option VALUE="1981">1981</option>

    <option VALUE="1982">1982</option>

    <option VALUE="1983">1983</option>

    <option VALUE="1984">1984</option>

    <option VALUE="1985">1985</option>

    <option VALUE="1986">1986</option>

    <option VALUE="1987">1987</option>

    <option VALUE="1988">1988</option>

    <option VALUE="1989">1989</option>

    <option VALUE="1990">1990</option>

    <option VALUE="1991">1991</option>

    <option VALUE="1992">1992</option>

    <option VALUE="1993">1993</option>

    <option VALUE="1994">1994</option>

    <option VALUE="1995">1995</option>

    <option VALUE="1996">1996</option>

    <option VALUE="1997">1997</option>

    <option VALUE="1998">1998</option>

    <option VALUE="1999">1999</option>

    <option VALUE="2000">2000</option>
							<OPTION VALUE="2001">2001</OPTION>
							<OPTION VALUE="2002">2002</OPTION>
							<OPTION VALUE="2003">2003</OPTION>
							<OPTION VALUE="2004">2004</OPTION>
							<OPTION VALUE="2005">2005</OPTION>
							<OPTION VALUE="2006">2006</OPTION>
							<OPTION VALUE="2007">2007</OPTION>
							<OPTION VALUE="2008">2008</OPTION>
							<OPTION VALUE="2009">2009</OPTION>			
                        </select>
                        <span class="grayfont1">年</span>
						<select name="graduateMonth" class="textshort" id="graduateMonth" onFocus="foGraduateDate()" onBlur="blGraduateDate()" >
		    <OPTION value = "">请选择.</OPTION>
  
    <option VALUE="01">01</option>

    <option VALUE="02">02</option>

    <option VALUE="03">03</option>

    <option VALUE="04">04</option>

    <option VALUE="05">05</option>

    <option VALUE="06">06</option>

    <option VALUE="07">07</option>

    <option VALUE="08">08</option>

    <option VALUE="09">09</option>

    <option VALUE="10">10</option>

    <option VALUE="11">11</option>

    <option VALUE="12">12</option>
						</select> <span class="grayfont1">月</span>
                           <span  >&nbsp;</span></td>
                        <td width="34%" id="graduateDateInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 毕业证书编号：</td>
                        <td width="43%" align="left"><input name="student.graduateCode" type="text" class="textreg" id="xueliNo" onFocus="foGraduateCode()" onBlur="blGraduateCode()" /> </td>
                        <td width="34%" id="graduateCode" >&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
</table>
   <!------------------------------------>
<table width="100%" border="0" id="workInfor" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;">
  
  <tr>
    <td align="center">
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">职业信息 </td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 职业：</td>
                        <td width="43%" align="left"><select name="student.occupation" class="textlong" id="occupation" onFocus="foOccupation()" onBlur="blOccupation()" >
                <OPTION value = "">请选择...</OPTION>
                
                <OPTION VALUE="工人">工人</OPTION>
                
                <OPTION VALUE="公务员">公务员</OPTION>
                
                <OPTION VALUE="中小学教师">中小学教师</OPTION>
                
                <OPTION VALUE="大学教师">大学教师</OPTION>
                
                <OPTION VALUE="科研人员">科研人员</OPTION>
                
                <OPTION VALUE="技术人员">技术人员</OPTION>
                
                <OPTION VALUE="其它从业人员">其它从业人员</OPTION>
                
                <OPTION VALUE="待业青年">待业青年</OPTION>
                
                <OPTION VALUE="学生">学生</OPTION>
                
                <OPTION VALUE="其他人员">其他人员</OPTION> 
                        </select></td>
                        <td width="34%"  id="occupationInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24">工作单位：</td>
                        <td width="43%" align="left"><input name="student.workplace" type="text" class="textreg" onFocus="foWorkspace()" onBlur="blWorkspace()"/></td>
                        <td width="34%" id="workspaceInfor" >&nbsp;</td>
                      </tr>
                     <tr>
                        <td width="23%" class="grayfont" height="24">单位电话：</td>
                        <td width="43%" align="left"><input name="student.workplacePhone" type="text" class="textreg" id="workspacePhone" onFocus="foWorkspacePhone()" onBlur="blWorkspacePhone()"/></td>
                        <td width="34%" id="workspacePhoneInfor" >&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td >&nbsp;</td>
                      </tr>
                      
                      <tr>
                        <td width="23%" class="grayfont" height="24">何时参加工作：</td>

                        <td width="43%" align="left">
						<select name="workYear" class="textshort" id="workYear" onFocus="foWorkDate()" onBlur="blWorkDate()" >
						 	<OPTION value = "">请选择...</OPTION>
    <option VALUE="1970">1970</option>

    <option VALUE="1971">1971</option>

    <option VALUE="1972">1972</option>

    <option VALUE="1973">1973</option>

    <option VALUE="1974">1974</option>

    <option VALUE="1975">1975</option>

    <option VALUE="1976">1976</option>

    <option VALUE="1977">1977</option>

    <option VALUE="1978">1978</option>

    <option VALUE="1979">1979</option>

    <option VALUE="1980">1980</option>

    <option VALUE="1981">1981</option>

    <option VALUE="1982">1982</option>

    <option VALUE="1983">1983</option>

    <option VALUE="1984">1984</option>

    <option VALUE="1985">1985</option>

    <option VALUE="1986">1986</option>

    <option VALUE="1987">1987</option>

    <option VALUE="1988">1988</option>

    <option VALUE="1989">1989</option>

    <option VALUE="1990">1990</option>

    <option VALUE="1991">1991</option>

    <option VALUE="1992">1992</option>

    <option VALUE="1993">1993</option>

    <option VALUE="1994">1994</option>

    <option VALUE="1995">1995</option>

    <option VALUE="1996">1996</option>

    <option VALUE="1997">1997</option>

    <option VALUE="1998">1998</option>

    <option VALUE="1999">1999</option>

    <option VALUE="2000">2000</option>
							<OPTION VALUE="2001">2001</OPTION>
							<OPTION VALUE="2002">2002</OPTION>
							<OPTION VALUE="2003">2003</OPTION>
							<OPTION VALUE="2004">2004</OPTION>
							<OPTION VALUE="2005">2005</OPTION>
							<OPTION VALUE="2006">2006</OPTION>
							<OPTION VALUE="2007">2007</OPTION>
							<OPTION VALUE="2008">2008</OPTION>
							<OPTION VALUE="2009">2009</OPTION>			
                      				
						</select> <span class="grayfont1">年</span>
						<select name="workMonth" class="textshort" id="workMonth" onFocus="foWorkDate()" onBlur="blWorkDate()">
		    <OPTION value = "">请选择.</OPTION>
  
    <option VALUE="01">01</option>

    <option VALUE="02">02</option>

    <option VALUE="03">03</option>

    <option VALUE="04">04</option>

    <option VALUE="05">05</option>

    <option VALUE="06">06</option>

    <option VALUE="07">07</option>

    <option VALUE="08">08</option>

    <option VALUE="09">09</option>

    <option VALUE="10">10</option>

    <option VALUE="11">11</option>

    <option VALUE="12">12</option>					
						</select> <span class="grayfont1">月</span></td>
                        <td width="34%"  id="workDateInfor">&nbsp;</td>
                      </tr>
					  <tr>
                        <td width="23%" class="grayfont" height="24">单位邮编：</td>
                        <td width="43%" align="left"><input name="student.workplaceZip" type="text" class="textreg" maxlength="6" id="workspaceZip" onFocus="foWorkspaceZip()" onBlur="blWorkspaceZip()" /></td>
                        <td width="34%" id="workspaceZipInfor" >&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
</table>
<table width="100%" border="0" id="contactInfor" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;">
  
  <tr>
    <td align="center">
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">联系方式 </td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 联系地址：</td>
                        <td width="43%" align="left"><input name="student.address" type="text" id="address" class="textreg"  onFocus="foAddress()" onBlur="blAddress()"  /></td>
                        <td width="34%" id="addressInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 手机：</td>
                        <td width="43%" align="left"><input name="student.mobilephone" type="text" class="textreg" id="mobilephone" onFocus="foMobilephone()" onBlur="blMobilephone()" /></td>
                        <td width="34%" id="mobilephoneInfor" >&nbsp;</td>
                      </tr>
                     <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> Email：</td>
                        <td width="42%" align="left"><input name="student.email" type="text" id="email" class="textreg"  onFocus="foEmail()" onBlur="blEmail()" /></td>
                        <td width="35%" id="emailInfor">&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                       <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 邮政编码：</td>
                        <td width="42%" align="left"><input name="student.zip" id="zip" type="text" class="textreg" maxlength="6" onFocus="fozip()" onBlur="blzip()" /></td>
                        <td width="35%" id="zipInfor" >&nbsp;</td>
                      </tr>
                      
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 联系电话：</td>

                        <td width="42%" align="left">
						<input name="student.phone" type="text" class="textreg" id="phone" onFocus="foPhone()" onBlur="blPhone()"  /></td>
                        <td width="35%" id="phoneInfor"  >&nbsp;</td>
                      </tr>
					  <tr>
                        <td width="23%" class="grayfont" height="24">&nbsp;</td>
                        <td width="42%" align="left">&nbsp;</td>
                        <td width="35%"  >&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
</table>
<table width="100%" id="applyInfor" border="0" cellspacing="0" cellpadding="0" style=" margin-bottom:5px;">
  
  <tr>
    <td align="center">
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">学习申请 </td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 学习中心：</td>
                        <td width="70%" align="left"><input type="text" id="combo-box-sites" name="site" size="52"  onFocus="foSite()" onBlur="blSite()"/>                        </td>
                        <td width="7%"  >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 所报层次：</td>
                        <td width="70%" align="left"> <input type="text" id="combo-box-edutypes" name="edutype" size="52"  onFocus="foEdutype()" onBlur="blEdutype()"/></td>
                       <td width="7%"  >&nbsp;</td>
                      </tr>
                     <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 所报专业：</td>
                        <td width="70%" align="left"><input type="text" id="combo-box-majors" name="major" size="52"  onFocus="foMajor()" onBlur="blMajor()"/></td>
                        <td width="7%"  >&nbsp;</td>
                      </tr>
					 <!--  <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 录取省份：</td>
                        <td width="70%" align="left"><select name="student.recProvince" class="textlonglong" id="recProvince"  onFocus="foRecProvince()" onBlur="blRecProvince()">
             <OPTION value = "">请选择省份...</OPTION>
                
                <OPTION VALUE="北京市">北京市</OPTION>
                
                <OPTION VALUE="天津市">天津市 </OPTION>
                
                <OPTION VALUE="河北省">河北省 </OPTION>
                
                <OPTION VALUE="山西省">山西省 </OPTION>
                
                <OPTION VALUE="内蒙古自治区">内蒙古自治区 </OPTION>
                
                <OPTION VALUE="辽宁省">辽宁省 </OPTION>
                
                <OPTION VALUE="吉林省">吉林省 </OPTION>
                
                <OPTION VALUE="黑龙江省">黑龙江省 </OPTION>
                
                <OPTION VALUE="上海市">上海市 </OPTION>
                
                <OPTION VALUE="江苏省">江苏省 </OPTION>
                
                <OPTION VALUE="浙江省">浙江省 </OPTION>
                
                <OPTION VALUE="安徽省">安徽省 </OPTION>
                
                <OPTION VALUE="福建省">福建省 </OPTION>
                
                <OPTION VALUE="江西省">江西省 </OPTION>
                
                <OPTION VALUE="山东省">山东省 </OPTION>
                
                <OPTION VALUE="河南省">河南省 </OPTION>
                
                <OPTION VALUE="湖北省">湖北省 </OPTION>
                
                <OPTION VALUE="湖南省">湖南省 </OPTION>
                
                <OPTION VALUE="广东省">广东省 </OPTION>
                
                <OPTION VALUE="广西壮族自治区">广西壮族自治区 </OPTION>
                
                <OPTION VALUE="海南省">海南省 </OPTION>
                
                <OPTION VALUE="重庆市">重庆市 </OPTION>
                
                <OPTION VALUE="四川省">四川省 </OPTION>
                
                <OPTION VALUE="贵州省">贵州省 </OPTION>
                
                <OPTION VALUE="云南省">云南省 </OPTION>
                
                <OPTION VALUE="西藏自治区">西藏自治区 </OPTION>
                
                <OPTION VALUE="陕西省">陕西省 </OPTION>
                
                <OPTION VALUE="甘肃省">甘肃省 </OPTION>
                
                <OPTION VALUE="青海省">青海省 </OPTION>
                
                <OPTION VALUE="宁夏回族自治区">宁夏回族自治区 </OPTION>
                
                <OPTION VALUE="新疆维吾尔自治区">新疆维吾尔自治区 </OPTION>
                
                <OPTION VALUE="台湾省">台湾省 </OPTION>
                
                <OPTION VALUE="香港特别行政区">香港特别行政区 </OPTION>
                
                <OPTION VALUE="其它">其它</OPTION>
                
                <OPTION VALUE="澳门特别行政区">澳门特别行政区</OPTION>                      
                        </select></td>
                        <td width="7%"  >&nbsp;</td>
                      </tr>  -->
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 是否为考试生：</td>
                        <td width="70%" align="left">
                         <select name="noexam" class="textlonglong" >
             					<OPTION value = "0">考试生</OPTION>
             					<OPTION value = "1">免试生</OPTION>  
             			</select>		           					
            			 </td>
                       <td width="7%"  >&nbsp;</td>
                      </tr>
                       <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 教师资格：</td>
                        <td width="70%" align="left"> 
                          <select name="teacher" class="textlonglong" >
             					<OPTION value = "0">无教师资格</OPTION>
             					<OPTION value = "1">有教师资格</OPTION>  
             					<OPTION value = "2">其他专业</OPTION>              					
             			</select></td>
                       <td width="7%"  >&nbsp;</td>
                      </tr>                           
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                       <tr>
                        <td width="50%" align="left" height="24" id="siteInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>
                      
                       <tr>
                        <td width="50%" align="left" height="24"  id="edutypeInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>
                       <tr>
                        <td width="50%" align="left" height="24" id="majorInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>
                       <tr>
                        <td width="50%" align="left" height="24" id="recProvinceInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>					  
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
</table></td>
          </tr>
        </table>
		<table width="96%" border="0" cellspacing="2" cellpadding="0">
          <tr>
            <td width="4%" class="orange" align="right">注：</td>
            <td width="96%">&nbsp;</td>
          </tr>
          <tr>
            <td align="right" valign="top" class="blackright">1.</td>
            <td class="blacktext">在网上报名后，请带齐相关材料(身份证或户口簿原件及复印件、毕业证原件及复印件、小一寸近期免冠彩色照片两张。)在报名截止日期前到所属地校外学习中心缴费，学习中心予以确认 。</td>
          </tr>
          <tr>
            <td align="right" class="blackright">2.</td>
            <td class="blacktext">逾期未缴费注册者，本报名信息自动作废。</td>
          </tr>
          <tr>
            <td align="right" class="blackright">3.</td>
            <td valign="top" class="blacktext">请按要求如实输入个人信息，并保证信息的准确性。输入后确认无误请按下"确认"键。</td>
          </tr>
          <tr>
             <td align="right" class="blackright">4.</td>
            <td class="blacktext">生僻字列表</td>
          </tr>
        </table>
		<table width="28%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="50%" id="finish_id"><img src="/web/registration/images/baomin1_07.jpg" width="103" height="28" border="0" style="cursor: hand"   onclick="sub();"  /></td>
            <td width="50%"><img src="/web/registration/images/baomin1_09.jpg" width="103" height="28" border="0" style="cursor: hand"  onclick="res();" /></td>
          </tr>
		  <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table>
    </table></td>
  </tr>

</table>

      <map name="Map" id="Map">
  <area shape="rect" coords="272,94,391,127" href="#" />
  <area shape="rect" coords="267,16,386,49" href="#" />
<area shape="rect" coords="73,94,194,128" href="#" />
<area shape="rect" coords="72,15,191,48" href="#" /><area shape="rect" coords="176,55,272,91" href="#" /></map>
 </form>
  </body>
</html>
