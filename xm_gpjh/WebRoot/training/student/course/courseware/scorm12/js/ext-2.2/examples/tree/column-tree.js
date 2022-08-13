/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */


Ext.onReady(function(){
/*
	var win;
	var button = Ext.get('show-btn');
	//构建tree
	var tree = creatTree();
	button.on('click', function(){
		createMask();
    // create the window on the first click and resume on subsequent clicks
		//alert(studyStatusObjStr);
		//构建win
    if(!win){
        win = new Ext.Window({
							applyTo     : 'hello-win',
							layout      : 'fit',
							width       : 736,
							height      : 420,
							closeAction :'hide',
							plain       : true,
							draggable	: true,
							disabled	: false,
							items     : tree
        });
    }
		win.show(button);
		win.on('show',function(){
			tree.root.expand(true,true);
		})
		win.on('beforehide',function(){
			removeMask();
		})
	});
*/
	function creatTree()
	{
		var tempObj = new Ext.tree.ColumnTree({
			width		: 700,
			height		: 420,
			rootVisible	: false,
			autoScroll	: true,
			//title		: '跟踪记录',
			//renderTo	: Ext.getBody(),
			
			columns:[{
				header:'章节',
				width:300,
				dataIndex:'title'
			},{
				header:'学习时长',
				width:80,
				dataIndex:'duration',
				renderer:showDuration
			},{
				header:'状态',
				width:80,
				dataIndex:'status',
				renderer:selectStatus
			},{
				header:'得分',
				width:50,
				dataIndex:'score',
				renderer:showScore
			},{
				header:'绩效',
				width:150,
				dataIndex:'effects',
				renderer:showEffects
			},{
				header:'链接',
				width:40,
				dataIndex:'links',
				renderer:getLink
			}],
	
			loader: new Ext.tree.TreeLoader({
				//dataUrl:'column-data.json',
				uiProviders:{
					'col': Ext.tree.ColumnNodeUI
				}
			}),
			
			//指定显示的根位置
			root: new Ext.tree.AsyncTreeNode({ 
				text:'课程学习模块学习记录', 
				expanded:true,
				children: (studyStatusObj && studyStatusObj.children)?studyStatusObj.children:json
				//children: json
			})
    });
		return tempObj;
	}
});

function selectStatus(value)
{
	var notAtt 	= "<img src='/training/student/course/courseware/scorm12/images/completed.gif' style='width:11px;height:11px;margin:0px 6px 0px 2px;vertical-align:middle;'/><span style='color:red;'>未学习</span>"
	var inCplt	= "<img src='/training/student/course/courseware/scorm12/images/incomplet.gif' style='width:11px;height:11px;margin:0px 6px 0px 2px;vertical-align:middle;'/><span style='color:#ff6600;'>未完成</span>"
	var cplted	= "<img src='/training/student/course/courseware/scorm12/images/notAttempted.gif' style='width:11px;height:11px;margin:0px 6px 0px 2px;vertical-align:middle;'/><span style='color:green;'>已完成</span>";
	if (value == 'not attempted')
	{
		return notAtt;
	}
	else if(value == 'incomplete')
	{
		return inCplt;
	}
	else if(value == 'completed')
	{
		return cplted;
	}
	else if(value == "")
	{
		return notAtt;
	}
	else
	{
		return value;
	}
}
function showScore(value)
{
	if(value == null || value == "")
	{
		return "0";
	}else{
		return value;	
	}
}

function showDuration(value)
{
	if (value!=null && value!="null" && value!="")
	{
		if(value.indexOf(".")!=-1)
			return ("<span>"+value.substring(0,value.indexOf("."))+"</span>");
		else
			return ("<span>"+value+"</span>");
	}
	else
	{
		return "00:00:00";
	}
}
function showEffects(value)
{
	if(value && value.indexOf("|")!=-1)
	{
		var a = value.split("|");
		if(a[0]!="null")
		{
			var b = a[0].split(":");
			var c = Number(b[0])*3600+Number(b[1])*60+Number(b[2]);
			var effect = c/Number(a[1]);
			//alert(effect);
		}
		
		if(effect<8 && Number(a[1])>60)
			return "<span style='color:red;'>优秀</span>";
		else if(effect>=8 && effect<30 && Number(a[1])>60)
			return "<span style='color:#ff6600;'>良好</span>";
		else if(effect>=30 && effect<60 && Number(a[1])>60)
			return "<span style='color:#ffcc33;'>一般</span>";
		else if(effect>=60 && Number(a[1])>60)
			return "<span style='color:green;'>较差</span>";
		else
			return "<span style='color:gray;'>基本没有成果，加油哦！</span>";
	}
}

function getLink(value)
{
	if(value && value!="")
	{
		var urls = courseBaseURL+value.substring(value.indexOf("?scoID="),value.length);
		return "<span onmouseout=\"this.style.color=\'#000000\'\" onmouseover=\"this.style.color=\'#ff6600\'\" onclick=\"document.location.href=\'"+ urls +"\'\">跳转</span>";
	}
}



//供测试用，缺少 effects和 links两个字段
//var json = [];
var json = [{
    title:'Unit One Higher Education',
    duration:'3 小时',
    status:'完成76％',
	score:'平均42分',
    uiProvider:'col',
    cls:'master-task',
    iconCls:'task-folder',
    children:[{
        title:'Part One Listen and Imitate',
        duration:'15 分',
        status:'未学习',
		score:'0分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    },{
        title:'Part Two Read and Practice',
        duration:'45 分',
        status:'未完成',
		score:'12分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    },{
        title:'Part Three Write and Produce',
        duration:'30 分',
        status:'已完成',
		score:'85分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    },{
        title:'Part Four Pick up your Grammar',
        duration:'30 分',
        status:'已完成',
		score:'77分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    },{
        title:'Part Five Have a Fun',
        duration:'1 hour',
        status:'已完成',
		score:'73分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    }]
},{
    title:'Unit Two Custom Field Example',
    duration:'2.5 小时',
    status:'完成23％',
	score:'平均42分',
    uiProvider:'col',
    cls:'master-task',
    iconCls:'task-folder',
    children:[{
        title:'Implement "Live Search" on extjs.com from Alex',
        duration:'1 小时',
        status:'已完成',
		score:'92分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    },{
        title:'Extend TwinTrigger',
        duration:'30 分',
        status:'未学习',
		score:'0分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    },{
        title:'Testing and debugging',
        duration:'1 小时',
        status:'未学习',
		score:'0分',
        uiProvider:'col',
        leaf:true,
        iconCls:'task'
    }]
}]
