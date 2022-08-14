$(document).ready(function() {
	var bank = (url('?bank') != null) ? url('?bank') : '';
	$('#branchBank').attr('href','branchBankList.jsp?uuid='+bank);
})


//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var address=$("#address").val().replace(/(^\s*)|(\s*$)/g, "");
	var bank = (url('?bank') != null) ? url('?bank') : '';
	if(bank==""){
		layer.msg('页面出错，请刷新页面', {
			time: 2000 
		});
	}else if(name==""){
		layer.msg('支行名称不能为空', {
			time: 2000 
		});
	}else if(address==""){
		layer.msg('支行地址不能为空', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		str = str +'&bank='+ bank;
		$.post('../rest/backadmin/branchBank/add',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
//					window.parent.getList();
//					parent.$.colorbox.close()
					window.location.href="branchBankList.jsp?uuid="+bank;
				}); 
			} else {
				layer.msg(data.message, {
					time: 2000 
				})
			}
		}) 
	}
	return false;
});