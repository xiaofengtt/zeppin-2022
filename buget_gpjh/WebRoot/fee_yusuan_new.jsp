<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>预算表</title>
<%
	String path = request.getContextPath();
%>
<link href="css/admincss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.calculation.js"></script>
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

		$("input[name^=qty_item_]").bind("keyup", recalc);
		$("input[name^=price_item_]").bind("keyup", recalc);
		$("input[name^=time_item_]").bind("keyup", recalc);
		
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
		
		 $("input[name^=sum]").sum("keyup", "#totalSum");
		 
		 <s:if test="subjectList!=null">
			 $("input[name^=person]").sum("keyup", "#total_person_1");
// 			 $("input[name^=person]").sum("keyup", "#qty_item_1");
			 
// 			 $("input[name^=person]").sum("keyup", "#qty_item_8");
// 			 $("input[name^=person]").sum("keyup", "#qty_item_11");
// 			 $("input[name^=person]").sum("keyup", "#qty_item_13");
		 </s:if>
		 
		 recalc();
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
				//var sum = $this.sum()+$("[id^=other_item]").sum();
				var sum = $this.sum();
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
		
		if( $('input[name="timearray"]').length <1){
			alert("请填写‘集中面授时间’");
			$("#tijiao").removeAttr("disabled");
			return false;
		}
		
   		reg=/^\d+(\.\d+)?$/ ; 
		//总费用 
   		total = $("#grandTotal").text();
		
		flag = false;
		var price_items = $("[name^=price_item]");
		for (var i=0;i<price_items.length;i++){
			price = price_items[i];
			if(!reg.test($(price).val())){  
		        flag = true;
		        break;
        	}
		}
		
		flag1 = false;
		var qty_items = $("[name^=qty_item]");
		for (var i=0;i<qty_items.length;i++){
			qty = qty_items[i];
			if($(qty).val()==''){  
		        flag1 = true;
		        break;
        	}
		}
		
		flag2 = false;
		var tims_items = $("[name^=time_item]");
		for (var i=0;i<tims_items.length;i++){
			time = tims_items[i];
			if($(time).val()==''){  
		        flag2 = true;
		        break;
        	}
		}
		
		$("#msg").html("");
  		 if(flag || flag1 || flag2){
			$("#msg").html("<b>所有计算说明均不能为空!</b>");
			$("#tijiao").removeAttr("disabled");
			return false;
		}

		//场地
		//alert("changdi"+$("[id^=total_item]:lt(13):gt(9)").sum());
// 		changdi = $("[id^=total_item]:lt(12):gt(8)").sum()/total;
		changdi = $("[id^=total_item]:lt(15):gt(11)").sum()/total;
		//宣传费
   		xuanchuan = $("[id^=total_item]:eq(12)").sum()/total;
      
	 
		//if(isNaN(diaoyan)||isNaN(xueyuan)||isNaN(zhuanjia)||isNaN(jiaoxue)||isNaN(changdi)||isNaN(xiangmu)||isNaN(qita)){
		if(isNaN(changdi) || isNaN(xuanchuan)){
		 alert("isNan error");
		 $("#tijiao").removeAttr("disabled");
			return false;
		}
		num_1=0;
		num_6=0;
		num_8=0;
		num_9=0;
		num_14=0;
		num_16=0;
		 num_1 = $("#qty_item_1").val();
		 num_6 = $("#qty_item_8").val();
		 num_8 = $("#qty_item_11").val();
		 num_9 = $("#qty_item_13").val();
		 
		 num_14 = $("#qty_item_14").val();
		 num_16 = $("#qty_item_16").val();
    
	    var peixunfei = $("#price_item_8").val();
	    var officesupplie = $("#price_item_16").val()
