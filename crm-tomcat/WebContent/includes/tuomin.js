//��������
var sensitiveSwitch = true;
//��ȡ��������DOM�ڵ��ֵ��Ŀǰ������Ҫ���ֵ�����input��ʹ��val����������DOMʹ��html����
function getSenValue(obj){
	if(obj.tagName.toLowerCase() == 'input'){
		return $.trim($(obj).val());
	}else{
		return $.trim($(obj).html());
	}
};
//������������DOM�ڵ��ֵ��Ŀǰ������Ҫ���ֵ�����input��ʹ��val����������DOMʹ��html����
function setSenValue(obj,val){
	if(obj.tagName.toLowerCase() == 'input'){
		$(obj).val(val);
	}else{
		$(obj).html(val);
	}
};
//�������÷���-���س������lastLenλ
function showOnlyLast(val,lastLen){
	return val.replace(/(.)/g,function(a,b,c,d){return (c<(val.length-lastLen))?'*':a});
}
//�������÷���-ֻ��ʾǰlenλ
function showOnlyFirst(val,len){
	return val.replace(/(.)/g,function(a,b,c,d){return (c>len)?'*':a});
}
$(function(){
	if(sensitiveSwitch){
		var tmpstr = '';
		//�ͻ���������
		$(".tuomin-custname").each(function(){
			setSenValue(this, getSenValue(this).substr(0,1)+'**');
		});
		//���֤������
		$(".tuomin-cardid").each(function(){
			tmpstr = getSenValue(this);
			setSenValue(this,showOnlyLast(tmpstr,4));
		});
		//�绰���롢�ֻ�����
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
		//�����ʼ�����
		$(".tuomin-email").each(function(){
			tmpstr = getSenValue(this);
			var splitIndex = tmpstr.indexOf('@');
			if(tmpstr && splitIndex>1){
				setSenValue(this,showOnlyFirst(tmpstr.substr(0,splitIndex),0)+tmpstr.substring(splitIndex,tmpstr.length));
			}
		});
		//��ַ����
		$(".tuomin-address").each(function(){
			tmpstr = getSenValue(this);
			var patt = /.*��*��/g;
			var ret = patt.exec(tmpstr);
			if(ret){
				setSenValue(this,showOnlyFirst(tmpstr, tmpstr.indexOf(ret[0])+ret[0].length-1));
			}
		});
		//�����˻�����
		$(".tuomin-bankacctname").each(function(){
			setSenValue(this, getSenValue(this).substr(0,1)+'**');
		});
		//�����˺�
		$(".tuomin-bankacct").each(function(){
			tmpstr = getSenValue(this);
			setSenValue(this,showOnlyLast(tmpstr,4));
		});
		//֧������
		$(".tuomin-banksubname").each(function(){
			setSenValue(this, getSenValue(this).substr(0,1)+'**');
		});
		//֧������2(����������֧�������á�-������һ��ʱ)
		$(".tuomin-banksubname2").each(function(){
			tmpstr = getSenValue(this);
			var splitIndex = tmpstr.indexOf('-')+2;
			if(tmpstr && splitIndex>1){
				setSenValue(this,getSenValue(this).substr(0,splitIndex)+'**');
			}
		});
	}
});