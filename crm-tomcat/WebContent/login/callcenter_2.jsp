<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <meta content="text/html; charset=GB18030" http-equiv="content-type" />
    <title>CrystalSoftPhon32 软电话控件Demo</title>
</head>
<body onload="window_onload()">
<input type="button" value="777外拨7781" onclick="cmdDial_onclick();"></input>
<input type="button" value="777NNNo777" onclick="csSoftPhone_evtStateChange(3);"></input>
<div style="margin-top:-6px; float:left; width:100%">
<object id="csSoftPhone"
        classid="CLSID:A972798F-50FC-4818-BCE2-2472BC68766C"
        codebase="CrystalSoftPhone32.CAB#version=3,2,0,3"
        style="width: 100%; height: 65pt"><param name="ServerIP" value="127.0.0.1"/>
        <param name="LocalIP" value="127.0.0.1"/><param name="AGENTINS" value="6001"/>
        <param name="SK" value="1001|1002"/><param name="AgentID" value="1001"/>
        <param name="AgentName" value="Demo"/></object>
</div>
    <input id="cmdDial" type="button" value="外拨" onclick="return cmdDial_onclick()" style="width: 84px" /><br />
    <textarea id="txtTrace" style="width: 100%; height: 294px"></textarea>&nbsp;
</body>    
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogonSucc">
// <!CDATA[
    return csSoftPhone_evtLogonSucc()
// ]]>
</script>
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogoffSucc">
// <!CDATA[
    return csSoftPhone_evtLogoffSucc()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallArrive">
// <!CDATA[
    return csSoftPhone_evtCallArrive()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialSucc">
// <!CDATA[
    return csSoftPhone_evtDialSucc()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtDialFailed(ErrNum)
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtStateChange(State)">
// <!CDATA[
    //alert (State);
    return csSoftPhone_evtStateChange(State);
// ]]>
</script>
<SCRIPT ID=clientcode LANGUAGE=javascript>
function Trace(msg)
{
    //alert(txtTrace.value );
    //alert(msg);    
    txtTrace.value = txtTrace.value + msg +"\r";
}

function cmdSetParam_onclick() {
    //var agent = Request("agentid");
    //可设定软电话的配色  0-银灰风格 1-淡蓝风格
    csSoftPhone.SetInitParam("PHONECOLORPLAN","0");
    //根据业务系统的登录情况，设置工号
    csSoftPhone.AgentID = "888";
    
    //LOGONMODE取值说明：
    //0，则代表座席已经通过认证，由业务将工号及姓名设置进来即可；
    csSoftPhone.setInitParam("LOGONMODE","0");
    csSoftPhone.setInitParam("GETLOCALSETTING","1");
    csSoftPhone.setInitParam("AGENTMSGMODE",2);  //坐席消息弹屏

    ////自动连接Server
    //csSoftPhone.connectServer();
    
    ////***连接后自动登录
    //if(   ! csSoftPhone.connectServer() )
    //   alert("连接Server失败！");
    //else
    //	csSoftPhone.logon();
}
function cmdSetStyle1_onclick() {
    csSoftPhone.SetInitParam("PHONECOLORPLAN","0");
}
function cmdSetStyle2_onclick() {
    csSoftPhone.SetInitParam("PHONECOLORPLAN","1");
}

</SCRIPT>



<SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>

function csSoftPhone_evtLogonSucc()
{
	Trace("Logon Succ");
}

function csSoftPhone_evtStateChange(st)
{
	//alert(st);
	Trace("StateChange:" + st );
}

function csSoftPhone_evtDialFailed(ErrNum)
{
	Trace("ErrNum:" + ErrNum );
}

function csSoftPhone_evtLogoffSucc()
{
	Trace("Logoff Succ");
}

function csSoftPhone_evtDialSucc()
{
	Trace("Dial Succ");
}


function csSoftPhone_evtCallArrive()
{
    var ANI="";
    var SK="";
    var COID = "";
    ANI=csSoftPhone.getCOInfo ("ANI");
    SK= csSoftPhone.getCOInfo("SK");
    COID= csSoftPhone.getCOInfo("COID");    

    //var crmhtml  = "bill.do?method=CustomerInfo&customerphone="+ANI+"";
    parent.document.all.mainFrame.src  = crmhtml;
}
function window_onunload() {
    if(csSoftPhone.GetAgentState != 0)
    {
        csSoftPhone.logOff();
    }
}
function cmdGetState_onclick() {
    alert(csSoftPhone.getAgentInfo("AGENTSTATE"));
}
function cmdDial_onclick() {
    csSoftPhone.Dial("015867113001","");

}
function window_onload() {
		//如果需要加载时自动连接Server
		cmdSetParam_onclick();  
}
function Request(strName) 
{ 
var strHref = window.document.location.href; 
var intPos = strHref.indexOf("?"); 
var strRight = strHref.substr(intPos + 1); 

var arrTmp = strRight.split("&"); 
for(var i = 0; i < arrTmp.length; i++) 
{ 
var arrTemp = arrTmp[i].split("="); 

if(arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1]; 
} 
return ""; 
} 
</SCRIPT>

</html>
