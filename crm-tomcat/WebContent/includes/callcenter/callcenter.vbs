'��ϯ״̬�ı��¼�
'iState��ο�����Ĳ���˵����
'lpszAgentId����
'lpszExtNo�ֻ���
'lpszCallerId�������
'iGrpNo���
sub phone_AgentState(ByVal iState, ByVal lpszAgentId, ByVal lpszExtNo, ByVal lpszCallerId, ByVal iGrpNo)
	Dim agentStatus,agentStatusStr,agentMessage
	agentMessage = lpszAgentId &"_"&lpszExtNo&"_"&lpszCallerId&"_"&iGrpNo&"_"&iState
	agentStatus = iState
	select case agentStatus
		case 0 
			agentStatusStr = "����"
		case 1 
			agentStatusStr = "����"
		case 2 
			agentStatusStr = "ͨ��"
		case 3 
			agentStatusStr = "ת����"
		case 4 
			agentStatusStr = "С��"
		case 5 
			agentStatusStr = "�Ⲧ��"
		case 6 
			agentStatusStr = "�Ⲧʧ��"
		case 7 
			agentStatusStr = "¼����"
		case 8 
			agentStatusStr = "ͨ������"
		case 9 
			agentStatusStr = "ת��ʧ��"
		case 10 
			agentStatusStr = "ժ��"
		case 11 
			agentStatusStr = "����"
		case 12 
			agentStatusStr = "�ǳ�"
		case 13 
			agentStatusStr = "����"
		case 14 
			agentStatusStr = "�Ͳ�"
		case 15 
			agentStatusStr = "��ѵ"
		case 16 
			agentStatusStr = "����"
		case 17 
			agentStatusStr = "�Ⲧͨ��"
		case 18 
			agentStatusStr = "�߹�"
		case 21
			agentStatusStr = "��������"
		case else
			agentStatusStr = ""
	end select
	agentMessage = agentMessage&"_"&agentStatusStr
	if agentStatus = 0 or agentStatus = 2 or agentStatus = 17 or agentStatus=4 then
		setAgentStatus lpszAgentId&","&lpszExtNo&","&lpszCallerId&","&iGrpNo&","&agentStatusStr
	end if
end sub

'�߹��¼�
'Extension����ϯ�ֻ��� 
'ReleaseNumber�Է����� 
sub phone_CallDisconnect(ByVal Extension, ByVal ReleaseNumber)
	'msgbox "�Է��һ�"
	'setMystatus "st_free"
end sub

'���������¼�
'Extension����ϯ�ֻ���
'Caller���к���
'Called���к���
sub phone_CallerRinging(ByVal Extension, ByVal Caller, ByVal Called)
	setMystatus "st_picked"
	telRing Caller
end sub

'ͨ�������¼�
'Extension����ϯ�ֻ���                                                                                                                                           
'Caller���к���                                                                                         
'Called���к���                                                                                         
'CallType��ʱ����                                                                                               
sub phone_CallTalking(ByVal Extension, ByVal Caller, ByVal Called, ByVal CallType)
	setMystatus "st_picked"
	setCallTalking
end sub

'�һ��¼�
'Extension����ϯ�ֻ���
sub phone_CallHangup(ByVal Extension)
	setMystatus "st_free"
	setCallHangup
end sub

'ժ���¼�
'Extension������ϯ�ֻ���
sub phone_CallPickup(ByVal Extension)	
	setMystatus "st_picked"
end sub



