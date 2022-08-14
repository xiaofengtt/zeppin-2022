var layout,dialog,q_dialog;

Ext.onReady(function() {
	layout = new Ext.BorderLayout(document.body, {
		north: {
			split: false
			, initialSize: 90
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
			,titlebar: true
			, initialSize: 105
			, margins: {top:0,bottom:0,right:0,left:0}
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
var dlayout;
var dialogLayout = function(){
	
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
						{text: '<b>�½���ͻ�</b>', handler: newJGCust},
						{text: '<b>�½���ϵ��</b>', handler: newCustContacts}
					]
				});
				var menu3 = new Ext.menu.Menu({
					id:'marketingMenu',
					items:[
						{text: '<b>��Ʒ��ѯ</b>', handler: queryPro},
						'-',
                        {text: '<b>�ͻ�Ԥ�Ǽ�</b>', handler: checkin},
						{text: '<b>�ͻ�ԤԼ</b>', handler: presell},
						{text: '<b>�ͻ��Ϲ�</b>', handler: purchase},
						{text: '<b>�ͻ��깺</b>', handler: applyreach},
						{text: '<b>�ͻ����</b>', handler: redeem}
					]	
				});
				var menu4 = new Ext.menu.Menu({
					id:'search',
					items:[
						{text: '<b>��Ϣ��ȫ�ļ���</b>', handler: searchAll}
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
						text:'Ӫ�����',
						menu:menu3
					},
					{
						text:'��Ϣ��',
						menu:menu4
					}
				);
				
				//Ext.getDom("callin-record").value = ' ������룺'+ Ext.getDom("cc_tel_num").value + '\n �ͻ���ƣ�' +  Ext.getDom("cc_cust_name").value;
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
        //��ѯ�ͻ���Ϣ
		ccService.getCustList(cc_tel_num,op_code,initCustInfoCallback);
        //��ʷ�����¼
        //ɾ��������
        var Container = document.getElementById("mybody");
        if(Container.firstChild != null){
            Container.firstChild.removeNode(true);
        }
        ccService.getCallRecords(0,cc_tel_num,callInfoCallBack);
    }
    	
	function initCustInfoCallback(data){
		var custJsonStr = data;
		custJson = eval(custJsonStr);
		if(custJson.length>0){
			DWRUtil.removeAllOptions("cc_cust_name");
			DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
			Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
			Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
			if(Ext.util.Format.undef(custJson[0].CUST_TYPE) == 1){
                Ext.getDom("customer_sex_title").style.display = "";
                Ext.getDom("customer_sex_content").style.display = "";
                Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].SEX_NAME);
            }else{
                Ext.getDom("customer_sex_title").style.display = "none";
                Ext.getDom("customer_sex_content").style.display = "none";
            }
			Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOUCH_TYPE_NAME);
			Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].MOBILE);
			Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BP);
			Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].O_TEL);
			Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].H_TEL);
            Ext.getDom("cc_cust_emial").value = Ext.util.Format.undef(custJson[0].E_MAIL);
			Ext.getDom("cc_cust_id").value = Ext.util.Format.undef(custJson[0].CUST_ID);
            Ext.getDom("cc_post_address").value = Ext.util.Format.undef(custJson[0].POST_ADDRESS);
            Ext.getDom("cc_post_code").value = Ext.util.Format.undef(custJson[0].POST_CODE);
            Ext.getDom("cc_post_address2").value = Ext.util.Format.undef(custJson[0].POST_ADDRESS2);
            Ext.getDom("cc_post_code2").value = Ext.util.Format.undef(custJson[0].POST_CODE2);
            Ext.getDom("cc_manager_man").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN_NAME);
            Ext.getDom("cc_card_type_name").value = Ext.util.Format.undef(custJson[0].CARD_TYPE_NAME);
            Ext.getDom("cc_card_id").value = Ext.util.Format.undef(custJson[0].CARD_ID);
            Ext.getDom("cc_manager_man_id").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN);
            Ext.getDom("cc_extension").value = Ext.util.Format.undef(custJson[0].EXTENSION);
            //Ext.getDom("cc_cust_tel_num").value = Ext.util.Format.undef(custJson[0].CUST_TEL);
            Ext.getDom("dd_h_tel").value = Ext.util.Format.undef(custJson[0].H_H_TEL);
            Ext.getDom("dd_o_tel").value = Ext.util.Format.undef(custJson[0].H_O_TEL);
            Ext.getDom("dd_mobile").value = Ext.util.Format.undef(custJson[0].H_MOBILE);
            Ext.getDom("dd_bp").value = Ext.util.Format.undef(custJson[0].H_BP);
            Ext.getDom("callin-record").value = "";
            //�ֶ����ҿͻ���ʾ����û��ƥ���Ͽͻ�
            Ext.getDom("update_cust_tel").style.display = "none";
            //��ʾת�ӿͻ�����
            Ext.getDom("zj_server_man").style.display = "none";
		}else{
			Ext.MessageBox.alert('����','û���ҵ�ƥ����û���Ϣ');
            DWRUtil.removeAllOptions("cc_cust_name");
            Ext.getDom("cc_cust_no").value = '';
            Ext.getDom("select_cc_cust_name").value = '';
            Ext.getDom("cc_cust_type").value = '';
            Ext.getDom("cc_cust_grade").value = '';
            Ext.getDom("cc_rg_money").value = '';
            Ext.getDom("cc_zr_money").value = '';
            Ext.getDom("cc_df_money").value = '';
            Ext.getDom("cc_sy_money").value = '';
            Ext.getDom("cc_buy_date").value = '';
            Ext.getDom("cc_manager_man").value = '';
            Ext.getDom("cc_card_type_name").value = '';
            Ext.getDom("cc_card_id").value = '';
            Ext.getDom("cc_manager_man_id").value = "";
            Ext.getDom("cc_extension").value = "";
           // Ext.getDom("cc_cust_tel_num").value = "";
            //�ֶ����ҿͻ���ʾ����û��ƥ���Ͽͻ�
            Ext.getDom("update_cust_tel").style.display = "none";
            //��ʾת�ӿͻ�����
            Ext.getDom("zj_server_man").style.display = "none";
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
			var my_status = Ext.getDom("my_status").value;		
			iframeUrl = '/affair/sms/cust_tel.jsp?input_flag=2&target_custid='+target_custid+'&my_status='+my_status;
		}
		
		var content = new Ext.ContentPanel(Ext.id(),{autoCreate:true,title: '����绰',closable:true,fitToFrame:true,fitContainer:true,autoScroll:false});
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='"+iframeUrl+"'>");
		if(callTalkType == 2){
			dlayout.add('center', new Ext.ContentPanel('callininfo', {title: '������Ϣ'}));
			initCustInfo();
		}else{
			dlayout.add('center',content);
		}
		
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
    function checkin(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�ͻ�Ԥ�Ǽ�',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/checkin_simple_add.jsp'>";
		if(Ext.getDom("cc_tel_num").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='/marketing/sell/checkin_simple_add.jsp?cust_tel="+Ext.getDom("cc_tel_num").value+"&customer_cust_id="+Ext.getDom("cc_cust_name").value+"'>";
		}
		var contentDiv  = content.getEl().createChild(contentStr);
		dlayout.add('center',content);
	}
	function presell(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�ͻ�ԤԼ',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
        var url = "/marketing/sell/bespeak_simple_add.jsp?cc_cust_id="+Ext.getDom("cc_cust_name").value+"";
        //��������
        if(Ext.getDom("user_id").value == 2)
            url = "/marketing/sell/reserve_simple_add.jsp?customer_cust_id="+Ext.getDom("cc_cust_name").value+"";
		var contentStr = "<iframe frameborder='0' style='width:100%;height:100%;' src="+url+">";
		if(Ext.getDom("cc_cust_name").value!=""){
			contentStr =  "<iframe frameborder='0' style='width:100%;height:100%;' src='"+url+"&cust_tel="+Ext.getDom("cc_tel_num").value+"'>";
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
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '�½���ͻ�',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
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
			Ext.MessageBox.alert("����","��δȷ������ͻ�����Ϣ�������½��ͻ���ϵ��");
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
	function searchAll(item){
		var content = new Ext.ContentPanel(Ext.id(), {autoCreate:true,title: '��Ϣ��ȫ�ļ���',closable:true,fitToFrame:true,fitContainer :true,autoScroll:false });
		var contentDiv  = content.getEl().createChild("<iframe frameborder='0'  style='width:100%;height:100%;' src='/wiki/search_all.jsp'>");
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
        if(text != ""){
            if(!telnum.test(text)){
                Ext.MessageBox.alert('����','������ĵ绰����'+text+'����Ϲ����������������');
            }else{
                Ext.MessageBox.confirm(
                    'ȷ��','��Ҫת�ӵĵ绰������:'+text+'������ȷ�ϰ�ť���ת��',
                    function(btn){
                        if(btn=='yes'){
                        	transferInit(text);	
                            transferComplete();
                            if(Ext.getDom("user_id").value != 15){
                            	dialog.hide();
                            }	
                        }	
                    }
                );
            }
        }else{
            Ext.MessageBox.alert('����','��������ֻ����');
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
	if(Ext.getDom("user_id").value == 15){
		Ext.MessageBox.confirm(
			'ȷ��','����ȷ�ϰ�ť��ʼ�绰����',
			function(btn){
				if(btn=='yes'){
					meetingComplete();
				}
			}
		);
	}else{
		Ext.MessageBox.prompt('�绰����','���������绰����ĵ���绰����:',telmeetingInitConfirm); 
	}	  
}

function telmeetingInitConfirm(btn,text){
	if(btn=='ok'){
		var telnum = /^[0-9]*$/;
		if(!telnum.test(text)){
			Ext.MessageBox.alert('����','������ĵ绰����'+text+'����Ϲ����������������');
		}else{
			
			Ext.MessageBox.confirm(
				'ȷ��','��Ҫת�ӵĵ绰������:'+text+'������ȷ�ϰ�ť��ʼ�绰����',
				function(btn){
					if(btn=='yes'){
						meetingInit(text);
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
	dlayout.add('center', new Ext.ContentPanel('callininfo', {title: 'ƥ����Ϣ'}));
	var q_cust_name = escape(Ext.getDom("q_cust_name").value);
	var q_card_num = escape(Ext.getDom("q_card_num").value);
    var input_operatorCode = escape(Ext.getDom("input_operatorCode").value);
	ccService.getCustInfo(q_cust_name,q_card_num,input_operatorCode,matchCustCallBack);
	q_dialog.hide();
}

function matchCustCallBack(data){
	var custJsonStr = data;
	custJson = eval(custJsonStr);
	if(custJson.length>0){
		DWRUtil.removeAllOptions("cc_cust_name");
		DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");
        //��ʷ�����¼
        //ɾ��������
        var Container = document.getElementById("mybody");
        if(Container.firstChild != null){
            Container.firstChild.removeNode(true);
        }
        ccService.getCallRecords(Ext.util.Format.undef(custJson[0].CUST_ID),'',callInfoCallBack);
		Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
		Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
		Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
		if(Ext.util.Format.undef(custJson[0].CUST_TYPE) == 1){
            Ext.getDom("customer_sex_title").style.display = "";
            Ext.getDom("customer_sex_content").style.display = "";
            Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].SEX_NAME);
        }else{
            Ext.getDom("customer_sex_title").style.display = "none";
            Ext.getDom("customer_sex_content").style.display = "none";
        }
		Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOUCH_TYPE_NAME);
		Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].MOBILE);
		Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BP);
		Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].O_TEL);
		Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].H_TEL);
        Ext.getDom("cc_cust_emial").value = Ext.util.Format.undef(custJson[0].E_MAIL);
		Ext.getDom("cc_cust_id").value = Ext.util.Format.undef(custJson[0].CUST_ID);
        Ext.getDom("cc_post_address").value = Ext.util.Format.undef(custJson[0].POST_ADDRESS);
        Ext.getDom("cc_post_code").value = Ext.util.Format.undef(custJson[0].POST_CODE);
        Ext.getDom("cc_post_address2").value = Ext.util.Format.undef(custJson[0].POST_ADDRESS2);
        Ext.getDom("cc_post_code2").value = Ext.util.Format.undef(custJson[0].POST_CODE2);
        Ext.getDom("cc_manager_man").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN_NAME);
        Ext.getDom("cc_card_type_name").value = Ext.util.Format.undef(custJson[0].CARD_TYPE_NAME);
        Ext.getDom("cc_card_id").value = Ext.util.Format.undef(custJson[0].CARD_ID);
        Ext.getDom("cc_manager_man_id").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN);
        Ext.getDom("cc_extension").value = Ext.util.Format.undef(custJson[0].EXTENSION);
        //Ext.getDom("cc_cust_tel_num").value = Ext.util.Format.undef(custJson[0].H_CUST_TEL);
        Ext.getDom("dd_h_tel").value = Ext.util.Format.undef(custJson[0].H_H_TEL);
        Ext.getDom("dd_o_tel").value = Ext.util.Format.undef(custJson[0].H_O_TEL);
        Ext.getDom("dd_mobile").value = Ext.util.Format.undef(custJson[0].H_MOBILE);
        Ext.getDom("dd_bp").value = Ext.util.Format.undef(custJson[0].BP);
        //�ֶ����ҿͻ���ʾ����û��ƥ���Ͽͻ�
        Ext.getDom("update_cust_tel").style.display = "";
        //��ʾת�ӿͻ�����
        Ext.getDom("zj_server_man").style.display = "";
	}else{
        DWRUtil.removeAllOptions("cc_cust_name");
		Ext.getDom("cc_cust_no").value = '';
		Ext.getDom("select_cc_cust_name").value = '';
		Ext.getDom("cc_cust_type").value = '';
		Ext.getDom("cc_cust_grade").value = '';
		Ext.getDom("cc_rg_money").value = '';
		Ext.getDom("cc_zr_money").value = '';
		Ext.getDom("cc_df_money").value = '';
		Ext.getDom("cc_sy_money").value = '';
		Ext.getDom("cc_buy_date").value = '';
        Ext.getDom("cc_manager_man").value = '';
        Ext.getDom("cc_card_type_name").value = '';
        Ext.getDom("cc_card_id").value = '';
        Ext.getDom("cc_manager_man_id").value = "";
        Ext.getDom("cc_extension").value = "";
        //Ext.getDom("cc_cust_tel_num").value = "";
        Ext.getDom("update_cust_tel").style.display = "none";
        Ext.getDom("zj_server_man").style.display = "none";
		Ext.MessageBox.alert('����','û���ҵ�ƥ����û���Ϣ');
	}
}

