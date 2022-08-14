var layout,dialog,q_dialog;

Ext.onReady(function() {
	layout = new Ext.BorderLayout(document.body, {
		north: {
			split: false
			, initialSize: 105
			, margins: {top:0,bottom:0,right:4,left:4}
		},
		west: {
			split:false,
			titlebar: true,	
			collapsible: true,
			animate: true,
			initialSize: 220, 
			margins: {top:0,bottom:0,right:0,left:4}
		},
		center: {
			split: false
			, autoScroll: true
			, margins: {top:0,bottom:0,right:4,left:0}
		},
		south: {
			split: false
			,collapsed : true
			,titlebar: true
			, initialSize: 105
			, margins: {top:0,bottom:0,right:4,left:4}
			,collapsible: true
			,animate: true
		}
	});
	layout.beginUpdate();
	layout.add('north', new Ext.ContentPanel('home-banner'));
	layout.add('west', new Ext.ContentPanel('home-column-left', {fitToFrame:true}));
	layout.add('center', new Ext.ContentPanel('home-column-main'));
	layout.add('south', new Ext.ContentPanel('home-column-south'));
	layout.restoreState();
	layout.endUpdate();
	if(Ext.isSafari || Ext.isOpera){
		layout.layout();
	}
	
	loadMenuAccordion();	
});
var dialogLayout = function(){
	var dlayout;
	return {
		init : function(){
			if(!dialog){ 
				dialog = new Ext.LayoutDialog("callin-dlg", { 
					modal:true,
					width:780,
					height:530,
					shadow:true,
					minWidth:600,
					minHeight:530,
					proxyDrag: true,
					resizable : false,
					title:'Call Center',//用户呼叫中心
					north:{
						split:false,
						initialSize: 25,
						minSize: 25,
						maxSize: 25,
						animate: false
					},
					center: {
						autoScroll:true,
						tabPosition: 'bottom',
						closeOnTab: true,
						alwaysShowTabs: true
					}
				});
				dialog.addKeyListener(27, dialog.hide, dialog);   
				dlayout = dialog.getLayout();
				dlayout.beginUpdate();
				var tb = new Ext.Toolbar('menubar');
				var menu1 = new Ext.menu.Menu({
					id:'ccMenu',
					items:[
						{text: '<b>Call</b>', handler: callTel},//拨打电话
						{text: '<b>Transform</b>', handler: transferTel},//转接电话
						{text: '<b>Hold on</b>', handler: holdTel},//保持通话
						{text: '<b>Many meetings</b>', handler: telmeetingInit}//多方会议
					]
				});
				var menu2 = new Ext.menu.Menu({
					id:'custMenu',
					items:[
						{text: '<b>Customer Matching</b>',handler:showCondition},//客户匹配
						'-',
						{text: '<b>Customer Query</b>', handler: queryCust},//查询客户
						{text: '<b>Add personal customer</b>', handler: newGRCust},//新建个人客户
						{text: '<b>Add institutions customer</b>', handler: newJGCust},//新建机构客户
						{text: '<b>Add contacter</b>', handler: newCustContacts}//新建联系人
					]
				});
				var menu3 = new Ext.menu.Menu({
					id:'marketingMenu',
					items:[
						{text: '<b>Product Query</b>', handler: queryPro},//产品查询
						'-',
						{text: '<b>Customer Booking</b>', handler: presell},//客户预约
						{text: '<b>Customer Subscribe</b>', handler: purchase},//客户认购
						{text: '<b>Customer Apply to purchase</b>', handler: applyreach},//客户申购
						{text: '<b>Customer Redeem</b>', handler: redeem}//客户赎回
					]	
				});
				tb.add(
					{
						text:'CallCenter Operation',//话务操作
						menu:menu1
					},
					{
						text:'Customer Operation',//客户操作
						menu:menu2
					},
					{
						text:'Marketing Operation',//营销操作
						menu:menu3
					}
				);
				
				//Ext.getDom("callin-record").value = ' 来电号码：'+ Ext.getDom("cc_tel_num").value + '\n 客户名称：' +  Ext.getDom("cc_cust_name").value;
				dlayout.add('north', new Ext.ContentPanel('north', {title: 'north',toolbar: tb}));
				//dlayout.add('center', new Ext.ContentPanel('callininfo', {title: '来电信息'}));
				dlayout.endUpdate();
				dialog.addListener("show",diayoutInit);
				dialog.addListener("hide",dlayoutHide);	
				
				}
			} 
		};

	function initCustInfo(){
		var cc_tel_num = Ext.getDom("cc_tel_num").value;
		//ccService.getCustList(cc_tel_num,op_code,initCustInfoCallback);
    }
    	
	function initCustInfoCallback(data){
		var custJsonStr = data;
		custJson = eval(custJsonStr);
		if(custJson.length>0){
			DWRUtil.removeAllOptions("cc_cust_name");
			DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
			Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
			Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_TYPE_NAME);
			Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].CUST_LEVEL_NAME);
			Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOTAL_MONEY);
			Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].EXCHANGE_AMOUNT);
			Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BACK_AMOUNT);
			Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].BEN_AMOUNT);
			Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].LAST_RG_DATE);
			Ext.getDom("cc_card_type_name").value = Ext.util.Format.undef(custJson[0].CARD_TYPE_NAME);
	        Ext.getDom("cc_card_id").value = Ext.util.Format.undef(custJson[0].CARD_ID);
	        Ext.getDom("cc_manager_man_id").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN);
	        Ext.getDom("cc_extension").value = Ext.util.Format.undef(custJson[0].EXTENSION);
		}else{
			Ext.MessageBox.alert('Warn','Can not find the matched customer infomation');//没有找到匹配的用户信息
		}
	}
	function diayoutInit(){
		var callTalkType = Ext.getDom("callTalkType").value;
		var target_custid = Ext.getDom("target_custid").value;
		var cc_tel_num = Ext.getDom("cc_tel_num").value;
		var iframeUrl = '';

		if(callTalkType==0){
			iframeUrl = '/callcenter/callingTelSelect.jsp';
		}
		else if(callTalkType==1){			
			iframeUrl = '/affair/sms/cust_tel.jsp?input_flag=2&target_custid='+target_custid;
		}
		else if(callTalkType==2){
			iframeUrl = '/callcenter/callingTelSelect.jsp?input_flag=2&input_flag2=1&phoneNumber='+cc_tel_num;
		}
		//拨打电话
		var content = new Ext.ContentPanel(Ext.id(),{autoCreate:true,title: 'Call',closable:true,fitToFrame:true,fitContainer:true,autoScroll:false});
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='"+iframeUrl+"'>");
		dlayout.add('center',content);
	}
    function dlayoutHide(){
    	while(dlayout.getRegion("center").panels.last()!=null)
    	{
    		dlayout.getRegion("center").remove(dlayout.getRegion("center").panels.last(),true);
    	}
    	Ext.getDom("q_cust_name").value = "";
		Ext.getDom("q_card_num").value = "";
		Ext.getDom("callTalkType").value = "0";
    }
    
	function queryPro(item){//产品查询
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Product Query',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/base/product_list.jsp'>");
		dlayout.add('center',content);
	}
	function presell(item){//客户预约
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Customer Booking',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/bespeak_add.jsp'>";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/bespeak_add.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function purchase(item){//客户认购
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Customer Subscribe',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/subscribe_add2.jsp'>";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/subscribe_add2.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function applyreach(item){//客户申购
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Customer Apply to purchase',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/apply_purchase_add.jsp'>";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/apply_purchase_add.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function redeem(item){//客户赎回
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Customer redeem',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/redemption_add.jsp'>");
		dlayout.add('center',content);
	}
	function queryCust(item){//查询客户信息
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Customer Query',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var input_operatorCode = document.getElementById("input_operatorCode").value;
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/client/analyse/client_query_list.jsp?v_setView=1&v_op_code='"+input_operatorCode+">");
		dlayout.add('center',content);
	}
	function newJGCust(item){//新建机构客户
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Add institutional Customer',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/client/clientinfo/clinet_info_new_end.jsp'>");
		dlayout.add('center',content);
	}
	function newGRCust(item){//新建个人客户
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Add Personal Customer',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/client/clientinfo/client_info_new.jsp'>");
		dlayout.add('center',content);
	}
	function newCustContacts(item){
		if(Ext.getDom("cc_cust_name").value==""){
			Ext.MessageBox.alert("Warn","Fail to determine the basic information about the clients");//警告 尚未确定来电客户基本信息 不能新建客户联系人
		}else{
			//新建联系人
			var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: 'Add contacts',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
			var cententStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/client/clientinfo/client_contacts_list.jsp?contactType=1&cust_id="+Ext.getDom("cc_cust_name").value+"'/>";
			var contentDiv  = content.getEl().createChild(cententStr);
			dlayout.add('center',content);
		}
	}
	function callTel(){
		//拨打电话
		var content = new Ext.ContentPanel(Ext.id(),{autoCreate:true,title: 'Call',closable:true,fitToFrame:true,fitContainer:true,autoScroll:false});
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/callcenter/callingTelSelect.jsp'>");
		dlayout.add('center',content);	
	}
}();
Ext.EventManager.onDocumentReady(dialogLayout.init, dialogLayout, true);


