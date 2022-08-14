var flagSubmit = true;
$(function(){
	getAdminName();
//	$(".content-left").css({
//		"min-height":$(window).height()
//	});
	$(".layerDiv").css({
		"height":$(window).height()
	});
	$(".layerInner").css({
		"height":$(window).height()-60
	});
});
$(window).resize(function(){
//	$(".content-left").css({
//		"min-height":$(window).height()
//	});
	$(".layerDiv").css({
		"height":$(window).height()
	});
	$(".layerInner").css({
		"height":$(window).height()-60
	});
});
$(".closeLayer").click(function(){
	$(".layerDiv").hide();
});
$(".content-left .content-left-list li").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
});
$(".operate-btn").hover(function(){
	$(".operate-ul").show();
},function(){
	$(".operate-ul").hide();
});
//全选
function allCheck(){
	$("#allCheck").click(function(){
		if($(this).hasClass("light")){
			$(this).removeClass("light");
			$(".check").each(function(){
				$(this).removeClass("light");
			});
		}else{
			$(this).addClass("light");
			$(".check").each(function(){
				$(this).addClass("light");
			});
		}
		return false;
	});
	$(".check").click(function(){
		var checkFlag = true;
		if($(this).hasClass("light")){
			$(this).removeClass("light");
			$("#allCheck").removeClass("light");
		}else{
			$(this).addClass("light");
			$(".check").each(function(){
				if ($(this).hasClass("light")) {
		            	
	            } else {
	                checkFlag = false;
	            }
			});
			if (checkFlag) {
	            $("#allCheck").addClass("light");
	        } else {
	            $("#allCheck").removeClass("light");
	        }
		}
		return false;
	});
	$("input[type='checkbox']").parent().click(function(){
		$(this).find("input[type='checkbox']").click();
		return false;
	});
}


function escape2Html(str) {
    if (str != null) {
        var arrEntities = {
            'lt': '<',
            'gt': '>',
            'nbsp': ' ',
            'amp': '&',
            'quot': '"'
        };
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function(all, t) {
            return arrEntities[t];
        });
    } else {
        return '';
    }
}
//退出登录
$(".layout-btn").click(function(){
	layer.confirm('确定要退出登录吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			$.ajax({
		        url: '../loginBack/logout',
		        type: 'get',
		        async:false,
		        data: {
		            
		        }
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg("退出登录成功！", {
				            time: 2000
				        },function(){
				        	window.location.href="login.html";
				        });
		    				            
	        		} else {
	        			if(r.errorCode=="302"){
	        				layer.msg(r.message, {
	        		            time: 2000
	        		        },function(){
	        		        	window.location.href="login.html";
	        		        });
	        			}else{
	        				layer.msg(r.message);
	        			}
	        		}
		    }).fail(function(r) {
		        layer.msg("error", {
		            time: 2000
		        },function(){
		        		window.location.href=window.location.href;
		        });
		    });   	
		}, function(){
		  layer.closeAll();
		});
});
//时区时间转化
function utcToLocation(UTCDateString){
	function formatFunc(str) {    //格式化显示
        return str > 9 ? str : '0' + str
      }
      var date2 = new Date(UTCDateString);     //这步是关键
      var year = date2.getFullYear();
      var mon = formatFunc(date2.getMonth() + 1);
      var day = formatFunc(date2.getDate());
      var hour = date2.getHours();
      var noon = hour >= 12 ? 'PM' : 'AM';
      hour = hour>=12?hour-12:hour;
      hour = formatFunc(hour);
      var min = formatFunc(date2.getMinutes());
      var dateStr = year+'-'+mon+'-'+day+' '+noon +' '+hour+':'+min;
      return dateStr;
}

