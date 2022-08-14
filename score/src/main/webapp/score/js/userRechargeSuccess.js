/**
 * 
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function(){
	getDetail();
});

//详情
function getDetail(){
	$.ajax({
        url: '../back/userHistory/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var type = "";//类型
				if(r.data.type=="add"){
					type = "录入";
				}else if(r.data.type=="confirm"){
					type = "确认";
				}
				$("#frontUserMobile").val(r.data.frontUserMobile);//手机号
    			$("#income").val(moneyFormat(r.data.income));
    			$("#capitalPlatformName").val(r.data.capitalPlatformName);
    			$("#poundage").val(moneyFormat(r.data.poundage));
    			$("#capitalAccountName").val(r.data.capitalAccountName);
    			$("#transData").val(r.data.transData);
    			$("#creatTime").val(formatDate(r.data.createtime));
    			$('#imageShow').attr('src', '..' + r.data.frontUserAccountCheckProof).show();
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
        	
        });
    });   	
}

