// callcenter.js
var statusOfCallCenter = 0;//0:初始状态1:连接成功2:连接失败3:注册分机号成功4:注册分机号失败
var myPhone;
var CTIserverIP;
var CTIserverPort;
var timer_id;
var init_success = 0;
function initMyPhone(){
	myPhone = window.parent.document.getElementById("phone");
}
//验证服务器IP与端口，并连接服务器
function connect2Server(serverIP,serverPort){
	
	var patternIP = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	var patternPort = /^[1-9]\d*$/;
	//验证IP和端口是否配置正确，暂时注释
	if(!patternIP.test(serverIP))
	{
		Ext.MessageBox.alert('警告','CallCenter服务器IP配置错误，CallCenter不可用，请联系管理员设置');
		return false;
	}else{
		CTIserverIP = serverIP;
	}
	
	if(!(patternPort.test(serverPort)&&serverPort>0&&serverPort<65536)){
		Ext.MessageBox.alert('警告','CallCenter服务器端口配置错误，CallCenter不可用，请联系管理员设置');
		return false;	
	}else{
		CTIserverPort = serverPort;
	}
	//*/
	Ext.MessageBox.show({
		title: '请稍候...',
		msg: '初始化中...',
		width:300,
		progress:true,
		//closable:false
		closable:true
	});
	
	setPhoneFlag();
		
	Ext.MessageBox.updateProgress(1/4, '开始配置CallCenter服务器信息');
	var f = function(){
		return function(){
			setCTIServer(CTIserverIP,CTIserverIP);
		}
	}
	setTimeout(f(),2000);
}

//设置CTI事件返回是否为OnTimer模式
function setPhoneFlag(){
	myPhone.SetTimerFlag(1);
}

//设置CTI服务器IP
function setCTIServer(CTIserverIP,CTIserverIP){
		myPhone.Set2IP(CTIserverIP,CTIserverIP);
}

//连接服务器
function openConn(){
	Ext.MessageBox.updateProgress(2/4, '开始连接CallCenter服务器');
	var f = function(){
		return function(){
				myPhone.ConnectServer(CTIserverPort);
		}
	}
	setTimeout(f(),2000);
}

//服务器连接成功
function connect2serverSucc(){
	
	//如果是初始状态，连接成功后调用注册分机号的方法
	if(statusOfCallCenter==0){
		statusOfCallCenter = 1;
		if(Ext.MessageBox.isVisible()){
			Ext.MessageBox.updateProgress(3/4, '连接CallCenter服务器成功，开始坐席登陆');
			var f = function(){
				return function(){
					setAgentInitParam();
					agentLogin();
				}
			}
			setTimeout(f(),2000);
		}
	}//如果是2即连接失败后重连接后连接成功执行如下代码
	else if(statusOfCallCenter==2){
		statusOfCallCenter = 1;
		Ext.MessageBox.show({
			title: '来自CallCenter的信息',
			msg: 'CallCenter现已连接可以使用',
			width:300,
			closable:true
		});
		
		var f = function(){
			return function(){
				Ext.MessageBox.hide();		
			}	
		}
		setTimeout(f(),5000);
	}//其他状态下重连接成功不做任何操作
	else{
		return false;
	}
	
}

//服务器连接失败
function connect2serverFaild(){
	//如果是初始状态，连接失败后给出提示，稍后关闭
	if(statusOfCallCenter==0){
		statusOfCallCenter = 2;
		if(Ext.MessageBox.isVisible()){
			Ext.MessageBox.updateProgress(1, '连接CallCenter服务器失败，稍后将自动重新连接');
			var f = function(){
				return function(){
					Ext.MessageBox.hide();
				}
			}
			setTimeout(f(),2000);
		}
	}else if(statusOfCallCenter==1){
		statusOfCallCenter = 2;
		Ext.MessageBox.show({
			title: '来自CallCenter的信息',
			msg: '与CallCenter的连接丢失，稍后将自动重新连接',
			width:300,
			closable:true
		});
		
		var f = function(){
			return function(){
				Ext.MessageBox.hide();
			}
		}
		setTimeout(f(),2000);
	}
}

//设置坐席的初始参数
function setAgentInitParam(){
	myPhone.SetAgentParam('5501','VRecord',5500,8300,0);
	/*具体调试时填写
	szQueueExt：IVR的第一个分机号。（队列分机号）
	szRecordExt：录音分机号。
	iQueueGrpNo：IVR组号。
	iRecordGrpNo：录音组号。
	iAgentType：0 — 表示坐席类型， 其他值表示非坐席类型。
	*/
	
}
//坐席登陆
function agentLogin(){
	myPhone.AgentLogon(op_code,extension,'0');
	/*具体调试时填写
	AgentId：表示工号。
	szExtNo：表示此工号绑定的分机。
	szGrpNo：表示此工号做在的坐席组。

	*/
}

//注册分机号成功
function registerDNSucc(){
	Ext.MessageBox.updateProgress(1, '坐席登陆成功');
	Ext.MessageBox.hide();
	setInterval("sendMystatus()",10000);
	setFree(extension,op_code);
	init_success = 1;//表示设备已经初始化并连接成功
}

//注册分机号失败
function registerDNFail(){
	Ext.MessageBox.updateProgress(1, '坐席登陆失败');
}

//来电振铃
function telRing(telNum){
	showStatus('来电');
	document.getElementById("cc_tel_num").value = telNum;
	document.getElementById("cc_tel_num_str").innerHTML = "<font color='red' style='font-weight: bold;'>"+telNum+"</font>";
	document.getElementById("callTalkType").value = 2;
	dialog.show(Ext.get('scrollMe'));
}

