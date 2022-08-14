var jnType = 0; //缴纳类型
var sqWages; //社保基数
var ylWages;//养老、失业社保基数
var gjjWages; //公积金
var grossPay; //税前工资
var isCustom = true;//是否自定义社保基数

(function($){
    $(window).load(function(){
    	var swiper = new Swiper('.swiper-container', {
			mode:'horizontal',
			pagination: '#figure_pagination_1',
			paginationClickable: true,
			autoplay: 4000,
			loop:true,
			autoplayDisableOnInteraction : false 
		}); 
        $('select.select_dq').selectOrDie();
        $("#jsq").click(function(){
        	$(".iframeJSQ").show();
        });
        $(".iframe").css({"height":$(window).height()});
    });
})(jQuery);
$(window).resize(function(){
	$(".iframe").css({"height":$(window).height()});
});

/******社保基数下拉框*****/
function sbjs() {
    var sbjs_selct= $("#sbjs").val();
    if(sbjs_selct== "1"){
        $("#js_yc2").removeAttr("disabled");
    }else {
        $("#js_yc2").val("4624");
        $("#js_yc2").attr("disabled","disabled");
    }
}
/******社保缴纳单选框*****/
function sbjn() {
    var radio_select=$('#sbjn input:radio[name="sq"]:checked').val();
    if(radio_select=="s0"){
        $("#sbjs").hide();
    }else {
        $("#sbjs").show();
    }

}
/******公积金缴纳单选框*****/
function sbjnx(obj) {
    var radio_selectx=$('#gjj_jn input:radio[name="gjj"]:checked').val();
    $(obj).parent().addClass("light").siblings().removeClass("light");
    if(radio_selectx=="g0"){
        $("#gjj_js").hide();
        $(".cal_box").height("320px");
    }else {
        $("#gjj_js").show();
        $(".cal_box").height("400px");
    }

}
/******公积金基数下拉框*****/
function gjj_sbjs() {
    var gjj_js_selct= $("#gjj_sbjs").val();
    if(gjj_js_selct=="1"){
        $("#js_yc").removeAttr("disabled");
    }else {
    	$("#js_yc").val("2273");
        $("#js_yc").attr("disabled","disabled");
    }
}
/****弹窗隐藏显示****/
$(".closeIframe").on("click",function () {
    $(".iframeResult").hide();
    $(".iframeJSQ").hide();
    $("#sqgz").val("");
    $(".w185").click();
    $(".sod_list li[data-value='2']").click();
});

function getSheBao(a){		
	if($(a).val()!="1"){
		$("#js_yc2").val("4624");
		$("#js_yc2").attr("disabled","disabled");
		isCustom = true;
	}else{
		$("#js_yc2").removeAttr("disabled");
		isCustom = false;
	}
}

