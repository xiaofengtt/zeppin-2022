$(function(){	
	getDate();
	$(".select-group select,.select-group input").css("width",($(".rangeInfo .col-md-9").width()-36)/2+"px");
	for(i=0;i<100;i++){
		weighting+="<option value='"+Number(i+1)+"'>"+Number(i+1)+"</option>";
	}
	$("#filterWeight").html(weighting);
	for(i=0;i<100;i++){
		old+="<option value='"+Number(i)+"'>"+Number(i)+"</option>";
	}
	$("#teachStartTime,#teacherEndTime,#startOldTime,#endOldTime").html(old);
	$(".mtcDiv").css("width",$(".rangeRight").width()-85+"px");
	if(filterId!=""){
		$("h3").html("编辑教师培训筛查器");
	}
});
var weighting="";//权重
var old="";
$(window).resize(function(){
	$(".select-group select").css("width",($(".rangeInfo .col-md-9").width()-36)/2+"px");
	$(".mtcDiv").css("width",$(".rangeRight").width()-85+"px");
})
//添加项目类型
function addFilterType(obj){
	var name=$(obj).attr("name");
	var content=$("#filter"+name).find(".clId").html();
	var id=$("#filter"+name).find(".clId").attr("name");
	if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){	
		$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="'+id+'">'+content+
				'</b><span class="close" onclick="deletes(this)">x</span></div>');
	}else{
		if(content=="全部"){
			layer.confirm('此筛查条件已有内容，不能添加全部选项', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if($("#show"+name).find(".mtcDiv b").html()=="全部"){
			layer.confirm('此筛查条件已有全部，不能添加其他选项', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else{
			$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="'+id+'">'+content+
					'</b><span class="close" onclick="deletes(this)">x</span></div>');
		}
	}
	
}
//原select
function addFilters(obj,mapName,selectId,map){
	var name=$(obj).attr("name");
	var content=$("#filter"+name).find("select option:selected").html();
	var id=$("#filter"+name).find("select").val();
	if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){	
		map.remove(id);
		yuanSelect(map,selectId);
		$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="'+id+'">'+content+
				'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+content+'\','+mapName+',\''+selectId+'\')">x</span></div>');
	}else{
		if(content=="全部"){
			layer.confirm('此筛查条件已有内容，不能添加全部选项', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if($("#show"+name).find(".mtcDiv b").html()=="全部"){
			layer.confirm('此筛查条件已有全部，不能添加其他选项', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else{
			map.remove(id);
			yuanSelect(map,selectId);
			$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="'+id+'">'+content+
					'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''
					+content+'\','+mapName+',\''+selectId+'\')">x</span></div>');
		}
	}
	
}
//筛选的select
function addFilter(obj,mapName,selectId,map){
	if(map.keys.length != 0) {
		var name=$(obj).attr("name");
		var content=$("#filter"+name).find(".select2-chosen").html();
		var id=$("#filter"+name).find("select").val();
		if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){	
			map.remove(id);
			yuanSelect(map,selectId);
			$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="'+id+'">'+content+'</b><span class="close" onclick="deleteSingle(this,\''
					+id+'\',\''+content+'\','+mapName+',\''+selectId+'\')">x</span></div>');
		}else{
			if(content=="全部"){
				layer.confirm('此筛查条件已有内容，不能添加全部选项', {
					btn : [ '确定' ]
				//按钮
				}, function() {
					layer.closeAll();
				});
				return false;
			}else if($("#show"+name).find(".mtcDiv b").html()=="全部"){
				layer.confirm('此筛查条件已有全部，不能添加其他选项', {
					btn : [ '确定' ]
				//按钮
				}, function() {
					layer.closeAll();
				});
				return false;
			}else{
				map.remove(id);
				yuanSelect(map,selectId);
				$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="'+id+'">'+content+'</b><span class="close" onclick="deleteSingle(this,\''
						+id+'\',\''+content+'\','+mapName+',\''+selectId+'\')">x</span></div>');
			}
		}
	}else{
		layer.confirm('已添加所有筛选条件', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}
}
//双输入框
function addFilterEach(obj,unit){
	var name=$(obj).attr("name");
	var contentStart=$("#filter"+name).find(".filter"+name+"Start").val();
	var contentEnd=$("#filter"+name).find(".filter"+name+"End").val();
	if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){	
		if(contentStart!=""&&contentEnd!=""){
			if($("#filter"+name).find(".filter"+name+"start").css("border-color")!="rgb(255, 0, 0)"&&$("#filter"+name).find(".filter"+name+"End").css("border-color")!="rgb(255, 0, 0)"){
				$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b start="'+contentStart+'" end="'
						+contentEnd+'">'+contentStart+'-'+contentEnd+unit+
				'</b><span class="close" onclick="deletes(this)">x</span></div>');
				return true;
			}else{
				layer.confirm('所填筛查条件有误，请填写正确的条件', {
					btn : [ '确定' ]
				//按钮
				}, function() {
					layer.closeAll();
				});
				return false;
			}	
		}else if(contentStart==""){
			$("#filter"+name).find(".filter"+name+"Start").css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('开始条件不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(contentEnd==""){
			$("#filter"+name).find(".filter"+name+"End").css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('结束条件不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}
		
	}else{
		layer.confirm('此筛查条件已有选项，不能添加多个条件', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}

}
//年份
function addFilterYear(obj,unit){
	var name=$(obj).attr("name");
	var contentStart=$("#filter"+name).find(".filter"+name+"Start").val();
	var contentEnd=$("#filter"+name).find(".filter"+name+"End").val();
	if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){	
		if(contentStart!="0"&&contentEnd!="0"){
			$("#filter"+name).find(".filter"+name+"Start").css({
				"border-color" : "#ccc",
				"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
				"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
			});
			$("#filter"+name).find(".filter"+name+"End").css({
				"border-color" : "#ccc",
				"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
				"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
			});
				$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b start="'+contentStart+'" end="'
						+contentEnd+'">'+contentStart+'-'+contentEnd+unit+
				'</b><span class="close" onclick="deletes(this)">x</span></div>');
				return true;	
		}else if(contentStart=="0"){
			$("#filter"+name).find(".filter"+name+"Start").css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('请选择开始年份', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(contentEnd=="0"){
			$("#filter"+name).find(".filter"+name+"End").css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('请选择结束年份', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}
		
	}else{
		layer.confirm('此筛查条件已有选项，不能添加多个条件', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}

}
//双语教师添加
function addFilterMutiLanguage(obj){
	var name=$(obj).attr("name");
	var contentStart=$("#filter"+name).find("input:checked").val();
	if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){		
		if(contentStart=="1"){
			$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="1">是</b><span class="close" onclick="deletes(this)">x</span></div>');	
		}else{
			$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b id="0">否</b><span class="close" onclick="deletes(this)">x</span></div>');	
		}			
	}else{
		layer.confirm('此筛查条件已有选项，不能添加多个条件', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}

}
//培训次数添加
function addFilterTimes(obj){
	var name=$(obj).attr("name");
	var contentStart=$("#filter"+name).find(".filter"+name+"Start option:selected").html();
	var contentEnd=$("#filter"+name).find(".filter"+name+"End option:selected").html();
	var contentInput=$("#filter"+name).find("input").val().replace(/(^\s*)|(\s*$)/g, "");
	if($("#show"+name).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){	
		if(contentInput!=""){
			$("#show"+name).find(".mtcDiv").append('<div class="mtcs"><b count1="'
					+$("#filter"+name).find(".filter"+name+"Start").val()+'" count2="'
					+$("#filter"+name).find(".filter"+name+"End").val()+'" count3="'+contentInput+'">'+contentStart+contentEnd+contentInput+
			'次</b><span class="close" onclick="deletes(this)">x</span></div>');		
		}else{
			$("#filter"+name).find("input").css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('培训次数不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}		
	}else{
		layer.confirm('此筛查条件已有选项，不能添加多个条件', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}

}
//删除多选的
function deleteSingle(obj,id,content,mapName,selectId){
	$(obj).parents(".mtcs").remove();
	mapName.put(id,content); 
	yuanSelect(mapName,selectId);
}
//删除单选的
function deletes(obj){
	$(obj).parents(".mtcs").remove();
}
//循环加载原select
function yuanSelect(mapName,selectId){
	var projectlevelStr="";
	mapName.each(function(key,value,index){//index是i,key和value是存的键值对
		if(value=="全部"){
			projectlevelStr='<option value="'+key+'" search="'+value+'">'+value+'</option>'+projectlevelStr;
		}else{
			projectlevelStr+='<option value="'+key+'" search="'+value+'">'+value+'</option>';
		}
    });
	$(selectId).find("select").html(projectlevelStr);
	if(mapName.keys.length == 0) {
		$(selectId).find(".select2-chosen").html("");
    }else{
    	$(selectId).find(".select2-chosen").html($(selectId).find("select option:eq(0)").html())
    }
	
	
}
var filterId = (url('?id') != null) ? url('?id') : '';
var page = (url('?page') != null) ? url('?page') : '1';	
//取消
function cancleBtn(){
	window.location.href="../admin/teacherTrainingFilter.jsp?page="+page;
}
var tArrayProjectlevel=[];
var tArrayProjectlevels=[];
var tArrayProjecttype=[];
var tArrayProjecttypes=[];
var tArrayProject=[];
var tArrayProjects=[];
var tArrayTrainingsubject=[];
var tArrayTrainingsubjects=[];
var tArrayTraininglanguage=[];
var tArrayTraininggrade=[];
var tArrayTeachingsubject=[];
var tArrayJobtitle=[];
var tArrayEthnic=[];
var tArrayPolitics=[];
var mtcProjectLevel="";
var mtcSubject="";
var mtcProjectType="";
var mtcProject="";
var mtcProjectLevels="";
var mtcSubjects="";
var mtcProjectTypes="";
var mtcProjects="";
var mtcTeachingGrade="";
var mtcEthnic="";
var mtcPolice="";
var mtcTeacherAge="";
var mtcJobTitle="";
var mtcTeachingLanguage="";
var mtcTeachingSubject="";
var mapProjectlevel =new Map();
var mapProjectlevels =new Map();
var mapProjecttype =new Map();
var mapProjecttypes =new Map();
var mapProject =new Map();
var mapProjects =new Map();
var mapTrainingsubject =new Map();
var mapTrainingsubjects =new Map();
var mapTraininglanguage=new Map();
var mapTeachinggrade=new Map();
var mapTeachingsubject=new Map();
var mapJobtitle=new Map();
var mapEthnic=new Map();
var mapPolitics=new Map();
var mapTeachingLanguage=new Map();
//获取页面数据
function getDate(){
	var id = (url('?id') != null) ? url('?id') : '';
	$.ajax({
        type: "GET",
        url: "../admin/sizer_load.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Result=="OK"){
        		var projectlevelStr="";
        		var projecttypeStr="";
        		var projectStr="";
        		var trainingsubjectStr="";
        		var teachinglanguageStr="";
        		var teachinggradeStr="";
        		var teachingsubjectStr="";
        		var jobtitleStr="";
        		var ethnicStr="";
        		var politicsStr="";
        		for (var i = 0; i < json.Records.projectlevel.length; i++) {
        			mapProjectlevel.put(json.Records.projectlevel[i].id,json.Records.projectlevel[i].name);
        			mapProjectlevels.put(json.Records.projectlevel[i].id,json.Records.projectlevel[i].name);
        		}	     		
        		for (var i = 0; i < json.Records.projecttype.length; i++) {
        			var childMap=new Map();
        			if(json.Records.projecttype[i].records!=undefined){  
        				var childrenMap=new Map();        				
        				for (var j = 0; j < json.Records.projecttype[i].records.length; j++) {
        					childrenMap.put(json.Records.projecttype[i].records[j].id,json.Records.projecttype[i].records[j].name);       					
//        					mapProjecttypes.put(json.Records.projecttype[i].records[j].id,json.Records.projecttype[i].records[j].name);   
        				}
        				childMap.put("id",json.Records.projecttype[i].id);
        				childMap.put("name",json.Records.projecttype[i].name);
        				childMap.put("datas",childrenMap);
        				mapProjecttype.put(json.Records.projecttype[i].id,childMap);
        				mapProjecttypes.put(json.Records.projecttype[i].id,childMap);
        			}else{
        				childMap.put("id",json.Records.projecttype[i].id);
        				childMap.put("name",json.Records.projecttype[i].name);
//        				childMap.put("datas",childrenMap);
        				mapProjecttype.put(json.Records.projecttype[i].id,childMap);
        				mapProjecttypes.put(json.Records.projecttype[i].id,childMap);
        			}
        		}
        		forTree(mapProjecttype); 
        		for (var i = 0; i < json.Records.project.length; i++) {	
        			mapProject.put(json.Records.project[i].id,json.Records.project[i].name);
        			mapProjects.put(json.Records.project[i].id,json.Records.project[i].name);
        		}
        		for (var i = 0; i < json.Records.trainingsubject.length; i++) {   
        			mapTrainingsubject.put(json.Records.trainingsubject[i].id,json.Records.trainingsubject[i].name);
        			mapTrainingsubjects.put(json.Records.trainingsubject[i].id,json.Records.trainingsubject[i].name);
        		}
        		for (var i = 0; i < json.Records.teachinglanguage.length; i++) {   
        			mapTeachingLanguage.put(json.Records.teachinglanguage[i].id,json.Records.teachinglanguage[i].name);
        		}
        		for (var i = 0; i < json.Records.teachinggrade.length; i++) {  
        			mapTeachinggrade.put(json.Records.teachinggrade[i].id,json.Records.teachinggrade[i].name);
        		}
        		for (var i = 0; i < json.Records.teachingsubject.length; i++) {
        			mapTeachingsubject.put(json.Records.teachingsubject[i].id,json.Records.teachingsubject[i].name);
        		}
        		for (var i = 0; i < json.Records.jobtitle.length; i++) {     
        			mapJobtitle.put(json.Records.jobtitle[i].id,json.Records.jobtitle[i].name);
        		}
        		for (var i = 0; i < json.Records.ethnic.length; i++) { 
        			mapEthnic.put(json.Records.ethnic[i].id,json.Records.ethnic[i].name);
        		}
        		for (var i = 0; i < json.Records.politics.length; i++) {
        			mapPolitics.put(json.Records.politics[i].id,json.Records.politics[i].name);
        		}
        		//筛查器信息
        		if(json.Records.teachersignupsizer!=undefined){
        			$("#filterName").val(json.Records.teachersignupsizer.name);
        			$("#filterState").val(json.Records.teachersignupsizer.status);
        			$("#filterWeight").val(json.Records.teachersignupsizer.weight);
        			//作用域范围部分
        			//总学时
        			if(json.Records.teachersignupsizer.actionScope.hours != null){
        				$("#showHours .mtcDiv").html('<div class="mtcs"><b start="'+json.Records.teachersignupsizer.actionScope.hours.starthours
            					+'" end="'+json.Records.teachersignupsizer.actionScope.hours.endhours
            					+'">'+json.Records.teachersignupsizer.actionScope.hours.starthours
            					+'-'+json.Records.teachersignupsizer.actionScope.hours.endhours
            					+'学时</b><span class="close" onclick="deletes(this)">x</span></div>');
        			} else {
        				$("#showHours .mtcDiv").html('<div class="mtcs"><b start="'+0
            					+'" end="'+0
            					+'">'+0
            					+'-'+0
            					+'学时</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}
        			
        			//项目级别
        			for(i=0;i<json.Records.teachersignupsizer.actionScope.projectLevel.length;i++){
        				var id=json.Records.teachersignupsizer.actionScope.projectLevel[i];
        				mtcProjectLevel+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.actionScope.projectLevel[i]+
        				'">'+mapProjectlevel.data[id]
        					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapProjectlevel.data[id]+
        					'\',mapProjectlevel,\'#filterLevel\')">x</span></div>';
        				mapProjectlevel.remove(id); 
        			}
        			$("#showLevel .mtcDiv").append(mtcProjectLevel);
            		
        			//培训学科
        			for(i=0;i<json.Records.teachersignupsizer.actionScope.subject.length;i++){
        				var id=json.Records.teachersignupsizer.actionScope.subject[i];
        				mtcSubject+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.actionScope.subject[i]+
        				'">'+mapTrainingsubject.data[id]
        					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapTrainingsubject.data[id]+
        					'\',mapTrainingsubject,\'#showSubject\')">x</span></div>';
        				mapTrainingsubject.remove(id); 
        			}
        			$("#showSubject .mtcDiv").append(mtcSubject);
        			//项目类型
          			for(i=0;i<json.Records.teachersignupsizer.actionScope.projectType.length;i++){
          				var id=json.Records.teachersignupsizer.actionScope.projectType[i];
          				mapProjecttype.each(function(key,value,index){
          					if(value.data.id==json.Records.teachersignupsizer.actionScope.projectType[i]){
          						mtcProjectType+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.actionScope.projectType[i]+
                 				'">'+value.data.name
                  					+'</b><span class="close" onclick="deletes(this)">x</span></div>';
          					}else{
          						if(value.data.datas != null||value.data.datas != undefined){
          							value.data.datas.each(function(keys,values,index){
          								if(keys==json.Records.teachersignupsizer.actionScope.projectType[i]){
          									mtcProjectType+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.actionScope.projectType[i]+
          	                 				'">'+values
          	                  					+'</b><span class="close" onclick="deletes(this)">x</span></div>';
          								}
          							})
              					}
          					}
          					
          				})
         			}
          			
         			$("#showType .mtcDiv").append(mtcProjectType);
         			forTree(mapProjecttype);  
        			//培训项目
        			for(i=0;i<json.Records.teachersignupsizer.actionScope.project.length;i++){
        				var id=json.Records.teachersignupsizer.actionScope.project[i];
        				mtcProject+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.actionScope.project[i]+
        				'">'+mapProject.data[id]
        					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapProject.data[id]+
        					'\',mapTrainingsubject,\'#showProject\')">x</span></div>';
        			}
        			$("#showProject .mtcDiv").append(mtcProject);
        			
        			//年份
        			$("#showYear .mtcDiv").html('<div class="mtcs"><b start="'+json.Records.teachersignupsizer.actionScope.years.startyear+
        					'" end="'+json.Records.teachersignupsizer.actionScope.years.endyear+
        					'">'+json.Records.teachersignupsizer.actionScope.years.startyear
        					+'-'+json.Records.teachersignupsizer.actionScope.years.endyear
        					+'年</b><span class="close" onclick="deletes(this)">x</span></div>');
        			//重复报名筛查
        			//总学时
        			if(json.Records.teachersignupsizer.reactionScope.hours!=null){
        				$("#showHourss .mtcDiv").html('<div class="mtcs"><b start="'+json.Records.teachersignupsizer.reactionScope.hours.starthours+
            					'" end="'+json.Records.teachersignupsizer.reactionScope.hours.endhours+
            					'">'+json.Records.teachersignupsizer.reactionScope.hours.starthours
            					+'-'+json.Records.teachersignupsizer.reactionScope.hours.endhours
            					+'学时</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}        			
        			//项目级别
        			if(json.Records.teachersignupsizer.reactionScope.projectLevel!=null){
        				for(i=0;i<json.Records.teachersignupsizer.reactionScope.projectLevel.length;i++){
        					var id=json.Records.teachersignupsizer.reactionScope.projectLevel[i];
            				mtcProjectLevels+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.reactionScope.projectLevel[i]+
            				'">'+mapProjectlevels.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapProjectlevels.data[id]+
            					'\',mapProjectlevels,\'#filterLevels\')">x</span></div>';
            				mapProjectlevels.remove(id); 
            			}
            			$("#showLevels .mtcDiv").append(mtcProjectLevels);
        			}   
        			
        			//培训学科
        			if(json.Records.teachersignupsizer.reactionScope.subject!=null){
        				for(i=0;i<json.Records.teachersignupsizer.reactionScope.subject.length;i++){
        					var id=json.Records.teachersignupsizer.reactionScope.subject[i];
            				mtcSubjects+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.reactionScope.subject[i]+
            				'">'+mapTrainingsubjects.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapTrainingsubjects.data[id]+
            					'\',mapTrainingsubjects,\'#filterSubjects\')">x</span></div>';
            				mapTrainingsubjects.remove(id); 
            			}
            			$("#showSubjects .mtcDiv").append(mtcSubjects);
        			}  
        			
        			//项目类型
        			if(json.Records.teachersignupsizer.reactionScope.projectType!=null){
        				for(i=0;i<json.Records.teachersignupsizer.reactionScope.projectType.length;i++){
        					mapProjecttypes.each(function(key,value,index){
              					if(value.data.id==json.Records.teachersignupsizer.reactionScope.projectType[i]){
              						mtcProjectTypes+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.reactionScope.projectType[i]+
                     				'">'+value.data.name
                      					+'</b><span class="close" onclick="deletes(this)">x</span></div>';
              					}else{
              						if(value.data.datas != null||value.data.datas != undefined){
              							value.data.datas.each(function(keys,values,index){
              								if(keys==json.Records.teachersignupsizer.reactionScope.projectType[i]){
              									mtcProjectTypes+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.reactionScope.projectType[i]+
              	                 				'">'+values
              	                  					+'</b><span class="close" onclick="deletes(this)">x</span></div>';
              								}
              							})
                  					}
              					}
              					
              				})
            			}
            			$("#showTypes .mtcDiv").append(mtcProjectTypes);
        			}        			
        			//培训项目
        			if(json.Records.teachersignupsizer.reactionScope.project!=null){
        				for(i=0;i<json.Records.teachersignupsizer.reactionScope.project.length;i++){
        					var id=json.Records.teachersignupsizer.reactionScope.project[i];
            				mtcProjects+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.reactionScope.project[i]+
            				'">'+mapProjects.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapProjects.data[id]+
            					'\',mapProjects,\'#filterProjects\')">x</span></div>';
            				mapProjects.remove(id);
            			}
            			$("#showProjects .mtcDiv").append(mtcProjects);
        			}      	
        			
        			//年份
        			if(json.Records.teachersignupsizer.reactionScope.years!=null){
        				$("#showYears .mtcDiv").html('<div class="mtcs"><b start="'+json.Records.teachersignupsizer.reactionScope.years.startyear
            					+'" end="'+json.Records.teachersignupsizer.reactionScope.years.endyear+
            					'">'+json.Records.teachersignupsizer.reactionScope.years.startyear
            					+'-'+json.Records.teachersignupsizer.reactionScope.years.endyear
            					+'年</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}       			
        			//培训次数
        			if(json.Records.teachersignupsizer.reactionScope.trainingCount!=null){
        				$("#showTimes .mtcDiv").html('<div class="mtcs"><b count1="'+
            					json.Records.teachersignupsizer.reactionScope.trainingCount.count1+'" count2="'+
                    			json.Records.teachersignupsizer.reactionScope.trainingCount.count2+'" count3="'+
                    			json.Records.teachersignupsizer.reactionScope.trainingCount.count3+'">'
            					+$("#filterTimes select:eq(0) option[value="+
            					json.Records.teachersignupsizer.reactionScope.trainingCount.count1+"]").text()
            					+$("#filterTimes select:eq(1) option[value="+
                    			json.Records.teachersignupsizer.reactionScope.trainingCount.count2+"]").text()
                    			+json.Records.teachersignupsizer.reactionScope.trainingCount.count3
                            	+'次</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}        			
        			//基本信息筛查
        			//教学学段
        			if(json.Records.teachersignupsizer.condition.teachingGrade!=null){
        				for(i=0;i<json.Records.teachersignupsizer.condition.teachingGrade.length;i++){
        					var id=json.Records.teachersignupsizer.condition.teachingGrade[i];
            				mtcTeachingGrade+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.teachingGrade[i]+
            				'">'+mapTeachinggrade.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapTeachinggrade.data[id]+
            					'\',mapTeachinggrade,\'#filterGrade\')">x</span></div>';
            				mapTeachinggrade.remove(id);
            			}
            			$("#showGrade .mtcDiv").append(mtcTeachingGrade);
        			}
        			
        			//民族
        			if(json.Records.teachersignupsizer.condition.ethnic!=null){
        				for(i=0;i<json.Records.teachersignupsizer.condition.ethnic.length;i++){
        					var id=json.Records.teachersignupsizer.condition.ethnic[i];
            				mtcEthnic+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.ethnic[i]+
            				'">'+mapEthnic.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapEthnic.data[id]+
            					'\',mapEthnic,\'#filterNation\')">x</span></div>';
            				mapEthnic.remove(id);
            			}
            			$("#showNation .mtcDiv").append(mtcEthnic);
        			}     			
        			
        			//政治面貌
        			if(json.Records.teachersignupsizer.condition.police!=null){
        				for(i=0;i<json.Records.teachersignupsizer.condition.police.length;i++){
        					var id=json.Records.teachersignupsizer.condition.police[i];
            				mtcPolice+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.police[i]+
            				'">'+mapPolitics.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapPolitics.data[id]+
            					'\',mapPolitics,\'#filterPolitice\')">x</span></div>';
            				mapPolitics.remove(id);
            			}
            			$("#showPolitice .mtcDiv").append(mtcPolice);
        			}		
        			
        			//年龄
        			if(json.Records.teachersignupsizer.condition.teacherAge!=null){
        				$("#showOld .mtcDiv").html('<div class="mtcs"><b start="'+json.Records.teachersignupsizer.condition.teacherAge.startAge+
            					'" end="'+json.Records.teachersignupsizer.condition.teacherAge.endAge+
            					'">'+json.Records.teachersignupsizer.condition.teacherAge.startAge
            					+'-'+json.Records.teachersignupsizer.condition.teacherAge.endAge
            					+'岁</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}
        			//职称
        			if(json.Records.teachersignupsizer.condition.jobTitle!=null){
        				for(i=0;i<json.Records.teachersignupsizer.condition.jobTitle.length;i++){
        					var id=json.Records.teachersignupsizer.condition.jobTitle[i];
            				mtcJobTitle+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.jobTitle[i]+
            				'">'+mapJobtitle.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapJobtitle.data[id]+
            					'\',mapJobtitle,\'#filterJobTitle\')">x</span></div>';
            				mapJobtitle.remove(id);
            			}
            			$("#showJobTitle .mtcDiv").append(mtcJobTitle);
        			}	
        			//双语教学
        			if(json.Records.teachersignupsizer.condition.multiLanguage!=null && json.Records.teachersignupsizer.condition.multiLanguage != ''){
        				$("#showMutiLanguage .mtcDiv").html('<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.multiLanguage+
            					'">'+$("#filterMutiLanguage input[value="+
            					json.Records.teachersignupsizer.condition.multiLanguage+"]").attr("text")
            					+'</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}
        			//教龄
        			if(json.Records.teachersignupsizer.condition.teachingAge!=null){
        				$("#showTeachTime .mtcDiv").html('<div class="mtcs"><b start="'+json.Records.teachersignupsizer.condition.teachingAge.startTimeAge+
            					'" end="'+json.Records.teachersignupsizer.condition.teachingAge.endTimeAge+
            					'">'+json.Records.teachersignupsizer.condition.teachingAge.startTimeAge
            					+'-'+json.Records.teachersignupsizer.condition.teachingAge.endTimeAge
            					+'年</b><span class="close" onclick="deletes(this)">x</span></div>');
        			}	
        			//主要教学语言
        			if(json.Records.teachersignupsizer.condition.teachingLanguage!=null){
        				for(i=0;i<json.Records.teachersignupsizer.condition.teachingLanguage.length;i++){
        					var id=json.Records.teachersignupsizer.condition.teachingLanguage[i];
            				mtcTeachingLanguage+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.teachingLanguage[i]+
            				'">'+mapTeachingLanguage.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapTeachingLanguage.data[id]+
            					'\',mapTeachingLanguage,\'#filterLanguage\')">x</span></div>';
            				mapTeachingLanguage.remove(id);
            			}
            			$("#showLanguage .mtcDiv").append(mtcTeachingLanguage);
        			}
        			
        			//主要教学学科
        			if(json.Records.teachersignupsizer.condition.teachingSubject!=null){
        				for(i=0;i<json.Records.teachersignupsizer.condition.teachingSubject.length;i++){
        					var id=json.Records.teachersignupsizer.condition.teachingSubject[i];
            				mtcTeachingSubject+='<div class="mtcs"><b id="'+json.Records.teachersignupsizer.condition.teachingSubject[i]+
            				'">'+mapTeachingsubject.data[id]
            					+'</b><span class="close" onclick="deleteSingle(this,\''+id+'\',\''+mapTeachingsubject.data[id]+
            					'\',mapTeachingsubject,\'#filterTeachSubject\')">x</span></div>';
            				mapTeachingsubject.remove(id);
            			}
            			$("#showTeachSubject .mtcDiv").append(mtcTeachingSubject);
        			}	
        			
        		}
        		//循环项目级别
    			projectlevelStr="";
    			mapProjectlevel.each(function(key,value,index){//index是i,key和value是存的键值对
    				projectlevelStr+='<option value="'+key+'">'+value+'</option>';
    		    });
    			$("#filterLevel select").html(projectlevelStr);
    			//循环培训学科
    			trainingsubjectStr="";
    			mapTrainingsubject.each(function(key,value,index){//index是i,key和value是存的键值对
    				trainingsubjectStr+='<option value="'+key+'" search="'
        			+value+'">'+value+'</option>';  
    		    });
    			$("#filterSubject select").html(trainingsubjectStr);
    			//循环培训项目
    			projectStr="";
    			mapProject.each(function(key,value,index){//index是i,key和value是存的键值对
    				projectStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterProject select").html(projectStr);
    			//循环项目级别
    			projectlevelsStr="";
    			mapProjectlevels.each(function(key,value,index){//index是i,key和value是存的键值对
    				projectlevelsStr+='<option value="'+key+'">'+value+'</option>';
    		    });
    			$("#filterLevels select").html(projectlevelsStr);
    			//循环培训学科
    			trainingsubjectsStr="";
    			mapTrainingsubjects.each(function(key,value,index){//index是i,key和value是存的键值对
    				trainingsubjectsStr+='<option value="'+key+'" search="'
        			+value+'">'+value+'</option>';  
    		    });
    			$("#filterSubjects select").html(trainingsubjectsStr);
    			//循环培训项目
    			projectsStr="";
    			mapProjects.each(function(key,value,index){//index是i,key和value是存的键值对
    				projectsStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterProjects select").html(projectsStr);
    			//循环教学学段
    			teachinggradeStr="";
    			mapTeachinggrade.each(function(key,value,index){//index是i,key和value是存的键值对
    				teachinggradeStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterGrade select").html(teachinggradeStr);
    			//循环民族
    			ethnicStr="";
    			mapEthnic.each(function(key,value,index){//index是i,key和value是存的键值对
    				ethnicStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterNation select").html(ethnicStr);
    			//循环政治面貌
    			politicsStr="";
    			mapPolitics.each(function(key,value,index){//index是i,key和value是存的键值对
    				politicsStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterPolitice select").html(politicsStr);
    			//循环职称
    			jobtitleStr="";
    			mapJobtitle.each(function(key,value,index){//index是i,key和value是存的键值对
    				jobtitleStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterJobTitle select").html(jobtitleStr);
    			//循环教学语言
    			teachinglanguageStr="";
    			mapTeachingLanguage.each(function(key,value,index){//index是i,key和value是存的键值对
    				teachinglanguageStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterLanguage select").html(teachinglanguageStr);
    			
    			//循环主要教学学科
    			teachingsubjectStr="";
    			mapTeachingsubject.each(function(key,value,index){//index是i,key和value是存的键值对
    				teachingsubjectStr+='<option value="'+key+'" search="'
        			+key+'">'+value+'</option>'; 
    		    });
    			$("#filterTeachSubject select").html(teachingsubjectStr);
        	}else{
        		layer.confirm(json.Message, {
    				btn : [ '确定' ]
    			//按钮
    			}, function() {
    				layer.closeAll();
    			});
        	}
        }
	}).done(function(){
		$('#filterGrade select,#filterProject select,#filterJobTitle select,#filterNation select,#filterPolitice select').select2();
		$('#filterProjects select,#filterSubjects select,#filterSubject select,#filterTeachSubject select,#filterLanguage select').select2();
	});
}
$(document).on("click", function(e) {
//	if (!$(e.target).parents().is('#filterType'))
//		$('#menuTree').hide();
//	if (!$(e.target).parents().is('#filterTypes'))
//		$('#menuTrees').hide();
});
var projectType = (url('?projectType')!= null) ? url('?projectType') : '0';
var str = "";
var strs="";
//树状结构
function forTree(o,l,id){	
	var urlstr = "";
	var urlstrs = "";
	o.each(function(key,value,index){
		//二级
		if(typeof value=="string"){
			urlstr = "<div><span onclick='getname(this)' class='span"+l+"'><a name='"+ key +"' pid='"+id+"'>"
			+ value
			+ "</a></span><ul>";
			urlstrs = "<div><span onclick='getnames(this)' class='span"+l+"'><a name='"+key +"' pid='"+id+"'>"
			+ value
			+ "</a></span><ul>";
		}else{
			value.each(function(keys,values,index){				
				urlstr = "<div class='parents'><span onclick='getname(this)' class='span"+l+"'><a name='"+ value.data.id +"'>"
				+ value.data.name
				+ "</a></span><ul>";
				urlstrs = "<div class='parents'><span onclick='getnames(this)' class='span"+l+"'><a name='"+ value.data.id +"'>"
				+ value.data.name
				+ "</a></span><ul>";		
			})
		} 
		str += urlstr;
		strs += urlstrs;
		if(value.data!=undefined){
			if (value.data.datas != null||value.data.datas != undefined) {
				forTree(value.data.datas,"2",value.data.id);
			}
		}
		
		str += "</ul></div>";
		strs += "</ul></div>";

	})
	$("#menuTree").html(str);
	$("#menuTrees").html(strs);
	var menuTree = function() {
		//给有子对象的元素加[+-]
		$("#menuTree ul,#menuTrees ul").each(function(index, element) {
			var ulContent = $(element).html();
			var spanContent = $(element).siblings("span").html();
			if (ulContent) {
				$(element).siblings("span").html("[+] " + spanContent);
			}
		});
		$("#menuTree,#menuTrees").find("div span").click(function(e) {
			var ul = $(this).siblings("ul");
			var spanStr = $(this).html();
			var spanContent = spanStr.substr(3, spanStr.length);
			if (ul.find("div").html() != null) {
				if (ul.css("display") == "none") {
					ul.show(300);
					$(this).html("[-] " + spanContent);
				} else {
					ul.hide(300);
					$(this).html("[+] " + spanContent);	
				}
			}
		})
	}()
}
function changeToggle(){
	$("#menuTree").toggle();
}
function changeToggles(){
	$("#menuTrees").toggle();
}
function getname(a){
//	window.event? window.event.cancelBubble = true : event.stopPropagation();
	if($(a).find("div").html() == null){
		$("#calId").html($(a).find("a").html());
	}
	$("#calId").attr("name",$(a).find("a").attr("name"));
	if($(a).find("a").attr("pid")!=undefined){
		$("#calId").attr("pid",$(a).find("a").attr("pid"));
	}else{
		$("#calId").removeAttr("pid");
	}	
}
function getnames(a){
//	window.event? window.event.cancelBubble = true : event.stopPropagation();
	if($(a).find("div").html() == null){
		$("#calIds").html($(a).find("a").html());
	}
	$("#calIds").attr("name",$(a).find("a").attr("name"));
	$("#calIds").attr("pid",$(a).find("a").attr("pid"));
}
function selectEndYearList(e){
	var this_ = $(e);
	var thisYear = this_.find("option:selected").val();
	$.getJSON('../admin/projectCycle_getYearList.action?startyear='+thisYear,function(data){
		if(data.Result == 'OK'){
			var yearStr = '';
			for(var i=0;i< data.Options.length;i++){
				var options = '<option value="'+data.Options[i].Value+'">'+data.Options[i].DisplayText+'</option>'
				
				yearStr += options;
			}
			
			this_.parent().find('select[name="endyear"]').html(yearStr);	
		}
		
	})
}
//名称校验
function nameBlur(obj){
	var value = $(obj).val();
	value = value.replace(/(^\s*)|(\s*$)/g, "");

	if (value != "") {
		$(obj).css({
			"border-color" : "#ccc",
			"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
			"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
		});
		return true;
	} else {
		$(obj).css({
			"border-color" : "#f00",
			"box-shadow" : "none"
		});
		layer.confirm('所填内容不能为空', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}
}

//总学时校验
function blurs(input1,input2,input3,input4){
	var reg=/^\d+$/; 
	var val1=$(input3).val().replace(/(^\s*)|(\s*$)/g, "");
	var val2=$(input4).val().replace(/(^\s*)|(\s*$)/g, "");
	if($(input1).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(reg.test($(input1).val().replace(/(^\s*)|(\s*$)/g, ""))){
			if($(input1).val().replace(/(^\s*)|(\s*$)/g, "")<=9999){
				if(reg.test(val2)&&val1<=val2){
					$(input1).css({
						"border-color" : "#ccc",
						"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
						"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
					});
					$(input2).css({
						"border-color" : "#ccc",
						"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
						"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
					});
					return true;
				}else if(Number(val1)>Number(val2)&&reg.test(val2)){
					$(input1).css({
						"border-color" : "#f00",
						"box-shadow" : "none"
					});
					layer.confirm('前面学时数不能大于后面学时数', {
						btn : [ '确定' ]
					//按钮
					}, function() {
						layer.closeAll();
					});
					return false;
				}else if($(input2).val().replace(/(^\s*)|(\s*$)/g, "")==""){
					$(input1).css({
						"border-color" : "#ccc",
						"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
						"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
					});
					return true;
				}
			}else{
				$(input1).css({
					"border-color" : "#f00",
					"box-shadow" : "none"
				});
				layer.confirm('所填必须为数字不能大于9999', {
					btn : [ '确定' ]
				//按钮
				}, function() {
					layer.closeAll();
				});
				return false;
			}
			
		}else{
			$(input1).css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('所填必须为数字且为正整数', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}
	}else{
		$(input1).css({
			"border-color" : "#f00",
			"box-shadow" : "none"
		});
		layer.confirm('所填内容不能为空', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}
}
//培训次数校验
$("#filterTimes input").blur(function(){
	var reg=/^\d+$/; 
	var val=$(this).val().replace(/(^\s*)|(\s*$)/g, "");
	if(val!=""){
		if(reg.test(val)){			
			$(this).css({
				"border-color" : "#ccc",
				"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
				"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
			});
			return true;
		}else{
			$(this).css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('所填必须为数字且为正整数', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}
	}else{
		$(this).css({
			"border-color" : "#f00",
			"box-shadow" : "none"
		});
		layer.confirm('所填内容不能为空', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
		return false;
	}
});
//教龄、年龄校验
function changes(input1,input2,input3,input4){
	$(input1).attr("changes","yes");
	var val1=Number($(input3).val());
	var val2=Number($(input4).val());
	if($(input2).attr("changes")=="yes"){
		if(val1<=val2){
			$(input1).css({
				"border-color" : "#ccc",
				"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
				"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
			});
			$(input2).css({
				"border-color" : "#ccc",
				"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
				"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
			});
			return true;
		}else if(val1>val2){
			$(input1).css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('前面的数字不能大于后面的数字', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}
	}else{
		$(input1).css({
			"border-color" : "#ccc",
			"box-shadow" : "inset 0 1px 1px rgba(0, 0, 0, .075)",
			"-webkit-box-shadow":"inset 0 1px 1px rgba(0, 0, 0, .075)",
		});
		return true;
	}
}
//验证作用域范围部分是否非空
function mtcDiv(obj){
	if($(obj).find(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){
		return false;
	}else{
		return true;
	}
}
//添加确定
function saveAndadd(){
	var rs1=nameBlur('#filterName');
	var rs2=mtcDiv("#showYear");
	var rs3=mtcDiv("#showLevel");
	var rs4=mtcDiv("#showType");
	var rs5=mtcDiv("#showProject");
	var rs6=mtcDiv("#showSubject");
	var rs7=mtcDiv("#showHours");
	if(rs1&&rs2&&rs3&&rs4&&rs5&&rs6&&rs7){
		var name=$("#filterName").val().replace(/(^\s*)|(\s*$)/g, "");
		var status=$("#filterState").val();
		var weight=$("#filterWeight").val();
		var startyear=$("#showYear b").attr("start");
		var endyear=$("#showYear b").attr("end");
		var projectlevel="";
		$("#showLevel .mtcs").each(function(i,obj){
			projectlevel+=$(this).find("b").attr("id")+",";
		});
		projectlevel=projectlevel.substring(0,projectlevel.length-1);
		var projecttype="";
		$("#showType .mtcs").each(function(i,obj){
			projecttype+=$(this).find("b").attr("id")+",";
		});
		projecttype=projecttype.substring(0,projecttype.length-1);
		var project="";
		$("#showProject .mtcs").each(function(i,obj){
			project+=$(this).find("b").attr("id")+",";
		});
		project=project.substring(0,project.length-1);
		var trainingsubject="";
		$("#showSubject .mtcs").each(function(i,obj){
			trainingsubject+=$(this).find("b").attr("id")+",";
		});
		trainingsubject=trainingsubject.substring(0,trainingsubject.length-1);
		var starthours=$("#showHours b").attr("start");
		var endhours=$("#showHours b").attr("end");
		var teachingGrade="";
		$("#showGrade .mtcs").each(function(i,obj){
			teachingGrade+=$(this).find("b").attr("id")+",";
		});
		teachingGrade=teachingGrade.substring(0,teachingGrade.length-1);
		var teachingSubject="";
		$("#showTeachSubject .mtcs").each(function(i,obj){
			teachingSubject+=$(this).find("b").attr("id")+",";
		});
		teachingSubject=teachingSubject.substring(0,teachingSubject.length-1);
		var teachingLanguage="";
		$("#showLanguage .mtcs").each(function(i,obj){
			teachingLanguage+=$(this).find("b").attr("id")+",";
		});
		teachingLanguage=teachingLanguage.substring(0,teachingLanguage.length-1);
		var multiLanguage=$("#showMutiLanguage b").attr("id");
		var jobTitle="";
		$("#showJobTitle .mtcs").each(function(i,obj){
			jobTitle+=$(this).find("b").attr("id")+",";
		});
		jobTitle=jobTitle.substring(0,jobTitle.length-1);
		var ethnic="";
		$("#showNation .mtcs").each(function(i,obj){
			ethnic+=$(this).find("b").attr("id")+",";
		});
		ethnic=ethnic.substring(0,ethnic.length-1);
		var startTeachingAge=$("#showTeachTime b").attr("start");
		var endTeachingAge=$("#showTeachTime b").attr("end");
		var startTeacherAge=$("#showOld b").attr("start");
		var endTeacherAge=$("#showOld b").attr("end");
		var police="";
		$("#showPolitice .mtcs").each(function(i,obj){
			police+=$(this).find("b").attr("id")+",";
		});
		police=police.substring(0,police.length-1);
		var count1=$("#showTimes b").attr("count1");
		var count2=$("#showTimes b").attr("count2");
		var count3=$("#showTimes b").attr("count3");
		var startyearre=$("#showYears b").attr("start");
		var endyearre=$("#showYears b").attr("end");
		var projectlevelre="";
		$("#showLevels .mtcs").each(function(i,obj){
			projectlevelre+=$(this).find("b").attr("id")+",";
		});
		projectlevelre=projectlevelre.substring(0,projectlevelre.length-1);
		var projecttypere="";
		$("#showTypes .mtcs").each(function(i,obj){
			projecttypere+=$(this).find("b").attr("id")+",";
		});
		projecttypere=projecttypere.substring(0,projecttypere.length-1);
		var projectre="";
		$("#showProjects .mtcs").each(function(i,obj){
			projectre+=$(this).find("b").attr("id")+",";
		});
		projectre=projectre.substring(0,projectre.length-1);
		var trainingsubjectre="";
		$("#showSubjects .mtcs").each(function(i,obj){
			trainingsubjectre+=$(this).find("b").attr("id")+",";
		});
		trainingsubjectre=trainingsubjectre.substring(0,trainingsubjectre.length-1);
		var starthoursre=$("#showHourss b").attr("start");
		var endhoursre=$("#showHourss b").attr("end");
		$.ajax({
	        type: "GET",
	        url: "../admin/sizer_save.action",
	        data: {id:filterId,name:name,status:status,weight:weight,startyear:startyear,endyear:endyear,projectlevel:projectlevel,projecttype:projecttype,project:project,trainingsubject:trainingsubject,starthours:starthours,endhours:endhours,teachingGrade:teachingGrade,teachingSubject:teachingSubject,teachingLanguage:teachingLanguage,multiLanguage:multiLanguage,jobTitle:jobTitle,ethnic:ethnic,startTeachingAge:startTeachingAge,endTeachingAge:endTeachingAge,startTeacherAge:startTeacherAge,endTeacherAge:endTeacherAge,police:police,count1:count1,count2:count2,count3:count3,startyearre:startyearre,endyearre:endyearre,projectlevelre:projectlevelre,projecttypere:projecttypere,projectre:projectre,trainingsubjectre:trainingsubjectre,starthoursre:starthoursre,endhoursre:endhoursre},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		if(filterId==""){
	        			window.location.href="../admin/teacherTrainingFilter.jsp";
	        		}else{
	        			window.location.href="../admin/teacherTrainingFilter.jsp?page="+page;
	        		}
	        	}else{
	        		layer.confirm(json.Message, {
	    				btn : [ '确定' ]
	    			//按钮
	    			}, function() {
	    				layer.closeAll();
	    			});
	    			return false;
	        	}
	        }
	    });
	}else{
		if(!rs1){
			$("#filterName").css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			layer.confirm('所填内容不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(!rs2){
			layer.confirm('定义筛查器作用域范围的年份不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(!rs3){
			layer.confirm('定义筛查器作用域范围的项目级别不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(!rs4){
			layer.confirm('定义筛查器作用域范围的项目类型不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(!rs5){
			layer.confirm('定义筛查器作用域范围的培训项目不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(!rs6){
			layer.confirm('定义筛查器作用域范围的培训学科不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}else if(!rs7){
			layer.confirm('定义筛查器作用域范围的总学时不能为空', {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
			return false;
		}
	}
}


