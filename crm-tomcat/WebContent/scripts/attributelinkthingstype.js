	//ajax
	var XMLHttpReq;
 	//创建XMLHttpRequest对象       
    function createXMLHttpRequest() {
		if(window.XMLHttpRequest) { //Mozilla 浏览器
			XMLHttpReq = new XMLHttpRequest();
		}
		else if (window.ActiveXObject) { // IE浏览器
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
	}
	//发送请求函数
	function sendRequest(url) {
		createXMLHttpRequest();
		XMLHttpReq.open("GET", url, true);
		XMLHttpReq.onreadystatechange = processResponse;//指定响应函数
		XMLHttpReq.send(null);  // 发送请求
	}
	// 处理返回信息函数
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	updateList();
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
    }
	// 刷新列表框函数
	function refreshList() {
		var attribute = document.getElementById("attribute").value;
//		if(attribute=="0")//非消耗品
//	 	{     
//			document.getElementById("expendable").style.display="none";
//			document.getElementById("noexpendable").style.display="";
//	 	} else if(attribute=="1"){
//			document.getElementById("expendable").style.display="";
//			document.getElementById("noexpendable").style.display="none";
//		} else {
//			clearList();
//	        return;
//		}
		if(attribute==""){
			clearList();
	        return;			
		}
		var url = "things.do?method=linkAttributeThingstype&attribute="+ attribute;
		sendRequest(url);
	}
	//更新列表框中列表项函数
	function updateList() {
	    clearList();

	    var thingstype = document.getElementById("thingstype");
		var values = XMLHttpReq.responseXML.getElementsByTagName("value");
		//alert("value:"+values[1].firstChild.nodeValue);
	    var results = XMLHttpReq.responseXML.getElementsByTagName("name");
	    var option = null;
	    
	    option = document.createElement("option");	
	    option.appendChild(document.createTextNode("请选择"));	
	    option.setAttribute("value",""); 
	    thingstype.appendChild(option);

	    
	    for(var i = 0; i < results.length; i++) {
	        //option = document.createElement("option");
	        //option.appendChild(document.createTextNode(results[i].firstChild.nodeValue));
			thingstype.options[i+1]=new Option(results[i].firstChild.nodeValue, values[i].firstChild.nodeValue, false, false);
	        //option.setAttribtue("value",values[i].firstChild.nodeValue);
	        //thingstype.appendChild(option);
	    }
	}
	//清除列表框中原有选项的函数
	function clearList() {
	    var thingstype = document.getElementById("thingstype");
	    while(thingstype.childNodes.length > 0) {
	        thingstype.removeChild(thingstype.childNodes[0]);
	    }
	}