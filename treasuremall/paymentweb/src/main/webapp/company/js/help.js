/**
 * 商户首页
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'正常',
	'disable':'停用'
}
$(function(){
	getCompany();
});
layui.use(['table', 'layer' ,'element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
});
function getCompany(){
	$.ajax({
        url: '../store/company/get',
        type: 'get',
        async:false,
        data: {}
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$('#code').html(r.data.code);
			$('#publicKey').html(r.data.systemPublic);
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
        });
    });   	
}