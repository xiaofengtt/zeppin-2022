// callcenter_jianxin.js
var statusOfCallCenter = 0;//0:初始状态1:连接成功2:连接失败3:注册分机号成功4:注册分机号失败
var myPhone;
var CTIserverIP;
var CTIserverPort;
var timer_id;
var init_success = 0;
var isWebcall = 0;
var LinkExtCode;
//加载
function initMyPhone(){
	myPhone = window.parent.document.getElementById("phone");
	isWebcall = window.parent.document.getElementById("isWebcall").value;
}
function connect2Server(serverIP,serverPort) {
//连接服务端
	//alert(serverIP+"----"+serverPort);
	var err = myPhone.Link_ConnectServer(serverIP,serverPort,1);
	if(err != "" ){
		Ext.MessageBox.alert('警告','连接服务器失败:'+ err);
		return;	
	}else{
		var err_login =  myPhone.Ext_Assign(extension,extension);
		if(err_login != ""){
			Ext.MessageBox.alert('警告','连接服务器失败:'+ err_login);
			return;
		}else{
			myPhone.Ext_CheckInQueue(extension);
			LinkExtCode = extension;
			myPhone.Ext_SetExtDoNotDisturb(extension,0);//致闲
			Ext.MessageBox.alert('警告','分机号'+extension+'坐席登录成功');
		}
	}
}		
//注销
function disconnect2server(op_code,extension){
	myPhone.Link_DisConnectServer();
}
//来电振铃
function telRing(telNum){
	isWebcall = window.parent.document.getElementById("isWebcall").value;
	if(isWebcall==1){
		window.location="http://211.155.228.43/showme";
	}
	
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
	myPhone.Ext_DropCall(extension);
}

//拨打电话
function callout(extension,telNum){
	showStatus('拨打电话');
	myPhone.Ext_MakeCall(extension,telNum);
}

////电话保持
function telHold(){
	showStatus('电话保持');
	//myPhone.CstaHoldCall(extension);
}

//电话取回
function telRetrieveCall(){
	showStatus('电话取回');
	myPhone.Ext_CancelTransfer(extension);	
}

//电话转接初始化
 function transferInit(telNum){
 	myPhone.Ext_TransferCall(LinkExtCode,telNum,"","");
 }
 
 //电话转接完成
 function transferComplete(){

 }

//电话会议初始化
function meetingInit(telNum){

}

//电话会议正式开始
function meetingComplete(){
	myPhone.Ext_ThreeParty(LinkExtCode);
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
	myPhone.Ext_SetExtDoNotDisturb(extension,0);
}
 
//置忙
function setBusy(extension,op_code){
	myPhone.Ext_SetExtDoNotDisturb(extension,1);
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
