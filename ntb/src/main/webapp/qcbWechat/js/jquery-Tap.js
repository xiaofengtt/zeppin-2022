//function Tap(options) {
//	var _this = this;
//	this.el = options.select;
//	this.callback = options.callback;
//	this.startTime = "",
//		this.endTime = "",
//		this.flag;
//	$(this.el).on("touchstart", function() {
//		_this.flag = true;
//		_this.startTime = Date.now();
//	});
//
//	$(this.el).on("touchmove", function() {
//		_this.flag = false;
//	});
//
//	$(this.el).on("touchend", function(event) {
//		if(_this.flag == false) {
//			return false;
//		}
//		_this.endTime = Date.now();
//		if(_this.endTime - _this.startTime <= 300) {
//			_this.callback(event);
//		}
//	});
//}

$.fn.extend({
	tap: function(callback) {
		this.startTime = "",
			this.endTime = "",
			this.flag;
		this.on("touchstart", function() {
			this.flag = true;
			this.startTime = Date.now();
		});

		this.on("touchmove", function() {
			this.flag = false;
		});

		this.on("touchend", function(event) {
			if(this.flag == false) {
				return false;
			}
			this.endTime = Date.now();
			if(this.endTime - this.startTime <= 300) {
				callback(event);
			}
		});
	}
});