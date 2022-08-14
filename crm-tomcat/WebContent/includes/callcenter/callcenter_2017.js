
var statusOfCallCenter = 0;//0:初始状态1:连接成功2:连接失败3:注册分机号成功4:注册分机号失败

function cmdSetParam_onclick(agentID) {
    //var agent = Request("agentid");
    //可设定软电话的配色  0-银灰风格 1-淡蓝风格
    csSoftPhone.SetInitParam("PHONECOLORPLAN","0");
    //根据业务系统的登录情况，设置工号
    csSoftPhone.AgentID = agentID;
    
    //LOGONMODE取值说明：
    //0，则代表座席已经通过认证，由业务将工号及姓名设置进来即可；
    csSoftPhone.setInitParam("LOGONMODE","0");
    csSoftPhone.setInitParam("GETLOCALSETTING","1");
    csSoftPhone.setInitParam("AGENTMSGMODE",2);  //坐席消息弹屏

    ////自动连接Server
    //csSoftPhone.connectServer();
    
    ////***连接后自动登录
    if( ! csSoftPhone.connectServer() ){
        alert("呼叫中心连接Server失败！");
        statusOfCallCenter = 2;
    }else{
    	csSoftPhone.logon();
    	statusOfCallCenter = 1;
    	//alert("呼叫中心连接Server OK！");
    }
}
function cmdSetStyle1_onclick() {
    csSoftPhone.SetInitParam("PHONECOLORPLAN","0");
}
function cmdSetStyle2_onclick() {
    csSoftPhone.SetInitParam("PHONECOLORPLAN","1");
}

function csSoftPhone_evtCallArrive(){
    var ANI="";
    var SK="";
    var COID = "";
    ANI=csSoftPhone.getCOInfo ("ANI");
    SK= csSoftPhone.getCOInfo("SK");
    COID= csSoftPhone.getCOInfo("COID");    

    //var crmhtml  = "bill.do?method=CustomerInfo&customerphone="+ANI+"";
    var crmhtml="/login/callcenter_custinfo.jsp?phone="+ANI+"&callid="+COID;
    window.open(crmhtml);
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
//外呼
function cmdDial_onclick(telphone) {
    if (statusOfCallCenter != 1){//alert("正在登录CallCenter")
    	cmdSetParam_onclick();
    }
	csSoftPhone.Dial(telphone,"");
}
function window_onload() {
		//如果需要加载时自动连接Server
		cmdSetParam_onclick();  
}
function Request(strName) { 
	var strHref = window.document.location.href; 
	var intPos = strHref.indexOf("?"); 
	var strRight = strHref.substr(intPos + 1); 

	var arrTmp = strRight.split("&"); 
	for(var i = 0; i < arrTmp.length; i++) { 
		var arrTemp = arrTmp[i].split("="); 
		if(arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1]; 
	} 
	return ""; 
}

function csSoftPhone_evtLogonSucc()
{
	//Trace("Logon Succ");
}

function csSoftPhone_evtStateChange(st)
{
	//alert(st);
	//Trace("StateChange:" + st );
}

function csSoftPhone_evtDialFailed(ErrNum)
{
	//Trace("ErrNum:" + ErrNum );
}

function csSoftPhone_evtLogoffSucc()
{
	//Trace("Logoff Succ");
}

function csSoftPhone_evtDialSucc()
{
	//Trace("Dial Succ");
}


