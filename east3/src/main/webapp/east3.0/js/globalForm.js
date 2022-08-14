$(document).ready(function() {
    var id = url("?id");
    var type = $("#formType").val();

    if (id != null) {
        update(type);
        $(".breadcrumb").find(".active").html("修改");
    } else {
        add(type);
        $(".breadcrumb").find(".active").html("添加");
    }

    function add(type) {
        $("#inputForm").attr("action", "../rest/backadmin/" + type + "/add");
        $('.form-checkbox').each(function(){
        	$(this).selectpicker({
            	"multipleSeparator": ";",
            	"noneSelectedText": ""
            });
        });
        if($("select.form-product").val()!=""){
        	$('#xtxmbhSelect').selectpicker('val',$("select.form-product").val());                        		 
    		$(".selectpicker").selectpicker('refresh');
        }        
    }
    $('.form-product').change(function(){
    	if($("select.form-product").val()!=""){
        	$('#xtxmbhSelect').selectpicker('val',$("select.form-product").val());                        		 
    		$(".selectpicker").selectpicker('refresh');
        }     
    });
    function update(type) {
        $("#inputForm").attr("action", "../rest/backadmin/" + type + "/edit");
        $.ajax({
                url: '../rest/backadmin/' + type + '/editGet',
                type: 'get',
                data: {
                    "id": id,
                    "dataproduct":$("select.form-product").val()
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    for (var key in r.data) {
                        $("input[name=" + key + "]").val(r.data[key]);
                        $("select[name=" + key + "]").val(r.data[key]);
                        if(key == "zjyyfs"||key == "gsywzl"){
                        	var values= [] ;
                        	if(r.data[key]){
                        		if(r.data[key].indexOf(";")>-1){
                            		values = r.data[key].split(";");
                            	}else{
                            		values[0] = r.data[key];
                            	}
                            	for(index in values){
                            		 $("select[name=" + key + "] option[value='"+values[index]+"']").attr("selected","selected");
                            	}
                        	}                        	
                        }else if(key=='ssdq'||key=='htqdd'||key=='zjtxdq'||key=='khqy'||key=='txdq'||key=='zcdq'||key=='ztjd'){
                        	if(r.data[key]){
                        		if(r.data[key].length>3){
                            		//境内
                            		$("select.form-ssdq").find("option[value='1']").attr("selected",true);
                            		var array = [r.data[key].substring(0, 2)+"0000",r.data[key].substring(0, 4)+"00",r.data[key].substring(0, 6)];
                            		getArea("form-province", "1", "","",array[0]);
                            		getArea("form-city", "2", "",array[1],array[1]);
                            		getArea("form-county", "3", "",array[2],array[2]);
                            	}else{
                            		//境外
                            		$("select.form-ssdq").find("option[value='2']").attr("selected",true);
                            		$("input[name='"+key+"']").parent().find(".form-jn").css("display","none");  
                            		$("input[name='"+key+"']").parent().find(".form-jw").removeClass("form-ssgb"); 
                            		$('#ssdq').selectpicker('val',r.data[key]);                        		 
                            		$(".selectpicker").selectpicker('refresh');
                            	}
                        	}                        	
                        }else if(key=='ssgb'){
                        	$('#ssgb').selectpicker('val',r.data[key]);                        		 
                    		$(".selectpicker").selectpicker('refresh');
                        }else if(key=='jzdq'){
                        	if(r.data[key]){
                        		if(r.data[key].length>3){
                            		//境内
                            		$("select.form-judq").find("option[value='1']").attr("selected",true);
                            		var array = [r.data[key].substring(0, 2)+"0000",r.data[key].substring(0, 4)+"00",r.data[key].substring(0, 6)];
                            		getArea("form-province-judq", "1", "","",array[0]);
                            		getArea("form-city-judq", "2", "",array[1],array[1]);
                            		getArea("form-county-judq", "3", "",array[2],array[2]);
                            	}else{
                            		//境外
                            		$("select.form-judq").find("option[value='2']").attr("selected",true);
                            		$("input[name='jzdq']").parent().find(".form-jn").css("display","none");  
                            		$("input[name='jzdq']").parent().find(".form-jw").removeClass("form-ssgb"); 
                            		$('#jzdq').selectpicker('val',r.data[key]);                        		 
                            		$(".selectpicker").selectpicker('refresh');
                            	}
                        	}                        	
                        }else if(key=='xtxmbh'||key=='xtxmbm'){
                        	$('#xtxmbhSelect').selectpicker('val',r.data[key]);                        		 
                    		$(".selectpicker").selectpicker('refresh');
                        }else if(key=='bz'||key=='dbbz'||key=='hsbz'||key=='jybz'||key=='jsbz'){
                        	$('#bzSelect').selectpicker('val',r.data[key]);                        		 
                    		$("#bzSelect").selectpicker('refresh');
                        }
                    }
                    $('.form-checkbox').each(function(){
                    	$(this).selectpicker({
                        	"multipleSeparator": ";",
                        	"noneSelectedText": ""
                        });
                    });
                    autocomplete();
                             
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }
    
    lay(".form-date").each(function(){
    	laydate.render({
    		elem: this,
    		format: "yyyyMMdd"
    	});
	});
    
    lay(".form-time").each(function(){
    	laydate.render({
    		elem: this,
    		type: "datetime",
    		format: "yyyyMMddHHmmss"
    	});
	});
    
    $("#btnSubmit").click(function() {
        $("select.input-required,input.input-required").each(function(){
        	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")==""){
        		layer.msg("请填写完整信息");
        		flagSubmit = false;
        		$('#myModal').modal('hide');
        		return false;
        	}
        	flagSubmit = true;
        });
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        
        var fFailed = false;
        var mFailed = ""
        $("input.form-decimal2").each(function(i,ttest){
        	var reg = /^(\-)?[0-9]+(.[0-9]{2})$/;
	     	if (ttest.value != '' && !reg.test(ttest.value)) {
	     		fFailed = true;
	     		mFailed = ttest.title + '需填写保留两位小数数据';
	     		return;
	     	}
        })
        $("input.form-decimal3").each(function(i,ttest){
        	var reg = /^(\-)?[0-9]+(.[0-9]{3})$/;
	     	if (ttest.value != '' && !reg.test(ttest.value)) {
	     		fFailed = true;
	     		mFailed = ttest.title + '需填写保留三位小数数据';
	     		return;
	     	}
        })
        $("input.form-decimal4").each(function(i,ttest){
        	var reg = /^(\-)?[0-9]+(.[0-9]{4})$/;
	     	if (ttest.value != '' && !reg.test(ttest.value)) {
	     		fFailed = true;
	     		mFailed = ttest.title + '需填写保留四位小数数据';
	     		return;
	     	}
        })
        $("input.form-money").each(function(i,ttest){
        	var reg = /^(\-)?\d+(\.\d{1,2})?$/;
        	if (ttest.value != '' && !reg.test(ttest.value)) {
        		fFailed = true;
        		mFailed = ttest.title + '需填写金额类型数据';
        		return;
        	}
        })
        
        if(fFailed){
        	$('#myModal').modal('hide');
        	layer.msg(mFailed,{
        		time: 3000
        	})
        	return false;
        }
        
        $("#inputForm").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    location.href = document.referrer;
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    });

    $("#btnCancel").click(function() {
        location.href = document.referrer;
    });
    //select 模糊查询
    $('.form-bz,.form-ssgb,.form-ssdgb,select[name="xtxmbm"],select[name="xtxmbh"]').selectpicker({  
        'selectedText': 'cat'  
    }); 
    //所属地区 境内 境外
    $(".form-ssdq").change(function(){
    	var ssdq=$(this).val();
    	if(ssdq=="1"){
    		$(this).parent().find(".form-jn").css("display","inline-block");
    		$(this).parent().find(".form-jw").addClass("form-ssgb");
    		$("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val($(".form-county option:selected").val());
    		$("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val($(".form-county option:selected").val());
    	}else if(ssdq=="2"){
    		$(this).parent().find(".form-jn").css("display","none");
    		$(this).parent().find(".form-jw").removeClass("form-ssgb");
    		$("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val($(this).parent().find("select#ssdq").val()); 
    		$("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val($(this).parent().find("select#ssdq").val()); 
    	}
    });
    $(".form-judq").change(function(){
    	var ssdq=$(this).val();
    	if(ssdq=="1"){
    		$(this).parent().find(".form-jn").css("display","inline-block");
    		$(this).parent().find(".form-jw").addClass("form-ssgb");
    		$("input[name='jzdq']").val($(".form-county-judq option:selected").val());
    	}else if(ssdq=="2"){
    		$(this).parent().find(".form-jn").css("display","none");
    		$(this).parent().find(".form-jw").removeClass("form-ssgb");
    		$("input[name='jzdq']").val($(this).parent().find("select#jzdq").val());    	
    	}
    });
    $("#ssdq").change(function(){
		$("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val($(this).parent().find("select.form-jw").val());
		$("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val($(this).parent().find("select.form-jw").val());
	});
    $("#jzdq").change(function(){
		$("input[name='jzdq']").val($(this).parent().find("select.form-jw").val());
	});
});
//获取地区
function getArea(className, level, pid,scode,scodeValue) {
    $.ajax({
        url: '../rest/backadmin/area/list',
        type: 'get',
        data: {
            "level": level,
            "pid": pid,
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime(),
            "scode":scode
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$("." + className).html("<option value='0'>请选择</option>");
            $.each(r.data, function(index, el) {
            	$("." + className).removeAttr("disabled");
            	$("." + className).next().removeAttr("disabled");
                if (el.level == 1 && el.name == "全国") {

                } else {
                    $("." + className).append("<option id=" + el.id + " value = " + el.scode + ">" + el.name + "</option>");
                    if(scodeValue!=""){
                    	$("." + className).find("option[value='"+scodeValue+"']").attr("selected",true);
                    }
                }
            });
            if(r.data.length==0){
            	$("." + className).html("").attr("disabled","disabled");
            	$("." + className).html("").next().attr("disabled","disabled");
            	if(className=="form-city"||className=="form-county"){
            		$("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val($(".form-province").val());
            		$("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val($(".form-province").val());
            	}else if(className=="form-city-judq"||className=="form-county-judq"){
            		$("input[name='jzdq']").val($(".form-province-judq").val());
            	}
            }

            $(".form-province").change(function(event) {
                getArea("form-city", "2", $(".form-province option:selected").attr("id"));
                $(".form-county").html("<option value='0'>请选择</option>");
                $("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val("");
                $("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val("");
            });
            $(".form-city").change(function(event) {
                getArea("form-county", "3", $(".form-city option:selected").attr("id"));
                $("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val("");
                $("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val("");
            });
            $(".form-county").change(function(){
            	$("input[name='ssdq'],input[name='zcdq'],input[name='ztjd']").val($(this).val());
            	$("input[name='htqdd'],input[name='zjtxdq'],input[name='khqy'],input[name='txdq']").val($(this).val());
            });
            $(".form-province-judq").change(function(event) {
                getArea("form-city-judq", "2", $(".form-province-judq option:selected").attr("id"));
                $(".form-county-judq").html("<option value='0'>请选择</option>");
                $("input[name='jzdq']").val("");
            });
            $(".form-city-judq").change(function(event) {
                getArea("form-county-judq", "3", $(".form-city-judq option:selected").attr("id"));
                $("input[name='jzdq']").val("");
            });
            $(".form-county-judq").change(function(){
            	$("input[name='jzdq']").val($(this).val());
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
$("#formGoBackBtn").click(function(){
	window.history.go(-1);
});
