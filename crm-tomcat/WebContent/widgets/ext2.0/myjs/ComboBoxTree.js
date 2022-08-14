
Ext.ux.ComboBoxTree = function(){
	this.treeId = Ext.id()+'-tree';
	
	this.maxHeight = arguments[0].maxHeight || arguments[0].height || this.maxHeight;
	this.tpl = new Ext.Template('<tpl for="."><div style="height:'+this.maxHeight+'px"><div id="'+this.treeId+'"></div></div></tpl>');
	this.store = new Ext.data.SimpleStore({fields:[],data:[[]]});
	this.selectedClass = '';
	this.mode = 'local';
	this.triggerAction = 'all';
	this.onSelect = Ext.emptyFn;
	this.editable = false;
	//all:���н�㶼��ѡ��
	//exceptRoot������㣬�����㶼��ѡ
	//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ��㣩��ѡ
	//leaf��ֻ��Ҷ�ӽ���ѡ 
	
	this.selectNodeModel = arguments[0].selectNodeModel || 'exceptRoot';

	Ext.ux.ComboBoxTree.superclass.constructor.apply(this, arguments);
	Ext.form.ComboBox.superclass.setValue.call(this, invest_type_name);//数据库取的值
}

Ext.extend(Ext.ux.ComboBoxTree,Ext.form.ComboBox, {
	expand : function(){
		Ext.ux.ComboBoxTree.superclass.expand.call(this);
		if(!this.tree.rendered){
			this.tree.height = this.maxHeight;
			this.tree.border=false;
			this.tree.autoScroll=true;
	        if(this.tree.xtype){
				this.tree = Ext.ComponentMgr.create(this.tree, this.tree.xtype);
			}
			this.tree.render(this.treeId);
	        var combox = this;
	        this.tree.on('click',function(node){
	        	var isRoot = (node == combox.tree.getRootNode());
	        	var selModel = combox.selectNodeModel;
	        	var isLeaf = node.isLeaf();
	        	if(isRoot && selModel != 'all'){
	        		return;
	        	}else if(selModel=='folder' && isLeaf){
	        		return;
	        	}else if(selModel=='leaf' && !isLeaf){
	        		return;
	        	}
	        	combox.setValue(node);
	        	combox.collapse();
	        });
			var root = this.tree.getRootNode();
			if(!root.isLoaded())
				root.reload();
		}
    },
    
	setValue : function(node){
        var text = node.text;
        this.lastSelectionText = text;
        if(this.hiddenField){
            this.hiddenField.value = node.id;
        }
        Ext.form.ComboBox.superclass.setValue.call(this, text);
		intrustType_Flag = '1';
        this.value = node.id;
        if(this.onNodeSelect)
        	this.onNodeSelect.apply(this, [node]);
    },
    
    initSetValue: function(text){
    	Ext.form.ComboBox.superclass.setValue.call(this, text);
    	this.lastSelectionText = text;
    },
    
    getValue : function(){
    	return typeof this.value != 'undefined' ? this.value : '';
    }
});

Ext.reg('combotree', Ext.ux.ComboBoxTree);