function loadMenuAccordion(){
	var stretchers = document.getElementsByClassName('stretcher');
	var toggles = document.getElementsByClassName('toggle');
	
	new Fx.Accordion(toggles, stretchers, {
		opacity: false,
		duration: 200
	});
}

function loadMain(url) {
	if(url==null||url=="")
		return false;
	else
		Ext.get('home-column-main').dom.src = url;
}

function insertScratchpaper(btn, content) {   
	if(content == ""){
		return;
	}
	scratchpaperManager.saveData(content, "", {
		callback: function(data) {},
		errorHandler : function(message) {
			Ext.MessageBox.alert("Warn", "You failed to save the information input!");//警告 您输入的便签信息未能保存
		},
		async : false
	});
}

function newScratchpaper() {
	var messageBox;
	Ext.get('newScratchpaper').on('click', function(e){
		messageBox = Ext.MessageBox.show({
			title: '新增便签',//新增便签
			msg: 'Please input the content you want to record：',//请输入您想记录的内容
			width: 300,
			buttons: Ext.MessageBox.OKCANCEL,
			multiline: true,
			modal: false,
			fn: insertScratchpaper,
			animEl: 'newScratchpaper'
		});
	});
}

function showLogOut(){
	var messageBox;
	Ext.get('logOutEl').on('click', function(e){
		messageBox = Ext.MessageBox.show({
			title: 'System options',
			msg: 'Exit system？',//是否退出系统
			buttons: Ext.MessageBox.OKCANCEL,
			multiline: false,
			modal: false,
			fn: logOut,
			animEl: 'logoutEl'
		});
	});
}