'��Ϣ�¼�
'szDestExtNo������Ϣ��Ŀ��ֻ���
'SourcNumber������Ϣ��Դ�ֻ�
'Param1����1
'Param2����2
'Param3����3
sub phone_ExtensionMsg(ByVal szDestExtNo, ByVal SourceNumber, ByVal Param1, ByVal Param2, ByVal Param3)
'������ϯ״̬
	if Param1 = "RequestAgentState" then
		'�յ������ú���״̬������Ϣ��������Ӧ�Ļ�Ӧ��Ŀǰ�ݲ���ʵ�ָù��ܣ�����
	elseif Param1 = "PlayAgentNo" then 
		setMystatus "st_picked"	
	elseif Param1 = "ReturnRecordPath" then 
		setReturnRecord Param2
	elseif Param1="AgentStatus" then
			'�յ������û���״̬��Ϣ������һ���ĸ�ʽ������֯��������ϯ״̬��ѯ
			'����Param3,�Զ��Ÿ���,��һ���ǹ��ţ��ڶ�����������룬�����������
			Dim agentStatusInfo ,agentStatusInfos,agentStatusStr
			agentStatusInfo = Param3
			agentStatusInfos = split(agentStatusInfo,",",-1,1)
			uid = agentStatusInfos(0)
			caller = agentStatusInfos(1)
			grpno = agentStatusInfos(2)
			'Param2����ϯ״̬
	 		agentStatus = Param2
			select case agentStatus
				case 0 
					agentStatusStr = "����"
				case 1 
					agentStatusStr = "����"
				case 2 
					agentStatusStr = "ͨ��"
				case 3 
					agentStatusStr = "ת����"
				case 4 
					agentStatusStr = "С��"
				case 5 
					agentStatusStr = "�Ⲧ��"
				case 6 
					agentStatusStr = "�Ⲧʧ��"
				case 7 
					agentStatusStr = "¼����"
				case 8 
					agentStatusStr = "ͨ������"
				case 9 
					agentStatusStr = "ת��ʧ��"
				case 10 
					agentStatusStr = "ժ��"
				case 11 
					agentStatusStr = "����"
				case 12 
					agentStatusStr = "�ǳ�"
				case 13 
					agentStatusStr = "����"
				case 14 
					agentStatusStr = "�Ͳ�"
				case 15 
					agentStatusStr = "��ѵ"
				case 16 
					agentStatusStr = "����"
				case 17 
					agentStatusStr = "�Ⲧͨ��"
				case 18 
					agentStatusStr = "�߹�"
				case 21
					agentStatusStr = "��������"
				case else
					agentStatusStr = ""
				end select

				if agentStatus = 0 or agentStatus = 2 or agentStatus = 17 or agentStatus=4 then
					setAgentStatus uid&","&SourceNumber&","&caller&","&grpno&","&agentStatusStr
				end if
	elseif Param1="ACD" then
					acdstring = Param3
					acdarray = split(acdstring,"^",-1,1)
					grpno = acdarray(0)
					callerid = acdarray(1)
					ivrch = acdarray(2)
					setQueueinfo Param2&","&grpno&","&callerid&","&ivrch
	end if
end sub

'�����¼�
'Extension����ϯ�ֻ���                                                                                            
'DestExtNoĿ��ֻ�                                                                                                          
sub phone_CallOnHold(ByVal Extension, ByVal DestNumber)                                       
                                                                                                                                                                           
end sub
                                                                                                               
'ȡ���¼�
'Extension����ϯ�ֻ���                                                                                            
'DestExtNoĿ��ֻ�
sub phone_CallRetrieve(ByVal Extension, ByVal RetrievedExt)                                  
                                                                                          
end sub
                                                                                                               
'�����¼�
'Extension���к���                                                                                              
'Called���к���                                                                                                   
sub phone_CallRingBack(ByVal Extension, ByVal Called)                                              
                                                                                             
end sub                                                                                                               
                                                                                                               
'ǿ��С���¼�                                                                                                 
sub phone_OnForceFree()                                                                           
 
end sub
                                                                                                              
'ǿ���˳��¼�                                                                                                 
sub phone_ForceLogOff()                                                                           
  
end sub
                                                                                                             
'ǿ��ʾæ�¼�                                                                                                 
sub phone_ForcePause()                                                                            

end sub
                                                                                                             
'�����¼�
'iQueType0 �C ����У� 1 �C �����С�                                                                            
'lpszCallerId�������                                                                                           
'lpszGrpNo���                                                                                                  
'lpszChannelIVRͨ���š�                                                                                                       
sub phone_QueueEvent(ByVal iQueType, ByVal lpszCallerId, ByVal lpszGrpNo, ByVal lpszChannel)
	Dim queueMessage,iQueTypeStr
	select case iQueType
	case 0 
		iQueTypeStr = "�����"
	case 1 
		iQueTypeStr = "������"
	end select
	queueMessage = lpszChannel&"_"&lpszGrpNo&"_"&lpszCallerId&"_"&iQueType&"_"&iQueTypeStr
	setQueueinfo iQueType&","&lpszGrpNo&","&lpszCallerId&","&lpszChannel
end sub 