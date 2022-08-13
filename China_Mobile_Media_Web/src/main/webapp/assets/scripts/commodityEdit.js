function isMoney(obj){
		   if (! obj) return false;
		   return (/^\d+(\.\d{1,2})?$/).test(obj);   
		}
		$(function() {
			var id = url('?id');
				$('input[name="id"]').val(id);
			$.get('../front/admin/commodity!execute?uid=b0002&id='+url('?id'),function(r){
				if(r.status == 'success') {
					var st = JSON.stringify(r);
				    var template = $('#DataTpl').html();
				    var html = Mustache.render(template, $.parseJSON(st));
				    $('#HtmlTpl').html(html);
				    $("#resourceId").uploadFile({
						url:"../front/admin/resource!execute?uid=d0001",
						allowedTypes:"jpg,png,jpeg,bmp,tiff",
						maxFileSize:1024*1024*1024*10,
						fileName:"video",
						maxFileCount : 1,
						dragDropStr: "",
						extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
						showStatusAfterSuccess:true,
						showDelete : true,
						showDone : false,
						showProgress: true,
						deletelStr : '删除',
						showAbort:true,
						onSuccess:function(files,data,xhr){
							if(data.status=="success"){
								$('input[name="cover"]').val('');
								$('input[name="cover"]').val(data.data.id);
								layer.msg("上传成功！");
								$("#coverImg").remove();
								$("#change").hide();
								
							}else{
								$('.alert-danger').html(data.message).show();
								layer.msg(data.message);
							}
						}
					});
					
					$("#resourceId2").uploadFile({
						url:".../front/admin/resource!execute?uid=d0002",
						allowedTypes:"rar,zip",
						maxFileSize:1024*1024*1024*10,
						fileName:"display",
						maxFileCount : 1,
						dragDropStr: "",
						extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
						showStatusAfterSuccess:false,
						showDelete : false,
						showProgress: true,
						deletelStr : '删除',
						showAbort:false,
						onSuccess:function(files,data,xhr){
							if(data.status=="success"){
								$('.alert-danger').hide();
								$('input[name="displays"]').val('');
								$('input[name="displays"]').val(data.data);
								layer.msg("上传成功！");
								
							}else{
								$('.alert-danger').html(data.message).show();
								layer.msg(data.message);
							}
						}
					});
					
					$("#change").click(function(){
						$("#coverImg").remove();
						$('input[name="cover"]').val('');
						$(this).hide();
					});
				}else {
					alert('服务端出错！请稍后重试！');
				}
			}).done(function(){
				$('#formsubmit').submit(function() {
					$('.alert-danger1').hide();
					$('.alert-danger2').hide();
					$('.alert-danger3').hide();
					$('.alert-danger4').hide();
					var name = $('input[name="name"]').val();
					var url = $('input[name="url"]').val();
					var price = $('input[name="price"]').val();
					var oprice = $('input[name="originalPrice"]').val();
					
					if(name == ""){
						$('.alert-danger3').html("请填写名称").show();
						return false;
					}
					
					if(oprice == ""){
						$('.alert-danger1').html("请填写原价").show();
						return false;
					}
					if(price == ""){
						$('.alert-danger2').html("请填写销售价格").show();
						return false;
					}
					
					if(!isMoney(oprice)){
						$('.alert-danger1').html("格式非法（只允许填写整数、小数）").show();
						return false;
					}
					if(!isMoney(price)){
						$('.alert-danger2').html("格式非法（只允许填写整数、小数）").show();
						return false;
					}
					if(url == ""){
						$('.alert-danger4').html("请填写商品URL").show();
						return false;
					}
					var str = $(this).serialize();
					$.post('../front/admin/commodity!execute?uid=b0004&'+ str, function(data) {
						if (data.status == "success") {
							$("#contentIframe",window.top.document).attr("src","../views/commodityList.html");
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