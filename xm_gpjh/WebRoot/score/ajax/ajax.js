//////////////////////////
function submitRequest(formObj,method,type,myProcess,conname){
	if(formObj){//判断form不为null
		var sinfo=null;
		var url =formObj.action;//获得url
		if(url.indexOf("?")==-1){
			url+="?time=";
		}else{
			url+="&time=";
		}
		url+=new Date().getTime();//url最后跟时间防止ie缓存
		if((method.toLowerCase())=="post"){
			sinfo =formToString(formObj);//做换字符编码
		}
		request(url,method,sinfo,type,myProcess,conname);//提交请求
		return true;
	}
	return false;
}

//把表单中的参数转为字符串
function formToString(formObj){
	var allStr="";
	if(formObj){
		var elementsObj=formObj.elements;
		var obj;
		if(elementsObj){
			for(var i=0; i<elementsObj.length;i+=1){
				obj=elementsObj[i];
				if(obj.name!=undefined&&obj.name!=""&&((obj.type!=undefined&&obj.type=='checkbox'&&obj.checked)||(obj.type==undefined&&!obj.disabled)||(obj.type!=undefined&&obj.type!='checkbox'&&!obj.disabled))){
					allStr+="&"+obj.name+"="+encodeURIComponent(obj.value);
				}
			}
		}else{
			alert("没有elements对象!");
			return ;
		}
	}else{
		alert("form不存在!");
		return ;
	}
	return allStr;
}

function request(url,method,sinfo,type,myProcess,conname) {
	var http_request =init_request();
	http_request.onreadystatechange = function(){
		if (http_request.readyState == 4&&http_request.status == 200) {
			var result="";
			if (type==null||type==""||type == "text") {//判断返回数据类型
				result=http_request.responseText;
			}else if (type == "xml") {
				result=http_request.responseXml;
			}
			if(myProcess){//判断自定义处理数据方法是否存在
				myProcess(result,conname);
			}else{
				pageChange(result,conname);
			}
		}
	}
	http_request.open(method, url, true);
	if(method==null||method==""||(method.toLowerCase())=="post"){
		http_request.setRequestHeader("Content-Length", sinfo.length);
		http_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	}
	http_request.send(sinfo);
}


function pageChange(count,conname){
	if(conname){
		conname.innerHTML = count ;
	}
}


function init_request() {
	var http_request = false;
	if (window.XMLHttpRequest) { //Mozilla
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {//MiME
			http_request.overrideMimeType("text/xml");
		}
	} else {
		if (window.ActiveXObject) { //IE
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			}catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				}catch (e) {
				}
			}
		}
	}
	if (!http_request) { //
		window.alert("XMLHttpRequest=null");
		return false;
	}
	return http_request;
}
