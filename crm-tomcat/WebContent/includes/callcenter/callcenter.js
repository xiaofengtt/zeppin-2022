// callcenter.js
var statusOfCallCenter = 0;//0:初始状态1:连接成功2:连接失败3:注册分机号成功4:注册分机号失败
var myPhone;
var CTIserverIP;
var CTIserverPort;
var timer_id;
var init_success = 0;
var isWebcall = 0;
//开始登录
function initMyPhone(){
	myPhone = window.parent.document.getElementById("phone");
	isWebcall = window.parent.document.getElementById("isWebcall").value;
}
function connect2Server(serverIP,serverPort,dbIP,dbName,dbUser,dbPassword) {
	//alert(dbIP+"----"+dbName+"----"+dbUser+"----"+dbPassword+"----"+serverIP+"----"+serverPort)
	//设置坐席类型
	myPhone.SetAgentType(0); //0：表示为普通坐席，4：表示班长坐席，其他表示非坐席
	//设置callcenter数据库参数，例如数据库服务器IP地址：192.168.1.40, 数据库名：CallCenter, 数据库用户名:sa，密码：1234
	myPhone.SetDBParam(dbIP,dbName,dbUser,dbPassword);
	//设置callcenter代理服务器参数，代理服务器IP:192.168.1.41, 代理服务器端口：21900
	myPhone.SetProxyServerParam(serverIP,serverPort);	
	//开始登陆CTI，工号为：8001, 分机号为：201
	myPhone.AgentLogin(op_code,extension);
}
//注销
function disconnect2server(op_code,extension) {
	myPhone.AgentLogOff(op_code,extension);   

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
function setBusy(extension,op_code){
	//alert("置忙1--"+timer_id)
	top.window.clearInterval(timer_id);
	clearInterval(timer_id);
	timer_id  = setInterval("setBusy2("+ op_code+")",10000);
	//alert("置忙2--"+timer_id)
}
function setBusy2(extension,op_code){
	myPhone.AgentNotReady(op_code,4);
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
