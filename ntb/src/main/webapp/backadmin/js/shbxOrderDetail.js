var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function() {
    getDate();
});

//获取值
function getDate() {
    $.get('../rest/backadmin/shbxOrder/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
        	var template = $.templates("#headTpl").render(r.data);
        	var template1 = $.templates("#bodyTpl").render(r.data);
            $("#head_box").html(template);
            $("#body_box").html(template1);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function(){
    	$(".process").colorbox({
		    iframe: true,
		    width: "400px",
		    height: "450px",
		    opacity: '0.5',
		    overlayClose: false,
		    escKey: true
		});
    });
}

