$(function() {
	var _w = 456;
	var _h = 344;
	var _old = {};
	var _new = {};
	var _txt = $(".textarea textarea");

	$(".upload-btn input").on("change", function() {
		var _this = $(this);
		var fr = new FileReader();
		fr.readAsDataURL(this.files[0]);

		var img = new Image();
		var btn = _this.parent();
		btn.hide();
		var upImg = btn.siblings(".upload-img");
		upImg.addClass("loading");

		fr.onload = function() {
			img.src = this.result;
			img.onload = function() {
				btn.siblings(".upload-img").html(img);
				var ratio = 1;
				if (img.width > img.height) {
					upImg.find("img").addClass("mh");
					ratio = _h / img.height;
				} else {
					upImg.find("img").addClass("mw");
					ratio = _w / img.width;
				}
				$('#file').attr('src',img.src);
				var scroll = new IScroll(upImg[0], {
					zoom : true,
					scrollX : true,
					scrollY : true,
					mouseWheel : true,
					bounce : false,
					wheelAction : 'zoom'
				});

				if (btn.hasClass("btn-old")) {
				//	ajaxFileUpload("image_btn1", "#image1");
					_old.img = img;
					_old.scroll = scroll;
					_old.ratio = ratio;
				}
				if (btn.hasClass("btn-new")) {
					//ajaxFileUpload("image_btn2", "#image2");
					_new.img = img;
					_new.scroll = scroll;
					_new.ratio = ratio;
				}

				setTimeout(function() {
					upImg.removeClass("loading").find("img").css("opacity", 1);
				}, 1000);
			}
		}
	});

	function imageData(obj) {
		obj.scroll.enabled = false;
		var canvas = document.createElement('canvas');
		
		canvas.width = _w;
		canvas.height = _h;
		var ctx = canvas.getContext('2d');

		var w = _w / obj.scroll.scale / obj.ratio;
		var h = _h / obj.scroll.scale / obj.ratio;
		var x = Math.abs(obj.scroll.x) / obj.scroll.scale / obj.ratio;
		var y = Math.abs(obj.scroll.y) / obj.scroll.scale / obj.ratio;

		ctx.drawImage(obj.img, x, y, w, h, 0, 0, _w, _h);
		return canvas.toDataURL();
	}

	function ajaxFileUpload(image, image_) {
		$.ajaxFileUpload({
			url : '/xdb/ajax/ajax!upLoad.do',// servlet请求路径
			secureuri : false,
			fileElementId : image,// 上传控件的id
			dataType : 'json',
			data : {
				paramName : image
			}, // 参数名称
			success : function(data, status) {
				$(image_).val(data.msg);
			},
			error : function(data, status, e) {
				alert('上传出错');
			}
		});

		return false;

	}
});
