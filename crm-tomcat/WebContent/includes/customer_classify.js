/**
 * client_class_edit.js
 * author taochen
 * Date:
 * javascript code for 客户评级
 *
 */
/*获得对象对象方法*/
function $(id){
	var obj = document.getElementById(id);
	if(obj){
		return obj;
	}
	else{
		return false;
	}
};
/*全局对象 用于保存全局变量*/
var oState = {
	TEMP_MAP 				: {},//用于 缓存 DATA数据
	CUST_DATA 				: {},
	CUST_LEVEL_MAP 			: {},
	CUST_DENFINE_ID_ARRAY 	: [],
	send1					: 0,
	send2					: 0,
	accept1					: 0,
	accept2					: 0,
	cd_no                   : 1,
	arrayAppend				: function(tArray,value){
		for(var i = 0; i<tArray.length;i++){
			if(tArray[i]==value){
				return ;
			}
		}
		tArray.push(value);
	}
};
/**************************************************************************************/
/*数据对象类*/
function DataObj(data,str){
	var array = data.split(str);
	this.getClassId = function(){
		var classId = array[0];
		return classId;
	}
	this.getClassName = function(){
		var className = array[1];
		return className;
	}
	this.getParentId = function(){
		var parentId = array[2];
		return parentId;
	}
	this.getBottomFlag = function(){
		var bottomFlag = array[3];
		return bottomFlag;
	}
	this.getLevelId = function(){
		var levelId = array[4];
		return levelId;
	}
	this.getTableFlag = function(){
		var tableFlag = array[5];
		return tableFlag;
	}
	this.getDetailId = function(){
		var detailId = array[6];
		return detailId;
	}

};
/*树对象类*/
function Tree(divId,title){
	var oAll = true;//私有变量 是否展开
	var treePanel = $(divId);
	var treeChildNodes = treePanel.childNodes;
	var treeRootId = null;
	//寻找根节点
	for(var i=0;i<treeChildNodes.length;i++){
		treeRootId = treeChildNodes[i].id;
		if(treeRootId=="treeRoot") break;
	}
	//设置根节点
	if(treeRootId==null){
		var rootNode = NodeFactory.createRootNode(title);
		treePanel.appendChild(rootNode);
		treeRootId = rootNode.id;
	}
	///////////////////////////////////////////////////////
	//更改oAll
	this.getOAll = function(){
		var temp = oAll;
		return temp;
	}
	this.setOAll = function(tOAll){
		oAll = tOAll;
	}
	this.addNode = function(data,queryId){
		var dataObj = new DataObj(data,"$");
		var parentId = null;
		var levelId = dataObj.getLevelId();
		//取得父节点
		if(dataObj.getParentId()==null||dataObj.getParentId()==0){
			parentId = treeRootId;
		}else{
			parentId = "treeNode_"+ queryId;
		}
		var parentTreeNode = $(parentId);
		var isLast2 = checkIsLast(parentTreeNode);
		var newTreeNode = NodeFactory.createTreeNode(data,queryId,oAll);

		parentTreeNode.appendChild(newTreeNode);
	}
	function checkIsLast(parentNodeObj){
		var tempNode = parentNodeObj;
		if(tempNode.id=="treeRoot"){
			return 0;
		}
		while(true){
			if(tempNode.parentNode.id=="treeRoot"){
				break;
			}
			tempNode = tempNode.parentNode;
		}
		if(tempNode.nextSibling==null){
			return 1;
		}
		return 0;
	}
};
/***************************************树方法集合***********************************************/
var TreeEvent = {
	//连结节点的方法
	joinAction : function(imgObj){
		var childNode = imgObj.parentNode.firstChild;//得到父节点的第一个节点
		if(imgObj.title=="minus"||imgObj.title=="minusbottom"){
			//移除所有子节点
			while(true){
				var tempNode = childNode.nextSibling;
				if(childNode.tagName=="div"||childNode.tagName=="DIV"){
					imgObj.parentNode.removeChild(childNode);
				}
				childNode = tempNode;
				if(childNode==null){
					break;
				}
			}
		}
		else if(imgObj.title=="plus"||imgObj.title=="plusbottom"){
			var sTreePanel = new Tree("TreePanel","客户级别");
			var imgId = imgObj.id;
			var imgIdArray = imgId.split("_");
			var _classId = imgIdArray[1];
			var _parentId = imgIdArray[2];
			var t_checkBoxId = "imgCheckbox_"+_classId+"_"+_parentId;
			var checkType = $(t_checkBoxId).title;
			var queryId = _classId+"_"+_parentId;
			var detailId = oState.CUST_LEVEL_MAP[_classId];
			var t_checkBoxId2 = "imgCheckbox_"+detailId +"_"+ _classId;
			//修正选择img
			var queryArray = new Array();
			var queryIdArray = new Array();
			var data = oState.TEMP_MAP[queryId];

			queryArray.push(data);
			queryIdArray.push(queryId);

			while(queryArray.length>0){
				var dataArray = queryArray.shift();
				var temp_queryId = queryIdArray.shift();

				for(var i=0;(dataArray!=null)&&i<dataArray.length;i++){
					var dataObj = new DataObj(dataArray[i],"$");
					var classId = dataObj.getClassId();
					var parentId = dataObj.getParentId();
					if(parentId==null||parentId==''){
						parentId = 0;
					}

					var queryId = classId +"_"+ parentId;
					var tempArray = oState.TEMP_MAP[queryId];
					queryArray.push(tempArray);
					queryIdArray.push(queryId);
					sTreePanel.addNode(dataArray[i],temp_queryId);

					if(i==(dataArray.length-1)){
						IMG.TO_AMEND(dataObj);
					}
				}
			}
			if($(t_checkBoxId2)!=null&&$(t_checkBoxId2).title=="checkbox0"){
				$(t_checkBoxId2).onclick();
			}
		}
		IMG.CHANGE_IMG(imgObj);
	},
	//选中功能节点的发法
	checkAction : function(imgObj){
		var sysFlag = 0;//系统标志 标识该级别为系统定义，无需人工操作
		var checkType = imgObj.title;
		var checkBoxId = imgObj.id;
		//选中节点信息
		var tempArray = checkBoxId.split("_");
		var classId = tempArray[1];
		var	parentId = tempArray[2];
		//树节点对象
		var treeId = "treeNode_"+classId+"_"+parentId;
		var queryId = classId+"_"+parentId;
		//父节点
		if(checkType=="checkbox0"){
			imgObj.src = IMG.ICONS["checkbox1"];
			imgObj.title="checkbox1";
			oState.CUST_LEVEL_MAP[parentId] = classId;
			oState.arrayAppend(oState.CUST_DENFINE_ID_ARRAY,parentId);
		}
		else if(checkType=="checkbox1"){
			imgObj.src = IMG.ICONS["checkbox0"];
			imgObj.title="checkbox0";
			oState.CUST_LEVEL_MAP[parentId] = null;
			oState.arrayAppend(oState.CUST_DENFINE_ID_ARRAY,parentId);
		}
		//子节点
		if(imgObj.title=="checkbox0"){
			TreeEvent.setChildCheckbox(queryId);
		}
		//父节点级联选择
		while(true){
			var dataObjX = new DataObj($(treeId).title,"$");
			if(dataObjX.getParentId()==""||dataObjX.getParentId()==0){
				break;
			}
			//父节点图片变更
			var t_parentNode = $(treeId).parentNode;
			var t_parentId = t_parentNode.id;
			var tempArray = t_parentId.split("_");
			var p_classId = tempArray[1];
			var	p_parentId = tempArray[2];
			TreeEvent.childNodeSVerif(t_parentId,sysFlag);//父节点同步变更
			//当前节点单选

			if(dataObjX.getTableFlag()==1){
				var queryId2 = p_classId+"_"+p_parentId;
				var childArray = oState.TEMP_MAP[queryId2];

				for(var childNo=0;childNo<childArray.length;childNo++){
					var dataStr = childArray[childNo];
					var childObj = new DataObj(dataStr,"$");
					//类别ID
					if(childObj.getClassId()!=dataObjX.getClassId()){
						var t_childCheckImgId = "imgCheckbox_"+childObj.getClassId()+"_"+childObj.getParentId();
						if(sysFlag==1){
							var t_treeId = "treeNode_"+childObj.getClassId()+"_"+childObj.getParentId();
							var t_imgId = "imgJoin_"+childObj.getClassId()+"_"+childObj.getParentId();

							if($(t_treeId)){
								t_parentNode.removeChild($(t_treeId));
							}
						}
						else if($(t_childCheckImgId).title=="checkbox1"){
							//当前子节点修改为未选
							$(t_childCheckImgId).src = IMG.ICONS["checkbox0"];
							$(t_childCheckImgId).title="checkbox0";
							//遍历子节点支点
							var queryArray2 = new Array();
							var dataArray2 = oState.TEMP_MAP[childObj.getClassId()+"_"+childObj.getParentId()];
							queryArray2.push(dataArray2);

							while(queryArray2.length>0){
								var dataArray2 = queryArray2.shift();
								for(var i=0;(dataArray2!=null)&&i<dataArray2.length;i++){
									var dataObj2 = new DataObj(dataArray2[i],"$");
									var t_checkBoxId2 = "imgCheckbox_"+dataObj2.getClassId()+"_"+dataObj2.getParentId();
									var queryId2 = dataObj2.getClassId()+"_"+dataObj2.getParentId();
									var tempArray2 = oState.TEMP_MAP[queryId2];
									queryArray2.push(tempArray2);

									if($(t_checkBoxId2)!=null){
										$(t_checkBoxId2).src = IMG.ICONS["checkbox0"];
										$(t_checkBoxId2).title="checkbox0";
									}
								}
							}
						}
					}
				}
			}
			treeId = t_parentId;
		}
	},
	//父节点同步功能
	childNodeSVerif : function(t_parentId,sysFlag){
		var strValue = "checkbox0";
		var dataObjX = new DataObj($(t_parentId).title,"$");
		var classId  = dataObjX.getClassId();
		var parentId = dataObjX.getParentId();
		var levelId  = dataObjX.getLevelId();
		var queryId  = classId+"_"+parentId;
		var data     = oState.TEMP_MAP[queryId];
		var parentCheckNodeId = "imgCheckbox_"+classId+"_"+parentId;

		for(var i=0;(data!=null)&&i<data.length;i++){
			var dataObj = new DataObj(data[i],"$");
			var t_checkBoxId = "imgCheckbox_"+dataObj.getClassId()+"_"+dataObj.getParentId();

			if($(t_checkBoxId).title=="checkbox1"){
				strValue = "checkbox1";
				break;
			}
		}
		$(parentCheckNodeId).title=strValue;
		if(strValue=="checkbox1"){
			if(levelId%2==0&&sysFlag==0){
				$(parentCheckNodeId).src = IMG.ICONS["checkbox1"];
			}
			else{
				var v_joinId = "imgJoin_"+ classId + "_"+ parentId;
				$(parentCheckNodeId).src = IMG.ICONS["checkbox2"];
				if(sysFlag==1){
					$(v_joinId).onclick = function(){};
				}
			}
		}
		else if(strValue=="checkbox0"){
			$(parentCheckNodeId).src = IMG.ICONS["checkbox0"];
		}
	},
	//设置子节点
	setChildCheckbox : function(queryId){
		var queryArray = new Array();
		var data = oState.TEMP_MAP[queryId];
		queryArray.push(data);

		while(queryArray.length>0){
			var dataArray = queryArray.shift();
			for(var i=0;(dataArray!=null)&&i<dataArray.length;i++){
				var dataObj = new DataObj(dataArray[i],"$");
				var classId = dataObj.getClassId();
				var parentId = dataObj.getParentId();
				if(parentId==''||parentId==null){
					parentId = 0;
				}
				var t_checkBoxId = "imgCheckbox_"+classId+"_"+parentId;
				var queryId = dataObj.getClassId()+"_"+dataObj.getParentId();
				var tempArray = oState.TEMP_MAP[queryId];
				queryArray.push(tempArray);
				if($(t_checkBoxId)!=null){
					$(t_checkBoxId).src = IMG.ICONS["checkbox0"];
					$(t_checkBoxId).title="checkbox0";
				}
			}
		}
	},
	setCustLevel : function(){
		var cust_id = $('cust_id').value;
		var cust_level_array = oState.CUST_DATA[cust_id];
		for(var custLevelNo = 0 ;custLevelNo<cust_level_array.length;custLevelNo++){
			var data = cust_level_array[custLevelNo];
			var tArray = data.split("$");
			var parentId = tArray[0];
			var classId = tArray[1];
			var custCheckboxId = "imgCheckbox_"+classId+"_"+parentId;

			if($(custCheckboxId)!=null&&$(custCheckboxId).title=="checkbox0"){
				$(custCheckboxId).onclick();
			}
		}
		DataProxy.initFlag = true;
	}
};
/***************************************节点工厂类***********************************************/
var NodeFactory = {
	C 		: function(sTag){return document.createElement(sTag);},
	CText  	: function (str){
		var textNode = NodeFactory.C("span");
		textNode.innerText = str;
		return textNode;
	},
	//得到IMG树
	createImg : function(str){
		var imgObj = NodeFactory.C("img");
		imgObj.src = IMG.ICONS[str];
		return imgObj;
	},
	//连结节点
	createJoinImgNode : function(oAll,dataObj){
		var imgJoinNode = null;
		var imgName = null;
		var bottomFlag = dataObj.getBottomFlag();

		if(bottomFlag==1){
			imgJoinNode = NodeFactory.createImg("join");
			imgName = "join";
		}
		else if(bottomFlag==2){
			if(oAll){
				imgJoinNode = NodeFactory.createImg("minus");
				imgName = "minus";
			}else{
				imgJoinNode = NodeFactory.createImg("plus");
				imgName = "plus";
			}
		}

		imgJoinNode.id="imgJoin_"+ dataObj.getClassId()+"_"+dataObj.getParentId();
		imgJoinNode.title = imgName;
		imgJoinNode.onclick = function(){TreeEvent.joinAction(this);};

		return imgJoinNode;
	},
	//Head 节点
	createHeadImgNode : function(dataObj){
		var imgHeadNode = null;
		if(dataObj.getBottomFlag()==2){
			imgHeadNode = NodeFactory.createImg("folderopen");
		}else if(dataObj.getBottomFlag()==1){
			imgHeadNode = NodeFactory.createImg("emp");
		}
		imgHeadNode.id="imgHead_"+ dataObj.getClassId()+"_"+dataObj.getParentId();
		return imgHeadNode;
	},
	//选择节点
	createCheckBoxImg : function(dataObj){
		var img_checkbox = NodeFactory.createImg("checkbox0");
		var parentId = dataObj.getParentId();
		img_checkbox.id  = "imgCheckbox_"+dataObj.getClassId()+"_"+dataObj.getParentId();
		img_checkbox.title = "checkbox0";
		//img_checkbox.setAttribute("name", "checkbox_"+dataObj.getParentId());
		img_checkbox.name = "checkbox_"+dataObj.getParentId();
		if(dataObj.getTableFlag()!=2){
			img_checkbox.onclick = function(){TreeEvent.checkAction(this);}
		}
		return img_checkbox;
	},
	//得到ROOT节点
	createRootNode : function(title){
		var rootNode = NodeFactory.C("div");
		var img_root = NodeFactory.createImg("root");
		var img_checkbox0 = NodeFactory.createImg("checkbox0");
		var textn = NodeFactory.CText(title);
		rootNode.id="treeRoot";
		rootNode.appendChild(img_root);
		rootNode.appendChild(textn);
		//rootNode.appendChild(img_checkbox0);
		return rootNode;
	},
	//得到普通 节点 isLast 0-不是 1-是
	createTreeNode : function(data,queryId,oAll){
		var dataObj 	= 	new DataObj(data,"$");
		var levelId 	= 	parseInt(dataObj.getLevelId());
		var textn		 = 	NodeFactory.CText(dataObj.getClassName());
		var img_checkbox = 	NodeFactory.createCheckBoxImg(dataObj);
		var img_join 	 = 	NodeFactory.createJoinImgNode(oAll,dataObj);
		var treeNode 	= 	NodeFactory.C("div");
		var queryArray 	=  NodeFactory.getLineOrder(queryId);

		treeNode.id		=	"treeNode_"+ dataObj.getClassId() + "_" + dataObj.getParentId();
		treeNode.title 	= 	data;

		while(queryArray.length>0){
			var lineStr = queryArray.pop();
			var t_array = lineStr.split("_");
			var img_line = NodeFactory.createImg(t_array[0]);

			img_line.name = lineStr;
			treeNode.appendChild(img_line);
		}
		treeNode.appendChild(img_join);
		treeNode.appendChild(img_checkbox);
		treeNode.appendChild(textn);

		return treeNode;
	},
	//得到前置空和线的顺序
	getLineOrder : function(queryId){
		var theArray = new Array();
		var v_parentId = "treeNode_"+queryId;
		var v_parentNode = $(v_parentId);
		//如果父节点为空 或为根节点
		if(queryId==null||v_parentNode==null||v_parentNode.id=="treeRoot"){
			return theArray;
		}
		while(v_parentNode.id!="treeRoot"){
			var lineStr;
			if(v_parentNode.nextSibling==null||v_parentNode.id=="treeNode_1001_10"){
				lineStr = "empty_"+v_parentNode.id;
			}
			else{
				lineStr = "line_"+v_parentNode.id;
			}

			theArray.push(lineStr);
			v_parentNode = v_parentNode.parentNode;
		}
		return theArray;
	}
};
/***************************************节点工厂类***********************************************/
var DataProxy = {
	dwrFlag 	: true,
	initFlag    : false,
	paramsObjs 	: [],
	paramsObjs2	: [],//保存缓存

	getCustLevelArray : function(classId,parentId,levelId,tableFlag,detailId){
		DataProxy.dwrFlag = false;

		CustomerService.getCustClassDefine2(classId,parentId,levelId,oState.cd_no,tableFlag,detailId,{callback: function(data){
			oState.accept1 +=1;
			DataProxy.dwrFlag = true;
			//如果返回数组不为空
			if(data.length>1){
				var key = data[0];//第一个是父项
				var dataArray = data.slice(1);
				oState.TEMP_MAP[key] = dataArray;//放到MAP中
				//如果有多个数据
				for(var i = 0;i<dataArray.length;i++){
					var dataValue = dataArray[i];
					var dataObj = new DataObj(dataValue,"$");
					//如果有下一节点

					if(dataObj.getBottomFlag()==2){
						var v_classId = dataObj.getClassId();
						var v_parentId = dataObj.getParentId();

						var v_tableFlag = dataObj.getTableFlag();

						var v_detailId = dataObj.getDetailId();

						var v_LevelId = parseInt(dataObj.getLevelId())+1;
						var paramsObj = {classId:v_classId,parentId:v_parentId,levelId:v_LevelId,tableFlag:v_tableFlag,detailId:v_detailId};
						DataProxy.paramsObjs.push(paramsObj);
						oState.send1 +=1;
					}
				}
				DataProxy.getAllCustLevel();
			}
			else{
				//alert("未搜到数据！classId=" +classId+ "  levelId="+levelId);
			}
		}});
	},
	getCustLevelDataById : function(cust_id){
		oState.send2 +=1;
		DataProxy.dwrFlag = false;
		CustomerService.getCustomerClass(cust_id,{callback: function(data){ //获得客户已有的评级
			DataProxy.dwrFlag = true;
			oState.accept2 +=1;
			oState.CUST_DATA[cust_id] = data;

			for(var i=0 ;data!=null&&i<data.length; i++){
				var tempArray = data[i].split($);
				var parentId = tempArray[0];
				var classId = tempArray[1];
				oState.CUST_LEVEL_MAP[parentId] = classId;
			}
		}});
	},
	modiAction : function(cust_id,define_id,detail_id){
		var input_man = document.getElementById("inputMan").value;
		DataProxy.dwrFlag = false;
		CustomerService.modifyCustomerClass(cust_id,define_id,detail_id,input_man,{callback: function(data){
			DataProxy.dwrFlag = true;
			$("bsucess").value = parseInt($("bsucess").value) + 1;
		}});
	},
	getAllCustLevel : function(){
		if(DataProxy.paramsObjs.length>0){
			var paramsObj = DataProxy.paramsObjs.shift();
			var v_classId = paramsObj.classId;
			var v_parentId = paramsObj.parentId;
			var v_LevelId = parseInt(paramsObj.levelId);
			var v_tableFlag = parseInt(paramsObj.tableFlag);
			var v_detailId = paramsObj.detailId;
			if(DataProxy.dwrFlag){
				DataProxy.getCustLevelArray(v_classId,v_parentId,v_LevelId,v_tableFlag,v_detailId);
			}
			setTimeout("DataProxy.getAllCustLevel();",500);
		}
	},
	setDataArray : function(sTreePanel,data){
		var queryArray = new Array();
		var queryIdArray = new Array();
		queryArray.push(data);
		while(queryArray.length>0){
			var dataArray = queryArray.shift();
			var temp_queryId = queryIdArray.shift();
			for(var j = 0;(dataArray!=null)&&j<dataArray.length;j++){
				var dataObj = new DataObj(dataArray[j],"$");
				var classId = dataObj.getClassId();
				var parentId = dataObj.getParentId();
					if(parentId==null||parentId==''){
						parentId = 0;
					}
				var queryId = classId +"_"+ parentId;
				var tempArray = oState.TEMP_MAP[queryId];
					queryArray.push(tempArray);
					queryIdArray.push(queryId);
					sTreePanel.addNode(dataArray[j],temp_queryId);
				if(j==(dataArray.length-1)){
					IMG.TO_AMEND(dataObj);
				}
			}
		}
		TreeEvent.setCustLevel();
	},
	updateCustLevel : function(){
		if(DataProxy.paramsObjs2.length>0){
			var paramsObj = DataProxy.paramsObjs2.shift();
			var v_cust_id = paramsObj.cust_id;
			var v_define_id = paramsObj.define_id;
			var v_detail_id = paramsObj.classId;
			if(DataProxy.dwrFlag){

				DataProxy.modiAction(v_cust_id,v_define_id,v_detail_id);
			}
			setTimeout("DataProxy.updateCustLevel();",500);
		}
		else if($('bsucess').value==oState.CUST_DENFINE_ID_ARRAY.length){
			alert('保存成功！');
			window.location.href='classify.jsp';
		}
	},
	init : function(cust_id){
		var paramsObj = {classId:0,parentId:0,levelId:1,tableFlag:0,detailId:0};
		DataProxy.paramsObjs.push(paramsObj);
		oState.send1 +=1;
		DataProxy.getAllCustLevel();
		DataProxy.getCustLevelDataById(cust_id);
		setTimeout("DataProxy.showInit();",1000);
	},
	showInit : function(){
		if(oState.accept1==oState.send1&&oState.accept2==oState.send2){
			var sTreePanel = new Tree('TreePanel','客户级别');
			var data = oState.TEMP_MAP['0_0'];
			DataProxy.setDataArray(sTreePanel,data);
		}
		else{
			setTimeout("DataProxy.showInit();",1000);
		}
	}
}
