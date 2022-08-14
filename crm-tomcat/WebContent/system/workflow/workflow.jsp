<%@ page contentType="text/html; charset=GBK"  import="java.util.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.workflow.*,enfo.crm.web.*,enfo.crm.vo.*" %>
<%@ include file="/includes/operator.inc" %>
<%

try{
String flow_no = Utility.trimNull(request.getParameter("flow_no"));
//String flow_name = Utility.trimNull(request.getParameter("flow_name"));
String flow_name = ConfigUtil.getPropertyNameById("FLOW_CATALOG","flow_name","flow_no",flow_no);
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
System.out.println("project_id=" + flow_no +";  sPage=" + sPage +";  sPagesize="+ sPagesize );

ImportFlowWorkLocal fwlocal = EJBFactory.getImportFlowWork();	
//String jsonStr = Utility.trimNull(local.list_WorkFlow(project_id));
//去掉字符串中的回车符
String jsonStr = Utility.replaceAll(fwlocal.list_WorkFlow(flow_no),System.getProperty("line.separator"),"");

List rsList = fwlocal.list_nodes_disabled(Utility.trimNull(flow_no));

String disNodStr = "";
int i = 0;

Iterator iter= rsList.iterator();

while(iter.hasNext()){
	Map disNodMap = (Map)iter.next();
	disNodStr += Utility.trimNull(disNodMap.get("CODE")) + "β";
	i++;
}

%>
<HTML xmlns:v="urn:schemas-microsoft-com:vml">
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<style type="text/css">
v\:*{BEHAVIOR:url(#default#VML)}body{margin:0,0,0,0;}
#group{position:absolute;top:0px;left:0px;height:100%;width:100%;z-index:-1000;}
#menu table{font-size:11px;background-image:url(img/menubg.gif);z-index:1000;height:100%;}
#menu td{white-space:nowrap;}
#movebar{font-size:11px;z-index:1000;cursor:move;height:30;width:8;}
#movebar div{height:30;width:8;background-image:url(img/grid-blue-split.gif);}
#menu img{vertical-align:middle;height:16px;}
#menu span{vertical-align:bottom;cursor:hand;padding-left:3px;padding-top:5px;padding-bottom:2px;padding-right:5;}
.x-window{}.x-window-tc{background:transparent url(img/window/top-bottom.png) repeat-x 0 0;overflow:hidden;zoom:1;height:20px;width:300px;padding-top:5px;font-size:12px;cursor:move;}
.x-window-tl{background:transparent url(img/window/left-corners.png) no-repeat 0 0;padding-left:6px;zoom:1;z-index:1;position:relative;}
.x-window-tr{background:transparent url(img/window/right-corners.png) no-repeat right 0;padding-right:6px;}
.x-window-bc{background:transparent url(img/window/top-bottom.png) repeat-x 0 bottom;zoom:1;text-align:center;height:30px;width:300px;padding-top:4px;}
.x-window-bl{background:transparent url(img/window/left-corners.png) no-repeat 0 bottom;padding-left:6px;zoom:1;}
.x-window-br{background:transparent url(img/window/right-corners.png) no-repeat right bottom;padding-right:6px;zoom:1;}
.x-window-mc{border:1px solid #99bbe8;padding:0;margin:0;font:normal 11px tahoma,arial,helvetica,sans-serif;background:#dfe8f6;width:300px;height:350px;}
.x-window-ml{background:transparent url(img/window/left-right.png) repeat-y 0 0;padding-left:6px;zoom:1;}
.x-window-mr{background:transparent url(img/window/left-right.png) repeat-y right 0;padding-right:6px;zoom:1;}
.x-tool-close{overflow:hidden;width:15px;height:15px;float:right;cursor:pointer;background:transparent url(img/window/tool-sprites.gif) no-repeat;background-position:0 -0;margin-left:2px;}
.x-tab-panel-header{border:1px solid #8db2e3;padding-bottom:2px;border-top-width:0;border-left-width:0;border-right-width:0;height:26px;}
.x-tab-panel-body{overflow-y:auto;height:100px;display:none;}.x-tab-panel-body-show{overflow-y:auto;height:320px;padding-top:10px;}
.x-tab-strip-top{float:left;width:100%;height:27px;padding-top:1px;background: #cedff5 repeat-x top;}
.x-tab-strip-wrap{width:100%;border-bottom:1px solid #8db2e3;}.x-tab-strip-top ul{list-style:none;margin:0px;padding:0px;}
.x-tab-strip-top li{float:left;display:block;width:100px;padding-left:3px;text-align:center;font-size:11px;}
.x-tab-strip-top .x-tab-left{background:transparent url(img/window/tabs-sprite.gif) no-repeat 0 -51px;padding-left:10px;}
.x-tab-strip-top .x-tab-right{background:transparent url(img/window/tabs-sprite.gif) no-repeat right -351px;padding-right:10px;}
.x-tab-strip-top .x-tab-middle{background:transparent url(img/window/tabs-sprite.gif) repeat-x 0 -201px;height:25px;overflow:hidden;padding-top:5px;cursor:hand;}
.x-tab-strip-top .x-tab-strip-active .x-tab-left{background:transparent url(img/window/tabs-sprite.gif) no-repeat 0 0px;padding-left:10px;}
.x-tab-strip-top .x-tab-strip-active .x-tab-right{background:transparent url(img/window/tabs-sprite.gif) no-repeat right -301px;padding-right:10px;}
.x-tab-strip-top .x-tab-strip-active .x-tab-middle{background:transparent url(img/window/tabs-sprite.gif) repeat-x 0 -151px;height:25px;overflow:hidden;padding-top:5px;font-weight:bold;cursor:hand;}
.x-form-field{font-family:tahoma,arial,helvetica,sans-serif;font-size:12px;font-size-adjust:none;font-stretch:normal;font-style:normal;font-variant:normal;font-weight:normal;line-height:normal;margin:0;background:#FFFFFF repeat-x scroll 0 0;border:1px solid #B5B8C8;padding:1px 3px;width:160;overflow:hidden;}
.btn{BORDER-RIGHT:#7b9ebd 1px solid;PADDING-RIGHT:2px;BORDER-TOP:#7b9ebd 1px solid;PADDING-LEFT:2px;FONT-SIZE:12px;FILTER:progid:DXImageTransform.Microsoft.Gradient(GradientType=0,StartColorStr=#ffffff,EndColorStr=#cecfde);BORDER-LEFT:#7b9ebd 1px solid;CURSOR:hand;COLOR:black;PADDING-TOP:2px;BORDER-BOTTOM:#7b9ebd 1px solid}
</style>
<SCRIPT LANGUAGE="javascript" SRC="/includes/workflow.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/checkService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script language="javascript">
var MenuAction={
    open:function(){},
    save:function getVmlObj(){
    	var projectID = '<%=flow_no%>';
    	//设置记录状态点编号的字符串
    	var nodesIDStr = "";
		//获取状态点
		var jsonStrNodeSig = new Array();
		var nodesObj = g.nodes;
		
		if(nodesObj != null && nodesObj.length != 0){
			var addflag = 0;
			for(i=0;i<nodesObj.length;i++){
				jsonStrNodeSig[i] = nodesObj[i].id + "β" + nodesObj[i].name + "β" + nodesObj[i].type + "β" + nodesObj[i].shape + "β" + nodesObj[i].number + "β" + nodesObj[i].left + "β" + nodesObj[i].top + "β" + nodesObj[i].width + "β" + nodesObj[i].height + "β";	
				nodesIDStr += nodesObj[i].id + "β";
				var nodeProperty = new Array();
				var nodePropertyStr = "";
				if(nodesObj[i].property == null){
					nodePropertyStr += "null";
				}else{
					for(j=0;j<nodesObj[i].property.length;j++){
						if(nodesObj[i].property[j] == null){
							sl_alert("状态点" + nodesObj[i].name + "必选属性未选择");
							return false;
						}
						nodeProperty[j] = '{"id":"' + nodesObj[i].property[j].id + '","text":"' + nodesObj[i].property[j].text + '","value":"' + nodesObj[i].property[j].value + '"}';
						switch(nodesObj[i].property[j].id){
			               	case 'n_p_didea':{//处理方式
			                	if(nodesObj[i].property[j].value != null && nodesObj[i].property[j].value != ''){
				                    jsonStrNodeSig[i] += nodesObj[i].property[j].value + "β";
				                }else{
				                	sl_alert("状态点" + nodesObj[i].name + "必选属性处理方式未选择");
				                	return false;				                	
				                }
								break;
			                }default:jsonStrNodeSig[i] += "";
								break;
			            }
			            
					}
					//alert(nodesObj[i].property.length);
					if(nodesObj[i].property.length == 6 ) jsonStrNodeSig[i] += "0"+"β";//新增的排列序号,针对之前没有此属性的状态点
					for(j=0;nodesObj[i].property.length != 1 && j<nodesObj[i].property.length-1;j++){
						nodePropertyStr += nodeProperty[j] + ',';
					}
					
					nodePropertyStr += nodeProperty[nodesObj[i].property.length-1];
					//alert( nodePropertyStr);
				}
				jsonStrNodeSig[i] += nodePropertyStr + "β";
				jsonStrNodeSig[i] = escape(jsonStrNodeSig[i]);
			}
		}
		//记录状态点连线的编号
		var linesIDStr = "";
		//获取状态点的连线
		var jsonStrLineSig = new Array();
		var linesObj = g.lines;

		if(linesObj != null && linesObj.length != 0){
			for(i=0;i<linesObj.length;i++){
				jsonStrLineSig[i] = linesObj[i].id + "β" + linesObj[i].name + "β" + linesObj[i].type + "β" + linesObj[i].shape + "β" + linesObj[i].number + "β" + linesObj[i].fromObj.id + "β" + linesObj[i].toObj.id + "β" + linesObj[i].fromX + "β" + linesObj[i].fromY + "β" + linesObj[i].toX + "β" + linesObj[i].toX + "β" + "[]β";		
				linesIDStr += linesObj[i].id + "β";
				
				var lineProperty = new Array();
				var linePropertyStr = "";
				
				if(linesObj[i].property == null){
					linePropertyStr += "null";
				}else{
					for(j=0;j<linesObj[i].property.length;j++){
						lineProperty[j] = '{"id":"' + linesObj[i].property[j].id + '","text":"' + linesObj[i].property[j].text + '","value":"' + linesObj[i].property[j].value + '"}';

						switch(linesObj[i].property[j].id){
			               	case 'l_p_order':{	//线的排序
			                	if(linesObj[i].property[j].value != null && linesObj[i].property[j].value !=''){
				                    jsonStrLineSig[i] += linesObj[i].property[j].value + "β";
			                    }else{
			                    	jsonStrLineSig[i] += "1" + "β";
			                    }
								break;
			                }case 'l_p_group':{//工作组设置
			                	if(linesObj[i].property[j].value != null && linesObj[i].property[j].value !=''){
				                    jsonStrLineSig[i] += linesObj[i].property[j].value + "β";
			                    }else{
				                    jsonStrLineSig[i] += "NULL" + "β";
									//此处做不控制 20111206
			                    	//sl_alert(linesObj[i].name + "未选择组群");
			                    	//return false;
			                    }
								break;
			                }case 'l_p_line_type':{	//线路类型
			                	if(linesObj[i].property[j].value != null && linesObj[i].property[j].value !=''){
				                    jsonStrLineSig[i] += linesObj[i].property[j].value + "β";
			                    }else{
			                    	jsonStrLineSig[i] += "1" + "β";
			                    }
								break;
			                }default:jsonStrLineSig[i] += "";
								break;
			            }
					}
					for(j=0;linesObj[i].property.length != 1 && j<linesObj[i].property.length-1;j++){

						linePropertyStr += lineProperty[j] + ',';
					}
					linePropertyStr += lineProperty[linesObj[i].property.length-1];
				}
				jsonStrLineSig[i] += linePropertyStr + "β";
				jsonStrLineSig[i] = escape(jsonStrLineSig[i]);
			}
		}
		if(jsonStrNodeSig ==null){
			sl_alert("不存在状态点，无法保存");
			return false;
		}else if(jsonStrNodeSig.length == 0){
			sl_alert("不存在状态点，无法保存");
			return false;
		}
		if(jsonStrLineSig ==null){
			sl_alert("不存在连线，无法保存");
			return false;
		}else if(jsonStrLineSig.length == 0){
			sl_alert("不存在连线，无法保存");
			return false;
		}
		nodesIDStr = escape(nodesIDStr);
		linesIDStr = escape(linesIDStr);


		checkService.setWorkFlowStr('<%=flow_no%>',jsonStrNodeSig,jsonStrLineSig,nodesIDStr,linesIDStr,<%=input_operatorCode%>,dwrCallbackWorkFlow);
		DWREngine.setErrorHandler(dwrErrorHandler);
	},
    start:function(){
        var n=new NodeOval();
		n.init();
    },
    end:function(){
        var n=new NodeOval();
		n.setType('end');
		n.init();
    },
    nodeRect:function(){
        var n=new Node();
		n.init();
		n.setProperty("node");
    },
    nodeImg:function(){
        var n=new NodeImg();
		n.init();
    },
    line:function(){
    	
        var group=document.getElementById('group');
		var Love=group.getAttribute('bindClass');
		if(Love.lineFlag==null){
            Love.lineFlag='line';
        }else if(Love.lineFlag=='line'){
            Love.lineFlag=null;
        }else{
            return false;
        }
		return true;
    },
    polyline:function(){
        var group=document.getElementById('group');
		var Love=group.getAttribute('bindClass');
		if(Love.lineFlag==null){
            Love.lineFlag='polyline';
        }else if(Love.lineFlag=='polyline'){
            Love.lineFlag=null;
        }else{
            return false;
        }
		return true;
    },
    remove:function(){
        var group=document.getElementById('group');
		var Love=group.getAttribute('bindClass');
		Love.removeSelected();
    },back:function(){
        location = "/system/newpro/flowtype.jsp" ;
    },addGroup:function(){
        window.open('../projectworkgroups.jsp?project_id=<%=flow_no%>', '','scrollbars=yes,resizable=no,left=140,top=100,height=500px,width=680px');
    },setPageGroup:function(){
        window.open('../setPageWorkgroups.jsp?project_id=<%=flow_no%>', '','scrollbars=yes,resizable=no,left=140,top=100,height=500px,width=680px');
    },grid:function(){
        var obj=document.body.style.backgroundImage;
		if(obj=='')
			document.body.style.backgroundImage='url(img/bg.jpg)';
		else 
			document.body.style.backgroundImage='';
    },
    changeStyle:function(obj){
        if(obj.style.color=='')
			obj.style.color='#ffffff';
		else 
			obj.style.color='';
    },
    over:function(obj){
        obj.style.backgroundColor='#A8D0F9';
    },
    out:function(obj){
        obj.style.backgroundColor='';
    }
}

var Prop={
    nodes:[[{
        subject:'状态编号',
        id:'n_p_id',
        text:'span'
    },{
        subject:'*节点类型',
        id:'n_p_type',
        text:'span'
    },{
        subject:'*节点名称',
        id:'n_p_name',
        text:'input'
    },{
       subject:'复杂信息设置',
        id:'n_p_relation_btn',
        text:'span',
        btn:{
            click:	function getFront_state(){
            			var flnodeUrl = "/system/workflow/wkflownode_view.jsp?flow_no=<%=flow_no%>&flow_name=<%=flow_name%>";
                    	flnodeUrl = flnodeUrl + "&node_code="+document.getElementById('n_p_id').innerHTML;
                    	flnodeUrl = flnodeUrl + "&node_name="+document.getElementById('n_p_name').value;
                    	var node_flag = document.getElementById('n_p_type').innerHTML;
                    	if(node_flag=='node'){
                    		node_flag = 'middle';
                    	}
                    	flnodeUrl = flnodeUrl + "&node_flag="+node_flag;
                    	var ret = showModalDialog(flnodeUrl, "", "dialogWidth:900px;dialogHeight:400px;status:0;help:0");
                    	if(ret != null && ret != 0){
							//var n_p_relation_no = document.getElementById('n_p_relation_no');
							//l_p_front_state.value = ret;
						}
            		},
            hide:true
        }
    }]],
    lines:[[{
        subject:'路径编号',
        id:'l_p_id',
        text:'span'
    },{
        subject:'*路径名称',
        id:'l_p_name',
        text:'input'
    },{
        subject:'上一状态编号',
        id:'l_p_pre_no',
        text:'span'
    },{
        subject:'上一状态名称',
        id:'l_p_pre',
        text:'span'
    },{
        subject:'下一状态编号',
        id:'l_p_next_no',
        text:'span'
    },{
        subject:'下一状态名称',
        id:'l_p_next',
        text:'span'
    },{
       	subject:'详细设置',
        id:'l_p_drive_btn',
        text:'span',
        btn:{
            click:	function getFront_state(){
            			var fldriveUrl = "/system/workflow/wkflowdrive_info.jsp?flow_no=<%=flow_no%>&flow_name=<%=flow_name%>";
            			fldriveUrl = fldriveUrl + "&drive_code="+document.getElementById('l_p_id').innerHTML;
                    	fldriveUrl = fldriveUrl + "&from_node_code="+document.getElementById('l_p_pre_no').innerHTML;
                    	fldriveUrl = fldriveUrl + "&node_name="+document.getElementById('l_p_pre').innerHTML;
                    	fldriveUrl = fldriveUrl + "&to_node_code="+document.getElementById('l_p_next_no').innerHTML;
                    	fldriveUrl = fldriveUrl + "&next_node_name="+document.getElementById('l_p_next').innerHTML;
                    	fldriveUrl = fldriveUrl + '&interfacetype_code=WorkFlowDrive010&table_code=FLOW_DRIVE';
                    	var ret = showModalDialog(fldriveUrl, "", "dialogWidth:600px;dialogHeight:450px;status:0;help:0");
                    	if(ret != null && ret != 0){
							//var n_p_relation_no = document.getElementById('n_p_relation_no');
							//l_p_front_state.value = ret;
						}
            		},
            hide:true
        }
    }]
	/*,[{
        subject:'有权工作组',
        id:'l_p_group',
        text:'textarea',
		btn:{
            click:	function getGroupID(){
                    	var ret = showModalDialog("projecttransfergroups_info.jsp?project_id=" + <%=flow_no%>, "", "dialogWidth:360px;dialogHeight:360px;status:0;help:0");
                    	if(ret != null && ret != ''){
	            			var lineObj = document.getElementById("objCode");
	            			var lineObjCode = lineObj.value;
	            			var linesObj = g.lines;
	            			var jsonStrLineSig = new Array();
							var win=document.getElementById('propWin');
							var obj=win.selected;
							if(obj){
							        var type=win.type;
									if(obj){
							        	obj.property=Prop.setProperty(type);
							        }
									obj.name=document.getElementById(type+'_p_name').value;
									if(obj.textObj)
										obj.textObj.innerHTML=obj.name;
									obj.obj.title=obj.name;
							}
							var l_p_group = document.getElementById('l_p_group');
							l_p_group.value = ret;
						}
            		},
            hide:true
        }
    }]*/],
    panels:[{
        flag:'help',
        id:'help',
        type:0,
        title:'帮助',
        body:'1.环节可用鼠标拖拽<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鼠标选中环节后不释放，拖拽鼠标，环节将随鼠标移动，释放鼠标后，环节不再移动。<br>'+'2.路径的两个端点可以通过鼠标拖拽，改变路径指向的环节<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鼠标选中路径的端点后不释放，拖拽鼠标，路径被选中的端点将鼠标移动，在选中的环节释放鼠标后，路径被选中的端点将指向该选中的环节。<br>'+'3.ctrl+a，全选。<br>4.按住ctrl，鼠标选中对象，可以多选。<br>5.delete，删除选中对象。<br>6.使用鼠标多选<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鼠标点击空白处不释放，拖拽鼠标，会出现一个长方形虚框，虚框的范围随着鼠标移动而变化，虚框范围内的对象将被选中。'+'<br>'
    },{
        flag:'n',
        id:'n_prop_panel',
        type:1,
        title:'环节',
        tabs:[{
            id:'节点设置',
            val:0,
            type:'nodes'
        }]
	},{
        flag:'l',
        id:'l_prop_panel',
        type:1,
        title:'路径',
        tabs:[{
            id:'路径设置',
            val:0,
            type:'lines'
        }]
	}],
    clear:function(){
        for(var i=0;i<this.nodes.length;i++){
            var obj=this.nodes[i];
			for(var j=0;j<obj.length;j++){
                switch(obj[j].text){
                    case 'span':document.getElementById(obj[j].id).innerHTML='';
						break;
					default:document.getElementById(obj[j].id).value='';
						break;
                }
            }
        }
		for(var i=0;i<this.lines.length;i++){
            var obj=this.lines[i];
			for(var j=0;j<obj.length;j++){
                switch(obj[j].text){
                    case 'span':document.getElementById(obj[j].id).innerHTML='';
						break;
					default:document.getElementById(obj[j].id).value='';
						break;
                }
            }
        }
    },
    setProperty:function(type){
        var objs=null;
		if(type=='n')
			objs=this.nodes;
		else 
			objs=this.lines;
		var arr=[];
		for(var i=0;i<objs.length;i++){
            var obj=objs[i];
			for(var j=0;j<obj.length;j++){
                var v=null;
				switch(obj[j].text){
                    case 'span':v=document.getElementById(obj[j].id).innerHTML;
						break;
					default:v=document.getElementById(obj[j].id).value;
						break;
                }
				var json={
                    id:obj[j].id,
                    text:obj[j].text,
                    value:v
                };
				arr[arr.length]=json;
            }
        }
		return arr;
    }
}
function dwrCallbackWorkFlow(data){
	if(data == 1){
		sl_alert("保存成功");
		location.reload();
	}else{
		sl_alert("保存失败");
		return false;
	}	
}

function test(){
	var objCode = document.getElementById("objCode");
    alert(objCode.value);
}

function clickCheckBox(){}
</script>
</HEAD>
<BODY class='BODY'  onload="javascript:Prop.clear();document.getElementById('group').focus();">
<input id="objCode" name="objCode" type="hidden" value="">
<input id="project_id" name="project_id" type="hidden" value="<%=flow_no%>">
<input id="objCodeOptions" name="objCodeOptions" type="hidden" value="">
<div id="tool"></div>
<div id="group" onselectstart="javascript:return false;">
</div>
<script language="javascript">
var jsonShow = '<%=jsonStr%>';
var disNodStr = '<%=disNodStr%>'//不可删除的节点
var disNodArr = disNodStr.split("β");

//alert(jsonShow);
var m = new Menu();
m.init();
var w = new Window();
w.left = screen.availWidth/2 - 10;
w.init();
var g = new Group();
g.init();
g.setGroupArea();
g.initDisabledNodes(disNodArr);

if(jsonShow != ''){
	var jsonObj = JSON.decode(jsonShow);
	g.jsonTo(jsonObj);
}
//显示网格
//document.body.style.backgroundImage='url(img/bg.jpg)';
//禁止右键
document.oncontextmenu=mouseLock; 
function mouseLock(){ 
	event.returnValue=false; 
} 
</script>
</BODY>
</HTML>

<%

}catch(Exception e){

	throw e;

}

 %>