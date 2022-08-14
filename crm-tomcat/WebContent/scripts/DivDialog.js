/**
 * Copyright (C), 1995-2006, 
 * File Name: Dialog.js
 * Encoding UTF-8 
 * Version: 4.0 
 * Date: 2007-01-18
 * History: // 修改历史记录列表，每条修改记录应包括修改日期、修改者及修改内容简述 
 * 1. Date: 2007-01-10
 *    Author: 
 *    Modification:创建 Div提示框
 * 2.
 * ...
 */
 
  DivDialog = Class.create();
  var Logout = false;
  var Auth = false;
  var isMouseDown = 0;
  var offsetX = 0;
  var offsetY = 0;
  var intervalIDdialog;
 /*
 ** 创建Div对话框
 */

 
 /*
 ** Div窗体系统功能，确认框消息框
 */
 DivDialog.Confirm = function(title,msg,fun){
    
    if(this.isExistById("DivConfirm")!=null)
        return;
    
    var DivConfirm = document.createElement("DIV");
    DivConfirm.className = "float-exit";
    DivConfirm.id = "DivConfirm";
    with(DivConfirm.style){
        position = "absolute";
        //width = "233px";
        //height = "117px" ;
        left = document.body.offsetWidth/2 + "px";
        top = "250px";
    }
    
    var DviTitle = "<div class=\"exit-title\" onmousedown=\"fDragging(document.getElementById('DivConfirm'), event, false);\"><div class=\"exit-title-left\">"
                       + "<img src=\"images/ico_tif.gif\" /> "
                       + title
                       + "</div>"
                       + "<div class=\"exit-title-right\"><a href=\"#\" onclick=\"RemoveObj('DivConfirm');RemoveObj('DivMark');\"><img src=\"images/exit-close.gif\" border=\"0\" /></a>"
                       + "</div></div>";
    
    var DviMsg = document.createElement("DIV");
    DviMsg.className = "exit-content";
    DviMsg.innerHTML = msg
                      + "<div class=\"exit-content2\"><img src=\"images/exit-line.gif\"  align=\"absmiddle\" /></div>";
    
    var DviButton = document.createElement("DIV");
    DviButton.className = "exit-content2";
    DviButton.innerHTML = "<input type=\"button\" value=\"" + Dialog_Btn_Submit + "\" onclick=\"" + fun + "\"/>  "
                        + "<input type=\"button\" value=\"" + Dialog_Btn_Cancel + "\" onclick=\"RemoveObj('DivConfirm');RemoveObj('DivMark');\"/>";
    
    DivConfirm.innerHTML = DviTitle;
    //DivConfirm.appendChild(DviTitle);
    DivConfirm.appendChild(DviMsg);
    DivConfirm.appendChild(DviButton);
    
    //增加退出系统模块
    this.Mark(1);
    document.body.appendChild(DivConfirm);
 }
 
 /*
 ** Div窗体系统功能,消息框的，
 */
 DivDialog.Alert = function(title,msg){
    
    if(this.isExistById("DivAlert")!=null)
        return;
    
    var DivAlert = document.createElement("DIV");
    DivAlert.className = "float-exit";
    DivAlert.id = "DivAlert";
    with(DivAlert.style){
        position = "absolute";
        //width = "233px";
        //height = "117px" ;
        left = document.body.offsetWidth/2 + "px";
        top = "250px";
    }
    
    var DviTitle = "<div class=\"exit-title\" onmousedown=\"fDragging(document.getElementById('DivAlert'), event, false);\"><div class=\"exit-title-left\">"
                       + "<img src=\"images/ico_tif.gif\" /> "
                       + title
                       + "</div>"
                       + "<div class=\"exit-title-right\"><a href=\"#\" onclick=\"RemoveObj('DivAlert');RemoveObj('DivMark');\"><img src=\"images/exit-close.gif\" border=\"0\" /></a>"
                       + "</div></div>";
    
    var DviMsg = document.createElement("DIV");
    DviMsg.className = "exit-content";
    DviMsg.innerHTML = msg
                      + "<div class=\"exit-content2\"><img src=\"images/exit-line.gif\" align=\"absmiddle\" /></div>";
    
    var DviButton = document.createElement("DIV");
    DviButton.className = "exit-content2";
    DviButton.innerHTML = "<input type=\"button\" value=\"" + Dialog_Btn_Submit + "\" onclick=\"RemoveObj('DivAlert');RemoveObj('DivMark');\"/>";
    
    DivAlert.innerHTML = DviTitle;
    //DivConfirm.appendChild(DviTitle);
    DivAlert.appendChild(DviMsg);
    DivAlert.appendChild(DviButton);
    
    //增加退出系统模块
    this.Mark(1);
    document.body.appendChild(DivAlert);
 }
 
  
  /*
 ** Div窗体系统功能,DNS查询，
 */
 DivDialog.DNSAlert = function(title,msg){
    
    if(this.isExistById("DivAlert")!=null)
        return;
    
    var DivAlert = document.createElement("DIV");
    DivAlert.className = "float-exit";
    DivAlert.id = "DivAlert";
    with(DivAlert.style){
        position = "absolute";
        width = "280px";
        //height = "117px" ;
        left = document.body.offsetWidth/2 + "px";
        top = "250px";
    }
    
    var DviTitle = "<div class=\"exit-title\" onmousedown=\"fDragging(document.getElementById('DivAlert'), event, false);\"><div class=\"exit-title-left\">"
                       + "<img src=\"images/ico_tif.gif\" /> "
                       + title
                       + "</div>"
                       + "<div class=\"exit-title-right\"><a href=\"#\" onclick=\"RemoveObj('DivAlert');RemoveObj('DivMark');\"><img src=\"images/exit-close.gif\" border=\"0\" /></a>"
                       + "</div></div>";
    
    var DviMsg = document.createElement("DIV");
    DviMsg.className = "exit-content";
    DviMsg.innerHTML = msg
                      + "<div class=\"exit-content2\"><img src=\"images/exit-line.gif\" align=\"absmiddle\" /></div>";
    
    var DviButton = document.createElement("DIV");
    DviButton.className = "exit-content2";
    DviButton.innerHTML = "<input type=\"button\" value=\"" + Dialog_Btn_Submit + "\" onclick=\"RemoveObj('DivAlert');RemoveObj('DivMark');\"/>";
    
    DivAlert.innerHTML = DviTitle;
    DivAlert.appendChild(DviMsg);
    DivAlert.appendChild(DviButton);
    
    //增加退出系统模块
    this.Mark(1);
    document.body.appendChild(DivAlert);
 }
 
 
 
 /*
 ** Div窗体系统功能，流量预警设置
 */
 DivDialog.FluxSetting = function(title,value,note){
    
    if(this.isExistById("DivFluxSetting")!=null){
        $("DivFluxSetting").src = "FluxSetting.aspx?init=1";
        $("DivFluxSetting").style.display="block";
        return;
    }
    
    var DivFluxSetting = document.createElement("DIV");
    DivFluxSetting.className = "float-approve";
    DivFluxSetting.id = "DivFluxSetting";
    with(DivFluxSetting.style){
        position = "absolute";
        width = "220px";
        //height = "135px" ;
        left = document.body.offsetWidth/2 + "px";
        top = "250px";
    }
    
    var DviTitle = "<div class=\"exit-title\" onmousedown=\"fDragging(document.getElementById('DivFluxSetting'), event, false);\"><div class=\"exit-title-left\">"
                       + "<img src=\"images/ico_tif.gif\" /> "
                       + title
                       + "</div>"
                       + "<div class=\"exit-title-right\"><a href=\"#\" onclick=\"RemoveObj('DivFluxSetting');RemoveObj('DivMark');\"><img src=\"images/exit-close.gif\" border=\"0\" /></a>"
                       + "</div></div>";
    
    var DviMsg = document.createElement("DIV");
    DviMsg.className = "exit-content";
    DviMsg.innerHTML = "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"
                      + "<tr>"
                      + "<td width=\"110\" class=\"useapprove\" align=\"left\"><strong>"+value+"</strong></td>"
                      + "<td align=\"left\"><input id=\"tbWebHostFlux\" onclick=\"$('tdFluxValueTitle').style.display='none';\" style=\"border:1px solid #9BA6B1; width:25px; font-size:11px\" /><strong>%</strong></td>"
                      + "</tr><tr>"
                      + "<td id=\"tdFluxValueTitle\" height=\"24\" colspan=\"2\" align=\"left\" style=\"color:#FF5E16;display:none;\">"+note+"</td>"
                      + "</tr></table><iframe id=\"iframeFluxSetteing\" src=\"FluxSetting.aspx?init=1\" style=\"display:none\"></iframe>"
                      + "<div class=\"exit-content2\"><img src=\"images/exit-line.gif \" width=\"180px\" align=\"absmiddle\" /></div>";
    
    var DviButton = document.createElement("DIV");
    DviButton.className = "exit-content2";
    DviButton.innerHTML = "<input type=\"button\" value=\"" + Dialog_Btn_Save + "\" onclick=\"FluxSet()\"/>";
    
    DivFluxSetting.innerHTML = DviTitle;
    DivFluxSetting.appendChild(DviMsg);
    DivFluxSetting.appendChild(DviButton);
    
    //增加系统模块
    this.Mark(1);
    document.body.appendChild(DivFluxSetting);
 }
 
  function FluxSet(){
    $("tdFluxValueTitle").style.display = "none";
    var value = $("tbWebHostFlux").value.replace(/(^\s*)  |(\s*$)/g,  "");
    if(value.length>0 && !isNaN(value) && value>=1 && value<=90){
        $("iframeFluxSetteing").src = "FluxSetting.aspx?value="+value;
    }
    else{
        $("tdFluxValueTitle").style.display = "block";
    }
 }
 
 /*
 ** 禁止操作(Mark)
 */
 DivDialog.Mark=function(type){
    if(this.isExistById("DivMark") != null)
        return;
        
    var mark = document.createElement("DIV");
    mark.id = "DivMark";
    mark.style.height = document.body.offsetHeight + "px";
    if(type == 0){
        SetDropList(false,loadingIframeId);
        mark.className = "popup-man";
    }
    else if(type == 1){
        mark.className = "popup-man-operate";
        SetDropList(false,loadingIframeId);
    }
    else if(type == 2){
        mark.className = "popup-man-tabloading";
        SetDropList(false,loadingIframeId);        
    }    
    
    
    document.body.appendChild(mark);    
 }
 
  
 
 /*
 ** Div窗体系统功能，忘记密码
 */
 DivDialog.Forgetpasswd = function(title,username,email,btText,msg){
    
    if(this.isExistById("DivForgetpasswd")!=null){
        $("DivForgetpasswd").src = "Forgetpasswd.aspx?init=1";
        $("DivForgetpasswd").style.display="block";
        return;
    }
    
    var DivForgetpasswd = document.createElement("DIV");
    DivForgetpasswd.className = "float-approve";
    DivForgetpasswd.id = "DivForgetpasswd";
    with(DivForgetpasswd.style){
        position = "absolute";
        width = "310px";
        //height = "135px" ;
        left = document.body.offsetWidth/2 + "px";
        top = "250px";
    }
    
    var DviTitle = "<div class=\"exit-title\" onmousedown=\"fDragging(document.getElementById('DivForgetpasswd'), event, false);\"><div class=\"exit-title-left\">"
                       + "<img src=\"images/ico_tif.gif\" /> "
                       + title
                       + "</div>"
                       + "<div class=\"exit-title-right\"><a href=\"#\" onclick=\"RemoveObj('DivForgetpasswd');RemoveObj('DivMark');\"><img src=\"images/exit-close.gif\" border=\"0\" /></a>"
                       + "</div></div>";
    
    var DviMsg = document.createElement("DIV");
    DviMsg.className = "exit-content";
    DviMsg.innerHTML = "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"
                      + "<tr>"
                      + "<td rowspan=\"2\" width=\"60\"><img src=\"images/Password.gif\"</td>"
                      + "<td width=\"80\" class=\"useapprove\" align=\"right\" style=\"color:#224D7E;\"><strong>"+username+"</strong></td>"
                      + "<td align=\"left\"><input id=\"UserName\" style=\"border:1px solid #9BA6B1; width:120px; font-size:11px\" /></td>"
                      + "</tr><tr>"
                     
                      + "<td width=\"80\" class=\"useapprove\" align=\"right\" style=\"color:#224D7E;\"><strong>"+email+"</strong></td>"
                      + "<td align=\"left\"><input id=\"Email\" style=\"border:1px solid #9BA6B1; width:120px; font-size:11px\" /></td>"
                      + "</tr><tr><td colspan=\"3\"><div style=\"margin-top:5px; padding:5px 5px 5px 75px; color:#ff6600\"><span id=\"forgetpwmsg\">" + msg + "</span></div></td></tr></table><iframe id=\"iframeForgetpasswd\" src=\"Forgetpasswd.aspx?init=1\" style=\"display:none\"></iframe>"
                      + "<div class=\"exit-content2\"><img src=\"images/exit-line.gif\" align=\"absmiddle\" /></div>";
                        
    var DviButton = document.createElement("DIV");
    DviButton.className = "exit-content2";
    DviButton.innerHTML = "<input type=\"button\" value=\"" + btText + "\" onclick=\"Getpasswd()\"/>";
    
    DivForgetpasswd.innerHTML = DviTitle;
    DivForgetpasswd.appendChild(DviMsg);
    DivForgetpasswd.appendChild(DviButton);
    
    //增加系统模块
    this.Mark(1);
    document.body.appendChild(DivForgetpasswd);
 }
 
