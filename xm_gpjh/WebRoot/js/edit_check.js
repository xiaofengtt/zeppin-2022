		//验证姓名，不能以空格开头和结尾
		function checkname(name){
				var myreg=/^([^\s()]|[^\s()][^()]*[^\s()])$/;
				if(!myreg.test(name))
				{
					return false;
				}
				else return true;
		
		}
		//验证手机格式
		function checkmobilephone(phone){
				var myreg=/^(((18[0-9]{1})|(13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
				if(!myreg.test(phone))
				{
					return false;
				}
				else return true;
		
		}
		//验证电话号码格式
		function checkphone(phone){
				var myreg=/(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
				if(!myreg.test(phone))
				{
					return false;
				}
				else return true;
		
		}		
		//验证邮箱格式
		function  checkemail(email){
		 		var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
				if(!pattern.test(email))
				{
					return false;
				}
				else return true;
		 }
/^[1-9]\d{5}$/

		//验证邮编格式
		function  checkzip(zip){
		 		var pattern= /^\d{6}$/;
				if(!pattern.test(zip))
				{
					return false;
				}
				else return true;
		 }
		//姓名部分
		function foName(){
			document.getElementById("baseInfor1").style.background="#D2D2FF";
			nameInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的真实姓名，中文姓名不要加空格</div>';
				  }
				  
		function blName(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		nameInfor.innerHTML='';
			var name=document.getElementById('name').value;
	   			if(name.length == 0){
		  		nameInfor.innerHTML='';
	   			}
	  			 else{
	  				 if(checkname(name)){
	  				 nameInfor.innerHTML='';
	  			 	}else{
		  			 nameInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名不能以空格开头和结尾、不能包含半角括号</div>'; 
	   				}
	   			}			
		}
		
		//姓名部分
		function foProvince(){
			document.getElementById("baseInfor1").style.background="#D2D2FF";
			provinceInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的出生地</div>';
				  }
				  
		function blProvince(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		provinceInfor.innerHTML='';
				
		}
		
		//身份证部分
       function foCardNo(){
		  	document.getElementById("baseInfor1").style.background="#D2D2FF";
	cardNoInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的证件号码</div>'; 
	   }
	   function blCardNo(){
	   document.getElementById("baseInfor1").style.background="#FFFFFF";
	   var idcard=document.getElementById('cardno').value;
	   if(idcard.length == 0){
		   cardNoInfor.innerHTML='';
		   finish_id.innerHTML='<img src="/web/registration/images/baomin1_07.jpg" width="103" height="28" border="0" style="cursor: hand"   onclick="sub();"  />';
	   }
	   else{
		   
		   var card_select =document.getElementById('cardType').value;
		   if(card_select=="身份证"){
			   if(checkIdCard()){
			   if(idcard==document.getElementById('stuCardId').value){
			   cardNoInfor.innerHTML='';
				finish_id.innerHTML='<img src="/web/registration/images/baomin1_07.jpg" width="103" height="28" border="0" style="cursor: hand"   onclick="sub();"  />';	   				    
			   }else{
			   cardNoisexist();
			   }
		   }
		   }
		   else{
			   if(idcard==document.getElementById('stuCardId').value){
			   cardNoInfor.innerHTML='';
				finish_id.innerHTML='<img src="/web/registration/images/baomin1_07.jpg" width="103" height="28" border="0" style="cursor: hand"   onclick="sub();"  />';	   				    
			   }else{
			   cardNoisexist();
			   }
		   
	   }
	   }
	   }
	   //民族
function foFolk(){
	document.getElementById("baseInfor1").style.background="#D2D2FF";
	folkInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的民族</div>';
				  }
				  
		function blFolk(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		folkInfor.innerHTML='';
		}	   
		
	//政治面貌	
function fozzmm(){
	document.getElementById("baseInfor1").style.background="#D2D2FF";
	zzmmInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的政治面貌</div>';
				  }
				  
		function blzzmm(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		zzmmInfor.innerHTML='';
		}	   
			//户口部分
			function foRegister(){
				document.getElementById("baseInfor1").style.background="#D2D2FF";
				registerInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的户口所在地</div>';
				  }
				  
		function blRegister(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		registerInfor.innerHTML='';
		}	   	   
		   		
		//文化程度	
		function foxueli(){
				document.getElementById("baseInfor1").style.background="#D2D2FF";
				xueliInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的文化程度</div>';
		 }
				  
		function blxueli(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		xueliInfor.innerHTML='';
		}	
		
		//婚姻状况	
		function foMarriage(){
				document.getElementById("baseInfor1").style.background="#D2D2FF";
				marriageInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的婚姻状况</div>';
		 }
				  
		function blMarriage(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		marriageInfor.innerHTML='';
		}		
		
		
		//性别	
		function foGender(){
				document.getElementById("baseInfor1").style.background="#D2D2FF";
				genderInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的性别</div>';
		 }
				  
		function blGender(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		genderInfor.innerHTML='';
		}	
		//证件类型	
		function foCardType(){
				document.getElementById("baseInfor1").style.background="#D2D2FF";
				cardTypeInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的证件类型</div>';
		 }
				  
		function blCardType(){
				document.getElementById("baseInfor1").style.background="#FFFFFF";
		cardTypeInfor.innerHTML='';
		}


		//毕业院校	
		function foGraduateSchool(){
				document.getElementById("graduateInfor").style.background="#D2D2FF";
				graduateSchoolInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的毕业院校</div>';
		 }
				  
		function blGraduateSchool(){
				document.getElementById("graduateInfor").style.background="#FFFFFF";
		  graduateSchoolInfor.innerHTML='';				
		
		}

		//就读专业	
		function foGraduateMajor(){
				document.getElementById("graduateInfor").style.background="#D2D2FF";
				graduateMajorInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您毕业的专业</div>';
		 }
				  
		function blGraduateMajor(){
				document.getElementById("graduateInfor").style.background="#FFFFFF";
		graduateMajorInfor.innerHTML='';
		checkMajorType();
		}

		//毕业时间	
		function foGraduateDate(){
				document.getElementById("graduateInfor").style.background="#D2D2FF";
				graduateDateInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的毕业年月</div>';
		 }
				  
		function blGraduateDate(){
				document.getElementById("graduateInfor").style.background="#FFFFFF";
		graduateDateInfor.innerHTML='';
		}

		//毕业证书编号
		function foGraduateCode(){
				document.getElementById("graduateInfor").style.background="#D2D2FF";
				graduateCode.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写毕业证书编号</div>';
		 }
				  
		function blGraduateCode(){
				document.getElementById("graduateInfor").style.background="#FFFFFF";
		graduateCode.innerHTML='';
		}

		//职业
		function foOccupation(){
				document.getElementById("workInfor").style.background="#D2D2FF";
				occupationInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的职业</div>';
		 }
				  
		function blOccupation(){
				document.getElementById("workInfor").style.background="#FFFFFF";
		occupationInfor.innerHTML='';
		}

		//工作单位
		function foWorkspace(){
				document.getElementById("workInfor").style.background="#D2D2FF";
				workspaceInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的工作单位</div>';
		 }
				  
		function blWorkspace(){
				document.getElementById("workInfor").style.background="#FFFFFF";
		workspaceInfor.innerHTML='';
		}
		//工作时间
		function foWorkDate(){
				document.getElementById("workInfor").style.background="#D2D2FF";
				workDateInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的工作年月</div>';
		 }
				  
		function blWorkDate(){
				document.getElementById("workInfor").style.background="#FFFFFF";
		workDateInfor.innerHTML='';
		}
		//工作单位电话
		function foWorkspacePhone(){
				document.getElementById("workInfor").style.background="#D2D2FF";
				workspacePhoneInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的单位电话</div>';
		 }
				  
		function blWorkspacePhone(){
				document.getElementById("workInfor").style.background="#FFFFFF";
		workspacePhoneInfor.innerHTML='';
			var workspacePhone=document.getElementById('workspacePhone').value;
	   			if(workspacePhone.length == 0){
		  		workspacePhoneInfor.innerHTML='';
	   			}
	  			 else{
	  				 if(checkphone(workspacePhone)){
	  				 workspacePhoneInfor.innerHTML='';
	  			 	}else{
		  			 workspacePhoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位电话格式不正确，请重新填写</div>'; 
	   				}
	   			}			
		}
		
		//工作单位邮编
		function foWorkspaceZip(){
				document.getElementById("workInfor").style.background="#D2D2FF";
				workspaceZipInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的单位邮编</div>';
		 }
				  
		function blWorkspaceZip(){
				document.getElementById("workInfor").style.background="#FFFFFF";
		workspaceZipInfor.innerHTML='';
			var zip=document.getElementById('workspaceZip').value;
	   			if(zip.length == 0){
		  		workspaceZipInfor.innerHTML='';
	   			}
	  			 else{
	  				 if(checkzip(zip)){
	  				 workspaceZipInfor.innerHTML='';
	  			 	}else{
		  			 workspaceZipInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位邮编格式不正确，请重新填写</div>'; 
	   				}
	   			}			
		}



		//联系地址
		function foAddress(){
				document.getElementById("contactInfor").style.background="#D2D2FF";
				addressInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的联系地址</div>';
		 }
				  
		function blAddress(){
				document.getElementById("contactInfor").style.background="#FFFFFF";		
		  			addressInfor.innerHTML='';			
		}

		//手机
		function foMobilephone(){
				document.getElementById("contactInfor").style.background="#D2D2FF";
				mobilephoneInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的手机号码</div>';
		 }
				  
		function blMobilephone(){
				document.getElementById("contactInfor").style.background="#FFFFFF";
		
				var mobilephone=document.getElementById('mobilephone').value;
	   			if(mobilephone.length == 0){
		  			mobilephoneInfor.innerHTML='';
	   			}
	  			 else{
	  			 if(checkmobilephone(mobilephone))
	  			 {
	  			 	mobilephoneInfor.innerHTML='';
	  			 }else{
		  			 mobilephoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号码格式不正确，请填写正确的手机号码</div>'; 
	   				}
	   			}				
				
		}

		//联系电话
		function foPhone(){
				document.getElementById("contactInfor").style.background="#D2D2FF";
				phoneInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的联系电话</div>';
		 }
				  
		function blPhone(){
				document.getElementById("contactInfor").style.background="#FFFFFF";
		
				var phone=document.getElementById('phone').value;
	   			if(phone.length == 0){
		  			phoneInfor.innerHTML='';
	   			}
	  			 else{
	  			 if(checkphone(phone))
	  			 {
	  			 	phoneInfor.innerHTML='';
	  			 }else{
		  			 phoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话号码格式不正确，请填写正确的电话号码</div>'; 
	   				}
	   			}				
				
		}


		//Email
		function foEmail(){
				document.getElementById("contactInfor").style.background="#D2D2FF";
				emailInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的邮箱</div>';
		 }
				  
		function blEmail(){
				document.getElementById("contactInfor").style.background="#FFFFFF";
				var email=document.getElementById('email').value;
	   			if(email.length == 0){
		  			emailInfor.innerHTML='';
	   			}
	  			 else{
	  				 if(checkemail(email)){
	  				 emailInfor.innerHTML='';
	  			 	}else{
		  			 emailInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱格式不正确，请重新填写</div>'; 
	   				}
	   			}				
						
		}

		//邮编
		function fozip(){
				document.getElementById("contactInfor").style.background="#D2D2FF";
				zipInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写您的邮政编码</div>';
		 }
				  
		function blzip(){
				document.getElementById("contactInfor").style.background="#FFFFFF";
				var zip=document.getElementById('zip').value;
	   			if(zip.length == 0){
		  			zipInfor.innerHTML='';
	   			}
	  			 else{
	  				 if(checkzip(zip)){
	  				 zipInfor.innerHTML='';
	  			 	}else{
		  			 zipInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮政编码格式不正确，请重新填写</div>'; 
	   				}
	   			}				
						
		}

		//学习中心
		function foSite(){
				document.getElementById("applyInfor").style.background="#D2D2FF";
				siteInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择所报的学习中心</div>';
		 }
				  
		function blSite(){
				document.getElementById("applyInfor").style.background="#FFFFFF";
		siteInfor.innerHTML='';
		}

		//层次
		function foEdutype(){
				document.getElementById("applyInfor").style.background="#D2D2FF";
				edutypeInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择所报层次</div>';
		 }
				  
		function blEdutype(){
				document.getElementById("applyInfor").style.background="#FFFFFF";
		edutypeInfor.innerHTML='';
		}

		//专业
		function foMajor(){
				document.getElementById("applyInfor").style.background="#D2D2FF";
				majorInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择所报专业</div>';
		 }
				  
		function blMajor(){
				document.getElementById("applyInfor").style.background="#FFFFFF";
		majorInfor.innerHTML='';
		checkMajorType();
		}
		
		function checkMajorType(){
			var major=document.getElementById('combo-box-majors').value;
			var oldMajor=document.getElementById('graduateMajor').value;
			var edutype=document.getElementById('combo-box-edutypes').value;	
			if(major!=null&&major.length >0&&oldMajor!=null&&oldMajor.length >0){
					if(edutype.indexOf('本')>0){
						
	 		var url="/entity/first/firstRegister_majorType.action?major=" + major + "&oldMajor=" + oldMajor;
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest( );
		       
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		        
		    }
		    req.open("Get",encodeURI(url),true);
		    req.onreadystatechange = getResult;
		    req.send(null);
			return true;
					}
				}
		}

		function getResult(){
			if(req.readyState == 4){
					if(req.status == 200){
						var textResult = req.responseText;
						if (textResult.indexOf('1')>=0){
							majorInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;跨专业！</div>';	
						}else{
						majorInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原专业！</div>';					
						}
					} 
				}
		}
		//录取省份
	/*	function foRecProvince(){
				document.getElementById("applyInfor").style.background="#D2D2FF";
				recProvinceInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择录取省份</div>';
		 }
				  
		function blRecProvince(){
				document.getElementById("applyInfor").style.background="#FFFFFF";
		recProvinceInfor.innerHTML='';
		} */
		
		function   checkIdCard(){ 
		
			var idcard=document.getElementById('cardno').value;
			
		      
			  var   area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}       
			  var   idcard,Y,JYM;     
			  var   S,M;     
			  var   idcard_array   =   new   Array();     
			  idcard_array   =   idcard.split("");     
			  //地区检验  
			
			  if(area[parseInt(idcard.substr(0,2))]==null)
			  {
			  	cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号码输入错误,请重新输入！</div>';
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
			  if(ereg.test(idcard))   return  true;     
			  else
			  {
			  	cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号码出生日期超出范围或含有非法字符！请重新输入！</div>'
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
			  if(M   ==   idcard_array[17])   return   true;   //检测ID的校验位     
			  else
			  {
			  	cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请重新输入！</div>';
			  	return false;
			  }     
			  }     
			  else
			  {
			  	cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号码出生日期超出范围或含有非法字符!请重新输入！</div>'
			  	return false;
			  }     
			  break;     
			  default: 
			  	cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号位数不对！请重新输入！</div>';
			  	return false;    
			  break;     
			  }     		 
		}
		//验证证件号码是否已存在
		function cardNoisexist(){
		var cardno=document.getElementById('cardno').value
			if(document.getElementById('cardType').value == '身份证'){
			var birDay = '';
			  if(cardno.length==15)//输入的身份证号是15位
				{
					birDay = cardno.substring(6,12);
					birDay = "19" + birDay;
					birDay = birDay.substring(0,4) + "-" + birDay.substring(4,6) + "-" + birDay.substr(6);
				}
				else//输入的身份证号是18位
				{
					birDay = cardno.substring(6,14);
					birDay = birDay.substring(0,4) + "-" + birDay.substring(4,6) + "-" + birDay.substr(6);	
				}
				document.getElementById('birthday').value = birDay;
			}
	 		var url="/entity/first/firstRegister_cardNoExist.action?student.cardNo=" + cardno;
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
						if (textResult.indexOf('1')>=0){
							cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所输入的证件号码已经存在，请重新输入！</div>';
							finish_id.innerHTML='';
							
						}else{
						cardNoInfor.innerHTML='<div class="checkInfor">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此证件号码可以注册！</div>';
							finish_id.innerHTML='<img src="/web/registration/images/baomin1_07.jpg" width="103" height="28" border="0" style="cursor: hand"   onclick="sub();"  />';
						
						}
					} 
				}
		}
		
		function checkBirthday()
		{
		
				var idCard=document.getElementById('cardno').value;
				var myBirthday=document.getElementById('birthday').value;
				var temStr="";
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
					birthdayInfo.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出生日期和身份证上的不同！请重新选择！</font></u>';
					return false;
				}
				return true;

		
			}
			
			function KeyPress(objTR) {
				//只允许录入数据字符 0-9 和小数点 
					//var objTR = element.document.activeElement;		
				var txtval=objTR.value;	
				var key = event.keyCode;
			
				if((key < 48||key > 57)&&key != 46)	{		
					event.keyCode = 0;
					alert("只能输入数字");
				} else {
					if(key == 46) {
						if(txtval.indexOf(".") != -1||txtval.length == 0)
							event.keyCode = 0;
					}
				}
			}
			
			function userAddGuarding(){
		
			var flag=true;
			var operateresult='';
				
			if(document.getElementById('name').value=='' || document.getElementById('name').value.trim() == ''){
				flag=false;
					nameInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名不能为空！</div>';
			}else {
	  			 if(!checkname(document.getElementById('name').value))
	  			 {
					 	flag=false;
		  			 nameInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名不能以空格开头和结尾、不能包含半角括号</div>'; 
	  			 }
			}
			
			if(document.getElementById('cardno').value=='' || document.getElementById('cardno').value.trim() == '' ){
				flag=false;
			cardNoInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证件号码不能为空！</div>'; 
			}else if(document.getElementById('cardType').value=="身份证"){
					if(!checkIdCard()){
							flag=false;
					}			
			}
			
			if(document.getElementById('cardType').value == ''){
				flag=false;
		cardTypeInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证件类型不能为空！</div>';
			}	
			
			if(document.getElementById('folk').value == ''){
				flag=false;
		folkInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;民族不能为空！</div>';
			}
			
			if(document.getElementById('province').value == ''){
				flag=false;
		provinceInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出生地不能为空！</div>';
			}
			
			if(document.getElementById('zzmm').value == ''){
				flag=false;
		zzmmInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;政治面貌不能为空！</div>';
			}	
			
			if(document.getElementById('register').value=='' || document.getElementById('register').value.trim() == ''){
				flag=false;
					registerInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户口不能为空！</div>';
			}
			
			if(document.getElementById('birthday').value == ''){
				flag=false;
		birthdayInfo.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出生日期不能为空！</div>';
			}
			else if(document.getElementById('cardType').value=="身份证"&&!document.getElementById('cardno').value==''){
				if(!checkBirthday()) flag=false;
			}
			
			if(document.getElementById('oldGraduatedEduType').value == ''){
				flag=false;
		xueliInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文化程度不能为空！</div>';
			}			
			
			
			if(document.getElementById('xueliSch').value=='' || document.getElementById('xueliSch').value.trim() == ''){
				flag=false;
					graduateSchoolInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;毕业院校不能为空！</div>';
			}
			
			if(document.getElementById('graduateMajor').value=='' || document.getElementById('graduateMajor').value.trim() == ''){
				flag=false;
					graduateMajorInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;就读专业不能为空！</div>';
			}			
			
			if(document.getElementById('graduateYear').value=='' || document.getElementById('graduateMonth').value == ''){
				flag=false;
					graduateDateInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;毕业时间不能为空！</div>';
			}

			if(document.getElementById('xueliNo').value=='' || document.getElementById('xueliNo').value.trim() == ''){
				flag=false;
					graduateCode.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;毕业证书编号不能为空！</div>';
			}	
			
			if(document.getElementById('occupation').value == ''){
				flag=false;
		occupationInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职业不能为空！</div>';
			}			

			var workspacePhone=document.getElementById('workspacePhone').value;
	   			if(!workspacePhone.length == 0){
		  			if(!checkphone(workspacePhone)){
						flag=false;
		  			 workspacePhoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位电话格式不正确，请重新填写</div>'; 
	  			 	}
	   			}
			
	   			if(!document.getElementById('workspaceZip').value == 0){
	 				if(!checkzip(document.getElementById('workspaceZip').value)){
					flag=false;	
		  			 workspaceZipInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位邮编格式不正确，请重新填写</div>'; 
	  			 	}
	   			}

			if(document.getElementById('address').value=='' || document.getElementById('address').value.trim() == ''){
				flag=false;
					addressInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系地址不能为空！</div>';
			}		  			 

			if(document.getElementById('mobilephone').value=='' || document.getElementById('mobilephone').value.trim() == ''){
			 	flag=false;
			 		mobilephoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机不能为空！</div>';
			}else{
	  			 if(!checkmobilephone(document.getElementById('mobilephone').value))
	  			 {
					 	flag=false;
		  			 mobilephoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号码格式不正确，请填写正确的手机号码</div>'; 
	  			 }
	   			}	

			if(document.getElementById('email').value=='' || document.getElementById('email').value.trim() == ''){
				flag=false;
					emailInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱不能为空！</div>';
			}else{
	  			 if(!checkemail(document.getElementById('email').value))
	  			 {
					 	flag=false;
		  			 emailInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱格式不正确，请重新填写</div>'; 
	  			 }
	   		}	

			if(document.getElementById('zip').value=='' || document.getElementById('zip').value.trim() == ''){
				flag=false;
					 zipInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮政编码不能为空！</div>';
			}else{
	  			 if(!checkzip(document.getElementById('zip').value))
	  			 {
					 	flag=false;
		  			 zipInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮政编码格式不正确，请重新填写</div>'; 
	  			 }
	   		}	

			if(document.getElementById('phone').value=='' || document.getElementById('phone').value.trim() == ''){
				flag=false;
					 phoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话不能为空！</div>';
			}else{
	  			 if(!checkphone(document.getElementById('phone').value))
	  			 {
					 	flag=false;
		  			 phoneInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话格式不正确，请填写正确的联系电话</div>'; 
	  			 }
	   		}	

			if(document.getElementById('combo-box-sites').value=='请选择学习中心...'){
				flag=false;
				siteInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必须选择学习中心！</div>';				
			}
			
			if(document.getElementById('combo-box-edutypes').value=='请选择层次...'){
				flag=false;
				edutypeInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必须选择报考类别！</div>';					
			}
			
			if(document.getElementById('combo-box-edutypes').value=='暂无层次可选'){
				flag=false;
				edutypeInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所选学习中心下暂无层次可选，请选择其他学习中心！</div>';					
			}
			
			if(document.getElementById('combo-box-majors').value=='请选择专业...'){
				flag=false;
				majorInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必须选择报考志愿！</div>';					
			}
			
			if(document.getElementById('combo-box-majors').value=='暂无专业可选'){
				flag=false;
				majorInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所选学习中心和层次下暂无专业可选，请重新选择！</div>';					
			}
			
		/*	if(document.getElementById('recProvince').value==''){
				flag=false;
				recProvinceInfor.innerHTML='<div class="checkError">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必须选择录取省份！</div>';					
			}			
			*/
			return flag;
		}