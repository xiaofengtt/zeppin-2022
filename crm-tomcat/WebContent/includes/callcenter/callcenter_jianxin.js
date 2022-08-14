// callcenter_jianxin.js
var statusOfCallCenter = 0;//0:��ʼ״̬1:���ӳɹ�2:����ʧ��3:ע��ֻ��ųɹ�4:ע��ֻ���ʧ��
var myPhone;
var CTIserverIP;
var CTIserverPort;
var timer_id;
var init_success = 0;
var isWebcall = 0;
var LinkExtCode;
//����
function initMyPhone(){
	myPhone = window.parent.document.getElementById("phone");
	isWebcall = window.parent.document.getElementById("isWebcall").value;
}
function connect2Server(serverIP,serverPort) {
//���ӷ����
	//alert(serverIP+"----"+serverPort);
	var err = myPhone.Link_ConnectServer(serverIP,serverPort,1);
	if(err != "" ){
		Ext.MessageBox.alert('����','���ӷ�����ʧ��:'+ err);
		return;	
	}else{
		var err_login =  myPhone.Ext_Assign(extension,extension);
		if(err_login != ""){
			Ext.MessageBox.alert('����','���ӷ�����ʧ��:'+ err_login);
			return;
		}else{
			myPhone.Ext_CheckInQueue(extension);
			LinkExtCode = extension;
			myPhone.Ext_SetExtDoNotDisturb(extension,0);//����
			Ext.MessageBox.alert('����','�ֻ���'+extension+'��ϯ��¼�ɹ�');
		}
	}
}		
//ע��
function disconnect2server(op_code,extension){
	myPhone.Link_DisConnectServer();
}
//��������
function telRing(telNum){
	isWebcall = window.parent.document.getElementById("isWebcall").value;
	if(isWebcall==1){
		window.location="http://211.155.228.43/showme";
	}
	
	showStatus('����');
	document.getElementById("cc_tel_num").value = telNum;
	document.getElementById("cc_tel_num_str").innerHTML = "<font color='red' style='font-weight: bold;'>"+telNum+"</font>";
	document.getElementById("callTalkType").value = 2;
	dialog.show(Ext.get('scrollMe'));
}
//ժ��
function telPickUp(){
	showStatus('ժ��');
	myPhone.CstaPickup(extension);
}

//�һ�
function telHangup(){
	showStatus('�һ�����');
	myPhone.Ext_DropCall(extension);
}

//����绰
function callout(extension,telNum){
	showStatus('����绰');
	myPhone.Ext_MakeCall(extension,telNum);
}

////�绰����
function telHold(){
	showStatus('�绰����');
	//myPhone.CstaHoldCall(extension);
}

//�绰ȡ��
function telRetrieveCall(){
	showStatus('�绰ȡ��');
	myPhone.Ext_CancelTransfer(extension);	
}

//�绰ת�ӳ�ʼ��
 function transferInit(telNum){
 	myPhone.Ext_TransferCall(LinkExtCode,telNum,"","");
 }
 
 //�绰ת�����
 function transferComplete(){

 }

//�绰�����ʼ��
function meetingInit(telNum){

}

//�绰������ʽ��ʼ
function meetingComplete(){
	myPhone.Ext_ThreeParty(LinkExtCode);
}

//�绰����
function telListen(telNum){
	//�ĵ���δ������˵���������ֳ���ѯ������ʽʵ��
}

//�绰ǿ��
function telPlug(telNum){
	//�ĵ���δ������˵���������ֳ���ѯ������ʽʵ��
}

//����
function setFree(extension,op_code){
	myPhone.Ext_SetExtDoNotDisturb(extension,0);
}
 
//��æ
function setBusy(extension,op_code){
	myPhone.Ext_SetExtDoNotDisturb(extension,1);
}

//�յ���Ӧ����ϯ״̬��������ã�������ϯ״̬
function setAgentStatus(statusInfo){
	document.getElementById("agent_status").value = document.getElementById("agent_status").value  + statusInfo+ '%';
}
//�յ���Ӧ����ϯ״̬��������ã����ڻ������
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
//���ŷ����¼�
function setReturnRecord(callId){
	var callRecordID = document.getElementById("callRecordID").value;
	var input_operatorCode = document.getElementById("input_operatorCode").value;
	var updateSql = "UPDATE TCALLRECORDS SET CALLCENTERID="+callId+" WHERE SERIAL_NO="+callRecordID;
	if(callRecordID!=0){
		//alert(updateSql);
		ccService.updateCallRecord(updateSql,input_operatorCode,callRecordID,{callback:function(data){}});
	}	
}
//ͨ�������¼�
function setCallTalking(){
	var myDate = new Date();
	var callTalkingStartTime = myDate.getHours() + '_' + myDate.getMinutes() + '_' + myDate.getSeconds();
	document.getElementById("callTalkingStartTime").value = callTalkingStartTime;
	showStatus('ͨ����');
}
//�һ��¼�
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
	showStatus('�һ�����');
}
//��ϯ״̬���
function setAgentState(lpszAgentId,agentMessage){
	callRecordStore.agentStore[lpszAgentId] = agentMessage;	
}
//�����¼����
function setQueueEvent(lpszChannel,queueMessage){
	callRecordStore.queueStore[lpszChannel] = queueMessage;	
}
