// callcenter.js
var statusOfCallCenter = 0;//0:��ʼ״̬1:���ӳɹ�2:����ʧ��3:ע��ֻ��ųɹ�4:ע��ֻ���ʧ��
var myPhone;
var CTIserverIP;
var CTIserverPort;
var timer_id;
var init_success = 0;
function initMyPhone(){
	myPhone = window.parent.document.getElementById("phone");
}
//��֤������IP��˿ڣ������ӷ�����
function connect2Server(serverIP,serverPort){
	
	var patternIP = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	var patternPort = /^[1-9]\d*$/;
	//��֤IP�Ͷ˿��Ƿ�������ȷ����ʱע��
	if(!patternIP.test(serverIP))
	{
		Ext.MessageBox.alert('����','CallCenter������IP���ô���CallCenter�����ã�����ϵ����Ա����');
		return false;
	}else{
		CTIserverIP = serverIP;
	}
	
	if(!(patternPort.test(serverPort)&&serverPort>0&&serverPort<65536)){
		Ext.MessageBox.alert('����','CallCenter�������˿����ô���CallCenter�����ã�����ϵ����Ա����');
		return false;	
	}else{
		CTIserverPort = serverPort;
	}
	//*/
	Ext.MessageBox.show({
		title: '���Ժ�...',
		msg: '��ʼ����...',
		width:300,
		progress:true,
		//closable:false
		closable:true
	});
	
	setPhoneFlag();
		
	Ext.MessageBox.updateProgress(1/4, '��ʼ����CallCenter��������Ϣ');
	var f = function(){
		return function(){
			setCTIServer(CTIserverIP,CTIserverIP);
		}
	}
	setTimeout(f(),2000);
}

//����CTI�¼������Ƿ�ΪOnTimerģʽ
function setPhoneFlag(){
	myPhone.SetTimerFlag(1);
}

//����CTI������IP
function setCTIServer(CTIserverIP,CTIserverIP){
		myPhone.Set2IP(CTIserverIP,CTIserverIP);
}

//���ӷ�����
function openConn(){
	Ext.MessageBox.updateProgress(2/4, '��ʼ����CallCenter������');
	var f = function(){
		return function(){
				myPhone.ConnectServer(CTIserverPort);
		}
	}
	setTimeout(f(),2000);
}

//���������ӳɹ�
function connect2serverSucc(){
	
	//����ǳ�ʼ״̬�����ӳɹ������ע��ֻ��ŵķ���
	if(statusOfCallCenter==0){
		statusOfCallCenter = 1;
		if(Ext.MessageBox.isVisible()){
			Ext.MessageBox.updateProgress(3/4, '����CallCenter�������ɹ�����ʼ��ϯ��½');
			var f = function(){
				return function(){
					setAgentInitParam();
					agentLogin();
				}
			}
			setTimeout(f(),2000);
		}
	}//�����2������ʧ�ܺ������Ӻ����ӳɹ�ִ�����´���
	else if(statusOfCallCenter==2){
		statusOfCallCenter = 1;
		Ext.MessageBox.show({
			title: '����CallCenter����Ϣ',
			msg: 'CallCenter�������ӿ���ʹ��',
			width:300,
			closable:true
		});
		
		var f = function(){
			return function(){
				Ext.MessageBox.hide();		
			}	
		}
		setTimeout(f(),5000);
	}//����״̬�������ӳɹ������κβ���
	else{
		return false;
	}
	
}

//����������ʧ��
function connect2serverFaild(){
	//����ǳ�ʼ״̬������ʧ�ܺ������ʾ���Ժ�ر�
	if(statusOfCallCenter==0){
		statusOfCallCenter = 2;
		if(Ext.MessageBox.isVisible()){
			Ext.MessageBox.updateProgress(1, '����CallCenter������ʧ�ܣ��Ժ��Զ���������');
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
			title: '����CallCenter����Ϣ',
			msg: '��CallCenter�����Ӷ�ʧ���Ժ��Զ���������',
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

//������ϯ�ĳ�ʼ����
function setAgentInitParam(){
	myPhone.SetAgentParam('5501','VRecord',5500,8300,0);
	/*�������ʱ��д
	szQueueExt��IVR�ĵ�һ���ֻ��š������зֻ��ţ�
	szRecordExt��¼���ֻ��š�
	iQueueGrpNo��IVR��š�
	iRecordGrpNo��¼����š�
	iAgentType��0 �� ��ʾ��ϯ���ͣ� ����ֵ��ʾ����ϯ���͡�
	*/
	
}
//��ϯ��½
function agentLogin(){
	myPhone.AgentLogon(op_code,extension,'0');
	/*�������ʱ��д
	AgentId����ʾ���š�
	szExtNo����ʾ�˹��Ű󶨵ķֻ���
	szGrpNo����ʾ�˹������ڵ���ϯ�顣

	*/
}

//ע��ֻ��ųɹ�
function registerDNSucc(){
	Ext.MessageBox.updateProgress(1, '��ϯ��½�ɹ�');
	Ext.MessageBox.hide();
	setInterval("sendMystatus()",10000);
	setFree(extension,op_code);
	init_success = 1;//��ʾ�豸�Ѿ���ʼ�������ӳɹ�
}

//ע��ֻ���ʧ��
function registerDNFail(){
	Ext.MessageBox.updateProgress(1, '��ϯ��½ʧ��');
}

//��������
function telRing(telNum){
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
	myPhone.CstaHangup(extension);
}

//����绰
function callout(extension,telNum){
	showStatus('����绰');
	myPhone.CstaMakeCall(extension,telNum);
}

//�绰����
function telHold(){
	showStatus('�绰����');
	myPhone.CstaHoldCall(extension);
}

//�绰ȡ��
function telRetrieveCall(){
	showStatus('�绰ȡ��');
	myPhone.CstaRetrieveCall(extension);	
}

//�绰ת�ӳ�ʼ��
 function transferInit(telNum){
 	myPhone.CstaStartTransfer(extension,telNum);
 }
 
 //�绰ת�����
 function transferComplete(){
 	myPhone.CstaCompleteTransfer(extension);
 }

//�绰�����ʼ��
function meetingInit(telNum){
	myPhone.CstaStartTransfer(extension,telNum);
}

//�绰������ʽ��ʼ
function meetingComplete(){
	myPhone.CstaCompleteConference(extension);
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
	//alert("����1--"+timer_id)
	top.window.clearInterval(timer_id);
	clearInterval(timer_id);
	timer_id = setInterval("setFree2("+ extension+","+ op_code+")",10000);
	//alert("����2--"+timer_id)
}
function setFree2(extension,op_code){
	myPhone.AgentReady(op_code,extension);
}

//��æ
function setBusy(op_code){
	//alert("��æ1--"+timer_id)
	top.window.clearInterval(timer_id);
	clearInterval(timer_id);
	timer_id  = setInterval("setBusy2("+ op_code+")",10000);
	//alert("��æ2--"+timer_id)
	
}
function setBusy2(op_code){
	myPhone.AgentNotReady(op_code,4);
}

//�˳�
function disconnect2server(extension,recordExtension,op_code){
	if(myPhone.value != null)
		myPhone.UnregisterExtenion(extension);
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