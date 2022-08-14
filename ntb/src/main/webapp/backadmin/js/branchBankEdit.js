var bank='';
$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/branchBank/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#DataCnt').html(html);
		    bank = r.data.bank;
		    $('#branchBank').attr('href','branchBankList.jsp?uuid='+bank);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
});
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
	
	if(name==""){
		layer.msg('支行名称不能为空', {
			time: 2000 
		});    
	}else if(address==""){
		layer.msg('支行地址不能为空', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/branchBank/edit',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('修改成功', {
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