// 		var peixunfei = $("[name^=price_item]:lt(12):gt(9)").sum();
	    num_total=0;
	 	num_total = $("#total_person_1").val();
	 	/*
		if(document.getElementById('pbid').value!=''){
			var counts = $("#total_p").val();
			num_total = counts.substring(counts.indexOf('总计')+3,counts.lastIndexOf('人'));
		}
	 	*/
		 
		var num_fenpei1 = true;
	 	var num_person = true;
		var num_a = true;
		var num_b = true;
		var num_c = true;
		var num_d = true;
		var num_e = true;
		var num_f = true;
		var num_g = true;
		var num_h = true;
		
		var a = true;
		var b = true;
		var c = true;
		var d = true;
		var e = true;
		var f = true;
		var g = true;
		var h = true;
		var i = true;
		var j = true;
		
		var o = true;
		var p = true;
		var q = true;
	
		 //场地总费用不超过20%	
		if(Number(changdi)>0.2){
		changdiInfor.innerHTML='<div style="color:red;">场地设备费,比例近似为:'+(changdi*100).toFixed(1)+'%,超过20%!</div>';
			$("[id^=td_item]:lt(15):gt(11)").css("color","red");
			 a = false;
			 $("#tijiao").removeAttr("disabled");
		}else{
			changdiInfor.innerHTML='<div style="color: black;">培训所需教室、实验室、场地、网络研修平台及相关设备的租用和服务费。</div>';
			$("[id^=td_item]:lt(15):gt(11)").css("color","black");
			 a = true;
		}
		
