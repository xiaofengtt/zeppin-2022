var windowHeight = "", //声明窗口高度
    windowWidth = "", //声明窗口宽度
    navHeight = "", //声明导航条高度
    mainHeight = "", //声明页面主体高度

    menuWidth = "", //声明左侧菜单宽度
    mainWidth = "", //声明页面主体宽度
    contentWidth = "", //声明content的宽度

    flagSubmit = true,
    regularNumber = /^\d+(\.\d+)?$|\d+(\.)?$/;//声明只包含数字和小数点的正则
// -------------------------------------


//获取当前时间
function realTime() {
    var now = new Date(); //创建Date对象
    var year = now.getFullYear(); //获取年份
    var month = now.getMonth(); //获取月份
    var date = now.getDate(); //获取日期
    var day = now.getDay(); //获取星期
    var hour = now.getHours(); //获取小时
    var minu = now.getMinutes(); //获取分钟
    var sec = now.getSeconds(); //获取秒钟
    month = month + 1;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    var week = arr_week[day]; //获取中文的星期
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minu < 10) {
        minu = "0" + minu;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }
    var time = year + "年" + month + "月" + date + "日 " + week + " " + hour + ":" + minu + ":" + sec; //组合系统时间
    $("#realTime").html(time); //显示系统时间
}


//-----------------------------------------

function getTime(timeStamp) {
    var time = "";
    var now = new Date(timeStamp); //创建Date对象
    var year = now.getFullYear(); //获取年份
    var month = now.getMonth(); //获取月份
    var date = now.getDate(); //获取日期
    var hour = now.getHours(); //获取小时
    var minu = now.getMinutes(); //获取分钟
    var sec = now.getSeconds(); //获取秒钟
    month = month + 1;
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minu < 10) {
        minu = "0" + minu;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }
    time = year + "-" + month + "-" + date + " " + hour + ":" + minu + ":" + sec;
    return time;
}

// --------------------------------

$(document).ready(function() {
    windowHeight = $(window).height(); //赋值窗口高度
    windowWidth = $(window).width(); //赋值窗口宽度
    navHeight = $(".navbar").height(); //赋值导航条高度
    mainHeight = windowHeight - navHeight; //赋值页面主体高度

    menuWidth = $("#menu").width() == null ? $("#super-menu").width() : $("#menu").width(); //赋值左侧菜单宽度
    mainWidth = $("#main").width(); //赋值页面主体宽度
    contentWidth = mainWidth - menuWidth-15; //赋值content的宽度

    // -----------------------

    //导航条显示时间
    realTime();
    setInterval(realTime, 1000);

    //主体main 高度赋值

    $("#main").css({
        "height": mainHeight + "px",
        "margin-top": navHeight + "px"
    });

    //content宽度

    $("#content").css({
        "width": contentWidth + "px"
    });
    $(window).resize(function() {
        mainWidth = $("#main").width();
        menuWidth = $("#menu").width() == null ? $("#super-menu").width() : $("#menu").width();
        contentWidth = mainWidth - menuWidth;
        $("#content").css({
            "width": contentWidth + "px"
        });
    });

    // ------------------------
    //2位小数自动补齐
    $(".form-decimal2,.form-money").blur(function(){
    	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
    	if(valueInput!=""){
	    	if(regularNumber.test(valueInput)){	    		
	    		var value=Math.round(parseFloat(valueInput)*100)/100;
	        	var xsd=value.toString().split(".");
	        	if(xsd.length==1){
	        		value=value.toString()+".00";
	        	}else if(xsd.length>1){
	        		if(xsd[1].length<2){
	        			value=value.toString()+"0";
	        		}
	            }
	        	$(this).val(value);	        	                   
	    	}else{
	    		layer.msg("请填写正确的数字格式");
	    	}
    	}	
    });
  //3位小数补全0
    $(".form-decimal3").blur(function(){
    	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
    	if(valueInput!=""){
	    	if(regularNumber.test(valueInput)){	    		
            	var value=(Math.round(parseFloat(valueInput)*1000)/1000).toFixed(3);
            	var xsd=value.toString().split(".");
            	if(xsd.length==1){
            		value=value.toString()+".000";
            	}else if(xsd.length>1){
                	 if(xsd[1].length==1){
                		 value=value.toString()+"0";
                	 }else if(xsd[1].length==0){
                		 value=value.toString()+"00";
                	 }
                }
            	$(this).val(value);	        	
	    	}else{
	    		layer.msg("请填写正确的数字格式");
	    	}    
    	}
    });
    //4位小数补全0
    $(".form-decimal4").blur(function(){
    	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
    	if(valueInput!=""){
	    	if(regularNumber.test(valueInput)){	    		
            	var value=(Math.round(parseFloat(valueInput)*10000)/10000).toFixed(4);
            	var xsd=value.toString().split(".");
            	if(xsd.length==1){
            		value=value.toString()+".000";
            	}else if(xsd.length>1){
                	 if(xsd[1].length==2){
                		 value=value.toString()+"0";
                	 }else if(xsd[1].length==1){
                		 value=value.toString()+"00";
                	 }if(xsd[1].length==0){
                		 value=value.toString()+"000";
                	 }
                }
            	$(this).val(value);	        	
	    	}else{
	    		layer.msg("请填写正确的数字格式");
	    	} 
    	}
    });
    setTimeout('match()',1000);//匹配菜单
}); //document.ready;

//报送周期
function getBszq(bszq){
	var datavalue = '';
	switch (bszq){
		case 1:
			datavalue='日报'
			break;
		case 2:
			datavalue='月报'
			break;
		case 3:
			datavalue='季报'
			break;
		case 4:
			datavalue='半年报'
			break;
		case 5:
			datavalue='年报'
			break;
		default:
			datavalue=bszq
	}
	return datavalue;
}

