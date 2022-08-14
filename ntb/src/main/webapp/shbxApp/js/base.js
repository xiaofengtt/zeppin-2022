var windowHeight = $(window).height(),	//获得屏幕高度
	layerHeight = $(".layer").height(),
	base = new Base64();
var isIDnumber = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;//身份证号
var regularNumber = /^\d+(\.\d+)?$|\d+(\.)?$/;//声明只包含数字和小数点的正则
var realnameAuthFlag = false;//实名flag
$(function(){
	refresh();
	$(".layerDiv").height(windowHeight);
	$(".payBox").css("min-height",windowHeight);
	$("#code-box").height(windowHeight);
	$(".agreementBox").height(windowHeight);
})
function refresh(){
	var deviceWidth = document.documentElement.clientWidth;
	if(deviceWidth>750){
	document.documentElement.style.fontSize=750/7.5+"px";
	}else{
		document.documentElement.style.fontSize=deviceWidth/7.5+"px";
	}
}

window.onresize=function(){
	refresh();
}
//校验手机号
function checkPhone(str){
	var isPhone = /^1(3|4|5|6|7|8|9)\d{9}$/;//手机号正则式	
	if(isPhone.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//验证邮箱
function checkEmail(str){
	var isEmail = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/g;
	if(isEmail.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//关闭layerDiv
$(".cancel-hook").click(function(){
	$(".layerDiv").hide();
});
//缴费年份
var data1 = [
    {
      text: '2018',
      value: '2018'
    }, {
      text: '2019',
      value: '2019'
    },
    {
      text: '2020',
      value: '2020'
    },
    {
      text: '2021',
      value: '2021'
    },
    {
      text: '2022',
      value: '2022'
    },
    {
      text: '2023',
      value: '2023'
    }, {
      text: '2024',
      value: '2024'
    },
    {
      text: '2025',
      value: '2025'
    },
    {
      text: '2026',
      value: '2026'
    },
    {
      text: '2027',
      value: '2027'
    },
    {
      text: '2028',
      value: '2028'
    }
  ];
//缴费月份
  var data2 = [
    {
      text: '01',
      value: '01'
    }, {
      text: '02',
      value: '02'
    },
    {
      text: '03',
      value: '03'
    },
    {
      text: '04',
      value: '04'
    },{
      text: '05',
      value: '05'
    }, {
      text: '06',
      value: '06'
    },
    {
      text: '07',
      value: '07'
    },
    {
      text: '08',
      value: '08'
    },{
      text: '09',
      value: '09'
    }, {
      text: '10',
      value: '10'
    },
    {
      text: '11',
      value: '11'
    },
    {
      text: '12',
      value: '12'
    }
  ];
//户口性质
var data3 = [
    {
      text: '本市城镇职工',
      value: '1'
    }, {
      text: '本市农村劳动力',
      value: '2'
    },
    {
      text: '外埠城镇职工',
      value: '3'
    },
    {
      text: '外埠农村劳动力',
      value: '4'
    }
  ];
  //民族
  var data4 = [
    {
      text: '汉族',
      value: '汉族'
    }, {
      text: '回族',
      value: '回族'
    },
    {
      text: '满族',
      value: '满族'
    },
    {
      text: '阿昌族',
      value: '阿昌族'
    },
    {
      text: '白族',
      value: '白族'
    },
    {
      text: '保安族',
      value: '保安族'
    },{
      text: '布朗族',
      value: '布朗族'
    }, {
      text: '布依族',
      value: '布依族'
    },
    {
      text: '朝鲜族',
      value: '朝鲜族'
    },
    {
      text: '达斡尔族',
      value: '达斡尔族'
    },
    {
      text: '傣族',
      value: '傣族'
    },
    {
      text: '德昂族',
      value: '德昂族'
    },{
      text: '东乡族',
      value: '东乡族'
    }, {
      text: '侗族',
      value: '侗族'
    },
    {
      text: '独龙族',
      value: '独龙族'
    },
    {
      text: '俄罗斯族',
      value: '俄罗斯族'
    },
    {
      text: '鄂伦春族',
      value: '鄂伦春族'
    },
    {
      text: '鄂温克族',
      value: '鄂温克族'
    },{
      text: '高山族',
      value: '高山族'
    }, {
      text: '仡佬族',
      value: '仡佬族'
    },
    {
      text: '哈尼族',
      value: '哈尼族'
    },
    {
      text: '哈萨克族',
      value: '哈萨克族'
    },
    {
      text: '赫哲族',
      value: '赫哲族'
    },
    {
      text: '基诺族',
      value: '基诺族'
    },{
      text: '京族',
      value: '京族'
    }, {
      text: '景颇族',
      value: '景颇族'
    },
    {
      text: '柯尔克孜族',
      value: '柯尔克孜族'
    },
    {
      text: '拉祜族',
      value: '拉祜族'
    },
    {
      text: '黎族',
      value: '黎族'
    },
    {
      text: '傈僳族',
      value: '傈僳族'
    },{
      text: '珞巴族',
      value: '珞巴族'
    }, {
      text: '毛南族',
      value: '毛南族'
    },
    {
      text: '门巴族',
      value: '门巴族'
    },
    {
      text: '蒙古族',
      value: '蒙古族'
    },
    {
      text: '苗族',
      value: '苗族'
    },
    {
      text: '仫佬族',
      value: '仫佬族'
    },{
      text: '纳西族',
      value: '纳西族'
    }, {
      text: '怒族',
      value: '怒族'
    },
    {
      text: '普米族',
      value: '普米族'
    },
    {
      text: '羌族',
      value: '羌族'
    },
    {
      text: '撒拉族',
      value: '撒拉族'
    },
    {
      text: '畲族',
      value: '畲族'
    },{
      text: '水族',
      value: '水族'
    }, {
      text: '塔吉克族',
      value: '塔吉克族'
    },
    {
      text: '塔塔尔族',
      value: '塔塔尔族'
    },
    {
      text: '土家族',
      value: '土家族'
    },
    {
      text: '土族',
      value: '土族'
    },
    {
      text: '佤族',
      value: '佤族'
    },{
      text: '维吾尔族',
      value: '维吾尔族'
    }, {
      text: '乌孜别克族',
      value: '乌孜别克族'
    },
    {
      text: '锡伯族',
      value: '锡伯族'
    },
    {
      text: '瑶族',
      value: '瑶族'
    },
    {
      text: '彝族',
      value: '彝族'
    },
    {
      text: '裕固族',
      value: '裕固族'
    },
    {
      text: '藏族',
      value: '藏族'
    },
    {
      text: '壮族',
      value: '壮族'
    }
  ];
  //学历
  var data5 = [
    {
      text: '博士',
      value: '博士'
    },{
      text: '硕士',
      value: '硕士'
    }, {
      text: '大学',
      value: '大学'
    },
    {
      text: '大专',
      value: '大专'
    },
    {
      text: '中专',
      value: '中专'
    },
    {
      text: '技校',
      value: '技校'
    },
    {
      text: '高中',
      value: '高中'
    },
    {
      text: '职高',
      value: '职高'
	},
    {
      text: '初中',
      value: '初中'
    },
    {
      text: '小学',
      value: '小学'
    },
    {
      text: '文盲或半文盲',
      value: '文盲或半文盲'
    }
  ];
//个人身份
  var data8 = [
      {
        text: '工人',
        value: '工人'
      }, {
        text: '干部',
        value: '干部'
      }
    ];
//缴费日 28天
  var data9 = [
    {
      text: '01',
      value: '01'
    }, {
      text: '02',
      value: '02'
    },
    {
      text: '03',
      value: '03'
    },
    {
      text: '04',
      value: '04'
    },{
      text: '05',
      value: '05'
    }, {
      text: '06',
      value: '06'
    },
    {
      text: '07',
      value: '07'
    },
    {
      text: '08',
      value: '08'
    },{
      text: '09',
      value: '09'
    }, {
      text: '10',
      value: '10'
    },
    {
      text: '11',
      value: '11'
    },
    {
      text: '12',
      value: '12'
    },{
        text: '13',
        value: '13'
      }, {
        text: '14',
        value: '14'
      },
      {
        text: '15',
        value: '15'
      },
      {
        text: '16',
        value: '16'
      },{
        text: '17',
        value: '17'
      }, {
        text: '18',
        value: '18'
      },
      {
        text: '19',
        value: '19'
      },
      {
        text: '20',
        value: '20'
      },{
        text: '21',
        value: '21'
      }, {
        text: '22',
        value: '22'
      },
      {
        text: '23',
        value: '23'
      },
      {
        text: '24',
        value: '24'
      },{
    	 text: '25',
    	 value: '25'
      }, {
    	 text: '26',
    	 value: '26'
      },
      {
    	 text: '27',
    	 value: '27'
      },
      {
    	 text: '28',
    	 value: '28'
      }
  ];
//缴费日 29天
  var data10 = [
    {
      text: '01',
      value: '01'
    }, {
      text: '02',
      value: '02'
    },
    {
      text: '03',
      value: '03'
    },
    {
      text: '04',
      value: '04'
    },{
      text: '05',
      value: '05'
    }, {
      text: '06',
      value: '06'
    },
    {
      text: '07',
      value: '07'
    },
    {
      text: '08',
      value: '08'
    },{
      text: '09',
      value: '09'
    }, {
      text: '10',
      value: '10'
    },
    {
      text: '11',
      value: '11'
    },
    {
      text: '12',
      value: '12'
    },{
        text: '13',
        value: '13'
      }, {
        text: '14',
        value: '14'
      },
      {
        text: '15',
        value: '15'
      },
      {
        text: '16',
        value: '16'
      },{
        text: '17',
        value: '17'
      }, {
        text: '18',
        value: '18'
      },
      {
        text: '19',
        value: '19'
      },
      {
        text: '20',
        value: '20'
      },{
        text: '21',
        value: '21'
      }, {
        text: '22',
        value: '22'
      },
      {
        text: '23',
        value: '23'
      },
      {
        text: '24',
        value: '24'
      },{
    	 text: '25',
    	 value: '25'
      }, {
    	 text: '26',
    	 value: '26'
      },
      {
    	 text: '27',
    	 value: '27'
      },
      {
    	 text: '28',
    	 value: '28'
      },
      {
     	 text: '29',
     	 value: '29'
       }
  ]; 
//缴费日 30天
  var data11 = [
    {
      text: '01',
      value: '01'
    }, {
      text: '02',
      value: '02'
    },
    {
      text: '03',
      value: '03'
    },
    {
      text: '04',
      value: '04'
    },{
      text: '05',
      value: '05'
    }, {
      text: '06',
      value: '06'
    },
    {
      text: '07',
      value: '07'
    },
    {
      text: '08',
      value: '08'
    },{
      text: '09',
      value: '09'
    }, {
      text: '10',
      value: '10'
    },
    {
      text: '11',
      value: '11'
    },
    {
      text: '12',
      value: '12'
    },{
        text: '13',
        value: '13'
      }, {
        text: '14',
        value: '14'
      },
      {
        text: '15',
        value: '15'
      },
      {
        text: '16',
        value: '16'
      },{
        text: '17',
        value: '17'
      }, {
        text: '18',
        value: '18'
      },
      {
        text: '19',
        value: '19'
      },
      {
        text: '20',
        value: '20'
      },{
        text: '21',
        value: '21'
      }, {
        text: '22',
        value: '22'
      },
      {
        text: '23',
        value: '23'
      },
      {
        text: '24',
        value: '24'
      },{
    	 text: '25',
    	 value: '25'
      }, {
    	 text: '26',
    	 value: '26'
      },
      {
    	 text: '27',
    	 value: '27'
      },
      {
    	 text: '28',
    	 value: '28'
      },
      {
     	 text: '29',
     	 value: '29'
       },
       {
    	 text: '30',
   	     value: '30'
        }
  ]; 
//缴费日 31天
  var data12 = [
    {
      text: '01',
      value: '01'
    }, {
      text: '02',
      value: '02'
    },
    {
      text: '03',
      value: '03'
    },
    {
      text: '04',
      value: '04'
    },{
      text: '05',
      value: '05'
    }, {
      text: '06',
      value: '06'
    },
    {
      text: '07',
      value: '07'
    },
    {
      text: '08',
      value: '08'
    },{
      text: '09',
      value: '09'
    }, {
      text: '10',
      value: '10'
    },
    {
      text: '11',
      value: '11'
    },
    {
      text: '12',
      value: '12'
    },{
        text: '13',
        value: '13'
      }, {
        text: '14',
        value: '14'
      },
      {
        text: '15',
        value: '15'
      },
      {
        text: '16',
        value: '16'
      },{
        text: '17',
        value: '17'
      }, {
        text: '18',
        value: '18'
      },
      {
        text: '19',
        value: '19'
      },
      {
        text: '20',
        value: '20'
      },{
        text: '21',
        value: '21'
      }, {
        text: '22',
        value: '22'
      },
      {
        text: '23',
        value: '23'
      },
      {
        text: '24',
        value: '24'
      },{
    	 text: '25',
    	 value: '25'
      }, {
    	 text: '26',
    	 value: '26'
      },
      {
    	 text: '27',
    	 value: '27'
      },
      {
    	 text: '28',
    	 value: '28'
      },
      {
     	 text: '29',
     	 value: '29'
       },
       {
    	 text: '30',
   	     value: '30'
        },
        {
       	 text: '31',
  	     value: '31'
       }
  ]; 
//户口所在地 
var picker1El = document.getElementById('picker1');

var picker1 = new Picker({
	title: '请选择户口所在地',
    data: [data6, data7]
});

picker1.on('picker.select', function (selectedVal, selectedIndex) {
    $("#picker1").find("input").val(data6[selectedIndex].text + ' ' +data7[selectedIndex].text);
    changeInput();
    getHkArea(data6[selectedIndex[0]].value,data6[selectedIndex[0]].pid,"2");
});

picker1.on('picker.change', function (index, selectedIndex) {
    console.log(index);
});

picker1.on('picker.valuechange', function (selectedVal, selectedIndex) {
    console.log(selectedVal);
});

if(picker1El){
	picker1El.addEventListener('click', function () {
	    picker1.show();
	});
}

//户口性质
var picker2El = document.getElementById('picker2');
function getHkxz(data){
	var index = 0;
	if(data){
		for(var i=0;i<data3.length;i++){
			if(data3[i].text==data){
				index = i;
			}
		}
	}
	var picker2 = new Picker({
		title: '请选择户口性质',
	    data: [data3],
	    selectedIndex: [index]
	});
	
	picker2.on('picker.select', function (selectedVal, selectedIndex) {
	    $("#picker2").find("input").val(data3[selectedIndex[0]].text);
	    $("#hkxz").val(data3[selectedIndex[0]].value);
	    changeInput();
	});

	if(picker2El){
		picker2El.addEventListener('click', function () {
		    picker2.show();
		});
	}

}

//民族
var picker3El = document.getElementById('picker3');
function getMz(data){
	var index = 0;
	if(data){
		for(var i=0;i<data4.length;i++){
			if(data4[i].text==data){
				index = i;
			}
		}
	}
	var picker3 = new Picker({
		title: '请选择民族',
	    data: [data4],
	    selectedIndex: [index]
	});
	
	picker3.on('picker.select', function (selectedVal, selectedIndex) {
	    $("#picker3").find("input").val(data4[selectedIndex[0]].text);
	    changeInput();
	});
	
	if(picker3El){
		picker3El.addEventListener('click', function () {
		    picker3.show();
		});
	}
}

//学历
var picker4El = document.getElementById('picker4');
function getXl(data){
	var index = 0;
	if(data){
		for(var i=0;i<data5.length;i++){
			if(data5[i].text==data){
				index = i;
			}
		}
	}
	var picker4 = new Picker({
		title: '请选择学历',
	    data: [data5],
	    selectedIndex: [index]
	});

	picker4.on('picker.select', function (selectedVal, selectedIndex) {
	    $("#picker4").find("input").val(data5[selectedIndex[0]].text);
	    changeInput();
	});
	 
	if(picker4El){
		picker4El.addEventListener('click', function () {
		    picker4.show();
		});
	}
}


var data13=[],data17=[];//参加工作时间月份 记录今年月份之前的月份
var cjgzsjYearIndex = 0;//参加工作时间 年份index
var cjgzsjMonthIndex = 0;//参加工作时间 月份份index
//参加工作时间
var picker5El = document.getElementById('picker5');
function getCjgzsj(workTime){
	data1=[];
	var Year=new Date().getFullYear();
	var Month = new Date().getMonth();
	for(var i=1980;i<=Year;i++){
		var dataList ={};
    	dataList.text = i;
    	dataList.value = i;   	
    	data1.push(dataList); 
	}
	for(var i=0;i<=Month;i++){
		var dataList ={};
		dataList.text = ("0"+(i+1)).substring(("0"+(i+1)).length-2,("0"+(i+1)).length);
    	dataList.value = ("0"+(i+1)).substring(("0"+(i+1)).length-2,("0"+(i+1)).length);   	 	
    	data13.push(dataList); 
	}
	if(workTime){
		cjgzsjYearIndex = workTime.substring(0,4) - 1980;
		cjgzsjMonthIndex = workTime.substring(5,7) - 1;
		if(workTime.substring(0,4)==Year){
			data17 = data13;
		}else{
			data17 = data2;
		}
	}else{
		cjgzsjYearIndex = Year - 1980;
		cjgzsjMonthIndex = Month;
		data17 = data13;
	}
	
	
	
	var picker5 = new Picker({
		title: '请选择参加工作时间',
		selectedIndex: [cjgzsjYearIndex, cjgzsjMonthIndex],
	    data: [data1,data17]
	});
	
	picker5.on('picker.select', function (selectedVal, selectedIndex) {
		$("#picker5").find("input").val(data1[selectedIndex[0]].text+'-'+data17[selectedIndex[1]].text);
		$("#cjgzsj").val(data1[selectedIndex[0]].value+'-'+data17[selectedIndex[1]].value+'-01');
	});

	picker5.on('picker.change', function (index, selectedIndex) {
		if(index=="0"){
			if(Year != Number(data1[selectedIndex].value)){
				data17 = data2;
			}else{
				data17 = data13;
			}	
		}
			
		picker5.refillColumn('1', data17);	
	    changeInput();
	});
	 
	if(picker5El){
		picker5El.addEventListener('click', function () {
		    picker5.show();
		});
	}
}

var data14=[],data15=[],data16=[];
//缴费月份
var picker7El = document.getElementById('picker7');
function getJfyeData(){
	var Data = new Date();
	var Year = Data.getFullYear(); //获取完整的年份
	var Day = new Date().getDate();
	var Month = Data.getMonth(); //月份
	if(Day>15){
		Month = new Date().getMonth()+1;
	}
	
	for(var i=0;i<10;i++){
		var dataList ={};
    	dataList.text = Year+i;
    	dataList.value = Year+i;   	
    	data14.push(dataList); 
	}
	for(var i=Month;i<12;i++){
		var dataList ={};
    	dataList.text = ("0"+(i+1)).substring(("0"+(i+1)).length-2,("0"+(i+1)).length);
    	dataList.value = ("0"+(i+1)).substring(("0"+(i+1)).length-2,("0"+(i+1)).length);   	
    	data15.push(dataList); 
	}
	data16 = data15;
	var picker7 = new Picker({
		title: '请选择缴费月份',
	    data: [data14,data16]
	});

	picker7.on('picker.select', function (selectedVal, selectedIndex) {
	    $("#picker7").find("input").val(data14[selectedIndex[0]].text+'-'+data16[selectedIndex[1]].text);
	    $("#jfyf").val(data14[selectedIndex[0]].value+'-'+data16[selectedIndex[1]].value+'-01');
	    changeInput();
		jsFwfYf();
	});

	picker7.on('picker.change', function (index, selectedIndex) {
		if(index=="0"){
			var Year =new Date().getFullYear();
			if(data14[selectedIndex].value==Year){
				data16=data15;				
			}else{
				data16=data2;				
			}
			picker7.refillColumn('1', data16);
		}	
	});

	picker7.on('picker.valuechange', function (selectedVal, selectedIndex) {
	    console.log(selectedVal);
	});
	 
	if(picker7El){
		picker7El.addEventListener('click', function () {
		    picker7.show();
		});
	}
}

//个人身份
var picker8El = document.getElementById('picker8');
function getCbrsf(data){
	var index = 0;
	if(data){
		for(var i=0;i<data8.length;i++){
			if(data8[i].text==data){
				index = i;
			}
		}
	}
	var picker8 = new Picker({
		title: '请选择个人身份',
	    data: [data8],
	    selectedIndex: [index]
	});

	picker8.on('picker.select', function (selectedVal, selectedIndex) {
	    $("#picker8").find("input").val(data8[selectedIndex].text);
	});

	if(picker8El){
		picker8El.addEventListener('click', function () {
			picker8.show();
		});
	}

}

//计算数值
function numericValue(a1,a2){
	return Number((a1*a2).toFixed(2));
}
//计算数值
function numericValues(a1,a2){
	return (a1*a2).toFixed(2);
}
var data6 = [];//省份
var data7 = [];//市
//地区
function getHkArea(pid, scode,level,flag){
	$.ajax({
        url: '../rest/shbxWeb/area/list',
        type: 'get',
        data: {
            "level": level,
            "pid": pid,
            "scode": scode,
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime()
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$.each(r.data, function(index, el) {
                if (el.level == 1 && el.name == "全国") {

                } else {
                	var dataList ={};
                	dataList.text = el.name;
                	dataList.value = el.uuid;
                	dataList.pid = el.scode;
                	if(level == "1"){
                		data6.push(dataList);                		
//                		picker1.refillColumn(0, data6);
                	}else{
                		data7=[];
                		data7.push(dataList);
                		picker1 = new Picker({
                			title: '请选择户口所在地',
                		    data: [data6, data7]
                		});

                		              		
                	}
                }
            });
    		picker1.on('picker.change', function (index, selectedIndex) { 
    			if(index=="0"){
    				$.ajax({
        		        url: '../rest/shbxWeb/area/list',
        		        type: 'get',
        		        data: {
        		            "level": '2',
        		            "pid": data6[selectedIndex].value,
        		            "scode": data6[selectedIndex].pid,
        		            "pageNum": 1,
        		            "pageSize": 1000,
        		            "timestamp":new Date().getTime() 
        		        }
        		    }).done(function(r) {
        		    	if(r.status == "SUCCESS"){
    	            		data7=[];
    	    		    	$.each(r.data, function(index, el) {
    	    	                if (el.level == 1 && el.name == "全国") {
    	
    	    	                } else {
    	    	                	var dataList ={};
    	    	                	dataList.text = el.name;
    	    	                	dataList.value = el.uuid;
    	    	                	dataList.pid = el.scode;   	
    		                		data7.push(dataList);    	                		              		
    	    	                }
    	    	            });
    	    		    	picker1.refillColumn('1', data7);
        		    	}
        		    });
    			}
    		    
    		});  
    		if(flag){
    			getHkArea(data6[0].value,data6[0].pid,"2");
    		}
    		picker1.on('picker.select', function (selectedVal, selectedIndex) {
    		    $("#picker1").find("input").val(data6[selectedIndex[0]].text + ' ' + data7[selectedIndex[1]].text);
    		    $("#cbdq").val(data7[selectedIndex[1]].value)
    		    changeInput();
    		});
    		
    	}else{
    		layer.msg(r.message);
    	}
        
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}


//小黑框弹出
function layerIn(msg){
	if($(".layer").css("display") != "block"){
		$(".layer").html(msg);
		$('.layer').fadeIn(function(){
			setTimeout(function(){
				$(".layer").fadeOut();
			},1000);
		});
	}
}

//layer高度定位
$(".layer").css({
	"top":windowHeight/2,
	"margin-top":layerHeight * -4
});
//转菊花弹出框高度赋值
$("#loading-box").css({
	"height": windowHeight
});
//转菊花弹出／隐藏
function loadingIn(){
	$("#loading-box").show();
}

function loadingOut(){
	$("#loading-box").hide();
}
//获取时间戳函数
function getTime() {
	var time = "";
	$.ajax({
			type: "get",
			url: "../rest/shbxWeb/login/getTime",
			async: false
		})
		.done(function(r) {
			time = r.data;
		});
	return time;
}

//社保结果
function socialSecurityResult(cardinal,gjjWages,hkxz,gjj){
	var sqWages,ylWages,khxzbl,grossPay;
	grossPay = $("input.sbjs").val()?Number($("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,"")):cardinal;
	if(cardinal<5080){
		sqWages = 5080;
	}else if(cardinal>23118){
		sqWages = 23118;
	}else{
		sqWages = cardinal;
	}
	if(cardinal<3387){
		ylWages = 3387;
	}else if(cardinal>23118){
		ylWages = 23118;
	}else{
		ylWages = cardinal;
	}
	if(hkxz=="1"){
		khxzbl = 0.002;
	}else{
		khxzbl = 0;
	}
	if(gjjWages==""||gjj=="2"||gjjWages=="0.00"){
		gjjWages = 0.00;
	}else{
		if(gjjWages<2273){
			gjjWages = 2273;
		}else if(gjjWages>23118){
			gjjWages = 23118;
		}else{
			gjjWages = gjjWages;
		}
	}
	
	var grjn = (ylWages*0.08 + sqWages*0.02 + 3 + ylWages*khxzbl +gjjWages*0.12).toFixed(2);//个人合计
	var gsjn = (ylWages*0.19 + sqWages*0.1 + ylWages*0.008 + sqWages*0.004 + sqWages*0.008+gjjWages*0.12).toFixed(2);//公司合计
	//个人所得税
	var gsjs= grossPay-grjn-5000;//个税缴纳基数 不超过1500的3% 1500-4500 10% 4500-9000 20% 9000-35000 25% 35000-55000 30% 55000-80000 35% 超过80000的部分 45%
	var gsjsTax;
	if(gsjs>80000){
		gsjsTax = (gsjs-80000)*0.45+25000*0.35+20000*0.3+10000*0.25+13000*0.2+9000*0.1+3000*0.03;
	}else if(gsjs<=80000&&gsjs>55000){
		gsjsTax = (gsjs-55000)*0.35+20000*0.3+10000*0.25+13000*0.2+9000*0.1+3000*0.03;
	}else if(gsjs<=55000&&gsjs>35000){
		gsjsTax = (gsjs-35000)*0.3+10000*0.25+13000*0.2+9000*0.1+3000*0.03;
	}else if(gsjs<=35000&&gsjs>25000){
		gsjsTax = (gsjs-25000)*0.25+13000*0.2+9000*0.1+3000*0.03;
	}else if(gsjs<=25000&&gsjs>12000){
		gsjsTax = (gsjs-12000)*0.2+9000*0.1+3000*0.03;
	}else if(gsjs<=12000&&gsjs>3000){
		gsjsTax = (gsjs-3000)*0.1+3000*0.03;
	}else if(gsjs<=3000&&gsjs>0){
		gsjsTax = gsjs*0.03;
	}else{
		gsjsTax = 0;
	} 
	var shsr = (ylWages*0.08 + sqWages*0.02 + 3 + ylWages*khxzbl +gjjWages*0.12+ylWages*0.19 + sqWages*0.1 + ylWages*0.008 + sqWages*0.004 + sqWages*0.008+gjjWages*0.12+gsjsTax).toFixed(2);//合计缴纳
	var shbfy = (ylWages*0.08 + sqWages*0.02 + 3 + ylWages*khxzbl +ylWages*0.19 + sqWages*0.1 + ylWages*0.008 + sqWages*0.004 + sqWages*0.008).toFixed(2);//社保费用
	var shbTotal = (ylWages*0.08 + sqWages*0.02 + 3 + ylWages*khxzbl +gjjWages*0.12+ylWages*0.19 + sqWages*0.1 + ylWages*0.008 + sqWages*0.004 + sqWages*0.008+gjjWages*0.12).toFixed(2);//合计缴纳
	var tableHtml = '<tr><th>类别</th><th>基数</th><th>个人</th><th>单位</th><th>总计</th></tr>';
	tableHtml += '<tr><td>养老</td><td>'+numericValues(ylWages,1)+'</td><td>'+numericValues(ylWages,0.08)+
	'</td><td>'+numericValues(ylWages,0.19)+'</td><td>'+numericValues(ylWages,0.27)+'</td></tr>';
	tableHtml += '<tr><td>医疗</td><td>'+numericValues(sqWages,1)+'</td><td>'+(Number(numericValues(sqWages,0.02))+3).toFixed(2)+
	'</td><td>'+numericValues(sqWages,0.1)+'</td><td>'+(Number(numericValues(sqWages,0.12))+3).toFixed(2)+'</td></tr>';
	tableHtml += '<tr><td>失业</td><td>'+numericValues(ylWages,1)+'</td><td>'+numericValues(ylWages,khxzbl)+
	'</td><td>'+numericValues(ylWages,0.008)+'</td><td>'+numericValues(ylWages,0.01)+'</td></tr>';
	tableHtml += '<tr><td>工伤</td><td>'+numericValues(sqWages,1)+'</td><td>0.00</td><td>'+numericValues(sqWages,0.004)+'</td><td>'+numericValues(sqWages,0.004)+'</td></tr>';
	tableHtml += '<tr><td>生育</td><td>'+numericValues(sqWages,1)+'</td><td>0.00</td><td>'+numericValues(sqWages,0.008)+'</td><td>'+numericValues(sqWages,0.008)+'</td></tr>';
	if(gjjWages!=0.00){
		tableHtml += '<tr><td>公积金</td><td>'+numericValues(gjjWages,1)+'</td><td>'+numericValues(gjjWages,0.12)+
		'</td><td>'+numericValues(gjjWages,0.12)+'</td><td>'+numericValues(gjjWages,0.24)+'</td></tr>';
	}	
	tableHtml += '<tr class="totalTr"><td>总计</td><td>-</td><td>'+grjn+
	'</td><td>'+gsjn+'</td><td>'+shbTotal+'</td></tr>';
	$(".tableBox table").html(tableHtml);
	$(".resultDiv").show();	
	 
    $(".sbfyValue").html(shbfy); //社保费用
    $(".gjjfyValue").html(numericValues(gjjWages,0.24)); //公积金费用
    $(".zjValue").html(shsr)//合计缴纳
    $(".jfje").val(shsr);
    $(".gsfyValue").html(gsjsTax.toFixed(2));
}


//确认框高度赋值
$("#confirm-box,#confirm-box-logout").css({
	"height": windowHeight
});
//确认框开启
function confirmIn(msg){
	$("#confirm-box").fadeIn();
	$("#confirm-box").find("h4").html(msg);
}
//确认框关闭
function confirmOut(){
	$("#confirm-box,#confirm-box-logout").fadeOut();
}

$("#confirm-box-close,#confirm-box-close-logout").click(function(){
	$("#confirm-box,#confirm-box-logout").fadeOut();
});
//验证码弹出框关闭

function codeBoxIn(){
	$("#code-box").fadeIn();
}
function codeBoxOut(){
	$("#code-box").fadeOut();
}
$("#code-box-close").click(function(){
	$("#code-box").fadeOut();	
});

//验证
function auth(authTel,authUuid){
	var numberStr = MathRand();//6位随机数字
	var md5Str = hex_md5(authTel + numberStr + authUuid);
	var token = base.encode(authTel + numberStr + md5Str);
	$.ajax({
		type: "post",
		url: "../rest/shbxWeb/login/auth",
		async: true,
		data: {
			"token": token
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {
			
		}else{
			location.replace("login.html");
		}
	}).fail(function(){
		location.replace("login.html");
	});
	
}
//随机生成6位数字
function MathRand(){
	var Num="";
	for(var i=0;i<6;i++){
		Num+=Math.floor(Math.random()*10);
	}
	return Num;
} 
//判断手机是安卓还是ios
function judgePhone(){
	var u = navigator.userAgent;

	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

	if(isAndroid){
		return 'isAndroid';
	}else if(isiOS){
		return 'isiOS';
	}else{
		return '';
	}
}
//2位小数自动补齐
$(".form-money").blur(function(){
	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
	if(valueInput!=""){
    	if(regularNumber.test(valueInput)){	    		
    		var value=valueInput;
        	var xsd=value.toString().split(".");
        	if(xsd.length==1){
        		value=value.toString()+".00";
        	}else if(xsd.length>1){
        		if(xsd[1].length<2){
        			value=value.toString()+"0";
        		}else{
                	value = xsd[0]+"."+xsd[1].substring(0,2);
                }
            }
        	$(this).val(value);	 
        	return false;
    	}else{
    		layerIn("请填写正确的数字格式");
    	}
	}	
});

$(".paySocial").click(function(){
	confirmOut();
	getIndexUserInfo();
});

//获取用户信息
function getIndexUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/get",
		async: true,
		data: {
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {
			realnameAuthFlag = r.data.realnameAuthFlag;
				
		}else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);	
			}
						
		}
		$.ajax({
			type: "get",
			url: "../rest/shbxWeb/insured/list",
			async: true,
			data: {
				'pageSize':1000,
				'pageNum':1
				
			}
		})
		.done(function(r) {
			loadingOut();
			if(r.status == "SUCCESS") {
				if(r.data.length!=0&&realnameAuthFlag){
					window.location.href="payHasInsuredRecordList.html";
				}else if(realnameAuthFlag&&r.data.length==0){
					window.location.href="newInsure.html?firatFlag=true";
				}else{
					confirmIn("您尚未提交证件信息审核");	
				}
			}else {
				if(r.status == "ERROR"&&r.errorCode=="302"){
					location.replace("login.html");
				}else{
					layerIn(r.message);				
				}						
			}
		})
		.fail(function() {
			loadingOut();
		});
	})
	.fail(function() {
		loadingOut();
		
	});
}

$(".indexConfirm #confirm").click(function(){
	confirmOut();
	location.replace("realnameAuthentication.html?realnameAuthFlag=false");
	localStorage.setItem("addBanktopPage","./newInsure.html?firatFlag=true");
});

//点击我的按钮
$(".myhome").click(function(){
	confirmOut();
	window.location.href="myHome.html";
//	$.ajax({
//		type: "get",
//		url: "../rest/shbxWeb/user/get",
//		async: true,
//		data: {
//			
//		},
//		beforeSend:function(){
//			loadingIn();
//		}
//	})
//	.done(function(r) {
//		if(r.status == "SUCCESS") {
//			location.replace("myHome.html");
//		}else{
//			if(r.status == "ERROR"&&r.errorCode=="302"){
//				location.replace("login.html");
//			}else{
//				layerIn(r.message);				
//			}		
//		}
//	})
//	.fail(function() {
//		loadingOut();
//	});
});

//清除所有cookie
function clearCookie(){
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i =  keys.length; i--;)
            document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()
    }   
}
$(".indexBar").click(function(){
	confirmOut();
	window.location.href="index.html";
});
$(".sshbBar").click(function(){
	confirmOut();
	window.location.href="socialSecurityCalculator.html";
});
$(".consultListBar").click(function(){
	confirmOut();
	window.location.href="consultList.html";
});
//计算服务费月份
function jsFwfYf(){
	var jfyf = $("#jfyf").val();
	var Year=new Date().getFullYear();
	var Day = new Date().getDate();
	var Month = new Date().getMonth()+1;
	if(Day>15){
		Month = new Date().getMonth()+2;
	}
	
	if(jfyf!=""){
		Year=jfyf.substring(0,4);
		Month = jfyf.substring(5,7);		
	}
	$(".fwf-1").html("1个月("+jsAfterMonth(Year,Month,0)+"~"+jsAfterMonth(Year,Month,0)+")");
	$(".fwf-3").html("3个月("+jsAfterMonth(Year,Month,0)+"~"+jsAfterMonth(Year,Month,2)+")");
	$(".fwf-6").html("6个月("+jsAfterMonth(Year,Month,0)+"~"+jsAfterMonth(Year,Month,5)+")");
	$(".fwf-12").html("12个月("+jsAfterMonth(Year,Month,0)+"~"+jsAfterMonth(Year,Month,11)+")");
}
function jsAfterMonth(year,month,length){
	var d = new Date(year, month);
    d.setMonth((d.getMonth()-1) + length);
    var yy1 = d.getFullYear();
    var mm1 = d.getMonth()+1;
    if (mm1 < 10 ) {
        mm1 = '0' + mm1;
    }
    return yy1+"."+mm1;
}
function loading(){
	$("body").show();
}
//四舍五入千分位逗号隔开保留两位小数
function separateComma(str){
	return str.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}
//随机在线咨询
function consulte(){
	if(judgePhone()=='isAndroid'||judgePhone()==''){
		window.JavascriptInterface.openQQ('2128762975');
	}else if(judgePhone()=='isiOS'){
		window.webkit.messageHandlers.openQQ.postMessage('2128762975');		
	}	
}
//拨打热线电话
function opentel(){
	if(judgePhone()=='isAndroid'||judgePhone()==''){
		window.JavascriptInterface.openTel('010-62226659');
	}else if(judgePhone()=='isiOS'){
		window.webkit.messageHandlers.openTel.postMessage('010-62226659');		
	}	
}