// 		//材料总费用不超过5％
// 		if(xuanchuan > 0.05){
// 			xuanchuanInfor.innerHTML='<div style="color: red;">项目宣传费,比例近似为:'+(xuanchuan*100).toFixed(1)+'%,超过5%!</div>';
// 			$("[id^=td_item]:eq(12)").css("color","red");
// 			 b = false;
// 		}else{
// 		      xuanchuanInfor.innerHTML='<div style="color: black;">培训成果总结及推广所需费用。</div>';
// 		      $("[id^=td_item]:eq(12)").css("color","black");
// 		      b = true;
// 		}

		//培训资料费 不大于300元
		if(Number(peixunfei) > 300){
			pxziliaoInfor.innerHTML='<div style="color: red;">学习资料费为:'+peixunfei+',超过300元!</div>';
			$("[id^=td_item]:eq(10)").css("color","red");
			 i = false;
			 $("#tijiao").removeAttr("disabled");
		}else{
		      pxziliaoInfor.innerHTML='<div style="color: black;">为学员学习提供培训资料（文本和教材等）所需费用。</div>';
		      $("[id^=td_item]:eq(10)").css("color","black");
		      i = true;
		}
		
		//办公用品费不大于300元
		if(Number(officesupplie) > 300){
			officesupplies.innerHTML='<div style="color: red;">办公用品费为:'+officesupplie+',超过300元!</div>';
			$("[id^=td_item]:eq(11)").css("color","red");
			 j = false;
			 $("#tijiao").removeAttr("disabled");
		}else{
			officesupplies.innerHTML='<div style="color: black;">培训期间必要的办公用品费。</div>';
		      $("[id^=td_item]:eq(11)").css("color","black");
		      j = true;
		}
		
		//授课费
		var jiangkefei = $("#price_item_5").val();
		if(Number(jiangkefei) > 2000){
			xuexiziliaoInfor.innerHTML='<div style="color: red;">授课费,为:'+jiangkefei+',超过2000元!</div>';
			$("[id^=td_item]:eq(6)").css("color","red");
			o = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			xuexiziliaoInfor.innerHTML='<div style="color: black;">聘请授课专家所需费用。</div>';
		    $("[id^=td_item]:eq(6)").css("color","black");
		    o = true;
		}
		
		//指导费
		var fudaofei = $("#price_item_6").val();
		if(Number(fudaofei) > 2000){
			zhuanjialaowuInfor.innerHTML='<div style="color: red;">指导费,为:'+fudaofei+',超过2000元!</div>';
			$("[id^=td_item]:eq(7)").css("color","red");
			 p = false;
			 $("#tijiao").removeAttr("disabled");
		}else{
			zhuanjialaowuInfor.innerHTML='<div style="color: black;">聘请专家及教师进行训前调研与设计、诊断测评、培训指导所需费用。</div>';
		      $("[id^=td_item]:eq(7)").css("color","black");
		      p = true;
		}
		
		//跟踪指导费
		var genzhongfei = $("#price_item_7").val();
		if(Number(genzhongfei) > 2000){
			jiaoxueInfor.innerHTML='<div style="color: red;">培训跟踪指导费,为:'+genzhongfei+',超过2000元!</div>';
			$("[id^=td_item]:eq(8)").css("color","red");
			 q = false;
			 $("#tijiao").removeAttr("disabled");
		}else{
			jiaoxueInfor.innerHTML='<div style="color: black;">聘请专家对学员进行培训跟踪指导所需费用。</div>';
		      $("[id^=td_item]:eq(8)").css("color","black");
		      q = true;
		}
		
		$("#msg").html("");
		<s:if test="subjectList!=null">
			<s:iterator value="subjectList" id="subject" status="sub">
				var perInName = "person_<s:property value='#sub.index' />"
				var perInValue = $("input[name="+perInName+"]").val();
				var subvalue="<s:property value='#subject[2]'/>"
				if(Number(perInValue)>Number(subvalue)){
					
// 					$("input[name="+perInName+"]").css("color","red");
// 					num_person = false;
// 					$("#msg").html("<b>学科人数不能超过默认值!</b>");
// 					$("#tijiao").removeAttr("disabled");
// 					return false;
				}
				else{
					$("input[name="+perInName+"]").css("color","black");
				}
			
			</s:iterator>
		</s:if>

		var num_fenpei = 0;
		num_fenpei = $("#fenpei_persons").val();

		<s:if test="subjectList!=null">
			if(Number(num_total)-Number(num_fenpei)>0){
// 				$("#total_person_1").css("color","red");
// 				$("#msg").html("<b>总人数不能超过参训总人数!</b>");
// 				$("#tijiao").removeAttr("disabled");
// 				num_fenpei1 = false;
			}else {
				$("#total_person_1").css("color","black");
			}
		</s:if>
		
		if(Number(num_1)-Number(num_total)>0){
			$("#span_qty_1").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			$("#qty_item_1").focus();
			num_a = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			$("#span_qty_1").css("color","black");
		}
		
		if(Number(num_6)-Number(num_total)>0){
			$("#span_qty_8").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			$("#qty_item_8").focus();
			num_e = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			if(h){
				$("#span_qty_8").css("color","black");
			}
		}
				
		if(Number(num_8)-Number(num_total)>0){
			$("#span_qty_11").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			$("#qty_item_11").focus();
			num_g = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			if(h){
				$("#span_qty_11").css("color","black");
			}
		}	
		if(Number(num_9)-Number(num_total)>0){
			$("#span_qty_13").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			$("#qty_item_13").focus();
			num_h = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			if(h){
				$("#span_qty_13").css("color","black");
			}
		}
		
		if(Number(num_14)-Number(num_total)>0){
			$("#span_qty_14").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			$("#qty_item_14").focus();
			num_h = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			if(h){
				$("#span_qty_14").css("color","black");
			}
		}
		if(Number(num_16)-Number(num_total)>0){
			$("#span_qty_16").css("color","red");
			$("#msg").html("<b>人数不能超过培训总人数!</b>");
			$("#qty_item_16").focus();
			num_h = false;
			$("#tijiao").removeAttr("disabled");
		}else{
			if(h){
				$("#span_qty_16").css("color","black");
			}
		}
		/*
		if(document.getElementById('peFeeBudget.trainingSpace').value==''){
				$("#msg").html("<b>培训地点不能为空！</b>");
				document.getElementById('peFeeBudget.trainingSpace').focus();
				return false;
		}

		if(document.getElementById('peFeeBudget.projectDirector').value==''){
				$("#msg").html("<b>项目负责人不能为空！</b>");
				document.getElementById('peFeeBudget.projectDirector').focus();
				return false;
		}
		*/

		if(document.getElementById('pbid').value==''){
				if(document.getElementById('classes').value==''){
					$("#msg").html("<b>培训班不能为空！</b>");
					document.getElementById('classes').focus();
					$("#tijiao").removeAttr("disabled");
					return false;
				}
		}
		if(document.getElementById('peFeeBudget.payeeName').value==''){
				$("#msg").html("<b>收款单位名称不能为空！</b>");
				document.getElementById('peFeeBudget.payeeName').focus();
				$("#tijiao").removeAttr("disabled");
				return false;
		}
		if(document.getElementById('peFeeBudget.openingBank').value==''){
				$("#msg").html("<b>开户银行不能为空！</b>");
				document.getElementById('peFeeBudget.openingBank').focus();
				$("#tijiao").removeAttr("disabled");
				return false;
		}
		if(document.getElementById('peFeeBudget.accountNumber').value==''){
				$("#msg").html("<b>账号不能为空！</b>");
				document.getElementById('peFeeBudget.accountNumber').focus();
				$("#tijiao").removeAttr("disabled");
				return false;
		}
		if(document.getElementById('peFeeBudget.contactInfo').value==''){
				$("#msg").html("<b>联系人及电话不能为空！</b>");
				document.getElementById('peFeeBudget.contactInfo').focus();
				$("#tijiao").removeAttr("disabled");
				return false;
		}
		if(document.getElementById('pbid').value==''){
			if($("#total_person_1").val()==0){
				alert("请正确填写培训人数！");
				$("#tijiao").removeAttr("disabled");
				return false;
			}
		}
		
		var stander = num_total*$("#stander").val();
		if(stander<money){
// 			alert("您的费用总和"+money+"元，超过"+stander+"元的标准！");
// 			$("#tijiao").removeAttr("disabled");
// 			return false;
		}else if(money>9999999.99){
			alert("您的费用总和"+money+"元，超过10,000,000.00元的最高限制！");
			$("#tijiao").removeAttr("disabled");
			return false;
		}
		
		$("#tijiao").attr("disabled","true");
		var k = (num_a&&num_b&&num_c&&num_d&&num_e&&num_f&&num_g&&num_h&&a&&b&&i&&j&&o&&p&&q);
		if(!k){
			$("#tijiao").removeAttr("disabled");
		}
		return k;
	}
	</script>
