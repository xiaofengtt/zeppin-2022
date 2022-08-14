var layout,dialog,q_dialog;

Ext.onReady(function() {
	layout = new Ext.BorderLayout(document.body, {
		north: {
			split: false
			, initialSize: 105
			, margins: {top:0,bottom:0,right:0,left:0}
		},
		west: {
			split:false,
			titlebar: true,	
			collapsible: true,
			animate: true,
			initialSize: 220, 
			margins: {top:0,bottom:0,right:0,left:0}
		},
		center: {
			split: false
			, autoScroll: true
			, margins: {top:0,bottom:0,right:0,left:0}
		},
		south: {
			split: false
			,collapsed : true
			,titlebar: false
			,initialSize: 105
			,margins: {top:0,bottom:0,right:0,left:0}
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
					title:'�û���������',
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
						{text: '<b>�����绰</b>', handler: callTel},
						{text: '<b>ת�ӵ绰</b>', handler: transferTel},
						{text: '<b>����ͨ��</b>', handler: holdTel},
						{text: '<b>�෽����</b>', handler: telmeetingInit}
					]
				});
				var menu2 = new Ext.menu.Menu({
					id:'custMenu',
					items:[
						{text: '<b>�ͻ�ƥ��</b>',handler:showCondition},
						'-',
						{text: '<b>��ѯ�ͻ�</b>', handler: queryCust},
						{text: '<b>�½����˿ͻ�</b>', handler: newGRCust},
						{text: '<b>�½������ͻ�</b>', handler: newJGCust},
						{text: '<b>�½���ϵ��</b>', handler: newCustContacts}
					]
				});
				var menu3 = new Ext.menu.Menu({
					id:'marketingMenu',
					items:[
						{text: '<b>��Ʒ��ѯ</b>', handler: queryPro},
						'-',
						{text: '<b>�ͻ�ԤԼ</b>', handler: presell},
						{text: '<b>�ͻ��Ϲ�</b>', handler: purchase},
						{text: '<b>�ͻ��깺</b>', handler: applyreach},
						{text: '<b>�ͻ����</b>', handler: redeem}
					]	
				});
				tb.add(
					{
						text:'�������',
						menu:menu1
					},
					{
						text:'�ͻ�����',
						menu:menu2
					},
					{
						text:'Ӫ������',
						menu:menu3
					}
				);
				
				//Ext.getDom("callin-record").value = ' ������룺'+ Ext.getDom("cc_tel_num").value + '\n �ͻ����ƣ�' +  Ext.getDom("cc_cust_name").value;
				dlayout.add('north', new Ext.ContentPanel('north', {title: 'north',toolbar: tb}));
				//dlayout.add('center', new Ext.ContentPanel('callininfo', {title: '������Ϣ'}));
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
			Ext.getDom("cc_manager_man").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN_NAME);
	        Ext.getDom("cc_card_type_name").value = Ext.util.Format.undef(custJson[0].CARD_TYPE_NAME);
	        Ext.getDom("cc_card_id").value = Ext.util.Format.undef(custJson[0].CARD_ID);
	        Ext.getDom("cc_manager_man_id").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN);
	        Ext.getDom("cc_extension").value = Ext.util.Format.undef(custJson[0].EXTENSION);
		}else{
			Ext.MessageBox.alert('����','û���ҵ�ƥ����û���Ϣ');
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
		var content = new Ext.ContentPanel(Ext.id(),{autoCreate:true,title: '����绰',closable:true,fitToFrame:true,fitContainer:true,autoScroll:false});
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
    
	function queryPro(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '��Ʒ��ѯ',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/base/product_list.jsp'>");
		dlayout.add('center',content);
	}
	function presell(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�ͻ�ԤԼ',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/bespeak_add.jsp'>";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/bespeak_add.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function purchase(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�ͻ��Ϲ�',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/subscribe_add2.jsp'>";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/subscribe_add2.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function applyreach(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�ͻ��깺',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/apply_purchase_add.jsp'>";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/apply_purchase_add.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function redeem(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�ͻ����',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/redemption_add.jsp'>");
		dlayout.add('center',content);
	}
	function queryCust(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '��ѯ�ͻ���Ϣ',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var input_operatorCode = document.getElementById("input_operatorCode").value;
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/client/analyse/client_query_list.jsp?v_setView=1&v_op_code='"+input_operatorCode+">");
		dlayout.add('center',content);
	}
	function newJGCust(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�½������ͻ�',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/client/clientinfo/clinet_info_new_end.jsp'>");
		dlayout.add('center',content);
	}
	function newGRCust(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�½����˿ͻ�',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/client/clientinfo/client_info_new.jsp'>");
		dlayout.add('center',content);
	}
	function newCustContacts(item){
		if(Ext.getDom("cc_cust_name").value==""){
			Ext.MessageBox.alert("����","��δȷ������ͻ�������Ϣ�������½��ͻ���ϵ��");
		}else{
			var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�½���ϵ��',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
			var cententStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/client/clientinfo/client_contacts_list.jsp?contactType=1&cust_id="+Ext.getDom("cc_cust_name").value+"'/>";
			var contentDiv  = content.getEl().createChild(cententStr);
			dlayout.add('center',content);
		}
	}
	function callTel(){
		var content = new Ext.ContentPanel(Ext.id(),{autoCreate:true,title: '����绰',closable:true,fitToFrame:true,fitContainer:true,autoScroll:false});
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
			Ext.MessageBox.alert("����", "������ı�ǩ��Ϣδ�ܱ���!");
		},
		async : false
	});
}

