<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnDoNotDisturbChange(ExtCode,DoNotDisturb)">//�ֻ�����ű�־�ı��¼�
	if (DoNotDisturb==0){
	    alert("�ֻ�"+ExtCode+"����!");
	}else{
	    alert("�ֻ�"+ExtCode+"��æ!");
	}
</script>

<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnCallIn(ExtCode,OtherCode,OtherRole,TransferCode,TransferRole,CallData)">//�ֻ������¼�
    //alert("�ֻ�"+ExtCode+"�绰����:"+OtherCode);
    telRing(OtherCode);
</script>

<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnConnected(ExtCode,OtherCode,OtherRole,TransferCode,TransferRole,CallData)">//�ֻ�ͨ���¼�
    //alert("�ֻ�"+ExtCode+"�绰��ͨ:"+OtherCode+"��¼���ļ���"+Ext_GetCallInfo_RecordFile(ExtCode));
</script>
<script LANGUAGE="JavaScript" FOR="phone" Event="ExtEvent_OnDisconnected(ExtCode)">//�ֻ��һ��¼�
	Ext.MessageBox.alert('��ʾ',"�ֻ�"+ExtCode+"ͨ������");
</script>