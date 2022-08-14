'坐席状态改变事件
'iState请参考后面的补充说明。
'lpszAgentId工号
'lpszExtNo分机号
'lpszCallerId来电号码
'iGrpNo组号
sub phone_AgentState(ByVal iState, ByVal lpszAgentId, ByVal lpszExtNo, ByVal lpszCallerId, ByVal iGrpNo)
	Dim agentStatus,agentStatusStr,agentMessage
	agentMessage = lpszAgentId &"_"&lpszExtNo&"_"&lpszCallerId&"_"&iGrpNo&"_"&iState
	agentStatus = iState
	select case agentStatus
		case 0 
			agentStatusStr = "空闲"
		case 1 
			agentStatusStr = "振铃"
		case 2 
			agentStatusStr = "通话"
		case 3 
			agentStatusStr = "转接中"
		case 4 
			agentStatusStr = "小休"
		case 5 
			agentStatusStr = "外拨中"
		case 6 
			agentStatusStr = "外拨失败"
		case 7 
			agentStatusStr = "录音中"
		case 8 
			agentStatusStr = "通话保持"
		case 9 
			agentStatusStr = "转接失败"
		case 10 
			agentStatusStr = "摘机"
		case 11 
			agentStatusStr = "回铃"
		case 12 
			agentStatusStr = "登出"
		case 13 
			agentStatusStr = "后处理"
		case 14 
			agentStatusStr = "就餐"
		case 15 
			agentStatusStr = "培训"
		case 16 
			agentStatusStr = "会议"
		case 17 
			agentStatusStr = "外拨通话"
		case 18 
			agentStatusStr = "催挂"
		case 21
			agentStatusStr = "主动服务"
		case else
			agentStatusStr = ""
	end select
	agentMessage = agentMessage&"_"&agentStatusStr
	if agentStatus = 0 or agentStatus = 2 or agentStatus = 17 or agentStatus=4 then
		setAgentStatus lpszAgentId&","&lpszExtNo&","&lpszCallerId&","&iGrpNo&","&agentStatusStr
	end if
end sub

'催挂事件
'Extension本坐席分机号 
'ReleaseNumber对方号码 
sub phone_CallDisconnect(ByVal Extension, ByVal ReleaseNumber)
	'msgbox "对方挂机"
	'setMystatus "st_free"
end sub

'来电震铃事件
'Extension本坐席分机号
'Caller主叫号码
'Called被叫号码
sub phone_CallerRinging(ByVal Extension, ByVal Caller, ByVal Called)
	setMystatus "st_picked"
	telRing Caller
end sub

'通话建立事件
'Extension本坐席分机号                                                                                                                                           
'Caller主叫号码                                                                                         
'Called被叫号码                                                                                         
'CallType暂时无用                                                                                               
sub phone_CallTalking(ByVal Extension, ByVal Caller, ByVal Called, ByVal CallType)
	setMystatus "st_picked"
	setCallTalking
end sub

'挂机事件
'Extension本坐席分机号
sub phone_CallHangup(ByVal Extension)
	setMystatus "st_free"
	setCallHangup
end sub

'摘机事件
'Extension：本坐席分机号
sub phone_CallPickup(ByVal Extension)	
	setMystatus "st_picked"
end sub



'消息事件
'szDestExtNo接收消息的目标分机。
'SourcNumber发送消息的源分机
'Param1参数1
'Param2参数2
'Param3参数3
sub phone_ExtensionMsg(ByVal szDestExtNo, ByVal SourceNumber, ByVal Param1, ByVal Param2, ByVal Param3)
'请求坐席状态
	if Param1 = "RequestAgentState" then
		'收到其他用乎的状态请求信息，做出相应的回应，目前暂不用实现该功能，留空
	elseif Param1 = "PlayAgentNo" then 
		setMystatus "st_picked"	
	elseif Param1 = "ReturnRecordPath" then 
		setReturnRecord Param2
	elseif Param1="AgentStatus" then
			'收到其他用户的状态信息，按照一定的格式进行组织，用于坐席状态查询
			'解析Param3,以逗号隔开,第一个是工号，第二个是来电号码，第三个是组号
			Dim agentStatusInfo ,agentStatusInfos,agentStatusStr
			agentStatusInfo = Param3
			agentStatusInfos = split(agentStatusInfo,",",-1,1)
			uid = agentStatusInfos(0)
			caller = agentStatusInfos(1)
			grpno = agentStatusInfos(2)
			'Param2是坐席状态
	 		agentStatus = Param2
			select case agentStatus
				case 0 
					agentStatusStr = "空闲"
				case 1 
					agentStatusStr = "振铃"
				case 2 
					agentStatusStr = "通话"
				case 3 
					agentStatusStr = "转接中"
				case 4 
					agentStatusStr = "小休"
				case 5 
					agentStatusStr = "外拨中"
				case 6 
					agentStatusStr = "外拨失败"
				case 7 
					agentStatusStr = "录音中"
				case 8 
					agentStatusStr = "通话保持"
				case 9 
					agentStatusStr = "转接失败"
				case 10 
					agentStatusStr = "摘机"
				case 11 
					agentStatusStr = "回铃"
				case 12 
					agentStatusStr = "登出"
				case 13 
					agentStatusStr = "后处理"
				case 14 
					agentStatusStr = "就餐"
				case 15 
					agentStatusStr = "培训"
				case 16 
					agentStatusStr = "会议"
				case 17 
					agentStatusStr = "外拨通话"
				case 18 
					agentStatusStr = "催挂"
				case 21
					agentStatusStr = "主动服务"
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

'保持事件
'Extension本坐席分机号                                                                                            
'DestExtNo目标分机                                                                                                          
sub phone_CallOnHold(ByVal Extension, ByVal DestNumber)                                       
                                                                                                                                                                           
end sub
                                                                                                               
'取回事件
'Extension本坐席分机号                                                                                            
'DestExtNo目标分机
sub phone_CallRetrieve(ByVal Extension, ByVal RetrievedExt)                                  
                                                                                          
end sub
                                                                                                               
'回铃事件
'Extension主叫号码                                                                                              
'Called被叫号码                                                                                                   
sub phone_CallRingBack(ByVal Extension, ByVal Called)                                              
                                                                                             
end sub                                                                                                               
                                                                                                               
'强制小休事件                                                                                                 
sub phone_OnForceFree()                                                                           
 
end sub
                                                                                                              
'强制退出事件                                                                                                 
sub phone_ForceLogOff()                                                                           
  
end sub
                                                                                                             
'强制示忙事件                                                                                                 
sub phone_ForcePause()                                                                            

end sub
                                                                                                             
'队列事件
'iQueType0 – 入队列， 1 – 出队列、                                                                            
'lpszCallerId来电号码                                                                                           
'lpszGrpNo组号                                                                                                  
'lpszChannelIVR通道号。                                                                                                       
sub phone_QueueEvent(ByVal iQueType, ByVal lpszCallerId, ByVal lpszGrpNo, ByVal lpszChannel)
	Dim queueMessage,iQueTypeStr
	select case iQueType
	case 0 
		iQueTypeStr = "入队列"
	case 1 
		iQueTypeStr = "出队列"
	end select
	queueMessage = lpszChannel&"_"&lpszGrpNo&"_"&lpszCallerId&"_"&iQueType&"_"&iQueTypeStr
	setQueueinfo iQueType&","&lpszGrpNo&","&lpszCallerId&","&lpszChannel
end sub 