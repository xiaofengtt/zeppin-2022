//表单非空验证
function input_test(ele) {
	$(ele).blur(function() {
		var _this = $(this);
			if(_this.val() == '') {
				_this.parent().addClass("b_b_r");
			} else {
				_this.parent().removeClass("b_b_r");
			}
	});
};

//非空+非数字验证；
function input_test_nan(ele) {
	$(ele).blur(function() {
		var _this = $(this);
			if(_this.val() == '' || isNaN(_this.val()) == true) {
				_this.parent().addClass("b_b_r");
			} else {
				_this.parent().removeClass("b_b_r");
			}
	})
};

function input_test_reg(ele,reg){
	$(ele).blur(function(){
		var _this = $(this);
			if(reg.test(_this.val())==false){
				_this.parent().addClass("b_b_r");
				
			}else{
				_this.parent().removeClass("b_b_r");
			}
	})
}