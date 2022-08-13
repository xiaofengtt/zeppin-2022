<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>决算表</title>
		<%
			String path = request.getContextPath();
		%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/jquery.calculation.js"></script>
		<style type="text/css">
<!--
td {
	border-bottom: 1px dashed #8bbedc;
}
.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
-->
        </style>
</head>
	<script type="text/javascript">
	 $(document).ready(function(){
	 $("input[name^=wenben_item_]").sum("keyup","#other_item_5");
		 $("input[name^=dianzi_item_]").sum("keyup","#other_item_6");
		$("input[name^=qty_item_]").bind("keyup", recalc);
		$("input[name^=price_item_]").bind("keyup", recalc);
		$("input[name^=time_item_]").bind("keyup", recalc);
		$("input[name^=other_item_]").bind("keyup", recalc);
		$("input[name^=wenben_item_]").bind("keyup", recalc);
		$("input[name^=dianzi_item_]").bind("keyup", recalc);
		$("input[name^=other_item_]").bind('keydown', function(e){
    		DigitInput(this, e, '');
		}).css("ime-mode", "disabled");
		$("input[name^=price_item_]").bind('keydown', function(e){
    		DigitInput(this, e, '');
		}).css("ime-mode", "disabled");
		$("input[name^=qty_item_]").bind('keydown', function(e){
    		DigitInput(this, e, 'int');
		}).css("ime-mode", "disabled");
		$("input[name^=time_item_]").bind('keydown', function(e){
    		DigitInput(this, e, 'int');
		}).css("ime-mode", "disabled");
		$("input[name^=person_]").bind('keydown', function(e){
    		DigitInput(this, e, 'int');
		}).css("ime-mode", "disabled");
		$("input[name^=wenben_item_]").bind('keydown', function(e){
    		DigitInput(this, e, 'int');
		}).css("ime-mode", "disabled");
		$("input[name^=dianzi_item_]").bind('keydown', function(e){
    		DigitInput(this, e, 'int');
		}).css("ime-mode", "disabled");
		$("input[name^=xueshi_item_]").bind('keydown', function(e){
    		DigitInput(this, e, 'int');
		}).css("ime-mode", "disabled");
		 recalc();
		 
		 $("input[name^=sum]").sum("keyup", "#totalSum");
		 $("input[name^=person]").sum("keyup", "#total_person_1");
		 
	 });
	 var money;
	 function recalc(){
		$("[id^=total_item]").calc(
			// the equation to use for the calculation
			"qty * price * time",
			// define the variables used in the equation, these can be a jQuery object
			{
				qty: $("input[name^=qty_item_]"),
				price: $("input[name^=price_item_]"),
				time: $("input[name^=time_item_]")
			},
			// define the formatting callback, the results of the calculation are passed to this function
			function (s){
				// return the number as a dollar amount
				return s.toFixed(2);
			},
			// define the finish callback, this runs after the calculation has been complete
			function ($this){
				// sum the total of the $("[id^=total_item]") selector
				var sum = $this.sum()+$("[id^=other_item]").sum();
				money = sum;
				$("#grandTotal").text(
					// round the results to 2 digits
					sum.toFixed(2)
				);
			}
		);
	}
	 
	
	// input只能输入数字和小数点
	function DigitInput(el,e,type) {
	    //8：退格键、46：delete、37-40： 方向键
	    //48-57：小键盘区的数字、96-105：主键盘区的数字
	    //110、190：小键盘区和主键盘区的小数
	    //189、109：小键盘区和主键盘区的负号
	    var e = e || window.event; //IE、FF下获取事件对象
	    var cod = e.charCode||e.keyCode; //IE、FF下获取键盘码
	    //屏蔽shift键的特殊字符
	    if(e.shiftKey) notValue(e);
	    //小数点处理
	    if (cod == 110 || cod == 190){
	    	if(type == 'int'){
	    		notValue(e);
	    	}else{
	        	(el.value.indexOf(".")>=0 || !el.value.length) && notValue(e);
	        }
	    } else {
	        if(cod!=8 && cod != 46 && (cod<37 || cod>40) && (cod<48 || cod>57) && (cod<96 || cod>105)) notValue(e);
	    }
	    function notValue(e){
	        e.preventDefault ? e.preventDefault() : e.returnValue=false;
	    }
	}
	
	
	function check(){ 
	
	//return false;
		//验证单选框的情况
		reg=/^\d+(\.\d+)?$/ ; 
		total = $("#grandTotal").text();
		flag = false;
		$("[id^=other_item]").each(function(i){
			var s = i+1;
			 if(!reg.test($(this).val())){  
            	flag = true;
        	 }
        	 
		});
		
		flag1 = false;
		$("[id^=note_item]").each(function(i){
			var s = i+1;
			 if($(this).val()==''){  
            	flag1 = true;
        	 }
		});
		$("[id^=wenben_item]").each(function(i){
			var s = i+1;
			 if($(this).val()==''){  
            	flag1 = true;
        	 }
		});
		$("[id^=dianzi_item]").each(function(i){
			var s = i+1;
			 if($(this).val()==''){  
            	flag1 = true;
        	 }
		});
		$("[id^=xueshi_item]").each(function(i){
			var s = i+1;
			 if($(this).val()==''){  
            	flag1 = true;
        	 }
		});
		$("#msg").html("");
		if(flag){
			$("#msg").html("<b>所有经费均不能为空!</b>");
			return false;
		}
		
		if(flag1){
			$("#msg").html("<b>所有计算说明均不能为空!</b>");
			return false;
		}
		//调研
		diaoyan = $("[id^=other_item]:lt(3)").sum()/total;
		//alert("diaoyan"+$("[id^=other_item]:lt(3)").sum());
		//学员
		xueyuan = ($("[id^=total_item]:lt(2)").sum()+$("[id^=other_item]:eq(3)").sum())/total;
		//alert($("[id^=total_item]:lt(2)").sum()+$("[id^=other_item]:eq(3)").sum());
		//专家
		//alert("专家"+$("[id^=total_item]:lt(6):gt(1)").sum());
		zhuanjia = $("[id^=total_item]:lt(6):gt(1)").sum()/total;
		//学习资料
		xuexiziliao = $("input[name^=price_item_6]").val();
		//alert("xuexiziliao"+xuexiziliao);
		//教学
		jiaoxue = ($("[id^=total_item]:eq(6)").sum()+$("[id^=other_item]:lt(6):gt(3)").sum())/total;
		//alert("jiaoxue"+$("[id^=total_item]:eq(6)").sum()+$("[id^=other_item]:lt(6):gt(3)").sum());
		//场地
		//alert("changdi"+$("[id^=total_item]:lt(9):gt(6)").sum());
		changdi = $("[id^=total_item]:lt(9):gt(6)").sum()/total;
		//项目
		//alert("xiangmu"+$("[id^=other_item]:lt(8):gt(5)").sum());
		xiangmu = $("[id^=other_item]:lt(8):gt(5)").sum()/total;
		//其他
		//alert($("[id^=total_item]:eq(9)").sum()+$("[id^=other_item]:lt(11):gt(7)").sum());
		qita = ($("[id^=total_item]:eq(9)").sum()+$("[id^=other_item]:lt(11):gt(7)").sum())/total;//return false;
		if(isNaN(diaoyan)||isNaN(xueyuan)||isNaN(zhuanjia)||isNaN(jiaoxue)||isNaN(changdi)||isNaN(xiangmu)||isNaN(qita)){
			return false;
		}
		
		 num_1 = $("#qty_item_1").val();
		 num_2 = $("#qty_item_2").val();
		// num_3 = $("#qty_item_5").val();
		 num_4 = $("#qty_item_6").val();
		// num_5 = $("#qty_item_9").val();
	 	 num_total = $("#total_person_1").val();//alert(num_1+","+num_2+","+num_3+","+num_4+","+num_5+","+num_total);
		if(document.getElementById('pbid').value!=''){
			var counts = $("#total_p").val();
			num_total = counts.substring(counts.indexOf('总计')+3,counts.lastIndexOf('人'));
		}
		var num_a = true;
		var num_b = true;
		var num_c = true;
		var num_d = true;
		var num_e = true;
		
		var a = true;
		var b = true;
		var c = true;
		var d = true;
		var e = true;
		var f = true;
		var g = true;
		var h = true;
	
		
		if(diaoyan>0.05){
			yanzhiInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;调研与方案研制费,比例近似为:'+(diaoyan*100).toFixed(1)+'%,超过5%!</div>';
			$("[id^=td_item]:lt(3)").css("color","red");
			 a = false;
		}else{
			yanzhiInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目方案研制过程中所需的会议费、问卷设计与分析劳务费、专家咨询费、专家评审费等	</div>'
			$("[id^=td_item]:lt(3)").css("color","black");
			 a = true;
		};
		
	/*	if(xueyuan<0.4){
			xueyuanInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学员食宿与交通费,比例近似为:'+(xueyuan*100).toFixed(1)+'%,低于40%!</div>';
			$("[id^=td_item]:lt(6):gt(2)").css("color","red");
			//$("#msg").html("<b>学员食宿与交通费,比例近似为:"+(xueyuan*100).toFixed(1)+"%,低于40%!</b>");
			$("#span_qty_1").css("color","red");
			$("#span_qty_2").css("color","red");
			 b = false;
		}else{
			xueyuanInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐费和住宿费是指学员在培训期间的伙食、住宿费用或按有关标准给予的食宿费补贴。交通费是指培训期间学员观摩考察所需交通费用。部分项目包括学员往返基本交通费用，以教育部通知为准。	</div>';
			$("[id^=td_item]:lt(6):gt(2)").css("color","black");
			if(num_a){
				$("#span_qty_1").css("color","black");
			}
			if(num_b){
				$("#span_qty_2").css("color","black");
			}
			 b = true;
		};
		if(zhuanjia<0.25){
			zhuanjiaInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;专家费用,比例近似为:'+(zhuanjia*100).toFixed(1)+'%,低于25%!</div>';
			$("[id^=td_item]:lt(9):gt(5)").css("color","red");
			$("#span_qty_5").css("color","red");
			//$("#msg").html("<b>专家费用,比例近似为:"+(zhuanjia*100).toFixed(1)+"%,低于25%!</b>");
			 c = false;
		}else{
			zhuanjiaInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训实施期间专家的授课费、食宿费、交通费。 </div>';
			$("[id^=td_item]:lt(9):gt(5)").css("color","black");
			if(num_c){
				$("#span_qty_5").css("color","black");
			}
			//$("#msg").html("<b>专家费用,比例近似为:"+(zhuanjia*100).toFixed(1)+"%,低于25%!</b>");
			 c = true;
		};
		*/
		if(xuexiziliao>100){
		xuexiziliaoInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学员学习资料费个人标准为:'+xuexiziliao+'/人,超过100/人!</div>';
			$("span_1").css("color","red");
			$("#span_qty_6").css("color","red");
			 d = false;
		}else{
			xuexiziliaoInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学员学习所必需的培训资料费用，包括文本资料、光盘等费用。</div>';
			$("[id^=span]:eq(0)").css("color","black");
			 d = true;
		};
		/*
		if(jiaoxue>0.08){
		jiaoxueInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;教学资源费用,比例近似为:'+(jiaoxue*100).toFixed(1)+'%,超过8%!</div>';
		$("[id^=td_item]:lt(12):gt(8)").css("color","red");
		//	$("#msg").html("<b>教学资源费用,比例近似为:"+(jiaoxue*100).toFixed(1)+"%,超过8%!</b>");
		$("#span_qty_6").css("color","red");
			 e = false;
		}else{
			jiaoxueInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训项目资源设计开发、编辑制作、修改完善培训资源等所需费用。 </div>';
			$("[id^=td_item]:lt(12):gt(8)").css("color","black");
		//	$("#msg").html("<b>教学资源费用,比例近似为:"+(jiaoxue*100).toFixed(1)+"%,超过8%!</b>");
			if(num_d){
				$("#span_qty_6").css("color","black");
			 }
			 e = true;
		};*/
		if(changdi>0.1){
		changdiInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;场地设备费,比例近似为:'+(changdi*100).toFixed(1)+'%,超过10%!</div>';
			$("[id^=td_item]:lt(16):gt(13)").css("color","red");
			 f = false;
		}else{
			changdiInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;培训期间所需教室、实验室和相关设施、设备等租赁费用及必要设备的购置费。</div>';
			$("[id^=td_item]:lt(16):gt(13)").css("color","black");
			 f = true;
		}
		if(xiangmu>0.05){
		xiangmuInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目评估费,比例近似为:'+(xiangmu*100).toFixed(1)+'%,超过5%!</div>';
			$("[id^=td_item]:lt(18):gt(15)").css("color","red");
			 g = false;
		}else{
		xiangmuInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开展培训效果评估所需费用以及进行项目总结、制作项目总结材料所需费用。 </div>';
			$("[id^=td_item]:lt(18):gt(15)").css("color","black");
			 g = true;
		}
		if(qita>0.03){
		qitaInfor.innerHTML='<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他支出,比例近似为:'+(qita*100).toFixed(1)+'%,超过3%!</div>';
			$("[id^=td_item]:lt(22):gt(17)").css("color","red");
			//$("#span_qty_9").css("color","red");
			 h = false;
		}else{
		qitaInfor.innerHTML='<div style="color: black;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;与培训项目直接相关的其它支出，如工作人员劳务费、宣传、公杂及不可预计性费用，此项支出不高于总经费的3%。</div>';
			$("[id^=td_item]:lt(22):gt(17)").css("color","black");
			 h = true;
		}
		
		$("#msg").html("");
		if(num_1-num_total>0){
			$("#span_qty_1").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			num_a = false;
		}else{
			$("#span_qty_1").css("color","black");
		}
		if(num_2-num_total>0){
			$("#span_qty_2").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			num_b = false;
		}
		else{
			$("#span_qty_2").css("color","black");
		}
		/*if(num_3-num_total>0){
			$("#span_qty_5").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			num_c = false;
		}else{
			$("#span_qty_5").css("color","black");
		}*/
		if(num_4-num_total>0){
			$("#span_qty_6").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			num_d = false;
		}else{
			if(d){
				$("#span_qty_6").css("color","black");
			}
		}
		/*if(num_5-num_total>0){
			$("#span_qty_9").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			num_e = false;
		}else{
			if(h){
				$("#span_qty_9").css("color","black");
			}
		}*/
			if(document.getElementById('peFeeActualBudget.trainingSpace').value==''){
				$("#msg").html("<b>培训地点不能为空！</b>");
				document.getElementById('peFeeActualBudget.trainingSpace').focus();
				return false;
			}
			if(document.getElementById('pbid').value==''){
				if(document.getElementById('classes').value==''){
					$("#msg").html("<b>培训班不能为空！</b>");
					document.getElementById('classes').focus();
					return false;
				}
			}
			if(document.getElementById('peFeeActualBudget.payeeName').value==''){
				$("#msg").html("<b>收款单位名称不能为空！</b>");
				document.getElementById('peFeeActualBudget.payeeName').focus();
				return false;
			}
			if(document.getElementById('peFeeActualBudget.openingBank').value==''){
				$("#msg").html("<b>开户银行不能为空！</b>");
				document.getElementById('peFeeActualBudget.openingBank').focus();
				return false;
			}
			if(document.getElementById('peFeeActualBudget.accountNumber').value==''){
				$("#msg").html("<b>账号不能为空！</b>");
				document.getElementById('peFeeActualBudget.accountNumber').focus();
				return false;
			}
			if(document.getElementById('peFeeActualBudget.contactInfo').value==''){
				$("#msg").html("<b>联系人及电话不能为空！</b>");
				document.getElementById('peFeeActualBudget.contactInfo').focus();
				return false;
			}
		if(document.getElementById('pbid').value==''){
			if($("#total_person_1").val()==0){
				alert("请正确填写培训人数！");
				return false;
			}
		}
		var stander = num_total*$("#stander").val();
		if(stander<money){
			alert("您的费用总和"+money+"元，超过"+stander+"元的标准！");
			return false;
		}else if(money>9999999.99){
			alert("您的费用总和"+money+"元，超过10,000,000.00元的最高限制！");
			return false;
		}

		var k = (num_a&&num_b&&num_c&&num_d&&num_e&&a&&b&&c&&d&&e&&f&&g&&h);
		return k;
		//return false; 
	}
	</script>
