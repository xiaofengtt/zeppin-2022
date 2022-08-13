
var jsonDate={ 
		"flagCloseend":{  //是否封闭产品
			"true":"是",
			"false":"否"
		},
		"currencyType":{  //理财币种
			"rmb":"人民币",
			"dollar":"外币"
		},
		"flagRedemption":{
			"false":"不可提前赎回",
			"true":"可提前赎回"
		},
		"flagFlexible":{
			"false":"否",
			"true":"是"
		},
		"flagPurchase":{
			"false":"不可在投资期间申购",
			"true":"可在投资期间申购"
		}
}
$(document).ready(function() {
	getbankList();	
	$("#valueDate,#maturityDate,#recordDate").click(function(){
		laydate({
            start: laydate.now(0, "YYYY/MM/DD"),
            istime: false,
            istoday: false,
            format: 'YYYY-MM-DD'
        });
	});
	$("#collectStarttime,#collectEndtime").click(function(){
		laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm:ss"),
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
	});
	
	$("#resourceId").uploadFile({
		id:"1",
		url:"../rest/backadmin/resource/add",
		allowedTypes:"doc,docx,pdf",
		maxFileSize:1024*1024*1024*10,
		fileName:"file",
		maxFileCount : 1,
		dragDropStr: "",
		extErrorStr:"文件格式不正确，请上传指定类型的文件",
		multiple:false,
		showDone:false,
		showDelete : true,
		deletelStr : '删除',
		doneStr: "完成",
		showAbort:false,
		showStatusAfterSuccess : false,
		maxFileCountErrorStr: "文件数量过多，请先删除。",
		onSuccess:function(files,data,xhr){
			$('input[name="document"]').val(data.data.uuid);
			$('#imageShow').attr('href','..'+data.data.url).html(data.data.filename+'.'+data.data.filetype).show();
			$(".ajax-upload-dragdrop").css("display","none");
		}
	});
	
	$("#subscribeFee").blur(function(){
		var subscribeFee = $("#subscribeFee").val().replace(/(^\s*)|(\s*$)/g, "");
		if(subscribeFee != ""){
			$("#subscribeFee").val(FormatAfterDotNumber(subscribeFee,2));
		}
	})
	$("#purchaseFee").blur(function(){
		var purchaseFee = $("#purchaseFee").val().replace(/(^\s*)|(\s*$)/g, "");
		if(purchaseFee != ""){
			$("#purchaseFee").val(FormatAfterDotNumber(purchaseFee,2));
		}
	})
	$("#redemingFee").blur(function(){
		var redemingFee = $("#redemingFee").val().replace(/(^\s*)|(\s*$)/g, "");
		if(redemingFee != ""){
			$("#redemingFee").val(FormatAfterDotNumber(redemingFee,2));
		}
	})
	$("#custodyFee").blur(function(){
		var custodyFee = $("#custodyFee").val().replace(/(^\s*)|(\s*$)/g, "");
		if(custodyFee != ""){
			$("#custodyFee").val(FormatAfterDotNumber(custodyFee,2));
		}
	})
	$("#networkFee").blur(function(){
		var networkFee = $("#networkFee").val().replace(/(^\s*)|(\s*$)/g, "");
		if(networkFee != ""){
			$("#networkFee").val(FormatAfterDotNumber(networkFee,2));
		}
	})
	$("#managementFee").blur(function(){
		var managementFee = $("#managementFee").val().replace(/(^\s*)|(\s*$)/g, "");
		if(managementFee != ""){
			$("#managementFee").val(FormatAfterDotNumber(managementFee,2));
		}
	})
	
	//默认值规则
	$('.defaultkey').focus(function(){
		var value = $(this).val();
		if(value=='0'){
			value=''
		}
		$(this).val(value);
	})
	$('.defaultkey1').focus(function(){
		var value = $(this).val();
		if(value=='0.00'){
			value=''
		}
		$(this).val(value);
	})
});

function getbankList(){
	//初始化资金托管方数据
	$.get('../rest/backadmin/bank/list?pageNum=1&pageSize=1000',function(r) {
		tinymecTool.load('investScope','');
		tinymecTool.load('revenueFeature','');
		tinymecTool.load('remark','');
		if(r.status =='SUCCESS') {
			var htmls = '';
			for(var i=0; i<r.totalResultCount; i++){
				htmls+='<option value="'+r.data[i].uuid+'" data-logo="'+r.data[i].logoUrl+'">'+r.data[i].name+'</option>';
				custodian[r.data[i].uuid]=r.data[i].name;
			}
			$('#custodian').append(htmls);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(){
		getareaList();		
	});
}

function getareaList(){
	//初始化发售地区数据
	$.get('../rest/backadmin/area/all',function(r) {
		if(r.status =='SUCCESS') {
			var htmls = '';
			for(var i=0; i<r.totalResultCount; i++){
				htmls+='<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$('#area').append(htmls);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
}

$("#valueDate").click(function(){
	laydate({
        start: laydate.now(0, "YYYY/MM/DD hh:mm"),
        istime: false,
        istoday: false,
        format: 'YYYY-MM-DD',
        choose: function(datas){
    		var maturityDate = $("#maturityDate").val().replace(/(^\s*)|(\s*$)/g, "");
        	if(datas != "" && maturityDate != ""){
    			if(compareDate(datas,maturityDate)){
    				var days = GetDateDiff(datas,maturityDate);
    				$("#term").val(days);
    			}
    		}
        }
    });
});
$("#maturityDate").click(function(){
	laydate({
        start: laydate.now(0, "YYYY/MM/DD hh:mm"),
        istime: false,
        istoday: false,
        format: 'YYYY-MM-DD',
        choose: function(datas){
        	if(valueDate != "" && datas != ""){
        		var valueDate = $("#valueDate").val().replace(/(^\s*)|(\s*$)/g, "");
    			if(compareDate(valueDate,datas)){
    				var days = GetDateDiff(valueDate,datas);
    				$("#term").val(days);
    			}
    		}
        }
    });
});
//比较日期大小  
function compareDate(checkStartDate, checkEndDate) {      
	var arys1= new Array();      
	var arys2= new Array();      
	if(checkStartDate != null && checkEndDate != null) {   
		
	    arys1=checkStartDate.split('-');   
	    
      	var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);      
      	arys2=checkEndDate.split('-');      
      	var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);      
		if(sdate > edate) {      
		    return false;         
		}  else {
		    return true;      
		} 
	}      
}
function GetDateDiff(startDate,endDate) {  
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
    return  dates;    
}

$("#formsubmit").submit(function(){
	$('input[name=investScope]').val(string2json2(tinyMCE.get('investScope').getContent()));
	$('input[name=revenueFeature]').val(string2json2(tinyMCE.get('revenueFeature').getContent()));
	$('input[name=remark]').val(string2json2(tinyMCE.get('remark').getContent()));
	var str = $(this).serialize();
	$.post('../rest/backadmin/bankFinancialProduct/add?'+str, function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('保存成功', {
				time: 1000 
			}, function(){
				window.close();
			}); 
		} else {
			layer.msg(r.message, {
				time: 2000 
			});
		}
	}); 
	return false;
});
function changeCustodian(){
	var url = $('#custodian option:selected').attr('data-logo');
	if(url != undefined && url!='' && url!='null'){
		$('#custodianLogo').attr('src','..'+url);
	}else{
		$('#custodianLogo').attr('src','');
	}
	
}