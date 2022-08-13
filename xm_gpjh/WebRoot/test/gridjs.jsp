<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>
//继承ext的PagingToolbar
Ext.PagingToolbarEx = Ext.extend(Ext.PagingToolbar, {

    doLoad : function(start){
		this.store.load({params:{start:start,limit:this.pageSize<s:property value="getGridConfig().getFieldsFilter()"/>}});
    }
});
Ext.reg('paging', Ext.PagingToolbarEx);

Ext.onReady(function(){

	//var servletPath = '<s:property value="getServletPath()" />'
	//Ext.state.Manager.setProvider(  
	//       new Ext.state.WhatyCookieProvider()  
	//);
	
	var g_start = <s:property value="getStart()"/>;
	var g_limit = <s:property value="getLimit()"/>;
	var g_page = 1;
	var g_total = 0;
	
	Ext.QuickTips.init();

	// turn on validation errors beside the field globally
	Ext.form.Field.prototype.msgTarget = 'side';
	
    // create the Data Store
    var store = new Ext.data.Store({
        // load using script tags for cross domain, if the data in on
		// the same domain as
        // this page, an HttpProxy would be better
        proxy: new Ext.data.HttpProxy({
            url: '<s:property value="getListAction()" escape="false"/>'
        }),

        // create reader that reads the Topic records
        reader: new Ext.data.JsonReader({
            root: 'models',
            totalProperty: 'totalCount',
            id: 'id',
            fields: [
            	<s:property value="gridConfig.getExtFields()"/>
            ]
        }),

        // turn on remote sorting
        remoteSort: true
    });
    store.setDefaultSort('id', 'desc');
    
    store.on('beforeload',function(){
	});

	// store 加载成功
	store.on('load',function(){
		g_total = store.getTotalCount();
		
		if (g_total == 0)
			Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="test.noMachingRecord"/>');
	});
	
	// store 加载失败
    store.on('loadexception',function(){
    	Ext.MessageBox.alert('<s:text name="test.error"/>', '<s:text name="test.loadexception"/>');
	});
	
	var typeData = new Ext.data.SimpleStore({
        fields: ['val', 'type', 'tip'],
        //data : Ext.exampledata.states // from states.js
        data : [
        	['XLS', 'Excel <s:text name="test.currentpage" />', ''],['XLSALL','Excel <s:text name="test.allpage" />','']
        ]
    });
    var typeCombo = new Ext.form.ComboBox({
        store: typeData,
        valueField: 'val',
        displayField:'type',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        editable: false,
        selectOnFocus:true
    });
    typeCombo.on("select",function(){
    				Ext.MessageBox.confirm('<s:text name="entity.confirmBox" />', 
		    	    '<s:text name="entity.export" />'+typeCombo.value, 
		    	    function(btn) {
			    	     if(btn == 'yes')
				         {	
							var tmp = "<form target='_blank' action='<s:property value="getExcelAction()"/>&format=" + typeCombo.value + "&date=" + (new Date()).getTime() + "&start=" + pagingbar.cursor + "&limit=" + g_limit + "&sort=" + store.getSortState().field + "&dir=" + store.getSortState().direction + "' method='post' name='exportExcel'>" + "<s:property value="getGridConfig().getExcelFiledsFilter()" escape="false"/>" + "</form>"; 
				         	exportexcel.innerHTML=tmp;
				         	document.exportExcel.submit();
						 }
					});
					});
    var pageData = new Ext.data.SimpleStore({
        fields: ['val', 'type', 'tip'],
        //data : Ext.exampledata.states // from states.js
        data : [
        	['5', '5', ''],
        	['10', '10', ''],
        	['20', '20', ''],
        	['50', '50', ''],
        	['100', '100', ''],
        	['1000', '1000', ''],
        	['1000000', '<s:text name="test.all"/>', '']
        ]
    });
    var pageCombo = new Ext.form.ComboBox({
        width:80,
        store: pageData,
        valueField: 'val',
        displayField:'type',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        editable: false,
        value: g_limit,
        emptyText: g_limit
   });
   pageCombo.on("select",function(){
   		g_limit = pageCombo.value;
   		pagingbar.pageSize = 1 * g_limit;
   		store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
   });

	/*
	// 页码跳转combobox
    var pagejumpData = new Ext.data.SimpleStore({
        fields: ['val'],
        //data : Ext.exampledata.states // from states.js
        data : [
        	['1'],
        	['2'],
        	['3']
        ]
    });
    var pagejumpCombo = new Ext.form.ComboBox({
        width:80,
        store: pagejumpData,
        valueField: 'val',
        displayField:'val',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        value: g_page,
        emptyText: g_page
   });
   pagejumpCombo.on("select",function(){
   		g_page = pagejumpCombo.value;
   		g_start = (g_page - 1) * g_limit;
   		store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
   });
   */

	<s:property value="gridConfig.getExtHighlightKeywordFunctions()" escape="false"/>
	<s:property value="gridConfig.getExtRenderFunctions()" escape="false"/>
	
    // the column model has information about grid columns
    // dataIndex maps the column to the specific data field in
    // the data store
    var cm = new Ext.grid.ColumnModel([
    		<s:if test="gridConfig.isShowCheckBox()==true">
	    		new Ext.grid.CheckboxSelectionModel(),
    		</s:if>
    		<s:property value="gridConfig.getExtColumnModel()"/>
    		<s:property value="gridConfig.getExtRenderColumns()"/>
        ]);

    // by default columns are sortable
    cm.defaultSortable = true;
	
    var menubar = [

    	<s:if test="gridConfig.isCanAdd()">
    	'-', {
        text:'<s:text name="entity.add"/>',
        iconCls:'addItem',
        handler:openAddModelWin 
        },
        </s:if>
        <s:if test="gridConfig.isCanBatchAdd()">
    	'-', {
        text:'<s:text name="entity.batchadd"/>',
        iconCls:'addItem',
        handler:openBatchAddModelWin
        },
        </s:if>
        <s:if test="gridConfig.isCanDelete()">
    	'-', {
        text:'<s:text name="entity.delete"/>',
        iconCls:'remove',
        handler: deleteModels
    	},
    	</s:if>
    	<s:if test="gridConfig.isCanExcelUpdate()">
    	'-', {
        text:'<s:property value="gridConfig.updateName"/>',
        iconCls:'updateItem',
        handler:openExcelUpdateModelWin
        },
        </s:if>
    	<s:property value="gridConfig.getExtMenus()"/>
    	'->', {
        text:'<s:text name="entity.reSize"/>',
        iconCls:'selfDef',
        handler: resetMenu
        },
    	'-'
    	];
	
	var winW = document.body.clientWidth;
	var winH = document.body.clientHeight;
	var panelW = winW - 30;
	var panelH = winH - 90;
	
	var pagingbar=new Ext.PagingToolbarEx({
            pageSize: g_limit,
            store: store,
            displayInfo: true,
            displayMsg: '<s:text name="entity.display"/> {0} - {1} of {2}',
            emptyMsg: "<s:text name="entity.no"/>",
            items:[
            	'-', '<s:text name="entity.pagesize"/>', pageCombo,
//            	'-', '<s:text name="entity.pagejump"/>', pagejumpCombo,
            	<s:if test="gridConfig.isPreview()">
                '-', {
                pressed: true,
                enableToggle:true,	
                text: '<s:text name="entity.showpreview"/>',
                cls: 'x-btn-text-icon details',
                iconCls: 'swich',
                toggleHandler: toggleDetails
           		},
           		</s:if>
           		'-'
           		<s:if test="gridConfig.isCanExcelExport()">
           		, '<s:text name="entity.export.fileType"/>', typeCombo</s:if>]
        });
 function resetMenu(){
   	 var colcount = 0;
        	for(i=0;i<grid.colModel.config.length;i++)  
	        {
              var cm = grid.colModel.config[i];
              if(cm.dataIndex!=''&&!cm.hidden)  // 判断列标识不为空,并且不是hidden
              {
                  colcount = colcount+1;
              }
	        }
        	if(colcount*100>window.document.body.clientWidth -30){
        		if(colcount*100==grid.getInnerWidth()){
        			resizeWin(window.document.body.clientWidth -14 ,window.document.body.clientHeight -76);
        		}else{
        			resizeWin(colcount*100 ,window.document.body.clientHeight -90);
        		}
	        }else{
	        	Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="entity.reSizeAlert"/>');
	        }
	  	
	   }
    var grid = new Ext.grid.GridPanel({
        el:'model-grid',
        width:panelW,
        height:panelH,
        store: store,
        cm: cm,
        trackMouseOver:true,
        sm: new Ext.grid.CheckboxSelectionModel(),
        loadMask: true,
        viewConfig: {
            forceFit:true
            <s:if test="gridConfig.isPreview()">
            ,
            enableRowBody:true,	//Preview function
            showPreview:true,
            getRowClass : function(record, rowIndex, p, store){
                if(this.showPreview){
                    p.body = '<p style="color: gray; padding-left: 25px">'+record.data.<s:property value="gridConfig.getPreviewDataIndex()"/>+'</p>';
                    return 'x-grid3-row-expanded';
                }
                return 'x-grid3-row-collapsed';
            }
            </s:if>
        },
        tbar:menubar,
        bbar:pagingbar

    });
	
    grid.on('rowdblclick', function(gridPanel, rowIndex, e) {
<s:if test="gridConfig.isCanUpdate()">
		var selectedId = store.data.items[rowIndex].id;  
		new openUpdateModelWin(selectedId);
</s:if>
<s:else>
		Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="test.updateDisabled"/>');
</s:else>
	}); 
   

    function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    }
    

    //以下为和添加、修改、删除有关的定义和方法---------------
	

    //批量添加界面
   function openBatchAddModelWin(btn,pressed){
		/*  window.open('<s:property value="getServletPath()"/>_turnToAbstractBatchAddExcel.action'); */
    
    
    var downloadexcel = new Ext.Action({
        text: '<s:text name="entity.downloadexcel"/>',
        handler: function(){
          window.open('<s:property value="getAddExcelDownloadAction()"/>'); 
        },
        iconCls: 'excelModel'
    });		
    
    
	 	var excelUpload = new Ext.form.TextField({ 
		 fieldLabel: '<s:text name="entity.uploadexcel"/>*',  
		 name: '_upload',  
		 allowBlank:false,
		 regex:new RegExp(/^(.*)(\.xls)$/),
		 regexText:'<s:text name="test.errorExcel"/>',
		inputType:'file',
		anchor: '90%'
		});
						
						
	   	 var formPanel = new Ext.form.FormPanel({
		    frame:true,
	        labelWidth: 100,
	       	defaultType: 'textfield',
			autoScroll:true,
			 fileUpload:true,
	        items:[new Ext.Button(downloadexcel), excelUpload]
	        
	    });
	   	var batchAddModelWin = new Ext.Window({
	       title: '<s:text name="entity.batchadd"/>',
	       width: 525,
	       height: 250,
	       minWidth: 300,
	       minHeight: 250,
	       layout: 'fit',
	       plain:true,
	       bodyStyle:'padding:5px;',
	       buttonAlign:'center',
	       items: formPanel ,

	       buttons: [{
		            text: '<s:text name="entity.save"/>', 
		            handler: function() {
		                // check form value 
		                if (formPanel.form.isValid()) {
			 		        formPanel.form.submit({
			 		         	url:'<s:property value="getAddExcelUploadAction()"/>',
					            waitMsg:'<s:text name="test.inProcessing"/>',

								success: function(form, action) {
								    var responseArray = action.result;
								    if(responseArray.success=='true'){
								    	Ext.MessageBox.alert('<s:text name="test.info"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
									    batchAddModelWin.close();
								    } else {
								    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								    }
								}
					        });                   
		                } else{
							Ext.MessageBox.alert('<s:text name="test.error"/>', '<s:text name="test.inputErrorPrompt"/>');
						}             
			        }
		        },{
		            text: '<s:text name="entity.cancel"/>',
		            handler: function(){batchAddModelWin.close();}
		        }]
	       
	       });
	       batchAddModelWin.show();
	    					
    }
    //打开添加界面
    function openAddModelWin(btn,pressed){

	   	 <s:property value="gridConfig.getExtAddVars()"/>
	   	 
	   	 var formPanel = new Ext.form.FormPanel({
		    frame:true,
	        labelWidth: 100,
	       	defaultType: 'textfield',
			autoScroll:true,
			fileUpload:true,
	        items: [
	        <s:property value="gridConfig.getExtAddItems()"/>
			]
	    });
	   	
	   	var addModelWin = new Ext.Window({
	       title: '<s:text name="test.addItem"/>',
	       <s:if test="gridConfig.isContainFCKEditor()">
	       width: 700,
	       height: 450,
	       </s:if>
	       <s:else>
	       width: 525,
	       height: 325,
	       </s:else>
	       minWidth: 300,
	       minHeight: 250,
	       layout: 'fit',
	       plain:true,
	       bodyStyle:'padding:5px;',
	       buttonAlign:'center',
	       items: formPanel,
	       buttons: [{
		            text: '<s:text name="entity.save"/>', 
		            handler: function() {
		                 <s:if test="gridConfig.isContainFCKEditor()">
		                 Ext.get('<s:property value="gridConfig.getEntityMemberName()"/>.<s:property value="gridConfig.getExtAddItems().substring(gridConfig.getExtAddItems().lastIndexOf(',')+1)"/>').dom.value=editorInstance.GetXHTML( true );
		                 </s:if>
		                // check form value 
		                if (formPanel.form.isValid()) {
			 		        formPanel.form.submit({
			 		        	url:'<s:property value="getAddAction()"/>',
					            waitMsg:'<s:text name="test.inProcessing"/>',

								success: function(form, action) {
								    var responseArray = action.result;
								    if(responseArray.success=='true'){
								    	Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="test.add"/><s:text name="test.success"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
								    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
									    addModelWin.close();
								    } else {
								    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								    }
								}
					        });                   
		                } else{
							Ext.MessageBox.alert('<s:text name="test.error"/>', '<s:text name="test.inputErrorPrompt"/>');
						}             
			        }
		        },{
		            text: '<s:text name="entity.cancel"/>',
		            handler: function(){addModelWin.close();}
		        }]
	       
	       });
	       addModelWin.show();
	       
	       <s:if test="gridConfig.isContainFCKEditor()">
	        var oFCKeditor = new FCKeditor( '<s:property value="gridConfig.getEntityMemberName()"/>.<s:property value="gridConfig.getExtAddItems().substring(gridConfig.getExtAddItems().lastIndexOf(',')+1)"/>' ) ; 
oFCKeditor.Height = 400 ; 
oFCKeditor.Width  = 650 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 

           </s:if>
           
    }
    
    //打开修改界面
    function openUpdateModelWin(selectedId){

		<s:property value="gridConfig.getExtAddVars()"/>
		 
		var formPanel = new Ext.form.FormPanel({
			
			labelWidth: 100,
			bodyStyle: 'padding:5px',
			autoScroll: true,
			<s:if test="gridConfig.isContainFile()">
			 fileUpload:true,
			</s:if>
			frame:true,
			reader: new Ext.data.JsonReader(
			{root: 'models'},[<s:property value="gridConfig.getExtUpdateFields()"/>]
			),
			items:[
				<s:property value="gridConfig.getExtAddItems()"/>
			]
		});
	
		formPanel.form.load({url:'<s:property value="getDetailAction()"/>&bean.id='+selectedId,waitMsg:'Loading'});
		
		var updateModelWin = new Ext.Window({
	       title: '<s:text name="test.editItem"/>',
	       <s:if test="gridConfig.isContainFCKEditor()">
	       width: 700,
	       height: 450,
	       </s:if>
	       <s:else>
	       width: 525,
	       height: 325,
	       </s:else>
	       minWidth: 300,
	       minHeight: 250,
	       layout: 'fit',
	       plain:true,
	       bodyStyle:'padding:5px;',
	       buttonAlign:'center',
	       items: formPanel,
	
	       buttons: [{
	           text: '<s:text name="entity.save"/>', 
	           handler: function() {
	               <s:if test="gridConfig.isContainFCKEditor()">
		                 Ext.get('<s:property value="gridConfig.getEntityMemberName()"/>.<s:property value="gridConfig.getExtAddItems().substring(gridConfig.getExtAddItems().lastIndexOf(',')+1)"/>').dom.value=editorInstance.GetXHTML( true );
		           </s:if>
	               // check form value 
	               if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({		
	 		            url:'<s:property value="getUpdateAction()"/>',
		                params:{'bean.id': selectedId},	
		                method:'post',      
			            waitMsg:'<s:text name="test.inProcessing"/>',

						success: function(form, action) {
						    var responseArray = action.result;
						    if(responseArray.success=='true'){
						    	Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="test.save"/><s:text name="test.success"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
						    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
							    updateModelWin.close();
						    } else {
						    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						    }
						}
			        });                   
	               } else{
					Ext.MessageBox.alert('<s:text name="test.error"/>', '<s:text name="test.inputErrorPrompt"/>');
				}             
	        }
	       },{
	           text: '<s:text name="entity.cancel"/>',
	           handler: function(){updateModelWin.close();}
	       }]
	   });
	   
	   <s:if test="gridConfig.isContainFCKEditor()">
	   updateModelWin.on('show',function(){
	   		 // create the Data Store
            var store2 = new Ext.data.Store({
             // load using script tags for cross domain, if the data in on
		     // the same domain as
             // this page, an HttpProxy would be better
            proxy: new Ext.data.HttpProxy({
                url: '<s:property value="getDetailAction()"/>?bean.id='+selectedId
             }),

             // create reader that reads the Topic records
            reader: new Ext.data.JsonReader({
               root: 'models',
               totalProperty: 'totalCount',
               id: 'id',
               fields: [
            	   <s:property value="gridConfig.getExtAddItems()"/>
               ]
           }),

           // turn on remote sorting
           remoteSort: true
           });

          // trigger the data store load
          store2.load();
    
          store2.on('load',function(_store,_record,_options){
    	      var con = _record[0].get('<s:property value="gridConfig.getEntityMemberName()"/>.<s:property value="gridConfig.getExtAddItems().substring(gridConfig.getExtAddItems().lastIndexOf(',')+1)"/>');
    	      editorInstance.SetHTML(con);
		
           });
    
    
	   
	   });
	   </s:if>
	   updateModelWin.show();
	   
	    <s:if test="gridConfig.isContainFCKEditor()">
	        var oFCKeditor = new FCKeditor( '<s:property value="gridConfig.getEntityMemberName()"/>.<s:property value="gridConfig.getExtAddItems().substring(gridConfig.getExtAddItems().lastIndexOf(',')+1)"/>' ) ; 
oFCKeditor.Height = 400 ; 
oFCKeditor.Width  = 650 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 

           </s:if>
    }
    
   //替换学员
   function openReplaceTrainingWin(selectedId){

		 <s:property value="gridConfig.getExtAddVars()"/>
	   	 
	   	 var formPanel = new Ext.form.FormPanel({
		    frame:true,
	        labelWidth: 100,
	       	defaultType: 'textfield',
			autoScroll:true,
			fileUpload:true,
	        items: [
	        <s:property value="gridConfig.getExtAddItems()"/>
			]
	    });
	   	
	   	var addModelWin = new Ext.Window({
	       title: '<s:text name="test.replaceTrainee"/>',
	       width: 525,
	       height: 325,
	       minWidth: 300,
	       minHeight: 250,
	       layout: 'fit',
	       plain:true,
	       bodyStyle:'padding:5px;',
	       buttonAlign:'center',
	       items: formPanel,
	       buttons: [{
		            text: '<s:text name="entity.save"/>', 
		            handler: function() {
		                
		                if (formPanel.form.isValid()) {
			 		        formPanel.form.submit({
			 		        	url:'<s:property value="getAddAction()"/>',
			 		        	params:{'bean.id': selectedId},	
					            waitMsg:'<s:text name="test.inProcessing"/>',

								success: function(form, action) {
								    var responseArray = action.result;
								    if(responseArray.success=='true'){
								    	Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="test.add"/><s:text name="test.success"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
								    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
									    addModelWin.close();
								    } else {
								    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								    }
								}
					        });                   
		                } else{
							Ext.MessageBox.alert('<s:text name="test.error"/>', '<s:text name="test.inputErrorPrompt"/>');
						}             
			        }
		        },{
		            text: '<s:text name="entity.cancel"/>',
		            handler: function(){addModelWin.close();}
		        }]
	       
	       });
	       addModelWin.show();
    }
   
   
   
   //excel更新
   function openExcelUpdateModelWin(){
   					
		var m = grid.getSelections();
	    if(m.length > 0)
	    {
						var jsonData = "";
				        for(var i = 0; i < m.length; i++){        		
							var ss =  m[i].get("id");
							if(i==0){
				           		jsonData = jsonData + ss ;
				           } else {
								jsonData = jsonData + "," + ss;	
							}
												
				        }	
				        jsonData=jsonData+",";	                                                                                                   
					   	        				        
   var downloadexcel = new Ext.Action({
        text: '<s:text name="entity.downloadexcel"/>',
        handler: function(){
					var tmp = "<form target='_blank' action='<s:property value="getUpdateExcelDownloadAction()"/>' method='post' name='exportExcel'>" + " <input name='ids' type='hidden' value='" + jsonData + "' />" + "</form>"; 
				         	exportexcel.innerHTML=tmp;
				         	document.exportExcel.submit();               				         	
        },
        iconCls: 'excelModel'
    });		
	  	 				var excelUpload = new Ext.form.TextField({ 
						 fieldLabel: '<s:text name="entity.uploadexcel"/>*',  
						 name: '_upload',  
						 allowBlank:false,
						 regex:new RegExp(/^(.*)(\.xls)$/),
						 regexText:'<s:text name="test.errorExcel"/>',
						inputType:'file',
						anchor: '90%'
						});
	   	 var formPanel = new Ext.form.FormPanel({
		    frame:true,
	        labelWidth: 100,
	       	defaultType: 'textfield',
			autoScroll:true,
			 fileUpload:true,
	        items:[new Ext.Button(downloadexcel), excelUpload]
	        
	    });
	   	var batchAddModelWin = new Ext.Window({
	       //title: '<s:text name="entity.excelUpdate"/>',
	        title: '<s:property value="gridConfig.updateName"/>',
	       width: 525,
	       height: 250,
	       minWidth: 300,
	       minHeight: 250,
	       layout: 'fit',
	       plain:true,
	       bodyStyle:'padding:5px;',
	       buttonAlign:'center',
	       items: formPanel ,

	       buttons: [{
		            text: '<s:text name="entity.save"/>', 
		            handler: function() {
		                // check form value 
		                if (formPanel.form.isValid()) {
			 		        formPanel.form.submit({
			 		        	url:'<s:property value="getUpdateExcelUploadAction()"/>',
					            waitMsg:'<s:text name="test.inProcessing"/>',

								success: function(form, action) {
								    var responseArray = action.result;
								    if(responseArray.success=='true'){
								    	Ext.MessageBox.alert('<s:text name="test.info"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
									    batchAddModelWin.close();
								    } else {
								    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
								    }
								}
					        });                   
		                } else{
							Ext.MessageBox.alert('<s:text name="test.error"/>', '<s:text name="test.inputErrorPrompt"/>');
						}             
			        }
		        },{
		            text: '<s:text name="entity.cancel"/>',
		            handler: function(){batchAddModelWin.close();}
		        }]
	       
	       });
	       batchAddModelWin.show();				        
	    
	    } else {
	    	Ext.MessageBox.alert('<s:text name="test.error"/>', 
	    	    '<s:text name="test.pleaseSelectAtLeastOneItem"/>'
	    	);
	    } 	    
   } 	    
    //删除
    function deleteModels(){
					
		var m = grid.getSelections();
	    if(m.length > 0)
	    {
	    	Ext.MessageBox.confirm('<s:text name="test.confirm"/>', 
	    	    '<s:text name="test.deleteConfirm"/>', 
	    	    function(btn) {
		    	     if(btn == 'yes')
			         {	
						var jsonData = "";
				        for(var i = 0, len = m.length; i < len; i++){        		
							var ss =  m[i].get("id");
							if(i==0)
				           		jsonData = jsonData + ss ;
						   	else
								jsonData = jsonData + "," + ss;	
							store.remove(m[i]);								
				        }	
				        jsonData=jsonData+",";
						Ext.Ajax.request({
									url:'<s:property value="getDeleteAction()"/>',
									params:{ids:jsonData},
							        method:'post',
							        waitMsg:'<s:text name="test.inProcessing"/>',
									success: function(response, options) {
									    var responseArray = Ext.util.JSON.decode(response.responseText);  
									    if(responseArray.success=='true'){
									    	Ext.MessageBox.alert('<s:text name="test.info"/>', '<s:text name="test.delete"/><s:text name="test.success"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
									    } else {
									    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
									    }
								    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
									}
						        });                	
					 
					 }
			    } 
			);	
	    } else {
	    	Ext.MessageBox.alert('<s:text name="test.error"/>', 
	    	    '<s:text name="test.pleaseSelectAtLeastOneItem"/>'
	    	);
	    } 
	}
    
    //GridConfig定义方法
<s:property value="gridConfig.getExtMenuScripts()" escape="false"/>
    
<s:iterator value="gridConfig.getListMenuConfig()" status="count">
<s:if test="getType()==\"function\"">
function GridConfigMenuFunction_<s:property value="#count.index"/>(){
	var m = grid.getSelections();
	if(m.length > 0)
	{
		Ext.MessageBox.confirm('<s:text name="test.confirm"/>', 
			'<s:text name="test.sureTodo"/>',
	    	function(btn) {
			if(btn == 'yes')
			{	
				var jsonData = "";
				for(var i = 0, len = m.length; i < len; i++){        		
					var ss =  m[i].get("id");
					if(i==0)
				        	jsonData = jsonData + ss ;
				   	else
						jsonData = jsonData + "," + ss;	
				        }	
				        jsonData=jsonData+",";
					Ext.Ajax.request({
						url:'<s:property value="getUpdateColumnAction()"/>',
						params:{ids:jsonData,column:'<s:property value="getColumnDataIndex()"/>',value:'<s:property value="getValue()"/>'},
					        method:'post',
					        waitMsg:'<s:text name="test.inProcessing"/>',
							success: function(response, options) {
							    var responseArray = Ext.util.JSON.decode(response.responseText);
							    if(responseArray.success=='true'){
							    	Ext.MessageBox.alert('<s:text name="test.info"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							    } else {
							    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							    }
						    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
							}
					        });
					 }
			    } 
			);	
	    } else {
	    	Ext.MessageBox.alert('<s:text name="test.error"/>', 
	    	    '<s:text name="test.pleaseSelectAtLeastOneItem"/>'
	    	);
		}
	}
</s:if>
<s:elseif test="getType()==\"functionB\"">
function GridConfigMenuFunction_<s:property value="#count.index"/>(){
	var m = grid.getSelections();
	<s:if test="single">
	if(m.length == 1)
	</s:if>
	<s:else>
	if(m.length > 0)
	</s:else>
	{
		Ext.MessageBox.confirm('<s:text name="test.confirm"/>', 
			'<s:text name="test.sureTodo"/>',
	    	function(btn) {
			if(btn == 'yes')
			{	
				var jsonData = "";
				for(var i = 0, len = m.length; i < len; i++){ 
				var checkColumn = '<s:property value="checkColumn"/>';       		
					var ss =  m[i].get(checkColumn);
					if(i==0)
				        	jsonData = jsonData + ss ;
				   	else
						jsonData = jsonData + "," + ss;	
				        }	
				        jsonData=jsonData+",";
				        <s:if test="ajax">
				        Ext.Ajax.request({
						url:'<s:property value="action"/>',
						params:{ids:jsonData},
					        method:'post',
					        waitMsg:'<s:text name="test.inProcessing"/>',
							success: function(response, options) {
							    var responseArray = Ext.util.JSON.decode(response.responseText);
							    if(responseArray.success=='true'){
							    	Ext.MessageBox.alert('<s:text name="test.info"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							    } else {
							    	Ext.MessageBox.alert('<s:text name="test.error"/>', responseArray.info + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							    }
						    	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
							}
					        });
				        </s:if>
				        <s:else> 
				       		document.write("<form action=<s:property value="action"/> method=post target=_blank name=formx1 style='display:none'>"); 
							document.write("<input type=hidden name=ids value='"+jsonData+"'>"); 
							document.write("</form>"); 
							document.formx1.submit(); 
				       		
				        	
				        	
				        </s:else>
					
					 }
			    } 
			);	
	    } else {
	    <s:if test="single">
	    	Ext.MessageBox.alert('<s:text name="test.error"/>', 
	    	    '<s:text name="test.pleaseSelectOneItem"/>'
	    	);
	    </s:if>
	    <s:else>
	    	Ext.MessageBox.alert('<s:text name="test.error"/>', 
	    	    '<s:text name="test.pleaseSelectAtLeastOneItem"/>'
	    	);
	    </s:else>
	    	
		}
	}
</s:elseif>
</s:iterator>

    //搜索
<s:property value="gridConfig.getExtSearchVars()"/>
    
   var s_formPanel =new Ext.FormPanel({
   		buttonAlign:'right',    
   		width:panelW,
        labelAlign: 'left',
        labelWidth: 100,
        frame:true,
        title: '<s:property value="gridConfig.getTitle()" escape="false"/>',
        <s:if test="gridConfig.isCanSearch()">
        collapsible:true,
        </s:if>
        collapsed:true,   //表示列表中搜索展开
        items: [{
            layout:'column',
            items:[
<s:property value="gridConfig.getExtSearchItems()"/>
            ]
        }]
    });
	    
//判断是否需要预搜索
<s:if test="isSearch()">
<s:property value="gridConfig.getExtPreSearchVars()"/>

   	 var search_formPanel = new Ext.form.FormPanel({
	    frame:true,
        labelWidth: 100,
		autoScroll:true,
        items: [
            <s:property value="gridConfig.getExtPreSearchItems()"/>
			],
		keys:[{ //处理键盘回车事件
        	key:Ext.EventObject.ENTER,  
        	fn:function() {
		           	s_formPanel.render("searchtool");
		           	grid.render();
		           	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getPreFieldsFilter()"/>}});
		           	<s:property value="getGridConfig().getCopyPreSearch()"/>
		           	searchWin.close();
		       },  
        	scope:this  
    	}]
	    });

	   	var searchWin = new Ext.Window({
	       title: '<s:property value="gridConfig.getTitle()"/> <s:text name="entity.search"/>',
	       width: 525,
	       height: 325,
	       minWidth: 300,
	       minHeight: 250,
	       layout: 'fit',
	       plain:true,
	       bodyStyle:'padding:5px;',
	       buttonAlign:'center',
	       closable:false,
	       items: search_formPanel,
	       buttons: [{
	            text: '<s:text name="entity.search"/>', 
	            handler: function() {
	            	s_formPanel.render("searchtool");
	            	grid.render();
	            	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getPreFieldsFilter()"/>}});
	            	<s:property value="getGridConfig().getCopyPreSearch()"/>
	            	searchWin.close();
		        }
	        }]
       
       });
       searchWin.show();
</s:if>
<s:else>
	s_formPanel.render("searchtool");
	grid.render();
	store.load({params:{start:g_start,limit:g_limit<s:property value="getGridConfig().getFieldsFilter()"/>}});
</s:else>
	function resizeWin(panelW,panelH){
		if(document.getElementById('searchtool').innerHTML==""){ 
			return ;
		}
	//褰撶獥鍙ｅぇ灏忔敼鍙樻椂,grid,s_formPanel鑷�傚簲澶у皬
	
		
		//panelW = window.document.body.clientWidth -30;
	//	panelH = window.document.body.clientHeight -90;
        grid.setWidth(panelW);
        grid.setHeight(panelH);
        
        var is_collapsed = s_formPanel.collapsed;
        
        <s:property value="gridConfig.getExtSearchValues()"/>  
        
        s_formPanel.destroy();
        <s:property value="gridConfig.getExtSearchVarsWithValue()"/>   
        s_formPanel =new Ext.FormPanel({
	   		buttonAlign:'right',    
	   		width:panelW,
	        labelAlign: 'left',
	        labelWidth: 100,
	        frame:true,
	        title: '<s:property value="gridConfig.getTitle()" escape="false"/>',
	        <s:if test="gridConfig.isCanSearch()">
	        collapsible:true,
	        </s:if>
	        collapsed:is_collapsed,
	        items: [{
	            layout:'column',
	            items:[
				<s:property value="gridConfig.getExtSearchItems()"/>
	            ]
	        }]
    	});
    	s_formPanel.render("searchtool");
	};

});

var editorInstance;   
						/**  
						 * FCKEditor初始化完成将调用此方法  
						 * @param {Object} editorInstance  
						 */  
						function FCKeditor_OnComplete( instance ) {   
						    editorInstance=instance;   
						};  