<body>
		<div id="main_content">
		<div class="content_title">
		  <div align="center" class="STYLE1">“国培计划”中小学教师培训经费决算表</div>
		</div>
			<div class="cntent_k" align="center">
				<form action="/entity/implementation/actualBudget_toSave.action" method="post" id="frmCreateCheckboxRange"
					onsubmit="return check();">
					<table width="80%" border="0" style="border: 1px dashed #8bbedc;"
						cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="27%" >培训项目：</td>
                        <td width="40%" align="left"><input type="text" name="peProApplyno.name" id="peProApplyno.name" value="<s:property value='peProApplyno.name'/>"
									style="width: 73%" readonly="readonly" />
						<input type="hidden" name="stander" id="stander" value="<s:property value='peProApplyno.feeStandard'/>" /></td>
                        <td width="33%">&nbsp;</td>
                      </tr>
                      <tr>
                        <td>培训时间：</td>
                        <td align="left">  
                        <select name="trainingYear"  id="trainingYear"  >
             			<option  value = "2010" <s:if test="trainingYear=='2010'">selected="selected"</s:if> >2010</option>
             			<option  value = "2011" <s:if test="trainingYear=='2011'">selected="selected"</s:if> >2011</option>
             			<option  value = "2012" <s:if test="trainingYear=='2012'">selected="selected"</s:if> >2012</option>
             			<option  value = "2013" <s:if test="trainingYear=='2013'">selected="selected"</s:if> >2013</option>
             			<option  value = "2014" <s:if test="trainingYear=='2014'">selected="selected"</s:if> >2014</option>
             			<option  value = "2015" <s:if test="trainingYear=='2015'">selected="selected"</s:if> >2015</option>
             			<option  value = "2016" <s:if test="trainingYear=='2016'">selected="selected"</s:if> >2016</option>
             			<option  value = "2017" <s:if test="trainingYear=='2017'">selected="selected"</s:if> >2017</option>
             			<option  value = "2018" <s:if test="trainingYear=='2018'">selected="selected"</s:if> >2018</option>
             			<option  value = "2019" <s:if test="trainingYear=='2019'">selected="selected"</s:if> >2019</option>
             			<option  value = "2020" <s:if test="trainingYear=='2020'">selected="selected"</s:if> >2020</option>
             			<option  value = "2021" <s:if test="trainingYear=='2021'">selected="selected"</s:if> >2021</option>
             			<option  value = "2022" <s:if test="trainingYear=='2022'">selected="selected"</s:if> >2022</option>
             			<option  value = "2023" <s:if test="trainingYear=='2023'">selected="selected"</s:if> >2023</option>
             			<option  value = "2024" <s:if test="trainingYear=='2024'">selected="selected"</s:if> >2024</option>
             			<option  value = "2025" <s:if test="trainingYear=='2025'">selected="selected"</s:if> >2025</option>
             			<option  value = "2026" <s:if test="trainingYear=='2026'">selected="selected"</s:if> >2026</option>
             			<option  value = "2027" <s:if test="trainingYear=='2027'">selected="selected"</s:if> >2027</option>
             			<option  value = "2028" <s:if test="trainingYear=='2028'">selected="selected"</s:if> >2028</option>
             			<option  value = "2029" <s:if test="trainingYear=='2029'">selected="selected"</s:if> >2029</option>
             			<option  value = "2030" <s:if test="trainingYear=='2030'">selected="selected"</s:if> >2030</option>
             			</select><span>年</span>
             			<select name="trainingMonth"  id="trainingMonth"  >
             			<option  value = "01" <s:if test="trainingMonth=='01'">selected="selected"</s:if> >01</option>
             			<option  value = "02" <s:if test="trainingMonth=='02'">selected="selected"</s:if> >02</option>
             			<option  value = "03" <s:if test="trainingMonth=='03'">selected="selected"</s:if> >03</option>
             			<option  value = "04" <s:if test="trainingMonth=='04'">selected="selected"</s:if> >04</option>
             			<option  value = "05" <s:if test="trainingMonth=='05'">selected="selected"</s:if> >05</option>
             			<option  value = "06" <s:if test="trainingMonth=='06'">selected="selected"</s:if> >06</option>
             			<option  value = "07" <s:if test="trainingMonth=='07'">selected="selected"</s:if> >07</option>
             			<option  value = "08" <s:if test="trainingMonth=='08'">selected="selected"</s:if> >08</option>
             			<option  value = "09" <s:if test="trainingMonth=='09'">selected="selected"</s:if> >09</option>
             			<option  value = "10" <s:if test="trainingMonth=='10'">selected="selected"</s:if> >10</option>
             			<option  value = "11" <s:if test="trainingMonth=='11'">selected="selected"</s:if> >11</option>
             			<option  value = "12" <s:if test="trainingMonth=='12'">selected="selected"</s:if> >12</option>
             			</select><span>月</span>&nbsp;&nbsp;至&nbsp;&nbsp;
             			<select name="trainingEndMonth"  id="trainingEndMonth"  >
             			<option  value = "01" <s:if test="trainingEndMonth=='01'">selected="selected"</s:if> >01</option>
             			<option  value = "02" <s:if test="trainingEndMonth=='02'">selected="selected"</s:if> >02</option>
             			<option  value = "03" <s:if test="trainingEndMonth=='03'">selected="selected"</s:if> >03</option>
             			<option  value = "04" <s:if test="trainingEndMonth=='04'">selected="selected"</s:if> >04</option>
             			<option  value = "05" <s:if test="trainingEndMonth=='05'">selected="selected"</s:if> >05</option>
             			<option  value = "06" <s:if test="trainingEndMonth=='06'">selected="selected"</s:if> >06</option>
             			<option  value = "07" <s:if test="trainingEndMonth=='07'">selected="selected"</s:if> >07</option>
             			<option  value = "08" <s:if test="trainingEndMonth=='08'">selected="selected"</s:if> >08</option>
             			<option  value = "09" <s:if test="trainingEndMonth=='09'">selected="selected"</s:if> >09</option>
             			<option  value = "10" <s:if test="trainingEndMonth=='10'">selected="selected"</s:if> >10</option>
             			<option  value = "11" <s:if test="trainingEndMonth=='11'">selected="selected"</s:if> >11</option>
             			<option  value = "12" <s:if test="trainingEndMonth=='12'">selected="selected"</s:if> >12</option>
             			</select><span>月</span>
             			</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td>培训地点：</td>
                        <td align="left"><input type="text" name="peFeeActualBudget.trainingSpace" id="peFeeActualBudget.trainingSpace" value="<s:property value='peFeeActualBudget.trainingSpace'/>"
								maxlength="50"	style="width: 73%" /></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td>承办单位：</td>
                        <td align="left"><input type="text" name="peFeeActualBudget.unitName" id="peFeeActualBudget.unitName" value="<s:property value='getPeUnit().name'/>"
								maxlength="25"	style="width: 73%" /></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td>培训人数：</td>
                        <td align="left" colspan="2">
                        	<s:set name="total_persons"  value="0"/>
                        	<s:if test="subjectList!=null">
                        	<s:iterator value="subjectList" id="subject" status="sub" >
                        	<s:property value="#subject[1]"/>:
							<input type="text" name="person_<s:property value='#sub.index' />" value="<s:property value="#subject[2]"/>" size="2" maxlength="4"  />
							<s:set name="total_persons"  value="#total_persons+#subject[2]"/>
							</s:iterator>
							总计:
							<input type="text" name="total_person_1" id="total_person_1" value="" size="5" readonly="readonly" />
							培训班：
							<input type="text" name="classes" id="classes" value="" size="2" maxlength="4"  />个
							</s:if>
							<s:else>
							<input type="hidden" id="total_p" name="total_p" value="<s:property value='peFeeActualBudget.personCount'/>" />
							<s:property value="peFeeActualBudget.personCount" />
							</s:else>
							
						</td>
                        
                      </tr>
                    </table>
					<br/>
					<table width="80%" border="0" style="border: 1px dashed #8bbedc;"
						cellpadding="0" cellspacing="0">
						
						
						<tr>
							<td colspan="2" align="center" width="27%">
								<strong>预算科目</strong>							</td>
							<td width="12%" align="center">
								<strong>经费（元）</strong>							</td>
							<td width="28%" align="center">
								<strong>计算说明</strong>							</td>
							<td width="33%" align="center">
								<strong>支出说明</strong>							</td>
						</tr>
						<tr>
							<td width="7%" rowspan="3" align="center">
								<strong>调研与方案研制</strong>							</td>
							<td width="20%" align="center" >
								前期研制费							</td>
							<td align="left" id="td_item_1">
								经费:
								<input type="text" name="other_item_1" id="other_item_1"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeSurvey'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_1" id="note_item_1" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteSurvey'/>"
									style="width: 73%" />							</td>
							<td rowspan="3" align="left" id="yanzhiInfor">
								项目方案研制过程中所需的会议费、问卷设计与分析劳务费、专家咨询费、专家评审费等							</td>
						</tr>
						<tr>
							<td align="center">
								方案研制费							</td>
							<td align="left" id="td_item_2">
								经费:
								<input type="text" name="other_item_2" id="other_item_2"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeResearch'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_2" id="note_item_2" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteResearch'/>"
									style="width: 73%" />							</td>
						</tr>
						<tr>
							<td align="center">
								项目论证费							</td>
							<td align="left" id="td_item_3">
								经费:
								<input type="text" name="other_item_3" id="other_item_3"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeArgument'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_3" id="note_item_3" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteArgument'/>"
									style="width: 73%" />							</td>
						</tr>
						<tr>
							<td rowspan="3" align="center">
								<p align="center">
									<strong>学员食宿</strong><strong>与交通费</strong>								</p>							</td>
							<td align="center">
								餐费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_1" id="total_item_1"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeMeal'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_4">
								
								<input type="text" name="price_item_1" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMeal.split('\\\\*')[0]"/>" size="2" />
								元/人/天×
								<input type="text" id="qty_item_1" name="qty_item_1" value="<s:if test="peFeeBudget.peFeeBudgetDetail.noteMeal.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetDetail.noteMeal.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>" size="2" />
								<span id="span_qty_1">人</span>×
								<input type="text" name="time_item_1" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMeal.split('\\\\*')[2]"/>" size="2" />
								天								</td>
							<td rowspan="3" align="left" id="xueyuanInfor">
								<p>
									餐费和住宿费是指学员在培训期间的费用。交通费是指本地观摩考察交通费用。								</p>							</td>
						</tr>
						<tr>
							<td align="center">
								住宿费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_2" id="total_item_2"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeAccommodation'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_5">
								
								<input type="text" name="price_item_2" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteAccommodation.split('\\\\*')[0]"/>" size="2" />
								元/人/天×
								<input type="text" id="qty_item_2" name="qty_item_2" value="<s:if test="peFeeBudget.peFeeBudgetDetail.noteAccommodation.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetDetail.noteAccommodation.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>" size="2" />
								<span id="span_qty_2">人</span>×
								
								<input type="text" name="time_item_2" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteAccommodation.split('\\\\*')[2]"/>" size="2" />	
								天						</td>
						</tr>
						<tr>
							<td align="center">
								交通费							</td>
							<td align="left" id="td_item_6">
								经费:
								<input type="text" name="other_item_4" id="other_item_4"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeTrafficStu'/>" size="5"  />							</td>
							<td align="left">
								 <input type="text" name="note_item_4" id="note_item_4" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteTrafficStu'/>"
									style="width: 73%" />  		</td>
						</tr>
						<tr>
							<td rowspan="5" align="center">
								<strong>专家费用</strong>							</td>
							<td rowspan="3" align="center">
								授课费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_3" id="total_item_3"
									value="" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_7">
								本地专家:
								<input type="text" name="price_item_3" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTeach.split('\\\\*')[0]"/>" size="2" />
								元/人次×
								<input type="hidden" name="qty_item_3" value="1" size="2" />
								
								<input type="text" name="time_item_3" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTeach.split('\\\\*')[1]"/>" size="2" />	
								人次						</td>
							<td rowspan="5" align="left" id="zhuanjiaInfor">
								培训实施期间专家的授课费、食宿费、交通费。食宿费按每位专家实际住宿天数计算。							</td>
						</tr>
						<tr>
						  <td align="left">
						  经费:
						  <input type="text" name="total_item_10" id="total_item_10"
									value="" size="5" readonly="readonly" /></td>
						  <td align="left" id="td_item_21">
						  		外地专家:
						  		<input type="text" name="price_item_10" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTeach.split('\\\\*')[2]"/>" size="2" />
								元/人次×
								<input type="hidden" name="qty_item_10" value="1" size="2" />
								
								<input type="text" name="time_item_10" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTeach.split('\\\\*')[3]"/>" size="2" />	
								人次					</td>
					  </tr>
						<tr>
						  <td align="left">&nbsp;</td>
						  <td align="left" id="td_item_22">
						  专家每人次平均授课学时数：<input type="text" name="xueshi_item_1" id="xueshi_item_1" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteOther"/>" size="2" />（1小时计1学时）</td>
					  </tr>
						<tr>
							<td align="center">
								交通费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_4" id="total_item_4"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeTrafficExpert'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_8">
								
								<input type="text" name="price_item_4" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTrafficExpert.split('\\\\*')[0]"/>" size="2" />
								元/人次×
								<input type="hidden" name="qty_item_4" value="1" size="2" />
								
								<input type="text" name="time_item_4" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTrafficExpert.split('\\\\*')[1]"/>" size="2" />	
								人次						</td>
						</tr>
						<tr>
							<td align="center">
								食宿费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_5" id="total_item_5"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeMealAccExpert'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_9">
								
								<input type="text" name="price_item_5" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMealAccExpert.split('\\\\*')[0]"/>" size="2" />
								元/人次/天×
								<span id="span_qty_5"></span>
								<input type="text" id="qty_item_5" name="qty_item_5" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMealAccExpert.split('\\\\*')[1]"/>" size="2" />
								人次×
								
								<input type="text" name="time_item_5" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMealAccExpert.split('\\\\*')[2]"/>" size="2" />	
								天						</td>
						</tr>
						<tr>
							<td rowspan="3" align="center">
								<strong>教学资源费用</strong>							</td>
							<td align="center">
								学员学习资料费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_6" id="total_item_6"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeMaterials'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_10">
								
								<input type="text" name="price_item_6" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMaterials.split('\\\\*')[0]"/>" size="2" />
								<span id="span_1">元/人</span>×
								
								<input type="text" id="qty_item_6" name="qty_item_6" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteMaterials.split('\\\\*')[1]"/>" size="2" />
								<span id="span_qty_6">人</span>
								<input type="hidden" name="time_item_6" value="1" size="2" />							</td>
							<td align="left" id="xuexiziliaoInfor">
								学员学习所必需的培训资料费用，包括文本资料、光盘等费用。							</td>
						</tr>
						<tr>
							<td align="center">
								<p align="center">
									文本课程资源开发费用								</p>							</td>
							<td align="left" id="td_item_11">
								经费:
								<input type="text" name="other_item_5" id="other_item_5"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeCourse'/>" size="5" readonly="readonly"/>							</td>
							<td align="left">
							 
								<input type="hidden" name="note_item_5" id="note_item_5" maxlength="100" value="1"
									style="width: 73%" />		
								设计开发费:
								<input type="text" name="wenben_item_1" id="wenben_item_1"
									value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTextCourse.split('\\\\+')[0]"/>" size="2" />
								元；制作使用费:
								<input type="text" name="wenben_item_2" id="wenben_item_2"
									value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteTextCourse.split('\\\\+')[1]"/>" size="2" />		
								元						</td>
							<td rowspan="2" align="left" id="jiaoxueInfor">
								培训项目资源设计开发、编辑制作、修改完善培训资源等所需费用。							</td>
						</tr>
						<tr>
							<td align="center">
								<p align="center">
									电子课程资源开发费用								</p>							</td>
							<td align="left" id="td_item_12">
								经费:
								<input type="text" name="other_item_6" id="other_item_6"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeElectronCourse'/>" size="5" readonly="readonly"/>							</td>
							<td align="left">
								<input type="hidden" name="note_item_6" id="note_item_6" maxlength="100" value="1"
									style="width: 73%" />	 
								设计开发费:
								<input type="text" name="dianzi_item_1" id="dianzi_item_1"
									value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteElectronCourse.split('\\\\+')[0]"/>" size="2" />
								元；制作使用费:
								<input type="text" name="dianzi_item_2" id="dianzi_item_2"
									value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteElectronCourse.split('\\\\+')[1]"/>" size="2" />	 	
									元					</td>
						</tr>
						<tr>
							<td rowspan="2" align="center">
								<strong>场地设备费</strong>							</td>
							<td align="center">
								场地租用费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_7" id="total_item_7"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetailDetail.feeAreaRent'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_13">
								
								<input type="text" name="price_item_7" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteAreaRent.split('\\\\*')[0]"/>" size="2" />
								元/班次/天×
								<input type="text" name="qty_item_7" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteAreaRent.split('\\\\*')[1]"/>" size="2" />
								班次×
								<input type="text" name="time_item_7" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteAreaRent.split('\\\\*')[2]"/>" size="2" />	
								天						</td>
							<td rowspan="2" align="left" id="changdiInfor">
								培训期间所需教室、实验室和相关设施、设备等租赁费用及必要设备的购置费。							</td>
						</tr>
						<tr>
							<td align="center">
								设备租用费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_8" id="total_item_8"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeEquipmentRent'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_14">
								
								<input type="text" name="price_item_8" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteEquipmentRent.split('\\\\*')[0]"/>" size="2" />
								元/班次/天×
								<input type="text" name="qty_item_8" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteEquipmentRent.split('\\\\*')[1]"/>" size="2" />
								班次×
								<input type="text" name="time_item_8" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteEquipmentRent.split('\\\\*')[2]"/>" size="2" />
								天							</td>
						</tr>
						<tr>
							<td rowspan="2" align="center">
								<p align="center">
									<strong>项目评估</strong><strong>费用</strong>								</p>							</td>
							<td align="center">
								项目评估费							</td>
							<td align="left" id="td_item_15">
								经费:
								<input type="text" name="other_item_7" id="other_item_7"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeAppraise'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_7" id="note_item_7" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteAppraise'/>"
									style="width: 73%" />							</td>
							<td rowspan="2" align="left" id="xiangmuInfor">
								开展培训效果评估所需费用以及进行项目总结、制作项目总结材料所需费用。							</td>
						</tr>
						<tr>
							<td align="center">
								项目总结费							</td>
							<td align="left" id="td_item_16">
								经费:
								<input type="text" name="other_item_8" id="other_item_8"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeSummaryAppraise'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_8" id="note_item_8" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteSummaryAppraise'/>"
									style="width: 73%" />							</td>
						</tr>
						<tr>
							<td rowspan="4" align="center">
								<strong>其他支出</strong>							</td>
							<td align="center">
								劳务费							</td>
							<td align="left">
								经费:
								<input type="text" name="total_item_9" id="total_item_9"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeLabourService'/>" size="5" readonly="readonly" />							</td>
							<td align="left" id="td_item_17">
								
								<input type="text" name="price_item_9" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteLabourService.split('\\\\*')[0]"/>" size="2" />
								元/人次/天×
								<span id="span_qty_9"></span>
								<input type="text" id="qty_item_9" name="qty_item_9" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteLabourService.split('\\\\*')[1]"/>" size="2" />
								人次×
								
								<input type="text" name="time_item_9" value="<s:property value="peFeeActualBudget.peFeeActualBudgetDetail.noteLabourService.split('\\\\*')[2]"/>" size="2" />	
								天						</td>
							<td rowspan="4" align="left" id="qitaInfor">
								与培训项目直接相关的其它支出，如工作人员劳务费、宣传、公杂及不可预计性费用，此项支出不高于总经费的3%。							</td>
						</tr>
						<tr>
							<td align="center">
								宣传费							</td>
							<td align="left" id="td_item_18">
								经费:
								<input type="text" name="other_item_9" id="other_item_9"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feePublicity'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_9" id="note_item_9" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.notePublicity'/>"
									style="width: 73%" />							</td>
						</tr>
						<tr>
							<td align="center">
								公杂费							</td>
							<td align="left" id="td_item_19">
								经费:
								<input type="text" name="other_item_10" id="other_item_10"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feePetty'/>" size="5" />							</td>
							<td align="left">
								<input type="text" name="note_item_10" id="note_item_10" maxlength="100" value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.notePetty'/>"
									style="width: 73%" />							</td>
						</tr>
						<!-- 
						<tr>
							<td align="center">
								不可预计支出							</td>
							<td align="left" id="td_item_20">
								经费:
								<input type="text" name="other_item_11" id="other_item_11"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.feeNoplan'/>" size="5" />							</td>
							<td align="left">&nbsp;
							
								<input type="text" name="note_item_11" id="note_item_11"
									value="<s:property value='peFeeActualBudget.peFeeActualBudgetDetail.noteNoplan'/>" maxlength="100" style="width: 73%" /> 
							</td>
						</tr>-->
						<tr>
						  <td align="center"><strong>合计</strong></td>
						  <td>&nbsp;</td>
						  <td colspan="2" align="left">
						  <div id="grandTotal" style="color: red"></div> </td>
						  <td>&nbsp;</td>
					  </tr>
				  </table>
					 <br/>
					<table width="80%" border="0" style="border: 1px dashed #8bbedc;"
						cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="50%" align="right">收款单位名称：
                        <input type="text" name="peFeeActualBudget.payeeName" id="peFeeActualBudget.payeeName" value="<s:property value='peFeeActualBudget.payeeName'/>"
								maxlength="50"	style="width: 73%" /></td>
                        <td width="50%" align="right">开户银行：
                        <input type="text" name="peFeeActualBudget.openingBank" id="peFeeActualBudget.openingBank" value="<s:property value='peFeeActualBudget.openingBank'/>"
								maxlength="25"	style="width: 73%" /></td>
                      </tr>
                      <tr>
                        <td align="right">账号：
                        <input type="text" name="peFeeActualBudget.accountNumber" id="peFeeActualBudget.accountNumber" value="<s:property value='peFeeActualBudget.accountNumber'/>"
								maxlength="25"	style="width: 73%" /></td>
                        <td align="right">联系人及电话：
                        <input type="text" name="peFeeActualBudget.contactInfo" id="peFeeActualBudget.contactInfo" value="<s:property value='peFeeActualBudget.contactInfo'/>"
								maxlength="25"	style="width: 73%" /></td>
                      </tr>
                    </table>
					<div id="msg" style="color: red;"></div>
					<!--  div>
						<input type="submit" value="提交" />
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" value="重置" />
					</div-->
					<!--申报批次ID  -->
					<input type="hidden" name="praid" id="praid" value="<s:property value='peProApplyno.id'/>"/>
					<!--预算表ID  -->
					<input type="hidden" name="pbid" id="pbid" value="<s:property value='peFeeActualBudget.id'/>"/>
				</form>
			</div>
</body>
</html>
