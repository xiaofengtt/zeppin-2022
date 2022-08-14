var submitFlag = true;
var userid = url("?userid") != null ? url("?userid") : "";//用户id
var hkszdCN = "";//户口所在地
$(function(){
	getUserInfo();
//	getHkArea("1", "", "","true");
});
//监听input变化 
$('input').bind('input propertychange', function() {
	changeInput();
});
$('input').blur(function() {
	$(window).scrollTop(0);
});
function changeInput(){
	var cbrxm = $("input.cbrxm").val().replace(/(^\s*)|(\s*$)/g,"");
	var sjh = $("input.sjh").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
	if(cbrxm!=""&&sjh!=""&&hkszd!=""&&hkxz!=""&&checkPhone(sjh)){
		$(".surebtn").addClass("light");
		submitFlag = true;
	}else{
		$(".surebtn").removeClass("light");
		submitFlag = false;
	}
}
$(".surebtn").click(function(){
	if(submitFlag){	
		editSave();
	}else{
		var cbrxm = $("input.cbrxm").val().replace(/(^\s*)|(\s*$)/g,"");
		var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
		var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
		var sjh = $("input.sjh").val().replace(/(^\s*)|(\s*$)/g,"");
		if(!checkPhone(sjh)){
			layerIn("请填写正确的手机号");
			return false;
		}else if(hkszd == ''){
			layer.msg("请选择户口所在地");
			return false;
		}else if(hkxz == ''){
			layer.msg("请选择户口性质");
			return false;
		}
	}
});

//获取用户信息
function getUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/insured/get",
		async: true,
		data: {
			'uuid':userid,
			'time':getTime()			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			//参保人信息
			$(".cbrxm").val(r.data.name);//参保人姓名
			$(".sjh").val(r.data.mobile);//参保人手机号
			
			$(".hkszd").val(r.data.householdareaName);//参保人户口所在地
			hkszdCN = r.data.householdareaName;
			$(".hkxz").val(r.data.householdtype);//参保人户口性质
			$(".mz").val(r.data.nationality);//参保人民族
			$(".xl").val(r.data.education);//参保人学历
			$(".cjgzsj").val(r.data.worktimeCN?r.data.worktimeCN.substring(0,7):'');//参保人参加工作时间
			$("#cjgzsj").val(r.data.worktimeCN?r.data.worktimeCN:'');//参保人参加工作时间
			$(".yx").val(r.data.email);//参保人邮箱
			$(".grsf").val(r.data.duty);//参保人身份
			$("#cbdq").val(r.data.householdarea)//户口所在地id
			//初始化参加工作时间
			getCjgzsj(r.data.worktimeCN);
			//初始化参保人身份
			getCbrsf(r.data.duty);
			//初始化学历
			getXl(r.data.education);
			//初始化民族
			getMz(r.data.nationality);
			//初始化户口性质
			getHkxz(r.data.householdtype);
			
			getHkArea("","","1",r.data.householdareaParent);
			
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
		layerIn("服务器错误");
	});
}
//修改保存
function editSave(){
	var sjh = $("input.sjh").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
	var email = $("input.yx").val().replace(/(^\s*)|(\s*$)/g,"");
	var education = $("input.xl").val().replace(/(^\s*)|(\s*$)/g,"");
	var worktime = $("input#cjgzsj").val().replace(/(^\s*)|(\s*$)/g,"");
	var nationality = $("input.mz").val().replace(/(^\s*)|(\s*$)/g,"");
	var duty = $("input.grsf").val().replace(/(^\s*)|(\s*$)/g,"");
	if(email!=""&&!checkEmail(email)){
		layerIn("邮箱格式不正确");
		return false;
	}
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/insured/edit",
		async: true,
		data: {
			'uuid':userid,
			'mobile':sjh,
			'householdtype':hkxz,
			'householdarea':$("#cbdq").val(),
			'email':email,
			'education':education,
			'worktime':worktime,
			'nationality':nationality,
			'duty':duty			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			//参保人信息
			layerIn("修改成功");
			setTimeout(function(){
				location.replace("payHasInsuredRecord.html?userid="+userid+"&edit=true");
			},1000);
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
		layerIn("服务器错误");
	});
}

var firstindex=0,secondindex=0;
function getHkArea(pid, scode,level,householdareaParent){
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
    		data7=[];
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
                		if(hkszdCN.substring(0,hkszdCN.indexOf(" "))==el.name){
                			firstindex = index-1;
                		}
                	}else{
                		data7.push(dataList);
                		if(hkszdCN.substring(hkszdCN.indexOf(" ")+1,hkszdCN.length)==el.name){
                			secondindex = index;
                		}
                		
                		              		
                	}
                }
            });
    		if(level =="1"){
    			getHkArea(householdareaParent,"","2");
    		}
    		if(level=="2"){
    			picker1 = new Picker({
        			title: '请选择户口所在地',
        		    data: [data6, data7],
        		    selectedIndex: [firstindex,secondindex]
        		});
    		}
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
