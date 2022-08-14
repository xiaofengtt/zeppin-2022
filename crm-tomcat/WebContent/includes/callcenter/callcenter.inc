<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnDoNotDisturbChange(ExtCode,DoNotDisturb)">//分机免打扰标志改变事件
	if (DoNotDisturb==0){
	    alert("分机"+ExtCode+"致闲!");
	}else{
	    alert("分机"+ExtCode+"致忙!");
	}
</script>

<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnCallIn(ExtCode,OtherCode,OtherRole,TransferCode,TransferRole,CallData)">//分机呼入事件
    //alert("分机"+ExtCode+"电话呼入:"+OtherCode);
    telRing(OtherCode);
</script>

<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnConnected(ExtCode,OtherCode,OtherRole,TransferCode,TransferRole,CallData)">//分机通话事件
    //alert("分机"+ExtCode+"电话接通:"+OtherCode+"；录音文件："+Ext_GetCallInfo_RecordFile(ExtCode));
</script>
<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnDisconnected(ExtCode)">//分机挂机事件
	Ext.MessageBox.alert('提示',"分机"+ExtCode+"通话结束");
</script>