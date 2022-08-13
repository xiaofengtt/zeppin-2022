function makeRequest(url) {
 http_request = false;
 if (window.XMLHttpRequest) {
  http_request = new XMLHttpRequest();
  if (http_request.overrideMimeType){
   http_request.overrideMimeType('text/xml');
  } 
 } else if (window.ActiveXObject) {
  try{
   http_request = new ActiveXObject("Msxml2.XMLHTTP"); 
  } catch (e) {
   try {
    http_request = new ActiveXObject("Microsoft.XMLHTTP");
   } catch (e) {
   }
  }
 } 
 if (!http_request) {
  alert("您的浏览器不支持当前操作，请使用 IE 5.0 以上版本!");
  return false;
 }
 
 http_request.onreadystatechange = init; 
 http_request.open('GET', url, true); 


 http_request.setRequestHeader("If-Modified-Since","0"); 

 http_request.send(null);
 setTimeout("makeRequest('"+url+"')", 6000); 
}

function init() 
{ 
	 if (http_request.readyState == 4) 
	 {
	  	if (http_request.status == 0 || http_request.status == 200) 
	  	{
	   		var result = http_request.responseText;
	   		if(result=="1")
	   		{
	    		document.getElementById ("msgview").innerHTML="<a href='/message/msginfo.action' target='_blank'><img src='./message/images/info_new.gif' border=0></a>"; 
	  		}
	   		else
	   		{
	   			document.getElementById ("msgview").innerHTML="<a href='/message/msginfo.action' target='_blank'><img src='./message/images/info.gif'  border=0></a>"; 
	   		}
	  	} 
	  	else 
	  	{
	  		//http_request.status != 200
	   		//alert("请求失败!");
	  	}
	 }
}
