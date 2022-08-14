//脱敏开关
var sensitiveSwitch = true;
//获取需脱敏的DOM节点的值，目前遇到需要区分的是在input中使用val（）、其他DOM使用html（）
function getSenValue(obj){
	if(obj.tagName.toLowerCase() == 'input'){
		return $.trim($(obj).val());
	}else{
		return $.trim($(obj).html());
	}
};
//设置需脱敏的DOM节点的值，目前遇到需要区分的是在input中使用val（）、其他DOM使用html（）
function setSenValue(obj,val){
	if(obj.tagName.toLowerCase() == 'input'){
		$(obj).val(val);
	}else{
		$(obj).html(val);
	}
};
//脱敏公用方法-隐藏除了最后lastLen位
function showOnlyLast(val,lastLen){
	return val.replace(/(.)/g,function(a,b,c,d){return (c<(val.length-lastLen))?'*':a});
}
//脱敏公用方法-只显示前len位
function showOnlyFirst(val,len){
	return val.replace(/(.)/g,function(a,b,c,d){return (c>len)?'*':a});
}
$(function(){
	if(sensitiveSwitch){
		var tmpstr = '';
		//客户姓名脱敏
		$(".tuomin-custname").each(function(){
			setSenValue(this, getSenValue(this).substr(0,1)+'**');
		});
		//身份证号脱敏
		$(".tuomin-cardid").each(function(){
			tmpstr = getSenValue(this);
			setSenValue(this,showOnlyLast(tmpstr,4));
		});
		//电话号码、手机脱敏
		$(".tuomin-tel").each(function(){
			tmpstr = getSenValue(this);
			if(tmpstr){
				if(tmpstr.indexOf('/')>-1){
					var tmparr = tmpstr.split('/');
					for(var i=0;i<tmparr.length;i++){
						tmparr[i] = $.trim(tmparr[i]);
						if(tmparr[i].substr(0,1) == '1' && tmparr[i].length == 11){
							tmparr[i] = tmparr[i].replace(/(\w)/g,function(a,b,c,d){return (c>2 && c<(tmparr[i].length-4))?'*':a});
						}else{
							tmparr[i] = showOnlyLast(tmparr[i],4);
						}
					}
					console.dir(tmparr);
					setSenValue(this,tmparr.join("/"));
				}else{
					if(tmpstr.substr(0,1) == '1' && tmpstr.length == 11){
						setSenValue(this,tmpstr.replace(/(\w)/g,function(a,b,c,d){return (c>2 && c<(tmpstr.length-4))?'*':a}));
					}else{
						setSenValue(this,showOnlyLast(tmpstr,4));
					}
				}
			}
		});
		//电子邮件脱敏
		$(".tuomin-email").each(function(){
			tmpstr = getSenValue(this);
			var splitIndex = tmpstr.indexOf('@');
			if(tmpstr && splitIndex>1){
				setSenValue(this,showOnlyFirst(tmpstr.substr(0,splitIndex),0)+tmpstr.substring(splitIndex,tmpstr.length));
			}
		});
		//地址脱敏
		$(".tuomin-address").each(function(){
			tmpstr = getSenValue(this);
			var patt = /.*市*区/g;
			var ret = patt.exec(tmpstr);
			if(ret){
				setSenValue(this,showOnlyFirst(tmpstr, tmpstr.indexOf(ret[0])+ret[0].length-1));
			}
		});
		//银行账户名称
		$(".tuomin-bankacctname").each(function(){
			setSenValue(this, getSenValue(this).substr(0,1)+'**');
		});
		//银行账号
		$(".tuomin-bankacct").each(function(){
			tmpstr = getSenValue(this);
			setSenValue(this,showOnlyLast(tmpstr,4));
		});
		//支行名称
		$(".tuomin-banksubname").each(function(){
			setSenValue(this, getSenValue(this).substr(0,1)+'**');
		});
		//支行名称2(本行名称与支行名称用‘-’连在一起时)
		$(".tuomin-banksubname2").each(function(){
			tmpstr = getSenValue(this);
			var splitIndex = tmpstr.indexOf('-')+2;
			if(tmpstr && splitIndex>1){
				setSenValue(this,getSenValue(this).substr(0,splitIndex)+'**');
			}
		});
	}
});