//摘机
function telPickUp(){
	showStatus('摘机');
	myPhone.CstaPickup(extension);
}

//挂机
function telHangup(){
	showStatus('挂机空闲');
	myPhone.CstaHangup(extension);
}

//拨打电话
function callout(extension,telNum){
	showStatus('拨打电话');
	myPhone.CstaMakeCall(extension,telNum);
}

//电话保持
function telHold(){
	showStatus('电话保持');
	myPhone.CstaHoldCall(extension);
}

//电话取回
function telRetrieveCall(){
	showStatus('电话取回');
	myPhone.CstaRetrieveCall(extension);	
}

//电话转接初始化
 function transferInit(telNum){
 	myPhone.CstaStartTransfer(extension,telNum);
 }
 
 //电话转接完成
 function transferComplete(){
 	myPhone.CstaCompleteTransfer(extension);
 }

//电话会议初始化
function meetingInit(telNum){
	myPhone.CstaStartTransfer(extension,telNum);
}

//电话会议正式开始
function meetingComplete(){
	myPhone.CstaCompleteConference(extension);
}

//电话监听
function telListen(telNum){
	//文档中未做具体说明，请在现场咨询合作方式实现
}

//电话强插
function telPlug(telNum){
	//文档中未做具体说明，请在现场咨询合作方式实现
}

//置闲
function setFree(extension,op_code){
	//alert("置闲1--"+timer_id)
	top.window.clearInterval(timer_id);
	clearInterval(timer_id);
	timer_id = setInterval("setFree2("+ extension+","+ op_code+")",10000);
	//alert("置闲2--"+timer_id)
}
function setFree2(extension,op_code){
	myPhone.AgentReady(op_code,extension);
}

//置忙
function setBusy(op_code){
	//alert("置忙1--"+timer_id)
	top.window.clearInterval(timer_id);
	clearInterval(timer_id);
	timer_id  = setInterval("setBusy2("+ op_code+")",10000);
	//alert("置忙2--"+timer_id)
	
}
function setBusy2(op_code){
	myPhone.AgentNotReady(op_code,4);
}

//退出
function disconnect2server(extension,recordExtension,op_code){
	if(myPhone.value != null)
		myPhone.UnregisterExtenion(extension);
}

//收到响应的坐席状态后进行设置，用于坐席状态
function setAgentStatus(statusInfo){
	document.getElementById("agent_status").value = document.getElementById("agent_status").value  + statusInfo+ '%';
}
//收到响应的坐席状态后进行设置，用于话务监视
function setCommunicationInfo(statusInfo){
	document.getElementById("communication_info").value = document.getElementById("communication_info").value  + statusInfo+ '%';
}

function setQueueinfo(queueinfo){
		document.getElementById("queueInfo").value = document.getElementById("queueInfo").value  + queueinfo+ '%';
}

function setMystatus(status){
	window.top.document.getElementById("my_status").value = status;
}

function sendMystatus(){
	myPhone.KeepAlive();	
}
//拨号返回事件
function setReturnRecord(callId){
	var callRecordID = document.getElementById("callRecordID").value;
	var input_operatorCode = document.getElementById("input_operatorCode").value;
	var updateSql = "UPDATE TCALLRECORDS SET CALLCENTERID="+callId+" WHERE SERIAL_NO="+callRecordID;
	if(callRecordID!=0){
		//alert(updateSql);
		ccService.updateCallRecord(updateSql,input_operatorCode,callRecordID,{callback:function(data){}});
	}	
}
//通话建立事件
function setCallTalking(){
	var myDate = new Date();
	var callTalkingStartTime = myDate.getHours() + '_' + myDate.getMinutes() + '_' + myDate.getSeconds();
	document.getElementById("callTalkingStartTime").value = callTalkingStartTime;
	showStatus('通话中');
}
//挂机事件
function setCallHangup(){
	var callRecordID = document.getElementById("callRecordID").value;
	var input_operatorCode = document.getElementById("input_operatorCode").value;
	var myDate = new Date();
	var callTalkingEndTime = myDate.getHours() + '_' + myDate.getMinutes() + '_' + myDate.getSeconds();
	var callTalkingStartTime = document.getElementById("callTalkingStartTime").value;
	
	if(callTalkingStartTime!=''&&callTalkingEndTime!=''){
		var startTimes = callTalkingStartTime.split("_");
		var endTimes = callTalkingEndTime.split("_");
		
		var hour = parseInt(endTimes[0])-parseInt(startTimes[0]);
		var minute = parseInt(endTimes[1])-parseInt(startTimes[1]);
		var second = parseInt(endTimes[2])-parseInt(startTimes[2]);
		
		var len = hour*3600 + minute*60 + second;
		var updateSql = "UPDATE TCALLRECORDS SET CALLLENGTH="+len+" WHERE SERIAL_NO="+callRecordID;
		if(callRecordID!=0){
			ccService.Updatecallrecord(updateSql,input_operatorCode,callRecordID,{callback:function(data){}});
		}	
	}
	showStatus('挂机空闲');
}
//坐席状态变更
function setAgentState(lpszAgentId,agentMessage){
	callRecordStore.agentStore[lpszAgentId] = agentMessage;	
}
//队列事件变更
function setQueueEvent(lpszChannel,queueMessage){
	callRecordStore.queueStore[lpszChannel] = queueMessage;	
}