var jnType = 0; //缴纳类型
var sqWages; //社保基数
var ylWages;//养老、失业社保基数
var gjjWages; //公积金
var grossPay; //税前工资
var isCustom = false;//是否自定义社保基数

/******公积金缴纳单选框*****/
function sbjnx(obj) {
    var radio_selectx=$('input:radio[name="gjj"]:checked').val();
    $(obj).parent().addClass("light").siblings().removeClass("light");
    if(radio_selectx=="g0"){
        $("#gjj_jn").hide();
        $(".cal_box").height("320px");
    }else {
        $("#gjj_jn").show();
        $(".cal_box").height("400px");
    }

}
//社保基数按钮选择
function sbjs(obj){
	if($(obj).hasClass("light")){
		$(obj).removeClass("light");
		$("#js_yc2").removeAttr("disabled").val("");
		isCustom = false;
	}else{
		$(obj).addClass("light");
		$("#js_yc2").val("5080");
        $("#js_yc2").attr("disabled","disabled");
        isCustom = true;
	}
}
//公积金基数按钮选择
function sbjn(obj){
	if($(obj).hasClass("light")){
		$(obj).removeClass("light");
		$("#js_yc").removeAttr("disabled").val("");
	}else{
		$(obj).addClass("light");
		$("#js_yc").val("2273");
        $("#js_yc").attr("disabled","disabled");
	}
}
//重置
function reset(){
	$(".calculationBox").show();
	$(".result").hide();
	$("#sqgz").val("");
	$("#js_yc2").val("");
    $("#js_yc2").removeAttr("disabled");
	$(".zdjs").removeClass("light");
	$("#js_yc").val("");
    $("#js_yc").removeAttr("disabled");
    $(".w185").click();
    
}
//添加正则校验
var reg = /^[1-9][0-9]*(\.\d{1,2})?$/;
var re = new RegExp(reg);
//计算
function compute(){
		//税前工资
		if($("#sqgz").val().replace(/(^\s*)|(\s*$)/g,"")==""){
			layer.msg("请输入税前工资");
			return false;
		}else if(reg.test($("#sqgz").val().replace(/(^\s*)|(\s*$)/g,""))==false){
			layer.msg("请输入正确的工资");
			return false;
		}
		//社保基数
		if($("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"")==""){
			layer.msg("请输入社保基数");
			return false;
		}else if(reg.test($("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,""))==false){
			layer.msg("请输入正确的社保基数");
			return false;
		}
		//公积金基数
		if($("#js_yc").val().replace(/(^\s*)|(\s*$)/g,"")==""&&$('input:radio[name="gjj"]:checked').val()=="g1"){
			layer.msg("请输入公积金基数");
			return false;
		}else if(reg.test($("#js_yc").val().replace(/(^\s*)|(\s*$)/g,""))==false&&$('input:radio[name="gjj"]:checked').val()=="g1"){
			layer.msg("请输入正确的公积金基数");
			return false;
		}
		//社保基数sqWages 养老、失业基数ylWages
		if(isCustom == true){
			sqWages = 5080;
			ylWages = 3387;
		}else{
			sqWages=$("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"");
			ylWages=$("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"");
			if(reg.test(sqWages)==true){
				if(sqWages<5080){
					sqWages = 5080;
				}else if(sqWages>23118){
					sqWages = 23118;
				}
				if(ylWages<3387){
					ylWages = 3387;
				}else if(ylWages>23118){
					ylWages = 23118;
				}else{
					ylWages = ylWages;
				}
			}else{
				sqWages = 5080;
				ylWages = 3387;
			}
		}
		
		
    //公积金基数
    var radio_selectx=$('input:radio[name="gjj"]:checked').val();
    gjjWages = $("#js_yc").val().replace(/(^\s*)|(\s*$)/g,"");
    if(radio_selectx!="g0" && reg.test(gjjWages)==true){
        if(gjjWages<2273){
				gjjWages = 2273;
			}else if(gjjWages>23118){
				gjjWages = 23118;
			}
    }else{
    		gjjWages = 2273;
    }
    setSsFundDataToHtml();
}

//回显工资及社保明细
function setSsFundDataToHtml(obj){
    var radio_selectx=$('input:radio[name="gjj"]:checked').val();    
    grossPay = $("#sqgz").val().replace(/(^\s*)|(\s*$)/g,"");
    var hkxz = $("#hkxz").val();
    var sybl;
    if(hkxz=="1"){
    	sybl = 0.002;
    	$(".sypercent").html("0.2%");
    }else{
    	sybl = 0;
    	$(".sypercent").html("0%");
    }
	$(".value1").html(numericValues(ylWages,0.08));
	$(".value2").html(numericValues(ylWages,0.19));
	$(".value3").html(numericValues(sqWages,0.02));
	$(".value4").html(numericValues(sqWages,0.09));
	$(".value6").html(numericValues(sqWages,0.01));
	$(".value7").html(numericValues(ylWages,sybl));
	$(".value8").html(numericValues(ylWages,0.008));
	$(".value10").html(numericValues(sqWages,0.004));
	$(".value12").html(numericValues(sqWages,0.008));
	if(radio_selectx != "g0"){
		$(".gjjLi").show();
		$(".value13").html(numericValues(gjjWages,0.12));
		$(".value14").html(numericValues(gjjWages,0.12));
	}else{
		$(".gjjLi").hide();
	}
	
	$(".result").show();
    $(".calculationBox").hide();
    var shsr;//合计缴纳
    var grjn;//个人缴纳
    var gsjn;//公司缴纳
		if(radio_selectx != "g0"){
			//缴纳社保和公积金
			grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,sybl)+numericValue(gjjWages,0.12)).toFixed(2); //个人缴纳
			
		}else{
			//缴纳社保不缴纳公积金
			grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,sybl)).toFixed(2); //个人缴纳
			
		}
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
    if(radio_selectx != "g0"){
			//缴纳社保和公积金
			grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,sybl)+numericValue(gjjWages,0.12)+gsjsTax).toFixed(2); //个人缴纳
			gsjn = (numericValue(ylWages,0.19)+numericValue(sqWages,0.1)+numericValue(ylWages,0.008)+numericValue(sqWages,0.004)+numericValue(sqWages,0.008)+numericValue(gjjWages,0.12)).toFixed(2);//公司缴纳
			shsr = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,sybl)+numericValue(gjjWages,0.12)+numericValue(ylWages,0.19)+numericValue(sqWages,0.1)+numericValue(ylWages,0.008)+numericValue(sqWages,0.004)+numericValue(sqWages,0.008)+numericValue(gjjWages,0.12)+gsjsTax).toFixed(2);
		}else{
			//缴纳社保不缴纳公积金
			grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,sybl)+gsjsTax).toFixed(2); //个人缴纳
			gsjn = (numericValue(ylWages,0.19)+numericValue(sqWages,0.1)+numericValue(ylWages,0.008)+numericValue(sqWages,0.004)+numericValue(sqWages,0.008)).toFixed(2);//公司缴纳
			shsr = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,sybl)+numericValue(ylWages,0.19)+numericValue(sqWages,0.1)+numericValue(ylWages,0.008)+numericValue(sqWages,0.004)+numericValue(sqWages,0.008)+gsjsTax).toFixed(2);
		}
    $(".personalPay").html(grjn); //个人缴纳
    $(".companyPay").html(gsjn); //公司缴纳
    $(".value15").html(gsjsTax.toFixed(2));//个人缴税
    $(".totlePay").html(shsr)//合计缴纳
    
}

//计算数值
function numericValue(a1,a2){
	return Number((a1*a2).toFixed(2));
}
//计算数值
function numericValues(a1,a2){
	return (a1*a2).toFixed(2);
}

$("#js_yc2").blur(function(){
	var value = $("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"");
	if(value<3387){
		value = 3387;
	}else if(value>23118){
		value = 23118;
	}else{
		value = value;
	}
	$("#js_yc2").val(value);
});
$("#js_yc").blur(function(){
	var value = $("#js_yc").val().replace(/(^\s*)|(\s*$)/g,"");
	if(value<2273){
		value = 2273;
	}else if(value>23118){
		value = 23118;
	}else{
		value = value;
	}
	$("#js_yc").val(value);
});