//归属业务大类
function getXtzzkjkmlx(xtzzkjkmlx){
	var datavalue = '';
	switch (xtzzkjkmlx){
	case 1:
		datavalue='资产'
		break;
	case 2:
		datavalue='负债'
		break;
	case 3:
		datavalue='所有者权益'
		break;
	case 4:
		datavalue='损益'
		break;
	case 5:
		datavalue='资产负债共同类'
		break;
	case 6:
		datavalue='表外'
		break;
	case 7:
		datavalue='其他'
		break;
	default:
		datavalue=xtzzkjkmlx
	}
	return datavalue;
}
//归属业务子类
function getGsywzl(gsywzl){
	var datavalue = '';
	switch (gsywzl){
	case '1':
		datavalue='各项贷款'
		break;
	case '2':
		datavalue='投资'
		break;
	case '3':
		datavalue='拆借资金（拆出）'
		break;
	case '4':
		datavalue='各项垫款'
		break;
	case '5':
		datavalue='各项准备金'
		break;
	case '6':
		datavalue='各项存款'
		break;
	case '7':
		datavalue='拆借资金（拆入）'
		break;
	case '8':
		datavalue='发行债券'
		break;
	case '9':
		datavalue='担保（保函）'
		break;
	case '10':
		datavalue='承兑票据'
		break;
	case '11':
		datavalue='委托贷款'
		break;
	case '12':
		datavalue='信用证'
		break;
	case '13':
		datavalue='理财业务'
		break;
	case '14':
		datavalue='交易'
		break;
	case '15':
		datavalue='利息收入'
		break;
	case '16':
		datavalue='中间业务收入'
		break;
	default:
		datavalue=gsywzl
	}
	return datavalue;
}
//自动补齐位数
function autocomplete(){
	//2位小数补全0
    $(".form-decimal2,.form-money").each(function(){
    	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
    	if(valueInput!=""){
	    	if(regularNumber.test(valueInput)){	    		
        		var value=Math.round(parseFloat(valueInput)*100)/100;
            	var xsd=value.toString().split(".");
            	if(xsd.length==1){
            		value=value.toString()+".00";
            	}else if(xsd.length>1){
                	 if(xsd[1].length<2){
                		 value=value.toString()+"0";
                	 }
                }
            	$(this).val(value);	        	                  
	    	}else{
	    		layer.msg("请填写正确的数字格式");
	    	}   
    	} 
    });
    //3位小数补全0
    $(".form-decimal3").each(function(){
    	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
    	if(valueInput!=""){
	    	if(regularNumber.test(valueInput)){	    		
            	var value=(Math.round(parseFloat(valueInput)*1000)/1000).toFixed(3);
            	var xsd=value.toString().split(".");
            	if(xsd.length==1){
            		value=value.toString()+".000";
            	}else if(xsd.length>1){
                	 if(xsd[1].length==1){
                		 value=value.toString()+"0";
                	 }else if(xsd[1].length==0){
                		 value=value.toString()+"00";
                	 }
                }
            	$(this).val(value);        	
	    	}else{
	    		layer.msg("请填写正确的数字格式");
	    	}  
    	}
    });
    //4位小数补全0
    $(".form-decimal4").each(function(){
    	var valueInput = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
    	if(valueInput!=""){
	    	if(regularNumber.test(valueInput)){	    		
            	var value=(Math.round(parseFloat(valueInput)*10000)/10000).toFixed(4);
            	var xsd=value.toString().split(".");
            	if(xsd.length==1){
            		value=value.toString()+".000";
            	}else if(xsd.length>1){
                	 if(xsd[1].length==2){
                		 value=value.toString()+"0";
                	 }else if(xsd[1].length==1){
                		 value=value.toString()+"00";
                	 }if(xsd[1].length==0){
                		 value=value.toString()+"000";
                	 }
                }
            	$(this).val(value);	        	
	    	}else{
	    		layer.msg("请填写正确的数字格式");
	    	} 
    	}
    });
}
//币种
function getBz(bz){
	var datavalue = '';
	switch (bz){
		case "CNY":
			datavalue='人民币元' 
			break;    
		case "USD":
			datavalue='美元' 
			break;
		case "EUR":
			datavalue='欧元'
			break;
		case "GBP":
			datavalue='英镑' 
			break;
		case "AUD":
			datavalue='澳大利亚元'
			break;
		case "JPY":
			datavalue='日元' 
			break;
		case "CAD":
			datavalue='加元' 
			break;
		case "NZD":
			datavalue='新西兰元' 
			break;
		case "MOP":
			datavalue='澳门元' 
			break;
		case "HKD":
			datavalue='香港元' 
			break;
		case "TWD":
			datavalue='新台湾元' 
			break;
		case "SGD":
			datavalue='新加坡元' 
			break;
		case "KRW":
			datavalue='韩元' 
			break;
		case "ZAR":
			datavalue='南非兰特' 
			break;
		case "RUB":
			datavalue='俄罗斯卢布' 
			break;
		case "INR":
			datavalue='印度卢比' 
			break;
		case "BRL":
			datavalue='巴西雷亚尔' 
			break;
		case "CHF":
			datavalue='瑞士法郎' 
			break;
		case "SEK":
			datavalue='瑞典克朗' 
			break;
		case "PHP":
			datavalue='菲律宾比索' 
			break;
		case "AFA":
			datavalue='阿富汗尼' 
			break;
		case "ALL":
			datavalue='列克' 
			break;
		case "DZD":
			datavalue='阿尔及利亚第纳尔' 
			break;
		case "ADP":
			datavalue='安道尔比塞塔' 
			break;
		case "AZM":
			datavalue='阿塞拜疆马纳特' 
			break;
		case "ARS":
			datavalue='阿根廷比索' 
			break;
		case "ATS":
			datavalue='先令' 
			break;
		case "BSD":
			datavalue='巴哈马元' 
			break;
		case "BHD":
			datavalue='巴林第纳尔' 
			break;
		case "BDT":
			datavalue='塔卡' 
			break;
		case "AMD":
			datavalue='亚美尼亚达姆' 
			break;
		case "BBD":
			datavalue='巴巴多斯元' 
			break;
		case "BEF":
			datavalue='比利时法郎' 
			break;
		case "BMD":
			datavalue='百慕大元' 
			break;
		case "BTN":
			datavalue='努尔特鲁姆' 
			break;
		case "BOB":
			datavalue='玻利瓦诺' 
			break;
		case "BWP":
			datavalue='普拉' 
			break;
		case "BZD":
			datavalue='伯利兹元' 
			break;
		case "SBD":
			datavalue='所罗门群岛元' 
			break;
		case "BND":
			datavalue='文莱元' 
			break;
		case "BGL":
			datavalue='列弗' 
			break;
		case "MMK":
			datavalue='缅元' 
			break;
		case "BIF":
			datavalue='布隆迪法郎' 
			break;
		case "KHR":
			datavalue='瑞尔' 
			break;
		case "CVE":
			datavalue='佛得角埃斯库多' 
			break;
		case "KYD":
			datavalue='开曼群岛元' 
			break;
		case "LKR":
			datavalue='斯里兰卡卢比' 
			break;
		case "CLP":
			datavalue='智利比索' 
			break;
		case "COP":
			datavalue='哥伦比亚比索' 
			break;
		case "KMF":
			datavalue='科摩罗法郎' 
			break;
		case "CRC":
			datavalue='哥斯达黎加科郎' 
			break;
		case "HRK":
			datavalue='克罗地亚库纳' 
			break;
		case "CUP":
			datavalue='古巴比索' 
			break;
		case "CYP":
			datavalue='塞浦路斯镑' 
			break;
		case "CZK":
			datavalue='捷克克朗' 
			break;
		case "DKK":
			datavalue='丹麦克朗' 
			break;
		case "DOP":
			datavalue='多米尼加比索' 
			break;
		case "SVC":
			datavalue='萨尔瓦多科郎' 
			break;
		case "ETB":
			datavalue='埃塞俄比亚比尔' 
			break;
		case "ERN":
			datavalue='纳克法' 
			break;
		case "EEK":
			datavalue='克罗姆' 
			break;
		case "FKP":
			datavalue='福克兰群岛镑' 
			break;
		case "FJD":
			datavalue='斐济元' 
			break;
		case "FIM":
			datavalue='马克' 
			break;
		case "FRF":
			datavalue='法国法郎' 
			break;
		case "DJF":
			datavalue='吉布提法郎' 
			break;
		case "GMD":
			datavalue='达拉西' 
			break;
		case "DEM":
			datavalue='德国马克' 
			break;
		case "GHC":
			datavalue='塞地' 
			break;
		case "GIP":
			datavalue='直布罗陀镑' 
			break;
		case "GRD":
			datavalue='德拉克马' 
			break;
		case "GTQ":
			datavalue='格查尔' 
			break;
		case "GNF":
			datavalue='几内亚法郎' 
			break;
		case "GYD":
			datavalue='圭亚那元' 
			break;
		case "HTG":
			datavalue='古德' 
			break;
		case "HNL":
			datavalue='伦皮拉' 
			break;
		case "HUF":
			datavalue='福林' 
			break;
		case "ISK":
			datavalue='冰岛克朗' 
			break;
		case "IDR":
			datavalue='卢比' 
			break;
		case "IRR":
			datavalue='伊朗里亚尔' 
			break;
		case "IQD":
			datavalue='伊拉克第纳尔' 
			break;
		case "IEP":
			datavalue='爱尔兰镑' 
			break;
		case "ILS":
			datavalue='新谢客尔' 
			break;
		case "ITL":
			datavalue='意大利里拉' 
			break;
		case "JMD":
			datavalue='牙买加元' 
			break;
		case "KZT":
			datavalue='坚戈' 
			break;
		case "JOD":
			datavalue='约旦第纳尔' 
			break;
		case "KES":
			datavalue='肯尼亚先令' 
			break;
		case "KPW":
			datavalue='北朝鲜圆' 
			break;
		case "KWD":
			datavalue='科威特第纳尔' 
			break;
		case "KGS":
			datavalue='索姆' 
			break;
		case "LAK":
			datavalue='基普' 
			break;
		case "LBP":
			datavalue='黎巴嫩镑' 
			break;
		case "LSL":
			datavalue='罗提' 
			break;
		case "LVL":
			datavalue='拉脱维亚拉特' 
			break;
		case "LRD":
			datavalue='利比里亚元' 
			break;
		case "LYD":
			datavalue='利比亚第纳尔' 
			break;
		case "LTL":
			datavalue='立陶宛' 
			break;
		case "LUF":
			datavalue='卢森堡法郎' 
			break;
		case "MGF":
			datavalue='马尔加什法郎' 
			break;
		case "MWK":
			datavalue='克瓦查' 
			break;
		case "MYR":
			datavalue='马来西亚林吉特' 
			break;
		case "MVR":
			datavalue='卢菲亚' 
			break;
		case "MTL":
			datavalue='马尔他里拉' 
			break;
		case "MRO":
			datavalue='乌吉亚' 
			break;
		case "MUR":
			datavalue='毛里求斯卢比' 
			break;
		case "MXN":
			datavalue='墨西哥比索' 
			break;
		case "MNT":
			datavalue='图格里克' 
			break;
		case "MDL":
			datavalue='摩尔瓦多列伊' 
			break;
		case "MAD":
			datavalue='摩洛哥迪拉姆' 
			break;
		case "MZM":
			datavalue='麦梯卡尔' 
			break;
		case "OMR":
			datavalue='阿曼里亚尔' 
			break;
		case "NAD":
			datavalue='纳米比亚元' 
			break;
		case "NPR":
			datavalue='尼泊尔卢比' 
			break;
		case "NLG":
			datavalue='荷兰盾' 
			break;
		case "ANG":
			datavalue='荷属安的列斯盾' 
			break;
		case "AWG":
			datavalue='阿鲁巴盾' 
			break;
		case "VUV":
			datavalue='瓦图' 
			break;
		case "NIO":
			datavalue='金科多巴' 
			break;
		case "NGN":
			datavalue='奈拉' 
			break;
		case "NOK":
			datavalue='挪威克朗' 
			break;
		case "PKR":
			datavalue='巴基斯坦卢比' 
			break;
		case "PAB":
			datavalue='巴波亚' 
			break;
		case "PGK":
			datavalue='基那' 
			break;
		case "PYG":
			datavalue='瓜拉尼' 
			break;
		case "PEN":
			datavalue='索尔' 
			break;
		case "PTE":
			datavalue='葡萄牙埃斯库多' 
			break;
		case "GWP":
			datavalue='几内亚比绍比索' 
			break;
		case "TPE":
			datavalue='东帝汶埃斯库多' 
			break;
		case "QAR":
			datavalue='卡塔尔里亚尔' 
			break;
		case "ROL":
			datavalue='列伊' 
			break;
		case "RWF":
			datavalue='卢旺达法郎' 
			break;
		case "SHP":
			datavalue='圣赫勒拿磅' 
			break;
		case "STD":
			datavalue='多布拉' 
			break;
		case "SAR":
			datavalue='沙特里亚尔' 
			break;
		case "SCR":
			datavalue='塞舌尔卢比' 
			break;
		case "SLL":
			datavalue='利昂' 
			break;
		case "SKK":
			datavalue='斯洛伐克克朗' 
			break;
		case "VND":
			datavalue='越南盾' 
			break;
		case "SIT":
			datavalue='托拉尔' 
			break;
		case "SOS":
			datavalue='索马里先令' 
			break;
		case "ZWD":
			datavalue='津巴布韦元' 
			break;
		case "ESP":
			datavalue='西班牙比塞塔' 
			break;
		case "SDD":
			datavalue='苏丹第纳尔' 
			break;
		case "SRG":
			datavalue='苏里南盾' 
			break;
		case "SZL":
			datavalue='里兰吉尼' 
			break;
		case "SYP":
			datavalue='叙利亚镑' 
			break;
		case "THB":
			datavalue='泰铢' 
			break;
		case "TOP":
			datavalue='邦加' 
			break;
		case "TTD":
			datavalue='特立尼达和多巴哥元' 
			break;
		case "AED":
			datavalue='UAE迪拉姆' 
			break;
		case "TND":
			datavalue='突尼斯第纳尔' 
			break;
		case "TRL":
			datavalue='土耳其里拉' 
			break;
		case "TMM":
			datavalue='马纳特' 
			break;
		case "UGX":
			datavalue='乌干达先令' 
			break;
		case "MKD":
			datavalue='第纳尔' 
			break;
		case "RUR":
			datavalue='俄罗斯卢布' 
			break;
		case "EGP":
			datavalue='埃及镑' 
			break;
		case "TZS":
			datavalue='坦桑尼亚先令' 
			break;
		case "UYU":
			datavalue='乌拉圭比索' 
			break;
		case "UZS":
			datavalue='乌兹别克斯坦苏姆' 
			break;
		case "WST":
			datavalue='塔拉' 
			break;
		case "YER":
			datavalue='也门里亚尔' 
			break;
		case "YUM":
			datavalue='南斯拉夫第纳尔' 
			break;
		case "ZMK":
			datavalue='克瓦查' 
			break;
		case "XAF":
			datavalue='CFA法郎BEAC' 
			break;
		case "XCD":
			datavalue='东加勒比元' 
			break;
		case "XOF":
			datavalue='CFA法郎BCEAO' 
			break;
		case "XPF":
			datavalue='CFP法郎' 
			break;
		case "TJS":
			datavalue='索莫尼' 
			break;
		case "AOA":
			datavalue='宽扎' 
			break;
		case "BYR":
			datavalue='白俄罗斯卢布' 
			break;
		case "BGN":
			datavalue='保加利亚列弗' 
			break;
		case "CDF":
			datavalue='刚果法郎' 
			break;
		case "BAM":
			datavalue='可自由兑换标记' 
			break;
		case "MXV":
			datavalue='墨西哥发展单位' 
			break;
		case "UAH":
			datavalue='格里夫纳' 
			break;
		case "GEL":
			datavalue='拉里' 
			break;
		case "BOV":
			datavalue='Mvdol(玻利维亚)' 
			break;
		case "PLN":
			datavalue='兹罗提' 
			break;
		case "XFU":
			datavalue='UIC法郎' 
			break;
		default:
			datavalue=bz
	}
	return datavalue;
}
//境外国家
function getJwgi(Jwgi){
	var datavalue = '';
	switch (Jwgi){
		case "840":
			datavalue='美国' 
			break;
	    case "826":
			datavalue='英国' 
			break;
	    case "124":
			datavalue='加拿大' 
			break;
	    case "076":
			datavalue='巴西' 
			break;
	    case "392":
			datavalue='日本' 
			break;
	    case "410":
			datavalue='韩国' 
			break;
	    case "702":
			datavalue='新加坡' 
			break;
	    case "276":
			datavalue='德国' 
			break;
	    case "380":
			datavalue='意大利' 
			break;
	    case "250":
			datavalue='法国' 
			break;
	    case "756":
			datavalue='瑞士' 
			break;
	    case "752":
			datavalue='瑞典' 
			break;
	    case "578":
			datavalue='挪威' 
			break;
	    case "372":
			datavalue='爱尔兰' 
			break;
	    case "040":
			datavalue='奥地利' 
			break;
	    case "620":
			datavalue='葡萄牙' 
			break;
	    case "724":
			datavalue='西班牙' 
			break;
	    case "036":
			datavalue='澳大利亚' 
			break;
	    case "554":
			datavalue='新西兰' 
			break;
	    case "004":
			datavalue='阿富汗' 
			break;
	    case "008":
			datavalue='阿尔巴尼亚' 
			break;
	    case "012":
			datavalue='阿尔及利亚' 
			break;
	    case "016":
			datavalue='美属萨摩亚' 
			break;
	    case "020":
			datavalue='安道尔' 
			break;
	    case "024":
			datavalue='安哥拉' 
			break;
	    case "660":
			datavalue='安圭拉' 
			break;
	    case "010":
			datavalue='南极洲' 
			break;
	    case "028":
			datavalue='安提瓜和巴布达' 
			break;
	    case "032":
			datavalue='阿根廷' 
			break;
	    case "051":
			datavalue='亚美尼亚' 
			break;
	    case "533":
			datavalue='阿鲁巴' 
			break;
	    case "031":
			datavalue='阿塞拜疆' 
			break;
	    case "044":
			datavalue='巴哈马' 
			break;
	    case "048":
			datavalue='巴林' 
			break;
	    case "050":
			datavalue='孟加拉国' 
			break;
	    case "052":
			datavalue='巴巴多斯' 
			break;
	    case "112":
			datavalue='白俄罗斯' 
			break;
	    case "056":
			datavalue='比利时' 
			break;
	    case "084":
			datavalue='伯利兹' 
			break;
	    case "204":
			datavalue='贝宁' 
			break;
	    case "060":
			datavalue='百慕大' 
			break;
	    case "064":
			datavalue='不丹' 
			break;
	    case "068":
			datavalue='玻利维亚' 
			break;
	    case "070":
			datavalue='波黑' 
			break;
	    case "072":
			datavalue='博茨瓦纳' 
			break;
	    case "074":
			datavalue='布维岛' 
			break;
	    case "086":
			datavalue='英属印度洋领地' 
			break;
	    case "096":
			datavalue='文莱' 
			break;
	    case "100":
			datavalue='保加利亚' 
			break;
	    case "854":
			datavalue='布基纳法索' 
			break;
	    case "108":
			datavalue='布隆迪' 
			break;
	    case "116":
			datavalue='柬埔寨' 
			break;
	    case "120":
			datavalue='喀麦隆' 
			break;
	    case "132":
			datavalue='佛得角' 
			break;
	    case "136":
			datavalue='开曼群岛' 
			break;
	    case "140":
			datavalue='中非' 
			break;
	    case "148":
			datavalue='乍得' 
			break;
	    case "152":
			datavalue='智利' 
			break;
	    case "162":
			datavalue='圣诞岛' 
			break;
	    case "166":
			datavalue='科科斯（基林）群岛' 
			break;
	    case "170":
			datavalue='哥伦比亚' 
			break;
	    case "174":
			datavalue='科摩罗' 
			break;
	    case "178":
			datavalue='刚果（布）' 
			break;
	    case "180":
			datavalue='刚果（金）' 
			break;
	    case "184":
			datavalue='库克群岛' 
			break;
	    case "188":
			datavalue='哥斯达黎加' 
			break;
	    case "384":
			datavalue='科特迪瓦' 
			break;
	    case "191":
			datavalue='克罗地亚' 
			break;
	    case "192":
			datavalue='古巴' 
			break;
	    case "196":
			datavalue='塞浦路斯' 
			break;
	    case "203":
			datavalue='捷克' 
			break;
	    case "208":
			datavalue='丹麦' 
			break;
	    case "262":
			datavalue='吉布提' 
			break;
	    case "212":
			datavalue='多米尼克' 
			break;
	    case "214":
			datavalue='多米尼加' 
			break;
	    case "626":
			datavalue='东帝汶' 
			break;
	    case "218":
			datavalue='厄瓜多尔' 
			break;
	    case "818":
			datavalue='埃及' 
			break;
	    case "222":
			datavalue='萨尔瓦多' 
			break;
	    case "226":
			datavalue='赤道几内亚' 
			break;
	    case "232":
			datavalue='厄立特里亚' 
			break;
	    case "233":
			datavalue='爱沙尼亚' 
			break;
	    case "231":
			datavalue='埃塞俄比亚' 
			break;
	    case "238":
			datavalue='福克兰群岛（马尔维纳斯）' 
			break;
	    case "234":
			datavalue='法罗群岛' 
			break;
	    case "242":
			datavalue='斐济' 
			break;
	    case "246":
			datavalue='芬兰' 
			break;
	    case "254":
			datavalue='法属圭亚那' 
			break;
	    case "258":
			datavalue='法属波利尼西亚' 
			break;
	    case "260":
			datavalue='法属南部领地' 
			break;
	    case "266":
			datavalue='加蓬' 
			break;
	    case "270":
			datavalue='冈比亚' 
			break;
	    case "268":
			datavalue='格鲁吉亚' 
			break;
	    case "288":
			datavalue='加纳' 
			break;
	    case "292":
			datavalue='直布罗陀' 
			break;
	    case "300":
			datavalue='希腊' 
			break;
	    case "304":
			datavalue='格陵兰' 
			break;
	    case "308":
			datavalue='格林纳达' 
			break;
	    case "312":
			datavalue='瓜德罗普' 
			break;
	    case "316":
			datavalue='关岛' 
			break;
	    case "320":
			datavalue='危地马拉' 
			break;
	    case "324":
			datavalue='几内亚' 
			break;
	    case "624":
			datavalue='几内亚比绍' 
			break;
	    case "328":
			datavalue='圭亚那' 
			break;
	    case "332":
			datavalue='海地' 
			break;
	    case "334":
			datavalue='赫德岛和麦克唐纳岛' 
			break;
	    case "340":
			datavalue='洪都拉斯' 
			break;
	    case "348":
			datavalue='匈牙利' 
			break;
	    case "352":
			datavalue='冰岛' 
			break;
	    case "356":
			datavalue='印度' 
			break;
	    case "360":
			datavalue='印度尼西亚' 
			break;
	    case "364":
			datavalue='伊朗' 
			break;
	    case "368":
			datavalue='伊拉克' 
			break;
	    case "376":
			datavalue='以色列' 
			break;
	    case "388":
			datavalue='牙买加' 
			break;
	    case "400":
			datavalue='约旦' 
			break;
	    case "398":
			datavalue='哈萨克斯坦' 
			break;
	    case "404":
			datavalue='肯尼亚' 
			break;
	    case "296":
			datavalue='基里巴斯' 
			break;
	    case "408":
			datavalue='朝鲜' 
			break;
	    case "414":
			datavalue='科威特' 
			break;
	    case "417":
			datavalue='吉尔吉斯斯坦' 
			break;
	    case "418":
			datavalue='老挝' 
			break;
	    case "428":
			datavalue='拉脱维亚' 
			break;
	    case "422":
			datavalue='黎巴嫩' 
			break;
	    case "426":
			datavalue='莱索托' 
			break;
	    case "430":
			datavalue='利比里亚' 
			break;
	    case "434":
			datavalue='利比亚' 
			break;
	    case "438":
			datavalue='列支敦士登' 
			break;
	    case "440":
			datavalue='立陶宛' 
			break;
	    case "442":
			datavalue='卢森堡' 
			break;
	    case "807":
			datavalue='前南巴其顿' 
			break;
	    case "450":
			datavalue='马达加斯加' 
			break;
	    case "454":
			datavalue='马拉维' 
			break;
	    case "458":
			datavalue='马来西亚' 
			break;
	    case "462":
			datavalue='马尔代夫' 
			break;
	    case "466":
			datavalue='马里' 
			break;
	    case "470":
			datavalue='马耳他' 
			break;
	    case "584":
			datavalue='马绍尔群岛' 
			break;
	    case "474":
			datavalue='马提尼克' 
			break;
	    case "478":
			datavalue='毛里塔尼亚' 
			break;
	    case "480":
			datavalue='毛里求斯' 
			break;
	    case "175":
			datavalue='马约特' 
			break;
	    case "484":
			datavalue='墨西哥' 
			break;
	    case "583":
			datavalue='密克罗尼西亚联邦' 
			break;
	    case "498":
			datavalue='摩尔多瓦' 
			break;
	    case "492":
			datavalue='摩纳哥' 
			break;
	    case "496":
			datavalue='蒙古' 
			break;
	    case "500":
			datavalue='蒙特塞拉特' 
			break;
	    case "504":
			datavalue='摩洛哥' 
			break;
	    case "508":
			datavalue='莫桑比克' 
			break;
	    case "104":
			datavalue='缅甸' 
			break;
	    case "516":
			datavalue='纳米比亚' 
			break;
	    case "520":
			datavalue='瑙鲁' 
			break;
	    case "524":
			datavalue='尼泊尔' 
			break;
	    case "528":
			datavalue='荷兰' 
			break;
	    case "530":
			datavalue='荷属安的列斯' 
			break;
	    case "540":
			datavalue='新喀里多尼亚' 
			break;
	    case "558":
			datavalue='尼加拉瓜' 
			break;
	    case "562":
			datavalue='尼日尔' 
			break;
	    case "566":
			datavalue='尼日利亚' 
			break;
	    case "570":
			datavalue='纽埃' 
			break;
	    case "574":
			datavalue='诺福克岛' 
			break;
	    case "580":
			datavalue='北马里亚纳' 
			break;
	    case "512":
			datavalue='阿曼' 
			break;
	    case "586":
			datavalue='巴基斯坦' 
			break;
	    case "585":
			datavalue='帕劳' 
			break;
	    case "275":
			datavalue='巴勒斯坦' 
			break;
	    case "591":
			datavalue='巴拿马' 
			break;
	    case "598":
			datavalue='巴布亚新几内亚' 
			break;
	    case "600":
			datavalue='巴拉圭' 
			break;
	    case "604":
			datavalue='秘鲁' 
			break;
	    case "608":
			datavalue='菲律宾' 
			break;
	    case "612":
			datavalue='皮特凯恩' 
			break;
	    case "616":
			datavalue='波兰' 
			break;
	    case "630":
			datavalue='波多黎各' 
			break;
	    case "634":
			datavalue='卡塔尔' 
			break;
	    case "638":
			datavalue='留尼汪' 
			break;
	    case "642":
			datavalue='罗马尼亚' 
			break;
	    case "643":
			datavalue='俄罗斯联邦' 
			break;
	    case "646":
			datavalue='卢旺达' 
			break;
	    case "654":
			datavalue='圣赫勒拿' 
			break;
	    case "659":
			datavalue='圣基茨和尼维斯' 
			break;
	    case "662":
			datavalue='圣卢西亚' 
			break;
	    case "666":
			datavalue='圣皮埃尔和密克隆' 
			break;
	    case "670":
			datavalue='圣文森特和格林纳丁斯' 
			break;
	    case "882":
			datavalue='萨摩亚' 
			break;
	    case "674":
			datavalue='圣马力诺' 
			break;
	    case "678":
			datavalue='圣多美和普林西比' 
			break;
	    case "682":
			datavalue='沙特阿拉伯' 
			break;
	    case "686":
			datavalue='塞内加尔' 
			break;
	    case "690":
			datavalue='塞舌尔' 
			break;
	    case "694":
			datavalue='塞拉利昂' 
			break;
	    case "703":
			datavalue='斯洛伐克' 
			break;
	    case "705":
			datavalue='斯洛文尼亚' 
			break;
	    case "090":
			datavalue='所罗门群岛' 
			break;
	    case "706":
			datavalue='索马里' 
			break;
	    case "710":
			datavalue='南非' 
			break;
	    case "239":
			datavalue='南乔治亚岛和南桑德韦奇岛' 
			break;
	    case "144":
			datavalue='斯里兰卡' 
			break;
	    case "736":
			datavalue='苏丹' 
			break;
	    case "740":
			datavalue='苏里南' 
			break;
	    case "744":
			datavalue='斯瓦尔巴岛和扬马延岛' 
			break;
	    case "748":
			datavalue='斯威士兰' 
			break;
	    case "760":
			datavalue='叙利亚' 
			break;
	    case "762":
			datavalue='塔吉克斯坦' 
			break;
	    case "834":
			datavalue='坦桑尼亚' 
			break;
	    case "764":
			datavalue='泰国' 
			break;
	    case "768":
			datavalue='多哥' 
			break;
	    case "772":
			datavalue='托克劳' 
			break;
	    case "776":
			datavalue='汤加' 
			break;
	    case "780":
			datavalue='特立尼克和多巴哥' 
			break;
	    case "788":
			datavalue='突尼斯' 
			break;
	    case "792":
			datavalue='土耳其' 
			break;
	    case "795":
			datavalue='土库曼斯坦' 
			break;
	    case "796":
			datavalue='特克斯和凯科斯群岛' 
			break;
	    case "798":
			datavalue='图瓦卢' 
			break;
	    case "800":
			datavalue='乌干达' 
			break;
	    case "804":
			datavalue='乌克兰' 
			break;
	    case "784":
			datavalue='阿联酋' 
			break;
	    case "581":
			datavalue='美国本土外小岛屿' 
			break;
	    case "858":
			datavalue='乌拉圭' 
			break;
	    case "860":
			datavalue='乌兹别克斯坦' 
			break;
	    case "548":
			datavalue='瓦努阿图' 
			break;
	    case "336":
			datavalue='梵蒂冈' 
			break;
	    case "862":
			datavalue='委内瑞拉' 
			break;
	    case "704":
			datavalue='越南' 
			break;
	    case "092":
			datavalue='英属维尔京群岛' 
			break;
	    case "850":
			datavalue='美属维尔京群岛' 
			break;
	    case "876":
			datavalue='瓦利斯和富图纳' 
			break;
	    case "732":
			datavalue='西撒哈拉' 
			break;
	    case "887":
			datavalue='也门' 
			break;
	    case "891":
			datavalue='南斯拉夫' 
			break;
	    case "894":
			datavalue='赞比亚' 
			break;
	    case "716":
			datavalue='津巴布韦' 
			break;
	    case "156":
			datavalue='中国' 
			break;
		default:
			datavalue=Jwgi
	}
	return datavalue;
}
//获取地区
function getScode(key,scode) {
    $.ajax({
        url: '../rest/backadmin/area/get',
        type: 'get',
        data: {
            "scode": scode
        }
    }).done(function(r) {
    	if(r.status=="SUCCESS"){
    		$("input[name="+key+"]").val(r.data);
    	}    	
    })
}
//40张表
function getdataType(dataType){
	var datavalue = '';
	switch (dataType){
		case "TKjXtxmzzkjqkmb":
			datavalue='信托项目总账会计全科目表' 
			break;
	    case "TKjXtxmzcfztjb":
			datavalue='信托项目资产负债科目统计表' 
			break;
	    case "TKjGynbkmdzb":
			datavalue='固有内部科目对照表' 
			break;
	    case "TKjGyzzkjqkmb":
			datavalue='固有总账会计全科目表' 
			break;
	    case "TKjGyzcfzkmtjb":
			datavalue='固有资产负债科目统计表' 
			break;
	    case "TKhXtkhgr":
			datavalue='信托客户（个人）' 
			break;
	    case "TKhXtkhjg":
			datavalue='信托客户（机构）' 
			break;
	    case "TKhJydsgr":
			datavalue='交易对手（个人）' 
			break;
	    case "TKhJydsjg":
			datavalue='交易对手（机构）' 
			break;
	    case "TKhJydsjggdxx":
			datavalue='交易对手（机构）股东信息' 
			break;
	    case "TXmXtzjmjhtxx":
			datavalue='信托资金募集合同信息' 
			break;
	    case "TXmXtzjyyhtxx":
			datavalue='信托资金运用合同信息' 
			break;
	    case "TXmFdcjsxmxx":
			datavalue='房地产建设项目信息' 
			break;
	    case "TXmFfdcjsxmxx":
			datavalue='非房地产建设项目信息' 
			break;
	    case "TXmXtdbhtb":
			datavalue='信托担保合同表' 
			break;
	    case "TXmXtdbgxb":
			datavalue='信托担保关系表' 
			break;
	    case "TXmXtdzywb":
			datavalue='信托抵质押物表' 
			break;
	    case "TXmXtxmsyqb":
			datavalue='信托项目受益权表' 
			break;
	    case "TXmXtxmqsxx":
			datavalue='信托项目清算信息' 
			break;
	    case "TXmXtxmyjhklypgb":
			datavalue='信托项目预计还款来源评估表' 
			break;
	    case "TJyXtzjmjjfpl":
			datavalue='信托资金募集及分配流水' 
			break;
	    case "TJyXtsyqzrxx":
			datavalue='信托受益权转让信息' 
			break;
	    case "TGyGyzhxx":
			datavalue='固有账户信息' 
			break;
	    case "TGyGyzzyyhtxx":
			datavalue='固有资金运用合同信息' 
			break;
		case "TGyGyzjyyjyl":
			datavalue='固有资金运用交易流水' 
			break;
		case "TGyGydbhtb":
			datavalue='固有担保合同表' 
			break;
		case "TGyGydbgxb":
			datavalue='固有担保关系表' 
			break;
		case "TGyGydzywb":
			datavalue='固有抵质押物表' 
			break;
		case "TGgGdxxb":
			datavalue='股东信息表' 
			break;
		case "TGgJgxxb":
			datavalue='机构信息表' 
			break;
		case "TGgYgxxb":
			datavalue='员工信息表' 
			break;
		case "TJyQjglxxfzq":
			datavalue='期间管理信息（非证劵类）' 
			break;
		case "TJyQjglxxzq":
			datavalue='期间管理信息（证劵类）' 
			break;
		case "TJyXtsypz":
			datavalue='信托受益凭据' 
			break;
		case "TJyXtzjyyjyl":
			datavalue='信托资金运用交易流水' 
			break;
		case "TKhDsfhzjgxx":
			datavalue='第三方合作机构信息' 
			break;
		case "TKhTzgwhtb":
			datavalue='投资顾问合同表' 
			break;
		case "TKjXtnbkmdzb":
			datavalue='信托内部科目对照表' 
			break;
		case "TXmXtxmxx":
			datavalue='信托项目信息' 
			break;
		case "TXmXtzhxx":
			datavalue='信托账户信息' 
			break;
		default:
			datavalue=dataType
	}
	return datavalue;
}
//撤销审核类型
function gettype(Type){
	var datavalue = '';
	switch (Type){
		case "add":
			datavalue='新增' 
			break;
	    case "edit":
			datavalue='编辑' 
			break;
	    case "delete":
			datavalue='删除' 
			break;
	    case "check":
			datavalue='审核通过' 
			break;
	    case "nopass":
			datavalue='审核不通过' 
			break;
	    case "rollback":
			datavalue='撤销审核' 
			break;
	    case "sync":
			datavalue='ETL采集' 
			break;
	    case "midsync":
			datavalue='中间库ETL采集' 
			break;
		default:
			datavalue=Type
	}
	return datavalue;
}
//40张表 驼峰转大写
function getUpper(dataType){
	var datavalue = '';
	switch (dataType){
		case "TKjXtxmzzkjqkmb":
			datavalue='XTXMZZKJQKMB' 
			break;
	    case "TKjXtxmzcfztjb":
			datavalue='XTXMZCFZTJB' 
			break;
	    case "TKjGynbkmdzb":
			datavalue='GYNBKMDZB' 
			break;
	    case "TKjGyzzkjqkmb":
			datavalue='GYZZKJQKMB' 
			break;
	    case "TKjGyzcfzkmtjb":
			datavalue='GYZCFZKMTJB' 
			break;
	    case "TKhXtkhgr":
			datavalue='XTKHGR' 
			break;
	    case "TKhXtkhjg":
			datavalue='XTKHJG' 
			break;
	    case "TKhJydsgr":
			datavalue='JYDSGR' 
			break;
	    case "TKhJydsjg":
			datavalue='JYDSJG' 
			break;
	    case "TKhJydsjggdxx":
			datavalue='JYDSJGGDXX' 
			break;
	    case "TXmXtzjmjhtxx":
			datavalue='XTZJMJHTXX' 
			break;
	    case "TXmXtzjyyhtxx":
			datavalue='XTZJYYHTXX' 
			break;
	    case "TXmFdcjsxmxx":
			datavalue='FDCJSXMXX' 
			break;
	    case "TXmFfdcjsxmxx":
			datavalue='FFDCJSXMXX' 
			break;
	    case "TXmXtdbhtb":
			datavalue='XTDBHTB' 
			break;
	    case "TXmXtdbgxb":
			datavalue='XTDBGXB' 
			break;
	    case "TXmXtdzywb":
			datavalue='XTDZYWB' 
			break;
	    case "TXmXtxmsyqb":
			datavalue='XTXMSYQB' 
			break;
	    case "TXmXtxmqsxx":
			datavalue='XTXMQSXX' 
			break;
	    case "TXmXtxmyjhklypgb":
			datavalue='XTXMYJHKLYPGB' 
			break;
	    case "TJyXtzjmjjfpl":
			datavalue='XTZJMJJFPLS' 
			break;
	    case "TJyXtsyqzrxx":
			datavalue='XTSYQZRXX' 
			break;
	    case "TGyGyzhxx":
			datavalue='GYZHXX' 
			break;
	    case "TGyGyzzyyhtxx":
			datavalue='GYZZYYHTXX' 
			break;
		case "TGyGyzjyyjyl":
			datavalue='GYZJYYJYLS' 
			break;
		case "TGyGydbhtb":
			datavalue='GYDBHTB' 
			break;
		case "TGyGydbgxb":
			datavalue='GYDBGXB' 
			break;
		case "TGyGydzywb":
			datavalue='GYDZYWB' 
			break;
		case "TGgGdxxb":
			datavalue='GDXXB' 
			break;
		case "TGgJgxxb":
			datavalue='JGXXB' 
			break;
		case "TGgYgxxb":
			datavalue='YGXXB' 
			break;
		case "TJyQjglxxfzq":
			datavalue='QJGLXXFZQ' 
			break;
		case "TJyQjglxxzq":
			datavalue='QJGLXXZQ' 
			break;
		case "TJyXtsypz":
			datavalue='XTSYPZ' 
			break;
		case "TJyXtzjyyjyl":
			datavalue='XTZJYYJYLS' 
			break;
		case "TKhDsfhzjgxx":
			datavalue='DSFHZJGXX' 
			break;
		case "TKhTzgwhtb":
			datavalue='TZGWHTB' 
			break;
		case "TKjXtnbkmdzb":
			datavalue='XTNBKMDZB' 
			break;
		case "TXmXtxmxx":
			datavalue='XTXMXX' 
			break;
		case "TXmXtzhxx":
			datavalue='XTZHXX' 
			break;
		default:
			datavalue=dataType
	}
	return datavalue;
}
//匹配菜单
function match() {
    var scodeSecond = $("#scode").val();
    var scodeFrist = scodeSecond.substring(0, 3);
    $(".ul li").each(function() {
        if ($(this).attr("name") == scodeSecond) {
            var thisa = $(this).addClass("lights").find('a');
            thisa.addClass("lia");
            thisa.css({"background":"#ffffff","color":"#1c91eb"});
            $(this).parent().parent().find("img").attr("src", "img/adown.png");
        }
        if ($(this).attr("name") == scodeFrist) {
            $(this).addClass("light").siblings().removeClass("light");
        }
    });
}
$('.form-product').change(function(){
	window.location.href=window.location.href;
});