function checkMenu(el_id,el_info){
	var messageBox;
	Ext.get(el_id).on('click', function(e){
		messageBox = Ext.MessageBox.show({
			title: 'System options',//系统选项
			msg: el_info,
			buttons: Ext.MessageBox.OK,
			multiline: false,
			modal: false,
			fn: function(btn){return;},
			animEl: el_id
		});
	});
}

function transferTel(){
    Ext.MessageBox.prompt('Telephone','Please input your transfrom telephone:',transferTelConfirm);//电话号码 请输入您要转接的电话号码
}

function transferTelConfirm(btn,text){
	if(btn=='ok'){
		var telnum = /^[0-9]*$/;
		if(!telnum.test(text)){
			Ext.MessageBox.alert('Warn','Your input telephone '+text+'does not conform to the rules，check and try again');//警告 您输入的电话号码 不符合规则  请检查后重新输入
		}else{
			transferInit(text);
			Ext.MessageBox.confirm(
				'Confirm','Your transform telephone is:'+text+'，click OK to transfroms',//确认 您要转接的电话号码是 单击确认按钮完成转接
				function(btn){
					if(btn=='yes'){
						transferComplete();
						dialog.hide();
					}	
				}
			);
		}
	}
}

function holdTel(){
	telHold();
	Ext.MessageBox.alert("Note","Phone keep in……,click OK to retrieve your telephone",retrieveTel);//通知  电话保持中  单击确认按钮取回电话
}