function getGongJiJin(a){
	if($(a).val()!="1"){
		$("#js_yc").attr("disabled","disabled");
	}else{
		$("#js_yc").removeAttr("disabled");
	}
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
		//社保基数sqWages 养老、失业基数ylWages
		if(isCustom == true){
			sqWages = 4624;
			ylWages = 3082;
		}else{
			sqWages=$("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"");
			ylWages=$("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"");
			if(reg.test(sqWages)==true){
				if(sqWages<4624){
					sqWages = 4624;
				}else if(sqWages>23118){
					sqWages = 23118;
				}
				if(ylWages<3082){
					ylWages = 3082;
				}else if(ylWages>23118){
					ylWages = 23118;
				}else{
					ylWages = ylWages;
				}
			}else{
				sqWages = 4624;
				ylWages = 3082;
			}
		}
		
		
    //公积金基数
    var radio_selectx=$('#gjj_jn input:radio[name="gjj"]:checked').val();
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
    var radio_selectx=$('#gjj_jn input:radio[name="gjj"]:checked').val();    
    grossPay = $("#sqgz").val().replace(/(^\s*)|(\s*$)/g,"");
	var str = '<tr>';
	str += '<td colspan="2">养老</td>';
	str += '<td width="20%">8%</td>';
	str += '<td width="18%">'+ numericValues(ylWages,0.08) +'</td>';
	str += '<td width="19%">19%</td>';
	str += '<td width="17%">'+ numericValues(ylWages,0.19) +'</td>';
	str += '</tr>';
	str += '<td colspan="2">医疗</td>';
	str += '<td width="20%">2%+3</td>';
	str += '<td width="18%">'+ (sqWages*0.02+3).toFixed(2) +'</td>';
	str += '<td width="19%">10%</td>';
	str += '<td width="17%">'+ numericValues(sqWages,0.1) +'</td>';
	str += '</tr>';
	
	str += '<td colspan="2">失业</td>';
	str += '<td width="20%">0.2%</td>';
	str += '<td width="18%">'+ numericValues(ylWages,0.002) +'</td>';
	str += '<td width="19%">0.8%</td>';
	str += '<td width="17%">'+ numericValues(ylWages,0.008) +'</td>';
	str += '</tr>';
	str += '<td colspan="2">工伤</td>';
	str += '<td width="20%">0%</td>';
	str += '<td width="18%"> 0.00 </td>';
	str += '<td width="19%">0.2%</td>';
	str += '<td width="17%">'+ numericValues(sqWages,0.002) +'</td>';
	str += '</tr>';
	str += '<td colspan="2">生育</td>';
	str += '<td width="20%">0%</td>';
	str += '<td width="18%"> 0.00 </td>';
	str += '<td width="19%">0.8%</td>';
	str += '<td width="17%">'+ numericValues(sqWages,0.008) +'</td>';
	str += '</tr>';
	str += '<td colspan="2">个人税额</td>';
	str += '<td width="20%">-</td>';
	str += '<td width="18%" class="gsAmount"></td>';
	str += '<td width="19%">-</td>';
	str += '<td width="17%">0.00</td>';
	str += '</tr>';
	if(radio_selectx != "g0"){
		str += '<td colspan="2">公积金</td>';
		str += '<td width="20%">12%</td>';
		str += '<td width="18%">'+ numericValues(gjjWages,0.12) +'</td>';
		str += '<td width="19%">12%</td>';
		str += '<td width="17%">'+ numericValues(gjjWages,0.12) +'</td>';
		str += '</tr>';
	}
	str += '<tr>';		
	str += '</tr>';
	$("#ssfunddetail").html(str);
	$(".iframeResult").show();
    $(".iframeJSQ").hide();
    var shsr;//税后收入
    var grjn;//个人缴纳
    var gsjn;//公司缴纳
		if(radio_selectx != "g0"){
			//缴纳社保和公积金
			grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,0.002)+numericValue(gjjWages,0.12)).toFixed(2); //个人缴纳
			gsjn = (numericValue(ylWages,0.19)+numericValue(sqWages,0.1)+numericValue(ylWages,0.008)+numericValue(sqWages,0.002)+numericValue(sqWages,0.008)+numericValue(gjjWages,0.12)).toFixed(2);//公司缴纳
		}else{
			//缴纳社保不缴纳公积金
			grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,0.002)).toFixed(2); //个人缴纳
			gsjn = (numericValue(ylWages,0.19)+numericValue(sqWages,0.1)+numericValue(ylWages,0.008)+numericValue(sqWages,0.002)+numericValue(sqWages,0.008)).toFixed(2);//公司缴纳
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
//    	if(radio_selectx != "g0"){
//			//缴纳社保和公积金
//			shsr = (Number(grossPay)-numericValue(ylWages,0.08)-numericValue(sqWages,0.02)-3-numericValue(ylWages,0.002)-numericValue(gjjWages,0.12)-gsjsTax).toFixed(2);//税后收入
//		}else{
//			//缴纳社保不缴纳公积金
//			shsr = (Number(grossPay)-numericValue(ylWages,0.08)-numericValue(sqWages,0.02)-3-numericValue(ylWages,0.002)-gsjsTax).toFixed(2);//税后收入
//		}
//    $("#shsrSet").attr("value",shsr);//税后收入
    grjn = (numericValue(ylWages,0.08)+numericValue(sqWages,0.02)+3+numericValue(ylWages,0.002)+gsjsTax).toFixed(2); 
    $("#grjnSet").attr("value",grjn); //个人缴纳
    $("#gsjnSet").attr("value",gsjn); //公司缴纳
    $(".gsAmount").html(gsjsTax.toFixed(2));//个人缴税
//    $("#gycbSet").attr("value",(Number(gsjn)+Number(grossPay)).toFixed(2));//雇佣成本
    
}

//计算数值
function numericValue(a1,a2){
	return Number((a1*a2).toFixed(2));
}
//计算数值
function numericValues(a1,a2){
	return (a1*a2).toFixed(2);
}
//随机在线咨询
function consulte(){
	var urls = new Array();
	urls[0] = "http://wpa.qq.com/msgrd?v=3&uin=44255428&site=qq&menu=yes";
	urls[1] = "http://wpa.qq.com/msgrd?v=3&uin=2494299642&site=qq&menu=yes";
	n = Math.floor(Math.random()*2);
	/* $("#onclineConsulte").attr("href",urls[n]); */
	$("#onclineConsulte").attr("href","http://wpa.qq.com/msgrd?v=3&uin=2128762975&site=qq&menu=yes");
	document.getElementById("onclineConsulte").click();
}