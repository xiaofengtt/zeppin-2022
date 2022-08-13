$(function() {
			var id = url('?id');
				$('input[name="id"]').val(id);
			$.get('../front/admin/videoinfo!execute?uid=g0002&id='+url('?id'),function(r){
				if(r.status == 'success') {
					var st = JSON.stringify(r);
				    var template = $('#DataTpl').html();
				    var html = Mustache.render(template, $.parseJSON(st));
				    $('#HtmlTpl').html(html);
				}else {
					alert('服务端出错！请稍后重试！');
				}
			}).done(function(){
				$('#formsubmit').submit(function() {
					var str = $(this).serialize();
					$.post('../front/admin/videoinfo!execute?uid=g0005&'+ str, function(data) {
						if (data.status == "success") {							
							$("#contentIframe",window.top.document).attr("src","../views/videoList.html");
						} else {
							$('.alert-danger').html(data.message).show();
						}
					})
					return false;
				});
			})
			
			//项目类型 helper 函数
			$(document).on("click",function(e) {
				if(!$(e.target).parents().is('.ufc'))
					$('.uul').hide();
			});
		})