function retrieveTel(){
	telRetrieveCall();
}

function telmeetingInit(){
	Ext.MessageBox.prompt('Telephone','Please input in the third conference call number :',telmeetingInitConfirm);//电话号码 请输入参与电话会议的第三方电话号码
}

function telmeetingInitConfirm(btn,text){
	if(btn=='ok'){
		var telnum = /^[0-9]*$/;
		if(!telnum.test(text)){
			Ext.MessageBox.alert('Warn','Your telephone '+text+'does not conform to the rules，check and try again!');//警告 您输入的电话号码 不符合规则 请检查后重新输入
		}else{
			meetingInit(text);
			Ext.MessageBox.confirm(
				'Confirm','Your transform telephone is:'+text+'，click OK to start telephone meetings',//确认 您要转接的电话号码是 单击确认按钮开始电话会议
				function(btn){
					if(btn=='yes'){
						meetingComplete();
					}	
				}
			);
		}
	}
}

function showCondition(){
	if(!q_dialog){
		q_dialog = new Ext.BasicDialog("hello-dlg", { 
                        width:300,
                        height:140,
                        shadow:true,
						modal: true,
                        minWidth:300,
                        minHeight:140,
                        proxyDrag: true,
                        resizable:false,
                        buttonAlign:'center'
                });
	    q_dialog.addKeyListener(27, q_dialog.hide, q_dialog);
	    q_dialog.addButton('OK', matchCust, q_dialog);//确 定
    	q_dialog.addButton('Cancel', q_dialog.hide, q_dialog);//关 闭'
    }
    q_dialog.show();
}

function matchCust(){
	var q_cust_name = escape(Ext.getDom("q_cust_name").value);
	var q_card_num = escape(Ext.getDom("q_card_num").value);
	ccService.getCustInfo(q_cust_name,q_card_num,matchCustCallBack);
	q_dialog.hide();
}

function matchCustCallBack(data){
	var custJsonStr = data;
	custJson = eval(custJsonStr);
	if(custJson.length>0){
		DWRUtil.removeAllOptions("cc_cust_name");
		DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
		Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
		Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
		Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_TYPE_NAME);
		Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].CUST_LEVEL_NAME);
		Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOTAL_MONEY);
		Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].EXCHANGE_AMOUNT);
		Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BACK_AMOUNT);
		Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].BEN_AMOUNT);
		Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].LAST_RG_DATE);	
	}else{
		Ext.MessageBox.alert('Warn','Can not find the matched customer infomation');//警告 没有找到匹配的用户信息
	}
}

function refreshCustInfo(){
	var custId = Ext.getDom("cc_cust_name").value;
	//ccService.getCustInfoById(custId,refreshCustInfoCallBack)
}

function refreshCustInfoCallBack(data){
	var custJsonStr = data;
	custJson = eval(custJsonStr);
	if(custJson.length>0){
		DWRUtil.removeAllOptions("cc_cust_name");
		DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
		Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
		Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
		Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_TYPE_NAME);
		Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].CUST_LEVEL_NAME);
		Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOTAL_MONEY);
		Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].EXCHANGE_AMOUNT);
		Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BACK_AMOUNT);
		Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].BEN_AMOUNT);
		Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].LAST_RG_DATE);	
	}else{
		Ext.MessageBox.alert('Warn','Can not find the matched customer infomation');//警告 没有找到匹配的用户信息
	}
}