function refreshCustInfo(){
	var custId = Ext.getDom("cc_cust_name").value;
    var cc_tel = Ext.getDom("cc_tel_num").value;
    //�ͻ���Ϣ
	ccService.getCustInfoById(custId,refreshCustInfoCallBack);
    //��ʷ�����¼
    //ɾ��������
    var Container = document.getElementById("mybody");
    if(Container.firstChild != null){
        Container.firstChild.removeNode(true);
    }
    ccService.getCallRecords(custId,'',callInfoCallBack);
}

function callInfoCallBack(data){
    //ɾ��������
    var Container = document.getElementById("mybody");
    if(Container.firstChild != null){
        Container.firstChild.removeNode(true);
    }
    //�����
    for(var i=0;i<data.length;i++){
        addRow(data[i].Direction,data[i].Content,data[i].CallTimeStr);
    }
}

function addRow(number1,number2,number3){
   var tableObj=document.getElementById("mybody");
   var newRowObj=tableObj.insertRow(tableObj.rows.length); 
   newRowObj.style.setAttribute("border-top","medium none");
   newRowObj.style.setAttribute("padding-left","30px");
   newRowObj.style.setAttribute("height","20px");
   var newColName=newRowObj.insertCell(newRowObj.cells.length); 
   newColName.setAttribute("width","8%"); newColName.setAttribute("align","center");      
   var newColPwd=newRowObj.insertCell(newRowObj.cells.length); 
   newColPwd.setAttribute("width","65%");
   var newColMrk=newRowObj.insertCell(newRowObj.cells.length); 
   newColMrk.setAttribute("width","17%");
    if(number1 == 1)
        number1 = "����";
    else
        number1 = "����";  
   newColName.innerHTML=number1;      
   newColPwd.innerHTML=number2;      
   newColMrk.innerHTML=number3;
}

