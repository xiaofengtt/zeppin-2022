$(function(){
	$(".main-left-item:eq(1)").children("a").addClass("color-orange").children("img").attr("src","./img/r-2.png");
	$(".main-left-item:eq(1) ul li:eq(1) a").addClass("color-orange");
});

//获取充值信息
function getInfo(){
	$.ajax({
        url: '../rest/qcb/companyAccount/getVirtualAccount',
        type: 'get',
        data: {   
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
			$(".companyAccountName").html(r.data.companyAccountName);
			$(".companyAccountNum").html(r.data.companyAccountNum.replace(/\s/g,'').replace(/(.{4})/g,"$1 ")+
					" <span class='color-orange'>"+r.data.accountNum+"</span>");
			$(".branchBankName").html(r.data.branchBankName);
			$(".branchBankAddress").html(r.data.branchBankAddress);
			$(".loadingOver").show();
        	$(".loadingDiv").hide();
		} else {
//			layer.msg(r.message);
		}
    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}