//时间戳转化
function formatDate(now) {
	var now = new Date(now);
	var year=now.getFullYear();
	var month="0"+(now.getMonth()+1);
	var date="0"+now.getDate();
	var hour="0"+now.getHours();
	var minute="0"+now.getMinutes();
	var second="0"+now.getSeconds();
	return year+"-"+month.substring(month.length-2,month.length)+
	"-"+date.substring(date.length-2,date.length)+" "+hour.substring(hour.length-2,hour.length)+
	":"+minute.substring(minute.length-2,minute.length)+
	":"+second.substring(second.length-2,second.length);
} 
//时间戳转化--只要时分秒
function formatDates(now) {
	var now = new Date(now);
	var hour="0"+now.getHours();
	var minute="0"+now.getMinutes();
	var second="0"+now.getSeconds();
	return hour.substring(hour.length-2,hour.length)+
	":"+minute.substring(minute.length-2,minute.length)+
	":"+second.substring(second.length-2,second.length);	
} 
//当前时间的前后几天
function formatDateBefore(now,aa) {
	var date1 = new Date(now),
    time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();//time1表示当前时间
    var date2 = new Date(date1);
    date2.setDate(date1.getDate()-aa);
    var month="0"+(date2.getMonth()+1);
	var date="0"+date2.getDate();
    var time2 = date2.getFullYear()+"-"+month.substring(month.length-2,month.length)+
	"-"+date.substring(date.length-2,date.length);
    console.log(time2)
    return time2;
//	var year=now.getFullYear();
//	var month="0"+(now.getMonth()+1);
//	var date="0"+(n?now.getDate()-n:now.getDate());
//	return year+"-"+month.substring(month.length-2,month.length)+
//	"-"+date.substring(date.length-2,date.length);
}
//时间间隔
function formatDateTime(now){
	var total = (now)/1000;
	var day = parseInt(total / (24*60*60));//计算整数天数
	var afterDay = total - day*24*60*60;//取得算出天数后剩余的秒数
	var hour = parseInt(afterDay/(60*60));//计算整数小时数
	var afterHour = total - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
	var min = parseInt(afterHour/60);//计算整数分
	var afterMin = total - day*24*60*60 - hour*60*60 - min*60;//取得算出分后剩余的秒数
	return day+'天'+hour+'小时'+min+'分'+afterMin+'秒'
}
// 时间天数
function formatDateDay(now){
	var total = (now)/1000;
	var day = parseInt(total / (24*60*60));//计算整数天数
	return day
} 
//返回按钮
$(".goBack-btn").click(function(){
	layer.confirm('确定要返回吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){	
		window.history.go(-1);
	}, function(){
		layer.closeAll();
	});
});

//来源数组
var baseSourceArr = [];
baseSourceArr['tencent'] = "腾讯";
baseSourceArr['sina'] = "新浪";
baseSourceArr['pptv'] = "pptv";
baseSourceArr['self'] = "本站";

//用户角色数组
var baseRoleArr = [];
baseRoleArr['yunyingjingli'] = "运营经理";
baseRoleArr['yunyingzhuanyuan'] = "运营专员";
baseRoleArr['admin'] = "管理员";
baseRoleArr['caiwujingli'] = "财务经理";
baseRoleArr['caiwuzhuanyuan'] = "财务专员";
baseRoleArr['jishuzhichi'] = "技术支持";
baseRoleArr['boss'] = "BOSS";
baseRoleArr['superadmin'] = "超级管理员";

//数组去重
function uniq(array){
    var temp = [];
    var index = [];
    var l = array.length;
    for(var i = 0; i < l; i++) {
        for(var j = i + 1; j < l; j++){
            if (array[i] === array[j]){
                i++;
                j = i;
            }
        }
        temp.push(array[i]);
        index.push(i);
    }
    return temp;
}
//货币格式化
function moneyFormat(obj){
	if(obj.toString()!=""){
		var xsdMoney=obj.toString().replace(/[^\d.]/g,"").split(".");
		var intPlace = xsdMoney[0];	
		var intMum = intPlace.replace(/\b(0+)/gi,"").split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('');
		if(intMum==''){
			intMum = 0;
		}
		if(xsdMoney.length==1){
	        return intMum+'.00';
	    }else if(xsdMoney.length>1){
	        if(xsdMoney[1].length<2){
	            return intMum+'.'+xsdMoney[1]+'0';
	        }else{
	        	return intMum+'.'+xsdMoney[1].substring(0,2);
	        }
	    }
	}else{
		return '';
	}
    
}
function clearNoNum(obj) {
    obj.value = obj.value.replace(/[^\d.]/g,"");  
    obj.value = obj.value.replace(/[^\d.]/g,"");  
    obj.value = obj.value.replace(/^\./g,"");  
    obj.value = obj.value.replace(/\.{2,}/g,"."); 
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');  

}  
jQuery.fn.serializeObject = function () {
	var formData = {};
	var formArray = this.serializeArray();
	 for(var i = 0, n = formArray.length; i < n; ++i){
		 formData[formArray[i].name] = formArray[i].value;
	 }
	 return formData;
};
//修改密码
$(".resetPwd").click(function(){
	layer.open({
	  type: 2, 
	  title:false,
	  area: ['500px', '350px'],
	  content:['resetPwd.html']
	}); 
});
// 获取登录人员信息
function getAdminName(){
	$.ajax({
        url: '../back/admin/getInfo',
        type: 'get',
        async:false,
        data: {
            
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$(".admin-name").html("欢迎"+r.data.realname)
		}
    })
}