function refreshCustInfoCallBack(data){
	var custJsonStr = data;
	custJson = eval(custJsonStr);
	if(custJson.length>0){
		//DWRUtil.removeAllOptions("cc_cust_name");//����б�
		//DWRUtil.addOptions("cc_cust_name", custJson, "CUST_ID", "CUST_NAME");//���ѡ�еĿͻ�
		Ext.getDom("cc_cust_no").value = Ext.util.Format.undef(custJson[0].CUST_NO);
		Ext.getDom("select_cc_cust_name").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
		Ext.getDom("cc_cust_type").value = Ext.util.Format.undef(custJson[0].CUST_NAME);
        
        if(Ext.util.Format.undef(custJson[0].CUST_TYPE) == 1){
            Ext.getDom("customer_sex_title").style.display = "";
            Ext.getDom("customer_sex_content").style.display = "";
            Ext.getDom("cc_cust_grade").value = Ext.util.Format.undef(custJson[0].SEX_NAME);
        }else{
            Ext.getDom("customer_sex_title").style.display = "none";
            Ext.getDom("customer_sex_content").style.display = "none";
        }
		Ext.getDom("cc_rg_money").value = Ext.util.Format.undef(custJson[0].TOUCH_TYPE_NAME);
		Ext.getDom("cc_zr_money").value = Ext.util.Format.undef(custJson[0].MOBILE);
		Ext.getDom("cc_df_money").value = Ext.util.Format.undef(custJson[0].BP);
		Ext.getDom("cc_sy_money").value = Ext.util.Format.undef(custJson[0].O_TEL);
		Ext.getDom("cc_buy_date").value = Ext.util.Format.undef(custJson[0].H_TEL);
        Ext.getDom("cc_cust_emial").value = Ext.util.Format.undef(custJson[0].E_MAIL);
        Ext.getDom("cc_cust_id").value = Ext.util.Format.undef(custJson[0].CUST_ID);
        Ext.getDom("cc_post_address").value = Ext.util.Format.undef(custJson[0].POST_ADDRESS);
        Ext.getDom("cc_post_code").value = Ext.util.Format.undef(custJson[0].POST_CODE);
        Ext.getDom("cc_post_address2").value = Ext.util.Format.undef(custJson[0].POST_ADDRESS2);
        Ext.getDom("cc_post_code2").value = Ext.util.Format.undef(custJson[0].POST_CODE2);
        Ext.getDom("cc_manager_man").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN_NAME);
        Ext.getDom("cc_card_type_name").value = Ext.util.Format.undef(custJson[0].CARD_TYPE_NAME);
        Ext.getDom("cc_card_id").value = Ext.util.Format.undef(custJson[0].CARD_ID);
        Ext.getDom("cc_manager_man_id").value = Ext.util.Format.undef(custJson[0].SERVICE_MAN);
        Ext.getDom("cc_extension").value = Ext.util.Format.undef(custJson[0].EXTENSION);
        //Ext.getDom("cc_cust_tel_num").value = Ext.util.Format.undef(custJson[0].CUST_TEL);        
        Ext.getDom("dd_h_tel").value = Ext.util.Format.undef(custJson[0].H_TEL);
        Ext.getDom("dd_o_tel").value = Ext.util.Format.undef(custJson[0].O_TEL);
        Ext.getDom("dd_mobile").value = Ext.util.Format.undef(custJson[0].MOBILE);
        Ext.getDom("dd_bp").value = Ext.util.Format.undef(custJson[0].BP);
        //�ֶ����ҿͻ���ʾ����û��ƥ���Ͽͻ�
        Ext.getDom("update_cust_tel").style.display = "";
        //��ʾת�ӿͻ�����
        Ext.getDom("zj_server_man").style.display = "";
	}else{
		DWRUtil.removeAllOptions("cc_cust_name");
		Ext.getDom("cc_cust_no").value = '';
		Ext.getDom("select_cc_cust_name").value = '';
		Ext.getDom("cc_cust_type").value = '';
		Ext.getDom("cc_cust_grade").value = '';
		Ext.getDom("cc_rg_money").value = '';
		Ext.getDom("cc_zr_money").value = '';
		Ext.getDom("cc_df_money").value = '';
		Ext.getDom("cc_sy_money").value = '';
		Ext.getDom("cc_buy_date").value = '';
        Ext.getDom("cc_manager_man").value = '';
        Ext.getDom("cc_card_type_name").value = '';
        Ext.getDom("cc_card_id").value = '';
        Ext.getDom("cc_manager_man_id").value = "";
        Ext.getDom("cc_extension").value = "";
       // Ext.getDom("cc_cust_tel_num").value = "";
        Ext.getDom("update_cust_tel").style.display = "none";
        Ext.getDom("zj_server_man").style.display = "none";
		Ext.MessageBox.alert('����','û���ҵ�ƥ����û���Ϣ');
	}
}
function showCustDetail(){
	cust_id = Ext.getDom("cc_cust_id").value;
	cust_name = Ext.getDom("select_cc_cust_name").value
	var content = new Ext.ContentPanel(Ext.id(),{autoCreate:true,title: cust_name,closable:true,fitToFrame:true,fitContainer:true,autoScroll:false});
	var contentDiv  = content.getEl().createChild("<iframe frameborder='0' style='width:100%;height:100%;' src='/callcenter/cust_detail_info.jsp?cust_id="+cust_id+"'>");
	dlayout.add('center',content);	
}


