//表单非空验证
function input_test(ele) {
	$(ele).blur(function() {
		var _this = $(this);
		setTimeout(function() {
			if(_this.val() == '') {
				_this.siblings(".warning").show();
				_this.css({"border-color":"#E0615F"});
			} else {
				_this.siblings(".warning").hide();
				_this.css({"border-color":"#C1C1C1"});
			}
		}, 120);
	});
};


//非空+非数字验证；
function input_test_nan(ele) {
	$(ele).blur(function() {
		var _this = $(this);
		setTimeout(function() {
			if(_this.val() == '' || isNaN(_this.val()) == true) {
				_this.siblings(".warning").show();
				_this.css({"border-color":"#E0615F"});
			} else {
				_this.siblings(".warning").hide();
				_this.css({"border-color":"#C1C1C1"});
			}
		}, 120);
	})
};
function input_test_nanl(ele) {
	$(ele).blur(function() {
		var _this = $(this);
		setTimeout(function() {
			if(_this.val() == '' || isNaN(_this.val()) == true || _this.val().length > 4) {
				_this.siblings(".warning").show();
				_this.css({"border-color":"#E0615F"});
			} else {
				_this.siblings(".warning").hide();
				_this.css({"border-color":"#C1C1C1"});
			}
		}, 120);
	})
};

//正则验证

function input_test_reg(ele,reg){
	$(ele).blur(function(){
		var _this = $(this);
		setTimeout(function(){
			if(reg.test(_this.val())==false){
				_this.siblings(".warning").show();
				_this.css({"border-color":'#E0615F'});
				
			}else{
				_this.siblings(".warning").hide();
				_this.css({"border-color":"#C1C1C1"});
			}
		},120);
	})
}