function Getpasswd(){
    var UserName = $("UserName").value;
    var Email = $("Email").value;
    
     if(UserName == "")
     {
        $("UserName").focus();
		return false;
	 }
	 
	 if(Email=="")
	 {
	    $("Email").focus();
		return false;
	 }
     if(UserName!="" && Email!="")
     {
        $("iframeForgetpasswd").src = "Forgetpasswd.aspx?username=" + UserName + "&email="+Email ;
    }
 }
 
  
 /*
 ** 页面加载功能
 */
 DivDialog.Loading=function(){
    if(loadingIframeId == "Default"){
        this.Mark(0);
        var popup2 = document.createElement("DIV");    
        //popup2.className = "popup2";
        popup2.id = "PageLoading";
        
        var popup_man = document.createElement("DIV");
        popup_man.className = "popup-man";
        
        var float_loading = document.createElement("DIV");
        float_loading.id = "float-loading";    
         
        var float_con = document.createElement("DIV");
        float_con.className = "float-con";
        float_con.className = "float-con";    
        var float_right = document.createElement("DIV");
        float_right.className = "float-right";
        float_right.innerHTML = "<img src=\"images/spack.gif\" width=\"1\" height=\"1\" />";
        
        var pdright = document.createElement("DIV");
        pdright.className = "pdright";
        var content = document.createElement("DIV");
        content.className = "con-tent";
        var float_bottom  = document.createElement("DIV");
        float_bottom.className = "float-bottom";
        float_bottom.innerHTML = "<img src=\"images/spack.gif\" width=\"1\" height=\"1\" />";
       
        var loading_line_bg = document.createElement("DIV");
        loading_line_bg.className = "loading-line-bg";
        loading_line_bg.innerHTML = "<div id=\"currplanbar\" style=\"background:url(images/load_lineBGs.gif); width:0%; height:6px;\"><img src=\"images/spack.gif\" width=\"1\" height=\"1\" /></div>";
        //进度数值
        var loading_font = document.createElement("DIV");
        loading_font.className = "loading-font";
        loading_font.id = "loading_font";
        loading_font.innerText = "0%";
        //点击刷新
        var loading_fonta = document.createElement("DIV");
        loading_fonta.innerHTML = "<a href=\"#\" onclick='RefreshPage(\"" + loadingIframeId + "\",\"" + loadingRrl + "\");CurrPlan = 0 ;TimeUsed = 0;'>[" + Dialog_Btn_Refresh + "]</a>";
        loading_fonta.className = "loading-fonta";
        
        content.appendChild(loading_line_bg);
        content.appendChild(loading_font);
        content.appendChild(loading_fonta);
        
        pdright.appendChild(content);
        pdright.appendChild(float_bottom);
        
        float_con.appendChild(pdright);
        
        float_loading.appendChild(float_con);
        float_loading.appendChild(float_right);
        
        popup2.appendChild(popup_man);
        popup2.appendChild(float_loading);
    }
    else{
        this.Mark(2);
        var popup2 = document.createElement("DIV");
        popup2.id = "PageLoading";
//        with(popup2.style){
//            position = "absolute";
//            top = "82px";
//            right = "50px";
//            width = "180px";
//            height= "30px";
//        }    
//        popup2.innerHTML = "<div style=\"height:26px; background:#C5C5C5; margin-left:1px;filter:Alpha(opacity=80);-moz-opacity:0.8;\"></div>"
//                            + "<div style=\"border:1px solid #0C83C1; height:19px; background:#F0F7FA; margin-top:-26px; margin-right:2px; color:#19538E; padding-top:3px; padding-left:5px;\">"
//                            + "<img src=\"images/Loading.gif\" align=\"absmiddle\"/>"
//                            + "页面载入中，请稍后...";
//                            +"</div></div>";
        popup2.innerHTML = "<div style=\"position:absolute; right:43%; top:40%; background:#C5C5C5;\">"
                            + "<div style=\"position:relative; left:-2px; top:-2px; font-size:14px; background:#F0F7FA; padding:6px 8px; border:solid 1px #0C83C1; color:#19538E;\">"
                            + "<img src=\"images/Loading.gif\" align=\"absmiddle\"/>"
                            + Dialog_Loading;
                            + "</div></div>";
    }
    
    //增加页面加载进度
    document.body.insertBefore(popup2,$("layout"));
 }
 
 /*
 ** 系统退出消息框
 */
 DivDialog.Logout=function(msg,fun)
 {
        if(loadingIframeId == "FileManager" || loadingIframeId == "DataBaseAdmin"){
            SetOtherTabpageStyle("Default",0);
        }
        
        this.Mark(0);
        var popup2 = document.createElement("DIV");    
        popup2.id = "PageLoadingExit";
        
        var popup_man = document.createElement("DIV");
        popup_man.className = "popup-man";
        
        var float_loading = document.createElement("DIV");
        float_loading.id = "float-loading";    
         
        var float_con = document.createElement("DIV");
        float_con.className = "float-con";
        var float_right = document.createElement("DIV");
        float_right.className = "float-right";
        float_right.innerHTML = "<img src=\"images/spack.gif\" width=\"1\" height=\"1\" />";
        
        var pdright = document.createElement("DIV");
        pdright.className = "pdright";
        var content = document.createElement("DIV");
        content.className = "con-tent";
        var float_bottom  = document.createElement("DIV");
        float_bottom.className = "float-bottom";
        float_bottom.innerHTML = "<img src=\"images/spack.gif\" width=\"1\" height=\"1\" />";
        
        //退出消息 
        var DviMsg = document.createElement("DIV");
        DviMsg.className = "logoutmsg";
        DviMsg.innerHTML = "<div id=\"currplanbar\" style=\"color:#E0E4E7;\"><img src=\"images/spack.gif\" width=\"1\" height=\"1\" />" + msg + "</div>";
        
        
        //退出按纽
        var DviButton = document.createElement("DIV");
        DviButton.className = "logoutbuttom";
        DviButton.innerHTML = "<input type=\"button\" value=\"" + Dialog_Btn_Submit + "\" onclick=\"" + fun + "\"/>"
                         + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"" + Dialog_Btn_Cancel + "\" onclick=\"RemoveObj('PageLoadingExit');RemoveObj('DivMark');\"/>";
    
        content.appendChild(DviMsg);
        content.appendChild(DviButton);
        pdright.appendChild(content);
        pdright.appendChild(float_bottom);
        float_con.appendChild(pdright);
        float_loading.appendChild(float_con);
        float_loading.appendChild(float_right);
        
        popup2.appendChild(popup_man);
        popup2.appendChild(float_loading);
        
        document.body.insertBefore(popup2,$("layout"));
 }
 
 /*
 ** 删除对话框
 */
 DivDialog.DeleteDialog=function(id){
    var obj = DivDialog.isExistById(id);
    if(obj != null){
        document.body.removeChild(obj);
    }
 }
 /*
 ** 操作进度提示
  */
 DivDialog.Operating=function(){
    //this.Mark(2);
    var popup2 = document.createElement("DIV");
    popup2.id = "PageLoading";
    popup2.innerHTML = "<div style=\"position:absolute; right:43%; top:40%; background:#C5C5C5;\">"
                    + "<div style=\"position:relative; left:-2px; top:-2px;font-size:14px; background:#F0F7FA; padding:6px 8px; border:solid 1px #0C83C1; color:#19538E;\">"
                    + "<img src=\"images/Loading.gif\" align=\"absmiddle\"/>"
                    + Dialog_Operating;
                    + "</div></div>";
    //增加操作进度
    document.body.insertBefore(popup2,$("layout"));
 }
 
 /*
 ** 判断对象是否存在
 */
 DivDialog.isExistById=function(id){
    var obj = $(id);
    if(obj == null || obj == "undefined")
        return null;
    else
        return obj;
 }
 
 DivDialog.MouseDown=function(id,Event){
    var obj = $(id);
    isMouseDown = 1;
    try{
        offsetX = Event.offsetX;
        offsetY = Event.offsetY;
    }
    catch(e){}
    SetCapture(Event.srcElement);
 }
 
 DivDialog.MouseMove=function(id,Event){
    if(isMouseDown==1){
        var obj = $(id);
        try{
            obj.style.left = (Event.clientX) + "px";
            obj.style.top = (Event.clientY) + "px";
        }
        catch(e){}
    }    
 }
 
 DivDialog.MouseUp=function(id,Event){
    isMouseDown = 0;
    ReleaseCapture(Event.srcElement);
 }
 
 
 //删除控件对象
 function RemoveObj(id){
    try{
        document.body.removeChild($(id));
        if(id == "DivMark"){
            SetDropList(true,loadingIframeId);
        }
    }
    catch(e){}
 }
 
 
 function SetCapture(o){
   if(o.setCapture) o.setCapture();
   else if(o.captureEvents)o.captureEvents(DivDialog.MouseMove|DivDialog.MouseUp);
  }
  
  function ReleaseCapture(o){
   if(o.releaseCapture) o.releaseCapture();
   else if(o.releaseEvents)o.releaseEvents(DivDialog.MouseMove|DivDialog.MouseUp);
  }
  
  function Submit(id)
    {
        try{
            var obj = document.getElementById(loadingIframeId).contentWindow.document.getElementById(id);
            obj.onclick = function(){return true;}
            obj.click();
            RemoveObj("DivConfirm");
            RemoveObj("DivMark");
            try{
                var tempList = document.getElementById(loadingIframeId).contentWindow.document.getElementsByTagName("DIV");
	            for(var i=0;i<tempList.length;i++){
	                if(tempList[i].className=="accurate" || tempList[i].className=="error"){
	                   tempList[i].style.display = "none";
	                }
	            }
            }
            catch(e){}
        }
        catch(e){}
    }
    
  function SetDropList(bDisplay,id){
    if(id != "FileAdmin" && id != "DataBaseAdmin"){
        try{
            var list = $(id).contentWindow.document.getElementsByTagName("SELECT");
            for(var i=0;i<list.length;i++){
                if(bDisplay){
                    list[i].style.display = "block";
                }else{
                    list[i].style.display = "none";
                }
            }
        }catch(e){}
    }
}