function newScratchpaper() {
	var messageBox;
	Ext.get('newScratchpaper').on('click', function(e){
		messageBox = Ext.MessageBox.show({
			title: '������ǩ',
			msg: '�����������¼�����ݣ�',
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
			title: 'ϵͳѡ��',
			msg: '�Ƿ��˳�ϵͳ��',
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
			title: 'ϵͳѡ��',
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
    Ext.MessageBox.prompt('�绰����','��������Ҫת�ӵĵ绰����:',transferTelConfirm);    
}

function transferTelConfirm(btn,text){
	if(btn=='ok'){
		var telnum = /^[0-9]*$/;
		if(!telnum.test(text)){
			Ext.MessageBox.alert('����','������ĵ绰����'+text+'�����Ϲ����������������');
		}else{
			transferInit(text);
			Ext.MessageBox.confirm(
				'ȷ��','��Ҫת�ӵĵ绰������:'+text+'������ȷ�ϰ�ť���ת��',
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
	Ext.MessageBox.alert("֪ͨ","�绰�����С���,����ȷ�ϰ�ťȡ�ص绰",retrieveTel);
}

function retrieveTel(){
	telRetrieveCall();
}

function telmeetingInit(){
	Ext.MessageBox.prompt('�绰����','���������绰����ĵ������绰����:',telmeetingInitConfirm);   
}

function telmeetingInitConfirm(btn,text){
	if(btn=='ok'){
		var telnum = /^[0-9]*$/;
		if(!telnum.test(text)){
			Ext.MessageBox.alert('����','������ĵ绰����'+text+'�����Ϲ����������������');
		}else{
			meetingInit(text);
			Ext.MessageBox.confirm(
				'ȷ��','��Ҫת�ӵĵ绰������:'+text+'������ȷ�ϰ�ť��ʼ�绰����',
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
	    q_dialog.addButton('ȷ ��', matchCust, q_dialog);
    	q_dialog.addButton('�� ��', q_dialog.hide, q_dialog);
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
		Ext.MessageBox.alert('����','û���ҵ�ƥ����û���Ϣ');
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
		Ext.MessageBox.alert('����','û���ҵ�ƥ����û���Ϣ');
	}
}