<body>
	<div id="main_content">
		<div class="content_title">
			<div align="center" class="STYLE1">“国培计划”——示范性项目培训经费预算表（适用于以集中培训为主的培训项目）</div>
		</div>
		<div class="cntent_k" align="center">
			<form action="budgetnew_savePeBudgetmp.action" 
			method="post" id="frmCreateCheckboxRange" onsubmit="return check();">
				<table width="80%" border="0" style="border: 1px dashed #8bbedc;"
					cellpadding="0" cellspacing="0">
					<tr>
						<td width="27%">培训项目：</td>
						<td width="40%" align="left"><input type="text"
							name="peProApplyno.name" id="peProApplyno.name"
							value="<s:property value='peProApplyno.name'/>"
							style="width: 73%" readonly="readonly" /> 
							
							<input type="hidden" name="stander" id="stander"
							value="<s:property value='peProApplyno.feeStandard'/>" />
						</td>
						<td width="33%">&nbsp;</td>
					</tr>
					
					<tr>
						<td>承办单位：</td>
						<td align="left"><input type="text"
							name="peFeeBudget.unitName" id="peFeeBudget.unitName"
							value="<s:property value='peFeeBudget.unitName'/>" maxlength="25"
							style="width: 73%" /></td>
						<td>&nbsp;</td>
					</tr>
					
					<tr>
						<td>培训学科及计划人数：</td>
						<td align="left" colspan="2">
							<s:set name="total_persons"  value="0" /> 
							<s:if test="subjectList!=null">
								<s:iterator value="subjectList" id="subject" status="sub">
									<s:property value="#subject[1]" />:
										<input type="text"	name="person_<s:property value='#sub.index' />"
											value="<s:property value="#subject[2]"/>" size="2"	maxlength="4" />
										<s:set name="total_persons" value="#total_persons+#subject[2]" />
									</s:iterator>
								总计:
								<input type="text" name="total_person_1" id="total_person_1"
									value="" size="5" readonly="readonly" />
								培训班：
								<s:set name="classesCount" value="1" />
								<s:if test="%{peFeeBudget.personCount!=null}">
									<s:set name="tempStr" value='peFeeBudget.personCount.split("、")[1]' />
									<s:set name="classesCount" value="#tempStr.substring(0, #tempStr.indexOf('个'))" />
								</s:if>
								<input type="text" name="classes" id="classes" value="<s:property value='#classesCount' />" size="2"
									maxlength="4" />个
							</s:if> 
							<s:else>
								<input type="hidden" id="total_p" name="total_p"
									value="<s:property value='peFeeBudget.personCount'/>" />
								<s:property value="peFeeBudget.personCount" />
								<s:set name="total_persons" value="" />
							</s:else> 
							<input type="hidden" id="fenpei_persons" name="fenpei_persons"	value="<s:property value="#total_persons"/>" />
						</td>
					</tr>
					
					<!-- 
					<tr>
						<td>培训地点：</td>
						<td align="left"><input type="text"
							name="peFeeBudget.trainingSpace" id="peFeeBudget.trainingSpace"
							value="<s:property value='peFeeBudget.trainingSpace'/>"
							maxlength="50" style="width: 73%" /></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>项目负责人：</td>
						<td align="left"><input type="text"
							name="peFeeBudget.projectDirector"
							id="peFeeBudget.projectDirector"
							value="<s:property value='peFeeBudget.projectDirector'/>"
							maxlength="50" style="width: 73%" /></td>
						<td>&nbsp;</td>
					</tr>
					
					 -->
					
					<tr>
						<td>集中面授时间：</td>
						<td align="left">
							
							<div>
								起始时间:<input type='date' name='timestart' style="width:120px;" /> 
								结束时间:<input type='date' name='timeend' style="width:120px;" /> 
								<input type="number" style="width:50px;" name="day" />天
								学时:<input type='number' style="width:50px;" name='keshi' />
								
							&nbsp;&nbsp;<button type="button" onclick="timeAdd()" >添加</button>
							</div>
							<div id="timeContent">
								<s:iterator value="peFeeBudget.training_data.split('\\\\;')" id="tdata">
									<div class="timegird"><s:property value='#tdata' /><span onclick="timeDel2(this)" class="timeclose">&times</span></div>
								</s:iterator>
							</div>
							<div id="timeinput" style="display:none">
								<s:iterator value="peFeeBudget.training_data.split('\\\\;')" id="tdata">
									<input type="hide" name="timearray" value="<s:property value='#tdata' />" />
								</s:iterator>
							</div>
							<style>
							.timegird {margin:0 0 10px;line-height:20px;font-size:16px;}
							.timeclose {float:right;cursor:pointer}
							</style>
							
							<script>
								function timeAdd(){
									var stime = $('input[name="timestart"]').val();
									var etime = $('input[name="timeend"]').val();
									var keshi = $('input[name="keshi"]').val();
									var day = $('input[name="day"]').val();
									
									if(stime == "" || etime=="" || keshi=="" || day == null){
										alert("集中面授时间各项不能为空");
										return;
									}
									
									$('#timeinput').append('<input type="hide" name="timearray" value="'+stime+'—'+etime+','+keshi+'学时'+','+day+'天'+'">');
									$('#timeContent').append('<div class="timegird">'+stime+'—'+etime+','+keshi+'学时'+','+day+'天'+'<span onclick="timeDel2(this)" class="timeclose">&times</span><div>');
									
								}
								
								function timeDel2(obj) {
									var obj = $(obj),pobj = obj.parents('.timegird'),index = $('.timegird').index(pobj);
									pobj.remove();
									$('input[name="timearray"]').eq(index).remove();
									
								}
							</script>
							
						</td>
						<td>&nbsp;</td>
					</tr>
					
				</table>
				<br />
				<table width="80%" border="0" style="border: 1px dashed #8bbedc;"
					cellpadding="0" cellspacing="0">

					<tr>
						<td colspan="2" align="center" width="27%"><strong>预算科目</strong></td>
						<td width="12%" align="center"><strong>经费（元）</strong></td>
						<td width="28%" align="center"><strong>计算说明</strong></td>
						<td width="34%" align="center"><strong>支出说明</strong></td>
					</tr>

					<tr>
						<td rowspan="2" align="center"><p align="center">
								<strong>住宿费</strong>
							</p></td>
						<td align="center">学员住宿费</td>
						<td align="left">
							经费: <input type="text" name="total_item_1"  id="total_item_1"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeMeal'/>"
							size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_1">
							
							<input type="text" name="price_item_1" size="2"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMeal.split('\\\\*')[0]"/>"/> 元/人/天× 
							
							<input type="text" id="qty_item_1" 	name="qty_item_1" size="2" 
							value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteMeal.split('\\\\*')[1] != null  "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMeal.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>"/> 
							<span id="span_qty_1">人</span>× 
							
							<input type="text"	name="time_item_1" size="2" 
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMeal.split('\\\\*')[2]"/>"/> 天
							
						</td>
						<td align="left" id="xueyuanzhusuInfor">学员在培训期间的住宿费。</td>
					</tr>
					<tr>
						<td align="center">专家住宿费</td>
						<td align="left">
							经费: <input type="text" name="total_item_2" id="total_item_2"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeAccommodation'/>"
								size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_2">
						
							<input type="text" name="price_item_2" size="2"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteAccommodation.split('\\\\*')[0]"/>" /> 元/人/天× 
							
							<input type="text" id="qty_item_2" name="qty_item_2"
								value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteAccommodation.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteAccommodation.split('\\\\*')[1]"/></s:if>"
								size="2" /> 
							<span id="span_qty_2">人</span>× 
							
							<input type="text" 	name="time_item_2" size="2"
							value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteAccommodation.split('\\\\*')[2]"/>" /> 天
						</td>
						<td align="left" id="zhuanjiazhusuInfor">专家及教师在培训期间的住宿费。</td>
					</tr>

					<tr>
						<td rowspan="2" align="center"><p align="center">
								<strong>伙食费</strong>
							</p></td>
						<td align="center">学员伙食费</td>
						<td align="left">
							经费: <input type="text" name="total_item_14"  id="total_item_14"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeElectronCourse'/>"
							size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_14">
							
							<input type="text" name="price_item_14" size="2"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteElectronCourse.split('\\\\*')[0]"/>"/> 元/人/天× 
							
							<input type="text" id="qty_item_14" 	name="qty_item_14" size="2" 
							value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteElectronCourse.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteElectronCourse.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>"/> 
							<span id="span_qty_14">人</span>× 
							
							<input type="text"	name="time_item_14" size="2" 
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteElectronCourse.split('\\\\*')[2]"/>"/> 天
							
						</td>
						<td align="left" id="xueyuanhuoshiInfor">学员在培训期间的伙食费。</td>
					</tr>
					<tr>
						<td align="center">专家伙食费</td>
						<td align="left">
							经费: <input type="text" name="total_item_15" id="total_item_15"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeMealExpert'/>"
								size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_15">
						
							<input type="text" name="price_item_15" size="2"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMealExpert.split('\\\\*')[0]"/>" /> 元/人次×
							
							<input type="text" name="qty_item_15"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMealExpert.split('\\\\*')[1]"/>"
								size="2" /> 人次
							
							<input type="hidden" name="time_item_15" value="1" size="2" /> 
						</td>
						<td align="left" id="zhuanjiahuoshiInfor">专家及教师在培训期间的伙食费。</td>
					</tr>
					
					<tr>
						<td rowspan="2" align="center"><strong>交通费</strong></td>
						<td align="center">实践性培训交通费</td>
						<td align="left">
							经费: <input type="text" name="total_item_3"
								id="total_item_3" value="" size="5" readonly="readonly" /></td>
						<td align="left" id="td_item_3">
						
							<input type="text" name="price_item_3"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTeach.split('\\\\*')[0]"/>"
								size="2" /> 元/次× 
							
							<input type="text" name="qty_item_3"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTeach.split('\\\\*')[1]"/>"
								size="2" /> 次
								
							<input type="hidden" name="time_item_3" value="1" size="2" />
						
						</td>
						<td align="left" id="xueyuanjiaotongInfor">开展实践性培训学员所需的交通费。</td>
					</tr>
					<tr>
						<td align="center">专家交通费</td>
						<td align="left">
						
							经费: <input type="text" name="total_item_4" id="total_item_4"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeTrafficExpert'/>"
								size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_4">
						
							<input type="text" name="price_item_4"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTrafficExpert.split('\\\\*')[0]"/>"
								size="2" /> 元/人次× 
								
							<input type="text" name="qty_item_4"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTrafficExpert.split('\\\\*')[1]"/>"
								size="2" /> 人次
							
							<input type="hidden" name="time_item_4" value="1" size="2" /> 
						
						</td>
						<td align="left" id="zhuanjiajiaotongInfor">专家往返交通费。</td>
					</tr>
					<tr>
						<td rowspan="3" align="center"><strong>讲课费</strong></td>
						<td align="center">授课费</td>
						<td align="left">
							
							经费: <input type="text" name="total_item_5" id="total_item_5"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeMealAccExpert'/>"
								size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_5">
						
							<input type="text" name="price_item_5" id="price_item_5"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMealAccExpert.split('\\\\*')[0]"/>"
								size="2" /> 
							
							<span id="span_1">元/（人•半天）</span>× 
							
							<input type="text" id="qty_item_5" name="qty_item_5"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMealAccExpert.split('\\\\*')[1]"/>"
								size="2" /> 
								
							<span id="span_qty_6">人次</span> 
							
							<input type="hidden" name="time_item_5" value="1" size="2" />
							
						</td>
						<td align="left" id="xuexiziliaoInfor">聘请授课专家所需费用。</td>
					</tr>
					<tr>
						<td align="center"><p align="center">指导费</p></td>
						<td align="left">
							经费: <input type="text" name="total_item_6" id="total_item_6"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeMaterials'/>"
								size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_6">
							<input type="text" name="price_item_6" id="price_item_6"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMaterials.split('\\\\*')[0]"/>"
								size="2" /> 
							
							<span id="span_1">元/人次</span>× 
							
							<input type="text" id="qty_item_6" name="qty_item_6"
								value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteMaterials.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteMaterials.split('\\\\*')[1]"/></s:if>"
								size="2" /> <span id="span_qty_6">人次</span> 
								
							<input type="hidden" name="time_item_6" value="1" size="2" />
						</td>
						<td align="left" id="zhuanjialaowuInfor">聘请专家及教师进行训前调研与设计、诊断测评、培训指导所需费用。</td>
					</tr>
					<tr>
						<td align="center"><p align="center">培训跟踪指导费</p></td>
						<td align="left">经费: <input type="text" name="total_item_7"
							id="total_item_7"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeAreaRent'/>"
							size="5" readonly="readonly" />
						</td>
						<td align="left" id="td_item_7">
						
							<input type="text" name="price_item_7"  id="price_item_7"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteAreaRent.split('\\\\*')[0]"/>"
								size="2" /> <span id="span_1">元/人次</span>× 
								
							<input type="text" id="qty_item_7" name="qty_item_7"
								value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteAreaRent.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteAreaRent.split('\\\\*')[1]"/></s:if>"
								size="2" /> <span id="span_qty_6">人次</span> 
								
							<input type="hidden" name="time_item_7" value="1" size="2" />
						</td>
						<td align="left" id="jiaoxueInfor">聘请专家对学员进行培训跟踪指导所需费用。</td>
					</tr>
					<tr>
						<td rowspan="3" align="center"><strong>培训资源费</strong></td>
						<td align="center">网络课程资源费</td>
						<td align="left">经费: <input type="text" name="total_item_9"
							id="total_item_9"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeSurvey'/>"
							size="5" readonly="readonly" /></td>
						<td align="left" id="td_item_9">
						
							<input type="text" name="price_item_9"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteSurvey.split('\\\\*')[0]"/>"
								size="2" /> 元/件× 
							
							<input type="text" name="qty_item_9"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteSurvey.split('\\\\*')[1]"/>"
								size="2" /> 件 
								
							<input type="hidden" name="time_item_9" value="1" size="2" />
						
						</td>
						<td align="left" id="">培训期间开展网络研修所需的预设性课程资源和生成性课程资源的设计、制作及使用费。</td>
					</tr>
					
					<tr>
						<td align="center">学习资料费</td>
							<td align="left">经费: <input type="text" name="total_item_8"
								id="total_item_8"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeEquipmentRent'/>"
								size="5" readonly="readonly" />
							</td>
							<td align="left" id="td_item_8">
							
								<input type="text" name="price_item_8" id="price_item_8"
									value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteEquipmentRent.split('\\\\*')[0]"/>"
									size="2" /> 元/人× 
									
								<input type="text" id="qty_item_8" name="qty_item_8"
									value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteEquipmentRent.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteEquipmentRent.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>"
									size="2" /> <span id="span_qty_8">人</span>
								
								<input type="hidden" name="time_item_8" value="1" size="2" />
							</td>
							<td align="left" id="pxziliaoInfor">为学员学习提供培训资料（文本和教材等）所需费用。</td>
					</tr>
					
					<tr>
						<td align="center">办公用品费</td>
							<td align="left">经费: <input type="text" name="total_item_16"
								id="total_item_16"
								value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeOfficeSupplies'/>"
								size="5" readonly="readonly" />
							</td>
							<td align="left" id="td_item_16">
							
								<input type="text" name="price_item_16" id="price_item_16"
									value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteOfficeSupplies.split('\\\\*')[0]"/>"
									size="2" /> 元/人× 
									
								<input type="text" id="qty_item_16" name="qty_item_16"
									value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteOfficeSupplies.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteOfficeSupplies.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>"
									size="2" /> <span id="span_qty_16">人</span>
								
								<input type="hidden" name="time_item_16" value="1" size="2" />
							</td>
							<td align="left" id="officesupplies">培训期间必要的办公用品费。</td>
					</tr>
					
					<tr>
						<td rowspan="3" align="center"><p align="center">
								<strong>培训场地及设备费</strong>
							</p></td>
						<td align="center">场地租用费</td>
						<td align="left">经费: <input type="text" name="total_item_10"
							id="total_item_10"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeResearch'/>"
							size="5" readonly="true" />
						</td>
						<td align="left" id="td_item_10">
						
							<input type="text"
								name="price_item_10"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteResearch.split('\\\\*')[0]"/>"
								size="2" /> 元/班次/天× 
								
							<input type="text" name="qty_item_10"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteResearch.split('\\\\*')[1]"/>"
								size="2" /> 班次× 
								
							<input type="text" name="time_item_10"
								value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteResearch.split('\\\\*')[2]"/>"
								size="2" /> 天
						
						</td>
						<td rowspan="3" align="left" id="changdiInfor">培训所需教室、实验室、场地、网络研修平台及相关设备的租用和服务费。</td>
					</tr>
					<tr>
						<td align="center">网络研修平台租用费</td>
						<td align="left">经费: <input type="text" name="total_item_11"
							id="total_item_11"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeArgument'/>"
							size="5" readonly="true" />
						</td>
						<td align="left" id="td_item_11">
						
						<input type="text"
							name="price_item_11"
							value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteArgument.split('\\\\*')[0]"/>"
							size="2" /> 元/人×
						
						<input type="text" id="qty_item_11"
							name="qty_item_11"
							value="<s:if test="peFeeBudget.peFeeBudgetNewDetail.noteArgument.split('\\\\*')[1] != null "><s:property value="peFeeBudget.peFeeBudgetNewDetail.noteArgument.split('\\\\*')[1]"/></s:if><s:else><s:property value="#total_persons"/></s:else>"
							size="2" /> <span id="span_qty_11">人 </span>
							
						<input type="hidden" name="time_item_11" value="1" size="2" />
					
					</td>
					</tr>
					<tr>
						<td align="center">设备租用费</td>
						<td align="left">经费: <input type="text" name="total_item_12"
							id="total_item_12"
							value="<s:property value='peFeeBudget.peFeeBudgetNewDetail.feeTrafficStu'/>"
							size="5" readonly="true" /></td>
						<td align="left" id="td_item_12">
						
						<input type="text"
							name="price_item_12"
							value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTrafficStu.split('\\\\*')[0]"/>"
							size="2" /> 元/班次/天× 
							
						<input type="text" name="qty_item_12"
							value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTrafficStu.split('\\\\*')[1]"/>"
							size="2" /> 班次× 
							
						<input type="text" name="time_item_12"
							value="<s:property value="peFeeBudget.peFeeBudgetNewDetail.noteTrafficStu.split('\\\\*')[2]"/>"
							size="2" /> 天
							
						</td>
					</tr>
					
					<tr>
						<td align="center"><strong>合计</strong></td>
						<td>&nbsp;</td>
						<td colspan="2" align="left">
							<div id="grandTotal" style="color: red"></div>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<p>
								<span style='font-family:华文中宋'>说明：</span>
								<span style='mso-bidi-font-size:10.5pt;font-family:华文中宋'>按项目需要填写，不需要科目注“<span>0</span>”</span>
								<span style='font-family:华文中宋'></span>
							</p>
						</td>
					</tr>
				</table>
				<br />
				<table width="80%" border="0" style="border: 1px dashed #8bbedc;"
					cellpadding="0" cellspacing="0">
					<tr>
						<td width="50%" align="right">
							收款单位名称： 
							<input type="text"
								name="peFeeBudget.payeeName" id="peFeeBudget.payeeName"
								value="<s:property value='peFeeBudget.payeeName'/>"
								maxlength="50" style="width: 73%" />
						</td>
						<td width="50%" align="right">
							开户银行： <input type="text"
							name="peFeeBudget.openingBank" id="peFeeBudget.openingBank"
							value="<s:property value='peFeeBudget.openingBank'/>"
							maxlength="25" style="width: 73%" />
						</td>
					</tr>
					<tr>
						<td align="right">账号： <input type="text"
							name="peFeeBudget.accountNumber" id="peFeeBudget.accountNumber"
							value="<s:property value='peFeeBudget.accountNumber'/>"
							maxlength="25" style="width: 73%" />
						</td>
						<td align="right">
							项目联系人及电话：						
							<input type="text"
								name="peFeeBudget.contactInfo" id="peFeeBudget.contactInfo"
								value="<s:property value='peFeeBudget.contactInfo'/>"
								maxlength="25" style="width: 73%" />
						</td>
					</tr>
				</table>
				<div id="msg" style="color: red;"></div>
				<div>
					<input type="submit" value="提交" id="tijiao" /> &nbsp;&nbsp;&nbsp;&nbsp; <input
						type="reset" value="重置" />
				</div>
				<!--申报批次ID  -->
				<input type="hidden" name="praid" id="praid" value="<s:property value='peProApplyno.id'/>" />
				<!--预算表ID  -->
				<input type="hidden" name="pbid" id="pbid" value="<s:property value='peFeeBudget.id'/>" />
				
			</form>
		</div>
</body>
</html>

